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

import com.io7m.jtensors.VectorM3I;

/**
 * The default implementation of the {@link OctTreeBuilderType}.
 *
 * @param <T>
 *          The precise type of octtree members.
 */

public final class OctTreeBuilder<T extends OctTreeMemberType<T>> implements
  OctTreeBuilderType<T>
{
  /**
   * @return A new octtree builder.
   *
   * @param <T>
   *          The precise type of octtree members.
   */

  public static
    <T extends OctTreeMemberType<T>>
    OctTreeBuilderType<T>
    newBuilder()
  {
    return new OctTreeBuilder<T>();
  }

  private boolean         limit;
  private final VectorM3I limit_size;
  private final VectorM3I position;
  private boolean         prune;
  private final VectorM3I size;

  private OctTreeBuilder()
  {
    this.limit_size = new VectorM3I();
    this.size = new VectorM3I();
    this.position = new VectorM3I();
  }

  @Override public OctTreeType<T> build()
  {
    if (this.prune) {
      if (this.limit) {
        return OctTreePruneLimit.newOctTree(
          this.size,
          this.position,
          this.limit_size);
      }
      return OctTreePrune.newOctTree(this.size, this.position);
    }

    if (this.limit) {
      return OctTreeLimit.newOctTree(
        this.size,
        this.position,
        this.limit_size);
    }

    return OctTreeBasic.newOctTree(this.size, this.position);
  }

  @Override public OctTreeSDType<T> buildWithSD()
  {
    if (this.prune) {
      if (this.limit) {
        return OctTreeSDPruneLimit.newOctTree(
          this.size,
          this.position,
          this.limit_size);
      }
      return OctTreeSDPrune.newOctTree(this.size, this.position);
    }
    if (this.limit) {
      return OctTreeSDLimit.newOctTree(
        this.size,
        this.position,
        this.limit_size);
    }
    return OctTreeSDBasic.newOctTree(this.size, this.position);
  }

  @Override public void disableLimitedOctantSizes()
  {
    this.limit = false;
  }

  @Override public void disablePruning()
  {
    this.prune = false;
  }

  @Override public void enableLimitedOctantSizes(
    final int x,
    final int y,
    final int z)
  {
    OctTreeChecks.checkSize("Octant minimum size", x, y, z);
    this.limit = true;
    this.limit_size.set3I(x, y, z);
  }

  @Override public void enablePruning()
  {
    this.prune = true;
  }

  @Override public void setPosition3i(
    final int x,
    final int y,
    final int z)
  {
    this.position.set3I(x, y, z);
  }

  @Override public void setSize3i(
    final int x,
    final int y,
    final int z)
  {
    OctTreeChecks.checkSize("Octtree size", x, y, z);
    this.size.set3I(x, y, z);
  }

}
