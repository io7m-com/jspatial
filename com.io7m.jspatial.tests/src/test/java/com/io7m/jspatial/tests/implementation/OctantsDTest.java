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

import com.io7m.jspatial.api.BoundingVolumeD;
import com.io7m.jspatial.implementation.OctantsD;
import com.io7m.jspatial.tests.api.BoundingVolumeDGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import net.java.quickcheck.generator.support.DoubleGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public final class OctantsDTest
{
  @Test
  public void testExhaustive()
  {
    final BoundingVolumeDGenerator generator =
      new BoundingVolumeDGenerator(new DoubleGenerator());

    QuickCheck.forAllVerbose(
      generator, new AbstractCharacteristic<BoundingVolumeD>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeD volume)
          throws Throwable
        {
          final OctantsD octs = OctantsD.subdivide(volume);

          final List<BoundingVolumeD> all = new ArrayList<>(8);
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

              final BoundingVolumeD a = all.get(i);
              final BoundingVolumeD b = all.get(j);
              Assert.assertFalse(a.overlaps(b));
              Assert.assertFalse(b.overlaps(a));
            }
          }
        }
      });
  }
}
