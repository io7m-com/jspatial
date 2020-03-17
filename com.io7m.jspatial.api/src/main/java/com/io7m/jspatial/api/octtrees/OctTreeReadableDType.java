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

package com.io7m.jspatial.api.octtrees;

import com.io7m.jregions.core.unparameterized.volumes.VolumeD;
import com.io7m.jspatial.api.Ray3D;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiFunction;

/**
 * The type of readable octtrees with {@code double} precision coordinates.
 *
 * @param <A> The precise type of octtree members
 *
 * @since 3.0.0
 */

public interface OctTreeReadableDType<A> extends OctTreeReadableType
{
  /**
   * @return The tree bounds
   */

  VolumeD bounds();

  /**
   * Determine whether or not the object has already been inserted into the tree.
   *
   * @param item The object
   *
   * @return {@code true} iff the object is in the tree
   */

  boolean contains(A item);

  /**
   * Apply {@code f} to each element of the tree.
   *
   * @param f   A mapping function
   * @param <B> The type of result elements
   *
   * @return A new tree
   */

  <B> OctTreeReadableDType<B> map(BiFunction<A, VolumeD, B> f);

  /**
   * Iterate over all octants within the tree.
   *
   * @param context A contextual value passed to {@code f}
   * @param f       An iteration function
   * @param <C>     The type of context values
   */

  <C> void iterateOctants(
    C context,
    OctTreeOctantIterationDType<A, C> f);

  /**
   * @param item The item
   *
   * @return The bounding volume that was specified for {@code item}
   *
   * @throws NoSuchElementException Iff the item is not present in the tree
   */

  VolumeD volumeFor(A item)
    throws NoSuchElementException;

  /**
   * Returns all objects in the tree that are completely contained within {@code volume}, saving the
   * results to {@code items}.
   *
   * @param volume The volume to examine
   * @param items  The returned items
   */

  void containedBy(
    VolumeD volume,
    Set<A> items);

  /**
   * Returns all objects in the tree that are overlapped {@code volume}, saving the results to
   * {@code items}.
   *
   * @param volume The volume to examine
   * @param items  The returned items
   */

  void overlappedBy(
    VolumeD volume,
    Set<A> items);

  /**
   * Returns all objects that are intersected by the given ray. The objects are returned in order of
   * distance from the origin of the ray: The first object returned will be the object nearest to
   * the origin.
   *
   * @param ray   The ray
   * @param items The intersected items
   */

  void raycast(
    Ray3D ray,
    SortedSet<OctTreeRaycastResultD<A>> items);
}
