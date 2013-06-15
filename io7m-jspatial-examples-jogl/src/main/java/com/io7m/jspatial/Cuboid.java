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

import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorReadable3I;

final class Cuboid implements OctTreeMember<Cuboid>
{
  private final @Nonnull VectorI3I lower;
  private final @Nonnull VectorI3I upper;
  private final long               id;

  Cuboid(
    final long id,
    final @Nonnull VectorI3I lower,
    final @Nonnull VectorI3I upper)
  {
    this.id = id;
    this.lower = lower;
    this.upper = upper;
  }

  @Override public @Nonnull VectorReadable3I boundingVolumeLower()
  {
    return this.lower;
  }

  @Override public @Nonnull VectorReadable3I boundingVolumeUpper()
  {
    return this.upper;
  }

  @Override public int compareTo(
    final Cuboid other)
  {
    if (other.id < this.id) {
      return -1;
    }
    if (other.id > this.id) {
      return 1;
    }
    return 0;
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
    final Cuboid other = (Cuboid) obj;
    if (this.lower == null) {
      if (other.lower != null) {
        return false;
      }
    } else if (!this.lower.equals(other.lower)) {
      return false;
    }
    if (this.upper == null) {
      if (other.upper != null) {
        return false;
      }
    } else if (!this.upper.equals(other.upper)) {
      return false;
    }
    return true;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result =
      (prime * result) + ((this.lower == null) ? 0 : this.lower.hashCode());
    result =
      (prime * result) + ((this.upper == null) ? 0 : this.upper.hashCode());
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[Cuboid lower=");
    builder.append(this.lower);
    builder.append(", upper=");
    builder.append(this.upper);
    builder.append("]");
    return builder.toString();
  }
}
