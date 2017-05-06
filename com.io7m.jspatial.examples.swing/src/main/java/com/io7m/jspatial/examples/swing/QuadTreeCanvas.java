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

import com.io7m.jfunctional.Unit;
import com.io7m.jnull.NullCheck;
import com.io7m.jspatial.api.BoundingAreaD;
import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jspatial.api.BoundingAreaLType;
import com.io7m.jspatial.api.Ray2D;
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationD;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import com.io7m.jspatial.api.quadtrees.QuadTreeDType;
import com.io7m.jspatial.api.quadtrees.QuadTreeLType;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultD;
import com.io7m.jspatial.api.quadtrees.QuadTreeRaycastResultL;
import com.io7m.jspatial.examples.swing.LogMessageType.Severity;
import com.io7m.jspatial.implementation.QuadTreeD;
import com.io7m.jspatial.implementation.QuadTreeL;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2L;
import io.reactivex.Observable;
import io.reactivex.Observer;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

/**
 * The tree canvas.
 */

final class QuadTreeCanvas extends JPanel
{
  private static final String INSTANCE_FONT;

  static {
    INSTANCE_FONT = "Monospaced 10";
  }

  private final Map<Integer, Item> items;
  private final Observer<LogMessage> log_messages;
  private final Set<Integer> query_area_results;
  private final SortedSet<QuadTreeRaycastResultL<Integer>> ray_area_results_l;
  private final SortedSet<QuadTreeRaycastResultD<Integer>> ray_area_results_d;
  private Function<QuadTreeCommandType, Unit> on_message_cases;
  private QuadTreeLType<Integer> tree_l;
  private QuadTreeDType<Integer> tree_d;
  private QuadTreeKind kind;
  private BoundingAreaL query_area_l;
  private BoundingAreaD query_area_d;
  private Ray2D ray;

  QuadTreeCanvas(
    final Observable<QuadTreeCommandType> in_tree_commands,
    final Observer<LogMessage> in_log_messages)
  {
    this.kind = QuadTreeKind.LONG_INTEGER;
    this.items = new HashMap<>(128);
    this.query_area_results = new HashSet<>(128);
    this.ray_area_results_l = new TreeSet<>();
    this.ray_area_results_d = new TreeSet<>();
    this.log_messages = in_log_messages;

    this.on_message_cases = QuadTreeCommandTypes.cases()
      .addObject(this::onCommandAddObject)
      .removeObject(this::onCommandRemoveObject)
      .trimQuadTree(this::onCommandTrimQuadTree)
      .createQuadTreeL(QuadTreeCanvas.this::onCommandCreateQuadTreeL)
      .createQuadTreeD(QuadTreeCanvas.this::onCommandCreateQuadTreeD)
      .areaQuery(this::onCommandAreaQuery)
      .rayQuery(this::onCommandRayQuery);

    in_tree_commands.subscribe(this::onCommand);
  }

  private Unit onCommandRayQuery(
    final Ray2D in_ray)
  {
    this.ray_area_results_l.clear();
    this.ray_area_results_d.clear();
    this.ray = null;

    switch (this.kind) {
      case LONG_INTEGER: {
        final QuadTreeLType<Integer> t = this.tree_l;
        if (t != null) {
          this.ray = in_ray;
          t.raycast(in_ray, this.ray_area_results_l);
          this.sendRayQueryCountMessage();
        }
        break;
      }
      case DOUBLE: {
        final QuadTreeDType<Integer> t = this.tree_d;
        if (t != null) {
          this.ray = in_ray;
          t.raycast(in_ray, this.ray_area_results_d);
          this.sendRayQueryCountMessage();
        }
        break;
      }
    }

    return Unit.unit();
  }

  private void sendRayQueryCountMessage()
  {
    final int count = Math.max(
      this.ray_area_results_d.size(),
      this.ray_area_results_l.size());

    this.log_messages.onNext(
      LogMessage.of(
        Severity.INFO,
        String.format("Ray query selected %d items", Integer.valueOf(count))));
  }

  private Unit onCommandAreaQuery(
    final BoundingAreaL area_l,
    final BoundingAreaD area_d,
    final boolean overlaps)
  {
    this.query_area_results.clear();

    switch (this.kind) {
      case LONG_INTEGER: {
        final QuadTreeLType<Integer> t = this.tree_l;
        if (t != null) {
          this.query_area_d = null;
          this.query_area_l = area_l;

          if (overlaps) {
            t.overlappedBy(area_l, this.query_area_results);
          } else {
            t.containedBy(area_l, this.query_area_results);
          }

          this.sendAreaQueryCountMessage();
        }
        break;
      }
      case DOUBLE: {
        final QuadTreeDType<Integer> t = this.tree_d;
        if (t != null) {
          this.query_area_d = area_d;
          this.query_area_l = null;

          if (overlaps) {
            t.overlappedBy(area_d, this.query_area_results);
          } else {
            t.containedBy(area_d, this.query_area_results);
          }

          this.sendAreaQueryCountMessage();
        }
        break;
      }
    }

    return Unit.unit();
  }

  private void sendAreaQueryCountMessage()
  {
    this.log_messages.onNext(
      LogMessage.of(
        Severity.INFO,
        String.format(
          "Area query selected %d items",
          Integer.valueOf(this.query_area_results.size()))));
  }

  private void onCommand(final QuadTreeCommandType m)
  {
    this.on_message_cases.apply(m);
    this.repaint();
  }

  private Unit onCommandCreateQuadTreeL(
    final QuadTreeConfigurationL config)
  {
    this.kind = QuadTreeKind.LONG_INTEGER;
    this.tree_l = QuadTreeL.create(config);
    this.items.clear();
    this.query_area_results.clear();
    this.query_area_d = null;
    this.query_area_l = null;
    this.ray = null;
    this.ray_area_results_d.clear();
    this.ray_area_results_l.clear();
    return Unit.unit();
  }

  private Unit onCommandCreateQuadTreeD(
    final QuadTreeConfigurationD config)
  {
    this.kind = QuadTreeKind.DOUBLE;
    this.tree_d = QuadTreeD.create(config);
    this.items.clear();
    this.query_area_results.clear();
    this.query_area_d = null;
    this.query_area_l = null;
    return Unit.unit();
  }

  private Unit onCommandTrimQuadTree()
  {
    switch (this.kind) {
      case LONG_INTEGER: {
        final QuadTreeLType<Integer> t = this.tree_l;
        if (t != null) {
          t.trim();
        }
        break;
      }
      case DOUBLE: {
        final QuadTreeDType<Integer> t = this.tree_d;
        if (t != null) {
          t.trim();
        }
        break;
      }
    }

    return Unit.unit();
  }

  private Unit onCommandRemoveObject(
    final Integer item)
  {
    switch (this.kind) {
      case LONG_INTEGER: {
        final QuadTreeLType<Integer> t = this.tree_l;
        if (t != null) {
          final boolean removed = t.remove(item);
          this.sendRemovedMessage(item, removed);
        }
        break;
      }
      case DOUBLE: {
        final QuadTreeDType<Integer> t = this.tree_d;
        if (t != null) {
          final boolean removed = t.remove(item);
          this.sendRemovedMessage(item, removed);
        }
        break;
      }
    }

    this.query_area_results.remove(item);
    this.items.remove(item);
    return Unit.unit();
  }

  private void sendRemovedMessage(
    final Integer item,
    final boolean removed)
  {
    if (removed) {
      this.log_messages.onNext(LogMessage.of(
        Severity.INFO,
        String.format("Removed item %d", item)));
    } else {
      this.log_messages.onNext(LogMessage.of(
        Severity.ERROR,
        String.format("Failed to remove item %d", item)));
    }
  }

  private Unit onCommandAddObject(
    final BoundingAreaL area,
    final Integer item)
  {
    switch (this.kind) {
      case LONG_INTEGER: {
        final QuadTreeLType<Integer> t = this.tree_l;
        if (t != null) {
          final boolean inserted = t.insert(item, area);
          this.sendInsertedMessage(area, item, inserted);
        }
        break;
      }
      case DOUBLE: {
        final QuadTreeDType<Integer> t = this.tree_d;
        if (t != null) {
          final BoundingAreaD area_d = BoundingAreaD.of(
            Vector2D.of(
              (double) area.lower().x(), (double) area.lower().y()),
            Vector2D.of(
              (double) area.upper().x(), (double) area.upper().y()));
          final boolean inserted = t.insert(item, area_d);
          this.sendInsertedMessage(area, item, inserted);
        }
        break;
      }
    }

    this.items.put(item, new Item(item, area));
    return Unit.unit();
  }

  private void sendInsertedMessage(
    final BoundingAreaLType area,
    final Integer item,
    final boolean inserted)
  {
    if (inserted) {
      this.log_messages.onNext(LogMessage.of(
        Severity.INFO,
        String.format(
          "Inserted item %d (%d+%d %dx%d)", item,
          Long.valueOf(area.lower().x()),
          Long.valueOf(area.lower().y()),
          Long.valueOf(area.width()),
          Long.valueOf(area.height()))));
    } else {
      this.log_messages.onNext(LogMessage.of(
        Severity.ERROR,
        String.format(
          "Failed to insert item %d (%d+%d %dx%d)", item,
          Long.valueOf(area.lower().x()),
          Long.valueOf(area.lower().y()),
          Long.valueOf(area.width()),
          Long.valueOf(area.height()))));
    }
  }

  @Override
  public void paint(final Graphics g)
  {
    super.paint(g);

    g.setColor(Color.BLACK);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());

    switch (this.kind) {
      case LONG_INTEGER: {
        this.drawTreeLong(g);
        break;
      }
      case DOUBLE: {
        this.drawTreeDouble(g);
        break;
      }
    }
  }

  private void drawTreeDouble(final Graphics g)
  {
    final QuadTreeDType<Integer> t = this.tree_d;
    if (t != null) {

      t.iterateQuadrants(g, (gg, quadrant, depth) -> {
        final Vector2D lower = quadrant.area().lower();

        gg.setColor(Color.GRAY);
        gg.drawRect(
          (int) lower.x(),
          (int) lower.y(),
          (int) quadrant.area().width(),
          (int) quadrant.area().height());
        return TreeVisitResult.RESULT_CONTINUE;
      });

      final BoundingAreaD qa = this.query_area_d;
      if (qa != null) {
        g.setColor(Color.CYAN);
        g.drawRect(
          (int) qa.lower().x(),
          (int) qa.lower().y(),
          (int) qa.width(),
          (int) qa.height());
      }

      g.setFont(Font.decode(INSTANCE_FONT));

      for (final Map.Entry<Integer, Item> entry : this.items.entrySet()) {
        final Item item = entry.getValue();
        final Vector2L lower = item.area.lower();

        if (t.contains(item.item)) {
          if (this.query_area_results.contains(item.item)) {
            g.setColor(Color.CYAN);
          } else {
            g.setColor(Color.WHITE);
          }
        } else {
          g.setColor(Color.RED);
        }

        g.drawRect(
          (int) lower.x(),
          (int) lower.y(),
          (int) item.area.width(),
          (int) item.area.height());
        g.drawString(
          item.item.toString(),
          (int) lower.x() + 2,
          (int) lower.y() + 8);
      }
    }
  }

  private void drawTreeLong(final Graphics g)
  {
    final QuadTreeLType<Integer> t = this.tree_l;
    if (t != null) {

      t.iterateQuadrants(g, (gg, quadrant, depth) -> {
        final Vector2L lower = quadrant.area().lower();

        gg.setColor(Color.GRAY);
        gg.drawRect(
          (int) lower.x(),
          (int) lower.y(),
          (int) quadrant.area().width(),
          (int) quadrant.area().height());
        return TreeVisitResult.RESULT_CONTINUE;
      });

      final BoundingAreaL qa = this.query_area_l;
      if (qa != null) {
        g.setColor(Color.CYAN);
        g.drawRect(
          (int) qa.lower().x(),
          (int) qa.lower().y(),
          (int) qa.width(),
          (int) qa.height());
      }

      g.setFont(Font.decode(INSTANCE_FONT));

      for (final Map.Entry<Integer, Item> entry : this.items.entrySet()) {
        final Item item = entry.getValue();
        final Vector2L lower = item.area.lower();

        if (t.contains(item.item)) {
          if (this.query_area_results.contains(item.item)) {
            g.setColor(Color.CYAN);
          } else {
            g.setColor(Color.WHITE);
          }
        } else {
          g.setColor(Color.RED);
        }

        g.drawRect(
          (int) lower.x(),
          (int) lower.y(),
          (int) item.area.width(),
          (int) item.area.height());
        g.drawString(
          item.item.toString(),
          (int) lower.x() + 2,
          (int) lower.y() + 8);
      }

      if (this.ray != null) {
        final double x0 = this.ray.origin().x();
        final double y0 = this.ray.origin().y();
        final double x1 = this.ray.direction().x() * 10000.0;
        final double y1 = this.ray.direction().y() * 10000.0;

        g.setColor(Color.GREEN);
        g.drawLine((int) x0, (int) y0, (int) (x0 + x1), (int) (y0 + y1));

        for (final QuadTreeRaycastResultL<Integer> r : this.ray_area_results_l) {
          if (this.items.containsKey(r.item())) {
            final Item item = this.items.get(r.item());
            final Vector2L lower = item.area.lower();

            g.drawRect(
              (int) lower.x(),
              (int) lower.y(),
              (int) item.area.width(),
              (int) item.area.height());
            g.drawString(
              Double.toString(r.distance()),
              (int) lower.x() + 2,
              (int) lower.y() + 16);
          }
        }
      }
    }
  }

  private static final class Item
  {
    private final Integer item;
    private final BoundingAreaL area;

    Item(
      final Integer in_item,
      final BoundingAreaL in_area)
    {
      this.item = NullCheck.notNull(in_item, "Item");
      this.area = NullCheck.notNull(in_area, "Area");
    }
  }
}
