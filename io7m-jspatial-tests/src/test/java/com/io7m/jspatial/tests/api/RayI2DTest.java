/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jspatial.tests.api;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jspatial.api.RayI2D;
import com.io7m.jtensors.VectorI2D;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the RayI2D type.
 */

public final class RayI2DTest
{
  @Test
  public void testRayEqualsNotCase0()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertNotEquals(ray0, null);
  }

  @Test
  public void testRayEqualsNotCase1()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertNotEquals(ray0, Integer.valueOf(23));
  }

  @Test
  public void testRayEqualsNotCase2()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(new VectorI2D(1.0, 2.0), VectorI2D.ZERO);
    Assert.assertNotEquals(ray0, ray1);
  }

  @Test
  public void testRayEqualsNotCase3()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, new VectorI2D(1.0, 2.0));
    Assert.assertNotEquals(ray0, ray1);
  }

  @Test
  public void testRayEqualsReflexive()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals(ray0, ray0);
  }

  @Test
  public void testRayEqualsSymmetric()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray0);
  }

  @Test
  public void testRayEqualsTransitive()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray2 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray2);
    Assert.assertEquals(ray0, ray2);
  }

  @Test
  public void testRayHashCodeEquals()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals((long) ray0.hashCode(), (long) ray1.hashCode());
  }

  @Test
  public void testRayToStringEquals()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    Assert.assertEquals(ray0.toString(), ray1.toString());
  }

  @Test
  public void testRayToStringNotEquals()
  {
    final RayI2D ray0 = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final RayI2D ray1 = new RayI2D(VectorI2D.ZERO, new VectorI2D(1.0, 2.0));
    Assert.assertNotEquals(ray0.toString(), ray1.toString());
  }

  @Test
  public void testRayZero()
  {
    final RayI2D ray = new RayI2D(VectorI2D.ZERO, VectorI2D.ZERO);
    final ContextRelative context = new ContextRelative();

    final VectorI2D ray_origin = ray.origin();
    final VectorI2D ray_direction = ray.direction();
    final VectorI2D ray_direction_inv = ray.directionInverse();

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
