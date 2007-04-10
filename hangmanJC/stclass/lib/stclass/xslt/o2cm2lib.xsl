<?xml version="1.0" encoding='ISO-8859-1' standalone='yes' ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" encoding="ISO-8859-1" omit-xml-declaration="yes"/>
    
    	<xsl:template match="*">
    		<xsl:copy><xsl:copy-of select="@*"/><xsl:apply-templates/></xsl:copy>
	</xsl:template>

	<xsl:template match="Ensure" />
	
	<xsl:template match="Invariant">
	<xsl:copy>
	<Invar />
	</xsl:copy>
	</xsl:template>
	
	<xsl:template match="Invariant/*" />
    
	<xsl:template match="Test" />
	
</xsl:stylesheet>