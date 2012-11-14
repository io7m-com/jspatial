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
    mode:make-title-number

    Title number generation. Given an element, these templates
    generate a number identifying the element suitable for use
    in titles and TOC entries.
    -->

  <xt:template
    name="io7m.structural-1_0_0.part-section-subsection-formal-item-title.make-title-number"
    mode="structural-1_0_0.make-title-number"
    match="s:part/s:section/s:subsection/s:formal-item/s:formal-item-title">
    <xt:variable name="part_index"><xt:number       count="s:document/s:part"/></xt:variable>
    <xt:variable name="section_index"><xt:number    count="s:document/s:part/s:section"/></xt:variable>
    <xt:variable name="subsection_index"><xt:number count="s:document/s:part/s:section/s:subsection"/></xt:variable>
    <xt:variable name="item_index"><xt:number       count="s:document/s:part/s:section/s:subsection/s:formal-item"/></xt:variable>
    <xt:value-of select="concat($part_index,'.',$section_index,'.',$subsection_index,'.',$item_index)"/>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.part-section-formal-item-title.make-title-number"
    mode="structural-1_0_0.make-title-number"
    match="s:part/s:section/s:formal-item/s:formal-item-title">
    <xt:variable name="part_index"><xt:number    count="s:document/s:part"/></xt:variable>
    <xt:variable name="section_index"><xt:number count="s:document/s:part/s:section"/></xt:variable>
    <xt:variable name="item_index"><xt:number    count="s:document/s:part/s:section/s:formal-item"/></xt:variable>
    <xt:value-of select="concat($part_index,'.',$section_index,'.',$item_index)"/>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.section-subsection-formal-item-title.make-title-number"
    mode="structural-1_0_0.make-title-number"
    match="s:document/s:section/s:subsection/s:formal-item/s:formal-item-title">
    <xt:variable name="section_index"><xt:number    count="s:document/s:section"/></xt:variable>
    <xt:variable name="subsection_index"><xt:number count="s:document/s:section/s:subsection"/></xt:variable>
    <xt:variable name="item_index"><xt:number       count="s:document/s:section/s:subsection/s:formal-item"/></xt:variable>
    <xt:value-of select="concat($section_index,'.',$subsection_index,'.',$item_index)"/>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.section-formal-item-title.make-title-number"
    mode="structural-1_0_0.make-title-number"
    match="s:document/s:section/s:formal-item/s:formal-item-title">
    <xt:variable name="section_index"><xt:number count="s:document/s:section"/></xt:variable>
    <xt:variable name="item_index"><xt:number    count="s:document/s:section/s:formal-item"/></xt:variable>
    <xt:value-of select="concat($section_index,'.',$item_index)"/>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.part-section-subsection-title.make-title-number"
    mode="structural-1_0_0.make-title-number"
    match="s:part/s:section/s:subsection/s:subsection-title">
    <xt:variable name="part_index"><xt:number       count="s:document/s:part"/></xt:variable>
    <xt:variable name="section_index"><xt:number    count="s:document/s:part/s:section"/></xt:variable>
    <xt:variable name="subsection_index"><xt:number count="s:document/s:part/s:section/s:subsection"/></xt:variable>
    <xt:value-of select="concat($part_index,'.',$section_index,'.',$subsection_index)"/>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.section-subsection-title.make-title-number"
    mode="structural-1_0_0.make-title-number"
    match="s:document/s:section/s:subsection/s:subsection-title">
    <xt:variable name="section_index"><xt:number    count="s:document/s:section"/></xt:variable>
    <xt:variable name="subsection_index"><xt:number count="s:document/s:section/s:subsection"/></xt:variable>
    <xt:value-of select="concat($section_index,'.',$subsection_index)"/>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.part-section-title.make-title-number"
    mode="structural-1_0_0.make-title-number"
    match="s:part/s:section/s:section-title">
    <xt:variable name="part_index"><xt:number    count="s:document/s:part"/></xt:variable>
    <xt:variable name="section_index"><xt:number count="s:document/s:part/s:section"/></xt:variable>
    <xt:value-of select="concat($part_index,'.',$section_index)"/>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.section-title.make-title-number"
    mode="structural-1_0_0.make-title-number"
    match="s:document/s:section/s:section-title">
    <xt:variable name="section_index"><xt:number count="s:document/s:section"/></xt:variable>
    <xt:value-of select="$section_index"/>
  </xt:template>

  <xt:template
    name="io7m.structural-1_0_0.part-title.make-title-number"
    mode="structural-1_0_0.make-title-number"
    match="s:part/s:part-title">
    <xt:number count="s:document/s:part"/>
  </xt:template>

  <!--
    mode:make-title-numbered

    Numbered title generation. Given an element, these templates
    generate a numbered title, suitable for use in TOC entries.

    The basic format is:

      $part.$section.$subsection $text

    As an example:

      1.2.3. Subsection Title
    -->

  <xt:template name="io7m.structural-1_0_0.any.title.make-title-numbered"
    match="s:document-title | s:part-title | s:section-title | s:subsection-title | s:formal-item-title"
    mode="structural-1_0_0.make-title-numbered">
    <xt:value-of select="."/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.formal-item.make-title-numbered" match="s:formal-item" mode="structural-1_0_0.make-title-numbered">
    <xt:apply-templates select="s:formal-item-title" mode="structural-1_0_0.make-title-number"/>.
    <xt:apply-templates select="s:formal-item-title" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.subsection.make-title-numbered" match="s:subsection" mode="structural-1_0_0.make-title-numbered">
    <xt:apply-templates select="s:subsection-title" mode="structural-1_0_0.make-title-number"/>.
    <xt:apply-templates select="s:subsection-title" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.section.make-title-numbered" match="s:section" mode="structural-1_0_0.make-title-numbered">
    <xt:apply-templates select="s:section-title" mode="structural-1_0_0.make-title-number"/>.
    <xt:apply-templates select="s:section-title" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.part.make-title-numbered" match="s:part" mode="structural-1_0_0.make-title-numbered">
    <xt:apply-templates select="s:part-title" mode="structural-1_0_0.make-title-number"/>.
    <xt:apply-templates select="s:part-title" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.document.make-title-numbered" match="s:document" mode="structural-1_0_0.make-title-numbered">
    <xt:apply-templates select="s:document-title" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <!--
    mode:title-next

    See mode:file-next for comments regarding the selection of nodes.
    -->

  <xt:template name="io7m.structural-1_0_0.document.title-next" match="s:document" mode="structural-1_0_0.title-next">
    <xt:apply-templates select="(descendant::s:part | descendant::s:section)[1]" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.document-part.title-next" match="s:document/s:part" mode="structural-1_0_0.title-next">
    <xt:apply-templates select="(descendant::s:section)[1]" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.document-section.title-next" match="s:document/s:section" mode="structural-1_0_0.title-next">
    <xt:apply-templates select="following-sibling::s:section[1]" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.part-section.title-next" match="s:part/s:section" mode="structural-1_0_0.title-next">
    <xt:choose>
      <xt:when test="count(following-sibling::s:section[1]) > 0">
        <xt:apply-templates select="following-sibling::s:section[1]" mode="structural-1_0_0.make-title-numbered"/>
      </xt:when>
      <xt:otherwise>
        <xt:apply-templates select="../following-sibling::s:part[1]" mode="structural-1_0_0.make-title-numbered"/>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.any.title-next" match="*" mode="structural-1_0_0.title-next">
    <xt:apply-templates select="parent::*" mode="structural-1_0_0.title-next"/>
  </xt:template>

  <!--
    mode:title-previous

    See mode:file-previous for comments regarding the selection of nodes.
    -->

  <xt:template name="io7m.structural-1_0_0.part.title-previous" match="s:part" mode="structural-1_0_0.title-previous">
    <xt:choose>
      <xt:when test="count(preceding-sibling::s:part) > 0">
        <xt:apply-templates
          select="(preceding-sibling::s:part[1])/s:section[last()]"
          mode="structural-1_0_0.make-title-numbered"/>
      </xt:when>
      <xt:otherwise>
        <xt:apply-templates
          select="parent::s:document"
          mode="structural-1_0_0.make-title-numbered"/>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.section.title-previous" match="s:document/s:section" mode="structural-1_0_0.title-previous">
    <xt:choose>
      <xt:when test="count(preceding-sibling::s:section[1]) > 0">
        <xt:apply-templates select="preceding-sibling::s:section[1]" mode="structural-1_0_0.make-title-numbered"/>
      </xt:when>
      <xt:otherwise>
        <xt:apply-templates select="parent::s:document" mode="structural-1_0_0.make-title-numbered"/>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.part-section.title-previous" match="s:part/s:section" mode="structural-1_0_0.title-previous">
    <xt:choose>
      <xt:when test="count(preceding-sibling::s:section[1]) > 0">
        <xt:apply-templates select="preceding-sibling::s:section[1]" mode="structural-1_0_0.make-title-numbered"/>
      </xt:when>
      <xt:otherwise>
        <xt:apply-templates select="parent::s:part" mode="structural-1_0_0.make-title-numbered"/>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.any.title-previous" match="*" mode="structural-1_0_0.title-previous">
    <xt:apply-templates select="parent::*" mode="structural-1_0_0.title-previous"/>
  </xt:template>

  <!--
    mode:title-up
    -->

  <xt:template name="io7m.structural-1_0_0.document-part.title-up" match="s:document/s:part" mode="structural-1_0_0.title-up">
    <xt:apply-templates select="ancestor::s:document[1]" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.document-section.title-up" match="s:document/s:section" mode="structural-1_0_0.title-up">
    <xt:apply-templates select="ancestor::s:document[1]" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.document-part-section.title-up" match="s:part/s:section" mode="structural-1_0_0.title-up">
    <xt:apply-templates select="ancestor::s:part[1]" mode="structural-1_0_0.make-title-numbered"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.any.title-up" match="*" mode="structural-1_0_0.title-up">
    <xt:apply-templates select="parent::*" mode="structural-1_0_0.title-up"/>
  </xt:template>

</xt:stylesheet>
