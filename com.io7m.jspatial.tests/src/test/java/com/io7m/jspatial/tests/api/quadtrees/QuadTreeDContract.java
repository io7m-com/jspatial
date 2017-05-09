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

package com.io7m.jspatial.tests.api.quadtrees;

import com.io7m.jfunctional.Unit;
import com.io7m.jregions.core.unparameterized.areas.AreaD;
import com.io7m.jregions.core.unparameterized.areas.AreasD;
import com.io7m.jspatial.api.Ray2D;
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationD;
import com.io7m.jspatial.api.quadtrees.QuadTreeDType;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultD;
import com.io7m.jspatial.tests.api.AreaDContainedGenerator;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors2D;
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

public abstract class QuadTreeDContract
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  private static int countQuadrants(final QuadTreeDType<?> tree)
  {
    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(count, (context, quadrant, depth) -> {
      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });
    return count.get();
  }

  protected abstract <T> QuadTreeDType<T> create(QuadTreeConfigurationD config);

  /**
   * Simple identities.
   */

  @Test
  public final void testIdentities()
  {
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);
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
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(-100.0, 200.0, -100.0, 200.0);
    Assert.assertFalse(tree.insert(item, item_area));
  }

  /**
   * Inserting a tiny object works.
   */

  @Test
  public final void testInsertTiny()
  {
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    cb.setMinimumQuadrantHeight(2.0);
    cb.setMinimumQuadrantWidth(2.0);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(1.0, 1.1, 1.0, 1.1);
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X0Y0 quadrant works.
   */

  @Test
  public final void testInsertX0Y0()
  {
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(1.0, 2.0, 1.0, 2.0);
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
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(98.0, 99.0, 1.0, 2.0);
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
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(1.0, 2.0, 98.0, 99.0);
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
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(98.0, 99.0, 98.0, 99.0);
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
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(2.0, 98.0, 2.0, 98.0);
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
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaD item_area0 = AreaD.of(2.0, 98.0, 2.0, 98.0);

    final AreaD item_area1 = AreaD.of(1.0, 2.0, 1.0, 2.0);

    final AreaD item_area2 = AreaD.of(98.0, 99.0, 1.0, 2.0);

    final AreaD item_area3 = AreaD.of(1.0, 2.0, 98.0, 99.0);

    final AreaD item_area4 = AreaD.of(98.0, 99.0, 98.0, 99.0);

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
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaD item_area0 = AreaD.of(2.0, 98.0, 2.0, 98.0);

    final AreaD item_area1 = AreaD.of(1.0, 2.0, 1.0, 2.0);

    final AreaD item_area2 = AreaD.of(98.0, 99.0, 1.0, 2.0);

    final AreaD item_area3 = AreaD.of(1.0, 2.0, 98.0, 99.0);

    final AreaD item_area4 = AreaD.of(98.0, 99.0, 98.0, 99.0);

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
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaD item_area0 = AreaD.of(2.0, 98.0, 2.0, 98.0);

    final AreaD item_area1 = AreaD.of(1.0, 2.0, 1.0, 2.0);

    final AreaD item_area2 = AreaD.of(98.0, 99.0, 1.0, 2.0);

    final AreaD item_area3 = AreaD.of(1.0, 2.0, 98.0, 99.0);

    final AreaD item_area4 = AreaD.of(98.0, 99.0, 98.0, 99.0);

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    final QuadTreeDType<Object> tree_map = tree.map((x, ignored) -> x);
    Assert.assertEquals(tree, tree_map);
  }

  /**
   * The areaFor query is correct.
   */

  @Test
  public final void testAreaFor()
  {
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaD item_area0 = AreaD.of(2.0, 98.0, 2.0, 98.0);

    final AreaD item_area1 = AreaD.of(1.0, 2.0, 1.0, 2.0);

    final AreaD item_area2 = AreaD.of(98.0, 99.0, 1.0, 2.0);

    final AreaD item_area3 = AreaD.of(1.0, 2.0, 98.0, 99.0);

    final AreaD item_area4 = AreaD.of(98.0, 99.0, 98.0, 99.0);

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
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);
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
    final AreaD area = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(1.0, 2.0, 1.0, 2.0);
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
      tree.containedBy(AreaD.of(97.0, 98.0, 97.0, 98.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y0()
  {
    final AreaD area = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(98.0, 99.0, 1.0, 2.0);
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
      tree.containedBy(AreaD.of(1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y1()
  {
    final AreaD area = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(1.0, 2.0, 98.0, 99.0);
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
      tree.containedBy(AreaD.of(1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y1()
  {
    final AreaD area = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(98.0, 99.0, 98.0, 99.0);
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
      tree.containedBy(AreaD.of(1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y0()
  {
    final AreaD area = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(1.0, 2.0, 1.0, 2.0);
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
      tree.overlappedBy(AreaD.of(97.0, 98.0, 97.0, 98.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y0()
  {
    final AreaD area = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(98.0, 99.0, 1.0, 2.0);
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
      tree.overlappedBy(AreaD.of(1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y1()
  {
    final AreaD area = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(98.0, 99.0, 98.0, 99.0);
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
      tree.overlappedBy(AreaD.of(1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y0 quadrant works.
   */

  @Test
  public final void testOverlappingNot()
  {
    final AreaD area = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(10.0, 90.0, 10.0, 90.0);
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
      tree.overlappedBy(AreaD.of(1.0, 9.0, 1.0, 9.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y1()
  {
    final AreaD area = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(1.0, 2.0, 98.0, 99.0);
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
      tree.overlappedBy(AreaD.of(1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Ray casting in the X0Y0 quadrant works.
   */

  @Test
  public final void testRaycastX0Y0()
  {
    final AreaD area = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final AreaD item_area0 = AreaD.of(10.0, 20.0, 11.0, 21.0);
    Assert.assertTrue(tree.insert(item0, item_area0));

    final Integer item1 = Integer.valueOf(1);
    final AreaD item_area1 = AreaD.of(15.0, 25.0, 16.0, 26.0);
    Assert.assertTrue(tree.insert(item1, item_area1));

    final Integer item2 = Integer.valueOf(2);
    final AreaD item_area2 = AreaD.of(25.0, 35.0, 26.0, 36.0);
    Assert.assertTrue(tree.insert(item2, item_area2));

    {
      final Vector2D origin = Vector2D.of(0.0, 0.0);
      final Vector2D direction = Vector2D.of(1.0, 1.0);
      final Ray2D ray = Ray2D.of(origin, direction);
      final SortedSet<QuadTreeRaycastResultD<Object>> items = new TreeSet<>();
      tree.raycast(ray, items);

      Assert.assertEquals(3L, (long) items.size());
      final Iterator<QuadTreeRaycastResultD<Object>> iter = items.iterator();

      final QuadTreeRaycastResultD<Object> result0 = iter.next();
      Assert.assertEquals(item0, result0.item());
      Assert.assertEquals(item_area0, result0.area());

      final QuadTreeRaycastResultD<Object> result1 = iter.next();
      Assert.assertEquals(item1, result1.item());
      Assert.assertEquals(item_area1, result1.area());

      final QuadTreeRaycastResultD<Object> result2 = iter.next();
      Assert.assertEquals(item2, result2.item());
      Assert.assertEquals(item_area2, result2.area());
    }

    {
      final Vector2D origin = Vector2D.of(0.0, 0.0);
      final Vector2D direction = Vector2D.of(1.0, 0.0);
      final Ray2D ray = Ray2D.of(origin, direction);
      final SortedSet<QuadTreeRaycastResultD<Object>> items = new TreeSet<>();
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
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    cb.setTrimOnRemove(false);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Integer> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaD item_area0 = AreaD.of(2.0, 98.0, 2.0, 98.0);

    final AreaD item_area1 = AreaD.of(1.0, 2.0, 1.0, 2.0);

    final AreaD item_area2 = AreaD.of(98.0, 99.0, 1.0, 2.0);

    final AreaD item_area3 = AreaD.of(1.0, 2.0, 98.0, 99.0);

    final AreaD item_area4 = AreaD.of(98.0, 99.0, 98.0, 99.0);

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    tree.trim();

    final int count_0 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(69L, (long) count_0);

    Assert.assertTrue(tree.remove(item1));
    tree.trim();

    final int count_1 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(69L - 16L, (long) count_1);

    Assert.assertTrue(tree.remove(item2));
    tree.trim();

    final int count_2 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(69L - (16L + 16L), (long) count_2);

    Assert.assertTrue(tree.remove(item3));
    tree.trim();

    final int count_3 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(69L - (16L + 16L + 16L), (long) count_3);

    Assert.assertTrue(tree.remove(item4));
    tree.trim();

    final int count_4 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(1L, (long) count_4);

    Assert.assertTrue(tree.remove(item0));
    tree.trim();

    final int count_5 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(1L, (long) count_5);
  }

  /**
   * Trimming on removal works.
   */

  @Test
  public final void testTrimOnRemoval()
  {
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    cb.setTrimOnRemove(true);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaD item_area0 = AreaD.of(2.0, 98.0, 2.0, 98.0);

    final AreaD item_area1 = AreaD.of(1.0, 2.0, 1.0, 2.0);

    final AreaD item_area2 = AreaD.of(98.0, 99.0, 1.0, 2.0);

    final AreaD item_area3 = AreaD.of(1.0, 2.0, 98.0, 99.0);

    final AreaD item_area4 = AreaD.of(98.0, 99.0, 98.0, 99.0);

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    final int count_0 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(69L, (long) count_0);

    Assert.assertTrue(tree.remove(item1));

    final int count_1 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(69L - 16L, (long) count_1);

    Assert.assertTrue(tree.remove(item2));

    final int count_2 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(69L - (16L + 16L), (long) count_2);

    Assert.assertTrue(tree.remove(item3));

    final int count_3 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(69L - (16L + 16L + 16L), (long) count_3);

    Assert.assertTrue(tree.remove(item4));

    final int count_4 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(1L, (long) count_4);

    Assert.assertTrue(tree.remove(item0));

    final int count_5 = QuadTreeDContract.countQuadrants(tree);
    Assert.assertEquals(1L, (long) count_5);
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversal()
  {
    final AreaD container = AreaD.of(-1000.0, 1000.0, -1000.0, 1000.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Integer> tree = this.create(c);

    final Generator<AreaD> gen =
      new AreaDContainedGenerator(container);

    final Map<Integer, AreaD> inserted = new HashMap<>(500);
    for (int index = 0; index < 500; ++index) {
      final Integer b_index = Integer.valueOf(index);
      final AreaD area = gen.next();
      Assert.assertTrue(tree.insert(b_index, area));
      inserted.put(b_index, area);
    }

    final Map<Integer, AreaD> found = new HashMap<>(500);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      Assert.assertTrue(AreasD.contains(container, quadrant.area()));

      final Map<Integer, AreaD> objects = quadrant.objects();
      for (final Map.Entry<Integer, AreaD> e : objects.entrySet()) {
        Assert.assertFalse(found.containsKey(e.getKey()));
        found.put(e.getKey(), e.getValue());
      }
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(inserted, found);
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversalStop0()
  {
    final AreaD container = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40.0);
    cb.setMinimumQuadrantWidth(40.0);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaD.of(10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(5L, (long) QuadTreeDContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      if (count.get() == 1) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(1L, (long) count.get());
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversalStop1()
  {
    final AreaD container = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40.0);
    cb.setMinimumQuadrantWidth(40.0);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaD.of(10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(5L, (long) QuadTreeDContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      if (count.get() == 2) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(2L, (long) count.get());
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversalStop2()
  {
    final AreaD container = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40.0);
    cb.setMinimumQuadrantWidth(40.0);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaD.of(10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(5L, (long) QuadTreeDContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      if (count.get() == 3) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(3L, (long) count.get());
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversalStop3()
  {
    final AreaD container = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40.0);
    cb.setMinimumQuadrantWidth(40.0);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaD.of(10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(5L, (long) QuadTreeDContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      if (count.get() == 4) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(4L, (long) count.get());
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversalStop4()
  {
    final AreaD container = AreaD.of(0.0, 100.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40.0);
    cb.setMinimumQuadrantWidth(40.0);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaD.of(10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(5L, (long) QuadTreeDContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      if (count.get() == 5) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(5L, (long) count.get());
  }

  /**
   * Simple raycast test.
   */

  @Test
  public final void testRaycastSimple()
  {
    final AreaD container = AreaD.of(0.0, 512.0, 0.0, 512.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Integer> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);

    Assert.assertTrue(tree.insert(item0, AreaD.of(32.0, 80.0, 32.0, 80.0)));

    Assert.assertTrue(tree.insert(
      item1,
      AreaD.of(400.0, 400.0 + 32.0, 32.0, 80.0)));

    Assert.assertTrue(tree.insert(item2, AreaD.of(400.0, 480.0, 400.0, 480.0)));

    final Ray2D ray = Ray2D.of(
      Vectors2D.zero(),
      Vectors2D.normalize(Vector2D.of(511.0, 511.0)));

    final SortedSet<QuadTreeRaycastResultD<Integer>> items = new TreeSet<>();
    tree.raycast(ray, items);

    Assert.assertEquals(2L, (long) items.size());
    final Iterator<QuadTreeRaycastResultD<Integer>> iter = items.iterator();

    {
      final QuadTreeRaycastResultD<Integer> rr = iter.next();
      final AreaD r = rr.area();
      Assert.assertEquals(item0, rr.item());
      Assert.assertEquals(32.0, r.minimumX(), 0.0);
      Assert.assertEquals(32.0, r.minimumY(), 0.0);
      Assert.assertEquals(80.0, r.maximumX(), 0.0);
      Assert.assertEquals(80.0, r.maximumY(), 0.0);
    }

    {
      final QuadTreeRaycastResultD<Integer> rr = iter.next();
      final AreaD r = rr.area();
      Assert.assertEquals(item2, rr.item());
      Assert.assertEquals(400.0, r.minimumX(), 0.0);
      Assert.assertEquals(400.0, r.minimumY(), 0.0);
      Assert.assertEquals(480.0, r.maximumX(), 0.0);
      Assert.assertEquals(480.0, r.maximumY(), 0.0);
    }

    Assert.assertFalse(iter.hasNext());
  }

  /**
   * The quadrant cannot be split due to small width.
   */

  @Test
  public final void testInsertCannotSplitWidth()
  {
    final AreaD area =
      AreaD.of(0.0, 1.0, 0.0, 100.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    cb.setMinimumQuadrantWidth(2.0);
    cb.setMinimumQuadrantHeight(2.0);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(0.0, 1.0, 0.0, 1.0);
    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertEquals(1L, (long) QuadTreeDContract.countQuadrants(tree));
  }

  /**
   * The quadrant cannot be split due to small height.
   */

  @Test
  public final void testInsertCannotSplitHeight()
  {
    final AreaD area =
      AreaD.of(0.0, 100.0, 0.0, 1.0);

    final QuadTreeConfigurationD.Builder cb = QuadTreeConfigurationD.builder();
    cb.setArea(area);
    cb.setMinimumQuadrantWidth(2.0);
    cb.setMinimumQuadrantHeight(2.0);
    final QuadTreeConfigurationD c = cb.build();

    final QuadTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaD item_area = AreaD.of(0.0, 1.0, 0.0, 1.0);
    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertEquals(1L, (long) QuadTreeDContract.countQuadrants(tree));
  }
}
