package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

public class OctTreeConfigTest
{
  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final OctTreeConfig c = new OctTreeConfig();
    Assert.assertFalse(c.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final OctTreeConfig c = new OctTreeConfig();
    Assert.assertFalse(c.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final OctTreeConfig c = new OctTreeConfig();
    final OctTreeConfig d = new OctTreeConfig();

    c.setSizeX(23);
    Assert.assertFalse(c.equals(d));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final OctTreeConfig c = new OctTreeConfig();
    final OctTreeConfig d = new OctTreeConfig();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.equals(d));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final OctTreeConfig c = new OctTreeConfig();
    Assert.assertEquals(c, c);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final OctTreeConfig c = new OctTreeConfig();
    final OctTreeConfig d = new OctTreeConfig();
    Assert.assertEquals(c, d);
    Assert.assertEquals(d, c);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsTransitive()
  {
    final OctTreeConfig c = new OctTreeConfig();
    final OctTreeConfig d = new OctTreeConfig();
    final OctTreeConfig e = new OctTreeConfig();
    Assert.assertEquals(c, d);
    Assert.assertEquals(d, e);
    Assert.assertEquals(c, e);
  }

  @SuppressWarnings("static-method") @Test public void testHashCode()
  {
    final OctTreeConfig c = new OctTreeConfig();
    final OctTreeConfig d = new OctTreeConfig();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.hashCode() == d.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testIdentities()
  {
    final OctTreeConfig c = new OctTreeConfig();

    Assert.assertEquals(
      OctTreeConfig.OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_X,
      c.getMinimumSizeX());
    Assert.assertEquals(
      OctTreeConfig.OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Y,
      c.getMinimumSizeY());
    Assert.assertEquals(
      OctTreeConfig.OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Z,
      c.getMinimumSizeZ());

    Assert.assertEquals(OctTreeConfig.OCTTREE_DEFAULT_SIZE_X, c.getSizeX());
    Assert.assertEquals(OctTreeConfig.OCTTREE_DEFAULT_SIZE_Y, c.getSizeY());
    Assert.assertEquals(OctTreeConfig.OCTTREE_DEFAULT_SIZE_Z, c.getSizeZ());

    Assert.assertEquals(
      OctTreeConfig.OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_X,
      c.getMinimumSize().getXI());
    Assert.assertEquals(
      OctTreeConfig.OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Y,
      c.getMinimumSize().getYI());
    Assert.assertEquals(
      OctTreeConfig.OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Z,
      c.getMinimumSize().getZI());

    Assert.assertEquals(OctTreeConfig.OCTTREE_DEFAULT_SIZE_X, c
      .getSize()
      .getXI());
    Assert.assertEquals(OctTreeConfig.OCTTREE_DEFAULT_SIZE_Y, c
      .getSize()
      .getYI());
    Assert.assertEquals(OctTreeConfig.OCTTREE_DEFAULT_SIZE_Z, c
      .getSize()
      .getZI());
  }

  @SuppressWarnings("static-method") @Test public void testSetIdentities()
  {
    final OctTreeConfig c = new OctTreeConfig();

    c.setSizeX(2);
    c.setSizeY(4);
    c.setSizeZ(6);

    c.setMinimumSizeX(5);
    c.setMinimumSizeY(7);
    c.setMinimumSizeZ(11);

    Assert.assertEquals(5, c.getMinimumSizeX());
    Assert.assertEquals(7, c.getMinimumSizeY());
    Assert.assertEquals(11, c.getMinimumSizeZ());

    Assert.assertEquals(2, c.getSizeX());
    Assert.assertEquals(4, c.getSizeY());
    Assert.assertEquals(6, c.getSizeZ());

    Assert.assertEquals(5, c.getMinimumSize().getXI());
    Assert.assertEquals(7, c.getMinimumSize().getYI());
    Assert.assertEquals(11, c.getMinimumSize().getZI());

    Assert.assertEquals(2, c.getSize().getXI());
    Assert.assertEquals(4, c.getSize().getYI());
    Assert.assertEquals(6, c.getSize().getZI());
  }

  @SuppressWarnings("static-method") @Test public void testToString()
  {
    final OctTreeConfig c = new OctTreeConfig();
    final OctTreeConfig d = new OctTreeConfig();

    c.setMinimumSizeX(23);
    Assert.assertFalse(c.toString().equals(d.toString()));
  }
}
