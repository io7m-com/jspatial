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

  <xt:template match="s:document" mode="structural-1_0_0.rendering"/>

  <xt:template name="io7m.structural-1_0_0.document-contents" match="s:document-contents" mode="structural-1_0_0.rendering">
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_contents structural_1_0_0_document_contents</xt:with-param>
      </xt:call-template>
      <xt:apply-templates select="parent::s:document" mode="structural-1_0_0.document-contents"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.document-style" match="s:document-style" mode="structural-1_0_0.rendering"/>

  <xt:template name="io7m.structural-1_0_0.document-title" match="s:document-title" mode="structural-1_0_0.rendering">
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_document_title</xt:with-param>
      </xt:call-template>
      <xt:value-of select="."/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.footnote" match="s:footnote" mode="structural-1_0_0.rendering">
    <xt:variable name="id"><xt:apply-templates select="." mode="structural-1_0_0.make-id"/></xt:variable>
    <xt:variable name="footnote_index">
      <xt:call-template name="io7m.structural-1_0_0.footnote-count">
        <xt:with-param name="node_current" select="."/>
        <xt:with-param name="node_end"     select="ancestor::s:document[1]"/>
        <xt:with-param name="accumulator"  select="0"/>
      </xt:call-template>
    </xt:variable>
    <xt:text>[</xt:text>
    <xt:element name="a">
      <xt:attribute name="id">
        <xt:value-of select="concat($id,'_ref')"/>
      </xt:attribute>
      <xt:attribute name="href">
        <xt:value-of select="concat('#',$id)"/>
      </xt:attribute>
      <xt:value-of select="$footnote_index"/>
    </xt:element>
    <xt:text>]</xt:text>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.formal-item" match="s:formal-item" mode="structural-1_0_0.rendering">
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_formal_item</xt:with-param>
      </xt:call-template>
      <xt:element name="a">
        <xt:attribute name="id">
          <xt:apply-templates select="." mode="structural-1_0_0.make-id"/>
        </xt:attribute>
      </xt:element>
      <xt:element name="div">
        <xt:attribute name="class">structural_1_0_0_formal_item_title</xt:attribute>
        <xt:apply-templates select="." mode="structural-1_0_0.make-title-numbered"/>
      </xt:element>
      <xt:apply-templates select="s:formal-item | s:formal-item-list | s:image | s:list-ordered | s:list-unordered | s:table | s:verbatim" mode="structural-1_0_0.rendering"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.formal-item.formal-item-list" match="s:formal-item" mode="structural-1_0_0.formal-item-list">
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_formal_item_list_item</xt:with-param>
      </xt:call-template>
      <xt:call-template name="io7m.structural-1_0_0.html.titled-anchor"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.formal-item-list" match="s:formal-item-list" mode="structural-1_0_0.rendering">
    <xt:variable name="want_kind" select="@kind"/>
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_formal_item_list</xt:with-param>
      </xt:call-template>
      <xt:element name="div">
        <xt:attribute name="class">structural_1_0_0_formal_item_list_title</xt:attribute>
        <xt:value-of select="concat('List of ',@kind)"/>
      </xt:element>
      <xt:apply-templates
        select="ancestor::s:document//s:formal-item[@kind = $want_kind]"
        mode="structural-1_0_0.formal-item-list"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.formal-item-title" match="s:formal-item-title" mode="structural-1_0_0.rendering"/>

  <xt:template name="io7m.structural-1_0_0.image" match="s:image" mode="structural-1_0_0.rendering">
    <xt:element name="img">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_image</xt:with-param>
      </xt:call-template>
      <xt:attribute name="id">
        <xt:apply-templates select="." mode="structural-1_0_0.make-id"/>
      </xt:attribute>
      <xt:attribute name="src">
        <xt:value-of select="@source"/>
      </xt:attribute>
      <xt:attribute name="alt">
        <xt:value-of select="."/>
      </xt:attribute>
      <xt:if test="@width">
        <xt:attribute name="width">
          <xt:value-of select="@width"/>
        </xt:attribute>
      </xt:if>
      <xt:if test="@height">
        <xt:attribute name="height">
          <xt:value-of select="@height"/>
        </xt:attribute>
      </xt:if>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.link" match="s:link" mode="structural-1_0_0.rendering">
    <xt:element name="a">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_link</xt:with-param>
      </xt:call-template>
      <xt:attribute name="href">
        <xt:apply-templates select="id (@target)" mode="structural-1_0_0.make-anchor-reference"/>
      </xt:attribute>
      <xt:apply-templates select="s:image | text()" mode="structural-1_0_0.rendering"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.link-external" match="s:link-external" mode="structural-1_0_0.rendering">
    <xt:element name="a">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_link_external</xt:with-param>
      </xt:call-template>
      <xt:attribute name="id">
        <xt:apply-templates select="." mode="structural-1_0_0.make-id"/>
      </xt:attribute>
      <xt:attribute name="href">
        <xt:value-of select="@target"/>
      </xt:attribute>
      <xt:apply-templates select="s:image | text()" mode="structural-1_0_0.rendering"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.list-item" match="s:list-item" mode="structural-1_0_0.rendering">
    <xt:element name="li">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_list_item</xt:with-param>
      </xt:call-template>
      <xt:apply-templates mode="structural-1_0_0.rendering"/>
    </xt:element>
  </xt:template>

  <xt:template match="s:list-item/text()" name="io7m.structural-1_0_0.list-item-text" mode="structural-1_0_0.rendering">
    <xt:value-of select="."/>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.list-ordered" match="s:list-ordered" mode="structural-1_0_0.rendering">
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_list_ordered</xt:with-param>
      </xt:call-template>
      <xt:element name="ol">
        <xt:apply-templates mode="structural-1_0_0.rendering"/>
      </xt:element>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.list-unordered" match="s:list-unordered" mode="structural-1_0_0.rendering">
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_list_unordered</xt:with-param>
      </xt:call-template>
      <xt:element name="ul">
        <xt:apply-templates mode="structural-1_0_0.rendering"/>
      </xt:element>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.paragraph" match="s:paragraph" mode="structural-1_0_0.rendering">
    <xt:variable name="id">
      <xt:apply-templates select="." mode="structural-1_0_0.make-id"/>
    </xt:variable>
    <xt:variable name="number">
      <xt:number count="s:paragraph"/>
    </xt:variable>

    <xt:element name="div">
      <xt:attribute name="class">structural_1_0_0_paragraph_container</xt:attribute>
      <xt:element name="div">
        <xt:call-template name="io7m.structural-1_0_0.html.make-id-attribute"/>
        <xt:attribute name="class">structural_1_0_0_paragraph_number</xt:attribute>
        <xt:element name="a">
          <xt:attribute name="id"><xt:value-of select="$id"/></xt:attribute>
          <xt:attribute name="href"><xt:value-of select="concat('#',$id)"/></xt:attribute>
          <xt:value-of select="$number"/>
        </xt:element>
      </xt:element>
      <xt:element name="div">
        <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
          <xt:with-param name="class">structural_1_0_0_paragraph</xt:with-param>
        </xt:call-template>
        <xt:apply-templates mode="structural-1_0_0.rendering"/>
      </xt:element>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.paragraph-text" match="s:paragraph/text()" mode="structural-1_0_0.rendering">
    <xt:value-of select="."/>
  </xt:template>

  <xt:template match="s:part" mode="structural-1_0_0.rendering"/>

  <xt:template match="s:part-contents" name="io7m.structural-1_0_0.part-contents" mode="structural-1_0_0.rendering">
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_contents structural_1_0_0_part_contents</xt:with-param>
      </xt:call-template>
      <xt:apply-templates select="parent::s:part" mode="structural-1_0_0.part-contents"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.part-title" match="s:part-title" mode="structural-1_0_0.rendering">
    <xt:element name="div">
      <xt:attribute name="class">structural_1_0_0_part_title_number</xt:attribute>
      <xt:element name="a">
        <xt:attribute name="href">
          <xt:apply-templates select="." mode="structural-1_0_0.make-anchor-reference"/>
        </xt:attribute>
        <xt:apply-templates select="." mode="structural-1_0_0.make-title-number"/>
      </xt:element>
    </xt:element>
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_part_title</xt:with-param>
      </xt:call-template>
      <xt:value-of select="."/>
    </xt:element>
  </xt:template>

  <xt:template match="s:section" mode="structural-1_0_0.rendering"/>

  <xt:template name="io7m.structural-1_0_0.section-contents" match="s:section-contents" mode="structural-1_0_0.rendering">
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_contents structural_1_0_0_section_contents</xt:with-param>
      </xt:call-template>
      <xt:apply-templates select="parent::s:section" mode="structural-1_0_0.section-contents"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.section-title" match="s:section-title" mode="structural-1_0_0.rendering">
    <xt:element name="div">
      <xt:attribute name="class">structural_1_0_0_section_title_number</xt:attribute>
      <xt:element name="a">
        <xt:attribute name="href">
          <xt:apply-templates select="." mode="structural-1_0_0.make-anchor-reference"/>
        </xt:attribute>
        <xt:apply-templates select="." mode="structural-1_0_0.make-title-number"/>
      </xt:element>
    </xt:element>
    <xt:element name="div">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_section_title</xt:with-param>
      </xt:call-template>
      <xt:value-of select="."/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.subsection" match="s:subsection" mode="structural-1_0_0.rendering">
    <xt:element name="div">
      <xt:attribute name="class">structural_1_0_0_subsection_container</xt:attribute>
      <xt:element name="a">
        <xt:attribute name="id">
          <xt:apply-templates select="." mode="structural-1_0_0.make-id"/>
        </xt:attribute>
      </xt:element>
      <xt:element name="div">
        <xt:attribute name="class">structural_1_0_0_subsection_title_number</xt:attribute>
        <xt:element name="a">
          <xt:attribute name="href">
            <xt:apply-templates select="." mode="structural-1_0_0.make-anchor-reference"/>
          </xt:attribute>
          <xt:apply-templates select="s:subsection-title" mode="structural-1_0_0.make-title-number"/>
        </xt:element>
      </xt:element>
      <xt:element name="div">
        <xt:attribute name="class">structural_1_0_0_subsection_title</xt:attribute>
        <xt:value-of select="s:subsection-title"/>
      </xt:element>
      <xt:element name="div">
        <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
          <xt:with-param name="class">structural_1_0_0_subsection</xt:with-param>
        </xt:call-template>
        <xt:call-template name="io7m.structural-1_0_0.html.make-id-attribute"/>
        <xt:apply-templates select="s:paragraph | s:formal-item" mode="structural-1_0_0.rendering"/>
      </xt:element>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.table-column-name" match="s:table-column-name" mode="structural-1_0_0.rendering">
    <xt:element name="th">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_table_column_name</xt:with-param>
      </xt:call-template>
      <xt:value-of select="."/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.table-head" match="s:table-head" mode="structural-1_0_0.rendering">
    <xt:element name="thead">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_table_head</xt:with-param>
      </xt:call-template>
      <xt:element name="tr">
        <xt:apply-templates select="s:table-column-name" mode="structural-1_0_0.rendering"/>
      </xt:element>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.table-cell" match="s:table-cell" mode="structural-1_0_0.rendering">
    <xt:element name="td">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_table_cell</xt:with-param>
      </xt:call-template>
      <xt:apply-templates select="s:footnote | s:image | s:link | s:link-external | s:list-ordered | s:list-unordered | s:term | s:verbatim | text()" mode="structural-1_0_0.rendering"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.table-row" match="s:table-row" mode="structural-1_0_0.rendering">
    <xt:element name="tr">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_table_row</xt:with-param>
      </xt:call-template>
      <xt:apply-templates select="s:table-cell" mode="structural-1_0_0.rendering"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.table-body" match="s:table-body" mode="structural-1_0_0.rendering">
    <xt:element name="tbody">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_table_body</xt:with-param>
      </xt:call-template>
      <xt:apply-templates select="s:table-row" mode="structural-1_0_0.rendering"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.table" match="s:table" mode="structural-1_0_0.rendering">
    <xt:element name="table">
      <xt:attribute name="id">
        <xt:apply-templates select="." mode="structural-1_0_0.make-id"/>
      </xt:attribute>
      <xt:attribute name="summary"><xt:value-of select="s:table-summary"/></xt:attribute>
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_table</xt:with-param>
      </xt:call-template>
      <xt:apply-templates select="s:table-head" mode="structural-1_0_0.rendering"/>
      <xt:apply-templates select="s:table-body" mode="structural-1_0_0.rendering"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.term" match="s:term" mode="structural-1_0_0.rendering">
    <xt:element name="span">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_term</xt:with-param>
      </xt:call-template>
      <xt:value-of select="."/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.verbatim" match="s:verbatim" mode="structural-1_0_0.rendering">
    <xt:element name="pre">
      <xt:call-template name="io7m.structural-1_0_0.html.make-standard-attributes">
        <xt:with-param name="class">structural_1_0_0_verbatim</xt:with-param>
      </xt:call-template>
      <xt:apply-templates mode="structural-1_0_0.rendering"/>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.verbatim-text" match="s:verbatim/text()" mode="structural-1_0_0.rendering">
    <xt:value-of select="."/>
  </xt:template>

</xt:stylesheet>
