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

  <!--
    mode:section-contents
    -->

  <xt:template match="text()" mode="structural-1_0_0.section-contents"/>

  <xt:template
    match="s:section/s:subsection"
    name="io7m.structural-1_0_0.match-doc-part-section-subsection.section_contents"
    mode="structural-1_0_0.section-contents">
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_section_contents_item structural_1_0_0_section_contents_item2 structural_1_0_0_section_contents_subsection</xt:with-param>
      </xt:call-template>
      <xt:call-template name="io7m.structural-1_0_0.html.titled-anchor"/>
    </xt:element>
  </xt:template>

  <xt:template
    match="s:section"
    name="io7m.structural-1_0_0.match-doc-section.section_contents"
    mode="structural-1_0_0.section-contents">
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_section_contents_item structural_1_0_0_section_contents_item1 structural_1_0_0_section_contents_section</xt:with-param>
      </xt:call-template>
      <xt:apply-templates select="s:subsection" mode="structural-1_0_0.section-contents"/>
    </xt:element>
  </xt:template>

</xt:stylesheet>
