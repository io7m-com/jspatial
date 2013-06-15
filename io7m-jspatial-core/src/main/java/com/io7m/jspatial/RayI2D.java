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
import javax.annotation.concurrent.Immutable;

import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorReadable2D;

/**
 * Immutable two-dimensional ray type, defined as an origin and a direction
 * vector.
 */

@Immutable public final class RayI2D
{
  final @Nonnull VectorI2D origin;
  final @Nonnull VectorI2D direction;
  final @Nonnull VectorI2D direction_inverse;

  public RayI2D(
    final @Nonnull VectorReadable2D origin,
    final @Nonnull VectorReadable2D direction)
  {
    this.origin = new VectorI2D(origin);
    this.direction = new VectorI2D(direction);
    this.direction_inverse =
      new VectorI2D(1.0 / direction.getXD(), 1.0 / direction.getYD());
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

    final RayI2D other = (RayI2D) obj;
    if (!this.direction.equals(other.direction)) {
      return false;
    }
    if (!this.origin.equals(other.origin)) {
      return false;
    }
    return true;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.direction.hashCode();
    result = (prime * result) + this.direction_inverse.hashCode();
    result = (prime * result) + this.origin.hashCode();
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[RayI2D ");
    builder.append(this.origin);
    builder.append(" ");
    builder.append(this.direction);
    builder.append("]");
    return builder.toString();
  }
}
