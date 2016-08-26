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

package com.io7m.jspatial.tests.implementation;

import com.io7m.jspatial.implementation.Subdivision;
import com.io7m.junreachable.UnreachableCodeException;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class SubdivisionTest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testSubdivision1LTooSmall()
  {
    this.expected.expect(RuntimeException.class);
    Subdivision.subdivide1L(0L, 0L, new long[4]);
  }

  @Test
  public void testSubdivision1LArrayTooSmall()
  {
    this.expected.expect(RuntimeException.class);
    Subdivision.subdivide1L(0L, 0L, new long[2]);
  }

  @Test
  public void testSubdivision1DArrayTooSmall()
  {
    this.expected.expect(RuntimeException.class);
    Subdivision.subdivide1D(0.0, 0.0, new double[2]);
  }

  @Test
  public void testSubdivisionUnreachable()
    throws Exception
  {
    final Constructor<Subdivision> c =
      Subdivision.class.getDeclaredConstructor();
    c.setAccessible(true);

    this.expected.expect(InvocationTargetException.class);
    this.expected.expectCause(Is.isA(UnreachableCodeException.class));
    c.newInstance();
  }
}
