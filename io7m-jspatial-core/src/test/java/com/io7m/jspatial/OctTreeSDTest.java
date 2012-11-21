package com.io7m.jspatial;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.UnreachableCodeException;
import com.io7m.jtensors.VectorI3I;

public final class OctTreeSDTest extends OctTreeCommonTests
{
  @Override <T extends OctTreeMember<T>> OctTreeInterface<T> makeOct128()
  {
    try {
      return new OctTreeSD<T>(new OctTreeConfig());
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
      return new OctTreeSD<T>(c);
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
      return new OctTreeSD<T>(c);
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
      return new OctTreeSD<T>(c);
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
      return new OctTreeSD<T>(c);
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
      return new OctTreeSD<T>(c);
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
      return new OctTreeSD<T>(c);
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
      return new OctTreeSD<T>(c);
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
      return new OctTreeSD<T>(c);
    } catch (final ConstraintError e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @SuppressWarnings("static-method") @Test public void testClearDynamic()
    throws ConstraintError,
      Exception
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, config.getSizeX());
    final Cuboid[] statics = TestUtilities.makeCuboids(10, config.getSizeX());

    for (final Cuboid c : dynamics) {
      {
        final boolean in = q.octTreeInsertSD(c, SDType.SD_DYNAMIC);
        Assert.assertTrue(in);
      }
      {
        final boolean in = q.octTreeInsertSD(c, SDType.SD_DYNAMIC);
        Assert.assertFalse(in);
      }
    }

    for (final Cuboid c : statics) {
      {
        final boolean in = q.octTreeInsertSD(c, SDType.SD_STATIC);
        Assert.assertTrue(in);
      }
      {
        final boolean in = q.octTreeInsertSD(c, SDType.SD_STATIC);
        Assert.assertFalse(in);
      }
    }

    {
      final IterationCounter counter = new IterationCounter();
      q.octTreeIterateObjects(counter);
      Assert.assertEquals(16, counter.count);
    }

    q.octTreeSDClearDynamic();

    {
      final IterationCounter counter = new IterationCounter();
      q.octTreeIterateObjects(counter);
      Assert.assertEquals(8, counter.count);
    }
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

    new OctTreeSD<Cuboid>(c);
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

    new OctTreeSD<Cuboid>(c);
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

    new OctTreeSD<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateSDOddX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(5);
    c.setSizeY(4);
    c.setSizeZ(4);
    c.setMinimumSizeX(3);
    c.setMinimumSizeY(4);
    c.setMinimumSizeZ(4);

    new OctTreeSD<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateSDOddY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(5);
    c.setSizeZ(4);
    c.setMinimumSizeX(4);
    c.setMinimumSizeY(3);
    c.setMinimumSizeZ(4);

    new OctTreeSD<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateSDOddZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(5);
    c.setMinimumSizeX(4);
    c.setMinimumSizeY(4);
    c.setMinimumSizeZ(3);

    new OctTreeSD<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateSDTooSmallX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(0);
    c.setSizeY(4);
    c.setSizeZ(4);

    new OctTreeSD<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateSDTooSmallY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(0);
    c.setSizeZ(4);

    new OctTreeSD<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateSDTooSmallZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(4);
    c.setSizeY(4);
    c.setSizeZ(0);

    new OctTreeSD<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallX()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(1);
    c.setSizeY(2);
    c.setSizeZ(2);

    new OctTreeSD<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallY()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(2);
    c.setSizeY(1);
    c.setSizeZ(2);

    new OctTreeSD<Cuboid>(c);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallZ()
    throws ConstraintError
  {
    final OctTreeConfig c = new OctTreeConfig();
    c.setSizeX(2);
    c.setSizeY(2);
    c.setSizeZ(1);

    new OctTreeSD<Cuboid>(c);
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

  @Test public void testIterateEarlyEndDynamic()
    throws ConstraintError,
      Exception
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, config.getSizeX());

    for (final Cuboid c : cubes) {
      q.octTreeInsertSD(c, SDType.SD_DYNAMIC);
    }

    final IterationChecker0 checker = new IterationChecker0() {
      Integer count = Integer.valueOf(0);

      @Override public Boolean call(
        final Cuboid x)
      {
        this.count = Integer.valueOf(this.count.intValue() + 1);
        this.got.add(x);

        if (this.count.intValue() == 4) {
          return Boolean.FALSE;
        }

        return Boolean.TRUE;
      }
    };

    q.octTreeIterateObjects(checker);

    Assert.assertEquals(4, checker.got.size());

    int count = 0;
    for (final Cuboid c : cubes) {
      if (checker.got.contains(c)) {
        ++count;
      }
    }

    Assert.assertEquals(4, count);
  }

  @Test public void testIterateEarlyEndStatic()
    throws ConstraintError,
      Exception
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, config.getSizeX());

    for (final Cuboid c : cubes) {
      q.octTreeInsertSD(c, SDType.SD_STATIC);
    }

    final IterationChecker0 checker = new IterationChecker0() {
      Integer count = Integer.valueOf(0);

      @Override public Boolean call(
        final Cuboid x)
      {
        this.count = Integer.valueOf(this.count.intValue() + 1);
        this.got.add(x);

        if (this.count.intValue() == 4) {
          return Boolean.FALSE;
        }

        return Boolean.TRUE;
      }
    };

    q.octTreeIterateObjects(checker);

    Assert.assertEquals(4, checker.got.size());

    int count = 0;
    for (final Cuboid c : cubes) {
      if (checker.got.contains(c)) {
        ++count;
      }
    }

    Assert.assertEquals(4, count);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingDynamic()
      throws ConstraintError
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, config.getSizeX());

    for (final Cuboid c : dynamics) {
      final boolean in = q.octTreeInsertSD(c, SDType.SD_DYNAMIC);
      Assert.assertTrue(in);
    }

    {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeContaining(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(127, 127, 127)), items);

      Assert.assertEquals(8, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingDynamicNot()
      throws ConstraintError
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);

    final boolean in =
      q.octTreeInsertSD(new Cuboid(
        0,
        new VectorI3I(66, 66, 66),
        new VectorI3I(127, 127, 127)), SDType.SD_DYNAMIC);
    Assert.assertTrue(in);

    {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeContaining(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(65, 65, 65)), items);

      Assert.assertEquals(0, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingDynamicOK()
      throws ConstraintError
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);

    final boolean in =
      q.octTreeInsertSD(new Cuboid(0, new VectorI3I(2, 2, 2), new VectorI3I(
        60,
        60,
        60)), SDType.SD_DYNAMIC);
    Assert.assertTrue(in);

    {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeContaining(new Cuboid(
        0,
        new VectorI3I(1, 1, 1),
        new VectorI3I(61, 61, 61)), items);

      Assert.assertEquals(1, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingStatic()
      throws ConstraintError
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid[] statics = TestUtilities.makeCuboids(0, config.getSizeX());

    for (final Cuboid c : statics) {
      final boolean in = q.octTreeInsertSD(c, SDType.SD_STATIC);
      Assert.assertTrue(in);
    }

    {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeContaining(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(127, 127, 127)), items);

      Assert.assertEquals(8, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingStaticNot()
      throws ConstraintError
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);

    final boolean in =
      q.octTreeInsertSD(new Cuboid(
        0,
        new VectorI3I(66, 66, 66),
        new VectorI3I(127, 127, 127)), SDType.SD_STATIC);
    Assert.assertTrue(in);

    {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeContaining(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(65, 65, 65)), items);

      Assert.assertEquals(0, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingStaticOK()
      throws ConstraintError
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);

    final boolean in =
      q.octTreeInsertSD(new Cuboid(0, new VectorI3I(2, 2, 2), new VectorI3I(
        60,
        60,
        60)), SDType.SD_STATIC);
    Assert.assertTrue(in);

    {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeContaining(new Cuboid(
        0,
        new VectorI3I(1, 1, 1),
        new VectorI3I(61, 61, 61)), items);

      Assert.assertEquals(1, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingDynamic()
      throws ConstraintError
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, config.getSizeX());

    for (final Cuboid c : dynamics) {
      final boolean in = q.octTreeInsertSD(c, SDType.SD_DYNAMIC);
      Assert.assertTrue(in);
    }

    {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeOverlapping(new Cuboid(
        0,
        new VectorI3I(16, 16, 16),
        new VectorI3I(80, 80, 80)), items);

      Assert.assertEquals(8, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingDynamicNot()
      throws ConstraintError
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, config.getSizeX());

    for (final Cuboid c : dynamics) {
      final boolean in = q.octTreeInsertSD(c, SDType.SD_DYNAMIC);
      Assert.assertTrue(in);
    }

    {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeOverlapping(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), items);

      Assert.assertEquals(0, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingStatic()
      throws ConstraintError
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid statics[] = TestUtilities.makeCuboids(0, config.getSizeX());

    for (final Cuboid c : statics) {
      final boolean in = q.octTreeInsertSD(c, SDType.SD_STATIC);
      Assert.assertTrue(in);
    }

    {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeOverlapping(new Cuboid(
        0,
        new VectorI3I(16, 16, 16),
        new VectorI3I(80, 80, 80)), items);

      Assert.assertEquals(8, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingStaticNot()
      throws ConstraintError
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid statics[] = TestUtilities.makeCuboids(0, config.getSizeX());

    for (final Cuboid c : statics) {
      final boolean in = q.octTreeInsertSD(c, SDType.SD_STATIC);
      Assert.assertTrue(in);
    }

    {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeOverlapping(new Cuboid(
        0,
        new VectorI3I(0, 0, 0),
        new VectorI3I(1, 1, 1)), items);

      Assert.assertEquals(0, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public void testRemoveSubDynamic()
    throws ConstraintError,
      Exception
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, config.getSizeX());

    for (final Cuboid c : dynamics) {
      {
        final boolean in = q.octTreeInsertSD(c, SDType.SD_DYNAMIC);
        Assert.assertTrue(in);
      }
      {
        final boolean in = q.octTreeInsertSD(c, SDType.SD_DYNAMIC);
        Assert.assertFalse(in);
      }
    }

    for (final Cuboid c : dynamics) {
      {
        final boolean removed = q.octTreeRemove(c);
        Assert.assertTrue(removed);
      }
      {
        final boolean removed = q.octTreeRemove(c);
        Assert.assertFalse(removed);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testRemoveSubStatic()
    throws ConstraintError,
      Exception
  {
    final OctTreeConfig config = new OctTreeConfig();
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(config);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, config.getSizeX());

    for (final Cuboid c : dynamics) {
      {
        final boolean in = q.octTreeInsertSD(c, SDType.SD_STATIC);
        Assert.assertTrue(in);
      }
      {
        final boolean in = q.octTreeInsertSD(c, SDType.SD_STATIC);
        Assert.assertFalse(in);
      }
    }

    for (final Cuboid c : dynamics) {
      {
        final boolean removed = q.octTreeRemove(c);
        Assert.assertTrue(removed);
      }
      {
        final boolean removed = q.octTreeRemove(c);
        Assert.assertFalse(removed);
      }
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
