package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

public class MutableVolumeTest
{
  @SuppressWarnings("static-method") @Test public void testBoundingIdentity()
  {
    final MutableVolume m = new MutableVolume();

    m.setLowerX(23);
    m.setLowerY(46);
    m.setLowerZ(115);
    m.setUpperX(69);
    m.setUpperY(92);
    m.setUpperZ(138);

    Assert.assertTrue(m.boundingVolumeLower().getXI() == 23);
    Assert.assertTrue(m.boundingVolumeLower().getYI() == 46);
    Assert.assertTrue(m.boundingVolumeLower().getZI() == 115);
    Assert.assertTrue(m.boundingVolumeUpper().getXI() == 69);
    Assert.assertTrue(m.boundingVolumeUpper().getYI() == 92);
    Assert.assertTrue(m.boundingVolumeUpper().getZI() == 138);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase0()
  {
    final MutableVolume m = new MutableVolume();
    Assert.assertFalse(m.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase1()
  {
    final MutableVolume m = new MutableVolume();
    Assert.assertFalse(m.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase2()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();

    m.setLowerX(1);

    Assert.assertFalse(m.equals(n));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsNotCase3()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();

    m.setUpperX(2);

    Assert.assertFalse(m.equals(n));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsReflexive()
  {
    final MutableVolume m = new MutableVolume();
    Assert.assertEquals(m, m);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsSymmetric()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, m);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsTransitive()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();
    final MutableVolume o = new MutableVolume();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, o);
    Assert.assertEquals(o, m);
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeVariance()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();

    m.setLowerX(23);

    Assert.assertTrue(m.hashCode() != n.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testToString()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();

    Assert.assertTrue(m.toString().equals(n.toString()));
  }

  @SuppressWarnings("static-method") @Test public void testToStringVariance()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();

    m.setLowerX(23);

    Assert.assertFalse(m.toString().equals(n.toString()));
  }
}
