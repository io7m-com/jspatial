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

package com.io7m.jspatial.examples.jogl;

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jspatial.octtrees.OctTreeMemberType;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorReadable3IType;

final class Cuboid implements OctTreeMemberType<Cuboid>
{
  private final VectorI3I lower;
  private final VectorI3I upper;
  private final long      id;

  Cuboid(
    final long in_id,
    final VectorI3I in_lower,
    final VectorI3I in_upper)
  {
    this.id = in_id;
    this.lower = NullCheck.notNull(in_lower);
    this.upper = NullCheck.notNull(in_upper);
  }

  @Override public VectorReadable3IType boundingVolumeLower()
  {
    return this.lower;
  }

  @Override public VectorReadable3IType boundingVolumeUpper()
  {
    return this.upper;
  }

  @Override public int compareTo(
    final @Nullable Cuboid other)
  {
    final Cuboid o = NullCheck.notNull(other);
    if (o.id < this.id) {
      return -1;
    }
    if (o.id > this.id) {
      return 1;
    }
    return 0;
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
    final Cuboid other = (Cuboid) obj;
    if (!this.lower.equals(other.lower)) {
      return false;
    }
    if (!this.upper.equals(other.upper)) {
      return false;
    }
    return true;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.lower.hashCode();
    result = (prime * result) + this.upper.hashCode();
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
