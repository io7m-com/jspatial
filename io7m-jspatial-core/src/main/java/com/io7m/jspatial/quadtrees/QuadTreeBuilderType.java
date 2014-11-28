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

/**
 * A mutable builder for instantiating quadtree implementations.
 *
 * @param <T>
 *          The precise type of members.
 */

public interface QuadTreeBuilderType<T extends QuadTreeMemberType<T>>
{
  /**
   * @return A new quadtree based on the parameters given so far.
   */

  QuadTreeType<T> build();

  /**
   * @return A new SD quadtree based on the parameters given so far.
   */

  QuadTreeSDType<T> buildWithSD();

  /**
   * Do not use specific minimum quadrant sizes.
   */

  void disableLimitedQuadrantSizes();

  /**
   * Disable pruning of empty quadtree nodes.
   */

  void disablePruning();

  /**
   * Use specific minimum quadrant sizes.
   *
   * @param x
   *          The minimum quadrant width - Must be even and greater than or
   *          equal to <code>2</code>.
   * @param y
   *          The minimum quadrant height - Must be even and greater than or
   *          equal to <code>2</code>.
   *
   * @throws IllegalArgumentException
   *           If the preconditions above are not satisfied.
   */

  void enableLimitedQuadrantSizes(
    int x,
    int y)
      throws IllegalArgumentException;

  /**
   * Enable pruning of empty quadtree nodes.
   */

  void enablePruning();

  /**
   * Set the position of the quadtree.
   *
   * @param x
   *          The X coordinate.
   * @param y
   *          The Y coordinate.
   */

  void setPosition2i(
    int x,
    int y);

  /**
   * <p>
   * Set the size of the quadtree.
   * </p>
   *
   * @param x
   *          The width - Must be even and greater than or equal to
   *          <code>2</code>.
   * @param y
   *          The height - Must be even and greater than or equal to
   *          <code>2</code>.
   *
   * @throws IllegalArgumentException
   *           If the preconditions above are not satisfied.
   */

  void setSize2i(
    int x,
    int y)
      throws IllegalArgumentException;
}
