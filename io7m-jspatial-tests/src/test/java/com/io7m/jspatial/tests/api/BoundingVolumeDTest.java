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

import com.io7m.jspatial.api.BoundingVolumeD;
import com.io7m.jspatial.api.BoundingVolumeDType;
import com.io7m.jtensors.VectorI3D;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.DoubleGenerator;
import org.junit.Assert;
import org.junit.Test;

public final class BoundingVolumeDTest extends BoundingVolumeDContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <A extends BoundingVolumeDType> Generator<A> generator()
  {
    return (Generator<A>) new BoundingVolumeDGenerator(new DoubleGenerator());
  }

  @Override
  protected BoundingVolumeDType create(
    final VectorI3D lower,
    final VectorI3D upper)
  {
    return BoundingVolumeD.of(lower, upper);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final BoundingVolumeD.Builder b0 = BoundingVolumeD.builder();

    final VectorI3D lower = new VectorI3D(0.0, 0.0, 0.0);
    final VectorI3D upper = new VectorI3D(10.0, 10.0, 10.0);
    b0.setLower(lower);
    b0.setUpper(upper);

    final BoundingVolumeD a0 = b0.build();
    Assert.assertEquals(lower, a0.lower());
    Assert.assertEquals(upper, a0.upper());

    final BoundingVolumeD.Builder b1 = BoundingVolumeD.builder();
    b1.from(a0);

    final BoundingVolumeD a1 = b1.build();
    Assert.assertEquals(a0, a1);

    final BoundingVolumeD a2 = BoundingVolumeD.copyOf(a1);
    Assert.assertEquals(a1, a2);

    final BoundingVolumeD a3 = BoundingVolumeD.copyOf(new BoundingVolumeDType()
    {
      @Override
      public VectorI3D lower()
      {
        return lower;
      }

      @Override
      public VectorI3D upper()
      {
        return upper;
      }
    });

    Assert.assertEquals(a1, a3);

    final VectorI3D lower_alt = new VectorI3D(1.0, 1.0, 1.0);
    final BoundingVolumeD a4 = a3.withLower(lower_alt);
    final VectorI3D upper_alt = new VectorI3D(9.0, 9.0, 9.0);
    final BoundingVolumeD a5 = a3.withUpper(upper_alt);

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
    final BoundingVolumeD.Builder b = BoundingVolumeD.builder();

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
    final BoundingVolumeD.Builder b = BoundingVolumeD.builder();
    b.setLower(new VectorI3D(0.0, 0.0, 0.0));

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
    final BoundingVolumeD.Builder b = BoundingVolumeD.builder();
    b.setUpper(new VectorI3D(0.0, 0.0, 0.0));

    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }
}
