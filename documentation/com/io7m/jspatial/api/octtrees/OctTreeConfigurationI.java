package com.io7m.jspatial.api.octtrees;

import com.io7m.jregions.core.unparameterized.volumes.VolumeI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * The type of integer octtree configurations.
 * @since 3.0.0
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "OctTreeConfigurationIType"})
public final class OctTreeConfigurationI implements OctTreeConfigurationIType {
  private final VolumeI volume;
  private final int minimumOctantWidth;
  private final int minimumOctantHeight;
  private final int minimumOctantDepth;
  private final boolean trimOnRemove;

  private OctTreeConfigurationI(
      VolumeI volume,
      int minimumOctantWidth,
      int minimumOctantHeight,
      int minimumOctantDepth,
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

  private OctTreeConfigurationI(OctTreeConfigurationI.Builder builder) {
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

  private OctTreeConfigurationI(
      OctTreeConfigurationI original,
      VolumeI volume,
      int minimumOctantWidth,
      int minimumOctantHeight,
      int minimumOctantDepth,
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
    private int minimumOctantWidth;
    private int minimumOctantWidthBuildStage;

    int minimumOctantWidth() {
      if (minimumOctantWidthBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minimumOctantWidthBuildStage == STAGE_UNINITIALIZED) {
        minimumOctantWidthBuildStage = STAGE_INITIALIZING;
        this.minimumOctantWidth = minimumOctantWidthInitialize();
        minimumOctantWidthBuildStage = STAGE_INITIALIZED;
      }
      return this.minimumOctantWidth;
    }

    void setMinimumOctantWidth(int minimumOctantWidth) {
      this.minimumOctantWidth = minimumOctantWidth;
      minimumOctantWidthBuildStage = STAGE_INITIALIZED;
    }
    private int minimumOctantHeight;
    private int minimumOctantHeightBuildStage;

    int minimumOctantHeight() {
      if (minimumOctantHeightBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minimumOctantHeightBuildStage == STAGE_UNINITIALIZED) {
        minimumOctantHeightBuildStage = STAGE_INITIALIZING;
        this.minimumOctantHeight = minimumOctantHeightInitialize();
        minimumOctantHeightBuildStage = STAGE_INITIALIZED;
      }
      return this.minimumOctantHeight;
    }

    void setMinimumOctantHeight(int minimumOctantHeight) {
      this.minimumOctantHeight = minimumOctantHeight;
      minimumOctantHeightBuildStage = STAGE_INITIALIZED;
    }
    private int minimumOctantDepth;
    private int minimumOctantDepthBuildStage;

    int minimumOctantDepth() {
      if (minimumOctantDepthBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minimumOctantDepthBuildStage == STAGE_UNINITIALIZED) {
        minimumOctantDepthBuildStage = STAGE_INITIALIZING;
        this.minimumOctantDepth = minimumOctantDepthInitialize();
        minimumOctantDepthBuildStage = STAGE_INITIALIZED;
      }
      return this.minimumOctantDepth;
    }

    void setMinimumOctantDepth(int minimumOctantDepth) {
      this.minimumOctantDepth = minimumOctantDepth;
      minimumOctantDepthBuildStage = STAGE_INITIALIZED;
    }
    private boolean trimOnRemove;
    private int trimOnRemoveBuildStage;

    boolean trimOnRemove() {
      if (trimOnRemoveBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (trimOnRemoveBuildStage == STAGE_UNINITIALIZED) {
        trimOnRemoveBuildStage = STAGE_INITIALIZING;
        this.trimOnRemove = trimOnRemoveInitialize();
        trimOnRemoveBuildStage = STAGE_INITIALIZED;
      }
      return this.trimOnRemove;
    }

    void setTrimOnRemove(boolean trimOnRemove) {
      this.trimOnRemove = trimOnRemove;
      trimOnRemoveBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      ArrayList<String> attributes = new ArrayList<String>();
      if (minimumOctantWidthBuildStage == STAGE_INITIALIZING) attributes.add("minimumOctantWidth");
      if (minimumOctantHeightBuildStage == STAGE_INITIALIZING) attributes.add("minimumOctantHeight");
      if (minimumOctantDepthBuildStage == STAGE_INITIALIZING) attributes.add("minimumOctantDepth");
      if (trimOnRemoveBuildStage == STAGE_INITIALIZING) attributes.add("trimOnRemove");
      return "Cannot build OctTreeConfigurationI, attribute initializers form cycle" + attributes;
    }
  }

  private int minimumOctantWidthInitialize() {
    return OctTreeConfigurationIType.super.minimumOctantWidth();
  }

  private int minimumOctantHeightInitialize() {
    return OctTreeConfigurationIType.super.minimumOctantHeight();
  }

  private int minimumOctantDepthInitialize() {
    return OctTreeConfigurationIType.super.minimumOctantDepth();
  }

  private boolean trimOnRemoveInitialize() {
    return OctTreeConfigurationIType.super.trimOnRemove();
  }

  /**
   * @return The maximum bounding volume of the tree
   */
  @Override
  public VolumeI volume() {
    return volume;
  }

  /**
   * @return The minimum width of octants (must be {@code >= 2})
   */
  @Override
  public int minimumOctantWidth() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.minimumOctantWidth()
        : this.minimumOctantWidth;
  }

  /**
   * @return The minimum height of octants (must be {@code >= 2})
   */
  @Override
  public int minimumOctantHeight() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.minimumOctantHeight()
        : this.minimumOctantHeight;
  }

  /**
   * @return The minimum depth of octants (must be {@code >= 2})
   */
  @Override
  public int minimumOctantDepth() {
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
   * Copy the current immutable object by setting a value for the {@link OctTreeConfigurationIType#volume() volume} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for volume
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeConfigurationI withVolume(VolumeI value) {
    if (this.volume == value) return this;
    VolumeI newValue = Objects.requireNonNull(value, "volume");
    return new OctTreeConfigurationI(
        this,
        newValue,
        this.minimumOctantWidth,
        this.minimumOctantHeight,
        this.minimumOctantDepth,
        this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeConfigurationIType#minimumOctantWidth() minimumOctantWidth} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for minimumOctantWidth
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeConfigurationI withMinimumOctantWidth(int value) {
    if (this.minimumOctantWidth == value) return this;
    return new OctTreeConfigurationI(this, this.volume, value, this.minimumOctantHeight, this.minimumOctantDepth, this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeConfigurationIType#minimumOctantHeight() minimumOctantHeight} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for minimumOctantHeight
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeConfigurationI withMinimumOctantHeight(int value) {
    if (this.minimumOctantHeight == value) return this;
    return new OctTreeConfigurationI(this, this.volume, this.minimumOctantWidth, value, this.minimumOctantDepth, this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeConfigurationIType#minimumOctantDepth() minimumOctantDepth} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for minimumOctantDepth
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeConfigurationI withMinimumOctantDepth(int value) {
    if (this.minimumOctantDepth == value) return this;
    return new OctTreeConfigurationI(this, this.volume, this.minimumOctantWidth, this.minimumOctantHeight, value, this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link OctTreeConfigurationIType#trimOnRemove() trimOnRemove} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for trimOnRemove
   * @return A modified copy of the {@code this} object
   */
  public final OctTreeConfigurationI withTrimOnRemove(boolean value) {
    if (this.trimOnRemove == value) return this;
    return new OctTreeConfigurationI(
        this,
        this.volume,
        this.minimumOctantWidth,
        this.minimumOctantHeight,
        this.minimumOctantDepth,
        value);
  }

  /**
   * This instance is equal to all instances of {@code OctTreeConfigurationI} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof OctTreeConfigurationI
        && equalTo((OctTreeConfigurationI) another);
  }

  private boolean equalTo(OctTreeConfigurationI another) {
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
    int h = 5381;
    h += (h << 5) + volume.hashCode();
    h += (h << 5) + minimumOctantWidth;
    h += (h << 5) + minimumOctantHeight;
    h += (h << 5) + minimumOctantDepth;
    h += (h << 5) + Boolean.hashCode(trimOnRemove);
    return h;
  }

  /**
   * Prints the immutable value {@code OctTreeConfigurationI} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "OctTreeConfigurationI{"
        + "volume=" + volume
        + ", minimumOctantWidth=" + minimumOctantWidth
        + ", minimumOctantHeight=" + minimumOctantHeight
        + ", minimumOctantDepth=" + minimumOctantDepth
        + ", trimOnRemove=" + trimOnRemove
        + "}";
  }

  /**
   * Construct a new immutable {@code OctTreeConfigurationI} instance.
   * @param volume The value for the {@code volume} attribute
   * @param minimumOctantWidth The value for the {@code minimumOctantWidth} attribute
   * @param minimumOctantHeight The value for the {@code minimumOctantHeight} attribute
   * @param minimumOctantDepth The value for the {@code minimumOctantDepth} attribute
   * @param trimOnRemove The value for the {@code trimOnRemove} attribute
   * @return An immutable OctTreeConfigurationI instance
   */
  public static OctTreeConfigurationI of(VolumeI volume, int minimumOctantWidth, int minimumOctantHeight, int minimumOctantDepth, boolean trimOnRemove) {
    return new OctTreeConfigurationI(volume, minimumOctantWidth, minimumOctantHeight, minimumOctantDepth, trimOnRemove);
  }

  /**
   * Creates an immutable copy of a {@link OctTreeConfigurationIType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable OctTreeConfigurationI instance
   */
  public static OctTreeConfigurationI copyOf(OctTreeConfigurationIType instance) {
    if (instance instanceof OctTreeConfigurationI) {
      return (OctTreeConfigurationI) instance;
    }
    return OctTreeConfigurationI.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link OctTreeConfigurationI OctTreeConfigurationI}.
   * @return A new OctTreeConfigurationI builder
   */
  public static OctTreeConfigurationI.Builder builder() {
    return new OctTreeConfigurationI.Builder();
  }

  /**
   * Builds instances of type {@link OctTreeConfigurationI OctTreeConfigurationI}.
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

    private VolumeI volume;
    private int minimumOctantWidth;
    private int minimumOctantHeight;
    private int minimumOctantDepth;
    private boolean trimOnRemove;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code OctTreeConfigurationIType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(OctTreeConfigurationIType instance) {
      Objects.requireNonNull(instance, "instance");
      setVolume(instance.volume());
      setMinimumOctantWidth(instance.minimumOctantWidth());
      setMinimumOctantHeight(instance.minimumOctantHeight());
      setMinimumOctantDepth(instance.minimumOctantDepth());
      setTrimOnRemove(instance.trimOnRemove());
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeConfigurationIType#volume() volume} attribute.
     * @param volume The value for volume 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setVolume(VolumeI volume) {
      this.volume = Objects.requireNonNull(volume, "volume");
      initBits &= ~INIT_BIT_VOLUME;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeConfigurationIType#minimumOctantWidth() minimumOctantWidth} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link OctTreeConfigurationIType#minimumOctantWidth() minimumOctantWidth}.</em>
     * @param minimumOctantWidth The value for minimumOctantWidth 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setMinimumOctantWidth(int minimumOctantWidth) {
      this.minimumOctantWidth = minimumOctantWidth;
      optBits |= OPT_BIT_MINIMUM_OCTANT_WIDTH;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeConfigurationIType#minimumOctantHeight() minimumOctantHeight} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link OctTreeConfigurationIType#minimumOctantHeight() minimumOctantHeight}.</em>
     * @param minimumOctantHeight The value for minimumOctantHeight 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setMinimumOctantHeight(int minimumOctantHeight) {
      this.minimumOctantHeight = minimumOctantHeight;
      optBits |= OPT_BIT_MINIMUM_OCTANT_HEIGHT;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeConfigurationIType#minimumOctantDepth() minimumOctantDepth} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link OctTreeConfigurationIType#minimumOctantDepth() minimumOctantDepth}.</em>
     * @param minimumOctantDepth The value for minimumOctantDepth 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setMinimumOctantDepth(int minimumOctantDepth) {
      this.minimumOctantDepth = minimumOctantDepth;
      optBits |= OPT_BIT_MINIMUM_OCTANT_DEPTH;
      return this;
    }

    /**
     * Initializes the value for the {@link OctTreeConfigurationIType#trimOnRemove() trimOnRemove} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link OctTreeConfigurationIType#trimOnRemove() trimOnRemove}.</em>
     * @param trimOnRemove The value for trimOnRemove 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setTrimOnRemove(boolean trimOnRemove) {
      this.trimOnRemove = trimOnRemove;
      optBits |= OPT_BIT_TRIM_ON_REMOVE;
      return this;
    }

    /**
     * Builds a new {@link OctTreeConfigurationI OctTreeConfigurationI}.
     * @return An immutable instance of OctTreeConfigurationI
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public OctTreeConfigurationI build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new OctTreeConfigurationI(this);
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
      return "Cannot build OctTreeConfigurationI, some of required attributes are not set " + attributes;
    }
  }
}
