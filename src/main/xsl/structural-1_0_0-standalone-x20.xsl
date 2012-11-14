<?xml version="1.0" encoding="UTF-8"?>

<!--
  Copyright © 2011 http://io7m.com

  Permission to use, copy, modify, and/or distribute this software for any
  purpose with or without fee is hereby granted, provided that the above
  copyright notice and this permission notice appear in all copies.

  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
-->

<!-- version: 1.0.0 -->

<xt:stylesheet version="2.0"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xt="http://www.w3.org/1999/XSL/Transform"
  xmlns:s="http://www.io7m.com/schemas/structural/1.0.0">

  <xt:include href="structural-1_0_0-x20.xsl"/>

  <xt:param name="io7m.structural-1_0_0.output_directory">.</xt:param>

  <xt:template name="io7m.structural-1_0_0.standalone-output-file-create">
    <xt:variable name="file_name"><xt:apply-templates select="." mode="structural-1_0_0.make-file"/></xt:variable>
    <xt:variable name="file_output"><xt:value-of select="concat($io7m.structural-1_0_0.output_directory,'/',$file_name)"/></xt:variable>

    <xt:result-document
      doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
      doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"
      encoding="UTF-8"
      href="{$file_output}"
      indent="yes"
      method="xml">
      <xt:apply-templates select="." mode="structural-1_0_0.html-main"/>
    </xt:result-document>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.standalone-main" match="/">
    <xt:for-each select="//s:document | //s:part | //s:section">
      <xt:call-template name="io7m.structural-1_0_0.standalone-output-file-create"/>
    </xt:for-each>
  </xt:template>

</xt:stylesheet>
