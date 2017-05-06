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

package com.io7m.jspatial.implementation;

import com.io7m.jaffirm.core.Invariants;
import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jspatial.api.Ray2D;
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import com.io7m.jspatial.api.quadtrees.QuadTreeLType;
import com.io7m.jspatial.api.quadtrees.QuadTreeQuadrantIterationLType;
import com.io7m.jspatial.api.quadtrees.QuadTreeQuadrantLType;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultL;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2L;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors2D;
import com.io7m.junreachable.UnreachableCodeException;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMaps;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiFunction;

/**
 * Default implementation of the {@link QuadTreeLType} interface.
 *
 * @param <T> The precise type of tree objects
 */

public final class QuadTreeL<T> implements QuadTreeLType<T>
{
  private final Reference2ReferenceOpenHashMap<T, BoundingAreaL> objects;
  private final QuadTreeConfigurationL config;
  private Quadrant root;

  private QuadTreeL(final QuadTreeConfigurationL in_config)
  {
    this.config = NullCheck.notNull(in_config, "Configuration");
    this.root = new Quadrant(null, in_config.area());
    this.objects = new Reference2ReferenceOpenHashMap<>();
  }

  /**
   * Create a new empty tree with the given bounds.
   *
   * @param config The tree configuration
   * @param <T>    The type of objects contained within the tree
   *
   * @return A new tree
   */

  public static <T> QuadTreeLType<T> create(
    final QuadTreeConfigurationL config)
  {
    return new QuadTreeL<>(config);
  }

  @Override
  public void trim()
  {
    this.root.trim();
  }

  @Override
  public long size()
  {
    return (long) this.objects.size();
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    final QuadTreeL<?> that = (QuadTreeL<?>) o;
    return this.objects.equals(that.objects);
  }

  @Override
  public int hashCode()
  {
    return this.objects.hashCode();
  }

  @Override
  public BoundingAreaL bounds()
  {
    return this.root.area;
  }

  @Override
  public boolean insert(
    final T item,
    final BoundingAreaL item_bounds)
  {
    NullCheck.notNull(item, "Item");
    NullCheck.notNull(item_bounds, "Bounds");

    if (this.objects.containsKey(item)) {
      this.remove(item);
      Invariants.checkInvariant(
        item,
        !this.objects.containsKey(item),
        x -> "Item must not be in tree");
    }

    return this.root.insert(item, item_bounds);
  }

  @Override
  public boolean contains(final T item)
  {
    return this.objects.containsKey(item);
  }

  @Override
  public boolean remove(final T item)
  {
    NullCheck.notNull(item, "Item");
    return this.root.remove(item);
  }

  @Override
  public void clear()
  {
    this.root = new Quadrant(null, this.root.area);
    this.objects.clear();
  }

  @Override
  public <U> QuadTreeLType<U> map(final BiFunction<T, BoundingAreaL, U> f)
  {
    NullCheck.notNull(f, "Function");

    final QuadTreeLType<U> qt = new QuadTreeL<>(this.config);
    for (final Map.Entry<T, BoundingAreaL> es : this.objects.entrySet()) {
      final T item = es.getKey();
      final BoundingAreaL item_area = es.getValue();
      qt.insert(f.apply(item, item_area), es.getValue());
    }
    return qt;
  }

  @Override
  public <C> void iterateQuadrants(
    final C context,
    final QuadTreeQuadrantIterationLType<T, C> f)
  {
    NullCheck.notNull(context, "Context");
    NullCheck.notNull(f, "Function");
    this.root.iterateQuadrants(context, f, 0L);
  }

  @Override
  public BoundingAreaL areaFor(final T item)
  {
    NullCheck.notNull(item, "Item");

    return this.objects.computeIfAbsent(item, i -> {
      throw new NoSuchElementException(i.toString());
    });
  }

  @Override
  public void containedBy(
    final BoundingAreaL area,
    final Set<T> items)
  {
    NullCheck.notNull(area, "Area");
    NullCheck.notNull(items, "Items");
    this.root.areaContaining(area, items);
  }

  @Override
  public void overlappedBy(
    final BoundingAreaL area,
    final Set<T> items)
  {
    NullCheck.notNull(area, "Area");
    NullCheck.notNull(items, "Items");
    this.root.areaOverlapping(area, items);
  }

  @Override
  public void raycast(
    final Ray2D ray,
    final SortedSet<QuadTreeRaycastResultL<T>> items)
  {
    NullCheck.notNull(ray, "Ray");
    NullCheck.notNull(items, "Items");
    this.root.raycast(ray, items);
  }

  protected final class Quadrant implements QuadTreeQuadrantLType<T>
  {
    private final BoundingAreaL area;
    private final Reference2ReferenceOpenHashMap<T, BoundingAreaL> quadrant_objects;
    private final @Nullable Quadrant parent;
    private @Nullable Quadrant x0y0;
    private @Nullable Quadrant x0y1;
    private @Nullable Quadrant x1y0;
    private @Nullable Quadrant x1y1;
    private Map<T, BoundingAreaL> quadrant_objects_view;

    protected Quadrant(
      final @Nullable Quadrant in_parent,
      final BoundingAreaL in_area)
    {
      this.parent = in_parent;
      this.area = NullCheck.notNull(in_area, "Area");
      this.quadrant_objects = new Reference2ReferenceOpenHashMap<>();
      this.quadrant_objects_view =
        Reference2ReferenceMaps.unmodifiable(this.quadrant_objects);
    }

    private boolean insert(
      final T item,
      final BoundingAreaL item_bounds)
    {
      Preconditions.checkPrecondition(
        item,
        !QuadTreeL.this.objects.containsKey(item),
        x -> "Object must not be in tree");

      return this.area.contains(item_bounds)
        && this.insertStep(item, item_bounds);
    }

    private boolean insertStep(
      final T item,
      final BoundingAreaL item_bounds)
    {
      /*
       * The object can fit in this node, but perhaps it is possible to fit it
       * more precisely within one of the child nodes.
       */

      /*
       * If this node is a leaf, and is large enough to split, do so.
       */

      if (this.isLeaf()) {
        if (this.canSplit()) {
          this.split();
        } else {

          /*
           * The node is a leaf, but cannot be split further. Insert directly.
           */

          return this.insertObject(item, item_bounds);
        }
      }

      /*
       * See if the object will fit in any of the child nodes.
       */

      Invariants.checkInvariant(!this.isLeaf(), "Node is not a leaf");

      if (this.x0y0.area.contains(item_bounds)) {
        return this.x0y0.insertStep(item, item_bounds);
      }
      if (this.x1y0.area.contains(item_bounds)) {
        return this.x1y0.insertStep(item, item_bounds);
      }
      if (this.x0y1.area.contains(item_bounds)) {
        return this.x0y1.insertStep(item, item_bounds);
      }
      if (this.x1y1.area.contains(item_bounds)) {
        return this.x1y1.insertStep(item, item_bounds);
      }

      /*
       * Otherwise, insert the object into this node.
       */

      return this.insertObject(item, item_bounds);
    }

    private boolean insertObject(
      final T item,
      final BoundingAreaL item_bounds)
    {
      QuadTreeL.this.objects.put(item, item_bounds);
      this.quadrant_objects.put(item, item_bounds);
      return true;
    }

    private void split()
    {
      Preconditions.checkPrecondition(this.canSplit(), "Quadrant can split");

      final Optional<QuadrantsL> q_opt = QuadrantsL.subdivide(this.area);
      if (q_opt.isPresent()) {
        final QuadrantsL q = q_opt.get();
        this.x0y0 = new Quadrant(this, q.x0y0());
        this.x0y1 = new Quadrant(this, q.x0y1());
        this.x1y0 = new Quadrant(this, q.x1y0());
        this.x1y1 = new Quadrant(this, q.x1y1());
      } else {
        throw new UnreachableCodeException();
      }
    }

    private boolean canSplit()
    {
      final long width = this.area.width();
      final long height = this.area.height();

      final long min_width =
        Math.max(2L, QuadTreeL.this.config.minimumQuadrantWidth());
      final long min_height =
        Math.max(2L, QuadTreeL.this.config.minimumQuadrantHeight());

      final long half_width = width / 2L;
      final long half_height = height / 2L;

      return half_width >= min_width && half_height >= min_height;
    }

    private boolean isLeaf()
    {
      return this.x0y0 == null;
    }

    private boolean remove(final T item)
    {
      if (!QuadTreeL.this.objects.containsKey(item)) {
        return false;
      }

      final BoundingAreaL bounds = QuadTreeL.this.objects.get(item);
      return this.removeStep(item, bounds);
    }

    private boolean removeStep(
      final T item,
      final BoundingAreaL item_bounds)
    {
      if (this.quadrant_objects.containsKey(item)) {
        this.quadrant_objects.remove(item);
        QuadTreeL.this.objects.remove(item);
        if (QuadTreeL.this.config.trimOnRemove()) {
          this.unsplitAttemptRecursive();
        }
        return true;
      }

      Invariants.checkInvariant(!this.isLeaf(), "Node cannot be a leaf");

      if (this.x0y0.area.contains(item_bounds)) {
        return this.x0y0.removeStep(item, item_bounds);
      }
      if (this.x1y0.area.contains(item_bounds)) {
        return this.x1y0.removeStep(item, item_bounds);
      }
      if (this.x0y1.area.contains(item_bounds)) {
        return this.x0y1.removeStep(item, item_bounds);
      }
      if (this.x1y1.area.contains(item_bounds)) {
        return this.x1y1.removeStep(item, item_bounds);
      }

      /*
       * The object must be in the tree, according to remove(). Therefore it
       * must be in this node, or one of the child quadrants.
       */

      throw new UnreachableCodeException();
    }

    private void areaContaining(
      final BoundingAreaL target_area,
      final Set<T> items)
    {
      /*
       * Avoid performing pointless containment checks.
       */

      if (this.isLeaf() && this.quadrant_objects.isEmpty()) {
        return;
      }

      /*
       * If {@code target_area} completely contains this quadrant, collect
       * everything in this quadrant and all children of this quadrant.
       */

      if (target_area.contains(this.area)) {
        this.collectRecursive(items);
      }

      /*
       * Otherwise, {@code target_area} may be overlapping this quadrant and
       * therefore some items may still be contained within {@code target_area}.
       */

      final ObjectSet<Map.Entry<T, BoundingAreaL>> entries =
        this.quadrant_objects.entrySet();
      final ObjectIterator<Map.Entry<T, BoundingAreaL>> iter =
        entries.iterator();

      while (iter.hasNext()) {
        final Map.Entry<T, BoundingAreaL> entry = iter.next();
        final T item = entry.getKey();
        final BoundingAreaL item_area = entry.getValue();

        if (target_area.contains(item_area)) {
          items.add(item);
        }
      }

      if (!this.isLeaf()) {
        this.x0y0.areaContaining(target_area, items);
        this.x0y1.areaContaining(target_area, items);
        this.x1y0.areaContaining(target_area, items);
        this.x1y1.areaContaining(target_area, items);
      }
    }

    private void collectRecursive(final Set<T> items)
    {
      items.addAll(this.quadrant_objects.keySet());
      if (!this.isLeaf()) {
        this.x0y0.collectRecursive(items);
        this.x0y1.collectRecursive(items);
        this.x1y0.collectRecursive(items);
        this.x1y1.collectRecursive(items);
      }
    }

    private void areaOverlapping(
      final BoundingAreaL target_area,
      final Set<T> items)
    {
      /*
       * Avoid performing pointless overlap checks.
       */

      if (this.isLeaf() && this.quadrant_objects.isEmpty()) {
        return;
      }

      /*
       * If {@code target_area} overlaps this quadrant, test each object
       * against {@code target_area}.
       */

      if (target_area.overlaps(this.area)) {
        final ObjectSet<Map.Entry<T, BoundingAreaL>> entries =
          this.quadrant_objects.entrySet();
        final ObjectIterator<Map.Entry<T, BoundingAreaL>> iter =
          entries.iterator();

        while (iter.hasNext()) {
          final Map.Entry<T, BoundingAreaL> entry = iter.next();
          final T item = entry.getKey();
          final BoundingAreaL item_area = entry.getValue();

          if (target_area.overlaps(item_area)) {
            items.add(item);
          }
        }

        if (!this.isLeaf()) {
          this.x0y0.areaOverlapping(target_area, items);
          this.x1y0.areaOverlapping(target_area, items);
          this.x0y1.areaOverlapping(target_area, items);
          this.x1y1.areaOverlapping(target_area, items);
        }
      }
    }

    private void raycast(
      final Ray2D ray,
      final SortedSet<QuadTreeRaycastResultL<T>> items)
    {
      /*
       * Avoid performing pointless ray checks.
       */

      if (this.isLeaf() && this.quadrant_objects.isEmpty()) {
        return;
      }

      /*
       * Check whether or not the ray intersects the quadrant.
       */

      final Vector2L lower = this.area.lower();
      final Vector2L upper = this.area.upper();
      final double x0 = (double) lower.x();
      final double x1 = (double) upper.x();
      final double y0 = (double) lower.y();
      final double y1 = (double) upper.y();

      /*
       * If the ray intersects the quadrant, check each item in the quadrant
       * against the ray.
       */

      if (ray.intersectsArea(x0, y0, x1, y1)) {
        final ObjectSet<Map.Entry<T, BoundingAreaL>> entries =
          this.quadrant_objects.entrySet();
        final ObjectIterator<Map.Entry<T, BoundingAreaL>> iter =
          entries.iterator();

        while (iter.hasNext()) {
          final Map.Entry<T, BoundingAreaL> entry = iter.next();
          final T item = entry.getKey();
          final BoundingAreaL item_area = entry.getValue();

          final Vector2L item_lower = item_area.lower();
          final Vector2L item_upper = item_area.upper();
          final double item_x0 = (double) item_lower.x();
          final double item_x1 = (double) item_upper.x();
          final double item_y0 = (double) item_lower.y();
          final double item_y1 = (double) item_upper.y();

          if (ray.intersectsArea(item_x0, item_y0, item_x1, item_y1)) {
            final double distance = Vectors2D.distance(
              Vector2D.of(item_x0, item_y0),
              ray.origin());
            final QuadTreeRaycastResultL<T> result =
              QuadTreeRaycastResultL.of(distance, item_area, item);
            items.add(result);
          }
        }

        if (!this.isLeaf()) {
          this.x0y0.raycast(ray, items);
          this.x0y1.raycast(ray, items);
          this.x1y0.raycast(ray, items);
          this.x1y1.raycast(ray, items);
        }
      }
    }

    private <C> TreeVisitResult iterateQuadrants(
      final C context,
      final QuadTreeQuadrantIterationLType<T, C> f,
      final long depth)
    {
      switch (f.apply(context, this, depth)) {
        case RESULT_CONTINUE: {
          if (!this.isLeaf()) {
            switch (this.x0y0.iterateQuadrants(
              context, f, Math.addExact(depth, 1L))) {
              case RESULT_CONTINUE:
                break;
              case RESULT_TERMINATE:
                return TreeVisitResult.RESULT_TERMINATE;
            }
            switch (this.x1y0.iterateQuadrants(
              context, f, Math.addExact(depth, 1L))) {
              case RESULT_CONTINUE:
                break;
              case RESULT_TERMINATE:
                return TreeVisitResult.RESULT_TERMINATE;
            }
            switch (this.x0y1.iterateQuadrants(
              context, f, Math.addExact(depth, 1L))) {
              case RESULT_CONTINUE:
                break;
              case RESULT_TERMINATE:
                return TreeVisitResult.RESULT_TERMINATE;
            }
            switch (this.x1y1.iterateQuadrants(
              context, f, Math.addExact(depth, 1L))) {
              case RESULT_CONTINUE:
                break;
              case RESULT_TERMINATE:
                return TreeVisitResult.RESULT_TERMINATE;
            }
          }
          return TreeVisitResult.RESULT_CONTINUE;
        }
        case RESULT_TERMINATE:
          return TreeVisitResult.RESULT_TERMINATE;
      }

      throw new UnreachableCodeException();
    }

    @Override
    public Map<T, BoundingAreaL> objects()
    {
      return this.quadrant_objects_view;
    }

    @Override
    public BoundingAreaL area()
    {
      return this.area;
    }

    /**
     * Attempt to turn this node back into a leaf.
     */

    private void unsplitAttempt()
    {
      if (!this.isLeaf()) {
        final boolean x0y0_can_be_pruned = this.x0y0.unsplitCanPrune();
        final boolean x1y0_can_be_pruned = this.x1y0.unsplitCanPrune();
        final boolean x0y1_can_be_pruned = this.x0y1.unsplitCanPrune();
        final boolean x1y1_can_be_pruned = this.x1y1.unsplitCanPrune();

        if (x0y0_can_be_pruned
          && x1y0_can_be_pruned
          && x0y1_can_be_pruned
          && x1y1_can_be_pruned) {
          this.x0y0 = null;
          this.x0y1 = null;
          this.x1y0 = null;
          this.x1y1 = null;
        }
      }
    }

    /**
     * Attempt to turn this node and as many ancestors of this node back into
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
      return this.isLeaf() && this.quadrant_objects.isEmpty();
    }

    private void trim()
    {
      if (this.isLeaf()) {
        this.unsplitAttemptRecursive();
      } else {
        this.x0y0.trim();
        if (this.isLeaf()) {
          return;
        }
        this.x0y1.trim();
        if (this.isLeaf()) {
          return;
        }
        this.x1y0.trim();
        if (this.isLeaf()) {
          return;
        }
        this.x1y1.trim();
      }
    }
  }
}
