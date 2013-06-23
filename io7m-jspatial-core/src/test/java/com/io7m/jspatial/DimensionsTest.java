package com.io7m.jspatial;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualFloat;

public class DimensionsTest
{
  @SuppressWarnings("static-method") @Test public void testSpan1D()
  {
    final int spans[] = new int[4];

    Dimensions.split1D(0, 3, spans);
    Assert.assertEquals(0, spans[0]);
    Assert.assertEquals(1, spans[1]);
    Assert.assertEquals(2, spans[2]);
    Assert.assertEquals(3, spans[3]);
  }

  @SuppressWarnings("static-method") @Test public void testSpan1DF()
  {
    final AlmostEqualFloat.ContextRelative context =
      new AlmostEqualFloat.ContextRelative();
    final float spans[] = new float[4];

    Dimensions.split1DF(0, 3, spans);
    System.err.println("testSpan1DF: { "
      + spans[0]
      + " "
      + spans[1]
      + " "
      + spans[2]
      + " "
      + spans[3]
      + " }");

    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, 0, spans[0]));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      1.4999999f,
      spans[1]));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, 1.5f, spans[2]));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, 3.0f, spans[3]));
  }

  @SuppressWarnings("static-method") @Test public void testSpan1DD()
  {
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    final double spans[] = new double[4];

    Dimensions.split1DD(0, 3, spans);
    System.err.println("testSpan1DD: { "
      + spans[0]
      + " "
      + spans[1]
      + " "
      + spans[2]
      + " "
      + spans[3]
      + " }");

    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 0, spans[0]));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      1.4999999999999998,
      spans[1]));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.5, spans[2]));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 3.0, spans[3]));
  }

  @SuppressWarnings("static-method") @Test public void testSpan1DVarious()
  {
    final int spans[] = new int[4];

    for (int i = 2; i < 30; ++i) {
      Dimensions.split1D(0, (1 << i) - 1, spans);

      final int lower_lower = 0;
      final int lower_upper = (1 << (i - 1)) - 1;
      final int upper_lower = 1 << (i - 1);
      final int upper_upper = (1 << i) - 1;

      System.err.print("testSpan1DVarious: expect [");
      System.err.print(lower_lower);
      System.err.print(" ");
      System.err.print(lower_upper);
      System.err.print(" ");
      System.err.print(upper_lower);
      System.err.print(" ");
      System.err.print(upper_upper);
      System.err.print(" ");
      System.err.println("]");

      System.err.print("testSpan1DVarious: actual [");
      for (final int k : spans) {
        System.err.print(k);
        System.err.print(" ");
      }
      System.err.println("]");

      Assert.assertEquals(lower_lower, spans[0]);
      Assert.assertEquals(lower_upper, spans[1]);
      Assert.assertEquals(upper_lower, spans[2]);
      Assert.assertEquals(upper_upper, spans[3]);
    }
  }

  @SuppressWarnings("static-method") @Test public void testSpan1DFVarious()
  {
    final float spans[] = new float[4];

    for (int i = 2; i < 30; ++i) {
      final float min = 0.0f;
      final float max = (float) Math.pow(2, i);
      final float half = (float) Math.pow(2, i - 1);

      final float lower_lower = min;
      final float lower_upper = Math.nextAfter(half, Float.MIN_VALUE);
      final float upper_lower = half;
      final float upper_upper = max;

      Dimensions.split1DF(min, max, spans);

      System.err.print("testSpan1DFVarious: expect [");
      System.err.print(lower_lower);
      System.err.print(" ");
      System.err.print(lower_upper);
      System.err.print(" ");
      System.err.print(upper_lower);
      System.err.print(" ");
      System.err.print(upper_upper);
      System.err.print(" ");
      System.err.println("]");

      System.err.print("testSpan1DFVarious: actual [");
      for (final float k : spans) {
        System.err.print(k);
        System.err.print(" ");
      }
      System.err.println("]");

      final AlmostEqualFloat.ContextRelative context =
        new AlmostEqualFloat.ContextRelative();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        lower_lower,
        spans[0]));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        lower_upper,
        spans[1]));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        upper_lower,
        spans[2]));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        upper_upper,
        spans[3]));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testSpan1DFVariousNegative()
  {
    final float spans[] = new float[4];

    for (int i = 2; i < 30; ++i) {
      final float min = (float) (0.0f - Math.pow(2, i));
      final float half = (float) (0.0f - Math.pow(2, i - 1));
      final float max = 0.0f;

      final float lower_lower = min;
      final float lower_upper = Math.nextAfter(half, Float.MIN_VALUE);
      final float upper_lower = half;
      final float upper_upper = max;

      Dimensions.split1DF(min, max, spans);

      System.err.print("testSpan1DFVariousNegative: expect [");
      System.err.print(lower_lower);
      System.err.print(" ");
      System.err.print(lower_upper);
      System.err.print(" ");
      System.err.print(upper_lower);
      System.err.print(" ");
      System.err.print(upper_upper);
      System.err.print(" ");
      System.err.println("]");

      System.err.print("testSpan1DFVariousNegative: actual [");
      for (final float k : spans) {
        System.err.print(k);
        System.err.print(" ");
      }
      System.err.println("]");

      final AlmostEqualFloat.ContextRelative context =
        new AlmostEqualFloat.ContextRelative();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        lower_lower,
        spans[0]));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        lower_upper,
        spans[1]));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        upper_lower,
        spans[2]));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        upper_upper,
        spans[3]));
    }
  }

  @SuppressWarnings("static-method") @Test public void testSpan1DDVarious()
  {
    final double spans[] = new double[4];

    for (int i = 2; i < 30; ++i) {
      final double min = 0.0f;
      final double max = Math.pow(2, i);
      final double half = Math.pow(2, i - 1);

      final double lower_lower = min;
      final double lower_upper = Math.nextAfter(half, Double.MIN_VALUE);
      final double upper_lower = half;
      final double upper_upper = max;

      Dimensions.split1DD(min, max, spans);

      System.err.print("testSpan1DDVarious: expect [");
      System.err.print(lower_lower);
      System.err.print(" ");
      System.err.print(lower_upper);
      System.err.print(" ");
      System.err.print(upper_lower);
      System.err.print(" ");
      System.err.print(upper_upper);
      System.err.print(" ");
      System.err.println("]");

      System.err.print("testSpan1DDVarious: actual [");
      for (final double k : spans) {
        System.err.print(k);
        System.err.print(" ");
      }
      System.err.println("]");

      final AlmostEqualDouble.ContextRelative context =
        new AlmostEqualDouble.ContextRelative();
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        lower_lower,
        spans[0]));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        lower_upper,
        spans[1]));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        upper_lower,
        spans[2]));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        upper_upper,
        spans[3]));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testSpan1DDVariousNegative()
  {
    final double spans[] = new double[4];

    for (int i = 2; i < 30; ++i) {
      final double min = 0.0 - Math.pow(2, i);
      final double half = 0.0 - Math.pow(2, i - 1);
      final double max = 0.0f;

      final double lower_lower = min;
      final double lower_upper = Math.nextAfter(half, Double.MIN_VALUE);
      final double upper_lower = half;
      final double upper_upper = max;

      Dimensions.split1DD(min, max, spans);

      System.err.print("testSpan1DDVariousNegative: expect [");
      System.err.print(lower_lower);
      System.err.print(" ");
      System.err.print(lower_upper);
      System.err.print(" ");
      System.err.print(upper_lower);
      System.err.print(" ");
      System.err.print(upper_upper);
      System.err.print(" ");
      System.err.println("]");

      System.err.print("testSpan1DDVariousNegative: actual [");
      for (final double k : spans) {
        System.err.print(k);
        System.err.print(" ");
      }
      System.err.println("]");

      final AlmostEqualDouble.ContextRelative context =
        new AlmostEqualDouble.ContextRelative();
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        lower_lower,
        spans[0]));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        lower_upper,
        spans[1]));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        upper_lower,
        spans[2]));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        upper_upper,
        spans[3]));
    }
  }
}
