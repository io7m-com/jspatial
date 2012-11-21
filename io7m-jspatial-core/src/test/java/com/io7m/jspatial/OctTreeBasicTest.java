package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.UnreachableCodeException;
import com.io7m.jtensors.VectorI3I;

public final class OctTreeBasicTest extends OctTreeCommonTests
{
  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct128()
  {
    try {
      return new OctTreeBasic<T>(new OctTreeConfig());
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
      return new OctTreeBasic<T>(c);
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
      return new OctTreeBasic<T>(c);
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
      return new OctTreeBasic<T>(c);
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
      return new OctTreeBasic<T>(c);
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
      return new OctTreeBasic<T>(c);
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
      return new OctTreeBasic<T>(c);
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
      return new OctTreeBasic<T>(c);
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
      return new OctTreeBasic<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateBasicOddX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(3);
    c.setSizeY(4);
    c.setSizeZ(4);

    new OctTreeBasic<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateBasicOddY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(3);
    c.setSizeZ(4);

    new OctTreeBasic<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateBasicOddZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(3);

    new OctTreeBasic<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateBasicTooSmallX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(0);
    c.setSizeY(4);
    c.setSizeZ(4);

    new OctTreeBasic<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateBasicTooSmallY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(0);
    c.setSizeZ(4);

    new OctTreeBasic<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateBasicTooSmallZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(0);

    new OctTreeBasic<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(3);
    c.setSizeY(2);
    c.setSizeZ(2);

    new OctTreeBasic<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(2);
    c.setSizeY(3);
    c.setSizeZ(2);

    new OctTreeBasic<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(2);
    c.setSizeY(2);
    c.setSizeZ(3);

    new OctTreeBasic<Cuboid>(c);
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
