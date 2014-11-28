/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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

package com.io7m.jspatial.tests.octtrees;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.octtrees.OctTreeBasic;
import com.io7m.jspatial.octtrees.OctTreeBuilder;
import com.io7m.jspatial.octtrees.OctTreeBuilderType;
import com.io7m.jspatial.octtrees.OctTreeLimit;
import com.io7m.jspatial.octtrees.OctTreePrune;
import com.io7m.jspatial.octtrees.OctTreePruneLimit;
import com.io7m.jspatial.octtrees.OctTreeSDBasic;
import com.io7m.jspatial.octtrees.OctTreeSDLimit;
import com.io7m.jspatial.octtrees.OctTreeSDPrune;
import com.io7m.jspatial.octtrees.OctTreeSDPruneLimit;
import com.io7m.jspatial.octtrees.OctTreeType;
import com.io7m.jspatial.tests.Cuboid;

@SuppressWarnings("static-method") public final class OctTreeBuilderTest
{
  @Test public void testBasic()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.disableLimitedOctantSizes();
    b.disablePruning();
    b.setPosition3i(0, 4, 8);
    b.setSize3i(128, 64, 32);

    final OctTreeType<Cuboid> r = b.build();
    Assert.assertTrue(r instanceof OctTreeBasic);
    Assert.assertEquals(128, r.octTreeGetSizeX());
    Assert.assertEquals(64, r.octTreeGetSizeY());
    Assert.assertEquals(32, r.octTreeGetSizeZ());
    Assert.assertEquals(0, r.octTreeGetPositionX());
    Assert.assertEquals(4, r.octTreeGetPositionY());
    Assert.assertEquals(8, r.octTreeGetPositionZ());
  }

  @Test public void testLimit()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.enableLimitedOctantSizes(2, 4, 8);
    b.disablePruning();
    b.setPosition3i(0, 4, 8);
    b.setSize3i(128, 64, 32);

    final OctTreeType<Cuboid> r = b.build();
    Assert.assertTrue(r instanceof OctTreeLimit);
    Assert.assertEquals(128, r.octTreeGetSizeX());
    Assert.assertEquals(64, r.octTreeGetSizeY());
    Assert.assertEquals(32, r.octTreeGetSizeZ());
    Assert.assertEquals(0, r.octTreeGetPositionX());
    Assert.assertEquals(4, r.octTreeGetPositionY());
    Assert.assertEquals(8, r.octTreeGetPositionZ());
  }

  @Test public void testPrune()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.disableLimitedOctantSizes();
    b.enablePruning();
    b.setPosition3i(0, 4, 8);
    b.setSize3i(128, 64, 32);

    final OctTreeType<Cuboid> r = b.build();
    Assert.assertTrue(r instanceof OctTreePrune);
    Assert.assertEquals(128, r.octTreeGetSizeX());
    Assert.assertEquals(64, r.octTreeGetSizeY());
    Assert.assertEquals(32, r.octTreeGetSizeZ());
    Assert.assertEquals(0, r.octTreeGetPositionX());
    Assert.assertEquals(4, r.octTreeGetPositionY());
    Assert.assertEquals(8, r.octTreeGetPositionZ());
  }

  @Test public void testPruneLimit()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.enableLimitedOctantSizes(2, 4, 8);
    b.enablePruning();
    b.setPosition3i(0, 4, 8);
    b.setSize3i(128, 64, 32);

    final OctTreeType<Cuboid> r = b.build();
    Assert.assertTrue(r instanceof OctTreePruneLimit);
    Assert.assertEquals(128, r.octTreeGetSizeX());
    Assert.assertEquals(64, r.octTreeGetSizeY());
    Assert.assertEquals(32, r.octTreeGetSizeZ());
    Assert.assertEquals(0, r.octTreeGetPositionX());
    Assert.assertEquals(4, r.octTreeGetPositionY());
    Assert.assertEquals(8, r.octTreeGetPositionZ());
  }

  @Test public void testSDBasic()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.disableLimitedOctantSizes();
    b.disablePruning();
    b.setPosition3i(0, 4, 8);
    b.setSize3i(128, 64, 32);

    final OctTreeType<Cuboid> r = b.buildWithSD();
    Assert.assertTrue(r instanceof OctTreeSDBasic);
    Assert.assertEquals(128, r.octTreeGetSizeX());
    Assert.assertEquals(64, r.octTreeGetSizeY());
    Assert.assertEquals(32, r.octTreeGetSizeZ());
    Assert.assertEquals(0, r.octTreeGetPositionX());
    Assert.assertEquals(4, r.octTreeGetPositionY());
    Assert.assertEquals(8, r.octTreeGetPositionZ());
  }

  @Test public void testSDLimit()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.enableLimitedOctantSizes(2, 4, 8);
    b.disablePruning();
    b.setPosition3i(0, 4, 8);
    b.setSize3i(128, 64, 32);

    final OctTreeType<Cuboid> r = b.buildWithSD();
    Assert.assertTrue(r instanceof OctTreeSDLimit);
    Assert.assertEquals(128, r.octTreeGetSizeX());
    Assert.assertEquals(64, r.octTreeGetSizeY());
    Assert.assertEquals(32, r.octTreeGetSizeZ());
    Assert.assertEquals(0, r.octTreeGetPositionX());
    Assert.assertEquals(4, r.octTreeGetPositionY());
    Assert.assertEquals(8, r.octTreeGetPositionZ());
  }

  @Test public void testSDPrune()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.disableLimitedOctantSizes();
    b.enablePruning();
    b.setPosition3i(0, 4, 8);
    b.setSize3i(128, 64, 32);

    final OctTreeType<Cuboid> r = b.buildWithSD();
    Assert.assertTrue(r instanceof OctTreeSDPrune);
    Assert.assertEquals(128, r.octTreeGetSizeX());
    Assert.assertEquals(64, r.octTreeGetSizeY());
    Assert.assertEquals(32, r.octTreeGetSizeZ());
    Assert.assertEquals(0, r.octTreeGetPositionX());
    Assert.assertEquals(4, r.octTreeGetPositionY());
    Assert.assertEquals(8, r.octTreeGetPositionZ());
  }

  @Test public void testSDPruneLimit()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.enableLimitedOctantSizes(2, 4, 8);
    b.enablePruning();
    b.setPosition3i(0, 4, 8);
    b.setSize3i(128, 64, 32);

    final OctTreeType<Cuboid> r = b.buildWithSD();
    Assert.assertTrue(r instanceof OctTreeSDPruneLimit);
    Assert.assertEquals(128, r.octTreeGetSizeX());
    Assert.assertEquals(64, r.octTreeGetSizeY());
    Assert.assertEquals(32, r.octTreeGetSizeZ());
    Assert.assertEquals(0, r.octTreeGetPositionX());
    Assert.assertEquals(4, r.octTreeGetPositionY());
    Assert.assertEquals(8, r.octTreeGetPositionZ());
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testTooSmallLimitX()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.enableLimitedOctantSizes(0, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testTooSmallLimitY()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.enableLimitedOctantSizes(2, 0, 2);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testTooSmallLimitZ()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.enableLimitedOctantSizes(2, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testTooSmallX()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.setSize3i(0, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testTooSmallY()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.setSize3i(2, 0, 2);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testTooSmallZ()
  {
    final OctTreeBuilderType<Cuboid> b = OctTreeBuilder.newBuilder();
    b.setSize3i(2, 2, 0);
  }
}
