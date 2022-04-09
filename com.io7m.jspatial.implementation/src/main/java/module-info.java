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

import com.io7m.jspatial.implementation.OctTreeSupplierD;
import com.io7m.jspatial.implementation.OctTreeSupplierI;
import com.io7m.jspatial.implementation.OctTreeSupplierL;
import com.io7m.jspatial.implementation.QuadTreeSupplierD;
import com.io7m.jspatial.implementation.QuadTreeSupplierI;
import com.io7m.jspatial.implementation.QuadTreeSupplierL;

/**
 * Spatial data structures (Main implementation)
 */

module com.io7m.jspatial.implementation
{
  requires static org.osgi.service.component.annotations;

  requires com.io7m.jspatial.api;
  requires com.io7m.junreachable.core;
  requires com.io7m.jregions.core;
  requires it.unimi.dsi.fastutil;
  requires com.io7m.jaffirm.core;
  requires com.io7m.jtensors.core;

  provides com.io7m.jspatial.api.octtrees.OctTreeSupplierDType with OctTreeSupplierD;
  provides com.io7m.jspatial.api.octtrees.OctTreeSupplierLType with OctTreeSupplierL;
  provides com.io7m.jspatial.api.octtrees.OctTreeSupplierIType with OctTreeSupplierI;

  provides com.io7m.jspatial.api.quadtrees.QuadTreeSupplierDType with QuadTreeSupplierD;
  provides com.io7m.jspatial.api.quadtrees.QuadTreeSupplierLType with QuadTreeSupplierL;
  provides com.io7m.jspatial.api.quadtrees.QuadTreeSupplierIType with QuadTreeSupplierI;

  exports com.io7m.jspatial.implementation;
}
