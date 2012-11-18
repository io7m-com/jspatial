package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jtensors.VectorI3I;

public class OctTreeRaycastResultTest
{
  @SuppressWarnings("static-method") @Test public void testEquals()
  {
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);
    final OctTreeRaycastResult<Cuboid> rr1 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);

    Assert.assertEquals(rr0, rr1);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);

    Assert.assertFalse(rr0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);

    Assert.assertFalse(rr0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);
    final OctTreeRaycastResult<Cuboid> rr1 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 2.0);

    Assert.assertFalse(rr0.equals(rr1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);
    final OctTreeRaycastResult<Cuboid> rr1 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2)), 1.0);

    Assert.assertFalse(rr0.equals(rr1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);

    Assert.assertEquals(rr0, rr0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);
    final OctTreeRaycastResult<Cuboid> rr1 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);

    Assert.assertEquals(rr0, rr1);
    Assert.assertEquals(rr1, rr0);
  }

  @SuppressWarnings("static-method") @Test public void testGet()
  {
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(1, 1, 1));
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(r, 1.0);

    Assert.assertTrue(rr0.getDistance() == 1.0);
    Assert.assertTrue(rr0.getObject() == r);
  }

  @SuppressWarnings("static-method") @Test public void testHashcode()
  {
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);
    final OctTreeRaycastResult<Cuboid> rr1 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);
    final OctTreeRaycastResult<Cuboid> rr2 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2)), 1.0);
    final OctTreeRaycastResult<Cuboid> rr3 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2)), 2.0);

    Assert.assertTrue(rr0.hashCode() == rr1.hashCode());
    Assert.assertFalse(rr0.hashCode() == rr2.hashCode());
    Assert.assertFalse(rr0.hashCode() == rr3.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testOrdering()
  {
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(1, 1, 1));
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(r, 1.0);
    final OctTreeRaycastResult<Cuboid> rr1 =
      new OctTreeRaycastResult<Cuboid>(r, 2.0);
    final OctTreeRaycastResult<Cuboid> rr2 =
      new OctTreeRaycastResult<Cuboid>(r, 3.0);

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
    final OctTreeRaycastResult<Cuboid> rr0 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);
    final OctTreeRaycastResult<Cuboid> rr1 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), 1.0);
    final OctTreeRaycastResult<Cuboid> rr2 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2)), 1.0);
    final OctTreeRaycastResult<Cuboid> rr3 =
      new OctTreeRaycastResult<Cuboid>(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2)), 2.0);

    Assert.assertTrue(rr0.toString().equals(rr1.toString()));
    Assert.assertTrue(rr0.toString().equals(rr0.toString()));
    Assert.assertFalse(rr0.toString().equals(rr2.toString()));
    Assert.assertFalse(rr0.toString().equals(rr3.toString()));
  }
}
