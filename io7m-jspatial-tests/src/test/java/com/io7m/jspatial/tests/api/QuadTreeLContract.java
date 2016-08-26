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

import com.io7m.jfunctional.Unit;
import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jspatial.api.RayI2D;
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import com.io7m.jspatial.api.quadtrees.QuadTreeLType;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultL;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2L;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tree contract.
 */

public abstract class QuadTreeLContract
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  protected abstract <T> QuadTreeLType<T> create(QuadTreeConfigurationL config);

  /**
   * Simple identities.
   */

  @Test
  public final void testIdentities()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);
    Assert.assertTrue(tree.isEmpty());
    Assert.assertEquals(0L, tree.size());
    Assert.assertEquals(area, tree.bounds());

    Assert.assertEquals(tree, tree);
    Assert.assertNotEquals(tree, Integer.valueOf(0));
    Assert.assertNotEquals(tree, null);
    Assert.assertEquals((long) tree.hashCode(), (long) tree.hashCode());
  }

  /**
   * Inserting an object that is too large fails.
   */

  @Test
  public final void testInsertTooLarge()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(-100L, -100L),
      new VectorI2L(200L, 200L));
    Assert.assertFalse(tree.insert(item, item_area));
  }

  /**
   * Inserting an object into the X0Y0 quadrant works.
   */

  @Test
  public final void testInsertX0Y0()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(1L, 1L),
      new VectorI2L(2L, 2L));
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y0 quadrant works.
   */

  @Test
  public final void testInsertX1Y0()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(98L, 1L),
      new VectorI2L(99L, 2L));
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X0Y1 quadrant works.
   */

  @Test
  public final void testInsertX0Y1()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(1L, 98L),
      new VectorI2L(2L, 99L));
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y1 quadrant works.
   */

  @Test
  public final void testInsertX1Y1()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(98L, 98L),
      new VectorI2L(99L, 99L));
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the root quadrant works.
   */

  @Test
  public final void testInsertCentral()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(2L, 2L),
      new VectorI2L(98L, 98L));
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting and removing objects is correct.
   */

  @Test
  public final void testInsertRemove()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final BoundingAreaL item_area0 = BoundingAreaL.of(
      new VectorI2L(2L, 2L),
      new VectorI2L(98L, 98L));

    final BoundingAreaL item_area1 = BoundingAreaL.of(
      new VectorI2L(1L, 1L),
      new VectorI2L(2L, 2L));

    final BoundingAreaL item_area2 = BoundingAreaL.of(
      new VectorI2L(98L, 1L),
      new VectorI2L(99L, 2L));

    final BoundingAreaL item_area3 = BoundingAreaL.of(
      new VectorI2L(1L, 98L),
      new VectorI2L(2L, 99L));

    final BoundingAreaL item_area4 = BoundingAreaL.of(
      new VectorI2L(98L, 98L),
      new VectorI2L(99L, 99L));

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    Assert.assertTrue(tree.contains(item0));
    Assert.assertTrue(tree.contains(item1));
    Assert.assertTrue(tree.contains(item2));
    Assert.assertTrue(tree.contains(item3));
    Assert.assertTrue(tree.contains(item4));

    Assert.assertEquals(5L, tree.size());

    Assert.assertTrue(tree.remove(item0));
    Assert.assertTrue(tree.remove(item1));
    Assert.assertTrue(tree.remove(item2));
    Assert.assertTrue(tree.remove(item3));
    Assert.assertTrue(tree.remove(item4));

    Assert.assertEquals(0L, tree.size());

    Assert.assertFalse(tree.contains(item0));
    Assert.assertFalse(tree.contains(item1));
    Assert.assertFalse(tree.contains(item2));
    Assert.assertFalse(tree.contains(item3));
    Assert.assertFalse(tree.contains(item4));

    Assert.assertFalse(tree.remove(item0));
    Assert.assertFalse(tree.remove(item1));
    Assert.assertFalse(tree.remove(item2));
    Assert.assertFalse(tree.remove(item3));
    Assert.assertFalse(tree.remove(item4));
  }

  /**
   * Inserting and clearing works.
   */

  @Test
  public final void testInsertClear()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final BoundingAreaL item_area0 = BoundingAreaL.of(
      new VectorI2L(2L, 2L),
      new VectorI2L(98L, 98L));

    final BoundingAreaL item_area1 = BoundingAreaL.of(
      new VectorI2L(1L, 1L),
      new VectorI2L(2L, 2L));

    final BoundingAreaL item_area2 = BoundingAreaL.of(
      new VectorI2L(98L, 1L),
      new VectorI2L(99L, 2L));

    final BoundingAreaL item_area3 = BoundingAreaL.of(
      new VectorI2L(1L, 98L),
      new VectorI2L(2L, 99L));

    final BoundingAreaL item_area4 = BoundingAreaL.of(
      new VectorI2L(98L, 98L),
      new VectorI2L(99L, 99L));

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    tree.clear();

    Assert.assertEquals(0L, tree.size());
    Assert.assertFalse(tree.contains(item0));
    Assert.assertFalse(tree.contains(item1));
    Assert.assertFalse(tree.contains(item2));
    Assert.assertFalse(tree.contains(item3));
    Assert.assertFalse(tree.contains(item4));
  }

  /**
   * Mapping with the identity function works.
   */

  @Test
  public final void testMapIdentity()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final BoundingAreaL item_area0 = BoundingAreaL.of(
      new VectorI2L(2L, 2L),
      new VectorI2L(98L, 98L));

    final BoundingAreaL item_area1 = BoundingAreaL.of(
      new VectorI2L(1L, 1L),
      new VectorI2L(2L, 2L));

    final BoundingAreaL item_area2 = BoundingAreaL.of(
      new VectorI2L(98L, 1L),
      new VectorI2L(99L, 2L));

    final BoundingAreaL item_area3 = BoundingAreaL.of(
      new VectorI2L(1L, 98L),
      new VectorI2L(2L, 99L));

    final BoundingAreaL item_area4 = BoundingAreaL.of(
      new VectorI2L(98L, 98L),
      new VectorI2L(99L, 99L));

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    final QuadTreeLType<Object> tree_map = tree.map((x, ignored) -> x);
    Assert.assertEquals(tree, tree_map);
  }

  /**
   * The areaFor query is correct.
   */

  @Test
  public final void testAreaFor()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final BoundingAreaL item_area0 = BoundingAreaL.of(
      new VectorI2L(2L, 2L),
      new VectorI2L(98L, 98L));

    final BoundingAreaL item_area1 = BoundingAreaL.of(
      new VectorI2L(1L, 1L),
      new VectorI2L(2L, 2L));

    final BoundingAreaL item_area2 = BoundingAreaL.of(
      new VectorI2L(98L, 1L),
      new VectorI2L(99L, 2L));

    final BoundingAreaL item_area3 = BoundingAreaL.of(
      new VectorI2L(1L, 98L),
      new VectorI2L(2L, 99L));

    final BoundingAreaL item_area4 = BoundingAreaL.of(
      new VectorI2L(98L, 98L),
      new VectorI2L(99L, 99L));

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    Assert.assertEquals(item_area0, tree.areaFor(item0));
    Assert.assertEquals(item_area1, tree.areaFor(item1));
    Assert.assertEquals(item_area2, tree.areaFor(item2));
    Assert.assertEquals(item_area3, tree.areaFor(item3));
    Assert.assertEquals(item_area4, tree.areaFor(item4));
  }

  /**
   * Nonexistent items do not have areas.
   */

  @Test
  public final void testAreaForNonexistent()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);
    final Integer item0 = Integer.valueOf(0);

    this.expected.expect(NoSuchElementException.class);
    tree.areaFor(item0);
    Assert.fail();
  }

  /**
   * Querying contained objects in the X0Y0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y0()
  {
    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(1L, 1L),
      new VectorI2L(2L, 2L));
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(BoundingAreaL.of(
        new VectorI2L(97L, 97L),
        new VectorI2L(98L, 98L)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y0()
  {
    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(98L, 1L),
      new VectorI2L(99L, 2L));
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(BoundingAreaL.of(
        new VectorI2L(1L, 1L),
        new VectorI2L(2L, 2L)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y1()
  {
    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(1L, 98L),
      new VectorI2L(2L, 99L));
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(BoundingAreaL.of(
        new VectorI2L(1L, 1L),
        new VectorI2L(2L, 2L)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y1()
  {
    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(98L, 98L),
      new VectorI2L(99L, 99L));
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(BoundingAreaL.of(
        new VectorI2L(1L, 1L),
        new VectorI2L(2L, 2L)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y0()
  {
    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(1L, 1L),
      new VectorI2L(2L, 2L));
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(BoundingAreaL.of(
        new VectorI2L(97L, 97L),
        new VectorI2L(98L, 98L)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y0()
  {
    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(98L, 1L),
      new VectorI2L(99L, 2L));
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(BoundingAreaL.of(
        new VectorI2L(1L, 1L),
        new VectorI2L(2L, 2L)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y1()
  {
    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(98L, 98L),
      new VectorI2L(99L, 99L));
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(BoundingAreaL.of(
        new VectorI2L(1L, 1L),
        new VectorI2L(2L, 2L)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y0 quadrant works.
   */

  @Test
  public final void testOverlappingNot()
  {
    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(10L, 10L),
      new VectorI2L(90L, 90L));
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(BoundingAreaL.of(
        new VectorI2L(1L, 1L),
        new VectorI2L(9L, 9L)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y1()
  {
    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingAreaL item_area = BoundingAreaL.of(
      new VectorI2L(1L, 98L),
      new VectorI2L(2L, 99L));
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_area, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(BoundingAreaL.of(
        new VectorI2L(1L, 1L),
        new VectorI2L(2L, 2L)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Ray casting in the X0Y0 quadrant works.
   */

  @Test
  public final void testRaycastX0Y0()
  {
    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final BoundingAreaL item_area0 = BoundingAreaL.of(
      new VectorI2L(10L, 11L),
      new VectorI2L(20L, 21L));
    Assert.assertTrue(tree.insert(item0, item_area0));

    final Integer item1 = Integer.valueOf(1);
    final BoundingAreaL item_area1 = BoundingAreaL.of(
      new VectorI2L(15L, 16L),
      new VectorI2L(25L, 26L));
    Assert.assertTrue(tree.insert(item1, item_area1));

    final Integer item2 = Integer.valueOf(2);
    final BoundingAreaL item_area2 = BoundingAreaL.of(
      new VectorI2L(25L, 26L),
      new VectorI2L(35L, 36L));
    Assert.assertTrue(tree.insert(item2, item_area2));

    {
      final VectorI2D origin = new VectorI2D(0.0, 0.0);
      final VectorI2D direction = new VectorI2D(1.0, 1.0);
      final RayI2D ray = new RayI2D(origin, direction);
      final SortedSet<QuadTreeRaycastResultL<Object>> items = new TreeSet<>();
      tree.raycast(ray, items);

      Assert.assertEquals(3L, (long) items.size());
      final Iterator<QuadTreeRaycastResultL<Object>> iter = items.iterator();

      final QuadTreeRaycastResultL<Object> result0 = iter.next();
      Assert.assertEquals(item0, result0.item());
      Assert.assertEquals(item_area0, result0.area());

      final QuadTreeRaycastResultL<Object> result1 = iter.next();
      Assert.assertEquals(item1, result1.item());
      Assert.assertEquals(item_area1, result1.area());

      final QuadTreeRaycastResultL<Object> result2 = iter.next();
      Assert.assertEquals(item2, result2.item());
      Assert.assertEquals(item_area2, result2.area());
    }

    {
      final VectorI2D origin = new VectorI2D(0.0, 0.0);
      final VectorI2D direction = new VectorI2D(1.0, 0.0);
      final RayI2D ray = new RayI2D(origin, direction);
      final SortedSet<QuadTreeRaycastResultL<Object>> items = new TreeSet<>();
      tree.raycast(ray, items);

      Assert.assertEquals(0L, (long) items.size());
    }
  }

  /**
   * Trimming works.
   */

  @Test
  public final void testTrim()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    cb.setTrimOnRemove(false);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final BoundingAreaL item_area0 = BoundingAreaL.of(
      new VectorI2L(2L, 2L),
      new VectorI2L(98L, 98L));

    final BoundingAreaL item_area1 = BoundingAreaL.of(
      new VectorI2L(1L, 1L),
      new VectorI2L(2L, 2L));

    final BoundingAreaL item_area2 = BoundingAreaL.of(
      new VectorI2L(98L, 1L),
      new VectorI2L(99L, 2L));

    final BoundingAreaL item_area3 = BoundingAreaL.of(
      new VectorI2L(1L, 98L),
      new VectorI2L(2L, 99L));

    final BoundingAreaL item_area4 = BoundingAreaL.of(
      new VectorI2L(98L, 98L),
      new VectorI2L(99L, 99L));

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    tree.trim();

    final int count_0 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(73L, (long) count_0);

    Assert.assertTrue(tree.remove(item1));
    tree.trim();

    final int count_1 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(73L - 16L, (long) count_1);

    Assert.assertTrue(tree.remove(item2));
    tree.trim();

    final int count_2 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(73L - (16L + 16L), (long) count_2);

    Assert.assertTrue(tree.remove(item3));
    tree.trim();

    final int count_3 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(73L - (16L + 16L + 16L), (long) count_3);

    Assert.assertTrue(tree.remove(item4));
    tree.trim();

    final int count_4 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(1L, (long) count_4);

    Assert.assertTrue(tree.remove(item0));
    tree.trim();

    final int count_5 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(1L, (long) count_5);
  }

  /**
   * Trimming on removal works.
   */

  @Test
  public final void testTrimOnRemoval()
  {
    final BoundingAreaL area =
      BoundingAreaL.of(new VectorI2L(0L, 0L), new VectorI2L(100L, 100L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    cb.setTrimOnRemove(true);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final BoundingAreaL item_area0 = BoundingAreaL.of(
      new VectorI2L(2L, 2L),
      new VectorI2L(98L, 98L));

    final BoundingAreaL item_area1 = BoundingAreaL.of(
      new VectorI2L(1L, 1L),
      new VectorI2L(2L, 2L));

    final BoundingAreaL item_area2 = BoundingAreaL.of(
      new VectorI2L(98L, 1L),
      new VectorI2L(99L, 2L));

    final BoundingAreaL item_area3 = BoundingAreaL.of(
      new VectorI2L(1L, 98L),
      new VectorI2L(2L, 99L));

    final BoundingAreaL item_area4 = BoundingAreaL.of(
      new VectorI2L(98L, 98L),
      new VectorI2L(99L, 99L));

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    final int count_0 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(73L, (long) count_0);

    Assert.assertTrue(tree.remove(item1));

    final int count_1 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(73L - 16L, (long) count_1);

    Assert.assertTrue(tree.remove(item2));

    final int count_2 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(73L - (16L + 16L), (long) count_2);

    Assert.assertTrue(tree.remove(item3));

    final int count_3 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(73L - (16L + 16L + 16L), (long) count_3);

    Assert.assertTrue(tree.remove(item4));

    final int count_4 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(1L, (long) count_4);

    Assert.assertTrue(tree.remove(item0));

    final int count_5 = QuadTreeLContract.countQuadrants(tree);
    Assert.assertEquals(1L, (long) count_5);
  }

  private static int countQuadrants(final QuadTreeLType<Object> tree)
  {
    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(count, (context, quadrant, depth) -> {
      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });
    return count.get();
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversal()
  {
    final BoundingAreaL container = BoundingAreaL.of(
      new VectorI2L(-1000L, -1000L),
      new VectorI2L(1000L, 1000L));

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Integer> tree = this.create(c);

    final Generator<BoundingAreaL> gen =
      new BoundingAreaLContainedGenerator(container);

    final Map<Integer, BoundingAreaL> inserted = new HashMap<>(500);
    for (int index = 0; index < 500; ++index) {
      final Integer b_index = Integer.valueOf(index);
      final BoundingAreaL area = gen.next();
      Assert.assertTrue(tree.insert(b_index, area));
      inserted.put(b_index, area);
    }

    final Map<Integer, BoundingAreaL> found = new HashMap<>(500);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      Assert.assertTrue(container.contains(quadrant.area()));

      final Map<Integer, BoundingAreaL> objects = quadrant.objects();
      for (final Map.Entry<Integer, BoundingAreaL> e : objects.entrySet()) {
        Assert.assertFalse(found.containsKey(e.getKey()));
        found.put(e.getKey(), e.getValue());
      }
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(inserted, found);
  }
}
