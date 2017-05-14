package com.io7m.jspatial.api.quadtrees;

import com.io7m.jregions.core.unparameterized.areas.AreaD;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * The type of quadtree raycast results.
 * @param <T> The precise type of objects
 * @since 3.0.0
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "QuadTreeRaycastResultDType<T>"})
public final class QuadTreeRaycastResultD<T>
    implements QuadTreeRaycastResultDType<T> {
  private final double distance;
  private final AreaD area;
  private final T item;

  private QuadTreeRaycastResultD(double distance, AreaD area, T item) {
    this.distance = distance;
    this.area = Objects.requireNonNull(area, "area");
    this.item = Objects.requireNonNull(item, "item");
  }

  private QuadTreeRaycastResultD(
      QuadTreeRaycastResultD original,
      double distance,
      AreaD area,
      T item) {
    this.distance = distance;
    this.area = area;
    this.item = item;
  }

  /**
   * @return The distance to the object
   */
  @Override
  public double distance() {
    return distance;
  }

  /**
   * @return The object area
   */
  @Override
  public AreaD area() {
    return area;
  }

  /**
   * @return The object
   */
  @Override
  public T item() {
    return item;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeRaycastResultDType#distance() distance} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for distance
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeRaycastResultD<T> withDistance(double value) {
    if (Double.doubleToLongBits(this.distance) == Double.doubleToLongBits(value)) return this;
    return new QuadTreeRaycastResultD<T>(this, value, this.area, this.item);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeRaycastResultDType#area() area} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for area
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeRaycastResultD<T> withArea(AreaD value) {
    if (this.area == value) return this;
    AreaD newValue = Objects.requireNonNull(value, "area");
    return new QuadTreeRaycastResultD<T>(this, this.distance, newValue, this.item);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeRaycastResultDType#item() item} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for item
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeRaycastResultD<T> withItem(T value) {
    if (this.item == value) return this;
    T newValue = Objects.requireNonNull(value, "item");
    return new QuadTreeRaycastResultD<T>(this, this.distance, this.area, newValue);
  }

  /**
   * This instance is equal to all instances of {@code QuadTreeRaycastResultD} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof QuadTreeRaycastResultD<?>
        && equalTo((QuadTreeRaycastResultD<T>) another);
  }

  private boolean equalTo(QuadTreeRaycastResultD<T> another) {
    return Double.doubleToLongBits(distance) == Double.doubleToLongBits(another.distance)
        && area.equals(another.area)
        && item.equals(another.item);
  }

  /**
   * Computes a hash code from attributes: {@code distance}, {@code area}, {@code item}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Double.hashCode(distance);
    h += (h << 5) + area.hashCode();
    h += (h << 5) + item.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code QuadTreeRaycastResultD} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "QuadTreeRaycastResultD{"
        + "distance=" + distance
        + ", area=" + area
        + ", item=" + item
        + "}";
  }

  /**
   * Construct a new immutable {@code QuadTreeRaycastResultD} instance.
   * @param distance The value for the {@code distance} attribute
   * @param area The value for the {@code area} attribute
   * @param item The value for the {@code item} attribute
   * @return An immutable QuadTreeRaycastResultD instance
   */
  public static <T> QuadTreeRaycastResultD<T> of(double distance, AreaD area, T item) {
    return new QuadTreeRaycastResultD<T>(distance, area, item);
  }

  /**
   * Creates an immutable copy of a {@link QuadTreeRaycastResultDType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param <T> generic parameter T
   * @param instance The instance to copy
   * @return A copied immutable QuadTreeRaycastResultD instance
   */
  public static <T> QuadTreeRaycastResultD<T> copyOf(QuadTreeRaycastResultDType<T> instance) {
    if (instance instanceof QuadTreeRaycastResultD<?>) {
      return (QuadTreeRaycastResultD<T>) instance;
    }
    return QuadTreeRaycastResultD.<T>builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link QuadTreeRaycastResultD QuadTreeRaycastResultD}.
   * @param <T> generic parameter T
   * @return A new QuadTreeRaycastResultD builder
   */
  public static <T> QuadTreeRaycastResultD.Builder<T> builder() {
    return new QuadTreeRaycastResultD.Builder<T>();
  }

  /**
   * Builds instances of type {@link QuadTreeRaycastResultD QuadTreeRaycastResultD}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder<T> {
    private static final long INIT_BIT_DISTANCE = 0x1L;
    private static final long INIT_BIT_AREA = 0x2L;
    private static final long INIT_BIT_ITEM = 0x4L;
    private long initBits = 0x7L;

    private double distance;
    private AreaD area;
    private T item;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code QuadTreeRaycastResultDType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> from(QuadTreeRaycastResultDType<T> instance) {
      Objects.requireNonNull(instance, "instance");
      setDistance(instance.distance());
      setArea(instance.area());
      setItem(instance.item());
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeRaycastResultDType#distance() distance} attribute.
     * @param distance The value for distance 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setDistance(double distance) {
      this.distance = distance;
      initBits &= ~INIT_BIT_DISTANCE;
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeRaycastResultDType#area() area} attribute.
     * @param area The value for area 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setArea(AreaD area) {
      this.area = Objects.requireNonNull(area, "area");
      initBits &= ~INIT_BIT_AREA;
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeRaycastResultDType#item() item} attribute.
     * @param item The value for item 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setItem(T item) {
      this.item = Objects.requireNonNull(item, "item");
      initBits &= ~INIT_BIT_ITEM;
      return this;
    }

    /**
     * Builds a new {@link QuadTreeRaycastResultD QuadTreeRaycastResultD}.
     * @return An immutable instance of QuadTreeRaycastResultD
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public QuadTreeRaycastResultD<T> build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new QuadTreeRaycastResultD<T>(null, distance, area, item);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_DISTANCE) != 0) attributes.add("distance");
      if ((initBits & INIT_BIT_AREA) != 0) attributes.add("area");
      if ((initBits & INIT_BIT_ITEM) != 0) attributes.add("item");
      return "Cannot build QuadTreeRaycastResultD, some of required attributes are not set " + attributes;
    }
  }
}
