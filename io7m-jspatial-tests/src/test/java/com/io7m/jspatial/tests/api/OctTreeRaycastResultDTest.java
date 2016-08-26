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
import com.io7m.jspatial.api.octtrees.OctTreeRaycastResultD;
import com.io7m.jspatial.api.octtrees.OctTreeRaycastResultDType;
import com.io7m.jtensors.VectorI3D;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contract completion for OctTreeRaycastResultD.
 */

public final class OctTreeRaycastResultDTest extends
  OctTreeRaycastResultDContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <T, A extends OctTreeRaycastResultDType<T>> Generator<A> generator()
  {
    return new OctTreeRaycastResultDGenerator(PrimitiveGenerators.integers());
  }

  @Override
  protected <T> OctTreeRaycastResultDType<T> create(
    final double distance,
    final BoundingVolumeD volume,
    final T object)
  {
    return OctTreeRaycastResultD.of(distance, volume, object);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final BoundingVolumeD volume0 = BoundingVolumeD.of(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(100.0, 100.0, 100.0));
    final BoundingVolumeD volume1 = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(99.0, 99.0, 99.0));

    final OctTreeRaycastResultD.Builder<Integer> b =
      OctTreeRaycastResultD.builder();

    b.setVolume(volume0);
    b.setDistance(23.0);
    b.setItem(Integer.valueOf(23));

    final OctTreeRaycastResultD<Integer> r0_eq = b.build();
    final OctTreeRaycastResultD<Integer> r0 = b.build();

    b.setDistance(24.0);
    final OctTreeRaycastResultD<Integer> r1 = b.build();

    b.setItem(Integer.valueOf(25));
    final OctTreeRaycastResultD<Integer> r2 = b.build();

    b.setVolume(volume1);
    final OctTreeRaycastResultD<Integer> r3 = b.build();

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
    final BoundingVolumeD volume0 = BoundingVolumeD.of(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeRaycastResultD.Builder<Integer> b =
      OctTreeRaycastResultD.builder();

    b.setVolume(volume0);
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
    final BoundingVolumeD volume0 = BoundingVolumeD.of(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeRaycastResultD.Builder<Integer> b =
      OctTreeRaycastResultD.builder();

    b.setVolume(volume0);
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
    final OctTreeRaycastResultD.Builder<Integer> b =
      OctTreeRaycastResultD.builder();

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
    final OctTreeRaycastResultD.Builder<Integer> b =
      OctTreeRaycastResultD.builder();

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
    final BoundingVolumeD volume0 = BoundingVolumeD.of(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeRaycastResultD.Builder<Integer> b =
      OctTreeRaycastResultD.builder();

    b.setVolume(volume0);
    b.setDistance(23.0);
    b.setItem(Integer.valueOf(23));

    final OctTreeRaycastResultD<Integer> r0 = b.build();
    final OctTreeRaycastResultD<Integer> r1 = b.from(r0).build();

    Assert.assertEquals(r0, r1);
  }

  /**
   * copyOf tests.
   */

  @Test
  public void testCopyOf()
  {
    final BoundingVolumeD volume0 = BoundingVolumeD.of(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeRaycastResultD.Builder<Integer> b =
      OctTreeRaycastResultD.builder();

    b.setVolume(volume0);
    b.setDistance(23.0);
    b.setItem(Integer.valueOf(23));

    final OctTreeRaycastResultD<Integer> r0 = b.build();
    final OctTreeRaycastResultD<Integer> r1 =
      OctTreeRaycastResultD.copyOf(r0);
    final OctTreeRaycastResultD<Integer> r2 =
      OctTreeRaycastResultD.copyOf(new OctTreeRaycastResultDType<Integer>()
      {
        @Override
        public double distance()
        {
          return r0.distance();
        }

        @Override
        public BoundingVolumeD volume()
        {
          return r0.volume();
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
    final BoundingVolumeD volume0 = BoundingVolumeD.of(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(100.0, 100.0, 100.0));
    final BoundingVolumeD volume1 = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(99.0, 99.0, 99.0));

    final OctTreeRaycastResultD.Builder<Integer> b =
      OctTreeRaycastResultD.builder();

    b.setVolume(volume0);
    b.setDistance(23.0);
    b.setItem(Integer.valueOf(23));

    final OctTreeRaycastResultD<Integer> r0 = b.build();
    final OctTreeRaycastResultD<Integer> r1 = r0.withDistance(24.0);
    final OctTreeRaycastResultD<Integer> r2 = r0.withItem(Integer.valueOf(25));
    final OctTreeRaycastResultD<Integer> r3 = r0.withVolume(volume1);

    Assert.assertEquals(r0, r0);

    Assert.assertNotEquals(r0, r1);
    Assert.assertNotEquals(r0, r2);
    Assert.assertNotEquals(r0, r3);

    Assert.assertNotEquals(r1, r2);
    Assert.assertNotEquals(r1, r3);

    Assert.assertNotEquals(r2, r3);
  }
}
