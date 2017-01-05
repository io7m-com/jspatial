package com.io7m.jspatial.api.octtrees;

import com.io7m.jspatial.api.BoundingVolumeL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * Immutable implementation of {@link OctTreeConfigurationLType}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code OctTreeConfigurationL.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code OctTreeConfigurationL.of()}.
 */
@SuppressWarnings("all")
@Generated({"Immutables.generator", "OctTreeConfigurationLType"})
public final class OctTreeConfigurationL implements OctTreeConfigurationLType {
  private final BoundingVolumeL volume;
  private final long minimumOctantWidth;
  private final long minimumOctantHeight;
  private final long minimumOctantDepth;
  private final boolean trimOnRemove;

  private OctTreeConfigurationL(
      BoundingVolumeL volume,
      long minimumOctantWidth,
      long minimumOctantHeight,
      long minimumOctantDepth,
      boolean trimOnRemove) {
    this.volume = Objects.requireNonNull(volume, "volume");
    this.minimumOctantWidth = minimumOctantWidth;
    this.minimumOctantHeight = minimumOctantHeight;
    this.minimumOctantDepth = minimumOctantDepth;
    this.trimOnRemove = trimOnRemove;
    initShim.setMinimumOctantWidth(this.minimumOctantWidth);
    initShim.setMinimumOctantHeight(this.minimumOctantHeight);
    initShim.setMinimumOctantDepth(this.minimumOctantDepth);
    initShim.setTrimOnRemove(this.trimOnRemove);
    this.initShim = null;
  }

  private OctTreeConfigurationL(OctTreeConfigurationL.Builder builder) {
    this.volume = builder.volume;
    if (builder.minimumOctantWidthIsSet()) {
      initShim.setMinimumOctantWidth(builder.minimumOctantWidth);
    }
    if (builder.minimumOctantHeightIsSet()) {
      initShim.setMinimumOctantHeight(builder.minimumOctantHeight);
    }
    if (builder.minimumOctantDepthIsSet()) {
      initShim.setMinimumOctantDepth(builder.minimumOctantDepth);
    }
    if (builder.trimOnRemoveIsSet()) {
      initShim.setTrimOnRemove(builder.trimOnRemove);
    }
    this.minimumOctantWidth = initShim.minimumOctantWidth();
    this.minimumOctantHeight = initShim.minimumOctantHeight();
    this.minimumOctantDepth = initShim.minimumOctantDepth();
    this.trimOnRemove = initShim.trimOnRemove();
    this.initShim = null;
  }

  private OctTreeConfigurationL(
      OctTreeConfigurationL original,
      BoundingVolumeL volume,
      long minimumOctantWidth,
      long minimumOctantHeight,
      long minimumOctantDepth,
      boolean trimOnRemove) {
    this.volume = volume;
    this.minimumOctantWidth = minimumOctantWidth;
    this.minimumOctantHeight = minimumOctantHeight;
    this.minimumOctantDepth = minimumOctantDepth;
    this.trimOnRemove = trimOnRemove;
    this.initShim = null;
  }

  private static final int STAGE_INITIALIZING = -1;
  private static final int STAGE_UNINITIALIZED = 0;
  private static final int STAGE_INITIALIZED = 1;
  private transient volatile InitShim initShim = new InitShim();

  private final class InitShim {
    private long minimumOctantWidth;
    private int minimumOctantWidthStage;

    long minimumOctantWidth() {
      if (minimumOctantWidthStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minimumOctantWidthStage == STAGE_UNINITIALIZED) {
        minimumOctantWidthStage = STAGE_INITIALIZING;
        this.minimumOctantWidth = minimumOctantWidthInitialize();
        minimumOctantWidthStage = STAGE_INITIALIZED;
      }
      return this.minimumOctantWidth;
    }

    void setMinimumOctantWidth(long minimumOctantWidth) {
      this.minimumOctantWidth = minimumOctantWidth;
      minimumOctantWidthStage = STAGE_INITIALIZED;
    }
    private long minimumOctantHeight;
    private int minimumOctantHeightStage;

    long minimumOctantHeight() {
      if (minimumOctantHeightStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minimumOctantHeightStage == STAGE_UNINITIALIZED) {
        minimumOctantHeightStage = STAGE_INITIALIZING;
        this.minimumOctantHeight = minimumOctantHeightInitialize();
        minimumOctantHeightStage = STAGE_INITIALIZED;
      }
      return this.minimumOctantHeight;
    }

    void setMinimumOctantHeight(long minimumOctantHeight) {
      this.minimumOctantHeight = minimumOctantHeight;
      minimumOctantHeightStage = STAGE_INITIALIZED;
    }
    private long minimumOctantDepth;
    private int minimumOctantDepthStage;

    long minimumOctantDepth() {
      if (minimumOctantDepthStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minimumOctantDepthStage == STAGE_UNINITIALIZED) {
        minimumOctantDepthStage = STAGE_INITIALIZING;
        this.minimumOctantDepth = minimumOctantDepthInitialize();
        minimumOctantDepthStage = STAGE_INITIALIZED;
      }
      return this.minimumOctantDepth;
    }

    void setMinimumOctantDepth(long minimumOctantDepth) {
      this.minimumOctantDepth = minimumOctantDepth;
      minimumOctantDepthStage = STAGE_INITIALIZED;
    }
    private boolean trimOnRemove;
    private int trimOnRemoveStage;

    boolean trimOnRemove() {
      if (trimOnRemoveStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (trimOnRemoveStage == STAGE_UNINITIALIZED) {
        trimOnRemoveStage = STAGE_INITIALIZING;
        this.trimOnRemove = trimOnRemoveInitialize();
        trimOnRemoveStage = STAGE_INITIALIZED;
      }
      return this.trimOnRemove;
    }

    void setTrimOnRemove(boolean trimOnRemove) {
      this.trimOnRemove = trimOnRemove;
      trimOnRemoveStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      ArrayList<String> attributes = new ArrayList<String>();
      if (minimumOctantWidthStage == STAGE_INITIALIZING) attributes.add("minimumOctantWidth");
      if (minimumOctantHeightStage == STAGE_INITIALIZING) attributes.add("minimumOctantHeight");
      if (minimumOctantDepthStage == STAGE_INITIALIZING) attributes.add("minimumOctantDepth");
      if (trimOnRemoveStage == STAGE_INITIALIZING) attributes.add("trimOnRemove");
      return "Cannot build OctTreeConfigurationL, attribute initializers form cycle" + attributes;
    }
  }

  private long minimumOctantWidthInitialize() {
    return OctTreeConfigurationLType.super.minimumOctantWidth();
  }

  private long minimumOctantHeightInitialize() {
    return OctTreeConfigurationLType.super.minimumOctantHeight();
  }

  private long minimumOctantDepthInitialize() {
    return OctTreeConfigurationLType.super.minimumOctantDepth();
  }

  private boolean trimOnRemoveInitialize() {
    return OctTreeConfigurationLType.super.trimOnRemove();
  }

  /**
   * @return The maximum bounding volume of the tree
   */
  @Override
  public BoundingVolumeL volume() {
    return volume;
  }

  /**
   * @return The minimum width of octants (must be {@code >= 2})
   */
  @Override
  public long minimumOctantWidth() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.minimumOctantWidth()
        : this.minimumOctantWidth;
  }

  /**
   * @return The minimum height of octants (must be {@code >= 2})
   */
  @Override
  public long minimumOctantHeight() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.minimumOctantHeight()
        : this.minimumOctantHeight;
  }

  /**
   * @return The minimum depth of octants (must be {@code >= 2})
   */
  @Override
  public long minimumOctantDepth() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.minimumOctantDepth()
        : this.minimumOctantDepth;
  }

  /**
   * @return {@code true} iff the implementation should attempt to trim empty
   * leaf nodes when an item is removed
   */
  @Override
  public boolean trimOnRemove() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.trimOnRemove()
        : this.trimOnRemove;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeConfigurationLType#volume() volume} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param volume A new value for volume
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeConfigurationL withVolume(BoundingVolumeL volume) {
    if (this.volume == volume) return this;
    BoundingVolumeL newValue = Objects.requireNonNull(volume, "volume");
    return new OctTreeConfigurationL(
        this,
        newValue,
        this.minimumOctantWidth,
        this.minimumOctantHeight,
        this.minimumOctantDepth,
        this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeConfigurationLType#minimumOctantWidth() minimumOctantWidth} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param minimumOctantWidth A new value for minimumOctantWidth
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeConfigurationL withMinimumOctantWidth(long minimumOctantWidth) {
    if (this.minimumOctantWidth == minimumOctantWidth) return this;
    return new OctTreeConfigurationL(
        this,
        this.volume,
        minimumOctantWidth,
        this.minimumOctantHeight,
        this.minimumOctantDepth,
        this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeConfigurationLType#minimumOctantHeight() minimumOctantHeight} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param minimumOctantHeight A new value for minimumOctantHeight
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeConfigurationL withMinimumOctantHeight(long minimumOctantHeight) {
    if (this.minimumOctantHeight == minimumOctantHeight) return this;
    return new OctTreeConfigurationL(
        this,
        this.volume,
        this.minimumOctantWidth,
        minimumOctantHeight,
        this.minimumOctantDepth,
        this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeConfigurationLType#minimumOctantDepth() minimumOctantDepth} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param minimumOctantDepth A new value for minimumOctantDepth
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeConfigurationL withMinimumOctantDepth(long minimumOctantDepth) {
    if (this.minimumOctantDepth == minimumOctantDepth) return this;
    return new OctTreeConfigurationL(
        this,
        this.volume,
        this.minimumOctantWidth,
        this.minimumOctantHeight,
        minimumOctantDepth,
        this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeConfigurationLType#trimOnRemove() trimOnRemove} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param trimOnRemove A new value for trimOnRemove
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeConfigurationL withTrimOnRemove(boolean trimOnRemove) {
    if (this.trimOnRemove == trimOnRemove) return this;
    return new OctTreeConfigurationL(
        this,
        this.volume,
        this.minimumOctantWidth,
        this.minimumOctantHeight,
        this.minimumOctantDepth,
        trimOnRemove);
  }

  /**
   * This instance is equal to all instances of {@code OctTreeConfigurationL} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof OctTreeConfigurationL
        && equalTo((OctTreeConfigurationL) another);
  }

  private boolean equalTo(OctTreeConfigurationL another) {
    return volume.equals(another.volume)
        && minimumOctantWidth == another.minimumOctantWidth
        && minimumOctantHeight == another.minimumOctantHeight
        && minimumOctantDepth == another.minimumOctantDepth
        && trimOnRemove == another.trimOnRemove;
  }

  /**
   * Computes a hash code from attributes: {@code volume}, {@code minimumOctantWidth}, {@code minimumOctantHeight}, {@code minimumOctantDepth}, {@code trimOnRemove}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + volume.hashCode();
    h = h * 17 + Long.hashCode(minimumOctantWidth);
    h = h * 17 + Long.hashCode(minimumOctantHeight);
    h = h * 17 + Long.hashCode(minimumOctantDepth);
    h = h * 17 + Boolean.hashCode(trimOnRemove);
    return h;
  }

  /**
   * Prints the immutable value {@code OctTreeConfigurationL} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "OctTreeConfigurationL{"
        + "volume=" + volume
        + ", minimumOctantWidth=" + minimumOctantWidth
        + ", minimumOctantHeight=" + minimumOctantHeight
        + ", minimumOctantDepth=" + minimumOctantDepth
        + ", trimOnRemove=" + trimOnRemove
        + "}";
  }

  /**
   * Construct a new immutable {@code OctTreeConfigurationL} instance.
   * @param volume The value for the {@code volume} attribute
   * @param minimumOctantWidth The value for the {@code minimumOctantWidth} attribute
   * @param minimumOctantHeight The value for the {@code minimumOctantHeight} attribute
   * @param minimumOctantDepth The value for the {@code minimumOctantDepth} attribute
   * @param trimOnRemove The value for the {@code trimOnRemove} attribute
   * @return An immutable OctTreeConfigurationL instance
   */
  public static OctTreeConfigurationL of(BoundingVolumeL volume, long minimumOctantWidth, long minimumOctantHeight, long minimumOctantDepth, boolean trimOnRemove) {
    return new OctTreeConfigurationL(volume, minimumOctantWidth, minimumOctantHeight, minimumOctantDepth, trimOnRemove);
  }

  /**
   * Creates an immutable copy of a {@link OctTreeConfigurationLType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable OctTreeConfigurationL instance
   */
  public static OctTreeConfigurationL copyOf(OctTreeConfigurationLType instance) {
    if (instance instanceof OctTreeConfigurationL) {
      return (OctTreeConfigurationL) instance;
    }
    return OctTreeConfigurationL.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link OctTreeConfigurationL OctTreeConfigurationL}.
   * @return A new OctTreeConfigurationL builder
   */
  public static OctTreeConfigurationL.Builder builder() {
    return new OctTreeConfigurationL.Builder();
  }

  /**
   * Builds instances of type {@link OctTreeConfigurationL OctTreeConfigurationL}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_VOLUME = 0x1L;
    private static final long OPT_BIT_MINIMUM_OCTANT_WIDTH = 0x1L;
    private static final long OPT_BIT_MINIMUM_OCTANT_HEIGHT = 0x2L;
    private static final long OPT_BIT_MINIMUM_OCTANT_DEPTH = 0x4L;
    private static final long OPT_BIT_TRIM_ON_REMOVE = 0x8L;
    private long initBits = 0x1L;
    private long optBits;

    private BoundingVolumeL volume;
    private long minimumOctantWidth;
    private long minimumOctantHeight;
    private long minimumOctantDepth;
    private boolean trimOnRemove;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code OctTreeConfigurationLType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(OctTreeConfigurationLType instance) {
      Objects.requireNonNull(instance, "instance");
      setVolume(instance.volume());
      setMinimumOctantWidth(instance.minimumOctantWidth());
      setMinimumOctantHeight(instance.minimumOctantHeight());
      setMinimumOctantDepth(instance.minimumOctantDepth());
      setTrimOnRemove(instance.trimOnRemove());
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeConfigurationLType#volume() volume} attribute.
     * @param volume The value for volume 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setVolume(BoundingVolumeL volume) {
      this.volume = Objects.requireNonNull(volume, "volume");
      initBits &= ~INIT_BIT_VOLUME;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeConfigurationLType#minimumOctantWidth() minimumOctantWidth} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link OctTreeConfigurationLType#minimumOctantWidth() minimumOctantWidth}.</em>
     * @param minimumOctantWidth The value for minimumOctantWidth 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setMinimumOctantWidth(long minimumOctantWidth) {
      this.minimumOctantWidth = minimumOctantWidth;
      optBits |= OPT_BIT_MINIMUM_OCTANT_WIDTH;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeConfigurationLType#minimumOctantHeight() minimumOctantHeight} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link OctTreeConfigurationLType#minimumOctantHeight() minimumOctantHeight}.</em>
     * @param minimumOctantHeight The value for minimumOctantHeight 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setMinimumOctantHeight(long minimumOctantHeight) {
      this.minimumOctantHeight = minimumOctantHeight;
      optBits |= OPT_BIT_MINIMUM_OCTANT_HEIGHT;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeConfigurationLType#minimumOctantDepth() minimumOctantDepth} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link OctTreeConfigurationLType#minimumOctantDepth() minimumOctantDepth}.</em>
     * @param minimumOctantDepth The value for minimumOctantDepth 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setMinimumOctantDepth(long minimumOctantDepth) {
      this.minimumOctantDepth = minimumOctantDepth;
      optBits |= OPT_BIT_MINIMUM_OCTANT_DEPTH;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeConfigurationLType#trimOnRemove() trimOnRemove} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link OctTreeConfigurationLType#trimOnRemove() trimOnRemove}.</em>
     * @param trimOnRemove The value for trimOnRemove 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setTrimOnRemove(boolean trimOnRemove) {
      this.trimOnRemove = trimOnRemove;
      optBits |= OPT_BIT_TRIM_ON_REMOVE;
      return this;
    }

    /**
     * Builds a new {@link OctTreeConfigurationL OctTreeConfigurationL}.
     * @return An immutable instance of OctTreeConfigurationL
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public OctTreeConfigurationL build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new OctTreeConfigurationL(this);
    }

    private boolean minimumOctantWidthIsSet() {
      return (optBits & OPT_BIT_MINIMUM_OCTANT_WIDTH) != 0;
    }

    private boolean minimumOctantHeightIsSet() {
      return (optBits & OPT_BIT_MINIMUM_OCTANT_HEIGHT) != 0;
    }

    private boolean minimumOctantDepthIsSet() {
      return (optBits & OPT_BIT_MINIMUM_OCTANT_DEPTH) != 0;
    }

    private boolean trimOnRemoveIsSet() {
      return (optBits & OPT_BIT_TRIM_ON_REMOVE) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_VOLUME) != 0) attributes.add("volume");
      return "Cannot build OctTreeConfigurationL, some of required attributes are not set " + attributes;
    }
  }
}
