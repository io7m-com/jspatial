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
    mode:make-anchor-reference

    Anchor reference generation. Given an element, these templates
    generate a string sufficient for use in an HTML anchor for linking
    directly to the element and the file containing the element.
    -->

  <xt:template name="io7m.structural-1_0_0.match-doc.make-anchor-reference" match="*" mode="structural-1_0_0.make-anchor-reference">
    <xt:variable name="file_id"><xt:apply-templates select="." mode="structural-1_0_0.make-file-id"/></xt:variable>
    <xt:variable name="elem_id"><xt:apply-templates select="." mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:value-of select="concat($file_id,'.xhtml#',$elem_id)"/>
  </xt:template>

  <!--
    mode:make-file-id

    These templates generate identifiers that are used in the
    construction of filenames. In other words, given an element,
    the concatenation of the result of the make-file-id template
    with '.xhtml', is the file containing the element.
    -->

  <xt:template name="io7m.structural-1_0_0.document.make-file-id" match="s:document" mode="structural-1_0_0.make-file-id">
    <xt:variable name="did"><xt:number count="s:document"/></xt:variable>
    <xt:value-of select="concat('d',$did)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.document-part.make-file-id" match="s:document/s:part" mode="structural-1_0_0.make-file-id">
    <xt:variable name="did"><xt:apply-templates select="parent::s:document" mode="structural-1_0_0.make-file-id"/></xt:variable>
    <xt:variable name="pid"><xt:number count="s:document/s:part"/></xt:variable>
    <xt:value-of select="concat($did,'p',$pid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.document-section.make-file-id" match="s:document/s:section" mode="structural-1_0_0.make-file-id">
    <xt:variable name="did"><xt:apply-templates select="parent::s:document" mode="structural-1_0_0.make-file-id"/></xt:variable>
    <xt:variable name="sid"><xt:number count="s:section"/></xt:variable>
    <xt:value-of select="concat($did,'s',$sid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.part-section.make-file-id" match="s:part/s:section" mode="structural-1_0_0.make-file-id">
    <xt:variable name="pid"><xt:apply-templates select="parent::s:part" mode="structural-1_0_0.make-file-id"/></xt:variable>
    <xt:variable name="sid"><xt:number count="s:document/s:part/s:section"/></xt:variable>
    <xt:value-of select="concat($pid,'s',$sid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.any.make-file-id" match="*" mode="structural-1_0_0.make-file-id">
    <xt:apply-templates select="parent::*" mode="structural-1_0_0.make-file-id"/>
  </xt:template>

  <!--
    mode:make-file

    See mode:make-file-id.
    -->

  <xt:template name="io7m.structural-1_0_0.any.make-file" match="*" mode="structural-1_0_0.make-file">
    <xt:variable name="fid"><xt:apply-templates select="." mode="structural-1_0_0.make-file-id"/></xt:variable>
    <xt:value-of select="concat($fid,'.xhtml')"/>
  </xt:template>

  <!--
    mode:file-previous

    The file-previous templates determine the name of the previous file,
    given any element.
    -->

  <!--
    The element preceding an s:section element can only be an s:section
    with the same s:document ancestor, or the s:document ancestor itself.
    -->

  <xt:template name="io7m.structural-1_0_0.section.file-previous" match="s:document/s:section" mode="structural-1_0_0.file-previous">
    <xt:choose>
      <xt:when test="count(preceding-sibling::s:section[1]) > 0">
        <xt:apply-templates select="preceding-sibling::s:section[1]" mode="structural-1_0_0.make-file"/>
      </xt:when>
      <xt:otherwise>
        <xt:apply-templates select="parent::s:document" mode="structural-1_0_0.make-file"/>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <!--
    The element preceding an s:part element can only be an s:section
    from a preceding s:part element (with the same s:document ancestor),
    or the s:document ancestor itself.
    -->

  <xt:template name="io7m.structural-1_0_0.part.file-previous" match="s:part" mode="structural-1_0_0.file-previous">
    <xt:choose>
      <xt:when test="count(preceding-sibling::s:part) > 0">
        <xt:apply-templates
          select="(preceding-sibling::s:part[1])/s:section[last()]"
          mode="structural-1_0_0.make-file"/>
      </xt:when>
      <xt:otherwise>
        <xt:apply-templates
          select="parent::s:document"
          mode="structural-1_0_0.make-file"/>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <!--
    The element preceding an s:section element can either be an
    s:section element from the same part or an ancestor s:part
    element.
    -->

  <xt:template name="io7m.structural-1_0_0.part-section.file-previous" match="s:part/s:section" mode="structural-1_0_0.file-previous">
    <xt:choose>
      <xt:when test="count(preceding-sibling::s:section[1]) > 0">
        <xt:apply-templates select="preceding-sibling::s:section[1]" mode="structural-1_0_0.make-file"/>
      </xt:when>
      <xt:otherwise>
        <xt:apply-templates select="parent::s:part" mode="structural-1_0_0.make-file"/>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.any.file-previous" match="*" mode="structural-1_0_0.file-previous">
    <xt:apply-templates select="parent::*" mode="structural-1_0_0.file-previous"/>
  </xt:template>

  <!--
    mode:file-next

    The file-next templates determine the name of the next file,
    given any element.
    -->

  <!--
    The next element following a s:document element must be either a
    s:part descendent or an s:section descendent. No other elements
    are possible, given the schema.
    -->

  <xt:template name="io7m.structural-1_0_0.document.file-next" match="s:document" mode="structural-1_0_0.file-next">
    <xt:apply-templates
      select="(descendant::s:part | descendant::s:section)[1]"
      mode="structural-1_0_0.make-file"/>
  </xt:template>

  <!--
    The next element following a s:part element must be a section
    descendent. No other elements are allowed, given the schema.
    -->

  <xt:template name="io7m.structural-1_0_0.part.file-next" match="s:part" mode="structural-1_0_0.file-next">
    <xt:apply-templates select="(descendant::s:section)[1]" mode="structural-1_0_0.make-file"/>
  </xt:template>

  <!--
    The next element following an s:section element, that occurs
    directly as a child of an s:document element, can only be a
    s:section element, assuming the next s:section has the same
    s:document as an ancestor.
    -->

  <xt:template name="io7m.structural-1_0_0.document-section.file-next" match="s:document/s:section" mode="structural-1_0_0.file-next">
    <xt:apply-templates select="following-sibling::s:section[1]" mode="structural-1_0_0.make-file"/>
  </xt:template>

  <!--
    The next element following an s:section element that occurs
    as a child of an s:part element can either be an s:part or
    s:section element, assuming either have the same s:document
    element as an ancestor.
    -->

  <xt:template name="io7m.structural-1_0_0.part-section.file-next" match="s:part/s:section" mode="structural-1_0_0.file-next">
    <xt:choose>
      <xt:when test="count(following-sibling::s:section[1]) > 0">
        <xt:apply-templates select="following-sibling::s:section[1]" mode="structural-1_0_0.make-file"/>
      </xt:when>
      <xt:otherwise>
        <xt:apply-templates select="../following-sibling::s:part[1]" mode="structural-1_0_0.make-file"/>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.any.file-next" match="*" mode="structural-1_0_0.file-next">
    <xt:apply-templates select="parent::*" mode="structural-1_0_0.file-next"/>
  </xt:template>

</xt:stylesheet>
