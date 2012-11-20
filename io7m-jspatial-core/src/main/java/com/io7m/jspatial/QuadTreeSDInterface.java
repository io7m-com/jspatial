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

import javax.annotation.Nonnull;

import com.io7m.jaux.Constraints.ConstraintError;

/**
 * The interface provided by quadtree implementations that distinguish between
 * static (immovable) and dynamic (movable) objects.
 */

public interface QuadTreeSDInterface<T extends QuadTreeMember<T>> extends
  QuadTreeInterface<T>
{
  /**
   * Insert the object <code>item</code> into the quadtree. The object will be
   * given the categorization <code>type</code>.
   * <p>
   * The function returns <code>false</code> if the object could not be
   * inserted for any reason (perhaps due to being too large).
   * </p>
   * 
   * @param item
   *          The object to insert
   * @param type
   *          The categorization of the object
   * 
   * @return <code>true</code> if the object was inserted
   * 
   * @throws ConstraintError
   *           Iff <code>item == null</code>
   */

  boolean quadTreeInsertSD(
    final @Nonnull T item,
    final @Nonnull SDType type)
    throws ConstraintError;

  /**
   * Remove all dynamic (movable) objects from the tree.
   */

  void quadTreeSDClearDynamic();
}
