package com.io7m.jspatial.tests;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jspatial.RayI2D;
import com.io7m.jtensors.VectorI2D;

@SuppressWarnings("static-method") public class RayI2DTest
{
  @Test public void testRayEqualsNotCase0()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertFalse(ray0.equals(null));
  }

  @Test public void testRayEqualsNotCase1()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertFalse(ray0.equals(Integer.valueOf(23)));
  }

  @Test public void testRayEqualsNotCase2()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(new VectorI2D(1, 2), VectorI2D.ZERO);
    Assert.assertFalse(ray0.equals(ray1));
  }

  @Test public void testRayEqualsNotCase3()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, new VectorI2D(1, 2));
    Assert.assertFalse(ray0.equals(ray1));
  }

  @Test public void testRayEqualsReflexive()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals(ray0, ray0);
  }

  @Test public void testRayEqualsSymmetric()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray0);
  }

  @Test public void testRayEqualsTransitive()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray2 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray2);
    Assert.assertEquals(ray0, ray2);
  }

  @Test public void testRayHashCodeEquals()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertTrue(ray0.hashCode() == ray1.hashCode());
  }

  @Test public void testRayToStringEquals()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertTrue(ray0.toString().equals(ray1.toString()));
  }

  @Test public void testRayToStringNotEquals()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, new VectorI2D(1, 2));
    Assert.assertFalse(ray0.toString().equals(ray1.toString()));
  }

  @Test public void testRayZero()
  {
    final RayI2D ray = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final ContextRelative context = new AlmostEqualDouble.ContextRelative();

    final VectorI2D ray_origin = ray.getOrigin();
    final VectorI2D ray_direction = ray.getDirection();
    final VectorI2D ray_direction_inv = ray.getDirectionInverse();

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_origin.getXD(),
      ray_origin.getYD()));

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_direction.getXD(),
      ray_direction.getYD()));

    Assert.assertTrue(ray_direction_inv.getXD() == Double.POSITIVE_INFINITY);
    Assert.assertTrue(ray_direction_inv.getYD() == Double.POSITIVE_INFINITY);
  }
}
