package com.io7m.jspatial.tests.quadtrees;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.quadtrees.QuadTreeRaycastResult;
import com.io7m.jspatial.tests.Rectangle;
import com.io7m.jtensors.VectorI2I;

public class QuadTreeRaycastResultTest
{
  @SuppressWarnings("static-method") @Test public void testEquals()
  {
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);
    final QuadTreeRaycastResult<Rectangle> rr1 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);

    Assert.assertEquals(rr0, rr1);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);

    Assert.assertFalse(rr0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);

    Assert.assertFalse(rr0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);
    final QuadTreeRaycastResult<Rectangle> rr1 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 2.0);

    Assert.assertFalse(rr0.equals(rr1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);
    final QuadTreeRaycastResult<Rectangle> rr1 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(2, 2)), 1.0);

    Assert.assertFalse(rr0.equals(rr1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);

    Assert.assertEquals(rr0, rr0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);
    final QuadTreeRaycastResult<Rectangle> rr1 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);

    Assert.assertEquals(rr0, rr1);
    Assert.assertEquals(rr1, rr0);
  }

  @SuppressWarnings("static-method") @Test public void testGet()
  {
    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(1, 1));
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(r, 1.0);

    Assert.assertTrue(rr0.getDistance() == 1.0);
    Assert.assertTrue(rr0.getObject() == r);
  }

  @SuppressWarnings("static-method") @Test public void testHashcode()
  {
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);
    final QuadTreeRaycastResult<Rectangle> rr1 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);
    final QuadTreeRaycastResult<Rectangle> rr2 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(2, 2)), 1.0);
    final QuadTreeRaycastResult<Rectangle> rr3 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(2, 2)), 2.0);

    Assert.assertTrue(rr0.hashCode() == rr1.hashCode());
    Assert.assertFalse(rr0.hashCode() == rr2.hashCode());
    Assert.assertFalse(rr0.hashCode() == rr3.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testOrdering()
  {
    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(1, 1));
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(r, 1.0);
    final QuadTreeRaycastResult<Rectangle> rr1 =
      new QuadTreeRaycastResult<Rectangle>(r, 2.0);
    final QuadTreeRaycastResult<Rectangle> rr2 =
      new QuadTreeRaycastResult<Rectangle>(r, 3.0);

    Assert.assertEquals(0, rr0.compareTo(rr0));
    Assert.assertEquals(0, rr1.compareTo(rr1));
    Assert.assertEquals(0, rr2.compareTo(rr2));
    Assert.assertEquals(-1, rr0.compareTo(rr1));
    Assert.assertEquals(1, rr1.compareTo(rr0));
    Assert.assertEquals(-1, rr1.compareTo(rr2));
    Assert.assertEquals(1, rr2.compareTo(rr1));
  }

  @SuppressWarnings("static-method") @Test public void testToString()
  {
    final QuadTreeRaycastResult<Rectangle> rr0 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);
    final QuadTreeRaycastResult<Rectangle> rr1 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(1, 1)), 1.0);
    final QuadTreeRaycastResult<Rectangle> rr2 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(2, 2)), 1.0);
    final QuadTreeRaycastResult<Rectangle> rr3 =
      new QuadTreeRaycastResult<Rectangle>(new Rectangle(0, new VectorI2I(
        0,
        0), new VectorI2I(2, 2)), 2.0);

    Assert.assertTrue(rr0.toString().equals(rr1.toString()));
    Assert.assertTrue(rr0.toString().equals(rr0.toString()));
    Assert.assertFalse(rr0.toString().equals(rr2.toString()));
    Assert.assertFalse(rr0.toString().equals(rr3.toString()));
  }
}
