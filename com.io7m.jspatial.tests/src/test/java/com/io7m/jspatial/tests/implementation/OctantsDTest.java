/*
 * Copyright © 2017 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.jregions.core.unparameterized.volumes.VolumeD;
import com.io7m.jregions.core.unparameterized.volumes.VolumeXYZSplitD;
import com.io7m.jregions.core.unparameterized.volumes.VolumesD;
import com.io7m.jregions.generators.VolumeDGenerator;
import com.io7m.jspatial.implementation.OctantsD;
import com.io7m.jspatial.tests.TestUtilities;
import com.io7m.jspatial.tests.rules.PercentagePassRule;
import com.io7m.jspatial.tests.rules.PercentagePassing;
import net.java.quickcheck.generator.support.DoubleGenerator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public final class OctantsDTest
{
  @Rule public PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Test
  @PercentagePassing
  public void testExhaustive()
  {
    final VolumeDGenerator generator =
      new VolumeDGenerator(new DoubleGenerator());

    final VolumeD volume = generator.next();
    final VolumeXYZSplitD<VolumeD> octs = OctantsD.subdivide(volume);

    final List<VolumeD> all = new ArrayList<>(8);
    all.add(octs.x0y0z0());
    all.add(octs.x1y0z0());
    all.add(octs.x0y1z0());
    all.add(octs.x1y1z0());
    all.add(octs.x0y0z1());
    all.add(octs.x1y0z1());
    all.add(octs.x0y1z1());
    all.add(octs.x1y1z1());

    for (int i = 0; i < all.size(); ++i) {
      Assert.assertTrue(VolumesD.contains(volume, all.get(i)));
      Assert.assertTrue(VolumesD.overlaps(all.get(i), volume));

      for (int j = 0; j < all.size(); ++j) {
        if (i == j) {
          continue;
        }

        final VolumeD a = all.get(i);
        final VolumeD b = all.get(j);
        Assert.assertFalse(VolumesD.overlaps(a, b));
        Assert.assertFalse(VolumesD.overlaps(b, a));
      }
    }
  }
}
