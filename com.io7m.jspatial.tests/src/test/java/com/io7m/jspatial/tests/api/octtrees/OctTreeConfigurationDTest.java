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

import com.io7m.jspatial.api.BoundingVolumeD;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationD;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationDType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Contract completion for OctTreeConfigurationD.
 */

public final class OctTreeConfigurationDTest extends
  OctTreeConfigurationDContract
{
  @SuppressWarnings("unchecked")
  @Override
  protected <A extends OctTreeConfigurationDType> Generator<A> generator()
  {
    return (Generator<A>) new OctTreeConfigurationDGenerator();
  }

  @Override
  protected OctTreeConfigurationDType create(final BoundingVolumeD volume)
  {
    final OctTreeConfigurationD.Builder b = OctTreeConfigurationD.builder();
    b.setVolume(volume);
    return b.build();
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilder()
  {
    final Vector3D lower0 = Vector3D.of(0.0, 0.0, 0.0);
    final Vector3D upper0 = Vector3D.of(100.0, 100.0, 100.0);
    final BoundingVolumeD volume0 = BoundingVolumeD.of(lower0, upper0);
    final OctTreeConfigurationD.Builder b0 = OctTreeConfigurationD.builder();
    b0.setVolume(volume0);

    final OctTreeConfigurationD qc0 = b0.build();
    Assert.assertEquals(volume0, qc0.volume());
    Assert.assertEquals(2.0, qc0.minimumOctantWidth(), 0.0);
    Assert.assertEquals(2.0, qc0.minimumOctantHeight(), 0.0);
    Assert.assertFalse(qc0.trimOnRemove());

    b0.setMinimumOctantHeight(3.0);
    final OctTreeConfigurationD qc1 = b0.build();
    Assert.assertEquals(volume0, qc1.volume());
    Assert.assertEquals(2.0, qc1.minimumOctantWidth(), 0.0);
    Assert.assertEquals(3.0, qc1.minimumOctantHeight(), 0.0);
    Assert.assertFalse(qc1.trimOnRemove());
    Assert.assertNotEquals(qc1, qc0);

    b0.setMinimumOctantWidth(4.0);
    final OctTreeConfigurationD qc2 = b0.build();
    Assert.assertEquals(volume0, qc2.volume());
    Assert.assertEquals(4.0, qc2.minimumOctantWidth(), 0.0);
    Assert.assertEquals(3.0, qc2.minimumOctantHeight(), 0.0);
    Assert.assertFalse(qc2.trimOnRemove());
    Assert.assertNotEquals(qc2, qc0);
    Assert.assertNotEquals(qc2, qc1);

    final Vector3D lower1 = Vector3D.of(1.0, 1.0, 1.0);
    final Vector3D upper1 = Vector3D.of(99.0, 99.0, 99.0);
    final BoundingVolumeD volume1 = BoundingVolumeD.of(lower1, upper1);

    b0.setVolume(volume1);
    final OctTreeConfigurationD qc3 = b0.build();
    Assert.assertEquals(volume1, qc3.volume());
    Assert.assertEquals(4.0, qc3.minimumOctantWidth(), 0.0);
    Assert.assertEquals(3.0, qc3.minimumOctantHeight(), 0.0);
    Assert.assertFalse(qc3.trimOnRemove());
    Assert.assertNotEquals(qc3, qc0);
    Assert.assertNotEquals(qc3, qc1);
    Assert.assertNotEquals(qc3, qc2);

    final OctTreeConfigurationD.Builder b1 = OctTreeConfigurationD.builder();
    b1.from(qc3);

    final OctTreeConfigurationD qc4 = b1.build();
    Assert.assertEquals(qc4, qc3);
  }

  /**
   * Builder tests.
   */

  @Test
  public void testBuilderMissing()
  {
    final OctTreeConfigurationD.Builder b = OctTreeConfigurationD.builder();

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
    final Vector3D lower0 = Vector3D.of(0.0, 0.0, 0.0);
    final Vector3D upper0 = Vector3D.of(100.0, 100.0, 100.0);
    final BoundingVolumeD volume0 = BoundingVolumeD.of(lower0, upper0);
    final OctTreeConfigurationD.Builder b = OctTreeConfigurationD.builder();
    b.setVolume(volume0);

    final OctTreeConfigurationD qc0 = b.build();
    final OctTreeConfigurationD qc0_eq = b.build();

    final Vector3D lower1 = Vector3D.of(1.0, 1.0, 1.0);
    final Vector3D upper1 = Vector3D.of(99.0, 99.0, 99.0);
    final BoundingVolumeD volume1 = BoundingVolumeD.of(lower1, upper1);

    final OctTreeConfigurationD qc1 = qc0.withVolume(volume1);
    final OctTreeConfigurationD qc2 = qc0.withMinimumOctantWidth(4.0);
    final OctTreeConfigurationD qc3 = qc0.withMinimumOctantHeight(3.0);
    final OctTreeConfigurationD qc4 = qc0.withMinimumOctantDepth(5.0);
    final OctTreeConfigurationD qc5 = qc0.withTrimOnRemove(true);

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
    final Vector3D lower0 = Vector3D.of(0.0, 0.0, 0.0);
    final Vector3D upper0 = Vector3D.of(100.0, 100.0, 100.0);
    final BoundingVolumeD volume0 = BoundingVolumeD.of(lower0, upper0);
    final OctTreeConfigurationD.Builder b = OctTreeConfigurationD.builder();
    b.setVolume(volume0);

    final OctTreeConfigurationD qc0 = b.build();

    final Vector3D lower1 = Vector3D.of(1.0, 1.0, 1.0);
    final Vector3D upper1 = Vector3D.of(99.0, 99.0, 99.0);
    final BoundingVolumeD volume1 = BoundingVolumeD.of(lower1, upper1);

    final OctTreeConfigurationD qc1 =
      OctTreeConfigurationD.of(volume1, 2.0, 2.0, 2.0, false);
    final OctTreeConfigurationD qc2 =
      OctTreeConfigurationD.of(volume0, 4.0, 2.0, 2.0, false);
    final OctTreeConfigurationD qc3 =
      OctTreeConfigurationD.of(volume0, 2.0, 3.0, 2.0, false);
    final OctTreeConfigurationD qc4 =
      OctTreeConfigurationD.of(volume0, 2.0, 2.0, 3.0, false);
    final OctTreeConfigurationD qc5 =
      OctTreeConfigurationD.of(volume0, 2.0, 2.0, 2.0, true);

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
    final Vector3D lower0 = Vector3D.of(0.0, 0.0, 0.0);
    final Vector3D upper0 = Vector3D.of(100.0, 100.0, 100.0);
    final BoundingVolumeD volume0 = BoundingVolumeD.of(lower0, upper0);
    final OctTreeConfigurationD.Builder b = OctTreeConfigurationD.builder();
    b.setVolume(volume0);

    final OctTreeConfigurationD qc0 = b.build();
    final OctTreeConfigurationD qc1 = OctTreeConfigurationD.copyOf(qc0);

    final OctTreeConfigurationDType qc2 = OctTreeConfigurationD.copyOf(
      new OctTreeConfigurationDType()
      {
        @Override
        public BoundingVolumeD volume()
        {
          return qc0.volume();
        }

        @Override
        public double minimumOctantWidth()
        {
          return qc0.minimumOctantWidth();
        }

        @Override
        public double minimumOctantHeight()
        {
          return qc0.minimumOctantHeight();
        }

        @Override
        public double minimumOctantDepth()
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
