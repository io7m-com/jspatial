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

package com.io7m.jspatial.octtrees;

import com.io7m.junreachable.UnreachableCodeException;

/**
 * Checks for octtree invariants.
 */

final class OctTreeChecks
{
  static void checkSize(
    final String name,
    final int width,
    final int height,
    final int depth)
  {
    if (width < 2) {
      final String s = String.format("%s: Width must be >= 2", name);
      throw new IllegalArgumentException(s);
    }
    if ((width % 2) != 0) {
      final String s = String.format("%s: Width must be even", name);
      throw new IllegalArgumentException(s);
    }

    if (height < 2) {
      final String s = String.format("%s: Height must be >= 2", name);
      throw new IllegalArgumentException(s);
    }
    if ((height % 2) != 0) {
      final String s = String.format("%s: Height must be even", name);
      throw new IllegalArgumentException(s);
    }

    if (depth < 2) {
      final String s = String.format("%s: Depth must be >= 2", name);
      throw new IllegalArgumentException(s);
    }
    if ((depth % 2) != 0) {
      final String s = String.format("%s: Depth must be even", name);
      throw new IllegalArgumentException(s);
    }
  }

  private OctTreeChecks()
  {
    throw new UnreachableCodeException();
  }
}
