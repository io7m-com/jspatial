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

package com.io7m.jspatial.examples.swing;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicReference;

import com.io7m.jfunctional.PartialFunctionType;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jspatial.BoundingAreaType;
import com.io7m.jspatial.RayI2D;
import com.io7m.jspatial.quadtrees.QuadTreeRaycastResult;
import com.io7m.jspatial.quadtrees.QuadTreeTraversalType;
import com.io7m.jspatial.quadtrees.QuadTreeType;
import com.io7m.jtensors.VectorReadable2IType;

final class QuadTreeCanvas extends Canvas
{
  private static final long                                     serialVersionUID;

  static {
    serialVersionUID = -4348626371362763989L;
  }

  private final AtomicReference<QuadTreeType<Rectangle>>        quad;
  private @Nullable RayI2D                                      ray;
  private @Nullable SortedSet<QuadTreeRaycastResult<Rectangle>> ray_items;
  private @Nullable BoundingAreaType                            area;
  private @Nullable SortedSet<Rectangle>                        area_items;

  public QuadTreeCanvas(
    final AtomicReference<QuadTreeType<Rectangle>> in_quad)
  {
    this.quad = NullCheck.notNull(in_quad);
  }

  @Override public void paint(
    final @Nullable Graphics g)
  {
    assert g != null;

    try {
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
      g.setColor(Color.WHITE);

      final QuadTreeType<Rectangle> qr = this.quad.get();
      assert qr != null;

      qr.quadTreeTraverse(new QuadTreeTraversalType<Exception>() {
        @Override public void visit(
          final int depth,
          final VectorReadable2IType lower,
          final VectorReadable2IType upper)
          throws Exception
        {
          g.setColor(Color.GRAY);
          g.drawRect(
            lower.getXI(),
            lower.getYI(),
            (upper.getXI() - lower.getXI()) + 1,
            (upper.getYI() - lower.getYI()) + 1);
        }
      });

      qr
        .quadTreeIterateObjects(new PartialFunctionType<Rectangle, Boolean, Exception>() {
          @Override public Boolean call(
            final Rectangle x)
            throws Exception
          {
            final VectorReadable2IType lower = x.boundingAreaLower();
            final VectorReadable2IType upper = x.boundingAreaUpper();

            g.setColor(Color.GREEN);
            g.drawRect(
              lower.getXI(),
              lower.getYI(),
              (upper.getXI() - lower.getXI()) + 1,
              (upper.getYI() - lower.getYI()) + 1);

            return NullCheck.notNull(Boolean.TRUE);
          }
        });

      final RayI2D ray_current = this.ray;
      if (ray_current != null) {
        g.setColor(Color.CYAN);

        final int origin_x = (int) ray_current.getOrigin().getXD();
        final int origin_y = (int) ray_current.getOrigin().getYD();

        int target_x = origin_x;
        target_x += (ray_current.getDirection().getXD()) * 1000;
        int target_y = origin_y;
        target_y += (ray_current.getDirection().getYD()) * 1000;

        g.drawLine(origin_x, origin_y, target_x, target_y);

        final SortedSet<QuadTreeRaycastResult<Rectangle>> ri = this.ray_items;
        assert ri != null;

        for (final QuadTreeRaycastResult<Rectangle> rr : ri) {
          final Rectangle r = rr.getObject();
          final VectorReadable2IType lower = r.boundingAreaLower();
          final VectorReadable2IType upper = r.boundingAreaUpper();

          g.drawRect(
            lower.getXI(),
            lower.getYI(),
            (upper.getXI() - lower.getXI()) + 1,
            (upper.getYI() - lower.getYI()) + 1);
        }
      }

      final BoundingAreaType a = this.area;
      if (a != null) {
        g.setColor(Color.RED);

        final VectorReadable2IType lower = a.boundingAreaLower();
        final VectorReadable2IType upper = a.boundingAreaUpper();

        g.drawRect(
          lower.getXI(),
          lower.getYI(),
          (upper.getXI() - lower.getXI()) + 1,
          (upper.getYI() - lower.getYI()) + 1);

        final SortedSet<Rectangle> ai = this.area_items;
        assert ai != null;
        for (final Rectangle rr : ai) {
          final VectorReadable2IType rlower = rr.boundingAreaLower();
          final VectorReadable2IType rupper = rr.boundingAreaUpper();

          g.drawRect(
            rlower.getXI(),
            rlower.getYI(),
            (rupper.getXI() - rlower.getXI()) + 1,
            (rupper.getYI() - rlower.getYI()) + 1);
        }
      }

    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void reset()
  {
    this.ray = null;
    this.ray_items = null;
    this.area = null;
    this.area_items = null;
  }

  public void setRayItems(
    final RayI2D in_ray,
    final SortedSet<QuadTreeRaycastResult<Rectangle>> in_items)
  {
    this.ray = in_ray;
    this.ray_items = in_items;
  }

  public void setAreaItems(
    final BoundingAreaType in_area,
    final SortedSet<Rectangle> in_items)
  {
    this.area = in_area;
    this.area_items = in_items;
  }
}
