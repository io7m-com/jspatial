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

package com.io7m.jspatial.octtrees;

/**
 * A mutable builder for instantiating octtree implementations.
 *
 * @param <T>
 *          The precise type of members.
 */

public interface OctTreeBuilderType<T extends OctTreeMemberType<T>>
{
  /**
   * @return A new octtree based on the parameters given so far.
   */

  OctTreeType<T> build();

  /**
   * @return A new SD octtree based on the parameters given so far.
   */

  OctTreeSDType<T> buildWithSD();

  /**
   * Do not use specific minimum quadrant sizes.
   */

  void disableLimitedOctantSizes();

  /**
   * Disable pruning of empty octtree nodes.
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
   * @param z
   *          The minimum quadrant depth - Must be even and greater than or
   *          equal to <code>2</code>.
   *
   * @throws IllegalArgumentException
   *           If the preconditions above are not satisfied.
   */

  void enableLimitedOctantSizes(
    int x,
    int y,
    int z)
      throws IllegalArgumentException;

  /**
   * Enable pruning of empty octtree nodes.
   */

  void enablePruning();

  /**
   * Set the position of the octtree.
   *
   * @param x
   *          The X coordinate.
   * @param y
   *          The Y coordinate.
   * @param z
   *          The Z coordinate.
   */

  void setPosition3i(
    int x,
    int y,
    int z);

  /**
   * <p>
   * Set the size of the octtree.
   * </p>
   *
   * @param x
   *          The width - Must be even and greater than or equal to
   *          <code>2</code>.
   * @param y
   *          The height - Must be even and greater than or equal to
   *          <code>2</code>.
   * @param z
   *          The depth - Must be even and greater than or equal to
   *          <code>2</code>.
   *
   * @throws IllegalArgumentException
   *           If the preconditions above are not satisfied.
   */

  void setSize3i(
    int x,
    int y,
    int z)
      throws IllegalArgumentException;
}
