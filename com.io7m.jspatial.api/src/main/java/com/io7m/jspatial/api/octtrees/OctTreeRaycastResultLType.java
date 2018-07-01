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

package com.io7m.jspatial.api.octtrees;

import com.io7m.immutables.styles.ImmutablesStyleType;
import com.io7m.jregions.core.unparameterized.volumes.VolumeL;
import org.immutables.value.Value;

/**
 * The type of octtree raycast results.
 *
 * @param <T> The precise type of objects
 *
 * @since 3.0.0
 */

@Value.Immutable
@ImmutablesStyleType
public interface OctTreeRaycastResultLType<T>
  extends Comparable<OctTreeRaycastResultLType<T>>
{
  @Override
  default int compareTo(final OctTreeRaycastResultLType<T> o)
  {
    return Double.compare(this.distance(), o.distance());
  }

  /**
   * @return The distance to the object
   */

  @Value.Parameter(order = 0)
  double distance();

  /**
   * @return The object volume
   */

  @Value.Parameter(order = 1)
  VolumeL volume();

  /**
   * @return The object
   */

  @Value.Parameter(order = 2)
  T item();
}
