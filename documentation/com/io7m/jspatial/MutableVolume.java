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

package com.io7m.jspatial;

import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorM3I;
import com.io7m.jtensors.VectorReadable3IType;

/**
 * A trivial mutable volume class, provided for convenience and intended to be
 * passed to various volume-consuming
 * {@link com.io7m.jspatial.octtrees.OctTreeType} methods.
 */

public final class MutableVolume implements BoundingVolumeType
{
  private final VectorM3I lower = new VectorM3I();
  private final VectorM3I upper = new VectorM3I();

  /**
   * Construct a new mutable volume.
   */

  public MutableVolume()
  {
    // Nothing.
  }

  @Override public VectorReadable3IType boundingVolumeLower()
  {
    return this.lower;
  }

  @Override public VectorReadable3IType boundingVolumeUpper()
  {
    return this.upper;
  }

  @Override public boolean equals(
    final @Nullable Object obj)
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

    final MutableVolume other = (MutableVolume) obj;
    return this.lower.equals(other.lower) && this.upper.equals(other.upper);
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.lower.hashCode();
    result = (prime * result) + this.upper.hashCode();
    return result;
  }

  /**
   * Set the lower corner of the mutable volume.
   *
   * @param x
   *          The X coordinate
   * @param y
   *          The Y coordinate
   * @param z
   *          The Z coordinate
   */

  public void setLower3i(
    final int x,
    final int y,
    final int z)
  {
    this.lower.set3I(x, y, z);
  }

  /**
   * Set the upper corner of the mutable volume.
   *
   * @param x
   *          The X coordinate
   * @param y
   *          The Y coordinate
   * @param z
   *          The Z coordinate
   */

  public void setUpper3i(
    final int x,
    final int y,
    final int z)
  {
    this.upper.set3I(x, y, z);
  }

  @Override public String toString()
  {
    final StringBuilder b = new StringBuilder();
    b.append("[MutableVolume  ");
    b.append(this.lower);
    b.append(" ");
    b.append(this.upper);
    b.append("]");
    final String r = b.toString();
    assert r != null;
    return r;
  }
}
