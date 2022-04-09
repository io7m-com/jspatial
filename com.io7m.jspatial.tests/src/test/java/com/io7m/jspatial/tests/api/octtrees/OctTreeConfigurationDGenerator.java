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

package com.io7m.jspatial.tests.api.octtrees;

import com.io7m.jregions.generators.VolumeDGenerator;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationD;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationDType;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.support.DoubleGenerator;

/**
 * Generator for tree configurations.
 */

public final class OctTreeConfigurationDGenerator implements Generator<OctTreeConfigurationDType>
{
  private final VolumeDGenerator volume;
  private final DoubleGenerator dgen;
  private final Generator<Boolean> bgen;

  public OctTreeConfigurationDGenerator()
  {
    this.volume = new VolumeDGenerator(new DoubleGenerator());
    this.dgen = new DoubleGenerator(0.0, Double.MAX_VALUE);
    this.bgen = PrimitiveGenerators.booleans();
  }

  @Override
  public OctTreeConfigurationDType next()
  {
    final OctTreeConfigurationD.Builder q = OctTreeConfigurationD.builder();
    q.setVolume(this.volume.next());
    q.setMinimumOctantHeight(this.dgen.next().doubleValue());
    q.setMinimumOctantWidth(this.dgen.next().doubleValue());
    q.setMinimumOctantDepth(this.dgen.next().doubleValue());
    q.setTrimOnRemove(this.bgen.next().booleanValue());
    return q.build();
  }
}
