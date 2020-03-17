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

import com.io7m.jregions.core.unparameterized.volumes.VolumeD;
import com.io7m.jregions.core.unparameterized.volumes.VolumesD;
import com.io7m.jspatial.api.Ray3D;
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationD;
import com.io7m.jspatial.api.octtrees.OctTreeDType;
import com.io7m.jspatial.api.octtrees.OctTreeRaycastResultD;
import com.io7m.jspatial.tests.api.VolumeDContainedGenerator;
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

public abstract class OctTreeDContract
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  private static int countOctants(final OctTreeDType<?> tree)
  {
    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(count, (context, quadrant, depth) -> {
      count.incrementAndGet();
      return TreeVisitResult.RESULT_CONTINUE;
    });
    return count.get();
  }

  protected abstract <T> OctTreeDType<T> create(OctTreeConfigurationD config);

  /**
   * Simple identities.
   */

  @Test
  public final void testIdentities()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);
    Assert.assertTrue(tree.isEmpty());
    Assert.assertEquals(0L, tree.size());
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
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      -100.0,
      200.0,
      -100.0,
      200.0,
      -100.0,
      200.0);
    Assert.assertFalse(tree.insert(item, item_volume));
  }

  /**
   * Inserting a tiny object works.
   */

  @Test
  public final void testInsertTiny()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    cb.setMinimumOctantHeight(2.0);
    cb.setMinimumOctantWidth(2.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      1.1,
      1.0,
      1.1,
      1.0,
      1.1);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testInsertX0Y0Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testInsertX1Y0Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X0Y1Z0 quadrant works.
   */

  @Test
  public final void testInsertX0Y1Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y1Z0 quadrant works.
   */

  @Test
  public final void testInsertX1Y1Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }


  /**
   * Inserting an object into the X0Y0Z1 quadrant works.
   */

  @Test
  public final void testInsertX0Y0Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testInsertX1Y0Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X0Y1Z1 quadrant works.
   */

  @Test
  public final void testInsertX0Y1Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());
  }

  /**
   * Inserting an object into the X1Y1Z1 quadrant works.
   */

  @Test
  public final void testInsertX1Y1Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
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
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      2.0,
      98.0,
      2.0,
      98.0,
      2.0,
      98.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    Assert.assertTrue(tree.contains(item));
    Assert.assertEquals(1L, tree.size());
    Assert.assertFalse(tree.isEmpty());

    Assert.assertTrue(tree.insert(item, item_volume));
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
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final VolumeD item_volume0 = VolumeD.of(
      2.0,
      98.0,
      2.0,
      98.0,
      2.0,
      98.0);

    final VolumeD item_volume1 = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      1.0,
      2.0);

    final VolumeD item_volume2 = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      1.0,
      2.0);

    final VolumeD item_volume3 = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      1.0,
      2.0);

    final VolumeD item_volume4 = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      1.0,
      2.0);

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
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final VolumeD item_volume0 = VolumeD.of(
      2.0,
      98.0,
      2.0,
      98.0,
      2.0,
      98.0);

    final VolumeD item_volume1 = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      1.0,
      2.0);

    final VolumeD item_volume2 = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      1.0,
      2.0);

    final VolumeD item_volume3 = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      1.0,
      2.0);

    final VolumeD item_volume4 = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      1.0,
      2.0);

    Assert.assertTrue(tree.insert(item0, item_volume0));
    Assert.assertTrue(tree.insert(item1, item_volume1));
    Assert.assertTrue(tree.insert(item2, item_volume2));
    Assert.assertTrue(tree.insert(item3, item_volume3));
    Assert.assertTrue(tree.insert(item4, item_volume4));

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
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final VolumeD item_volume0 = VolumeD.of(
      2.0,
      98.0,
      2.0,
      98.0,
      2.0,
      98.0);

    final VolumeD item_volume1 = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      1.0,
      2.0);

    final VolumeD item_volume2 = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      1.0,
      2.0);

    final VolumeD item_volume3 = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      1.0,
      2.0);

    final VolumeD item_volume4 = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      1.0,
      2.0);

    Assert.assertTrue(tree.insert(item0, item_volume0));
    Assert.assertTrue(tree.insert(item1, item_volume1));
    Assert.assertTrue(tree.insert(item2, item_volume2));
    Assert.assertTrue(tree.insert(item3, item_volume3));
    Assert.assertTrue(tree.insert(item4, item_volume4));

    final OctTreeDType<Object> tree_map = tree.map((x, ignored) -> x);
    Assert.assertEquals(tree, tree_map);
  }

  /**
   * The volumeFor query is correct.
   */

  @Test
  public final void testVolumeFor()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final VolumeD item_volume0 = VolumeD.of(
      2.0,
      98.0,
      2.0,
      98.0,
      2.0,
      98.0);

    final VolumeD item_volume1 = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      1.0,
      2.0);

    final VolumeD item_volume2 = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      1.0,
      2.0);

    final VolumeD item_volume3 = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      1.0,
      2.0);

    final VolumeD item_volume4 = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      1.0,
      2.0);

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
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);
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
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeD.of(97.0, 98.0, 97.0, 98.0, 97.0, 98.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y0Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y1Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y1Z0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y1Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y0Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeD.of(97.0, 98.0, 97.0, 98.0, 97.0, 98.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y0Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y1Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y1Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y1Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.containedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y0Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeD.of(97.0, 98.0, 97.0, 98.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y0Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y1Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y1Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      1.0,
      2.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y0Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeD.of(97.0, 98.0, 97.0, 98.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y0Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y1Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y1Z1()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      98.0,
      99.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingNot()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      10.0,
      90.0,
      10.0,
      90.0,
      1.0,
      90.0);
    Assert.assertTrue(tree.insert(item, item_volume));

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(item_volume, set);
      Assert.assertEquals(1L, (long) set.size());
      Assert.assertTrue(set.contains(item));
    }

    {
      final HashSet<Object> set = new HashSet<>(1);
      tree.overlappedBy(VolumeD.of(1.0, 9.0, 1.0, 9.0, 1.0, 9.0), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }


  /**
   * Ray casting in the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testRaycastX0Y0Z0()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final VolumeD item_volume0 = VolumeD.of(
      10.0,
      20.0,
      11.0,
      21.0,
      0.0,
      99.0);
    Assert.assertTrue(tree.insert(item0, item_volume0));

    final Integer item1 = Integer.valueOf(1);
    final VolumeD item_volume1 = VolumeD.of(
      15.0,
      25.0,
      16.0,
      26.0,
      0.0,
      99.0);
    Assert.assertTrue(tree.insert(item1, item_volume1));

    final Integer item2 = Integer.valueOf(2);
    final VolumeD item_volume2 = VolumeD.of(
      25.0,
      35.0,
      26.0,
      36.0,
      0.0,
      99.0);
    Assert.assertTrue(tree.insert(item2, item_volume2));

    {
      final Vector3D origin = Vector3D.of(0.0, 0.0, 1.0);
      final Vector3D direction = Vector3D.of(1.0, 1.0, 0.0);
      final Ray3D ray = Ray3D.of(origin, direction);
      final SortedSet<OctTreeRaycastResultD<Object>> items = new TreeSet<>();
      tree.raycast(ray, items);

      Assert.assertEquals(3L, (long) items.size());
      final Iterator<OctTreeRaycastResultD<Object>> iter = items.iterator();

      final OctTreeRaycastResultD<Object> result0 = iter.next();
      Assert.assertEquals(item0, result0.item());
      Assert.assertEquals(item_volume0, result0.volume());

      final OctTreeRaycastResultD<Object> result1 = iter.next();
      Assert.assertEquals(item1, result1.item());
      Assert.assertEquals(item_volume1, result1.volume());

      final OctTreeRaycastResultD<Object> result2 = iter.next();
      Assert.assertEquals(item2, result2.item());
      Assert.assertEquals(item_volume2, result2.volume());
    }

    {
      final Vector3D origin = Vector3D.of(0.0, 0.0, 0.0);
      final Vector3D direction = Vector3D.of(1.0, 0.0, 1.0);
      final Ray3D ray = Ray3D.of(origin, direction);
      final SortedSet<OctTreeRaycastResultD<Object>> items = new TreeSet<>();
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
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    cb.setTrimOnRemove(false);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Integer> tree = this.create(c);

    final List<Integer> items = new ArrayList<>(9);
    for (int index = 0; index < 9; ++index) {
      items.add(Integer.valueOf(index));
    }

    final List<VolumeD> volumes = new ArrayList<>(9);

    volumes.add(VolumeD.of(2.0, 98.0, 2.0, 98.0, 2.0, 98.0));

    volumes.add(VolumeD.of(1.0, 2.0, 1.0, 2.0, 1.0, 2.0));
    volumes.add(VolumeD.of(98.0, 99.0, 1.0, 2.0, 1.0, 2.0));
    volumes.add(VolumeD.of(1.0, 2.0, 98.0, 99.0, 1.0, 2.0));
    volumes.add(VolumeD.of(98.0, 99.0, 98.0, 99.0, 1.0, 2.0));

    volumes.add(VolumeD.of(1.0, 2.0, 1.0, 2.0, 98.0, 99.0));
    volumes.add(VolumeD.of(98.0, 99.0, 1.0, 2.0, 98.0, 99.0));
    volumes.add(VolumeD.of(1.0, 2.0, 98.0, 99.0, 98.0, 99.0));
    volumes.add(VolumeD.of(98.0, 99.0, 98.0, 99.0, 98.0, 99.0));

    for (int index = 0; index < 9; ++index) {
      Assert.assertTrue(tree.insert(items.get(index), volumes.get(index)));
    }

    {
      final int count = OctTreeDContract.countOctants(tree);
      Assert.assertEquals(265L, count);
    }

    for (int index = 0; index < 9; ++index) {
      Assert.assertTrue(tree.remove(items.get(index)));

      tree.trim();
      final int count = OctTreeDContract.countOctants(tree);

      System.out.println("index: " + index + " count: " + count);
      if (index != 8) {
        Assert.assertEquals(265L - (index * 32L), (long) count);
      } else {
        Assert.assertEquals(1L, (long) count);
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
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    cb.setTrimOnRemove(true);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final VolumeD item_volume0 = VolumeD.of(
      2.0,
      98.0,
      2.0,
      98.0,
      2.0,
      98.0);

    final VolumeD item_volume1 = VolumeD.of(
      1.0,
      2.0,
      1.0,
      2.0,
      1.0,
      2.0);

    final VolumeD item_volume2 = VolumeD.of(
      98.0,
      99.0,
      1.0,
      2.0,
      1.0,
      2.0);

    final VolumeD item_volume3 = VolumeD.of(
      1.0,
      2.0,
      98.0,
      99.0,
      1.0,
      2.0);

    final VolumeD item_volume4 = VolumeD.of(
      98.0,
      99.0,
      98.0,
      99.0,
      1.0,
      2.0);

    Assert.assertTrue(tree.insert(item0, item_volume0));
    Assert.assertTrue(tree.insert(item1, item_volume1));
    Assert.assertTrue(tree.insert(item2, item_volume2));
    Assert.assertTrue(tree.insert(item3, item_volume3));
    Assert.assertTrue(tree.insert(item4, item_volume4));

    final int count_0 = OctTreeDContract.countOctants(tree);
    Assert.assertEquals(137L, (long) count_0);

    Assert.assertTrue(tree.remove(item1));

    final int count_1 = OctTreeDContract.countOctants(tree);
    Assert.assertEquals(137L - 32L, (long) count_1);

    Assert.assertTrue(tree.remove(item2));

    final int count_2 = OctTreeDContract.countOctants(tree);
    Assert.assertEquals(137L - (32L + 32L), (long) count_2);

    Assert.assertTrue(tree.remove(item3));

    final int count_3 = OctTreeDContract.countOctants(tree);
    Assert.assertEquals(137L - (32L + 32L + 32L), (long) count_3);

    Assert.assertTrue(tree.remove(item4));

    final int count_4 = OctTreeDContract.countOctants(tree);
    Assert.assertEquals(1L, (long) count_4);

    Assert.assertTrue(tree.remove(item0));

    final int count_5 = OctTreeDContract.countOctants(tree);
    Assert.assertEquals(1L, (long) count_5);
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversal()
  {
    final VolumeD container = VolumeD.of(
      -1000.0,
      1000.0,
      -1000.0,
      1000.0,
      -1000.0,
      1000.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Integer> tree = this.create(c);

    final Generator<VolumeD> gen =
      new VolumeDContainedGenerator(container);

    final Map<Integer, VolumeD> inserted = new HashMap<>(500);
    for (int index = 0; index < 500; ++index) {
      final Integer b_index = Integer.valueOf(index);
      final VolumeD volume = gen.next();
      Assert.assertTrue(tree.insert(b_index, volume));
      inserted.put(b_index, volume);
    }

    final Map<Integer, VolumeD> found = new HashMap<>(500);
    tree.iterateOctants(
      Integer.valueOf(0), (context, octant, depth) -> {
        Assert.assertTrue(VolumesD.contains(container, octant.volume()));

        final Map<Integer, VolumeD> objects = octant.objects();
        for (final Map.Entry<Integer, VolumeD> e : objects.entrySet()) {
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
    final VolumeD container =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(
      Integer.valueOf(0), (context, quadrant, depth) -> {
        if (count.get() == 1) {
          return TreeVisitResult.RESULT_TERMINATE;
        }

        count.incrementAndGet();
        return TreeVisitResult.RESULT_CONTINUE;
      });

    Assert.assertEquals(1L, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop1()
  {
    final VolumeD container =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(
      Integer.valueOf(0), (context, quadrant, depth) -> {
        if (count.get() == 2) {
          return TreeVisitResult.RESULT_TERMINATE;
        }

        count.incrementAndGet();
        return TreeVisitResult.RESULT_CONTINUE;
      });

    Assert.assertEquals(2L, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop2()
  {
    final VolumeD container =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(
      Integer.valueOf(0), (context, quadrant, depth) -> {
        if (count.get() == 3) {
          return TreeVisitResult.RESULT_TERMINATE;
        }

        count.incrementAndGet();
        return TreeVisitResult.RESULT_CONTINUE;
      });

    Assert.assertEquals(3L, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop3()
  {
    final VolumeD container =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(
      Integer.valueOf(0), (context, quadrant, depth) -> {
        if (count.get() == 4) {
          return TreeVisitResult.RESULT_TERMINATE;
        }

        count.incrementAndGet();
        return TreeVisitResult.RESULT_CONTINUE;
      });

    Assert.assertEquals(4L, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop4()
  {
    final VolumeD container =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(
      Integer.valueOf(0), (context, quadrant, depth) -> {
        if (count.get() == 5) {
          return TreeVisitResult.RESULT_TERMINATE;
        }

        count.incrementAndGet();
        return TreeVisitResult.RESULT_CONTINUE;
      });

    Assert.assertEquals(5L, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop5()
  {
    final VolumeD container =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(
      Integer.valueOf(0), (context, quadrant, depth) -> {
        if (count.get() == 6) {
          return TreeVisitResult.RESULT_TERMINATE;
        }

        count.incrementAndGet();
        return TreeVisitResult.RESULT_CONTINUE;
      });

    Assert.assertEquals(6L, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop6()
  {
    final VolumeD container =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(
      Integer.valueOf(0), (context, quadrant, depth) -> {
        if (count.get() == 7) {
          return TreeVisitResult.RESULT_TERMINATE;
        }

        count.incrementAndGet();
        return TreeVisitResult.RESULT_CONTINUE;
      });

    Assert.assertEquals(7L, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop7()
  {
    final VolumeD container =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(
      Integer.valueOf(0), (context, quadrant, depth) -> {
        if (count.get() == 8) {
          return TreeVisitResult.RESULT_TERMINATE;
        }

        count.incrementAndGet();
        return TreeVisitResult.RESULT_CONTINUE;
      });

    Assert.assertEquals(8L, (long) count.get());
  }

  /**
   * Octant traversal works.
   */

  @Test
  public final void testOctantTraversalStop8()
  {
    final VolumeD container =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      VolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0)));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(
      Integer.valueOf(0), (context, quadrant, depth) -> {
        if (count.get() == 9) {
          return TreeVisitResult.RESULT_TERMINATE;
        }

        count.incrementAndGet();
        return TreeVisitResult.RESULT_CONTINUE;
      });

    Assert.assertEquals(9L, (long) count.get());
  }

  /**
   * Simple raycast test.
   */

  @Test
  public final void testRaycastSimple()
  {
    final VolumeD container = VolumeD.of(
      0.0,
      512.0,
      0.0,
      512.0,
      0.0,
      512.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Integer> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);

    Assert.assertTrue(tree.insert(
      item0,
      VolumeD.of(
        32.0,
        80.0,
        32.0,
        80.0,
        0.0,
        511.0)));

    Assert.assertTrue(tree.insert(
      item1,
      VolumeD.of(
        400.0,
        400.0 + 32.0,
        32.0,
        80.0,
        0.0,
        511.0)));

    Assert.assertTrue(tree.insert(
      item2,
      VolumeD.of(
        400.0,
        480.0,
        400.0,
        480.0,
        0.0,
        511.0)));

    final Ray3D ray = Ray3D.of(
      Vector3D.of(0.0, 0.0, 1.0),
      Vectors3D.normalize(Vector3D.of(511.0, 511.0, 0.0)));

    final SortedSet<OctTreeRaycastResultD<Integer>> items = new TreeSet<>();
    tree.raycast(ray, items);

    Assert.assertEquals(2L, (long) items.size());
    final Iterator<OctTreeRaycastResultD<Integer>> iter = items.iterator();

    {
      final OctTreeRaycastResultD<Integer> rr = iter.next();
      final VolumeD r = rr.volume();
      Assert.assertEquals(item0, rr.item());
      Assert.assertEquals(32.0, r.minimumX(), 0.0);
      Assert.assertEquals(32.0, r.minimumY(), 0.0);
      Assert.assertEquals(80.0, r.maximumX(), 0.0);
      Assert.assertEquals(80.0, r.maximumY(), 0.0);
    }

    {
      final OctTreeRaycastResultD<Integer> rr = iter.next();
      final VolumeD r = rr.volume();
      Assert.assertEquals(item2, rr.item());
      Assert.assertEquals(400.0, r.minimumX(), 0.0);
      Assert.assertEquals(400.0, r.minimumY(), 0.0);
      Assert.assertEquals(480.0, r.maximumX(), 0.0);
      Assert.assertEquals(480.0, r.maximumY(), 0.0);
    }

    Assert.assertFalse(iter.hasNext());
  }

  /**
   * The octant cannot be split due to small width.
   */

  @Test
  public final void testInsertCannotSplitWidth()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 1.0, 0.0, 100.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      0.0,
      1.0,
      0.0,
      1.0,
      0.0,
      1.0);
    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertEquals(1L, (long) OctTreeDContract.countOctants(tree));
  }

  /**
   * The octant cannot be split due to small height.
   */

  @Test
  public final void testInsertCannotSplitHeight()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 1.0, 0.0, 100.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      0.0,
      1.0,
      0.0,
      1.0,
      0.0,
      1.0);
    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertEquals(1L, (long) OctTreeDContract.countOctants(tree));
  }

  /**
   * The octant cannot be split due to small depth.
   */

  @Test
  public final void testInsertCannotSplitDepth()
  {
    final VolumeD volume =
      VolumeD.of(0.0, 100.0, 0.0, 100.0, 0.0, 1.0);

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final VolumeD item_volume = VolumeD.of(
      0.0,
      1.0,
      0.0,
      1.0,
      0.0,
      1.0);
    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertEquals(1L, (long) OctTreeDContract.countOctants(tree));
  }
}
