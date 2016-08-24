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

import com.io7m.jtensors.VectorReadable3IType;

/**
 * <p>
 * The type of procedures used when iterating over octants in octtrees.
 * </p>
 *
 * @param <E>
 *          The type of raised exceptions.
 */

public interface OctTreeTraversalType<E extends Throwable>
{
  /**
   * Visit an octant.
   *
   * @param depth
   *          The depth of the octant.
   * @param lower
   *          The lower corner of the octant.
   * @param upper
   *          The upper corner of the octant.
   * @throws E
   *           If required.
   */

  void visit(
    final int depth,
    final VectorReadable3IType lower,
    final VectorReadable3IType upper)
      throws E;
}
