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
import com.io7m.jregions.core.unparameterized.areas.AreaL;
import com.io7m.jregions.core.unparameterized.areas.AreaXYSplitL;
import com.io7m.jregions.core.unparameterized.areas.AreasL;
import com.io7m.jregions.generators.AreaLGenerator;
import com.io7m.jspatial.implementation.QuadrantsL;
import com.io7m.jspatial.tests.TestUtilities;
import com.io7m.jspatial.tests.rules.PercentagePassRule;
import com.io7m.jspatial.tests.rules.PercentagePassing;
import net.java.quickcheck.generator.support.LongGenerator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;

import static com.io7m.jfunctional.Unit.*;

public final class QuadrantsLTest
{
  @Rule public PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Test
  @PercentagePassing
  public void testExhaustive()
  {
    final AreaLGenerator generator =
      new AreaLGenerator(new LongGenerator(-10000L, 10000L));

    final AreaL area = generator.next();
    final Optional<AreaXYSplitL<AreaL>> q_opt = QuadrantsL.subdivide(area);
    Assert.assertTrue(q_opt.isPresent());

    q_opt.map(quads -> {
      Assert.assertTrue(AreasL.contains(area, quads.x0y0()));
      Assert.assertTrue(AreasL.contains(area, quads.x1y0()));
      Assert.assertTrue(AreasL.contains(area, quads.x0y1()));
      Assert.assertTrue(AreasL.contains(area, quads.x1y1()));

      System.out.printf(
        "Check no overlap %s and %s\n",
        quads.x0y0(),
        quads.x1y0());
      Assert.assertFalse(AreasL.overlaps(quads.x0y0(), quads.x1y0()));
      System.out.printf(
        "Check no overlap %s and %s\n",
        quads.x0y0(),
        quads.x0y1());
      Assert.assertFalse(AreasL.overlaps(quads.x0y0(), quads.x0y1()));
      System.out.printf(
        "Check no overlap %s and %s\n",
        quads.x0y0(),
        quads.x1y1());
      Assert.assertFalse(AreasL.overlaps(quads.x0y0(), quads.x1y1()));
      return unit();
    });
  }

  @Test
  public void testOverlapSpecific()
  {
    final Optional<AreaXYSplitL<AreaL>> q_opt =
      QuadrantsL.subdivide(AreaL.of(0L, 10L, 0L, 10L));
    Assert.assertTrue(q_opt.isPresent());

    final AreaXYSplitL<AreaL> quads = q_opt.get();

    System.out.printf("x0y0 %s\n", quads.x0y0());
    System.out.printf("x1y0 %s\n", quads.x1y0());
    System.out.printf("x0y1 %s\n", quads.x0y1());
    System.out.printf("x1y1 %s\n", quads.x1y1());

    Assert.assertTrue(AreasL.overlaps(quads.x0y0(), quads.x0y0()));
    Assert.assertFalse(AreasL.overlaps(quads.x0y0(), quads.x0y1()));
    Assert.assertFalse(AreasL.overlaps(quads.x0y0(), quads.x1y0()));
    Assert.assertFalse(AreasL.overlaps(quads.x0y0(), quads.x1y1()));

    Assert.assertFalse(AreasL.overlaps(quads.x1y0(), quads.x0y0()));
    Assert.assertFalse(AreasL.overlaps(quads.x1y0(), quads.x0y1()));
    Assert.assertTrue(AreasL.overlaps(quads.x1y0(), quads.x1y0()));
    Assert.assertFalse(AreasL.overlaps(quads.x1y0(), quads.x1y1()));

    Assert.assertFalse(AreasL.overlaps(quads.x0y1(), quads.x0y0()));
    Assert.assertTrue(AreasL.overlaps(quads.x0y1(), quads.x0y1()));
    Assert.assertFalse(AreasL.overlaps(quads.x0y1(), quads.x1y0()));
    Assert.assertFalse(AreasL.overlaps(quads.x0y1(), quads.x1y1()));

    Assert.assertFalse(AreasL.overlaps(quads.x1y1(), quads.x0y0()));
    Assert.assertFalse(AreasL.overlaps(quads.x1y1(), quads.x0y1()));
    Assert.assertFalse(AreasL.overlaps(quads.x1y1(), quads.x1y0()));
    Assert.assertTrue(AreasL.overlaps(quads.x1y1(), quads.x1y1()));
  }

  @Test
  public void testTooSmallHeight()
  {
    Assert.assertEquals(
      Optional.empty(),
      QuadrantsL.subdivide(
        AreaL.of(0L, 2L, 0L, 1L)));
  }

  @Test
  public void testTooSmallWidth()
  {
    Assert.assertEquals(
      Optional.empty(),
      QuadrantsL.subdivide(
        AreaL.of(0L, 1L, 0L, 2L)));
  }
}
