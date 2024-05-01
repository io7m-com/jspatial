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

package com.io7m.jspatial.tests.api.octtrees;

import com.io7m.jregions.core.unparameterized.volumes.VolumeI;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationI;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationIType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3I;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contract completion for OctTreeConfigurationI.
 */

public final class OctTreeConfigurationITest extends
  OctTreeConfigurationIContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <A extends OctTreeConfigurationIType> Generator<A> generator()
  {
    return (Generator<A>) new OctTreeConfigurationIGenerator();
  }

  @Override
  protected OctTreeConfigurationIType create(final VolumeI volume)
  {
    final OctTreeConfigurationI.Builder b = OctTreeConfigurationI.builder();
    b.setVolume(volume);
    return b.build();
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final VolumeI volume0 = VolumeI.of(0, 100, 0, 100, 0, 100);
    final OctTreeConfigurationI.Builder b0 = OctTreeConfigurationI.builder();
    b0.setVolume(volume0);

    final OctTreeConfigurationI qc0 = b0.build();
    Assert.assertEquals(volume0, qc0.volume());
    Assert.assertEquals(2, qc0.minimumOctantWidth());
    Assert.assertEquals(2, qc0.minimumOctantHeight());
    Assert.assertFalse(qc0.trimOnRemove());

    b0.setMinimumOctantHeight(3);
    final OctTreeConfigurationI qc1 = b0.build();
    Assert.assertEquals(volume0, qc1.volume());
    Assert.assertEquals(2, qc1.minimumOctantWidth());
    Assert.assertEquals(3, qc1.minimumOctantHeight());
    Assert.assertFalse(qc1.trimOnRemove());
    Assert.assertNotEquals(qc1, qc0);

    b0.setMinimumOctantWidth(4);
    final OctTreeConfigurationI qc2 = b0.build();
    Assert.assertEquals(volume0, qc2.volume());
    Assert.assertEquals(4, qc2.minimumOctantWidth());
    Assert.assertEquals(3, qc2.minimumOctantHeight());
    Assert.assertFalse(qc2.trimOnRemove());
    Assert.assertNotEquals(qc2, qc0);
    Assert.assertNotEquals(qc2, qc1);

    final VolumeI volume1 = VolumeI.of(1, 99, 1, 99, 1, 99);

    b0.setVolume(volume1);
    final OctTreeConfigurationI qc3 = b0.build();
    Assert.assertEquals(volume1, qc3.volume());
    Assert.assertEquals(4, qc3.minimumOctantWidth());
    Assert.assertEquals(3, qc3.minimumOctantHeight());
    Assert.assertFalse(qc3.trimOnRemove());
    Assert.assertNotEquals(qc3, qc0);
    Assert.assertNotEquals(qc3, qc1);
    Assert.assertNotEquals(qc3, qc2);

    final OctTreeConfigurationI.Builder b1 = OctTreeConfigurationI.builder();
    b1.from(qc3);

    final OctTreeConfigurationI qc4 = b1.build();
    Assert.assertEquals(qc4, qc3);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilderMissing()
  {
    final OctTreeConfigurationI.Builder b = OctTreeConfigurationI.builder();

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
    final VolumeI volume0 = VolumeI.of(0, 100, 0, 100, 0, 100);
    final OctTreeConfigurationI.Builder b = OctTreeConfigurationI.builder();
    b.setVolume(volume0);

    final OctTreeConfigurationI qc0 = b.build();
    final OctTreeConfigurationI qc0_eq = b.build();

    final VolumeI volume1 = VolumeI.of(1, 99, 1, 99, 1, 99);

    final OctTreeConfigurationI qc1 = qc0.withVolume(volume1);
    final OctTreeConfigurationI qc2 = qc0.withMinimumOctantWidth(4);
    final OctTreeConfigurationI qc3 = qc0.withMinimumOctantHeight(3);
    final OctTreeConfigurationI qc4 = qc0.withMinimumOctantDepth(5);
    final OctTreeConfigurationI qc5 = qc0.withTrimOnRemove(true);

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
    final VolumeI volume0 = VolumeI.of(0, 100, 0, 100, 0, 100);
    final OctTreeConfigurationI.Builder b = OctTreeConfigurationI.builder();
    b.setVolume(volume0);

    final OctTreeConfigurationI qc0 = b.build();

    final VolumeI volume1 = VolumeI.of(1, 99, 1, 99, 1, 99);

    final OctTreeConfigurationI qc1 =
      OctTreeConfigurationI.of(volume1, 2, 2, 2, false);
    final OctTreeConfigurationI qc2 =
      OctTreeConfigurationI.of(volume0, 4, 2, 2, false);
    final OctTreeConfigurationI qc3 =
      OctTreeConfigurationI.of(volume0, 2, 3, 2, false);
    final OctTreeConfigurationI qc4 =
      OctTreeConfigurationI.of(volume0, 2, 2, 3, false);
    final OctTreeConfigurationI qc5 =
      OctTreeConfigurationI.of(volume0, 2, 2, 2, true);

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
    final Vector3I lower0 = Vector3I.of(0, 0, 0);
    final Vector3I upper0 = Vector3I.of(100, 100, 100);
    final VolumeI volume0 = VolumeI.of(0, 100, 0, 100, 0, 100);
    final OctTreeConfigurationI.Builder b = OctTreeConfigurationI.builder();
    b.setVolume(volume0);

    final OctTreeConfigurationI qc0 = b.build();
    final OctTreeConfigurationI qc1 = OctTreeConfigurationI.copyOf(qc0);

    final OctTreeConfigurationIType qc2 = OctTreeConfigurationI.copyOf(
      new OctTreeConfigurationIType()
      {
        @Override
        public VolumeI volume()
        {
          return qc0.volume();
        }

        @Override
        public int minimumOctantWidth()
        {
          return qc0.minimumOctantWidth();
        }

        @Override
        public int minimumOctantHeight()
        {
          return qc0.minimumOctantHeight();
        }

        @Override
        public int minimumOctantDepth()
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
