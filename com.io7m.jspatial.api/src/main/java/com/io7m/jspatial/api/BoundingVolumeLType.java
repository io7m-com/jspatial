/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jspatial.api;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jtensors.VectorI3L;
import org.immutables.value.Value;

/**
 * <p>The type of bounding volumes with {@code long} integer coordinates.</p>
 *
 * <p>Bounding volumes have an inclusive lower bound and an exclusive upper
 * bound.</p>
 *
 * @since 3.0.0
 */

@ImmutableStyleType
@Value.Immutable
public interface BoundingVolumeLType
{
  /**
   * Check the volume invariants.
   */

  @Value.Check
  default void checkInvariants()
  {
    final VectorI3L lo = this.lower();
    final VectorI3L hi = this.upper();

    if (lo.getXL() >= hi.getXL()) {
      final StringBuilder sb = new StringBuilder(64);
      sb.append("Malformed volume bounds.");
      sb.append(System.lineSeparator());
      sb.append("  Lower X coordinate must be < upper X coordinate.");
      sb.append(System.lineSeparator());
      sb.append("  Lower: ");
      sb.append(lo);
      sb.append(System.lineSeparator());
      sb.append("  Upper: ");
      sb.append(hi);
      sb.append(System.lineSeparator());
      throw new IllegalArgumentException(sb.toString());
    }

    if (lo.getYL() >= hi.getYL()) {
      final StringBuilder sb = new StringBuilder(64);
      sb.append("Malformed volume bounds.");
      sb.append(System.lineSeparator());
      sb.append("  Lower Y coordinate must be < upper Y coordinate.");
      sb.append(System.lineSeparator());
      sb.append("  Lower: ");
      sb.append(lo);
      sb.append(System.lineSeparator());
      sb.append("  Upper: ");
      sb.append(hi);
      sb.append(System.lineSeparator());
      throw new IllegalArgumentException(sb.toString());
    }

    if (lo.getZL() >= hi.getZL()) {
      final StringBuilder sb = new StringBuilder(64);
      sb.append("Malformed volume bounds.");
      sb.append(System.lineSeparator());
      sb.append("  Lower Z coordinate must be < upper Z coordinate.");
      sb.append(System.lineSeparator());
      sb.append("  Lower: ");
      sb.append(lo);
      sb.append(System.lineSeparator());
      sb.append("  Upper: ");
      sb.append(hi);
      sb.append(System.lineSeparator());
      throw new IllegalArgumentException(sb.toString());
    }
  }

  /**
   * @return The lower corner of the volume
   */

  @Value.Parameter(order = 0)
  VectorI3L lower();

  /**
   * @return The exclusive upper corner of the volume
   */

  @Value.Parameter(order = 1)
  VectorI3L upper();

  /**
   * @return The width of the volume
   */

  default long width()
  {
    return CheckedMath.subtract(this.upper().getXL(), this.lower().getXL());
  }

  /**
   * @return The height of the volume
   */

  default long height()
  {
    return CheckedMath.subtract(this.upper().getYL(), this.lower().getYL());
  }

  /**
   * @return The depth of the volume
   */

  default long depth()
  {
    return CheckedMath.subtract(this.upper().getZL(), this.lower().getZL());
  }

  /**
   * <p>
   * Determine whether or not this volume overlaps another given volume.
   * </p>
   * <p>
   * Overlapping is reflexive: {@code ∀a. a.overlaps(a) == true}
   * </p>
   * <p>
   * Overlapping is symmetric: {@code ∀a b. a.overlaps(b) == b.overlaps(a)}
   * </p>
   *
   * @param other The other volume
   *
   * @return {@code true} iff this volume overlaps {@code other}
   */

  default boolean overlaps(
    final BoundingVolumeLType other)
  {
    NullCheck.notNull(other, "other");

    final VectorI3L a_lo = this.lower();
    final VectorI3L a_hi = this.upper();
    final VectorI3L b_lo = other.lower();
    final VectorI3L b_hi = other.upper();

    final long a_x0 = a_lo.getXL();
    final long a_x1 = a_hi.getXL();
    final long a_y0 = a_lo.getYL();
    final long a_y1 = a_hi.getYL();
    final long a_z0 = a_lo.getZL();
    final long a_z1 = a_hi.getZL();

    final long b_x0 = b_lo.getXL();
    final long b_x1 = b_hi.getXL();
    final long b_y0 = b_lo.getYL();
    final long b_y1 = b_hi.getYL();
    final long b_z0 = b_lo.getZL();
    final long b_z1 = b_hi.getZL();

    final boolean c0 = a_x0 < b_x1;
    final boolean c1 = a_x1 > b_x0;
    final boolean c2 = a_y0 < b_y1;
    final boolean c3 = a_y1 > b_y0;
    final boolean c4 = a_z0 < b_z1;
    final boolean c5 = a_z1 > b_z0;

    return c0 && c1 && c2 && c3 && c4 && c5;
  }

  /**
   * <p>
   * Determine whether or not this volume contains another given volume.
   * </p>
   * <p>
   * Containment is reflexive:
   * {@code ∀a. a.contains(a) }
   * </p>
   * <p>
   * Containment is transitive:
   * {@code ∀a b c. a.contains(b) → b.contains(c) → a.contains(c)}
   * </p>
   * <p>
   * Containment implies overlapping:
   * {@code ∀a b. a.contains(b) → b.overlaps(a)}
   * </p>
   *
   * @param other The other volume
   *
   * @return {@code true} iff this volume contains {@code other}
   */

  default boolean contains(
    final BoundingVolumeLType other)
  {
    NullCheck.notNull(other, "other");

    final VectorI3L a_lo = this.lower();
    final VectorI3L a_hi = this.upper();
    final VectorI3L b_lo = other.lower();
    final VectorI3L b_hi = other.upper();

    final long a_x0 = a_lo.getXL();
    final long a_x1 = a_hi.getXL();
    final long a_y0 = a_lo.getYL();
    final long a_y1 = a_hi.getYL();
    final long a_z0 = a_lo.getZL();
    final long a_z1 = a_hi.getZL();

    final long b_x0 = b_lo.getXL();
    final long b_x1 = b_hi.getXL();
    final long b_y0 = b_lo.getYL();
    final long b_y1 = b_hi.getYL();
    final long b_z0 = b_lo.getZL();
    final long b_z1 = b_hi.getZL();

    final boolean c0 = b_x0 >= a_x0;
    final boolean c1 = b_x1 <= a_x1;
    final boolean c2 = b_y0 >= a_y0;
    final boolean c3 = b_y1 <= a_y1;
    final boolean c4 = b_z0 >= a_z0;
    final boolean c5 = b_z1 <= a_z1;

    return c0 && c1 && c2 && c3 && c4 && c5;
  }
}
