<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ui="https://github.com/bordertech/wcomponents/namespace/ui/v1.0" xmlns:html="http://www.w3.org/1999/xhtml" version="1.0">
	<!--
		Called from the transform for ui:subTr/ui:content. The call passes parameters for certain calculations so that 
		they do not have to be re-done in the helper.
		
		param myTable: the nearest ancestor ui:table element
		param parentIsClosed: Integer: 1 if the row parent is in a closed sub-row otherwise 0.
		param topRowIsStriped: Integer: 1 if the top level row is striped otherwise 0.
	-->
	<xsl:template name="WTableAdditionalContentClass" />
</xsl:stylesheet>
