package com.io7m.jspatial.api;

import com.io7m.jtensors.VectorI2L;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * Immutable implementation of {@link BoundingAreaLType}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code BoundingAreaL.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code BoundingAreaL.of()}.
 */
@SuppressWarnings("all")
@Generated({"Immutables.generator", "BoundingAreaLType"})
public final class BoundingAreaL implements BoundingAreaLType {
  private final VectorI2L lower;
  private final VectorI2L upper;

  private BoundingAreaL(VectorI2L lower, VectorI2L upper) {
    this.lower = Objects.requireNonNull(lower, "lower");
    this.upper = Objects.requireNonNull(upper, "upper");
  }

  private BoundingAreaL(BoundingAreaL original, VectorI2L lower, VectorI2L upper) {
    this.lower = lower;
    this.upper = upper;
  }

  /**
   * @return The lower corner of the area
   */
  @Override
  public VectorI2L lower() {
    return lower;
  }

  /**
   * @return The exclusive upper corner of the area
   */
  @Override
  public VectorI2L upper() {
    return upper;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link BoundingAreaLType#lower() lower} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param lower A new value for lower
   * @return A modified copy of the {@code this} object
   */
  public final BoundingAreaL withLower(VectorI2L lower) {
    if (this.lower == lower) return this;
    VectorI2L newValue = Objects.requireNonNull(lower, "lower");
    return validate(new BoundingAreaL(this, newValue, this.upper));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link BoundingAreaLType#upper() upper} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param upper A new value for upper
   * @return A modified copy of the {@code this} object
   */
  public final BoundingAreaL withUpper(VectorI2L upper) {
    if (this.upper == upper) return this;
    VectorI2L newValue = Objects.requireNonNull(upper, "upper");
    return validate(new BoundingAreaL(this, this.lower, newValue));
  }

  /**
   * This instance is equal to all instances of {@code BoundingAreaL} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof BoundingAreaL
        && equalTo((BoundingAreaL) another);
  }

  private boolean equalTo(BoundingAreaL another) {
    return lower.equals(another.lower)
        && upper.equals(another.upper);
  }

  /**
   * Computes a hash code from attributes: {@code lower}, {@code upper}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + lower.hashCode();
    h = h * 17 + upper.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code BoundingAreaL} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "BoundingAreaL{"
        + "lower=" + lower
        + ", upper=" + upper
        + "}";
  }

  /**
   * Construct a new immutable {@code BoundingAreaL} instance.
   * @param lower The value for the {@code lower} attribute
   * @param upper The value for the {@code upper} attribute
   * @return An immutable BoundingAreaL instance
   */
  public static BoundingAreaL of(VectorI2L lower, VectorI2L upper) {
    return validate(new BoundingAreaL(lower, upper));
  }

  private static BoundingAreaL validate(BoundingAreaL instance) {
    instance.checkInvariants();
    return instance;
  }

  /**
   * Creates an immutable copy of a {@link BoundingAreaLType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable BoundingAreaL instance
   */
  public static BoundingAreaL copyOf(BoundingAreaLType instance) {
    if (instance instanceof BoundingAreaL) {
      return (BoundingAreaL) instance;
    }
    return BoundingAreaL.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link BoundingAreaL BoundingAreaL}.
   * @return A new BoundingAreaL builder
   */
  public static BoundingAreaL.Builder builder() {
    return new BoundingAreaL.Builder();
  }

  /**
   * Builds instances of type {@link BoundingAreaL BoundingAreaL}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_LOWER = 0x1L;
    private static final long INIT_BIT_UPPER = 0x2L;
    private long initBits = 0x3L;

    private VectorI2L lower;
    private VectorI2L upper;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code BoundingAreaLType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(BoundingAreaLType instance) {
      Objects.requireNonNull(instance, "instance");
      setLower(instance.lower());
      setUpper(instance.upper());
      return this;
    }

    /**
     * Initializes the value for the {@link BoundingAreaLType#lower() lower} attribute.
     * @param lower The value for lower 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setLower(VectorI2L lower) {
      this.lower = Objects.requireNonNull(lower, "lower");
      initBits &= ~INIT_BIT_LOWER;
      return this;
    }

    /**
     * Initializes the value for the {@link BoundingAreaLType#upper() upper} attribute.
     * @param upper The value for upper 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setUpper(VectorI2L upper) {
      this.upper = Objects.requireNonNull(upper, "upper");
      initBits &= ~INIT_BIT_UPPER;
      return this;
    }

    /**
     * Builds a new {@link BoundingAreaL BoundingAreaL}.
     * @return An immutable instance of BoundingAreaL
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public BoundingAreaL build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return BoundingAreaL.validate(new BoundingAreaL(null, lower, upper));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_LOWER) != 0) attributes.add("lower");
      if ((initBits & INIT_BIT_UPPER) != 0) attributes.add("upper");
      return "Cannot build BoundingAreaL, some of required attributes are not set " + attributes;
    }
  }
}
