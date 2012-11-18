/*
 * Copyright © 2012 http://io7m.com
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
import com.io7m.jtensors.VectorI3D;

/**
 * The interface provided by octtree implementations.
 */

public interface OctTreeInterface<T extends OctTreeMember<T>>
{
  /**
   * Delete all objects, if any, contained within the octtree.
   */

  void octTreeClear();

  /**
   * Retrieve the maximum size of the octtree on the X axis.
   */

  int octTreeGetSizeX();

  /**
   * Retrieve the maximum size of the octtree on the Y axis.
   */

  int octTreeGetSizeY();

  /**
   * Retrieve the maximum size of the octtree on the Z axis.
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
   * 
   * @throws ConstraintError
   *           Iff <code>item == null</code>
   */

  boolean octTreeInsert(
    final @Nonnull T item)
    throws ConstraintError;

  /**
   * Pass each object in the octtree to <code>f.call()</code>, in no
   * particular order. Iteration stops if <code>f.call()</code> returns
   * <code>false</code>, or raises an exception.
   * 
   * @throws Exception
   *           Propagated from <code>f.call()</code>
   * @throws ConstraintError
   *           Iff <code>f == null</code>
   */

  void octTreeIterateObjects(
    final @Nonnull Function<T, Boolean> f)
    throws Exception,
      ConstraintError;

  /**
   * Returns the objects intersected by the ray <code>ray</code> in
   * <code>items</code>.
   * 
   * The objects are returned in order of increasing scalar distance from the
   * origin of <code>ray</code>. That is, the nearest object to the origin of
   * <code>ray</code> will be the first item in <code>items</code>.
   * 
   * @see VectorI3D#distance(VectorI3D, VectorI3D)
   * @param items
   *          The returned objects
   * @throws ConstraintError
   *           Iff any of the following hold:
   *           <ul>
   *           <li><code>ray == null</code></li>
   *           <li><code>items == null</code></li>
   *           </ul>
   */

  void octTreeQueryRaycast(
    final @Nonnull RayI3D ray,
    final @Nonnull SortedSet<OctTreeRaycastResult<T>> items)
    throws ConstraintError;

  /**
   * Returns all objects in the tree that are completely contained within
   * <code>volume</code>, saving the results to <code>items</code>.
   * 
   * @param volume
   *          The volume to examine
   * @param items
   *          The returned items
   * @throws ConstraintError
   *           Iff any of the following hold:
   *           <ul>
   *           <li><code>items == null</code>
   *           <li><code>volume == null</code>
   *           <li><code>volume</code> is not well formed</li>
   *           </ul>
   * 
   * 
   * @see BoundingVolumeCheck#wellFormed(BoundingVolume)
   */

  void octTreeQueryVolumeContaining(
    final @Nonnull BoundingVolume volume,
    final @Nonnull SortedSet<T> items)
    throws ConstraintError;

  /**
   * Returns all objects in the tree that are overlapped by
   * <code>volume</code>, saving the results to <code>items</code>.
   * 
   * @param volume
   *          The volume to examine
   * @param items
   *          The returned items
   * @throws ConstraintError
   *           Iff any of the following hold:
   *           <ul>
   *           <li><code>items == null</code>
   *           <li><code>volume == null</code>
   *           <li><code>volume</code> is not well formed</li>
   *           </ul>
   * 
   * 
   * @see BoundingVolumeCheck#wellFormed(BoundingVolume)
   */

  void octTreeQueryVolumeOverlapping(
    final @Nonnull BoundingVolume volume,
    final @Nonnull SortedSet<T> items)
    throws ConstraintError;

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
   * 
   * @throws ConstraintError
   *           Iff <code>item == null</code>
   */

  boolean octTreeRemove(
    final @Nonnull T item)
    throws ConstraintError;

  /**
   * Pass each node of the given octtree to <code>traversal.visit()</code>, in
   * depth-first order.
   * 
   * @param traversal
   *          The traversal
   * @throws Exception
   *           Propagated from <code>traversal.visit()</code>
   * @throws ConstraintError
   *           Iff <code>traversal == null</code>
   */

  void octTreeTraverse(
    final @Nonnull OctTreeTraversal traversal)
    throws Exception,
      ConstraintError;

}
