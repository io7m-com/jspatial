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
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorReadable2I;

public final class QuadTreeViewer implements Runnable
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
        g.setColor(Color.WHITE);

        QuadTreeViewer.this.quadtree
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

        QuadTreeViewer.this.quadtree
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
                QuadTreeViewer.fatal(e);
                return Boolean.FALSE;
              }
            }
          });

        for (final Rectangle rect : QuadTreeViewer.this.selected) {
          g.setColor(Color.GREEN);
          g.drawRect(rect.boundingAreaLower().getXI(), rect
            .boundingAreaLower()
            .getYI(), rect.getWidth(), rect.getHeight());
        }

        g.setColor(Color.YELLOW);
        g.drawRect(
          QuadTreeViewer.this.selection.lower.x,
          QuadTreeViewer.this.selection.lower.y,
          QuadTreeViewer.this.selection.getWidth(),
          QuadTreeViewer.this.selection.getHeight());

        g.drawString(
          QuadTreeViewer.this.selection.lower.toString(),
          QuadTreeViewer.this.selection.lower.x,
          QuadTreeViewer.this.selection.lower.y);
        g.drawString(
          QuadTreeViewer.this.selection.upper.toString(),
          QuadTreeViewer.this.selection.upper.x,
          QuadTreeViewer.this.selection.upper.y);

      } catch (final Exception e) {
        QuadTreeViewer.fatal(e);
      } catch (final ConstraintError e) {
        QuadTreeViewer.fatal(e);
      }
    }
  }

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

  private final JPanel             panel;
  private QuadTreeBasic<Rectangle> quadtree;
  private final Selection          selection;
  private final List<Rectangle>    selected;

  public QuadTreeViewer()
    throws ConstraintError
  {
    this.selected = new LinkedList<Rectangle>();
    this.selection = new Selection();
    this.quadtree =
      new QuadTreeBasic<Rectangle>(
        QuadTreeViewer.CANVAS_SIZE_X,
        QuadTreeViewer.CANVAS_SIZE_Y);

    this.panel = new JPanel();
    this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));

    {
      final JPanel canvas_panel = new JPanel();
      final TreeCanvas canvas = new TreeCanvas();
      canvas.setSize(
        QuadTreeViewer.CANVAS_SIZE_X,
        QuadTreeViewer.CANVAS_SIZE_Y);
      canvas.setMinimumSize(new Dimension(
        QuadTreeViewer.CANVAS_SIZE_X,
        QuadTreeViewer.CANVAS_SIZE_Y));
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
          QuadTreeViewer.this.selection.lower.x = e.getX();
          QuadTreeViewer.this.selection.lower.y = e.getY();
        }

        @SuppressWarnings("synthetic-access") @Override public
          void
          mouseReleased(
            final MouseEvent e)
        {
          QuadTreeViewer.this.selection.upper.x = e.getX();
          QuadTreeViewer.this.selection.upper.y = e.getY();

          if (e.getButton() == MouseEvent.BUTTON1) {
            QuadTreeViewer.this.commandSelectContaining(canvas);
          } else if (e.getButton() == MouseEvent.BUTTON3) {
            QuadTreeViewer.this.commandSelectOverlapping(canvas);
          }
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
          QuadTreeViewer.this.commandReset(canvas);
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
          QuadTreeViewer.this.commandInsert(canvas, r);
        }
      });

      random.addActionListener(new ActionListener() {
        @SuppressWarnings("unused") @Override public void actionPerformed(
          final ActionEvent _)
        {
          QuadTreeViewer.this.commandReset(canvas);
          QuadTreeViewer.this.commandRandomize();
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
      QuadTreeViewer.this.quadtree.quadTreeInsert(rect);
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
      for (int index = 0; index < 100; ++index) {
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
      QuadTreeViewer.fatal(e);
    }
  }

  void commandReset(
    final Canvas canvas)
  {
    try {
      QuadTreeViewer.this.quadtree =
        new QuadTreeBasic<Rectangle>(
          QuadTreeViewer.CANVAS_SIZE_X,
          QuadTreeViewer.CANVAS_SIZE_Y);
      canvas.repaint();
    } catch (final ConstraintError x) {
      QuadTreeViewer.fatal(x);
    }
  }

  void commandSelectContaining(
    final Canvas canvas)
  {
    try {
      QuadTreeViewer.this.selected.clear();
      QuadTreeViewer.this.quadtree.quadTreeQueryAreaContaining(
        QuadTreeViewer.this.selection,
        QuadTreeViewer.this.selected);
      canvas.repaint();
    } catch (final ConstraintError e) {
      // Ignored!
    }
  }

  void commandSelectOverlapping(
    final Canvas canvas)
  {
    try {
      QuadTreeViewer.this.selected.clear();
      QuadTreeViewer.this.quadtree.quadTreeQueryAreaOverlapping(
        QuadTreeViewer.this.selection,
        QuadTreeViewer.this.selected);
      canvas.repaint();
    } catch (final ConstraintError e) {
      // Ignored!
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
