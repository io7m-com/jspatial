/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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

package com.io7m.jspatial.quadtrees;

import java.util.SortedSet;

import com.io7m.jfunctional.PartialFunctionType;
import com.io7m.jspatial.BoundingAreaType;
import com.io7m.jspatial.RayI2D;

/**
 * The interface provided by quadtree implementations.
 *
 * @param <T>
 *          The precise type of quadtree members.
 */

public interface QuadTreeType<T extends QuadTreeMemberType<T>>
{
  /**
   * Delete all objects, if any, contained within the quadtree.
   */

  void quadTreeClear();

  /**
   * @return The position of the lower corner of the quadtree on the X axis.
   */

  int quadTreeGetPositionX();

  /**
   * @return The position of the lower corner of the quadtree on the Y axis.
   */

  int quadTreeGetPositionY();

  /**
   * @return The maximum size of the quadtree on the X axis.
   */

  int quadTreeGetSizeX();

  /**
   * @return The maximum size of the quadtree on the Y axis.
   */

  int quadTreeGetSizeY();

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
   * @throws IllegalArgumentException
   *           If the object's bounding area is not <i>well-formed</i>.
   *
   * @see com.io7m.jspatial.BoundingAreaCheck#isWellFormed(BoundingAreaType)
   */

  boolean quadTreeInsert(
    T item)
      throws IllegalArgumentException;

  /**
   * Pass each object in the quadtree to <code>f.call()</code>, in no
   * particular order. Iteration stops if <code>f.call()</code> returns
   * <code>false</code>, or raises an exception.
   *
   * @param f
   *          The function that will receive each object
   * @param <E>
   *          The type of raised exceptions
   * @throws E
   *           Propagated from <code>f.call()</code>
   */

  <E extends Throwable> void quadTreeIterateObjects(
    PartialFunctionType<T, Boolean, E> f)
      throws E;

  /**
   * Returns all objects in the tree that are completely contained within
   * <code>area</code>, saving the results to <code>items</code>.
   *
   * @param area
   *          The area to examine
   * @param items
   *          The returned items
   * @throws IllegalArgumentException
   *           Iff <code>area</code> is not well formed
   *
   * @see com.io7m.jspatial.BoundingAreaCheck#isWellFormed(BoundingAreaType)
   */

  void quadTreeQueryAreaContaining(
    BoundingAreaType area,
    SortedSet<T> items)
      throws IllegalArgumentException;

  /**
   * Returns all objects in the tree that are at least partially contained
   * within <code>area</code>, saving the results to <code>items</code>.
   *
   * @param area
   *          The area to examine
   * @param items
   *          The returned items
   *
   * @throws IllegalArgumentException
   *           Iff <code>area</code> is not well formed
   *
   * @see com.io7m.jspatial.BoundingAreaCheck#isWellFormed(BoundingAreaType)
   */

  void quadTreeQueryAreaOverlapping(
    BoundingAreaType area,
    SortedSet<T> items)
      throws IllegalArgumentException;

  /**
   * Returns the objects intersected by the ray <code>ray</code> in
   * <code>items</code>.
   *
   * The objects are returned in order of increasing scalar distance from the
   * origin of <code>ray</code>. That is, the nearest object to the origin of
   * <code>ray</code> will be the first item in <code>items</code>.
   *
   * @see com.io7m.jtensors.VectorI2D#distance(com.io7m.jtensors.VectorReadable2DType,
   *      com.io7m.jtensors.VectorReadable2DType)
   *
   * @param items
   *          The returned objects
   * @param ray
   *          The ray
   */

  void quadTreeQueryRaycast(
    RayI2D ray,
    SortedSet<QuadTreeRaycastResult<T>> items);

  /**
   * Return the set of quadrants intersected by <code>ray</code>.
   *
   * @param ray
   *          The ray
   * @param items
   *          The resulting set of quadrants
   */

  void quadTreeQueryRaycastQuadrants(
    RayI2D ray,
    SortedSet<QuadTreeRaycastResult<QuadrantType>> items);

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
   */

  boolean quadTreeRemove(
    T item);

  /**
   * Pass each node of the given quadtree to <code>traversal.visit()</code>,
   * in depth-first order.
   *
   * @param traversal
   *          The traversal
   * @throws E
   *           Propagated from <code>traversal.visit()</code>
   * @param <E>
   *          The type of raised exceptions
   */

  <E extends Throwable> void quadTreeTraverse(
    QuadTreeTraversalType<E> traversal)
      throws E;
}
