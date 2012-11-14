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
    mode:make-id

    The make-id templates generate identifiers that uniquely identify
    elements. They are used in the construction of HTML anchors. Not
    all elements can be assigned unique IDs - if an element cannot be
    assigned a unique ID, the ID of the nearest enclosing element that
    can will be returned.
    -->

  <xt:template name="io7m.structural-1_0_0.document.make-id" match="s:document" mode="structural-1_0_0.make-id">
    <xt:variable name="did"><xt:number count="s:document"/></xt:variable>
    <xt:value-of select="concat('d',$did)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.part.make-id" match="s:part" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="pid"><xt:number count="s:part"/></xt:variable>
    <xt:value-of select="concat($id,'p',$pid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.section.make-id" match="s:section" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="sid"><xt:number count="s:section"/></xt:variable>
    <xt:value-of select="concat($id,'s',$sid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.subsection.make-id" match="s:subsection" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="zid"><xt:number count="s:subsection"/></xt:variable>
    <xt:value-of select="concat($id,'z',$zid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.paragraph.make-id" match="s:paragraph" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="pid"><xt:number count="s:paragraph"/></xt:variable>
    <xt:value-of select="concat($id,'q',$pid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.footnote.make-id" match="s:footnote" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="fid"><xt:number count="s:footnote"/></xt:variable>
    <xt:value-of select="concat($id,'f',$fid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.image.make-id" match="s:image" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="iid"><xt:number count="s:image"/></xt:variable>
    <xt:value-of select="concat($id,'i',$iid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.list-ordered.make-id" match="s:list-ordered" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="loid"><xt:number count="s:list-ordered"/></xt:variable>
    <xt:value-of select="concat($id,'lo',$loid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.match-list-unordered.make-id" match="s:list-unordered" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="luid"><xt:number count="s:list-unordered"/></xt:variable>
    <xt:value-of select="concat($id,'lu',$luid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.list-item.make-id" match="s:list-item" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="liid"><xt:number count="s:list-item"/></xt:variable>
    <xt:value-of select="concat($id,'li',$liid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.link-external.make-id" match="s:link-external" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="xid"><xt:number count="s:link-external"/></xt:variable>
    <xt:value-of select="concat($id,'x',$xid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.table.make-id" match="s:table" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="tid"><xt:number count="s:table"/></xt:variable>
    <xt:value-of select="concat($id,'t',$tid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.table-row.make-id" match="s:table-row" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="tid"><xt:number count="s:table-row"/></xt:variable>
    <xt:value-of select="concat($id,'r',$tid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.table-cell.make-id" match="s:table-cell" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="tid"><xt:number count="s:table-cell"/></xt:variable>
    <xt:value-of select="concat($id,'c',$tid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.match-formal-item.make-id" match="s:formal-item" mode="structural-1_0_0.make-id">
    <xt:variable name="id"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="gid"><xt:number count="s:formal-item"/></xt:variable>
    <xt:value-of select="concat($id,'g',$gid)"/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.any.make-id" match="*" mode="structural-1_0_0.make-id">
    <xt:apply-templates select="parent::*" mode="structural-1_0_0.make-id"/>
  </xt:template>

  <!--
    mode:make-id-anchor

    Make an HTML anchor element containing the result of make-id for
    the current element.
    -->

  <xt:template name="io7m.structural-1_0_0.make-id-anchor" match="*" mode="structural-1_0_0.make-id-anchor">
    <xt:element name="a">
      <xt:attribute name="class">structural_1_0_0_anchor</xt:attribute>
      <xt:attribute name="id">
        <xt:apply-templates select="." mode="structural-1_0_0.make-id"/>
      </xt:attribute>
    </xt:element>
  </xt:template>

</xt:stylesheet>
