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
import com.io7m.jspatial.api.BoundingAreaD;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import net.jcip.annotations.Immutable;

/**
 * Functions to produce new quadrants from an existing area.
 */

@Immutable
public final class QuadrantsD
{
  private final BoundingAreaD x0y0;
  private final BoundingAreaD x1y0;
  private final BoundingAreaD x0y1;
  private final BoundingAreaD x1y1;

  private QuadrantsD(
    final BoundingAreaD in_x0y0,
    final BoundingAreaD in_x1y0,
    final BoundingAreaD in_x0y1,
    final BoundingAreaD in_x1y1)
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

  public static QuadrantsD subdivide(
    final BoundingAreaD area)
  {
    NullCheck.notNull(area, "Area");

    final Vector2D lower = area.lower();
    final Vector2D upper = area.upper();

    final double[] x_spans = new double[4];
    Subdivision.subdivide1D(lower.x(), upper.x(), x_spans);
    final double[] y_spans = new double[4];
    Subdivision.subdivide1D(lower.y(), upper.y(), y_spans);

    final Vector2D x0y0_lower = Vector2D.of(x_spans[0], y_spans[0]);
    final Vector2D x0y1_lower = Vector2D.of(x_spans[0], y_spans[2]);
    final Vector2D x1y0_lower = Vector2D.of(x_spans[2], y_spans[0]);
    final Vector2D x1y1_lower = Vector2D.of(x_spans[2], y_spans[2]);
    final Vector2D x0y0_upper = Vector2D.of(x_spans[1], y_spans[1]);
    final Vector2D x0y1_upper = Vector2D.of(x_spans[1], y_spans[3]);
    final Vector2D x1y0_upper = Vector2D.of(x_spans[3], y_spans[1]);
    final Vector2D x1y1_upper = Vector2D.of(x_spans[3], y_spans[3]);

    final BoundingAreaD x0y0 = BoundingAreaD.of(x0y0_lower, x0y0_upper);
    final BoundingAreaD x1y0 = BoundingAreaD.of(x1y0_lower, x1y0_upper);
    final BoundingAreaD x0y1 = BoundingAreaD.of(x0y1_lower, x0y1_upper);
    final BoundingAreaD x1y1 = BoundingAreaD.of(x1y1_lower, x1y1_upper);

    return new QuadrantsD(x0y0, x1y0, x0y1, x1y1);
  }

  /**
   * @return The least X, least Y quadrant
   */

  public BoundingAreaD x0y0()
  {
    return this.x0y0;
  }

  /**
   * @return The most X, least Y quadrant
   */

  public BoundingAreaD x1y0()
  {
    return this.x1y0;
  }

  /**
   * @return The least X, most Y quadrant
   */

  public BoundingAreaD x0y1()
  {
    return this.x0y1;
  }

  /**
   * @return The most X, most Y quadrant
   */

  public BoundingAreaD x1y1()
  {
    return this.x1y1;
  }

}
