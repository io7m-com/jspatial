package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

public class MutableAreaDTest
{
  @SuppressWarnings("static-method") @Test public void testBoundingIdentity()
  {
    final MutableAreaD m = new MutableAreaD();

    m.setLowerXF(23);
    m.setLowerYF(46);
    m.setUpperXF(69);
    m.setUpperYF(92);

    Assert.assertTrue(m.boundingAreaLowerD().getXD() == 23);
    Assert.assertTrue(m.boundingAreaLowerD().getYD() == 46);
    Assert.assertTrue(m.boundingAreaUpperD().getXD() == 69);
    Assert.assertTrue(m.boundingAreaUpperD().getYD() == 92);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final MutableAreaD m = new MutableAreaD();
    Assert.assertFalse(m.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final MutableAreaD m = new MutableAreaD();
    Assert.assertFalse(m.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final MutableAreaD m = new MutableAreaD();
    final MutableAreaD n = new MutableAreaD();

    m.setLowerXF(1);

    Assert.assertFalse(m.equals(n));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final MutableAreaD m = new MutableAreaD();
    final MutableAreaD n = new MutableAreaD();

    m.setUpperXF(2);

    Assert.assertFalse(m.equals(n));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final MutableAreaD m = new MutableAreaD();
    Assert.assertEquals(m, m);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final MutableAreaD m = new MutableAreaD();
    final MutableAreaD n = new MutableAreaD();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, m);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsTransitive()
  {
    final MutableAreaD m = new MutableAreaD();
    final MutableAreaD n = new MutableAreaD();
    final MutableAreaD o = new MutableAreaD();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, o);
    Assert.assertEquals(o, m);
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeVariance()
  {
    final MutableAreaD m = new MutableAreaD();
    final MutableAreaD n = new MutableAreaD();

    m.setLowerXF(23);

    Assert.assertTrue(m.hashCode() != n.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testToString()
  {
    final MutableAreaD m = new MutableAreaD();
    final MutableAreaD n = new MutableAreaD();

    Assert.assertTrue(m.toString().equals(n.toString()));
  }

  @SuppressWarnings("static-method") @Test public void testToStringVariance()
  {
    final MutableAreaD m = new MutableAreaD();
    final MutableAreaD n = new MutableAreaD();

    m.setLowerXF(23);

    Assert.assertFalse(m.toString().equals(n.toString()));
  }
}
