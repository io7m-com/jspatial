package com.io7m.jspatial.api.quadtrees;

import com.io7m.jregions.core.unparameterized.areas.AreaL;
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
@Generated({"Immutables.generator", "QuadTreeRaycastResultLType<T>"})
public final class QuadTreeRaycastResultL<T>
    implements QuadTreeRaycastResultLType<T> {
  private final double distance;
  private final AreaL area;
  private final T item;

  private QuadTreeRaycastResultL(double distance, AreaL area, T item) {
    this.distance = distance;
    this.area = Objects.requireNonNull(area, "area");
    this.item = Objects.requireNonNull(item, "item");
  }

  private QuadTreeRaycastResultL(
      QuadTreeRaycastResultL original,
      double distance,
      AreaL area,
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
  public AreaL area() {
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
   * Copy the current immutable object by setting a value for the {@link QuadTreeRaycastResultLType#distance() distance} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for distance
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeRaycastResultL<T> withDistance(double value) {
    if (Double.doubleToLongBits(this.distance) == Double.doubleToLongBits(value)) return this;
    return new QuadTreeRaycastResultL<T>(this, value, this.area, this.item);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeRaycastResultLType#area() area} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for area
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeRaycastResultL<T> withArea(AreaL value) {
    if (this.area == value) return this;
    AreaL newValue = Objects.requireNonNull(value, "area");
    return new QuadTreeRaycastResultL<T>(this, this.distance, newValue, this.item);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeRaycastResultLType#item() item} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for item
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeRaycastResultL<T> withItem(T value) {
    if (this.item == value) return this;
    T newValue = Objects.requireNonNull(value, "item");
    return new QuadTreeRaycastResultL<T>(this, this.distance, this.area, newValue);
  }

  /**
   * This instance is equal to all instances of {@code QuadTreeRaycastResultL} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof QuadTreeRaycastResultL<?>
        && equalTo((QuadTreeRaycastResultL<T>) another);
  }

  private boolean equalTo(QuadTreeRaycastResultL<T> another) {
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
   * Prints the immutable value {@code QuadTreeRaycastResultL} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "QuadTreeRaycastResultL{"
        + "distance=" + distance
        + ", area=" + area
        + ", item=" + item
        + "}";
  }

  /**
   * Construct a new immutable {@code QuadTreeRaycastResultL} instance.
   * @param distance The value for the {@code distance} attribute
   * @param area The value for the {@code area} attribute
   * @param item The value for the {@code item} attribute
   * @return An immutable QuadTreeRaycastResultL instance
   */
  public static <T> QuadTreeRaycastResultL<T> of(double distance, AreaL area, T item) {
    return new QuadTreeRaycastResultL<T>(distance, area, item);
  }

  /**
   * Creates an immutable copy of a {@link QuadTreeRaycastResultLType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param <T> generic parameter T
   * @param instance The instance to copy
   * @return A copied immutable QuadTreeRaycastResultL instance
   */
  public static <T> QuadTreeRaycastResultL<T> copyOf(QuadTreeRaycastResultLType<T> instance) {
    if (instance instanceof QuadTreeRaycastResultL<?>) {
      return (QuadTreeRaycastResultL<T>) instance;
    }
    return QuadTreeRaycastResultL.<T>builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link QuadTreeRaycastResultL QuadTreeRaycastResultL}.
   * @param <T> generic parameter T
   * @return A new QuadTreeRaycastResultL builder
   */
  public static <T> QuadTreeRaycastResultL.Builder<T> builder() {
    return new QuadTreeRaycastResultL.Builder<T>();
  }

  /**
   * Builds instances of type {@link QuadTreeRaycastResultL QuadTreeRaycastResultL}.
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
    private AreaL area;
    private T item;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code QuadTreeRaycastResultLType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> from(QuadTreeRaycastResultLType<T> instance) {
      Objects.requireNonNull(instance, "instance");
      setDistance(instance.distance());
      setArea(instance.area());
      setItem(instance.item());
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeRaycastResultLType#distance() distance} attribute.
     * @param distance The value for distance 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setDistance(double distance) {
      this.distance = distance;
      initBits &= ~INIT_BIT_DISTANCE;
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeRaycastResultLType#area() area} attribute.
     * @param area The value for area 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setArea(AreaL area) {
      this.area = Objects.requireNonNull(area, "area");
      initBits &= ~INIT_BIT_AREA;
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeRaycastResultLType#item() item} attribute.
     * @param item The value for item 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setItem(T item) {
      this.item = Objects.requireNonNull(item, "item");
      initBits &= ~INIT_BIT_ITEM;
      return this;
    }

    /**
     * Builds a new {@link QuadTreeRaycastResultL QuadTreeRaycastResultL}.
     * @return An immutable instance of QuadTreeRaycastResultL
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public QuadTreeRaycastResultL<T> build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new QuadTreeRaycastResultL<T>(null, distance, area, item);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_DISTANCE) != 0) attributes.add("distance");
      if ((initBits & INIT_BIT_AREA) != 0) attributes.add("area");
      if ((initBits & INIT_BIT_ITEM) != 0) attributes.add("item");
      return "Cannot build QuadTreeRaycastResultL, some of required attributes are not set " + attributes;
    }
  }
}
