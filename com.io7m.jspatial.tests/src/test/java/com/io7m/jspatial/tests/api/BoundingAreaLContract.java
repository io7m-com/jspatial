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

import com.io7m.jintegers.CheckedMath;
import com.io7m.jspatial.api.BoundingAreaLType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2L;
import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Contract for the {@link BoundingAreaLType}.
 */

public abstract class BoundingAreaLContract
{
  /**
   * Expected exception.
   */

  @Rule public final ExpectedException expected = ExpectedException.none();

  protected abstract <A extends BoundingAreaLType> Generator<A> generator();

  protected abstract BoundingAreaLType create(
    final Vector2L lower,
    final Vector2L upper);

  /**
   * A zero width area.
   */

  @Test
  public final void testWidthZero()
  {
    this.expected.expect(IllegalArgumentException.class);
    this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(0L, 1L));
    Assert.fail();
  }

  /**
   * A zero height area.
   */

  @Test
  public final void testHeightZero()
  {
    this.expected.expect(IllegalArgumentException.class);
    this.create(
      Vector2L.of(0L, 1L),
      Vector2L.of(0L, 0L));
    Assert.fail();
  }

  /**
   * Invariant violated with X value.
   */

  @Test
  public final void testInvariantFailedX()
  {
    this.expected.expect(IllegalArgumentException.class);
    this.create(
      Vector2L.of(2L, 0L),
      Vector2L.of(2L, 2L));
    Assert.fail();
  }

  /**
   * Invariant violated with Y value.
   */

  @Test
  public final void testInvariantFailedY()
  {
    this.expected.expect(IllegalArgumentException.class);
    this.create(
      Vector2L.of(0L, 2L),
      Vector2L.of(2L, 2L));
    Assert.fail();
  }

  /**
   * Test equals, hash code, etc
   */

  @Test
  public final void testEqualsHashcodeToString()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(2L, 2L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(2L, 2L));
    final BoundingAreaLType a2 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(1L, 2L));
    final BoundingAreaLType a3 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(2L, 1L));

    Assert.assertEquals(a0, a0);
    Assert.assertEquals(a1, a0);
    Assert.assertEquals(a0, a1);
    Assert.assertNotEquals(a0, a2);
    Assert.assertNotEquals(a0, a3);
    Assert.assertNotEquals(a0, null);
    Assert.assertNotEquals(a0, Integer.valueOf(23));

    Assert.assertEquals((long) a0.hashCode(), (long) a0.hashCode());
    Assert.assertEquals(a0.toString(), a0.toString());
    Assert.assertEquals(a1.toString(), a0.toString());
    Assert.assertNotEquals(a0.toString(), a2.toString());
    Assert.assertNotEquals(a0.toString(), a3.toString());
  }

  /**
   * Non-containment case.
   */

  @Test
  public final void testContainsNot0()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(8L, 8L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(-1L, 1L),
      Vector2L.of(7L, 7L));

    Assert.assertFalse(a0.contains(a1));
  }

  /**
   * Non-containment case.
   */

  @Test
  public final void testContainsNot1()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(8L, 8L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(1L, 1L),
      Vector2L.of(7L, 9L));

    Assert.assertFalse(a0.contains(a1));
  }

  /**
   * Non-containment case.
   */

  @Test
  public final void testContainsNot2()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(8L, 8L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(1L, 1L),
      Vector2L.of(9L, 7L));

    Assert.assertFalse(a0.contains(a1));
  }

  /**
   * Non-containment case.
   */

  @Test
  public final void testContainsNot3()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(8L, 8L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(1L, -1L),
      Vector2L.of(7L, 7L));

    Assert.assertFalse(a0.contains(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotNW()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(-2L, -2L),
      Vector2L.of(0L, 0L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(2L, 2L));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotN()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(0L, -2L),
      Vector2L.of(2L, 0L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(2L, 2L));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotNE()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(2L, 4L),
      Vector2L.of(4L, 6L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(2L, 2L));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotE()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(2L, 0L),
      Vector2L.of(4L, 2L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(2L, 2L));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotSE()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(2L, 2L),
      Vector2L.of(4L, 4L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(2L, 2L));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotS()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(0L, 2L),
      Vector2L.of(2L, 4L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(2L, 2L));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotSW()
  {
    final BoundingAreaLType a0 = this.create(
      Vector2L.of(-2L, 2L),
      Vector2L.of(0L, 4L));
    final BoundingAreaLType a1 = this.create(
      Vector2L.of(0L, 0L),
      Vector2L.of(2L, 2L));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Containment is reflexive.
   */

  @Test
  public final void testContainsReflexive()
  {
    QuickCheck.forAllVerbose(
      this.generator(),
      new AbstractCharacteristic<BoundingAreaLType>()
      {
        @Override
        protected void doSpecify(final BoundingAreaLType area)
          throws Throwable
        {
          Assert.assertTrue(area.contains(area));
        }
      });
  }

  /**
   * Overlapping is reflexive.
   */

  @Test
  public final void testOverlapsReflexive()
  {
    QuickCheck.forAllVerbose(
      this.generator(),
      new AbstractCharacteristic<BoundingAreaLType>()
      {
        @Override
        protected void doSpecify(final BoundingAreaLType area)
          throws Throwable
        {
          Assert.assertTrue(area.overlaps(area));
        }
      });
  }

  /**
   * Overlapping is symmetric.
   */

  @Test
  public final void testOverlapsSymmetric()
  {
    final Generator<BoundingAreaLType> generator = this.generator();
    QuickCheck.forAllVerbose(
      generator,
      new AbstractCharacteristic<BoundingAreaLType>()
      {
        @Override
        protected void doSpecify(final BoundingAreaLType area)
          throws Throwable
        {
          final BoundingAreaLType next = generator.next();
          if (next.overlaps(area)) {
            Assert.assertTrue(area.overlaps(next));
          } else {
            Assert.assertFalse(area.overlaps(next));
          }
        }
      });
  }

  /**
   * Containment is transitive.
   */

  @Test
  public final void testContainsTransitive()
  {
    final Generator<BoundingAreaLType> gen = this.generator();
    QuickCheck.forAllVerbose(
      gen,
      new AbstractCharacteristic<BoundingAreaLType>()
      {
        @Override
        protected void doSpecify(final BoundingAreaLType area0)
          throws Throwable
        {
          if (area0.width() >= 4L && area0.height() >= 4L) {
            final Vector2L lower0 = area0.lower();
            final Vector2L upper0 = area0.upper();

            final BoundingAreaLType area1 =
              BoundingAreaLContract.this.create(
                Vector2L.of(
                  CheckedMath.add(lower0.x(), 1L),
                  CheckedMath.add(lower0.y(), 1L)),
                Vector2L.of(
                  CheckedMath.subtract(upper0.x(), 1L),
                  CheckedMath.subtract(upper0.y(), 1L)));

            final BoundingAreaLType area2 =
              BoundingAreaLContract.this.create(
                Vector2L.of(
                  CheckedMath.add(lower0.x(), 2L),
                  CheckedMath.add(lower0.y(), 2L)),
                Vector2L.of(
                  CheckedMath.subtract(upper0.x(), 2L),
                  CheckedMath.subtract(upper0.y(), 2L)));

            Assert.assertTrue(area0.contains(area0));
            Assert.assertTrue(area0.contains(area1));
            Assert.assertTrue(area1.contains(area2));
            Assert.assertTrue(area0.contains(area2));
          }
        }
      });
  }

  /**
   * Containment implies overlapping.
   */

  @Test
  public final void testContainsImpliesOverlapping()
  {
    final Generator<BoundingAreaLType> gen = this.generator();
    QuickCheck.forAllVerbose(
      gen,
      new AbstractCharacteristic<BoundingAreaLType>()
      {
        @Override
        protected void doSpecify(final BoundingAreaLType area0)
          throws Throwable
        {
          if (area0.width() >= 4L && area0.height() >= 4L) {
            final Vector2L lower0 = area0.lower();
            final Vector2L upper0 = area0.upper();

            final BoundingAreaLType area1 =
              BoundingAreaLContract.this.create(
                Vector2L.of(
                  CheckedMath.add(lower0.x(), 1L),
                  CheckedMath.add(lower0.y(), 1L)),
                Vector2L.of(
                  CheckedMath.subtract(upper0.x(), 1L),
                  CheckedMath.subtract(upper0.y(), 1L)));

            Assert.assertTrue(area0.contains(area0));
            Assert.assertTrue(area0.contains(area1));
            Assert.assertTrue(area0.overlaps(area1));
            Assert.assertTrue(area1.overlaps(area0));
          }
        }
      });
  }

}
