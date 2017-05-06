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
import com.io7m.jspatial.api.BoundingAreaLType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2L;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contract completion for {@link BoundingAreaL}.
 */

public final class BoundingAreaLTest extends BoundingAreaLContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <A extends BoundingAreaLType> Generator<A> generator()
  {
    return (Generator<A>) new BoundingAreaLGenerator();
  }

  @Override
  protected BoundingAreaLType create(
    final Vector2L lower,
    final Vector2L upper)
  {
    return BoundingAreaL.of(lower, upper);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final BoundingAreaL.Builder b0 = BoundingAreaL.builder();

    final Vector2L lower = Vector2L.of(0L, 0L);
    final Vector2L upper = Vector2L.of(10L, 10L);
    b0.setLower(lower);
    b0.setUpper(upper);

    final BoundingAreaL a0 = b0.build();
    Assert.assertEquals(lower, a0.lower());
    Assert.assertEquals(upper, a0.upper());

    final BoundingAreaL.Builder b1 = BoundingAreaL.builder();
    b1.from(a0);

    final BoundingAreaL a1 = b1.build();
    Assert.assertEquals(a0, a1);

    final BoundingAreaL a2 = BoundingAreaL.copyOf(a1);
    Assert.assertEquals(a1, a2);

    final BoundingAreaL a3 = BoundingAreaL.copyOf(new BoundingAreaLType()
    {
      @Override
      public Vector2L lower()
      {
        return lower;
      }

      @Override
      public Vector2L upper()
      {
        return upper;
      }
    });

    Assert.assertEquals(a1, a3);

    final Vector2L lower_alt = Vector2L.of(1L, 1L);
    final BoundingAreaL a4 = a3.withLower(lower_alt);
    final Vector2L upper_alt = Vector2L.of(9L, 9L);
    final BoundingAreaL a5 = a3.withUpper(upper_alt);

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
    final BoundingAreaL.Builder b = BoundingAreaL.builder();

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
    final BoundingAreaL.Builder b = BoundingAreaL.builder();
    b.setLower(Vector2L.of(0L, 0L));

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
    final BoundingAreaL.Builder b = BoundingAreaL.builder();
    b.setUpper(Vector2L.of(0L, 0L));

    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }
}
