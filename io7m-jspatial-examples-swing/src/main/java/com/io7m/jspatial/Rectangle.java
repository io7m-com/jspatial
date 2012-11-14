package com.io7m.jspatial;

import javax.annotation.Nonnull;

import com.io7m.jspatial.BoundingArea;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorReadable2I;

final class Rectangle implements BoundingArea
{
  private final @Nonnull VectorI2I lower;
  private final @Nonnull VectorI2I upper;

  public Rectangle(
    final @Nonnull VectorI2I lower,
    final @Nonnull VectorI2I upper)
  {
    this.lower = lower;
    this.upper = upper;
  }

  @Override public @Nonnull VectorReadable2I boundingAreaLower()
  {
    return this.lower;
  }

  @Override public @Nonnull VectorReadable2I boundingAreaUpper()
  {
    return this.upper;
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
    final Rectangle other = (Rectangle) obj;
    if (this.lower == null) {
      if (other.lower != null) {
        return false;
      }
    } else if (!this.lower.equals(other.lower)) {
      return false;
    }
    if (this.upper == null) {
      if (other.upper != null) {
        return false;
      }
    } else if (!this.upper.equals(other.upper)) {
      return false;
    }
    return true;
  }

  int getHeight()
  {
    return (this.upper.y - this.lower.y) + 1;
  }

  int getWidth()
  {
    return (this.upper.x - this.lower.x) + 1;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result =
      (prime * result) + ((this.lower == null) ? 0 : this.lower.hashCode());
    result =
      (prime * result) + ((this.upper == null) ? 0 : this.upper.hashCode());
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[Rectangle lower=");
    builder.append(this.lower);
    builder.append(", upper=");
    builder.append(this.upper);
    builder.append("]");
    return builder.toString();
  }
}
