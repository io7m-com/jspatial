package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jtensors.VectorI2F;

public class QuadTreeRaycastResultFTest
{
  @SuppressWarnings("static-method") @Test public void testEquals()
  {
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr1 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);

    Assert.assertEquals(rr0, rr1);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);

    Assert.assertFalse(rr0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);

    Assert.assertFalse(rr0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr1 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 2.0);

    Assert.assertFalse(rr0.equals(rr1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr1 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(2, 2)), 1.0);

    Assert.assertFalse(rr0.equals(rr1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);

    Assert.assertEquals(rr0, rr0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr1 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);

    Assert.assertEquals(rr0, rr1);
    Assert.assertEquals(rr1, rr0);
  }

  @SuppressWarnings("static-method") @Test public void testGet()
  {
    final RectangleF r =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(1, 1));
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(r, 1.0);

    Assert.assertTrue(rr0.getDistance() == 1.0);
    Assert.assertTrue(rr0.getObject() == r);
  }

  @SuppressWarnings("static-method") @Test public void testHashcode()
  {
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr1 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr2 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(2, 2)), 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr3 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(2, 2)), 2.0);

    Assert.assertTrue(rr0.hashCode() == rr1.hashCode());
    Assert.assertFalse(rr0.hashCode() == rr2.hashCode());
    Assert.assertFalse(rr0.hashCode() == rr3.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testOrdering()
  {
    final RectangleF r =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(1, 1));
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(r, 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr1 =
      new QuadTreeRaycastResultF<RectangleF>(r, 2.0);
    final QuadTreeRaycastResultF<RectangleF> rr2 =
      new QuadTreeRaycastResultF<RectangleF>(r, 3.0);

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
    final QuadTreeRaycastResultF<RectangleF> rr0 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr1 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(1, 1)), 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr2 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(2, 2)), 1.0);
    final QuadTreeRaycastResultF<RectangleF> rr3 =
      new QuadTreeRaycastResultF<RectangleF>(new RectangleF(0, new VectorI2F(
        0,
        0), new VectorI2F(2, 2)), 2.0);

    Assert.assertTrue(rr0.toString().equals(rr1.toString()));
    Assert.assertTrue(rr0.toString().equals(rr0.toString()));
    Assert.assertFalse(rr0.toString().equals(rr2.toString()));
    Assert.assertFalse(rr0.toString().equals(rr3.toString()));
  }
}
