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
    Footnote handling
    -->

  <xt:template match="s:footnote/text()">
    <xt:value-of select="."/>
  </xt:template>

  <xt:template match="s:part" mode="structural-1_0_0.footnote-count">
    <xt:value-of select="count(preceding-sibling::s:part//s:footnote)"/>
  </xt:template>

  <xt:template match="s:section" mode="structural-1_0_0.footnote-count">
    <xt:value-of select="count(preceding-sibling::s:section//s:footnote)"/>
  </xt:template>

  <xt:template match="s:subsection" mode="structural-1_0_0.footnote-count">
    <xt:value-of select="count(preceding-sibling::s:subsection//s:footnote)"/>
  </xt:template>

  <xt:template match="s:paragraph" mode="structural-1_0_0.footnote-count">
    <xt:value-of select="count(preceding-sibling::s:paragraph//s:footnote)"/>
  </xt:template>

  <xt:template match="s:footnote" mode="structural-1_0_0.footnote-count">
    <xt:value-of select="count(preceding-sibling::s:footnote) + 1"/>
  </xt:template>

  <xt:template match="*" mode="structural-1_0_0.footnote-count">
    <xt:value-of select="0"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.footnote-count">
    <xt:param name="node_current"/>
    <xt:param name="node_end"/>
    <xt:param name="accumulator"/>

    <xt:variable name="f_count">
      <xt:apply-templates select="$node_current" mode="structural-1_0_0.footnote-count"/>
    </xt:variable>

    <xt:choose>
      <xt:when test="$node_current = $node_end">
        <xt:value-of select="$accumulator"/>
      </xt:when>
      <xt:otherwise>
        <xt:call-template name="io7m.structural-1_0_0.footnote-count">
          <xt:with-param name="node_current" select="$node_current/.."/>
          <xt:with-param name="node_end"     select="$node_end"/>
          <xt:with-param name="accumulator"  select="$accumulator + $f_count"/>
        </xt:call-template>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.footnote-body" match="s:footnote" mode="structural-1_0_0.footnote-body">
    <xt:variable name="id"><xt:apply-templates select="." mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="footnote_index">
      <xt:call-template name="io7m.structural-1_0_0.footnote-count">
        <xt:with-param name="node_current" select="."/>
        <xt:with-param name="node_end"     select="ancestor::s:document[1]"/>
        <xt:with-param name="accumulator"  select="0"/>
      </xt:call-template>
    </xt:variable>
    <xt:element name="div">
      <xt:attribute name="class">structural_1_0_0_footnote_number</xt:attribute>
      <xt:text>[</xt:text>
      <xt:element name="a">
        <xt:attribute name="id">
          <xt:value-of select="$id"/>
        </xt:attribute>
        <xt:attribute name="href">
          <xt:value-of select="concat('#',$id,'_ref')"/>
        </xt:attribute>
        <xt:value-of select="$footnote_index"/>
      </xt:element>
      <xt:text>]</xt:text>
    </xt:element>
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_footnote_body</xt:with-param>
      </xt:call-template>
      <xt:apply-templates
        mode="structural-1_0_0.rendering"
        select="text() | s:footnote | s:image | s:link | s:link-external | s:list-ordered | s:list-unordered | s:table | s:term | s:verbatim"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.section-footnotes" match="s:section" mode="structural-1_0_0.footnotes">
    <xt:if test="count(descendant::s:footnote) > 0">
      <xt:element name="div">
        <xt:attribute name="class">structural_1_0_0_footnotes</xt:attribute>
        <xt:element name="hr"/>
        <xt:apply-templates select="descendant::s:footnote" mode="structural-1_0_0.footnote-body"/>
      </xt:element>
    </xt:if>
  </xt:template>

  <xt:template match="*" mode="structural-1_0_0.footnotes" name="io7m.structural-1_0_0.section-footnotes-any"/>

</xt:stylesheet>
