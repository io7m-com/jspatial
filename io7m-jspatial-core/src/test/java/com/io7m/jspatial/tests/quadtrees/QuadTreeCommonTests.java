package tests.quadtrees;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jfunctional.PartialFunctionType;
import com.io7m.jnull.NullCheckException;
import com.io7m.jspatial.Dimensions;
import com.io7m.jspatial.RayI2D;
import com.io7m.jspatial.quadtrees.QuadTreeMemberType;
import com.io7m.jspatial.quadtrees.QuadTreeRaycastResult;
import com.io7m.jspatial.quadtrees.QuadTreeTraversalType;
import com.io7m.jspatial.quadtrees.QuadTreeType;
import com.io7m.jspatial.tests.Rectangle;
import tests.utilities.TestUtilities;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorReadable2IType;

public abstract class QuadTreeCommonTests
{
  protected static final class Counter implements
  QuadTreeTraversalType<Exception>
  {
    int count = 0;

    Counter()
    {

    }

    @Override public void visit(
      final int depth,
      final VectorReadable2IType lower,
      final VectorReadable2IType upper)
        throws Exception
    {
      ++this.count;
    }
  }

  protected static abstract class IterationChecker0 implements
  PartialFunctionType<Rectangle, Boolean, Exception>
  {
    boolean found_r0 = false;
    boolean found_r1 = false;
    boolean found_r2 = false;
    boolean found_r3 = false;

    IterationChecker0()
    {

    }
  }

  protected static abstract class IterationChecker1 implements
  PartialFunctionType<Rectangle, Boolean, Exception>
  {
    int count = 0;

    IterationChecker1()
    {

    }
  }

  protected static final class IterationCounter implements
  PartialFunctionType<Rectangle, Boolean, Exception>
  {
    int count = 0;

    IterationCounter()
    {

    }

    @Override public Boolean call(
      final Rectangle x)
    {
      ++this.count;
      return Boolean.TRUE;
    }
  }

  abstract <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad128();

  abstract <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad16();

  abstract <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad2();

  abstract <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad32();

  abstract <T extends QuadTreeMemberType<T>> QuadTreeType<T> makeQuad512();

  @Test public void testClear()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad128();
    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(8, 8), new VectorI2I(48, 48));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(8, 80), new VectorI2I(48, 120));
    final Rectangle r2 =
      new Rectangle(2, new VectorI2I(80, 8), new VectorI2I(120, 48));
    final Rectangle r3 =
      new Rectangle(3, new VectorI2I(80, 80), new VectorI2I(120, 120));

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

  @Test public void testCountInitial()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad128();
    final Counter c = new Counter();

    q.quadTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public void testCreate()
  {
    final QuadTreeType<Rectangle> q = this.makeQuad128();
    Assert.assertEquals(128, q.quadTreeGetSizeX());
    Assert.assertEquals(128, q.quadTreeGetSizeY());
    Assert.assertEquals(0, q.quadTreeGetPositionX());
    Assert.assertEquals(0, q.quadTreeGetPositionY());
  }

  @Test public void testInsertAtImmediateRootChildren()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();
    final Counter c = new Counter();
    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(7, 7));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(8, 0), new VectorI2I(15, 7));
    final Rectangle r2 =
      new Rectangle(2, new VectorI2I(0, 8), new VectorI2I(7, 15));
    final Rectangle r3 =
      new Rectangle(3, new VectorI2I(8, 8), new VectorI2I(15, 15));

    {
      final VectorReadable2IType bal = r0.boundingAreaLower();
      final VectorReadable2IType bau = r0.boundingAreaUpper();
      Assert.assertEquals(8, Dimensions.getSpanSizeX(bal, bau));
      Assert.assertEquals(8, Dimensions.getSpanSizeY(bal, bau));
    }

    {
      final VectorReadable2IType bal = r1.boundingAreaLower();
      final VectorReadable2IType bau = r1.boundingAreaUpper();
      Assert.assertEquals(8, Dimensions.getSpanSizeX(bal, bau));
      Assert.assertEquals(8, Dimensions.getSpanSizeY(bal, bau));
    }

    {
      final VectorReadable2IType bal = r2.boundingAreaLower();
      final VectorReadable2IType bau = r2.boundingAreaUpper();
      Assert.assertEquals(8, Dimensions.getSpanSizeX(bal, bau));
      Assert.assertEquals(8, Dimensions.getSpanSizeY(bal, bau));
    }

    {
      final VectorReadable2IType bal = r3.boundingAreaLower();
      final VectorReadable2IType bau = r3.boundingAreaUpper();
      Assert.assertEquals(8, Dimensions.getSpanSizeX(bal, bau));
      Assert.assertEquals(8, Dimensions.getSpanSizeY(bal, bau));
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

  @Test public void testInsertAtRoot()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();
    final Counter c = new Counter();
    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(12, 12));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    q.quadTreeTraverse(c);
    Assert.assertEquals(5, c.count);
  }

  @Test public void testInsertDuplicate()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();
    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(12, 12));

    boolean in = false;
    in = q.quadTreeInsert(r);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r);
    Assert.assertFalse(in);
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testInsertIllFormed()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();
    final Rectangle r =
      new Rectangle(0, new VectorI2I(12, 12), new VectorI2I(0, 0));

    q.quadTreeInsert(r);
  }

  @Test public void testInsertOutside()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();
    final Rectangle r =
      new Rectangle(0, new VectorI2I(18, 18), new VectorI2I(28, 28));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertFalse(in);
  }

  @Test public void testInsertTiny()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();
    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(
        0,
        new VectorI2I(0, 0),
        new VectorI2I(15, 15)), items);
      Assert.assertEquals(1, items.size());
    }
  }

  @Test public void testIterate()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();
    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(7, 7));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(8, 0), new VectorI2I(15, 7));
    final Rectangle r2 =
      new Rectangle(2, new VectorI2I(0, 8), new VectorI2I(7, 15));
    final Rectangle r3 =
      new Rectangle(3, new VectorI2I(8, 8), new VectorI2I(15, 15));

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

  @Test(expected = NullCheckException.class) public void testIterateNull()
    throws Throwable
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();
    @SuppressWarnings("unchecked") final PartialFunctionType<Rectangle, Boolean, Exception> f =
      (PartialFunctionType<Rectangle, Boolean, Exception>) TestUtilities
      .actuallyNull();
    q.quadTreeIterateObjects(f);
  }

  @Test public void testIterateStopEarly()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();
    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(7, 7));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(8, 0), new VectorI2I(15, 7));
    final Rectangle r2 =
      new Rectangle(2, new VectorI2I(0, 8), new VectorI2I(7, 15));
    final Rectangle r3 =
      new Rectangle(3, new VectorI2I(8, 8), new VectorI2I(15, 15));

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

  @Test public void testQueryContaining()
  {
    final QuadTreeType<Rectangle> q = this.makeQuad128();
    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(8, 8), new VectorI2I(48, 48));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(8, 80), new VectorI2I(48, 120));
    final Rectangle r2 =
      new Rectangle(2, new VectorI2I(80, 8), new VectorI2I(120, 48));
    final Rectangle r3 =
      new Rectangle(3, new VectorI2I(80, 80), new VectorI2I(120, 120));

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
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(0, new VectorI2I(
        8 - 4,
        8 - 4), new VectorI2I(48 + 4, 48 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r0));
    }

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(0, new VectorI2I(
        8 - 4,
        80 - 4), new VectorI2I(48 + 2, 120 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r1));
    }

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(0, new VectorI2I(
        80 - 4,
        8 - 4), new VectorI2I(120 + 4, 48 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r2));
    }

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(0, new VectorI2I(
        80 - 4,
        80 - 4), new VectorI2I(120 + 4, 120 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r3));
    }
  }

  @Test public void testQueryContainingExact()
  {
    final QuadTreeType<Rectangle> q = this.makeQuad128();
    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(8, 8), new VectorI2I(48, 48));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(8, 80), new VectorI2I(48, 120));
    final Rectangle r2 =
      new Rectangle(2, new VectorI2I(80, 8), new VectorI2I(120, 48));
    final Rectangle r3 =
      new Rectangle(3, new VectorI2I(80, 80), new VectorI2I(120, 120));

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
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaContaining(new Rectangle(
        0,
        new VectorI2I(0, 0),
        new VectorI2I(127, 127)), items);
      Assert.assertEquals(4, items.size());
      Assert.assertTrue(items.contains(r0));
      Assert.assertTrue(items.contains(r1));
      Assert.assertTrue(items.contains(r2));
      Assert.assertTrue(items.contains(r3));
    }
  }

  @Test public void testQueryOverlapping()
  {
    final QuadTreeType<Rectangle> q = this.makeQuad128();
    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(8, 8), new VectorI2I(48, 48));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(8, 80), new VectorI2I(48, 120));
    final Rectangle r2 =
      new Rectangle(2, new VectorI2I(80, 8), new VectorI2I(120, 48));
    final Rectangle r3 =
      new Rectangle(3, new VectorI2I(80, 80), new VectorI2I(120, 120));

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
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(0, new VectorI2I(
        8 - 2,
        8 - 2), new VectorI2I(8 + 2, 8 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r0));
    }

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(0, new VectorI2I(
        8 - 2,
        80 - 2), new VectorI2I(8 + 2, 80 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r1));
    }

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(0, new VectorI2I(
        80 - 2,
        8 - 2), new VectorI2I(80 + 2, 8 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r2));
    }

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(0, new VectorI2I(
        80 - 2,
        80 - 2), new VectorI2I(80 + 2, 80 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r3));
    }
  }

  @Test public void testQueryOverlappingExact()
  {
    final QuadTreeType<Rectangle> q = this.makeQuad128();
    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(8, 8), new VectorI2I(48, 48));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(8, 80), new VectorI2I(48, 120));
    final Rectangle r2 =
      new Rectangle(2, new VectorI2I(80, 8), new VectorI2I(120, 48));
    final Rectangle r3 =
      new Rectangle(3, new VectorI2I(80, 80), new VectorI2I(120, 120));

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
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(0, new VectorI2I(
        8 + 2,
        8 + 2), new VectorI2I(120 - 2, 120 - 2)), items);
      Assert.assertEquals(4, items.size());
      Assert.assertTrue(items.contains(r0));
      Assert.assertTrue(items.contains(r1));
      Assert.assertTrue(items.contains(r2));
      Assert.assertTrue(items.contains(r3));
    }
  }

  @Test public void testQueryOverlappingNotAll()
  {
    final QuadTreeType<Rectangle> q = this.makeQuad128();
    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(127, 127));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(4, 4), new VectorI2I(127, 127));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(
        0,
        new VectorI2I(0, 0),
        new VectorI2I(2, 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r0));
    }

    {
      final SortedSet<Rectangle> items = new TreeSet<Rectangle>();
      q.quadTreeQueryAreaOverlapping(new Rectangle(0, new VectorI2I(
        127 - 4,
        127 - 4), new VectorI2I(127, 127)), items);
      Assert.assertEquals(2, items.size());
      Assert.assertTrue(items.contains(r0));
      Assert.assertTrue(items.contains(r1));
    }
  }

  @Test public void testRaycast()
  {
    final QuadTreeType<Rectangle> q = this.makeQuad512();

    q.quadTreeInsert(new Rectangle(0, new VectorI2I(32, 32), new VectorI2I(
      80,
      80)));
    q.quadTreeInsert(new Rectangle(1, new VectorI2I(400, 32), new VectorI2I(
      400 + 32,
      80)));
    q.quadTreeInsert(new Rectangle(2, new VectorI2I(400, 400), new VectorI2I(
      480,
      480)));

    final RayI2D ray =
      new RayI2D(VectorI2D.ZERO, VectorI2D.normalize(new VectorI2D(511, 511)));
    final SortedSet<QuadTreeRaycastResult<Rectangle>> items =
      new TreeSet<QuadTreeRaycastResult<Rectangle>>();
    q.quadTreeQueryRaycast(ray, items);

    Assert.assertEquals(2, items.size());

    final Iterator<QuadTreeRaycastResult<Rectangle>> iter = items.iterator();

    {
      final QuadTreeRaycastResult<Rectangle> rr = iter.next();
      final Rectangle r = rr.getObject();
      Assert.assertEquals(32, r.boundingAreaLower().getXI());
      Assert.assertEquals(32, r.boundingAreaLower().getYI());
    }

    {
      final QuadTreeRaycastResult<Rectangle> rr = iter.next();
      final Rectangle r = rr.getObject();
      Assert.assertEquals(400, r.boundingAreaLower().getXI());
      Assert.assertEquals(400, r.boundingAreaLower().getYI());
    }

    Assert.assertFalse(iter.hasNext());
  }

  @Test public void testRemove()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad16();
    final Rectangle r =
      new Rectangle(0, new VectorI2I(0, 0), new VectorI2I(12, 12));

    boolean in = false;
    in = q.quadTreeInsert(r);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r);
    Assert.assertFalse(in);

    boolean removed = false;
    removed = q.quadTreeRemove(r);
    Assert.assertTrue(removed);
    removed = q.quadTreeRemove(r);
    Assert.assertFalse(removed);
  }

  @Test public void testRemoveSub()
    throws Exception
  {
    final QuadTreeType<Rectangle> q = this.makeQuad32();

    final Rectangle r0 =
      new Rectangle(0, new VectorI2I(2, 2), new VectorI2I(4, 4));
    final Rectangle r1 =
      new Rectangle(1, new VectorI2I(18, 2), new VectorI2I(20, 4));
    final Rectangle r2 =
      new Rectangle(2, new VectorI2I(2, 18), new VectorI2I(4, 20));
    final Rectangle r3 =
      new Rectangle(3, new VectorI2I(18, 18), new VectorI2I(20, 20));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r2);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r3);
    Assert.assertTrue(in);

    boolean removed = false;
    removed = q.quadTreeRemove(r0);
    Assert.assertTrue(removed);
    removed = q.quadTreeRemove(r1);
    Assert.assertTrue(removed);
    removed = q.quadTreeRemove(r2);
    Assert.assertTrue(removed);
    removed = q.quadTreeRemove(r3);
    Assert.assertTrue(removed);
  }

  @Test public void testToString()
  {
    final QuadTreeType<Rectangle> q = this.makeQuad128();
    System.err.println(q.toString());
  }
}
