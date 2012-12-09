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

import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorM3I;
import com.io7m.jtensors.VectorReadable3I;

/**
 * Octtree configuration class, used to instantiate octtree implementations.
 */

public final class OctTreeConfig
{
  private final @Nonnull VectorM3I              size;
  private final @Nonnull VectorM3I              minimum_size;
  private final @Nonnull VectorM3I              position;

  public static final int                       OCTTREE_DEFAULT_SIZE_X;
  public static final int                       OCTTREE_DEFAULT_SIZE_Y;
  public static final int                       OCTTREE_DEFAULT_SIZE_Z;
  public static final int                       OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_X;
  public static final int                       OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Y;
  public static final int                       OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Z;
  public static final @Nonnull VectorReadable3I OCTTREE_DEFAULT_POSITION;

  static {
    OCTTREE_DEFAULT_SIZE_X = 128;
    OCTTREE_DEFAULT_SIZE_Y = 128;
    OCTTREE_DEFAULT_SIZE_Z = 128;
    OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_X = 2;
    OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Y = 2;
    OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Z = 2;
    OCTTREE_DEFAULT_POSITION = VectorI3I.ZERO;
  }

  /**
   * Initialize an empty configuration. The default values will build a
   * octtree of size
   * <code>({@link OctTreeConfig#OCTTREE_DEFAULT_SIZE_X}, {@link OctTreeConfig#OCTTREE_DEFAULT_SIZE_Y}, {@link OctTreeConfig#OCTTREE_DEFAULT_SIZE_Z})</code>
   * with a minimum octant size of
   * <code>({@link OctTreeConfig#OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_X}, {@link OctTreeConfig#OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Y}, {@link OctTreeConfig#OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Z})</code>
   * .
   * 
   * Note that not all implementations will respect the minimum octant size.
   */

  public OctTreeConfig()
  {
    this.position = new VectorM3I();
    VectorM3I.copy(OctTreeConfig.OCTTREE_DEFAULT_POSITION, this.position);

    this.size =
      new VectorM3I(
        OctTreeConfig.OCTTREE_DEFAULT_SIZE_X,
        OctTreeConfig.OCTTREE_DEFAULT_SIZE_Y,
        OctTreeConfig.OCTTREE_DEFAULT_SIZE_Z);
    this.minimum_size =
      new VectorM3I(
        OctTreeConfig.OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_X,
        OctTreeConfig.OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Y,
        OctTreeConfig.OCTTREE_DEFAULT_MINIMUM_OCTANT_SIZE_Z);
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

    final OctTreeConfig other = (OctTreeConfig) obj;
    if (!this.minimum_size.equals(other.minimum_size)) {
      return false;
    }
    if (!this.size.equals(other.size)) {
      return false;
    }
    return true;
  }

  /**
   * Retrieve the minimum octant size of the intended octtree.
   */

  public @Nonnull VectorReadable3I getMinimumSize()
  {
    return this.minimum_size;
  }

  /**
   * Retrieve the minimum octant size on the X axis of the intended octtree.
   */

  public int getMinimumSizeX()
  {
    return this.minimum_size.x;
  }

  /**
   * Retrieve the minimum octant size on the Y axis of the intended octtree.
   */

  public int getMinimumSizeY()
  {
    return this.minimum_size.y;
  }

  /**
   * Retrieve the minimum octant size on the Z axis of the intended octtree.
   */

  public int getMinimumSizeZ()
  {
    return this.minimum_size.z;
  }

  /**
   * Retrieve the position of the lower XYZ corner of the intended octtree.
   */

  public @Nonnull VectorReadable3I getPosition()
  {
    return this.position;
  }

  /**
   * Retrieve the position on the X axis of the lower corner of the intended
   * octtree.
   */

  public int getPositionX()
  {
    return this.position.getXI();
  }

  /**
   * Retrieve the position on the Y axis of the lower corner of the intended
   * octtree.
   */

  public int getPositionY()
  {
    return this.position.getYI();
  }

  /**
   * Retrieve the position on the Z axis of the lower corner of the intended
   * octtree.
   */

  public int getPositionZ()
  {
    return this.position.getZI();
  }

  /**
   * Retrieve the size of the intended octtree.
   */

  public @Nonnull VectorReadable3I getSize()
  {
    return this.size;
  }

  /**
   * Retrieve the size on the X axis of the intended octtree.
   */

  public int getSizeX()
  {
    return this.size.x;
  }

  /**
   * Retrieve the size on the Y axis of the intended octtree.
   */

  public int getSizeY()
  {
    return this.size.y;
  }

  /**
   * Retrieve the size on the Z axis of the intended octtree.
   */

  public int getSizeZ()
  {
    return this.size.z;
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
   * Set the minimum octant size on the X axis of the intended octtree.
   */

  public void setMinimumSizeX(
    final int x)
  {
    this.minimum_size.x = x;
  }

  /**
   * Set the minimum octant size on the Y axis of the intended octtree.
   */

  public void setMinimumSizeY(
    final int y)
  {
    this.minimum_size.y = y;
  }

  /**
   * Set the minimum octant size on the Z axis of the intended octtree.
   */

  public void setMinimumSizeZ(
    final int z)
  {
    this.minimum_size.z = z;
  }

  /**
   * Set the position of the lower XYZ corner of the intended octtree.
   */

  public void setPosition(
    final @Nonnull VectorReadable3I position)
  {
    VectorM3I.copy(position, this.position);
  }

  /**
   * Set the position on the X axis of the intended octtree.
   */

  public void setPositionX(
    final int x)
  {
    this.position.x = x;
  }

  /**
   * Set the position on the Y axis of the intended octtree.
   */

  public void setPositionY(
    final int y)
  {
    this.position.y = y;
  }

  /**
   * Set the position on the Z axis of the intended octtree.
   */

  public void setPositionZ(
    final int z)
  {
    this.position.z = z;
  }

  /**
   * Set the size on the X axis of the intended octtree.
   */

  public void setSizeX(
    final int x)
  {
    this.size.x = x;
  }

  /**
   * Set the size on the Y axis of the intended octtree.
   */

  public void setSizeY(
    final int y)
  {
    this.size.y = y;
  }

  /**
   * Set the size on the Z axis of the intended octtree.
   */

  public void setSizeZ(
    final int z)
  {
    this.size.z = z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[OctTreeConfig ");
    builder.append(this.size);
    builder.append(" ");
    builder.append(this.minimum_size);
    builder.append(" ");
    builder.append(this.position);
    builder.append("]");
    return builder.toString();
  }
}
