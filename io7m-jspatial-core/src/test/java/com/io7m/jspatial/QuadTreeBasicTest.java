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

public final class QuadTreeBasicTest extends QuadTreeCommonTests
{
  @Override <T extends QuadTreeMember<T>> QuadTreeInterface<T> makeQuad128()
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(128);
    config.setSizeY(128);

    try {
      return new QuadTreeBasic<T>(config);
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
      return new QuadTreeBasic<T>(config);
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
      return new QuadTreeBasic<T>(config);
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
      return new QuadTreeBasic<T>(config);
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
      return new QuadTreeBasic<T>(config);
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

    new QuadTreeBasic<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddY()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(3);

    new QuadTreeBasic<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallX()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(1);
    config.setSizeY(2);

    new QuadTreeBasic<Rectangle>(config);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallY()
    throws ConstraintError
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(1);

    new QuadTreeBasic<Rectangle>(config);
  }

  @SuppressWarnings("static-method") @Test public void testInsertSplitNotX()
    throws ConstraintError,
      Exception
  {
    final QuadTreeConfig config = new QuadTreeConfig();
    config.setSizeX(2);
    config.setSizeY(4);
    final QuadTreeInterface<Rectangle> q =
      new QuadTreeBasic<Rectangle>(config);

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
      new QuadTreeBasic<Rectangle>(config);

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
    final QuadTreeBasic.Quadrants q =
      new QuadTreeBasic.Quadrants(lower, upper);

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
    final QuadTreeBasic<Rectangle> q = new QuadTreeBasic<Rectangle>(config);

    q.quadTreeInsert(new Rectangle(0, new VectorI2I(32, 32), new VectorI2I(
      80,
      80)));
    q.quadTreeInsert(new Rectangle(1, new VectorI2I(400, 400), new VectorI2I(
      480,
      480)));

    final RayI2D ray =
      new RayI2D(VectorI2D.ZERO, VectorI2D.normalize(new VectorI2D(511, 511)));
    final SortedSet<QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant>> items =
      new TreeSet<QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(6, items.size());

    final Iterator<QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant>> iter =
      items.iterator();

    {
      final QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeBasic<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(0, quad.boundingAreaLower().getXI());
      Assert.assertEquals(0, quad.boundingAreaLower().getYI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(63, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeBasic<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(64, quad.boundingAreaLower().getXI());
      Assert.assertEquals(64, quad.boundingAreaLower().getYI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(127, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeBasic<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(128, quad.boundingAreaLower().getXI());
      Assert.assertEquals(128, quad.boundingAreaLower().getYI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(255, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeBasic<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(256, quad.boundingAreaLower().getXI());
      Assert.assertEquals(256, quad.boundingAreaLower().getYI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(383, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeBasic<Rectangle>.Quadrant quad = rq.getObject();
      Assert.assertEquals(384, quad.boundingAreaLower().getXI());
      Assert.assertEquals(384, quad.boundingAreaLower().getYI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getXI());
      Assert.assertEquals(447, quad.boundingAreaUpper().getYI());
    }

    {
      final QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant> rq =
        iter.next();
      final QuadTreeBasic<Rectangle>.Quadrant quad = rq.getObject();
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
    final QuadTreeBasic<Rectangle> q = new QuadTreeBasic<Rectangle>(config);

    final RayI2D ray =
      new RayI2D(new VectorI2D(512, 512), VectorI2D.normalize(new VectorI2D(
        -0.5,
        -0.5)));
    final SortedSet<QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant>> items =
      new TreeSet<QuadTreeRaycastResult<QuadTreeBasic<Rectangle>.Quadrant>>();
    q.quadTreeQueryRaycastQuadrants(ray, items);

    Assert.assertEquals(1, items.size());
  }
}
