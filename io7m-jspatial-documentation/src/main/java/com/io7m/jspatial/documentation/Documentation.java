/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jspatial.documentation;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Functions for retrieving the documentation.
 */

public final class Documentation
{
  private Documentation()
  {
    throw new AssertionError("Unreachable code!");
  }

  /**
   * @return The resource URI of the documentation.
   */

  public static URI getDocumentationXMLLocation()
  {
    try {
      final URL url =
        Documentation.class
          .getResource("/com/io7m/jspatial/documentation/documentation.xml");
      assert url != null;
      final URI uri = url.toURI();
      assert uri != null;
      return uri;
    } catch (final URISyntaxException e) {
      throw new AssertionError(e);
    }
  }
}
