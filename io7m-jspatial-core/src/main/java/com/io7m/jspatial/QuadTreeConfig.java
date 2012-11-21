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

import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorReadable2I;

/**
 * Quadtree configuration class, used to instantiate quadtree implementations.
 */

public final class QuadTreeConfig
{
  private final @Nonnull VectorM2I size;
  private final @Nonnull VectorM2I minimum_size;

  public static final int          QUADTREE_DEFAULT_SIZE_X;
  public static final int          QUADTREE_DEFAULT_SIZE_Y;
  public static final int          QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_X;
  public static final int          QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_Y;

  static {
    QUADTREE_DEFAULT_SIZE_X = 128;
    QUADTREE_DEFAULT_SIZE_Y = 128;
    QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_X = 2;
    QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_Y = 2;
  }

  /**
   * Initialize an empty configuration. The default values will build a
   * quadtree of size
   * <code>({@link QuadTreeConfig#QUADTREE_DEFAULT_SIZE_X}, {@link QuadTreeConfig#QUADTREE_DEFAULT_SIZE_Y})</code>
   * with a minimum quadrant size of
   * <code>({@link QuadTreeConfig#QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_X}, {@link QuadTreeConfig#QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_Y})</code>
   * .
   * 
   * Note that not all implementations will respect the minimum quadrant size.
   */

  public QuadTreeConfig()
  {
    this.size =
      new VectorM2I(
        QuadTreeConfig.QUADTREE_DEFAULT_SIZE_X,
        QuadTreeConfig.QUADTREE_DEFAULT_SIZE_Y);
    this.minimum_size =
      new VectorM2I(
        QuadTreeConfig.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_X,
        QuadTreeConfig.QUADTREE_DEFAULT_MINIMUM_QUADRANT_SIZE_Y);
  }

  @Override public boolean equals(
    final Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }

    final QuadTreeConfig other = (QuadTreeConfig) obj;
    if (!this.minimum_size.equals(other.minimum_size)) {
      return false;
    }
    if (!this.size.equals(other.size)) {
      return false;
    }
    return true;
  }

  /**
   * Retrieve the minimum quadrant size of the intended quadtree.
   */

  public @Nonnull VectorReadable2I getMinimumSize()
  {
    return this.minimum_size;
  }

  /**
   * Retrieve the minimum quadrant size on the X axis of the intended
   * quadtree.
   */

  public int getMinimumSizeX()
  {
    return this.minimum_size.x;
  }

  /**
   * Retrieve the minimum quadrant size on the Y axis of the intended
   * quadtree.
   */

  public int getMinimumSizeY()
  {
    return this.minimum_size.y;
  }

  /**
   * Retrieve the size of the intended quadtree.
   */

  public @Nonnull VectorReadable2I getSize()
  {
    return this.size;
  }

  /**
   * Retrieve the size on the X axis of the intended quadtree.
   */

  public int getSizeX()
  {
    return this.size.x;
  }

  /**
   * Retrieve the size on the Y axis of the intended quadtree.
   */

  public int getSizeY()
  {
    return this.size.y;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.minimum_size.hashCode();
    result = (prime * result) + this.size.hashCode();
    return result;
  }

  /**
   * Set the minimum quadrant size on the X axis of the intended quadtree.
   */

  public void setMinimumSizeX(
    final int x)
  {
    this.minimum_size.x = x;
  }

  /**
   * Set the minimum quadrant size on the Y axis of the intended quadtree.
   */

  public void setMinimumSizeY(
    final int y)
  {
    this.minimum_size.y = y;
  }

  /**
   * Set the size on the X axis of the intended quadtree.
   */

  public void setSizeX(
    final int x)
  {
    this.size.x = x;
  }

  /**
   * Set the size on the Y axis of the intended quadtree.
   */

  public void setSizeY(
    final int y)
  {
    this.size.y = y;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[QuadTreeConfig ");
    builder.append(this.size);
    builder.append(" ");
    builder.append(this.minimum_size);
    builder.append("]");
    return builder.toString();
  }
}
