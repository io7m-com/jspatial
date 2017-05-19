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

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jnull.NullCheck;
import com.io7m.jregions.core.unparameterized.areas.AreaD;
import com.io7m.jregions.core.unparameterized.areas.AreaL;
import com.io7m.jspatial.api.Ray2D;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationD;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationL;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors2D;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import net.java.dev.designgridlayout.DesignGridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Font;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controls.
 */

final class QuadTreeControls extends JPanel
{
  private final BehaviorSubject<QuadTreeCommandType> events;
  private final JButton object_add;
  private final JButton tree_create;
  private final AtomicInteger pool;
  private final QuadTreeConfigurationL.Builder config_bl;
  private final QuadTreeConfigurationD.Builder config_bd;

  private final JList<Integer> objects;
  private final DefaultListModel<Integer> objects_model;
  private final JScrollPane objects_scroller;
  private final JButton object_remove;
  private final JButton tree_trim;
  private final JCheckBox tree_trim_remove;
  private final QuadTreeComboBox<QuadTreeKind> tree_kind;

  private final SpinnerNumberModel object_width_model;
  private final JSpinner object_width;
  private final SpinnerNumberModel object_height_model;
  private final JSpinner object_height;
  private final SpinnerNumberModel object_x_model;
  private final JSpinner object_x;
  private final SpinnerNumberModel object_y_model;
  private final JSpinner object_y;

  private final SpinnerNumberModel tree_width_model;
  private final JSpinner tree_width;
  private final SpinnerNumberModel tree_height_model;
  private final JSpinner tree_height;
  private final SpinnerNumberModel tree_x_model;
  private final JSpinner tree_x;
  private final SpinnerNumberModel tree_y_model;
  private final JSpinner tree_y;
  private final SpinnerNumberModel tree_qmin_width_model;
  private final SpinnerNumberModel tree_qmin_height_model;
  private final JSpinner tree_qmin_width;
  private final JSpinner tree_qmin_height;

  private final SpinnerNumberModel area_query_width_model;
  private final JSpinner area_query_width;
  private final SpinnerNumberModel area_query_height_model;
  private final JSpinner area_query_height;
  private final SpinnerNumberModel area_query_x_model;
  private final JSpinner area_query_x;
  private final SpinnerNumberModel area_query_y_model;
  private final JSpinner area_query_y;
  private final JButton area_query_run;
  private final JCheckBox area_query_overlapping;

  private final JSpinner objects_random_count;
  private final JButton objects_random_add;
  private final SpinnerNumberModel objects_random_count_model;

  private final SpinnerNumberModel ray_query_x0_model;
  private final JSpinner ray_query_x0;
  private final SpinnerNumberModel ray_query_y0_model;
  private final JSpinner ray_query_y0;
  private final SpinnerNumberModel ray_query_x1_model;
  private final JSpinner ray_query_x1;
  private final SpinnerNumberModel ray_query_y1_model;
  private final JSpinner ray_query_y1;
  private final JButton ray_query_run;

  QuadTreeControls()
  {
    this.pool = new AtomicInteger(0);
    this.config_bl = QuadTreeConfigurationL.builder();
    this.config_bd = QuadTreeConfigurationD.builder();

    this.objects_model = new DefaultListModel<>();
    this.objects = new JList<>(this.objects_model);
    this.objects.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    this.objects_scroller = new JScrollPane(this.objects);
    this.objects_scroller.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.objects_scroller.setHorizontalScrollBarPolicy(
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    this.object_width_model = new SpinnerNumberModel(64, 1, 1024, 1);
    this.object_width = new JSpinner(this.object_width_model);
    this.object_height_model = new SpinnerNumberModel(64, 1, 1024, 1);
    this.object_height = new JSpinner(this.object_height_model);
    this.object_x_model = new SpinnerNumberModel(16, 1, 1024, 1);
    this.object_x = new JSpinner(this.object_x_model);
    this.object_y_model = new SpinnerNumberModel(16, 1, 1024, 1);
    this.object_y = new JSpinner(this.object_y_model);

    this.object_add = new JButton("Add");
    this.object_remove = new JButton("Remove");
    this.object_remove.setEnabled(false);

    this.objects_random_count_model = new SpinnerNumberModel(10, 1, 50, 1);
    this.objects_random_count = new JSpinner(this.objects_random_count_model);
    this.objects_random_add = new JButton("Add");

    this.tree_kind = new QuadTreeComboBox<>(QuadTreeKind.class);
    this.tree_width_model = new SpinnerNumberModel(512, 1, 1024, 1);
    this.tree_width = new JSpinner(this.tree_width_model);
    this.tree_height_model = new SpinnerNumberModel(512, 1, 1024, 1);
    this.tree_height = new JSpinner(this.tree_height_model);
    this.tree_x_model = new SpinnerNumberModel(16, 1, 1024, 1);
    this.tree_x = new JSpinner(this.tree_x_model);
    this.tree_y_model = new SpinnerNumberModel(16, 1, 1024, 1);
    this.tree_y = new JSpinner(this.tree_y_model);

    this.tree_qmin_width_model = new SpinnerNumberModel(8, 1, 1024, 1);
    this.tree_qmin_width = new JSpinner(this.tree_qmin_width_model);
    this.tree_qmin_height_model = new SpinnerNumberModel(8, 1, 1024, 1);
    this.tree_qmin_height = new JSpinner(this.tree_qmin_height_model);

    this.tree_trim_remove = new JCheckBox();
    this.tree_trim_remove.setSelected(false);

    this.tree_create = new JButton("Create");
    this.tree_trim = new JButton("Trim");

    this.area_query_width_model = new SpinnerNumberModel(400, 1, 1024, 1);
    this.area_query_width = new JSpinner(this.area_query_width_model);
    this.area_query_height_model = new SpinnerNumberModel(400, 1, 1024, 1);
    this.area_query_height = new JSpinner(this.area_query_height_model);
    this.area_query_x_model = new SpinnerNumberModel(32, 1, 1024, 1);
    this.area_query_x = new JSpinner(this.area_query_x_model);
    this.area_query_y_model = new SpinnerNumberModel(32, 1, 1024, 1);
    this.area_query_y = new JSpinner(this.area_query_y_model);
    this.area_query_overlapping = new JCheckBox();
    this.area_query_run = new JButton("Query");

    this.ray_query_x0_model = new SpinnerNumberModel(2, 1, 1024, 1);
    this.ray_query_x0 = new JSpinner(this.ray_query_x0_model);
    this.ray_query_y0_model = new SpinnerNumberModel(2, 1, 1024, 1);
    this.ray_query_y0 = new JSpinner(this.ray_query_y0_model);
    this.ray_query_x1_model = new SpinnerNumberModel(254, 1, 1024, 1);
    this.ray_query_x1 = new JSpinner(this.ray_query_x1_model);
    this.ray_query_y1_model = new SpinnerNumberModel(254, 1, 1024, 1);
    this.ray_query_y1 = new JSpinner(this.ray_query_y1_model);
    this.ray_query_run = new JButton("Query");

    final DesignGridLayout dg = new DesignGridLayout(this);
    this.layout(dg);

    this.config_bl.setArea(AreaL.of(
      this.tree_x_model.getNumber().longValue(),
      this.tree_width_model.getNumber().longValue(),
      this.tree_y_model.getNumber().longValue(),
      this.tree_height_model.getNumber().longValue()));

    this.events = BehaviorSubject.createDefault(
      QuadTreeCommandTypes.createQuadTreeL(this.config_bl.build()));

    this.object_add.addActionListener(
      e -> this.onObjectAdd());
    this.objects.addListSelectionListener(
      e -> this.onObjectListChanged());
    this.objects_random_add.addActionListener(
      e -> this.onObjectsRandomAdd());
    this.object_remove.addActionListener(
      e -> this.onObjectRemove());
    this.tree_create.addActionListener(
      e -> this.onTreeCreate());
    this.tree_trim.addActionListener(
      e -> this.events.onNext(QuadTreeCommandTypes.trimQuadTree()));
    this.area_query_run.addActionListener(
      e -> this.onAreaQuery());
    this.ray_query_run.addActionListener(
      e -> this.onRayQuery());
  }

  private static long randomLong(
    final long min,
    final long max)
  {
    final long range = max - min;
    final long r = (long) (Math.random() * (double) range);
    return r + min;
  }

  private static JLabel largerLabel(final String text)
  {
    final JLabel label = new JLabel(text);
    final Font f = label.getFont();
    label.setFont(f.deriveFont(f.getSize2D() * 1.3f));
    return label;
  }

  private void layout(final DesignGridLayout dg)
  {
    dg.row().left().add(largerLabel("Tree")).fill();
    dg.emptyRow();
    dg.row().grid(new JLabel("Kind")).add(this.tree_kind);
    dg.row().grid(new JLabel("X")).add(this.tree_x);
    dg.row().grid(new JLabel("Y")).add(this.tree_y);
    dg.row().grid(new JLabel("Width")).add(this.tree_width);
    dg.row().grid(new JLabel("Height")).add(this.tree_height);
    dg.row().grid(new JLabel("Limit Q Width")).add(this.tree_qmin_width);
    dg.row().grid(new JLabel("Limit Q Height")).add(this.tree_qmin_height);
    dg.emptyRow();
    dg.row().grid(new JLabel("Trim Removals")).add(this.tree_trim_remove);
    dg.emptyRow();
    dg.row().center().add(this.tree_create).fill();
    dg.row().center().add(this.tree_trim).fill();
    dg.emptyRow();

    dg.row().left().add(largerLabel("Objects")).fill();
    dg.emptyRow();
    dg.row().grid(new JLabel("X")).add(this.object_x);
    dg.row().grid(new JLabel("Y")).add(this.object_y);
    dg.row().grid(new JLabel("Width")).add(this.object_width);
    dg.row().grid(new JLabel("Height")).add(this.object_height);
    dg.row().center().add(this.object_add).fill();
    dg.row().center().add(this.objects_scroller).fill();
    dg.row().center().add(this.object_remove).fill();
    dg.emptyRow();

    dg.row().left().add(largerLabel("Random Objects")).fill();
    dg.emptyRow();
    dg.row().grid(new JLabel("Count")).add(this.objects_random_count);
    dg.row().center().add(this.objects_random_add).fill();
    dg.emptyRow();

    dg.row().left().add(largerLabel("Area Query")).fill();
    dg.emptyRow();
    dg.row().grid(new JLabel("X")).add(this.area_query_x);
    dg.row().grid(new JLabel("Y")).add(this.area_query_y);
    dg.row().grid(new JLabel("Width")).add(this.area_query_width);
    dg.row().grid(new JLabel("Height")).add(this.area_query_height);
    dg.emptyRow();
    dg.row().grid(new JLabel("Overlapping")).add(this.area_query_overlapping);
    dg.emptyRow();
    dg.row().center().add(this.area_query_run).fill();
    dg.emptyRow();

    dg.row().left().add(largerLabel("Ray Query")).fill();
    dg.emptyRow();
    dg.row()
      .grid(new JLabel("X0")).add(this.ray_query_x0)
      .grid(new JLabel("Y0")).add(this.ray_query_y0);
    dg.row()
      .grid(new JLabel("X1")).add(this.ray_query_x1)
      .grid(new JLabel("Y1")).add(this.ray_query_y1);
    dg.emptyRow();
    dg.row().center().add(this.ray_query_run).fill();
    dg.emptyRow();
  }

  private void onObjectListChanged()
  {
    final Integer selected = this.objects.getSelectedValue();
    this.object_remove.setEnabled(selected != null);
  }

  private void onObjectsRandomAdd()
  {
    final int count = this.objects_random_count_model.getNumber().intValue();
    Preconditions.checkPreconditionI(
      count, count >= 0, x -> "Count must be non-negative");

    final long x_base = this.object_x_model.getNumber().longValue();
    final long y_base = this.object_y_model.getNumber().longValue();
    final long w_base = this.object_width_model.getNumber().longValue();
    final long h_base = this.object_height_model.getNumber().longValue();

    final long x_max =
      this.tree_x_model.getNumber().longValue()
        + this.tree_width_model.getNumber().longValue();
    final long y_max =
      this.tree_y_model.getNumber().longValue()
        + this.tree_height_model.getNumber().longValue();

    for (int index = 0; index < count; ++index) {
      final long x0 = randomLong(x_base, x_max);
      final long y0 = randomLong(y_base, y_max);
      final long w = randomLong(1L, w_base);
      final long h = randomLong(1L, h_base);
      final long x1 = x0 + w;
      final long y1 = y0 + h;

      final AreaL area = AreaL.of(x0, x1, y0, y1);

      final Integer item = Integer.valueOf(this.pool.getAndIncrement());
      this.objects_model.addElement(item);
      this.events.onNext(QuadTreeCommandTypes.addObject(area, item));
    }
  }

  private void onObjectRemove()
  {
    final List<Integer> range = this.objects.getSelectedValuesList();
    for (final Integer o : range) {
      this.objects_model.removeElement(o);
      this.events.onNext(QuadTreeCommandTypes.removeObject(o));
    }
  }

  private void onAreaQuery()
  {
    final long x0 = this.area_query_x_model.getNumber().longValue();
    final long y0 = this.area_query_y_model.getNumber().longValue();
    final long x1 = x0 + this.area_query_width_model.getNumber().longValue();
    final long y1 = y0 + this.area_query_height_model.getNumber().longValue();

    final AreaL area_l =
      AreaL.of(x0, x1, y0, y1);
    final AreaD area_d =
      AreaD.of((double) x0, (double) x1, (double) y0, (double) y1);

    this.events.onNext(QuadTreeCommandTypes.areaQuery(
      area_l,
      area_d,
      this.area_query_overlapping.isSelected()));
  }

  private void onRayQuery()
  {
    final double x0 = (double) this.ray_query_x0_model.getNumber().longValue();
    final double y0 = (double) this.ray_query_y0_model.getNumber().longValue();
    final double x1 = (double) this.ray_query_x1_model.getNumber().longValue();
    final double y1 = (double) this.ray_query_y1_model.getNumber().longValue();

    final Vector2D p0 = Vector2D.of(x0, y0);
    final Vector2D p1 = Vector2D.of(x1, y1);

    final Vector2D origin =
      Vector2D.of(x0, y0);
    final Vector2D direction =
      Vectors2D.normalize(Vectors2D.subtract(p1, p0));

    final Ray2D ray = Ray2D.of(origin, direction);
    this.events.onNext(QuadTreeCommandTypes.rayQuery(ray));
  }

  private void onTreeCreate()
  {
    final long x0 = this.tree_x_model.getNumber().longValue();
    final long y0 = this.tree_y_model.getNumber().longValue();
    final long x1 = x0 + this.tree_width_model.getNumber().longValue();
    final long y1 = y0 + this.tree_height_model.getNumber().longValue();

    final AreaL area_l =
      AreaL.of(x0, x1, y0, y1);
    final AreaD area_d =
      AreaD.of((double) x0, (double) x1, (double) y0, (double) y1);

    this.config_bl.setMinimumQuadrantHeight(
      this.tree_qmin_height_model.getNumber().longValue());
    this.config_bl.setMinimumQuadrantWidth(
      this.tree_qmin_width_model.getNumber().longValue());
    this.config_bl.setArea(area_l);
    this.config_bl.setTrimOnRemove(this.tree_trim_remove.isSelected());

    this.config_bd.setMinimumQuadrantHeight(
      this.tree_qmin_height_model.getNumber().doubleValue());
    this.config_bd.setMinimumQuadrantWidth(
      this.tree_qmin_width_model.getNumber().doubleValue());
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
    final long x0 = this.object_x_model.getNumber().longValue();
    final long y0 = this.object_y_model.getNumber().longValue();
    final long x1 = x0 + this.object_width_model.getNumber().longValue();
    final long y1 = y0 + this.object_height_model.getNumber().longValue();

    final AreaL area = AreaL.of(x0, x1, y0, y1);
    final Integer item = Integer.valueOf(this.pool.getAndIncrement());
    this.objects_model.addElement(item);
    this.events.onNext(QuadTreeCommandTypes.addObject(area, item));
  }

  Observable<QuadTreeCommandType> events()
  {
    return this.events;
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
