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

import com.io7m.jregions.core.unparameterized.volumes.VolumeI;
import com.io7m.jregions.core.unparameterized.volumes.VolumeXYZSplitI;
import com.io7m.jregions.core.unparameterized.volumes.VolumesI;
import com.io7m.jregions.generators.VolumeIGenerator;
import com.io7m.jspatial.implementation.OctantsI;
import com.io7m.jspatial.tests.TestUtilities;
import com.io7m.jspatial.tests.rules.PercentagePassRule;
import com.io7m.jspatial.tests.rules.PercentagePassing;
import net.java.quickcheck.generator.support.IntegerGenerator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class OctantsITest
{
  @Rule public PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Test
  @PercentagePassing
  public void testExhaustive()
  {
    final VolumeIGenerator generator =
      new VolumeIGenerator(new IntegerGenerator(-10000, 10000));

    final VolumeI volume = generator.next();
    final Optional<VolumeXYZSplitI<VolumeI>> o_opt =
      OctantsI.subdivide(volume);
    Assert.assertTrue(o_opt.isPresent());

    o_opt.map(octs -> {
      final List<VolumeI> all = new ArrayList<>(8);
      all.add(octs.x0y0z0());
      all.add(octs.x1y0z0());
      all.add(octs.x0y1z0());
      all.add(octs.x1y1z0());
      all.add(octs.x0y0z1());
      all.add(octs.x1y0z1());
      all.add(octs.x0y1z1());
      all.add(octs.x1y1z1());

      for (int i = 0; i < all.size(); ++i) {
        final VolumeI v0 = all.get(i);
        System.out.printf("contains: %s %s\n", volume, v0);
        Assert.assertTrue(VolumesI.contains(volume, v0));
        Assert.assertTrue(VolumesI.overlaps(v0, volume));

        for (int j = 0; j < all.size(); ++j) {
          if (i == j) {
            continue;
          }

          final VolumeI a = v0;
          final VolumeI b = all.get(j);
          Assert.assertFalse(VolumesI.overlaps(a, b));
          Assert.assertFalse(VolumesI.overlaps(b, a));
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
      OctantsI.subdivide(
        VolumeI.of(0, 2, 0, 1, 0, 2)));
  }

  @Test
  public void testTooSmallWidth()
  {
    Assert.assertEquals(
      Optional.empty(),
      OctantsI.subdivide(
        VolumeI.of(0, 1, 0, 2, 0, 2)));
  }

  @Test
  public void testTooSmallDepth()
  {
    Assert.assertEquals(
      Optional.empty(),
      OctantsI.subdivide(
        VolumeI.of(0, 2, 0, 2, 0, 1)));
  }
}
