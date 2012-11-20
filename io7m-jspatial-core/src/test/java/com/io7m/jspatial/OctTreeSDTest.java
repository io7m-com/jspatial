package com.io7m.jspatial;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.Constraints.ConstraintError;
import com.io7m.jaux.functional.Function;
import com.io7m.jspatial.OctTreeSD.Octants;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorReadable3I;

public class OctTreeSDTest
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
    final TreeSet<Cuboid> got;

    IterationChecker0()
    {
      this.got = new TreeSet<Cuboid>();
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
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
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

  @SuppressWarnings("static-method") @Test public void testClearDynamic()
    throws ConstraintError,
      Exception
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, 128);
    final Cuboid[] statics = TestUtilities.makeCuboids(10, 128);

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

  @SuppressWarnings("static-method") @Test public void testCountInitial()
    throws Exception,
      ConstraintError
  {
    final OctTreeSD<Cuboid> qt = new OctTreeSD<Cuboid>(8, 8, 8);
    final Counter c = new Counter();

    qt.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @SuppressWarnings({ "static-method" }) @Test public void testCreate()
    throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(2, 2, 2);
    Assert.assertEquals(2, q.octTreeGetSizeX());
    Assert.assertEquals(2, q.octTreeGetSizeY());
    Assert.assertEquals(2, q.octTreeGetSizeZ());
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddX()
    throws ConstraintError
  {
    new OctTreeSD<Cuboid>(3, 2, 2);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddY()
    throws ConstraintError
  {
    new OctTreeSD<Cuboid>(2, 3, 2);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateOddZ()
    throws ConstraintError
  {
    new OctTreeSD<Cuboid>(2, 2, 3);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallX()
    throws ConstraintError
  {
    new OctTreeSD<Cuboid>(1, 2, 2);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallY()
    throws ConstraintError
  {
    new OctTreeSD<Cuboid>(2, 1, 2);
  }

  @SuppressWarnings({ "unused", "static-method" }) @Test(
    expected = ConstraintError.class) public void testCreateTooSmallZ()
    throws ConstraintError
  {
    new OctTreeSD<Cuboid>(2, 2, 1);
  }

  @SuppressWarnings("static-method") @Test public void testInsertAtRoot()
    throws ConstraintError,
      Exception
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(16, 16, 16);
    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(12, 12, 12));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @SuppressWarnings("static-method") @Test public void testInsertDuplicate()
    throws ConstraintError,
      Exception
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(16, 16, 16);
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
    OctTreeSD<Cuboid> q = null;
    Cuboid r = null;

    try {
      q = new OctTreeSD<Cuboid>(16, 16, 16);
      r = new Cuboid(0, new VectorI3I(12, 12, 12), new VectorI3I(0, 0, 0));
    } catch (final ConstraintError e) {
      Assert.fail(e.toString());
    }

    assert q != null;
    q.octTreeInsert(r);
  }

  @SuppressWarnings("static-method") @Test public void testInsertImmediate()
    throws ConstraintError,
      Exception
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(32, 32, 32);
    final Counter counter = new Counter();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, 32);

    for (final Cuboid c : cubes) {
      final boolean in = q.octTreeInsert(c);
      Assert.assertTrue(in);
    }

    q.octTreeTraverse(counter);
    Assert.assertEquals(73, counter.count);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInsertLeafNoSplit()
      throws ConstraintError,
        Exception
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(2, 2, 2);
    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(0, 0, 0));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @SuppressWarnings("static-method") @Test public void testInsertOutside()
    throws ConstraintError,
      Exception
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(16, 16, 16);
    final Cuboid r =
      new Cuboid(0, new VectorI3I(18, 18, 18), new VectorI3I(28, 28, 28));

    final boolean in = q.octTreeInsert(r);
    Assert.assertFalse(in);
  }

  @Test public void testIterate()
    throws ConstraintError,
      Exception
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(32, 32, 32);
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, 32);

    for (final Cuboid c : cubes) {
      q.octTreeInsert(c);
    }

    final IterationChecker0 checker = new IterationChecker0() {
      @Override public Boolean call(
        final Cuboid x)
      {
        this.got.add(x);
        return Boolean.TRUE;
      }
    };

    q.octTreeIterateObjects(checker);

    Assert.assertEquals(8, checker.got.size());
    for (final Cuboid c : cubes) {
      Assert.assertTrue(checker.got.contains(c));
    }
  }

  @Test public void testIterateEarlyEndDynamic()
    throws ConstraintError,
      Exception
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(32, 32, 32);
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, 32);

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
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(32, 32, 32);
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, 32);

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

  @SuppressWarnings("static-method") @Test(expected = ConstraintError.class) public
    void
    testIterateNull()
      throws ConstraintError,
        Exception
  {
    OctTreeSD<Cuboid> q = null;

    try {
      q = new OctTreeSD<Cuboid>(16, 16, 16);
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
    Assert.assertEquals(8, q.x0y0z0_lower.z);
    Assert.assertEquals(11, q.x0y0z0_upper.x);
    Assert.assertEquals(11, q.x0y0z0_upper.y);
    Assert.assertEquals(11, q.x0y0z0_upper.z);

    Assert.assertEquals(12, q.x1y0z0_lower.x);
    Assert.assertEquals(8, q.x1y0z0_lower.y);
    Assert.assertEquals(8, q.x1y0z0_lower.z);
    Assert.assertEquals(15, q.x1y0z0_upper.x);
    Assert.assertEquals(11, q.x1y0z0_upper.y);
    Assert.assertEquals(11, q.x1y0z0_upper.z);

    Assert.assertEquals(8, q.x0y1z0_lower.x);
    Assert.assertEquals(12, q.x0y1z0_lower.y);
    Assert.assertEquals(8, q.x0y1z0_lower.z);
    Assert.assertEquals(11, q.x0y1z0_upper.x);
    Assert.assertEquals(15, q.x0y1z0_upper.y);
    Assert.assertEquals(11, q.x0y1z0_upper.z);

    Assert.assertEquals(12, q.x1y1z0_lower.x);
    Assert.assertEquals(12, q.x1y1z0_lower.y);
    Assert.assertEquals(8, q.x1y1z0_lower.z);
    Assert.assertEquals(15, q.x1y1z0_upper.x);
    Assert.assertEquals(15, q.x1y1z0_upper.y);
    Assert.assertEquals(11, q.x1y1z0_upper.z);

    Assert.assertEquals(8, q.x0y0z1_lower.x);
    Assert.assertEquals(8, q.x0y0z1_lower.y);
    Assert.assertEquals(12, q.x0y0z1_lower.z);
    Assert.assertEquals(11, q.x0y0z1_upper.x);
    Assert.assertEquals(11, q.x0y0z1_upper.y);
    Assert.assertEquals(15, q.x0y0z1_upper.z);

    Assert.assertEquals(12, q.x1y0z1_lower.x);
    Assert.assertEquals(8, q.x1y0z1_lower.y);
    Assert.assertEquals(12, q.x1y0z1_lower.z);
    Assert.assertEquals(15, q.x1y0z1_upper.x);
    Assert.assertEquals(11, q.x1y0z1_upper.y);
    Assert.assertEquals(15, q.x1y0z1_upper.z);

    Assert.assertEquals(8, q.x0y1z1_lower.x);
    Assert.assertEquals(12, q.x0y1z1_lower.y);
    Assert.assertEquals(12, q.x0y1z1_lower.z);
    Assert.assertEquals(11, q.x0y1z1_upper.x);
    Assert.assertEquals(15, q.x0y1z1_upper.y);
    Assert.assertEquals(15, q.x0y1z1_upper.z);

    Assert.assertEquals(12, q.x1y1z1_lower.x);
    Assert.assertEquals(12, q.x1y1z1_lower.y);
    Assert.assertEquals(12, q.x1y1z1_lower.z);
    Assert.assertEquals(15, q.x1y1z1_upper.x);
    Assert.assertEquals(15, q.x1y1z1_upper.y);
    Assert.assertEquals(15, q.x1y1z1_upper.z);
  }

  @SuppressWarnings("static-method") @Test public void testQueryContaining()
    throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] cubes = TestUtilities.makeCuboids(0, 128);

    for (final Cuboid c : cubes) {
      final boolean in = q.octTreeInsert(c);
      Assert.assertTrue(in);
    }

    for (final Cuboid c : cubes) {
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeContaining(c, items);
      Assert.assertEquals(1, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingDynamic()
      throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, 128);

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
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);

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
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);

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
    testQueryContainingExact()
      throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] cubes = TestUtilities.makeCuboids(0, 128);

    for (final Cuboid c : cubes) {
      final boolean in = q.octTreeInsert(c);
      Assert.assertTrue(in);
    }

    final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
    q.octTreeQueryVolumeContaining(new Cuboid(
      0,
      new VectorI3I(0, 0, 0),
      new VectorI3I(127, 127, 127)), items);

    Assert.assertEquals(8, items.size());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryContainingStatic()
      throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] statics = TestUtilities.makeCuboids(0, 128);

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
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);

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
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);

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

  @SuppressWarnings("static-method") @Test public void testQueryOverlapping()
    throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] cubes = TestUtilities.makeCuboids(0, 128);

    for (final Cuboid c : cubes) {
      final boolean in = q.octTreeInsert(c);
      Assert.assertTrue(in);
    }

    for (final Cuboid c : cubes) {
      final VectorReadable3I lower = c.boundingVolumeLower();
      final VectorReadable3I upper = c.boundingVolumeUpper();

      final VectorI3I new_lower =
        new VectorI3I(lower.getXI() - 2, lower.getYI() - 2, lower.getZI() - 2);
      final VectorI3I new_upper =
        new VectorI3I(upper.getXI() - 8, upper.getYI() - 8, upper.getZI() - 8);

      final Cuboid d = new Cuboid(c.getId(), new_lower, new_upper);
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeOverlapping(d, items);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingDynamic()
      throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, 128);

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
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, 128);

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
    testQueryOverlappingExact()
      throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] cubes = TestUtilities.makeCuboids(0, 128);

    for (final Cuboid c : cubes) {
      final boolean in = q.octTreeInsert(c);
      Assert.assertTrue(in);
    }

    final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
    q.octTreeQueryVolumeOverlapping(new Cuboid(
      0,
      new VectorI3I(0, 0, 0),
      new VectorI3I(127, 127, 127)), items);

    Assert.assertEquals(8, items.size());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingNot()
      throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid c =
      new Cuboid(0, new VectorI3I(4, 4, 4), new VectorI3I(8, 8, 8));
    final Cuboid d =
      new Cuboid(0, new VectorI3I(10, 10, 10), new VectorI3I(12, 12, 12));

    final boolean in = q.octTreeInsert(c);
    Assert.assertTrue(in);

    final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
    q.octTreeQueryVolumeOverlapping(d, items);

    Assert.assertEquals(0, items.size());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQueryOverlappingStatic()
      throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] statics = TestUtilities.makeCuboids(0, 128);

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
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] statics = TestUtilities.makeCuboids(0, 128);

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

  @SuppressWarnings("static-method") @Test public void testRaycast()
    throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] cubes = TestUtilities.makeCuboids(0, 128);

    for (final Cuboid c : cubes) {
      boolean added = q.octTreeInsert(c);
      Assert.assertTrue(added);
      added = q.octTreeInsert(c);
      Assert.assertFalse(added);
    }

    {
      final VectorI3D direction =
        VectorI3D.normalize(new VectorI3D(127, 127, 127));
      final RayI3D ray = new RayI3D(VectorI3D.ZERO, direction);

      final SortedSet<OctTreeRaycastResult<Cuboid>> items =
        new TreeSet<OctTreeRaycastResult<Cuboid>>();
      q.octTreeQueryRaycast(ray, items);

      Assert.assertEquals(2, items.size());

      {
        final OctTreeRaycastResult<Cuboid> r = items.first();
        Assert.assertTrue(r.getObject() == cubes[0]);
      }

      {
        final OctTreeRaycastResult<Cuboid> r = items.last();
        Assert.assertTrue(r.getObject() == cubes[7]);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testRaycastNot()
    throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] cubes = TestUtilities.makeCuboids(0, 128);

    for (final Cuboid c : cubes) {
      boolean added = q.octTreeInsert(c);
      Assert.assertTrue(added);
      added = q.octTreeInsert(c);
      Assert.assertFalse(added);
    }

    {
      final VectorI3D direction = VectorI3D.normalize(new VectorI3D(0, 1, 0));
      final RayI3D ray = new RayI3D(VectorI3D.ZERO, direction);

      final SortedSet<OctTreeRaycastResult<Cuboid>> items =
        new TreeSet<OctTreeRaycastResult<Cuboid>>();
      q.octTreeQueryRaycast(ray, items);

      Assert.assertEquals(0, items.size());
    }
  }

  @SuppressWarnings("static-method") @Test public void testRemove()
    throws ConstraintError,
      Exception
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(16, 16, 16);
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
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(32, 32, 32);
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, 32);

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

  @SuppressWarnings("static-method") @Test public void testRemoveSubDynamic()
    throws ConstraintError,
      Exception
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, 128);

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
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    final Cuboid[] dynamics = TestUtilities.makeCuboids(0, 128);

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

  @SuppressWarnings("static-method") @Test public void testToString()
    throws ConstraintError
  {
    final OctTreeSD<Cuboid> q = new OctTreeSD<Cuboid>(128, 128, 128);
    System.err.println(q.toString());
  }
}
