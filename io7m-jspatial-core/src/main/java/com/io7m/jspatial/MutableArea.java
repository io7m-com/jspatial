package com.io7m.jspatial;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorReadable2I;

/**
 * A trivial mutable area class, provided for convenience and intended to be
 * passed to various area-consuming {@link QuadTreeInterface} methods.
 */

@NotThreadSafe public final class MutableArea implements BoundingArea
{
  private final @Nonnull VectorM2I lower = new VectorM2I();
  private final @Nonnull VectorM2I upper = new VectorM2I();

  public MutableArea()
  {
    // Nothing.
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

    final MutableArea other = (MutableArea) obj;
    return this.lower.equals(other.lower) && this.upper.equals(other.upper);
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.lower.hashCode();
    result = (prime * result) + this.upper.hashCode();
    return result;
  }

  public void setLowerX(
    final int x)
  {
    this.lower.x = x;
  }

  public void setLowerY(
    final int y)
  {
    this.lower.y = y;
  }

  public void setUpperX(
    final int x)
  {
    this.upper.x = x;
  }

  public void setUpperY(
    final int y)
  {
    this.upper.y = y;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[MutableArea  ");
    builder.append(this.lower);
    builder.append(" ");
    builder.append(this.upper);
    builder.append("]");
    return builder.toString();
  }
}
