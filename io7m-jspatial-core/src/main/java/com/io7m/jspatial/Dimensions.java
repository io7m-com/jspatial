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
import javax.annotation.concurrent.ThreadSafe;

import com.io7m.jaux.UnreachableCodeException;
import com.io7m.jtensors.VectorReadable2D;
import com.io7m.jtensors.VectorReadable2F;
import com.io7m.jtensors.VectorReadable2I;
import com.io7m.jtensors.VectorReadable3D;
import com.io7m.jtensors.VectorReadable3F;
import com.io7m.jtensors.VectorReadable3I;

@ThreadSafe final class Dimensions
{
  /**
   * Return the span on the X axis of the inclusive range defined by the given
   * points.
   * 
   * @return upper.getXI() - lower.getXI()) + 1
   */

  static int getSpanSizeX(
    final @Nonnull VectorReadable2I lower,
    final @Nonnull VectorReadable2I upper)
  {
    return (upper.getXI() - lower.getXI()) + 1;
  }

  /**
   * Return the span on the X axis of the inclusive range defined by the given
   * points.
   * 
   * @return upper.getXD() - lower.getXD()
   * @since 2.1.0
   */

  static double getSpanSizeXD(
    final @Nonnull VectorReadable2D lower,
    final @Nonnull VectorReadable2D upper)
  {
    return (upper.getXD() - lower.getXD());
  }

  /**
   * Return the span on the X axis of the inclusive range defined by the given
   * points.
   * 
   * @return upper.getXF() - lower.getXF()
   * @since 2.1.0
   */

  static float getSpanSizeXF(
    final @Nonnull VectorReadable2F lower,
    final @Nonnull VectorReadable2F upper)
  {
    return (upper.getXF() - lower.getXF());
  }

  /**
   * Return the span on the Y axis of the inclusive range defined by the given
   * points.
   * 
   * @return upper.getYI() - lower.getYI()) + 1
   */

  static int getSpanSizeY(
    final @Nonnull VectorReadable2I lower,
    final @Nonnull VectorReadable2I upper)
  {
    return (upper.getYI() - lower.getYI()) + 1;
  }

  /**
   * Return the span on the Y axis of the inclusive range defined by the given
   * points.
   * 
   * @return upper.getYD() - lower.getYD()
   * @since 2.1.0
   */

  static double getSpanSizeYD(
    final @Nonnull VectorReadable2D lower,
    final @Nonnull VectorReadable2D upper)
  {
    return (upper.getYD() - lower.getYD());
  }

  /**
   * Return the span on the Y axis of the inclusive range defined by the given
   * points.
   * 
   * @return upper.getYF() - lower.getYF()
   * @since 2.1.0
   */

  static float getSpanSizeYF(
    final @Nonnull VectorReadable2F lower,
    final @Nonnull VectorReadable2F upper)
  {
    return (upper.getYF() - lower.getYF());
  }

  /**
   * Return the span on the Z axis of the inclusive range defined by the given
   * points.
   * 
   * @return upper.getZI() - lower.getZI()) + 1
   */

  static int getSpanSizeZ(
    final @Nonnull VectorReadable3I lower,
    final @Nonnull VectorReadable3I upper)
  {
    return (upper.getZI() - lower.getZI()) + 1;
  }

  /**
   * Return the span on the Z axis of the inclusive range defined by the given
   * points.
   * 
   * @return upper.getZD() - lower.getZD())
   * @since 2.1.0
   */

  static double getSpanSizeZD(
    final @Nonnull VectorReadable3D lower,
    final @Nonnull VectorReadable3D upper)
  {
    return (upper.getZD() - lower.getZD());
  }

  /**
   * Return the span on the Z axis of the inclusive range defined by the given
   * points.
   * 
   * @return upper.getZF() - lower.getZF())
   * @since 2.1.0
   */

  static float getSpanSizeZF(
    final @Nonnull VectorReadable3F lower,
    final @Nonnull VectorReadable3F upper)
  {
    return (upper.getZF() - lower.getZF());
  }

  /**
   * <p>
   * Given an inclusive range defined by <code>[low .. high]</code>, split the
   * range in the middle and produce two new inclusive ranges. That is,
   * produce a vector <code>v</code> such that:
   * </p>
   * <p>
   * <ul>
   * <li><code>v[0] == low</code></li>
   * <li><code>v[1] == ((high - low) / 2) - 1</code></li>
   * <li><code>v[2] == (high - low) / 2</code></li>
   * <li><code>v[3] == high</code></li>
   * </ul>
   * </p>
   * <p>
   * The function assumes <code>low &lt; high</code>, and
   * <code>out.length == 4</code>.
   * </p>
   */

  static void split1D(
    final int low,
    final int high,
    final int[] out)
  {
    assert low < high;
    assert out.length == 4;

    final int size = (high - low) + 1;
    out[0] = low;
    out[1] = low + ((size >> 1) - 1);
    out[2] = low + (size >> 1);
    out[3] = high;
  }

  /**
   * <p>
   * Given an inclusive range defined by <code>[low .. high]</code>, split the
   * range in the middle and produce two new inclusive ranges. That is,
   * produce a vector <code>v</code> such that:
   * </p>
   * <p>
   * <ul>
   * <li><code>v[0] == low</code></li>
   * <li><code>v[1] == pred ((high - low) / 2)</code></li>
   * <li><code>v[2] == (high - low) / 2</code></li>
   * <li><code>v[3] == high</code></li>
   * </ul>
   * </p>
   * <p>
   * Where <code>pred</code> is the floating point predecessor function (
   * {@link Math#nextAfter(float, double)} in this case).
   * </p>
   * <p>
   * The function assumes <code>low &lt; high</code>, and
   * <code>out.length == 4</code>.
   * </p>
   * 
   * @since 2.1.0
   */

  static void split1DF(
    final float low,
    final float high,
    final float[] out)
  {
    assert low < high;
    assert out.length == 4;

    final float size = high - low;
    final float diff = size / 2;

    out[0] = low;
    out[1] = Math.nextAfter(low + diff, Float.MIN_VALUE);
    out[2] = low + diff;
    out[3] = high;
  }

  /**
   * <p>
   * Given an inclusive range defined by <code>[low .. high]</code>, split the
   * range in the middle and produce two new ranges. That is, produce a vector
   * <code>v</code> such that:
   * </p>
   * <p>
   * <ul>
   * <li><code>v[0] == low</code></li>
   * <li><code>v[1] == pred ((high - low) / 2)</code></li>
   * <li><code>v[2] == (high - low) / 2</code></li>
   * <li><code>v[3] == high</code></li>
   * </ul>
   * </p>
   * <p>
   * Where <code>pred</code> is the floating point predecessor function (
   * {@link Math#nextAfter(double, double)} in this case).
   * </p>
   * <p>
   * The function assumes <code>low &lt; high</code>, and
   * <code>out.length == 4</code>.
   * </p>
   * 
   * @since 2.1.0
   */

  static void split1DD(
    final double low,
    final double high,
    final double[] out)
  {
    assert low < high;
    assert out.length == 4;

    final double size = high - low;
    final double diff = size / 2;

    out[0] = low;
    out[1] = Math.nextAfter(low + diff, Double.MIN_VALUE);
    out[2] = low + diff;
    out[3] = high;
  }

  private Dimensions()
  {
    throw new UnreachableCodeException();
  }
}
