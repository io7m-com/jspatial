package com.io7m.jspatial;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.UnreachableCodeException;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2F;

public final class QuadTreeBasicFTest extends QuadTreeFCommonTests
{
  @Override
    <T extends QuadTreeMemberF<T>>
    QuadTreeInterfaceF<T>
    makeQuad128()
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(128);
    config.setSizeY(128);

    try {
      return new QuadTreeBasicF<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberF<T>> QuadTreeInterfaceF<T> makeQuad16()
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(16);
    config.setSizeY(16);

    try {
      return new QuadTreeBasicF<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberF<T>> QuadTreeInterfaceF<T> makeQuad2()
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(2);
    config.setSizeY(2);

    try {
      return new QuadTreeBasicF<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends QuadTreeMemberF<T>> QuadTreeInterfaceF<T> makeQuad32()
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(32);
    config.setSizeY(32);

    try {
      return new QuadTreeBasicF<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override
    <T extends QuadTreeMemberF<T>>
    QuadTreeInterfaceF<T>
    makeQuad512()
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(512);
    config.setSizeY(512);

    try {
      return new QuadTreeBasicF<T>(config);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddX()
    throws ConstraintError
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(3);
    config.setSizeY(2);

    new QuadTreeBasicF<RectangleF>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddY()
    throws ConstraintError
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(2);
    config.setSizeY(3);

    new QuadTreeBasicF<RectangleF>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallX()
    throws ConstraintError
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(1);
    config.setSizeY(2);

    new QuadTreeBasicF<RectangleF>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallY()
    throws ConstraintError
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(2);
    config.setSizeY(1);

    new QuadTreeBasicF<RectangleF>(config);
  }

  @SuppressWarnings("static-method") @Test public void testInsertSplitNotX()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(2);
    config.setSizeY(4);
    final QuadTreeInterfaceF<RectangleF> q =
      new QuadTreeBasicF<RectangleF>(config);

    final RectangleF r =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(1, 1));

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
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(4);
    config.setSizeY(2);
    final QuadTreeInterfaceF<RectangleF> q =
      new QuadTreeBasicF<RectangleF>(config);

    final RectangleF r =
      new RectangleF(0, new VectorI2F(0, 0), new VectorI2F(0, 0));

    final boolean in = q.quadTreeInsert(r);
    Assert.assertTrue(in);

    final Counter counter = new Counter();
    q.quadTreeTraverse(counter);
    Assert.assertEquals(5, counter.count);
  }

  @SuppressWarnings("static-method") @Test public void testQuadrantsSimple()
  {
    final VectorI2F lower = new VectorI2F(8, 8);
    final VectorI2F upper = new VectorI2F(15, 15);
    final QuadTreeBasicF.Quadrants q =
      new QuadTreeBasicF.Quadrants(lower, upper);

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
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(512);
    config.setSizeY(512);
    final QuadTreeBasicF<RectangleF> q =
      new QuadTreeBasicF<RectangleF>(config);

    q.quadTreeInsert(new RectangleF(0, new VectorI2F(32, 32), new VectorI2F(
      80,
      80)));
    q.quadTreeInsert(new RectangleF(
      1,
      new VectorI2F(400, 400),
      new VectorI2F(480, 480)));

    final RayI2D ray =
      new RayI2D(VectorI2D.ZERO, VectorI2D.normalize(new VectorI2D(511, 511)));
    final SortedSet<QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant>> items =
      new TreeSet<QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(6, items.size());

    final Iterator<QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant>> iter =
      items.iterator();

    {
      final QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant> rq =
        iter.next();
      final QuadTreeBasicF<RectangleF>.Quadrant quad = rq.getObject();
      Assert.assertEquals(0, quad.boundingAreaLowerF().getXF());
      Assert.assertEquals(0, quad.boundingAreaLowerF().getYF());
      Assert.assertEquals(63, quad.boundingAreaUpperF().getXF());
      Assert.assertEquals(63, quad.boundingAreaUpperF().getYF());
    }

    {
      final QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant> rq =
        iter.next();
      final QuadTreeBasicF<RectangleF>.Quadrant quad = rq.getObject();
      Assert.assertEquals(64, quad.boundingAreaLowerF().getXF());
      Assert.assertEquals(64, quad.boundingAreaLowerF().getYF());
      Assert.assertEquals(127, quad.boundingAreaUpperF().getXF());
      Assert.assertEquals(127, quad.boundingAreaUpperF().getYF());
    }

    {
      final QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant> rq =
        iter.next();
      final QuadTreeBasicF<RectangleF>.Quadrant quad = rq.getObject();
      Assert.assertEquals(128, quad.boundingAreaLowerF().getXF());
      Assert.assertEquals(128, quad.boundingAreaLowerF().getYF());
      Assert.assertEquals(255, quad.boundingAreaUpperF().getXF());
      Assert.assertEquals(255, quad.boundingAreaUpperF().getYF());
    }

    {
      final QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant> rq =
        iter.next();
      final QuadTreeBasicF<RectangleF>.Quadrant quad = rq.getObject();
      Assert.assertEquals(256, quad.boundingAreaLowerF().getXF());
      Assert.assertEquals(256, quad.boundingAreaLowerF().getYF());
      Assert.assertEquals(383, quad.boundingAreaUpperF().getXF());
      Assert.assertEquals(383, quad.boundingAreaUpperF().getYF());
    }

    {
      final QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant> rq =
        iter.next();
      final QuadTreeBasicF<RectangleF>.Quadrant quad = rq.getObject();
      Assert.assertEquals(384, quad.boundingAreaLowerF().getXF());
      Assert.assertEquals(384, quad.boundingAreaLowerF().getYF());
      Assert.assertEquals(447, quad.boundingAreaUpperF().getXF());
      Assert.assertEquals(447, quad.boundingAreaUpperF().getYF());
    }

    {
      final QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant> rq =
        iter.next();
      final QuadTreeBasicF<RectangleF>.Quadrant quad = rq.getObject();
      Assert.assertEquals(448, quad.boundingAreaLowerF().getXF());
      Assert.assertEquals(448, quad.boundingAreaLowerF().getYF());
      Assert.assertEquals(511, quad.boundingAreaUpperF().getXF());
      Assert.assertEquals(511, quad.boundingAreaUpperF().getYF());
    }

    Assert.assertFalse(iter.hasNext());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRaycastQuadrantsNegativeRay()
      throws ConstraintError
  {
    final QuadTreeConfigF config = new QuadTreeConfigF();
    config.setSizeX(512);
    config.setSizeY(512);
    final QuadTreeBasicF<RectangleF> q =
      new QuadTreeBasicF<RectangleF>(config);

    final RayI2D ray =
      new RayI2D(new VectorI2D(512, 512), VectorI2D.normalize(new VectorI2D(
        -0.5,
        -0.5)));
    final SortedSet<QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant>> items =
      new TreeSet<QuadTreeRaycastResultF<QuadTreeBasicF<RectangleF>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(1, items.size());
  }
}
