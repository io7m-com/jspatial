package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.UnreachableCodeException;
import com.io7m.jtensors.VectorI3I;

public final class OctTreePruneTest extends OctTreeCommonTests
{
  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct128()
  {
    try {
      return new OctTreePrune<T>(new OctTreeConfig());
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct16()
  {
    try {
      final OctTreeConfig c = new OctTreeConfig();
      c.setSizeX(16);
      c.setSizeY(16);
      c.setSizeZ(16);
      return new OctTreePrune<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct2()
  {
    try {
      final OctTreeConfig c = new OctTreeConfig();
      c.setSizeX(2);
      c.setSizeY(2);
      c.setSizeZ(2);
      return new OctTreePrune<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct2_2_4()
  {
    try {
      final OctTreeConfig c = new OctTreeConfig();
      c.setSizeX(2);
      c.setSizeY(2);
      c.setSizeZ(4);
      return new OctTreePrune<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct2_4_2()
  {
    try {
      final OctTreeConfig c = new OctTreeConfig();
      c.setSizeX(2);
      c.setSizeY(4);
      c.setSizeZ(2);
      return new OctTreePrune<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct2_4_4()
  {
    try {
      final OctTreeConfig c = new OctTreeConfig();
      c.setSizeX(2);
      c.setSizeY(4);
      c.setSizeZ(4);
      return new OctTreePrune<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct4_2_2()
  {
    try {
      final OctTreeConfig c = new OctTreeConfig();
      c.setSizeX(4);
      c.setSizeY(2);
      c.setSizeZ(2);
      return new OctTreePrune<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct4_2_4()
  {
    try {
      final OctTreeConfig c = new OctTreeConfig();
      c.setSizeX(4);
      c.setSizeY(2);
      c.setSizeZ(4);
      return new OctTreePrune<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct4_4_2()
  {
    try {
      final OctTreeConfig c = new OctTreeConfig();
      c.setSizeX(4);
      c.setSizeY(4);
      c.setSizeZ(2);
      return new OctTreePrune<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(3);
    c.setSizeY(2);
    c.setSizeZ(2);
    c.setMinimumSizeX(2);
    c.setMinimumSizeY(2);
    c.setMinimumSizeZ(2);

    new OctTreePrune<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(2);
    c.setSizeY(3);
    c.setSizeZ(2);
    c.setMinimumSizeX(2);
    c.setMinimumSizeY(2);
    c.setMinimumSizeZ(2);

    new OctTreePrune<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(2);
    c.setSizeY(2);
    c.setSizeZ(3);
    c.setMinimumSizeX(2);
    c.setMinimumSizeY(2);
    c.setMinimumSizeZ(2);

    new OctTreePrune<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreatePruneOddX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(3);
    c.setSizeY(4);
    c.setSizeZ(4);

    new OctTreePrune<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreatePruneOddY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(3);
    c.setSizeZ(4);

    new OctTreePrune<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreatePruneOddZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(3);

    new OctTreePrune<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(1);
    c.setSizeY(2);
    c.setSizeZ(2);
    c.setMinimumSizeX(2);
    c.setMinimumSizeY(2);
    c.setMinimumSizeZ(2);

    new OctTreePrune<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(2);
    c.setSizeY(1);
    c.setSizeZ(2);
    c.setMinimumSizeX(2);
    c.setMinimumSizeY(2);
    c.setMinimumSizeZ(2);

    new OctTreePrune<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(2);
    c.setSizeY(2);
    c.setSizeZ(1);
    c.setMinimumSizeX(2);
    c.setMinimumSizeY(2);
    c.setMinimumSizeZ(2);

    new OctTreePrune<Cuboid>(c);
  }

  @Test public final void testInsertLeafNoSplit()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct2();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @Test public void testRemovePrune()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct128();
    final Cuboid[] cuboids = TestUtilities.makeCuboids(0, 128);

    for (final Cuboid c : cuboids) {
      {
        final boolean in = q.octTreeInsert(c);
        Assert.assertTrue(in);
      }

      {
        final boolean in = q.octTreeInsert(c);
        Assert.assertFalse(in);
      }
    }

    {
      // (8 ^ 0) + (8 ^ 1) + (8 ^ 2)
      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(73, counter.count);
    }

    {
      // (8 ^ 0) + (8 ^ 1) + (8 ^ 2) - 8
      final boolean removed = q.octTreeRemove(cuboids[0]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(65, counter.count);
    }

    {
      // (8 ^ 0) + (8 ^ 1) + (8 ^ 2) - 8 - 8
      final boolean removed = q.octTreeRemove(cuboids[1]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(57, counter.count);
    }

    {
      // (8 ^ 0) + (8 ^ 1) + (8 ^ 2) - 8 - 8 - 8
      final boolean removed = q.octTreeRemove(cuboids[2]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(49, counter.count);
    }

    {
      // (8 ^ 0) + (8 ^ 1) + (8 ^ 2) - 8 - 8 - 8 - 8
      final boolean removed = q.octTreeRemove(cuboids[3]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(41, counter.count);
    }

    {
      // (8 ^ 0) + (8 ^ 1) + (8 ^ 2) - 8 - 8 - 8 - 8 - 8
      final boolean removed = q.octTreeRemove(cuboids[4]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(33, counter.count);
    }

    {
      // (8 ^ 0) + (8 ^ 1) + (8 ^ 2) - 8 - 8 - 8 - 8 - 8 - 8
      final boolean removed = q.octTreeRemove(cuboids[5]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(25, counter.count);
    }

    {
      // (8 ^ 0) + (8 ^ 1) + (8 ^ 2) - 8 - 8 - 8 - 8 - 8 - 8 - 8
      final boolean removed = q.octTreeRemove(cuboids[6]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(17, counter.count);
    }

    {
      // (8 ^ 0)
      final boolean removed = q.octTreeRemove(cuboids[7]);
      Assert.assertTrue(removed);

      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(1, counter.count);
    }

    {
      final IterationCounter counter = new IterationCounter();
      q.octTreeIterateObjects(counter);
      Assert.assertEquals(0, counter.count);
    }

    for (final Cuboid c : cuboids) {
      {
        final boolean in = q.octTreeInsert(c);
        Assert.assertTrue(in);
      }

      {
        final boolean in = q.octTreeInsert(c);
        Assert.assertFalse(in);
      }
    }

    {
      final IterationCounter counter = new IterationCounter();
      q.octTreeIterateObjects(counter);
      Assert.assertEquals(8, counter.count);
    }

    {
      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(73, counter.count);
    }
  }

  @Test public void testRemovePruneNotLeafNotEmpty()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct16();

    /**
     * The cuboids are larger than the smallest octant size, and as a result,
     * will not be inserted into leaf nodes. Removing one of them will trigger
     * an attempt to prune nodes, which will fail due to a non-empty non-leaf
     * node.
     */

    final Cuboid c0 =
      new Cuboid(0, new VectorI3I(1, 1, 1), new VectorI3I(7, 7, 7));
    final Cuboid c1 =
      new Cuboid(1, new VectorI3I(1, 1, 1), new VectorI3I(7, 7, 7));

    {
      final boolean in = q.octTreeInsert(c0);
      Assert.assertTrue(in);
    }

    {
      final boolean in = q.octTreeInsert(c1);
      Assert.assertTrue(in);
    }

    {
      // (8 ^ 0) + (8 ^ 1) + 8
      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(17, counter.count);
    }

    {
      final boolean removed = q.octTreeRemove(c0);
      Assert.assertTrue(removed);
    }

    {
      // (8 ^ 0) + (8 ^ 1)
      final Counter counter = new Counter();
      q.octTreeTraverse(counter);
      Assert.assertEquals(9, counter.count);
    }
  }

  @Test public final void testInsertSplit()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct128();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(57, c.count);
  }

  @Test public final void testInsertSplitNot()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct2();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @Test public final void testInsertSplitXNotYNotZ()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct4_2_2();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @Test public final void testInsertSplitXYNotZ()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct4_4_2();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @Test public final void testInsertSplitXZNotY()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct4_2_4();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @Test public final void testInsertSplitYNotZNotZ()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct2_4_2();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @Test public final void testInsertSplitYZNotX()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct2_4_4();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @Test public final void testInsertSplitZNotXNotY()
    throws ConstraintError,
      Exception
  {
    final OctTreeInterface<Cuboid> q = this.makeOct2_2_4();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

}
