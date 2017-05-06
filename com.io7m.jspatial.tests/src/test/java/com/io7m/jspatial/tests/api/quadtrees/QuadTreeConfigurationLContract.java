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

package com.io7m.jspatial.tests.api.quadtrees;

import com.io7m.jspatial.api.BoundingAreaL;
import com.io7m.jspatial.api.quadtrees.QuadTreeConfigurationLType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2L;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Contract for long integer tree configurations.
 */

public abstract class QuadTreeConfigurationLContract
{
  /**
   * Expected exception.
   */

  @Rule public final ExpectedException expected = ExpectedException.none();

  protected abstract <A extends QuadTreeConfigurationLType> Generator<A> generator();

  protected abstract QuadTreeConfigurationLType create(
    final BoundingAreaL area);

  /**
   * Identities.
   */

  @Test
  public final void testIdentities()
  {
    final Vector2L lower = Vector2L.of(0L, 0L);
    final Vector2L upper = Vector2L.of(100L, 100L);
    final BoundingAreaL area = BoundingAreaL.of(lower, upper);
    final QuadTreeConfigurationLType c = this.create(area);

    Assert.assertEquals(area, c.area());
    Assert.assertEquals(2L, c.minimumQuadrantHeight());
    Assert.assertEquals(2L, c.minimumQuadrantWidth());
    Assert.assertFalse(c.trimOnRemove());
  }
}
