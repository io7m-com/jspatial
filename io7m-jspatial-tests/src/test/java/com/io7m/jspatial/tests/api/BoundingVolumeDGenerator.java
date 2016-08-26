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

import com.io7m.jnull.NullCheck;
import com.io7m.jspatial.api.BoundingVolumeD;
import com.io7m.jtensors.VectorI3D;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.DoubleGenerator;

import java.util.ArrayList;
import java.util.List;

public final class BoundingVolumeDGenerator implements Generator<BoundingVolumeD>
{
  private final DoubleGenerator g;

  public BoundingVolumeDGenerator(final DoubleGenerator in_g)
  {
    this.g = NullCheck.notNull(in_g, "Double generator");
  }

  @Override
  public BoundingVolumeD next()
  {
    final List<Double> vs = new ArrayList<>(4);
    vs.add(this.g.next());
    vs.add(this.g.next());
    vs.add(this.g.next());
    vs.add(this.g.next());
    vs.add(this.g.next());
    vs.add(this.g.next());
    vs.sort((x, y) -> Double.compare(x.doubleValue(), y.doubleValue()));

    final VectorI3D lo = new VectorI3D(
      vs.remove(0).doubleValue(),
      vs.remove(0).doubleValue(),
      vs.remove(0).doubleValue());

    final VectorI3D hi = new VectorI3D(
      vs.remove(0).doubleValue(),
      vs.remove(0).doubleValue(),
      vs.remove(0).doubleValue());

    return BoundingVolumeD.of(lo, hi);
  }
}
