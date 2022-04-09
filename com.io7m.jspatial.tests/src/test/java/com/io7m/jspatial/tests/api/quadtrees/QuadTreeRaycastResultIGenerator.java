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

package com.io7m.jspatial.tests.api.quadtrees;

import com.io7m.jregions.generators.AreaIGenerator;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultI;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultIType;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.DoubleGenerator;
import net.java.quickcheck.generator.support.IntegerGenerator;

public final class QuadTreeRaycastResultIGenerator<A>
  implements Generator<QuadTreeRaycastResultIType<A>>
{
  private final DoubleGenerator dgen;
  private final AreaIGenerator agen;
  private final Generator<A> ogen;

  public QuadTreeRaycastResultIGenerator(
    final Generator<A> in_ogen)
  {
    this.dgen = new DoubleGenerator(0.0, Double.MAX_VALUE);
    this.agen = new AreaIGenerator(new IntegerGenerator());
    this.ogen = in_ogen;
  }

  @Override
  public QuadTreeRaycastResultIType<A> next()
  {
    return QuadTreeRaycastResultI.of(
      this.dgen.next().doubleValue(),
      this.agen.next(),
      this.ogen.next());
  }
}
