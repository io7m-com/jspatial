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
import com.io7m.jspatial.api.Ray3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3D;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the Ray3D type.
 */

public final class RayI3DTest
{
  @Test
  public void testRayEqualsNotCase0()
  {
    final Ray3D ray0 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    Assert.assertNotEquals(ray0, null);
  }

  @Test
  public void testRayEqualsNotCase1()
  {
    final Ray3D ray0 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    Assert.assertNotEquals(ray0, Integer.valueOf(23));
  }

  @Test
  public void testRayEqualsNotCase2()
  {
    final Ray3D ray0 =
      Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    final Ray3D ray1 =
      Ray3D.of(Vector3D.of(1.0, 2.0, 3.0), Vectors3D.zero());
    Assert.assertNotEquals(ray0, ray1);
  }

  @Test
  public void testRayEqualsNotCase3()
  {
    final Ray3D ray0 =
      Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    final Ray3D ray1 =
      Ray3D.of(Vectors3D.zero(), Vector3D.of(1.0, 2.0, 3.0));
    Assert.assertNotEquals(ray0, ray1);
  }

  @Test
  public void testRayEqualsReflexive()
  {
    final Ray3D ray0 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    Assert.assertEquals(ray0, ray0);
  }

  @Test
  public void testRayEqualsSymmetric()
  {
    final Ray3D ray0 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    final Ray3D ray1 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray0);
  }

  @Test
  public void testRayEqualsTransitive()
  {
    final Ray3D ray0 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    final Ray3D ray1 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    final Ray3D ray2 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    Assert.assertEquals(ray0, ray1);
    Assert.assertEquals(ray1, ray2);
    Assert.assertEquals(ray0, ray2);
  }

  @Test
  public void testRayHashCodeEquals()
  {
    final Ray3D ray0 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    final Ray3D ray1 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    Assert.assertTrue(ray0.hashCode() == ray1.hashCode());
  }

  @Test
  public void testRayToStringEquals()
  {
    final Ray3D ray0 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    final Ray3D ray1 = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    Assert.assertTrue(ray0.toString().equals(ray1.toString()));
  }

  @Test
  public void testRayToStringNotEquals()
  {
    final Ray3D ray0 =
      Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    final Ray3D ray1 =
      Ray3D.of(Vectors3D.zero(), Vector3D.of(1.0, 2.0, 3.0));
    Assert.assertFalse(ray0.toString().equals(ray1.toString()));
  }

  @Test
  public void testRayZero()
  {
    final Ray3D ray = Ray3D.of(Vectors3D.zero(), Vectors3D.zero());
    final ContextRelative context = new ContextRelative();

    final Vector3D ray_origin = ray.origin();
    final Vector3D ray_direction = ray.direction();
    final Vector3D ray_direction_inv = ray.directionInverse();

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_origin.x(),
      ray_origin.y()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_direction.x(),
      ray_direction.y()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_origin.x(),
      ray_origin.z()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      ray_direction.x(),
      ray_direction.z()));
    Assert.assertTrue(ray_direction_inv.x() == Double.POSITIVE_INFINITY);
    Assert.assertTrue(ray_direction_inv.y() == Double.POSITIVE_INFINITY);
    Assert.assertTrue(ray_direction_inv.z() == Double.POSITIVE_INFINITY);
  }

  @Test
  public void testRayIntersection()
  {
    final Vector3D lower = Vector3D.of(2.0, 2.0, 2.0);
    final Vector3D upper = Vector3D.of(4.0, 4.0, 4.0);

    {
      // Intersect -X face in +X direction
      final Vector3D origin = Vector3D.of(1.0, 3.0, 3.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(1.0, 0.0, 0.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertTrue(i);
    }

    {
      // Intersect +X face in -X direction
      final Vector3D origin = Vector3D.of(6.0, 3.0, 3.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(-1.0, 0.0, 0.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertTrue(i);
    }

    {
      // Intersect +Y face in -Y direction
      final Vector3D origin = Vector3D.of(3.0, 6.0, 3.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(0.0, -1.0, 0.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertTrue(i);
    }

    {
      // Intersect -Y face in +Y direction
      final Vector3D origin = Vector3D.of(3.0, 1.0, 3.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(0.0, 1.0, 0.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertTrue(i);
    }

    {
      // Intersect -Z face in +Z direction
      final Vector3D origin = Vector3D.of(3.0, 3.0, 1.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(0.0, 0.0, 1.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertTrue(i);
    }

    {
      // Intersect +Z face in -Z direction
      final Vector3D origin = Vector3D.of(3.0, 3.0, 6.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(0.0, 0.0, -1.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertTrue(i);
    }
  }

  @Test
  public void testRayNoIntersection()
  {
    final Vector3D lower = Vector3D.of(2.0, 2.0, 2.0);
    final Vector3D upper = Vector3D.of(4.0, 4.0, 4.0);

    {
      // Do not intersect -X face in +X direction
      final Vector3D origin = Vector3D.of(3.0, 0.0, 0.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(1.0, 0.0, 0.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect +X face in -X direction
      final Vector3D origin = Vector3D.of(6.0, 0.0, 0.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(-1.0, 0.0, 0.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect +Y face in -Y direction
      final Vector3D origin = Vector3D.of(3.0, 1.0, 3.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(0.0, -1.0, 0.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect -Y face in +Y direction
      final Vector3D origin = Vector3D.of(3.0, 6.0, 3.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(0.0, 1.0, 0.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect -Z face in +Z direction
      final Vector3D origin = Vector3D.of(3.0, 3.0, 6.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(0.0, 0.0, 1.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertFalse(i);
    }

    {
      // Do not intersect +Z face in -Z direction
      final Vector3D origin = Vector3D.of(3.0, 3.0, 1.0);
      final Vector3D direct =
        Vectors3D.normalize(Vector3D.of(0.0, 0.0, -1.0));
      final Ray3D ray = Ray3D.of(origin, direct);

      final boolean i =
        ray.intersectsVolume(
          lower.x(),
          lower.y(),
          lower.z(),
          upper.x(),
          upper.y(),
          upper.z());
      Assert.assertFalse(i);
    }
  }
}
