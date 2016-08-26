package com.io7m.jspatial.tests;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jspatial.RayI3D;
import com.io7m.jtensors.VectorI3D;

import java.nio.file.FileVisitResult;

@SuppressWarnings("static-method") public class RayI3DTest
{
  FileVisitResult x;

  @Test public void testRayEqualsNotCase0()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertFalse(ray0.equals(null));
  }

  @Test public void testRayEqualsNotCase1()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertFalse(ray0.equals(Integer.valueOf(23)));
  }

  @Test public void testRayEqualsNotCase2()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(new VectorI3D(1, 2, 3), VectorI3D.ZERO);
    Assert.assertFalse(ray0.equals(ray1));
  }

  @Test public void testRayEqualsNotCase3()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, new VectorI3D(1, 2, 3));
    Assert.assertFalse(ray0.equals(ray1));
  }

  @Test public void testRayEqualsReflexive()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertEquals(ray0, ray0);
  }

  @Test public void testRayEqualsSymmetric()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray0);
  }

  @Test public void testRayEqualsTransitive()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray2 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray2);
    Assert.assertEquals(ray0, ray2);
  }

  @Test public void testRayHashCodeEquals()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertTrue(ray0.hashCode() == ray1.hashCode());
  }

  @Test public void testRayToStringEquals()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertTrue(ray0.toString().equals(ray1.toString()));
  }

  @Test public void testRayToStringNotEquals()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, new VectorI3D(1, 2, 3));
    Assert.assertFalse(ray0.toString().equals(ray1.toString()));
  }

  @Test public void testRayZero()
  {
    final RayI3D ray = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final ContextRelative context = new AlmostEqualDouble.ContextRelative();

    final VectorI3D ray_origin = ray.getOrigin();
    final VectorI3D ray_direction = ray.getDirection();
    final VectorI3D ray_direction_inv = ray.getDirectionInverse();

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_origin.getXD(),
      ray_origin.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_direction.getXD(),
      ray_direction.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_origin.getXD(),
      ray_origin.getZD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_direction.getXD(),
      ray_direction.getZD()));
    Assert.assertTrue(ray_direction_inv.getXD() == Double.POSITIVE_INFINITY);
    Assert.assertTrue(ray_direction_inv.getYD() == Double.POSITIVE_INFINITY);
    Assert.assertTrue(ray_direction_inv.getZD() == Double.POSITIVE_INFINITY);
  }
}
