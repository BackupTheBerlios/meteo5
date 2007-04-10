<?xml version="1.0" encoding='ISO-8859-1' standalone='yes' ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" encoding="ISO-8859-1" omit-xml-declaration="no"/>

<!-- OUTPUT A XML DOCUMENT -->


<!-- *********************************************************************** -->
<xsl:variable name="cr">
  <xsl:text>
</xsl:text>
</xsl:variable>

<xsl:variable name="tab">
	<xsl:text>   </xsl:text>
</xsl:variable>

<!-- *********************************************************************** -->
<xsl:template match="*">
	
	<xsl:param name="indent" select="''" />
	
	<xsl:value-of select="$indent" />
	<xsl:copy>

		<!-- Attributes -->
		<xsl:for-each select="@*">
			<xsl:copy />
		</xsl:for-each>

		<!-- Text -->
		<xsl:value-of select="text()" />
		
		<xsl:if test="boolean(child::*)">
			<xsl:value-of select="$cr" />
		</xsl:if>
	
		<!-- Child(s) -->
		<xsl:apply-templates select="*">
			<xsl:with-param name="indent" select="concat($tab,$indent)" />
		</xsl:apply-templates>

		<xsl:if test="boolean(child::*)">
			<xsl:value-of select="$indent" />
		</xsl:if>

	</xsl:copy>
	<xsl:value-of select="$cr" />
	
</xsl:template>

</xsl:stylesheet>