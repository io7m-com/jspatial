package com.io7m.jspatial.tests;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.MutableArea;

@SuppressWarnings("static-method") public class MutableAreaTest
{
  @Test public void testBoundingIdentity()
  {
    final MutableArea m = new MutableArea();

    m.setLower2i(23, 46);
    m.setUpper2i(69, 92);

    Assert.assertTrue(m.boundingAreaLower().getXI() == 23);
    Assert.assertTrue(m.boundingAreaLower().getYI() == 46);
    Assert.assertTrue(m.boundingAreaUpper().getXI() == 69);
    Assert.assertTrue(m.boundingAreaUpper().getYI() == 92);
  }

  @Test public void testEqualsNotCase0()
  {
    final MutableArea m = new MutableArea();
    Assert.assertFalse(m.equals(null));
  }

  @Test public void testEqualsNotCase1()
  {
    final MutableArea m = new MutableArea();
    Assert.assertFalse(m.equals(Integer.valueOf(23)));
  }

  @Test public void testEqualsNotCase2()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();

    m.setLower2i(1, 0);

    Assert.assertFalse(m.equals(n));
  }

  @Test public void testEqualsNotCase3()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();

    m.setUpper2i(2, 0);

    Assert.assertFalse(m.equals(n));
  }

  @Test public void testEqualsReflexive()
  {
    final MutableArea m = new MutableArea();
    Assert.assertEquals(m, m);
  }

  @Test public void testEqualsSymmetric()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, m);
  }

  @Test public void testEqualsTransitive()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();
    final MutableArea o = new MutableArea();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, o);
    Assert.assertEquals(o, m);
  }

  @Test public void testHashCodeVariance()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();

    m.setLower2i(23, 0);

    Assert.assertTrue(m.hashCode() != n.hashCode());
  }

  @Test public void testToString()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();

    Assert.assertTrue(m.toString().equals(n.toString()));
  }

  @Test public void testToStringVariance()
  {
    final MutableArea m = new MutableArea();
    final MutableArea n = new MutableArea();

    m.setLower2i(23, 0);

    Assert.assertFalse(m.toString().equals(n.toString()));
  }
}
