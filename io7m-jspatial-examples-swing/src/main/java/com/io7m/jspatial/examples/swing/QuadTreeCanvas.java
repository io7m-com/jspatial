/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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
import com.io7m.jspatial.api.TreeVisitResult;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationD;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import com.io7m.jspatial.api.quadtrees.QuadTreeDType;
import com.io7m.jspatial.api.quadtrees.QuadTreeLType;
import com.io7m.jspatial.examples.swing.LogMessageType.Severity;
import com.io7m.jspatial.implementation.QuadTreeD;
import com.io7m.jspatial.implementation.QuadTreeL;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2L;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The tree canvas.
 */

final class QuadTreeCanvas extends JPanel
{
  private final Subscription subscription;
  private final Map<Integer, Item> items;
  private final Observer<LogMessage> log_messages;
  private Function<QuadTreeCommandType, Unit> on_message_cases;
  private QuadTreeLType<Integer> tree_l;
  private QuadTreeDType<Integer> tree_d;
  private QuadTreeKind kind;

  QuadTreeCanvas(
    final Observable<QuadTreeCommandType> in_tree_commands,
    final Observer<LogMessage> in_log_messages)
  {
    this.kind = QuadTreeKind.LONG_INTEGER;
    this.items = new HashMap<>(128);
    this.log_messages = in_log_messages;

    this.on_message_cases = QuadTreeCommandTypes.cases()
      .addObject(this::onCommandAddObject)
      .removeObject(this::onCommandRemoveObject)
      .trimQuadTree(this::onCommandTrimQuadTree)
      .createQuadTreeL(QuadTreeCanvas.this::onCommandCreateQuadTreeL)
      .createQuadTreeD(QuadTreeCanvas.this::onCommandCreateQuadTreeD);

    this.subscription = in_tree_commands.subscribe(this::onCommand);
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
    return Unit.unit();
  }

  private Unit onCommandCreateQuadTreeD(
    final QuadTreeConfigurationD config)
  {
    this.kind = QuadTreeKind.DOUBLE;
    this.tree_d = QuadTreeD.create(config);
    this.items.clear();
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
            new VectorI2D(
              (double) area.lower().getXL(), (double) area.lower().getYL()),
            new VectorI2D(
              (double) area.upper().getXL(), (double) area.upper().getYL()));
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
    final BoundingAreaL area,
    final Integer item,
    final boolean inserted)
  {
    if (inserted) {
      this.log_messages.onNext(LogMessage.of(
        Severity.INFO,
        String.format(
          "Inserted item %d (%d+%d %dx%d)", item,
          Long.valueOf(area.lower().getXL()),
          Long.valueOf(area.lower().getYL()),
          Long.valueOf(area.width()),
          Long.valueOf(area.height()))));
    } else {
      this.log_messages.onNext(LogMessage.of(
        Severity.ERROR,
        String.format(
          "Failed to insert item %d (%d+%d %dx%d)", item,
          Long.valueOf(area.lower().getXL()),
          Long.valueOf(area.lower().getYL()),
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
        final VectorI2D lower = quadrant.area().lower();

        gg.setColor(Color.GRAY);
        gg.drawRect(
          (int) lower.getXD(),
          (int) lower.getYD(),
          (int) quadrant.area().width(),
          (int) quadrant.area().height());
        return TreeVisitResult.RESULT_CONTINUE;
      });

      g.setFont(Font.decode("Monospaced 8"));

      for (final Map.Entry<Integer, Item> entry : this.items.entrySet()) {
        final Item item = entry.getValue();
        final VectorI2L lower = item.area.lower();

        if (t.contains(item.item)) {
          g.setColor(Color.WHITE);
        } else {
          g.setColor(Color.RED);
        }

        g.drawRect(
          (int) lower.getXL(),
          (int) lower.getYL(),
          (int) item.area.width(),
          (int) item.area.height());
        g.drawString(
          item.item.toString(),
          (int) lower.getXL() + 2,
          (int) lower.getYL() + 8);
      }
    }
  }

  private void drawTreeLong(final Graphics g)
  {
    final QuadTreeLType<Integer> t = this.tree_l;
    if (t != null) {

      t.iterateQuadrants(g, (gg, quadrant, depth) -> {
        final VectorI2L lower = quadrant.area().lower();

        gg.setColor(Color.GRAY);
        gg.drawRect(
          (int) lower.getXL(),
          (int) lower.getYL(),
          (int) quadrant.area().width(),
          (int) quadrant.area().height());
        return TreeVisitResult.RESULT_CONTINUE;
      });

      g.setFont(Font.decode("Monospaced 8"));

      for (final Map.Entry<Integer, Item> entry : this.items.entrySet()) {
        final Item item = entry.getValue();
        final VectorI2L lower = item.area.lower();

        if (t.contains(item.item)) {
          g.setColor(Color.WHITE);
        } else {
          g.setColor(Color.RED);
        }

        g.drawRect(
          (int) lower.getXL(),
          (int) lower.getYL(),
          (int) item.area.width(),
          (int) item.area.height());
        g.drawString(
          item.item.toString(),
          (int) lower.getXL() + 2,
          (int) lower.getYL() + 8);
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
