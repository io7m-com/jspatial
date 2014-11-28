/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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

package com.io7m.jspatial.quadtrees;

import java.util.SortedSet;
import java.util.TreeSet;

import com.io7m.jfunctional.PartialFunctionType;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jspatial.BoundingAreaCheck;
import com.io7m.jspatial.BoundingAreaType;
import com.io7m.jspatial.Dimensions;
import com.io7m.jspatial.RayI2D;
import com.io7m.jspatial.SDType;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * <p>
 * A quadtree implement based on both {@link QuadTreeSDLimit} and
 * {@link QuadTreeSDPrune}.
 * </p>
 *
 * @param <T>
 *          The type of objects contained within the tree.
 */

@SuppressWarnings("synthetic-access") public final class QuadTreeSDPruneLimit<T extends QuadTreeMemberType<T>> implements
  QuadTreeSDType<T>
{
  final class Quadrant implements QuadrantType
  {
    private boolean                  leaf;
    private final VectorI2I          lower;
    private final @Nullable Quadrant parent;
    private final SortedSet<T>       quadrant_objects_dynamic;
    private final SortedSet<T>       quadrant_objects_static;
    private final int                quadrant_size_x;
    private final int                quadrant_size_y;
    private final VectorI2I          upper;
    private @Nullable Quadrant       x0y0;
    private @Nullable Quadrant       x0y1;
    private @Nullable Quadrant       x1y0;
    private @Nullable Quadrant       x1y1;

    /**
     * Construct a quadrant defined by the inclusive ranges given by
     * <code>lower</code> and <code>upper</code>.
     */

    Quadrant(
      final @Nullable Quadrant in_parent,
      final VectorI2I in_lower,
      final VectorI2I in_upper)
    {
      this.parent = in_parent;
      this.upper = in_upper;
      this.lower = in_lower;
      this.x0y0 = null;
      this.x1y0 = null;
      this.x0y1 = null;
      this.x1y1 = null;
      this.leaf = true;
      this.quadrant_objects_static = new TreeSet<T>();
      this.quadrant_objects_dynamic = new TreeSet<T>();
      this.quadrant_size_x = Dimensions.getSpanSizeX(this.lower, this.upper);
      this.quadrant_size_y = Dimensions.getSpanSizeY(this.lower, this.upper);
    }

    void areaContaining(
      final BoundingAreaType area,
      final SortedSet<T> items)
    {
      /**
       * If <code>area</code> completely contains this quadrant, collect
       * everything in this quadrant and all children of this quadrant.
       */

      if (BoundingAreaCheck.containedWithin(area, this)) {
        this.collectRecursive(items);
        return;
      }

      /**
       * Otherwise, <code>area</code> may be overlapping this quadrant and
       * therefore some items may still be contained within <code>area</code>.
       */

      for (final T object : this.quadrant_objects_static) {
        assert object != null;
        if (BoundingAreaCheck.containedWithin(area, object)) {
          items.add(object);
        }
      }
      for (final T object : this.quadrant_objects_dynamic) {
        assert object != null;
        if (BoundingAreaCheck.containedWithin(area, object)) {
          items.add(object);
        }
      }

      if (this.leaf == false) {
        assert this.x0y0 != null;
        this.x0y0.areaContaining(area, items);
        assert this.x0y1 != null;
        this.x0y1.areaContaining(area, items);
        assert this.x1y0 != null;
        this.x1y0.areaContaining(area, items);
        assert this.x1y1 != null;
        this.x1y1.areaContaining(area, items);
      }
    }

    void areaOverlapping(
      final BoundingAreaType area,
      final SortedSet<T> items)
    {
      /**
       * If <code>area</code> overlaps this quadrant, test each object against
       * <code>area</code>.
       */

      if (BoundingAreaCheck.overlapsArea(area, this)) {
        for (final T object : this.quadrant_objects_static) {
          assert object != null;
          if (BoundingAreaCheck.overlapsArea(area, object)) {
            items.add(object);
          }
        }
        for (final T object : this.quadrant_objects_dynamic) {
          assert object != null;
          if (BoundingAreaCheck.overlapsArea(area, object)) {
            items.add(object);
          }
        }

        if (this.leaf == false) {
          assert this.x0y0 != null;
          this.x0y0.areaOverlapping(area, items);
          assert this.x1y0 != null;
          this.x1y0.areaOverlapping(area, items);
          assert this.x0y1 != null;
          this.x0y1.areaOverlapping(area, items);
          assert this.x1y1 != null;
          this.x1y1.areaOverlapping(area, items);
        }
      }
    }

    @Override public VectorReadable2IType boundingAreaLower()
    {
      return this.lower;
    }

    @Override public VectorReadable2IType boundingAreaUpper()
    {
      return this.upper;
    }

    private boolean canSplit()
    {
      final VectorI2I min = QuadTreeSDPruneLimit.this.minimum_size;
      return (this.quadrant_size_x >= (min.getXI() << 1))
        && (this.quadrant_size_y >= (min.getYI() << 1));
    }

    void clear()
    {
      this.quadrant_objects_static.clear();
      this.quadrant_objects_dynamic.clear();
      if (this.leaf == false) {
        assert this.x0y0 != null;
        this.x0y0.clear();
        assert this.x1y0 != null;
        this.x1y0.clear();
        assert this.x0y1 != null;
        this.x0y1.clear();
        assert this.x1y1 != null;
        this.x1y1.clear();
      }
    }

    void clearDynamic()
    {
      this.quadrant_objects_dynamic.clear();
      if (this.leaf == false) {
        assert this.x0y0 != null;
        this.x0y0.clearDynamic();
        assert this.x1y0 != null;
        this.x1y0.clearDynamic();
        assert this.x0y1 != null;
        this.x0y1.clearDynamic();
        assert this.x1y1 != null;
        this.x1y1.clearDynamic();
      }
    }

    private void collectRecursive(
      final SortedSet<T> items)
    {
      items.addAll(this.quadrant_objects_static);
      items.addAll(this.quadrant_objects_dynamic);
      if (this.leaf == false) {
        assert this.x0y0 != null;
        this.x0y0.collectRecursive(items);
        assert this.x0y1 != null;
        this.x0y1.collectRecursive(items);
        assert this.x1y0 != null;
        this.x1y0.collectRecursive(items);
        assert this.x1y1 != null;
        this.x1y1.collectRecursive(items);
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
      final T item,
      final SDType type)
    {
      return this.insertBase(item, type);
    }

    /**
     * Insertion base case: item may or may not fit within node.
     */

    private boolean insertBase(
      final T item,
      final SDType type)
    {
      if (QuadTreeSDPruneLimit.this.objects_all_static.contains(item)) {
        return false;
      }
      if (QuadTreeSDPruneLimit.this.objects_all_dynamic.contains(item)) {
        return false;
      }
      if (BoundingAreaCheck.containedWithin(this, item)) {
        return this.insertStep(item, type);
      }
      return false;
    }

    /**
     * Insert the given object into the current quadrant's object list, and
     * also inserted into the "global" object list.
     */

    private boolean insertObject(
      final T item,
      final SDType type)
    {
      switch (type) {
        case SD_DYNAMIC:
        {
          QuadTreeSDPruneLimit.this.objects_all_dynamic.add(item);
          this.quadrant_objects_dynamic.add(item);
          return true;
        }
        case SD_STATIC:
        {
          QuadTreeSDPruneLimit.this.objects_all_static.add(item);
          this.quadrant_objects_static.add(item);
          return true;
        }
      }

      throw new UnreachableCodeException();
    }

    /**
     * Insertion inductive case: item fits within node, but may fit more
     * precisely within a child node.
     */

    private boolean insertStep(
      final T item,
      final SDType type)
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

          return this.insertObject(item, type);
        }
      }

      /**
       * See if the object will fit in any of the child nodes.
       */

      assert this.leaf == false;
      assert this.x0y0 != null;
      if (BoundingAreaCheck.containedWithin(this.x0y0, item)) {
        assert this.x0y0 != null;
        return this.x0y0.insertStep(item, type);
      }
      assert this.x1y0 != null;
      if (BoundingAreaCheck.containedWithin(this.x1y0, item)) {
        assert this.x1y0 != null;
        return this.x1y0.insertStep(item, type);
      }
      assert this.x0y1 != null;
      if (BoundingAreaCheck.containedWithin(this.x0y1, item)) {
        assert this.x0y1 != null;
        return this.x0y1.insertStep(item, type);
      }
      assert this.x1y1 != null;
      if (BoundingAreaCheck.containedWithin(this.x1y1, item)) {
        assert this.x1y1 != null;
        return this.x1y1.insertStep(item, type);
      }

      /**
       * Otherwise, insert the object into this node.
       */
      return this.insertObject(item, type);
    }

    void raycast(
      final RayI2D ray,
      final SortedSet<QuadTreeRaycastResult<T>> items)
    {
      if (BoundingAreaCheck.rayBoxIntersects(
        ray,
        this.lower.getXI(),
        this.lower.getYI(),
        this.upper.getXI(),
        this.upper.getYI())) {

        this.raycastCheckObjects(ray, this.quadrant_objects_static, items);
        this.raycastCheckObjects(ray, this.quadrant_objects_dynamic, items);

        if (this.leaf == false) {
          assert this.x0y0 != null;
          this.x0y0.raycast(ray, items);
          assert this.x0y1 != null;
          this.x0y1.raycast(ray, items);
          assert this.x1y0 != null;
          this.x1y0.raycast(ray, items);
          assert this.x1y1 != null;
          this.x1y1.raycast(ray, items);
        }
      }
    }

    void raycastCheckObjects(
      final RayI2D ray,
      final SortedSet<T> objects,
      final SortedSet<QuadTreeRaycastResult<T>> items)
    {
      for (final T object : objects) {
        final VectorReadable2IType object_lower = object.boundingAreaLower();
        final VectorReadable2IType object_upper = object.boundingAreaUpper();

        if (BoundingAreaCheck.rayBoxIntersects(
          ray,
          object_lower.getXI(),
          object_lower.getYI(),
          object_upper.getXI(),
          object_upper.getYI())) {

          final QuadTreeRaycastResult<T> r =
            new QuadTreeRaycastResult<T>(object, VectorI2D.distance(
              new VectorI2D(object_lower.getXI(), object_lower.getYI()),
              ray.getOrigin()));
          items.add(r);
        }
      }
    }

    void raycastQuadrants(
      final RayI2D ray,
      final SortedSet<QuadTreeRaycastResult<QuadrantType>> quadrants)
    {
      if (BoundingAreaCheck.rayBoxIntersects(
        ray,
        this.lower.getXI(),
        this.lower.getYI(),
        this.upper.getXI(),
        this.upper.getYI())) {

        if (this.leaf) {
          final QuadTreeRaycastResult<QuadrantType> r =
            new QuadTreeRaycastResult<QuadrantType>(this, VectorI2D.distance(
              new VectorI2D(this.lower.getXI(), this.lower.getYI()),
              ray.getOrigin()));
          quadrants.add(r);
          return;
        }

        assert this.x0y0 != null;
        this.x0y0.raycastQuadrants(ray, quadrants);
        assert this.x0y1 != null;
        this.x0y1.raycastQuadrants(ray, quadrants);
        assert this.x1y0 != null;
        this.x1y0.raycastQuadrants(ray, quadrants);
        assert this.x1y1 != null;
        this.x1y1.raycastQuadrants(ray, quadrants);
      }
    }

    boolean remove(
      final T item)
    {
      if ((QuadTreeSDPruneLimit.this.objects_all_dynamic.contains(item) == false)
        && (QuadTreeSDPruneLimit.this.objects_all_static.contains(item) == false)) {
        return false;
      }

      /**
       * If an object is in objects_all, then it must be within the bounds of
       * the tree, according to insert().
       */
      assert BoundingAreaCheck.containedWithin(this, item);
      return this.removeStep(item);
    }

    private boolean removeStep(
      final T item)
    {
      if (this.quadrant_objects_dynamic.contains(item)) {
        this.quadrant_objects_dynamic.remove(item);
        QuadTreeSDPruneLimit.this.objects_all_dynamic.remove(item);
        this.unsplitAttemptRecursive();
        return true;
      }

      if (this.quadrant_objects_static.contains(item)) {
        this.quadrant_objects_static.remove(item);
        QuadTreeSDPruneLimit.this.objects_all_static.remove(item);
        this.unsplitAttemptRecursive();
        return true;
      }

      this.unsplitAttemptRecursive();

      if (this.leaf == false) {
        assert this.x0y0 != null;
        if (BoundingAreaCheck.containedWithin(this.x0y0, item)) {
          assert this.x0y0 != null;
          return this.x0y0.removeStep(item);
        }
        assert this.x1y0 != null;
        if (BoundingAreaCheck.containedWithin(this.x1y0, item)) {
          assert this.x1y0 != null;
          return this.x1y0.removeStep(item);
        }
        assert this.x0y1 != null;
        if (BoundingAreaCheck.containedWithin(this.x0y1, item)) {
          assert this.x0y1 != null;
          return this.x0y1.removeStep(item);
        }
        assert this.x1y1 != null;
        if (BoundingAreaCheck.containedWithin(this.x1y1, item)) {
          assert this.x1y1 != null;
          return this.x1y1.removeStep(item);
        }
      }

      /**
       * The object must be in the tree, according to remove(). Therefore it
       * must be in this node, or one of the child quadrants.
       */
      throw new UnreachableCodeException();
    }

    /**
     * Split this node into four quadrants.
     */

    private void split()
    {
      assert this.canSplit();

      final Quadrants q = Quadrants.split(this.lower, this.upper);
      this.x0y0 = new Quadrant(this, q.getX0Y0Lower(), q.getX0Y0Upper());
      this.x0y1 = new Quadrant(this, q.getX0Y1Lower(), q.getX0Y1Upper());
      this.x1y0 = new Quadrant(this, q.getX1Y0Lower(), q.getX1Y0Upper());
      this.x1y1 = new Quadrant(this, q.getX1Y1Lower(), q.getX1Y1Upper());
      this.leaf = false;
    }

    <E extends Throwable> void traverse(
      final int depth,
      final QuadTreeTraversalType<E> traversal)
      throws E
    {
      traversal.visit(depth, this.lower, this.upper);
      if (this.leaf == false) {
        assert this.x0y0 != null;
        this.x0y0.traverse(depth + 1, traversal);
        assert this.x1y0 != null;
        this.x1y0.traverse(depth + 1, traversal);
        assert this.x0y1 != null;
        this.x0y1.traverse(depth + 1, traversal);
        assert this.x1y1 != null;
        this.x1y1.traverse(depth + 1, traversal);
      }
    }

    private void unsplitAttempt()
    {
      if (this.leaf == false) {
        boolean prunable = true;
        assert this.x0y0 != null;
        prunable &= this.x0y0.unsplitCanPrune();
        assert this.x1y0 != null;
        prunable &= this.x1y0.unsplitCanPrune();
        assert this.x0y1 != null;
        prunable &= this.x0y1.unsplitCanPrune();
        assert this.x1y1 != null;
        prunable &= this.x1y1.unsplitCanPrune();

        if (prunable) {
          this.leaf = true;
          this.x0y0 = null;
          this.x0y1 = null;
          this.x1y0 = null;
          this.x1y1 = null;
        }
      }
    }

    /**
     * Attempt to turn this node and as many ancestors if this node back into
     * leaves as possible.
     */

    private void unsplitAttemptRecursive()
    {
      this.unsplitAttempt();
      if (this.parent != null) {
        this.parent.unsplitAttemptRecursive();
      }
    }

    private boolean unsplitCanPrune()
    {
      return (this.leaf == true)
        && this.quadrant_objects_dynamic.isEmpty()
        && this.quadrant_objects_static.isEmpty();
    }
  }

  /**
   * Construct a new quadtree with the given size and position.
   *
   * @param size
   *          The size.
   * @param position
   *          The position.
   * @param size_minimum
   *          The minimum quadrant size.
   *
   * @return A new quadtree.
   * @param <T>
   *          The precise type of quadtree members.
   */

  public static
    <T extends QuadTreeMemberType<T>>
    QuadTreeSDType<T>
    newQuadTree(
      final VectorReadable2IType size,
      final VectorReadable2IType position,
      final VectorReadable2IType size_minimum)
  {
    return new QuadTreeSDPruneLimit<T>(position, size, size_minimum);
  }

  private final VectorI2I    minimum_size;
  private final SortedSet<T> objects_all_dynamic;
  private final SortedSet<T> objects_all_static;
  private final VectorI2I    position;
  private final Quadrant     root;
  private final VectorI2I    size;

  @SuppressWarnings("boxing") private QuadTreeSDPruneLimit(
    final VectorReadable2IType in_position,
    final VectorReadable2IType in_size,
    final VectorReadable2IType in_size_minimum)
  {
    NullCheck.notNull(in_position, "Position");
    NullCheck.notNull(in_size, "Size");
    NullCheck.notNull(in_size_minimum, "Minimum size");

    QuadTreeChecks.checkSize(
      "Quadtree size",
      in_size.getXI(),
      in_size.getYI());
    QuadTreeChecks.checkSize(
      "Minimum quadrant size",
      in_size_minimum.getXI(),
      in_size_minimum.getYI());

    if (in_size_minimum.getXI() > in_size.getXI()) {
      final String s =
        String
          .format(
            "Minimum quadrant width (%d) is greater than the quadtree width (%d)",
            in_size_minimum.getXI(),
            in_size.getXI());
      throw new IllegalArgumentException(s);
    }
    if (in_size_minimum.getYI() > in_size.getYI()) {
      final String s =
        String
          .format(
            "Minimum quadrant height (%d) is greater than the quadtree height (%d)",
            in_size_minimum.getYI(),
            in_size.getYI());
      throw new IllegalArgumentException(s);
    }

    this.objects_all_static = new TreeSet<T>();
    this.objects_all_dynamic = new TreeSet<T>();
    this.position = new VectorI2I(in_position);
    this.minimum_size = new VectorI2I(in_size_minimum);
    this.size = new VectorI2I(in_size);

    final VectorI2I lower = new VectorI2I(this.position);
    final VectorI2I upper =
      new VectorI2I(
        (this.position.getXI() + this.size.getXI()) - 1,
        (this.position.getYI() + this.size.getYI()) - 1);
    this.root = new Quadrant(null, lower, upper);
  }

  @Override public void quadTreeClear()
  {
    this.root.clear();
    this.objects_all_static.clear();
    this.objects_all_dynamic.clear();
  }

  @Override public int quadTreeGetPositionX()
  {
    return this.root.lower.getXI();
  }

  @Override public int quadTreeGetPositionY()
  {
    return this.root.lower.getYI();
  }

  @Override public int quadTreeGetSizeX()
  {
    return this.root.quadrant_size_x;
  }

  @Override public int quadTreeGetSizeY()
  {
    return this.root.quadrant_size_y;
  }

  @Override public boolean quadTreeInsert(
    final T item)
  {
    BoundingAreaCheck.checkWellFormed(item);
    return this.root.insert(item, SDType.SD_DYNAMIC);
  }

  @Override public boolean quadTreeInsertSD(
    final T item,
    final SDType type)
  {
    BoundingAreaCheck.checkWellFormed(item);
    NullCheck.notNull(type, "Type");
    return this.root.insert(item, type);
  }

  @Override public <E extends Throwable> void quadTreeIterateObjects(
    final PartialFunctionType<T, Boolean, E> f)
    throws E
  {
    NullCheck.notNull(f, "Function");

    for (final T item : this.objects_all_static) {
      assert item != null;
      final Boolean next = f.call(item);
      if (next.booleanValue() == false) {
        break;
      }
    }

    for (final T item : this.objects_all_dynamic) {
      assert item != null;
      final Boolean next = f.call(item);
      if (next.booleanValue() == false) {
        break;
      }
    }
  }

  @Override public void quadTreeQueryAreaContaining(
    final BoundingAreaType area,
    final SortedSet<T> items)
  {
    BoundingAreaCheck.checkWellFormed(area);
    NullCheck.notNullAll(items, "Items");
    this.root.areaContaining(area, items);
  }

  @Override public void quadTreeQueryAreaOverlapping(
    final BoundingAreaType area,
    final SortedSet<T> items)
  {
    BoundingAreaCheck.checkWellFormed(area);
    NullCheck.notNullAll(items, "Items");
    this.root.areaOverlapping(area, items);
  }

  @Override public void quadTreeQueryRaycast(
    final RayI2D ray,
    final SortedSet<QuadTreeRaycastResult<T>> items)
  {
    NullCheck.notNull(ray, "Ray");
    NullCheck.notNullAll(items, "Items");
    this.root.raycast(ray, items);
  }

  @Override public void quadTreeQueryRaycastQuadrants(
    final RayI2D ray,
    final SortedSet<QuadTreeRaycastResult<QuadrantType>> items)
  {
    NullCheck.notNull(ray, "Ray");
    NullCheck.notNull(items, "Items");
    this.root.raycastQuadrants(ray, items);
  }

  @Override public boolean quadTreeRemove(
    final T item)
  {
    BoundingAreaCheck.checkWellFormed(item);
    return this.root.remove(item);
  }

  @Override public void quadTreeSDClearDynamic()
  {
    this.root.clearDynamic();
    this.objects_all_dynamic.clear();
  }

  @Override public <E extends Throwable> void quadTreeTraverse(
    final QuadTreeTraversalType<E> traversal)
    throws E
  {
    NullCheck.notNull(traversal, "Traversal");
    this.root.traverse(0, traversal);
  }

  @Override public String toString()
  {
    final StringBuilder b = new StringBuilder();
    b.append("[QuadTree ");
    b.append(this.root.lower);
    b.append(" ");
    b.append(this.root.upper);
    b.append(" ");
    b.append(this.objects_all_static);
    b.append(" ");
    b.append(this.objects_all_dynamic);
    b.append("]");
    final String r = b.toString();
    assert r != null;
    return r;
  }
}
