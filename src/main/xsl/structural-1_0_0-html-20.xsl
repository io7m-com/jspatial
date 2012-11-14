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
    Create an HTML anchor linking to the current element, with the
    element's numbered title as the hyperlink text.
    -->

  <xt:template name="io7m.structural-1_0_0.html.titled-anchor">
    <xt:element name="a">
      <xt:attribute name="href">
        <xt:apply-templates select="." mode="structural-1_0_0.make-anchor-reference"/>
      </xt:attribute>
      <xt:apply-templates select="." mode="structural-1_0_0.make-title-numbered"/>
    </xt:element>
  </xt:template>

  <!--
    Iff the current element has an 'type' attribute, create
    an XHTML class attribute with the contents appended to
    the given string.
    -->

  <xt:template name="io7m.structural-1_0_0.html.class-from-type-attribute">
    <xt:param name="text"/>
    <xt:choose>
      <xt:when test="@type">
        <xt:attribute name="class">
          <xt:value-of select="concat($text,' ', @type)"/>
        </xt:attribute>
      </xt:when>
      <xt:otherwise>
        <xt:attribute name="class">
          <xt:value-of select="$text"/>
        </xt:attribute>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <!--
    Iff the current element has an xml:id attribute, translate
    it to an XHTML id attribute and prefix it to avoid name
    collisions with generated attributes.
    -->

  <xt:template name="io7m.structural-1_0_0.html.make-id-attribute">
    <xt:if test="@xml:id">
      <xt:attribute name="id">
        <xt:value-of select="concat('ref_',@xml:id)"/>
      </xt:attribute>
    </xt:if>
  </xt:template>

  <!--
    Process the standard attributes for elements.
    -->

  <xt:template name="io7m.structural-1_0_0.html.make-standard-attributes">
    <xt:param name="class"/>
    <xt:call-template name="io7m.structural-1_0_0.html.class-from-type-attribute">
      <xt:with-param name="text" select="$class"/>
    </xt:call-template>
  </xt:template>

  <!--
    mode:html-head
    -->

  <xt:template
    name="io7m.structural-1_0_0.html.document-style-html-head"
    match="s:document-style"
    mode="structural-1_0_0.html-head">
    <xt:element name="link">
      <xt:attribute name="rel">stylesheet</xt:attribute>
      <xt:attribute name="type">text/css</xt:attribute>
      <xt:attribute name="href"><xt:value-of select="."/></xt:attribute>
    </xt:element>
  </xt:template>

  <!--
    HTML document head
    -->

  <xt:template name="io7m.structural-1_0_0.html.html-head">
    <xt:param name="title"/>
    <xt:element name="head">
      <xt:element name="link">
        <xt:attribute name="rel">stylesheet</xt:attribute>
        <xt:attribute name="type">text/css</xt:attribute>
        <xt:attribute name="href">structural-1_0_0-layout.css</xt:attribute>
      </xt:element>
      <xt:element name="link">
        <xt:attribute name="rel">stylesheet</xt:attribute>
        <xt:attribute name="type">text/css</xt:attribute>
        <xt:attribute name="href">structural-1_0_0-colour.css</xt:attribute>
      </xt:element>
      <xt:apply-templates select="descendant::s:document/s:document-style" mode="structural-1_0_0.html-head"/>
      <xt:apply-templates select="s:document-style"                        mode="structural-1_0_0.html-head"/>
      <xt:apply-templates select="ancestor::s:document/s:document-style"   mode="structural-1_0_0.html-head"/>
      <xt:element name="title">
        <xt:value-of select="$title"/>
      </xt:element>
    </xt:element>
  </xt:template>

  <!--
    The HTML navigation bar shown on document pages.
    -->

  <xt:template name="io7m.structural-1_0_0.html.document-navbar">
    <xt:param name="file_next"/>
    <xt:param name="title_next"/>
    <xt:param name="at_top"/>

    <xt:choose>
      <xt:when test="$at_top = 1">
        <xt:element name="div">
          <xt:attribute name="class">structural_1_0_0_navbar structural_1_0_0_navbar_top</xt:attribute>
          <xt:element name="table">
            <xt:attribute name="summary">Navigation bar</xt:attribute>
            <xt:attribute name="class">structural_1_0_0_navbar_table</xt:attribute>
            <xt:element name="tr">
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_next_title_cell</xt:attribute>
                <xt:value-of select="$title_next"/>
              </xt:element>
            </xt:element>
            <xt:element name="tr">
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_next_file_cell</xt:attribute>
                <xt:element name="a">
                  <xt:attribute name="href"><xt:value-of select="$file_next"/></xt:attribute>
                  Next
                </xt:element>
              </xt:element>
            </xt:element>
          </xt:element>
          <xt:element name="hr">
            <xt:attribute name="class">structural_1_0_0_hr</xt:attribute>
          </xt:element>
        </xt:element>
      </xt:when>
      <xt:otherwise>
        <xt:element name="hr">
          <xt:attribute name="class">structural_1_0_0_hr</xt:attribute>
        </xt:element>
        <xt:element name="div">
          <xt:attribute name="class">structural_1_0_0_navbar structural_1_0_0_navbar_bottom</xt:attribute>
          <xt:element name="table">
            <xt:attribute name="summary">Navigation bar</xt:attribute>
            <xt:attribute name="class">structural_1_0_0_navbar_table</xt:attribute>
            <xt:element name="tr">
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_next_file_cell</xt:attribute>
                <xt:element name="a">
                  <xt:attribute name="href"><xt:value-of select="$file_next"/></xt:attribute>
                  Next
                </xt:element>
              </xt:element>
            </xt:element>
            <xt:element name="tr">
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_next_title_cell</xt:attribute>
                <xt:value-of select="$title_next"/>
              </xt:element>
            </xt:element>
          </xt:element>
        </xt:element>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <!--
    The standard navigation bar shown on each section/part page.
    -->

  <xt:template name="io7m.structural-1_0_0.html.section-or-part-navbar">
    <xt:param name="file_prev"/>
    <xt:param name="file_next"/>
    <xt:param name="file_up"/>
    <xt:param name="title_next"/>
    <xt:param name="title_prev"/>
    <xt:param name="title_up"/>
    <xt:param name="at_top"/>

    <xt:choose>
      <xt:when test="$at_top = 1">
        <xt:element name="div">
          <xt:attribute name="class">structural_1_0_0_navbar structural_1_0_0_navbar_top</xt:attribute>
          <xt:element name="table">
            <xt:attribute name="summary">Navigation bar</xt:attribute>
            <xt:attribute name="class">structural_1_0_0_navbar_table</xt:attribute>
            <xt:element name="tr">
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_prev_title_cell</xt:attribute>
                <xt:value-of select="$title_prev"/>
              </xt:element>
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_up_title_cell</xt:attribute>
                <xt:value-of select="$title_up"/>
              </xt:element>
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_next_title_cell</xt:attribute>
                <xt:if test="string-length($title_next) > 0">
                  <xt:value-of select="$title_next"/>
                </xt:if>
              </xt:element>
            </xt:element>
            <xt:element name="tr">
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_prev_file_cell</xt:attribute>
                <xt:element name="a">
                  <xt:attribute name="href"><xt:value-of select="$file_prev"/></xt:attribute>
                  Previous
                </xt:element>
              </xt:element>
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_up_file_cell</xt:attribute>
                <xt:element name="a">
                  <xt:attribute name="href"><xt:value-of select="$file_up"/></xt:attribute>
                  Up
                </xt:element>
              </xt:element>
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_next_file_cell</xt:attribute>
                <xt:if test="string-length($file_next)">
                  <xt:element name="a">
                    <xt:attribute name="href"><xt:value-of select="$file_next"/></xt:attribute>
                    Next
                  </xt:element>
                </xt:if>
              </xt:element>
            </xt:element>
          </xt:element>
          <xt:element name="hr">
            <xt:attribute name="class">structural_1_0_0_hr</xt:attribute>
          </xt:element>
        </xt:element>
      </xt:when>
      <xt:otherwise>
        <xt:element name="div">
          <xt:attribute name="class">structural_1_0_0_navbar structural_1_0_0_navbar_bottom</xt:attribute>
          <xt:element name="hr">
            <xt:attribute name="class">structural_1_0_0_hr</xt:attribute>
          </xt:element>
          <xt:element name="table">
            <xt:attribute name="summary">Navigation bar</xt:attribute>
            <xt:attribute name="class">structural_1_0_0_navbar_table</xt:attribute>
            <xt:element name="tr">
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_prev_file_cell</xt:attribute>
                <xt:element name="a">
                  <xt:attribute name="href"><xt:value-of select="$file_prev"/></xt:attribute>
                  Previous
                </xt:element>
              </xt:element>
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_up_file_cell</xt:attribute>
                <xt:element name="a">
                  <xt:attribute name="href"><xt:value-of select="$file_up"/></xt:attribute>
                  Up
                </xt:element>
              </xt:element>
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_next_file_cell</xt:attribute>
                <xt:if test="string-length($file_next) > 0">
                  <xt:element name="a">
                    <xt:attribute name="href"><xt:value-of select="$file_next"/></xt:attribute>
                    Next
                  </xt:element>
                </xt:if>
              </xt:element>
            </xt:element>
            <xt:element name="tr">
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_prev_title_cell</xt:attribute>
                <xt:value-of select="$title_prev"/>
              </xt:element>
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_up_title_cell</xt:attribute>
                <xt:value-of select="$title_up"/>
              </xt:element>
              <xt:element name="td">
                <xt:attribute name="class">structural_1_0_0_navbar_next_title_cell</xt:attribute>
                <xt:if test="string-length($title_next) > 0">
                  <xt:value-of select="$title_next"/>
                </xt:if>
              </xt:element>
            </xt:element>
          </xt:element>
        </xt:element>
      </xt:otherwise>
    </xt:choose>
  </xt:template>

  <!--
    mode:html-main

    These templates are responsible for creating output files and
    generating the main HTML structure for pages.
    -->

  <xt:template name="io7m.structural-1_0_0.html.section-or-part-body-contents">
    <xt:param name="class"/>

    <xt:variable name="file_next"><xt:apply-templates select="." mode="structural-1_0_0.file-next"/></xt:variable>
    <xt:variable name="file_prev"><xt:apply-templates select="." mode="structural-1_0_0.file-previous"/></xt:variable>
    <xt:variable name="file_up"><xt:apply-templates select="parent::*" mode="structural-1_0_0.make-file"/></xt:variable>
    <xt:variable name="title_next"><xt:apply-templates select="." mode="structural-1_0_0.title-next"/></xt:variable>
    <xt:variable name="title_prev"><xt:apply-templates select="." mode="structural-1_0_0.title-previous"/></xt:variable>
    <xt:variable name="title_up"><xt:apply-templates select="." mode="structural-1_0_0.title-up"/></xt:variable>

    <xt:element name="div">
      <xt:attribute name="class">structural_1_0_0_body</xt:attribute>
      <xt:call-template name="io7m.structural-1_0_0.html.make-id-attribute"/>
      <xt:call-template name="io7m.structural-1_0_0.html.section-or-part-navbar">
        <xt:with-param name="at_top"     select="1"/>
        <xt:with-param name="file_next"  select="$file_next"/>
        <xt:with-param name="file_prev"  select="$file_prev"/>
        <xt:with-param name="file_up"    select="$file_up"/>
        <xt:with-param name="title_next" select="$title_next"/>
        <xt:with-param name="title_prev" select="$title_prev"/>
        <xt:with-param name="title_up"   select="$title_up"/>
      </xt:call-template>
      <xt:element name="div">
        <xt:attribute name="class"><xt:value-of select="$class"/></xt:attribute>
        <xt:apply-templates select="." mode="structural-1_0_0.make-id-anchor"/>
        <xt:apply-templates mode="structural-1_0_0.rendering"/>
      </xt:element>
      <xt:apply-templates select="." mode="structural-1_0_0.footnotes"/>
      <xt:call-template name="io7m.structural-1_0_0.html.section-or-part-navbar">
        <xt:with-param name="at_top"     select="0"/>
        <xt:with-param name="file_next"  select="$file_next"/>
        <xt:with-param name="file_prev"  select="$file_prev"/>
        <xt:with-param name="file_up"    select="$file_up"/>
        <xt:with-param name="title_next" select="$title_next"/>
        <xt:with-param name="title_prev" select="$title_prev"/>
        <xt:with-param name="title_up"   select="$title_up"/>
      </xt:call-template>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.html.document-body-contents">
    <xt:variable name="file_next"><xt:apply-templates  select="." mode="structural-1_0_0.file-next"/></xt:variable>
    <xt:variable name="title_next"><xt:apply-templates select="." mode="structural-1_0_0.title-next"/></xt:variable>
    <xt:element name="div">
      <xt:attribute name="class">structural_1_0_0_body</xt:attribute>
      <xt:call-template name="io7m.structural-1_0_0.html.document-navbar">
        <xt:with-param name="at_top"     select="1"/>
        <xt:with-param name="file_next"  select="$file_next"/>
        <xt:with-param name="title_next" select="$title_next"/>
      </xt:call-template>
      <xt:apply-templates mode="structural-1_0_0.rendering"/>
      <xt:call-template name="io7m.structural-1_0_0.html.document-navbar">
        <xt:with-param name="at_top"     select="0"/>
        <xt:with-param name="file_next"  select="$file_next"/>
        <xt:with-param name="title_next" select="$title_next"/>
      </xt:call-template>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.html.section-or-part-html" match="s:section | s:part" mode="structural-1_0_0.html-main">
    <xt:element name="html">
      <xt:call-template name="io7m.structural-1_0_0.html.html-head">
        <xt:with-param name="title">
          <xt:apply-templates select="." mode="structural-1_0_0.make-title-numbered"/>
        </xt:with-param>
      </xt:call-template>
      <xt:element name="body">
        <xt:choose>
          <xt:when test="local-name(.) = 'section'">
            <xt:call-template name="io7m.structural-1_0_0.html.section-or-part-body-contents">
              <xt:with-param name="class">structural_1_0_0_section_container</xt:with-param>
            </xt:call-template>
          </xt:when>
          <xt:when test="local-name(.) = 'part'">
            <xt:call-template name="io7m.structural-1_0_0.html.section-or-part-body-contents">
              <xt:with-param name="class">structural_1_0_0_part_container</xt:with-param>
            </xt:call-template>
          </xt:when>
        </xt:choose>
      </xt:element>
    </xt:element>
  </xt:template>

  <xt:template name="io7m.structural-1_0_0.html.document-html" match="s:document" mode="structural-1_0_0.html-main">
    <xt:element name="html">
      <xt:call-template name="io7m.structural-1_0_0.html.html-head">
        <xt:with-param name="title">
          <xt:value-of select="s:document-title"/>
        </xt:with-param>
      </xt:call-template>
      <xt:element name="body">
        <xt:call-template name="io7m.structural-1_0_0.html.document-body-contents"/>
      </xt:element>
    </xt:element>
  </xt:template>

</xt:stylesheet>
