package com.io7m.jspatial.tests.octtrees;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.octtrees.OctTreeMemberType;
import com.io7m.jspatial.octtrees.OctTreePruneLimit;
import com.io7m.jspatial.octtrees.OctTreeType;
import com.io7m.jspatial.tests.Cuboid;
import com.io7m.jspatial.tests.utilities.TestUtilities;
import com.io7m.jtensors.VectorI3I;
import com.io7m.junreachable.UnreachableCodeException;

@SuppressWarnings({ "static-method", "null" }) public final class OctTreePruneLimitTest extends
  OctTreeCommonTests
{
  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct128()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override
    <T extends OctTreeMemberType<T>>
    OctTreeType<T>
    makeOct128Offset64()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        new VectorI3I(64, 64, 64),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override
    <T extends OctTreeMemberType<T>>
    OctTreeType<T>
    makeOct128OffsetM64()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        new VectorI3I(-64, -64, -64),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct16()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(16, 16, 16),
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(2, 2, 2),
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_2_4()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(2, 2, 4),
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_4_2()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(2, 4, 2),
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_4_4()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(2, 4, 4),
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_2_2()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(4, 2, 2),
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_2_4()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(4, 2, 4),
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_4_2()
  {
    try {
      return OctTreePruneLimit.newOctTree(
        new VectorI3I(4, 4, 2),
        new VectorI3I(0, 0, 0),
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitOddX()
  {
    OctTreePruneLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(3, 4, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitOddY()
  {
    OctTreePruneLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 3, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitOddZ()
  {
    OctTreePruneLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 4, 3));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooLargeX()
  {
    OctTreePruneLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(6, 4, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooLargeY()
  {
    OctTreePruneLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 6, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooLargeZ()
  {
    OctTreePruneLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 4, 6));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooSmallX()
  {
    OctTreePruneLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(1, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooSmallY()
  {
    OctTreePruneLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(2, 1, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooSmallZ()
  {
    OctTreePruneLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 1));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateOddX()
  {
    OctTreePruneLimit.newOctTree(new VectorI3I(3, 2, 2), new VectorI3I(
      0,
      0,
      0), new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateOddY()
  {
    OctTreePruneLimit.newOctTree(new VectorI3I(2, 3, 2), new VectorI3I(
      0,
      0,
      0), new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateOddZ()
  {
    OctTreePruneLimit.newOctTree(new VectorI3I(2, 2, 3), new VectorI3I(
      0,
      0,
      0), new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreatePruneOddX()
  {
    OctTreePruneLimit.newOctTree(new VectorI3I(3, 4, 4), new VectorI3I(
      0,
      0,
      0), new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreatePruneOddY()
  {
    OctTreePruneLimit.newOctTree(new VectorI3I(4, 3, 4), new VectorI3I(
      0,
      0,
      0), new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreatePruneOddZ()
  {
    OctTreePruneLimit.newOctTree(new VectorI3I(4, 4, 3), new VectorI3I(
      0,
      0,
      0), new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateTooSmallX()
  {
    OctTreePruneLimit.newOctTree(new VectorI3I(1, 2, 2), new VectorI3I(
      0,
      0,
      0), new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateTooSmallY()
  {
    OctTreePruneLimit.newOctTree(new VectorI3I(2, 1, 2), new VectorI3I(
      0,
      0,
      0), new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateTooSmallZ()
  {
    OctTreePruneLimit.newOctTree(new VectorI3I(2, 2, 1), new VectorI3I(
      0,
      0,
      0), new VectorI3I(2, 2, 2));
  }

  @Test public final void testInsertLeafNoSplit()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct2();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public final void testInsertSplit()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct128();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(49, c.count);
  }

  @Test public final void testInsertSplitNot()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct2();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public final void testInsertSplitXNotYNotZ()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct4_2_2();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public final void testInsertSplitXYNotZ()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct4_4_2();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public final void testInsertSplitXZNotY()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct4_2_4();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public final void testInsertSplitYNotZNotZ()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct2_4_2();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public final void testInsertSplitYZNotX()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct2_4_4();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public final void testInsertSplitZNotXNotY()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct2_2_4();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public void testRemovePrune()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
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
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct16();

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
}
