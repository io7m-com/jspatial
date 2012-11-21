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

public final class QuadTreeSDTest extends QuadTreeCommonTests
{
  @Override <T extends QuadTreeMember<T>> QuadTreeInterface<T> makeQuad128()
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(128);
    config.setSizeY(128);

    try {
      return new QuadTreeSD<T>(config);
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
      return new QuadTreeSD<T>(config);
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
      return new QuadTreeSD<T>(config);
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
      return new QuadTreeSD<T>(config);
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
      return new QuadTreeSD<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateSDXOdd()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(3);
    config.setSizeY(4);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateSDXTooSmall()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(1);
    config.setSizeY(2);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateSDYOdd()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(4);
    config.setSizeY(3);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateSDYTooSmall()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(1);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInsertTypeDynamicCollision()
      throws ConstraintError,
        Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(16);
    config.setSizeY(16);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(12, 12));

    boolean in = false;
    in = q.quadTreeInsertSD(r, SDType.SD_DYNAMIC);
    Assert.assertTrue(in);
    in = q.quadTreeInsertSD(r, SDType.SD_STATIC);
    Assert.assertFalse(in);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInsertTypeStaticCollision()
      throws ConstraintError,
        Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(16);
    config.setSizeY(16);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(12, 12));

    boolean in = false;
    in = q.quadTreeInsertSD(r, SDType.SD_STATIC);
    Assert.assertTrue(in);
    in = q.quadTreeInsertSD(r, SDType.SD_DYNAMIC);
    Assert.assertFalse(in);
  }

  @Test public void testIterateStopEarlyDynamic()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(16);
    config.setSizeY(16);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);

    final Rectangle[] rectangles = TestUtilities.makeRectangles(0, 16);

    for (final Rectangle r : rectangles) {
      final boolean in = q.quadTreeInsertSD(r, SDType.SD_DYNAMIC);
      Assert.assertTrue(in);
    }

    final IterationChecker1 counter = new IterationChecker1() {
      @SuppressWarnings("unused") @Override public Boolean call(
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
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(16);
    config.setSizeY(16);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);

    final Rectangle[] rectangles = TestUtilities.makeRectangles(0, 16);

    for (final Rectangle r : rectangles) {
      final boolean in = q.quadTreeInsertSD(r, SDType.SD_STATIC);
      Assert.assertTrue(in);
    }

    final IterationChecker1 counter = new IterationChecker1() {
      @SuppressWarnings("unused") @Override public Boolean call(
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

  @SuppressWarnings("static-method") @Test public void testQuadrantsSimple()
  {
    final VectorI2I lower = new VectorI2I(8, 8);
    final VectorI2I upper = new VectorI2I(15, 15);
    final QuadTreeSD.Quadrants q = new QuadTreeSD.Quadrants(lower, upper);

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

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingStatic()
      throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);

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

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingStaticNot()
      throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);

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

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingStatic()
      throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);
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

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingStaticNot()
      throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);

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

  @SuppressWarnings("static-method") @Test public void testRaycastQuadrants()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(512);
    config.setSizeY(512);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);

    q.quadTreeInsert(new Rectangle(0, new VectorI2I(32, 32), new VectorI2I(
      80,
      80)));
    q.quadTreeInsert(new Rectangle(1, new VectorI2I(400, 400), new VectorI2I(
      480,
      480)));

    final RayI2D ray =
      new RayI2D(VectorI2D.ZERO, VectorI2D.normalize(new VectorI2D(511, 511)));
    final SortedSet<QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant>> items =
      new TreeSet<QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(6, items.size());

    final Iterator<QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant>> iter =
      items.iterator();

    {
      final QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSD<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(0, quad.boundingAreaLower().getXI());
      Assert.assertEquals(0, quad.boundingAreaLower().getYI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSD<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(64, quad.boundingAreaLower().getXI());
      Assert.assertEquals(64, quad.boundingAreaLower().getYI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSD<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(128, quad.boundingAreaLower().getXI());
      Assert.assertEquals(128, quad.boundingAreaLower().getYI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSD<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(256, quad.boundingAreaLower().getXI());
      Assert.assertEquals(256, quad.boundingAreaLower().getYI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSD<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(384, quad.boundingAreaLower().getXI());
      Assert.assertEquals(384, quad.boundingAreaLower().getYI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSD<Rectangle>.Quadrant quad = rq.getObject();
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
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);

    final RayI2D ray =
      new RayI2D(new VectorI2D(512, 512), VectorI2D.normalize(new VectorI2D(
        -0.5,
        -0.5)));
    final SortedSet<QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant>> items =
      new TreeSet<QuadTreeRaycastResult<QuadTreeSD<Rectangle>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(1, items.size());
  }

  @SuppressWarnings("static-method") @Test public void testRemoveSubDynamic()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(32);
    config.setSizeY(32);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);
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

  @SuppressWarnings("static-method") @Test public void testRemoveSubStatic()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(32);
    config.setSizeY(32);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);
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

  @SuppressWarnings("static-method") @Test public void testInsertSplitNotX()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(4);
    final QuadTreeInterface<Rectangle> q = new QuadTreeSD<Rectangle>(config);

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
    final QuadTreeInterface<Rectangle> q = new QuadTreeSD<Rectangle>(config);

    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    final Counter counter = new Counter();
    q.quadTreeTraverse(counter);
    Assert.assertEquals(5, counter.count);
  }

  @SuppressWarnings("static-method") @Test public void testClearDynamic()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(32);
    config.setSizeY(32);
    final QuadTreeSD<Rectangle> q = new QuadTreeSD<Rectangle>(config);
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
}
