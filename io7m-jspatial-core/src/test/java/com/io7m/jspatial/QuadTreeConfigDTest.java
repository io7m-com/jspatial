package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;

public class QuadTreeConfigDTest
{
  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();
    Assert.assertFalse(c.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();
    Assert.assertFalse(c.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();
    final QuadTreeConfigD d = new QuadTreeConfigD();

    c.setSizeX(23);
    Assert.assertFalse(c.equals(d));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();
    final QuadTreeConfigD d = new QuadTreeConfigD();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.equals(d));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();
    Assert.assertEquals(c, c);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();
    final QuadTreeConfigD d = new QuadTreeConfigD();
    Assert.assertEquals(c, d);
    Assert.assertEquals(d, c);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsTransitive()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();
    final QuadTreeConfigD d = new QuadTreeConfigD();
    final QuadTreeConfigD e = new QuadTreeConfigD();
    Assert.assertEquals(c, d);
    Assert.assertEquals(d, e);
    Assert.assertEquals(c, e);
  }

  @SuppressWarnings("static-method") @Test public void testHashCode()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();
    final QuadTreeConfigD d = new QuadTreeConfigD();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.hashCode() == d.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testIdentities()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();

    Assert.assertEquals(
      QuadTreeConfigD.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_X,
      c.getMinimumSizeX());
    Assert.assertEquals(
      QuadTreeConfigD.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_Y,
      c.getMinimumSizeY());

    Assert
      .assertEquals(QuadTreeConfigD.QUADTREE_DEFAULT_SIZE_X, c.getSizeX());
    Assert
      .assertEquals(QuadTreeConfigD.QUADTREE_DEFAULT_SIZE_Y, c.getSizeY());

    Assert.assertEquals(
      QuadTreeConfigD.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_X,
      c.getMinimumSize().getXI());
    Assert.assertEquals(
      QuadTreeConfigD.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_Y,
      c.getMinimumSize().getYI());

    Assert.assertEquals(QuadTreeConfigD.QUADTREE_DEFAULT_SIZE_X, c
      .getSize()
      .getXI());
    Assert.assertEquals(QuadTreeConfigD.QUADTREE_DEFAULT_SIZE_Y, c
      .getSize()
      .getYI());

    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      QuadTreeConfigD.QUADTREE_DEFAULT_POSITION.getXD(),
      c.getPosition().getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      QuadTreeConfigD.QUADTREE_DEFAULT_POSITION.getYD(),
      c.getPosition().getYD()));
  }

  @SuppressWarnings("static-method") @Test public void testSetIdentities()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();

    c.setSizeX(2);
    c.setSizeY(4);

    c.setMinimumSizeX(5);
    c.setMinimumSizeY(7);

    c.setPositionX(20);
    c.setPositionY(21);

    Assert.assertEquals(5, c.getMinimumSizeX());
    Assert.assertEquals(7, c.getMinimumSizeY());

    Assert.assertEquals(2, c.getSizeX());
    Assert.assertEquals(4, c.getSizeY());

    Assert.assertEquals(5, c.getMinimumSize().getXI());
    Assert.assertEquals(7, c.getMinimumSize().getYI());

    Assert.assertEquals(2, c.getSize().getXI());
    Assert.assertEquals(4, c.getSize().getYI());

    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      20,
      c.getPositionX()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      21,
      c.getPositionY()));
  }

  @SuppressWarnings("static-method") @Test public void testToString()
  {
    final QuadTreeConfigD c = new QuadTreeConfigD();
    final QuadTreeConfigD d = new QuadTreeConfigD();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.toString().equals(d.toString()));
  }
}
