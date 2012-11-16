package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.ApproximatelyEqualDouble;
import com.io7m.jtensors.VectorI2D;

public class RayI2DTest
{
  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsNotCase0()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertFalse(ray0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsNotCase1()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertFalse(ray0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsNotCase2()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(new VectorI2D(1, 2), VectorI2D.ZERO);
    Assert.assertFalse(ray0.equals(ray1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsNotCase3()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, new VectorI2D(1, 2));
    Assert.assertFalse(ray0.equals(ray1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsReflexive()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals(ray0, ray0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsSymmetric()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsTransitive()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray2 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray2);
    Assert.assertEquals(ray0, ray2);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayHashCodeEquals()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertTrue(ray0.hashCode() == ray1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayToStringEquals()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertTrue(ray0.toString().equals(ray1.toString()));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayToStringNotEquals()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, new VectorI2D(1, 2));
    Assert.assertFalse(ray0.toString().equals(ray1.toString()));
  }

  @SuppressWarnings("static-method") @Test public void testRayZero()
  {
    final RayI2D ray = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      ray.origin.x,
      ray.origin.y));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      ray.direction.x,
      ray.direction.y));
    Assert.assertTrue(ray.direction_inverse.x == Double.POSITIVE_INFINITY);
    Assert.assertTrue(ray.direction_inverse.y == Double.POSITIVE_INFINITY);
  }
}
