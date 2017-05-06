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
import com.io7m.jspatial.api.BoundingVolumeL;
import com.io7m.jspatial.api.BoundingVolumeLType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3L;
import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Contract for the {@link BoundingVolumeLType}.
 */

public abstract class BoundingVolumeLContract
{
  /**
   * Expected exception.
   */

  @Rule public final ExpectedException expected = ExpectedException.none();

  protected abstract <A extends BoundingVolumeLType> Generator<A> generator();

  protected abstract BoundingVolumeLType create(
    final Vector3L lower,
    final Vector3L upper);

  /**
   * A zero width volume.
   */

  @Test
  public final void testWidthZero()
  {
    this.expected.expect(IllegalArgumentException.class);
    this.create(
      Vector3L.of(0L, 0L, 0L),
      Vector3L.of(0L, 1L, 1L));
    Assert.fail();
  }

  /**
   * A zero height volume.
   */

  @Test
  public final void testHeightZero()
  {
    this.expected.expect(IllegalArgumentException.class);
    this.create(
      Vector3L.of(0L, 0L, 0L),
      Vector3L.of(1L, 0L, 1L));
    Assert.fail();
  }

  /**
   * A zero depth volume.
   */

  @Test
  public final void testDepthZero()
  {
    this.expected.expect(IllegalArgumentException.class);
    this.create(
      Vector3L.of(0L, 0L, 0L),
      Vector3L.of(1L, 1L, 0L));
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
      Vector3L.of(2L, 0L, 0L),
      Vector3L.of(2L, 2L, 2L));
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
      Vector3L.of(0L, 2L, 0L),
      Vector3L.of(2L, 2L, 2L));
    Assert.fail();
  }

  /**
   * Invariant violated with Z value.
   */

  @Test
  public final void testInvariantFailedZ()
  {
    this.expected.expect(IllegalArgumentException.class);
    this.create(
      Vector3L.of(0L, 0L, 2L),
      Vector3L.of(2L, 2L, 2L));
    Assert.fail();
  }

  /**
   * Test equals, hash code, etc
   */

  @Test
  public final void testEqualsHashcodeToString()
  {
    final BoundingVolumeLType a0 = this.create(
      Vector3L.of(0L, 0L, 0L),
      Vector3L.of(2L, 2L, 2L));
    final BoundingVolumeLType a1 = this.create(
      Vector3L.of(0L, 0L, 0L),
      Vector3L.of(2L, 2L, 2L));
    final BoundingVolumeLType a2 = this.create(
      Vector3L.of(0L, 0L, 0L),
      Vector3L.of(1L, 2L, 2L));
    final BoundingVolumeLType a3 = this.create(
      Vector3L.of(0L, 0L, 0L),
      Vector3L.of(2L, 1L, 2L));

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
   * Containment is reflexive.
   */

  @Test
  public final void testContainsReflexive()
  {
    QuickCheck.forAllVerbose(
      this.generator(),
      new AbstractCharacteristic<BoundingVolumeLType>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeLType volume)
          throws Throwable
        {
          Assert.assertTrue(volume.contains(volume));
        }
      });
  }

  /**
   * All non-overlapping cases.
   */

  @Test
  public final void testOverlapsNot()
  {
    for (long z = 0L; z < 3L; ++z) {
      for (long y = 0L; y < 3L; ++y) {
        for (long x = 0L; x < 3L; ++x) {

          if (x == y && y == z) {
            continue;
          }

          final BoundingVolumeL center = BoundingVolumeL.of(
            Vector3L.of(10L, 10L, 10L),
            Vector3L.of(20L, 20L, 20L));

          final long low_x = x * 10L;
          final long low_y = y * 10L;
          final long low_z = z * 10L;

          final BoundingVolumeL overlap = BoundingVolumeL.of(
            Vector3L.of(low_x, low_y, low_z),
            Vector3L.of(low_x + 10L, low_y + 10L, low_z + 10L));

          Assert.assertFalse(center.overlaps(overlap));
          Assert.assertFalse(overlap.overlaps(center));
        }
      }
    }
  }

  /**
   * All non-containment cases.
   */

  @Test
  public final void testContainsNot()
  {
    for (long z = 0L; z < 3L; ++z) {
      for (long y = 0L; y < 3L; ++y) {
        for (long x = 0L; x < 3L; ++x) {

          if (x == y && y == z) {
            continue;
          }

          final BoundingVolumeL center = BoundingVolumeL.of(
            Vector3L.of(10L, 10L, 10L),
            Vector3L.of(20L, 20L, 20L));

          final long low_x = x * 10L;
          final long low_y = y * 10L;
          final long low_z = z * 10L;

          final BoundingVolumeL overlap = BoundingVolumeL.of(
            Vector3L.of(low_x, low_y, low_z),
            Vector3L.of(low_x + 10L, low_y + 10L, low_z + 10L));

          Assert.assertFalse(center.contains(overlap));
          Assert.assertFalse(overlap.contains(center));
        }
      }
    }
  }

  /**
   * Overlapping is reflexive.
   */

  @Test
  public final void testOverlapsReflexive()
  {
    QuickCheck.forAllVerbose(
      this.generator(),
      new AbstractCharacteristic<BoundingVolumeLType>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeLType volume)
          throws Throwable
        {
          Assert.assertTrue(volume.overlaps(volume));
        }
      });
  }

  /**
   * Overlapping is symmetric.
   */

  @Test
  public final void testOverlapsSymmetric()
  {
    final Generator<BoundingVolumeLType> generator = this.generator();
    QuickCheck.forAllVerbose(
      generator,
      new AbstractCharacteristic<BoundingVolumeLType>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeLType volume)
          throws Throwable
        {
          final BoundingVolumeLType next = generator.next();
          if (next.overlaps(volume)) {
            Assert.assertTrue(volume.overlaps(next));
          } else {
            Assert.assertFalse(volume.overlaps(next));
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
    final Generator<BoundingVolumeLType> gen = this.generator();
    QuickCheck.forAllVerbose(
      gen,
      new AbstractCharacteristic<BoundingVolumeLType>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeLType volume0)
          throws Throwable
        {
          if (volume0.width() >= 4L && volume0.height() >= 4L && volume0.depth() >= 4L) {
            final Vector3L lower0 = volume0.lower();
            final Vector3L upper0 = volume0.upper();

            final BoundingVolumeLType volume1 =
              BoundingVolumeLContract.this.create(
                Vector3L.of(
                  CheckedMath.add(lower0.x(), 1L),
                  CheckedMath.add(lower0.y(), 1L),
                  CheckedMath.add(lower0.z(), 1L)),
                Vector3L.of(
                  CheckedMath.subtract(upper0.x(), 1L),
                  CheckedMath.subtract(upper0.y(), 1L),
                  CheckedMath.subtract(upper0.z(), 1L)));

            final BoundingVolumeLType volume2 =
              BoundingVolumeLContract.this.create(
                Vector3L.of(
                  CheckedMath.add(lower0.x(), 2L),
                  CheckedMath.add(lower0.y(), 2L),
                  CheckedMath.add(lower0.z(), 2L)),
                Vector3L.of(
                  CheckedMath.subtract(upper0.x(), 2L),
                  CheckedMath.subtract(upper0.y(), 2L),
                  CheckedMath.subtract(upper0.z(), 2L)));

            Assert.assertTrue(volume0.contains(volume0));
            Assert.assertTrue(volume0.contains(volume1));
            Assert.assertTrue(volume1.contains(volume2));
            Assert.assertTrue(volume0.contains(volume2));
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
    final Generator<BoundingVolumeLType> gen = this.generator();
    QuickCheck.forAllVerbose(
      gen,
      new AbstractCharacteristic<BoundingVolumeLType>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeLType volume0)
          throws Throwable
        {
          if (volume0.width() >= 4L && volume0.height() >= 4L && volume0.depth() >= 4L) {
            final Vector3L lower0 = volume0.lower();
            final Vector3L upper0 = volume0.upper();

            final BoundingVolumeLType volume1 =
              BoundingVolumeLContract.this.create(
                Vector3L.of(
                  CheckedMath.add(lower0.x(), 1L),
                  CheckedMath.add(lower0.y(), 1L),
                  CheckedMath.add(lower0.z(), 1L)),
                Vector3L.of(
                  CheckedMath.subtract(upper0.x(), 1L),
                  CheckedMath.subtract(upper0.y(), 1L),
                  CheckedMath.subtract(upper0.z(), 1L)));

            Assert.assertTrue(volume0.contains(volume0));
            Assert.assertTrue(volume0.contains(volume1));
            Assert.assertTrue(volume0.overlaps(volume1));
            Assert.assertTrue(volume1.overlaps(volume0));
          }
        }
      });
  }

}
