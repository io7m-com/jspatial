package com.io7m.jspatial.tests;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jspatial.Dimensions;

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

  @SuppressWarnings("static-method") @Test public void testSpan1DVarious()
  {
    final int spans[] = new int[4];

    for (int i = 2; i < 30; ++i) {
      Dimensions.split1D(0, (1 << i) - 1, spans);

      final int lower_lower = 0;
      final int lower_upper = (1 << (i - 1)) - 1;
      final int upper_lower = 1 << (i - 1);
      final int upper_upper = (1 << i) - 1;

      System.err.print("testSpan1DVarious: [");
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

}
