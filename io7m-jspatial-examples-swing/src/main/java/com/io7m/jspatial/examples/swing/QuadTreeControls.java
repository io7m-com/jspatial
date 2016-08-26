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

import com.io7m.jnull.NullCheck;
import com.io7m.jspatial.api.BoundingAreaD;
import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationD;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2L;
import net.java.dev.designgridlayout.DesignGridLayout;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Font;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controls.
 */

final class QuadTreeControls extends JPanel
{
  private final BehaviorSubject<QuadTreeCommandType> events;
  private final JSlider object_x;
  private final JSlider object_y;
  private final JSlider object_width;
  private final JSlider object_height;
  private final JButton object_add;
  private final JSlider tree_x;
  private final JSlider tree_y;
  private final JSlider tree_width;
  private final JSlider tree_height;
  private final JButton tree_create;
  private final AtomicInteger pool;
  private final JTextField object_width_show;
  private final JTextField object_height_show;
  private final JTextField object_x_show;
  private final JTextField object_y_show;
  private final JTextField tree_width_show;
  private final JTextField tree_height_show;
  private final JTextField tree_x_show;
  private final JTextField tree_y_show;
  private final QuadTreeConfigurationL.Builder config_bl;
  private final QuadTreeConfigurationD.Builder config_bd;
  private final JSlider tree_qmin_width;
  private final JTextField tree_qmin_width_show;
  private final JSlider tree_qmin_height;
  private final JTextField tree_qmin_height_show;
  private final JList<Integer> objects;
  private final DefaultListModel<Integer> objects_model;
  private final JScrollPane objects_scroller;
  private final JButton object_remove;
  private final JButton tree_trim;
  private final JCheckBox tree_trim_remove;
  private final QuadTreeComboBox<QuadTreeKind> tree_kind;

  QuadTreeControls()
  {
    this.pool = new AtomicInteger(0);
    this.config_bl = QuadTreeConfigurationL.builder();
    this.config_bd = QuadTreeConfigurationD.builder();

    this.objects_model = new DefaultListModel<>();
    this.objects = new JList<>(this.objects_model);
    this.objects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.objects_scroller = new JScrollPane(this.objects);
    this.objects_scroller.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.objects_scroller.setHorizontalScrollBarPolicy(
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    this.object_width = new JSlider(1, 1024);
    this.object_width.setValue(2);
    this.object_width_show = new JTextField();
    this.object_width_show.setEditable(false);
    this.object_height = new JSlider(1, 1024);
    this.object_height.setValue(2);
    this.object_height_show = new JTextField();
    this.object_height_show.setEditable(false);
    this.object_x = new JSlider(1, 1024);
    this.object_x.setValue(2);
    this.object_x_show = new JTextField();
    this.object_x_show.setEditable(false);
    this.object_y = new JSlider(1, 1024);
    this.object_y.setValue(2);
    this.object_y_show = new JTextField();
    this.object_y_show.setEditable(false);

    this.object_width.addChangeListener(
      new SliderFieldUpdater(this.object_width, this.object_width_show));
    this.object_height.addChangeListener(
      new SliderFieldUpdater(this.object_height, this.object_height_show));
    this.object_x.addChangeListener(
      new SliderFieldUpdater(this.object_x, this.object_x_show));
    this.object_y.addChangeListener(
      new SliderFieldUpdater(this.object_y, this.object_y_show));

    this.object_width.setValue(32);
    this.object_height.setValue(32);
    this.object_x.setValue(16);
    this.object_y.setValue(16);

    this.object_add = new JButton("Add");
    this.object_remove = new JButton("Remove");
    this.object_remove.setEnabled(false);

    this.tree_kind = new QuadTreeComboBox<>(QuadTreeKind.class);
    this.tree_width = new JSlider(1, 1024);
    this.tree_width.setValue(2);
    this.tree_width_show = new JTextField();
    this.tree_width_show.setEditable(false);
    this.tree_height = new JSlider(1, 1024);
    this.tree_height.setValue(2);
    this.tree_height_show = new JTextField();
    this.tree_height_show.setEditable(false);
    this.tree_x = new JSlider(1, 1024);
    this.tree_x.setValue(16);
    this.tree_x_show = new JTextField();
    this.tree_x_show.setEditable(false);
    this.tree_y = new JSlider(1, 1024);
    this.tree_y.setValue(16);
    this.tree_y_show = new JTextField();
    this.tree_y_show.setEditable(false);

    this.tree_qmin_width = new JSlider(2, 128);
    this.tree_qmin_width.setValue(2);
    this.tree_qmin_width_show = new JTextField();
    this.tree_qmin_width_show.setEditable(false);

    this.tree_qmin_height = new JSlider(2, 128);
    this.tree_qmin_height.setValue(2);
    this.tree_qmin_height_show = new JTextField();
    this.tree_qmin_height_show.setEditable(false);

    this.tree_trim_remove = new JCheckBox();
    this.tree_trim_remove.setSelected(false);

    this.tree_width.addChangeListener(
      new SliderFieldUpdater(this.tree_width, this.tree_width_show));
    this.tree_height.addChangeListener(
      new SliderFieldUpdater(this.tree_height, this.tree_height_show));
    this.tree_x.addChangeListener(
      new SliderFieldUpdater(this.tree_x, this.tree_x_show));
    this.tree_y.addChangeListener(
      new SliderFieldUpdater(this.tree_y, this.tree_y_show));
    this.tree_qmin_height.addChangeListener(
      new SliderFieldUpdater(
        this.tree_qmin_height,
        this.tree_qmin_height_show));
    this.tree_qmin_width.addChangeListener(
      new SliderFieldUpdater(this.tree_qmin_width, this.tree_qmin_width_show));

    this.tree_width.setValue(512);
    this.tree_height.setValue(512);
    this.tree_x.setValue(2);
    this.tree_y.setValue(2);
    this.tree_qmin_height.setValue(2);
    this.tree_qmin_width.setValue(2);

    this.tree_create = new JButton("Create");
    this.tree_trim = new JButton("Trim");

    final DesignGridLayout dg = new DesignGridLayout(this);
    this.layout(dg);

    this.config_bl.setArea(BoundingAreaL.of(
      new VectorI2L(
        (long) this.tree_x.getValue(),
        (long) this.tree_y.getValue()),
      new VectorI2L(
        (long) this.tree_width.getValue(),
        (long) this.tree_height.getValue())));

    this.events = BehaviorSubject.create(
      QuadTreeCommandTypes.createQuadTreeL(this.config_bl.build()));

    this.object_add.addActionListener(
      e -> this.onObjectAdd());
    this.objects.addListSelectionListener(
      e -> this.onObjectListChanged());
    this.object_remove.addActionListener(
      e -> this.onObjectRemove());
    this.tree_create.addActionListener(
      e -> this.onTreeCreate());
    this.tree_trim.addActionListener(
      e -> this.events.onNext(QuadTreeCommandTypes.trimQuadTree()));
  }

  private void layout(final DesignGridLayout dg)
  {
    dg.row().left().add(QuadTreeControls.largerLabel("Tree")).fill();
    dg.emptyRow();
    dg.row().grid(new JLabel("Kind")).add(this.tree_kind);
    dg.row().grid(new JLabel("X")).add(this.tree_x, 2).add(this.tree_x_show);
    dg.row().grid(new JLabel("Y")).add(this.tree_y, 2).add(this.tree_y_show);
    dg.row().grid(new JLabel("Width")).add(
      this.tree_width,
      2).add(this.tree_width_show);
    dg.row().grid(new JLabel("Height")).add(
      this.tree_height,
      2).add(this.tree_height_show);
    dg.row().grid(new JLabel("Limit Q Width")).add(
      this.tree_qmin_width,
      2).add(this.tree_qmin_width_show);
    dg.row().grid(new JLabel("Limit Q Height")).add(
      this.tree_qmin_height,
      2).add(this.tree_qmin_height_show);
    dg.emptyRow();
    dg.row().grid(new JLabel("Trim Removals")).add(this.tree_trim_remove);
    dg.emptyRow();
    dg.row().center().add(this.tree_create).fill();
    dg.row().center().add(this.tree_trim).fill();
    dg.emptyRow();

    dg.row().left().add(QuadTreeControls.largerLabel("Objects")).fill();
    dg.emptyRow();
    dg.row().grid(new JLabel("X")).add(
      this.object_x,
      2).add(this.object_x_show);
    dg.row().grid(new JLabel("Y")).add(
      this.object_y,
      2).add(this.object_y_show);
    dg.row().grid(new JLabel("Width")).add(
      this.object_width,
      2).add(this.object_width_show);
    dg.row().grid(new JLabel("Height")).add(
      this.object_height,
      2).add(this.object_height_show);
    dg.row().center().add(this.object_add).fill();
    dg.row().center().add(this.objects_scroller).fill();
    dg.row().center().add(this.object_remove).fill();
    dg.emptyRow();
  }

  private void onObjectListChanged()
  {
    final Integer selected = this.objects.getSelectedValue();
    this.object_remove.setEnabled(selected != null);
  }

  private void onObjectRemove()
  {
    final Integer selected = this.objects.getSelectedValue();
    if (selected != null) {
      this.objects_model.removeElement(selected);
      this.events.onNext(QuadTreeCommandTypes.removeObject(selected));
    }
  }

  private void onTreeCreate()
  {
    final long x0 =
      (long) this.tree_x.getValue();
    final long y0 =
      (long) this.tree_y.getValue();
    final long x1 =
      x0 + (long) this.tree_width.getValue();
    final long y1 =
      y0 + (long) this.tree_height.getValue();

    final BoundingAreaL area_l =
      BoundingAreaL.of(
        new VectorI2L(x0, y0),
        new VectorI2L(x1, y1));

    final BoundingAreaD area_d =
      BoundingAreaD.of(
        new VectorI2D((double) x0, (double) y0),
        new VectorI2D((double) x1, (double) y1));

    this.config_bl.setMinimumQuadrantHeight(
      (long) this.tree_qmin_height.getValue());
    this.config_bl.setMinimumQuadrantWidth(
      (long) this.tree_qmin_width.getValue());
    this.config_bl.setArea(area_l);
    this.config_bl.setTrimOnRemove(this.tree_trim_remove.isSelected());

    this.config_bd.setMinimumQuadrantHeight(
      (double) this.tree_qmin_height.getValue());
    this.config_bd.setMinimumQuadrantWidth(
      (double) this.tree_qmin_width.getValue());
    this.config_bd.setArea(area_d);
    this.config_bd.setTrimOnRemove(this.tree_trim_remove.isSelected());

    QuadTreeCommandType m = null;
    switch (this.tree_kind.getSelectedItem()) {
      case LONG_INTEGER: {
        m = QuadTreeCommandTypes.createQuadTreeL(this.config_bl.build());
        break;
      }
      case DOUBLE: {
        m = QuadTreeCommandTypes.createQuadTreeD(this.config_bd.build());
        break;
      }
    }

    this.objects_model.clear();
    this.events.onNext(m);
  }

  private void onObjectAdd()
  {
    final long x0 =
      (long) this.object_x.getValue();
    final long y0 =
      (long) this.object_y.getValue();
    final long x1 =
      x0 + (long) this.object_width.getValue();
    final long y1 =
      y0 + (long) this.object_height.getValue();

    final BoundingAreaL area = BoundingAreaL.of(
      new VectorI2L(x0, y0),
      new VectorI2L(x1, y1));

    final Integer item = Integer.valueOf(this.pool.getAndIncrement());
    this.objects_model.addElement(item);
    this.events.onNext(QuadTreeCommandTypes.addObject(area, item));
  }

  Observable<QuadTreeCommandType> events()
  {
    return this.events;
  }

  private static JLabel largerLabel(final String text)
  {
    final JLabel label = new JLabel(text);
    final Font f = label.getFont();
    label.setFont(f.deriveFont(f.getSize2D() * 1.2f));
    return label;
  }

  private static final class SliderFieldUpdater implements ChangeListener
  {
    private final JSlider slider;
    private final JTextField field;

    SliderFieldUpdater(
      final JSlider in_slider,
      final JTextField in_field)
    {
      this.slider = NullCheck.notNull(in_slider, "Slider");
      this.field = NullCheck.notNull(in_field, "Field");
    }

    @Override
    public void stateChanged(final ChangeEvent e)
    {
      this.field.setText(Integer.toString(this.slider.getValue()));
    }
  }
}
