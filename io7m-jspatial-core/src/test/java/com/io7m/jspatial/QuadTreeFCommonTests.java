package com.io7m.jspatial;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.AlmostEqualFloat.ContextRelative;
import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.functional.Function;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.VectorReadable2F;

public abstract class QuadTreeFCommonTests
{
  protected static final class Counter implements QuadTreeTraversalF
  {
    int count = 0;

    Counter()
    {

    }

    @Override public void visitF(
      final int depth,
      final VectorReadable2F lower,
      final VectorReadable2F upper)
      throws Exception
    {
      ++this.count;
    }
  }

  protected static abstract class IterationChecker0 implements
    Function<RectangleF, Boolean>
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
    Function<RectangleF, Boolean>
  {
    int count = 0;

    IterationChecker1()
    {

    }
  }

  protected static final class IterationCounter implements
    Function<RectangleF, Boolean>
  {
    int count = 0;

    IterationCounter()
    {

    }

    @Override public Boolean call(
      final RectangleF x)
    {
      ++this.count;
      return Boolean.TRUE;
    }
  }

  abstract <T extends QuadTreeMemberF<T>> QuadTreeInterfaceF<T> makeQuad128();

  abstract <T extends QuadTreeMemberF<T>> QuadTreeInterfaceF<T> makeQuad16();

  abstract <T extends QuadTreeMemberF<T>> QuadTreeInterfaceF<T> makeQuad2();

  abstract <T extends QuadTreeMemberF<T>> QuadTreeInterfaceF<T> makeQuad32();

  abstract <T extends QuadTreeMemberF<T>> QuadTreeInterfaceF<T> makeQuad512();

  @Test public void testClear()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad128();
    final RectangleF r0 =
      new RectangleF(0, new VectorI2F(8, 8), new VectorI2F(48, 48));
    final RectangleF r1 =
      new RectangleF(1, new VectorI2F(8, 80), new VectorI2F(48, 120));
    final RectangleF r2 =
      new RectangleF(2, new VectorI2F(80, 8), new VectorI2F(120, 48));
    final RectangleF r3 =
      new RectangleF(3, new VectorI2F(80, 80), new VectorI2F(120, 120));

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
    throws Exception,
      ConstraintError
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad128();
    final Counter c = new Counter();

    q.quadTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public void testCreate()
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad128();
    Assert.assertEquals(128, q.quadTreeGetSizeX());
    Assert.assertEquals(128, q.quadTreeGetSizeY());
  }

  @Test public void testInsertAtImmediateRootChildren()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad16();
    final Counter c = new Counter();
    final RectangleF r0 =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(7, 7));
    final RectangleF r1 =
      new RectangleF(1, new VectorI2F(8, 0), new VectorI2F(15, 7));
    final RectangleF r2 =
      new RectangleF(2, new VectorI2F(0, 8), new VectorI2F(7, 15));
    final RectangleF r3 =
      new RectangleF(3, new VectorI2F(8, 8), new VectorI2F(15, 15));

    final ContextRelative context = new AlmostEqualFloat.ContextRelative();

    {
      final VectorReadable2F bal = r0.boundingAreaLowerF();
      final VectorReadable2F bau = r0.boundingAreaUpperF();

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        8,
        Dimensions.getSpanSizeXF(bal, bau)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        8,
        Dimensions.getSpanSizeYF(bal, bau)));
    }

    {
      final VectorReadable2F bal = r1.boundingAreaLowerF();
      final VectorReadable2F bau = r1.boundingAreaUpperF();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        8,
        Dimensions.getSpanSizeXF(bal, bau)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        8,
        Dimensions.getSpanSizeYF(bal, bau)));
    }

    {
      final VectorReadable2F bal = r2.boundingAreaLowerF();
      final VectorReadable2F bau = r2.boundingAreaUpperF();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        8,
        Dimensions.getSpanSizeXF(bal, bau)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        8,
        Dimensions.getSpanSizeYF(bal, bau)));
    }

    {
      final VectorReadable2F bal = r3.boundingAreaLowerF();
      final VectorReadable2F bau = r3.boundingAreaUpperF();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        8,
        Dimensions.getSpanSizeXF(bal, bau)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        8,
        Dimensions.getSpanSizeYF(bal, bau)));
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
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad16();
    final Counter c = new Counter();
    final RectangleF r =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(12, 12));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    q.quadTreeTraverse(c);
    Assert.assertEquals(5, c.count);
  }

  @Test public void testInsertDuplicate()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad16();
    final RectangleF r =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(12, 12));

    boolean in = false;
    in = q.quadTreeInsert(r);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r);
    Assert.assertFalse(in);
  }

  @Test(expected = ConstraintError.class) public void testInsertIllFormed()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad16();
    final RectangleF r =
      new RectangleF(0, new VectorI2F(12, 12), new VectorI2F(0, 0));

    q.quadTreeInsert(r);
  }

  @Test public void testInsertOutside()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad16();
    final RectangleF r =
      new RectangleF(0, new VectorI2F(18, 18), new VectorI2F(28, 28));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertFalse(in);
  }

  @Test public void testInsertTiny()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad16();
    final RectangleF r =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    {
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaContaining(new RectangleF(
        0,
        new VectorI2F(0, 0),
        new VectorI2F(15, 15)), items);
      Assert.assertEquals(1, items.size());
    }
  }

  @Test public void testIterate()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad16();
    final RectangleF r0 =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(7, 7));
    final RectangleF r1 =
      new RectangleF(1, new VectorI2F(8, 0), new VectorI2F(15, 7));
    final RectangleF r2 =
      new RectangleF(2, new VectorI2F(0, 8), new VectorI2F(7, 15));
    final RectangleF r3 =
      new RectangleF(3, new VectorI2F(8, 8), new VectorI2F(15, 15));

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
        final RectangleF x)
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

  @Test(expected = ConstraintError.class) public void testIterateNull()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad16();
    q.quadTreeIterateObjects(null);
  }

  @Test public void testIterateStopEarly()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad16();
    final RectangleF r0 =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(7, 7));
    final RectangleF r1 =
      new RectangleF(1, new VectorI2F(8, 0), new VectorI2F(15, 7));
    final RectangleF r2 =
      new RectangleF(2, new VectorI2F(0, 8), new VectorI2F(7, 15));
    final RectangleF r3 =
      new RectangleF(3, new VectorI2F(8, 8), new VectorI2F(15, 15));

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
        final RectangleF x)
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
    throws ConstraintError
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad128();
    final RectangleF r0 =
      new RectangleF(0, new VectorI2F(8, 8), new VectorI2F(48, 48));
    final RectangleF r1 =
      new RectangleF(1, new VectorI2F(8, 80), new VectorI2F(48, 120));
    final RectangleF r2 =
      new RectangleF(2, new VectorI2F(80, 8), new VectorI2F(120, 48));
    final RectangleF r3 =
      new RectangleF(3, new VectorI2F(80, 80), new VectorI2F(120, 120));

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
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaContaining(new RectangleF(0, new VectorI2F(
        8 - 4,
        8 - 4), new VectorI2F(48 + 4, 48 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r0));
    }

    {
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaContaining(new RectangleF(0, new VectorI2F(
        8 - 4,
        80 - 4), new VectorI2F(48 + 2, 120 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r1));
    }

    {
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaContaining(new RectangleF(0, new VectorI2F(
        80 - 4,
        8 - 4), new VectorI2F(120 + 4, 48 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r2));
    }

    {
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaContaining(new RectangleF(0, new VectorI2F(
        80 - 4,
        80 - 4), new VectorI2F(120 + 4, 120 + 4)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r3));
    }
  }

  @Test public void testQueryContainingExact()
    throws ConstraintError
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad128();
    final RectangleF r0 =
      new RectangleF(0, new VectorI2F(8, 8), new VectorI2F(48, 48));
    final RectangleF r1 =
      new RectangleF(1, new VectorI2F(8, 80), new VectorI2F(48, 120));
    final RectangleF r2 =
      new RectangleF(2, new VectorI2F(80, 8), new VectorI2F(120, 48));
    final RectangleF r3 =
      new RectangleF(3, new VectorI2F(80, 80), new VectorI2F(120, 120));

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
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaContaining(new RectangleF(
        0,
        new VectorI2F(0, 0),
        new VectorI2F(127, 127)), items);
      Assert.assertEquals(4, items.size());
      Assert.assertTrue(items.contains(r0));
      Assert.assertTrue(items.contains(r1));
      Assert.assertTrue(items.contains(r2));
      Assert.assertTrue(items.contains(r3));
    }
  }

  @Test public void testQueryOverlapping()
    throws ConstraintError
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad128();
    final RectangleF r0 =
      new RectangleF(0, new VectorI2F(8, 8), new VectorI2F(48, 48));
    final RectangleF r1 =
      new RectangleF(1, new VectorI2F(8, 80), new VectorI2F(48, 120));
    final RectangleF r2 =
      new RectangleF(2, new VectorI2F(80, 8), new VectorI2F(120, 48));
    final RectangleF r3 =
      new RectangleF(3, new VectorI2F(80, 80), new VectorI2F(120, 120));

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
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaOverlapping(new RectangleF(0, new VectorI2F(
        8 - 2,
        8 - 2), new VectorI2F(8 + 2, 8 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r0));
    }

    {
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaOverlapping(new RectangleF(0, new VectorI2F(
        8 - 2,
        80 - 2), new VectorI2F(8 + 2, 80 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r1));
    }

    {
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaOverlapping(new RectangleF(0, new VectorI2F(
        80 - 2,
        8 - 2), new VectorI2F(80 + 2, 8 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r2));
    }

    {
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaOverlapping(new RectangleF(0, new VectorI2F(
        80 - 2,
        80 - 2), new VectorI2F(80 + 2, 80 + 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r3));
    }
  }

  @Test public void testQueryOverlappingExact()
    throws ConstraintError
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad128();
    final RectangleF r0 =
      new RectangleF(0, new VectorI2F(8, 8), new VectorI2F(48, 48));
    final RectangleF r1 =
      new RectangleF(1, new VectorI2F(8, 80), new VectorI2F(48, 120));
    final RectangleF r2 =
      new RectangleF(2, new VectorI2F(80, 8), new VectorI2F(120, 48));
    final RectangleF r3 =
      new RectangleF(3, new VectorI2F(80, 80), new VectorI2F(120, 120));

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
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaOverlapping(new RectangleF(0, new VectorI2F(
        8 + 2,
        8 + 2), new VectorI2F(120 - 2, 120 - 2)), items);
      Assert.assertEquals(4, items.size());
      Assert.assertTrue(items.contains(r0));
      Assert.assertTrue(items.contains(r1));
      Assert.assertTrue(items.contains(r2));
      Assert.assertTrue(items.contains(r3));
    }
  }

  @Test public void testQueryOverlappingNotAll()
    throws ConstraintError
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad128();
    final RectangleF r0 =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(127, 127));
    final RectangleF r1 =
      new RectangleF(1, new VectorI2F(4, 4), new VectorI2F(127, 127));

    boolean in = false;
    in = q.quadTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.quadTreeInsert(r1);
    Assert.assertTrue(in);

    {
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaOverlapping(new RectangleF(
        0,
        new VectorI2F(0, 0),
        new VectorI2F(2, 2)), items);
      Assert.assertEquals(1, items.size());
      Assert.assertTrue(items.contains(r0));
    }

    {
      final SortedSet<RectangleF> items = new TreeSet<RectangleF>();
      q.quadTreeQueryAreaOverlapping(new RectangleF(0, new VectorI2F(
        127 - 4,
        127 - 4), new VectorI2F(127, 127)), items);
      Assert.assertEquals(2, items.size());
      Assert.assertTrue(items.contains(r0));
      Assert.assertTrue(items.contains(r1));
    }
  }

  @Test public void testRaycast()
    throws ConstraintError
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad512();

    q.quadTreeInsert(new RectangleF(0, new VectorI2F(32, 32), new VectorI2F(
      80,
      80)));
    q.quadTreeInsert(new RectangleF(1, new VectorI2F(400, 32), new VectorI2F(
      400 + 32,
      80)));
    q.quadTreeInsert(new RectangleF(
      2,
      new VectorI2F(400, 400),
      new VectorI2F(480, 480)));

    final RayI2D ray =
      new RayI2D(VectorI2D.ZERO, VectorI2D.normalize(new VectorI2D(511, 511)));
    final SortedSet<QuadTreeRaycastResultF<RectangleF>> items =
      new TreeSet<QuadTreeRaycastResultF<RectangleF>>();
    q.quadTreeQueryRaycast(ray, items);

    Assert.assertEquals(2, items.size());

    final ContextRelative context = new AlmostEqualFloat.ContextRelative();

    final Iterator<QuadTreeRaycastResultF<RectangleF>> iter =
      items.iterator();

    {
      final QuadTreeRaycastResultF<RectangleF> rr = iter.next();
      final RectangleF r = rr.getObject();

      Assert.assertTrue(AlmostEqualFloat.almostEqual(context, 32, r
        .boundingAreaLowerF()
        .getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(context, 32, r
        .boundingAreaLowerF()
        .getYF()));
    }

    {
      final QuadTreeRaycastResultF<RectangleF> rr = iter.next();
      final RectangleF r = rr.getObject();

      Assert.assertTrue(AlmostEqualFloat.almostEqual(context, 400, r
        .boundingAreaLowerF()
        .getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(context, 400, r
        .boundingAreaLowerF()
        .getYF()));
    }

    Assert.assertFalse(iter.hasNext());
  }

  @Test public void testRemove()
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad16();
    final RectangleF r =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(12, 12));

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
    throws ConstraintError,
      Exception
  {
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad32();

    final RectangleF r0 =
      new RectangleF(0, new VectorI2F(2, 2), new VectorI2F(4, 4));
    final RectangleF r1 =
      new RectangleF(1, new VectorI2F(18, 2), new VectorI2F(20, 4));
    final RectangleF r2 =
      new RectangleF(2, new VectorI2F(2, 18), new VectorI2F(4, 20));
    final RectangleF r3 =
      new RectangleF(3, new VectorI2F(18, 18), new VectorI2F(20, 20));

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
    final QuadTreeInterfaceF<RectangleF> q = this.makeQuad128();
    System.err.println(q.toString());
  }
}
