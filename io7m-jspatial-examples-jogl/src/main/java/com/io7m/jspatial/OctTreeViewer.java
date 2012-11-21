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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
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
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable3I;
import com.jogamp.opengl.util.Animator;

/**
 * Octtree viewer demonstrating tree operations.
 */

public final class OctTreeViewer implements Runnable
{
  public static final class Input
  {
    public boolean orbit_left    = false;
    public boolean orbit_right   = false;
    public boolean rotate_left   = false;
    public boolean rotate_right  = false;
    public boolean move_forward  = false;
    public boolean move_backward = false;
    public boolean move_up       = false;
    public boolean move_down     = false;
    public boolean pan_left      = false;
    public boolean pan_right     = false;
    public boolean zoom_in       = false;
    public boolean zoom_out      = false;

    public boolean box_forward   = false;
    public boolean box_backward  = false;
    public boolean box_left      = false;
    public boolean box_right     = false;
    public boolean box_up        = false;
    public boolean box_down      = false;
  }

  final class ViewKeyListener implements KeyListener
  {
    @Override public void keyPressed(
      final KeyEvent e)
    {
      switch (e.getKeyChar()) {
        case 'q':
        {
          OctTreeViewer.this.input_state.orbit_left = true;
          break;
        }
        case 'e':
        {
          OctTreeViewer.this.input_state.orbit_right = true;
          break;
        }
        case 'a':
        {
          OctTreeViewer.this.input_state.pan_left = true;
          break;
        }
        case 'd':
        {
          OctTreeViewer.this.input_state.pan_right = true;
          break;
        }
        case 'w':
        {
          OctTreeViewer.this.input_state.move_forward = true;
          break;
        }
        case 's':
        {
          OctTreeViewer.this.input_state.move_backward = true;
          break;
        }
        case 'f':
        {
          OctTreeViewer.this.input_state.move_up = true;
          break;
        }
        case 'v':
        {
          OctTreeViewer.this.input_state.move_down = true;
          break;
        }
        case 'g':
        {
          OctTreeViewer.this.input_state.zoom_in = true;
          break;
        }
        case 'b':
        {
          OctTreeViewer.this.input_state.zoom_out = true;
          break;
        }
      }
    }

    @Override public void keyReleased(
      final KeyEvent e)
    {
      switch (e.getKeyChar()) {
        case 'q':
        {
          OctTreeViewer.this.input_state.orbit_left = false;
          break;
        }
        case 'e':
        {
          OctTreeViewer.this.input_state.orbit_right = false;
          break;
        }
        case 'a':
        {
          OctTreeViewer.this.input_state.pan_left = false;
          break;
        }
        case 'd':
        {
          OctTreeViewer.this.input_state.pan_right = false;
          break;
        }
        case 'w':
        {
          OctTreeViewer.this.input_state.move_forward = false;
          break;
        }
        case 's':
        {
          OctTreeViewer.this.input_state.move_backward = false;
          break;
        }
        case 'f':
        {
          OctTreeViewer.this.input_state.move_up = false;
          break;
        }
        case 'v':
        {
          OctTreeViewer.this.input_state.move_down = false;
          break;
        }
        case 'g':
        {
          OctTreeViewer.this.input_state.zoom_in = false;
          break;
        }
        case 'b':
        {
          OctTreeViewer.this.input_state.zoom_out = false;
          break;
        }
        case 'p':
        {
          System.out.println("Position:    "
            + OctTreeViewer.this.camera_position);
          System.out.println("Focus:       "
            + OctTreeViewer.this.camera_focus);
          System.out.println("Orientation: "
            + OctTreeViewer.this.camera_orientation);
          System.out.println("Orbit offset: "
            + OctTreeViewer.this.camera_orbit_offset);
        }
      }
    }

    @Override public void keyTyped(
      @SuppressWarnings("unused") final KeyEvent e)
    {
      // Nothing
    }
  }

  final class ViewRenderer implements GLEventListener
  {
    @Override public void display(
      final GLAutoDrawable drawable)
    {
      try {
        final GL2 gl = drawable.getGL().getGL2();
        final GLU glu = GLU.createGLU(gl);

        gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        final double aspect =
          OctTreeViewer.CANVAS_SIZE_X / OctTreeViewer.CANVAS_SIZE_Y;
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(35, aspect, 1.0, 1000.0);

        this.move();

        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(
          OctTreeViewer.this.camera_position.x,
          OctTreeViewer.this.camera_position.y,
          OctTreeViewer.this.camera_position.z,
          OctTreeViewer.this.camera_focus.x,
          OctTreeViewer.this.camera_focus.y,
          OctTreeViewer.this.camera_focus.z,
          0,
          1,
          0);

        this.renderGrid(gl);
        this.renderTree(gl);
      } catch (final Exception e) {
        e.printStackTrace();
      } catch (final ConstraintError e) {
        e.printStackTrace();
      }
    }

    @SuppressWarnings("unused") @Override public void dispose(
      final GLAutoDrawable drawable)
    {
      // Nothing
    }

    @SuppressWarnings("unused") @Override public void init(
      final GLAutoDrawable drawable)
    {
      // Nothing
    }

    private void move()
    {
      if (OctTreeViewer.this.input_state.move_forward) {
        OctTreeViewer.this.camera_velocity_forward =
          OctTreeViewer.CAMERA_SPEED_LINEAR;
      }
      if (OctTreeViewer.this.input_state.move_backward) {
        OctTreeViewer.this.camera_velocity_forward =
          -OctTreeViewer.CAMERA_SPEED_LINEAR;
      }
      if (OctTreeViewer.this.input_state.move_up) {
        OctTreeViewer.this.camera_velocity_up =
          OctTreeViewer.CAMERA_SPEED_LINEAR;
      }
      if (OctTreeViewer.this.input_state.move_down) {
        OctTreeViewer.this.camera_velocity_up =
          -OctTreeViewer.CAMERA_SPEED_LINEAR;
      }

      if (OctTreeViewer.this.input_state.zoom_in) {
        OctTreeViewer.this.camera_orbit_offset += 1.0;
      }
      if (OctTreeViewer.this.input_state.zoom_out) {
        OctTreeViewer.this.camera_orbit_offset -= 1.0;
      }

      if (OctTreeViewer.this.input_state.pan_left) {
        OctTreeViewer.this.camera_velocity_side =
          -OctTreeViewer.CAMERA_SPEED_LINEAR;
      }
      if (OctTreeViewer.this.input_state.pan_right) {
        OctTreeViewer.this.camera_velocity_side =
          OctTreeViewer.CAMERA_SPEED_LINEAR;
      }
      if (OctTreeViewer.this.input_state.rotate_left) {
        OctTreeViewer.this.camera_rotate_velocity =
          -OctTreeViewer.CAMERA_SPEED_ANGULAR * 0.1;
      }
      if (OctTreeViewer.this.input_state.rotate_right) {
        OctTreeViewer.this.camera_rotate_velocity =
          OctTreeViewer.CAMERA_SPEED_ANGULAR * 0.1;
      }
      if (OctTreeViewer.this.input_state.orbit_left) {
        OctTreeViewer.this.camera_orbit_velocity =
          OctTreeViewer.CAMERA_SPEED_ANGULAR * 0.1;
      }
      if (OctTreeViewer.this.input_state.orbit_right) {
        OctTreeViewer.this.camera_orbit_velocity =
          -OctTreeViewer.CAMERA_SPEED_ANGULAR * 0.1;
      }

      OctTreeViewer.this.camera_rotate_velocity *=
        OctTreeViewer.CAMERA_FRICTION;
      OctTreeViewer.this.camera_orbit_velocity *=
        OctTreeViewer.CAMERA_FRICTION;
      OctTreeViewer.this.camera_velocity_forward *=
        OctTreeViewer.CAMERA_FRICTION;
      OctTreeViewer.this.camera_velocity_side *=
        OctTreeViewer.CAMERA_FRICTION;
      OctTreeViewer.this.camera_velocity_up *= OctTreeViewer.CAMERA_FRICTION;

      OctTreeViewer.this.camera_orbit_offset =
        OctTreeViewer.this.camera_orbit_offset < 1.0
          ? 1.0
          : OctTreeViewer.this.camera_orbit_offset;

      OctTreeViewer.this.camera_orientation +=
        OctTreeViewer.this.camera_rotate_velocity;

      /*
       * Normalize orientation to 0 .. 2π
       */

      OctTreeViewer.this.camera_orientation =
        (OctTreeViewer.this.camera_orientation > (2 * Math.PI))
          ? OctTreeViewer.this.camera_orientation -= 2 * Math.PI
          : OctTreeViewer.this.camera_orientation;
      OctTreeViewer.this.camera_orientation =
        (OctTreeViewer.this.camera_orientation < 0.0)
          ? OctTreeViewer.this.camera_orientation += 2 * Math.PI
          : OctTreeViewer.this.camera_orientation;

      assert OctTreeViewer.this.camera_orientation >= 0.0;
      assert OctTreeViewer.this.camera_orientation <= (2 * Math.PI);

      /*
       * Apply velocities given the current orientation.
       */

      OctTreeViewer.this.camera_position.y +=
        OctTreeViewer.this.camera_velocity_up;
      OctTreeViewer.this.camera_position.x +=
        OctTreeViewer.this.camera_velocity_forward
          * Math.cos(OctTreeViewer.this.camera_orientation);
      OctTreeViewer.this.camera_position.x +=
        OctTreeViewer.this.camera_velocity_side
          * Math.cos(OctTreeViewer.this.camera_orientation + (0.5 * Math.PI));

      OctTreeViewer.this.camera_position.z +=
        OctTreeViewer.this.camera_velocity_forward
          * Math.sin(OctTreeViewer.this.camera_orientation);
      OctTreeViewer.this.camera_position.z +=
        OctTreeViewer.this.camera_velocity_side
          * Math.sin(OctTreeViewer.this.camera_orientation + (0.5 * Math.PI));

      /*
       * Calculate the focus point around which to orbit.
       */

      OctTreeViewer.this.camera_focus.x =
        OctTreeViewer.this.camera_position.x;
      OctTreeViewer.this.camera_focus.z =
        OctTreeViewer.this.camera_position.z;

      OctTreeViewer.this.camera_focus.x +=
        OctTreeViewer.this.camera_orbit_offset
          * Math.cos(OctTreeViewer.this.camera_orientation);
      OctTreeViewer.this.camera_focus.z +=
        OctTreeViewer.this.camera_orbit_offset
          * Math.sin(OctTreeViewer.this.camera_orientation);

      /*
       * Move the camera around the origin by starting at the origin and
       * moving opposite to the current orientation with the orbit factored
       * in; add PI to get the opposite orientation.
       */

      OctTreeViewer.this.camera_position.x =
        OctTreeViewer.this.camera_focus.x;
      OctTreeViewer.this.camera_position.z =
        OctTreeViewer.this.camera_focus.z;
      OctTreeViewer.this.camera_position.x +=
        OctTreeViewer.this.camera_orbit_offset
          * Math.cos(OctTreeViewer.this.camera_orientation
            + OctTreeViewer.this.camera_orbit_velocity
            + Math.PI);
      OctTreeViewer.this.camera_position.z +=
        OctTreeViewer.this.camera_orbit_offset
          * Math.sin(OctTreeViewer.this.camera_orientation
            + OctTreeViewer.this.camera_orbit_velocity
            + Math.PI);

      /*
       * Adjust orientation so that the camera is pointed back at the origin
       * again.
       */

      OctTreeViewer.this.camera_orientation +=
        OctTreeViewer.this.camera_orbit_velocity;
    }

    private void renderGrid(
      final GL2 gl)
    {
      gl.glEnable(GL.GL_BLEND);
      gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

      for (int x = -1024; x < 1024; ++x) {
        gl.glBegin(GL.GL_LINES);
        {
          gl.glColor4d(0.5, 0.5, 0.5, 0.1);
          gl.glVertex3d(x, 0, 1024);
          gl.glVertex3d(x, 0, -1024);
        }
        gl.glEnd();
      }

      gl.glBegin(GL.GL_LINES);
      {
        gl.glColor4d(1, 1, 1, 0.1);
        gl.glVertex3d(0, 0, 1024);
        gl.glVertex3d(0, 0, -1024);

        gl.glVertex3d(1024, 0, 0);
        gl.glVertex3d(-1024, 0, 0);
      }
      gl.glEnd();

      for (int z = -1024; z < 1024; ++z) {
        gl.glBegin(GL.GL_LINES);
        {
          gl.glColor4d(0.5, 0.5, 0.5, 0.1);
          gl.glVertex3d(-1024, 0, z);
          gl.glVertex3d(1024, 0, z);
        }
        gl.glEnd();
      }

      gl.glDisable(GL.GL_BLEND);
    }

    @SuppressWarnings("synthetic-access") private void renderTree(
      final GL2 gl)
      throws Exception,
        ConstraintError
    {
      gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE);

      gl.glEnable(GL.GL_BLEND);
      gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

      OctTreeViewer.this.octtree.octTreeTraverse(new OctTreeTraversal() {
        @SuppressWarnings("unused") @Override public void visit(
          final int depth,
          final @Nonnull VectorReadable3I lower,
          final @Nonnull VectorReadable3I upper)
          throws Exception
        {
          gl.glBegin(GL2.GL_QUADS);
          {
            gl.glColor4d(1.0, 1.0, 1.0, 0.05);

            gl.glVertex3d(lower.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(lower.getXI(), upper.getYI(), upper.getZI());

            gl.glVertex3d(upper.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), upper.getZI());

            gl.glVertex3d(lower.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), lower.getZI());

            gl.glVertex3d(lower.getXI(), upper.getYI(), upper.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), upper.getZI());
          }
          gl.glEnd();
        }
      });

      OctTreeViewer.this.octtree
        .octTreeIterateObjects(new Function<Cuboid, Boolean>() {
          @Override public Boolean call(
            final Cuboid x)
          {
            final VectorReadable3I lower = x.boundingVolumeLower();
            final VectorReadable3I upper = x.boundingVolumeUpper();

            gl.glBegin(GL2.GL_QUADS);
            {
              gl.glColor4d(0.8, 0.8, 0.8, 0.8);

              gl.glVertex3d(lower.getXI(), upper.getYI(), lower.getZI());
              gl.glVertex3d(lower.getXI(), lower.getYI(), lower.getZI());
              gl.glVertex3d(lower.getXI(), lower.getYI(), upper.getZI());
              gl.glVertex3d(lower.getXI(), upper.getYI(), upper.getZI());

              gl.glVertex3d(upper.getXI(), upper.getYI(), lower.getZI());
              gl.glVertex3d(upper.getXI(), lower.getYI(), lower.getZI());
              gl.glVertex3d(upper.getXI(), lower.getYI(), upper.getZI());
              gl.glVertex3d(upper.getXI(), upper.getYI(), upper.getZI());

              gl.glVertex3d(lower.getXI(), upper.getYI(), lower.getZI());
              gl.glVertex3d(lower.getXI(), lower.getYI(), lower.getZI());
              gl.glVertex3d(upper.getXI(), lower.getYI(), lower.getZI());
              gl.glVertex3d(upper.getXI(), upper.getYI(), lower.getZI());

              gl.glVertex3d(lower.getXI(), upper.getYI(), upper.getZI());
              gl.glVertex3d(lower.getXI(), lower.getYI(), upper.getZI());
              gl.glVertex3d(upper.getXI(), lower.getYI(), upper.getZI());
              gl.glVertex3d(upper.getXI(), upper.getYI(), upper.getZI());
            }
            gl.glEnd();

            return Boolean.TRUE;
          }
        });

      if (OctTreeViewer.this.volume_selected) {
        {
          final VectorReadable3I lower =
            OctTreeViewer.this.volume_select.boundingVolumeLower();
          final VectorReadable3I upper =
            OctTreeViewer.this.volume_select.boundingVolumeUpper();

          gl.glBegin(GL2.GL_QUADS);
          {
            gl.glColor4d(1.0, 1.0, 0.0, 0.2);

            gl.glVertex3d(lower.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(lower.getXI(), upper.getYI(), upper.getZI());

            gl.glVertex3d(upper.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), upper.getZI());

            gl.glVertex3d(lower.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), lower.getZI());

            gl.glVertex3d(lower.getXI(), upper.getYI(), upper.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), upper.getZI());
          }
          gl.glEnd();
        }

        for (final Cuboid c : OctTreeViewer.this.volume_select_results) {
          final VectorReadable3I lower = c.boundingVolumeLower();
          final VectorReadable3I upper = c.boundingVolumeUpper();

          gl.glBegin(GL2.GL_QUADS);
          {
            gl.glColor4d(1.0, 0.0, 0.0, 0.2);

            gl.glVertex3d(lower.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(lower.getXI(), upper.getYI(), upper.getZI());

            gl.glVertex3d(upper.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), upper.getZI());

            gl.glVertex3d(lower.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), lower.getZI());

            gl.glVertex3d(lower.getXI(), upper.getYI(), upper.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), upper.getZI());
          }
          gl.glEnd();
        }
      }

      if (OctTreeViewer.this.raycast_active) {
        final double target_x =
          OctTreeViewer.this.raycast_ray.direction.x
            * (OctTreeViewer.TREE_SIZE_X * 1000);
        final double target_y =
          OctTreeViewer.this.raycast_ray.direction.y
            * (OctTreeViewer.TREE_SIZE_Y * 1000);
        final double target_z =
          OctTreeViewer.this.raycast_ray.direction.y
            * (OctTreeViewer.TREE_SIZE_Z * 1000);

        gl.glBegin(GL.GL_LINES);
        {
          gl.glColor4d(0.0, 1.0, 1.0, 0.3);

          gl.glVertex3d(
            OctTreeViewer.this.raycast_ray.origin.x,
            OctTreeViewer.this.raycast_ray.origin.y,
            OctTreeViewer.this.raycast_ray.origin.z);
          gl.glVertex3d(target_x, target_y, target_z);
        }
        gl.glEnd();

        for (final OctTreeRaycastResult<Cuboid> r : OctTreeViewer.this.raycast_selection) {
          final Cuboid c = r.getObject();
          final VectorReadable3I lower = c.boundingVolumeLower();
          final VectorReadable3I upper = c.boundingVolumeUpper();

          gl.glBegin(GL2.GL_QUADS);
          {
            gl.glColor4d(0.0, 1.0, 1.0, 0.3);

            gl.glVertex3d(lower.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(lower.getXI(), upper.getYI(), upper.getZI());

            gl.glVertex3d(upper.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), upper.getZI());

            gl.glVertex3d(lower.getXI(), upper.getYI(), lower.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), lower.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), lower.getZI());

            gl.glVertex3d(lower.getXI(), upper.getYI(), upper.getZI());
            gl.glVertex3d(lower.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), lower.getYI(), upper.getZI());
            gl.glVertex3d(upper.getXI(), upper.getYI(), upper.getZI());
          }
          gl.glEnd();
        }
      }

      gl.glDisable(GL.GL_BLEND);
    }

    @SuppressWarnings("unused") @Override public void reshape(
      final GLAutoDrawable drawable,
      final int x,
      final int y,
      final int width,
      final int height)
    {
      // Nothing
    }
  }

  private static final int    CANVAS_SIZE_X        = 512;
  private static final int    CANVAS_SIZE_Y        = 512;

  private static final double CAMERA_SPEED_LINEAR  = 0.3;
  private static final double CAMERA_SPEED_ANGULAR = 0.3;
  private static final double CAMERA_FRICTION      = 0.85;

  private static final int    TREE_SIZE_X          = 128;
  private static final int    TREE_SIZE_Y          = 128;
  private static final int    TREE_SIZE_Z          = 128;

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
          final OctTreeViewer v = new OctTreeViewer();
          final JFrame frame = new JFrame("OctTreeViewer");
          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame.getContentPane().add(v.getPanel());
          frame.pack();
          frame.setVisible(true);
          v.run();
        } catch (final ConstraintError e) {
          OctTreeViewer.fatal(e);
        } catch (final Throwable e) {
          OctTreeViewer.fatal(e);
        }
      }
    });
  }

  private final GLCanvas                                                                      canvas;
  private final JPanel                                                                        panel;
  private OctTreeInterface<Cuboid>                                                            octtree;
  protected final HashMap<String, PartialFunction<Unit, OctTreeInterface<Cuboid>, Throwable>> octtree_constructors;

  protected final VectorM3F                                                                   camera_focus;
  protected final VectorM3F                                                                   camera_position;
  protected double                                                                            camera_orientation;
  protected double                                                                            camera_velocity_forward;
  protected double                                                                            camera_velocity_side;
  protected double                                                                            camera_velocity_up;
  protected double                                                                            camera_rotate_velocity;
  protected double                                                                            camera_orbit_velocity;
  protected double                                                                            camera_orbit_offset;
  protected Input                                                                             input_state;

  private final JPanel                                                                        canvas_container;
  private final JPanel                                                                        control_container;

  private final SortedSet<OctTreeRaycastResult<Cuboid>>                                       raycast_selection;
  private RayI3D                                                                              raycast_ray;
  private boolean                                                                             raycast_active;

  private Cuboid                                                                              volume_select;
  private final SortedSet<Cuboid>                                                             volume_select_results;
  private boolean                                                                             volume_selected;

  private final AtomicLong                                                                    current_id;

  public OctTreeViewer()
    throws Throwable
  {
    this.input_state = new Input();

    this.camera_position = new VectorM3F(286.9f, 177.14f, 273.5f);
    this.camera_focus = new VectorM3F(-10f, 2f, -5.8f);
    this.camera_orientation = 3.89f;
    this.camera_orbit_offset = 408f;
    this.camera_velocity_forward = 0.0;
    this.camera_velocity_side = 0.0;
    this.camera_velocity_up = 0.0;
    this.camera_rotate_velocity = 0.0;
    this.camera_orbit_velocity = 0.0;

    this.current_id = new AtomicLong();
    this.octtree =
      new OctTreePrune<Cuboid>(
        OctTreeViewer.TREE_SIZE_X,
        OctTreeViewer.TREE_SIZE_Y,
        OctTreeViewer.TREE_SIZE_Z);
    this.octtree_constructors =
      new HashMap<String, PartialFunction<Unit, OctTreeInterface<Cuboid>, Throwable>>();
    this.initializeConstructors();

    this.volume_select = new Cuboid(0, VectorI3I.ZERO, VectorI3I.ZERO);
    this.volume_select_results = new TreeSet<Cuboid>();
    this.volume_selected = false;

    this.raycast_selection = new TreeSet<OctTreeRaycastResult<Cuboid>>();
    this.raycast_ray = null;
    this.raycast_active = false;

    this.canvas_container = new JPanel();
    this.canvas_container.setLayout(new BoxLayout(
      this.canvas_container,
      BoxLayout.Y_AXIS));

    this.canvas = this.makeGLCanvas();
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
   * Delete all objects in the current octtree.
   */

  protected void commandClear()
  {
    this.octtree.octTreeClear();
    this.commandSelectReset();
    this.raycast_active = false;
  }

  /**
   * Delete the currently selected objects (if any).
   */

  protected void commandDeleteSelected()
  {
    try {
      if (this.volume_selected) {
        for (final Cuboid c : this.volume_select_results) {
          final boolean deleted = this.octtree.octTreeRemove(c);
          if (deleted) {
            System.err.println("Deleted: " + c);
          } else {
            System.err.println("Failed to delete: " + c);
          }
        }
        this.commandSelectReset();
      }
      this.canvas.repaint();
    } catch (final ConstraintError x) {
      OctTreeViewer.fatal(x);
    }
  }

  /**
   * Insert the given cuboid into the current octtree.
   */

  protected void commandInsert(
    final Cuboid cube)
  {
    try {
      System.err.println("Insert: " + cube);
      OctTreeViewer.this.octtree.octTreeInsert(cube);
    } catch (final IllegalArgumentException x) {
      OctTreeViewer.error(x);
    } catch (final ConstraintError x) {
      OctTreeViewer.error(x);
    }
  }

  /**
   * Quit the program.
   */

  protected void commandQuit()
  {
    this.canvas.destroy();
    System.exit(0);
  }

  /**
   * Fill the current octtree with 100 randomly sized cuboids.
   */

  protected void commandRandomize()
  {
    try {
      for (int i = 0; i < 100; ++i) {
        final int width = (int) (2 + (Math.random() * 16));
        final int height = (int) (2 + (Math.random() * 16));
        final int depth = (int) (2 + (Math.random() * 16));

        final int x0 = (int) (Math.random() * OctTreeViewer.TREE_SIZE_X);
        final int y0 = (int) (Math.random() * OctTreeViewer.TREE_SIZE_Y);
        final int z0 = (int) (Math.random() * OctTreeViewer.TREE_SIZE_Z);

        final int x1 = Math.min(x0 + width, OctTreeViewer.TREE_SIZE_X - 1);
        final int y1 = Math.min(y0 + height, OctTreeViewer.TREE_SIZE_Y - 1);
        final int z1 = Math.min(z0 + depth, OctTreeViewer.TREE_SIZE_Z - 1);

        final VectorI3I r0 = new VectorI3I(x0, y0, z0);
        final VectorI3I r1 = new VectorI3I(x1, y1, z1);

        this.octtree.octTreeInsert(new Cuboid(this.current_id
          .incrementAndGet(), r0, r1));
      }
    } catch (final ConstraintError e) {
      OctTreeViewer.fatal(e);
    }
  }

  /**
   * Cast the given ray through the current octtree.
   */

  protected void commandRaycast(
    final RayI3D ray)
  {
    System.err.println("Cast: " + ray);

    try {
      this.raycast_selection.clear();
      this.octtree.octTreeQueryRaycast(ray, this.raycast_selection);
      this.raycast_active = true;
      this.raycast_ray = ray;
    } catch (final ConstraintError e) {
      OctTreeViewer.error(e);
    }
  }

  /**
   * Select objects within the current octtree.
   */

  protected void commandSelect(
    final Cuboid c,
    final boolean containing)
  {
    System.err.println("Select: "
      + c
      + " "
      + (containing ? "(containing)" : "(overlapping)"));

    try {
      this.commandDeleteSelected();

      this.volume_select = c;
      if (containing) {
        this.octtree.octTreeQueryVolumeContaining(
          c,
          this.volume_select_results);
      } else {
        this.octtree.octTreeQueryVolumeOverlapping(
          c,
          this.volume_select_results);
      }
      this.volume_selected = true;
    } catch (final ConstraintError e) {
      OctTreeViewer.error(e);
    }
  }

  protected void commandSelectImplementation(
    final PartialFunction<Unit, OctTreeInterface<Cuboid>, Throwable> f)
    throws Throwable
  {
    this.octtree = f.call(new Unit());
    this.commandClear();
  }

  /**
   * Reset the current selection state.
   */

  protected void commandSelectReset()
  {
    this.volume_selected = false;
    this.volume_select_results.clear();
  }

  private Component getPanel()
  {
    return this.panel;
  }

  /**
   * Initialize the map of names to octtree constructors.
   */

  private void initializeConstructors()
  {
    this.octtree_constructors.put(
      "OctTreeBasic",
      new PartialFunction<Unit, OctTreeInterface<Cuboid>, Throwable>() {
        @SuppressWarnings("unused") @Override public
          OctTreeInterface<Cuboid>
          call(
            final Unit _)
            throws ConstraintError
        {
          return new OctTreeBasic<Cuboid>(
            OctTreeViewer.TREE_SIZE_X,
            OctTreeViewer.TREE_SIZE_Y,
            OctTreeViewer.TREE_SIZE_Z);
        }
      });

    this.octtree_constructors.put(
      "OctTreeSD",
      new PartialFunction<Unit, OctTreeInterface<Cuboid>, Throwable>() {
        @SuppressWarnings("unused") @Override public
          OctTreeInterface<Cuboid>
          call(
            final Unit _)
            throws ConstraintError
        {
          return new OctTreeSD<Cuboid>(
            OctTreeViewer.TREE_SIZE_X,
            OctTreeViewer.TREE_SIZE_Y,
            OctTreeViewer.TREE_SIZE_Z);
        }
      });

    this.octtree_constructors.put(
      "OctTreePrune",
      new PartialFunction<Unit, OctTreeInterface<Cuboid>, Throwable>() {
        @SuppressWarnings("unused") @Override public
          OctTreeInterface<Cuboid>
          call(
            final Unit _)
            throws ConstraintError
        {
          System.err.println("Selected OctTreePrune");
          return new OctTreePrune<Cuboid>(
            OctTreeViewer.TREE_SIZE_X,
            OctTreeViewer.TREE_SIZE_Y,
            OctTreeViewer.TREE_SIZE_Z);
        }
      });
  }

  private GLCanvas makeGLCanvas()
  {
    final GLCanvas c = new GLCanvas();
    c.setSize(OctTreeViewer.CANVAS_SIZE_X, OctTreeViewer.CANVAS_SIZE_Y);
    c.setMinimumSize(new Dimension(
      OctTreeViewer.CANVAS_SIZE_X,
      OctTreeViewer.CANVAS_SIZE_Y));
    c.setPreferredSize(new Dimension(
      OctTreeViewer.CANVAS_SIZE_X,
      OctTreeViewer.CANVAS_SIZE_Y));

    final Animator animator = new Animator(c);
    animator.start();

    c.addKeyListener(new ViewKeyListener());
    c.addGLEventListener(new ViewRenderer());
    c.setFocusable(true);

    return c;
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
    for (final String key : this.octtree_constructors.keySet()) {
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
          if (OctTreeViewer.this.octtree_constructors.containsKey(name)) {
            final PartialFunction<Unit, OctTreeInterface<Cuboid>, Throwable> f =
              OctTreeViewer.this.octtree_constructors.get(name);
            OctTreeViewer.this.commandSelectImplementation(f);
          }
        } catch (final Throwable x) {
          OctTreeViewer.fatal(x);
        }
      }

    });

    p.add(cb);
    return p;
  }

  private JPanel makeInsertControls()
  {
    final JButton insert = new JButton("Insert");

    final JTextField input_x0 = new JTextField("0");
    final JTextField input_y0 = new JTextField("0");
    final JTextField input_z0 = new JTextField("0");
    final JTextField input_x1 =
      new JTextField("" + (OctTreeViewer.TREE_SIZE_X - 1));
    final JTextField input_y1 =
      new JTextField("" + (OctTreeViewer.TREE_SIZE_Y - 1));
    final JTextField input_z1 =
      new JTextField("" + (OctTreeViewer.TREE_SIZE_Z - 1));

    input_x0.setColumns(3);
    input_y0.setColumns(3);
    input_z0.setColumns(3);
    input_x1.setColumns(3);
    input_y1.setColumns(3);
    input_z1.setColumns(3);

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
        final int z0 = Integer.parseInt(input_z0.getText());
        final int z1 = Integer.parseInt(input_z1.getText());

        final Cuboid c =
          new Cuboid(
            OctTreeViewer.this.current_id.getAndIncrement(),
            new VectorI3I(x0, y0, z0),
            new VectorI3I(x1, y1, z1));

        OctTreeViewer.this.commandInsert(c);
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
      c.gridx = 0;
      c.gridy = 2;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_insert.add(new JLabel("z0"), c);
      c.gridx = c.gridx + 1;
      controls_insert.add(input_z0, c);
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
      c.gridx = 2;
      c.gridy = 2;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_insert.add(new JLabel("z1"), c);
      c.gridx = c.gridx + 1;
      controls_insert.add(input_z1, c);
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

  private JPanel makeRaycastControls()
  {
    final JButton cast = new JButton("Cast");

    final JPanel controls_raycast = new JPanel();
    controls_raycast.setBorder(BorderFactory.createTitledBorder("Raycast"));
    controls_raycast.setLayout(new GridBagLayout());

    final JTextField input_x0 = new JTextField("0");
    final JTextField input_y0 = new JTextField("0");
    final JTextField input_z0 = new JTextField("0");
    final JTextField input_x1 =
      new JTextField("" + (OctTreeViewer.TREE_SIZE_X - 1));
    final JTextField input_y1 =
      new JTextField("" + (OctTreeViewer.TREE_SIZE_Y - 1));
    final JTextField input_z1 =
      new JTextField("" + (OctTreeViewer.TREE_SIZE_Z - 1));

    input_x0.setColumns(3);
    input_y0.setColumns(3);
    input_z0.setColumns(3);
    input_x1.setColumns(3);
    input_y1.setColumns(3);
    input_z1.setColumns(3);

    cast.addActionListener(new ActionListener() {
      @SuppressWarnings({ "unused" }) @Override public void actionPerformed(
        final ActionEvent _)
      {
        final int x0 = Integer.parseInt(input_x0.getText());
        final int x1 = Integer.parseInt(input_x1.getText());
        final int y0 = Integer.parseInt(input_y0.getText());
        final int y1 = Integer.parseInt(input_y1.getText());
        final int z0 = Integer.parseInt(input_z0.getText());
        final int z1 = Integer.parseInt(input_z1.getText());

        final VectorI3D origin = new VectorI3D(x0, y0, z0);
        final VectorI3D direction =
          VectorI3D.normalize(new VectorI3D(x0 + x1, y0 + y1, z0 + z1));
        final RayI3D ray = new RayI3D(origin, direction);

        OctTreeViewer.this.commandRaycast(ray);
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
      c.gridx = 0;
      c.gridy = 2;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_raycast.add(new JLabel("z0"), c);
      c.gridx = c.gridx + 1;
      controls_raycast.add(input_z0, c);
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
      c.gridx = 2;
      c.gridy = 2;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_raycast.add(new JLabel("z1"), c);
      c.gridx = c.gridx + 1;
      controls_raycast.add(input_z1, c);
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

  private JPanel makeSelectControls()
  {
    final JButton select = new JButton("Select");
    final JButton delete = new JButton("Delete");

    final JPanel controls_select = new JPanel();
    controls_select.setBorder(BorderFactory.createTitledBorder("Select"));
    controls_select.setLayout(new GridBagLayout());

    final JTextField input_x0 = new JTextField("0");
    final JTextField input_y0 = new JTextField("0");
    final JTextField input_z0 = new JTextField("0");
    final JTextField input_x1 =
      new JTextField("" + (OctTreeViewer.TREE_SIZE_X - 1));
    final JTextField input_y1 =
      new JTextField("" + (OctTreeViewer.TREE_SIZE_Y - 1));
    final JTextField input_z1 =
      new JTextField("" + (OctTreeViewer.TREE_SIZE_Z - 1));

    input_x0.setColumns(3);
    input_y0.setColumns(3);
    input_z0.setColumns(3);
    input_x1.setColumns(3);
    input_y1.setColumns(3);
    input_z1.setColumns(3);

    final JRadioButton input_cont = new JRadioButton("Containing");
    input_cont
      .setToolTipText("Only select objects strictly contained by this volume");

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
        final int z0 = Integer.parseInt(input_z0.getText());
        final int z1 = Integer.parseInt(input_z1.getText());
        final boolean containing = input_cont.isSelected();

        final Cuboid c =
          new Cuboid(
            OctTreeViewer.this.current_id.getAndIncrement(),
            new VectorI3I(x0, y0, z0),
            new VectorI3I(x1, y1, z1));

        OctTreeViewer.this.commandSelect(c, containing);
      }
    });

    delete.addActionListener(new ActionListener() {
      @SuppressWarnings({ "unused" }) @Override public void actionPerformed(
        final ActionEvent _)
      {
        OctTreeViewer.this.commandDeleteSelected();
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
      c.gridx = 0;
      c.gridy = 2;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_select.add(new JLabel("z0"), c);
      c.gridx = c.gridx + 1;
      controls_select.add(input_z0, c);
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
      c.gridx = 2;
      c.gridy = 2;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_select.add(new JLabel("z1"), c);
      c.gridx = c.gridx + 1;
      controls_select.add(input_z1, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 4;
      c.gridy = 0;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_select.add(input_cont, c);
    }

    {
      final GridBagConstraints c = new GridBagConstraints();
      c.gridx = 4;
      c.gridy = 1;
      c.insets = padding;
      c.gridheight = 1;
      c.gridwidth = 1;
      controls_select.add(select, c);
      c.gridy = 2;
      controls_select.add(delete, c);
    }

    return controls_select;
  }

  private JPanel makeViewerControls()
  {
    final JButton clear = new JButton("Clear");
    final JButton random = new JButton("Randomize");
    final JButton quit = new JButton("Quit");

    clear.addActionListener(new ActionListener() {
      @SuppressWarnings({ "unused" }) @Override public void actionPerformed(
        final ActionEvent e)
      {
        OctTreeViewer.this.commandClear();
      }
    });

    quit.addActionListener(new ActionListener() {
      @SuppressWarnings("unused") @Override public void actionPerformed(
        final ActionEvent event)
      {
        OctTreeViewer.this.commandQuit();
      }
    });

    random.addActionListener(new ActionListener() {
      @SuppressWarnings("unused") @Override public void actionPerformed(
        final ActionEvent _)
      {
        OctTreeViewer.this.commandRandomize();
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
