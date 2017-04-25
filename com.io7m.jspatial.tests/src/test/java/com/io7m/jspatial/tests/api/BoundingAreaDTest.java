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

import com.io7m.jspatial.api.BoundingAreaD;
import com.io7m.jspatial.api.BoundingAreaDType;
import com.io7m.jtensors.VectorI2D;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.DoubleGenerator;
import org.junit.Assert;
import org.junit.Test;

public final class BoundingAreaDTest extends BoundingAreaDContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <A extends BoundingAreaDType> Generator<A> generator()
  {
    return (Generator<A>) new BoundingAreaDGenerator(new DoubleGenerator());
  }

  @Override
  protected BoundingAreaDType create(
    final VectorI2D lower,
    final VectorI2D upper)
  {
    return BoundingAreaD.of(lower, upper);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final BoundingAreaD.Builder b0 = BoundingAreaD.builder();

    final VectorI2D lower = new VectorI2D(0.0, 0.0);
    final VectorI2D upper = new VectorI2D(10.0, 10.0);
    b0.setLower(lower);
    b0.setUpper(upper);

    final BoundingAreaD a0 = b0.build();
    Assert.assertEquals(lower, a0.lower());
    Assert.assertEquals(upper, a0.upper());

    final BoundingAreaD.Builder b1 = BoundingAreaD.builder();
    b1.from(a0);

    final BoundingAreaD a1 = b1.build();
    Assert.assertEquals(a0, a1);

    final BoundingAreaD a2 = BoundingAreaD.copyOf(a1);
    Assert.assertEquals(a1, a2);

    final BoundingAreaD a3 = BoundingAreaD.copyOf(new BoundingAreaDType()
    {
      @Override
      public VectorI2D lower()
      {
        return lower;
      }

      @Override
      public VectorI2D upper()
      {
        return upper;
      }
    });

    Assert.assertEquals(a1, a3);

    final VectorI2D lower_alt = new VectorI2D(1.0, 1.0);
    final BoundingAreaD a4 = a3.withLower(lower_alt);
    final VectorI2D upper_alt = new VectorI2D(9.0, 9.0);
    final BoundingAreaD a5 = a3.withUpper(upper_alt);

    Assert.assertEquals(lower_alt, a4.lower());
    Assert.assertEquals(upper_alt, a5.upper());
    Assert.assertEquals(a0, a0.withLower(a0.lower()));
    Assert.assertEquals(a0, a0.withUpper(a0.upper()));
  }

  /**
   * Builder incompleteness.
   */

  @Test
  public void testBuilderIncomplete0()
  {
    final BoundingAreaD.Builder b = BoundingAreaD.builder();

    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }

  /**
   * Builder incompleteness.
   */

  @Test
  public void testBuilderIncomplete1()
  {
    final BoundingAreaD.Builder b = BoundingAreaD.builder();
    b.setLower(new VectorI2D(0.0, 0.0));

    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }

  /**
   * Builder incompleteness.
   */

  @Test
  public void testBuilderIncomplete2()
  {
    final BoundingAreaD.Builder b = BoundingAreaD.builder();
    b.setUpper(new VectorI2D(0.0, 0.0));

    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }
}
