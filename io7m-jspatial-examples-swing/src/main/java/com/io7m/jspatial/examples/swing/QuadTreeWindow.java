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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.java.dev.designgridlayout.DesignGridLayout;

import com.io7m.jnull.Nullable;
import com.io7m.jspatial.BoundingAreaType;
import com.io7m.jspatial.RayI2D;
import com.io7m.jspatial.quadtrees.QuadTreeBuilder;
import com.io7m.jspatial.quadtrees.QuadTreeBuilderType;
import com.io7m.jspatial.quadtrees.QuadTreeRaycastResult;
import com.io7m.jspatial.quadtrees.QuadTreeType;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;

@SuppressWarnings({ "boxing", "synthetic-access" }) final class QuadTreeWindow extends
  JFrame
{
  private static final long                              serialVersionUID;

  static {
    serialVersionUID = -2580684679938964300L;
    TREE_SIZE_X = 512;
    TREE_SIZE_Y = 512;
  }

  private static final int                               TREE_SIZE_X;
  private static final int                               TREE_SIZE_Y;

  private final QuadTreeCanvas                           canvas;
  private final JPanel                                   canvas_container;
  private final JPanel                                   control_container;
  private final AtomicReference<QuadTreeType<Rectangle>> quad;
  private final AtomicLong                               id_pool;

  // CHECKSTYLE:OFF
  public QuadTreeWindow()
  // CHECKSTYLE:ON
  {
    final JPanel status = new JPanel();
    status.setLayout(new FlowLayout(FlowLayout.LEADING));
    final JLabel status_text = new JLabel(" ");
    status.add(status_text);

    this.id_pool = new AtomicLong(0);
    this.control_container = new JPanel();

    final JTextField insert_x0 = new JTextField("0");
    final JTextField insert_y0 = new JTextField("0");
    final JTextField insert_x1 = new JTextField("0");
    final JTextField insert_y1 = new JTextField("0");

    final Color field_default = insert_x0.getBackground();

    final JTextField ray_x0 = new JTextField("0");
    final JTextField ray_y0 = new JTextField("0");
    final JTextField ray_x1 = new JTextField("0");
    final JTextField ray_y1 = new JTextField("0");
    final JButton raycast = new JButton("Cast");

    raycast.addActionListener(new ActionListener() {
      @Override public void actionPerformed(
        final @Nullable ActionEvent e)
      {
        final QuadTreeType<Rectangle> q = QuadTreeWindow.this.quad.get();
        if (q != null) {
          try {
            final int x0 = Integer.valueOf(ray_x0.getText());
            final int y0 = Integer.valueOf(ray_y0.getText());
            final int x1 = Integer.valueOf(ray_x1.getText());
            final int y1 = Integer.valueOf(ray_y1.getText());

            final VectorI2D origin = new VectorI2D(x0, y0);
            final VectorI2D target = new VectorI2D(x1, y1);
            final VectorI2D direction =
              VectorI2D.normalize(VectorI2D.subtract(target, origin));
            final RayI2D ray = new RayI2D(origin, direction);

            final SortedSet<QuadTreeRaycastResult<Rectangle>> items =
              new TreeSet<QuadTreeRaycastResult<Rectangle>>();
            q.quadTreeQueryRaycast(ray, items);

            QuadTreeWindow.this.canvas.setRayItems(ray, items);
            QuadTreeWindow.this.canvas.repaint();

            ray_x0.setBackground(field_default);
            ray_y0.setBackground(field_default);
            ray_x1.setBackground(field_default);
            ray_y1.setBackground(field_default);
            status_text.setText(String.format(
              "Ray intersected %d items",
              items.size()));
          } catch (final NumberFormatException x) {
            ray_x0.setBackground(Color.pink);
            ray_y0.setBackground(Color.pink);
            ray_x1.setBackground(Color.pink);
            ray_y1.setBackground(Color.pink);
            status_text.setText("Error: All ray fields must be numbers");
          }
        }
      }
    });

    final JTextField area_x0 = new JTextField("0");
    final JTextField area_y0 = new JTextField("0");
    final JTextField area_x1 = new JTextField("0");
    final JTextField area_y1 = new JTextField("0");
    final JCheckBox area_contain = new JCheckBox("Contained only");
    area_contain
      .setToolTipText("Only show objects strictly contained within the area (as opposed to just overlapping)");
    final JButton area = new JButton("Query");

    area.addActionListener(new ActionListener() {
      @Override public void actionPerformed(
        final @Nullable ActionEvent e)
      {
        final QuadTreeType<Rectangle> q = QuadTreeWindow.this.quad.get();
        if (q != null) {
          try {
            final int x0 = Integer.valueOf(area_x0.getText());
            final int y0 = Integer.valueOf(area_y0.getText());
            final int x1 = Integer.valueOf(area_x1.getText());
            final int y1 = Integer.valueOf(area_y1.getText());

            final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
            final BoundingAreaType query_area =
              new Rectangle(
                Long.MIN_VALUE,
                new VectorI2I(x0, y0),
                new VectorI2I(x1, y1));

            if (area_contain.isSelected()) {
              q.quadTreeQueryAreaContaining(query_area, items);
            } else {
              q.quadTreeQueryAreaOverlapping(query_area, items);
            }

            QuadTreeWindow.this.canvas.setAreaItems(query_area, items);
            QuadTreeWindow.this.canvas.repaint();

            area_x0.setBackground(field_default);
            area_y0.setBackground(field_default);
            area_x1.setBackground(field_default);
            area_y1.setBackground(field_default);
            status_text.setText(String.format(
              "Area intersected %d items",
              items.size()));
          } catch (final NumberFormatException x) {
            area_x0.setBackground(Color.pink);
            area_y0.setBackground(Color.pink);
            area_x1.setBackground(Color.pink);
            area_y1.setBackground(Color.pink);
            status_text.setText("Error: All area fields must be numbers");
          }
        }
      }
    });

    final JTextField limit_x = new JTextField("2");
    final JTextField limit_y = new JTextField("2");
    final JCheckBox limit = new JCheckBox("Limit quadrant size");
    limit.addActionListener(new ActionListener() {
      @Override public void actionPerformed(
        final @Nullable ActionEvent e)
      {
        final boolean selected = limit.isSelected();
        limit_x.setEnabled(selected);
        limit_y.setEnabled(selected);
        limit_x.setEditable(selected);
        limit_y.setEditable(selected);
      }
    });
    limit.setSelected(false);
    limit_x.setEnabled(false);
    limit_y.setEnabled(false);

    final JCheckBox prune = new JCheckBox("Prune empty quadrants");
    prune.setSelected(false);

    final JButton insert = new JButton("Insert");
    insert.addActionListener(new ActionListener() {
      @Override public void actionPerformed(
        final @Nullable ActionEvent e)
      {
        final QuadTreeType<Rectangle> q = QuadTreeWindow.this.quad.get();
        if (q != null) {
          try {
            final int x0 = Integer.valueOf(insert_x0.getText());
            final int y0 = Integer.valueOf(insert_y0.getText());
            final int x1 = Integer.valueOf(insert_x1.getText());
            final int y1 = Integer.valueOf(insert_y1.getText());
            final VectorI2I in_lower = new VectorI2I(x0, y0);
            final VectorI2I in_upper = new VectorI2I(x1, y1);
            final Rectangle r =
              new Rectangle(
                QuadTreeWindow.this.id_pool.incrementAndGet(),
                in_lower,
                in_upper);
            q.quadTreeInsert(r);
            QuadTreeWindow.this.canvas.repaint();

            insert_x0.setBackground(field_default);
            insert_y0.setBackground(field_default);
            insert_x1.setBackground(field_default);
            insert_y1.setBackground(field_default);
            status_text.setText(" ");
          } catch (final NumberFormatException x) {
            insert_x0.setBackground(Color.pink);
            insert_y0.setBackground(Color.pink);
            insert_x1.setBackground(Color.pink);
            insert_y1.setBackground(Color.pink);
            status_text.setText("Error: All insert fields must be numbers");
          }
        }
      }
    });

    final JTextField insert_w_min = new JTextField("10");
    final JTextField insert_w_max = new JTextField("20");
    final JTextField insert_h_min = new JTextField("10");
    final JTextField insert_h_max = new JTextField("20");
    final JTextField insert_count = new JTextField("10");

    final JButton insert_random = new JButton("Insert random");
    insert_random.addActionListener(new ActionListener() {
      @Override public void actionPerformed(
        final @Nullable ActionEvent e)
      {
        final QuadTreeType<Rectangle> q = QuadTreeWindow.this.quad.get();
        if (q != null) {
          try {
            final int w_min = Integer.valueOf(insert_w_min.getText());
            final int w_max = Integer.valueOf(insert_w_max.getText());
            final int h_min = Integer.valueOf(insert_h_min.getText());
            final int h_max = Integer.valueOf(insert_h_max.getText());
            final int count = Integer.valueOf(insert_count.getText());

            for (int index = 0; index < count; ++index) {
              final VectorI2I in_lower =
                new VectorI2I(
                  (int) (Math.random() * QuadTreeWindow.TREE_SIZE_X),
                  (int) (Math.random() * QuadTreeWindow.TREE_SIZE_Y));
              final int width = (int) ((Math.random() * w_max) + w_min);
              final int height = (int) ((Math.random() * h_max) + h_min);
              final VectorI2I in_upper =
                new VectorI2I(in_lower.getXI() + width, in_lower.getYI()
                  + height);
              final Rectangle r =
                new Rectangle(
                  QuadTreeWindow.this.id_pool.incrementAndGet(),
                  in_lower,
                  in_upper);
              q.quadTreeInsert(r);
            }

            QuadTreeWindow.this.canvas.repaint();

            insert_w_min.setBackground(field_default);
            insert_w_max.setBackground(field_default);
            insert_h_min.setBackground(field_default);
            insert_h_max.setBackground(field_default);
            insert_count.setBackground(field_default);
            status_text.setText(" ");
          } catch (final NumberFormatException x) {
            insert_w_min.setBackground(Color.pink);
            insert_w_max.setBackground(Color.pink);
            insert_h_min.setBackground(Color.pink);
            insert_h_max.setBackground(Color.pink);
            insert_count.setBackground(Color.pink);
            status_text.setText("Error: All insert fields must be numbers");
          }
        }
      }
    });

    final JButton clear = new JButton("Clear");
    clear.addActionListener(new ActionListener() {
      @Override public void actionPerformed(
        final @Nullable ActionEvent e)
      {
        final QuadTreeType<Rectangle> q = QuadTreeWindow.this.quad.get();
        if (q != null) {
          q.quadTreeClear();
          QuadTreeWindow.this.canvas.reset();
          QuadTreeWindow.this.canvas.repaint();
        }
      }
    });

    final JButton reload = new JButton("Reload");
    reload.addActionListener(new ActionListener() {
      @Override public void actionPerformed(
        final @Nullable ActionEvent e)
      {
        final QuadTreeType<Rectangle> new_q =
          QuadTreeWindow.makeTree(limit_x, limit_y, limit, prune);
        QuadTreeWindow.this.quad.set(new_q);
        final String class_name = new_q.getClass().getName();
        final String message =
          String.format("Instantiated %s class", class_name);
        status_text.setText(message);
        QuadTreeWindow.this.canvas.reset();
        QuadTreeWindow.this.canvas.repaint();
      }
    });

    final DesignGridLayout dg = new DesignGridLayout(this.control_container);
    dg.row().grid(new JLabel("Insert")).add(new JSeparator());
    dg
      .row()
      .grid(new JLabel("x0"))
      .add(insert_x0)
      .add(new JLabel("y0"), insert_y0);
    dg
      .row()
      .grid(new JLabel("x1"))
      .add(insert_x1)
      .add(new JLabel("y1"), insert_y1);
    dg.row().grid().add(insert);

    dg.row().grid(new JLabel("Insert random")).add(new JSeparator());
    dg
      .row()
      .grid(new JLabel("Min width"))
      .add(insert_w_min)
      .add(new JLabel("Min height"), insert_h_min);
    dg
      .row()
      .grid(new JLabel("Max width"))
      .add(insert_w_max)
      .add(new JLabel("Max height"), insert_h_max);
    dg.row().grid(new JLabel("Count")).add(insert_count);
    dg.row().grid().add(insert_random);

    dg.row().grid(new JLabel("Area query")).add(new JSeparator());
    dg
      .row()
      .grid(new JLabel("x0"))
      .add(area_x0)
      .add(new JLabel("y0"), area_y0);
    dg
      .row()
      .grid(new JLabel("x1"))
      .add(area_x1)
      .add(new JLabel("y1"), area_y1);
    dg.row().grid().add(area_contain);
    dg.row().grid().add(area);

    dg.row().grid(new JLabel("Ray casting")).add(new JSeparator());
    dg.row().grid(new JLabel("x0")).add(ray_x0).add(new JLabel("y0"), ray_y0);
    dg.row().grid(new JLabel("x1")).add(ray_x1).add(new JLabel("y1"), ray_y1);
    dg.row().grid().add(raycast);

    dg.row().left().add(limit, new JSeparator()).fill();
    dg.row().grid(new JLabel("Minimum X")).add(limit_x);
    dg.row().grid(new JLabel("Minimum Y")).add(limit_y);
    dg.row().center().add(new JSeparator()).fill();
    dg.row().left().add(prune);
    dg.row().center().add(new JSeparator()).fill();
    dg.row().left().add(clear).add(reload);

    final QuadTreeType<Rectangle> qt =
      QuadTreeWindow.makeTree(limit_x, limit_y, limit, prune);
    this.quad = new AtomicReference<QuadTreeType<Rectangle>>(qt);

    this.canvas = new QuadTreeCanvas(this.quad);
    this.canvas_container = new JPanel();
    this.canvas_container.setLayout(new BoxLayout(
      this.canvas_container,
      BoxLayout.Y_AXIS));
    this.canvas_container.add(this.canvas);
    this.canvas.setSize(
      QuadTreeWindow.TREE_SIZE_X + 16,
      QuadTreeWindow.TREE_SIZE_Y + 16);

    final Container content = this.getContentPane();
    content.setLayout(new BorderLayout());
    content.add(this.canvas_container, BorderLayout.LINE_START);
    content.add(this.control_container, BorderLayout.LINE_END);
    content.add(status, BorderLayout.PAGE_END);
  }

  private static QuadTreeType<Rectangle> makeTree(
    final JTextField limit_x,
    final JTextField limit_y,
    final JCheckBox limit,
    final JCheckBox prune)
  {
    final QuadTreeBuilderType<Rectangle> b = QuadTreeBuilder.newBuilder();
    if (prune.isSelected()) {
      b.enablePruning();
    } else {
      b.disablePruning();
    }

    if (limit.isSelected()) {
      final String lim_xs = limit_x.getText();
      final String lim_ys = limit_y.getText();
      final Integer lim_x = Integer.valueOf(lim_xs);
      final Integer lim_y = Integer.valueOf(lim_ys);
      b.enableLimitedQuadrantSizes(lim_x.intValue(), lim_y.intValue());
    } else {
      b.disableLimitedQuadrantSizes();
    }

    b.setSize2i(QuadTreeWindow.TREE_SIZE_X, QuadTreeWindow.TREE_SIZE_Y);

    final QuadTreeType<Rectangle> qt = b.build();
    return qt;
  }
}
