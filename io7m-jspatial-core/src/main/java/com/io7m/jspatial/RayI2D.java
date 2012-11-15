package com.io7m.jspatial;

import javax.annotation.Nonnull;

import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorReadable2D;

final class RayI2D
{
  final @Nonnull VectorI2D origin;
  final @Nonnull VectorI2D direction;
  final @Nonnull VectorI2D direction_inverse;

  RayI2D(
    final @Nonnull VectorReadable2D origin,
    final @Nonnull VectorReadable2D direction)
  {
    this.origin = new VectorI2D(origin);
    this.direction = new VectorI2D(direction);
    this.direction_inverse =
      new VectorI2D(1.0 / direction.getXD(), 1.0 / direction.getYD());
  }
}
