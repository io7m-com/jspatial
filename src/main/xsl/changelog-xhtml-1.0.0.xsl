<?xml version="1.0" encoding="UTF-8"?>

<!--
  Copyright © 2012 http://io7m.com

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

<xt:stylesheet
  version="2.0"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xt="http://www.w3.org/1999/XSL/Transform"
  xmlns:c="http://www.io7m.com/schemas/changelog/1.0.0"
  exclude-result-prefixes="c">

  <xt:output
    method="xml"
    encoding="UTF-8"
    indent="yes"
    omit-xml-declaration="yes" />

  <xt:template
    match="c:date"
    mode="io7m.changelog.primitives">
    <td class="page_table_column">
      <xt:value-of select="." />
    </td>
  </xt:template>

  <xt:template
    match="c:type-code-new"
    mode="io7m.changelog.primitives">
    <xt:value-of select="'Code new: '" />
  </xt:template>

  <xt:template
    match="c:type-code-change"
    mode="io7m.changelog.primitives">
    <xt:value-of select="'Code change: '" />
  </xt:template>

  <xt:template
    match="c:type-code-fix"
    mode="io7m.changelog.primitives">
    <xt:value-of select="'Code fix: '" />
  </xt:template>

  <xt:template
    match="c:type-platform-new"
    mode="io7m.changelog.primitives">
    <xt:value-of select="'Platform new: '" />
  </xt:template>

  <xt:template
    match="c:summary"
    mode="io7m.changelog.primitives">
    <xt:value-of select="normalize-space(.)" />
  </xt:template>

  <xt:template
    match="c:ticket"
    mode="io7m.changelog.primitives">
    <xt:variable
      name="ticket_system"
      select="ancestor::c:release/@ticket-system" />
    <xt:value-of select="' (ticket '" />
    <a>
      <xt:attribute name="href">
        <xt:value-of select="concat(id($ticket_system)/c:ticket-url,.)" />
      </xt:attribute>
      <xt:value-of select="." />
    </a>
    <xt:value-of select="')'" />
  </xt:template>

  <xt:template
    match="c:item"
    mode="io7m.changelog.primitives">
    <tr class="page_table_row">
      <xt:apply-templates
        select="c:date"
        mode="io7m.changelog.primitives" />
      <td class="page_table_column">
        <xt:apply-templates
          select="c:type-code-new | c:type-code-change | c:type-code-fix | c:type-platform-new"
          mode="io7m.changelog.primitives" />
        <xt:apply-templates
          select="c:summary"
          mode="io7m.changelog.primitives" />
        <xt:apply-templates
          select="c:ticket"
          mode="io7m.changelog.primitives" />
      </td>
    </tr>
  </xt:template>

  <xt:template
    match="c:release"
    mode="io7m.changelog.primitives">
    <tr class="page_table_row">
      <xt:apply-templates select="c:date" mode="io7m.changelog.primitives" />
      <td class="page_table_column">
        <xt:value-of select="concat('Release: ', ancestor::c:changelog/c:project, ' ', c:version)" />
      </td>
    </tr>
    <xt:apply-templates select="c:item" mode="io7m.changelog.primitives" />
  </xt:template>

  <xt:template
    match="c:changelog"
    mode="io7m.changelog.primitives">
    <table class="changelog page_table" summary="Changes">
      <xt:apply-templates select="c:release" mode="io7m.changelog.primitives" />
    </table>
  </xt:template>

  <xt:template match="c:changelog">
    <xt:apply-templates select="." mode="io7m.changelog.primitives" />
  </xt:template>

</xt:stylesheet>
