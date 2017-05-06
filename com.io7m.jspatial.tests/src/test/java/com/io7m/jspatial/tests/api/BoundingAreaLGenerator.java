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

import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2L;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.LongGenerator;

import java.util.ArrayList;
import java.util.List;

public final class BoundingAreaLGenerator implements Generator<BoundingAreaL>
{
  private final LongGenerator lg;

  public BoundingAreaLGenerator()
  {
    this.lg = new LongGenerator(Long.MIN_VALUE >> 1L, Long.MAX_VALUE >> 1L);
  }

  @Override
  public BoundingAreaL next()
  {
    final List<Long> vs = new ArrayList<>(4);
    vs.add(this.lg.next());
    vs.add(this.lg.next());
    vs.add(this.lg.next());
    vs.add(this.lg.next());
    vs.sort((x, y) -> Long.compare(x.longValue(), y.longValue()));

    final Vector2L lo =
      Vector2L.of(vs.remove(0).longValue(), vs.remove(0).longValue());
    final Vector2L hi =
      Vector2L.of(vs.remove(0).longValue(), vs.remove(0).longValue());
    return BoundingAreaL.of(lo, hi);
  }
}
