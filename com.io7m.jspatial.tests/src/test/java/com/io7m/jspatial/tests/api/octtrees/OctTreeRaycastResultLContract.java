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

package com.io7m.jspatial.tests.api.octtrees;

import com.io7m.jregions.core.unparameterized.volumes.VolumeL;
import com.io7m.jspatial.api.octtrees.OctTreeRaycastResultLType;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Contract for the {@link OctTreeRaycastResultLType}.
 */

public abstract class OctTreeRaycastResultLContract
{
  /**
   * Expected exception.
   */

  @Rule public final ExpectedException expected = ExpectedException.none();

  protected abstract <T, A extends OctTreeRaycastResultLType<T>> Generator<A> generator();

  protected abstract <T> OctTreeRaycastResultLType<T> create(
    final double distance,
    final VolumeL volume,
    final T object);

  @Test
  public final void testIdentities()
  {
    final VolumeL volume0 = VolumeL.of(
      0L, 100L, 0L, 100L, 0L, 100L);
    final VolumeL volume1 = VolumeL.of(
      1L, 99L, 1L, 99L, 1L, 99L);

    final OctTreeRaycastResultLType<Integer> k0 =
      this.create(23.0, volume0, Integer.valueOf(23));
    final OctTreeRaycastResultLType<Integer> k1 =
      this.create(24.0, volume0, Integer.valueOf(23));
    final OctTreeRaycastResultLType<Integer> k2 =
      this.create(23.0, volume1, Integer.valueOf(23));
    final OctTreeRaycastResultLType<Integer> k3 =
      this.create(23.0, volume0, Integer.valueOf(24));
    final OctTreeRaycastResultLType<Integer> k4 =
      this.create(23.0, volume0, Integer.valueOf(23));

    Assert.assertEquals(23.0, k0.distance(), 0.0);
    Assert.assertEquals(volume0, k0.volume());
    Assert.assertEquals(Integer.valueOf(23), k0.item());

    Assert.assertEquals(24.0, k1.distance(), 0.0);
    Assert.assertEquals(volume0, k1.volume());
    Assert.assertEquals(Integer.valueOf(23), k1.item());

    Assert.assertEquals(23.0, k2.distance(), 0.0);
    Assert.assertEquals(volume1, k2.volume());
    Assert.assertEquals(Integer.valueOf(23), k2.item());

    Assert.assertEquals(23.0, k3.distance(), 0.0);
    Assert.assertEquals(volume0, k3.volume());
    Assert.assertEquals(Integer.valueOf(24), k3.item());

    Assert.assertEquals(k0, k0);
    Assert.assertEquals(k0, k4);
    Assert.assertNotEquals(k0, k1);
    Assert.assertNotEquals(k0, k2);
    Assert.assertNotEquals(k0, k3);

    Assert.assertEquals((long) k0.hashCode(), (long) k0.hashCode());
    Assert.assertEquals((long) k0.hashCode(), (long) k4.hashCode());
    Assert.assertNotEquals((long) k0.hashCode(), (long) k1.hashCode());
    Assert.assertNotEquals((long) k0.hashCode(), (long) k2.hashCode());
    Assert.assertNotEquals((long) k0.hashCode(), (long) k3.hashCode());

    Assert.assertEquals(k0.toString(), k0.toString());
    Assert.assertEquals(k0.toString(), k4.toString());
    Assert.assertNotEquals(k0.toString(), k1.toString());
    Assert.assertNotEquals(k0.toString(), k2.toString());
    Assert.assertNotEquals(k0.toString(), k3.toString());
  }
}
