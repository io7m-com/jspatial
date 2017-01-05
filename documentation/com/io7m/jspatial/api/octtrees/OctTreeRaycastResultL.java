package com.io7m.jspatial.api.octtrees;

import com.io7m.jspatial.api.BoundingVolumeL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * Immutable implementation of {@link OctTreeRaycastResultLType}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code OctTreeRaycastResultL.<T>builder()}.
 * Use the static factory method to create immutable instances:
 * {@code OctTreeRaycastResultL.<T>of()}.
 */
@SuppressWarnings("all")
@Generated({"Immutables.generator", "OctTreeRaycastResultLType<T>"})
public final class OctTreeRaycastResultL<T>
    implements OctTreeRaycastResultLType<T> {
  private final double distance;
  private final BoundingVolumeL volume;
  private final T item;

  private OctTreeRaycastResultL(double distance, BoundingVolumeL volume, T item) {
    this.distance = distance;
    this.volume = Objects.requireNonNull(volume, "volume");
    this.item = Objects.requireNonNull(item, "item");
  }

  private OctTreeRaycastResultL(
      OctTreeRaycastResultL original,
      double distance,
      BoundingVolumeL volume,
      T item) {
    this.distance = distance;
    this.volume = volume;
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
   * @return The object volume
   */
  @Override
  public BoundingVolumeL volume() {
    return volume;
  }

  /**
   * @return The object
   */
  @Override
  public T item() {
    return item;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeRaycastResultLType#distance() distance} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param distance A new value for distance
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeRaycastResultL<T> withDistance(double distance) {
    if (Double.doubleToLongBits(this.distance) == Double.doubleToLongBits(distance)) return this;
    return new OctTreeRaycastResultL<T>(this, distance, this.volume, this.item);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeRaycastResultLType#volume() volume} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param volume A new value for volume
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeRaycastResultL<T> withVolume(BoundingVolumeL volume) {
    if (this.volume == volume) return this;
    BoundingVolumeL newValue = Objects.requireNonNull(volume, "volume");
    return new OctTreeRaycastResultL<T>(this, this.distance, newValue, this.item);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeRaycastResultLType#item() item} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param item A new value for item
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeRaycastResultL<T> withItem(T item) {
    if (this.item == item) return this;
    T newValue = Objects.requireNonNull(item, "item");
    return new OctTreeRaycastResultL<T>(this, this.distance, this.volume, newValue);
  }

  /**
   * This instance is equal to all instances of {@code OctTreeRaycastResultL} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof OctTreeRaycastResultL<?>
        && equalTo((OctTreeRaycastResultL<?>) another);
  }

  private boolean equalTo(OctTreeRaycastResultL<?> another) {
    return Double.doubleToLongBits(distance) == Double.doubleToLongBits(another.distance)
        && volume.equals(another.volume)
        && item.equals(another.item);
  }

  /**
   * Computes a hash code from attributes: {@code distance}, {@code volume}, {@code item}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + Double.hashCode(distance);
    h = h * 17 + volume.hashCode();
    h = h * 17 + item.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code OctTreeRaycastResultL} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "OctTreeRaycastResultL{"
        + "distance=" + distance
        + ", volume=" + volume
        + ", item=" + item
        + "}";
  }

  /**
   * Construct a new immutable {@code OctTreeRaycastResultL} instance.
   * @param distance The value for the {@code distance} attribute
   * @param volume The value for the {@code volume} attribute
   * @param item The value for the {@code item} attribute
   * @return An immutable OctTreeRaycastResultL instance
   */
  public static <T> OctTreeRaycastResultL<T> of(double distance, BoundingVolumeL volume, T item) {
    return new OctTreeRaycastResultL<T>(distance, volume, item);
  }

  /**
   * Creates an immutable copy of a {@link OctTreeRaycastResultLType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param <T> generic parameter T
   * @param instance The instance to copy
   * @return A copied immutable OctTreeRaycastResultL instance
   */
  public static <T> OctTreeRaycastResultL<T> copyOf(OctTreeRaycastResultLType<T> instance) {
    if (instance instanceof OctTreeRaycastResultL<?>) {
      return (OctTreeRaycastResultL<T>) instance;
    }
    return OctTreeRaycastResultL.<T>builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link OctTreeRaycastResultL OctTreeRaycastResultL}.
   * @param <T> generic parameter T
   * @return A new OctTreeRaycastResultL builder
   */
  public static <T> OctTreeRaycastResultL.Builder<T> builder() {
    return new OctTreeRaycastResultL.Builder<T>();
  }

  /**
   * Builds instances of type {@link OctTreeRaycastResultL OctTreeRaycastResultL}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder<T> {
    private static final long INIT_BIT_DISTANCE = 0x1L;
    private static final long INIT_BIT_VOLUME = 0x2L;
    private static final long INIT_BIT_ITEM = 0x4L;
    private long initBits = 0x7L;

    private double distance;
    private BoundingVolumeL volume;
    private T item;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code OctTreeRaycastResultLType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> from(OctTreeRaycastResultLType<T> instance) {
      Objects.requireNonNull(instance, "instance");
      setDistance(instance.distance());
      setVolume(instance.volume());
      setItem(instance.item());
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeRaycastResultLType#distance() distance} attribute.
     * @param distance The value for distance 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setDistance(double distance) {
      this.distance = distance;
      initBits &= ~INIT_BIT_DISTANCE;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeRaycastResultLType#volume() volume} attribute.
     * @param volume The value for volume 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setVolume(BoundingVolumeL volume) {
      this.volume = Objects.requireNonNull(volume, "volume");
      initBits &= ~INIT_BIT_VOLUME;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeRaycastResultLType#item() item} attribute.
     * @param item The value for item 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setItem(T item) {
      this.item = Objects.requireNonNull(item, "item");
      initBits &= ~INIT_BIT_ITEM;
      return this;
    }

    /**
     * Builds a new {@link OctTreeRaycastResultL OctTreeRaycastResultL}.
     * @return An immutable instance of OctTreeRaycastResultL
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public OctTreeRaycastResultL<T> build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new OctTreeRaycastResultL<T>(null, distance, volume, item);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_DISTANCE) != 0) attributes.add("distance");
      if ((initBits & INIT_BIT_VOLUME) != 0) attributes.add("volume");
      if ((initBits & INIT_BIT_ITEM) != 0) attributes.add("item");
      return "Cannot build OctTreeRaycastResultL, some of required attributes are not set " + attributes;
    }
  }
}
