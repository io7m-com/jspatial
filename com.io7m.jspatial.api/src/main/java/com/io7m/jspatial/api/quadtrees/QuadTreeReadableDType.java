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

package com.io7m.jspatial.api.quadtrees;

import com.io7m.jregions.core.unparameterized.areas.AreaD;
import com.io7m.jspatial.api.Ray2D;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiFunction;

/**
 * The type of readable quadtrees with {@code double} precision coordinates.
 *
 * @param <A> The precise type of quadtree members
 *
 * @since 3.0.0
 */

public interface QuadTreeReadableDType<A> extends QuadTreeReadableType
{
  /**
   * @return The tree bounds
   */

  AreaD bounds();

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

  <B> QuadTreeReadableDType<B> map(BiFunction<A, AreaD, B> f);

  /**
   * Iterate over all quadrants within the tree.
   *
   * @param context A contextual value passed to {@code f}
   * @param f       An iteration function
   * @param <C>     The type of context values
   */

  <C> void iterateQuadrants(
    C context,
    QuadTreeQuadrantIterationDType<A, C> f);

  /**
   * @param item The item
   *
   * @return The bounding area that was specified for {@code item}
   *
   * @throws NoSuchElementException Iff the item is not present in the tree
   */

  AreaD areaFor(A item)
    throws NoSuchElementException;

  /**
   * Returns all objects in the tree that are completely contained within {@code area}, saving the
   * results to {@code items}.
   *
   * @param area  The area to examine
   * @param items The returned items
   */

  void containedBy(
    AreaD area,
    Set<A> items);

  /**
   * Returns all objects in the tree that are overlapped {@code area}, saving the results to {@code
   * items}.
   *
   * @param area  The area to examine
   * @param items The returned items
   */

  void overlappedBy(
    AreaD area,
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
    Ray2D ray,
    SortedSet<QuadTreeRaycastResultD<A>> items);
}
