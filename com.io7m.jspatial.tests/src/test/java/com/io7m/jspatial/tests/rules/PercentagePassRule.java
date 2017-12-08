/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jspatial.tests.rules;

import java.util.Objects;
import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PercentagePassRule implements TestRule
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PercentagePassRule.class);
  }

  private final int iterations;

  public PercentagePassRule(
    final int in_iter)
  {
    this.iterations = in_iter;
    if (in_iter < 1) {
      throw new IllegalArgumentException(
        String.format(
          "Number of iterations %d must be at least 1",
          Integer.valueOf(in_iter)));
    }
  }

  @Override
  public Statement apply(
    final Statement statement,
    final Description description)
  {
    final PercentagePassing passing =
      description.getAnnotation(PercentagePassing.class);
    if (passing != null) {
      return new PercentagePassStatement(
        this.iterations, statement, description, passing.passPercent());
    }
    return statement;
  }

  private static final class PercentagePassStatement extends Statement
  {
    private final int iterations;
    private final Statement statement;
    private final double target_percent;
    private final Description description;

    private PercentagePassStatement(
      final int in_iterations,
      final Statement in_statement,
      final Description in_description,
      final double in_percent)
    {
      this.iterations = in_iterations;
      this.statement =
        Objects.requireNonNull(in_statement, "Statement");
      this.description =
        Objects.requireNonNull(in_description, "Description");
      this.target_percent = in_percent;
    }

    @Override
    public void evaluate()
      throws Throwable
    {
      int pass = 0;

      for (int index = 0; index < this.iterations; ++index) {
        try {
          this.statement.evaluate();
          LOG.trace(
            "{} [{}]: succeeded",
            this.description.getDisplayName(),
            Integer.valueOf(index));
          ++pass;
        } catch (final Throwable e) {
          LOG.error(
            "{} [{}]: failed: ",
            this.description.getDisplayName(),
            Integer.valueOf(index),
            e);
        }
      }

      final double actual_percent =
        ((double) pass / (double) this.iterations) * 100.0;

      final StringBuilder sb = new StringBuilder(128);
      sb.append("Target pass percentage: ");
      sb.append(String.format("%.2f", Double.valueOf(this.target_percent)));
      sb.append("%");
      sb.append(System.lineSeparator());
      sb.append("Actual pass percentage: ");
      sb.append(String.format("%.2f", Double.valueOf(actual_percent)));
      sb.append("%");
      sb.append(" (");
      sb.append(this.iterations);
      sb.append((" iterations, "));
      sb.append(pass);
      sb.append(" passes, ");
      sb.append(this.iterations - pass);
      sb.append(" failures)");
      final String message = sb.toString();

      LOG.trace(
        "{}: {}", this.description.getDisplayName(), message);
      if (actual_percent < this.target_percent) {
        Assert.fail(message);
      }
    }
  }
}
