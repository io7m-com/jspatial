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
import com.io7m.jspatial.api.BoundingVolumeD;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.DoubleGenerator;

import java.util.ArrayList;
import java.util.List;

public final class BoundingVolumeDContainedGenerator implements Generator<BoundingVolumeD>
{
  private final DoubleGenerator lgx;
  private final DoubleGenerator lgy;
  private final DoubleGenerator lgz;
  private final BoundingVolumeD container;

  public BoundingVolumeDContainedGenerator(
    final BoundingVolumeD container)
  {
    this.lgx =
      new DoubleGenerator(container.lower().x(), container.upper().x());
    this.lgy =
      new DoubleGenerator(container.lower().y(), container.upper().y());
    this.lgz =
      new DoubleGenerator(container.lower().z(), container.upper().z());

    this.container = container;
  }

  @Override
  public BoundingVolumeD next()
  {
    final List<Double> vx = new ArrayList<>(2);
    vx.add(this.lgx.next());
    vx.add(this.lgx.next());
    vx.sort((x, y) -> Double.compare(x.doubleValue(), y.doubleValue()));

    final List<Double> vy = new ArrayList<>(2);
    vy.add(this.lgy.next());
    vy.add(this.lgy.next());
    vy.sort((x, y) -> Double.compare(x.doubleValue(), y.doubleValue()));

    final List<Double> vz = new ArrayList<>(2);
    vz.add(this.lgz.next());
    vz.add(this.lgz.next());
    vz.sort((x, y) -> Double.compare(x.doubleValue(), y.doubleValue()));

    final Vector3D lo = Vector3D.of(
      vx.get(0).doubleValue(),
      vy.get(0).doubleValue(),
      vz.get(0).doubleValue());

    final Vector3D hi = Vector3D.of(
      vx.get(1).doubleValue(),
      vy.get(1).doubleValue(),
      vz.get(1).doubleValue());

    final BoundingVolumeD area = BoundingVolumeD.of(lo, hi);
    Postconditions.checkPostcondition(
      area, this.container.contains(area), a -> "Volume must be contained");
    return area;
  }
}
