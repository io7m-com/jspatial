/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
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

package com.io7m.jspatial.tests.bugs;

import com.io7m.jregions.core.unparameterized.areas.AreasL;
import com.io7m.jspatial.api.Ray2D;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import com.io7m.jspatial.api.quadtrees.QuadTreeLType;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultL;
import com.io7m.jspatial.implementation.QuadTreeL;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors2D;
import org.junit.Assert;
import org.junit.Test;

import java.util.TreeSet;

public final class Bug7
{
  @Test
  public void testBug7_0()
  {
    final QuadTreeConfigurationL c =
      QuadTreeConfigurationL.of(
        AreasL.create(0L, 0L, 512L, 512L),
        2L,
        2L,
        true);

    final QuadTreeLType<Integer> q = QuadTreeL.create(c);

    final boolean inserted =
      q.insert(
        Integer.valueOf(0),
        AreasL.create(32L, 32L, 64L, 64L));

    Assert.assertTrue(inserted);

    final TreeSet<QuadTreeRaycastResultL<Integer>> results = new TreeSet<>();

    q.raycast(
      Ray2D.of(
        Vector2D.of(16.0, 32.0),
        Vector2D.of(256.0, 32.0)),
      results);

    Assert.assertEquals(1L, (long) results.size());
    Assert.assertEquals(Integer.valueOf(0), results.first().item());
  }

  @Test
  public void testBug7_Ray()
  {
    final Ray2D ray =
      Ray2D.of(
        Vector2D.of(16.0, 32.0),
        Vector2D.of(1.0, 0.0));

    // This will fail due to the fast ray intersection test
    Assert.assertFalse(
      ray.intersectsArea(32.0, 32.0, 96.0, 96.0));
  }
}
