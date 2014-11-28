package com.io7m.jspatial.tests;

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jspatial.quadtrees.QuadTreeMemberType;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorReadable2IType;

public final class Rectangle implements QuadTreeMemberType<Rectangle>
{
  private final VectorI2I lower;
  private final VectorI2I upper;
  private final long      id;

  public Rectangle(
    final long in_id,
    final VectorI2I in_lower,
    final VectorI2I in_upper)
  {
    this.id = in_id;
    this.lower = NullCheck.notNull(in_lower);
    this.upper = NullCheck.notNull(in_upper);
  }

  @Override public VectorReadable2IType boundingAreaLower()
  {
    return this.lower;
  }

  @Override public VectorReadable2IType boundingAreaUpper()
  {
    return this.upper;
  }

  @Override public int compareTo(
    final @Nullable Rectangle other)
  {
    final Rectangle o = NullCheck.notNull(other);

    if (o.id < this.id) {
      return -1;
    }
    if (o.id > this.id) {
      return 1;
    }
    return 0;
  }

  @Override public boolean equals(
    final @Nullable Object obj)
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
    if (!this.lower.equals(other.lower)) {
      return false;
    }
    if (!this.upper.equals(other.upper)) {
      return false;
    }
    return true;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.lower.hashCode();
    result = (prime * result) + this.upper.hashCode();
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder b = new StringBuilder();
    b.append("[Rectangle lower=");
    b.append(this.lower);
    b.append(", upper=");
    b.append(this.upper);
    b.append("]");
    final String r = b.toString();
    assert r != null;
    return r;
  }
}
