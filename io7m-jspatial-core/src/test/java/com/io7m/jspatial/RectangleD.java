package com.io7m.jspatial;

import javax.annotation.Nonnull;

import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorReadable2D;

final class RectangleD implements QuadTreeMemberD<RectangleD>
{
  private final @Nonnull VectorI2D lower;
  private final @Nonnull VectorI2D upper;
  private final long               id;

  RectangleD(
    final long id,
    final @Nonnull VectorI2D lower,
    final @Nonnull VectorI2D upper)
  {
    this.id = id;
    this.lower = lower;
    this.upper = upper;
  }

  @Override public @Nonnull VectorReadable2D boundingAreaLowerD()
  {
    return this.lower;
  }

  @Override public @Nonnull VectorReadable2D boundingAreaUpperD()
  {
    return this.upper;
  }

  @Override public int compareTo(
    final RectangleD other)
  {
    if (other.id < this.id) {
      return -1;
    }
    if (other.id > this.id) {
      return 1;
    }
    return 0;
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
    final RectangleD other = (RectangleD) obj;
    if (this.id != other.id) {
      return false;
    }
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

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + (int) (this.id ^ (this.id >>> 32));
    result =
      (prime * result) + ((this.lower == null) ? 0 : this.lower.hashCode());
    result =
      (prime * result) + ((this.upper == null) ? 0 : this.upper.hashCode());
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[RectangleD lower=");
    builder.append(this.lower);
    builder.append(", upper=");
    builder.append(this.upper);
    builder.append("]");
    return builder.toString();
  }
}
