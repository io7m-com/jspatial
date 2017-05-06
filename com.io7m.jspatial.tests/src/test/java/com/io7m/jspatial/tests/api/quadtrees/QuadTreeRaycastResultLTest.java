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

package com.io7m.jspatial.tests.api.quadtrees;

import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultL;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultLType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2L;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contract completion for QuadTreeRaycastResultL.
 */

public final class QuadTreeRaycastResultLTest extends
  QuadTreeRaycastResultLContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <T, A extends QuadTreeRaycastResultLType<T>> Generator<A> generator()
  {
    return new QuadTreeRaycastResultLGenerator(PrimitiveGenerators.integers());
  }

  @Override
  protected <T> QuadTreeRaycastResultLType<T> create(
    final double distance,
    final BoundingAreaL area,
    final T object)
  {
    return QuadTreeRaycastResultL.of(distance, area, object);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final BoundingAreaL area0 = BoundingAreaL.of(
      Vector2L.of(0L, 0L),
      Vector2L.of(100L, 100L));
    final BoundingAreaL area1 = BoundingAreaL.of(
      Vector2L.of(1L, 1L),
      Vector2L.of(99L, 99L));

    final QuadTreeRaycastResultL.Builder<Integer> b =
      QuadTreeRaycastResultL.builder();

    b.setArea(area0);
    b.setDistance(23.0);
    b.setItem(Integer.valueOf(23));

    final QuadTreeRaycastResultL<Integer> r0_eq = b.build();
    final QuadTreeRaycastResultL<Integer> r0 = b.build();

    b.setDistance(24.0);
    final QuadTreeRaycastResultL<Integer> r1 = b.build();

    b.setItem(Integer.valueOf(25));
    final QuadTreeRaycastResultL<Integer> r2 = b.build();

    b.setArea(area1);
    final QuadTreeRaycastResultL<Integer> r3 = b.build();

    Assert.assertEquals(r0, r0);
    Assert.assertEquals(r0, r0_eq);

    Assert.assertNotEquals(r0, r1);
    Assert.assertNotEquals(r0, r2);
    Assert.assertNotEquals(r0, r3);

    Assert.assertNotEquals(r1, r2);
    Assert.assertNotEquals(r1, r3);

    Assert.assertNotEquals(r2, r3);
  }

  /**
   * Builder missing parameters.
   */

  @Test
  public void testBuilderMissing0()
  {
    final BoundingAreaL area0 = BoundingAreaL.of(
      Vector2L.of(0L, 0L),
      Vector2L.of(100L, 100L));

    final QuadTreeRaycastResultL.Builder<Integer> b =
      QuadTreeRaycastResultL.builder();

    b.setArea(area0);
    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }

  /**
   * Builder missing parameters.
   */

  @Test
  public void testBuilderMissing1()
  {
    final BoundingAreaL area0 = BoundingAreaL.of(
      Vector2L.of(0L, 0L),
      Vector2L.of(100L, 100L));

    final QuadTreeRaycastResultL.Builder<Integer> b =
      QuadTreeRaycastResultL.builder();

    b.setArea(area0);
    b.setDistance(1.0);
    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }

  /**
   * Builder missing parameters.
   */

  @Test
  public void testBuilderMissing2()
  {
    final QuadTreeRaycastResultL.Builder<Integer> b =
      QuadTreeRaycastResultL.builder();

    b.setDistance(1.0);
    b.setItem(Integer.valueOf(23));
    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }

  /**
   * Builder missing parameters.
   */

  @Test
  public void testBuilderMissing3()
  {
    final QuadTreeRaycastResultL.Builder<Integer> b =
      QuadTreeRaycastResultL.builder();

    b.setItem(Integer.valueOf(23));
    this.expected.expect(IllegalStateException.class);
    b.build();
    Assert.fail();
  }

  /**
   * from tests.
   */

  @Test
  public void testFrom()
  {
    final BoundingAreaL area0 = BoundingAreaL.of(
      Vector2L.of(0L, 0L),
      Vector2L.of(100L, 100L));

    final QuadTreeRaycastResultL.Builder<Integer> b =
      QuadTreeRaycastResultL.builder();

    b.setArea(area0);
    b.setDistance(23.0);
    b.setItem(Integer.valueOf(23));

    final QuadTreeRaycastResultL<Integer> r0 = b.build();
    final QuadTreeRaycastResultL<Integer> r1 = b.from(r0).build();

    Assert.assertEquals(r0, r1);
  }

  /**
   * copyOf tests.
   */

  @Test
  public void testCopyOf()
  {
    final BoundingAreaL area0 = BoundingAreaL.of(
      Vector2L.of(0L, 0L),
      Vector2L.of(100L, 100L));

    final QuadTreeRaycastResultL.Builder<Integer> b =
      QuadTreeRaycastResultL.builder();

    b.setArea(area0);
    b.setDistance(23.0);
    b.setItem(Integer.valueOf(23));

    final QuadTreeRaycastResultL<Integer> r0 = b.build();
    final QuadTreeRaycastResultL<Integer> r1 =
      QuadTreeRaycastResultL.copyOf(r0);
    final QuadTreeRaycastResultL<Integer> r2 =
      QuadTreeRaycastResultL.copyOf(new QuadTreeRaycastResultLType<Integer>()
      {
        @Override
        public double distance()
        {
          return r0.distance();
        }

        @Override
        public BoundingAreaL area()
        {
          return r0.area();
        }

        @Override
        public Integer item()
        {
          return r0.item();
        }
      });

    Assert.assertEquals(r0, r1);
    Assert.assertEquals(r0, r2);
  }

  /**
   * With tests.
   */

  @Test
  public void testWith()
  {
    final BoundingAreaL area0 = BoundingAreaL.of(
      Vector2L.of(0L, 0L),
      Vector2L.of(100L, 100L));
    final BoundingAreaL area1 = BoundingAreaL.of(
      Vector2L.of(1L, 1L),
      Vector2L.of(99L, 99L));

    final QuadTreeRaycastResultL.Builder<Integer> b =
      QuadTreeRaycastResultL.builder();

    b.setArea(area0);
    b.setDistance(23.0);
    b.setItem(Integer.valueOf(23));

    final QuadTreeRaycastResultL<Integer> r0 = b.build();
    final QuadTreeRaycastResultL<Integer> r1 = r0.withDistance(24.0);
    final QuadTreeRaycastResultL<Integer> r2 = r0.withItem(Integer.valueOf(25));
    final QuadTreeRaycastResultL<Integer> r3 = r0.withArea(area1);

    Assert.assertEquals(r0, r0);

    Assert.assertNotEquals(r0, r1);
    Assert.assertNotEquals(r0, r2);
    Assert.assertNotEquals(r0, r3);

    Assert.assertNotEquals(r1, r2);
    Assert.assertNotEquals(r1, r3);

    Assert.assertNotEquals(r2, r3);
  }
}
