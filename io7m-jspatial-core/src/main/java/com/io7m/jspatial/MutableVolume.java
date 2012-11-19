package com.io7m.jspatial;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jtensors.VectorM3I;
import com.io7m.jtensors.VectorReadable3I;

/**
 * A trivial mutable volume class, provided for convenience and intended to be
 * passed to various volume-consuming {@link OctTreeInterface} methods.
 */

@NotThreadSafe public final class MutableVolume implements BoundingVolume
{
  private final @Nonnull VectorM3I lower = new VectorM3I();
  private final @Nonnull VectorM3I upper = new VectorM3I();

  public MutableVolume()
  {
    // Nothing.
  }

  @Override public @Nonnull VectorReadable3I boundingVolumeLower()
  {
    return this.lower;
  }

  @Override public @Nonnull VectorReadable3I boundingVolumeUpper()
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

    final MutableVolume other = (MutableVolume) obj;
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

  public void setLowerZ(
    final int z)
  {
    this.lower.z = z;
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

  public void setUpperZ(
    final int z)
  {
    this.upper.z = z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[MutableVolume  ");
    builder.append(this.lower);
    builder.append(" ");
    builder.append(this.upper);
    builder.append("]");
    return builder.toString();
  }
}
