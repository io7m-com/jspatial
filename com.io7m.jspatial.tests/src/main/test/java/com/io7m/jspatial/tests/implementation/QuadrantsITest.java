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

import com.io7m.jregions.core.unparameterized.areas.AreaI;
import com.io7m.jregions.core.unparameterized.areas.AreaXYSplitI;
import com.io7m.jregions.core.unparameterized.areas.AreasI;
import com.io7m.jregions.generators.AreaIGenerator;
import com.io7m.jspatial.implementation.QuadrantsI;
import com.io7m.jspatial.tests.TestUtilities;
import com.io7m.jspatial.tests.rules.PercentagePassRule;
import com.io7m.jspatial.tests.rules.PercentagePassing;
import net.java.quickcheck.generator.support.IntegerGenerator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;

public final class QuadrantsITest
{
  @Rule public PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Test
  @PercentagePassing
  public void testExhaustive()
  {
    final AreaIGenerator generator =
      new AreaIGenerator(new IntegerGenerator(-10000, 10000));

    final AreaI area = generator.next();
    final Optional<AreaXYSplitI<AreaI>> q_opt = QuadrantsI.subdivide(area);
    Assert.assertTrue(q_opt.isPresent());

    q_opt.map(quads -> {
      Assert.assertTrue(AreasI.contains(area, quads.x0y0()));
      Assert.assertTrue(AreasI.contains(area, quads.x1y0()));
      Assert.assertTrue(AreasI.contains(area, quads.x0y1()));
      Assert.assertTrue(AreasI.contains(area, quads.x1y1()));

      System.out.printf(
        "Check no overlap %s and %s\n",
        quads.x0y0(),
        quads.x1y0());
      Assert.assertFalse(AreasI.overlaps(quads.x0y0(), quads.x1y0()));
      System.out.printf(
        "Check no overlap %s and %s\n",
        quads.x0y0(),
        quads.x0y1());
      Assert.assertFalse(AreasI.overlaps(quads.x0y0(), quads.x0y1()));
      System.out.printf(
        "Check no overlap %s and %s\n",
        quads.x0y0(),
        quads.x1y1());
      Assert.assertFalse(AreasI.overlaps(quads.x0y0(), quads.x1y1()));
      return null;
    });
  }

  @Test
  public void testOverlapSpecific()
  {
    final Optional<AreaXYSplitI<AreaI>> q_opt =
      QuadrantsI.subdivide(AreaI.of(0, 10, 0, 10));
    Assert.assertTrue(q_opt.isPresent());

    final AreaXYSplitI<AreaI> quads = q_opt.get();

    System.out.printf("x0y0 %s\n", quads.x0y0());
    System.out.printf("x1y0 %s\n", quads.x1y0());
    System.out.printf("x0y1 %s\n", quads.x0y1());
    System.out.printf("x1y1 %s\n", quads.x1y1());

    Assert.assertTrue(AreasI.overlaps(quads.x0y0(), quads.x0y0()));
    Assert.assertFalse(AreasI.overlaps(quads.x0y0(), quads.x0y1()));
    Assert.assertFalse(AreasI.overlaps(quads.x0y0(), quads.x1y0()));
    Assert.assertFalse(AreasI.overlaps(quads.x0y0(), quads.x1y1()));

    Assert.assertFalse(AreasI.overlaps(quads.x1y0(), quads.x0y0()));
    Assert.assertFalse(AreasI.overlaps(quads.x1y0(), quads.x0y1()));
    Assert.assertTrue(AreasI.overlaps(quads.x1y0(), quads.x1y0()));
    Assert.assertFalse(AreasI.overlaps(quads.x1y0(), quads.x1y1()));

    Assert.assertFalse(AreasI.overlaps(quads.x0y1(), quads.x0y0()));
    Assert.assertTrue(AreasI.overlaps(quads.x0y1(), quads.x0y1()));
    Assert.assertFalse(AreasI.overlaps(quads.x0y1(), quads.x1y0()));
    Assert.assertFalse(AreasI.overlaps(quads.x0y1(), quads.x1y1()));

    Assert.assertFalse(AreasI.overlaps(quads.x1y1(), quads.x0y0()));
    Assert.assertFalse(AreasI.overlaps(quads.x1y1(), quads.x0y1()));
    Assert.assertFalse(AreasI.overlaps(quads.x1y1(), quads.x1y0()));
    Assert.assertTrue(AreasI.overlaps(quads.x1y1(), quads.x1y1()));
  }

  @Test
  public void testTooSmallHeight()
  {
    Assert.assertEquals(
      Optional.empty(),
      QuadrantsI.subdivide(
        AreaI.of(0, 2, 0, 1)));
  }

  @Test
  public void testTooSmallWidth()
  {
    Assert.assertEquals(
      Optional.empty(),
      QuadrantsI.subdivide(
        AreaI.of(0, 1, 0, 2)));
  }
}
