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

package com.io7m.jspatial.tests.api.octtrees;

import com.io7m.jspatial.api.octtrees.OctTreeConfigurationL;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationLType;
import com.io7m.jspatial.tests.api.BoundingVolumeLGenerator;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.support.LongGenerator;

/**
 * Generator for tree configurations.
 */

public final class OctTreeConfigurationLGenerator
  implements Generator<OctTreeConfigurationLType>
{
  private final BoundingVolumeLGenerator area;
  private final LongGenerator lgen;
  private final Generator<Boolean> bgen;

  public OctTreeConfigurationLGenerator()
  {
    this.area = new BoundingVolumeLGenerator();
    this.lgen = new LongGenerator(2L, Long.MAX_VALUE);
    this.bgen = PrimitiveGenerators.booleans();
  }

  @Override
  public OctTreeConfigurationLType next()
  {
    final OctTreeConfigurationL.Builder q = OctTreeConfigurationL.builder();
    q.setVolume(this.area.next());
    q.setMinimumOctantHeight(this.lgen.next().longValue());
    q.setMinimumOctantWidth(this.lgen.next().longValue());
    q.setMinimumOctantDepth(this.lgen.next().longValue());
    q.setTrimOnRemove(this.bgen.next().booleanValue());
    return q.build();
  }
}
