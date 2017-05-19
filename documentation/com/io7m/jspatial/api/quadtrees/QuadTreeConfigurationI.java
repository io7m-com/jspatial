package com.io7m.jspatial.api.quadtrees;

import com.io7m.jregions.core.unparameterized.areas.AreaI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * The type of integer quadtree configurations.
 * @since 3.0.0
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "QuadTreeConfigurationIType"})
public final class QuadTreeConfigurationI
    implements QuadTreeConfigurationIType {
  private final AreaI area;
  private final int minimumQuadrantWidth;
  private final int minimumQuadrantHeight;
  private final boolean trimOnRemove;

  private QuadTreeConfigurationI(
      AreaI area,
      int minimumQuadrantWidth,
      int minimumQuadrantHeight,
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

  private QuadTreeConfigurationI(QuadTreeConfigurationI.Builder builder) {
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

  private QuadTreeConfigurationI(
      QuadTreeConfigurationI original,
      AreaI area,
      int minimumQuadrantWidth,
      int minimumQuadrantHeight,
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
    private int minimumQuadrantWidth;
    private int minimumQuadrantWidthBuildStage;

    int minimumQuadrantWidth() {
      if (minimumQuadrantWidthBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minimumQuadrantWidthBuildStage == STAGE_UNINITIALIZED) {
        minimumQuadrantWidthBuildStage = STAGE_INITIALIZING;
        this.minimumQuadrantWidth = minimumQuadrantWidthInitialize();
        minimumQuadrantWidthBuildStage = STAGE_INITIALIZED;
      }
      return this.minimumQuadrantWidth;
    }

    void setMinimumQuadrantWidth(int minimumQuadrantWidth) {
      this.minimumQuadrantWidth = minimumQuadrantWidth;
      minimumQuadrantWidthBuildStage = STAGE_INITIALIZED;
    }
    private int minimumQuadrantHeight;
    private int minimumQuadrantHeightBuildStage;

    int minimumQuadrantHeight() {
      if (minimumQuadrantHeightBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minimumQuadrantHeightBuildStage == STAGE_UNINITIALIZED) {
        minimumQuadrantHeightBuildStage = STAGE_INITIALIZING;
        this.minimumQuadrantHeight = minimumQuadrantHeightInitialize();
        minimumQuadrantHeightBuildStage = STAGE_INITIALIZED;
      }
      return this.minimumQuadrantHeight;
    }

    void setMinimumQuadrantHeight(int minimumQuadrantHeight) {
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
      return "Cannot build QuadTreeConfigurationI, attribute initializers form cycle" + attributes;
    }
  }

  private int minimumQuadrantWidthInitialize() {
    return QuadTreeConfigurationIType.super.minimumQuadrantWidth();
  }

  private int minimumQuadrantHeightInitialize() {
    return QuadTreeConfigurationIType.super.minimumQuadrantHeight();
  }

  private boolean trimOnRemoveInitialize() {
    return QuadTreeConfigurationIType.super.trimOnRemove();
  }

  /**
   * @return The maximum bounding area of the tree
   */
  @Override
  public AreaI area() {
    return area;
  }

  /**
   * @return The minimum width of quadrants (must be {@code >= 2})
   */
  @Override
  public int minimumQuadrantWidth() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.minimumQuadrantWidth()
        : this.minimumQuadrantWidth;
  }

  /**
   * @return The minimum height of quadrants (must be {@code >= 2})
   */
  @Override
  public int minimumQuadrantHeight() {
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
   * Copy the current immutable object by setting a value for the {@link QuadTreeConfigurationIType#area() area} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for area
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeConfigurationI withArea(AreaI value) {
    if (this.area == value) return this;
    AreaI newValue = Objects.requireNonNull(value, "area");
    return new QuadTreeConfigurationI(this, newValue, this.minimumQuadrantWidth, this.minimumQuadrantHeight, this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeConfigurationIType#minimumQuadrantWidth() minimumQuadrantWidth} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for minimumQuadrantWidth
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeConfigurationI withMinimumQuadrantWidth(int value) {
    if (this.minimumQuadrantWidth == value) return this;
    return new QuadTreeConfigurationI(this, this.area, value, this.minimumQuadrantHeight, this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeConfigurationIType#minimumQuadrantHeight() minimumQuadrantHeight} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for minimumQuadrantHeight
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeConfigurationI withMinimumQuadrantHeight(int value) {
    if (this.minimumQuadrantHeight == value) return this;
    return new QuadTreeConfigurationI(this, this.area, this.minimumQuadrantWidth, value, this.trimOnRemove);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link QuadTreeConfigurationIType#trimOnRemove() trimOnRemove} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for trimOnRemove
   * @return A modified copy of the {@code this} object
   */
  public final QuadTreeConfigurationI withTrimOnRemove(boolean value) {
    if (this.trimOnRemove == value) return this;
    return new QuadTreeConfigurationI(this, this.area, this.minimumQuadrantWidth, this.minimumQuadrantHeight, value);
  }

  /**
   * This instance is equal to all instances of {@code QuadTreeConfigurationI} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof QuadTreeConfigurationI
        && equalTo((QuadTreeConfigurationI) another);
  }

  private boolean equalTo(QuadTreeConfigurationI another) {
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
    h += (h << 5) + minimumQuadrantWidth;
    h += (h << 5) + minimumQuadrantHeight;
    h += (h << 5) + Boolean.hashCode(trimOnRemove);
    return h;
  }

  /**
   * Prints the immutable value {@code QuadTreeConfigurationI} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "QuadTreeConfigurationI{"
        + "area=" + area
        + ", minimumQuadrantWidth=" + minimumQuadrantWidth
        + ", minimumQuadrantHeight=" + minimumQuadrantHeight
        + ", trimOnRemove=" + trimOnRemove
        + "}";
  }

  /**
   * Construct a new immutable {@code QuadTreeConfigurationI} instance.
   * @param area The value for the {@code area} attribute
   * @param minimumQuadrantWidth The value for the {@code minimumQuadrantWidth} attribute
   * @param minimumQuadrantHeight The value for the {@code minimumQuadrantHeight} attribute
   * @param trimOnRemove The value for the {@code trimOnRemove} attribute
   * @return An immutable QuadTreeConfigurationI instance
   */
  public static QuadTreeConfigurationI of(AreaI area, int minimumQuadrantWidth, int minimumQuadrantHeight, boolean trimOnRemove) {
    return new QuadTreeConfigurationI(area, minimumQuadrantWidth, minimumQuadrantHeight, trimOnRemove);
  }

  /**
   * Creates an immutable copy of a {@link QuadTreeConfigurationIType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable QuadTreeConfigurationI instance
   */
  public static QuadTreeConfigurationI copyOf(QuadTreeConfigurationIType instance) {
    if (instance instanceof QuadTreeConfigurationI) {
      return (QuadTreeConfigurationI) instance;
    }
    return QuadTreeConfigurationI.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link QuadTreeConfigurationI QuadTreeConfigurationI}.
   * @return A new QuadTreeConfigurationI builder
   */
  public static QuadTreeConfigurationI.Builder builder() {
    return new QuadTreeConfigurationI.Builder();
  }

  /**
   * Builds instances of type {@link QuadTreeConfigurationI QuadTreeConfigurationI}.
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

    private AreaI area;
    private int minimumQuadrantWidth;
    private int minimumQuadrantHeight;
    private boolean trimOnRemove;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code QuadTreeConfigurationIType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(QuadTreeConfigurationIType instance) {
      Objects.requireNonNull(instance, "instance");
      setArea(instance.area());
      setMinimumQuadrantWidth(instance.minimumQuadrantWidth());
      setMinimumQuadrantHeight(instance.minimumQuadrantHeight());
      setTrimOnRemove(instance.trimOnRemove());
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeConfigurationIType#area() area} attribute.
     * @param area The value for area 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setArea(AreaI area) {
      this.area = Objects.requireNonNull(area, "area");
      initBits &= ~INIT_BIT_AREA;
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeConfigurationIType#minimumQuadrantWidth() minimumQuadrantWidth} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link QuadTreeConfigurationIType#minimumQuadrantWidth() minimumQuadrantWidth}.</em>
     * @param minimumQuadrantWidth The value for minimumQuadrantWidth 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setMinimumQuadrantWidth(int minimumQuadrantWidth) {
      this.minimumQuadrantWidth = minimumQuadrantWidth;
      optBits |= OPT_BIT_MINIMUM_QUADRANT_WIDTH;
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeConfigurationIType#minimumQuadrantHeight() minimumQuadrantHeight} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link QuadTreeConfigurationIType#minimumQuadrantHeight() minimumQuadrantHeight}.</em>
     * @param minimumQuadrantHeight The value for minimumQuadrantHeight 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setMinimumQuadrantHeight(int minimumQuadrantHeight) {
      this.minimumQuadrantHeight = minimumQuadrantHeight;
      optBits |= OPT_BIT_MINIMUM_QUADRANT_HEIGHT;
      return this;
    }

    /**
     * Initializes the value for the {@link QuadTreeConfigurationIType#trimOnRemove() trimOnRemove} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link QuadTreeConfigurationIType#trimOnRemove() trimOnRemove}.</em>
     * @param trimOnRemove The value for trimOnRemove 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setTrimOnRemove(boolean trimOnRemove) {
      this.trimOnRemove = trimOnRemove;
      optBits |= OPT_BIT_TRIM_ON_REMOVE;
      return this;
    }

    /**
     * Builds a new {@link QuadTreeConfigurationI QuadTreeConfigurationI}.
     * @return An immutable instance of QuadTreeConfigurationI
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public QuadTreeConfigurationI build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new QuadTreeConfigurationI(this);
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
      return "Cannot build QuadTreeConfigurationI, some of required attributes are not set " + attributes;
    }
  }
}
