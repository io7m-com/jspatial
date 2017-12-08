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
import com.io7m.jregions.core.unparameterized.volumes.VolumeL;
import com.io7m.jregions.core.unparameterized.volumes.VolumeXYZSplitL;
import com.io7m.jregions.core.unparameterized.volumes.VolumesL;
import com.io7m.jspatial.api.Ray3D;
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.octtrees.OctTreeConfigurationL;
import com.io7m.jspatial.api.octtrees.OctTreeLType;
import com.io7m.jspatial.api.octtrees.OctTreeOctantIterationLType;
import com.io7m.jspatial.api.octtrees.OctTreeOctantLType;
import com.io7m.jspatial.api.octtrees.OctTreeRaycastResultL;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3D;
import com.io7m.junreachable.UnreachableCodeException;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMaps;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiFunction;

/**
 * Default implementation of the {@link OctTreeLType} interface.
 *
 * @param <T> The precise type of tree objects
 */

public final class OctTreeL<T> implements OctTreeLType<T>
{
  private final Reference2ReferenceOpenHashMap<T, VolumeL> objects;
  private final OctTreeConfigurationL config;
  private Octant root;

  private OctTreeL(final OctTreeConfigurationL in_config)
  {
    this.config = Objects.requireNonNull(in_config, "Configuration");
    this.root = new Octant(null, in_config.volume());
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

  public static <T> OctTreeLType<T> create(
    final OctTreeConfigurationL config)
  {
    return new OctTreeL<>(config);
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

    final OctTreeL<?> that = (OctTreeL<?>) o;
    return this.objects.equals(that.objects);
  }

  @Override
  public int hashCode()
  {
    return this.objects.hashCode();
  }

  @Override
  public VolumeL bounds()
  {
    return this.root.volume;
  }

  @Override
  public boolean insert(
    final T item,
    final VolumeL item_bounds)
  {
    Objects.requireNonNull(item, "Item");
    Objects.requireNonNull(item_bounds, "Bounds");

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
    Objects.requireNonNull(item, "Item");
    return this.root.remove(item);
  }

  @Override
  public void clear()
  {
    this.root = new Octant(null, this.root.volume);
    this.objects.clear();
  }

  @Override
  public <U> OctTreeLType<U> map(final BiFunction<T, VolumeL, U> f)
  {
    Objects.requireNonNull(f, "Function");

    final OctTreeLType<U> qt = new OctTreeL<>(this.config);
    for (final Map.Entry<T, VolumeL> es : this.objects.entrySet()) {
      final T item = es.getKey();
      final VolumeL item_volume = es.getValue();
      qt.insert(f.apply(item, item_volume), es.getValue());
    }
    return qt;
  }

  @Override
  public <C> void iterateOctants(
    final C context,
    final OctTreeOctantIterationLType<T, C> f)
  {
    Objects.requireNonNull(context, "Context");
    Objects.requireNonNull(f, "Function");
    this.root.iterateOctants(context, f, 0L);
  }

  @Override
  public VolumeL volumeFor(final T item)
  {
    Objects.requireNonNull(item, "Item");

    return this.objects.computeIfAbsent(item, i -> {
      throw new NoSuchElementException(i.toString());
    });
  }

  @Override
  public void containedBy(
    final VolumeL volume,
    final Set<T> items)
  {
    Objects.requireNonNull(volume, "Volume");
    Objects.requireNonNull(items, "Items");
    this.root.volumeContaining(volume, items);
  }

  @Override
  public void overlappedBy(
    final VolumeL volume,
    final Set<T> items)
  {
    Objects.requireNonNull(volume, "Volume");
    Objects.requireNonNull(items, "Items");
    this.root.volumeOverlapping(volume, items);
  }

  @Override
  public void raycast(
    final Ray3D ray,
    final SortedSet<OctTreeRaycastResultL<T>> items)
  {
    Objects.requireNonNull(ray, "Ray");
    Objects.requireNonNull(items, "Items");
    this.root.raycast(ray, items);
  }

  protected final class Octant implements OctTreeOctantLType<T>
  {
    private final VolumeL volume;
    private final Reference2ReferenceOpenHashMap<T, VolumeL> octant_objects;
    private final Octant parent;
    private final Map<T, VolumeL> octant_objects_view;
    private Octant x0y0z0;
    private Octant x0y1z0;
    private Octant x1y0z0;
    private Octant x1y1z0;
    private Octant x0y0z1;
    private Octant x0y1z1;
    private Octant x1y0z1;
    private Octant x1y1z1;

    private Octant(
      final Octant in_parent,
      final VolumeL in_volume)
    {
      this.parent = in_parent;
      this.volume = Objects.requireNonNull(in_volume, "Volume");
      this.octant_objects = new Reference2ReferenceOpenHashMap<>();
      this.octant_objects_view =
        Reference2ReferenceMaps.unmodifiable(this.octant_objects);
    }

    private boolean insert(
      final T item,
      final VolumeL item_bounds)
    {
      Preconditions.checkPrecondition(
        item,
        !OctTreeL.this.objects.containsKey(item),
        x -> "Object must not be in tree");

      return VolumesL.contains(this.volume, item_bounds)
        && this.insertStep(item, item_bounds);
    }

    private boolean insertStep(
      final T item,
      final VolumeL item_bounds)
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

      if (this.insertStepTryZ0(item, item_bounds)) {
        return true;
      }

      if (this.insertStepTryZ1(item, item_bounds)) {
        return true;
      }

      /*
       * Otherwise, insert the object into this node.
       */

      return this.insertObject(item, item_bounds);
    }

    private boolean insertStepTryZ0(
      final T item,
      final VolumeL item_bounds)
    {
      if (VolumesL.contains(this.x0y0z0.volume, item_bounds)) {
        return this.x0y0z0.insertStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x1y0z0.volume, item_bounds)) {
        return this.x1y0z0.insertStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x0y1z0.volume, item_bounds)) {
        return this.x0y1z0.insertStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x1y1z0.volume, item_bounds)) {
        return this.x1y1z0.insertStep(item, item_bounds);
      }
      return false;
    }

    private boolean insertStepTryZ1(
      final T item,
      final VolumeL item_bounds)
    {
      if (VolumesL.contains(this.x0y0z1.volume, item_bounds)) {
        return this.x0y0z1.insertStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x1y0z1.volume, item_bounds)) {
        return this.x1y0z1.insertStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x0y1z1.volume, item_bounds)) {
        return this.x0y1z1.insertStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x1y1z1.volume, item_bounds)) {
        return this.x1y1z1.insertStep(item, item_bounds);
      }
      return false;
    }

    private boolean insertObject(
      final T item,
      final VolumeL item_bounds)
    {
      OctTreeL.this.objects.put(item, item_bounds);
      this.octant_objects.put(item, item_bounds);
      return true;
    }

    private void split()
    {
      Preconditions.checkPrecondition(this.canSplit(), "Octant can split");

      final Optional<VolumeXYZSplitL<VolumeL>> q_opt = OctantsL.subdivide(this.volume);
      Invariants.checkInvariant(q_opt.isPresent(), "Octant must be splittable");

      q_opt.ifPresent(q -> {
        this.x0y0z0 = new Octant(this, q.x0y0z0());
        this.x0y1z0 = new Octant(this, q.x0y1z0());
        this.x1y0z0 = new Octant(this, q.x1y0z0());
        this.x1y1z0 = new Octant(this, q.x1y1z0());

        this.x0y0z1 = new Octant(this, q.x0y0z1());
        this.x0y1z1 = new Octant(this, q.x0y1z1());
        this.x1y0z1 = new Octant(this, q.x1y0z1());
        this.x1y1z1 = new Octant(this, q.x1y1z1());
      });
    }

    private boolean canSplit()
    {
      final long width = this.volume.sizeX();
      final long height = this.volume.sizeY();
      final long depth = this.volume.sizeZ();

      final long min_width =
        Math.max(2L, OctTreeL.this.config.minimumOctantWidth());
      final long min_height =
        Math.max(2L, OctTreeL.this.config.minimumOctantHeight());
      final long min_depth =
        Math.max(2L, OctTreeL.this.config.minimumOctantDepth());

      final long half_width = width / 2L;
      final long half_height = height / 2L;
      final long half_depth = depth / 2L;

      return half_width >= min_width
        && half_height >= min_height
        && half_depth >= min_depth;
    }

    private boolean isLeaf()
    {
      return this.x0y0z0 == null;
    }

    private boolean remove(final T item)
    {
      if (!OctTreeL.this.objects.containsKey(item)) {
        return false;
      }

      final VolumeL bounds = OctTreeL.this.objects.get(item);
      return this.removeStep(item, bounds);
    }

    private boolean removeStep(
      final T item,
      final VolumeL item_bounds)
    {
      if (this.octant_objects.containsKey(item)) {
        this.octant_objects.remove(item);
        OctTreeL.this.objects.remove(item);
        if (OctTreeL.this.config.trimOnRemove()) {
          this.unsplitAttemptRecursive();
        }
        return true;
      }

      /*
       * Try removing the item from the child nodes.
       */

      Invariants.checkInvariant(!this.isLeaf(), "Node cannot be a leaf");

      if (this.removeStepTryZ0(item, item_bounds)) {
        return true;
      }

      if (this.removeStepTryZ1(item, item_bounds)) {
        return true;
      }

      /*
       * The object must be in the tree, according to remove(). Therefore it
       * must be in this node, or one of the child octants.
       */

      throw new UnreachableCodeException();
    }

    private boolean removeStepTryZ0(
      final T item,
      final VolumeL item_bounds)
    {
      if (VolumesL.contains(this.x0y0z0.volume, item_bounds)) {
        return this.x0y0z0.removeStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x1y0z0.volume, item_bounds)) {
        return this.x1y0z0.removeStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x0y1z0.volume, item_bounds)) {
        return this.x0y1z0.removeStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x1y1z0.volume, item_bounds)) {
        return this.x1y1z0.removeStep(item, item_bounds);
      }
      return false;
    }

    private boolean removeStepTryZ1(
      final T item,
      final VolumeL item_bounds)
    {
      if (VolumesL.contains(this.x0y0z1.volume, item_bounds)) {
        return this.x0y0z1.removeStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x1y0z1.volume, item_bounds)) {
        return this.x1y0z1.removeStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x0y1z1.volume, item_bounds)) {
        return this.x0y1z1.removeStep(item, item_bounds);
      }
      if (VolumesL.contains(this.x1y1z1.volume, item_bounds)) {
        return this.x1y1z1.removeStep(item, item_bounds);
      }
      return false;
    }

    private void volumeContaining(
      final VolumeL target_volume,
      final Set<T> items)
    {
      /*
       * Avoid performing pointless containment checks.
       */

      if (this.isLeaf() && this.octant_objects.isEmpty()) {
        return;
      }

      /*
       * If {@code target_volume} completely contains this octant, collect
       * everything in this octant and all children of this octant.
       */

      if (VolumesL.contains(target_volume, this.volume)) {
        this.collectRecursive(items);
      }

      /*
       * Otherwise, {@code target_volume} may be overlapping this octant and
       * therefore some items may still be contained within {@code target_volume}.
       */

      final ObjectSet<Map.Entry<T, VolumeL>> entries =
        this.octant_objects.entrySet();
      final ObjectIterator<Map.Entry<T, VolumeL>> iter =
        entries.iterator();

      while (iter.hasNext()) {
        final Map.Entry<T, VolumeL> entry = iter.next();
        final T item = entry.getKey();
        final VolumeL item_volume = entry.getValue();

        if (VolumesL.contains(target_volume, item_volume)) {
          items.add(item);
        }
      }

      if (!this.isLeaf()) {
        this.x0y0z0.volumeContaining(target_volume, items);
        this.x0y1z0.volumeContaining(target_volume, items);
        this.x1y0z0.volumeContaining(target_volume, items);
        this.x1y1z0.volumeContaining(target_volume, items);

        this.x0y0z1.volumeContaining(target_volume, items);
        this.x0y1z1.volumeContaining(target_volume, items);
        this.x1y0z1.volumeContaining(target_volume, items);
        this.x1y1z1.volumeContaining(target_volume, items);
      }
    }

    private void collectRecursive(final Set<T> items)
    {
      items.addAll(this.octant_objects.keySet());
      if (!this.isLeaf()) {
        this.x0y0z0.collectRecursive(items);
        this.x0y1z0.collectRecursive(items);
        this.x1y0z0.collectRecursive(items);
        this.x1y1z0.collectRecursive(items);

        this.x0y0z1.collectRecursive(items);
        this.x0y1z1.collectRecursive(items);
        this.x1y0z1.collectRecursive(items);
        this.x1y1z1.collectRecursive(items);
      }
    }

    private void volumeOverlapping(
      final VolumeL target_volume,
      final Set<T> items)
    {
      /*
       * Avoid performing pointless overlap checks.
       */

      if (this.isLeaf() && this.octant_objects.isEmpty()) {
        return;
      }

      /*
       * If {@code target_volume} overlaps this octant, test each object
       * against {@code target_volume}.
       */

      if (VolumesL.overlaps(target_volume, this.volume)) {
        final ObjectSet<Map.Entry<T, VolumeL>> entries =
          this.octant_objects.entrySet();
        final ObjectIterator<Map.Entry<T, VolumeL>> iter =
          entries.iterator();

        while (iter.hasNext()) {
          final Map.Entry<T, VolumeL> entry = iter.next();
          final T item = entry.getKey();
          final VolumeL item_volume = entry.getValue();

          if (VolumesL.overlaps(target_volume, item_volume)) {
            items.add(item);
          }
        }

        if (!this.isLeaf()) {
          this.x0y0z0.volumeOverlapping(target_volume, items);
          this.x1y0z0.volumeOverlapping(target_volume, items);
          this.x0y1z0.volumeOverlapping(target_volume, items);
          this.x1y1z0.volumeOverlapping(target_volume, items);

          this.x0y0z1.volumeOverlapping(target_volume, items);
          this.x1y0z1.volumeOverlapping(target_volume, items);
          this.x0y1z1.volumeOverlapping(target_volume, items);
          this.x1y1z1.volumeOverlapping(target_volume, items);
        }
      }
    }

    private void raycast(
      final Ray3D ray,
      final SortedSet<OctTreeRaycastResultL<T>> items)
    {
      /*
       * Avoid performing pointless ray checks.
       */

      if (this.isLeaf() && this.octant_objects.isEmpty()) {
        return;
      }

      /*
       * Check whether or not the ray intersects the octant.
       */

      final double x0 = (double) this.volume.minimumX();
      final double x1 = (double) this.volume.maximumX();
      final double y0 = (double) this.volume.minimumY();
      final double y1 = (double) this.volume.maximumY();
      final double z0 = (double) this.volume.minimumZ();
      final double z1 = (double) this.volume.maximumZ();

      /*
       * If the ray intersects the octant, check each item in the octant
       * against the ray.
       */

      if (ray.intersectsVolume(x0, y0, z0, x1, y1, z1)) {
        final ObjectSet<Map.Entry<T, VolumeL>> entries =
          this.octant_objects.entrySet();
        final ObjectIterator<Map.Entry<T, VolumeL>> iter =
          entries.iterator();

        while (iter.hasNext()) {
          final Map.Entry<T, VolumeL> entry = iter.next();
          final T item = entry.getKey();
          final VolumeL item_volume = entry.getValue();

          final double item_x0 = (double) item_volume.minimumX();
          final double item_x1 = (double) item_volume.maximumX();
          final double item_y0 = (double) item_volume.minimumY();
          final double item_y1 = (double) item_volume.maximumY();
          final double item_z0 = (double) item_volume.minimumZ();
          final double item_z1 = (double) item_volume.maximumZ();

          if (ray.intersectsVolume(
            item_x0,
            item_y0,
            item_z0,
            item_x1,
            item_y1,
            item_z1)) {
            final double distance = Vectors3D.distance(
              Vector3D.of(item_x0, item_y0, item_z0),
              ray.origin());
            final OctTreeRaycastResultL<T> result =
              OctTreeRaycastResultL.of(distance, item_volume, item);
            items.add(result);
          }
        }

        if (!this.isLeaf()) {
          this.x0y0z0.raycast(ray, items);
          this.x0y1z0.raycast(ray, items);
          this.x1y0z0.raycast(ray, items);
          this.x1y1z0.raycast(ray, items);

          this.x0y0z1.raycast(ray, items);
          this.x0y1z1.raycast(ray, items);
          this.x1y0z1.raycast(ray, items);
          this.x1y1z1.raycast(ray, items);
        }
      }
    }

    private <C> TreeVisitResult iterateOctants(
      final C context,
      final OctTreeOctantIterationLType<T, C> f,
      final long depth)
    {
      switch (f.apply(context, this, depth)) {
        case RESULT_CONTINUE: {
          if (!this.isLeaf()) {
            switch (this.iterateOctantsZ0(context, f, depth)) {
              case RESULT_CONTINUE:
                break;
              case RESULT_TERMINATE:
                return TreeVisitResult.RESULT_TERMINATE;
            }
            switch (this.iterateOctantsZ1(context, f, depth)) {
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

    private <C> TreeVisitResult iterateOctantsZ0(
      final C c,
      final OctTreeOctantIterationLType<T, C> f,
      final long depth)
    {
      switch (this.x0y0z0.iterateOctants(c, f, Math.addExact(depth, 1L))) {
        case RESULT_CONTINUE:
          break;
        case RESULT_TERMINATE:
          return TreeVisitResult.RESULT_TERMINATE;
      }
      switch (this.x1y0z0.iterateOctants(c, f, Math.addExact(depth, 1L))) {
        case RESULT_CONTINUE:
          break;
        case RESULT_TERMINATE:
          return TreeVisitResult.RESULT_TERMINATE;
      }
      switch (this.x0y1z0.iterateOctants(c, f, Math.addExact(depth, 1L))) {
        case RESULT_CONTINUE:
          break;
        case RESULT_TERMINATE:
          return TreeVisitResult.RESULT_TERMINATE;
      }
      switch (this.x1y1z0.iterateOctants(c, f, Math.addExact(depth, 1L))) {
        case RESULT_CONTINUE:
          break;
        case RESULT_TERMINATE:
          return TreeVisitResult.RESULT_TERMINATE;
      }
      return TreeVisitResult.RESULT_CONTINUE;
    }

    private <C> TreeVisitResult iterateOctantsZ1(
      final C c,
      final OctTreeOctantIterationLType<T, C> f,
      final long depth)
    {
      switch (this.x0y0z1.iterateOctants(c, f, Math.addExact(depth, 1L))) {
        case RESULT_CONTINUE:
          break;
        case RESULT_TERMINATE:
          return TreeVisitResult.RESULT_TERMINATE;
      }
      switch (this.x1y0z1.iterateOctants(c, f, Math.addExact(depth, 1L))) {
        case RESULT_CONTINUE:
          break;
        case RESULT_TERMINATE:
          return TreeVisitResult.RESULT_TERMINATE;
      }
      switch (this.x0y1z1.iterateOctants(c, f, Math.addExact(depth, 1L))) {
        case RESULT_CONTINUE:
          break;
        case RESULT_TERMINATE:
          return TreeVisitResult.RESULT_TERMINATE;
      }
      switch (this.x1y1z1.iterateOctants(c, f, Math.addExact(depth, 1L))) {
        case RESULT_CONTINUE:
          break;
        case RESULT_TERMINATE:
          return TreeVisitResult.RESULT_TERMINATE;
      }
      return TreeVisitResult.RESULT_CONTINUE;
    }

    @Override
    public Map<T, VolumeL> objects()
    {
      return this.octant_objects_view;
    }

    @Override
    public VolumeL volume()
    {
      return this.volume;
    }

    /**
     * Attempt to turn this node back into a leaf.
     */

    private void unsplitAttempt()
    {
      if (!this.isLeaf()) {
        boolean prune = true;

        prune &= this.x0y0z0.unsplitCanPrune();
        prune &= this.x1y0z0.unsplitCanPrune();
        prune &= this.x0y1z0.unsplitCanPrune();
        prune &= this.x1y1z0.unsplitCanPrune();

        prune &= this.x0y0z1.unsplitCanPrune();
        prune &= this.x1y0z1.unsplitCanPrune();
        prune &= this.x0y1z1.unsplitCanPrune();
        prune &= this.x1y1z1.unsplitCanPrune();

        if (prune) {
          this.x0y0z0 = null;
          this.x0y1z0 = null;
          this.x1y0z0 = null;
          this.x1y1z0 = null;
          this.x0y0z1 = null;
          this.x0y1z1 = null;
          this.x1y0z1 = null;
          this.x1y1z1 = null;
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
      return this.isLeaf() && this.octant_objects.isEmpty();
    }

    private void trim()
    {
      if (this.isLeaf()) {
        this.unsplitAttemptRecursive();
      } else {
        this.x0y0z0.trim();
        if (this.isLeaf()) {
          return;
        }
        this.x0y1z0.trim();
        if (this.isLeaf()) {
          return;
        }
        this.x1y0z0.trim();
        if (this.isLeaf()) {
          return;
        }
        this.x1y1z0.trim();
        if (this.isLeaf()) {
          return;
        }

        this.x0y0z1.trim();
        if (this.isLeaf()) {
          return;
        }
        this.x0y1z1.trim();
        if (this.isLeaf()) {
          return;
        }
        this.x1y0z1.trim();
        if (this.isLeaf()) {
          return;
        }
        this.x1y1z1.trim();
      }
    }
  }
}
