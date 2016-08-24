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

package com.io7m.jspatial;

import com.io7m.jnull.NullCheck;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorReadable3IType;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * <p>
 * Overlap and containment checks between bounding volumes.
 * </p>
 *
 * @see BoundingVolumeType
 */

public final class BoundingVolumeCheck
{
  /**
   * The result of an overlap or containment check.
   */

  public enum Result
  {
    /**
     * The object is contained within the query volume.
     */

    RESULT_CONTAINED_WITHIN,

    /**
     * The object does not overlap the query volume.
     */

    RESULT_NO_OVERLAP,

    /**
     * The object is overlapped by the query volume.
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
   *          The item that may be contained
   * @return The containment result
   */

  public static Result checkAgainst(
    final BoundingVolumeType container,
    final BoundingVolumeType item)
  {
    final int c_x0 = container.boundingVolumeLower().getXI();
    final int c_x1 = container.boundingVolumeUpper().getXI();
    final int c_y0 = container.boundingVolumeLower().getYI();
    final int c_y1 = container.boundingVolumeUpper().getYI();
    final int c_z0 = container.boundingVolumeLower().getZI();
    final int c_z1 = container.boundingVolumeUpper().getZI();

    final int i_x0 = item.boundingVolumeLower().getXI();
    final int i_x1 = item.boundingVolumeUpper().getXI();
    final int i_y0 = item.boundingVolumeLower().getYI();
    final int i_y1 = item.boundingVolumeUpper().getYI();
    final int i_z0 = item.boundingVolumeLower().getZI();
    final int i_z1 = item.boundingVolumeUpper().getZI();

    // Check for containment
    if (BoundingVolumeCheck.contains(
      c_x0,
      c_x1,
      c_y0,
      c_y1,
      c_z0,
      c_z1,
      i_x0,
      i_x1,
      i_y0,
      i_y1,
      i_z0,
      i_z1)) {
      return Result.RESULT_CONTAINED_WITHIN;
    }

    // Check for overlap.
    if (BoundingVolumeCheck.overlaps(
      c_x0,
      c_x1,
      c_y0,
      c_y1,
      c_z0,
      c_z1,
      i_x0,
      i_x1,
      i_y0,
      i_y1,
      i_z0,
      i_z1)) {
      return Result.RESULT_OVERLAP;
    }

    return Result.RESULT_NO_OVERLAP;
  }

  /**
   * Evaluates {@link #isWellFormed(BoundingVolumeType)} for the given item
   * and raises {@link IllegalArgumentException} if the result is
   * <code>false</code>.
   *
   * @param item
   *          The area.
   */

  public static void checkWellFormed(
    final BoundingVolumeType item)
  {
    if (BoundingVolumeCheck.isWellFormed(item) == false) {
      throw new IllegalArgumentException("Bounding volume is not well-formed");
    }
  }

  /**
   * @param container
   *          The container
   * @param item
   *          The item that may be contained
   * @return <code>true</code> iff <code>item</code> is completely contained
   *         within <code>container</code>.
   */

  public static boolean containedWithin(
    final BoundingVolumeType container,
    final BoundingVolumeType item)
  {
    final int c_x0 = container.boundingVolumeLower().getXI();
    final int c_x1 = container.boundingVolumeUpper().getXI();
    final int c_y0 = container.boundingVolumeLower().getYI();
    final int c_y1 = container.boundingVolumeUpper().getYI();
    final int c_z0 = container.boundingVolumeLower().getZI();
    final int c_z1 = container.boundingVolumeUpper().getZI();

    final int i_x0 = item.boundingVolumeLower().getXI();
    final int i_x1 = item.boundingVolumeUpper().getXI();
    final int i_y0 = item.boundingVolumeLower().getYI();
    final int i_y1 = item.boundingVolumeUpper().getYI();
    final int i_z0 = item.boundingVolumeLower().getZI();
    final int i_z1 = item.boundingVolumeUpper().getZI();

    return BoundingVolumeCheck.contains(
      c_x0,
      c_x1,
      c_y0,
      c_y1,
      c_z0,
      c_z1,
      i_x0,
      i_x1,
      i_y0,
      i_y1,
      i_z0,
      i_z1);
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
   * @param a_z0
   *          The Z coordinate of the lower corner of <code>a</code>
   * @param a_z1
   *          The Z coordinate of the upper corner of <code>a</code>
   * @param b_x0
   *          The X coordinate of the lower corner of <code>b</code>
   * @param b_x1
   *          The X coordinate of the upper corner of <code>b</code>
   * @param b_y0
   *          The Y coordinate of the lower corner of <code>b</code>
   * @param b_y1
   *          The Y coordinate of the upper corner of <code>b</code>
   * @param b_z0
   *          The Z coordinate of the lower corner of <code>b</code>
   * @param b_z1
   *          The Z coordinate of the upper corner of <code>b</code>
   * @return <code>true</code> if <code>a</code> contains <code>b</code>.
   */

  public static boolean contains(
    final int a_x0,
    final int a_x1,
    final int a_y0,
    final int a_y1,
    final int a_z0,
    final int a_z1,
    final int b_x0,
    final int b_x1,
    final int b_y0,
    final int b_y1,
    final int b_z0,
    final int b_z1)
  {
    final boolean c0 = b_x0 >= a_x0;
    final boolean c1 = b_x1 <= a_x1;
    final boolean c2 = b_y0 >= a_y0;
    final boolean c3 = b_y1 <= a_y1;
    final boolean c4 = b_z0 >= a_z0;
    final boolean c5 = b_z1 <= a_z1;

    return c0 && c1 && c2 && c3 && c4 && c5;
  }

  /**
   * @param container
   *          The volume
   * @return <code>true</code> iff the given bounding volume is well formed.
   *         That is, iff
   *         <code>container.boundingVolumeLower().getXI() &lt;= container.boundingVolumeUpper().getXI()</code>
   *         and
   *         <code>container.boundingVolumeLower().getYI() &lt;= container.boundingVolumeUpper().getYI()</code>
   *         and
   *         <code>container.boundingVolumeLower().getZI() &lt;= container.boundingVolumeUpper().getZI()</code>
   *         .
   */

  public static boolean isWellFormed(
    final BoundingVolumeType container)
  {
    NullCheck.notNull(container, "Container");

    final VectorReadable3IType lower = container.boundingVolumeLower();
    final VectorReadable3IType upper = container.boundingVolumeUpper();
    if (lower.getXI() > upper.getXI()) {
      return false;
    }
    if (lower.getYI() > upper.getYI()) {
      return false;
    }
    if (lower.getZI() > upper.getZI()) {
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
   * @param a_z0
   *          The Z coordinate of the lower corner of <code>a</code>
   * @param a_z1
   *          The Z coordinate of the upper corner of <code>a</code>
   * @param b_x0
   *          The X coordinate of the lower corner of <code>b</code>
   * @param b_x1
   *          The X coordinate of the upper corner of <code>b</code>
   * @param b_y0
   *          The Y coordinate of the lower corner of <code>b</code>
   * @param b_y1
   *          The Y coordinate of the upper corner of <code>b</code>
   * @param b_z0
   *          The Z coordinate of the lower corner of <code>b</code>
   * @param b_z1
   *          The Z coordinate of the upper corner of <code>b</code>
   * @return <code>true</code> if <code>a</code> overlaps <code>b</code>.
   */

  public static boolean overlaps(
    final int a_x0,
    final int a_x1,
    final int a_y0,
    final int a_y1,
    final int a_z0,
    final int a_z1,
    final int b_x0,
    final int b_x1,
    final int b_y0,
    final int b_y1,
    final int b_z0,
    final int b_z1)
  {
    final boolean c0 = a_x0 < b_x1;
    final boolean c1 = a_x1 > b_x0;
    final boolean c2 = a_y0 < b_y1;
    final boolean c3 = a_y1 > b_y0;
    final boolean c4 = a_z0 < b_z1;
    final boolean c5 = a_z1 > b_z0;

    return c0 && c1 && c2 && c3 && c4 && c5;
  }

  /**
   * @param container
   *          The container
   * @param item
   *          The item that may be overlapping
   * @return <code>true</code> iff <code>item</code> overlaps
   *         <code>container</code>.
   */

  public static boolean overlapsVolume(
    final BoundingVolumeType container,
    final BoundingVolumeType item)
  {
    final int c_x0 = container.boundingVolumeLower().getXI();
    final int c_x1 = container.boundingVolumeUpper().getXI();
    final int c_y0 = container.boundingVolumeLower().getYI();
    final int c_y1 = container.boundingVolumeUpper().getYI();
    final int c_z0 = container.boundingVolumeLower().getZI();
    final int c_z1 = container.boundingVolumeUpper().getZI();

    final int i_x0 = item.boundingVolumeLower().getXI();
    final int i_x1 = item.boundingVolumeUpper().getXI();
    final int i_y0 = item.boundingVolumeLower().getYI();
    final int i_y1 = item.boundingVolumeUpper().getYI();
    final int i_z0 = item.boundingVolumeLower().getZI();
    final int i_z1 = item.boundingVolumeUpper().getZI();

    return BoundingVolumeCheck.overlaps(
      c_x0,
      c_x1,
      c_y0,
      c_y1,
      c_z0,
      c_z1,
      i_x0,
      i_x1,
      i_y0,
      i_y1,
      i_z0,
      i_z1);
  }

  /**
   * <p>Branchless optimization of the Kay-Kajiya slab ray/AABB intersection test
   * by Tavian Barnes.</p>
   * <p>See <a href="http://tavianator.com/2011/05/fast-branchless-raybounding-box-intersections/">tavianator.com</a>.</p>

   * @param ray
   *          The ray
   * @param x0
   *          The lower X coordinate
   * @param y0
   *          The lower Y coordinate
   * @param z0
   *          The lower z coordinate
   * @param x1
   *          The upper X coordinate
   * @param y1
   *          The upper Y coordinate
   * @param z1
   *          The upper z coordinate
   * @return <code>true</code> if the ray intersects the box given by the
   *         corners.
   */

  public static boolean rayBoxIntersects(
    final RayI3D ray,
    final double x0,
    final double y0,
    final double z0,
    final double x1,
    final double y1,
    final double z1)
  {
    final VectorI3D ray_origin = ray.getOrigin();
    final VectorI3D ray_direction_inv = ray.getDirectionInverse();

    final double tx0 = (x0 - ray_origin.getXD()) * ray_direction_inv.getXD();
    final double tx1 = (x1 - ray_origin.getXD()) * ray_direction_inv.getXD();

    double tmin = Math.min(tx0, tx1);
    double tmax = Math.max(tx0, tx1);

    final double ty0 = (y0 - ray_origin.getYD()) * ray_direction_inv.getYD();
    final double ty1 = (y1 - ray_origin.getYD()) * ray_direction_inv.getYD();

    tmin = Math.max(tmin, Math.min(ty0, ty1));
    tmax = Math.min(tmax, Math.max(ty0, ty1));

    final double tz0 = (z0 - ray_origin.getZD()) * ray_direction_inv.getZD();
    final double tz1 = (z1 - ray_origin.getZD()) * ray_direction_inv.getZD();

    tmin = Math.max(tmin, Math.min(tz0, tz1));
    tmax = Math.min(tmax, Math.max(tz0, tz1));

    return ((tmax >= Math.max(0, tmin)) && (tmin < Double.POSITIVE_INFINITY));
  }

  private BoundingVolumeCheck()
  {
    throw new UnreachableCodeException();
  }
}
