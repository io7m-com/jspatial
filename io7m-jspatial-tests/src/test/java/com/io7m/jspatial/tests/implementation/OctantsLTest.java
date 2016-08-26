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

package com.io7m.jspatial.tests.implementation;

import com.io7m.jfunctional.Unit;
import com.io7m.jspatial.api.BoundingVolumeL;
import com.io7m.jspatial.api.BoundingVolumeL;
import com.io7m.jspatial.implementation.OctantsL;
import com.io7m.jspatial.tests.api.BoundingVolumeLGenerator;
import com.io7m.jtensors.VectorI3L;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class OctantsLTest
{
  @Test
  public void testExhaustive()
  {
    final BoundingVolumeLGenerator generator = new BoundingVolumeLGenerator();

    QuickCheck.forAllVerbose(
      generator, new AbstractCharacteristic<BoundingVolumeL>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeL volume)
          throws Throwable
        {
          final Optional<OctantsL> o_opt = OctantsL.subdivide(volume);
          Assert.assertTrue(o_opt.isPresent());

          o_opt.map(octs -> {

            final List<BoundingVolumeL> all = new ArrayList<>(8);
            all.add(octs.x0y0z0());
            all.add(octs.x1y0z0());
            all.add(octs.x0y1z0());
            all.add(octs.x1y1z0());
            all.add(octs.x0y0z1());
            all.add(octs.x1y0z1());
            all.add(octs.x0y1z1());
            all.add(octs.x1y1z1());

            for (int i = 0; i < all.size(); ++i) {
              volume.contains(all.get(i));
              all.get(i).overlaps(volume);

              for (int j = 0; j < all.size(); ++j) {
                if (i == j) {
                  continue;
                }

                final BoundingVolumeL a = all.get(i);
                final BoundingVolumeL b = all.get(j);
                Assert.assertFalse(a.overlaps(b));
                Assert.assertFalse(b.overlaps(a));
              }
            }

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
      OctantsL.subdivide(
        BoundingVolumeL.of(
          new VectorI3L(0L, 0L, 0L),
          new VectorI3L(2L, 1L, 2L))));
  }

  @Test
  public void testTooSmallWidth()
  {
    Assert.assertEquals(
      Optional.empty(),
      OctantsL.subdivide(
        BoundingVolumeL.of(
          new VectorI3L(0L, 0L, 0L),
          new VectorI3L(1L, 2L, 2L))));
  }

  @Test
  public void testTooSmallDepth()
  {
    Assert.assertEquals(
      Optional.empty(),
      OctantsL.subdivide(
        BoundingVolumeL.of(
          new VectorI3L(0L, 0L, 0L),
          new VectorI3L(2L, 2L, 1L))));
  }
}
