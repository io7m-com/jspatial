package com.io7m.jspatial.tests.quadtrees;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.RayI2D;
import com.io7m.jspatial.quadtrees.QuadTreeMemberType;
import com.io7m.jspatial.quadtrees.QuadTreePrune;
import com.io7m.jspatial.quadtrees.QuadTreeRaycastResult;
import com.io7m.jspatial.quadtrees.QuadTreeType;
import com.io7m.jspatial.quadtrees.QuadrantType;
import com.io7m.jspatial.tests.Rectangle;
import com.io7m.jspatial.tests.utilities.TestUtilities;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.junreachable.UnreachableCodeException;

@SuppressWarnings({ "unused", "static-method" }) public final class QuadTreePruneTest extends
QuadTreeCommonTests
{
  @Override <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad128()
  {
    try {
      return QuadTreePrune.newQuadTree(
        new VectorI2I(128, 128),
        VectorI2I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad16()
  {
    try {
      return QuadTreePrune.newQuadTree(new VectorI2I(16, 16), VectorI2I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad2()
  {
    try {
      return QuadTreePrune.newQuadTree(new VectorI2I(2, 2), VectorI2I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad32()
  {
    try {
      return QuadTreePrune.newQuadTree(new VectorI2I(32, 32), VectorI2I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad512()
  {
    try {
      return QuadTreePrune.newQuadTree(
        new VectorI2I(512, 512),
        VectorI2I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Test(expected = Exception.class) public void testCreateOddX()
  {
    QuadTreePrune.newQuadTree(new VectorI2I(3, 2), VectorI2I.ZERO);
  }

  @Test(expected = Exception.class) public void testCreateOddY()
  {
    QuadTreePrune.newQuadTree(new VectorI2I(2, 3), VectorI2I.ZERO);
  }

  @Test(expected = Exception.class) public void testCreateTooSmallX()
  {
    QuadTreePrune.newQuadTree(new VectorI2I(1, 2), VectorI2I.ZERO);
  }

  @Test(expected = Exception.class) public void testCreateTooSmallY()
  {
    QuadTreePrune.newQuadTree(new VectorI2I(2, 1), VectorI2I.ZERO);
  }

  @Test public void testInsertSplitNotX()
    throws Exception
  {
    final QuadTreeType<Rectangle> q =
      QuadTreePrune.newQuadTree(new VectorI2I(2, 4), VectorI2I.ZERO);

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    final Counter counter = new Counter();
    q.quadTreeTraverse(counter);
    Assert.assertEquals(5, counter.count);
  }

  @Test public void testInsertSplitNotY()
    throws Exception
  {
    final QuadTreeType<Rectangle> q =
      QuadTreePrune.newQuadTree(new VectorI2I(4, 2), VectorI2I.ZERO);

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    final Counter counter = new Counter();
    q.quadTreeTraverse(counter);
    Assert.assertEquals(5, counter.count);
  }

  @Test public void testRaycastQuadrants()
  {
    final QuadTreeType<Rectangle> q =
      QuadTreePrune.newQuadTree(new VectorI2I(512, 512), VectorI2I.ZERO);

    q.quadTreeInsert(new Rectangle(0, new VectorI2I(32, 32), new VectorI2I(
      80,
      80)));
    q.quadTreeInsert(new Rectangle(1, new VectorI2I(400, 400), new VectorI2I(
      480,
      480)));

    final RayI2D ray =
      new RayI2D(VectorI2D.ZERO, VectorI2D.normalize(new VectorI2D(511, 511)));
    final SortedSet<QuadTreeRaycastResult<QuadrantType>> items =
      new TreeSet<QuadTreeRaycastResult<QuadrantType>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(6, items.size());

    final Iterator<QuadTreeRaycastResult<QuadrantType>> iter =
      items.iterator();

    {
      final QuadTreeRaycastResult<QuadrantType> rq = iter.next();
      final QuadrantType quad = rq.getObject();
      Assert.assertEquals(0, quad.boundingAreaLower().getXI());
      Assert.assertEquals(0, quad.boundingAreaLower().getYI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadrantType> rq = iter.next();
      final QuadrantType quad = rq.getObject();
      Assert.assertEquals(64, quad.boundingAreaLower().getXI());
      Assert.assertEquals(64, quad.boundingAreaLower().getYI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadrantType> rq = iter.next();
      final QuadrantType quad = rq.getObject();
      Assert.assertEquals(128, quad.boundingAreaLower().getXI());
      Assert.assertEquals(128, quad.boundingAreaLower().getYI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadrantType> rq = iter.next();
      final QuadrantType quad = rq.getObject();
      Assert.assertEquals(256, quad.boundingAreaLower().getXI());
      Assert.assertEquals(256, quad.boundingAreaLower().getYI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadrantType> rq = iter.next();
      final QuadrantType quad = rq.getObject();
      Assert.assertEquals(384, quad.boundingAreaLower().getXI());
      Assert.assertEquals(384, quad.boundingAreaLower().getYI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadrantType> rq = iter.next();
      final QuadrantType quad = rq.getObject();
      Assert.assertEquals(448, quad.boundingAreaLower().getXI());
      Assert.assertEquals(448, quad.boundingAreaLower().getYI());
      Assert.assertEquals(511, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(511, quad.boundingAreaUpper().getYI());
    }

    Assert.assertFalse(iter.hasNext());
  }

  @Test public void testRaycastQuadrantsNegativeRay()
  {
    final QuadTreeType<Rectangle> q =
      QuadTreePrune.newQuadTree(new VectorI2I(512, 512), VectorI2I.ZERO);

    final RayI2D ray =
      new RayI2D(new VectorI2D(512, 512), VectorI2D.normalize(new VectorI2D(
        -0.5,
        -0.5)));
    final SortedSet<QuadTreeRaycastResult<QuadrantType>> items =
      new TreeSet<QuadTreeRaycastResult<QuadrantType>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(1, items.size());
  }

  @Test public void testRemovePrune()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad128();
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
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();

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
