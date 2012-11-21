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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.functional.Function;
import com.io7m.jaux.functional.PartialFunction;
import com.io7m.jaux.functional.Unit;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorReadable2I;

/**
 * Quadtree viewer demonstrating tree operations.
 */

public final class QuadTreeViewer implements Runnable
{
  /**
   * A trivial 2D canvas that draws the current quadtree.
   */

  private class TreeCanvas extends Canvas
  {
    private static final long serialVersionUID = 474729240519661207L;

    public TreeCanvas()
    {
      // Nothing.
    }

    @SuppressWarnings("synthetic-access") @Override public void paint(
      final Graphics g)
    {
      try {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);

        QuadTreeViewer.this.quadtree
          .quadTreeTraverse(new QuadTreeTraversal() {
            @Override public void visit(
              @SuppressWarnings("unused") final int depth,
              final @Nonnull VectorReadable2I lower,
              final @Nonnull VectorReadable2I upper)
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

        QuadTreeViewer.this.quadtree
          .quadTreeIterateObjects(new Function<Rectangle, Boolean>() {
            @Override public Boolean call(
              final Rectangle x)
            {
              final VectorReadable2I lower = x.boundingAreaLower();
              final VectorReadable2I upper = x.boundingAreaUpper();

              g.setColor(Color.GREEN);
              g.drawRect(
                lower.getXI(),
                lower.getYI(),
                (upper.getXI() - lower.getXI()) + 1,
                (upper.getYI() - lower.getYI()) + 1);

              return Boolean.TRUE;
            }
          });

        if (QuadTreeViewer.this.area_selected) {
          g.setColor(Color.RED);

          {
            final VectorReadable2I lower =
              QuadTreeViewer.this.area_select.boundingAreaLower();
            final VectorReadable2I upper =
              QuadTreeViewer.this.area_select.boundingAreaUpper();

            g.drawRect(
              lower.getXI(),
              lower.getYI(),
              (upper.getXI() - lower.getXI()) + 1,
              (upper.getYI() - lower.getYI()) + 1);
          }

          for (final Rectangle r : QuadTreeViewer.this.area_select_results) {
            final VectorReadable2I lower = r.boundingAreaLower();
            final VectorReadable2I upper = r.boundingAreaUpper();

            g.drawRect(
              lower.getXI(),
              lower.getYI(),
              (upper.getXI() - lower.getXI()) + 1,
              (upper.getYI() - lower.getYI()) + 1);
          }
        }

        if (QuadTreeViewer.this.raycast_active) {
          g.setColor(Color.BLUE);

          final int origin_x = (int) QuadTreeViewer.this.raycast_ray.origin.x;
          final int origin_y = (int) QuadTreeViewer.this.raycast_ray.origin.y;

          int target_x = origin_x;
          target_x += (QuadTreeViewer.this.raycast_ray.direction.x) * 1000;
          int target_y = origin_y;
          target_y += (QuadTreeViewer.this.raycast_ray.direction.y) * 1000;

          g.drawLine(origin_x, origin_y, target_x, target_y);

          for (final QuadTreeRaycastResult<Rectangle> rr : QuadTreeViewer.this.raycast_selection) {
            final Rectangle r = rr.getObject();
            final VectorReadable2I lower = r.boundingAreaLower();
            final VectorReadable2I upper = r.boundingAreaUpper();

            g.drawRect(
              lower.getXI(),
              lower.getYI(),
              (upper.getXI() - lower.getXI()) + 1,
              (upper.getYI() - lower.getYI()) + 1);
          }
        }

      } catch (final Exception e) {
        QuadTreeViewer.fatal(e);
      } catch (final ConstraintError e) {
        QuadTreeViewer.fatal(e);
      }
    }
  }

  private static final int CANVAS_SIZE_X = 512;
  private static final int CANVAS_SIZE_Y = 512;
  private static final int TREE_SIZE_X   = 512;
  private static final int TREE_SIZE_Y   = 512;

  static <T extends Throwable> void error(
    final T e)
  {
    e.printStackTrace();
  }

  static <T extends Throwable> void fatal(
    final T e)
  {
    e.printStackTrace();
    System.exit(1);
  }

  public static void main(
    final String args[])
  {
    SwingUtilities.invokeLater(new Runnable() {
      @SuppressWarnings("synthetic-access") @Override public void run()
      {
        try {
          final QuadTreeViewer v = new QuadTreeViewer();
          final JFrame frame = new JFrame("QuadTreeViewer");
          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame.getContentPane().add(v.getPanel());
          frame.pack();
          frame.setVisible(true);
          v.run();
        } catch (final ConstraintError e) {
          QuadTreeViewer.fatal(e);
        }
      }
    });
  }

  private final TreeCanvas                                                                        canvas;
  private final JPanel                                                                            panel;
  private final JPanel                                                                            canvas_container;
  private final JPanel                                                                            control_container;

  private final SortedSet<QuadTreeRaycastResult<Rectangle>>                                       raycast_selection;
  private RayI2D                                                                                  raycast_ray;
  private boolean                                                                                 raycast_active;

  private Rectangle                                                                               area_select;
  private final SortedSet<Rectangle>                                                              area_select_results;
  private boolean                                                                                 area_selected;

  protected QuadTreeInterface<Rectangle>                                                          quadtree;
  protected final HashMap<String, PartialFunction<Unit, QuadTreeInterface<Rectangle>, Throwable>> quadtree_constructors;
  protected final QuadTreeConfig                                                                  quadtree_config;

  private final AtomicLong                                                                        current_id;

  public QuadTreeViewer()
    throws ConstraintError
  {
    this.current_id = new AtomicLong();

    this.quadtree_config = new QuadTreeConfig();
    this.quadtree_config.setSizeX(QuadTreeViewer.TREE_SIZE_X);
    this.quadtree_config.setSizeY(QuadTreeViewer.TREE_SIZE_Y);

    this.quadtree = new QuadTreeBasic<Rectangle>(this.quadtree_config);
    this.quadtree_constructors =
      new HashMap<String, PartialFunction<Unit, QuadTreeInterface<Rectangle>, Throwable>>();
    this.initializeConstructors();

    this.area_select = new Rectangle(0, VectorI2I.ZERO, VectorI2I.ZERO);
    this.area_select_results = new TreeSet<Rectangle>();
    this.area_selected = false;

    this.raycast_selection = new TreeSet<QuadTreeRaycastResult<Rectangle>>();
    this.raycast_ray = null;
    this.raycast_active = false;

    this.canvas_container = new JPanel();
    this.canvas_container.setLayout(new BoxLayout(
      this.canvas_container,
      BoxLayout.Y_AXIS));

    this.canvas = new TreeCanvas();
    this.canvas.setPreferredSize(new Dimension(
      QuadTreeViewer.CANVAS_SIZE_X,
      QuadTreeViewer.CANVAS_SIZE_Y));
    this.canvas.setMinimumSize(new Dimension(
      QuadTreeViewer.CANVAS_SIZE_X,
      QuadTreeViewer.CANVAS_SIZE_Y));
    this.canvas.setMaximumSize(new Dimension(
      QuadTreeViewer.CANVAS_SIZE_X,
      QuadTreeViewer.CANVAS_SIZE_Y));
    this.canvas_container.add(this.canvas);

    this.control_container = new JPanel();
    this.control_container.setLayout(new BoxLayout(
      this.control_container,
      BoxLayout.Y_AXIS));

    this.control_container.add(this.makeImplementationControls());
    this.control_container.add(this.makeInsertControls());
    this.control_container.add(this.makeRaycastControls());
    this.control_container.add(this.makeSelectControls());
    this.control_container.add(this.makeViewerControls());

    this.panel = new JPanel();
    this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.X_AXIS));
    this.panel.add(this.canvas_container);
    this.panel.add(this.control_container);
  }

  /**
   * Delete all objects in the current quadtree.
   */

  protected void commandClear()
  {
    this.quadtree.quadTreeClear();
    this.commandSelectReset();
    this.raycast_active = false;
    this.canvas.repaint();
  }

  /**
   * Insert the given rectangle into the current quadtree.
   */

  protected void commandInsert(
    final Rectangle r)
  {
    try {
      System.err.println("Insert: " + r);
      QuadTreeViewer.this.quadtree.quadTreeInsert(r);
      this.canvas.repaint();
    } catch (final IllegalArgumentException x) {
      QuadTreeViewer.error(x);
    } catch (final ConstraintError x) {
      QuadTreeViewer.error(x);
    }
  }

  /**
   * Quit the program.
   */

  @SuppressWarnings("static-method") protected void commandQuit()
  {
    System.exit(0);
  }

  /**
   * Fill the current quadtree with 100 randomly sized rectangles.
   */

  protected void commandRandomize()
  {
    try {
      for (int i = 0; i < 100; ++i) {
        final int width = (int) (2 + (Math.random() * 16));
        final int height = (int) (2 + (Math.random() * 16));

        final int x0 = (int) (Math.random() * QuadTreeViewer.TREE_SIZE_X);
        final int y0 = (int) (Math.random() * QuadTreeViewer.TREE_SIZE_Y);

        final int x1 = Math.min(x0 + width, QuadTreeViewer.TREE_SIZE_X - 1);
        final int y1 = Math.min(y0 + height, QuadTreeViewer.TREE_SIZE_Y - 1);

        final VectorI2I r0 = new VectorI2I(x0, y0);
        final VectorI2I r1 = new VectorI2I(x1, y1);

        this.quadtree.quadTreeInsert(new Rectangle(this.current_id
          .incrementAndGet(), r0, r1));
      }
      this.canvas.repaint();
    } catch (final ConstraintError e) {
      QuadTreeViewer.fatal(e);
    }
  }

  /**
   * Cast the given ray through the current quadtree.
   */

  protected void commandRaycast(
    final RayI2D ray)
  {
    System.err.println("Cast: " + ray);

    try {
      this.raycast_selection.clear();
      this.quadtree.quadTreeQueryRaycast(ray, this.raycast_selection);
      this.raycast_active = true;
      this.raycast_ray = ray;
      this.canvas.repaint();
    } catch (final ConstraintError e) {
      QuadTreeViewer.error(e);
    }
  }

  /**
   * Select objects within the current quadtree.
   */

  protected void commandSelect(
    final Rectangle c,
    final boolean containing)
  {
    System.err.println("Select: "
      + c
      + " "
      + (containing ? "(containing)" : "(overlapping)"));

    try {
      this.commandSelectReset();

      this.area_select = c;

      if (containing) {
        this.quadtree
          .quadTreeQueryAreaContaining(c, this.area_select_results);
      } else {
        this.quadtree.quadTreeQueryAreaOverlapping(
          c,
          this.area_select_results);
      }
      this.area_selected = true;
      this.canvas.repaint();
    } catch (final ConstraintError e) {
      QuadTreeViewer.error(e);
    }
  }

  protected void commandSelectImplementation(
    final PartialFunction<Unit, QuadTreeInterface<Rectangle>, Throwable> f)
    throws Throwable
  {
    this.quadtree = f.call(new Unit());
    this.commandClear();
  }

  private Component getPanel()
  {
    return this.panel;
  }

  /**
   * Initialize the map of names to quadtree constructors.
   */

  private void initializeConstructors()
  {
    this.quadtree_constructors.put(
      "QuadTreeBasic",
      new PartialFunction<Unit, QuadTreeInterface<Rectangle>, Throwable>() {
        @SuppressWarnings("unused") @Override public
          QuadTreeInterface<Rectangle>
          call(
            final Unit _)
            throws ConstraintError
        {
          return new QuadTreeBasic<Rectangle>(
            QuadTreeViewer.this.quadtree_config);
        }
      });

    this.quadtree_constructors.put(
      "QuadTreeSD",
      new PartialFunction<Unit, QuadTreeInterface<Rectangle>, Throwable>() {
        @SuppressWarnings("unused") @Override public
          QuadTreeInterface<Rectangle>
          call(
            final Unit _)
            throws ConstraintError
        {
          return new QuadTreeSD<Rectangle>(
            QuadTreeViewer.this.quadtree_config);
        }
      });

    this.quadtree_constructors.put(
      "QuadTreePrune",
      new PartialFunction<Unit, QuadTreeInterface<Rectangle>, Throwable>() {
        @SuppressWarnings("unused") @Override public
          QuadTreeInterface<Rectangle>
          call(
            final Unit _)
            throws ConstraintError
        {
          return new QuadTreePrune<Rectangle>(
            QuadTreeViewer.this.quadtree_config);
        }
      });

    this.quadtree_constructors.put(
      "QuadTreeLimit",
      new PartialFunction<Unit, QuadTreeInterface<Rectangle>, Throwable>() {
        @SuppressWarnings("unused") @Override public
          QuadTreeInterface<Rectangle>
          call(
            final Unit _)
            throws ConstraintError
        {
          return new QuadTreeLimit<Rectangle>(
            QuadTreeViewer.this.quadtree_config);
        }
      });
  }

  /**
   * Construct the panel of controls that select the desired quadtree
   * implementation.
   */

  private JPanel makeImplementationControls()
  {
    final JPanel p = new JPanel();
    p.setBorder(BorderFactory.createTitledBorder("Implementation"));

    final Vector<String> names = new Vector<String>();
    for (final String key : this.quadtree_constructors.keySet()) {
      names.add(key);
    }

    final JComboBox cb = new JComboBox(names);

    cb.addActionListener(new ActionListener() {
      @Override public void actionPerformed(
        final ActionEvent e)
      {
        try {
          final JComboBox box = (JComboBox) e.getSource();
          final String name = (String) box.getSelectedItem();
          if (QuadTreeViewer.this.quadtree_constructors.containsKey(name)) {
            final PartialFunction<Unit, QuadTreeInterface<Rectangle>, Throwable> f =
              QuadTreeViewer.this.quadtree_constructors.get(name);
            QuadTreeViewer.this.commandSelectImplementation(f);
          }
        } catch (final Throwable x) {
          QuadTreeViewer.fatal(x);
        }
      }

    });

    p.add(cb);
    return p;
  }

  /**
   * Construct the panel of controls that deal with object insertion.
   */

  private JPanel makeInsertControls()
  {
    final JButton insert = new JButton("Insert");

    final JTextField input_x0 = new JTextField("0");
    final JTextField input_y0 = new JTextField("0");
    final JTextField input_x1 =
      new JTextField("" + (QuadTreeViewer.TREE_SIZE_X - 1));
    final JTextField input_y1 =
      new JTextField("" + (QuadTreeViewer.TREE_SIZE_Y - 1));

    input_x0.setColumns(3);
    input_y0.setColumns(3);
    input_x1.setColumns(3);
    input_y1.setColumns(3);

    insert.addActionListener(new ActionListener() {
      @SuppressWarnings({ "unused", "synthetic-access" }) @Override public
        void
        actionPerformed(
          final ActionEvent _)
      {
        final int x0 = Integer.parseInt(input_x0.getText());
        final int x1 = Integer.parseInt(input_x1.getText());
        final int y0 = Integer.parseInt(input_y0.getText());
        final int y1 = Integer.parseInt(input_y1.getText());

        final Rectangle c =
          new Rectangle(
            QuadTreeViewer.this.current_id.getAndIncrement(),
            new VectorI2I(x0, y0),
            new VectorI2I(x1, y1));

        QuadTreeViewer.this.commandInsert(c);
      }
    });

    final JPanel controls_insert = new JPanel();
    controls_insert.setBorder(BorderFactory.createTitledBorder("Insert"));
    controls_insert.setLayout(new GridBagLayout());

    final Insets padding = new Insets(4, 8, 4, 8);

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 0;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_insert.add(new JLabel("x0"), c);
      c.gridx = c.gridx + 1;
      controls_insert.add(input_x0, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 1;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_insert.add(new JLabel("y0"), c);
      c.gridx = c.gridx + 1;
      controls_insert.add(input_y0, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 2;
      c.gridy = 0;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_insert.add(new JLabel("x1"), c);
      c.gridx = c.gridx + 1;
      controls_insert.add(input_x1, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 2;
      c.gridy = 1;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_insert.add(new JLabel("y1"), c);
      c.gridx = c.gridx + 1;
      controls_insert.add(input_y1, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 4;
      c.gridy = 0;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_insert.add(insert, c);
    }

    return controls_insert;
  }

  /**
   * Construct the panel of controls that deal with raycasting.
   */

  private JPanel makeRaycastControls()
  {
    final JButton cast = new JButton("Cast");

    final JPanel controls_raycast = new JPanel();
    controls_raycast.setBorder(BorderFactory.createTitledBorder("Raycast"));
    controls_raycast.setLayout(new GridBagLayout());

    final JTextField input_x0 = new JTextField("0");
    final JTextField input_y0 = new JTextField("0");
    final JTextField input_x1 =
      new JTextField("" + (QuadTreeViewer.TREE_SIZE_X - 1));
    final JTextField input_y1 =
      new JTextField("" + (QuadTreeViewer.TREE_SIZE_Y - 1));

    input_x0.setColumns(3);
    input_y0.setColumns(3);
    input_x1.setColumns(3);
    input_y1.setColumns(3);

    cast.addActionListener(new ActionListener() {
      @SuppressWarnings({ "unused" }) @Override public void actionPerformed(
        final ActionEvent _)
      {
        final int x0 = Integer.parseInt(input_x0.getText());
        final int x1 = Integer.parseInt(input_x1.getText());
        final int y0 = Integer.parseInt(input_y0.getText());
        final int y1 = Integer.parseInt(input_y1.getText());

        final VectorI2D origin = new VectorI2D(x0, y0);
        final VectorI2D target = new VectorI2D(x1, y1);
        final VectorI2D direction =
          VectorI2D.normalize(VectorI2D.subtract(target, origin));

        final RayI2D ray = new RayI2D(origin, direction);

        QuadTreeViewer.this.commandRaycast(ray);
      }
    });

    final Insets padding = new Insets(4, 8, 4, 8);

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 0;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_raycast.add(new JLabel("x0"), c);
      c.gridx = c.gridx + 1;
      controls_raycast.add(input_x0, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 1;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_raycast.add(new JLabel("y0"), c);
      c.gridx = c.gridx + 1;
      controls_raycast.add(input_y0, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 2;
      c.gridy = 0;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_raycast.add(new JLabel("x1"), c);
      c.gridx = c.gridx + 1;
      controls_raycast.add(input_x1, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 2;
      c.gridy = 1;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_raycast.add(new JLabel("y1"), c);
      c.gridx = c.gridx + 1;
      controls_raycast.add(input_y1, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 4;
      c.gridy = 0;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_raycast.add(cast, c);
    }

    return controls_raycast;
  }

  /**
   * Construct the panel of controls that deal with object selection.
   */

  private JPanel makeSelectControls()
  {
    final JButton select = new JButton("Select");
    final JButton delete = new JButton("Delete");

    final JPanel controls_select = new JPanel();
    controls_select.setBorder(BorderFactory.createTitledBorder("Select"));
    controls_select.setLayout(new GridBagLayout());

    final JTextField input_x0 = new JTextField("0");
    final JTextField input_y0 = new JTextField("0");
    final JTextField input_x1 =
      new JTextField("" + (QuadTreeViewer.TREE_SIZE_X - 1));
    final JTextField input_y1 =
      new JTextField("" + (QuadTreeViewer.TREE_SIZE_Y - 1));

    input_x0.setColumns(3);
    input_y0.setColumns(3);
    input_x1.setColumns(3);
    input_y1.setColumns(3);

    final JRadioButton input_cont = new JRadioButton("Containing");
    input_cont
      .setToolTipText("Only select objects strictly contained by this area");

    select.addActionListener(new ActionListener() {
      @SuppressWarnings({ "unused", "synthetic-access" }) @Override public
        void
        actionPerformed(
          final ActionEvent _)
      {
        final int x0 = Integer.parseInt(input_x0.getText());
        final int x1 = Integer.parseInt(input_x1.getText());
        final int y0 = Integer.parseInt(input_y0.getText());
        final int y1 = Integer.parseInt(input_y1.getText());
        final boolean containing = input_cont.isSelected();

        final Rectangle c =
          new Rectangle(
            QuadTreeViewer.this.current_id.getAndIncrement(),
            new VectorI2I(x0, y0),
            new VectorI2I(x1, y1));

        QuadTreeViewer.this.commandSelect(c, containing);
      }
    });

    delete.addActionListener(new ActionListener() {
      @SuppressWarnings({ "unused" }) @Override public void actionPerformed(
        final ActionEvent _)
      {
        QuadTreeViewer.this.commandDeleteSelected();
      }
    });

    final Insets padding = new Insets(4, 8, 4, 8);

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 0;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_select.add(new JLabel("x0"), c);
      c.gridx = c.gridx + 1;
      controls_select.add(input_x0, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 1;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_select.add(new JLabel("y0"), c);
      c.gridx = c.gridx + 1;
      controls_select.add(input_y0, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 2;
      c.gridy = 0;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_select.add(new JLabel("x1"), c);
      c.gridx = c.gridx + 1;
      controls_select.add(input_x1, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 2;
      c.gridy = 1;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_select.add(new JLabel("y1"), c);
      c.gridx = c.gridx + 1;
      controls_select.add(input_y1, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 4;
      c.gridy = 0;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_select.add(input_cont, c);
      c.gridy = 1;
      controls_select.add(select, c);
      c.gridy = 2;
      controls_select.add(delete, c);
    }

    return controls_select;
  }

  /**
   * Delete the currently selected objects (if any).
   */

  protected void commandDeleteSelected()
  {
    try {
      if (this.area_selected) {
        for (final Rectangle r : this.area_select_results) {
          final boolean deleted = this.quadtree.quadTreeRemove(r);
          if (deleted) {
            System.err.println("Deleted: " + r);
          } else {
            System.err.println("Failed to delete: " + r);
          }
        }
        this.commandSelectReset();
      }
      this.canvas.repaint();
    } catch (final ConstraintError x) {
      QuadTreeViewer.fatal(x);
    }
  }

  /**
   * Reset the current selection state.
   */

  protected void commandSelectReset()
  {
    this.area_selected = false;
    this.area_select_results.clear();
  }

  /**
   * Construct the panel of controls that deal with global viewer controls.
   */

  private JPanel makeViewerControls()
  {
    final JButton clear = new JButton("Clear");
    final JButton random = new JButton("Randomize");
    final JButton quit = new JButton("Quit");

    clear.addActionListener(new ActionListener() {
      @SuppressWarnings({ "unused" }) @Override public void actionPerformed(
        final ActionEvent e)
      {
        QuadTreeViewer.this.commandClear();
      }
    });

    quit.addActionListener(new ActionListener() {
      @SuppressWarnings("unused") @Override public void actionPerformed(
        final ActionEvent event)
      {
        QuadTreeViewer.this.commandQuit();
      }
    });

    random.addActionListener(new ActionListener() {
      @SuppressWarnings("unused") @Override public void actionPerformed(
        final ActionEvent _)
      {
        QuadTreeViewer.this.commandRandomize();
      }
    });

    final JPanel controls_viewer = new JPanel();
    controls_viewer.setBorder(BorderFactory.createTitledBorder("Viewer"));
    controls_viewer.add(random);
    controls_viewer.add(clear);
    controls_viewer.add(quit);
    return controls_viewer;
  }

  @Override public void run()
  {
    // TODO Auto-generated method stub
  }
}
