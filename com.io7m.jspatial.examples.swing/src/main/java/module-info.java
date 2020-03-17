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

module com.io7m.jspatial.examples.swing
{
  requires java.desktop;

  requires static org.immutables.value;
  requires static com.io7m.immutables.style;

  requires com.io7m.jaffirm.core;
  requires com.io7m.jregions.core;
  requires com.io7m.jspatial.api;
  requires com.io7m.jspatial.implementation;
  requires com.io7m.jtensors.core;
  requires designgridlayout;
  requires io.reactivex.rxjava2;
  requires org.slf4j;

  exports com.io7m.jspatial.examples.swing;
}
