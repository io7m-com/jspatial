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
import com.io7m.jtensors.VectorI2L;
import org.immutables.value.Value;

/**
 * <p>The type of bounding areas with {@code long} integer coordinates.</p>
 *
 * <p>Bounding areas have an inclusive lower bound and an exclusive upper
 * bound.</p>
 *
 * @since 3.0.0
 */

@ImmutableStyleType
@Value.Immutable
public interface BoundingAreaLType
{
  /**
   * Check the area invariants.
   */

  @Value.Check
  default void checkInvariants()
  {
    final VectorI2L lo = this.lower();
    final VectorI2L hi = this.upper();

    if (lo.getXL() >= hi.getXL()) {
      final StringBuilder sb = new StringBuilder(64);
      sb.append("Malformed area bounds.");
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
      sb.append("Malformed area bounds.");
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
  }

  /**
   * @return The lower corner of the area
   */

  @Value.Parameter(order = 0)
  VectorI2L lower();

  /**
   * @return The exclusive upper corner of the area
   */

  @Value.Parameter(order = 1)
  VectorI2L upper();

  /**
   * @return The width of the area
   */

  default long width()
  {
    return CheckedMath.subtract(this.upper().getXL(), this.lower().getXL());
  }

  /**
   * @return The height of the area
   */

  default long height()
  {
    return CheckedMath.subtract(this.upper().getYL(), this.lower().getYL());
  }

  /**
   * <p>
   * Determine whether or not this area overlaps another given area.
   * </p>
   * <p>
   * Overlapping is reflexive: {@code ∀a. a.overlaps(a) == true}
   * </p>
   * <p>
   * Overlapping is symmetric: {@code ∀a b. a.overlaps(b) == b.overlaps(a)}
   * </p>
   *
   * @param other The other area
   *
   * @return {@code true} iff this area overlaps {@code other}
   */

  default boolean overlaps(
    final BoundingAreaLType other)
  {
    NullCheck.notNull(other, "other");

    final VectorI2L a_lo = this.lower();
    final VectorI2L a_hi = this.upper();
    final VectorI2L b_lo = other.lower();
    final VectorI2L b_hi = other.upper();

    final long a_x0 = a_lo.getXL();
    final long a_x1 = a_hi.getXL();
    final long a_y0 = a_lo.getYL();
    final long a_y1 = a_hi.getYL();
    final long b_x0 = b_lo.getXL();
    final long b_x1 = b_hi.getXL();
    final long b_y0 = b_lo.getYL();
    final long b_y1 = b_hi.getYL();

    final boolean c0 = a_x0 < b_x1;
    final boolean c1 = a_x1 > b_x0;
    final boolean c2 = a_y0 < b_y1;
    final boolean c3 = a_y1 > b_y0;

    return c0 && c1 && c2 && c3;
  }

  /**
   * <p>
   * Determine whether or not this area contains another given area.
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
   * @param other The other area
   *
   * @return {@code true} iff this area contains {@code other}
   */

  default boolean contains(
    final BoundingAreaLType other)
  {
    NullCheck.notNull(other, "other");

    final VectorI2L a_lo = this.lower();
    final VectorI2L a_hi = this.upper();
    final VectorI2L b_lo = other.lower();
    final VectorI2L b_hi = other.upper();

    final long a_x0 = a_lo.getXL();
    final long a_x1 = a_hi.getXL();
    final long a_y0 = a_lo.getYL();
    final long a_y1 = a_hi.getYL();
    final long b_x0 = b_lo.getXL();
    final long b_x1 = b_hi.getXL();
    final long b_y0 = b_lo.getYL();
    final long b_y1 = b_hi.getYL();

    final boolean c0 = b_x0 >= a_x0;
    final boolean c1 = b_x1 <= a_x1;
    final boolean c2 = b_y0 >= a_y0;
    final boolean c3 = b_y1 <= a_y1;

    return c0 && c1 && c2 && c3;
  }
}
