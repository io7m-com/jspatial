package com.io7m.jspatial;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.UnreachableCodeException;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;

public final class QuadTreePruneTest extends QuadTreeCommonTests
{
  @Override <T extends QuadTreeMember<T>> QuadTreeInterface<T> makeQuad128()
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(128);
    config.setSizeY(128);

    try {
      return new QuadTreePrune<T>(config);
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
      return new QuadTreePrune<T>(config);
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
      return new QuadTreePrune<T>(config);
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
      return new QuadTreePrune<T>(config);
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
      return new QuadTreePrune<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddX()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(3);
    config.setSizeY(2);

    new QuadTreePrune<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddY()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(3);

    new QuadTreePrune<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallX()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(1);
    config.setSizeY(2);

    new QuadTreePrune<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallY()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(1);

    new QuadTreePrune<Rectangle>(config);
  }

  @SuppressWarnings("static-method") @Test public void testInsertSplitNotX()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(4);
    final QuadTreeInterface<Rectangle> q =
      new QuadTreePrune<Rectangle>(config);

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    final Counter counter = new Counter();
    q.quadTreeTraverse(counter);
    Assert.assertEquals(5, counter.count);
  }

  @SuppressWarnings("static-method") @Test public void testInsertSplitNotY()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(4);
    config.setSizeY(2);
    final QuadTreeInterface<Rectangle> q =
      new QuadTreePrune<Rectangle>(config);

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    final Counter counter = new Counter();
    q.quadTreeTraverse(counter);
    Assert.assertEquals(5, counter.count);
  }

  @SuppressWarnings("static-method") @Test public void testQuadrantsSimple()
  {
    final VectorI2I lower = new VectorI2I(8, 8);
    final VectorI2I upper = new VectorI2I(15, 15);
    final QuadTreePrune.Quadrants q =
      new QuadTreePrune.Quadrants(lower, upper);

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
    final QuadTreePrune<Rectangle> q = new QuadTreePrune<Rectangle>(config);

    q.quadTreeInsert(new Rectangle(0, new VectorI2I(32, 32), new VectorI2I(
      80,
      80)));
    q.quadTreeInsert(new Rectangle(1, new VectorI2I(400, 400), new VectorI2I(
      480,
      480)));

    final RayI2D ray =
      new RayI2D(VectorI2D.ZERO, VectorI2D.normalize(new VectorI2D(511, 511)));
    final SortedSet<QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant>> items =
      new TreeSet<QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(6, items.size());

    final Iterator<QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant>> iter =
      items.iterator();

    {
      final QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreePrune<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(0, quad.boundingAreaLower().getXI());
      Assert.assertEquals(0, quad.boundingAreaLower().getYI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreePrune<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(64, quad.boundingAreaLower().getXI());
      Assert.assertEquals(64, quad.boundingAreaLower().getYI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreePrune<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(128, quad.boundingAreaLower().getXI());
      Assert.assertEquals(128, quad.boundingAreaLower().getYI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreePrune<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(256, quad.boundingAreaLower().getXI());
      Assert.assertEquals(256, quad.boundingAreaLower().getYI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreePrune<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(384, quad.boundingAreaLower().getXI());
      Assert.assertEquals(384, quad.boundingAreaLower().getYI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreePrune<Rectangle>.Quadrant quad = rq.getObject();
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
    final QuadTreePrune<Rectangle> q = new QuadTreePrune<Rectangle>(config);

    final RayI2D ray =
      new RayI2D(new VectorI2D(512, 512), VectorI2D.normalize(new VectorI2D(
        -0.5,
        -0.5)));
    final SortedSet<QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant>> items =
      new TreeSet<QuadTreeRaycastResult<QuadTreePrune<Rectangle>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(1, items.size());
  }

  @Test public void testRemovePrune()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterface<Rectangle> q = this.makeQuad128();
    final Rectangle[] rectangles =
      TestUtilities.makeRectangles(0, q.quadTreeGetSizeX());

    for (final Rectangle r : rectangles) {
      {
        final boolean in = q.quadTreeInsert(r);
        Assert.assertTrue(in);
      }

      {
        final boolean in = q.quadTreeInsert(r);
        Assert.assertFalse(in);
      }
    }

    {
      final Counter counter = new Counter();
      q.quadTreeTraverse(counter);
      Assert.assertEquals(21, counter.count);
    }

    {
      final boolean removed = q.quadTreeRemove(rectangles[0]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.quadTreeTraverse(counter);
      Assert.assertEquals(17, counter.count);
    }

    {
      final boolean removed = q.quadTreeRemove(rectangles[1]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.quadTreeTraverse(counter);
      Assert.assertEquals(13, counter.count);
    }

    {
      final boolean removed = q.quadTreeRemove(rectangles[2]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.quadTreeTraverse(counter);
      Assert.assertEquals(9, counter.count);
    }

    {
      final boolean removed = q.quadTreeRemove(rectangles[3]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.quadTreeTraverse(counter);
      Assert.assertEquals(1, counter.count);
    }

    {
      final IterationCounter counter = new IterationCounter();
      q.quadTreeIterateObjects(counter);
      Assert.assertEquals(0, counter.count);
    }

    for (final Rectangle r : rectangles) {
      {
        final boolean in = q.quadTreeInsert(r);
        Assert.assertTrue(in);
      }

      {
        final boolean in = q.quadTreeInsert(r);
        Assert.assertFalse(in);
      }
    }

    {
      final IterationCounter counter = new IterationCounter();
      q.quadTreeIterateObjects(counter);
      Assert.assertEquals(4, counter.count);
    }

    {
      final Counter counter = new Counter();
      q.quadTreeTraverse(counter);
      Assert.assertEquals(21, counter.count);
    }
  }

  @Test public void testRemovePruneNotLeafNotEmpty()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterface<Rectangle> q = this.makeQuad16();

    /**
     * The rectangles are larger than the smallest quadrant size, and as a
     * result, will not be inserted into leaf nodes. Removing one of them will
     * trigger an attempt to prune nodes, which will fail due to a non-empty
     * non-leaf node.
     */

    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(1, 1), new VectorI2I(7, 7));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(1, 1), new VectorI2I(7, 7));

    {
      final boolean in = q.quadTreeInsert(r0);
      Assert.assertTrue(in);
    }

    {
      final boolean in = q.quadTreeInsert(r1);
      Assert.assertTrue(in);
    }

    {
      // (4 ^ 0) + (4 ^ 1) + 4
      final Counter counter = new Counter();
      q.quadTreeTraverse(counter);
      Assert.assertEquals(9, counter.count);
    }

    {
      final boolean removed = q.quadTreeRemove(r0);
      Assert.assertTrue(removed);
    }

    {
      // (4 ^ 0) + (4 ^ 1)
      final Counter counter = new Counter();
      q.quadTreeTraverse(counter);
      Assert.assertEquals(5, counter.count);
    }
  }
}
