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

import com.io7m.jregions.core.unparameterized.volumes.VolumeL;
import com.io7m.jregions.core.unparameterized.volumes.VolumeXYZSplitL;
import com.io7m.jregions.core.unparameterized.volumes.VolumesL;
import com.io7m.jregions.generators.VolumeLGenerator;
import com.io7m.jspatial.implementation.OctantsL;
import com.io7m.jspatial.tests.TestUtilities;
import com.io7m.jspatial.tests.rules.PercentagePassRule;
import com.io7m.jspatial.tests.rules.PercentagePassing;
import net.java.quickcheck.generator.support.LongGenerator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class OctantsLTest
{
  @Rule public PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Test
  @PercentagePassing
  public void testExhaustive()
  {
    final VolumeLGenerator generator =
      new VolumeLGenerator(new LongGenerator(-10000L, 10000L));

    final VolumeL volume = generator.next();
    final Optional<VolumeXYZSplitL<VolumeL>> o_opt =
      OctantsL.subdivide(volume);
    Assert.assertTrue(o_opt.isPresent());

    o_opt.map(octs -> {
      final List<VolumeL> all = new ArrayList<>(8);
      all.add(octs.x0y0z0());
      all.add(octs.x1y0z0());
      all.add(octs.x0y1z0());
      all.add(octs.x1y1z0());
      all.add(octs.x0y0z1());
      all.add(octs.x1y0z1());
      all.add(octs.x0y1z1());
      all.add(octs.x1y1z1());

      for (int i = 0; i < all.size(); ++i) {
        final VolumeL v0 = all.get(i);
        System.out.printf("contains: %s %s\n", volume, v0);
        Assert.assertTrue(VolumesL.contains(volume, v0));
        Assert.assertTrue(VolumesL.overlaps(v0, volume));

        for (int j = 0; j < all.size(); ++j) {
          if (i == j) {
            continue;
          }

          final VolumeL a = v0;
          final VolumeL b = all.get(j);
          Assert.assertFalse(VolumesL.overlaps(a, b));
          Assert.assertFalse(VolumesL.overlaps(b, a));
        }
      }
      return null;
    });
  }

  @Test
  public void testTooSmallHeight()
  {
    Assert.assertEquals(
      Optional.empty(),
      OctantsL.subdivide(
        VolumeL.of(0L, 2L, 0L, 1L, 0L, 2L)));
  }

  @Test
  public void testTooSmallWidth()
  {
    Assert.assertEquals(
      Optional.empty(),
      OctantsL.subdivide(
        VolumeL.of(0L, 1L, 0L, 2L, 0L, 2L)));
  }

  @Test
  public void testTooSmallDepth()
  {
    Assert.assertEquals(
      Optional.empty(),
      OctantsL.subdivide(
        VolumeL.of(0L, 2L, 0L, 2L, 0L, 1L)));
  }
}
