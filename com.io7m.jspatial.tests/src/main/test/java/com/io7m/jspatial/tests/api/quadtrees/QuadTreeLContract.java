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

package com.io7m.jspatial.tests.api.quadtrees;

import com.io7m.jregions.core.unparameterized.areas.AreaL;
import com.io7m.jregions.core.unparameterized.areas.AreasL;
import com.io7m.jspatial.api.Ray2D;
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import com.io7m.jspatial.api.quadtrees.QuadTreeLType;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultL;
import com.io7m.jspatial.tests.api.AreaLContainedGenerator;
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

public abstract class QuadTreeLContract
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  private static int countQuadrants(final QuadTreeLType<Object> tree)
  {
    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(count, (context, quadrant, depth) -> {
      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });
    return count.get();
  }

  protected abstract <T> QuadTreeLType<T> create(QuadTreeConfigurationL config);

  /**
   * Simple identities.
   */

  @Test
  public final void testIdentities()
  {
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(-100L, 200L, -100L, 200L);
    Assert.assertFalse(tree.insert(item, item_area));
  }

  /**
   * Inserting an object into the X0Y0 quadrant works.
   */

  @Test
  public final void testInsertX0Y0()
  {
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(1L, 2L, 1L, 2L);
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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(98L, 99L, 1L, 2L);
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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(1L, 2L, 98L, 99L);
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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(98L, 99L, 98L, 99L);
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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(2L, 98L, 2L, 98L);
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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaL item_area0 = AreaL.of(2L, 98L, 2L, 98L);

    final AreaL item_area1 = AreaL.of(1L, 2L, 1L, 2L);

    final AreaL item_area2 = AreaL.of(98L, 99L, 1L, 2L);

    final AreaL item_area3 = AreaL.of(1L, 2L, 98L, 99L);

    final AreaL item_area4 = AreaL.of(98L, 99L, 98L, 99L);

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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaL item_area0 = AreaL.of(2L, 98L, 2L, 98L);

    final AreaL item_area1 = AreaL.of(1L, 2L, 1L, 2L);

    final AreaL item_area2 = AreaL.of(98L, 99L, 1L, 2L);

    final AreaL item_area3 = AreaL.of(1L, 2L, 98L, 99L);

    final AreaL item_area4 = AreaL.of(98L, 99L, 98L, 99L);

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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaL item_area0 = AreaL.of(2L, 98L, 2L, 98L);

    final AreaL item_area1 = AreaL.of(1L, 2L, 1L, 2L);

    final AreaL item_area2 = AreaL.of(98L, 99L, 1L, 2L);

    final AreaL item_area3 = AreaL.of(1L, 2L, 98L, 99L);

    final AreaL item_area4 = AreaL.of(98L, 99L, 98L, 99L);

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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaL item_area0 = AreaL.of(2L, 98L, 2L, 98L);

    final AreaL item_area1 = AreaL.of(1L, 2L, 1L, 2L);

    final AreaL item_area2 = AreaL.of(98L, 99L, 1L, 2L);

    final AreaL item_area3 = AreaL.of(1L, 2L, 98L, 99L);

    final AreaL item_area4 = AreaL.of(98L, 99L, 98L, 99L);

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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

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
    final AreaL area = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(1L, 2L, 1L, 2L);
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
      tree.containedBy(AreaL.of(97L, 98L, 97L, 98L), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y0()
  {
    final AreaL area = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(98L, 99L, 1L, 2L);
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
      tree.containedBy(AreaL.of(1L, 2L, 1L, 2L), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y1()
  {
    final AreaL area = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(1L, 2L, 98L, 99L);
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
      tree.containedBy(AreaL.of(1L, 2L, 1L, 2L), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y1()
  {
    final AreaL area = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(98L, 99L, 98L, 99L);
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
      tree.containedBy(AreaL.of(1L, 2L, 1L, 2L), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y0()
  {
    final AreaL area = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(1L, 2L, 1L, 2L);
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
      tree.overlappedBy(AreaL.of(97L, 98L, 97L, 98L), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y0()
  {
    final AreaL area = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(98L, 99L, 1L, 2L);
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
      tree.overlappedBy(AreaL.of(1L, 2L, 1L, 2L), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y1()
  {
    final AreaL area = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(98L, 99L, 98L, 99L);
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
      tree.overlappedBy(AreaL.of(1L, 2L, 1L, 2L), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y0 quadrant works.
   */

  @Test
  public final void testOverlappingNot()
  {
    final AreaL area = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(10L, 90L, 10L, 90L);
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
      tree.overlappedBy(AreaL.of(1L, 9L, 1L, 9L), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y1()
  {
    final AreaL area = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(1L, 2L, 98L, 99L);
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
      tree.overlappedBy(AreaL.of(1L, 2L, 1L, 2L), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Ray casting in the X0Y0 quadrant works.
   */

  @Test
  public final void testRaycastX0Y0()
  {
    final AreaL area = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final AreaL item_area0 = AreaL.of(10L, 20L, 11L, 21L);
    Assert.assertTrue(tree.insert(item0, item_area0));

    final Integer item1 = Integer.valueOf(1);
    final AreaL item_area1 = AreaL.of(15L, 25L, 16L, 26L);
    Assert.assertTrue(tree.insert(item1, item_area1));

    final Integer item2 = Integer.valueOf(2);
    final AreaL item_area2 = AreaL.of(25L, 35L, 26L, 36L);
    Assert.assertTrue(tree.insert(item2, item_area2));

    {
      final Vector2D origin = Vector2D.of(0.0, 0.0);
      final Vector2D direction = Vector2D.of(1.0, 1.0);
      final Ray2D ray = Ray2D.of(origin, direction);
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
      final Vector2D origin = Vector2D.of(0.0, 0.0);
      final Vector2D direction = Vector2D.of(1.0, 0.0);
      final Ray2D ray = Ray2D.of(origin, direction);
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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

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

    final AreaL item_area0 = AreaL.of(2L, 98L, 2L, 98L);

    final AreaL item_area1 = AreaL.of(1L, 2L, 1L, 2L);

    final AreaL item_area2 = AreaL.of(98L, 99L, 1L, 2L);

    final AreaL item_area3 = AreaL.of(1L, 2L, 98L, 99L);

    final AreaL item_area4 = AreaL.of(98L, 99L, 98L, 99L);

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
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 100L);

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

    final AreaL item_area0 = AreaL.of(2L, 98L, 2L, 98L);

    final AreaL item_area1 = AreaL.of(1L, 2L, 1L, 2L);

    final AreaL item_area2 = AreaL.of(98L, 99L, 1L, 2L);

    final AreaL item_area3 = AreaL.of(1L, 2L, 98L, 99L);

    final AreaL item_area4 = AreaL.of(98L, 99L, 98L, 99L);

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

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversal()
  {
    final AreaL container = AreaL.of(-1000L, 1000L, -1000L, 1000L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Integer> tree = this.create(c);

    final Generator<AreaL> gen =
      new AreaLContainedGenerator(container);

    final Map<Integer, AreaL> inserted = new HashMap<>(500);
    for (int index = 0; index < 500; ++index) {
      final Integer b_index = Integer.valueOf(index);
      final AreaL area = gen.next();
      Assert.assertTrue(tree.insert(b_index, area));
      inserted.put(b_index, area);
    }

    final Map<Integer, AreaL> found = new HashMap<>(500);
    tree.iterateQuadrants(Integer.valueOf(0), (context, quadrant, depth) -> {
      Assert.assertTrue(AreasL.contains(container, quadrant.area()));

      final Map<Integer, AreaL> objects = quadrant.objects();
      for (final Map.Entry<Integer, AreaL> e : objects.entrySet()) {
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
    final AreaL container = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40L);
    cb.setMinimumQuadrantWidth(40L);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaL.of(10L, 20L, 10L, 20L)));
    tree.trim();

    Assert.assertEquals(5L, (long) QuadTreeLContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Integer.valueOf(0), (context, quadrant, depth) -> {
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
    final AreaL container = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40L);
    cb.setMinimumQuadrantWidth(40L);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaL.of(10L, 20L, 10L, 20L)));
    tree.trim();

    Assert.assertEquals(5L, (long) QuadTreeLContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Integer.valueOf(0), (context, quadrant, depth) -> {
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
    final AreaL container = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40L);
    cb.setMinimumQuadrantWidth(40L);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaL.of(10L, 20L, 10L, 20L)));
    tree.trim();

    Assert.assertEquals(5L, (long) QuadTreeLContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Integer.valueOf(0), (context, quadrant, depth) -> {
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
    final AreaL container = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40L);
    cb.setMinimumQuadrantWidth(40L);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaL.of(10L, 20L, 10L, 20L)));
    tree.trim();

    Assert.assertEquals(5L, (long) QuadTreeLContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Integer.valueOf(0), (context, quadrant, depth) -> {
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
    final AreaL container = AreaL.of(0L, 100L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40L);
    cb.setMinimumQuadrantWidth(40L);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaL.of(10L, 20L, 10L, 20L)));
    tree.trim();

    Assert.assertEquals(5L, (long) QuadTreeLContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Integer.valueOf(0), (context, quadrant, depth) -> {
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
    final AreaL container = AreaL.of(0L, 512L, 0L, 512L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Integer> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);

    Assert.assertTrue(tree.insert(item0, AreaL.of(32L, 80L, 32L, 80L)));

    Assert.assertTrue(tree.insert(item1, AreaL.of(400L, 400L + 32L, 32L, 80L)));

    Assert.assertTrue(tree.insert(item2, AreaL.of(400L, 480L, 400L, 480L)));

    final Ray2D ray = Ray2D.of(
      Vectors2D.zero(),
      Vectors2D.normalize(Vector2D.of(511.0, 511.0)));

    final SortedSet<QuadTreeRaycastResultL<Integer>> items = new TreeSet<>();
    tree.raycast(ray, items);

    Assert.assertEquals(2L, (long) items.size());
    final Iterator<QuadTreeRaycastResultL<Integer>> iter = items.iterator();

    {
      final QuadTreeRaycastResultL<Integer> rr = iter.next();
      final AreaL r = rr.area();
      Assert.assertEquals(item0, rr.item());
      Assert.assertEquals(32L, r.minimumX());
      Assert.assertEquals(32L, r.minimumY());
      Assert.assertEquals(80L, r.maximumX());
      Assert.assertEquals(80L, r.maximumY());
    }

    {
      final QuadTreeRaycastResultL<Integer> rr = iter.next();
      final AreaL r = rr.area();
      Assert.assertEquals(item2, rr.item());
      Assert.assertEquals(400L, r.minimumX());
      Assert.assertEquals(400L, r.minimumY());
      Assert.assertEquals(480L, r.maximumX());
      Assert.assertEquals(480L, r.maximumY());
    }

    Assert.assertFalse(iter.hasNext());
  }

  /**
   * The quadrant cannot be split due to small width.
   */

  @Test
  public final void testInsertCannotSplitWidth()
  {
    final AreaL area =
      AreaL.of(0L, 1L, 0L, 100L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(0L, 1L, 0L, 1L);
    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertEquals(1L, (long) QuadTreeLContract.countQuadrants(tree));
  }

  /**
   * The quadrant cannot be split due to small height.
   */

  @Test
  public final void testInsertCannotSplitHeight()
  {
    final AreaL area =
      AreaL.of(0L, 100L, 0L, 1L);

    final QuadTreeConfigurationL.Builder cb = QuadTreeConfigurationL.builder();
    cb.setArea(area);
    final QuadTreeConfigurationL c = cb.build();

    final QuadTreeLType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaL item_area = AreaL.of(0L, 1L, 0L, 1L);
    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertEquals(1L, (long) QuadTreeLContract.countQuadrants(tree));
  }
}
