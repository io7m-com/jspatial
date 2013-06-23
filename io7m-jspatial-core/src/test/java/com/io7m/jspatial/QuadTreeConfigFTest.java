package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.AlmostEqualFloat.ContextRelative;

public class QuadTreeConfigFTest
{
  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();
    Assert.assertFalse(c.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();
    Assert.assertFalse(c.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();
    final QuadTreeConfigF d = new QuadTreeConfigF();

    c.setSizeX(23);
    Assert.assertFalse(c.equals(d));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();
    final QuadTreeConfigF d = new QuadTreeConfigF();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.equals(d));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();
    Assert.assertEquals(c, c);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();
    final QuadTreeConfigF d = new QuadTreeConfigF();
    Assert.assertEquals(c, d);
    Assert.assertEquals(d, c);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsTransitive()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();
    final QuadTreeConfigF d = new QuadTreeConfigF();
    final QuadTreeConfigF e = new QuadTreeConfigF();
    Assert.assertEquals(c, d);
    Assert.assertEquals(d, e);
    Assert.assertEquals(c, e);
  }

  @SuppressWarnings("static-method") @Test public void testHashCode()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();
    final QuadTreeConfigF d = new QuadTreeConfigF();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.hashCode() == d.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testIdentities()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();

    Assert.assertEquals(
      QuadTreeConfigF.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_X,
      c.getMinimumSizeX());
    Assert.assertEquals(
      QuadTreeConfigF.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_Y,
      c.getMinimumSizeY());

    Assert
      .assertEquals(QuadTreeConfigF.QUADTREE_DEFAULT_SIZE_X, c.getSizeX());
    Assert
      .assertEquals(QuadTreeConfigF.QUADTREE_DEFAULT_SIZE_Y, c.getSizeY());

    Assert.assertEquals(
      QuadTreeConfigF.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_X,
      c.getMinimumSize().getXI());
    Assert.assertEquals(
      QuadTreeConfigF.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_Y,
      c.getMinimumSize().getYI());

    Assert.assertEquals(QuadTreeConfigF.QUADTREE_DEFAULT_SIZE_X, c
      .getSize()
      .getXI());
    Assert.assertEquals(QuadTreeConfigF.QUADTREE_DEFAULT_SIZE_Y, c
      .getSize()
      .getYI());

    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      QuadTreeConfigF.QUADTREE_DEFAULT_POSITION.getXF(),
      c.getPosition().getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      QuadTreeConfigF.QUADTREE_DEFAULT_POSITION.getYF(),
      c.getPosition().getYF()));
  }

  @SuppressWarnings("static-method") @Test public void testSetIdentities()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();

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
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      20,
      c.getPositionX()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      21,
      c.getPositionY()));
  }

  @SuppressWarnings("static-method") @Test public void testToString()
  {
    final QuadTreeConfigF c = new QuadTreeConfigF();
    final QuadTreeConfigF d = new QuadTreeConfigF();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.toString().equals(d.toString()));
  }
}
