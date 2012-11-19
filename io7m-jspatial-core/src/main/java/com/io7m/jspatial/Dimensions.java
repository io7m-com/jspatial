package com.io7m.jspatial;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import com.io7m.jaux.UnreachableCodeException;
import com.io7m.jtensors.VectorReadable2I;
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
   * Given an inclusive range defined by <code>[low .. high]</code>, split the
   * range in the middle and produce two new inclusive ranges.
   * 
   * <p>
   * The lower and upper bounds of the lower range are stored in
   * <code>out[0]</code> and <code>out[1]</code>, respectively. The lower and
   * upper bounds of the upper range are stored in <code>out[2]</code> and
   * <code>out[3]</code>, respectively.
   * </p>
   */

  static void split1D(
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
