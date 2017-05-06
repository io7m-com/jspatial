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
import com.io7m.jspatial.api.Ray2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2I;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors2D;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the Ray2D type.
 */

public final class RayI2DTest
{
  @Test
  public void testRayEqualsNotCase0()
  {
    final Ray2D ray0 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    Assert.assertNotEquals(ray0, null);
  }

  @Test
  public void testRayEqualsNotCase1()
  {
    final Ray2D ray0 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    Assert.assertNotEquals(ray0, Integer.valueOf(23));
  }

  @Test
  public void testRayEqualsNotCase2()
  {
    final Ray2D ray0 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    final Ray2D ray1 = Ray2D.of(Vector2D.of(1.0, 2.0), Vectors2D.zero());
    Assert.assertNotEquals(ray0, ray1);
  }

  @Test
  public void testRayEqualsNotCase3()
  {
    final Ray2D ray0 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    final Ray2D ray1 = Ray2D.of(Vectors2D.zero(), Vector2D.of(1.0, 2.0));
    Assert.assertNotEquals(ray0, ray1);
  }

  @Test
  public void testRayEqualsReflexive()
  {
    final Ray2D ray0 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    Assert.assertEquals(ray0, ray0);
  }

  @Test
  public void testRayEqualsSymmetric()
  {
    final Ray2D ray0 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    final Ray2D ray1 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray0);
  }

  @Test
  public void testRayEqualsTransitive()
  {
    final Ray2D ray0 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    final Ray2D ray1 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    final Ray2D ray2 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray2);
    Assert.assertEquals(ray0, ray2);
  }

  @Test
  public void testRayHashCodeEquals()
  {
    final Ray2D ray0 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    final Ray2D ray1 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    Assert.assertEquals((long) ray0.hashCode(), (long) ray1.hashCode());
  }

  @Test
  public void testRayToStringEquals()
  {
    final Ray2D ray0 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    final Ray2D ray1 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    Assert.assertEquals(ray0.toString(), ray1.toString());
  }

  @Test
  public void testRayToStringNotEquals()
  {
    final Ray2D ray0 = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    final Ray2D ray1 = Ray2D.of(Vectors2D.zero(), Vector2D.of(1.0, 2.0));
    Assert.assertNotEquals(ray0.toString(), ray1.toString());
  }

  @Test
  public void testRayZero()
  {
    final Ray2D ray = Ray2D.of(Vectors2D.zero(), Vectors2D.zero());
    final ContextRelative context = new ContextRelative();

    final Vector2D ray_origin = ray.origin();
    final Vector2D ray_direction = ray.direction();
    final Vector2D ray_direction_inv = ray.directionInverse();

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_origin.x(),
      ray_origin.y()));

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_direction.x(),
      ray_direction.y()));

    Assert.assertTrue(ray_direction_inv.x() == Double.POSITIVE_INFINITY);
    Assert.assertTrue(ray_direction_inv.y() == Double.POSITIVE_INFINITY);
  }

  @Test
  public void testRayIntersection()
  {
    final Vector2I lower = Vector2I.of(2, 2);
    final Vector2I upper = Vector2I.of(4, 4);

    {
      // Intersect -X edge in +X direction
      final Vector2D origin = Vector2D.of(1.0, 3.0);
      final Vector2D direct = Vectors2D.normalize(Vector2D.of(1.0, 0.0));
      final Ray2D ray = Ray2D.of(origin, direct);

      final boolean i =
        ray.intersectsArea(
          (double) lower.x(),
          (double) lower.y(),
          (double) upper.x(),
          (double) upper.y());
      Assert.assertTrue(i);
    }

    {
      // Intersect +X edge in -X direction
      final Vector2D origin = Vector2D.of(6.0, 3.0);
      final Vector2D direct = Vectors2D.normalize(Vector2D.of(-1.0, 0.0));
      final Ray2D ray = Ray2D.of(origin, direct);

      final boolean i =
        ray.intersectsArea(
          (double) lower.x(),
          (double) lower.y(),
          (double) upper.x(),
          (double) upper.y());
      Assert.assertTrue(i);
    }

    {
      // Intersect +Y edge in -Y direction
      final Vector2D origin = Vector2D.of(3.0, 6.0);
      final Vector2D direct = Vectors2D.normalize(Vector2D.of(0.0, -1.0));
      final Ray2D ray = Ray2D.of(origin, direct);

      final boolean i =
        ray.intersectsArea(
          (double) lower.x(),
          (double) lower.y(),
          (double) upper.x(),
          (double) upper.y());
      Assert.assertTrue(i);
    }

    {
      // Intersect -Y edge in +Y direction
      final Vector2D origin = Vector2D.of(3.0, 1.0);
      final Vector2D direct = Vectors2D.normalize(Vector2D.of(0.0, 1.0));
      final Ray2D ray = Ray2D.of(origin, direct);

      final boolean i =
        ray.intersectsArea(
          (double) lower.x(),
          (double) lower.y(),
          (double) upper.x(),
          (double) upper.y());
      Assert.assertTrue(i);
    }
  }

  @Test
  public void testRayNoIntersection()
  {
    final Vector2I lower = Vector2I.of(2, 2);
    final Vector2I upper = Vector2I.of(4, 4);

    {
      // Do not intersect -X edge in +X direction
      final Vector2D origin = Vector2D.of(1.0, 0.0);
      final Vector2D direct = Vectors2D.normalize(Vector2D.of(1.0, 0.0));
      final Ray2D ray = Ray2D.of(origin, direct);

      final boolean i =
        ray.intersectsArea(
          (double) lower.x(),
          (double) lower.y(),
          (double) upper.x(),
          (double) upper.y());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect +X edge in -X direction
      final Vector2D origin = Vector2D.of(6.0, 0.0);
      final Vector2D direct = Vectors2D.normalize(Vector2D.of(-1.0, 0.0));
      final Ray2D ray = Ray2D.of(origin, direct);

      final boolean i =
        ray.intersectsArea(
          (double) lower.x(),
          (double) lower.y(),
          (double) upper.x(),
          (double) upper.y());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect +Y edge in -Y direction
      final Vector2D origin = Vector2D.of(0.0, 6.0);
      final Vector2D direct = Vectors2D.normalize(Vector2D.of(0.0, -1.0));
      final Ray2D ray = Ray2D.of(origin, direct);

      final boolean i =
        ray.intersectsArea(
          (double) lower.x(),
          (double) lower.y(),
          (double) upper.x(),
          (double) upper.y());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect -Y edge in +Y direction
      final Vector2D origin = Vector2D.of(0.0, 1.0);
      final Vector2D direct = Vectors2D.normalize(Vector2D.of(0.0, 1.0));
      final Ray2D ray = Ray2D.of(origin, direct);

      final boolean i =
        ray.intersectsArea(
          (double) lower.x(),
          (double) lower.y(),
          (double) upper.x(),
          (double) upper.y());
      Assert.assertFalse(i);
    }
  }
}
