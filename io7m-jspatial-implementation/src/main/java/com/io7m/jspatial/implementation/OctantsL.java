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
import com.io7m.jspatial.api.BoundingVolumeL;
import com.io7m.jtensors.VectorI3L;
import net.jcip.annotations.Immutable;

import java.util.Optional;

/**
 * Functions to produce new octants from an existing volume.
 */

@Immutable
public final class OctantsL
{
  private final BoundingVolumeL x0y0z0;
  private final BoundingVolumeL x1y0z0;
  private final BoundingVolumeL x0y1z0;
  private final BoundingVolumeL x1y1z0;
  private final BoundingVolumeL x0y0z1;
  private final BoundingVolumeL x1y0z1;
  private final BoundingVolumeL x0y1z1;
  private final BoundingVolumeL x1y1z1;

  private OctantsL(
    final BoundingVolumeL in_x0y0z0,
    final BoundingVolumeL in_x1y0z0,
    final BoundingVolumeL in_x0y1z0,
    final BoundingVolumeL in_x1y1z0,
    final BoundingVolumeL in_x0y0z1,
    final BoundingVolumeL in_x1y0z1,
    final BoundingVolumeL in_x0y1z1,
    final BoundingVolumeL in_x1y1z1)
  {
    this.x0y0z0 = NullCheck.notNull(in_x0y0z0, "x0y0z0");
    this.x1y0z0 = NullCheck.notNull(in_x1y0z0, "x1y0z0");
    this.x0y1z0 = NullCheck.notNull(in_x0y1z0, "x0y1z0");
    this.x1y1z0 = NullCheck.notNull(in_x1y1z0, "x1y1z0");
    this.x0y0z1 = NullCheck.notNull(in_x0y0z1, "x0y0z1");
    this.x1y0z1 = NullCheck.notNull(in_x1y0z1, "x1y0z1");
    this.x0y1z1 = NullCheck.notNull(in_x0y1z1, "x0y1z1");
    this.x1y1z1 = NullCheck.notNull(in_x1y1z1, "x1y1z1");
  }

  /**
   * <p>Subdivide a given volume into octants.</p>
   *
   * @param volume The volume
   *
   * @return A set of newly subdivided octants.
   */

  public static Optional<OctantsL> subdivide(
    final BoundingVolumeL volume)
  {
    NullCheck.notNull(volume, "Volume");

    final long width = volume.width();
    final long height = volume.height();
    final long depth = volume.depth();

    if (width >= 2L && height >= 2L && depth >= 2L) {
      final VectorI3L lower = volume.lower();
      final VectorI3L upper = volume.upper();

      final long[] x_spans = new long[4];
      Subdivision.subdivide1L(lower.getXL(), upper.getXL(), x_spans);
      final long[] y_spans = new long[4];
      Subdivision.subdivide1L(lower.getYL(), upper.getYL(), y_spans);
      final long[] z_spans = new long[4];
      Subdivision.subdivide1L(lower.getZL(), upper.getZL(), z_spans);

      final VectorI3L x0y0z0_lower =
        new VectorI3L(x_spans[0], y_spans[0], z_spans[0]);
      final VectorI3L x0y0z0_upper =
        new VectorI3L(x_spans[1], y_spans[1], z_spans[1]);

      final VectorI3L x1y0z0_lower =
        new VectorI3L(x_spans[2], y_spans[0], z_spans[0]);
      final VectorI3L x1y0z0_upper =
        new VectorI3L(x_spans[3], y_spans[1], z_spans[1]);

      final VectorI3L x0y1z0_lower =
        new VectorI3L(x_spans[0], y_spans[2], z_spans[0]);
      final VectorI3L x0y1z0_upper =
        new VectorI3L(x_spans[1], y_spans[3], z_spans[1]);

      final VectorI3L x1y1z0_lower =
        new VectorI3L(x_spans[2], y_spans[2], z_spans[0]);
      final VectorI3L x1y1z0_upper =
        new VectorI3L(x_spans[3], y_spans[3], z_spans[1]);

      final VectorI3L x0y0z1_lower =
        new VectorI3L(x_spans[0], y_spans[0], z_spans[2]);
      final VectorI3L x0y0z1_upper =
        new VectorI3L(x_spans[1], y_spans[1], z_spans[3]);

      final VectorI3L x1y0z1_lower =
        new VectorI3L(x_spans[2], y_spans[0], z_spans[2]);
      final VectorI3L x1y0z1_upper =
        new VectorI3L(x_spans[3], y_spans[1], z_spans[3]);

      final VectorI3L x0y1z1_lower =
        new VectorI3L(x_spans[0], y_spans[2], z_spans[2]);
      final VectorI3L x0y1z1_upper =
        new VectorI3L(x_spans[1], y_spans[3], z_spans[3]);

      final VectorI3L x1y1z1_lower =
        new VectorI3L(x_spans[2], y_spans[2], z_spans[2]);
      final VectorI3L x1y1z1_upper =
        new VectorI3L(x_spans[3], y_spans[3], z_spans[3]);

      final BoundingVolumeL x0y0z0 =
        BoundingVolumeL.of(x0y0z0_lower, x0y0z0_upper);
      final BoundingVolumeL x1y0z0 =
        BoundingVolumeL.of(x1y0z0_lower, x1y0z0_upper);
      final BoundingVolumeL x0y1z0 =
        BoundingVolumeL.of(x0y1z0_lower, x0y1z0_upper);
      final BoundingVolumeL x1y1z0 =
        BoundingVolumeL.of(x1y1z0_lower, x1y1z0_upper);

      final BoundingVolumeL x0y0z1 =
        BoundingVolumeL.of(x0y0z1_lower, x0y0z1_upper);
      final BoundingVolumeL x1y0z1 =
        BoundingVolumeL.of(x1y0z1_lower, x1y0z1_upper);
      final BoundingVolumeL x0y1z1 =
        BoundingVolumeL.of(x0y1z1_lower, x0y1z1_upper);
      final BoundingVolumeL x1y1z1 =
        BoundingVolumeL.of(x1y1z1_lower, x1y1z1_upper);

      return Optional.of(new OctantsL(
        x0y0z0, x1y0z0, x0y1z0, x1y1z0,
        x0y0z1, x1y0z1, x0y1z1, x1y1z1));
    }

    return Optional.empty();
  }

  /**
   * @return The least X, least Y, least Z octant
   */

  public BoundingVolumeL x0y0z0()
  {
    return this.x0y0z0;
  }

  /**
   * @return The most X, least Y, least Z octant
   */

  public BoundingVolumeL x1y0z0()
  {
    return this.x1y0z0;
  }

  /**
   * @return The least X, most Y, least Z octant
   */

  public BoundingVolumeL x0y1z0()
  {
    return this.x0y1z0;
  }

  /**
   * @return The most X, most Y, least Z octant
   */

  public BoundingVolumeL x1y1z0()
  {
    return this.x1y1z0;
  }

  /**
   * @return The least X, least Y, most Z octant
   */

  public BoundingVolumeL x0y0z1()
  {
    return this.x0y0z1;
  }

  /**
   * @return The most X, least Y, most Z octant
   */

  public BoundingVolumeL x1y0z1()
  {
    return this.x1y0z1;
  }

  /**
   * @return The least X, most Y, most Z octant
   */

  public BoundingVolumeL x0y1z1()
  {
    return this.x0y1z1;
  }

  /**
   * @return The most X, most Y, most Z octant
   */

  public BoundingVolumeL x1y1z1()
  {
    return this.x1y1z1;
  }
}
