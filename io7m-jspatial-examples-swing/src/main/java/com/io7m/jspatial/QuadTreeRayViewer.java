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
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.functional.Function;
import com.io7m.jspatial.BoundingArea;
import com.io7m.jspatial.QuadTreeSimple;
import com.io7m.jspatial.QuadTreeTraversal;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorReadable2I;

public final class QuadTreeRayViewer implements Runnable
{
  static final class Selection implements BoundingArea
  {
    VectorM2I lower = new VectorM2I();
    VectorM2I upper = new VectorM2I();

    @Override public @Nonnull VectorReadable2I boundingAreaLower()
    {
      return this.lower;
    }

    @Override public @Nonnull VectorReadable2I boundingAreaUpper()
    {
      return this.upper;
    }

    int getHeight()
    {
      return (this.upper.y - this.lower.y) + 1;
    }

    int getWidth()
    {
      return (this.upper.x - this.lower.x) + 1;
    }

    @Override public String toString()
    {
      final StringBuilder builder = new StringBuilder();
      builder.append("[Selection lower=");
      builder.append(this.lower);
      builder.append(", upper=");
      builder.append(this.upper);
      builder.append("]");
      return builder.toString();
    }
  }

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
        g.setColor(Color.LIGHT_GRAY);

        g.translate(
          QuadTreeRayViewer.CANVAS_OFFSET,
          QuadTreeRayViewer.CANVAS_OFFSET);

        QuadTreeRayViewer.this.quadtree
          .quadTreeTraverse(new QuadTreeTraversal() {
            @SuppressWarnings("unused") @Override public void visit(
              final int depth,
              final @Nonnull VectorReadable2I lower,
              final @Nonnull VectorReadable2I upper)
              throws Exception
            {
              final int x = lower.getXI();
              final int y = lower.getYI();
              final int w = (upper.getXI() - lower.getXI()) + 1;
              final int h = (upper.getYI() - lower.getYI()) + 1;
              g.drawRect(x, y, w, h);
            }
          });

        QuadTreeRayViewer.this.quadtree
          .quadTreeIterateObjects(new Function<Rectangle, Boolean>() {
            @Override public Boolean call(
              final Rectangle r)
            {
              try {
                g.setColor(Color.RED);
                final int x = r.boundingAreaLower().getXI();
                final int y = r.boundingAreaLower().getYI();
                final int w =
                  (r.boundingAreaUpper().getXI() - r
                    .boundingAreaLower()
                    .getXI()) + 1;
                final int h =
                  (r.boundingAreaUpper().getYI() - r
                    .boundingAreaLower()
                    .getYI()) + 1;
                g.drawRect(x, y, w, h);
                return Boolean.valueOf(true);
              } catch (final Exception e) {
                QuadTreeRayViewer.fatal(e);
                return Boolean.FALSE;
              }
            }
          });

        g.setColor(Color.YELLOW);
        g.drawString(
          QuadTreeRayViewer.this.ray_origin.toString(),
          QuadTreeRayViewer.this.ray_origin.x,
          QuadTreeRayViewer.this.ray_origin.y);

        {
          final int x0 = QuadTreeRayViewer.this.ray_origin.x;
          final int x1 =
            (int) (QuadTreeRayViewer.this.ray_direction.x * 1024);
          final int y0 = QuadTreeRayViewer.this.ray_origin.y;
          final int y1 =
            (int) (QuadTreeRayViewer.this.ray_direction.y * 1024);
          g.drawLine(x0, y0, x1, y1);
        }

        for (final QuadTreeSimple<Rectangle>.Quadrant q : QuadTreeRayViewer.this.quadrants) {
          final VectorReadable2I lower = q.boundingAreaLower();
          final VectorReadable2I upper = q.boundingAreaUpper();

          g.setColor(Color.CYAN);
          g.drawRect(
            lower.getXI(),
            lower.getYI(),
            (upper.getXI() - lower.getXI()) + 1,
            (upper.getYI() - lower.getYI()) + 1);
        }

      } catch (final Exception e) {
        QuadTreeRayViewer.fatal(e);
      } catch (final ConstraintError e) {
        QuadTreeRayViewer.fatal(e);
      }
    }
  }

  private static final int CANVAS_OFFSET = 16;

  private static final int CANVAS_SIZE_Y = 512;
  private static final int CANVAS_SIZE_X = 512;

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
          final QuadTreeRayViewer v = new QuadTreeRayViewer();
          final JFrame frame = new JFrame("QuadTreeRayViewer");
          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame.getContentPane().add(v.getPanel());
          frame.pack();
          frame.setVisible(true);
          v.run();
        } catch (final ConstraintError e) {
          QuadTreeRayViewer.fatal(e);
        }
      }
    });
  }

  private final JPanel                           panel;
  private QuadTreeSimple<Rectangle>              quadtree;
  private final VectorM2I                        ray_origin;
  private final VectorM2I                        ray_target;
  private final VectorM2F                        ray_direction;

  final List<QuadTreeSimple<Rectangle>.Quadrant> quadrants;

  public QuadTreeRayViewer()
    throws ConstraintError
  {
    this.quadrants = new LinkedList<QuadTreeSimple<Rectangle>.Quadrant>();

    this.ray_direction = new VectorM2F();
    this.ray_origin = new VectorM2I();
    this.ray_target = new VectorM2I();
    this.quadtree =
      new QuadTreeSimple<Rectangle>(
        QuadTreeRayViewer.CANVAS_SIZE_X,
        QuadTreeRayViewer.CANVAS_SIZE_Y);

    this.panel = new JPanel();
    this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));

    {
      final JPanel canvas_panel = new JPanel();
      final TreeCanvas canvas = new TreeCanvas();
      canvas.setSize(
        QuadTreeRayViewer.CANVAS_SIZE_X
          + (QuadTreeRayViewer.CANVAS_OFFSET * 2),
        QuadTreeRayViewer.CANVAS_SIZE_Y
          + (QuadTreeRayViewer.CANVAS_OFFSET * 2));
      canvas.setMinimumSize(canvas.getSize());
      canvas_panel.add(canvas);
      this.panel.add(canvas_panel);

      canvas.addMouseListener(new MouseListener() {
        @SuppressWarnings("unused") @Override public void mouseClicked(
          final MouseEvent _)
        {
          // Unused
        }

        @SuppressWarnings("unused") @Override public void mouseEntered(
          final MouseEvent _)
        {
          // Unused
        }

        @SuppressWarnings("unused") @Override public void mouseExited(
          final MouseEvent _)
        {
          // Unused
        }

        @SuppressWarnings("synthetic-access") @Override public
          void
          mousePressed(
            final MouseEvent e)
        {
          QuadTreeRayViewer.this.ray_origin.x =
            e.getX() - QuadTreeRayViewer.CANVAS_OFFSET;
          QuadTreeRayViewer.this.ray_origin.y =
            e.getY() - QuadTreeRayViewer.CANVAS_OFFSET;
        }

        @SuppressWarnings("synthetic-access") @Override public
          void
          mouseReleased(
            final MouseEvent e)
        {
          QuadTreeRayViewer.this.ray_target.x =
            e.getX() - QuadTreeRayViewer.CANVAS_OFFSET;
          QuadTreeRayViewer.this.ray_target.y =
            e.getY() - QuadTreeRayViewer.CANVAS_OFFSET;

          QuadTreeRayViewer.this.ray_direction.x =
            QuadTreeRayViewer.this.ray_target.x
              - QuadTreeRayViewer.this.ray_origin.x;
          QuadTreeRayViewer.this.ray_direction.y =
            QuadTreeRayViewer.this.ray_target.y
              - QuadTreeRayViewer.this.ray_origin.y;

          VectorM2F.normalizeInPlace(QuadTreeRayViewer.this.ray_direction);

          QuadTreeRayViewer.this.commandRayCast(canvas);
        }
      });

      final JLabel label_lower = new JLabel("x0, y0");
      final JLabel label_upper = new JLabel("x1, y1");

      final JTextField input_x0 = new JTextField("0");
      final JTextField input_y0 = new JTextField("0");
      final JTextField input_x1 = new JTextField("7");
      final JTextField input_y1 = new JTextField("7");

      final JButton insert = new JButton("Insert");
      final JButton reset = new JButton("Reset");
      final JButton random = new JButton("Randomize");
      final JButton quit = new JButton("Quit");

      reset.addActionListener(new ActionListener() {
        @SuppressWarnings({ "unused" }) @Override public
          void
          actionPerformed(
            final ActionEvent e)
        {
          QuadTreeRayViewer.this.commandReset(canvas);
        }
      });

      quit.addActionListener(new ActionListener() {
        @SuppressWarnings("unused") @Override public void actionPerformed(
          final ActionEvent event)
        {
          System.exit(0);
        }
      });

      insert.addActionListener(new ActionListener() {
        @SuppressWarnings({ "unused" }) @Override public
          void
          actionPerformed(
            final ActionEvent _)
        {
          final int x0 = Integer.parseInt(input_x0.getText());
          final int x1 = Integer.parseInt(input_x1.getText());
          final int y0 = Integer.parseInt(input_y0.getText());
          final int y1 = Integer.parseInt(input_y1.getText());
          final Rectangle r =
            new Rectangle(new VectorI2I(x0, y0), new VectorI2I(x1, y1));
          QuadTreeRayViewer.this.commandInsert(canvas, r);
        }
      });

      random.addActionListener(new ActionListener() {
        @SuppressWarnings("unused") @Override public void actionPerformed(
          final ActionEvent _)
        {
          QuadTreeRayViewer.this.commandReset(canvas);
          QuadTreeRayViewer.this.commandRandomize();
        }
      });

      final JPanel controls_panel = new JPanel();
      controls_panel.setLayout(new GridBagLayout());

      final Insets padding = new Insets(4, 8, 4, 8);

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        controls_panel.add(label_lower, c);
      }

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipadx = 16;
        controls_panel.add(input_x0, c);
      }

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipadx = 16;
        controls_panel.add(input_y0, c);
      }

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 3;
        c.gridy = 0;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        controls_panel.add(insert, c);
      }

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        controls_panel.add(label_upper, c);
      }

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipadx = 16;
        controls_panel.add(input_x1, c);
      }

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 1;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipadx = 16;
        controls_panel.add(input_y1, c);
      }

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 3;
        c.gridy = 1;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1.0;
        controls_panel.add(new JPanel(), c);
      }

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 1;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        controls_panel.add(reset, c);
      }

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 3;
        c.gridy = 1;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        controls_panel.add(random, c);
      }

      {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 5;
        c.gridy = 1;
        c.insets = padding;
        c.gridheight = 1;
        c.gridwidth = 1;
        controls_panel.add(quit, c);
      }

      this.panel.add(controls_panel);
    }
  }

  void commandInsert(
    final Canvas canvas,
    final Rectangle rect)
  {
    try {
      QuadTreeRayViewer.this.quadtree.quadTreeInsert(rect);
      canvas.repaint();
    } catch (final IllegalArgumentException __) {
      // Ignored!
    } catch (final ConstraintError x) {
      // Ignored!
    }
  }

  void commandRandomize()
  {
    try {
      for (int index = 0; index < 10; ++index) {
        final int width = (int) (2 + (Math.random() * 64));
        final int height = (int) (2 + (Math.random() * 64));
        final int x0 = (int) (Math.random() * 512);
        final int y0 = (int) (Math.random() * 512);
        final int x1 = Math.min(x0 + width, 511);
        final int y1 = Math.min(y0 + height, 511);
        final VectorI2I r0 = new VectorI2I(x0, y0);
        final VectorI2I r1 = new VectorI2I(x1, y1);
        this.quadtree.quadTreeInsert(new Rectangle(r0, r1));
      }
    } catch (final ConstraintError e) {
      QuadTreeRayViewer.fatal(e);
    }
  }

  void commandRayCast(
    final Canvas canvas)
  {
    try {
      System.out.println("origin: " + this.ray_origin);
      System.out.println("direct: " + this.ray_direction);

      this.quadrants.clear();
      this.quadtree.quadTreeQueryRaycastQuadrants(
        this.ray_origin,
        this.ray_direction,
        this.quadrants);
      canvas.repaint();
    } catch (final ConstraintError e) {
      // Ignored!
    }
  }

  void commandReset(
    final Canvas canvas)
  {
    try {
      QuadTreeRayViewer.this.quadtree =
        new QuadTreeSimple<Rectangle>(
          QuadTreeRayViewer.CANVAS_SIZE_X,
          QuadTreeRayViewer.CANVAS_SIZE_Y);
      canvas.repaint();
    } catch (final ConstraintError x) {
      QuadTreeRayViewer.fatal(x);
    }
  }

  private Component getPanel()
  {
    return this.panel;
  }

  @Override public void run()
  {
    // TODO Auto-generated method stub
  }
}
