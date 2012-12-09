package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.UnreachableCodeException;
import com.io7m.jtensors.VectorI3I;

public final class OctTreeLimitTest extends OctTreeCommonTests
{
  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct128()
  {
    try {
      return new OctTreeLimit<T>(new OctTreeConfig());
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
      return new OctTreeLimit<T>(c);
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
      return new OctTreeLimit<T>(c);
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
      return new OctTreeLimit<T>(c);
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
      return new OctTreeLimit<T>(c);
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
      return new OctTreeLimit<T>(c);
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
      return new OctTreeLimit<T>(c);
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
      return new OctTreeLimit<T>(c);
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
      return new OctTreeLimit<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitOddX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(4);
    c.setMinimumSizeX(3);
    c.setMinimumSizeY(4);
    c.setMinimumSizeZ(4);

    new OctTreeLimit<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitOddY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(4);
    c.setMinimumSizeX(4);
    c.setMinimumSizeY(3);
    c.setMinimumSizeZ(4);

    new OctTreeLimit<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitOddZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(4);
    c.setMinimumSizeX(4);
    c.setMinimumSizeY(4);
    c.setMinimumSizeZ(3);

    new OctTreeLimit<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitTooLargeX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(4);
    c.setMinimumSizeX(5);
    c.setMinimumSizeY(4);
    c.setMinimumSizeZ(4);

    new OctTreeLimit<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitTooLargeY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(4);
    c.setMinimumSizeX(4);
    c.setMinimumSizeY(5);
    c.setMinimumSizeZ(4);

    new OctTreeLimit<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitTooLargeZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(4);
    c.setMinimumSizeX(4);
    c.setMinimumSizeY(4);
    c.setMinimumSizeZ(5);

    new OctTreeLimit<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitTooSmallX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(4);
    c.setMinimumSizeX(1);
    c.setMinimumSizeY(2);
    c.setMinimumSizeZ(2);

    new OctTreeLimit<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitTooSmallY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(4);
    c.setMinimumSizeX(2);
    c.setMinimumSizeY(1);
    c.setMinimumSizeZ(2);

    new OctTreeLimit<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateLimitTooSmallZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(4);
    c.setMinimumSizeX(2);
    c.setMinimumSizeY(2);
    c.setMinimumSizeZ(1);

    new OctTreeLimit<Cuboid>(c);
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

    new OctTreeLimit<Cuboid>(c);
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

    new OctTreeLimit<Cuboid>(c);
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

    new OctTreeLimit<Cuboid>(c);
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

    new OctTreeLimit<Cuboid>(c);
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

    new OctTreeLimit<Cuboid>(c);
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

    new OctTreeLimit<Cuboid>(c);
  }

  @SuppressWarnings("static-method") @Test public final
    void
    testInsertLeafNoSplit()
      throws ConstraintError,
        Exception
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(128);
    c.setSizeY(128);
    c.setSizeZ(128);
    c.setMinimumSizeX(128);
    c.setMinimumSizeY(128);
    c.setMinimumSizeZ(128);

    final OctTreeLimit<Cuboid> q = new OctTreeLimit<Cuboid>(c);

    final Counter counter = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(counter);
    Assert.assertEquals(1, counter.count);
  }

  @SuppressWarnings("static-method") @Test public final
    void
    testInsertSplitNotX()
      throws ConstraintError,
        Exception
  {
    final OctTreeConfig config = new OctTreeConfig();
    config.setSizeX(128);
    config.setSizeY(128);
    config.setSizeZ(128);
    config.setMinimumSizeX(128);
    config.setMinimumSizeY(2);
    config.setMinimumSizeZ(2);
    final OctTreeInterface<Cuboid> q = new OctTreeLimit<Cuboid>(config);

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @SuppressWarnings("static-method") @Test public final
    void
    testInsertSplitNotY()
      throws ConstraintError,
        Exception
  {
    final OctTreeConfig config = new OctTreeConfig();
    config.setSizeX(128);
    config.setSizeY(128);
    config.setSizeZ(128);
    config.setMinimumSizeX(2);
    config.setMinimumSizeY(128);
    config.setMinimumSizeZ(2);
    final OctTreeInterface<Cuboid> q = new OctTreeLimit<Cuboid>(config);

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @SuppressWarnings("static-method") @Test public final
    void
    testInsertSplitNotZ()
      throws ConstraintError,
        Exception
  {
    final OctTreeConfig config = new OctTreeConfig();
    config.setSizeX(128);
    config.setSizeY(128);
    config.setSizeZ(128);
    config.setMinimumSizeX(2);
    config.setMinimumSizeY(2);
    config.setMinimumSizeZ(128);
    final OctTreeInterface<Cuboid> q = new OctTreeLimit<Cuboid>(config);

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Override
    <T extends OctTreeMember<T>>
    OctTreeInterface<T>
    makeOct128Offset64()
  {
    try {
      final OctTreeConfig c = new OctTreeConfig();
      c.setPosition(new VectorI3I(64, 64, 64));
      return new OctTreeLimit<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override
    <T extends OctTreeMember<T>>
    OctTreeInterface<T>
    makeOct128OffsetM64()
  {
    try {
      final OctTreeConfig c = new OctTreeConfig();
      c.setPosition(new VectorI3I(-64, -64, -64));
      return new OctTreeLimit<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }
}
