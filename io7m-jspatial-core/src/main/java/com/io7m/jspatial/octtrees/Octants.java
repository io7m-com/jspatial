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

package com.io7m.jspatial.octtrees;

import com.io7m.jspatial.Dimensions;
import com.io7m.jtensors.VectorI3I;

/**
 * Functions to produce new octants from an existing pair of corners.
 */

public final class Octants
{
  /**
   * Split an octant defined by the two points <code>lower</code> and
   * <code>upper</code> into eight octants.
   *
   * @return Eight new octants
   * @param upper
   *          The upper corner
   * @param lower
   *          The lower corner
   */

  public static Octants split(
    final VectorI3I lower,
    final VectorI3I upper)
  {
    final int size_x = Dimensions.getSpanSizeX(lower, upper);
    final int size_y = Dimensions.getSpanSizeY(lower, upper);
    final int size_z = Dimensions.getSpanSizeZ(lower, upper);

    assert size_x >= 2;
    assert size_y >= 2;
    assert size_z >= 2;

    final int[] x_spans = new int[4];
    final int[] y_spans = new int[4];
    final int[] z_spans = new int[4];

    Dimensions.split1D(lower.getXI(), upper.getXI(), x_spans);
    Dimensions.split1D(lower.getYI(), upper.getYI(), y_spans);
    Dimensions.split1D(lower.getZI(), upper.getZI(), z_spans);

    final VectorI3I in_x0y0z0_lower =
      new VectorI3I(x_spans[0], y_spans[0], z_spans[0]);
    final VectorI3I in_x0y0z0_upper =
      new VectorI3I(x_spans[1], y_spans[1], z_spans[1]);

    final VectorI3I in_x1y0z0_lower =
      new VectorI3I(x_spans[2], y_spans[0], z_spans[0]);
    final VectorI3I in_x1y0z0_upper =
      new VectorI3I(x_spans[3], y_spans[1], z_spans[1]);

    final VectorI3I in_x0y1z0_lower =
      new VectorI3I(x_spans[0], y_spans[2], z_spans[0]);
    final VectorI3I in_x0y1z0_upper =
      new VectorI3I(x_spans[1], y_spans[3], z_spans[1]);

    final VectorI3I in_x1y1z0_lower =
      new VectorI3I(x_spans[2], y_spans[2], z_spans[0]);
    final VectorI3I in_x1y1z0_upper =
      new VectorI3I(x_spans[3], y_spans[3], z_spans[1]);

    final VectorI3I in_x0y0z1_lower =
      new VectorI3I(x_spans[0], y_spans[0], z_spans[2]);
    final VectorI3I in_x0y0z1_upper =
      new VectorI3I(x_spans[1], y_spans[1], z_spans[3]);

    final VectorI3I in_x1y0z1_lower =
      new VectorI3I(x_spans[2], y_spans[0], z_spans[2]);
    final VectorI3I in_x1y0z1_upper =
      new VectorI3I(x_spans[3], y_spans[1], z_spans[3]);

    final VectorI3I in_x0y1z1_lower =
      new VectorI3I(x_spans[0], y_spans[2], z_spans[2]);
    final VectorI3I in_x0y1z1_upper =
      new VectorI3I(x_spans[1], y_spans[3], z_spans[3]);

    final VectorI3I in_x1y1z1_lower =
      new VectorI3I(x_spans[2], y_spans[2], z_spans[2]);
    final VectorI3I in_x1y1z1_upper =
      new VectorI3I(x_spans[3], y_spans[3], z_spans[3]);

    return new Octants(
      in_x0y0z0_lower,
      in_x0y0z0_upper,
      in_x1y0z0_lower,
      in_x1y0z0_upper,
      in_x0y1z0_lower,
      in_x0y1z0_upper,
      in_x1y1z0_lower,
      in_x1y1z0_upper,
      in_x0y0z1_lower,
      in_x0y0z1_upper,
      in_x1y0z1_lower,
      in_x1y0z1_upper,
      in_x0y1z1_lower,
      in_x0y1z1_upper,
      in_x1y1z1_lower,
      in_x1y1z1_upper);
  }

  private final VectorI3I x0y0z0_lower;
  private final VectorI3I x0y0z0_upper;
  private final VectorI3I x0y0z1_lower;
  private final VectorI3I x0y0z1_upper;
  private final VectorI3I x0y1z0_lower;
  private final VectorI3I x0y1z0_upper;
  private final VectorI3I x0y1z1_lower;
  private final VectorI3I x0y1z1_upper;
  private final VectorI3I x1y0z0_lower;
  private final VectorI3I x1y0z0_upper;
  private final VectorI3I x1y0z1_lower;
  private final VectorI3I x1y0z1_upper;
  private final VectorI3I x1y1z0_lower;
  private final VectorI3I x1y1z0_upper;
  private final VectorI3I x1y1z1_lower;

  private final VectorI3I x1y1z1_upper;

  private Octants(
    final VectorI3I in_x0y0z0_lower,
    final VectorI3I in_x0y0z0_upper,
    final VectorI3I in_x1y0z0_lower,
    final VectorI3I in_x1y0z0_upper,
    final VectorI3I in_x0y1z0_lower,
    final VectorI3I in_x0y1z0_upper,
    final VectorI3I in_x1y1z0_lower,
    final VectorI3I in_x1y1z0_upper,
    final VectorI3I in_x0y0z1_lower,
    final VectorI3I in_x0y0z1_upper,
    final VectorI3I in_x1y0z1_lower,
    final VectorI3I in_x1y0z1_upper,
    final VectorI3I in_x0y1z1_lower,
    final VectorI3I in_x0y1z1_upper,
    final VectorI3I in_x1y1z1_lower,
    final VectorI3I in_x1y1z1_upper)
  {
    this.x0y0z0_lower = in_x0y0z0_lower;
    this.x0y0z0_upper = in_x0y0z0_upper;
    this.x1y0z0_lower = in_x1y0z0_lower;
    this.x1y0z0_upper = in_x1y0z0_upper;
    this.x0y1z0_lower = in_x0y1z0_lower;
    this.x0y1z0_upper = in_x0y1z0_upper;
    this.x1y1z0_lower = in_x1y1z0_lower;
    this.x1y1z0_upper = in_x1y1z0_upper;
    this.x0y0z1_lower = in_x0y0z1_lower;
    this.x0y0z1_upper = in_x0y0z1_upper;
    this.x1y0z1_lower = in_x1y0z1_lower;
    this.x1y0z1_upper = in_x1y0z1_upper;
    this.x0y1z1_lower = in_x0y1z1_lower;
    this.x0y1z1_upper = in_x0y1z1_upper;
    this.x1y1z1_lower = in_x1y1z1_lower;
    this.x1y1z1_upper = in_x1y1z1_upper;
  }

  /**
   * @return The lower corner of the <code>(x0, y0, z0)</code> octant.
   */

  public VectorI3I getX0Y0Z0Lower()
  {
    return this.x0y0z0_lower;
  }

  /**
   * @return The upper corner of the <code>(x0, y0, z0)</code> octant.
   */

  public VectorI3I getX0Y0Z0Upper()
  {
    return this.x0y0z0_upper;
  }

  /**
   * @return The lower corner of the <code>(x0, y0, z1)</code> octant.
   */

  public VectorI3I getX0Y0Z1Lower()
  {
    return this.x0y0z1_lower;
  }

  /**
   * @return The upper corner of the <code>(x0, y0, z1)</code> octant.
   */

  public VectorI3I getX0Y0Z1Upper()
  {
    return this.x0y0z1_upper;
  }

  /**
   * @return The lower corner of the <code>(x0, y1, z0)</code> octant.
   */

  public VectorI3I getX0Y1Z0Lower()
  {
    return this.x0y1z0_lower;
  }

  /**
   * @return The upper corner of the <code>(x0, y1, z0)</code> octant.
   */

  public VectorI3I getX0Y1Z0Upper()
  {
    return this.x0y1z0_upper;
  }

  /**
   * @return The lower corner of the <code>(x0, y1, z1)</code> octant.
   */

  public VectorI3I getX0Y1Z1Lower()
  {
    return this.x0y1z1_lower;
  }

  /**
   * @return The upper corner of the <code>(x0, y1, z1)</code> octant.
   */

  public VectorI3I getX0Y1Z1Upper()
  {
    return this.x0y1z1_upper;
  }

  /**
   * @return The lower corner of the <code>(x1, y0, z0)</code> octant.
   */

  public VectorI3I getX1Y0Z0Lower()
  {
    return this.x1y0z0_lower;
  }

  /**
   * @return The upper corner of the <code>(x1, y0, z0)</code> octant.
   */

  public VectorI3I getX1Y0Z0Upper()
  {
    return this.x1y0z0_upper;
  }

  /**
   * @return The lower corner of the <code>(x1, y0, z1)</code> octant.
   */

  public VectorI3I getX1Y0Z1Lower()
  {
    return this.x1y0z1_lower;
  }

  /**
   * @return The upper corner of the <code>(x1, y0, z1)</code> octant.
   */

  public VectorI3I getX1Y0Z1Upper()
  {
    return this.x1y0z1_upper;
  }

  /**
   * @return The lower corner of the <code>(x1, y1, z0)</code> octant.
   */

  public VectorI3I getX1Y1Z0Lower()
  {
    return this.x1y1z0_lower;
  }

  /**
   * @return The upper corner of the <code>(x1, y1, z0)</code> octant.
   */

  public VectorI3I getX1Y1Z0Upper()
  {
    return this.x1y1z0_upper;
  }

  /**
   * @return The lower corner of the <code>(x1, y1, z1)</code> octant.
   */

  public VectorI3I getX1Y1Z1Lower()
  {
    return this.x1y1z1_lower;
  }

  /**
   * @return The upper corner of the <code>(x1, y1, z1)</code> octant.
   */

  public VectorI3I getX1Y1Z1Upper()
  {
    return this.x1y1z1_upper;
  }
}
