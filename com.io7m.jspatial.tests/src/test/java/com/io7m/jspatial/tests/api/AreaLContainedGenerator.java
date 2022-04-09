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
import com.io7m.jregions.core.unparameterized.areas.AreaL;
import com.io7m.jregions.core.unparameterized.areas.AreasL;
import net.java.quickcheck.Generator;

import java.util.Random;

public final class AreaLContainedGenerator implements Generator<AreaL>
{
  private final AreaL container;

  public AreaLContainedGenerator(
    final AreaL in_container)
  {
    this.container = Objects.requireNonNull(in_container, "Contaienr");
  }

  @Override
  public AreaL next()
  {
    final Random random = new Random();

    final long x_size =
      (long) random.nextInt(Math.toIntExact(this.container.sizeX() / 2L)) + 1L;
    final long y_size =
      (long) random.nextInt(Math.toIntExact(this.container.sizeY() / 2L)) + 1L;

    final AreaL initial = AreasL.create(
      this.container.minimumX(),
      this.container.minimumY(),
      x_size,
      y_size);

    final long x_shift = (long) random.nextInt(Math.toIntExact(x_size));
    final long y_shift = (long) random.nextInt(Math.toIntExact(y_size));

    final AreaL area =
      AreasL.moveRelative(initial, x_shift, y_shift);

    Postconditions.checkPostconditionV(
      AreasL.contains(this.container, area),
      "Area %s contains %s",
      this.container,
      area);
    return area;
  }
}
