package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

public class MutableAreaFTest
{
  @SuppressWarnings("static-method") @Test public void testBoundingIdentity()
  {
    final MutableAreaF m = new MutableAreaF();

    m.setLowerXF(23);
    m.setLowerYF(46);
    m.setUpperXF(69);
    m.setUpperYF(92);

    Assert.assertTrue(m.boundingAreaLowerF().getXF() == 23);
    Assert.assertTrue(m.boundingAreaLowerF().getYF() == 46);
    Assert.assertTrue(m.boundingAreaUpperF().getXF() == 69);
    Assert.assertTrue(m.boundingAreaUpperF().getYF() == 92);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final MutableAreaF m = new MutableAreaF();
    Assert.assertFalse(m.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final MutableAreaF m = new MutableAreaF();
    Assert.assertFalse(m.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final MutableAreaF m = new MutableAreaF();
    final MutableAreaF n = new MutableAreaF();

    m.setLowerXF(1);

    Assert.assertFalse(m.equals(n));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final MutableAreaF m = new MutableAreaF();
    final MutableAreaF n = new MutableAreaF();

    m.setUpperXF(2);

    Assert.assertFalse(m.equals(n));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final MutableAreaF m = new MutableAreaF();
    Assert.assertEquals(m, m);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final MutableAreaF m = new MutableAreaF();
    final MutableAreaF n = new MutableAreaF();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, m);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsTransitive()
  {
    final MutableAreaF m = new MutableAreaF();
    final MutableAreaF n = new MutableAreaF();
    final MutableAreaF o = new MutableAreaF();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, o);
    Assert.assertEquals(o, m);
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeVariance()
  {
    final MutableAreaF m = new MutableAreaF();
    final MutableAreaF n = new MutableAreaF();

    m.setLowerXF(23);

    Assert.assertTrue(m.hashCode() != n.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testToString()
  {
    final MutableAreaF m = new MutableAreaF();
    final MutableAreaF n = new MutableAreaF();

    Assert.assertTrue(m.toString().equals(n.toString()));
  }

  @SuppressWarnings("static-method") @Test public void testToStringVariance()
  {
    final MutableAreaF m = new MutableAreaF();
    final MutableAreaF n = new MutableAreaF();

    m.setLowerXF(23);

    Assert.assertFalse(m.toString().equals(n.toString()));
  }
}
