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

package com.io7m.jspatial.tests.api;

import com.io7m.jaffirm.core.Postconditions;
import com.io7m.jspatial.api.BoundingVolumeL;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3L;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.LongGenerator;

import java.util.ArrayList;
import java.util.List;

public final class BoundingVolumeLContainedGenerator implements Generator<BoundingVolumeL>
{
  private final LongGenerator lgx;
  private final LongGenerator lgy;
  private final LongGenerator lgz;
  private final BoundingVolumeL container;

  public BoundingVolumeLContainedGenerator(
    final BoundingVolumeL container)
  {
    this.lgx =
      new LongGenerator(
        container.lower().x() + 1L,
        container.upper().x() - 1L);
    this.lgy =
      new LongGenerator(
        container.lower().y() + 1L,
        container.upper().y() - 1L);
    this.lgz =
      new LongGenerator(
        container.lower().z() + 1L,
        container.upper().z() - 1L);
    this.container = container;
  }

  @Override
  public BoundingVolumeL next()
  {
    try {
      final List<Long> vx = new ArrayList<>(2);
      vx.add(this.lgx.next());
      vx.add(this.lgx.next());
      vx.sort((x, y) -> Long.compare(x.longValue(), y.longValue()));

      final List<Long> vy = new ArrayList<>(2);
      vy.add(this.lgy.next());
      vy.add(this.lgy.next());
      vy.sort((x, y) -> Long.compare(x.longValue(), y.longValue()));

      final List<Long> vz = new ArrayList<>(2);
      vz.add(this.lgz.next());
      vz.add(this.lgz.next());
      vz.sort((x, y) -> Long.compare(x.longValue(), y.longValue()));

      final Vector3L lo = Vector3L.of(
        vx.get(0).longValue(),
        vy.get(0).longValue(),
        vz.get(0).longValue());

      final Vector3L hi = Vector3L.of(
        vx.get(1).longValue(),
        vy.get(1).longValue(),
        vz.get(1).longValue());

      final BoundingVolumeL area = BoundingVolumeL.of(lo, hi);
      Postconditions.checkPostcondition(
        area, this.container.contains(area), a -> "Volume must be contained");
      return area;
    } catch (final IllegalArgumentException e) {
      e.printStackTrace();
      return this.next();
    }
  }
}
