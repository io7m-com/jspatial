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

import com.io7m.jregions.core.unparameterized.areas.AreaD;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationD;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationDType;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contract completion for QuadTreeConfigurationD.
 */

public final class QuadTreeConfigurationDTest extends
  QuadTreeConfigurationDContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <A extends QuadTreeConfigurationDType> Generator<A> generator()
  {
    return (Generator<A>) new QuadTreeConfigurationDGenerator();
  }

  @Override
  protected QuadTreeConfigurationDType create(final AreaD area)
  {
    final QuadTreeConfigurationD.Builder b = QuadTreeConfigurationD.builder();
    b.setArea(area);
    return b.build();
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final AreaD area0 = AreaD.of(0.0, 100.0, 0.0, 100.0);
    final QuadTreeConfigurationD.Builder b0 = QuadTreeConfigurationD.builder();
    b0.setArea(area0);

    final QuadTreeConfigurationD qc0 = b0.build();
    Assert.assertEquals(area0, qc0.area());
    Assert.assertEquals(2.0, qc0.minimumQuadrantWidth(), 0.0);
    Assert.assertEquals(2.0, qc0.minimumQuadrantHeight(), 0.0);
    Assert.assertFalse(qc0.trimOnRemove());

    b0.setMinimumQuadrantHeight(3.0);
    final QuadTreeConfigurationD qc1 = b0.build();
    Assert.assertEquals(area0, qc1.area());
    Assert.assertEquals(2.0, qc1.minimumQuadrantWidth(), 0.0);
    Assert.assertEquals(3.0, qc1.minimumQuadrantHeight(), 0.0);
    Assert.assertFalse(qc1.trimOnRemove());
    Assert.assertNotEquals(qc1, qc0);

    b0.setMinimumQuadrantWidth(4.0);
    final QuadTreeConfigurationD qc2 = b0.build();
    Assert.assertEquals(area0, qc2.area());
    Assert.assertEquals(4.0, qc2.minimumQuadrantWidth(), 0.0);
    Assert.assertEquals(3.0, qc2.minimumQuadrantHeight(), 0.0);
    Assert.assertFalse(qc2.trimOnRemove());
    Assert.assertNotEquals(qc2, qc0);
    Assert.assertNotEquals(qc2, qc1);

    final AreaD area1 = AreaD.of(1.0, 99.0, 1.0, 99.0);

    b0.setArea(area1);
    final QuadTreeConfigurationD qc3 = b0.build();
    Assert.assertEquals(area1, qc3.area());
    Assert.assertEquals(4.0, qc3.minimumQuadrantWidth(), 0.0);
    Assert.assertEquals(3.0, qc3.minimumQuadrantHeight(), 0.0);
    Assert.assertFalse(qc3.trimOnRemove());
    Assert.assertNotEquals(qc3, qc0);
    Assert.assertNotEquals(qc3, qc1);
    Assert.assertNotEquals(qc3, qc2);

    final QuadTreeConfigurationD.Builder b1 = QuadTreeConfigurationD.builder();
    b1.from(qc3);

    final QuadTreeConfigurationD qc4 = b1.build();
    Assert.assertEquals(qc4, qc3);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilderMissing()
  {
    final QuadTreeConfigurationD.Builder b = QuadTreeConfigurationD.builder();

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
    final AreaD area0 = AreaD.of(0.0, 100.0, 0.0, 100.0);
    final QuadTreeConfigurationD.Builder b = QuadTreeConfigurationD.builder();
    b.setArea(area0);

    final QuadTreeConfigurationD qc0 = b.build();
    final QuadTreeConfigurationD qc0_eq = b.build();

    final AreaD area1 = AreaD.of(1.0, 99.0, 1.0, 99.0);

    final QuadTreeConfigurationD qc1 = qc0.withArea(area1);
    final QuadTreeConfigurationD qc2 = qc0.withMinimumQuadrantWidth(4.0);
    final QuadTreeConfigurationD qc3 = qc0.withMinimumQuadrantHeight(3.0);
    final QuadTreeConfigurationD qc4 = qc0.withTrimOnRemove(true);

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
    final AreaD area0 = AreaD.of(0.0, 100.0, 0.0, 100.0);
    final QuadTreeConfigurationD.Builder b = QuadTreeConfigurationD.builder();
    b.setArea(area0);

    final QuadTreeConfigurationD qc0 = b.build();

    final AreaD area1 = AreaD.of(1.0, 99.0, 1.0, 99.0);

    final QuadTreeConfigurationD qc1 =
      QuadTreeConfigurationD.of(area1, 2.0, 2.0, false);
    final QuadTreeConfigurationD qc2 =
      QuadTreeConfigurationD.of(area0, 4.0, 2.0, false);
    final QuadTreeConfigurationD qc3 =
      QuadTreeConfigurationD.of(area0, 2.0, 3.0, false);
    final QuadTreeConfigurationD qc4 =
      QuadTreeConfigurationD.of(area0, 2.0, 2.0, true);

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
    final AreaD area0 = AreaD.of(0.0, 100.0, 0.0, 100.0);
    final QuadTreeConfigurationD.Builder b = QuadTreeConfigurationD.builder();
    b.setArea(area0);

    final QuadTreeConfigurationD qc0 = b.build();
    final QuadTreeConfigurationD qc1 = QuadTreeConfigurationD.copyOf(qc0);

    final QuadTreeConfigurationDType qc2 = QuadTreeConfigurationD.copyOf(
      new QuadTreeConfigurationDType()
      {
        @Override
        public AreaD area()
        {
          return qc0.area();
        }

        @Override
        public double minimumQuadrantWidth()
        {
          return qc0.minimumQuadrantWidth();
        }

        @Override
        public double minimumQuadrantHeight()
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
