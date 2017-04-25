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

package com.io7m.jspatial.tests.api.octtrees;

import com.io7m.jfunctional.Unit;
import com.io7m.jspatial.api.BoundingVolumeD;
import com.io7m.jspatial.api.BoundingVolumeL;
import com.io7m.jspatial.api.RayI3D;
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationD;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationL;
import com.io7m.jspatial.api.octtrees.OctTreeDType;
import com.io7m.jspatial.api.octtrees.OctTreeLType;
import com.io7m.jspatial.api.octtrees.OctTreeRaycastResultD;
import com.io7m.jspatial.tests.api.BoundingVolumeDContainedGenerator;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3L;
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(-100.0, -100.0, -100.0),
      new VectorI3D(200.0, 200.0, 200.0));
    Assert.assertFalse(tree.insert(item, item_volume));
  }

  /**
   * Inserting a tiny object works.
   */

  @Test
  public final void testInsertTiny()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    cb.setMinimumOctantHeight(2.0);
    cb.setMinimumOctantWidth(2.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(1.1, 1.1, 1.1));
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(2.0, 2.0, 2.0));
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 1.0),
      new VectorI3D(99.0, 2.0, 2.0));
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 1.0),
      new VectorI3D(2.0, 99.0, 2.0));
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 1.0),
      new VectorI3D(99.0, 99.0, 2.0));
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 98.0),
      new VectorI3D(2.0, 2.0, 99.0));
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 98.0),
      new VectorI3D(99.0, 2.0, 99.0));
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 98.0),
      new VectorI3D(2.0, 99.0, 99.0));
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 98.0),
      new VectorI3D(99.0, 99.0, 99.0));
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(2.0, 2.0, 2.0),
      new VectorI3D(98.0, 98.0, 98.0));
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final BoundingVolumeD item_volume0 = BoundingVolumeD.of(
      new VectorI3D(2.0, 2.0, 2.0),
      new VectorI3D(98.0, 98.0, 98.0));

    final BoundingVolumeD item_volume1 = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(2.0, 2.0, 2.0));

    final BoundingVolumeD item_volume2 = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 1.0),
      new VectorI3D(99.0, 2.0, 2.0));

    final BoundingVolumeD item_volume3 = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 1.0),
      new VectorI3D(2.0, 99.0, 2.0));

    final BoundingVolumeD item_volume4 = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 1.0),
      new VectorI3D(99.0, 99.0, 2.0));

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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final BoundingVolumeD item_volume0 = BoundingVolumeD.of(
      new VectorI3D(2.0, 2.0, 2.0),
      new VectorI3D(98.0, 98.0, 98.0));

    final BoundingVolumeD item_volume1 = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(2.0, 2.0, 2.0));

    final BoundingVolumeD item_volume2 = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 1.0),
      new VectorI3D(99.0, 2.0, 2.0));

    final BoundingVolumeD item_volume3 = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 1.0),
      new VectorI3D(2.0, 99.0, 2.0));

    final BoundingVolumeD item_volume4 = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 1.0),
      new VectorI3D(99.0, 99.0, 2.0));

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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final BoundingVolumeD item_volume0 = BoundingVolumeD.of(
      new VectorI3D(2.0, 2.0, 2.0),
      new VectorI3D(98.0, 98.0, 98.0));

    final BoundingVolumeD item_volume1 = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(2.0, 2.0, 2.0));

    final BoundingVolumeD item_volume2 = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 1.0),
      new VectorI3D(99.0, 2.0, 2.0));

    final BoundingVolumeD item_volume3 = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 1.0),
      new VectorI3D(2.0, 99.0, 2.0));

    final BoundingVolumeD item_volume4 = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 1.0),
      new VectorI3D(99.0, 99.0, 2.0));

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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);
    final Integer item3 = Integer.valueOf(3);
    final Integer item4 = Integer.valueOf(4);

    final BoundingVolumeD item_volume0 = BoundingVolumeD.of(
      new VectorI3D(2.0, 2.0, 2.0),
      new VectorI3D(98.0, 98.0, 98.0));

    final BoundingVolumeD item_volume1 = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(2.0, 2.0, 2.0));

    final BoundingVolumeD item_volume2 = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 1.0),
      new VectorI3D(99.0, 2.0, 2.0));

    final BoundingVolumeD item_volume3 = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 1.0),
      new VectorI3D(2.0, 99.0, 2.0));

    final BoundingVolumeD item_volume4 = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 1.0),
      new VectorI3D(99.0, 99.0, 2.0));

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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(2.0, 2.0, 2.0));
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
      tree.containedBy(BoundingVolumeD.of(
        new VectorI3D(97.0, 97.0, 97.0),
        new VectorI3D(98.0, 98.0, 98.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y0Z0()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 1.0),
      new VectorI3D(99.0, 2.0, 2.0));
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
      tree.containedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y1Z0()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 1.0),
      new VectorI3D(2.0, 99.0, 2.0));
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
      tree.containedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y1Z0 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y1Z0()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 1.0),
      new VectorI3D(99.0, 99.0, 2.0));
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
      tree.containedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y0Z1()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 98.0),
      new VectorI3D(2.0, 2.0, 99.0));
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
      tree.containedBy(BoundingVolumeD.of(
        new VectorI3D(97.0, 97.0, 97.0),
        new VectorI3D(98.0, 98.0, 98.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y0Z1()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 98.0),
      new VectorI3D(99.0, 2.0, 99.0));
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
      tree.containedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX0Y1Z1()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 98.0),
      new VectorI3D(2.0, 99.0, 99.0));
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
      tree.containedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y1Z1 quadrant works.
   */

  @Test
  public final void testContainedObjectsX1Y1Z1()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 98.0),
      new VectorI3D(99.0, 99.0, 99.0));
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
      tree.containedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y0Z0()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(2.0, 2.0, 2.0));
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
      tree.overlappedBy(BoundingVolumeD.of(
        new VectorI3D(97.0, 97.0, 1.0),
        new VectorI3D(98.0, 98.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y0Z0()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 1.0),
      new VectorI3D(99.0, 2.0, 2.0));
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
      tree.overlappedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y1Z0()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 1.0),
      new VectorI3D(99.0, 99.0, 2.0));
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
      tree.overlappedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z0 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y1Z0()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 1.0),
      new VectorI3D(2.0, 99.0, 2.0));
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
      tree.overlappedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y0Z1()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 98.0),
      new VectorI3D(2.0, 2.0, 99.0));
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
      tree.overlappedBy(BoundingVolumeD.of(
        new VectorI3D(97.0, 97.0, 1.0),
        new VectorI3D(98.0, 98.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y0Z1()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 98.0),
      new VectorI3D(99.0, 2.0, 99.0));
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
      tree.overlappedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X1Y0Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX1Y1Z1()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 98.0),
      new VectorI3D(99.0, 99.0, 99.0));
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
      tree.overlappedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }

  /**
   * Querying contained objects in the X0Y1Z1 quadrant works.
   */

  @Test
  public final void testOverlappingObjectsX0Y1Z1()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 98.0),
      new VectorI3D(2.0, 99.0, 99.0));
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
      tree.overlappedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(2.0, 2.0, 2.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }


  /**
   * Querying contained objects in the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testOverlappingNot()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(10.0, 10.0, 1.0),
      new VectorI3D(90.0, 90.0, 90.0));
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
      tree.overlappedBy(BoundingVolumeD.of(
        new VectorI3D(1.0, 1.0, 1.0),
        new VectorI3D(9.0, 9.0, 9.0)), set);
      Assert.assertEquals(0L, (long) set.size());
    }
  }


  /**
   * Ray casting in the X0Y0Z0 quadrant works.
   */

  @Test
  public final void testRaycastX0Y0Z0()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final BoundingVolumeD item_volume0 = BoundingVolumeD.of(
      new VectorI3D(10.0, 11.0, 0.0),
      new VectorI3D(20.0, 21.0, 99.0));
    Assert.assertTrue(tree.insert(item0, item_volume0));

    final Integer item1 = Integer.valueOf(1);
    final BoundingVolumeD item_volume1 = BoundingVolumeD.of(
      new VectorI3D(15.0, 16.0, 0.0),
      new VectorI3D(25.0, 26.0, 99.0));
    Assert.assertTrue(tree.insert(item1, item_volume1));

    final Integer item2 = Integer.valueOf(2);
    final BoundingVolumeD item_volume2 = BoundingVolumeD.of(
      new VectorI3D(25.0, 26.0, 0.0),
      new VectorI3D(35.0, 36.0, 99.0));
    Assert.assertTrue(tree.insert(item2, item_volume2));

    {
      final VectorI3D origin = new VectorI3D(0.0, 0.0, 1.0);
      final VectorI3D direction = new VectorI3D(1.0, 1.0, 0.0);
      final RayI3D ray = new RayI3D(origin, direction);
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
      final VectorI3D origin = new VectorI3D(0.0, 0.0, 0.0);
      final VectorI3D direction = new VectorI3D(1.0, 0.0, 1.0);
      final RayI3D ray = new RayI3D(origin, direction);
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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    cb.setTrimOnRemove(false);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Integer> tree = this.create(c);

    final List<Integer> items = new ArrayList<>(9);
    for (int index = 0; index < 9; ++index) {
      items.add(Integer.valueOf(index));
    }

    final List<BoundingVolumeD> volumes = new ArrayList<>(9);

    volumes.add(BoundingVolumeD.of(
      new VectorI3D(2.0, 2.0, 2.0),
      new VectorI3D(98.0, 98.0, 98.0)));

    volumes.add(BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(2.0, 2.0, 2.0)));
    volumes.add(BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 1.0),
      new VectorI3D(99.0, 2.0, 2.0)));
    volumes.add(BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 1.0),
      new VectorI3D(2.0, 99.0, 2.0)));
    volumes.add(BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 1.0),
      new VectorI3D(99.0, 99.0, 2.0)));

    volumes.add(BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 98.0),
      new VectorI3D(2.0, 2.0, 99.0)));
    volumes.add(BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 98.0),
      new VectorI3D(99.0, 2.0, 99.0)));
    volumes.add(BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 98.0),
      new VectorI3D(2.0, 99.0, 99.0)));
    volumes.add(BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 98.0),
      new VectorI3D(99.0, 99.0, 99.0)));

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
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

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

    final BoundingVolumeD item_volume0 = BoundingVolumeD.of(
      new VectorI3D(2.0, 2.0, 2.0),
      new VectorI3D(98.0, 98.0, 98.0));

    final BoundingVolumeD item_volume1 = BoundingVolumeD.of(
      new VectorI3D(1.0, 1.0, 1.0),
      new VectorI3D(2.0, 2.0, 2.0));

    final BoundingVolumeD item_volume2 = BoundingVolumeD.of(
      new VectorI3D(98.0, 1.0, 1.0),
      new VectorI3D(99.0, 2.0, 2.0));

    final BoundingVolumeD item_volume3 = BoundingVolumeD.of(
      new VectorI3D(1.0, 98.0, 1.0),
      new VectorI3D(2.0, 99.0, 2.0));

    final BoundingVolumeD item_volume4 = BoundingVolumeD.of(
      new VectorI3D(98.0, 98.0, 1.0),
      new VectorI3D(99.0, 99.0, 2.0));

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
    final BoundingVolumeD container = BoundingVolumeD.of(
      new VectorI3D(-1000.0, -1000.0, -1000.0),
      new VectorI3D(1000.0, 1000.0, 1000.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Integer> tree = this.create(c);

    final Generator<BoundingVolumeD> gen =
      new BoundingVolumeDContainedGenerator(container);

    final Map<Integer, BoundingVolumeD> inserted = new HashMap<>(500);
    for (int index = 0; index < 500; ++index) {
      final Integer b_index = Integer.valueOf(index);
      final BoundingVolumeD volume = gen.next();
      Assert.assertTrue(tree.insert(b_index, volume));
      inserted.put(b_index, volume);
    }

    final Map<Integer, BoundingVolumeD> found = new HashMap<>(500);
    tree.iterateOctants(Unit.unit(), (context, quadrant, depth) -> {
      Assert.assertTrue(container.contains(quadrant.volume()));

      final Map<Integer, BoundingVolumeD> objects = quadrant.objects();
      for (final Map.Entry<Integer, BoundingVolumeD> e : objects.entrySet()) {
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
    final BoundingVolumeD container =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      BoundingVolumeD.of(
        new VectorI3D(10.0, 10.0, 10.0),
        new VectorI3D(20.0, 20.0, 20.0))));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Unit.unit(), (context, quadrant, depth) -> {
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
    final BoundingVolumeD container =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      BoundingVolumeD.of(
        new VectorI3D(10.0, 10.0, 10.0),
        new VectorI3D(20.0, 20.0, 20.0))));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Unit.unit(), (context, quadrant, depth) -> {
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
    final BoundingVolumeD container =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      BoundingVolumeD.of(
        new VectorI3D(10.0, 10.0, 10.0),
        new VectorI3D(20.0, 20.0, 20.0))));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Unit.unit(), (context, quadrant, depth) -> {
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
    final BoundingVolumeD container =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      BoundingVolumeD.of(
        new VectorI3D(10.0, 10.0, 10.0),
        new VectorI3D(20.0, 20.0, 20.0))));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Unit.unit(), (context, quadrant, depth) -> {
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
    final BoundingVolumeD container =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      BoundingVolumeD.of(
        new VectorI3D(10.0, 10.0, 10.0),
        new VectorI3D(20.0, 20.0, 20.0))));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Unit.unit(), (context, quadrant, depth) -> {
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
    final BoundingVolumeD container =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      BoundingVolumeD.of(
        new VectorI3D(10.0, 10.0, 10.0),
        new VectorI3D(20.0, 20.0, 20.0))));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Unit.unit(), (context, quadrant, depth) -> {
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
    final BoundingVolumeD container =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      BoundingVolumeD.of(
        new VectorI3D(10.0, 10.0, 10.0),
        new VectorI3D(20.0, 20.0, 20.0))));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Unit.unit(), (context, quadrant, depth) -> {
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
    final BoundingVolumeD container =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      BoundingVolumeD.of(
        new VectorI3D(10.0, 10.0, 10.0),
        new VectorI3D(20.0, 20.0, 20.0))));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Unit.unit(), (context, quadrant, depth) -> {
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
    final BoundingVolumeD container =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    cb.setMinimumOctantHeight(40.0);
    cb.setMinimumOctantWidth(40.0);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    Assert.assertTrue(tree.insert(
      Integer.valueOf(0),
      BoundingVolumeD.of(
        new VectorI3D(10.0, 10.0, 10.0),
        new VectorI3D(20.0, 20.0, 20.0))));
    tree.trim();

    Assert.assertEquals(9L, (long) OctTreeDContract.countOctants(tree));

    final AtomicInteger count = new AtomicInteger(0);
    tree.iterateOctants(Unit.unit(), (context, quadrant, depth) -> {
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
    final BoundingVolumeD container = BoundingVolumeD.of(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(512.0, 512.0, 512.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(container);
    cb.setTrimOnRemove(true);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Integer> tree = this.create(c);

    final Integer item0 = Integer.valueOf(0);
    final Integer item1 = Integer.valueOf(1);
    final Integer item2 = Integer.valueOf(2);

    Assert.assertTrue(tree.insert(item0, BoundingVolumeD.of(
      new VectorI3D(32.0, 32.0, 0.0),
      new VectorI3D(80.0, 80.0, 511.0)
    )));

    Assert.assertTrue(tree.insert(item1, BoundingVolumeD.of(
      new VectorI3D(400.0, 32.0, 0.0),
      new VectorI3D(400.0 + 32.0, 80.0, 511.0)
    )));

    Assert.assertTrue(tree.insert(item2, BoundingVolumeD.of(
      new VectorI3D(400.0, 400.0, 0.0),
      new VectorI3D(480.0, 480.0, 511.0)
    )));

    final RayI3D ray = new RayI3D(
      new VectorI3D(0.0, 0.0, 1.0),
      VectorI3D.normalize(new VectorI3D(511.0, 511.0, 0.0)));

    final SortedSet<OctTreeRaycastResultD<Integer>> items = new TreeSet<>();
    tree.raycast(ray, items);

    Assert.assertEquals(2L, (long) items.size());
    final Iterator<OctTreeRaycastResultD<Integer>> iter = items.iterator();

    {
      final OctTreeRaycastResultD<Integer> rr = iter.next();
      final BoundingVolumeD r = rr.volume();
      Assert.assertEquals(item0, rr.item());
      Assert.assertEquals(32.0, r.lower().getXD(), 0.0);
      Assert.assertEquals(32.0, r.lower().getYD(), 0.0);
      Assert.assertEquals(80.0, r.upper().getXD(), 0.0);
      Assert.assertEquals(80.0, r.upper().getYD(), 0.0);
    }

    {
      final OctTreeRaycastResultD<Integer> rr = iter.next();
      final BoundingVolumeD r = rr.volume();
      Assert.assertEquals(item2, rr.item());
      Assert.assertEquals(400.0, r.lower().getXD(), 0.0);
      Assert.assertEquals(400.0, r.lower().getYD(), 0.0);
      Assert.assertEquals(480.0, r.upper().getXD(), 0.0);
      Assert.assertEquals(480.0, r.upper().getYD(), 0.0);
    }

    Assert.assertFalse(iter.hasNext());
  }

  /**
   * The octant cannot be split due to small width.
   */

  @Test
  public final void testInsertCannotSplitWidth()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(1.0, 100.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(1.0, 1.0, 1.0));
    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertEquals(1L, (long) OctTreeDContract.countOctants(tree));
  }

  /**
   * The octant cannot be split due to small height.
   */

  @Test
  public final void testInsertCannotSplitHeight()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 1.0, 100.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(1.0, 1.0, 1.0));
    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertEquals(1L, (long) OctTreeDContract.countOctants(tree));
  }

  /**
   * The octant cannot be split due to small depth.
   */

  @Test
  public final void testInsertCannotSplitDepth()
  {
    final BoundingVolumeD volume =
      BoundingVolumeD.of(
        new VectorI3D(0.0, 0.0, 0.0),
        new VectorI3D(100.0, 100.0, 1.0));

    final OctTreeConfigurationD.Builder cb = OctTreeConfigurationD.builder();
    cb.setVolume(volume);
    final OctTreeConfigurationD c = cb.build();

    final OctTreeDType<Object> tree = this.create(c);

    final Integer item = Integer.valueOf(0);
    final BoundingVolumeD item_volume = BoundingVolumeD.of(
      new VectorI3D(0.0, 0.0, 0.0),
      new VectorI3D(1.0, 1.0, 1.0));
    Assert.assertTrue(tree.insert(item, item_volume));
    Assert.assertEquals(1L, (long) OctTreeDContract.countOctants(tree));
  }
}
