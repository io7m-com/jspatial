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

package com.io7m.jspatial.tests.implementation;

import com.io7m.jfunctional.Unit;
import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jspatial.implementation.QuadrantsL;
import com.io7m.jspatial.tests.api.BoundingAreaLGenerator;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2L;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public final class QuadrantsLTest
{
  @Test
  public void testExhaustive()
  {
    final BoundingAreaLGenerator generator = new BoundingAreaLGenerator();

    QuickCheck.forAllVerbose(
      generator, new AbstractCharacteristic<BoundingAreaL>()
      {
        @Override
        protected void doSpecify(final BoundingAreaL area)
          throws Throwable
        {
          final Optional<QuadrantsL> q_opt = QuadrantsL.subdivide(area);
          Assert.assertTrue(q_opt.isPresent());

          q_opt.map(quads -> {
            Assert.assertTrue(area.contains(quads.x0y0()));
            Assert.assertTrue(area.contains(quads.x1y0()));
            Assert.assertTrue(area.contains(quads.x0y1()));
            Assert.assertTrue(area.contains(quads.x1y1()));

            Assert.assertFalse(quads.x0y0().overlaps(quads.x1y0()));
            Assert.assertFalse(quads.x0y0().overlaps(quads.x0y1()));
            Assert.assertFalse(quads.x0y0().overlaps(quads.x1y1()));

            return Unit.unit();
          });
        }
      });
  }

  @Test
  public void testTooSmallHeight()
  {
    Assert.assertEquals(
      Optional.empty(),
      QuadrantsL.subdivide(
        BoundingAreaL.of(
          Vector2L.of(0L, 0L),
          Vector2L.of(2L, 1L))));
  }

  @Test
  public void testTooSmallWidth()
  {
    Assert.assertEquals(
      Optional.empty(),
      QuadrantsL.subdivide(
        BoundingAreaL.of(
          Vector2L.of(0L, 0L),
          Vector2L.of(1L, 2L))));
  }
}
