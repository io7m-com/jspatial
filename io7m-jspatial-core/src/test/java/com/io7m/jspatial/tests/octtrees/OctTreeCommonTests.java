package tests.octtrees;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jfunctional.PartialFunctionType;
import com.io7m.jnull.NullCheckException;
import com.io7m.jspatial.RayI3D;
import com.io7m.jspatial.octtrees.OctTreeMemberType;
import com.io7m.jspatial.octtrees.OctTreeRaycastResult;
import com.io7m.jspatial.octtrees.OctTreeTraversalType;
import com.io7m.jspatial.octtrees.OctTreeType;
import com.io7m.jspatial.tests.Cuboid;
import tests.utilities.TestUtilities;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorReadable3IType;

@SuppressWarnings({ "null", "unchecked" }) public abstract class OctTreeCommonTests
{
  protected static final class Counter implements
  OctTreeTraversalType<Exception>
  {
    int count = 0;

    Counter()
    {

    }

    @Override public final void visit(
      final int depth,
      final VectorReadable3IType lower,
      final VectorReadable3IType upper)
        throws Exception
    {
      ++this.count;
    }
  }

  protected static abstract class IterationChecker0 implements
  PartialFunctionType<Cuboid, Boolean, Exception>
  {
    final TreeSet<Cuboid> got;

    IterationChecker0()
    {
      this.got = new TreeSet<Cuboid>();
    }
  }

  protected static final class IterationCounter implements
  PartialFunctionType<Cuboid, Boolean, Exception>
  {
    int count = 0;

    IterationCounter()
    {

    }

    @Override public Boolean call(
      final Cuboid x)
    {
      ++this.count;
      return Boolean.TRUE;
    }
  }

  abstract <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct128();

  abstract
  <T extends OctTreeMemberType<T>>
  OctTreeType<T>
  makeOct128Offset64();

  abstract
  <T extends OctTreeMemberType<T>>
  OctTreeType<T>
  makeOct128OffsetM64();

  abstract <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct16();

  abstract <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2();

  abstract <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_2_4();

  abstract <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_4_2();

  abstract <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct2_4_4();

  abstract <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_2_2();

  abstract <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_2_4();

  abstract <T extends OctTreeMemberType<T>> OctTreeType<T> makeOct4_4_2();

  @Test public final void testClear()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
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

  @Test public final void testCountInitial()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Counter c = new Counter();

    q.octTreeTraverse(c);
    Assert.assertEquals(1, c.count);
  }

  @Test public final void testCreate()
  {
    {
      final OctTreeType<Cuboid> q = this.makeOct128();
      Assert.assertEquals(128, q.octTreeGetSizeX());
      Assert.assertEquals(128, q.octTreeGetSizeY());
      Assert.assertEquals(128, q.octTreeGetSizeZ());
      Assert.assertEquals(0, q.octTreeGetPositionX());
      Assert.assertEquals(0, q.octTreeGetPositionY());
      Assert.assertEquals(0, q.octTreeGetPositionZ());
    }

    {
      final OctTreeType<Cuboid> q = this.makeOct128Offset64();
      Assert.assertEquals(128, q.octTreeGetSizeX());
      Assert.assertEquals(128, q.octTreeGetSizeY());
      Assert.assertEquals(128, q.octTreeGetSizeZ());
      Assert.assertEquals(64, q.octTreeGetPositionX());
      Assert.assertEquals(64, q.octTreeGetPositionY());
      Assert.assertEquals(64, q.octTreeGetPositionZ());
    }

    {
      final OctTreeType<Cuboid> q = this.makeOct128OffsetM64();
      Assert.assertEquals(128, q.octTreeGetSizeX());
      Assert.assertEquals(128, q.octTreeGetSizeY());
      Assert.assertEquals(128, q.octTreeGetSizeZ());
      Assert.assertEquals(-64, q.octTreeGetPositionX());
      Assert.assertEquals(-64, q.octTreeGetPositionY());
      Assert.assertEquals(-64, q.octTreeGetPositionZ());
    }
  }

  @Test public final void testInsertAtRoot()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct16();

    final Counter c = new Counter();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(12, 12, 12));

    final boolean in = q.octTreeInsert(r);
    Assert.assertTrue(in);

    q.octTreeTraverse(c);
    Assert.assertEquals(9, c.count);
  }

  @Test public final void testInsertDuplicate()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct16();

    final Cuboid r =
      new Cuboid(0, new VectorI3I(0, 0, 0), new VectorI3I(12, 12, 12));

    boolean in = false;
    in = q.octTreeInsert(r);
    Assert.assertTrue(in);
    in = q.octTreeInsert(r);
    Assert.assertFalse(in);
  }

  @Test(expected = IllegalArgumentException.class) public final
  void
  testInsertIllFormed()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Cuboid r =
      new Cuboid(0, new VectorI3I(12, 12, 12), new VectorI3I(0, 0, 0));

    assert q != null;
    q.octTreeInsert(r);
  }

  @Test public final void testInsertImmediate()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Counter counter = new Counter();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, q.octTreeGetSizeX());

    for (final Cuboid c : cubes) {
      final boolean in = q.octTreeInsert(c);
      Assert.assertTrue(in);
    }

    q.octTreeTraverse(counter);
    Assert.assertEquals(73, counter.count);
  }

  @Test public final void testInsertOutside()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct16();

    final Cuboid r =
      new Cuboid(0, new VectorI3I(18, 18, 18), new VectorI3I(28, 28, 28));

    final boolean in = q.octTreeInsert(r);
    Assert.assertFalse(in);
  }

  @Test public final void testIterate()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, q.octTreeGetSizeX());

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

  @Test public final void testIterateEarlyEnd()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, q.octTreeGetSizeX());

    for (final Cuboid c : cubes) {
      q.octTreeInsert(c);
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

  @Test(expected = NullCheckException.class) public final
  void
  testIterateNull()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    q
    .octTreeIterateObjects((PartialFunctionType<Cuboid, Boolean, Exception>) TestUtilities
      .actuallyNull());
  }

  @Test public final void testQueryContaining()
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, q.octTreeGetSizeX());

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

  @Test public final void testQueryContainingExact()
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, q.octTreeGetSizeX());

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

  @Test public final void testQueryOverlapping()
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, q.octTreeGetSizeX());

    for (final Cuboid c : cubes) {
      final boolean in = q.octTreeInsert(c);
      Assert.assertTrue(in);
    }

    for (final Cuboid c : cubes) {
      final VectorReadable3IType lower = c.boundingVolumeLower();
      final VectorReadable3IType upper = c.boundingVolumeUpper();

      final VectorI3I new_lower =
        new VectorI3I(lower.getXI() - 2, lower.getYI() - 2, lower.getZI() - 2);
      final VectorI3I new_upper =
        new VectorI3I(upper.getXI() - 8, upper.getYI() - 8, upper.getZI() - 8);

      final Cuboid d = new Cuboid(c.getId(), new_lower, new_upper);
      final SortedSet<Cuboid> items = new TreeSet<Cuboid>();
      q.octTreeQueryVolumeOverlapping(d, items);
    }
  }

  @Test public final void testQueryOverlappingExact()
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, q.octTreeGetSizeX());

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

  @Test public final void testQueryOverlappingNot()
  {
    final OctTreeType<Cuboid> q = this.makeOct128();

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

  @Test public final void testRaycast()
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, q.octTreeGetSizeX());

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

  @Test public final void testRaycastNot()
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, q.octTreeGetSizeX());

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

  @Test public final void testRemove()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct16();

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

  @Test public final void testRemoveInsertInvariants()
    throws Exception
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    final Cuboid cubes[] = TestUtilities.makeCuboids(0, q.octTreeGetSizeX());

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

  @Test public final void testToString()
  {
    final OctTreeType<Cuboid> q = this.makeOct128();
    System.err.println(q.toString());
  }
}
