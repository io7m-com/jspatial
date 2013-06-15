/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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

package com.io7m.jspatial;

import java.util.SortedSet;

import javax.annotation.Nonnull;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.functional.Function;
import com.io7m.jtensors.VectorI2D;

/**
 * The interface provided by quadtree implementations.
 */

public interface QuadTreeInterface<T extends QuadTreeMember<T>>
{
  /**
   * Delete all objects, if any, contained within the quadtree.
   */

  void quadTreeClear();

  /**
   * Retrieve the maximum size of the quadtree on the X axis.
   */

  int quadTreeGetSizeX();

  /**
   * Retrieve the maximum size of the quadtree on the Y axis.
   */

  int quadTreeGetSizeY();

  /**
   * Retrieve the position of the lower corner of the quadtree on the X axis.
   */

  int quadTreeGetPositionX();

  /**
   * Retrieve the position of the lower corner of the quadtree on the Y axis.
   */

  int quadTreeGetPositionY();

  /**
   * Insert the object <code>item</code> into the quadtree.
   * <p>
   * The function returns <code>false</code> if the object could not be
   * inserted for any reason (perhaps due to being too large).
   * </p>
   * 
   * @param item
   *          The object to insert
   * 
   * @return <code>true</code> if the object was inserted
   * 
   * @throws ConstraintError
   *           Iff <code>item == null</code>
   */

  boolean quadTreeInsert(
    final @Nonnull T item)
    throws ConstraintError;

  /**
   * Pass each object in the quadtree to <code>f.call()</code>, in no
   * particular order. Iteration stops if <code>f.call()</code> returns
   * <code>false</code>, or raises an exception.
   * 
   * @throws Exception
   *           Propagated from <code>f.call()</code>
   * @throws ConstraintError
   *           Iff <code>f == null</code>
   */

  void quadTreeIterateObjects(
    final @Nonnull Function<T, Boolean> f)
    throws Exception,
      ConstraintError;

  /**
   * Returns all objects in the tree that are completely contained within
   * <code>area</code>, saving the results to <code>items</code>.
   * 
   * @param area
   *          The area to examine
   * @param items
   *          The returned items
   * @throws ConstraintError
   *           Iff any of the following hold:
   *           <ul>
   *           <li><code>items == null</code>
   *           <li><code>area == null</code>
   *           <li><code>area</code> is not well formed</li>
   *           </ul>
   * 
   * 
   * @see BoundingAreaCheck#wellFormed(BoundingArea)
   */

  void quadTreeQueryAreaContaining(
    final @Nonnull BoundingArea area,
    final @Nonnull SortedSet<T> items)
    throws ConstraintError;

  /**
   * Returns all objects in the tree that are at least partially contained
   * within <code>area</code>, saving the results to <code>items</code>.
   * 
   * @param area
   *          The area to examine
   * @param items
   *          The returned items
   * 
   * @throws ConstraintError
   *           Iff <code>area</code> is not well formed
   * 
   * @see BoundingAreaCheck#wellFormed(BoundingArea)
   */

  void quadTreeQueryAreaOverlapping(
    final @Nonnull BoundingArea area,
    final @Nonnull SortedSet<T> items)
    throws ConstraintError;

  /**
   * Returns the objects intersected by the ray <code>ray</code> in
   * <code>items</code>.
   * 
   * The objects are returned in order of increasing scalar distance from the
   * origin of <code>ray</code>. That is, the nearest object to the origin of
   * <code>ray</code> will be the first item in <code>items</code>.
   * 
   * @see VectorI2D#distance(VectorI2D, VectorI2D)
   * @param items
   *          The returned objects
   * @throws ConstraintError
   *           Iff any of the following hold:
   *           <ul>
   *           <li><code>ray == null</code></li>
   *           <li><code>items == null</code></li>
   *           </ul>
   */

  void quadTreeQueryRaycast(
    final @Nonnull RayI2D ray,
    final @Nonnull SortedSet<QuadTreeRaycastResult<T>> items)
    throws ConstraintError;

  /**
   * Remove the object <code>item</code> from the quadtree.
   * <p>
   * The function returns <code>false</code> if the object could not be
   * removed for any reason (perhaps due to not being in the tree in the first
   * place).
   * </p>
   * 
   * @param item
   *          The object to remove
   * 
   * @return <code>true</code> if the object was removed
   * 
   * @throws ConstraintError
   *           Iff <code>item == null</code>
   */

  boolean quadTreeRemove(
    final @Nonnull T item)
    throws ConstraintError;

  /**
   * Pass each node of the given quadtree to <code>traversal.visit()</code>,
   * in depth-first order.
   * 
   * @param traversal
   *          The traversal
   * @throws Exception
   *           Propagated from <code>traversal.visit()</code>
   * @throws ConstraintError
   *           Iff <code>traversal == null</code>
   */

  void quadTreeTraverse(
    final @Nonnull QuadTreeTraversal traversal)
    throws Exception,
      ConstraintError;
}
