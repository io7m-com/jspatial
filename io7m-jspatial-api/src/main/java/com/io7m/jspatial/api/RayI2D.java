/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jspatial.api;

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorReadable2DType;

/**
 * Immutable two-dimensional ray type, defined as an origin and a direction
 * vector.
 */

public final class RayI2D
{
  private final VectorI2D direction;
  private final VectorI2D direction_inverse;
  private final VectorI2D origin;

  /**
   * Construct a new immutable ray.
   *
   * @param in_origin    The origin
   * @param in_direction The direction
   */

  public RayI2D(
    final VectorReadable2DType in_origin,
    final VectorReadable2DType in_direction)
  {
    this.origin =
      new VectorI2D(NullCheck.notNull(in_origin, "Origin"));
    this.direction =
      new VectorI2D(NullCheck.notNull(in_direction, "Direction"));
    this.direction_inverse =
      new VectorI2D(
        1.0 / this.direction.getXD(),
        1.0 / this.direction.getYD());
  }

  @Override
  public boolean equals(
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

    final RayI2D other = (RayI2D) obj;
    return this.direction.equals(other.direction)
      && this.origin.equals(other.origin);
  }

  /**
   * @return The ray direction
   */

  public VectorI2D direction()
  {
    return this.direction;
  }

  /**
   * @return The inverse of the ray direction
   */

  public VectorI2D directionInverse()
  {
    return this.direction_inverse;
  }

  /**
   * @return The ray origin
   */

  public VectorI2D origin()
  {
    return this.origin;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.direction.hashCode();
    result = (prime * result) + this.direction_inverse.hashCode();
    result = (prime * result) + this.origin.hashCode();
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder b = new StringBuilder(64);
    b.append("[RayI2D ");
    b.append(this.origin);
    b.append(" ");
    b.append(this.direction);
    b.append("]");
    return b.toString();
  }
}
