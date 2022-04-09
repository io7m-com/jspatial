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

package com.io7m.jspatial.api.octtrees;

import com.io7m.jspatial.api.TreeVisitResult;

/**
 * The type of functions used to iterate over the octants of octtrees.
 *
 * @param <A> The type of tree objects
 * @param <C> The type of contextual values
 *
 * @since 3.0.0
 */

@FunctionalInterface
public interface OctTreeOctantIterationIType<A, C>
{
  /**
   * Apply the function.
   *
   * @param context A context value
   * @param octant  The current octant
   * @param depth   The current octant depth
   *
   * @return A value indicating how or if the traversal should continue
   */

  TreeVisitResult apply(
    C context,
    OctTreeOctantIType<A> octant,
    long depth);
}
