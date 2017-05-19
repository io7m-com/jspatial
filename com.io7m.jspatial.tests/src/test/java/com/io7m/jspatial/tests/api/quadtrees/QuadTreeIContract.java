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
import com.io7m.jregions.core.unparameterized.areas.AreaI;
import com.io7m.jregions.core.unparameterized.areas.AreasI;
import com.io7m.jspatial.api.Ray2D;
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationI;
import com.io7m.jspatial.api.quadtrees.QuadTreeIType;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultI;
import com.io7m.jspatial.tests.api.AreaIContainedGenerator;
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

public abstract class QuadTreeIContract
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  private static int countQuadrants(final QuadTreeIType<Object> tree)
  {
    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(count, (context, quadrant, depth) -> {
      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });
    return count.get();
  }

  protected abstract <T> QuadTreeIType<T> create(QuadTreeConfigurationI config);

  /**
   * Simple identities.
   */

  @Test
  public final void testIdentities()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);
    Assert.assertTrue(tree.isEmpty());
    Assert.assertEquals(0, tree.size());
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
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(-100, 200, -100, 200);
    Assert.assertFalse(tree.insert(item, item_area));
  }

  /**
   * Inserting an object into the X0Y0 quadrant works.
   */

  @Test
  public final void testInsertX0Y0()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(1, 2, 1, 2);
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y0 quadrant works.
   */

  @Test
  public final void testInsertX1Y0()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(98, 99, 1, 2);
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X0Y1 quadrant works.
   */

  @Test
  public final void testInsertX0Y1()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(1, 2, 98, 99);
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y1 quadrant works.
   */

  @Test
  public final void testInsertX1Y1()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(98, 99, 98, 99);
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the root quadrant works.
   */

  @Test
  public final void testInsertCentral()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(2, 98, 2, 98);
    Assert.assertTrue(tree.insert(item, item_area));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting and removing objects is correct.
   */

  @Test
  public final void testInsertRemove()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaI item_area0 = AreaI.of(2, 98, 2, 98);

    final AreaI item_area1 = AreaI.of(1, 2, 1, 2);

    final AreaI item_area2 = AreaI.of(98, 99, 1, 2);

    final AreaI item_area3 = AreaI.of(1, 2, 98, 99);

    final AreaI item_area4 = AreaI.of(98, 99, 98, 99);

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

    Assert.assertEquals(5, tree.size());

    Assert.assertTrue(tree.remove(item0));
    Assert.assertTrue(tree.remove(item1));
    Assert.assertTrue(tree.remove(item2));
    Assert.assertTrue(tree.remove(item3));
    Assert.assertTrue(tree.remove(item4));

    Assert.assertEquals(0, tree.size());

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
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaI item_area0 = AreaI.of(2, 98, 2, 98);

    final AreaI item_area1 = AreaI.of(1, 2, 1, 2);

    final AreaI item_area2 = AreaI.of(98, 99, 1, 2);

    final AreaI item_area3 = AreaI.of(1, 2, 98, 99);

    final AreaI item_area4 = AreaI.of(98, 99, 98, 99);

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    tree.clear();

    Assert.assertEquals(0, tree.size());
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
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaI item_area0 = AreaI.of(2, 98, 2, 98);

    final AreaI item_area1 = AreaI.of(1, 2, 1, 2);

    final AreaI item_area2 = AreaI.of(98, 99, 1, 2);

    final AreaI item_area3 = AreaI.of(1, 2, 98, 99);

    final AreaI item_area4 = AreaI.of(98, 99, 98, 99);

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    final QuadTreeIType<Object> tree_map = tree.map((x, ignored) -> x);
    Assert.assertEquals(tree, tree_map);
  }

  /**
   * The areaFor query is correct.
   */

  @Test
  public final void testAreaFor()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaI item_area0 = AreaI.of(2, 98, 2, 98);

    final AreaI item_area1 = AreaI.of(1, 2, 1, 2);

    final AreaI item_area2 = AreaI.of(98, 99, 1, 2);

    final AreaI item_area3 = AreaI.of(1, 2, 98, 99);

    final AreaI item_area4 = AreaI.of(98, 99, 98, 99);

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
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);
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
    final AreaI area = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(1, 2, 1, 2);
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(AreaI.of(97, 98, 97, 98), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y0()
  {
    final AreaI area = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(98, 99, 1, 2);
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(AreaI.of(1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y1()
  {
    final AreaI area = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(1, 2, 98, 99);
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(AreaI.of(1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y1()
  {
    final AreaI area = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(98, 99, 98, 99);
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(AreaI.of(1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y0()
  {
    final AreaI area = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(1, 2, 1, 2);
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(AreaI.of(97, 98, 97, 98), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y0()
  {
    final AreaI area = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(98, 99, 1, 2);
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(AreaI.of(1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y1()
  {
    final AreaI area = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(98, 99, 98, 99);
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(AreaI.of(1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y0 quadrant works.
   */

  @Test
  public final void testOverlappingNot()
  {
    final AreaI area = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(10, 90, 10, 90);
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(AreaI.of(1, 9, 1, 9), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y1()
  {
    final AreaI area = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(1, 2, 98, 99);
    Assert.assertTrue(tree.insert(item, item_area));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_area, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(AreaI.of(1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Ray casting in the X0Y0 quadrant works.
   */

  @Test
  public final void testRaycastX0Y0()
  {
    final AreaI area = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final AreaI item_area0 = AreaI.of(10, 20, 11, 21);
    Assert.assertTrue(tree.insert(item0, item_area0));

    final Integer item1 = Integer.valueOf(1);
    final AreaI item_area1 = AreaI.of(15, 25, 16, 26);
    Assert.assertTrue(tree.insert(item1, item_area1));

    final Integer item2 = Integer.valueOf(2);
    final AreaI item_area2 = AreaI.of(25, 35, 26, 36);
    Assert.assertTrue(tree.insert(item2, item_area2));

    {
      final Vector2D origin = Vector2D.of(0.0, 0.0);
      final Vector2D direction = Vector2D.of(1.0, 1.0);
      final Ray2D ray = Ray2D.of(origin, direction);
      final SortedSet<QuadTreeRaycastResultI<Object>> items = new TreeSet<>();
      tree.raycast(ray, items);

      Assert.assertEquals(3, (long) items.size());
      final Iterator<QuadTreeRaycastResultI<Object>> iter = items.iterator();

      final QuadTreeRaycastResultI<Object> result0 = iter.next();
      Assert.assertEquals(item0, result0.item());
      Assert.assertEquals(item_area0, result0.area());

      final QuadTreeRaycastResultI<Object> result1 = iter.next();
      Assert.assertEquals(item1, result1.item());
      Assert.assertEquals(item_area1, result1.area());

      final QuadTreeRaycastResultI<Object> result2 = iter.next();
      Assert.assertEquals(item2, result2.item());
      Assert.assertEquals(item_area2, result2.area());
    }

    {
      final Vector2D origin = Vector2D.of(0.0, 0.0);
      final Vector2D direction = Vector2D.of(1.0, 0.0);
      final Ray2D ray = Ray2D.of(origin, direction);
      final SortedSet<QuadTreeRaycastResultI<Object>> items = new TreeSet<>();
      tree.raycast(ray, items);

      Assert.assertEquals(0, (long) items.size());
    }
  }

  /**
   * Trimming works.
   */

  @Test
  public final void testTrim()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    cb.setTrimOnRemove(false);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaI item_area0 = AreaI.of(2, 98, 2, 98);

    final AreaI item_area1 = AreaI.of(1, 2, 1, 2);

    final AreaI item_area2 = AreaI.of(98, 99, 1, 2);

    final AreaI item_area3 = AreaI.of(1, 2, 98, 99);

    final AreaI item_area4 = AreaI.of(98, 99, 98, 99);

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    tree.trim();

    final int count_0 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(73, (long) count_0);

    Assert.assertTrue(tree.remove(item1));
    tree.trim();

    final int count_1 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(73 - 16, (long) count_1);

    Assert.assertTrue(tree.remove(item2));
    tree.trim();

    final int count_2 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(73 - (16 + 16), (long) count_2);

    Assert.assertTrue(tree.remove(item3));
    tree.trim();

    final int count_3 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(73 - (16 + 16 + 16), (long) count_3);

    Assert.assertTrue(tree.remove(item4));
    tree.trim();

    final int count_4 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(1, (long) count_4);

    Assert.assertTrue(tree.remove(item0));
    tree.trim();

    final int count_5 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(1, (long) count_5);
  }

  /**
   * Trimming on removal works.
   */

  @Test
  public final void testTrimOnRemoval()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    cb.setTrimOnRemove(true);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final AreaI item_area0 = AreaI.of(2, 98, 2, 98);

    final AreaI item_area1 = AreaI.of(1, 2, 1, 2);

    final AreaI item_area2 = AreaI.of(98, 99, 1, 2);

    final AreaI item_area3 = AreaI.of(1, 2, 98, 99);

    final AreaI item_area4 = AreaI.of(98, 99, 98, 99);

    Assert.assertTrue(tree.insert(item0, item_area0));
    Assert.assertTrue(tree.insert(item1, item_area1));
    Assert.assertTrue(tree.insert(item2, item_area2));
    Assert.assertTrue(tree.insert(item3, item_area3));
    Assert.assertTrue(tree.insert(item4, item_area4));

    final int count_0 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(73, (long) count_0);

    Assert.assertTrue(tree.remove(item1));

    final int count_1 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(73 - 16, (long) count_1);

    Assert.assertTrue(tree.remove(item2));

    final int count_2 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(73 - (16 + 16), (long) count_2);

    Assert.assertTrue(tree.remove(item3));

    final int count_3 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(73 - (16 + 16 + 16), (long) count_3);

    Assert.assertTrue(tree.remove(item4));

    final int count_4 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(1, (long) count_4);

    Assert.assertTrue(tree.remove(item0));

    final int count_5 = QuadTreeIContract.countQuadrants(tree);
    Assert.assertEquals(1, (long) count_5);
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversal()
  {
    final AreaI container = AreaI.of(-1000, 1000, -1000, 1000);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Integer> tree = this.create(c);

    final Generator<AreaI> gen =
      new AreaIContainedGenerator(container);

    final Map<Integer, AreaI> inserted = new HashMap<>(500);
    for (int index = 0; index < 500; ++index) {
      final Integer b_index = Integer.valueOf(index);
      final AreaI area = gen.next();
      Assert.assertTrue(tree.insert(b_index, area));
      inserted.put(b_index, area);
    }

    final Map<Integer, AreaI> found = new HashMap<>(500);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      Assert.assertTrue(AreasI.contains(container, quadrant.area()));

      final Map<Integer, AreaI> objects = quadrant.objects();
      for (final Map.Entry<Integer, AreaI> e : objects.entrySet()) {
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
    final AreaI container = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40);
    cb.setMinimumQuadrantWidth(40);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaI.of(10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(5, (long) QuadTreeIContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      if (count.get() == 1) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(1, (long) count.get());
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversalStop1()
  {
    final AreaI container = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40);
    cb.setMinimumQuadrantWidth(40);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaI.of(10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(5, (long) QuadTreeIContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      if (count.get() == 2) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(2, (long) count.get());
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversalStop2()
  {
    final AreaI container = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40);
    cb.setMinimumQuadrantWidth(40);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaI.of(10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(5, (long) QuadTreeIContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      if (count.get() == 3) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(3, (long) count.get());
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversalStop3()
  {
    final AreaI container = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40);
    cb.setMinimumQuadrantWidth(40);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaI.of(10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(5, (long) QuadTreeIContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      if (count.get() == 4) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(4, (long) count.get());
  }

  /**
   * Quadrant traversal works.
   */

  @Test
  public final void testQuadrantTraversalStop4()
  {
    final AreaI container = AreaI.of(0, 100, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumQuadrantHeight(40);
    cb.setMinimumQuadrantWidth(40);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      AreaI.of(10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(5, (long) QuadTreeIContract.countQuadrants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateQuadrants(Unit.unit(), (context, quadrant, depth) -> {
      if (count.get() == 5) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(5, (long) count.get());
  }

  /**
   * Simple raycast test.
   */

  @Test
  public final void testRaycastSimple()
  {
    final AreaI container = AreaI.of(0, 512, 0, 512);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(container);
    cb.setTrimOnRemove(true);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Integer> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);

    Assert.assertTrue(tree.insert(item0, AreaI.of(32, 80, 32, 80)));

    Assert.assertTrue(tree.insert(item1, AreaI.of(400, 400 + 32, 32, 80)));

    Assert.assertTrue(tree.insert(item2, AreaI.of(400, 480, 400, 480)));

    final Ray2D ray = Ray2D.of(
      Vectors2D.zero(),
      Vectors2D.normalize(Vector2D.of(511.0, 511.0)));

    final SortedSet<QuadTreeRaycastResultI<Integer>> items = new TreeSet<>();
    tree.raycast(ray, items);

    Assert.assertEquals(2, (long) items.size());
    final Iterator<QuadTreeRaycastResultI<Integer>> iter = items.iterator();

    {
      final QuadTreeRaycastResultI<Integer> rr = iter.next();
      final AreaI r = rr.area();
      Assert.assertEquals(item0, rr.item());
      Assert.assertEquals(32, r.minimumX());
      Assert.assertEquals(32, r.minimumY());
      Assert.assertEquals(80, r.maximumX());
      Assert.assertEquals(80, r.maximumY());
    }

    {
      final QuadTreeRaycastResultI<Integer> rr = iter.next();
      final AreaI r = rr.area();
      Assert.assertEquals(item2, rr.item());
      Assert.assertEquals(400, r.minimumX());
      Assert.assertEquals(400, r.minimumY());
      Assert.assertEquals(480, r.maximumX());
      Assert.assertEquals(480, r.maximumY());
    }

    Assert.assertFalse(iter.hasNext());
  }

  /**
   * The quadrant cannot be split due to small width.
   */

  @Test
  public final void testInsertCannotSplitWidth()
  {
    final AreaI area =
      AreaI.of(0, 1, 0, 100);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(0, 1, 0, 1);
    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertEquals(1, (long) QuadTreeIContract.countQuadrants(tree));
  }

  /**
   * The quadrant cannot be split due to small height.
   */

  @Test
  public final void testInsertCannotSplitHeight()
  {
    final AreaI area =
      AreaI.of(0, 100, 0, 1);

    final QuadTreeConfigurationI.Builder cb = QuadTreeConfigurationI.builder();
    cb.setArea(area);
    final QuadTreeConfigurationI c = cb.build();

    final QuadTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final AreaI item_area = AreaI.of(0, 1, 0, 1);
    Assert.assertTrue(tree.insert(item, item_area));
    Assert.assertEquals(1, (long) QuadTreeIContract.countQuadrants(tree));
  }
}
