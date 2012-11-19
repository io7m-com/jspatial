package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

public class MutableAreaTest
{
  @SuppressWarnings("static-method") @Test public void testBoundingIdentity()
  {
    final MutableArea m = new MutableArea();

    m.setLowerX(23);
    m.setLowerY(46);
    m.setUpperX(69);
    m.setUpperY(92);

    Assert.assertTrue(m.boundingAreaLower().getXI() == 23);
    Assert.assertTrue(m.boundingAreaLower().getYI() == 46);
    Assert.assertTrue(m.boundingAreaUpper().getXI() == 69);
    Assert.assertTrue(m.boundingAreaUpper().getYI() == 92);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final MutableArea m = new MutableArea();
    Assert.assertFalse(m.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final MutableArea m = new MutableArea();
    Assert.assertFalse(m.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();

    m.setLowerX(1);

    Assert.assertFalse(m.equals(n));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();

    m.setUpperX(2);

    Assert.assertFalse(m.equals(n));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final MutableArea m = new MutableArea();
    Assert.assertEquals(m, m);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, m);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsTransitive()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();
    final MutableArea o = new MutableArea();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, o);
    Assert.assertEquals(o, m);
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeVariance()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();

    m.setLowerX(23);

    Assert.assertTrue(m.hashCode() != n.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testToString()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();

    Assert.assertTrue(m.toString().equals(n.toString()));
  }

  @SuppressWarnings("static-method") @Test public void testToStringVariance()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();

    m.setLowerX(23);

    Assert.assertFalse(m.toString().equals(n.toString()));
  }
}
