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

package tests.quadtrees;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.quadtrees.Quadrants;
import com.io7m.jtensors.VectorI2I;

@SuppressWarnings("static-method") public final class QuadrantsTest
{
  @Test public void testQuadrantsSimple()
  {
    final VectorI2I lower = new VectorI2I(8, 8);
    final VectorI2I upper = new VectorI2I(15, 15);
    final Quadrants q = Quadrants.split(lower, upper);

    Assert.assertEquals(8, q.getX0Y0Lower().getXI());
    Assert.assertEquals(8, q.getX0Y0Lower().getYI());
    Assert.assertEquals(11, q.getX0Y0Upper().getXI());
    Assert.assertEquals(11, q.getX0Y0Upper().getYI());

    Assert.assertEquals(12, q.getX1Y0Lower().getXI());
    Assert.assertEquals(8, q.getX1Y0Lower().getYI());
    Assert.assertEquals(15, q.getX1Y0Upper().getXI());
    Assert.assertEquals(11, q.getX1Y0Upper().getYI());

    Assert.assertEquals(8, q.getX0Y1Lower().getXI());
    Assert.assertEquals(12, q.getX0Y1Lower().getYI());
    Assert.assertEquals(11, q.getX0Y1Upper().getXI());
    Assert.assertEquals(15, q.getX0Y1Upper().getYI());

    Assert.assertEquals(12, q.getX1Y1Lower().getXI());
    Assert.assertEquals(12, q.getX1Y1Lower().getYI());
    Assert.assertEquals(15, q.getX1Y1Upper().getXI());
    Assert.assertEquals(15, q.getX1Y1Upper().getYI());
  }
}
