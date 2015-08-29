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

package com.io7m.jspatial.tests.quadtrees;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.quadtrees.QuadTreeBasic;
import com.io7m.jspatial.quadtrees.QuadTreeBuilder;
import com.io7m.jspatial.quadtrees.QuadTreeBuilderType;
import com.io7m.jspatial.quadtrees.QuadTreeLimit;
import com.io7m.jspatial.quadtrees.QuadTreePrune;
import com.io7m.jspatial.quadtrees.QuadTreePruneLimit;
import com.io7m.jspatial.quadtrees.QuadTreeSDBasic;
import com.io7m.jspatial.quadtrees.QuadTreeSDLimit;
import com.io7m.jspatial.quadtrees.QuadTreeSDPrune;
import com.io7m.jspatial.quadtrees.QuadTreeSDPruneLimit;
import com.io7m.jspatial.quadtrees.QuadTreeType;
import com.io7m.jspatial.tests.Rectangle;

@SuppressWarnings("static-method") public final class QuadTreeBuilderTest
{
  @Test public void testBasic()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.disableLimitedQuadrantSizes();
    b.disablePruning();
    b.setPosition2i(0, 4);
    b.setSize2i(128, 64);

    final QuadTreeType<Rectangle> r = b.build();
    Assert.assertTrue(r instanceof QuadTreeBasic);
    Assert.assertEquals(128, r.quadTreeGetSizeX());
    Assert.assertEquals(64, r.quadTreeGetSizeY());
    Assert.assertEquals(0, r.quadTreeGetPositionX());
    Assert.assertEquals(4, r.quadTreeGetPositionY());
  }

  @Test public void testLimit()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.enableLimitedQuadrantSizes(32, 48);
    b.disablePruning();
    b.setPosition2i(0, 4);
    b.setSize2i(128, 64);

    final QuadTreeType<Rectangle> r = b.build();
    Assert.assertTrue(r instanceof QuadTreeLimit);
    Assert.assertEquals(128, r.quadTreeGetSizeX());
    Assert.assertEquals(64, r.quadTreeGetSizeY());
    Assert.assertEquals(0, r.quadTreeGetPositionX());
    Assert.assertEquals(4, r.quadTreeGetPositionY());
  }

  @Test public void testPrune()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.disableLimitedQuadrantSizes();
    b.enablePruning();
    b.setPosition2i(0, 4);
    b.setSize2i(128, 64);

    final QuadTreeType<Rectangle> r = b.build();
    Assert.assertTrue(r instanceof QuadTreePrune);
    Assert.assertEquals(128, r.quadTreeGetSizeX());
    Assert.assertEquals(64, r.quadTreeGetSizeY());
    Assert.assertEquals(0, r.quadTreeGetPositionX());
    Assert.assertEquals(4, r.quadTreeGetPositionY());
  }

  @Test public void testPruneLimit()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.enableLimitedQuadrantSizes(32, 48);
    b.enablePruning();
    b.setPosition2i(0, 4);
    b.setSize2i(128, 64);

    final QuadTreeType<Rectangle> r = b.build();
    Assert.assertTrue(r instanceof QuadTreePruneLimit);
    Assert.assertEquals(128, r.quadTreeGetSizeX());
    Assert.assertEquals(64, r.quadTreeGetSizeY());
    Assert.assertEquals(0, r.quadTreeGetPositionX());
    Assert.assertEquals(4, r.quadTreeGetPositionY());
  }

  @Test public void testSDBasic()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.disableLimitedQuadrantSizes();
    b.disablePruning();
    b.setPosition2i(0, 4);
    b.setSize2i(128, 64);

    final QuadTreeType<Rectangle> r = b.buildWithSD();
    Assert.assertTrue(r instanceof QuadTreeSDBasic);
    Assert.assertEquals(128, r.quadTreeGetSizeX());
    Assert.assertEquals(64, r.quadTreeGetSizeY());
    Assert.assertEquals(0, r.quadTreeGetPositionX());
    Assert.assertEquals(4, r.quadTreeGetPositionY());
  }

  @Test public void testSDLimit()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.enableLimitedQuadrantSizes(32, 48);
    b.disablePruning();
    b.setPosition2i(0, 4);
    b.setSize2i(128, 64);

    final QuadTreeType<Rectangle> r = b.buildWithSD();
    Assert.assertTrue(r instanceof QuadTreeSDLimit);
    Assert.assertEquals(128, r.quadTreeGetSizeX());
    Assert.assertEquals(64, r.quadTreeGetSizeY());
    Assert.assertEquals(0, r.quadTreeGetPositionX());
    Assert.assertEquals(4, r.quadTreeGetPositionY());
  }

  @Test public void testSDPrune()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.disableLimitedQuadrantSizes();
    b.enablePruning();
    b.setPosition2i(0, 4);
    b.setSize2i(128, 64);

    final QuadTreeType<Rectangle> r = b.buildWithSD();
    Assert.assertTrue(r instanceof QuadTreeSDPrune);
    Assert.assertEquals(128, r.quadTreeGetSizeX());
    Assert.assertEquals(64, r.quadTreeGetSizeY());
    Assert.assertEquals(0, r.quadTreeGetPositionX());
    Assert.assertEquals(4, r.quadTreeGetPositionY());
  }

  @Test public void testSDPruneLimit()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.enableLimitedQuadrantSizes(32, 48);
    b.enablePruning();
    b.setPosition2i(0, 4);
    b.setSize2i(128, 64);

    final QuadTreeType<Rectangle> r = b.buildWithSD();
    Assert.assertTrue(r instanceof QuadTreeSDPruneLimit);
    Assert.assertEquals(128, r.quadTreeGetSizeX());
    Assert.assertEquals(64, r.quadTreeGetSizeY());
    Assert.assertEquals(0, r.quadTreeGetPositionX());
    Assert.assertEquals(4, r.quadTreeGetPositionY());
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testTooSmallLimitX()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.enableLimitedQuadrantSizes(0, 2);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testTooSmallLimitY()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.enableLimitedQuadrantSizes(2, 0);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testTooSmallX()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.setSize2i(0, 2);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testTooSmallY()
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    b.setSize2i(2, 0);
  }
}
