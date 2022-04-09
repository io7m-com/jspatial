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

package com.io7m.jspatial.tests.api;

import com.io7m.jaffirm.core.Postconditions;
import java.util.Objects;
import com.io7m.jregions.core.unparameterized.volumes.VolumeI;
import com.io7m.jregions.core.unparameterized.volumes.VolumesI;
import net.java.quickcheck.Generator;

import java.util.Random;

public final class VolumeIContainedGenerator implements Generator<VolumeI>
{
  private final VolumeI container;

  public VolumeIContainedGenerator(
    final VolumeI in_container)
  {
    this.container = Objects.requireNonNull(in_container, "Contaienr");
  }

  @Override
  public VolumeI next()
  {
    final Random random = new Random();

    final int x_size =
      random.nextInt(Math.toIntExact(this.container.sizeX() / 2)) + 1;
    final int y_size =
      random.nextInt(Math.toIntExact(this.container.sizeY() / 2)) + 1;
    final int z_size =
      random.nextInt(Math.toIntExact(this.container.sizeZ() / 2)) + 1;

    final VolumeI initial = VolumesI.create(
      this.container.minimumX(),
      this.container.minimumY(),
      this.container.minimumZ(),
      x_size,
      y_size,
      z_size);

    final int x_shift = random.nextInt(Math.toIntExact(x_size));
    final int y_shift = random.nextInt(Math.toIntExact(y_size));
    final int z_shift = random.nextInt(Math.toIntExact(z_size));

    final VolumeI volume =
      VolumesI.moveRelative(initial, x_shift, y_shift, z_shift);

    Postconditions.checkPostconditionV(
      VolumesI.contains(this.container, volume),
      "Volume %s contains %s",
      this.container,
      volume);
    return volume;
  }
}
