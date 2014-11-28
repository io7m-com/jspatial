package com.io7m.jspatial.tests.quadtrees;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.Dimensions;
import com.io7m.jspatial.RayI2D;
import com.io7m.jspatial.SDType;
import com.io7m.jspatial.quadtrees.QuadTreeMemberType;
import com.io7m.jspatial.quadtrees.QuadTreeRaycastResult;
import com.io7m.jspatial.quadtrees.QuadTreeSDPruneLimit;
import com.io7m.jspatial.quadtrees.QuadTreeSDType;
import com.io7m.jspatial.quadtrees.QuadTreeTraversalType;
import com.io7m.jspatial.quadtrees.QuadTreeType;
import com.io7m.jspatial.quadtrees.QuadrantType;
import com.io7m.jspatial.tests.Rectangle;
import com.io7m.jspatial.tests.utilities.TestUtilities;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.junreachable.UnreachableCodeException;

@SuppressWarnings({ "static-method" }) public final class QuadTreeSDPruneLimitTest extends
  QuadTreeCommonTests
{
  @Override <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad128()
  {
    try {
      final VectorReadable2IType size = new VectorI2I(128, 128);
      final VectorReadable2IType position = VectorI2I.ZERO;
      final VectorI2I limit = new VectorI2I(2, 2);
      return QuadTreeSDPruneLimit.newQuadTree(size, position, limit);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad16()
  {
    try {
      final VectorReadable2IType size = new VectorI2I(16, 16);
      final VectorReadable2IType position = VectorI2I.ZERO;
      final VectorI2I limit = new VectorI2I(2, 2);
      return QuadTreeSDPruneLimit.newQuadTree(size, position, limit);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad2()
  {
    try {
      final VectorReadable2IType size = new VectorI2I(2, 2);
      final VectorReadable2IType position = VectorI2I.ZERO;
      final VectorI2I limit = new VectorI2I(2, 2);
      return QuadTreeSDPruneLimit.newQuadTree(size, position, limit);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad32()
  {
    try {
      final VectorReadable2IType size = new VectorI2I(32, 32);
      final VectorReadable2IType position = VectorI2I.ZERO;
      final VectorI2I limit = new VectorI2I(2, 2);
      return QuadTreeSDPruneLimit.newQuadTree(size, position, limit);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad512()
  {
    try {
      final VectorReadable2IType size = new VectorI2I(512, 512);
      final VectorReadable2IType position = VectorI2I.ZERO;
      final VectorI2I limit = new VectorI2I(2, 2);
      return QuadTreeSDPruneLimit.newQuadTree(size, position, limit);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Test public void testClearDynamic()
    throws Exception
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(32, 32),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));
    final Rectangle[] dynamics = TestUtilities.makeRectangles(0, 32);
    final Rectangle[] statics = TestUtilities.makeRectangles(10, 32);

    for (final Rectangle r : dynamics) {
      final boolean in = q.quadTreeInsertSD(r, SDType.SD_DYNAMIC);
      Assert.assertTrue(in);
    }
    for (final Rectangle r : statics) {
      final boolean in = q.quadTreeInsertSD(r, SDType.SD_STATIC);
      Assert.assertTrue(in);
    }

    {
      final IterationCounter counter = new IterationCounter();
      q.quadTreeIterateObjects(counter);
      Assert.assertEquals(8, counter.count);
    }

    q.quadTreeSDClearDynamic();

    {
      final IterationCounter counter = new IterationCounter();
      q.quadTreeIterateObjects(counter);
      Assert.assertEquals(4, counter.count);
    }
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitXOdd()
  {
    final VectorReadable2IType size = new VectorI2I(4, 4);
    final VectorReadable2IType position = VectorI2I.ZERO;
    final VectorReadable2IType size_minimum = new VectorI2I(3, 2);
    QuadTreeSDPruneLimit.newQuadTree(size, position, size_minimum);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitXTooBig()
  {
    final VectorReadable2IType size = new VectorI2I(2, 2);
    final VectorReadable2IType position = VectorI2I.ZERO;
    final VectorReadable2IType size_minimum = new VectorI2I(4, 2);
    QuadTreeSDPruneLimit.newQuadTree(size, position, size_minimum);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitXTooSmall()
  {
    final VectorReadable2IType size = new VectorI2I(2, 2);
    final VectorReadable2IType position = VectorI2I.ZERO;
    final VectorReadable2IType size_minimum = new VectorI2I(1, 2);
    QuadTreeSDPruneLimit.newQuadTree(size, position, size_minimum);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitYOdd()
  {
    final VectorReadable2IType size = new VectorI2I(4, 4);
    final VectorReadable2IType position = VectorI2I.ZERO;
    final VectorReadable2IType size_minimum = new VectorI2I(2, 3);
    QuadTreeSDPruneLimit.newQuadTree(size, position, size_minimum);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitYTooBig()
  {
    final VectorReadable2IType size = new VectorI2I(2, 2);
    final VectorReadable2IType position = VectorI2I.ZERO;
    final VectorReadable2IType size_minimum = new VectorI2I(2, 4);
    QuadTreeSDPruneLimit.newQuadTree(size, position, size_minimum);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitYTooSmall()
  {
    final VectorReadable2IType size = new VectorI2I(2, 2);
    final VectorReadable2IType position = VectorI2I.ZERO;
    final VectorReadable2IType size_minimum = new VectorI2I(2, 1);
    QuadTreeSDPruneLimit.newQuadTree(size, position, size_minimum);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateSDXOdd()
  {
    QuadTreeSDPruneLimit.newQuadTree(
      new VectorI2I(3, 2),
      VectorI2I.ZERO,
      new VectorI2I(2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateSDXTooSmall()
  {
    QuadTreeSDPruneLimit.newQuadTree(
      new VectorI2I(1, 2),
      VectorI2I.ZERO,
      new VectorI2I(2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateSDYOdd()
  {
    QuadTreeSDPruneLimit.newQuadTree(
      new VectorI2I(4, 3),
      VectorI2I.ZERO,
      new VectorI2I(2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateSDYTooSmall()
  {
    QuadTreeSDPruneLimit.newQuadTree(
      new VectorI2I(2, 1),
      VectorI2I.ZERO,
      new VectorI2I(2, 2));
  }

  @Test public void testInsertLimit()
    throws Exception
  {
    final QuadTreeType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(16, 16),
        VectorI2I.ZERO,
        new VectorI2I(8, 8));

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    q.quadTreeTraverse(new QuadTreeTraversalType<Exception>() {
      @Override public void visit(
        final int depth,
        final VectorReadable2IType lower,
        final VectorReadable2IType upper)
        throws Exception
      {
        Assert.assertTrue(Dimensions.getSpanSizeX(lower, upper) >= 8);
        Assert.assertTrue(Dimensions.getSpanSizeX(lower, upper) >= 8);
      }
    });
  }

  @Test public void testInsertSplitNotX()
    throws Exception
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(2, 4),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    final Counter counter = new Counter();
    q.quadTreeTraverse(counter);
    Assert.assertEquals(1, counter.count);
  }

  @Test public void testInsertSplitNotY()
    throws Exception
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(4, 2),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    final Counter counter = new Counter();
    q.quadTreeTraverse(counter);
    Assert.assertEquals(1, counter.count);
  }

  @Test public void testInsertTypeDynamicCollision()
    throws Exception
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(16, 16),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(12, 12));

    boolean in = false;
    in = q.quadTreeInsertSD(r, SDType.SD_DYNAMIC);
    Assert.assertTrue(in);
    in = q.quadTreeInsertSD(r, SDType.SD_STATIC);
    Assert.assertFalse(in);
  }

  @Test public void testInsertTypeStaticCollision()
    throws Exception
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(16, 16),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(12, 12));

    boolean in = false;
    in = q.quadTreeInsertSD(r, SDType.SD_STATIC);
    Assert.assertTrue(in);
    in = q.quadTreeInsertSD(r, SDType.SD_DYNAMIC);
    Assert.assertFalse(in);
  }

  @Test public void testIterateStopEarlyDynamic()
    throws Exception
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(16, 16),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

    final Rectangle[] rectangles = TestUtilities.makeRectangles(0, 16);

    for (final Rectangle r : rectangles) {
      final boolean in = q.quadTreeInsertSD(r, SDType.SD_DYNAMIC);
      Assert.assertTrue(in);
    }

    final IterationChecker1 counter = new IterationChecker1() {
      @Override public Boolean call(
        final Rectangle x)
      {
        ++this.count;
        if (this.count >= 2) {
          return Boolean.FALSE;
        }
        return Boolean.TRUE;
      }
    };

    q.quadTreeIterateObjects(counter);

    Assert.assertEquals(2, counter.count);
  }

  @Test public void testIterateStopEarlyStatic()
    throws Exception
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(16, 16),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

    final Rectangle[] rectangles = TestUtilities.makeRectangles(0, 16);

    for (final Rectangle r : rectangles) {
      final boolean in = q.quadTreeInsertSD(r, SDType.SD_STATIC);
      Assert.assertTrue(in);
    }

    final IterationChecker1 counter = new IterationChecker1() {
      @Override public Boolean call(
        final Rectangle x)
      {
        ++this.count;
        if (this.count >= 2) {
          return Boolean.FALSE;
        }
        return Boolean.TRUE;
      }
    };

    q.quadTreeIterateObjects(counter);

    Assert.assertEquals(2, counter.count);
  }

  @Test public void testQueryContainingStatic()
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(128, 128),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

    final boolean in =
      q.quadTreeInsertSD(new Rectangle(
        0,
        new VectorI2I(66, 66),
        new VectorI2I(127, 127)), SDType.SD_STATIC);
    Assert.assertTrue(in);

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(
        0,
        new VectorI2I(66, 66),
        new VectorI2I(127, 127)), items);

      Assert.assertEquals(1, items.size());
    }
  }

  @Test public void testQueryContainingStaticNot()
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(128, 128),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

    final boolean in =
      q.quadTreeInsertSD(new Rectangle(
        0,
        new VectorI2I(66, 66),
        new VectorI2I(127, 127)), SDType.SD_STATIC);
    Assert.assertTrue(in);

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(
        0,
        new VectorI2I(0, 0),
        new VectorI2I(65, 65)), items);

      Assert.assertEquals(0, items.size());
    }
  }

  @Test public void testQueryOverlappingStatic()
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(128, 128),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

    final Rectangle[] dynamics = TestUtilities.makeRectangles(0, 128);
    final Rectangle[] statics = TestUtilities.makeRectangles(10, 128);

    for (final Rectangle r : dynamics) {
      final boolean in = q.quadTreeInsertSD(r, SDType.SD_DYNAMIC);
      Assert.assertTrue(in);
    }
    for (final Rectangle r : statics) {
      final boolean in = q.quadTreeInsertSD(r, SDType.SD_STATIC);
      Assert.assertTrue(in);
    }

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(
        0,
        new VectorI2I(16, 16),
        new VectorI2I(80, 80)), items);

      Assert.assertEquals(8, items.size());
    }
  }

  @Test public void testQueryOverlappingStaticNot()
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(128, 128),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

    final boolean in =
      q.quadTreeInsertSD(new Rectangle(
        0,
        new VectorI2I(66, 66),
        new VectorI2I(127, 127)), SDType.SD_STATIC);
    Assert.assertTrue(in);

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(
        0,
        new VectorI2I(0, 0),
        new VectorI2I(65, 65)), items);

      Assert.assertEquals(0, items.size());
    }
  }

  @Test public void testRaycastQuadrants()
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(512, 512),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

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
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(512, 512),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));

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

  @Test public void testRemoveSubDynamic()
    throws Exception
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(32, 32),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));
    final Rectangle[] rectangles = TestUtilities.makeRectangles(0, 32);

    for (final Rectangle r : rectangles) {
      final boolean in = q.quadTreeInsertSD(r, SDType.SD_DYNAMIC);
      Assert.assertTrue(in);
    }

    for (final Rectangle r : rectangles) {
      final boolean removed = q.quadTreeRemove(r);
      Assert.assertTrue(removed);
    }
  }

  @Test public void testRemoveSubStatic()
    throws Exception
  {
    final QuadTreeSDType<Rectangle> q =
      QuadTreeSDPruneLimit.newQuadTree(
        new VectorI2I(32, 32),
        VectorI2I.ZERO,
        new VectorI2I(2, 2));
    final Rectangle[] rectangles = TestUtilities.makeRectangles(0, 32);

    for (final Rectangle r : rectangles) {
      final boolean in = q.quadTreeInsertSD(r, SDType.SD_STATIC);
      Assert.assertTrue(in);
    }

    for (final Rectangle r : rectangles) {
      final boolean removed = q.quadTreeRemove(r);
      Assert.assertTrue(removed);
    }
  }

}
