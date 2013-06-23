/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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

import javax.annotation.Nonnull;

import com.io7m.jtensors.VectorReadable2F;

/**
 * <p>
 * The type of objects with bounding areas, defined with single-precision
 * floating point coordinate values.
 * </p>
 * <p>
 * Note that in contrast to {@link BoundingArea}, the ranges given have an
 * inclusive lower bound and exclusive upper bound. Therefore, an area with
 * the lower corner at <code>[0,0]</code> and upper corner also at
 * <code>[0,0]</code> describes an area of width <code>0</code> and height
 * <code>0</code>.
 * </p>
 * <p>
 * The values returned by the interface functions are expected to be constant
 * over calls to {@linkplain QuadTreeInterfaceF quadtree} insertion functions.
 * That is, if:
 * <ol>
 * <li>An object <code>O</code> implements {@link BoundingAreaF}</li>
 * <li>The values of <code>O.{@link #boundingAreaLowerF()}</code> and
 * <code>O.{@link #boundingAreaUpperF()}</code> are taken.</li>
 * <li><code>O</code> is inserted into the tree <code>T</code></li>
 * <li>The values of <code>O.{@link #boundingAreaLowerF()}</code> and
 * <code>O.{@link #boundingAreaUpperF()}</code> are taken again and return
 * different results from the first calls.</li>
 * </ol>
 * </p>
 * <p>
 * ... Then the results are undefined.
 * </p>
 * 
 * @since 2.1.0
 */

public interface BoundingAreaF
{
  /**
   * Retrieve the lower corner of the area.
   */

  @Nonnull VectorReadable2F boundingAreaLowerF();

  /**
   * Retrieve the upper corner of the area.
   */

  @Nonnull VectorReadable2F boundingAreaUpperF();
}
