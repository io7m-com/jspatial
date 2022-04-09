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
import com.io7m.jregions.core.unparameterized.volumes.VolumesI;
import com.io7m.jspatial.api.Ray3D;
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationI;
import com.io7m.jspatial.api.octtrees.OctTreeIType;
import com.io7m.jspatial.api.octtrees.OctTreeRaycastResultI;
import com.io7m.jspatial.tests.api.VolumeIContainedGenerator;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3D;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tree contract.
 */

public abstract class OctTreeIContract
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  private static int countOctants(final OctTreeIType<?> tree)
  {
    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(count, (context, quadrant, depth) -> {
      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });
    return count.get();
  }

  protected abstract <T> OctTreeIType<T> create(OctTreeConfigurationI config);

  /**
   * Simple identities.
   */

  @Test
  public final void testIdentities()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);
    Assert.assertTrue(tree.isEmpty());
    Assert.assertEquals(0, tree.size());
    Assert.assertEquals(volume, tree.bounds());

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
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(
      -100,
      200,
      -100,
      200,
      -100,
      200);
    Assert.assertFalse(tree.insert(item, item_volume));
  }

  /**
   * Inserting a tiny object works.
   */

  @Test
  public final void testInsertTiny()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    cb.setMinimumOctantHeight(2);
    cb.setMinimumOctantWidth(2);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 1, 2, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testInsertX0Y0Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 1, 2, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testInsertX1Y0Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(98, 99, 1, 2, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X0Y1Z0 quadrant works.
   */

  @Test
  public final void testInsertX0Y1Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 98, 99, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y1Z0 quadrant works.
   */

  @Test
  public final void testInsertX1Y1Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(98, 99, 98, 99, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }


  /**
   * Inserting an object into the X0Y0Z1 quadrant works.
   */

  @Test
  public final void testInsertX0Y0Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 1, 2, 98, 99);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testInsertX1Y0Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(98, 99, 1, 2, 98, 99);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X0Y1Z1 quadrant works.
   */

  @Test
  public final void testInsertX0Y1Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 98, 99, 98, 99);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y1Z1 quadrant works.
   */

  @Test
  public final void testInsertX1Y1Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(
      98,
      99,
      98,
      99,
      98,
      99);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
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
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(2, 98, 2, 98, 2, 98);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
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
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final VolumeI item_volume0 = VolumeI.of(2, 98, 2, 98, 2, 98);

    final VolumeI item_volume1 = VolumeI.of(1, 2, 1, 2, 1, 2);

    final VolumeI item_volume2 = VolumeI.of(98, 99, 1, 2, 1, 2);

    final VolumeI item_volume3 = VolumeI.of(1, 2, 98, 99, 1, 2);

    final VolumeI item_volume4 = VolumeI.of(98, 99, 98, 99, 1, 2);

    Assert.assertTrue(tree.insert(item0, item_volume0));
    Assert.assertTrue(tree.insert(item1, item_volume1));
    Assert.assertTrue(tree.insert(item2, item_volume2));
    Assert.assertTrue(tree.insert(item3, item_volume3));
    Assert.assertTrue(tree.insert(item4, item_volume4));

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
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final VolumeI item_volume0 = VolumeI.of(2, 98, 2, 98, 2, 98);

    final VolumeI item_volume1 = VolumeI.of(1, 2, 1, 2, 1, 2);

    final VolumeI item_volume2 = VolumeI.of(98, 99, 1, 2, 1, 2);

    final VolumeI item_volume3 = VolumeI.of(1, 2, 98, 99, 1, 2);

    final VolumeI item_volume4 = VolumeI.of(98, 99, 98, 99, 1, 2);

    Assert.assertTrue(tree.insert(item0, item_volume0));
    Assert.assertTrue(tree.insert(item1, item_volume1));
    Assert.assertTrue(tree.insert(item2, item_volume2));
    Assert.assertTrue(tree.insert(item3, item_volume3));
    Assert.assertTrue(tree.insert(item4, item_volume4));

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
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final VolumeI item_volume0 = VolumeI.of(2, 98, 2, 98, 2, 98);

    final VolumeI item_volume1 = VolumeI.of(1, 2, 1, 2, 1, 2);

    final VolumeI item_volume2 = VolumeI.of(98, 99, 1, 2, 1, 2);

    final VolumeI item_volume3 = VolumeI.of(1, 2, 98, 99, 1, 2);

    final VolumeI item_volume4 = VolumeI.of(98, 99, 98, 99, 1, 2);

    Assert.assertTrue(tree.insert(item0, item_volume0));
    Assert.assertTrue(tree.insert(item1, item_volume1));
    Assert.assertTrue(tree.insert(item2, item_volume2));
    Assert.assertTrue(tree.insert(item3, item_volume3));
    Assert.assertTrue(tree.insert(item4, item_volume4));

    final OctTreeIType<Object> tree_map = tree.map((x, ignored) -> x);
    Assert.assertEquals(tree, tree_map);
  }

  /**
   * The volumeFor query is correct.
   */

  @Test
  public final void testVolumeFor()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final VolumeI item_volume0 = VolumeI.of(2, 98, 2, 98, 2, 98);

    final VolumeI item_volume1 = VolumeI.of(1, 2, 1, 2, 1, 2);

    final VolumeI item_volume2 = VolumeI.of(98, 99, 1, 2, 1, 2);

    final VolumeI item_volume3 = VolumeI.of(1, 2, 98, 99, 1, 2);

    final VolumeI item_volume4 = VolumeI.of(98, 99, 98, 99, 1, 2);

    Assert.assertTrue(tree.insert(item0, item_volume0));
    Assert.assertTrue(tree.insert(item1, item_volume1));
    Assert.assertTrue(tree.insert(item2, item_volume2));
    Assert.assertTrue(tree.insert(item3, item_volume3));
    Assert.assertTrue(tree.insert(item4, item_volume4));

    Assert.assertEquals(item_volume0, tree.volumeFor(item0));
    Assert.assertEquals(item_volume1, tree.volumeFor(item1));
    Assert.assertEquals(item_volume2, tree.volumeFor(item2));
    Assert.assertEquals(item_volume3, tree.volumeFor(item3));
    Assert.assertEquals(item_volume4, tree.volumeFor(item4));
  }

  /**
   * Nonexistent items do not have volumes.
   */

  @Test
  public final void testVolumeForNonexistent()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);
    final Integer item0 = Integer.valueOf(0);

    this.expected.expect(NoSuchElementException.class);
    tree.volumeFor(item0);
    Assert.fail();
  }

  /**
   * Querying contained objects in the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y0Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 1, 2, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeI.of(97, 98, 97, 98, 97, 98), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y0Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(98, 99, 1, 2, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y1Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 98, 99, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y1Z0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y1Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(98, 99, 98, 99, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y0Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 1, 2, 98, 99);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeI.of(97, 98, 97, 98, 97, 98), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y0Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(98, 99, 1, 2, 98, 99);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y1Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 98, 99, 98, 99);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y1Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y1Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(
      98,
      99,
      98,
      99,
      98,
      99);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y0Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 1, 2, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeI.of(97, 98, 97, 98, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y0Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(98, 99, 1, 2, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y1Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(98, 99, 98, 99, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y1Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 98, 99, 1, 2);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y0Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 1, 2, 98, 99);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeI.of(97, 98, 97, 98, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y0Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(98, 99, 1, 2, 98, 99);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y1Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(
      98,
      99,
      98,
      99,
      98,
      99);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y1Z1()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(1, 2, 98, 99, 98, 99);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeI.of(1, 2, 1, 2, 1, 2), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingNot()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(10, 90, 10, 90, 1, 90);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeI.of(1, 9, 1, 9, 1, 9), set);
      Assert.assertEquals(0, (long) set.size());
    }
  }


  /**
   * Ray casting in the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testRaycastX0Y0Z0()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final VolumeI item_volume0 = VolumeI.of(
      10,
      20,
      11,
      21,
      0,
      99);
    Assert.assertTrue(tree.insert(item0, item_volume0));

    final Integer item1 = Integer.valueOf(1);
    final VolumeI item_volume1 = VolumeI.of(
      15,
      25,
      16,
      26,
      0,
      99);
    Assert.assertTrue(tree.insert(item1, item_volume1));

    final Integer item2 = Integer.valueOf(2);
    final VolumeI item_volume2 = VolumeI.of(
      25,
      35,
      26,
      36,
      0,
      99);
    Assert.assertTrue(tree.insert(item2, item_volume2));

    {
      final Vector3D origin = Vector3D.of(0.0, 0.0, 1.0);
      final Vector3D direction = Vector3D.of(1.0, 1.0, 0.0);
      final Ray3D ray = Ray3D.of(origin, direction);
      final SortedSet<OctTreeRaycastResultI<Object>> items = new TreeSet<>();
      tree.raycast(ray, items);

      Assert.assertEquals(3, (long) items.size());
      final Iterator<OctTreeRaycastResultI<Object>> iter = items.iterator();

      final OctTreeRaycastResultI<Object> result0 = iter.next();
      Assert.assertEquals(item0, result0.item());
      Assert.assertEquals(item_volume0, result0.volume());

      final OctTreeRaycastResultI<Object> result1 = iter.next();
      Assert.assertEquals(item1, result1.item());
      Assert.assertEquals(item_volume1, result1.volume());

      final OctTreeRaycastResultI<Object> result2 = iter.next();
      Assert.assertEquals(item2, result2.item());
      Assert.assertEquals(item_volume2, result2.volume());
    }

    {
      final Vector3D origin = Vector3D.of(0.0, 0.0, 0.0);
      final Vector3D direction = Vector3D.of(1.0, 0.0, 1.0);
      final Ray3D ray = Ray3D.of(origin, direction);
      final SortedSet<OctTreeRaycastResultI<Object>> items = new TreeSet<>();
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
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    cb.setTrimOnRemove(false);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Integer> tree = this.create(c);

    final List<Integer> items = new ArrayList<>(9);
    for (int index = 0; index < 9; ++index) {
      items.add(Integer.valueOf(index));
    }

    final List<VolumeI> volumes = new ArrayList<>(9);

    volumes.add(VolumeI.of(2, 98, 2, 98, 2, 98));

    volumes.add(VolumeI.of(1, 2, 1, 2, 1, 2));
    volumes.add(VolumeI.of(98, 99, 1, 2, 1, 2));
    volumes.add(VolumeI.of(1, 2, 98, 99, 1, 2));
    volumes.add(VolumeI.of(98, 99, 98, 99, 1, 2));

    volumes.add(VolumeI.of(1, 2, 1, 2, 98, 99));
    volumes.add(VolumeI.of(98, 99, 1, 2, 98, 99));
    volumes.add(VolumeI.of(1, 2, 98, 99, 98, 99));
    volumes.add(VolumeI.of(98, 99, 98, 99, 98, 99));

    for (int index = 0; index < 9; ++index) {
      Assert.assertTrue(tree.insert(items.get(index), volumes.get(index)));
    }

    {
      final int count = OctTreeIContract.countOctants(tree);
      Assert.assertEquals(273, count);
    }

    for (int index = 0; index < 9; ++index) {
      Assert.assertTrue(tree.remove(items.get(index)));

      tree.trim();
      final int count = OctTreeIContract.countOctants(tree);

      System.out.println("index: " + index + " count: " + count);
      if (index != 8) {
        Assert.assertEquals(273 - (index * 32), (long) count);
      } else {
        Assert.assertEquals(1, (long) count);
      }
    }

    Assert.assertTrue(tree.isEmpty());
  }

  /**
   * Trimming on removal works.
   */

  @Test
  public final void testTrimOnRemoval()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    cb.setTrimOnRemove(true);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final VolumeI item_volume0 = VolumeI.of(2, 98, 2, 98, 2, 98);
    final VolumeI item_volume1 = VolumeI.of(1, 2, 1, 2, 1, 2);
    final VolumeI item_volume2 = VolumeI.of(98, 99, 1, 2, 1, 2);
    final VolumeI item_volume3 = VolumeI.of(1, 2, 98, 99, 1, 2);
    final VolumeI item_volume4 = VolumeI.of(98, 99, 98, 99, 1, 2);

    Assert.assertTrue(tree.insert(item0, item_volume0));
    Assert.assertTrue(tree.insert(item1, item_volume1));
    Assert.assertTrue(tree.insert(item2, item_volume2));
    Assert.assertTrue(tree.insert(item3, item_volume3));
    Assert.assertTrue(tree.insert(item4, item_volume4));

    final int count_0 = OctTreeIContract.countOctants(tree);
    Assert.assertEquals(137, (long) count_0);

    Assert.assertTrue(tree.remove(item1));

    final int count_1 = OctTreeIContract.countOctants(tree);
    Assert.assertEquals(137 - 32, (long) count_1);

    Assert.assertTrue(tree.remove(item2));

    final int count_2 = OctTreeIContract.countOctants(tree);
    Assert.assertEquals(137 - (32 + 32), (long) count_2);

    Assert.assertTrue(tree.remove(item3));

    final int count_3 = OctTreeIContract.countOctants(tree);
    Assert.assertEquals(137 - (32 + 32 + 32), (long) count_3);

    Assert.assertTrue(tree.remove(item4));

    final int count_4 = OctTreeIContract.countOctants(tree);
    Assert.assertEquals(1, (long) count_4);

    Assert.assertTrue(tree.remove(item0));

    final int count_5 = OctTreeIContract.countOctants(tree);
    Assert.assertEquals(1, (long) count_5);
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversal()
  {
    final VolumeI container = VolumeI.of(
      -1000,
      1000,
      -1000,
      1000,
      -1000,
      1000);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Integer> tree = this.create(c);

    final Generator<VolumeI> gen =
      new VolumeIContainedGenerator(container);

    final Map<Integer, VolumeI> inserted = new HashMap<>(500);
    for (int index = 0; index < 500; ++index) {
      final Integer b_index = Integer.valueOf(index);
      final VolumeI volume = gen.next();
      Assert.assertTrue(tree.insert(b_index, volume));
      inserted.put(b_index, volume);
    }

    final Map<Integer, VolumeI> found = new HashMap<>(500);
    tree.iterateOctants(Integer.valueOf(0), (context, octant, depth) -> {
      Assert.assertTrue(VolumesI.contains(container, octant.volume()));

      final Map<Integer, VolumeI> objects = octant.objects();
      for (final Map.Entry<Integer, VolumeI> e : objects.entrySet()) {
        Assert.assertFalse(found.containsKey(e.getKey()));
        found.put(e.getKey(), e.getValue());
      }
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(inserted, found);
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop0()
  {
    final VolumeI container =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40);
    cb.setMinimumOctantWidth(40);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeI.of(10, 20, 10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(9, (long) OctTreeIContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Integer.valueOf(0), (context, quadrant, depth) -> {
      if (count.get() == 1) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(1, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop1()
  {
    final VolumeI container =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40);
    cb.setMinimumOctantWidth(40);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeI.of(10, 20, 10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(9, (long) OctTreeIContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Integer.valueOf(0), (context, quadrant, depth) -> {
      if (count.get() == 2) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(2, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop2()
  {
    final VolumeI container =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40);
    cb.setMinimumOctantWidth(40);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeI.of(10, 20, 10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(9, (long) OctTreeIContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Integer.valueOf(0), (context, quadrant, depth) -> {
      if (count.get() == 3) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(3, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop3()
  {
    final VolumeI container =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40);
    cb.setMinimumOctantWidth(40);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeI.of(10, 20, 10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(9, (long) OctTreeIContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Integer.valueOf(0), (context, quadrant, depth) -> {
      if (count.get() == 4) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(4, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop4()
  {
    final VolumeI container =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40);
    cb.setMinimumOctantWidth(40);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeI.of(10, 20, 10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(9, (long) OctTreeIContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Integer.valueOf(0), (context, quadrant, depth) -> {
      if (count.get() == 5) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(5, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop5()
  {
    final VolumeI container =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40);
    cb.setMinimumOctantWidth(40);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeI.of(10, 20, 10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(9, (long) OctTreeIContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Integer.valueOf(0), (context, quadrant, depth) -> {
      if (count.get() == 6) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(6, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop6()
  {
    final VolumeI container =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40);
    cb.setMinimumOctantWidth(40);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeI.of(10, 20, 10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(9, (long) OctTreeIContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Integer.valueOf(0), (context, quadrant, depth) -> {
      if (count.get() == 7) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(7, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop7()
  {
    final VolumeI container =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40);
    cb.setMinimumOctantWidth(40);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeI.of(10, 20, 10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(9, (long) OctTreeIContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Integer.valueOf(0), (context, quadrant, depth) -> {
      if (count.get() == 8) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(8, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop8()
  {
    final VolumeI container =
      VolumeI.of(0, 100, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40);
    cb.setMinimumOctantWidth(40);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeI.of(10, 20, 10, 20, 10, 20)));
    tree.trim();

    Assert.assertEquals(9, (long) OctTreeIContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Integer.valueOf(0), (context, quadrant, depth) -> {
      if (count.get() == 9) {
        return TreeVisitResult.RESULT_TERMINATE;
      }

      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });

    Assert.assertEquals(9, (long) count.get());
  }

  /**
   * Simple raycast test.
   */

  @Test
  public final void testRaycastSimple()
  {
    final VolumeI container = VolumeI.of(0, 512, 0, 512, 0, 512);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Integer> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);

    Assert.assertTrue(tree.insert(
      item0,
      VolumeI.of(32, 80, 32, 80, 0, 511)));

    Assert.assertTrue(tree.insert(
      item1,
      VolumeI.of(
        400,
        400 + 32,
        32,
        80,
        0,
        511)));

    Assert.assertTrue(tree.insert(
      item2,
      VolumeI.of(400, 480, 400, 480, 0, 511)));

    final Ray3D ray = Ray3D.of(
      Vector3D.of(0.0, 0.0, 1.0),
      Vectors3D.normalize(Vector3D.of(511.0, 511.0, 0.0)));

    final SortedSet<OctTreeRaycastResultI<Integer>> items = new TreeSet<>();
    tree.raycast(ray, items);

    Assert.assertEquals(2, (long) items.size());
    final Iterator<OctTreeRaycastResultI<Integer>> iter = items.iterator();

    {
      final OctTreeRaycastResultI<Integer> rr = iter.next();
      final VolumeI r = rr.volume();
      Assert.assertEquals(item0, rr.item());
      Assert.assertEquals(32, r.minimumX());
      Assert.assertEquals(32, r.minimumY());
      Assert.assertEquals(80, r.maximumX());
      Assert.assertEquals(80, r.maximumY());
    }

    {
      final OctTreeRaycastResultI<Integer> rr = iter.next();
      final VolumeI r = rr.volume();
      Assert.assertEquals(item2, rr.item());
      Assert.assertEquals(400, r.minimumX());
      Assert.assertEquals(400, r.minimumY());
      Assert.assertEquals(480, r.maximumX());
      Assert.assertEquals(480, r.maximumY());
    }

    Assert.assertFalse(iter.hasNext());
  }

  /**
   * The octant cannot be split due to small width.
   */

  @Test
  public final void testInsertCannotSplitWidth()
  {
    final VolumeI volume =
      VolumeI.of(0, 1, 0, 100, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(0, 1, 0, 1, 0, 1);
    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertEquals(1, (long) OctTreeIContract.countOctants(tree));
  }

  /**
   * The octant cannot be split due to small height.
   */

  @Test
  public final void testInsertCannotSplitHeight()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 1, 0, 100);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(0, 1, 0, 1, 0, 1);
    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertEquals(1, (long) OctTreeIContract.countOctants(tree));
  }

  /**
   * The octant cannot be split due to small depth.
   */

  @Test
  public final void testInsertCannotSplitDepth()
  {
    final VolumeI volume =
      VolumeI.of(0, 100, 0, 100, 0, 1);

    final OctTreeConfigurationI.Builder cb = OctTreeConfigurationI.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationI c = cb.build();

    final OctTreeIType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeI item_volume = VolumeI.of(0, 1, 0, 1, 0, 1);
    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertEquals(1, (long) OctTreeIContract.countOctants(tree));
  }
}
