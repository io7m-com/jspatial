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

import com.io7m.jnull.NullCheck;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Overlap and containment checks between bounding areas.
 *
 * @see BoundingAreaType
 */

public final class BoundingAreaCheck
{
  /**
   * The result of an overlap or containment check.
   */

  public static enum Result
  {
    /**
     * The object is contained within the query area.
     */

    RESULT_CONTAINED_WITHIN,

    /**
     * The object does not overlap the query area.
     */

    RESULT_NO_OVERLAP,

    /**
     * The object is overlapped by the query area.
     */

    RESULT_OVERLAP,
  }

  /**
   * Determine whether <code>b</code> overlaps <code>a</code>, or is
   * completely contained by <code>a</code>.
   *
   * @param container
   *          The container
   * @param item
   *          The item that may or may not be contained
   * @return The containment result
   */

  public static Result checkAgainst(
    final BoundingAreaType container,
    final BoundingAreaType item)
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
   * Evaluates {@link #isWellFormed(BoundingAreaType)} for the given item and
   * raises {@link IllegalArgumentException} if the result is
   * <code>false</code>.
   *
   * @param item
   *          The area.
   */

  public static void checkWellFormed(
    final BoundingAreaType item)
  {
    if (BoundingAreaCheck.isWellFormed(item) == false) {
      throw new IllegalArgumentException("Bounding area is not well-formed");
    }
  }

  /**
   * @return <code>true</code> iff <code>item</code> is completely contained
   *         within <code>container</code>.
   * @param container
   *          The container
   * @param item
   *          The item that may or may not be contained
   */

  public static boolean containedWithin(
    final BoundingAreaType container,
    final BoundingAreaType item)
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

  /**
   * The area <code>a</code> described by the given vertices contains
   * <code>b</code>.
   *
   * @param a_x0
   *          The X coordinate of the lower corner of <code>a</code>
   * @param a_x1
   *          The X coordinate of the upper corner of <code>a</code>
   * @param a_y0
   *          The Y coordinate of the lower corner of <code>a</code>
   * @param a_y1
   *          The Y coordinate of the upper corner of <code>a</code>
   * @param b_x0
   *          The X coordinate of the lower corner of <code>b</code>
   * @param b_x1
   *          The X coordinate of the upper corner of <code>b</code>
   * @param b_y0
   *          The Y coordinate of the lower corner of <code>b</code>
   * @param b_y1
   *          The Y coordinate of the upper corner of <code>b</code>
   * @return <code>true</code> if <code>a</code> contains <code>b</code>.
   */

  public static boolean contains(
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

  /**
   * @param container
   *          The bounding area to examine.
   * @return <code>true</code> iff the given bounding area is well formed.
   *         That is, iff
   *         <code>container.boundingAreaLower().getXI() <= container.boundingAreaUpper().getXI()</code>
   *         and
   *         <code>container.boundingAreaLower().getYI() <= container.boundingAreaUpper().getYI()</code>
   *         .
   */

  public static boolean isWellFormed(
    final BoundingAreaType container)
  {
    NullCheck.notNull(container, "Container");

    final VectorReadable2IType lower = container.boundingAreaLower();
    final VectorReadable2IType upper = container.boundingAreaUpper();
    if (lower.getXI() > upper.getXI()) {
      return false;
    }
    if (lower.getYI() > upper.getYI()) {
      return false;
    }
    return true;
  }

  /**
   * The area <code>a</code> described by the given vertices overlaps
   * <code>b</code>.
   *
   * @param a_x0
   *          The X coordinate of the lower corner of <code>a</code>
   * @param a_x1
   *          The X coordinate of the upper corner of <code>a</code>
   * @param a_y0
   *          The Y coordinate of the lower corner of <code>a</code>
   * @param a_y1
   *          The Y coordinate of the upper corner of <code>a</code>
   * @param b_x0
   *          The X coordinate of the lower corner of <code>b</code>
   * @param b_x1
   *          The X coordinate of the upper corner of <code>b</code>
   * @param b_y0
   *          The Y coordinate of the lower corner of <code>b</code>
   * @param b_y1
   *          The Y coordinate of the upper corner of <code>b</code>
   * @return <code>true</code> if <code>a</code> overlaps <code>b</code>.
   */

  public static boolean overlaps(
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
   * @return <code>true</code> iff <code>item</code> overlaps
   *         <code>container</code>.
   * @param container
   *          The container
   * @param item
   *          The item that may or may not be overlapping
   */

  public static boolean overlapsArea(
    final BoundingAreaType container,
    final BoundingAreaType item)
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
   * @param ray
   *          The ray.
   * @param x0
   *          The lower X coordinate.
   * @param x1
   *          The upper X coordinate.
   * @param y0
   *          The lower Y coordinate.
   * @param y1
   *          The upper Y coordinate.
   *
   * @return <code>true</code> if the ray is intersecting the box.
   *
   * @see http://tavianator.com/2011/05/fast-branchless-raybounding-box-
   *      intersections/
   */

  public static boolean rayBoxIntersects(
    final RayI2D ray,
    final double x0,
    final double y0,
    final double x1,
    final double y1)
  {
    final VectorI2D ray_origin = ray.getOrigin();
    final VectorI2D ray_direction_inv = ray.getDirectionInverse();

    final double tx0 = (x0 - ray_origin.getXD()) * ray_direction_inv.getXD();
    final double tx1 = (x1 - ray_origin.getXD()) * ray_direction_inv.getXD();

    double tmin = Math.min(tx0, tx1);
    double tmax = Math.max(tx0, tx1);

    final double ty0 = (y0 - ray_origin.getYD()) * ray_direction_inv.getYD();
    final double ty1 = (y1 - ray_origin.getYD()) * ray_direction_inv.getYD();

    tmin = Math.max(tmin, Math.min(ty0, ty1));
    tmax = Math.min(tmax, Math.max(ty0, ty1));

    return ((tmax >= Math.max(0, tmin)) && (tmin < Double.POSITIVE_INFINITY));
  }

  private BoundingAreaCheck()
  {
    throw new UnreachableCodeException();
  }
}
