package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.BoundingVolumeCheck.Result;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3I;

public class BoundingVolumeCheckTest
{
  @SuppressWarnings("static-method") @Test public
    void
    testContainedAsymmetric()
  {
    final Cuboid cube0 =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(7, 7, 7));
    final Cuboid cube1 =
      new Cuboid(1, new VectorI3I(2, 2, 2), new VectorI3I(5, 5, 5));

    {
      final Result r = BoundingVolumeCheck.checkAgainst(cube0, cube1);
      Assert.assertEquals(Result.RESULT_CONTAINED_WITHIN, r);
    }

    {
      final Result r = BoundingVolumeCheck.checkAgainst(cube1, cube0);
      Assert.assertEquals(Result.RESULT_OVERLAP, r);
    }
  }

  @SuppressWarnings("static-method") @Test public void testContainedNot0()
  {
    final Cuboid cube0 =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(2, 2, 2));

    {
      final Cuboid cube1 =
        new Cuboid(1, new VectorI3I(3, 3, 3), new VectorI3I(5, 5, 5));
      final Result r0 = BoundingVolumeCheck.checkAgainst(cube0, cube1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingVolumeCheck.checkAgainst(cube1, cube0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }

    {
      final Cuboid cube1 =
        new Cuboid(1, new VectorI3I(0, 3, 3), new VectorI3I(5, 5, 5));
      final Result r0 = BoundingVolumeCheck.checkAgainst(cube0, cube1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingVolumeCheck.checkAgainst(cube1, cube0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }

    {
      final Cuboid cube1 =
        new Cuboid(1, new VectorI3I(3, 0, 3), new VectorI3I(5, 5, 5));
      final Result r0 = BoundingVolumeCheck.checkAgainst(cube0, cube1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingVolumeCheck.checkAgainst(cube1, cube0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testContainedReflexive()
  {
    final Cuboid cube =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(7, 7, 7));

    final Result r = BoundingVolumeCheck.checkAgainst(cube, cube);
    Assert.assertEquals(Result.RESULT_CONTAINED_WITHIN, r);
  }

  @SuppressWarnings("static-method") @Test public void testContainedSimple()
  {
    final Cuboid container =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(15, 15, 15));
    final Cuboid item =
      new Cuboid(1, new VectorI3I(0, 0, 0), new VectorI3I(7, 7, 7));

    final boolean in = BoundingVolumeCheck.containedWithin(container, item);
    Assert.assertTrue(in);
  }

  @SuppressWarnings("static-method") @Test public void testContains_0()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 8;
    final int a_y1 = 8;
    final int a_z1 = 8;

    final int b_x0 = 3;
    final int b_y0 = 3;
    final int b_z0 = 3;
    final int b_x1 = 5;
    final int b_y1 = 5;
    final int b_z1 = 5;

    Assert.assertTrue(BoundingVolumeCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testContainsNot_0()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_z0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testContainsNot_1()
  {
    final int a_x0 = 5;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 3;

    final int b_x0 = 4; // branch: b_x0 >= a_x0 == false
    final int b_y0 = 5;
    final int b_z0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testContainsNot_2()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_z0 = 5;
    final int b_x1 = 2; // branch: b_x1 <= a_x1 == false
    final int b_y1 = 8;
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testContainsNot_3()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 3;

    final int b_x0 = 5;
    final int b_y0 = -1; // branch: b_y0 >= a_y0 == false
    final int b_z0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testContainsNot_4()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_z0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 3; // branch: b_y1 <= a_y1 == true
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testContainsNot_5()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_z0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;
    final int b_z1 = -1; // branch: b_z1 <= a_z1 == true

    Assert.assertFalse(BoundingVolumeCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testContainsNot_6()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_z0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;
    final int b_z1 = 8; // branch: b_z1 <= a_z1 == false

    Assert.assertFalse(BoundingVolumeCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testContainsNot_7()
  {
    final int a_x0 = 8;
    final int a_y0 = 8;
    final int a_z0 = 8;
    final int a_x1 = 12;
    final int a_y1 = 12;
    final int a_z1 = 12;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_z0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testOverlapsComplete()
  {
    final Cuboid container =
      new Cuboid(0, new VectorI3I(5, 5, 5), new VectorI3I(10, 10, 10));
    final Cuboid c0 =
      new Cuboid(1, new VectorI3I(0, 0, 0), new VectorI3I(6, 6, 6));
    final Cuboid c1 =
      new Cuboid(1, new VectorI3I(5, 5, 5), new VectorI3I(12, 12, 12));

    boolean in = false;
    in = BoundingVolumeCheck.overlapsVolume(container, c0);
    Assert.assertTrue(in);
    in = BoundingVolumeCheck.overlapsVolume(container, c1);
    Assert.assertTrue(in);

    in = BoundingVolumeCheck.overlapsVolume(c0, container);
    Assert.assertTrue(in);
    in = BoundingVolumeCheck.overlapsVolume(c1, container);
    Assert.assertTrue(in);
  }

  @SuppressWarnings("static-method") @Test public void testOverlapsNot_0()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_z0 = 8;
    final int b_x1 = 8;
    final int b_y1 = 8;
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.overlaps(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testOverlapsNot_1()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_z0 = 5;
    final int b_x1 = -1; // branch: a_x0 < b_x1 == false
    final int b_y1 = 8;
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.overlaps(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testOverlapsNot_2()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 3;

    final int b_x0 = 2; // branch: a_x1 > b_x0 == true
    final int b_y0 = 5;
    final int b_z0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.overlaps(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testOverlapsNot_3()
  {
    final int a_x0 = 0;
    final int a_y0 = 8;
    final int a_z0 = 8;
    final int a_x1 = 3;
    final int a_y1 = 3;
    final int a_z1 = 0;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_z0 = 8;
    final int b_x1 = 8;
    final int b_y1 = 8; // branch: a_y0 < b_y1 == false
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.overlaps(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testOverlapsNot_4()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_z0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 6;
    final int a_z1 = 0;

    final int b_x0 = 5;
    final int b_y0 = 5; // branch: a_y1 > b_y0 == true
    final int b_z0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;
    final int b_z1 = 8;

    Assert.assertFalse(BoundingVolumeCheck.overlaps(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      a_z0,
      a_z1,
      b_x0,
      b_x1,
      b_y0,
      b_y1,
      b_z0,
      b_z1));
  }

  @SuppressWarnings("static-method") @Test public void testRayIntersection()
  {
    final VectorI3I lower = new VectorI3I(2, 2, 2);
    final VectorI3I upper = new VectorI3I(4, 4, 4);

    {
      // Intersect -X face in +X direction
      final VectorI3D origin = new VectorI3D(1, 3, 3);
      final VectorI3D direct = VectorI3D.normalize(new VectorI3D(1, 0, 0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        BoundingVolumeCheck.rayBoxIntersects(
          ray,
          lower.x,
          lower.y,
          lower.z,
          upper.x,
          upper.y,
          upper.z);
      Assert.assertTrue(i);
    }

    {
      // Intersect +X face in -X direction
      final VectorI3D origin = new VectorI3D(6, 3, 3);
      final VectorI3D direct = VectorI3D.normalize(new VectorI3D(-1, 0, 0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        BoundingVolumeCheck.rayBoxIntersects(
          ray,
          lower.x,
          lower.y,
          lower.z,
          upper.x,
          upper.y,
          upper.z);
      Assert.assertTrue(i);
    }

    {
      // Intersect +Y face in -Y direction
      final VectorI3D origin = new VectorI3D(3, 6, 3);
      final VectorI3D direct = VectorI3D.normalize(new VectorI3D(0, -1, 0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        BoundingVolumeCheck.rayBoxIntersects(
          ray,
          lower.x,
          lower.y,
          lower.z,
          upper.x,
          upper.y,
          upper.z);
      Assert.assertTrue(i);
    }

    {
      // Intersect -Y face in +Y direction
      final VectorI3D origin = new VectorI3D(3, 1, 3);
      final VectorI3D direct = VectorI3D.normalize(new VectorI3D(0, 1, 0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        BoundingVolumeCheck.rayBoxIntersects(
          ray,
          lower.x,
          lower.y,
          lower.z,
          upper.x,
          upper.y,
          upper.z);
      Assert.assertTrue(i);
    }

    {
      // Intersect -Z face in +Z direction
      final VectorI3D origin = new VectorI3D(3, 3, 1);
      final VectorI3D direct = VectorI3D.normalize(new VectorI3D(0, 0, 1));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        BoundingVolumeCheck.rayBoxIntersects(
          ray,
          lower.x,
          lower.y,
          lower.z,
          upper.x,
          upper.y,
          upper.z);
      Assert.assertTrue(i);
    }

    {
      // Intersect +Z face in -Z direction
      final VectorI3D origin = new VectorI3D(3, 3, 6);
      final VectorI3D direct = VectorI3D.normalize(new VectorI3D(0, 0, -1));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        BoundingVolumeCheck.rayBoxIntersects(
          ray,
          lower.x,
          lower.y,
          lower.z,
          upper.x,
          upper.y,
          upper.z);
      Assert.assertTrue(i);
    }
  }

  @SuppressWarnings("static-method") @Test public void testWellFormed()
  {
    final Cuboid cube =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(7, 7, 7));

    Assert.assertTrue(BoundingVolumeCheck.wellFormed(cube));
  }

  @SuppressWarnings("static-method") @Test public void testWellFormedNotX()
  {
    final Cuboid cube =
      new Cuboid(0, new VectorI3I(8, 0, 0), new VectorI3I(7, 7, 7));

    Assert.assertFalse(BoundingVolumeCheck.wellFormed(cube));
  }

  @SuppressWarnings("static-method") @Test public void testWellFormedNotY()
  {
    final Cuboid cube =
      new Cuboid(0, new VectorI3I(0, 8, 0), new VectorI3I(7, 7, 7));

    Assert.assertFalse(BoundingVolumeCheck.wellFormed(cube));
  }

  @SuppressWarnings("static-method") @Test public void testWellFormedNotZ()
  {
    final Cuboid cube =
      new Cuboid(0, new VectorI3I(0, 0, 8), new VectorI3I(7, 7, 7));

    Assert.assertFalse(BoundingVolumeCheck.wellFormed(cube));
  }
}
