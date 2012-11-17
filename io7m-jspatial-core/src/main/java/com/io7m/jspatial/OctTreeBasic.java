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

import java.util.TreeSet;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.io7m.jaux.Constraints;
import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.UnimplementedCodeException;
import com.io7m.jaux.functional.Function;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorReadable3I;

/**
 * Basic implementation of Octrees, based on a 3D generalization of
 * {@link OctTreeBasic}.
 */

public class OctTreeBasic<T extends OctTreeMember<T>> implements
  OctTreeInterface<T>
{
  final class Octant implements BoundingVolume
  {
    private final @Nonnull VectorI3I  lower;
    private final @Nonnull VectorI3I  upper;
    final int                         size_x;
    final int                         size_y;
    final int                         size_z;
    private @CheckForNull Octant      x0y0z0;
    private @CheckForNull Octant      x1y0z0;
    private @CheckForNull Octant      x0y1z0;
    private @CheckForNull Octant      x1y1z0;
    private @CheckForNull Octant      x0y0z1;
    private @CheckForNull Octant      x1y0z1;
    private @CheckForNull Octant      x0y1z1;
    private @CheckForNull Octant      x1y1z1;
    private boolean                   leaf;
    private final @Nonnull TreeSet<T> octant_objects;

    public Octant(
      final @Nonnull VectorI3I lower,
      final @Nonnull VectorI3I upper)
    {
      this.lower = lower;
      this.upper = upper;
      this.size_x = Dimensions.getSpanSizeX(this.lower, this.upper);
      this.size_y = Dimensions.getSpanSizeY(this.lower, this.upper);
      this.size_z = Dimensions.getSpanSizeZ(this.lower, this.upper);

      this.octant_objects = new TreeSet<T>();

      this.leaf = true;
      this.x0y0z0 = null;
      this.x1y0z0 = null;
      this.x0y1z0 = null;
      this.x1y1z0 = null;
      this.x0y0z1 = null;
      this.x1y0z1 = null;
      this.x0y1z1 = null;
      this.x1y1z1 = null;
    }

    @Override public @Nonnull VectorReadable3I boundingVolumeLower()
    {
      return this.lower;
    }

    @Override public @Nonnull VectorReadable3I boundingVolumeUpper()
    {
      return this.upper;
    }

    private boolean canSplit()
    {
      if (this.leaf) {
        return (this.size_x >= 2) && (this.size_y >= 2) && (this.size_z >= 2);
      }
      return false;
    }

    void clear()
    {
      this.octant_objects.clear();
      if (this.leaf == false) {
        this.x0y0z0.clear();
        this.x1y0z0.clear();
        this.x0y1z0.clear();
        this.x1y1z0.clear();
        this.x0y0z1.clear();
        this.x1y0z1.clear();
        this.x0y1z1.clear();
        this.x1y1z1.clear();
      }
    }

    /**
     * Attempt to insert <code>item</code> into this node, or the children of
     * this node.
     * 
     * @return <code>true</code>, if the item was inserted and
     *         <code>false</code> otherwise.
     */

    boolean insert(
      final @Nonnull T item)
    {
      return this.insertBase(item);
    }

    /**
     * Insertion base case: item may or may not fit within node.
     */

    @SuppressWarnings("synthetic-access") private boolean insertBase(
      final @Nonnull T item)
    {
      if (OctTreeBasic.this.objects_all.contains(item)) {
        return false;
      }
      if (BoundingVolumeCheck.containedWithin(this, item)) {
        return this.insertStep(item);
      }
      return false;
    }

    /**
     * Insert the given object into the current octant's object list, and also
     * inserted into the "global" object list.
     */

    @SuppressWarnings("synthetic-access") private boolean insertObject(
      final @Nonnull T item)
    {
      OctTreeBasic.this.objects_all.add(item);
      this.octant_objects.add(item);
      return true;
    }

    /**
     * Insertion inductive case: item fits within node, but may fit more
     * precisely within a child node.
     */

    private boolean insertStep(
      final @Nonnull T item)
    {
      /**
       * The object can fit in this node, but perhaps it is possible to fit it
       * more precisely within one of the child nodes.
       */

      /**
       * If this node is a leaf, and is large enough to split, do so.
       */

      if (this.leaf == true) {
        if (this.canSplit()) {
          this.split();
        } else {

          /**
           * The node is a leaf, but cannot be split further. Insert directly.
           */

          return this.insertObject(item);
        }
      }

      /**
       * See if the object will fit in any of the child nodes.
       */

      assert this.leaf == false;

      if (BoundingVolumeCheck.containedWithin(this.x0y0z0, item)) {
        return this.x0y0z0.insertStep(item);
      }
      if (BoundingVolumeCheck.containedWithin(this.x1y0z0, item)) {
        return this.x1y0z0.insertStep(item);
      }
      if (BoundingVolumeCheck.containedWithin(this.x0y1z0, item)) {
        return this.x0y1z0.insertStep(item);
      }
      if (BoundingVolumeCheck.containedWithin(this.x1y1z0, item)) {
        return this.x1y1z0.insertStep(item);
      }

      if (BoundingVolumeCheck.containedWithin(this.x0y0z1, item)) {
        return this.x0y0z1.insertStep(item);
      }
      if (BoundingVolumeCheck.containedWithin(this.x1y0z1, item)) {
        return this.x1y0z1.insertStep(item);
      }
      if (BoundingVolumeCheck.containedWithin(this.x0y1z1, item)) {
        return this.x0y1z1.insertStep(item);
      }
      if (BoundingVolumeCheck.containedWithin(this.x1y1z1, item)) {
        return this.x1y1z1.insertStep(item);
      }

      /**
       * Otherwise, insert the object into this node.
       */
      return this.insertObject(item);
    }

    /**
     * Split this node into four octants.
     */

    private void split()
    {
      assert this.canSplit();

      final Octants q = new Octants(this.lower, this.upper);
      this.x0y0z0 = new Octant(q.x0y0z0_lower, q.x0y0z0_upper);
      this.x0y1z0 = new Octant(q.x0y1z0_lower, q.x0y1z0_upper);
      this.x1y0z0 = new Octant(q.x1y0z0_lower, q.x1y0z0_upper);
      this.x1y1z0 = new Octant(q.x1y1z0_lower, q.x1y1z0_upper);
      this.x0y0z1 = new Octant(q.x0y0z1_lower, q.x0y0z1_upper);
      this.x0y1z1 = new Octant(q.x0y1z1_lower, q.x0y1z1_upper);
      this.x1y0z1 = new Octant(q.x1y0z1_lower, q.x1y0z1_upper);
      this.x1y1z1 = new Octant(q.x1y1z1_lower, q.x1y1z1_upper);
      this.leaf = false;
    }

    void traverse(
      final int depth,
      final @Nonnull OctTreeTraversal traversal)
      throws Exception
    {
      traversal.visit(depth, this.lower, this.upper);

      if (this.leaf == false) {
        this.x0y0z0.traverse(depth + 1, traversal);
        this.x1y0z0.traverse(depth + 1, traversal);
        this.x0y1z0.traverse(depth + 1, traversal);
        this.x1y1z0.traverse(depth + 1, traversal);
        this.x0y0z1.traverse(depth + 1, traversal);
        this.x1y0z1.traverse(depth + 1, traversal);
        this.x0y1z1.traverse(depth + 1, traversal);
        this.x1y1z1.traverse(depth + 1, traversal);
      }
    }
  }

  static final class Octants
  {
    final @Nonnull VectorI3I x0y0z0_lower;
    final @Nonnull VectorI3I x0y0z0_upper;

    final @Nonnull VectorI3I x1y0z0_lower;
    final @Nonnull VectorI3I x1y0z0_upper;

    final @Nonnull VectorI3I x0y1z0_lower;
    final @Nonnull VectorI3I x0y1z0_upper;

    final @Nonnull VectorI3I x1y1z0_lower;
    final @Nonnull VectorI3I x1y1z0_upper;

    final @Nonnull VectorI3I x0y0z1_lower;
    final @Nonnull VectorI3I x0y0z1_upper;

    final @Nonnull VectorI3I x1y0z1_lower;
    final @Nonnull VectorI3I x1y0z1_upper;

    final @Nonnull VectorI3I x0y1z1_lower;
    final @Nonnull VectorI3I x0y1z1_upper;

    final @Nonnull VectorI3I x1y1z1_lower;
    final @Nonnull VectorI3I x1y1z1_upper;

    int                      new_size_x;
    int                      new_size_y;
    int                      new_size_z;

    /**
     * Split an octant defined by the two points <code>lower</code> and
     * <code>upper</code> into eight octants.
     */

    Octants(
      final @Nonnull VectorI3I lower,
      final @Nonnull VectorI3I upper)
    {
      final int size_x = Dimensions.getSpanSizeX(lower, upper);
      final int size_y = Dimensions.getSpanSizeY(lower, upper);
      final int size_z = Dimensions.getSpanSizeZ(lower, upper);

      assert size_x >= 2;
      assert size_y >= 2;
      assert size_z >= 2;

      this.new_size_x = size_x >> 1;
      this.new_size_y = size_y >> 1;
      this.new_size_z = size_z >> 1;

      final int[] x_spans = new int[4];
      final int[] y_spans = new int[4];
      final int[] z_spans = new int[4];

      Dimensions.split1D(lower.getXI(), upper.getXI(), x_spans);
      Dimensions.split1D(lower.getYI(), upper.getYI(), y_spans);
      Dimensions.split1D(lower.getZI(), upper.getZI(), z_spans);

      this.x0y0z0_lower = new VectorI3I(x_spans[0], y_spans[0], z_spans[0]);
      this.x0y0z0_upper = new VectorI3I(x_spans[1], y_spans[1], z_spans[1]);

      this.x1y0z0_lower = new VectorI3I(x_spans[2], y_spans[0], z_spans[0]);
      this.x1y0z0_upper = new VectorI3I(x_spans[3], y_spans[1], z_spans[1]);

      this.x0y1z0_lower = new VectorI3I(x_spans[0], y_spans[2], z_spans[0]);
      this.x0y1z0_upper = new VectorI3I(x_spans[1], y_spans[3], z_spans[1]);

      this.x1y1z0_lower = new VectorI3I(x_spans[2], y_spans[2], z_spans[0]);
      this.x1y1z0_upper = new VectorI3I(x_spans[3], y_spans[3], z_spans[1]);

      this.x0y0z1_lower = new VectorI3I(x_spans[0], y_spans[0], z_spans[2]);
      this.x0y0z1_upper = new VectorI3I(x_spans[1], y_spans[1], z_spans[3]);

      this.x1y0z1_lower = new VectorI3I(x_spans[2], y_spans[0], z_spans[2]);
      this.x1y0z1_upper = new VectorI3I(x_spans[3], y_spans[1], z_spans[3]);

      this.x0y1z1_lower = new VectorI3I(x_spans[0], y_spans[2], z_spans[2]);
      this.x0y1z1_upper = new VectorI3I(x_spans[1], y_spans[3], z_spans[3]);

      this.x1y1z1_lower = new VectorI3I(x_spans[2], y_spans[2], z_spans[2]);
      this.x1y1z1_upper = new VectorI3I(x_spans[3], y_spans[3], z_spans[3]);
    }
  }

  private final @Nonnull Octant     root;
  private final @Nonnull TreeSet<T> objects_all;

  public OctTreeBasic(
    final int size_x,
    final int size_y,
    final int size_z)
    throws ConstraintError
  {
    Constraints.constrainRange(size_x, 2, Integer.MAX_VALUE, "X size");
    Constraints.constrainRange(size_y, 2, Integer.MAX_VALUE, "Y size");
    Constraints.constrainRange(size_z, 2, Integer.MAX_VALUE, "Z size");
    Constraints.constrainArbitrary((size_x % 2) == 0, "X size is even");
    Constraints.constrainArbitrary((size_y % 2) == 0, "Y size is even");
    Constraints.constrainArbitrary((size_z % 2) == 0, "Z size is even");

    this.objects_all = new TreeSet<T>();
    this.root =
      new Octant(new VectorI3I(0, 0, 0), new VectorI3I(
        size_x - 1,
        size_y - 1,
        size_z - 1));
  }

  @Override public void octTreeClear()
  {
    throw new UnimplementedCodeException();
  }

  @Override public int octTreeGetSizeX()
  {
    return this.root.size_x;
  }

  @Override public int octTreeGetSizeY()
  {
    return this.root.size_y;
  }

  @Override public int octTreeGetSizeZ()
  {
    return this.root.size_z;
  }

  @Override public boolean octTreeInsert(
    final @Nonnull T item)
    throws ConstraintError
  {
    Constraints.constrainNotNull(item, "Item");
    Constraints.constrainArbitrary(
      BoundingVolumeCheck.wellFormed(item),
      "Bounding volume is well-formed");

    return this.root.insert(item);
  }

  @Override public void octTreeIterateObjects(
    final @Nonnull Function<T, Boolean> f)
    throws Exception,
      ConstraintError
  {
    Constraints.constrainNotNull(f, "Function");

    for (final T object : this.objects_all) {
      final Boolean r = f.call(object);
      if (r.booleanValue() == false) {
        break;
      }
    }
  }

  @Override public void octTreeTraverse(
    final @Nonnull OctTreeTraversal traversal)
    throws Exception,
      ConstraintError
  {
    Constraints.constrainNotNull(traversal, "Traversal");
    this.root.traverse(0, traversal);
  }
}
