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
import com.io7m.jspatial.api.BoundingAreaD;
import com.io7m.jtensors.VectorI2D;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.DoubleGenerator;

import java.util.ArrayList;
import java.util.List;

public final class BoundingAreaDContainedGenerator implements Generator<BoundingAreaD>
{
  private final DoubleGenerator lgx;
  private final DoubleGenerator lgy;
  private final BoundingAreaD container;

  public BoundingAreaDContainedGenerator(
    final BoundingAreaD container)
  {
    this.lgx =
      new DoubleGenerator(container.lower().getXD(), container.upper().getXD());
    this.lgy =
      new DoubleGenerator(container.lower().getYD(), container.upper().getYD());
    this.container = container;
  }

  @Override
  public BoundingAreaD next()
  {
    final List<Double> vx = new ArrayList<>(2);
    vx.add(this.lgx.next());
    vx.add(this.lgx.next());
    vx.sort((x, y) -> Double.compare(x.doubleValue(), y.doubleValue()));

    final List<Double> vy = new ArrayList<>(2);
    vy.add(this.lgy.next());
    vy.add(this.lgy.next());
    vy.sort((x, y) -> Double.compare(x.doubleValue(), y.doubleValue()));

    final VectorI2D lo =
      new VectorI2D(vx.get(0).doubleValue(), vy.get(0).doubleValue());
    final VectorI2D hi =
      new VectorI2D(vx.get(1).doubleValue(), vy.get(1).doubleValue());

    final BoundingAreaD area = BoundingAreaD.of(lo, hi);
    Postconditions.checkPostcondition(
      area, this.container.contains(area), a -> "Area must be contained");
    return area;
  }
}
