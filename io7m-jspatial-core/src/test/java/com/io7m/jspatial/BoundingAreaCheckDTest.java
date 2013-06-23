package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.BoundingAreaCheck.Result;
import com.io7m.jtensors.VectorI2D;

public final class BoundingAreaCheckDTest
{
  @SuppressWarnings("static-method") @Test public
    void
    testDContainedAsymmetric()
  {
    final RectangleD rect0 =
      new RectangleD(0, new VectorI2D(0, 0), new VectorI2D(7, 7));
    final RectangleD rect1 =
      new RectangleD(1, new VectorI2D(2, 2), new VectorI2D(5, 5));

    {
      final Result r = BoundingAreaCheck.checkAgainstD(rect0, rect1);
      Assert.assertEquals(Result.RESULT_CONTAINED_WITHIN, r);
    }

    {
      final Result r = BoundingAreaCheck.checkAgainstD(rect1, rect0);
      Assert.assertEquals(Result.RESULT_OVERLAP, r);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDContainedNot0()
  {
    final RectangleD rect0 =
      new RectangleD(0, new VectorI2D(0, 0), new VectorI2D(2, 2));

    {
      final RectangleD rect1 =
        new RectangleD(1, new VectorI2D(3, 3), new VectorI2D(5, 5));
      final Result r0 = BoundingAreaCheck.checkAgainstD(rect0, rect1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingAreaCheck.checkAgainstD(rect1, rect0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }

    {
      final RectangleD rect1 =
        new RectangleD(1, new VectorI2D(0, 3), new VectorI2D(5, 5));
      final Result r0 = BoundingAreaCheck.checkAgainstD(rect0, rect1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingAreaCheck.checkAgainstD(rect1, rect0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }

    {
      final RectangleD rect1 =
        new RectangleD(1, new VectorI2D(3, 0), new VectorI2D(5, 5));
      final Result r0 = BoundingAreaCheck.checkAgainstD(rect0, rect1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingAreaCheck.checkAgainstD(rect1, rect0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDContainedIrreflexive()
  {
    final RectangleD rect =
      new RectangleD(0, new VectorI2D(0, 0), new VectorI2D(7, 7));

    final Result r = BoundingAreaCheck.checkAgainstD(rect, rect);
    Assert.assertFalse(r == Result.RESULT_CONTAINED_WITHIN);
  }

  @SuppressWarnings("static-method") @Test public void testDContainedSimple()
  {
    final RectangleD container =
      new RectangleD(0, new VectorI2D(0, 0), new VectorI2D(15, 15));
    final RectangleD item =
      new RectangleD(1, new VectorI2D(0, 0), new VectorI2D(7, 7));

    final boolean in = BoundingAreaCheck.containedWithinD(container, item);
    Assert.assertTrue(in);
  }

  @SuppressWarnings("static-method") @Test public void testDContainsNot_0()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.containsD(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testDContainsNot_1()
  {
    final int a_x0 = 5;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 4; // branch: b_x0 >= a_x0 == false
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.containsD(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testDContainsNot_2()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 2; // branch: b_x1 <= a_x1 == false
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.containsD(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testDContainsNot_3()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = -1; // branch: b_y0 >= a_y0 == false
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.containsD(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testDContainsNot_4()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 3; // branch: b_y1 <= a_y1 == true

    Assert.assertFalse(BoundingAreaCheck.containsD(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testDOverlapsNot_0()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlapsD(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testDOverlapsNot_1()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = -1; // branch: a_x0 < b_x1 == false
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlapsD(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testDOverlapsNot_2()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 2; // branch: a_x1 > b_x0 == true
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlapsD(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testDOverlapsNot_3()
  {
    final int a_x0 = 0;
    final int a_y0 = 8;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8; // branch: a_y0 < b_y1 == false

    Assert.assertFalse(BoundingAreaCheck.overlapsD(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testDOverlapsNot_4()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 6;

    final int b_x0 = 5;
    final int b_y0 = 5; // branch: a_y1 > b_y0 == true
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlapsD(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testDRayIntersection()
  {
    final VectorI2D lower = new VectorI2D(2, 2);
    final VectorI2D upper = new VectorI2D(4, 4);

    {
      // Intersect -X edge in +X direction
      final VectorI2D origin = new VectorI2D(1, 3);
      final VectorI2D direct = VectorI2D.normalize(new VectorI2D(1, 0));
      final RayI2D ray = new RayI2D(origin, direct);

      final boolean i =
        BoundingAreaCheck.rayBoxIntersects(
          ray,
          lower.x,
          lower.y,
          upper.x,
          upper.y);
      Assert.assertTrue(i);
    }

    {
      // Intersect +X edge in -X direction
      final VectorI2D origin = new VectorI2D(6, 3);
      final VectorI2D direct = VectorI2D.normalize(new VectorI2D(-1, 0));
      final RayI2D ray = new RayI2D(origin, direct);

      final boolean i =
        BoundingAreaCheck.rayBoxIntersects(
          ray,
          lower.x,
          lower.y,
          upper.x,
          upper.y);
      Assert.assertTrue(i);
    }

    {
      // Intersect +Y edge in -Y direction
      final VectorI2D origin = new VectorI2D(3, 6);
      final VectorI2D direct = VectorI2D.normalize(new VectorI2D(0, -1));
      final RayI2D ray = new RayI2D(origin, direct);

      final boolean i =
        BoundingAreaCheck.rayBoxIntersects(
          ray,
          lower.x,
          lower.y,
          upper.x,
          upper.y);
      Assert.assertTrue(i);
    }

    {
      // Intersect -Y edge in +Y direction
      final VectorI2D origin = new VectorI2D(3, 1);
      final VectorI2D direct = VectorI2D.normalize(new VectorI2D(0, 1));
      final RayI2D ray = new RayI2D(origin, direct);

      final boolean i =
        BoundingAreaCheck.rayBoxIntersects(
          ray,
          lower.x,
          lower.y,
          upper.x,
          upper.y);
      Assert.assertTrue(i);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDWellFormed()
  {
    final RectangleD rect =
      new RectangleD(0, new VectorI2D(0, 0), new VectorI2D(7, 7));

    Assert.assertTrue(BoundingAreaCheck.wellFormedD(rect));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDWellFormedNotXEq()
  {
    final RectangleD rect =
      new RectangleD(0, new VectorI2D(0, 0), new VectorI2D(0, 7));

    Assert.assertFalse(BoundingAreaCheck.wellFormedD(rect));
  }

  @SuppressWarnings("static-method") @Test public void testDWellFormedNotX()
  {
    final RectangleD rect =
      new RectangleD(0, new VectorI2D(8, 0), new VectorI2D(7, 7));

    Assert.assertFalse(BoundingAreaCheck.wellFormedD(rect));
  }

  @SuppressWarnings("static-method") @Test public void testDWellFormedNotY()
  {
    final RectangleD rect =
      new RectangleD(0, new VectorI2D(0, 8), new VectorI2D(7, 7));

    Assert.assertFalse(BoundingAreaCheck.wellFormedD(rect));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDWellFormedNotYEq()
  {
    final RectangleD rect =
      new RectangleD(0, new VectorI2D(0, 0), new VectorI2D(7, 0));

    Assert.assertFalse(BoundingAreaCheck.wellFormedD(rect));
  }

}
