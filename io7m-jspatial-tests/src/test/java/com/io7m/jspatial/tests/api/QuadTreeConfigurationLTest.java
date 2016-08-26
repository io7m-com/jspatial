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

import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationLType;
import com.io7m.jtensors.VectorI2L;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contract completion for QuadTreeConfigurationL.
 */

public final class QuadTreeConfigurationLTest extends
  QuadTreeConfigurationLContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <A extends QuadTreeConfigurationLType> Generator<A> generator()
  {
    return (Generator<A>) new QuadTreeConfigurationLGenerator();
  }

  @Override
  protected QuadTreeConfigurationLType create(final BoundingAreaL area)
  {
    final QuadTreeConfigurationL.Builder b = QuadTreeConfigurationL.builder();
    b.setArea(area);
    return b.build();
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final VectorI2L lower0 = new VectorI2L(0L, 0L);
    final VectorI2L upper0 = new VectorI2L(100L, 100L);
    final BoundingAreaL area0 = BoundingAreaL.of(lower0, upper0);
    final QuadTreeConfigurationL.Builder b0 = QuadTreeConfigurationL.builder();
    b0.setArea(area0);

    final QuadTreeConfigurationL qc0 = b0.build();
    Assert.assertEquals(area0, qc0.area());
    Assert.assertEquals(2L, qc0.minimumQuadrantWidth());
    Assert.assertEquals(2L, qc0.minimumQuadrantHeight());
    Assert.assertFalse(qc0.trimOnRemove());

    b0.setMinimumQuadrantHeight(3L);
    final QuadTreeConfigurationL qc1 = b0.build();
    Assert.assertEquals(area0, qc1.area());
    Assert.assertEquals(2L, qc1.minimumQuadrantWidth());
    Assert.assertEquals(3L, qc1.minimumQuadrantHeight());
    Assert.assertFalse(qc1.trimOnRemove());
    Assert.assertNotEquals(qc1, qc0);

    b0.setMinimumQuadrantWidth(4L);
    final QuadTreeConfigurationL qc2 = b0.build();
    Assert.assertEquals(area0, qc2.area());
    Assert.assertEquals(4L, qc2.minimumQuadrantWidth());
    Assert.assertEquals(3L, qc2.minimumQuadrantHeight());
    Assert.assertFalse(qc2.trimOnRemove());
    Assert.assertNotEquals(qc2, qc0);
    Assert.assertNotEquals(qc2, qc1);

    final VectorI2L lower1 = new VectorI2L(1L, 1L);
    final VectorI2L upper1 = new VectorI2L(99L, 99L);
    final BoundingAreaL area1 = BoundingAreaL.of(lower1, upper1);

    b0.setArea(area1);
    final QuadTreeConfigurationL qc3 = b0.build();
    Assert.assertEquals(area1, qc3.area());
    Assert.assertEquals(4L, qc3.minimumQuadrantWidth());
    Assert.assertEquals(3L, qc3.minimumQuadrantHeight());
    Assert.assertFalse(qc3.trimOnRemove());
    Assert.assertNotEquals(qc3, qc0);
    Assert.assertNotEquals(qc3, qc1);
    Assert.assertNotEquals(qc3, qc2);

    final QuadTreeConfigurationL.Builder b1 = QuadTreeConfigurationL.builder();
    b1.from(qc3);

    final QuadTreeConfigurationL qc4 = b1.build();
    Assert.assertEquals(qc4, qc3);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilderMissing()
  {
    final QuadTreeConfigurationL.Builder b = QuadTreeConfigurationL.builder();

    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }

  /**
   * With tests.
   */

  @Test
  public void testWith()
  {
    final VectorI2L lower0 = new VectorI2L(0L, 0L);
    final VectorI2L upper0 = new VectorI2L(100L, 100L);
    final BoundingAreaL area0 = BoundingAreaL.of(lower0, upper0);
    final QuadTreeConfigurationL.Builder b = QuadTreeConfigurationL.builder();
    b.setArea(area0);

    final QuadTreeConfigurationL qc0 = b.build();
    final QuadTreeConfigurationL qc0_eq = b.build();

    final VectorI2L lower1 = new VectorI2L(1L, 1L);
    final VectorI2L upper1 = new VectorI2L(99L, 99L);
    final BoundingAreaL area1 = BoundingAreaL.of(lower1, upper1);

    final QuadTreeConfigurationL qc1 = qc0.withArea(area1);
    final QuadTreeConfigurationL qc2 = qc0.withMinimumQuadrantWidth(4L);
    final QuadTreeConfigurationL qc3 = qc0.withMinimumQuadrantHeight(3L);
    final QuadTreeConfigurationL qc4 = qc0.withTrimOnRemove(true);

    Assert.assertEquals(qc0, qc0);
    Assert.assertEquals(qc0_eq, qc0);

    Assert.assertNotEquals(qc0, qc1);
    Assert.assertNotEquals(qc0, qc2);
    Assert.assertNotEquals(qc0, qc3);
    Assert.assertNotEquals(qc0, qc4);

    Assert.assertNotEquals(qc1, qc2);
    Assert.assertNotEquals(qc1, qc3);
    Assert.assertNotEquals(qc1, qc4);

    Assert.assertNotEquals(qc2, qc3);
    Assert.assertNotEquals(qc2, qc4);

    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc1.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc2.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc4.hashCode());

    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc2.hashCode());
    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc4.hashCode());

    Assert.assertNotEquals((long) qc2.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc2.hashCode(), (long) qc4.hashCode());

    Assert.assertNotEquals(qc0.toString(), qc1.toString());
    Assert.assertNotEquals(qc0.toString(), qc2.toString());
    Assert.assertNotEquals(qc0.toString(), qc3.toString());
    Assert.assertNotEquals(qc0.toString(), qc4.toString());

    Assert.assertNotEquals(qc1.toString(), qc2.toString());
    Assert.assertNotEquals(qc1.toString(), qc3.toString());
    Assert.assertNotEquals(qc1.toString(), qc4.toString());

    Assert.assertNotEquals(qc2.toString(), qc3.toString());
    Assert.assertNotEquals(qc2.toString(), qc4.toString());
  }

  /**
   * Of tests.
   */

  @Test
  public void testOf()
  {
    final VectorI2L lower0 = new VectorI2L(0L, 0L);
    final VectorI2L upper0 = new VectorI2L(100L, 100L);
    final BoundingAreaL area0 = BoundingAreaL.of(lower0, upper0);
    final QuadTreeConfigurationL.Builder b = QuadTreeConfigurationL.builder();
    b.setArea(area0);

    final QuadTreeConfigurationL qc0 = b.build();

    final VectorI2L lower1 = new VectorI2L(1L, 1L);
    final VectorI2L upper1 = new VectorI2L(99L, 99L);
    final BoundingAreaL area1 = BoundingAreaL.of(lower1, upper1);

    final QuadTreeConfigurationL qc1 =
      QuadTreeConfigurationL.of(area1, 2L, 2L, false);
    final QuadTreeConfigurationL qc2 =
      QuadTreeConfigurationL.of(area0, 4L, 2L, false);
    final QuadTreeConfigurationL qc3 =
      QuadTreeConfigurationL.of(area0, 2L, 3L, false);
    final QuadTreeConfigurationL qc4 =
      QuadTreeConfigurationL.of(area0, 2L, 2L, true);

    Assert.assertNotEquals(qc0, qc1);
    Assert.assertNotEquals(qc0, qc2);
    Assert.assertNotEquals(qc0, qc3);
    Assert.assertNotEquals(qc0, qc4);

    Assert.assertNotEquals(qc1, qc2);
    Assert.assertNotEquals(qc1, qc3);
    Assert.assertNotEquals(qc1, qc4);

    Assert.assertNotEquals(qc2, qc3);
    Assert.assertNotEquals(qc2, qc4);

    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc1.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc2.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc4.hashCode());

    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc2.hashCode());
    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc4.hashCode());

    Assert.assertNotEquals((long) qc2.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc2.hashCode(), (long) qc4.hashCode());

    Assert.assertNotEquals(qc0.toString(), qc1.toString());
    Assert.assertNotEquals(qc0.toString(), qc2.toString());
    Assert.assertNotEquals(qc0.toString(), qc3.toString());
    Assert.assertNotEquals(qc0.toString(), qc4.toString());

    Assert.assertNotEquals(qc1.toString(), qc2.toString());
    Assert.assertNotEquals(qc1.toString(), qc3.toString());
    Assert.assertNotEquals(qc1.toString(), qc4.toString());

    Assert.assertNotEquals(qc2.toString(), qc3.toString());
    Assert.assertNotEquals(qc2.toString(), qc4.toString());
  }

  /**
   * copyOf tests.
   */

  @Test
  public void testCopyOf()
  {
    final VectorI2L lower0 = new VectorI2L(0L, 0L);
    final VectorI2L upper0 = new VectorI2L(100L, 100L);
    final BoundingAreaL area0 = BoundingAreaL.of(lower0, upper0);
    final QuadTreeConfigurationL.Builder b = QuadTreeConfigurationL.builder();
    b.setArea(area0);

    final QuadTreeConfigurationL qc0 = b.build();
    final QuadTreeConfigurationL qc1 = QuadTreeConfigurationL.copyOf(qc0);

    final QuadTreeConfigurationLType qc2 = QuadTreeConfigurationL.copyOf(
      new QuadTreeConfigurationLType()
      {
        @Override
        public BoundingAreaL area()
        {
          return qc0.area();
        }

        @Override
        public long minimumQuadrantWidth()
        {
          return qc0.minimumQuadrantWidth();
        }

        @Override
        public long minimumQuadrantHeight()
        {
          return qc0.minimumQuadrantHeight();
        }

        @Override
        public boolean trimOnRemove()
        {
          return qc0.trimOnRemove();
        }
      });

    Assert.assertEquals(qc0, qc1);
    Assert.assertEquals(qc0, qc2);
    Assert.assertEquals(qc1, qc2);
  }
}
