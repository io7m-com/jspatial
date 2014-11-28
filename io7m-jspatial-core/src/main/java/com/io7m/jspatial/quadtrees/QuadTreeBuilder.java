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

import com.io7m.jtensors.VectorM2I;
import com.io7m.junreachable.UnimplementedCodeException;

/**
 * The default implementation of the {@link QuadTreeBuilderType}.
 *
 * @param <T>
 *          The precise type of quadtree members.
 */

public final class QuadTreeBuilder<T extends QuadTreeMemberType<T>> implements
  QuadTreeBuilderType<T>
{
  /**
   * @return A new quadtree builder.
   *
   * @param <T>
   *          The precise type of quadtree members.
   */

  public static
    <T extends QuadTreeMemberType<T>>
    QuadTreeBuilderType<T>
    newBuilder()
  {
    return new QuadTreeBuilder<T>();
  }

  private boolean         limit;
  private final VectorM2I limit_size;
  private final VectorM2I position;
  private boolean         prune;
  private final VectorM2I size;

  private QuadTreeBuilder()
  {
    this.limit_size = new VectorM2I();
    this.size = new VectorM2I();
    this.position = new VectorM2I();
  }

  @Override public QuadTreeType<T> build()
  {
    if (this.prune) {
      if (this.limit) {
        return QuadTreePruneLimit.newQuadTree(
          this.size,
          this.position,
          this.limit_size);
      }
      return QuadTreePrune.newQuadTree(this.size, this.position);
    }

    if (this.limit) {
      return QuadTreeLimit.newQuadTree(
        this.size,
        this.position,
        this.limit_size);
    }

    return QuadTreeBasic.newQuadTree(this.size, this.position);
  }

  @Override public QuadTreeSDType<T> buildWithSD()
  {
    if (this.prune) {
      if (this.limit) {
        throw new UnimplementedCodeException();
      }
      throw new UnimplementedCodeException();
    }
    if (this.limit) {
      throw new UnimplementedCodeException();
    }
    return QuadTreeSDBasic.newQuadTree(this.size, this.position);
  }

  @Override public void disableLimitedQuadrantSizes()
  {
    this.limit = false;
  }

  @Override public void disablePruning()
  {
    this.prune = false;
  }

  @Override public void enableLimitedQuadrantSizes(
    final int x,
    final int y)
  {
    QuadTreeChecks.checkSize("Quadrant minimum size", x, y);
    this.limit = true;
    this.limit_size.set2I(x, y);
  }

  @Override public void enablePruning()
  {
    this.prune = true;
  }

  @Override public void setPosition2i(
    final int x,
    final int y)
  {
    this.position.set2I(x, y);
  }

  @Override public void setSize2i(
    final int x,
    final int y)
  {
    QuadTreeChecks.checkSize("Quadtree size", x, y);
    this.size.set2I(x, y);
  }

}
