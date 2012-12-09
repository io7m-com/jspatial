package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

public class QuadTreeConfigTest
{
  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final QuadTreeConfig c = new QuadTreeConfig();
    Assert.assertFalse(c.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final QuadTreeConfig c = new QuadTreeConfig();
    Assert.assertFalse(c.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final QuadTreeConfig c = new QuadTreeConfig();
    final QuadTreeConfig d = new QuadTreeConfig();

    c.setSizeX(23);
    Assert.assertFalse(c.equals(d));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final QuadTreeConfig c = new QuadTreeConfig();
    final QuadTreeConfig d = new QuadTreeConfig();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.equals(d));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final QuadTreeConfig c = new QuadTreeConfig();
    Assert.assertEquals(c, c);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final QuadTreeConfig c = new QuadTreeConfig();
    final QuadTreeConfig d = new QuadTreeConfig();
    Assert.assertEquals(c, d);
    Assert.assertEquals(d, c);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsTransitive()
  {
    final QuadTreeConfig c = new QuadTreeConfig();
    final QuadTreeConfig d = new QuadTreeConfig();
    final QuadTreeConfig e = new QuadTreeConfig();
    Assert.assertEquals(c, d);
    Assert.assertEquals(d, e);
    Assert.assertEquals(c, e);
  }

  @SuppressWarnings("static-method") @Test public void testHashCode()
  {
    final QuadTreeConfig c = new QuadTreeConfig();
    final QuadTreeConfig d = new QuadTreeConfig();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.hashCode() == d.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testIdentities()
  {
    final QuadTreeConfig c = new QuadTreeConfig();

    Assert.assertEquals(
      QuadTreeConfig.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_X,
      c.getMinimumSizeX());
    Assert.assertEquals(
      QuadTreeConfig.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_Y,
      c.getMinimumSizeY());

    Assert.assertEquals(QuadTreeConfig.QUADTREE_DEFAULT_SIZE_X, c.getSizeX());
    Assert.assertEquals(QuadTreeConfig.QUADTREE_DEFAULT_SIZE_Y, c.getSizeY());

    Assert.assertEquals(
      QuadTreeConfig.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_X,
      c.getMinimumSize().getXI());
    Assert.assertEquals(
      QuadTreeConfig.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_Y,
      c.getMinimumSize().getYI());

    Assert.assertEquals(QuadTreeConfig.QUADTREE_DEFAULT_SIZE_X, c
      .getSize()
      .getXI());
    Assert.assertEquals(QuadTreeConfig.QUADTREE_DEFAULT_SIZE_Y, c
      .getSize()
      .getYI());

    Assert.assertEquals(QuadTreeConfig.QUADTREE_DEFAULT_POSITION.getXI(), c
      .getPosition()
      .getXI());
    Assert.assertEquals(QuadTreeConfig.QUADTREE_DEFAULT_POSITION.getYI(), c
      .getPosition()
      .getYI());
  }

  @SuppressWarnings("static-method") @Test public void testSetIdentities()
  {
    final QuadTreeConfig c = new QuadTreeConfig();

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

    Assert.assertEquals(20, c.getPositionX());
    Assert.assertEquals(21, c.getPositionY());
  }

  @SuppressWarnings("static-method") @Test public void testToString()
  {
    final QuadTreeConfig c = new QuadTreeConfig();
    final QuadTreeConfig d = new QuadTreeConfig();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.toString().equals(d.toString()));
  }
}
