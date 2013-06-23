package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.BoundingAreaCheck.Result;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2F;

public final class BoundingAreaCheckFTest
{
  @SuppressWarnings("static-method") @Test public
    void
    testFContainedAsymmetric()
  {
    final RectangleF rect0 =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(7, 7));
    final RectangleF rect1 =
      new RectangleF(1, new VectorI2F(2, 2), new VectorI2F(5, 5));

    {
      final Result r = BoundingAreaCheck.checkAgainstF(rect0, rect1);
      Assert.assertEquals(Result.RESULT_CONTAINED_WITHIN, r);
    }

    {
      final Result r = BoundingAreaCheck.checkAgainstF(rect1, rect0);
      Assert.assertEquals(Result.RESULT_OVERLAP, r);
    }
  }

  @SuppressWarnings("static-method") @Test public void testFContainedNot0()
  {
    final RectangleF rect0 =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(2, 2));

    {
      final RectangleF rect1 =
        new RectangleF(1, new VectorI2F(3, 3), new VectorI2F(5, 5));
      final Result r0 = BoundingAreaCheck.checkAgainstF(rect0, rect1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingAreaCheck.checkAgainstF(rect1, rect0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }

    {
      final RectangleF rect1 =
        new RectangleF(1, new VectorI2F(0, 3), new VectorI2F(5, 5));
      final Result r0 = BoundingAreaCheck.checkAgainstF(rect0, rect1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingAreaCheck.checkAgainstF(rect1, rect0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }

    {
      final RectangleF rect1 =
        new RectangleF(1, new VectorI2F(3, 0), new VectorI2F(5, 5));
      final Result r0 = BoundingAreaCheck.checkAgainstF(rect0, rect1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingAreaCheck.checkAgainstF(rect1, rect0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testFContainedIrreflexive()
  {
    final RectangleF rect =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(7, 7));

    final Result r = BoundingAreaCheck.checkAgainstF(rect, rect);
    Assert.assertFalse(r == Result.RESULT_CONTAINED_WITHIN);
  }

  @SuppressWarnings("static-method") @Test public void testFContainedSimple()
  {
    final RectangleF container =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(15, 15));
    final RectangleF item =
      new RectangleF(1, new VectorI2F(0, 0), new VectorI2F(7, 7));

    final boolean in = BoundingAreaCheck.containedWithinF(container, item);
    Assert.assertTrue(in);
  }

  @SuppressWarnings("static-method") @Test public void testFContainsNot_0()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.containsF(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testFContainsNot_1()
  {
    final int a_x0 = 5;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 4; // branch: b_x0 >= a_x0 == false
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.containsF(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testFContainsNot_2()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 2; // branch: b_x1 <= a_x1 == false
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.containsF(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testFContainsNot_3()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = -1; // branch: b_y0 >= a_y0 == false
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.containsF(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testFContainsNot_4()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 3; // branch: b_y1 <= a_y1 == true

    Assert.assertFalse(BoundingAreaCheck.containsF(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testFOverlapsNot_0()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlapsF(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testFOverlapsNot_1()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = -1; // branch: a_x0 < b_x1 == false
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlapsF(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testFOverlapsNot_2()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 2; // branch: a_x1 > b_x0 == true
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlapsF(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testFOverlapsNot_3()
  {
    final int a_x0 = 0;
    final int a_y0 = 8;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8; // branch: a_y0 < b_y1 == false

    Assert.assertFalse(BoundingAreaCheck.overlapsF(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testFOverlapsNot_4()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 6;

    final int b_x0 = 5;
    final int b_y0 = 5; // branch: a_y1 > b_y0 == true
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlapsF(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @SuppressWarnings("static-method") @Test public void testFRayIntersection()
  {
    final VectorI2F lower = new VectorI2F(2, 2);
    final VectorI2F upper = new VectorI2F(4, 4);

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

  @SuppressWarnings("static-method") @Test public void testFWellFormed()
  {
    final RectangleF rect =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(7, 7));

    Assert.assertTrue(BoundingAreaCheck.wellFormedF(rect));
  }

  @SuppressWarnings("static-method") @Test public void testFWellFormedNotX()
  {
    final RectangleF rect =
      new RectangleF(0, new VectorI2F(8, 0), new VectorI2F(7, 7));

    Assert.assertFalse(BoundingAreaCheck.wellFormedF(rect));
  }

  @SuppressWarnings("static-method") @Test public void testFWellFormedNotY()
  {
    final RectangleF rect =
      new RectangleF(0, new VectorI2F(0, 8), new VectorI2F(7, 7));

    Assert.assertFalse(BoundingAreaCheck.wellFormedF(rect));
  }

}
