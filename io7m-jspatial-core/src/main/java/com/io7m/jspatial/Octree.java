/*
 * Copyright © 2012 http://io7m.com
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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.io7m.jaux.Constraints;
import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jtensors.VectorI3I;

public final class Octree
{
  private static final class Node
  {
    private final @Nonnull VectorI3I                      position;
    private final @Nonnull VectorI3I                      size;
    private final @Nonnull List<? extends BoundingVolume> objects;
    private boolean                                       is_leaf;

    private @CheckForNull Node                            x0y0z0;
    private @CheckForNull Node                            x1y0z0;
    private @CheckForNull Node                            x0y1z0;
    private @CheckForNull Node                            x1y1z0;
    private @CheckForNull Node                            x0y0z1;
    private @CheckForNull Node                            x1y0z1;
    private @CheckForNull Node                            x0y1z1;
    private @CheckForNull Node                            x1y1z1;

    Node(
      final @Nonnull VectorI3I position,
      final @Nonnull VectorI3I size,
      final @Nonnull List<? extends BoundingVolume> objects)
    {
      this.position = position;
      this.size = size;
      this.objects = objects;
      this.is_leaf = true;

      this.x0y0z0 = null;
      this.x1y0z0 = null;
      this.x0y1z0 = null;
      this.x1y1z0 = null;
      this.x0y0z1 = null;
      this.x1y0z1 = null;
      this.x0y1z1 = null;
      this.x1y1z1 = null;
    }

    boolean canSplit()
    {
      if (this.size.x < 2) {
        return false;
      }
      if (this.size.y < 2) {
        return false;
      }
      if (this.size.z < 2) {
        return false;
      }
      return true;
    }

    void split()
    {
      assert this.is_leaf;

      final int new_sx = this.size.x >> 1;
      final int new_sy = this.size.y >> 1;
      final int new_sz = this.size.z >> 1;

      final VectorI3I new_size = new VectorI3I(new_sx, new_sy, new_sz);

      final int x0 = this.position.x;
      final int x1 = this.position.x + new_sx;
      final int y0 = this.position.y;
      final int y1 = this.position.y + new_sy;
      final int z0 = this.position.z;
      final int z1 = this.position.z + new_sz;

      this.x0y0z0 =
        new Node(
          new VectorI3I(x0, y0, z0),
          new_size,
          new ArrayList<BoundingVolume>());
      this.x1y0z0 =
        new Node(
          new VectorI3I(x1, y0, z0),
          new_size,
          new ArrayList<BoundingVolume>());

      this.x0y1z0 =
        new Node(
          new VectorI3I(x0, y1, z0),
          new_size,
          new ArrayList<BoundingVolume>());
      this.x1y1z0 =
        new Node(
          new VectorI3I(x1, y1, z0),
          new_size,
          new ArrayList<BoundingVolume>());

      this.x0y0z1 =
        new Node(
          new VectorI3I(x0, y0, z1),
          new_size,
          new ArrayList<BoundingVolume>());
      this.x1y0z1 =
        new Node(
          new VectorI3I(x1, y0, z1),
          new_size,
          new ArrayList<BoundingVolume>());

      this.x0y1z1 =
        new Node(
          new VectorI3I(x0, y1, z1),
          new_size,
          new ArrayList<BoundingVolume>());
      this.x1y1z1 =
        new Node(
          new VectorI3I(x1, y1, z1),
          new_size,
          new ArrayList<BoundingVolume>());

      this.is_leaf = false;
    }

    @Override public String toString()
    {
      final StringBuilder builder = new StringBuilder();
      builder.append("[Node position=");
      builder.append(this.position);
      builder.append(" ");
      builder.append(this.size);
      builder.append(" ");
      builder.append(this.objects);
      builder.append(" ");
      builder.append(this.x0y0z0);
      builder.append(" ");
      builder.append(this.x1y0z0);
      builder.append(" ");
      builder.append(this.x0y1z0);
      builder.append(" ");
      builder.append(this.x1y1z0);
      builder.append(" ");
      builder.append(this.x0y0z1);
      builder.append(" ");
      builder.append(this.x1y0z1);
      builder.append(" ");
      builder.append(this.x0y1z1);
      builder.append(" ");
      builder.append(this.x1y1z1);
      builder.append("]");
      return builder.toString();
    }
  }

  private @Nonnull final Node root;

  public Octree(
    final int exponent)
    throws ConstraintError
  {
    Constraints.constrainRange(exponent, 1, 30, "Exponent");

    final VectorI3I size =
      new VectorI3I(1 << exponent, 1 << exponent, 1 << exponent);

    this.root =
      new Node(VectorI3I.ZERO, size, new ArrayList<BoundingVolume>());
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[Octree ");
    builder.append(this.root);
    builder.append("]");
    return builder.toString();
  }
}
