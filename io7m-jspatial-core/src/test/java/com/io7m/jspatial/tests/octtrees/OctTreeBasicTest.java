package com.io7m.jspatial.tests.octtrees;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.octtrees.OctTreeBasic;
import com.io7m.jspatial.octtrees.OctTreeMemberType;
import com.io7m.jspatial.octtrees.OctTreeType;
import com.io7m.jspatial.tests.Cuboid;
import com.io7m.jtensors.VectorI3I;
import com.io7m.junreachable.UnreachableCodeException;

@SuppressWarnings({ "static-method" }) public final class OctTreeBasicTest extends
  OctTreeCommonTests
{
  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct128()
  {
    try {
      return OctTreeBasic.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct16()
  {
    try {
      return OctTreeBasic.newOctTree(
        new VectorI3I(16, 16, 16),
        VectorI3I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2()
  {
    try {
      return OctTreeBasic.newOctTree(new VectorI3I(2, 2, 2), VectorI3I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_2_4()
  {
    try {
      return OctTreeBasic.newOctTree(new VectorI3I(2, 2, 4), VectorI3I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_4_2()
  {
    try {
      return OctTreeBasic.newOctTree(new VectorI3I(2, 4, 2), VectorI3I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_4_4()
  {
    try {
      return OctTreeBasic.newOctTree(new VectorI3I(2, 4, 4), VectorI3I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_2_2()
  {
    try {
      return OctTreeBasic.newOctTree(new VectorI3I(4, 2, 2), VectorI3I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_2_4()
  {
    try {
      return OctTreeBasic.newOctTree(new VectorI3I(4, 2, 4), VectorI3I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_4_2()
  {
    try {
      return OctTreeBasic.newOctTree(new VectorI3I(4, 4, 2), VectorI3I.ZERO);
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateBasicOddX()
  {
    OctTreeBasic.newOctTree(new VectorI3I(3, 4, 4), VectorI3I.ZERO);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateBasicOddY()
  {
    OctTreeBasic.newOctTree(new VectorI3I(4, 3, 4), VectorI3I.ZERO);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateBasicOddZ()
  {
    OctTreeBasic.newOctTree(new VectorI3I(4, 4, 3), VectorI3I.ZERO);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateBasicTooSmallX()
  {
    OctTreeBasic.newOctTree(new VectorI3I(0, 4, 4), VectorI3I.ZERO);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateBasicTooSmallY()
  {
    OctTreeBasic.newOctTree(new VectorI3I(4, 0, 4), VectorI3I.ZERO);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateBasicTooSmallZ()
  {
    OctTreeBasic.newOctTree(new VectorI3I(4, 4, 0), VectorI3I.ZERO);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateOddX()
  {
    OctTreeBasic.newOctTree(new VectorI3I(3, 2, 2), VectorI3I.ZERO);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateOddY()
  {
    OctTreeBasic.newOctTree(new VectorI3I(2, 3, 2), VectorI3I.ZERO);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateOddZ()
  {
    OctTreeBasic.newOctTree(new VectorI3I(2, 2, 3), VectorI3I.ZERO);
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
    Assert.assertEquals(9, c.count);
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
    Assert.assertEquals(57, c.count);
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
    Assert.assertEquals(9, c.count);
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
    Assert.assertEquals(9, c.count);
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
    Assert.assertEquals(9, c.count);
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
    Assert.assertEquals(9, c.count);
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
    Assert.assertEquals(9, c.count);
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
    Assert.assertEquals(9, c.count);
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
    Assert.assertEquals(9, c.count);
  }

  @Override
    <T extends OctTreeMemberType<T>>
    OctTreeType<T>
    makeOct128Offset64()
  {
    try {
      return OctTreeBasic.newOctTree(
        new VectorI3I(128, 128, 128),
        new VectorI3I(64, 64, 64));
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
      return OctTreeBasic.newOctTree(
        new VectorI3I(128, 128, 128),
        new VectorI3I(-64, -64, -64));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }
}
