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

import java.util.LinkedList;
import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.io7m.jaux.Constraints;
import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.UnimplementedCodeException;
import com.io7m.jaux.functional.Function;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorReadable2I;

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
 * </ul>
 * 
 * @param <T>
 *          The type of objects contained within the tree
 */

class QuadTreeSimpleRCSimple<T extends BoundingArea> implements
  QuadTreeInterface<T>
{
  class Quadrant implements BoundingArea
  {
    private final @Nonnull VectorReadable2I lower;
    private final @Nonnull VectorReadable2I upper;
    private @CheckForNull Quadrant          x0y0;
    private @CheckForNull Quadrant          x1y0;
    private @CheckForNull Quadrant          x0y1;
    private @CheckForNull Quadrant          x1y1;
    private boolean                         leaf;
    private final @Nonnull List<T>          quadrant_objects;
    final int                               size_x;
    final int                               size_y;

    /**
     * Construct a quadrant defined by the inclusive ranges given by
     * <code>lower</code> and <code>upper</code>.
     */

    Quadrant(
      final @Nonnull VectorReadable2I lower,
      final @Nonnull VectorReadable2I upper)
    {
      this.upper = upper;
      this.lower = lower;
      this.x0y0 = null;
      this.x1y0 = null;
      this.x0y1 = null;
      this.x1y1 = null;
      this.leaf = true;
      this.quadrant_objects = new LinkedList<T>();
      this.size_x =
        QuadTreeSimpleRCSimple.getSpanSizeX(this.lower, this.upper);
      this.size_y =
        QuadTreeSimpleRCSimple.getSpanSizeY(this.lower, this.upper);
    }

    void areaContaining(
      final @Nonnull BoundingArea area,
      final @Nonnull List<T> items)
    {
      /**
       * If <code>area</code> completely contains this quadrant, collect
       * everything in this quadrant and all chidren of this quadrant.
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
        if (BoundingAreaCheck.containedWithin(area, object)) {
          items.add(object);
        }
      }

      if (this.leaf == false) {
        this.x0y0.areaContaining(area, items);
        this.x0y1.areaContaining(area, items);
        this.x1y0.areaContaining(area, items);
        this.x1y1.areaContaining(area, items);
      }
    }

    void areaOverlapping(
      final @Nonnull BoundingArea area,
      final @Nonnull List<T> items)
    {
      /**
       * If <code>area</code> overlaps this quadrant, test each object against
       * <code>area</code>.
       */

      if (BoundingAreaCheck.overlapsArea(area, this)) {
        for (final T object : this.quadrant_objects) {
          if (BoundingAreaCheck.overlapsArea(area, object)) {
            items.add(object);
          }
        }

        if (this.leaf == false) {
          this.x0y0.areaOverlapping(area, items);
          this.x1y0.areaOverlapping(area, items);
          this.x0y1.areaOverlapping(area, items);
          this.x1y1.areaOverlapping(area, items);
        }
      }
    }

    @Override public @Nonnull VectorReadable2I boundingAreaLower()
    {
      return this.lower;
    }

    @Override public @Nonnull VectorReadable2I boundingAreaUpper()
    {
      return this.upper;
    }

    private boolean canSplit()
    {
      if (this.leaf) {
        return (this.size_x >= 2) && (this.size_y >= 2);
      }
      return false;
    }

    void clear()
    {
      this.quadrant_objects.clear();
      if (this.leaf == false) {
        this.x0y0.clear();
        this.x1y0.clear();
        this.x0y1.clear();
        this.x1y1.clear();
      }
    }

    private void collectRecursive(
      final @Nonnull List<T> items)
    {
      items.addAll(this.quadrant_objects);
      if (this.leaf == false) {
        this.x0y0.collectRecursive(items);
        this.x0y1.collectRecursive(items);
        this.x1y0.collectRecursive(items);
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
      final @Nonnull T item)
    {
      return this.insertBase(item);
    }

    /**
     * Insertion base case: item may or may not fit within node.
     */

    private boolean insertBase(
      final @Nonnull T item)
    {
      if (BoundingAreaCheck.containedWithin(this, item)) {
        return this.insertStep(item);
      }
      return false;
    }

    /**
     * Insert the given object into the current quadrant's object list, and
     * also inserted into the "global" object list.
     */

    @SuppressWarnings("synthetic-access") private boolean insertObject(
      final @Nonnull T item)
    {
      QuadTreeSimpleRCSimple.this.objects_all.add(item);
      this.quadrant_objects.add(item);
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

      if (BoundingAreaCheck.containedWithin(this.x0y0, item)) {
        return this.x0y0.insertStep(item);
      }
      if (BoundingAreaCheck.containedWithin(this.x1y0, item)) {
        return this.x1y0.insertStep(item);
      }
      if (BoundingAreaCheck.containedWithin(this.x0y1, item)) {
        return this.x0y1.insertStep(item);
      }
      if (BoundingAreaCheck.containedWithin(this.x1y1, item)) {
        return this.x1y1.insertStep(item);
      }

      /**
       * Otherwise, insert the object into this node.
       */
      return this.insertObject(item);
    }

    void raycast(
      final @Nonnull RayI2D ray,
      final @Nonnull List<Quadrant> quadrants)
    {
      if (this.raycastBoxIntersects(ray)) {
        if (this.leaf) {
          quadrants.add(this);
          return;
        }

        this.x0y0.raycast(ray, quadrants);
        this.x0y1.raycast(ray, quadrants);
        this.x1y0.raycast(ray, quadrants);
        this.x1y1.raycast(ray, quadrants);
      }
    }

    /**
     * Branchless optimization of the Kay-Kajiya slab ray/AABB intersection
     * test by Tavian Barnes.
     * 
     * @see http://tavianator.com/2011/05/fast-branchless-raybounding-box-
     *      intersections/
     */

    boolean raycastBoxIntersects(
      final @Nonnull RayI2D ray)
    {
      final double x0 = this.lower.getXI();
      final double y0 = this.lower.getYI();
      final double x1 = this.upper.getXI();
      final double y1 = this.upper.getYI();

      final double tx0 = (x0 - ray.origin.x) * ray.direction_inverse.x;
      final double tx1 = (x1 - ray.origin.x) * ray.direction_inverse.x;

      double tmin = Math.min(tx0, tx1);
      double tmax = Math.max(tx0, tx1);

      final double ty0 = (y0 - ray.origin.y) * ray.direction_inverse.y;
      final double ty1 = (y1 - ray.origin.y) * ray.direction_inverse.y;

      tmin = Math.max(tmin, Math.min(ty0, ty1));
      tmax = Math.min(tmax, Math.max(ty0, ty1));

      return ((tmax >= Math.max(0, tmin)) && (tmin < Double.POSITIVE_INFINITY));
    }

    /**
     * Split this node into four quadrants.
     */

    private void split()
    {
      assert this.canSplit();

      final Quadrants q =
        QuadTreeSimpleRCSimple.splitQuadrants(this.lower, this.upper);
      this.x0y0 = new Quadrant(q.x0y0_lower, q.x0y0_upper);
      this.x0y1 = new Quadrant(q.x0y1_lower, q.x0y1_upper);
      this.x1y0 = new Quadrant(q.x1y0_lower, q.x1y0_upper);
      this.x1y1 = new Quadrant(q.x1y1_lower, q.x1y1_upper);
      this.leaf = false;
    }

    @Override public String toString()
    {
      final StringBuilder builder = new StringBuilder();
      builder.append("[Quadrant ");
      builder.append(this.lower);
      builder.append(" ");
      builder.append(this.upper);
      builder.append(" ");
      builder.append(this.leaf);
      builder.append(" ");
      builder.append(this.quadrant_objects);
      builder.append("]");
      return builder.toString();
    }

    void traverse(
      final int depth,
      final @Nonnull QuadTreeTraversal traversal)
      throws Exception
    {
      traversal.visit(depth, this.lower, this.upper);
      if (this.leaf == false) {
        this.x0y0.traverse(depth + 1, traversal);
        this.x1y0.traverse(depth + 1, traversal);
        this.x0y1.traverse(depth + 1, traversal);
        this.x1y1.traverse(depth + 1, traversal);
      }
    }
  }

  static final class Quadrants
  {
    final @Nonnull VectorM2I x0y0_lower = new VectorM2I();
    final @Nonnull VectorM2I x0y0_upper = new VectorM2I();
    final @Nonnull VectorM2I x1y0_lower = new VectorM2I();
    final @Nonnull VectorM2I x1y0_upper = new VectorM2I();
    final @Nonnull VectorM2I x0y1_lower = new VectorM2I();
    final @Nonnull VectorM2I x0y1_upper = new VectorM2I();
    final @Nonnull VectorM2I x1y1_lower = new VectorM2I();
    final @Nonnull VectorM2I x1y1_upper = new VectorM2I();
    int                      size_x     = 0;
    int                      size_y     = 0;

    Quadrants()
    {

    }
  }

  static int getSpanSizeX(
    final @Nonnull VectorReadable2I lower,
    final @Nonnull VectorReadable2I upper)
  {
    return (upper.getXI() - lower.getXI()) + 1;
  }

  static int getSpanSizeY(
    final @Nonnull VectorReadable2I lower,
    final @Nonnull VectorReadable2I upper)
  {
    return (upper.getYI() - lower.getYI()) + 1;
  }

  /**
   * Given an inclusive range defined by <code>[low .. high]</code>, split the
   * range in the middle and produce two new inclusive ranges.
   * 
   * <p>
   * The lower and upper bounds of the lower range are stored in
   * <code>out[0]</code> and <code>out[1]</code>, respectively. The lower and
   * upper bounds of the upper range are stored in <code>out[2]</code> and
   * <code>out[3]</code>, respectively.
   * </p>
   */

  static void split1D(
    final int low,
    final int high,
    final int[] out)
  {
    assert out.length == 4;

    final int size = (high - low) + 1;
    out[0] = low;
    out[1] = low + ((size >> 1) - 1);
    out[2] = low + (size >> 1);
    out[3] = high;
  }

  /**
   * Split a quadrant defined by the two points <code>lower</code> and
   * <code>upper</code> into four quadrants.
   * 
   * @throws ConstraintError
   *           Iff the quadrant is too small to split (the size of either
   *           dimension is less than 2)
   */

  static @Nonnull Quadrants splitQuadrants(
    final @Nonnull VectorReadable2I lower,
    final @Nonnull VectorReadable2I upper)
  {
    final int size_x = QuadTreeSimpleRCSimple.getSpanSizeX(lower, upper);
    final int size_y = QuadTreeSimpleRCSimple.getSpanSizeY(lower, upper);

    assert size_x >= 2;
    assert size_y >= 2;

    final Quadrants q = new Quadrants();
    q.size_x = size_x >> 1;
    q.size_y = size_y >> 1;

    final int[] spans = new int[4];

    QuadTreeSimpleRCSimple.split1D(lower.getXI(), upper.getXI(), spans);
    q.x0y0_lower.x = spans[0];
    q.x0y1_lower.x = spans[0];
    q.x0y0_upper.x = spans[1];
    q.x0y1_upper.x = spans[1];
    q.x1y0_lower.x = spans[2];
    q.x1y1_lower.x = spans[2];
    q.x1y0_upper.x = spans[3];
    q.x1y1_upper.x = spans[3];

    QuadTreeSimpleRCSimple.split1D(lower.getYI(), upper.getYI(), spans);
    q.x0y0_lower.y = spans[0];
    q.x1y0_lower.y = spans[0];
    q.x0y0_upper.y = spans[1];
    q.x1y0_upper.y = spans[1];
    q.x0y1_lower.y = spans[2];
    q.x1y1_lower.y = spans[2];
    q.x0y1_upper.y = spans[3];
    q.x1y1_upper.y = spans[3];

    return q;
  }

  private final @Nonnull Quadrant root;
  private final @Nonnull List<T>  objects_all;

  public QuadTreeSimpleRCSimple(
    final int size_x,
    final int size_y)
    throws ConstraintError
  {
    Constraints.constrainRange(size_x, 2, Integer.MAX_VALUE, "X size");
    Constraints.constrainRange(size_y, 2, Integer.MAX_VALUE, "Y size");
    Constraints.constrainArbitrary((size_x % 2) == 0, "X size is even");
    Constraints.constrainArbitrary((size_y % 2) == 0, "Y size is even");

    this.objects_all = new LinkedList<T>();
    this.root =
      new Quadrant(new VectorI2I(0, 0), new VectorI2I(size_x - 1, size_y - 1));
  }

  @Override public void quadTreeClear()
  {
    this.root.clear();
    this.objects_all.clear();
  }

  @Override public int quadTreeGetSizeX()
  {
    return this.root.size_x;
  }

  @Override public int quadTreeGetSizeY()
  {
    return this.root.size_y;
  }

  @Override public boolean quadTreeInsert(
    final @Nonnull T item)
    throws ConstraintError
  {
    Constraints.constrainNotNull(item, "Item");
    Constraints.constrainArbitrary(
      BoundingAreaCheck.wellFormed(item),
      "Bounding area is well-formed");

    return this.root.insert(item);
  }

  @Override public void quadTreeIterateObjects(
    final @Nonnull Function<T, Boolean> f)
    throws Exception,
      ConstraintError
  {
    Constraints.constrainNotNull(f, "Function");

    for (final T item : this.objects_all) {
      final Boolean next = f.call(item);
      if (next.booleanValue() == false) {
        break;
      }
    }
  }

  @Override public void quadTreeQueryAreaContaining(
    final @Nonnull BoundingArea area,
    final @Nonnull List<T> items)
    throws ConstraintError
  {
    Constraints.constrainNotNull(area, "Area");
    Constraints.constrainArbitrary(
      BoundingAreaCheck.wellFormed(area),
      "Bounding area is well-formed");
    Constraints.constrainNotNull(items, "Items");

    this.root.areaContaining(area, items);
  }

  @Override public void quadTreeQueryAreaOverlapping(
    final @Nonnull BoundingArea area,
    final @Nonnull List<T> items)
    throws ConstraintError
  {
    Constraints.constrainNotNull(area, "Area");
    Constraints.constrainArbitrary(
      BoundingAreaCheck.wellFormed(area),
      "Bounding area is well-formed");
    Constraints.constrainNotNull(items, "Items");

    this.root.areaOverlapping(area, items);
  }

  @Override public void quadTreeQueryRaycast(
    final @Nonnull RayI2D ray,
    final @Nonnull List<T> items)
    throws ConstraintError
  {
    Constraints.constrainNotNull(ray, "Ray");
    Constraints.constrainNotNull(items, "Items");

    throw new UnimplementedCodeException();
  }

  /**
   * Return the set of quadrants intersected by <code>ray</code>.
   * 
   * @param ray
   *          The ray
   * @param items
   *          The resulting set of quadrants
   * @throws ConstraintError
   *           Iff any of the following hold:
   *           <ul>
   *           <li><code>ray == null</code></li>
   *           <li><code>items == null</code></li>
   *           </ul>
   */

  void quadTreeQueryRaycastQuadrants(
    final @Nonnull RayI2D ray,
    final @Nonnull List<Quadrant> items)
    throws ConstraintError
  {
    Constraints.constrainNotNull(ray, "Ray");
    Constraints.constrainNotNull(items, "Items");

    this.root.raycast(ray, items);
  }

  @Override public void quadTreeTraverse(
    final @Nonnull QuadTreeTraversal traversal)
    throws Exception,
      ConstraintError
  {
    Constraints.constrainNotNull(traversal, "Traversal");
    this.root.traverse(0, traversal);
  }

  @SuppressWarnings("synthetic-access") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[QuadTree ");
    builder.append(this.root.lower);
    builder.append(" ");
    builder.append(this.root.upper);
    builder.append(" ");
    builder.append(this.objects_all);
    builder.append("]");
    return builder.toString();
  }
}
