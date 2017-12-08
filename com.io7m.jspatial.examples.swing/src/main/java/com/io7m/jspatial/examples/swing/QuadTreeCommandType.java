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

package com.io7m.jspatial.examples.swing;

import com.io7m.jregions.core.unparameterized.areas.AreaD;
import com.io7m.jregions.core.unparameterized.areas.AreaL;
import com.io7m.jspatial.api.JSpatialImmutableStyleType;
import com.io7m.jspatial.api.Ray2D;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationD;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import org.immutables.value.Value;

interface QuadTreeCommandType
{
  enum Kind
  {
    ADD_OBJECT,
    REMOVE_OBJECT,
    TRIM,
    CREATE_QUAD_TREE_L,
    CREATE_QUAD_TREE_D,
    AREA_QUERY,
    RAY_QUERY
  }

  Kind kind();

  @JSpatialImmutableStyleType
  @Value.Immutable
  interface AddObjectType extends QuadTreeCommandType
  {
    @Override
    default Kind kind()
    {
      return Kind.ADD_OBJECT;
    }

    @Value.Parameter
    AreaL area();

    @Value.Parameter
    Integer item();
  }

  @JSpatialImmutableStyleType
  @Value.Immutable
  interface RemoveObjectType extends QuadTreeCommandType
  {
    @Override
    default Kind kind()
    {
      return Kind.REMOVE_OBJECT;
    }

    @Value.Parameter
    Integer item();
  }

  @JSpatialImmutableStyleType
  @Value.Immutable
  interface TrimQuadTreeType extends QuadTreeCommandType
  {
    @Override
    default Kind kind()
    {
      return Kind.TRIM;
    }
  }

  @JSpatialImmutableStyleType
  @Value.Immutable
  interface CreateQuadTreeLType extends QuadTreeCommandType
  {
    @Override
    default Kind kind()
    {
      return Kind.CREATE_QUAD_TREE_L;
    }

    @Value.Parameter
    QuadTreeConfigurationL configuration();
  }

  @JSpatialImmutableStyleType
  @Value.Immutable
  interface CreateQuadTreeDType extends QuadTreeCommandType
  {
    @Override
    default Kind kind()
    {
      return Kind.CREATE_QUAD_TREE_D;
    }

    @Value.Parameter
    QuadTreeConfigurationD configuration();
  }

  @JSpatialImmutableStyleType
  @Value.Immutable
  interface AreaQueryType extends QuadTreeCommandType
  {
    @Override
    default Kind kind()
    {
      return Kind.AREA_QUERY;
    }

    @Value.Parameter
    AreaL areaL();

    @Value.Parameter
    AreaD areaD();

    @Value.Parameter
    boolean overlaps();
  }

  @JSpatialImmutableStyleType
  @Value.Immutable
  interface RayQueryType extends QuadTreeCommandType
  {
    @Override
    default Kind kind()
    {
      return Kind.RAY_QUERY;
    }

    @Value.Parameter
    Ray2D ray();
  }
}
