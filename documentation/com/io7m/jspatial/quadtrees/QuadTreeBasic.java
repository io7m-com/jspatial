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
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * <p>
 * An extremely simple quadtree implementation. This implementation emphasizes
 * simplicity and correctness over performance, to serve as a base for an
 * understanding of the other implementations.
 * </p>
 * <p>
 * The tree has the following properties (or lack thereof):
 * </p>
 * <ul>
 * <li>No attempt is made to distinguish between static and movable objects.
 * In most games and other simulations, objects are inserted into an empty
 * quadtree once per frame. If most of those objects do not move (such as
 * static geometry in the game/simulation world), then they will always be
 * inserted into the same point in the tree and re-inserting them every frame
 * is wasteful and/or redundant. An obvious optimization, therefore, is to
 * distinguish between static and movable objects in the tree and to provide
 * support for removing just the movable objects in one efficient operation.</li>
 * <li>
 * As various operations can potentially traverse the entire tree, it is
 * desirable to keep the number of nodes (quadrants) in the tree to a minimum.
 * This implementation does not allow the programmer to specify a minimum size
 * for quadrants and does not attempt to remove redundant nodes when an object
 * is removed from the tree.</li>
 * <li>
 * Each child quadrant of a given quadrant is exactly half the width and
 * height of the parent.</li>
 * <li>
 * Values of type <code>QuadTreeBasic</code> cannot be safely accessed from
 * multiple threads without explicit synchronization.</li>
 * </ul>
 *
 * @param <T>
 *          The type of objects contained within the tree.
 */

@SuppressWarnings("synthetic-access") public final class QuadTreeBasic<T extends QuadTreeMemberType<T>> implements
QuadTreeType<T>
{
  final class Quadrant implements QuadrantType
  {
    private boolean            leaf;
    private final VectorI2I    lower;
    private final SortedSet<T> quadrant_objects;
    private final int          quadrant_size_x;
    private final int          quadrant_size_y;
    private final VectorI2I    upper;

    private @Nullable Quadrant x0y0;
    private @Nullable Quadrant x0y1;
    private @Nullable Quadrant x1y0;
    private @Nullable Quadrant x1y1;

    /**
     * Construct a quadrant defined by the inclusive ranges given by
     * <code>in_lower</code> and <code>in_upper</code>.
     */

    Quadrant(
      final VectorI2I in_lower,
      final VectorI2I in_upper)
      {
      this.upper = in_upper;
      this.lower = in_lower;
      this.x0y0 = null;
      this.x1y0 = null;
      this.x0y1 = null;
      this.x1y1 = null;
      this.leaf = true;
      this.quadrant_objects = new TreeSet<T>();
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

      for (final T object : this.quadrant_objects) {
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
        for (final T object : this.quadrant_objects) {
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
      return (this.quadrant_size_x >= 2) && (this.quadrant_size_y >= 2);
    }

    void clear()
    {
      this.quadrant_objects.clear();
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

    private void collectRecursive(
      final SortedSet<T> items)
    {
      items.addAll(this.quadrant_objects);
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
      final T item)
    {
      return this.insertBase(item);
    }

    /**
     * Insertion base case: item may or may not fit within node.
     */

    private boolean insertBase(
      final T item)
    {
      if (QuadTreeBasic.this.objects_all.contains(item)) {
        return false;
      }
      if (BoundingAreaCheck.containedWithin(this, item)) {
        return this.insertStep(item);
      }
      return false;
    }

    /**
     * Insert the given object into the current quadrant's object list, and
     * also inserted into the "global" object list.
     */

    private boolean insertObject(
      final T item)
    {
      QuadTreeBasic.this.objects_all.add(item);
      this.quadrant_objects.add(item);
      return true;
    }

    /**
     * Insertion inductive case: item fits within node, but may fit more
     * precisely within a child node.
     */

    private boolean insertStep(
      final T item)
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
      assert this.x0y0 != null;
      if (BoundingAreaCheck.containedWithin(this.x0y0, item)) {
        assert this.x0y0 != null;
        return this.x0y0.insertStep(item);
      }
      assert this.x1y0 != null;
      if (BoundingAreaCheck.containedWithin(this.x1y0, item)) {
        assert this.x1y0 != null;
        return this.x1y0.insertStep(item);
      }
      assert this.x0y1 != null;
      if (BoundingAreaCheck.containedWithin(this.x0y1, item)) {
        assert this.x0y1 != null;
        return this.x0y1.insertStep(item);
      }
      assert this.x1y1 != null;
      if (BoundingAreaCheck.containedWithin(this.x1y1, item)) {
        assert this.x1y1 != null;
        return this.x1y1.insertStep(item);
      }

      /**
       * Otherwise, insert the object into this node.
       */
      return this.insertObject(item);
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

        for (final T object : this.quadrant_objects) {
          final VectorReadable2IType object_lower =
            object.boundingAreaLower();
          final VectorReadable2IType object_upper =
            object.boundingAreaUpper();

          if (BoundingAreaCheck.rayBoxIntersects(
            ray,
            object_lower.getXI(),
            object_lower.getYI(),
            object_upper.getXI(),
            object_upper.getYI())) {

            final VectorI2D lower_d =
              new VectorI2D(object_lower.getXI(), object_lower.getYI());
            final QuadTreeRaycastResult<T> r =
              new QuadTreeRaycastResult<T>(object, VectorI2D.distance(
                lower_d,
                ray.getOrigin()));
            items.add(r);
          }
        }

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
          final VectorI2D lower_d =
            new VectorI2D(this.lower.getXI(), this.lower.getYI());
          final QuadTreeRaycastResult<QuadrantType> r =
            new QuadTreeRaycastResult<QuadrantType>(this, VectorI2D.distance(
              lower_d,
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
      if (QuadTreeBasic.this.objects_all.contains(item) == false) {
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
      if (this.quadrant_objects.contains(item)) {
        this.quadrant_objects.remove(item);
        QuadTreeBasic.this.objects_all.remove(item);
        return true;
      }

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
      this.x0y0 = new Quadrant(q.getX0Y0Lower(), q.getX0Y0Upper());
      this.x0y1 = new Quadrant(q.getX0Y1Lower(), q.getX0Y1Upper());
      this.x1y0 = new Quadrant(q.getX1Y0Lower(), q.getX1Y0Upper());
      this.x1y1 = new Quadrant(q.getX1Y1Lower(), q.getX1Y1Upper());
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
  }

  /**
   * Construct a new quadtree with the given size and position.
   *
   * @param size
   *          The size.
   * @param position
   *          The position.
   * @return A new quadtree.
   * @param <T>
   *          The precise type of quadtree members.
   */

  public static
  <T extends QuadTreeMemberType<T>>
  QuadTreeType<T>
  newQuadTree(
    final VectorReadable2IType size,
    final VectorReadable2IType position)
    {
    return new QuadTreeBasic<T>(position, size);
    }

  private final SortedSet<T> objects_all;
  private final Quadrant     root;

  private QuadTreeBasic(
    final VectorReadable2IType position,
    final VectorReadable2IType size)
  {
    NullCheck.notNull(position, "Position");
    NullCheck.notNull(size, "Size");
    QuadTreeChecks.checkSize("Quadtree size", size.getXI(), size.getYI());

    this.objects_all = new TreeSet<T>();

    final VectorI2I lower = new VectorI2I(position);
    final VectorI2I upper =
      new VectorI2I(
        (position.getXI() + size.getXI()) - 1,
        (position.getYI() + size.getYI()) - 1);
    this.root = new Quadrant(lower, upper);
  }

  @Override public void quadTreeClear()
  {
    this.root.clear();
    this.objects_all.clear();
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
    return this.root.insert(item);
  }

  @Override public <E extends Throwable> void quadTreeIterateObjects(
    final PartialFunctionType<T, Boolean, E> f)
      throws E
  {
    NullCheck.notNull(f, "Function");

    for (final T item : this.objects_all) {
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
    b.append(this.objects_all);
    b.append("]");
    final String r = b.toString();
    assert r != null;
    return r;
  }
}
