package com.io7m.jspatial;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.UnreachableCodeException;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorReadable2I;

public final class QuadTreeLimitTest extends QuadTreeCommonTests
{
  @Override <T extends QuadTreeMember<T>> QuadTreeInterface<T> makeQuad128()
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(128);
    config.setSizeY(128);

    try {
      return new QuadTreeLimit<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMember<T>> QuadTreeInterface<T> makeQuad16()
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(16);
    config.setSizeY(16);

    try {
      return new QuadTreeLimit<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMember<T>> QuadTreeInterface<T> makeQuad2()
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(2);

    try {
      return new QuadTreeLimit<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMember<T>> QuadTreeInterface<T> makeQuad32()
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(32);
    config.setSizeY(32);

    try {
      return new QuadTreeLimit<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMember<T>> QuadTreeInterface<T> makeQuad512()
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(512);
    config.setSizeY(512);

    try {
      return new QuadTreeLimit<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitXOdd()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(4);
    config.setSizeY(4);
    config.setMinimumSizeX(3);
    config.setMinimumSizeY(2);
    final QuadTreeLimit<Rectangle> q = new QuadTreeLimit<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitXTooBig()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(2);
    config.setMinimumSizeX(4);
    config.setMinimumSizeY(2);
    final QuadTreeLimit<Rectangle> q = new QuadTreeLimit<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitXTooSmall()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(2);
    config.setMinimumSizeX(1);
    config.setMinimumSizeY(2);
    final QuadTreeLimit<Rectangle> q = new QuadTreeLimit<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitYOdd()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(4);
    config.setSizeY(4);
    config.setMinimumSizeX(2);
    config.setMinimumSizeY(3);
    final QuadTreeLimit<Rectangle> q = new QuadTreeLimit<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitYTooBig()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(2);
    config.setMinimumSizeX(2);
    config.setMinimumSizeY(4);
    final QuadTreeLimit<Rectangle> q = new QuadTreeLimit<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitYTooSmall()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(2);
    config.setMinimumSizeX(2);
    config.setMinimumSizeY(1);
    final QuadTreeLimit<Rectangle> q = new QuadTreeLimit<Rectangle>(config);
  }

  @SuppressWarnings("static-method") @Test public void testInsertLimit()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(16);
    config.setSizeY(16);
    config.setMinimumSizeX(8);
    config.setMinimumSizeY(8);
    final QuadTreeLimit<Rectangle> q = new QuadTreeLimit<Rectangle>(config);

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    q.quadTreeTraverse(new QuadTreeTraversal() {
      @SuppressWarnings("unused") @Override public void visit(
        final int depth,
        final @Nonnull VectorReadable2I lower,
        final @Nonnull VectorReadable2I upper)
        throws Exception
      {
        Assert.assertTrue(Dimensions.getSpanSizeX(lower, upper) >= 8);
        Assert.assertTrue(Dimensions.getSpanSizeX(lower, upper) >= 8);
      }
    });
  }

  @SuppressWarnings("static-method") @Test public void testQuadrantsSimple()
  {
    final VectorI2I lower = new VectorI2I(8, 8);
    final VectorI2I upper = new VectorI2I(15, 15);
    final QuadTreeLimit.Quadrants q =
      new QuadTreeLimit.Quadrants(lower, upper);

    Assert.assertEquals(8, q.x0y0_lower.x);
    Assert.assertEquals(8, q.x0y0_lower.y);
    Assert.assertEquals(11, q.x0y0_upper.x);
    Assert.assertEquals(11, q.x0y0_upper.y);

    Assert.assertEquals(12, q.x1y0_lower.x);
    Assert.assertEquals(8, q.x1y0_lower.y);
    Assert.assertEquals(15, q.x1y0_upper.x);
    Assert.assertEquals(11, q.x1y0_upper.y);

    Assert.assertEquals(8, q.x0y1_lower.x);
    Assert.assertEquals(12, q.x0y1_lower.y);
    Assert.assertEquals(11, q.x0y1_upper.x);
    Assert.assertEquals(15, q.x0y1_upper.y);

    Assert.assertEquals(12, q.x1y1_lower.x);
    Assert.assertEquals(12, q.x1y1_lower.y);
    Assert.assertEquals(15, q.x1y1_upper.x);
    Assert.assertEquals(15, q.x1y1_upper.y);
  }

  @SuppressWarnings("static-method") @Test public void testRaycastQuadrants()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(512);
    config.setSizeY(512);
    final QuadTreeLimit<Rectangle> q = new QuadTreeLimit<Rectangle>(config);

    q.quadTreeInsert(new Rectangle(0, new VectorI2I(32, 32), new VectorI2I(
      80,
      80)));
    q.quadTreeInsert(new Rectangle(1, new VectorI2I(400, 400), new VectorI2I(
      480,
      480)));

    final RayI2D ray =
      new RayI2D(VectorI2D.ZERO, VectorI2D.normalize(new VectorI2D(511, 511)));
    final SortedSet<QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant>> items =
      new TreeSet<QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(6, items.size());

    final Iterator<QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant>> iter =
      items.iterator();

    {
      final QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeLimit<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(0, quad.boundingAreaLower().getXI());
      Assert.assertEquals(0, quad.boundingAreaLower().getYI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeLimit<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(64, quad.boundingAreaLower().getXI());
      Assert.assertEquals(64, quad.boundingAreaLower().getYI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeLimit<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(128, quad.boundingAreaLower().getXI());
      Assert.assertEquals(128, quad.boundingAreaLower().getYI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeLimit<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(256, quad.boundingAreaLower().getXI());
      Assert.assertEquals(256, quad.boundingAreaLower().getYI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeLimit<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(384, quad.boundingAreaLower().getXI());
      Assert.assertEquals(384, quad.boundingAreaLower().getYI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeLimit<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(448, quad.boundingAreaLower().getXI());
      Assert.assertEquals(448, quad.boundingAreaLower().getYI());
      Assert.assertEquals(511, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(511, quad.boundingAreaUpper().getYI());
    }

    Assert.assertFalse(iter.hasNext());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRaycastQuadrantsNegativeRay()
      throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(512);
    config.setSizeY(512);
    final QuadTreeLimit<Rectangle> q = new QuadTreeLimit<Rectangle>(config);

    final RayI2D ray =
      new RayI2D(new VectorI2D(512, 512), VectorI2D.normalize(new VectorI2D(
        -0.5,
        -0.5)));
    final SortedSet<QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant>> items =
      new TreeSet<QuadTreeRaycastResult<QuadTreeLimit<Rectangle>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(1, items.size());
  }

  @SuppressWarnings("static-method") @Test public void testInsertSplitNotX()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(128);
    config.setSizeY(128);
    config.setMinimumSizeX(128);
    config.setMinimumSizeY(2);
    final QuadTreeInterface<Rectangle> q =
      new QuadTreeLimit<Rectangle>(config);

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    final Counter counter = new Counter();
    q.quadTreeTraverse(counter);
    Assert.assertEquals(1, counter.count);
  }

  @SuppressWarnings("static-method") @Test public void testInsertSplitNotY()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(128);
    config.setSizeY(128);
    config.setMinimumSizeX(2);
    config.setMinimumSizeY(128);
    final QuadTreeInterface<Rectangle> q =
      new QuadTreeLimit<Rectangle>(config);

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    final Counter counter = new Counter();
    q.quadTreeTraverse(counter);
    Assert.assertEquals(1, counter.count);
  }
}
