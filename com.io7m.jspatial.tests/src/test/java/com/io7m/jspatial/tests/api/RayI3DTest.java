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
import com.io7m.jspatial.api.RayI3D;
import com.io7m.jtensors.VectorI3D;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the RayI3D type.
 */

public final class RayI3DTest
{
  @Test
  public void testRayEqualsNotCase0()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertNotEquals(ray0, null);
  }

  @Test
  public void testRayEqualsNotCase1()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertNotEquals(ray0, Integer.valueOf(23));
  }

  @Test
  public void testRayEqualsNotCase2()
  {
    final RayI3D ray0 =
      new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 =
      new RayI3D(new VectorI3D(1.0, 2.0, 3.0), VectorI3D.ZERO);
    Assert.assertNotEquals(ray0, ray1);
  }

  @Test
  public void testRayEqualsNotCase3()
  {
    final RayI3D ray0 =
      new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 =
      new RayI3D(VectorI3D.ZERO, new VectorI3D(1.0, 2.0, 3.0));
    Assert.assertNotEquals(ray0, ray1);
  }

  @Test
  public void testRayEqualsReflexive()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertEquals(ray0, ray0);
  }

  @Test
  public void testRayEqualsSymmetric()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray0);
  }

  @Test
  public void testRayEqualsTransitive()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray2 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray2);
    Assert.assertEquals(ray0, ray2);
  }

  @Test
  public void testRayHashCodeEquals()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertTrue(ray0.hashCode() == ray1.hashCode());
  }

  @Test
  public void testRayToStringEquals()
  {
    final RayI3D ray0 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    Assert.assertTrue(ray0.toString().equals(ray1.toString()));
  }

  @Test
  public void testRayToStringNotEquals()
  {
    final RayI3D ray0 =
      new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final RayI3D ray1 =
      new RayI3D(VectorI3D.ZERO, new VectorI3D(1.0, 2.0, 3.0));
    Assert.assertFalse(ray0.toString().equals(ray1.toString()));
  }

  @Test
  public void testRayZero()
  {
    final RayI3D ray = new RayI3D(VectorI3D.ZERO, VectorI3D.ZERO);
    final ContextRelative context = new ContextRelative();

    final VectorI3D ray_origin = ray.origin();
    final VectorI3D ray_direction = ray.direction();
    final VectorI3D ray_direction_inv = ray.directionInverse();

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

  @Test
  public void testRayIntersection()
  {
    final VectorI3D lower = new VectorI3D(2.0, 2.0, 2.0);
    final VectorI3D upper = new VectorI3D(4.0, 4.0, 4.0);

    {
      // Intersect -X face in +X direction
      final VectorI3D origin = new VectorI3D(1.0, 3.0, 3.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(1.0, 0.0, 0.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertTrue(i);
    }

    {
      // Intersect +X face in -X direction
      final VectorI3D origin = new VectorI3D(6.0, 3.0, 3.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(-1.0, 0.0, 0.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertTrue(i);
    }

    {
      // Intersect +Y face in -Y direction
      final VectorI3D origin = new VectorI3D(3.0, 6.0, 3.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(0.0, -1.0, 0.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertTrue(i);
    }

    {
      // Intersect -Y face in +Y direction
      final VectorI3D origin = new VectorI3D(3.0, 1.0, 3.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(0.0, 1.0, 0.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertTrue(i);
    }

    {
      // Intersect -Z face in +Z direction
      final VectorI3D origin = new VectorI3D(3.0, 3.0, 1.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(0.0, 0.0, 1.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertTrue(i);
    }

    {
      // Intersect +Z face in -Z direction
      final VectorI3D origin = new VectorI3D(3.0, 3.0, 6.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(0.0, 0.0, -1.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertTrue(i);
    }
  }

  @Test
  public void testRayNoIntersection()
  {
    final VectorI3D lower = new VectorI3D(2.0, 2.0, 2.0);
    final VectorI3D upper = new VectorI3D(4.0, 4.0, 4.0);

    {
      // Do not intersect -X face in +X direction
      final VectorI3D origin = new VectorI3D(3.0, 0.0, 0.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(1.0, 0.0, 0.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect +X face in -X direction
      final VectorI3D origin = new VectorI3D(6.0, 0.0, 0.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(-1.0, 0.0, 0.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect +Y face in -Y direction
      final VectorI3D origin = new VectorI3D(3.0, 1.0, 3.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(0.0, -1.0, 0.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect -Y face in +Y direction
      final VectorI3D origin = new VectorI3D(3.0, 6.0, 3.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(0.0, 1.0, 0.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect -Z face in +Z direction
      final VectorI3D origin = new VectorI3D(3.0, 3.0, 6.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(0.0, 0.0, 1.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect +Z face in -Z direction
      final VectorI3D origin = new VectorI3D(3.0, 3.0, 1.0);
      final VectorI3D direct =
        VectorI3D.normalize(new VectorI3D(0.0, 0.0, -1.0));
      final RayI3D ray = new RayI3D(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.getXD(),
          lower.getYD(),
          lower.getZD(),
          upper.getXD(),
          upper.getYD(),
          upper.getZD());
      Assert.assertFalse(i);
    }
  }
}
