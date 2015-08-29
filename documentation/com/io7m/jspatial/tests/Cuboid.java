package com.io7m.jspatial.tests;

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jspatial.octtrees.OctTreeMemberType;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorReadable3IType;

public final class Cuboid implements OctTreeMemberType<Cuboid>
{
  private final long      id;
  private final VectorI3I lower;
  private final VectorI3I upper;

  public Cuboid(
    final long in_id,
    final VectorI3I in_lower,
    final VectorI3I in_upper)
  {
    this.id = in_id;
    this.lower = in_lower;
    this.upper = in_upper;
  }

  @Override public VectorReadable3IType boundingVolumeLower()
  {
    return this.lower;
  }

  @Override public VectorReadable3IType boundingVolumeUpper()
  {
    return this.upper;
  }

  @Override public int compareTo(
    final @Nullable Cuboid other)
  {
    final Cuboid o = NullCheck.notNull(other, "Other");

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
    final Cuboid other = (Cuboid) obj;
    if (!this.lower.equals(other.lower)) {
      return false;
    }
    if (!this.upper.equals(other.upper)) {
      return false;
    }
    return true;
  }

  public long getId()
  {
    return this.id;
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
    b.append("[Cuboid lower=");
    b.append(this.lower);
    b.append(", upper=");
    b.append(this.upper);
    b.append("]");
    final String r = b.toString();
    assert r != null;
    return r;
  }
}
