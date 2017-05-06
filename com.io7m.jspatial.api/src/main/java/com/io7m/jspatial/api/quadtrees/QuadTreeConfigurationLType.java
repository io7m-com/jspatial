/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jspatial.api.quadtrees;

import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jspatial.api.JSpatialImmutableStyleType;
import org.immutables.value.Value;

/**
 * The type of long integer quadtree configurations.
 *
 * @since 3.0.0
 */

@JSpatialImmutableStyleType
@Value.Immutable
public interface QuadTreeConfigurationLType
{
  /**
   * @return The maximum bounding area of the tree
   */

  @Value.Parameter
  BoundingAreaL area();

  /**
   * @return The minimum width of quadrants (must be {@code >= 2})
   */

  @Value.Parameter
  @Value.Default
  default long minimumQuadrantWidth()
  {
    return 2L;
  }

  /**
   * @return The minimum height of quadrants (must be {@code >= 2})
   */

  @Value.Parameter
  @Value.Default
  default long minimumQuadrantHeight()
  {
    return 2L;
  }

  /**
   * @return {@code true} iff the implementation should attempt to trim empty
   * leaf nodes when an item is removed
   */

  @Value.Parameter
  @Value.Default
  default boolean trimOnRemove()
  {
    return false;
  }
}
