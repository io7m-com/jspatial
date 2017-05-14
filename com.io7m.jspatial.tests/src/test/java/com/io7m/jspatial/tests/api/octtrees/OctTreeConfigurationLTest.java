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
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationL;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationLType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3L;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contract completion for OctTreeConfigurationL.
 */

public final class OctTreeConfigurationLTest extends
  OctTreeConfigurationLContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <A extends OctTreeConfigurationLType> Generator<A> generator()
  {
    return (Generator<A>) new OctTreeConfigurationLGenerator();
  }

  @Override
  protected OctTreeConfigurationLType create(final VolumeL volume)
  {
    final OctTreeConfigurationL.Builder b = OctTreeConfigurationL.builder();
    b.setVolume(volume);
    return b.build();
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final VolumeL volume0 = VolumeL.of(0L, 100L, 0L, 100L, 0L, 100L);
    final OctTreeConfigurationL.Builder b0 = OctTreeConfigurationL.builder();
    b0.setVolume(volume0);

    final OctTreeConfigurationL qc0 = b0.build();
    Assert.assertEquals(volume0, qc0.volume());
    Assert.assertEquals(2L, qc0.minimumOctantWidth());
    Assert.assertEquals(2L, qc0.minimumOctantHeight());
    Assert.assertFalse(qc0.trimOnRemove());

    b0.setMinimumOctantHeight(3L);
    final OctTreeConfigurationL qc1 = b0.build();
    Assert.assertEquals(volume0, qc1.volume());
    Assert.assertEquals(2L, qc1.minimumOctantWidth());
    Assert.assertEquals(3L, qc1.minimumOctantHeight());
    Assert.assertFalse(qc1.trimOnRemove());
    Assert.assertNotEquals(qc1, qc0);

    b0.setMinimumOctantWidth(4L);
    final OctTreeConfigurationL qc2 = b0.build();
    Assert.assertEquals(volume0, qc2.volume());
    Assert.assertEquals(4L, qc2.minimumOctantWidth());
    Assert.assertEquals(3L, qc2.minimumOctantHeight());
    Assert.assertFalse(qc2.trimOnRemove());
    Assert.assertNotEquals(qc2, qc0);
    Assert.assertNotEquals(qc2, qc1);

    final VolumeL volume1 = VolumeL.of(1L, 99L, 1L, 99L, 1L, 99L);

    b0.setVolume(volume1);
    final OctTreeConfigurationL qc3 = b0.build();
    Assert.assertEquals(volume1, qc3.volume());
    Assert.assertEquals(4L, qc3.minimumOctantWidth());
    Assert.assertEquals(3L, qc3.minimumOctantHeight());
    Assert.assertFalse(qc3.trimOnRemove());
    Assert.assertNotEquals(qc3, qc0);
    Assert.assertNotEquals(qc3, qc1);
    Assert.assertNotEquals(qc3, qc2);

    final OctTreeConfigurationL.Builder b1 = OctTreeConfigurationL.builder();
    b1.from(qc3);

    final OctTreeConfigurationL qc4 = b1.build();
    Assert.assertEquals(qc4, qc3);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilderMissing()
  {
    final OctTreeConfigurationL.Builder b = OctTreeConfigurationL.builder();

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
    final VolumeL volume0 = VolumeL.of(0L, 100L, 0L, 100L, 0L, 100L);
    final OctTreeConfigurationL.Builder b = OctTreeConfigurationL.builder();
    b.setVolume(volume0);

    final OctTreeConfigurationL qc0 = b.build();
    final OctTreeConfigurationL qc0_eq = b.build();

    final VolumeL volume1 = VolumeL.of(1L, 99L, 1L, 99L, 1L, 99L);

    final OctTreeConfigurationL qc1 = qc0.withVolume(volume1);
    final OctTreeConfigurationL qc2 = qc0.withMinimumOctantWidth(4L);
    final OctTreeConfigurationL qc3 = qc0.withMinimumOctantHeight(3L);
    final OctTreeConfigurationL qc4 = qc0.withMinimumOctantDepth(5L);
    final OctTreeConfigurationL qc5 = qc0.withTrimOnRemove(true);

    Assert.assertEquals(qc0, qc0);
    Assert.assertEquals(qc0_eq, qc0);

    Assert.assertNotEquals(qc0, qc1);
    Assert.assertNotEquals(qc0, qc2);
    Assert.assertNotEquals(qc0, qc3);
    Assert.assertNotEquals(qc0, qc4);
    Assert.assertNotEquals(qc0, qc5);

    Assert.assertNotEquals(qc1, qc2);
    Assert.assertNotEquals(qc1, qc3);
    Assert.assertNotEquals(qc1, qc4);
    Assert.assertNotEquals(qc1, qc5);

    Assert.assertNotEquals(qc2, qc3);
    Assert.assertNotEquals(qc2, qc4);
    Assert.assertNotEquals(qc2, qc5);

    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc1.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc2.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc4.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc5.hashCode());

    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc2.hashCode());
    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc4.hashCode());
    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc5.hashCode());

    Assert.assertNotEquals((long) qc2.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc2.hashCode(), (long) qc4.hashCode());
    Assert.assertNotEquals((long) qc2.hashCode(), (long) qc5.hashCode());

    Assert.assertNotEquals(qc0.toString(), qc1.toString());
    Assert.assertNotEquals(qc0.toString(), qc2.toString());
    Assert.assertNotEquals(qc0.toString(), qc3.toString());
    Assert.assertNotEquals(qc0.toString(), qc4.toString());
    Assert.assertNotEquals(qc0.toString(), qc5.toString());

    Assert.assertNotEquals(qc1.toString(), qc2.toString());
    Assert.assertNotEquals(qc1.toString(), qc3.toString());
    Assert.assertNotEquals(qc1.toString(), qc4.toString());
    Assert.assertNotEquals(qc1.toString(), qc5.toString());

    Assert.assertNotEquals(qc2.toString(), qc3.toString());
    Assert.assertNotEquals(qc2.toString(), qc4.toString());
    Assert.assertNotEquals(qc2.toString(), qc5.toString());
  }

  /**
   * Of tests.
   */

  @Test
  public void testOf()
  {
    final VolumeL volume0 = VolumeL.of(0L, 100L, 0L, 100L, 0L, 100L);
    final OctTreeConfigurationL.Builder b = OctTreeConfigurationL.builder();
    b.setVolume(volume0);

    final OctTreeConfigurationL qc0 = b.build();

    final VolumeL volume1 = VolumeL.of(1L, 99L, 1L, 99L, 1L, 99L);

    final OctTreeConfigurationL qc1 =
      OctTreeConfigurationL.of(volume1, 2L, 2L, 2L, false);
    final OctTreeConfigurationL qc2 =
      OctTreeConfigurationL.of(volume0, 4L, 2L, 2L, false);
    final OctTreeConfigurationL qc3 =
      OctTreeConfigurationL.of(volume0, 2L, 3L, 2L, false);
    final OctTreeConfigurationL qc4 =
      OctTreeConfigurationL.of(volume0, 2L, 2L, 3L, false);
    final OctTreeConfigurationL qc5 =
      OctTreeConfigurationL.of(volume0, 2L, 2L, 2L, true);

    Assert.assertNotEquals(qc0, qc1);
    Assert.assertNotEquals(qc0, qc2);
    Assert.assertNotEquals(qc0, qc3);
    Assert.assertNotEquals(qc0, qc4);
    Assert.assertNotEquals(qc0, qc5);

    Assert.assertNotEquals(qc1, qc2);
    Assert.assertNotEquals(qc1, qc3);
    Assert.assertNotEquals(qc1, qc4);
    Assert.assertNotEquals(qc1, qc5);

    Assert.assertNotEquals(qc2, qc3);
    Assert.assertNotEquals(qc2, qc4);
    Assert.assertNotEquals(qc2, qc5);

    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc1.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc2.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc4.hashCode());
    Assert.assertNotEquals((long) qc0.hashCode(), (long) qc5.hashCode());

    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc2.hashCode());
    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc4.hashCode());
    Assert.assertNotEquals((long) qc1.hashCode(), (long) qc5.hashCode());

    Assert.assertNotEquals((long) qc2.hashCode(), (long) qc3.hashCode());
    Assert.assertNotEquals((long) qc2.hashCode(), (long) qc4.hashCode());
    Assert.assertNotEquals((long) qc2.hashCode(), (long) qc5.hashCode());

    Assert.assertNotEquals(qc0.toString(), qc1.toString());
    Assert.assertNotEquals(qc0.toString(), qc2.toString());
    Assert.assertNotEquals(qc0.toString(), qc3.toString());
    Assert.assertNotEquals(qc0.toString(), qc4.toString());
    Assert.assertNotEquals(qc0.toString(), qc5.toString());

    Assert.assertNotEquals(qc1.toString(), qc2.toString());
    Assert.assertNotEquals(qc1.toString(), qc3.toString());
    Assert.assertNotEquals(qc1.toString(), qc4.toString());
    Assert.assertNotEquals(qc1.toString(), qc5.toString());

    Assert.assertNotEquals(qc2.toString(), qc3.toString());
    Assert.assertNotEquals(qc2.toString(), qc4.toString());
    Assert.assertNotEquals(qc2.toString(), qc5.toString());
  }

  /**
   * copyOf tests.
   */

  @Test
  public void testCopyOf()
  {
    final Vector3L lower0 = Vector3L.of(0L, 0L, 0L);
    final Vector3L upper0 = Vector3L.of(100L, 100L, 100L);
    final VolumeL volume0 = VolumeL.of(0L, 100L, 0L, 100L, 0L, 100L);
    final OctTreeConfigurationL.Builder b = OctTreeConfigurationL.builder();
    b.setVolume(volume0);

    final OctTreeConfigurationL qc0 = b.build();
    final OctTreeConfigurationL qc1 = OctTreeConfigurationL.copyOf(qc0);

    final OctTreeConfigurationLType qc2 = OctTreeConfigurationL.copyOf(
      new OctTreeConfigurationLType()
      {
        @Override
        public VolumeL volume()
        {
          return qc0.volume();
        }

        @Override
        public long minimumOctantWidth()
        {
          return qc0.minimumOctantWidth();
        }

        @Override
        public long minimumOctantHeight()
        {
          return qc0.minimumOctantHeight();
        }

        @Override
        public long minimumOctantDepth()
        {
          return qc0.minimumOctantDepth();
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
