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

package com.io7m.jspatial.implementation;

import com.io7m.jnull.NullCheck;
import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jtensors.VectorI2L;
import net.jcip.annotations.Immutable;

import java.util.Optional;

/**
 * Functions to produce new quadrants from an existing area.
 */

@Immutable
public final class QuadrantsL
{
  private final BoundingAreaL x0y0;
  private final BoundingAreaL x1y0;
  private final BoundingAreaL x0y1;
  private final BoundingAreaL x1y1;

  private QuadrantsL(
    final BoundingAreaL in_x0y0,
    final BoundingAreaL in_x1y0,
    final BoundingAreaL in_x0y1,
    final BoundingAreaL in_x1y1)
  {
    this.x0y0 = NullCheck.notNull(in_x0y0, "x0y0");
    this.x1y0 = NullCheck.notNull(in_x1y0, "x1y0");
    this.x0y1 = NullCheck.notNull(in_x0y1, "x0y1");
    this.x1y1 = NullCheck.notNull(in_x1y1, "x1y1");
  }

  /**
   * <p>Subdivide a given area into quadrants.</p>
   *
   * @param area The area
   *
   * @return A set of newly subdivided quadrants.
   */

  public static Optional<QuadrantsL> subdivide(
    final BoundingAreaL area)
  {
    NullCheck.notNull(area, "Area");

    final long width = area.width();
    final long height = area.height();

    if (width >= 2L && height >= 2L) {
      final VectorI2L lower = area.lower();
      final VectorI2L upper = area.upper();

      final long[] x_spans = new long[4];
      Subdivision.subdivide1L(lower.getXL(), upper.getXL(), x_spans);
      final long[] y_spans = new long[4];
      Subdivision.subdivide1L(lower.getYL(), upper.getYL(), y_spans);

      final VectorI2L x0y0_lower = new VectorI2L(x_spans[0], y_spans[0]);
      final VectorI2L x0y1_lower = new VectorI2L(x_spans[0], y_spans[2]);
      final VectorI2L x1y0_lower = new VectorI2L(x_spans[2], y_spans[0]);
      final VectorI2L x1y1_lower = new VectorI2L(x_spans[2], y_spans[2]);
      final VectorI2L x0y0_upper = new VectorI2L(x_spans[1], y_spans[1]);
      final VectorI2L x0y1_upper = new VectorI2L(x_spans[1], y_spans[3]);
      final VectorI2L x1y0_upper = new VectorI2L(x_spans[3], y_spans[1]);
      final VectorI2L x1y1_upper = new VectorI2L(x_spans[3], y_spans[3]);

      final BoundingAreaL x0y0 = BoundingAreaL.of(x0y0_lower, x0y0_upper);
      final BoundingAreaL x1y0 = BoundingAreaL.of(x1y0_lower, x1y0_upper);
      final BoundingAreaL x0y1 = BoundingAreaL.of(x0y1_lower, x0y1_upper);
      final BoundingAreaL x1y1 = BoundingAreaL.of(x1y1_lower, x1y1_upper);

      return Optional.of(new QuadrantsL(x0y0, x1y0, x0y1, x1y1));
    }

    return Optional.empty();
  }

  /**
   * @return The least X, least Y quadrant
   */

  public BoundingAreaL x0y0()
  {
    return this.x0y0;
  }

  /**
   * @return The most X, least Y quadrant
   */

  public BoundingAreaL x1y0()
  {
    return this.x1y0;
  }

  /**
   * @return The least X, most Y quadrant
   */

  public BoundingAreaL x0y1()
  {
    return this.x0y1;
  }

  /**
   * @return The most X, most Y quadrant
   */

  public BoundingAreaL x1y1()
  {
    return this.x1y1;
  }

}
