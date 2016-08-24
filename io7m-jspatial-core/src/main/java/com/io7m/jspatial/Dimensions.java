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

import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorReadable3IType;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions to calculate dimensions and spans.
 */

public final class Dimensions
{
  /**
   * Return the span on the X axis of the inclusive range defined by the given
   * points.
   *
   * @param lower
   *          The lower point
   * @param upper
   *          The upper point
   * @return upper.getXI() - lower.getXI()) + 1
   */

  public static int getSpanSizeX(
    final VectorReadable2IType lower,
    final VectorReadable2IType upper)
  {
    return (upper.getXI() - lower.getXI()) + 1;
  }

  /**
   * Return the span on the Y axis of the inclusive range defined by the given
   * points.
   *
   * @param lower
   *          The lower point
   * @param upper
   *          The upper point
   * @return upper.getYI() - lower.getYI()) + 1
   */

  public static int getSpanSizeY(
    final VectorReadable2IType lower,
    final VectorReadable2IType upper)
  {
    return (upper.getYI() - lower.getYI()) + 1;
  }

  /**
   * Return the span on the Z axis of the inclusive range defined by the given
   * points.
   *
   * @param lower
   *          The lower point
   * @param upper
   *          The upper point
   * @return upper.getZI() - lower.getZI()) + 1
   */

  public static int getSpanSizeZ(
    final VectorReadable3IType lower,
    final VectorReadable3IType upper)
  {
    return (upper.getZI() - lower.getZI()) + 1;
  }

  /**
   * <p>
   * Given an inclusive range defined by <code>[low .. high]</code>, split the
   * range in the middle and produce two new inclusive ranges.
   * </p>
   * <p>
   * The lower and upper bounds of the lower range are stored in
   * <code>out[0]</code> and <code>out[1]</code>, respectively. The lower and
   * upper bounds of the upper range are stored in <code>out[2]</code> and
   * <code>out[3]</code>, respectively.
   * </p>
   *
   * @param low
   *          The lower bound
   * @param high
   *          The upper bound
   * @param out
   *          The output vector
   */

  public static void split1D(
    final int low,
    final int high,
    final int[] out)
  {
    assert out.length == 4;

    final int size = (high - low) + 1;
    out[0] = low;
    out[1] = low + ((size >> 1) - 1);
    out[2] = low + (size >> 1);
    out[3] = high;
  }

  private Dimensions()
  {
    throw new UnreachableCodeException();
  }
}
