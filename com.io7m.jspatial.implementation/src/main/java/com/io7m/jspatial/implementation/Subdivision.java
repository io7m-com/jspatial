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

package com.io7m.jspatial.implementation;

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jintegers.CheckedMath;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions to subdivide ranges.
 */

public final class Subdivision
{
  private Subdivision()
  {
    throw new UnreachableCodeException();
  }

  /**
   * <p>
   * Given a half closed range defined by {@code [low .. high]}, subdivide the
   * range in the middle and produce two new half closed ranges.
   * </p>
   * <p>
   * The lower and upper bounds of the lower range are stored in
   * {@code out[0]} and {@code out[1]}, respectively. The lower and
   * upper bounds of the upper range are stored in {@code out[2]} and
   * {@code out[3]}, respectively.
   * </p>
   *
   * @param low  The lower bound
   * @param high The upper bound
   * @param out  The output vector
   */

  public static void subdivide1L(
    final long low,
    final long high,
    final long[] out)
  {
    Preconditions.checkPreconditionL(
      (long) out.length, out.length == 4, x -> "Output length must be 4");

    final long size = CheckedMath.subtract(high, low);

    Preconditions.checkPreconditionL(
      size, size >= 2L, x -> "Size must be >= 2");

    out[0] = low;
    out[1] = CheckedMath.add(low, size >> 1);
    out[2] = CheckedMath.add(low, size >> 1);
    out[3] = high;
  }

  /**
   * <p>
   * Given a half closed range defined by {@code [low .. high]}, subdivide the
   * range in the middle and produce two new half closed ranges.
   * </p>
   * <p>
   * The lower and upper bounds of the lower range are stored in
   * {@code out[0]} and {@code out[1]}, respectively. The lower and
   * upper bounds of the upper range are stored in {@code out[2]} and
   * {@code out[3]}, respectively.
   * </p>
   *
   * @param low  The lower bound
   * @param high The upper bound
   * @param out  The output vector
   */

  public static void subdivide1D(
    final double low,
    final double high,
    final double[] out)
  {
    Preconditions.checkPreconditionL(
      (long) out.length, out.length == 4, x -> "Output length must be 4");

    final double size = high - low;
    out[0] = low;
    out[1] = low + (size / 2.0);
    out[2] = low + (size / 2.0);
    out[3] = high;
  }
}
