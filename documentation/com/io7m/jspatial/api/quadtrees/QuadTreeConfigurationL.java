package com.io7m.jspatial.api.quadtrees;

import com.io7m.jregions.core.unparameterized.areas.AreaL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * The type of long integer quadtree configurations.
 * @since 3.0.0
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "QuadTreeConfigurationLType"})
public final class QuadTreeConfigurationL
    implements QuadTreeConfigurationLType {
  private final AreaL area;
  private final long minimumQuadrantWidth;
  private final long minimumQuadrantHeight;
  private final boolean trimOnRemove;

  private QuadTreeConfigurationL(
      AreaL area,
      long minimumQuadrantWidth,
      long minimumQuadrantHeight,
      boolean trimOnRemove) {
    this.area = Objects.requireNonNull(area, "area");
    this.minimumQuadrantWidth = minimumQuadrantWidth;
    this.minimumQuadrantHeight = minimumQuadrantHeight;
    this.trimOnRemove = trimOnRemove;
    initShim.setMinimumQuadrantWidth(this.minimumQuadrantWidth);
    initShim.setMinimumQuadrantHeight(this.minimumQuadrantHeight);
    initShim.setTrimOnRemove(this.trimOnRemove);
    this.initShim = null;
  }

  private QuadTreeConfigurationL(QuadTreeConfigurationL.Builder builder) {
    this.area = builder.area;
    if (builder.minimumQuadrantWidthIsSet()) {
      initShim.setMinimumQuadrantWidth(builder.minimumQuadrantWidth);
    }
    if (builder.minimumQuadrantHeightIsSet()) {
      initShim.setMinimumQuadrantHeight(builder.minimumQuadrantHeight);
    }
    if (builder.trimOnRemoveIsSet()) {
      initShim.setTrimOnRemove(builder.trimOnRemove);
    }
    this.minimumQuadrantWidth = initShim.minimumQuadrantWidth();
    this.minimumQuadrantHeight = initShim.minimumQuadrantHeight();
    this.trimOnRemove = initShim.trimOnRemove();
    this.initShim = null;
  }

  private QuadTreeConfigurationL(
      QuadTreeConfigurationL original,
      AreaL area,
      long minimumQuadrantWidth,
      long minimumQuadrantHeight,
      boolean trimOnRemove) {
    this.area = area;
    this.minimumQuadrantWidth = minimumQuadrantWidth;
    this.minimumQuadrantHeight = minimumQuadrantHeight;
    this.trimOnRemove = trimOnRemove;
    this.initShim = null;
  }

  private static final int STAGE_INITIALIZING = -1;
  private static final int STAGE_UNINITIALIZED = 0;
  private static final int STAGE_INITIALIZED = 1;
  private transient volatile InitShim initShim = new InitShim();

  private final class InitShim {
    private long minimumQuadrantWidth;
    private int minimumQuadrantWidthBuildStage;

    long minimumQuadrantWidth() {
      if (minimumQuadrantWidthBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minimumQuadrantWidthBuildStage == STAGE_UNINITIALIZED) {
        minimumQuadrantWidthBuildStage = STAGE_INITIALIZING;
        this.minimumQuadrantWidth = minimumQuadrantWidthInitialize();
        minimumQuadrantWidthBuildStage = STAGE_INITIALIZED;
      }
      return this.minimumQuadrantWidth;
    }

    void setMinimumQuadrantWidth(long minimumQuadrantWidth) {
      this.minimumQuadrantWidth = minimumQuadrantWidth;
      minimumQuadrantWidthBuildStage = STAGE_INITIALIZED;
    }
    private long minimumQuadrantHeight;
    private int minimumQuadrantHeightBuildStage;

    long minimumQuadrantHeight() {
      if (minimumQuadrantHeightBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minimumQuadrantHeightBuildStage == STAGE_UNINITIALIZED) {
        minimumQuadrantHeightBuildStage = STAGE_INITIALIZING;
        this.minimumQuadrantHeight = minimumQuadrantHeightInitialize();
        minimumQuadrantHeightBuildStage = STAGE_INITIALIZED;
      }
      return this.minimumQuadrantHeight;
    }

    void setMinimumQuadrantHeight(long minimumQuadrantHeight) {
      this.minimumQuadrantHeight = minimumQuadrantHeight;
      minimumQuadrantHeightBuildStage = STAGE_INITIALIZED;
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
      if (minimumQuadrantWidthBuildStage == STAGE_INITIALIZING) attributes.add("minimumQuadrantWidth");
      if (minimumQuadrantHeightBuildStage == STAGE_INITIALIZING) attributes.add("minimumQuadrantHeight");
      if (trimOnRemoveBuildStage == STAGE_INITIALIZING) attributes.add("trimOnRemove");
      return "Cannot build QuadTreeConfigurationL, attribute initializers form cycle" + attributes;
    }
  }

  private long minimumQuadrantWidthInitialize() {
    return QuadTreeConfigurationLType.super.minimumQuadrantWidth();
  }

  private long minimumQuadrantHeightInitialize() {
    return QuadTreeConfigurationLType.super.minimumQuadrantHeight();
  }

  private boolean trimOnRemoveInitialize() {
    return QuadTreeConfigurationLType.super.trimOnRemove();
  }

  /**
   * @return The maximum bounding area of the tree
   */
  @Override
  public AreaL area() {
    return area;
  }

  /**
   * @return The minimum width of quadrants (must be {@code >= 2})
   */
  @Override
  public long minimumQuadrantWidth() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.minimumQuadrantWidth()
        : this.minimumQuadrantWidth;
  }

  /**
   * @return The minimum height of quadrants (must be {@code >= 2})
   */
  @Override
  public long minimumQuadrantHeight() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.minimumQuadrantHeight()
        : this.minimumQuadrantHeight;
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
   * Copy the current immutable object by setting a value for the {@link QuadTreeConfigurationLType#area() area} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for area
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeConfigurationL withArea(AreaL value) {
    if (this.area == value) return this;
    AreaL newValue = Objects.requireNonNull(value, "area");
    return new QuadTreeConfigurationL(this, newValue, this.minimumQuadrantWidth, this.minimumQuadrantHeight, this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeConfigurationLType#minimumQuadrantWidth() minimumQuadrantWidth} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for minimumQuadrantWidth
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeConfigurationL withMinimumQuadrantWidth(long value) {
    if (this.minimumQuadrantWidth == value) return this;
    return new QuadTreeConfigurationL(this, this.area, value, this.minimumQuadrantHeight, this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeConfigurationLType#minimumQuadrantHeight() minimumQuadrantHeight} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for minimumQuadrantHeight
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeConfigurationL withMinimumQuadrantHeight(long value) {
    if (this.minimumQuadrantHeight == value) return this;
    return new QuadTreeConfigurationL(this, this.area, this.minimumQuadrantWidth, value, this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeConfigurationLType#trimOnRemove() trimOnRemove} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for trimOnRemove
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeConfigurationL withTrimOnRemove(boolean value) {
    if (this.trimOnRemove == value) return this;
    return new QuadTreeConfigurationL(this, this.area, this.minimumQuadrantWidth, this.minimumQuadrantHeight, value);
  }

  /**
   * This instance is equal to all instances of {@code QuadTreeConfigurationL} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof QuadTreeConfigurationL
        && equalTo((QuadTreeConfigurationL) another);
  }

  private boolean equalTo(QuadTreeConfigurationL another) {
    return area.equals(another.area)
        && minimumQuadrantWidth == another.minimumQuadrantWidth
        && minimumQuadrantHeight == another.minimumQuadrantHeight
        && trimOnRemove == another.trimOnRemove;
  }

  /**
   * Computes a hash code from attributes: {@code area}, {@code minimumQuadrantWidth}, {@code minimumQuadrantHeight}, {@code trimOnRemove}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + area.hashCode();
    h += (h << 5) + Long.hashCode(minimumQuadrantWidth);
    h += (h << 5) + Long.hashCode(minimumQuadrantHeight);
    h += (h << 5) + Boolean.hashCode(trimOnRemove);
    return h;
  }

  /**
   * Prints the immutable value {@code QuadTreeConfigurationL} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "QuadTreeConfigurationL{"
        + "area=" + area
        + ", minimumQuadrantWidth=" + minimumQuadrantWidth
        + ", minimumQuadrantHeight=" + minimumQuadrantHeight
        + ", trimOnRemove=" + trimOnRemove
        + "}";
  }

  /**
   * Construct a new immutable {@code QuadTreeConfigurationL} instance.
   * @param area The value for the {@code area} attribute
   * @param minimumQuadrantWidth The value for the {@code minimumQuadrantWidth} attribute
   * @param minimumQuadrantHeight The value for the {@code minimumQuadrantHeight} attribute
   * @param trimOnRemove The value for the {@code trimOnRemove} attribute
   * @return An immutable QuadTreeConfigurationL instance
   */
  public static QuadTreeConfigurationL of(AreaL area, long minimumQuadrantWidth, long minimumQuadrantHeight, boolean trimOnRemove) {
    return new QuadTreeConfigurationL(area, minimumQuadrantWidth, minimumQuadrantHeight, trimOnRemove);
  }

  /**
   * Creates an immutable copy of a {@link QuadTreeConfigurationLType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable QuadTreeConfigurationL instance
   */
  public static QuadTreeConfigurationL copyOf(QuadTreeConfigurationLType instance) {
    if (instance instanceof QuadTreeConfigurationL) {
      return (QuadTreeConfigurationL) instance;
    }
    return QuadTreeConfigurationL.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link QuadTreeConfigurationL QuadTreeConfigurationL}.
   * @return A new QuadTreeConfigurationL builder
   */
  public static QuadTreeConfigurationL.Builder builder() {
    return new QuadTreeConfigurationL.Builder();
  }

  /**
   * Builds instances of type {@link QuadTreeConfigurationL QuadTreeConfigurationL}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_AREA = 0x1L;
    private static final long OPT_BIT_MINIMUM_QUADRANT_WIDTH = 0x1L;
    private static final long OPT_BIT_MINIMUM_QUADRANT_HEIGHT = 0x2L;
    private static final long OPT_BIT_TRIM_ON_REMOVE = 0x4L;
    private long initBits = 0x1L;
    private long optBits;

    private AreaL area;
    private long minimumQuadrantWidth;
    private long minimumQuadrantHeight;
    private boolean trimOnRemove;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code QuadTreeConfigurationLType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(QuadTreeConfigurationLType instance) {
      Objects.requireNonNull(instance, "instance");
      setArea(instance.area());
      setMinimumQuadrantWidth(instance.minimumQuadrantWidth());
      setMinimumQuadrantHeight(instance.minimumQuadrantHeight());
      setTrimOnRemove(instance.trimOnRemove());
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeConfigurationLType#area() area} attribute.
     * @param area The value for area 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setArea(AreaL area) {
      this.area = Objects.requireNonNull(area, "area");
      initBits &= ~INIT_BIT_AREA;
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeConfigurationLType#minimumQuadrantWidth() minimumQuadrantWidth} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link QuadTreeConfigurationLType#minimumQuadrantWidth() minimumQuadrantWidth}.</em>
     * @param minimumQuadrantWidth The value for minimumQuadrantWidth 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setMinimumQuadrantWidth(long minimumQuadrantWidth) {
      this.minimumQuadrantWidth = minimumQuadrantWidth;
      optBits |= OPT_BIT_MINIMUM_QUADRANT_WIDTH;
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeConfigurationLType#minimumQuadrantHeight() minimumQuadrantHeight} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link QuadTreeConfigurationLType#minimumQuadrantHeight() minimumQuadrantHeight}.</em>
     * @param minimumQuadrantHeight The value for minimumQuadrantHeight 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setMinimumQuadrantHeight(long minimumQuadrantHeight) {
      this.minimumQuadrantHeight = minimumQuadrantHeight;
      optBits |= OPT_BIT_MINIMUM_QUADRANT_HEIGHT;
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeConfigurationLType#trimOnRemove() trimOnRemove} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link QuadTreeConfigurationLType#trimOnRemove() trimOnRemove}.</em>
     * @param trimOnRemove The value for trimOnRemove 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setTrimOnRemove(boolean trimOnRemove) {
      this.trimOnRemove = trimOnRemove;
      optBits |= OPT_BIT_TRIM_ON_REMOVE;
      return this;
    }

    /**
     * Builds a new {@link QuadTreeConfigurationL QuadTreeConfigurationL}.
     * @return An immutable instance of QuadTreeConfigurationL
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public QuadTreeConfigurationL build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new QuadTreeConfigurationL(this);
    }

    private boolean minimumQuadrantWidthIsSet() {
      return (optBits & OPT_BIT_MINIMUM_QUADRANT_WIDTH) != 0;
    }

    private boolean minimumQuadrantHeightIsSet() {
      return (optBits & OPT_BIT_MINIMUM_QUADRANT_HEIGHT) != 0;
    }

    private boolean trimOnRemoveIsSet() {
      return (optBits & OPT_BIT_TRIM_ON_REMOVE) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_AREA) != 0) attributes.add("area");
      return "Cannot build QuadTreeConfigurationL, some of required attributes are not set " + attributes;
    }
  }
}
