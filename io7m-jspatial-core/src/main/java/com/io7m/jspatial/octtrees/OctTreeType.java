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

package com.io7m.jspatial.octtrees;

import java.util.SortedSet;

import com.io7m.jfunctional.PartialFunctionType;
import com.io7m.jspatial.BoundingVolumeType;
import com.io7m.jspatial.RayI3D;

/**
 * The interface provided by octtree implementations.
 *
 * @param <T>
 *          The precise type of octtree members.
 */

public interface OctTreeType<T extends OctTreeMemberType<T>>
{
  /**
   * Delete all objects, if any, contained within the octtree.
   */

  void octTreeClear();

  /**
   * @return The X axis value of the lower corner of the octtree.
   */

  int octTreeGetPositionX();

  /**
   * @return The Y axis value of the lower corner of the octtree.
   */

  int octTreeGetPositionY();

  /**
   * @return The Z axis value of the lower corner of the octtree.
   */

  int octTreeGetPositionZ();

  /**
   * @return The maximum size of the octtree on the X axis.
   */

  int octTreeGetSizeX();

  /**
   * @return The maximum size of the octtree on the Y axis.
   */

  int octTreeGetSizeY();

  /**
   * @return The maximum size of the octtree on the Z axis.
   */

  int octTreeGetSizeZ();

  /**
   * Insert the object <code>item</code> into the octtree.
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
   *           If the object's bounding volume is not <i>well-formed</i>.
   *
   * @see com.io7m.jspatial.BoundingVolumeCheck#isWellFormed(BoundingVolumeType)
   */

  boolean octTreeInsert(
    T item)
      throws IllegalArgumentException;

  /**
   * Pass each object in the octtree to <code>f.call()</code>, in no
   * particular order. Iteration stops if <code>f.call()</code> returns
   * <code>false</code>, or raises an exception.
   *
   * @param f
   *          The function that will receive each object
   * @throws E
   *           Propagated from <code>f.call()</code>
   * @param <E>
   *          The type of raised exceptions.
   */

  <E extends Throwable> void octTreeIterateObjects(
    PartialFunctionType<T, Boolean, E> f)
      throws E;

  /**
   * Returns the objects intersected by the ray <code>ray</code> in
   * <code>items</code>.
   *
   * The objects are returned in order of increasing scalar distance from the
   * origin of <code>ray</code>. That is, the nearest object to the origin of
   * <code>ray</code> will be the first item in <code>items</code>.
   *
   * @see com.io7m.jtensors.VectorI3D#distance(com.io7m.jtensors.VectorReadable3DType,
   *      com.io7m.jtensors.VectorReadable3DType)
   *
   * @param ray
   *          The ray
   * @param items
   *          The returned objects
   * @throws IllegalArgumentException
   *           If the object's bounding volume is not <i>well-formed</i>.
   *
   * @see com.io7m.jspatial.BoundingVolumeCheck#isWellFormed(BoundingVolumeType)
   */

  void octTreeQueryRaycast(
    RayI3D ray,
    SortedSet<OctTreeRaycastResult<T>> items)
      throws IllegalArgumentException;

  /**
   * Returns all objects in the tree that are completely contained within
   * <code>volume</code>, saving the results to <code>items</code>.
   *
   * @param volume
   *          The volume to examine
   * @param items
   *          The returned items
   * @throws IllegalArgumentException
   *           If the object's bounding volume is not <i>well-formed</i>.
   *
   * @see com.io7m.jspatial.BoundingVolumeCheck#isWellFormed(BoundingVolumeType)
   */

  void octTreeQueryVolumeContaining(
    BoundingVolumeType volume,
    SortedSet<T> items)
      throws IllegalArgumentException;

  /**
   * Returns all objects in the tree that are overlapped by
   * <code>volume</code>, saving the results to <code>items</code>.
   *
   * @param volume
   *          The volume to examine
   * @param items
   *          The returned items
   * @throws IllegalArgumentException
   *           If the object's bounding volume is not <i>well-formed</i>.
   *
   * @see com.io7m.jspatial.BoundingVolumeCheck#isWellFormed(BoundingVolumeType)
   */

  void octTreeQueryVolumeOverlapping(
    BoundingVolumeType volume,
    SortedSet<T> items)
      throws IllegalArgumentException;

  /**
   * Remove the object <code>item</code> from the octtree.
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
   * @throws IllegalArgumentException
   *           If the object's bounding volume is not <i>well-formed</i>.
   *
   * @see com.io7m.jspatial.BoundingVolumeCheck#isWellFormed(BoundingVolumeType)
   */

  boolean octTreeRemove(
    T item)
      throws IllegalArgumentException;

  /**
   * Pass each node of the given octtree to <code>traversal.visit()</code>, in
   * depth-first order.
   *
   * @param traversal
   *          The traversal
   * @throws E
   *           Propagated from <code>traversal.visit()</code>
   * @param <E>
   *          The type of raised exceptions
   */

  <E extends Throwable> void octTreeTraverse(
    OctTreeTraversalType<E> traversal)
      throws E;

}
