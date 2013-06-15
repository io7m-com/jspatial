/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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
import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jtensors.VectorM3I;
import com.io7m.jtensors.VectorReadable3I;

/**
 * A trivial mutable volume class, provided for convenience and intended to be
 * passed to various volume-consuming {@link OctTreeInterface} methods.
 */

@NotThreadSafe public final class MutableVolume implements BoundingVolume
{
  private final @Nonnull VectorM3I lower = new VectorM3I();
  private final @Nonnull VectorM3I upper = new VectorM3I();

  public MutableVolume()
  {
    // Nothing.
  }

  @Override public @Nonnull VectorReadable3I boundingVolumeLower()
  {
    return this.lower;
  }

  @Override public @Nonnull VectorReadable3I boundingVolumeUpper()
  {
    return this.upper;
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

  public void setLowerX(
    final int x)
  {
    this.lower.x = x;
  }

  public void setLowerY(
    final int y)
  {
    this.lower.y = y;
  }

  public void setLowerZ(
    final int z)
  {
    this.lower.z = z;
  }

  public void setUpperX(
    final int x)
  {
    this.upper.x = x;
  }

  public void setUpperY(
    final int y)
  {
    this.upper.y = y;
  }

  public void setUpperZ(
    final int z)
  {
    this.upper.z = z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[MutableVolume  ");
    builder.append(this.lower);
    builder.append(" ");
    builder.append(this.upper);
    builder.append("]");
    return builder.toString();
  }
}
