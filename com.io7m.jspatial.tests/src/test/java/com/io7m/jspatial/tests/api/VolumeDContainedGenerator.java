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

package com.io7m.jspatial.tests.api;

import com.io7m.jaffirm.core.Postconditions;
import java.util.Objects;
import com.io7m.jregions.core.unparameterized.volumes.VolumeD;
import com.io7m.jregions.core.unparameterized.volumes.VolumesD;
import net.java.quickcheck.Generator;

import java.util.Random;

public final class VolumeDContainedGenerator implements Generator<VolumeD>
{
  private final VolumeD container;

  public VolumeDContainedGenerator(
    final VolumeD in_container)
  {
    this.container = Objects.requireNonNull(in_container, "Container");
  }

  @Override
  public VolumeD next()
  {
    final Random random = new Random();

    final double x_size =
      random.nextDouble() * (this.container.sizeX() / 2.0);
    final double y_size =
      random.nextDouble() * (this.container.sizeY() / 2.0);
    final double z_size =
      random.nextDouble() * (this.container.sizeZ() / 2.0);

    final VolumeD initial = VolumesD.create(
      this.container.minimumX(),
      this.container.minimumY(),
      this.container.minimumZ(),
      x_size,
      y_size,
      z_size);

    final double x_shift = random.nextDouble() * x_size;
    final double y_shift = random.nextDouble() * y_size;
    final double z_shift = random.nextDouble() * z_size;

    final VolumeD volume =
      VolumesD.moveRelative(initial, x_shift, y_shift, z_shift);

    Postconditions.checkPostconditionV(
      VolumesD.contains(this.container, volume),
      "Volume %s contains %s",
      this.container,
      volume);
    return volume;
  }
}
