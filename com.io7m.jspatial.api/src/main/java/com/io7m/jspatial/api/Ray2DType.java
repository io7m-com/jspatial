/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
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

import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import org.immutables.value.Value;

/**
 * Immutable two-dimensional ray type, defined as an origin and a direction
 * vector.
 *
 * @since 3.0.0
 */

@JSpatialImmutableStyleType
@Value.Immutable
public interface Ray2DType
{
  /**
   * @return The origin point of this ray
   */

  @Value.Parameter
  Vector2D origin();

  /**
   * @return The direction of this ray
   */

  @Value.Parameter
  Vector2D direction();

  /**
   * @return The inverted direction of this ray
   */

  @Value.Derived
  @Value.Auxiliary
  default Vector2D directionInverse()
  {
    return Vector2D.of(
      1.0 / this.direction().x(),
      1.0 / this.direction().y());
  }

  /**
   * <p>Branchless optimization of the Kay-Kajiya slab ray/AABB intersection
   * test by Tavian Barnes.</p>
   *
   * <p>See <a href="http://tavianator.com/2011/05/fast-branchless-raybounding-box-intersections/">tavianator.com</a>.</p>
   *
   * @param x0 The lower X coordinate.
   * @param x1 The upper X coordinate.
   * @param y0 The lower Y coordinate.
   * @param y1 The upper Y coordinate.
   *
   * @return {@code true} if the ray is intersecting the box.
   */

  default boolean intersectsArea(
    final double x0,
    final double y0,
    final double x1,
    final double y1)
  {
    final Vector2D origin = this.origin();
    final Vector2D direction_inverse = this.directionInverse();

    final double tx0 = (x0 - origin.x()) * direction_inverse.x();
    final double tx1 = (x1 - origin.x()) * direction_inverse.x();

    double tmin = Math.min(tx0, tx1);
    double tmax = Math.max(tx0, tx1);

    final double ty0 = (y0 - origin.y()) * direction_inverse.y();
    final double ty1 = (y1 - origin.y()) * direction_inverse.y();

    tmin = Math.max(tmin, Math.min(ty0, ty1));
    tmax = Math.min(tmax, Math.max(ty0, ty1));

    return ((tmax >= Math.max(0.0, tmin)) && (tmin < Double.POSITIVE_INFINITY));
  }
}
