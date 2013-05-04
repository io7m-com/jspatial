package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jtensors.VectorI3D;

public class RayI3DTest
{
  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsNotCase0()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertFalse(ray0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsNotCase1()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertFalse(ray0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsNotCase2()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(new VectorI3D(1, 2, 3), VectorI3D.ZERO);
    Assert.assertFalse(ray0.equals(ray1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsNotCase3()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, new VectorI3D(1, 2, 3));
    Assert.assertFalse(ray0.equals(ray1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsReflexive()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertEquals(ray0, ray0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsSymmetric()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayEqualsTransitive()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray2 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray2);
    Assert.assertEquals(ray0, ray2);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayHashCodeEquals()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertTrue(ray0.hashCode() == ray1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayToStringEquals()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertTrue(ray0.toString().equals(ray1.toString()));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRayToStringNotEquals()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, new VectorI3D(1, 2, 3));
    Assert.assertFalse(ray0.toString().equals(ray1.toString()));
  }

  @SuppressWarnings("static-method") @Test public void testRayZero()
  {
    final RayI3D ray = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final ContextRelative context = new AlmostEqualDouble.ContextRelative();

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray.origin.x,
      ray.origin.y));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray.direction.x,
      ray.direction.y));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray.origin.x,
      ray.origin.z));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray.direction.x,
      ray.direction.z));
    Assert.assertTrue(ray.direction_inverse.x == Double.POSITIVE_INFINITY);
    Assert.assertTrue(ray.direction_inverse.y == Double.POSITIVE_INFINITY);
    Assert.assertTrue(ray.direction_inverse.z == Double.POSITIVE_INFINITY);
  }
}
