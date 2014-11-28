package com.io7m.jspatial.tests.octtrees;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.octtrees.OctTreeLimit;
import com.io7m.jspatial.octtrees.OctTreeMemberType;
import com.io7m.jspatial.octtrees.OctTreeType;
import com.io7m.jspatial.tests.Cuboid;
import com.io7m.jtensors.VectorI3I;
import com.io7m.junreachable.UnreachableCodeException;

@SuppressWarnings({ "static-method" }) public final class OctTreeLimitTest extends
OctTreeCommonTests
{
  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct128()
  {
    try {
      return OctTreeLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
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
      return OctTreeLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        new VectorI3I(64, 64, 64),
        new VectorI3I(128, 128, 128));
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
      return OctTreeLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        new VectorI3I(-64, -64, -64),
        new VectorI3I(128, 128, 128));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct16()
  {
    try {
      return OctTreeLimit.newOctTree(
        new VectorI3I(16, 16, 16),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2()
  {
    try {
      return OctTreeLimit.newOctTree(
        new VectorI3I(2, 2, 2),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_2_4()
  {
    try {
      return OctTreeLimit.newOctTree(
        new VectorI3I(2, 2, 4),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_4_2()
  {
    try {
      return OctTreeLimit.newOctTree(
        new VectorI3I(2, 4, 2),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_4_4()
  {
    try {
      return OctTreeLimit.newOctTree(
        new VectorI3I(2, 4, 4),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_2_2()
  {
    try {
      return OctTreeLimit.newOctTree(
        new VectorI3I(4, 2, 2),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_2_4()
  {
    try {
      return OctTreeLimit.newOctTree(
        new VectorI3I(4, 2, 4),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_4_2()
  {
    try {
      return OctTreeLimit.newOctTree(
        new VectorI3I(4, 4, 2),
        VectorI3I.ZERO,
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
    OctTreeLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(3, 4, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateLimitOddY()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 3, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateLimitOddZ()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 4, 3));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateLimitTooLargeX()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(5, 4, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateLimitTooLargeY()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 5, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateLimitTooLargeZ()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 4, 5));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateLimitTooSmallX()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(1, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateLimitTooSmallY()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(2, 1, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateLimitTooSmallZ()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 1));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateOddX()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(3, 2, 2),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateOddY()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(2, 3, 2),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateOddZ()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(2, 2, 3),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateTooSmallX()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(1, 2, 2),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateTooSmallY()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(2, 1, 2),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
  void
  testCreateTooSmallZ()
  {
    OctTreeLimit.newOctTree(
      new VectorI3I(2, 2, 1),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test public final void testInsertLeafNoSplit()
    throws Exception
  {
    final OctTreeType<Cuboid> q =
      OctTreeLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(128, 128, 128));

    final Counter counter = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(counter);
    Assert.assertEquals(1, counter.count);
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

  @Test public final void testInsertSplitNotX()
    throws Exception
  {
    final OctTreeType<Cuboid> q =
      OctTreeLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(128, 2, 2));

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public final void testInsertSplitNotY()
    throws Exception
  {
    final OctTreeType<Cuboid> q =
      OctTreeLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 128, 2));

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public final void testInsertSplitNotZ()
    throws Exception
  {
    final OctTreeType<Cuboid> q =
      OctTreeLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 128));

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }
}
