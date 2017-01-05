package com.io7m.jspatial.api;

import com.io7m.jtensors.VectorI2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * Immutable implementation of {@link BoundingAreaDType}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code BoundingAreaD.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code BoundingAreaD.of()}.
 */
@SuppressWarnings("all")
@Generated({"Immutables.generator", "BoundingAreaDType"})
public final class BoundingAreaD implements BoundingAreaDType {
  private final VectorI2D lower;
  private final VectorI2D upper;

  private BoundingAreaD(VectorI2D lower, VectorI2D upper) {
    this.lower = Objects.requireNonNull(lower, "lower");
    this.upper = Objects.requireNonNull(upper, "upper");
  }

  private BoundingAreaD(BoundingAreaD original, VectorI2D lower, VectorI2D upper) {
    this.lower = lower;
    this.upper = upper;
  }

  /**
   * @return The lower corner of the area
   */
  @Override
  public VectorI2D lower() {
    return lower;
  }

  /**
   * @return The exclusive upper corner of the area
   */
  @Override
  public VectorI2D upper() {
    return upper;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link BoundingAreaDType#lower() lower} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param lower A new value for lower
   * @return A modified copy of the {@code this} object
   */
  public final BoundingAreaD withLower(VectorI2D lower) {
    if (this.lower == lower) return this;
    VectorI2D newValue = Objects.requireNonNull(lower, "lower");
    return validate(new BoundingAreaD(this, newValue, this.upper));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link BoundingAreaDType#upper() upper} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param upper A new value for upper
   * @return A modified copy of the {@code this} object
   */
  public final BoundingAreaD withUpper(VectorI2D upper) {
    if (this.upper == upper) return this;
    VectorI2D newValue = Objects.requireNonNull(upper, "upper");
    return validate(new BoundingAreaD(this, this.lower, newValue));
  }

  /**
   * This instance is equal to all instances of {@code BoundingAreaD} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof BoundingAreaD
        && equalTo((BoundingAreaD) another);
  }

  private boolean equalTo(BoundingAreaD another) {
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
   * Prints the immutable value {@code BoundingAreaD} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "BoundingAreaD{"
        + "lower=" + lower
        + ", upper=" + upper
        + "}";
  }

  /**
   * Construct a new immutable {@code BoundingAreaD} instance.
   * @param lower The value for the {@code lower} attribute
   * @param upper The value for the {@code upper} attribute
   * @return An immutable BoundingAreaD instance
   */
  public static BoundingAreaD of(VectorI2D lower, VectorI2D upper) {
    return validate(new BoundingAreaD(lower, upper));
  }

  private static BoundingAreaD validate(BoundingAreaD instance) {
    instance.checkInvariants();
    return instance;
  }

  /**
   * Creates an immutable copy of a {@link BoundingAreaDType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable BoundingAreaD instance
   */
  public static BoundingAreaD copyOf(BoundingAreaDType instance) {
    if (instance instanceof BoundingAreaD) {
      return (BoundingAreaD) instance;
    }
    return BoundingAreaD.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link BoundingAreaD BoundingAreaD}.
   * @return A new BoundingAreaD builder
   */
  public static BoundingAreaD.Builder builder() {
    return new BoundingAreaD.Builder();
  }

  /**
   * Builds instances of type {@link BoundingAreaD BoundingAreaD}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_LOWER = 0x1L;
    private static final long INIT_BIT_UPPER = 0x2L;
    private long initBits = 0x3L;

    private VectorI2D lower;
    private VectorI2D upper;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code BoundingAreaDType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(BoundingAreaDType instance) {
      Objects.requireNonNull(instance, "instance");
      setLower(instance.lower());
      setUpper(instance.upper());
      return this;
    }

    /**
     * Initializes the value for the {@link BoundingAreaDType#lower() lower} attribute.
     * @param lower The value for lower 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setLower(VectorI2D lower) {
      this.lower = Objects.requireNonNull(lower, "lower");
      initBits &= ~INIT_BIT_LOWER;
      return this;
    }

    /**
     * Initializes the value for the {@link BoundingAreaDType#upper() upper} attribute.
     * @param upper The value for upper 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setUpper(VectorI2D upper) {
      this.upper = Objects.requireNonNull(upper, "upper");
      initBits &= ~INIT_BIT_UPPER;
      return this;
    }

    /**
     * Builds a new {@link BoundingAreaD BoundingAreaD}.
     * @return An immutable instance of BoundingAreaD
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public BoundingAreaD build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return BoundingAreaD.validate(new BoundingAreaD(null, lower, upper));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_LOWER) != 0) attributes.add("lower");
      if ((initBits & INIT_BIT_UPPER) != 0) attributes.add("upper");
      return "Cannot build BoundingAreaD, some of required attributes are not set " + attributes;
    }
  }
}
