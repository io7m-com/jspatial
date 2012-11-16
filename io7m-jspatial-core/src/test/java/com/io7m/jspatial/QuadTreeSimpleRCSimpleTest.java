package com.io7m.jspatial;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.functional.Function;
import com.io7m.jspatial.QuadTreeSimpleRCSimple.Quadrants;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorReadable2I;

public class QuadTreeSimpleRCSimpleTest
{
  private static class Counter implements QuadTreeTraversal
  {
    int count = 0;

    Counter()
    {

    }

    @SuppressWarnings("unused") @Override public void visit(
      final int depth,
      final VectorReadable2I lower,
      final VectorReadable2I upper)
      throws Exception
    {
      ++this.count;
    }
  }

  private static abstract class IterationChecker0 implements
    Function<Rectangle, Boolean>
  {
    boolean found_r0 = false;
    boolean found_r1 = false;
    boolean found_r2 = false;
    boolean found_r3 = false;

    IterationChecker0()
    {

    }
  }

  private static abstract class IterationChecker1 implements
    Function<Rectangle, Boolean>
  {
    int count = 0;

    IterationChecker1()
    {

    }
  }

  private static class IterationCounter implements
    Function<Rectangle, Boolean>
  {
    int count = 0;

    IterationCounter()
    {

    }

    @SuppressWarnings("unused") @Override public Boolean call(
      final Rectangle x)
    {
      ++this.count;
      return Boolean.TRUE;
    }
  }

  @SuppressWarnings("static-method") @Test public void testClear()
    throws ConstraintError,
      Exception
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(128, 128);
    final Rectangle r0 =
      new Rectangle(new VectorI2I(8, 8), new VectorI2I(48, 48));
    final Rectangle r1 =
      new Rectangle(new VectorI2I(8, 80), new VectorI2I(48, 120));
    final Rectangle r2 =
      new Rectangle(new VectorI2I(80, 8), new VectorI2I(120, 48));
    final Rectangle r3 =
      new Rectangle(new VectorI2I(80, 80), new VectorI2I(120, 120));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r2);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r3);
    Assert.assertTrue(in);

    {
      final IterationCounter counter = new IterationCounter();
      q.quadTreeIterateObjects(counter);
      Assert.assertEquals(4, counter.count);
    }

    {
      final IterationCounter counter = new IterationCounter();
      q.quadTreeClear();
      q.quadTreeIterateObjects(counter);
      Assert.assertEquals(0, counter.count);
    }
  }

  @SuppressWarnings("static-method") @Test public void testCountInitial()
    throws Exception,
      ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> qt =
      new QuadTreeSimpleRCSimple<Rectangle>(8, 8);
    final Counter c = new Counter();

    qt.quadTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @SuppressWarnings({ "static-method" }) @Test public void testCreate()
    throws ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(2, 2);
    Assert.assertEquals(2, q.quadTreeGetSizeX());
    Assert.assertEquals(2, q.quadTreeGetSizeY());
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddX()
    throws ConstraintError
  {
    new QuadTreeSimpleRCSimple<Rectangle>(3, 2);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddY()
    throws ConstraintError
  {
    new QuadTreeSimpleRCSimple<Rectangle>(2, 3);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallX()
    throws ConstraintError
  {
    new QuadTreeSimpleRCSimple<Rectangle>(1, 2);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallY()
    throws ConstraintError
  {
    new QuadTreeSimpleRCSimple<Rectangle>(2, 1);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInsertAtImmediateRootChildren()
      throws ConstraintError,
        Exception
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(16, 16);
    final Counter c = new Counter();
    final Rectangle r0 =
      new Rectangle(new VectorI2I(0, 0), new VectorI2I(7, 7));
    final Rectangle r1 =
      new Rectangle(new VectorI2I(8, 0), new VectorI2I(15, 7));
    final Rectangle r2 =
      new Rectangle(new VectorI2I(0, 8), new VectorI2I(7, 15));
    final Rectangle r3 =
      new Rectangle(new VectorI2I(8, 8), new VectorI2I(15, 15));

    {
      final VectorReadable2I bal = r0.boundingAreaLower();
      final VectorReadable2I bau = r0.boundingAreaUpper();
      Assert.assertEquals(8, QuadTreeSimpleRCSimple.getSpanSizeX(bal, bau));
      Assert.assertEquals(8, QuadTreeSimpleRCSimple.getSpanSizeY(bal, bau));
    }

    {
      final VectorReadable2I bal = r1.boundingAreaLower();
      final VectorReadable2I bau = r1.boundingAreaUpper();
      Assert.assertEquals(8, QuadTreeSimpleRCSimple.getSpanSizeX(bal, bau));
      Assert.assertEquals(8, QuadTreeSimpleRCSimple.getSpanSizeY(bal, bau));
    }

    {
      final VectorReadable2I bal = r2.boundingAreaLower();
      final VectorReadable2I bau = r2.boundingAreaUpper();
      Assert.assertEquals(8, QuadTreeSimpleRCSimple.getSpanSizeX(bal, bau));
      Assert.assertEquals(8, QuadTreeSimpleRCSimple.getSpanSizeY(bal, bau));
    }

    {
      final VectorReadable2I bal = r3.boundingAreaLower();
      final VectorReadable2I bau = r3.boundingAreaUpper();
      Assert.assertEquals(8, QuadTreeSimpleRCSimple.getSpanSizeX(bal, bau));
      Assert.assertEquals(8, QuadTreeSimpleRCSimple.getSpanSizeY(bal, bau));
    }

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r2);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r3);
    Assert.assertTrue(in);

    q.quadTreeTraverse(c);
    Assert.assertEquals(21, c.count);
  }

  @SuppressWarnings("static-method") @Test public void testInsertAtRoot()
    throws ConstraintError,
      Exception
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(16, 16);
    final Counter c = new Counter();
    final Rectangle r =
      new Rectangle(new VectorI2I(0, 0), new VectorI2I(12, 12));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    q.quadTreeTraverse(c);
    Assert.assertEquals(5, c.count);
  }

  @SuppressWarnings("static-method") @Test(expected = ConstraintError.class) public
    void
    testInsertIllFormed()
      throws ConstraintError,
        Exception
  {
    QuadTreeSimpleRCSimple<Rectangle> q = null;
    Rectangle r = null;

    try {
      q = new QuadTreeSimpleRCSimple<Rectangle>(16, 16);
      r = new Rectangle(new VectorI2I(12, 12), new VectorI2I(0, 0));
    } catch (final ConstraintError e) {
      Assert.fail(e.toString());
    }

    assert q != null;
    q.quadTreeInsert(r);
  }

  @SuppressWarnings("static-method") @Test public void testInsertOutside()
    throws ConstraintError,
      Exception
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(16, 16);
    final Rectangle r =
      new Rectangle(new VectorI2I(18, 18), new VectorI2I(28, 28));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertFalse(in);
  }

  @SuppressWarnings("static-method") @Test public void testInsertTiny()
    throws ConstraintError,
      Exception
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(16, 16);
    final Rectangle r =
      new Rectangle(new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(
        new VectorI2I(0, 0),
        new VectorI2I(15, 15)), items);
      Assert.assertEquals(1, items.size());
    }
  }

  @Test public void testIterate()
    throws ConstraintError,
      Exception
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(16, 16);
    final Rectangle r0 =
      new Rectangle(new VectorI2I(0, 0), new VectorI2I(7, 7));
    final Rectangle r1 =
      new Rectangle(new VectorI2I(8, 0), new VectorI2I(15, 7));
    final Rectangle r2 =
      new Rectangle(new VectorI2I(0, 8), new VectorI2I(7, 15));
    final Rectangle r3 =
      new Rectangle(new VectorI2I(8, 8), new VectorI2I(15, 15));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r2);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r3);
    Assert.assertTrue(in);

    final IterationChecker0 counter = new IterationChecker0() {
      @Override public Boolean call(
        final Rectangle x)
      {
        if (x == r0) {
          this.found_r0 = true;
        }
        if (x == r1) {
          this.found_r1 = true;
        }
        if (x == r2) {
          this.found_r2 = true;
        }
        if (x == r3) {
          this.found_r3 = true;
        }

        return Boolean.TRUE;
      }
    };

    q.quadTreeIterateObjects(counter);

    Assert.assertTrue(counter.found_r0);
    Assert.assertTrue(counter.found_r1);
    Assert.assertTrue(counter.found_r2);
    Assert.assertTrue(counter.found_r3);
  }

  @SuppressWarnings("static-method") @Test(expected = ConstraintError.class) public
    void
    testIterateNull()
      throws ConstraintError,
        Exception
  {
    QuadTreeSimpleRCSimple<Rectangle> q = null;

    try {
      q = new QuadTreeSimpleRCSimple<Rectangle>(16, 16);
    } catch (final ConstraintError e) {
      Assert.fail(e.toString());
    }

    assert q != null;
    q.quadTreeIterateObjects(null);
  }

  @Test public void testIterateStopEarly()
    throws ConstraintError,
      Exception
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(16, 16);
    final Rectangle r0 =
      new Rectangle(new VectorI2I(0, 0), new VectorI2I(7, 7));
    final Rectangle r1 =
      new Rectangle(new VectorI2I(8, 0), new VectorI2I(15, 7));
    final Rectangle r2 =
      new Rectangle(new VectorI2I(0, 8), new VectorI2I(7, 15));
    final Rectangle r3 =
      new Rectangle(new VectorI2I(8, 8), new VectorI2I(15, 15));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r2);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r3);
    Assert.assertTrue(in);

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
    final Quadrants q = new Quadrants(lower, upper);

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

  @SuppressWarnings("static-method") @Test public void testQueryContaining()
    throws ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(128, 128);
    final Rectangle r0 =
      new Rectangle(new VectorI2I(8, 8), new VectorI2I(48, 48));
    final Rectangle r1 =
      new Rectangle(new VectorI2I(8, 80), new VectorI2I(48, 120));
    final Rectangle r2 =
      new Rectangle(new VectorI2I(80, 8), new VectorI2I(120, 48));
    final Rectangle r3 =
      new Rectangle(new VectorI2I(80, 80), new VectorI2I(120, 120));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r2);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r3);
    Assert.assertTrue(in);

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(
        new VectorI2I(8 - 4, 8 - 4),
        new VectorI2I(48 + 4, 48 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r0));
    }

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(
        new VectorI2I(8 - 4, 80 - 4),
        new VectorI2I(48 + 2, 120 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r1));
    }

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(
        new VectorI2I(80 - 4, 8 - 4),
        new VectorI2I(120 + 4, 48 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r2));
    }

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(new VectorI2I(
        80 - 4,
        80 - 4), new VectorI2I(120 + 4, 120 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r3));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingExact()
      throws ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(128, 128);
    final Rectangle r0 =
      new Rectangle(new VectorI2I(8, 8), new VectorI2I(48, 48));
    final Rectangle r1 =
      new Rectangle(new VectorI2I(8, 80), new VectorI2I(48, 120));
    final Rectangle r2 =
      new Rectangle(new VectorI2I(80, 8), new VectorI2I(120, 48));
    final Rectangle r3 =
      new Rectangle(new VectorI2I(80, 80), new VectorI2I(120, 120));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r2);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r3);
    Assert.assertTrue(in);

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(
        new VectorI2I(0, 0),
        new VectorI2I(127, 127)), items);
      Assert.assertEquals(4, items.size());
      Assert.assertTrue(items.contains(r0));
      Assert.assertTrue(items.contains(r1));
      Assert.assertTrue(items.contains(r2));
      Assert.assertTrue(items.contains(r3));
    }
  }

  @SuppressWarnings("static-method") @Test public void testQueryOverlapping()
    throws ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(128, 128);
    final Rectangle r0 =
      new Rectangle(new VectorI2I(8, 8), new VectorI2I(48, 48));
    final Rectangle r1 =
      new Rectangle(new VectorI2I(8, 80), new VectorI2I(48, 120));
    final Rectangle r2 =
      new Rectangle(new VectorI2I(80, 8), new VectorI2I(120, 48));
    final Rectangle r3 =
      new Rectangle(new VectorI2I(80, 80), new VectorI2I(120, 120));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r2);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r3);
    Assert.assertTrue(in);

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(
        new VectorI2I(8 - 2, 8 - 2),
        new VectorI2I(8 + 2, 8 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r0));
    }

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(new VectorI2I(
        8 - 2,
        80 - 2), new VectorI2I(8 + 2, 80 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r1));
    }

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(new VectorI2I(
        80 - 2,
        8 - 2), new VectorI2I(80 + 2, 8 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r2));
    }

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(new VectorI2I(
        80 - 2,
        80 - 2), new VectorI2I(80 + 2, 80 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r3));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingExact()
      throws ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(128, 128);
    final Rectangle r0 =
      new Rectangle(new VectorI2I(8, 8), new VectorI2I(48, 48));
    final Rectangle r1 =
      new Rectangle(new VectorI2I(8, 80), new VectorI2I(48, 120));
    final Rectangle r2 =
      new Rectangle(new VectorI2I(80, 8), new VectorI2I(120, 48));
    final Rectangle r3 =
      new Rectangle(new VectorI2I(80, 80), new VectorI2I(120, 120));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r2);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r3);
    Assert.assertTrue(in);

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(
        new VectorI2I(8 + 2, 8 + 2),
        new VectorI2I(120 - 2, 120 - 2)), items);
      Assert.assertEquals(4, items.size());
      Assert.assertTrue(items.contains(r0));
      Assert.assertTrue(items.contains(r1));
      Assert.assertTrue(items.contains(r2));
      Assert.assertTrue(items.contains(r3));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingNotAll()
      throws ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(128, 128);
    final Rectangle r0 =
      new Rectangle(new VectorI2I(0, 0), new VectorI2I(127, 127));
    final Rectangle r1 =
      new Rectangle(new VectorI2I(4, 4), new VectorI2I(127, 127));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(
        new VectorI2I(0, 0),
        new VectorI2I(2, 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r0));
    }

    {
      final List<Rectangle> items = new LinkedList<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(new VectorI2I(
        127 - 4,
        127 - 4), new VectorI2I(127, 127)), items);
      Assert.assertEquals(2, items.size());
      Assert.assertTrue(items.contains(r0));
      Assert.assertTrue(items.contains(r1));
    }
  }

  @SuppressWarnings("static-method") @Test public void testRaycastQuadrants()
    throws ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(512, 512);

    q.quadTreeInsert(new Rectangle(new VectorI2I(32, 32), new VectorI2I(
      80,
      80)));
    q.quadTreeInsert(new Rectangle(new VectorI2I(400, 400), new VectorI2I(
      480,
      480)));

    final RayI2D ray =
      new RayI2D(VectorI2D.ZERO, VectorI2D.normalize(new VectorI2D(511, 511)));
    final SortedSet<RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant>> items =
      new TreeSet<RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(6, items.size());

    final Iterator<RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant>> iter =
      items.iterator();

    {
      final RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSimpleRCSimple<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(0, quad.boundingAreaLower().getXI());
      Assert.assertEquals(0, quad.boundingAreaLower().getYI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getYI());
    }

    {
      final RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSimpleRCSimple<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(64, quad.boundingAreaLower().getXI());
      Assert.assertEquals(64, quad.boundingAreaLower().getYI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getYI());
    }

    {
      final RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSimpleRCSimple<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(128, quad.boundingAreaLower().getXI());
      Assert.assertEquals(128, quad.boundingAreaLower().getYI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getYI());
    }

    {
      final RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSimpleRCSimple<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(256, quad.boundingAreaLower().getXI());
      Assert.assertEquals(256, quad.boundingAreaLower().getYI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getYI());
    }

    {
      final RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSimpleRCSimple<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(384, quad.boundingAreaLower().getXI());
      Assert.assertEquals(384, quad.boundingAreaLower().getYI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getYI());
    }

    {
      final RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeSimpleRCSimple<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(448, quad.boundingAreaLower().getXI());
      Assert.assertEquals(448, quad.boundingAreaLower().getYI());
      Assert.assertEquals(511, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(511, quad.boundingAreaUpper().getYI());
    }

    Assert.assertFalse(iter.hasNext());
  }

  @SuppressWarnings("static-method") @Test public void testRaycast()
    throws ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(512, 512);

    q.quadTreeInsert(new Rectangle(new VectorI2I(32, 32), new VectorI2I(
      80,
      80)));
    q.quadTreeInsert(new Rectangle(new VectorI2I(400, 32), new VectorI2I(
      400 + 32,
      80)));
    q.quadTreeInsert(new Rectangle(new VectorI2I(400, 400), new VectorI2I(
      480,
      480)));

    final RayI2D ray =
      new RayI2D(VectorI2D.ZERO, VectorI2D.normalize(new VectorI2D(511, 511)));
    final SortedSet<RaycastResult<Rectangle>> items =
      new TreeSet<RaycastResult<Rectangle>>();
    q.quadTreeQueryRaycast(ray, items);

    Assert.assertEquals(2, items.size());

    final Iterator<RaycastResult<Rectangle>> iter = items.iterator();

    {
      final RaycastResult<Rectangle> rr = iter.next();
      final Rectangle r = rr.getObject();
      Assert.assertEquals(32, r.boundingAreaLower().getXI());
      Assert.assertEquals(32, r.boundingAreaLower().getYI());
    }

    {
      final RaycastResult<Rectangle> rr = iter.next();
      final Rectangle r = rr.getObject();
      Assert.assertEquals(400, r.boundingAreaLower().getXI());
      Assert.assertEquals(400, r.boundingAreaLower().getYI());
    }

    Assert.assertFalse(iter.hasNext());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRaycastQuadrantsNegativeRay()
      throws ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(512, 512);

    final RayI2D ray =
      new RayI2D(new VectorI2D(512, 512), VectorI2D.normalize(new VectorI2D(
        -0.5,
        -0.5)));
    final SortedSet<RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant>> items =
      new TreeSet<RaycastResult<QuadTreeSimpleRCSimple<Rectangle>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(1, items.size());
  }

  @SuppressWarnings("static-method") @Test public void testSpan1D()
  {
    final int spans[] = new int[4];

    QuadTreeSimpleRCSimple.split1D(0, 3, spans);
    Assert.assertEquals(0, spans[0]);
    Assert.assertEquals(1, spans[1]);
    Assert.assertEquals(2, spans[2]);
    Assert.assertEquals(3, spans[3]);
  }

  @SuppressWarnings("static-method") @Test public void testSpan1DVarious()
  {
    final int spans[] = new int[4];

    for (int i = 2; i < 30; ++i) {
      QuadTreeSimpleRCSimple.split1D(0, (1 << i) - 1, spans);

      final int lower_lower = 0;
      final int lower_upper = (1 << (i - 1)) - 1;
      final int upper_lower = 1 << (i - 1);
      final int upper_upper = (1 << i) - 1;

      System.err.print("testSpan1DVarious: [");
      for (final int k : spans) {
        System.err.print(k);
        System.err.print(" ");
      }
      System.err.println("]");

      Assert.assertEquals(lower_lower, spans[0]);
      Assert.assertEquals(lower_upper, spans[1]);
      Assert.assertEquals(upper_lower, spans[2]);
      Assert.assertEquals(upper_upper, spans[3]);
    }
  }

  @SuppressWarnings("static-method") @Test public void testToString()
    throws ConstraintError
  {
    final QuadTreeSimpleRCSimple<Rectangle> q =
      new QuadTreeSimpleRCSimple<Rectangle>(128, 128);
    System.err.println(q.toString());
  }
}
