package tests.octtrees;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.SDType;
import com.io7m.jspatial.octtrees.OctTreeMemberType;
import com.io7m.jspatial.octtrees.OctTreeSDLimit;
import com.io7m.jspatial.octtrees.OctTreeSDType;
import com.io7m.jspatial.octtrees.OctTreeType;
import com.io7m.jspatial.tests.Cuboid;
import tests.utilities.TestUtilities;
import com.io7m.jtensors.VectorI3I;
import com.io7m.junreachable.UnreachableCodeException;

@SuppressWarnings({ "static-method", "null" }) public final class OctTreeSDLimitTest extends
  OctTreeCommonTests
{
  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct128()
  {
    try {
      return OctTreeSDLimit.newOctTree(
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
      return OctTreeSDLimit.newOctTree(
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
      return OctTreeSDLimit.newOctTree(
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
      return OctTreeSDLimit.newOctTree(
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
      return OctTreeSDLimit.newOctTree(new VectorI3I(2, 2, 2), new VectorI3I(
        0,
        0,
        0), new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_2_4()
  {
    try {
      return OctTreeSDLimit.newOctTree(new VectorI3I(2, 2, 4), new VectorI3I(
        0,
        0,
        0), new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_4_2()
  {
    try {
      return OctTreeSDLimit.newOctTree(new VectorI3I(2, 4, 2), new VectorI3I(
        0,
        0,
        0), new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_4_4()
  {
    try {
      return OctTreeSDLimit.newOctTree(new VectorI3I(2, 4, 4), new VectorI3I(
        0,
        0,
        0), new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_2_2()
  {
    try {
      return OctTreeSDLimit.newOctTree(new VectorI3I(4, 2, 2), new VectorI3I(
        0,
        0,
        0), new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_2_4()
  {
    try {
      return OctTreeSDLimit.newOctTree(new VectorI3I(4, 2, 4), new VectorI3I(
        0,
        0,
        0), new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Override <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_4_2()
  {
    try {
      return OctTreeSDLimit.newOctTree(new VectorI3I(4, 4, 2), new VectorI3I(
        0,
        0,
        0), new VectorI3I(2, 2, 2));
    } catch (final Exception e) {
      Assert.fail(e.getMessage());
    }

    throw new UnreachableCodeException();
  }

  @Test public void testClearDynamic()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
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

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitOddX()
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(3, 4, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitOddY()
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 3, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitOddZ()
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 4, 3));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooLargeX()
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(6, 4, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooLargeY()
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 6, 4));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooLargeZ()
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(4, 4, 6));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooSmallX()
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(1, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooSmallY()
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(2, 1, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateLimitTooSmallZ()
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 1));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateOddX()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(3, 2, 2),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateOddY()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(2, 3, 2),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateOddZ()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(2, 2, 3),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateSDOddX()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(5, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateSDOddY()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 5, 4),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateSDOddZ()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 5),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateSDTooSmallX()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(0, 4, 4),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateSDTooSmallY()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 0, 4),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateSDTooSmallZ()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(4, 4, 0),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateTooSmallX()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(1, 2, 2),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateTooSmallY()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(2, 1, 2),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCreateTooSmallZ()
      throws Exception
  {
    OctTreeSDLimit.newOctTree(
      new VectorI3I(2, 2, 1),
      VectorI3I.ZERO,
      new VectorI3I(2, 2, 2));
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

  @Test public void testIterateEarlyEndDynamic()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, 128);

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
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, 128);

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

  @Test public void testQueryContainingDynamic()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
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

  @Test public void testQueryContainingDynamicNot()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));

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

  @Test public void testQueryContainingDynamicOK()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));

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

  @Test public void testQueryContainingStatic()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
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

  @Test public void testQueryContainingStaticNot()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));

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

  @Test public void testQueryContainingStaticOK()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));

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

  @Test public void testQueryOverlappingDynamic()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
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

  @Test public void testQueryOverlappingDynamicNot()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
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

  @Test public void testQueryOverlappingStatic()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    final Cuboid statics[] = TestUtilities.makeCuboids(0, 128);

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

  @Test public void testQueryOverlappingStaticNot()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
    final Cuboid statics[] = TestUtilities.makeCuboids(0, 128);

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

  @Test public void testRemoveSubDynamic()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
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

  @Test public void testRemoveSubStatic()
    throws Exception
  {
    final OctTreeSDType<Cuboid> q =
      OctTreeSDLimit.newOctTree(
        new VectorI3I(128, 128, 128),
        VectorI3I.ZERO,
        new VectorI3I(2, 2, 2));
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
}
