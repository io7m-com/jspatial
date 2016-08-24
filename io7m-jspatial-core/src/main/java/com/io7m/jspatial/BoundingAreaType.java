/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jspatial;

import com.io7m.jtensors.VectorReadable2IType;

/**
 * <p>
 * The type of objects with bounding areas.
 * </p>
 * <p>
 * Ranges are inclusive, and therefore it is impossible for a bounding area to
 * be of zero width or height: An area with the lower corner at
 * <code>[0,0]</code> and the upper corner at <code>[0,0]</code> describes an
 * area of width <code>1</code> and height <code>1</code> because
 * <code>0</code> is simultaneously the lowermost and uppermost valid value
 * for each axis.
 * </p>
 * <p>
 * The results are undefined if the values returned by the interface functions
 * change after the object is inserted into the tree.
 * </p>
 */

public interface BoundingAreaType
{
  /**
   * @return The lower corner of the area.
   */

  VectorReadable2IType boundingAreaLower();

  /**
   * @return The upper corner of the area.
   */

  VectorReadable2IType boundingAreaUpper();
}
