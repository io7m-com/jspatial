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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

/**
 * Viewer window.
 */

final class QuadTreeWindow extends JFrame
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(QuadTreeWindow.class);
  }

  private final QuadTreeCanvas canvas;
  private final QuadTreeControls controls;
  private final JSplitPane split;
  private final StatusBar status;
  private final PublishSubject<LogMessage> messages;
  private final JScrollPane controls_scroll;
  private final JScrollPane canvas_scroll;

  QuadTreeWindow()
  {
    super("Quad Tree Viewer");

    this.setPreferredSize(new Dimension(800, 600));

    this.setJMenuBar(QuadTreeWindow.makeMenu());

    this.messages = PublishSubject.create();
    this.controls = new QuadTreeControls();
    this.controls_scroll = new JScrollPane(this.controls);
    this.canvas = new QuadTreeCanvas(this.controls.events(), this.messages);
    this.canvas_scroll = new JScrollPane(this.canvas);

    this.split = new JSplitPane(
      JSplitPane.HORIZONTAL_SPLIT, this.canvas_scroll, this.controls_scroll);

    this.status = new StatusBar(this.messages);

    Thread.setDefaultUncaughtExceptionHandler(
      (t, e) -> {
        QuadTreeWindow.LOG.error("uncaught exception: ", e);
        this.messages.onNext(
          LogMessage.of(LogMessageType.Severity.ERROR, e.getMessage()));
      });

    final Container pane = this.getContentPane();
    pane.add(this.split, BorderLayout.CENTER);
    pane.add(this.status, BorderLayout.PAGE_END);

    this.messages.onNext(LogMessage.of(
      LogMessageType.Severity.INFO,
      "Quad tree viewer started."));

    this.pack();
    this.split.setDividerLocation(0.7);
  }

  private static JMenuBar makeMenu()
  {
    final JMenuItem m_file_quit = new JMenuItem("Quit");
    m_file_quit.addActionListener(e -> System.exit(0));

    final JMenu m_file = new JMenu("File");
    m_file.add(m_file_quit);

    final JMenuBar bar = new JMenuBar();
    bar.add(m_file);
    return bar;
  }

  private static final class StatusBar extends JPanel
  {
    private final Subscription subscription;
    private final JLabel text;

    StatusBar(final Observable<LogMessage> messages)
    {
      this.subscription = messages.subscribe(this::onMessage);
      this.text = new JLabel();
      this.add(this.text);
    }

    private void onMessage(final LogMessageType m)
    {
      switch (m.severity()) {
        case DEBUG:
          break;
        case INFO:
        case ERROR:
          this.text.setText(m.message());
          break;
      }
    }
  }
}
