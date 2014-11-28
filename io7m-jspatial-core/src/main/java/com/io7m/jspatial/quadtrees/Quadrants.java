/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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

package com.io7m.jspatial.quadtrees;

import com.io7m.jspatial.Dimensions;
import com.io7m.jtensors.VectorI2I;

/**
 * Functions to produce new quadrants from an existing pair of corners.
 */

public final class Quadrants
{
  /**
   * <p>
   * Split a quadrant defined by the two points <code>lower</code> and
   * <code>upper</code> into four quadrants.
   * </p>
   *
   * @param lower
   *          The lower corner
   * @param upper
   *          The upper corner
   * @return A set of newly split quadrants.
   */

  public static Quadrants split(
    final VectorI2I lower,
    final VectorI2I upper)
  {
    final int size_x = Dimensions.getSpanSizeX(lower, upper);
    final int size_y = Dimensions.getSpanSizeY(lower, upper);

    assert size_x >= 2;
    assert size_y >= 2;

    final int[] x_spans = new int[4];
    final int[] y_spans = new int[4];

    Dimensions.split1D(lower.getXI(), upper.getXI(), x_spans);
    Dimensions.split1D(lower.getYI(), upper.getYI(), y_spans);

    final VectorI2I in_x0y0_lower = new VectorI2I(x_spans[0], y_spans[0]);
    final VectorI2I in_x0y1_lower = new VectorI2I(x_spans[0], y_spans[2]);
    final VectorI2I in_x1y0_lower = new VectorI2I(x_spans[2], y_spans[0]);
    final VectorI2I in_x1y1_lower = new VectorI2I(x_spans[2], y_spans[2]);
    final VectorI2I in_x0y0_upper = new VectorI2I(x_spans[1], y_spans[1]);
    final VectorI2I in_x0y1_upper = new VectorI2I(x_spans[1], y_spans[3]);
    final VectorI2I in_x1y0_upper = new VectorI2I(x_spans[3], y_spans[1]);
    final VectorI2I in_x1y1_upper = new VectorI2I(x_spans[3], y_spans[3]);

    return new Quadrants(
      in_x0y0_lower,
      in_x0y0_upper,
      in_x0y1_lower,
      in_x0y1_upper,
      in_x1y0_lower,
      in_x1y0_upper,
      in_x1y1_lower,
      in_x1y1_upper);
  }

  private final VectorI2I x0y0_lower;
  private final VectorI2I x0y0_upper;
  private final VectorI2I x0y1_lower;
  private final VectorI2I x0y1_upper;
  private final VectorI2I x1y0_lower;
  private final VectorI2I x1y0_upper;
  private final VectorI2I x1y1_lower;
  private final VectorI2I x1y1_upper;

  private Quadrants(
    final VectorI2I in_x0y0_lower,
    final VectorI2I in_x0y0_upper,
    final VectorI2I in_x0y1_lower,
    final VectorI2I in_x0y1_upper,
    final VectorI2I in_x1y0_lower,
    final VectorI2I in_x1y0_upper,
    final VectorI2I in_x1y1_lower,
    final VectorI2I in_x1y1_upper)
  {
    this.x0y0_lower = in_x0y0_lower;
    this.x0y0_upper = in_x0y0_upper;
    this.x0y1_lower = in_x0y1_lower;
    this.x0y1_upper = in_x0y1_upper;
    this.x1y0_lower = in_x1y0_lower;
    this.x1y0_upper = in_x1y0_upper;
    this.x1y1_lower = in_x1y1_lower;
    this.x1y1_upper = in_x1y1_upper;
  }

  /**
   * @return The lower <code>(x0, y0)</code> corner
   */

  public VectorI2I getX0Y0Lower()
  {
    return this.x0y0_lower;
  }

  /**
   * @return The upper <code>(x0, y0)</code> corner
   */

  public VectorI2I getX0Y0Upper()
  {
    return this.x0y0_upper;
  }

  /**
   * @return The lower <code>(x0, y1)</code> corner
   */

  public VectorI2I getX0Y1Lower()
  {
    return this.x0y1_lower;
  }

  /**
   * @return The upper <code>(x0, y1)</code> corner
   */

  public VectorI2I getX0Y1Upper()
  {
    return this.x0y1_upper;
  }

  /**
   * @return The lower <code>(x1, y0)</code> corner
   */

  public VectorI2I getX1Y0Lower()
  {
    return this.x1y0_lower;
  }

  /**
   * @return The upper <code>(x1, y0)</code> corner
   */

  public VectorI2I getX1Y0Upper()
  {
    return this.x1y0_upper;
  }

  /**
   * @return The lower <code>(x1, y1)</code> corner
   */

  public VectorI2I getX1Y1Lower()
  {
    return this.x1y1_lower;
  }

  /**
   * @return The upper <code>(x1, y1)</code> corner
   */

  public VectorI2I getX1Y1Upper()
  {
    return this.x1y1_upper;
  }
}
