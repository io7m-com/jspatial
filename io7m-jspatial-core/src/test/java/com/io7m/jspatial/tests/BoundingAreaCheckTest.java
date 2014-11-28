/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
 * 
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jspatial.tests;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.BoundingAreaCheck;
import com.io7m.jspatial.BoundingAreaCheck.Result;
import com.io7m.jspatial.RayI2D;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;

@SuppressWarnings("static-method") public class BoundingAreaCheckTest
{
  @Test public void testContainedAsymmetric()
  {
    final Rectangle rect0 =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(7, 7));
    final Rectangle rect1 =
      new Rectangle(1, new VectorI2I(2, 2), new VectorI2I(5, 5));

    {
      final Result r = BoundingAreaCheck.checkAgainst(rect0, rect1);
      Assert.assertEquals(Result.RESULT_CONTAINED_WITHIN, r);
    }

    {
      final Result r = BoundingAreaCheck.checkAgainst(rect1, rect0);
      Assert.assertEquals(Result.RESULT_OVERLAP, r);
    }
  }

  @Test public void testContainedNot0()
  {
    final Rectangle rect0 =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(2, 2));

    {
      final Rectangle rect1 =
        new Rectangle(1, new VectorI2I(3, 3), new VectorI2I(5, 5));
      final Result r0 = BoundingAreaCheck.checkAgainst(rect0, rect1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingAreaCheck.checkAgainst(rect1, rect0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }

    {
      final Rectangle rect1 =
        new Rectangle(1, new VectorI2I(0, 3), new VectorI2I(5, 5));
      final Result r0 = BoundingAreaCheck.checkAgainst(rect0, rect1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingAreaCheck.checkAgainst(rect1, rect0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }

    {
      final Rectangle rect1 =
        new Rectangle(1, new VectorI2I(3, 0), new VectorI2I(5, 5));
      final Result r0 = BoundingAreaCheck.checkAgainst(rect0, rect1);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r0);
      final Result r1 = BoundingAreaCheck.checkAgainst(rect1, rect0);
      Assert.assertEquals(Result.RESULT_NO_OVERLAP, r1);
    }
  }

  @Test public void testContainedReflexive()
  {
    final Rectangle rect =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(7, 7));

    final Result r = BoundingAreaCheck.checkAgainst(rect, rect);
    Assert.assertEquals(Result.RESULT_CONTAINED_WITHIN, r);
  }

  @Test public void testContainedSimple()
  {
    final Rectangle container =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(15, 15));
    final Rectangle item =
      new Rectangle(1, new VectorI2I(0, 0), new VectorI2I(7, 7));

    final boolean in = BoundingAreaCheck.containedWithin(container, item);
    Assert.assertTrue(in);
  }

  @Test public void testContainsNot_0()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @Test public void testContainsNot_1()
  {
    final int a_x0 = 5;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 4; // branch: b_x0 >= a_x0 == false
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @Test public void testContainsNot_2()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 2; // branch: b_x1 <= a_x1 == false
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @Test public void testContainsNot_3()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = -1; // branch: b_y0 >= a_y0 == false
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @Test public void testContainsNot_4()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 3; // branch: b_y1 <= a_y1 == true

    Assert.assertFalse(BoundingAreaCheck.contains(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @Test public void testOverlapsNot_0()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlaps(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @Test public void testOverlapsNot_1()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = -1; // branch: a_x0 < b_x1 == false
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlaps(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @Test public void testOverlapsNot_2()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 2; // branch: a_x1 > b_x0 == true
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlaps(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @Test public void testOverlapsNot_3()
  {
    final int a_x0 = 0;
    final int a_y0 = 8;
    final int a_x1 = 3;
    final int a_y1 = 3;

    final int b_x0 = 5;
    final int b_y0 = 5;
    final int b_x1 = 8;
    final int b_y1 = 8; // branch: a_y0 < b_y1 == false

    Assert.assertFalse(BoundingAreaCheck.overlaps(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @Test public void testOverlapsNot_4()
  {
    final int a_x0 = 0;
    final int a_y0 = 0;
    final int a_x1 = 3;
    final int a_y1 = 6;

    final int b_x0 = 5;
    final int b_y0 = 5; // branch: a_y1 > b_y0 == true
    final int b_x1 = 8;
    final int b_y1 = 8;

    Assert.assertFalse(BoundingAreaCheck.overlaps(
      a_x0,
      a_x1,
      a_y0,
      a_y1,
      b_x0,
      b_x1,
      b_y0,
      b_y1));
  }

  @Test public void testRayIntersection()
  {
    final VectorI2I lower = new VectorI2I(2, 2);
    final VectorI2I upper = new VectorI2I(4, 4);

    {
      // Intersect -X edge in +X direction
      final VectorI2D origin = new VectorI2D(1, 3);
      final VectorI2D direct = VectorI2D.normalize(new VectorI2D(1, 0));
      final RayI2D ray = new RayI2D(origin, direct);

      final boolean i =
        BoundingAreaCheck.rayBoxIntersects(
          ray,
          lower.getXI(),
          lower.getYI(),
          upper.getXI(),
          upper.getYI());
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
          lower.getXI(),
          lower.getYI(),
          upper.getXI(),
          upper.getYI());
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
          lower.getXI(),
          lower.getYI(),
          upper.getXI(),
          upper.getYI());
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
          lower.getXI(),
          lower.getYI(),
          upper.getXI(),
          upper.getYI());
      Assert.assertTrue(i);
    }
  }

  @Test public void testWellFormed()
  {
    final Rectangle rect =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(7, 7));

    Assert.assertTrue(BoundingAreaCheck.isWellFormed(rect));
  }

  @Test public void testWellFormedNotX()
  {
    final Rectangle rect =
      new Rectangle(0, new VectorI2I(8, 0), new VectorI2I(7, 7));

    Assert.assertFalse(BoundingAreaCheck.isWellFormed(rect));
  }

  @Test public void testWellFormedNotY()
  {
    final Rectangle rect =
      new Rectangle(0, new VectorI2I(0, 8), new VectorI2I(7, 7));

    Assert.assertFalse(BoundingAreaCheck.isWellFormed(rect));
  }
}
