package com.io7m.jspatial.tests;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.MutableVolume;

@SuppressWarnings("static-method") public class MutableVolumeTest
{
  @Test public void testBoundingIdentity()
  {
    final MutableVolume m = new MutableVolume();

    m.setLower3i(23, 46, 115);
    m.setUpper3i(69, 92, 138);

    Assert.assertTrue(m.boundingVolumeLower().getXI() == 23);
    Assert.assertTrue(m.boundingVolumeLower().getYI() == 46);
    Assert.assertTrue(m.boundingVolumeLower().getZI() == 115);
    Assert.assertTrue(m.boundingVolumeUpper().getXI() == 69);
    Assert.assertTrue(m.boundingVolumeUpper().getYI() == 92);
    Assert.assertTrue(m.boundingVolumeUpper().getZI() == 138);
  }

  @Test public void testEqualsNotCase0()
  {
    final MutableVolume m = new MutableVolume();
    Assert.assertFalse(m.equals(null));
  }

  @Test public void testEqualsNotCase1()
  {
    final MutableVolume m = new MutableVolume();
    Assert.assertFalse(m.equals(Integer.valueOf(23)));
  }

  @Test public void testEqualsNotCase2()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();

    m.setLower3i(1, 0, 0);

    Assert.assertFalse(m.equals(n));
  }

  @Test public void testEqualsNotCase3()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();

    m.setUpper3i(2, 0, 0);

    Assert.assertFalse(m.equals(n));
  }

  @Test public void testEqualsReflexive()
  {
    final MutableVolume m = new MutableVolume();
    Assert.assertEquals(m, m);
  }

  @Test public void testEqualsSymmetric()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, m);
  }

  @Test public void testEqualsTransitive()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();
    final MutableVolume o = new MutableVolume();
    Assert.assertEquals(m, n);
    Assert.assertEquals(n, o);
    Assert.assertEquals(o, m);
  }

  @Test public void testHashCodeVariance()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();

    m.setLower3i(23, 0, 0);

    Assert.assertTrue(m.hashCode() != n.hashCode());
  }

  @Test public void testToString()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();

    Assert.assertTrue(m.toString().equals(n.toString()));
  }

  @Test public void testToStringVariance()
  {
    final MutableVolume m = new MutableVolume();
    final MutableVolume n = new MutableVolume();

    m.setLower3i(23, 0, 0);

    Assert.assertFalse(m.toString().equals(n.toString()));
  }
}
