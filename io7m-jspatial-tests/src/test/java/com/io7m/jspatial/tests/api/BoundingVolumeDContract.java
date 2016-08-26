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
import com.io7m.jspatial.api.BoundingVolumeL;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3L;
import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Contract for the {@link BoundingVolumeDType}.
 */

public abstract class BoundingVolumeDContract
{
  /**
   * Expected exception.
   */

  @Rule public final ExpectedException expected = ExpectedException.none();

  protected abstract <A extends BoundingVolumeDType> Generator<A> generator();

  protected abstract BoundingVolumeDType create(
    final VectorI3D lower,
    final VectorI3D upper);

  /**
   * Invariant violated with X value.
   */

  @Test
  public final void testInvariantFailedX()
  {
    this.expected.expect(IllegalArgumentException.class);
    this.create(
      new VectorI3D(2.0, 0.0, 0.0),
      new VectorI3D(2.0, 2.0, 2.0));
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
      new VectorI3D(0.0, 2.0, 0.0),
      new VectorI3D(2.0, 2.0, 2.0));
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
      new VectorI3D(0.0, 0.0, 2.0),
      new VectorI3D(2.0, 2.0, 2.0));
    Assert.fail();
  }

  /**
   * Test equals, hash code, etc
   */

  @Test
  public final void testEqualsHashcodeToString()
  {
    final BoundingVolumeDType a0 = this.create(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(2.0, 2.0, 2.0));
    final BoundingVolumeDType a1 = this.create(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(2.0, 2.0, 2.0));
    final BoundingVolumeDType a2 = this.create(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(1.0, 2.0, 2.0));
    final BoundingVolumeDType a3 = this.create(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(2.0, 1.0, 2.0));
    final BoundingVolumeDType a4 = this.create(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(2.0, 2.0, 1.0));

    Assert.assertEquals(a0, a0);
    Assert.assertEquals(a1, a0);
    Assert.assertEquals(a0, a1);
    Assert.assertNotEquals(a0, a2);
    Assert.assertNotEquals(a0, a3);
    Assert.assertNotEquals(a0, a4);
    Assert.assertNotEquals(a0, null);
    Assert.assertNotEquals(a0, Integer.valueOf(23));

    Assert.assertEquals((long) a0.hashCode(), (long) a0.hashCode());
    Assert.assertEquals(a0.toString(), a0.toString());
    Assert.assertEquals(a1.toString(), a0.toString());
    Assert.assertNotEquals(a0.toString(), a2.toString());
    Assert.assertNotEquals(a0.toString(), a3.toString());
    Assert.assertNotEquals(a0.toString(), a4.toString());
  }

  /**
   * All non-containment cases.
   */

  @Test
  public final void testContainsNot()
  {
    for (double z = 0.0; z < 3.0; ++z) {
      for (double y = 0.0; y < 3.0; ++y) {
        for (double x = 0.0; x < 3.0; ++x) {

          if (x == y && y == z) {
            continue;
          }

          final BoundingVolumeD center = BoundingVolumeD.of(
            new VectorI3D(10.0, 10.0, 10.0),
            new VectorI3D(20.0, 20.0, 20.0));

          final double low_x = x * 10.0;
          final double low_y = y * 10.0;
          final double low_z = z * 10.0;

          final BoundingVolumeD overlap = BoundingVolumeD.of(
            new VectorI3D(low_x, low_y, low_z),
            new VectorI3D(
              low_x + 10.0,
              low_y + 10.0,
              low_z + 10.0));

          Assert.assertFalse(center.contains(overlap));
          Assert.assertFalse(overlap.contains(center));
        }
      }
    }
  }

  /**
   * Containment is reflexive.
   */

  @Test
  public final void testContainsReflexive()
  {
    QuickCheck.forAllVerbose(
      this.generator(),
      new AbstractCharacteristic<BoundingVolumeDType>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeDType volume)
          throws Throwable
        {
          Assert.assertTrue(volume.contains(volume));
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
      new AbstractCharacteristic<BoundingVolumeDType>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeDType volume)
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
    final Generator<BoundingVolumeDType> generator = this.generator();
    QuickCheck.forAllVerbose(
      generator,
      new AbstractCharacteristic<BoundingVolumeDType>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeDType volume)
          throws Throwable
        {
          final BoundingVolumeDType next = generator.next();
          if (next.overlaps(volume)) {
            Assert.assertTrue(volume.overlaps(next));
          } else {
            Assert.assertFalse(volume.overlaps(next));
          }
        }
      });
  }

  /**
   * All non-overlapping cases.
   */

  @Test
  public final void testOverlapsNot()
  {
    for (double z = 0.0; z < 3.0; ++z) {
      for (double y = 0.0; y < 3.0; ++y) {
        for (double x = 0.0; x < 3.0; ++x) {

          if (x == y && y == z) {
            continue;
          }

          final BoundingVolumeD center = BoundingVolumeD.of(
            new VectorI3D(10.0, 10.0, 10.0),
            new VectorI3D(20.0, 20.0, 20.0));

          final double low_x = x * 10.0;
          final double low_y = y * 10.0;
          final double low_z = z * 10.0;

          final BoundingVolumeD overlap = BoundingVolumeD.of(
            new VectorI3D(low_x, low_y, low_z),
            new VectorI3D(
              low_x + 10.0,
              low_y + 10.0,
              low_z + 10.0));

          Assert.assertFalse(center.overlaps(overlap));
          Assert.assertFalse(overlap.overlaps(center));
        }
      }
    }
  }

  /**
   * Containment is transitive.
   */

  @Test
  public final void testContainsTransitive()
  {
    final Generator<BoundingVolumeDType> gen = this.generator();
    QuickCheck.forAllVerbose(
      gen,
      new AbstractCharacteristic<BoundingVolumeDType>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeDType volume0)
          throws Throwable
        {
          if (volume0.width() >= 4.0 && volume0.height() >= 4.0 && volume0.depth() >= 4.0) {
            final VectorI3D lower0 = volume0.lower();
            final VectorI3D upper0 = volume0.upper();

            final BoundingVolumeDType volume1 =
              BoundingVolumeDContract.this.create(
                new VectorI3D(
                  lower0.getXD() + 1.0,
                  lower0.getYD() + 1.0,
                  lower0.getZD() + 1.0),
                new VectorI3D(
                  upper0.getXD() - 1.0,
                  upper0.getYD() - 1.0,
                  upper0.getZD() - 1.0));

            final BoundingVolumeDType volume2 =
              BoundingVolumeDContract.this.create(
                new VectorI3D(
                  lower0.getXD() + 2.0,
                  lower0.getYD() + 2.0,
                  lower0.getZD() + 2.0),
                new VectorI3D(
                  upper0.getXD() - 2.0,
                  upper0.getYD() - 2.0,
                  upper0.getZD() - 2.0));

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
    final Generator<BoundingVolumeDType> gen = this.generator();
    QuickCheck.forAllVerbose(
      gen,
      new AbstractCharacteristic<BoundingVolumeDType>()
      {
        @Override
        protected void doSpecify(final BoundingVolumeDType volume0)
          throws Throwable
        {
          if (volume0.width() >= 4.0 && volume0.height() >= 4.0) {
            final VectorI3D lower0 = volume0.lower();
            final VectorI3D upper0 = volume0.upper();

            final BoundingVolumeDType volume1 =
              BoundingVolumeDContract.this.create(
                new VectorI3D(
                  lower0.getXD() + 1.0,
                  lower0.getYD() + 1.0,
                  lower0.getZD() + 1.0),
                new VectorI3D(
                  upper0.getXD() - 1.0,
                  upper0.getYD() - 1.0,
                  upper0.getZD() - 1.0));

            Assert.assertTrue(volume0.contains(volume0));
            Assert.assertTrue(volume0.contains(volume1));
            Assert.assertTrue(volume0.overlaps(volume1));
            Assert.assertTrue(volume1.overlaps(volume0));
          }
        }
      });
  }
}
