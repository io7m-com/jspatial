package tests.examples;

import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.MutableArea;
import com.io7m.jspatial.RayI2D;
import com.io7m.jspatial.quadtrees.QuadTreeBasic;
import com.io7m.jspatial.quadtrees.QuadTreeMemberType;
import com.io7m.jspatial.quadtrees.QuadTreeRaycastResult;
import com.io7m.jspatial.quadtrees.QuadTreeType;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorReadable2IType;

@SuppressWarnings("static-method") public final class TrivialQuad
{
  /**
   * An extremely simple class that implements {@link QuadTreeMemberType}.
   */

  static class Something implements QuadTreeMemberType<Something>
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

    private final VectorM2I         lower;

    private final VectorM2I         upper;

    Something(
      final VectorM2I in_lower,
      final VectorM2I in_upper)
      {
      this.id = Something.pool.incrementAndGet();
      this.lower = in_lower;
      this.upper = in_upper;
      }

    @Override public VectorReadable2IType boundingAreaLower()
    {
      return this.lower;
    }

    @Override public VectorReadable2IType boundingAreaUpper()
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
     * Create a quadtree of width and height 128, using the simplest
     * implementation the package provides.
     */

    final QuadTreeType<Something> tree =
      QuadTreeBasic.newQuadTree(new VectorM2I(128, 128), VectorI2I.ZERO);

    /**
     * Insert four objects into the tree. The sizes and positions of the
     * object will place one in each corner of the area described by the tree.
     */

    Something s0;
    Something s1;
    Something s2;
    Something s3;

    {
      final VectorM2I lower = new VectorM2I(0, 0);
      final VectorM2I upper = new VectorM2I(31, 31);
      s0 = new Something(lower, upper);
    }

    {
      final VectorM2I lower = new VectorM2I(64, 0);
      final VectorM2I upper = new VectorM2I(64 + 31, 31);
      s1 = new Something(lower, upper);
    }

    {
      final VectorM2I lower = new VectorM2I(0, 64);
      final VectorM2I upper = new VectorM2I(0 + 31, 64 + 31);
      s2 = new Something(lower, upper);
    }

    {
      final VectorM2I lower = new VectorM2I(64, 64);
      final VectorM2I upper = new VectorM2I(64 + 31, 64 + 31);
      s3 = new Something(lower, upper);
    }

    boolean inserted = true;
    inserted &= tree.quadTreeInsert(s0);
    inserted &= tree.quadTreeInsert(s1);
    inserted &= tree.quadTreeInsert(s2);
    inserted &= tree.quadTreeInsert(s3);

    Assert.assertTrue(inserted);

    /**
     * Now, select an area of the tree and check that the expected objects
     * were contained within the area.
     */

    {
      final MutableArea area = new MutableArea();
      area.setLower2i(0, 0);
      area.setUpper2i(40, 128);

      final TreeSet<Something> objects = new TreeSet<Something>();
      tree.quadTreeQueryAreaContaining(area, objects);

      Assert.assertEquals(2, objects.size());
      Assert.assertTrue(objects.contains(s0));
      Assert.assertFalse(objects.contains(s1));
      Assert.assertTrue(objects.contains(s2));
      Assert.assertFalse(objects.contains(s3));
    }

    /**
     * Now, select another area of the tree and check that the expected
     * objects were overlapped by the area.
     */

    {
      final MutableArea area = new MutableArea();
      area.setLower2i(0, 0);
      area.setUpper2i(80, 80);

      final TreeSet<Something> objects = new TreeSet<Something>();
      tree.quadTreeQueryAreaOverlapping(area, objects);

      Assert.assertEquals(4, objects.size());
      Assert.assertTrue(objects.contains(s0));
      Assert.assertTrue(objects.contains(s1));
      Assert.assertTrue(objects.contains(s2));
      Assert.assertTrue(objects.contains(s3));
    }

    /**
     * Now, cast a ray from (16,16) towards (128,128), and check that the
     * expected objects were intersected by the ray.
     *
     * Note that objects are returned in order of increasing distance: The
     * nearest object intersected by the ray will be the first in the returned
     * set.
     */

    {
      final VectorI2D origin = new VectorI2D(16, 16);
      final VectorI2D direction =
        VectorI2D.normalize(new VectorI2D(128, 128));
      final RayI2D ray = new RayI2D(origin, direction);

      final TreeSet<QuadTreeRaycastResult<Something>> objects =
        new TreeSet<QuadTreeRaycastResult<Something>>();
      tree.quadTreeQueryRaycast(ray, objects);

      Assert.assertEquals(2, objects.size());
      Assert.assertSame(objects.first().getObject(), s0);
      Assert.assertSame(objects.last().getObject(), s3);
    }
  }
}
