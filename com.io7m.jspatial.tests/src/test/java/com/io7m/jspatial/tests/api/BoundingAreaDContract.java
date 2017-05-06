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

import com.io7m.jspatial.api.BoundingAreaDType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Contract for the {@link BoundingAreaDType}.
 */

public abstract class BoundingAreaDContract
{
  /**
   * Expected exception.
   */

  @Rule public final ExpectedException expected = ExpectedException.none();

  protected abstract <A extends BoundingAreaDType> Generator<A> generator();

  protected abstract BoundingAreaDType create(
    final Vector2D lower,
    final Vector2D upper);

  /**
   * Invariant violated with X value.
   */

  @Test
  public final void testInvariantFailedX()
  {
    this.expected.expect(IllegalArgumentException.class);
    this.create(
      Vector2D.of(2.0, 0.0),
      Vector2D.of(2.0, 2.0));
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
      Vector2D.of(0.0, 2.0),
      Vector2D.of(2.0, 2.0));
    Assert.fail();
  }

  /**
   * Test equals, hash code, etc
   */

  @Test
  public final void testEqualsHashcodeToString()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(2.0, 2.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(2.0, 2.0));
    final BoundingAreaDType a2 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(1.0, 2.0));
    final BoundingAreaDType a3 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(2.0, 1.0));

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
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(8.0, 8.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(-1.0, 1.0),
      Vector2D.of(7.0, 7.0));

    Assert.assertFalse(a0.contains(a1));
  }

  /**
   * Non-containment case.
   */

  @Test
  public final void testContainsNot1()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(8.0, 8.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(1.0, 1.0),
      Vector2D.of(7.0, 9.0));

    Assert.assertFalse(a0.contains(a1));
  }

  /**
   * Non-containment case.
   */

  @Test
  public final void testContainsNot2()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(8.0, 8.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(1.0, 1.0),
      Vector2D.of(9.0, 7.0));

    Assert.assertFalse(a0.contains(a1));
  }

  /**
   * Non-containment case.
   */

  @Test
  public final void testContainsNot3()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(8.0, 8.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(1.0, -1.0),
      Vector2D.of(7.0, 7.0));

    Assert.assertFalse(a0.contains(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotNW()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(-2.0, -2.0),
      Vector2D.of(0.0, 0.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(2.0, 2.0));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotN()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(0.0, -2.0),
      Vector2D.of(2.0, 0.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(2.0, 2.0));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotNE()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(2.0, 4.0),
      Vector2D.of(4.0, 6.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(2.0, 2.0));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotE()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(2.0, 0.0),
      Vector2D.of(4.0, 2.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(2.0, 2.0));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotSE()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(2.0, 2.0),
      Vector2D.of(4.0, 4.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(2.0, 2.0));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotS()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(0.0, 2.0),
      Vector2D.of(2.0, 4.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(2.0, 2.0));

    Assert.assertFalse(a0.overlaps(a1));
  }

  /**
   * Non-overlap case.
   */

  @Test
  public final void testOverlapsNotSW()
  {
    final BoundingAreaDType a0 = this.create(
      Vector2D.of(-2.0, 2.0),
      Vector2D.of(0.0, 4.0));
    final BoundingAreaDType a1 = this.create(
      Vector2D.of(0.0, 0.0),
      Vector2D.of(2.0, 2.0));

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
      new AbstractCharacteristic<BoundingAreaDType>()
      {
        @Override
        protected void doSpecify(final BoundingAreaDType area)
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
      new AbstractCharacteristic<BoundingAreaDType>()
      {
        @Override
        protected void doSpecify(final BoundingAreaDType area)
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
    final Generator<BoundingAreaDType> generator = this.generator();
    QuickCheck.forAllVerbose(
      generator,
      new AbstractCharacteristic<BoundingAreaDType>()
      {
        @Override
        protected void doSpecify(final BoundingAreaDType area)
          throws Throwable
        {
          final BoundingAreaDType next = generator.next();
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
    final Generator<BoundingAreaDType> gen = this.generator();
    QuickCheck.forAllVerbose(
      gen,
      new AbstractCharacteristic<BoundingAreaDType>()
      {
        @Override
        protected void doSpecify(final BoundingAreaDType area0)
          throws Throwable
        {
          if (area0.width() >= 4.0 && area0.height() >= 4.0) {
            final Vector2D lower0 = area0.lower();
            final Vector2D upper0 = area0.upper();

            final BoundingAreaDType area1 =
              BoundingAreaDContract.this.create(
                Vector2D.of(
                  lower0.x() + 1.0,
                  lower0.y() + 1.0),
                Vector2D.of(
                  upper0.x() - 1.0,
                  upper0.y() - 1.0));

            final BoundingAreaDType area2 =
              BoundingAreaDContract.this.create(
                Vector2D.of(
                  lower0.x() + 2.0,
                  lower0.y() + 2.0),
                Vector2D.of(
                  upper0.x() - 2.0,
                  upper0.y() - 2.0));

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
    final Generator<BoundingAreaDType> gen = this.generator();
    QuickCheck.forAllVerbose(
      gen,
      new AbstractCharacteristic<BoundingAreaDType>()
      {
        @Override
        protected void doSpecify(final BoundingAreaDType area0)
          throws Throwable
        {
          if (area0.width() >= 4.0 && area0.height() >= 4.0) {
            final Vector2D lower0 = area0.lower();
            final Vector2D upper0 = area0.upper();

            final BoundingAreaDType area1 =
              BoundingAreaDContract.this.create(
                Vector2D.of(
                  lower0.x() + 1.0,
                  lower0.y() + 1.0),
                Vector2D.of(
                  upper0.x() - 1.0,
                  upper0.y() - 1.0));

            Assert.assertTrue(area0.contains(area0));
            Assert.assertTrue(area0.contains(area1));
            Assert.assertTrue(area0.overlaps(area1));
            Assert.assertTrue(area1.overlaps(area0));
          }
        }
      });
  }
}
