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

import com.io7m.jspatial.api.BoundingVolumeL;
import com.io7m.jspatial.api.BoundingVolumeLType;
import com.io7m.jtensors.VectorI3L;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contract completion for {@link BoundingVolumeL}.
 */

public final class BoundingVolumeLTest extends BoundingVolumeLContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <A extends BoundingVolumeLType> Generator<A> generator()
  {
    return (Generator<A>) new BoundingVolumeLGenerator();
  }

  @Override
  protected BoundingVolumeLType create(
    final VectorI3L lower,
    final VectorI3L upper)
  {
    return BoundingVolumeL.of(lower, upper);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final BoundingVolumeL.Builder b0 = BoundingVolumeL.builder();

    final VectorI3L lower = new VectorI3L(0L, 0L, 0L);
    final VectorI3L upper = new VectorI3L(10L, 10L, 10L);
    b0.setLower(lower);
    b0.setUpper(upper);

    final BoundingVolumeL a0 = b0.build();
    Assert.assertEquals(lower, a0.lower());
    Assert.assertEquals(upper, a0.upper());

    final BoundingVolumeL.Builder b1 = BoundingVolumeL.builder();
    b1.from(a0);

    final BoundingVolumeL a1 = b1.build();
    Assert.assertEquals(a0, a1);

    final BoundingVolumeL a2 = BoundingVolumeL.copyOf(a1);
    Assert.assertEquals(a1, a2);

    final BoundingVolumeL a3 = BoundingVolumeL.copyOf(new BoundingVolumeLType()
    {
      @Override
      public VectorI3L lower()
      {
        return lower;
      }

      @Override
      public VectorI3L upper()
      {
        return upper;
      }
    });

    Assert.assertEquals(a1, a3);

    final VectorI3L lower_alt = new VectorI3L(1L, 1L, 1L);
    final BoundingVolumeL a4 = a3.withLower(lower_alt);
    final VectorI3L upper_alt = new VectorI3L(9L, 9L, 9L);
    final BoundingVolumeL a5 = a3.withUpper(upper_alt);

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
    final BoundingVolumeL.Builder b = BoundingVolumeL.builder();

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
    final BoundingVolumeL.Builder b = BoundingVolumeL.builder();
    b.setLower(new VectorI3L(0L, 0L, 0L));

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
    final BoundingVolumeL.Builder b = BoundingVolumeL.builder();
    b.setUpper(new VectorI3L(0L, 0L, 0L));

    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }
}
