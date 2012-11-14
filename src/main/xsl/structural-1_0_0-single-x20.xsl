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

  <!--
    - This stylesheet produces single-page documents by overriding templates
    - in the standalone multi-page document stylesheets and adding new
    - top-level templates that do not create new files.
    -->

  <xt:output
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"
    encoding="UTF-8"
    method="xml"
    indent="yes"/>

  <!--
    - The mode:make-anchor-reference template is overridden here to
    - prevent links being created to now nonexistent files. In other
    - words, whenever a link would have been created to a specific file,
    - it is instead assumed that everything is in the same file and only
    - an anchor reference is necessary.
    -->

  <xt:template
    name="io7m.structural-1_0_0.match-doc.make-anchor-reference.override-single"
    match="*"
    mode="structural-1_0_0.make-anchor-reference">
    <xt:variable name="elem_id"><xt:apply-templates select="." mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:value-of select="concat('#',$elem_id)"/>
  </xt:template>

  <!--
    - As it no longer makes sense to generate footnotes for each section (as sections
    - are no longer on their own pages), this template recreates the usual footnote
    - body rendering for documents.
    -->

  <xt:template
    name="io7m.structural-1_0_0.document-footnotes"
    match="s:document"
    mode="structural-1_0_0.footnotes">
    <xt:if test="count(descendant::*//s:footnote) > 0">
      <xt:element name="div">
        <xt:attribute name="class">structural_1_0_0_footnotes</xt:attribute>
        <xt:element name="hr"/>
        <xt:apply-templates select="descendant::*//s:footnote" mode="structural-1_0_0.footnote-body"/>
      </xt:element>
    </xt:if>
  </xt:template>

  <!--
    - These templates replace the normal section/part/document handling with
    - logic that creates a single HTML file.
    -->

  <xt:template
    name="io7m.structural-1_0_0.single-any"
    match="*"
    mode="structural-1_0_0.single"/>

  <xt:template
    name="io7m.structural-1_0_0.single-section"
    match="s:section"
    mode="structural-1_0_0.single">
    <xt:element name="div">
      <xt:attribute name="class">structural_1_0_0_section_container</xt:attribute>
      <xt:apply-templates select="." mode="structural-1_0_0.make-id-anchor"/>
      <xt:apply-templates mode="structural-1_0_0.rendering"/>
    </xt:element>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.single-part"
    match="s:part"
    mode="structural-1_0_0.single">
    <xt:element name="div">
      <xt:attribute name="class">structural_1_0_0_part_container</xt:attribute>
      <xt:apply-templates select="." mode="structural-1_0_0.make-id-anchor"/>
      <xt:apply-templates mode="structural-1_0_0.rendering"/>
      <xt:apply-templates select="s:section" mode="structural-1_0_0.single"/>
    </xt:element>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.single-document"
    match="s:document"
    mode="structural-1_0_0.single">
    <xt:element name="html">
      <xt:call-template name="io7m.structural-1_0_0.html.html-head">
        <xt:with-param name="title">
          <xt:value-of select="s:document-title"/>
        </xt:with-param>
      </xt:call-template>
      <xt:element name="body">
        <xt:element name="div">
          <xt:attribute name="class">structural_1_0_0_body</xt:attribute>
          <xt:apply-templates select="." mode="structural-1_0_0.make-id-anchor"/>
          <xt:apply-templates mode="structural-1_0_0.rendering"/>
          <xt:apply-templates select="s:section | s:part" mode="structural-1_0_0.single"/>
          <xt:apply-templates select="." mode="structural-1_0_0.footnotes"/>
        </xt:element>
      </xt:element>
    </xt:element>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.single-main"
    match="/">
    <xt:apply-templates select="//s:document" mode="structural-1_0_0.single"/>
  </xt:template>

</xt:stylesheet>
