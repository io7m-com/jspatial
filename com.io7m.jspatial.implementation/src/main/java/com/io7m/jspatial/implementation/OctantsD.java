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
import com.io7m.jspatial.api.BoundingVolumeD;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import net.jcip.annotations.Immutable;

/**
 * Functions to produce new octants from an existing volume.
 */

@Immutable
public final class OctantsD
{
  private final BoundingVolumeD x0y0z0;
  private final BoundingVolumeD x1y0z0;
  private final BoundingVolumeD x0y1z0;
  private final BoundingVolumeD x1y1z0;
  private final BoundingVolumeD x0y0z1;
  private final BoundingVolumeD x1y0z1;
  private final BoundingVolumeD x0y1z1;
  private final BoundingVolumeD x1y1z1;

  private OctantsD(
    final BoundingVolumeD in_x0y0z0,
    final BoundingVolumeD in_x1y0z0,
    final BoundingVolumeD in_x0y1z0,
    final BoundingVolumeD in_x1y1z0,
    final BoundingVolumeD in_x0y0z1,
    final BoundingVolumeD in_x1y0z1,
    final BoundingVolumeD in_x0y1z1,
    final BoundingVolumeD in_x1y1z1)
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

  public static OctantsD subdivide(
    final BoundingVolumeD volume)
  {
    NullCheck.notNull(volume, "Volume");

    final Vector3D lower = volume.lower();
    final Vector3D upper = volume.upper();

    final double[] x_spans = new double[4];
    Subdivision.subdivide1D(lower.x(), upper.x(), x_spans);
    final double[] y_spans = new double[4];
    Subdivision.subdivide1D(lower.y(), upper.y(), y_spans);
    final double[] z_spans = new double[4];
    Subdivision.subdivide1D(lower.z(), upper.z(), z_spans);

    final Vector3D x0y0z0_lower =
      Vector3D.of(x_spans[0], y_spans[0], z_spans[0]);
    final Vector3D x0y0z0_upper =
      Vector3D.of(x_spans[1], y_spans[1], z_spans[1]);

    final Vector3D x1y0z0_lower =
      Vector3D.of(x_spans[2], y_spans[0], z_spans[0]);
    final Vector3D x1y0z0_upper =
      Vector3D.of(x_spans[3], y_spans[1], z_spans[1]);

    final Vector3D x0y1z0_lower =
      Vector3D.of(x_spans[0], y_spans[2], z_spans[0]);
    final Vector3D x0y1z0_upper =
      Vector3D.of(x_spans[1], y_spans[3], z_spans[1]);

    final Vector3D x1y1z0_lower =
      Vector3D.of(x_spans[2], y_spans[2], z_spans[0]);
    final Vector3D x1y1z0_upper =
      Vector3D.of(x_spans[3], y_spans[3], z_spans[1]);

    final Vector3D x0y0z1_lower =
      Vector3D.of(x_spans[0], y_spans[0], z_spans[2]);
    final Vector3D x0y0z1_upper =
      Vector3D.of(x_spans[1], y_spans[1], z_spans[3]);

    final Vector3D x1y0z1_lower =
      Vector3D.of(x_spans[2], y_spans[0], z_spans[2]);
    final Vector3D x1y0z1_upper =
      Vector3D.of(x_spans[3], y_spans[1], z_spans[3]);

    final Vector3D x0y1z1_lower =
      Vector3D.of(x_spans[0], y_spans[2], z_spans[2]);
    final Vector3D x0y1z1_upper =
      Vector3D.of(x_spans[1], y_spans[3], z_spans[3]);

    final Vector3D x1y1z1_lower =
      Vector3D.of(x_spans[2], y_spans[2], z_spans[2]);
    final Vector3D x1y1z1_upper =
      Vector3D.of(x_spans[3], y_spans[3], z_spans[3]);

    final BoundingVolumeD x0y0z0 =
      BoundingVolumeD.of(x0y0z0_lower, x0y0z0_upper);
    final BoundingVolumeD x1y0z0 =
      BoundingVolumeD.of(x1y0z0_lower, x1y0z0_upper);
    final BoundingVolumeD x0y1z0 =
      BoundingVolumeD.of(x0y1z0_lower, x0y1z0_upper);
    final BoundingVolumeD x1y1z0 =
      BoundingVolumeD.of(x1y1z0_lower, x1y1z0_upper);

    final BoundingVolumeD x0y0z1 =
      BoundingVolumeD.of(x0y0z1_lower, x0y0z1_upper);
    final BoundingVolumeD x1y0z1 =
      BoundingVolumeD.of(x1y0z1_lower, x1y0z1_upper);
    final BoundingVolumeD x0y1z1 =
      BoundingVolumeD.of(x0y1z1_lower, x0y1z1_upper);
    final BoundingVolumeD x1y1z1 =
      BoundingVolumeD.of(x1y1z1_lower, x1y1z1_upper);

    return new OctantsD(
      x0y0z0, x1y0z0, x0y1z0, x1y1z0,
      x0y0z1, x1y0z1, x0y1z1, x1y1z1);
  }

  /**
   * @return The least X, least Y, least Z octant
   */

  public BoundingVolumeD x0y0z0()
  {
    return this.x0y0z0;
  }

  /**
   * @return The most X, least Y, least Z octant
   */

  public BoundingVolumeD x1y0z0()
  {
    return this.x1y0z0;
  }

  /**
   * @return The least X, most Y, least Z octant
   */

  public BoundingVolumeD x0y1z0()
  {
    return this.x0y1z0;
  }

  /**
   * @return The most X, most Y, least Z octant
   */

  public BoundingVolumeD x1y1z0()
  {
    return this.x1y1z0;
  }

  /**
   * @return The least X, least Y, most Z octant
   */

  public BoundingVolumeD x0y0z1()
  {
    return this.x0y0z1;
  }

  /**
   * @return The most X, least Y, most Z octant
   */

  public BoundingVolumeD x1y0z1()
  {
    return this.x1y0z1;
  }

  /**
   * @return The least X, most Y, most Z octant
   */

  public BoundingVolumeD x0y1z1()
  {
    return this.x0y1z1;
  }

  /**
   * @return The most X, most Y, most Z octant
   */

  public BoundingVolumeD x1y1z1()
  {
    return this.x1y1z1;
  }
}
