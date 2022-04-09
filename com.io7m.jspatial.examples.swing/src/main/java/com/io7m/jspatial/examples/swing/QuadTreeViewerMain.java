/*
 * Copyright © 2017 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Main program.
 */

public final class QuadTreeViewerMain
{
  private QuadTreeViewerMain()
  {

  }

  /**
   * Command line entry point.
   *
   * @param args Command line arguments
   */

  public static void main(final String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      final QuadTreeWindow win = new QuadTreeWindow();
      win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      win.pack();
      win.setVisible(true);
    });
  }
}
