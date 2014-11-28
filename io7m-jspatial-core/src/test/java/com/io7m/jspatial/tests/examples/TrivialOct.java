package com.io7m.jspatial.tests.examples;

import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.MutableVolume;
import com.io7m.jspatial.RayI3D;
import com.io7m.jspatial.octtrees.OctTreeBasic;
import com.io7m.jspatial.octtrees.OctTreeMemberType;
import com.io7m.jspatial.octtrees.OctTreeRaycastResult;
import com.io7m.jspatial.octtrees.OctTreeType;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorM3I;
import com.io7m.jtensors.VectorReadable3IType;

@SuppressWarnings("static-method") public final class TrivialOct
{
  /**
   * An extremely simple class that implements {@link OctTreeMemberType}.
   */

  static class Something implements OctTreeMemberType<Something>
  {
    /**
     * A "pool" of unique identifiers, shared between all objects of type
     * <code>Something</code>.
     */

    private static final AtomicLong pool = new AtomicLong(0);
    /**
     * The unique identifier of this object.
     */

    private final long              id;

    /**
     * The lower and upper corners of this object's axis-aligned bounding box.
     */

    private final VectorM3I         lower;

    private final VectorM3I         upper;

    Something(
      final VectorM3I in_lower,
      final VectorM3I in_upper)
      {
      this.id = Something.pool.incrementAndGet();
      this.lower = in_lower;
      this.upper = in_upper;
      }

    @Override public VectorReadable3IType boundingVolumeLower()
    {
      return this.lower;
    }

    @Override public VectorReadable3IType boundingVolumeUpper()
    {
      return this.upper;
    }

    @Override public int compareTo(
      final Something other)
    {
      if (this.id > other.id) {
        return 1;
      }
      if (this.id < other.id) {
        return -1;
      }
      return 0;
    }

  }

  @Test public void example()
  {
    /**
     * Create a octtree of width, height, and depth 128, using the simplest
     * implementation the package provides.
     */

    final OctTreeType<Something> tree =
      OctTreeBasic.newOctTree(new VectorI3I(128, 128, 128), VectorI3I.ZERO);

    /**
     * Insert eight objects into the tree. The sizes and positions of the
     * object will place one in each corner of the volume described by the
     * tree.
     */

    Something s0;
    Something s1;
    Something s2;
    Something s3;
    Something s4;
    Something s5;
    Something s6;
    Something s7;

    {
      final VectorM3I lower = new VectorM3I(0, 0, 0);
      final VectorM3I upper = new VectorM3I(31, 31, 31);
      s0 = new Something(lower, upper);
    }

    {
      final VectorM3I lower = new VectorM3I(64, 0, 0);
      final VectorM3I upper = new VectorM3I(64 + 31, 31, 31);
      s1 = new Something(lower, upper);
    }

    {
      final VectorM3I lower = new VectorM3I(0, 64, 0);
      final VectorM3I upper = new VectorM3I(31, 64 + 31, 31);
      s2 = new Something(lower, upper);
    }

    {
      final VectorM3I lower = new VectorM3I(64, 64, 0);
      final VectorM3I upper = new VectorM3I(64 + 31, 64 + 31, 31);
      s3 = new Something(lower, upper);
    }

    /**
     * Upper Z...
     */

    {
      final VectorM3I lower = new VectorM3I(0, 0, 64);
      final VectorM3I upper = new VectorM3I(31, 31, 64 + 31);
      s4 = new Something(lower, upper);
    }

    {
      final VectorM3I lower = new VectorM3I(64, 0, 64);
      final VectorM3I upper = new VectorM3I(64 + 31, 31, 64 + 31);
      s5 = new Something(lower, upper);
    }

    {
      final VectorM3I lower = new VectorM3I(0, 64, 64);
      final VectorM3I upper = new VectorM3I(31, 64 + 31, 64 + 31);
      s6 = new Something(lower, upper);
    }

    {
      final VectorM3I lower = new VectorM3I(64, 64, 64);
      final VectorM3I upper = new VectorM3I(64 + 31, 64 + 31, 64 + 31);
      s7 = new Something(lower, upper);
    }

    boolean inserted = true;
    inserted &= tree.octTreeInsert(s0);
    inserted &= tree.octTreeInsert(s1);
    inserted &= tree.octTreeInsert(s2);
    inserted &= tree.octTreeInsert(s3);
    inserted &= tree.octTreeInsert(s4);
    inserted &= tree.octTreeInsert(s5);
    inserted &= tree.octTreeInsert(s6);
    inserted &= tree.octTreeInsert(s7);

    Assert.assertTrue(inserted);

    /**
     * Now, select a volume of the tree and check that the expected objects
     * were contained within the volume.
     */

    {
      final MutableVolume volume = new MutableVolume();
      volume.setLower3i(0, 0, 0);
      volume.setUpper3i(40, 128, 40);

      final TreeSet<Something> objects = new TreeSet<Something>();
      tree.octTreeQueryVolumeContaining(volume, objects);

      Assert.assertEquals(2, objects.size());
      Assert.assertTrue(objects.contains(s0));
      Assert.assertFalse(objects.contains(s1));
      Assert.assertTrue(objects.contains(s2));
      Assert.assertFalse(objects.contains(s3));
    }

    /**
     * Now, select another volume of the tree and check that the expected
     * objects were overlapped by the volume.
     */

    {
      final MutableVolume volume = new MutableVolume();
      volume.setLower3i(0, 0, 80);
      volume.setUpper3i(80, 80, 128);

      final TreeSet<Something> objects = new TreeSet<Something>();
      tree.octTreeQueryVolumeOverlapping(volume, objects);

      Assert.assertEquals(4, objects.size());
      Assert.assertTrue(objects.contains(s4));
      Assert.assertTrue(objects.contains(s5));
      Assert.assertTrue(objects.contains(s6));
      Assert.assertTrue(objects.contains(s7));
    }

    /**
     * Now, cast a ray from (16,16,16) towards (128,128,16), and check that
     * the expected objects were intersected by the ray.
     *
     * Note that objects are returned in order of increasing distance: The
     * nearest object intersected by the ray will be the first in the returned
     * set.
     */

    {

      final VectorI3D origin = new VectorI3D(16, 16, 16);
      final VectorI3D direction =
        VectorI3D.normalize(new VectorI3D(128, 128, 16));
      final RayI3D ray = new RayI3D(origin, direction);

      final TreeSet<OctTreeRaycastResult<Something>> objects =
        new TreeSet<OctTreeRaycastResult<Something>>();
      tree.octTreeQueryRaycast(ray, objects);

      Assert.assertEquals(2, objects.size());
      Assert.assertSame(objects.first().getObject(), s0);
      Assert.assertSame(objects.last().getObject(), s3);
    }
  }
}
