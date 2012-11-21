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
import javax.annotation.concurrent.ThreadSafe;

import com.io7m.jaux.UnreachableCodeException;

/**
 * Overlap and containment checks between bounding areas.
 * 
 * @see BoundingArea
 */

@ThreadSafe public final class BoundingAreaCheck
{
  static enum Result
  {
    RESULT_NO_OVERLAP,
    RESULT_OVERLAP,
    RESULT_CONTAINED_WITHIN,
  }

  /**
   * Determine whether <code>b</code> overlaps <code>a</code>, or is
   * completely contained by <code>a</code>.
   */

  static @Nonnull Result checkAgainst(
    final @Nonnull BoundingArea container,
    final @Nonnull BoundingArea item)
  {
    final int c_x0 = container.boundingAreaLower().getXI();
    final int c_x1 = container.boundingAreaUpper().getXI();
    final int c_y0 = container.boundingAreaLower().getYI();
    final int c_y1 = container.boundingAreaUpper().getYI();

    final int i_x0 = item.boundingAreaLower().getXI();
    final int i_x1 = item.boundingAreaUpper().getXI();
    final int i_y0 = item.boundingAreaLower().getYI();
    final int i_y1 = item.boundingAreaUpper().getYI();

    // Check for containment
    if (BoundingAreaCheck.contains(
      c_x0,
      c_x1,
      c_y0,
      c_y1,
      i_x0,
      i_x1,
      i_y0,
      i_y1)) {
      return Result.RESULT_CONTAINED_WITHIN;
    }

    // Check for overlap.
    if (BoundingAreaCheck.overlaps(
      c_x0,
      c_x1,
      c_y0,
      c_y1,
      i_x0,
      i_x1,
      i_y0,
      i_y1)) {
      return Result.RESULT_OVERLAP;
    }

    return Result.RESULT_NO_OVERLAP;
  }

  /**
   * Return <code>true</code> iff <code>item</code> is completely contained
   * within <code>container</code>.
   */

  static boolean containedWithin(
    final @Nonnull BoundingArea container,
    final @Nonnull BoundingArea item)
  {
    final int c_x0 = container.boundingAreaLower().getXI();
    final int c_x1 = container.boundingAreaUpper().getXI();
    final int c_y0 = container.boundingAreaLower().getYI();
    final int c_y1 = container.boundingAreaUpper().getYI();

    final int i_x0 = item.boundingAreaLower().getXI();
    final int i_x1 = item.boundingAreaUpper().getXI();
    final int i_y0 = item.boundingAreaLower().getYI();
    final int i_y1 = item.boundingAreaUpper().getYI();

    return BoundingAreaCheck.contains(
      c_x0,
      c_x1,
      c_y0,
      c_y1,
      i_x0,
      i_x1,
      i_y0,
      i_y1);
  }

  static boolean contains(
    final int a_x0,
    final int a_x1,
    final int a_y0,
    final int a_y1,
    final int b_x0,
    final int b_x1,
    final int b_y0,
    final int b_y1)
  {
    final boolean c0 = b_x0 >= a_x0;
    final boolean c1 = b_x1 <= a_x1;
    final boolean c2 = b_y0 >= a_y0;
    final boolean c3 = b_y1 <= a_y1;

    return (c0 && c1 && c2 && c3);
  }

  static boolean overlaps(
    final int a_x0,
    final int a_x1,
    final int a_y0,
    final int a_y1,
    final int b_x0,
    final int b_x1,
    final int b_y0,
    final int b_y1)
  {
    final boolean c0 = a_x0 < b_x1;
    final boolean c1 = a_x1 > b_x0;
    final boolean c2 = a_y0 < b_y1;
    final boolean c3 = a_y1 > b_y0;

    return c0 && c1 && c2 && c3;
  }

  /**
   * Return <code>true</code> iff <code>item</code> overlaps
   * <code>container</code>.
   */

  static boolean overlapsArea(
    final @Nonnull BoundingArea container,
    final @Nonnull BoundingArea item)
  {
    final int c_x0 = container.boundingAreaLower().getXI();
    final int c_x1 = container.boundingAreaUpper().getXI();
    final int c_y0 = container.boundingAreaLower().getYI();
    final int c_y1 = container.boundingAreaUpper().getYI();

    final int i_x0 = item.boundingAreaLower().getXI();
    final int i_x1 = item.boundingAreaUpper().getXI();
    final int i_y0 = item.boundingAreaLower().getYI();
    final int i_y1 = item.boundingAreaUpper().getYI();

    return BoundingAreaCheck.overlaps(
      c_x0,
      c_x1,
      c_y0,
      c_y1,
      i_x0,
      i_x1,
      i_y0,
      i_y1);
  }

  /**
   * Branchless optimization of the Kay-Kajiya slab ray/AABB intersection test
   * by Tavian Barnes.
   * 
   * @see http://tavianator.com/2011/05/fast-branchless-raybounding-box-
   *      intersections/
   */

  static boolean rayBoxIntersects(
    final @Nonnull RayI2D ray,
    final double x0,
    final double y0,
    final double x1,
    final double y1)
  {
    final double tx0 = (x0 - ray.origin.x) * ray.direction_inverse.x;
    final double tx1 = (x1 - ray.origin.x) * ray.direction_inverse.x;

    double tmin = Math.min(tx0, tx1);
    double tmax = Math.max(tx0, tx1);

    final double ty0 = (y0 - ray.origin.y) * ray.direction_inverse.y;
    final double ty1 = (y1 - ray.origin.y) * ray.direction_inverse.y;

    tmin = Math.max(tmin, Math.min(ty0, ty1));
    tmax = Math.min(tmax, Math.max(ty0, ty1));

    return ((tmax >= Math.max(0, tmin)) && (tmin < Double.POSITIVE_INFINITY));
  }

  /**
   * Return <code>true</code> iff the given bounding area is well formed. That
   * is, iff
   * <code>container.boundingAreaLower().getXI() <= container.boundingAreaUpper().getXI()</code>
   * and
   * <code>container.boundingAreaLower().getYI() <= container.boundingAreaUpper().getYI()</code>
   * .
   */

  static boolean wellFormed(
    final @Nonnull BoundingArea container)
  {
    if (container.boundingAreaLower().getXI() > container
      .boundingAreaUpper()
      .getXI()) {
      return false;
    }
    if (container.boundingAreaLower().getYI() > container
      .boundingAreaUpper()
      .getYI()) {
      return false;
    }
    return true;
  }

  private BoundingAreaCheck()
  {
    throw new UnreachableCodeException();
  }
}
