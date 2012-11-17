package com.io7m.jspatial;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.functional.Function;
import com.io7m.jspatial.OctTreeBasic.Octants;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorReadable3I;

public class OctTreeBasicTest
{
  private static class Counter implements OctTreeTraversal
  {
    int count = 0;

    Counter()
    {

    }

    @SuppressWarnings("unused") @Override public void visit(
      final int depth,
      final @Nonnull VectorReadable3I lower,
      final @Nonnull VectorReadable3I upper)
      throws Exception
    {
      ++this.count;
    }
  }

  private static abstract class IterationChecker0 implements
    Function<Cuboid, Boolean>
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
    Function<Cuboid, Boolean>
  {
    int count = 0;

    IterationChecker1()
    {

    }
  }

  private static class IterationCounter implements Function<Cuboid, Boolean>
  {
    int count = 0;

    IterationCounter()
    {

    }

    @SuppressWarnings("unused") @Override public Boolean call(
      final Cuboid x)
    {
      ++this.count;
      return Boolean.TRUE;
    }
  }

  @SuppressWarnings("static-method") @Test public void testClear()
    throws ConstraintError,
      Exception
  {
    final OctTreeBasic<Cuboid> q = new OctTreeBasic<Cuboid>(128, 128, 128);
    final Cuboid r0 =
      new Cuboid(0, new VectorI3I(8, 8, 8), new VectorI3I(48, 48, 48));
    final Cuboid r1 =
      new Cuboid(1, new VectorI3I(8, 80, 8), new VectorI3I(48, 120, 48));
    final Cuboid r2 =
      new Cuboid(2, new VectorI3I(80, 8, 8), new VectorI3I(120, 48, 48));
    final Cuboid r3 =
      new Cuboid(3, new VectorI3I(80, 80, 8), new VectorI3I(120, 120, 48));

    boolean in = false;
    in = q.octTreeInsert(r0);
    Assert.assertTrue(in);
    in = q.octTreeInsert(r1);
    Assert.assertTrue(in);
    in = q.octTreeInsert(r2);
    Assert.assertTrue(in);
    in = q.octTreeInsert(r3);
    Assert.assertTrue(in);

    {
      final IterationCounter counter = new IterationCounter();
      q.octTreeIterateObjects(counter);
      Assert.assertEquals(4, counter.count);
    }

    {
      final IterationCounter counter = new IterationCounter();
      q.octTreeClear();
      q.octTreeIterateObjects(counter);
      Assert.assertEquals(0, counter.count);
    }
  }

  @SuppressWarnings("static-method") @Test public void testCountInitial()
    throws Exception,
      ConstraintError
  {
    final OctTreeBasic<Cuboid> qt = new OctTreeBasic<Cuboid>(8, 8, 8);
    final Counter c = new Counter();

    qt.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @SuppressWarnings({ "static-method" }) @Test public void testCreate()
    throws ConstraintError
  {
    final OctTreeBasic<Cuboid> q = new OctTreeBasic<Cuboid>(2, 2, 2);
    Assert.assertEquals(2, q.octTreeGetSizeX());
    Assert.assertEquals(2, q.octTreeGetSizeY());
    Assert.assertEquals(2, q.octTreeGetSizeZ());
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddX()
    throws ConstraintError
  {
    new OctTreeBasic<Cuboid>(3, 2, 2);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddY()
    throws ConstraintError
  {
    new OctTreeBasic<Cuboid>(2, 3, 2);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddZ()
    throws ConstraintError
  {
    new OctTreeBasic<Cuboid>(2, 2, 3);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallX()
    throws ConstraintError
  {
    new OctTreeBasic<Cuboid>(1, 2, 2);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallY()
    throws ConstraintError
  {
    new OctTreeBasic<Cuboid>(2, 1, 2);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallZ()
    throws ConstraintError
  {
    new OctTreeBasic<Cuboid>(2, 2, 1);
  }

  @SuppressWarnings("static-method") @Test public void testInsertAtRoot()
    throws ConstraintError,
      Exception
  {
    final OctTreeBasic<Cuboid> q = new OctTreeBasic<Cuboid>(16, 16, 16);
    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(12, 12, 12));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInsertLeafNoSplit()
      throws ConstraintError,
        Exception
  {
    final OctTreeBasic<Cuboid> q = new OctTreeBasic<Cuboid>(2, 2, 2);
    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @SuppressWarnings("static-method") @Test public void testInsertImmediate()
    throws ConstraintError,
      Exception
  {
    final int size = 10;

    final OctTreeBasic<Cuboid> q = new OctTreeBasic<Cuboid>(32, 32, 32);
    final Counter counter = new Counter();

    final Cuboid cubes[] = new Cuboid[8];

    int x_root = 2;
    int y_root = 2;
    int z_root = 2;
    cubes[0] =
      new Cuboid(0, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 18;
    y_root = 2;
    z_root = 2;
    cubes[1] =
      new Cuboid(1, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 2;
    y_root = 18;
    z_root = 2;
    cubes[2] =
      new Cuboid(2, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 18;
    y_root = 18;
    z_root = 2;
    cubes[3] =
      new Cuboid(3, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    //
    // Upper Z
    //

    x_root = 2;
    y_root = 2;
    z_root = 18;
    cubes[4] =
      new Cuboid(4, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 18;
    y_root = 2;
    z_root = 18;
    cubes[5] =
      new Cuboid(5, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 2;
    y_root = 18;
    z_root = 18;
    cubes[6] =
      new Cuboid(6, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 18;
    y_root = 18;
    z_root = 18;
    cubes[7] =
      new Cuboid(7, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    for (final Cuboid c : cubes) {
      final boolean in = q.octTreeInsert(c);
      Assert.assertTrue(in);
    }

    q.octTreeTraverse(counter);
    Assert.assertEquals(73, counter.count);
  }

  @SuppressWarnings("static-method") @Test public void testInsertDuplicate()
    throws ConstraintError,
      Exception
  {
    final OctTreeBasic<Cuboid> q = new OctTreeBasic<Cuboid>(16, 16, 16);
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(12, 12, 12));

    boolean in = false;
    in = q.octTreeInsert(r);
    Assert.assertTrue(in);
    in = q.octTreeInsert(r);
    Assert.assertFalse(in);
  }

  @SuppressWarnings("static-method") @Test(expected = ConstraintError.class) public
    void
    testInsertIllFormed()
      throws ConstraintError,
        Exception
  {
    OctTreeBasic<Cuboid> q = null;
    Cuboid r = null;

    try {
      q = new OctTreeBasic<Cuboid>(16, 16, 16);
      r = new Cuboid(0, new VectorI3I(12, 12, 12), new VectorI3I(0, 0, 0));
    } catch (final ConstraintError e) {
      Assert.fail(e.toString());
    }

    assert q != null;
    q.octTreeInsert(r);
  }

  @SuppressWarnings("static-method") @Test public void testInsertOutside()
    throws ConstraintError,
      Exception
  {
    final OctTreeBasic<Cuboid> q = new OctTreeBasic<Cuboid>(16, 16, 16);
    final Cuboid r =
      new Cuboid(0, new VectorI3I(18, 18, 18), new VectorI3I(28, 28, 28));

    final boolean in = q.octTreeInsert(r);
    Assert.assertFalse(in);
  }

  @SuppressWarnings("static-method") @Test(expected = ConstraintError.class) public
    void
    testIterateNull()
      throws ConstraintError,
        Exception
  {
    OctTreeBasic<Cuboid> q = null;

    try {
      q = new OctTreeBasic<Cuboid>(16, 16, 16);
    } catch (final ConstraintError e) {
      Assert.fail(e.toString());
    }

    assert q != null;
    q.octTreeIterateObjects(null);
  }

  @SuppressWarnings("static-method") @Test public void testOctantsSimple()
  {
    final VectorI3I lower = new VectorI3I(8, 8, 8);
    final VectorI3I upper = new VectorI3I(15, 15, 15);
    final Octants q = new Octants(lower, upper);

    Assert.assertEquals(8, q.x0y0z0_lower.x);
    Assert.assertEquals(8, q.x0y0z0_lower.y);
    Assert.assertEquals(11, q.x0y0z0_upper.x);
    Assert.assertEquals(11, q.x0y0z0_upper.y);

    Assert.assertEquals(12, q.x1y0z0_lower.x);
    Assert.assertEquals(8, q.x1y0z0_lower.y);
    Assert.assertEquals(15, q.x1y0z0_upper.x);
    Assert.assertEquals(11, q.x1y0z0_upper.y);

    Assert.assertEquals(8, q.x0y1z0_lower.x);
    Assert.assertEquals(12, q.x0y1z0_lower.y);
    Assert.assertEquals(11, q.x0y1z0_upper.x);
    Assert.assertEquals(15, q.x0y1z0_upper.y);

    Assert.assertEquals(12, q.x1y1z0_lower.x);
    Assert.assertEquals(12, q.x1y1z0_lower.y);
    Assert.assertEquals(15, q.x1y1z0_upper.x);
    Assert.assertEquals(15, q.x1y1z0_upper.y);
  }

  @SuppressWarnings("static-method") @Test public void testRemove()
    throws ConstraintError,
      Exception
  {
    final OctTreeBasic<Cuboid> q = new OctTreeBasic<Cuboid>(16, 16, 16);
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(12, 12, 12));

    boolean in = false;
    in = q.octTreeInsert(r);
    Assert.assertTrue(in);
    in = q.octTreeInsert(r);
    Assert.assertFalse(in);

    boolean removed = false;
    removed = q.octTreeRemove(r);
    Assert.assertTrue(removed);
    removed = q.octTreeRemove(r);
    Assert.assertFalse(removed);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testRemoveInsertInvariants()
      throws ConstraintError,
        Exception
  {
    final OctTreeBasic<Cuboid> q = new OctTreeBasic<Cuboid>(32, 32, 32);
    final int size = 10;
    final Cuboid cubes[] = new Cuboid[8];

    int x_root = 2;
    int y_root = 2;
    int z_root = 2;
    cubes[0] =
      new Cuboid(0, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 18;
    y_root = 2;
    z_root = 2;
    cubes[1] =
      new Cuboid(1, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 2;
    y_root = 18;
    z_root = 2;
    cubes[2] =
      new Cuboid(2, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 18;
    y_root = 18;
    z_root = 2;
    cubes[3] =
      new Cuboid(3, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    //
    // Upper Z
    //

    x_root = 2;
    y_root = 2;
    z_root = 18;
    cubes[4] =
      new Cuboid(4, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 18;
    y_root = 2;
    z_root = 18;
    cubes[5] =
      new Cuboid(5, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 2;
    y_root = 18;
    z_root = 18;
    cubes[6] =
      new Cuboid(6, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    x_root = 18;
    y_root = 18;
    z_root = 18;
    cubes[7] =
      new Cuboid(7, new VectorI3I(x_root, y_root, z_root), new VectorI3I(
        x_root + size,
        y_root + size,
        z_root + size));

    for (final Cuboid c : cubes) {
      boolean added = q.octTreeInsert(c);
      Assert.assertTrue(added);
      added = q.octTreeInsert(c);
      Assert.assertFalse(added);
    }

    for (final Cuboid c : cubes) {
      boolean removed = false;
      removed = q.octTreeRemove(c);
      Assert.assertTrue(removed);
      removed = q.octTreeRemove(c);
      Assert.assertFalse(removed);
    }
  }

  @SuppressWarnings("static-method") @Test public void testToString()
    throws ConstraintError
  {
    final OctTreeBasic<Cuboid> q = new OctTreeBasic<Cuboid>(128, 128, 128);
    System.err.println(q.toString());
  }
}
