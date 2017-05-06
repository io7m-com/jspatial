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

package com.io7m.jspatial.examples.swing;

import com.io7m.jspatial.api.BoundingAreaD;
import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jspatial.api.Ray2D;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationD;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import org.derive4j.Data;

@Data
interface QuadTreeCommandType
{
  <R> R match(CasesType<R> cases);

  interface CasesType<R>
  {
    R addObject(
      BoundingAreaL area_l,
      Integer item);

    R removeObject(
      Integer item);

    R trimQuadTree();

    R createQuadTreeL(
      QuadTreeConfigurationL lconfig);

    R createQuadTreeD(
      QuadTreeConfigurationD dconfig);

    R areaQuery(
      BoundingAreaL area_l,
      BoundingAreaD area_d,
      boolean overlaps);

    R rayQuery(
      Ray2D ray);
  }
}
