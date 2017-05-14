package com.io7m.jspatial.api;

import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * Immutable two-dimensional ray type, defined as an origin and a direction
 * vector.
 * @since 3.0.0
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "Ray2DType"})
public final class Ray2D implements Ray2DType {
  private final Vector2D origin;
  private final Vector2D direction;
  private final Vector2D directionInverse;

  private Ray2D(
      Vector2D origin,
      Vector2D direction) {
    this.origin = Objects.requireNonNull(origin, "origin");
    this.direction = Objects.requireNonNull(direction, "direction");
    this.directionInverse = Objects.requireNonNull(Ray2DType.super.directionInverse(), "directionInverse");
  }

  private Ray2D(
      Ray2D original,
      Vector2D origin,
      Vector2D direction) {
    this.origin = origin;
    this.direction = direction;
    this.directionInverse = Objects.requireNonNull(Ray2DType.super.directionInverse(), "directionInverse");
  }

  /**
   * @return The origin point of this ray
   */
  @Override
  public Vector2D origin() {
    return origin;
  }

  /**
   * @return The direction of this ray
   */
  @Override
  public Vector2D direction() {
    return direction;
  }

  /**
   * @return The inverted direction of this ray
   */
  @Override
  public Vector2D directionInverse() {
    return directionInverse;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Ray2DType#origin() origin} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for origin
   * @return A modified copy of the {@code this} object
   */
  public final Ray2D withOrigin(Vector2D value) {
    if (this.origin == value) return this;
    Vector2D newValue = Objects.requireNonNull(value, "origin");
    return new Ray2D(this, newValue, this.direction);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Ray2DType#direction() direction} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for direction
   * @return A modified copy of the {@code this} object
   */
  public final Ray2D withDirection(Vector2D value) {
    if (this.direction == value) return this;
    Vector2D newValue = Objects.requireNonNull(value, "direction");
    return new Ray2D(this, this.origin, newValue);
  }

  /**
   * This instance is equal to all instances of {@code Ray2D} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof Ray2D
        && equalTo((Ray2D) another);
  }

  private boolean equalTo(Ray2D another) {
    return origin.equals(another.origin)
        && direction.equals(another.direction);
  }

  /**
   * Computes a hash code from attributes: {@code origin}, {@code direction}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + origin.hashCode();
    h += (h << 5) + direction.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code Ray2D} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "Ray2D{"
        + "origin=" + origin
        + ", direction=" + direction
        + "}";
  }

  /**
   * Construct a new immutable {@code Ray2D} instance.
   * @param origin The value for the {@code origin} attribute
   * @param direction The value for the {@code direction} attribute
   * @return An immutable Ray2D instance
   */
  public static Ray2D of(Vector2D origin, Vector2D direction) {
    return new Ray2D(origin, direction);
  }

  /**
   * Creates an immutable copy of a {@link Ray2DType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Ray2D instance
   */
  public static Ray2D copyOf(Ray2DType instance) {
    if (instance instanceof Ray2D) {
      return (Ray2D) instance;
    }
    return Ray2D.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link Ray2D Ray2D}.
   * @return A new Ray2D builder
   */
  public static Ray2D.Builder builder() {
    return new Ray2D.Builder();
  }

  /**
   * Builds instances of type {@link Ray2D Ray2D}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_ORIGIN = 0x1L;
    private static final long INIT_BIT_DIRECTION = 0x2L;
    private long initBits = 0x3L;

    private Vector2D origin;
    private Vector2D direction;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code Ray2DType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(Ray2DType instance) {
      Objects.requireNonNull(instance, "instance");
      setOrigin(instance.origin());
      setDirection(instance.direction());
      return this;
    }

    /**
     * Initializes the value for the {@link Ray2DType#origin() origin} attribute.
     * @param origin The value for origin 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setOrigin(Vector2D origin) {
      this.origin = Objects.requireNonNull(origin, "origin");
      initBits &= ~INIT_BIT_ORIGIN;
      return this;
    }

    /**
     * Initializes the value for the {@link Ray2DType#direction() direction} attribute.
     * @param direction The value for direction 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setDirection(Vector2D direction) {
      this.direction = Objects.requireNonNull(direction, "direction");
      initBits &= ~INIT_BIT_DIRECTION;
      return this;
    }

    /**
     * Builds a new {@link Ray2D Ray2D}.
     * @return An immutable instance of Ray2D
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public Ray2D build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new Ray2D(null, origin, direction);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_ORIGIN) != 0) attributes.add("origin");
      if ((initBits & INIT_BIT_DIRECTION) != 0) attributes.add("direction");
      return "Cannot build Ray2D, some of required attributes are not set " + attributes;
    }
  }
}
