package com.io7m.jspatial.api.octtrees;

import com.io7m.jregions.core.unparameterized.volumes.VolumeD;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * The type of octtree raycast results.
 * @param <T> The precise type of objects
 * @since 3.0.0
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "OctTreeRaycastResultDType<T>"})
public final class OctTreeRaycastResultD<T>
    implements OctTreeRaycastResultDType<T> {
  private final double distance;
  private final VolumeD volume;
  private final T item;

  private OctTreeRaycastResultD(double distance, VolumeD volume, T item) {
    this.distance = distance;
    this.volume = Objects.requireNonNull(volume, "volume");
    this.item = Objects.requireNonNull(item, "item");
  }

  private OctTreeRaycastResultD(
      OctTreeRaycastResultD original,
      double distance,
      VolumeD volume,
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
  public VolumeD volume() {
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
   * Copy the current immutable object by setting a value for the {@link OctTreeRaycastResultDType#distance() distance} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for distance
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeRaycastResultD<T> withDistance(double value) {
    if (Double.doubleToLongBits(this.distance) == Double.doubleToLongBits(value)) return this;
    return new OctTreeRaycastResultD<T>(this, value, this.volume, this.item);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeRaycastResultDType#volume() volume} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for volume
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeRaycastResultD<T> withVolume(VolumeD value) {
    if (this.volume == value) return this;
    VolumeD newValue = Objects.requireNonNull(value, "volume");
    return new OctTreeRaycastResultD<T>(this, this.distance, newValue, this.item);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeRaycastResultDType#item() item} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for item
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeRaycastResultD<T> withItem(T value) {
    if (this.item == value) return this;
    T newValue = Objects.requireNonNull(value, "item");
    return new OctTreeRaycastResultD<T>(this, this.distance, this.volume, newValue);
  }

  /**
   * This instance is equal to all instances of {@code OctTreeRaycastResultD} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof OctTreeRaycastResultD<?>
        && equalTo((OctTreeRaycastResultD<T>) another);
  }

  private boolean equalTo(OctTreeRaycastResultD<T> another) {
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
    int h = 5381;
    h += (h << 5) + Double.hashCode(distance);
    h += (h << 5) + volume.hashCode();
    h += (h << 5) + item.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code OctTreeRaycastResultD} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "OctTreeRaycastResultD{"
        + "distance=" + distance
        + ", volume=" + volume
        + ", item=" + item
        + "}";
  }

  /**
   * Construct a new immutable {@code OctTreeRaycastResultD} instance.
   * @param distance The value for the {@code distance} attribute
   * @param volume The value for the {@code volume} attribute
   * @param item The value for the {@code item} attribute
   * @return An immutable OctTreeRaycastResultD instance
   */
  public static <T> OctTreeRaycastResultD<T> of(double distance, VolumeD volume, T item) {
    return new OctTreeRaycastResultD<T>(distance, volume, item);
  }

  /**
   * Creates an immutable copy of a {@link OctTreeRaycastResultDType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param <T> generic parameter T
   * @param instance The instance to copy
   * @return A copied immutable OctTreeRaycastResultD instance
   */
  public static <T> OctTreeRaycastResultD<T> copyOf(OctTreeRaycastResultDType<T> instance) {
    if (instance instanceof OctTreeRaycastResultD<?>) {
      return (OctTreeRaycastResultD<T>) instance;
    }
    return OctTreeRaycastResultD.<T>builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link OctTreeRaycastResultD OctTreeRaycastResultD}.
   * @param <T> generic parameter T
   * @return A new OctTreeRaycastResultD builder
   */
  public static <T> OctTreeRaycastResultD.Builder<T> builder() {
    return new OctTreeRaycastResultD.Builder<T>();
  }

  /**
   * Builds instances of type {@link OctTreeRaycastResultD OctTreeRaycastResultD}.
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
    private VolumeD volume;
    private T item;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code OctTreeRaycastResultDType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> from(OctTreeRaycastResultDType<T> instance) {
      Objects.requireNonNull(instance, "instance");
      setDistance(instance.distance());
      setVolume(instance.volume());
      setItem(instance.item());
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeRaycastResultDType#distance() distance} attribute.
     * @param distance The value for distance 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setDistance(double distance) {
      this.distance = distance;
      initBits &= ~INIT_BIT_DISTANCE;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeRaycastResultDType#volume() volume} attribute.
     * @param volume The value for volume 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setVolume(VolumeD volume) {
      this.volume = Objects.requireNonNull(volume, "volume");
      initBits &= ~INIT_BIT_VOLUME;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeRaycastResultDType#item() item} attribute.
     * @param item The value for item 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<T> setItem(T item) {
      this.item = Objects.requireNonNull(item, "item");
      initBits &= ~INIT_BIT_ITEM;
      return this;
    }

    /**
     * Builds a new {@link OctTreeRaycastResultD OctTreeRaycastResultD}.
     * @return An immutable instance of OctTreeRaycastResultD
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public OctTreeRaycastResultD<T> build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new OctTreeRaycastResultD<T>(null, distance, volume, item);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_DISTANCE) != 0) attributes.add("distance");
      if ((initBits & INIT_BIT_VOLUME) != 0) attributes.add("volume");
      if ((initBits & INIT_BIT_ITEM) != 0) attributes.add("item");
      return "Cannot build OctTreeRaycastResultD, some of required attributes are not set " + attributes;
    }
  }
}
