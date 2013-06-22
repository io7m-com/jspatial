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

/**
 * <p>
 * An object returned by a successful raycast operation. Objects are ordered
 * by their scalar distance from the origin of the ray.
 * </p>
 * <p>
 * Note that although the <code>QuadTreeRaycastResultD</code> class is
 * immutable, whether or not the object of type <code>T</code> returned by
 * {@link #getObject()} is mutable depends entirely on the user and therefore
 * thread-safety is also the responsibility of the user.
 * </p>
 * 
 * @since 2.1.0
 */

@Immutable public final class QuadTreeRaycastResultD<T extends BoundingAreaD> implements
  Comparable<QuadTreeRaycastResultD<T>>
{
  private final double     distance;
  private final @Nonnull T object;

  QuadTreeRaycastResultD(
    final @Nonnull T object,
    final double distance)
  {
    this.object = object;
    this.distance = distance;
  }

  @Override public int compareTo(
    final @Nonnull QuadTreeRaycastResultD<T> other)
  {
    return Double.compare(this.distance, other.distance);
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
    final QuadTreeRaycastResultD<?> other = (QuadTreeRaycastResultD<?>) obj;
    if (Double.doubleToLongBits(this.distance) != Double
      .doubleToLongBits(other.distance)) {
      return false;
    }
    if (!this.object.equals(other.object)) {
      return false;
    }
    return true;
  }

  /**
   * Retrieve the distance of this object from the origin of the ray.
   */

  public double getDistance()
  {
    return this.distance;
  }

  /**
   * Retrieve the intersected object.
   */

  public @Nonnull T getObject()
  {
    return this.object;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(this.distance);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    result = (prime * result) + this.object.hashCode();
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[QuadTreeRaycastResultF ");
    builder.append(this.distance);
    builder.append(" ");
    builder.append(this.object);
    builder.append("]");
    return builder.toString();
  }
}