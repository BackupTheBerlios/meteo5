<?xml version="1.0" encoding='ISO-8859-1' standalone='yes' ?>

<!-- G�n�ration de documentation d'un source xsl-->
<!-- ********************************** -->
<!-- Auteur	: Guillaume Falcini	-->
<!-- Cr�ation   : 02/08/03		-->
<!-- Maj	: 02/08/03		-->
<!-- ********************************** -->


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text" encoding="ISO-8859-1" omit-xml-declaration="yes"/>

<!-- ************************************************************************************************** -->
<!-- Global variable Declaration -->

<xsl:variable name="cr">
<xsl:text>
</xsl:text>
</xsl:variable>

<xsl:variable name="incIndent" select="'2'" />


<!-- ************************************************************************************************** -->
<xsl:template match="/">

	<xsl:element name="HTML">
		<xsl:element name="HEAD">
			<xsl:element name="TITLE">
				<xsl:text>Fichier de transformation XSL </xsl:text>
			</xsl:element>
		</xsl:element>
		<xsl:element name="BODY">
			<xsl:value-of select="$cr" />
			<xsl:value-of select="$cr" />

			<xsl:apply-templates select="xsl:stylesheet" />
		</xsl:element>
	</xsl:element>


</xsl:template>


<!-- ************************************************************************************************** -->
<xsl:template match="xsl:stylesheet">

	<xsl:text>Variables : </xsl:text>
	<xsl:element name="BR" />
	<xsl:value-of select="$cr" />
	<xsl:for-each select="xsl:variable">
		<xsl:apply-templates select=".">
			<xsl:with-param name="indent" select="$incIndent" />
		</xsl:apply-templates>
		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />
	</xsl:for-each>
	<xsl:value-of select="$cr" />
	<xsl:apply-templates select="xsl:template">
		<xsl:with-param name="indent" select="$incIndent" />
	</xsl:apply-templates>


</xsl:template>


<!-- ************************************************************************************************** -->
<xsl:template match="xsl:template">

	<xsl:param name="indent" />


	<xsl:call-template name="hrefTemplates2">
		<xsl:with-param name="match" select="attribute::match" />
		<xsl:with-param name="mode" select="attribute::mode" />
		<xsl:with-param name="name" select="attribute::name" />
	</xsl:call-template>

	<xsl:element name="FONT">
		<xsl:attribute name="SIZE"><xsl:text>+3</xsl:text></xsl:attribute>

		<xsl:text>Template </xsl:text>
		<xsl:value-of select="attribute::name" />
		<xsl:text>, match </xsl:text>
		<xsl:value-of select="attribute::match" />
		<xsl:if test="boolean(attribute::mode)">
			<xsl:text> (</xsl:text>
			<xsl:value-of select="attribute::mode" />
			<xsl:text>)</xsl:text>
		</xsl:if>
	</xsl:element>
	<xsl:value-of select="$cr" />

	<xsl:element name="BR"/>

	<xsl:apply-templates select="comment()[position() = 1]">
		<xsl:with-param name="indent" select="$indent + $incIndent" />
	</xsl:apply-templates>

	<xsl:element name="BR"/>
	<xsl:element name="BR"/>

	<xsl:element name="B">
		<xsl:text>Param�tres : </xsl:text>
		<xsl:element name="BR" />

		<xsl:apply-templates select="xsl:param">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>

		<xsl:element name="BR" />

		<xsl:text>Variables : </xsl:text>
		<xsl:element name="BR" />

		<xsl:apply-templates select="xsl:variable">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>

		<xsl:element name="BR" />

		<xsl:text>Code : </xsl:text>
		<xsl:element name="BR" />
	
		<xsl:apply-templates select="xsl:if|xsl:choose|xsl:for-each|xsl:apply-templates|xsl:call-template|xsl:text|xsl:value-of|comment()[position() != 1]">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>
	

	<xsl:element name="HR" />
	<xsl:element name="BR" />
	<xsl:element name="BR" />

</xsl:template>


<!-- ************************************************************************************************** -->
<xsl:template match="xsl:apply-templates">

	<xsl:param name="indent" />
	
	<xsl:variable name="couleur" select="'#1080C0'" />

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:value-of select="$couleur" /></xsl:attribute>
	
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>

		<xsl:text>Apply-templates : </xsl:text>

		<xsl:call-template name="hrefTemplates">
			<xsl:with-param name="match" select="attribute::select" />
			<xsl:with-param name="mode" select="attribute::mode" />
			<xsl:with-param name="couleur" select="$couleur" />
		</xsl:call-template>
	
		<xsl:if test="boolean(attribute::mode)">
			<xsl:text> / </xsl:text>
			<xsl:value-of select="attribute::mode" />
		</xsl:if>
		<xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;</xsl:text>





		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />

		<xsl:apply-templates select="xsl:with-param">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>


</xsl:template>

<!-- ************************************************************************************************** -->
<xsl:template match="xsl:call-template">

	<xsl:param name="indent" />

	<xsl:variable name="couleur" select="'#1010C0'" />

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:value-of select="$couleur" /></xsl:attribute>

		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>
		<xsl:text>Call-template : </xsl:text>

		<xsl:call-template name="hrefTemplates">
			<xsl:with-param name="match" select="attribute::name" />
			<xsl:with-param name="mode" select="attribute::mode" />
			<xsl:with-param name="couleur" select="$couleur" />
		</xsl:call-template>

		<xsl:if test="boolean(attribute::mode)">
			<xsl:text> / </xsl:text>
			<xsl:value-of select="attribute::mode" />
		</xsl:if>
		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />

		<xsl:apply-templates select="xsl:with-param">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>

</xsl:template>

<!-- ************************************************************************************************** -->
<xsl:template match="xsl:with-param">

	<xsl:param name="indent" />

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>#107010</xsl:text></xsl:attribute>
	
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>
		<xsl:value-of select="attribute::name" />
		<xsl:text> : </xsl:text>
		<xsl:value-of select="attribute::select" />
		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />

		<xsl:apply-templates select="xsl:call-template|xsl:apply-templates|xsl:text|xsl:value-of|xsl:if|xsl:choose|xsl:for-each|xsl:variable">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>

</xsl:template>

<!-- ************************************************************************************************** -->
<xsl:template match="xsl:param">

	<xsl:param name="indent" />

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>#05D060</xsl:text></xsl:attribute>
	
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>
		<xsl:value-of select="attribute::name" />
		<xsl:text> : </xsl:text>
		<xsl:value-of select="attribute::select" />

		<xsl:apply-templates select="comment()" mode="paramVariable"/>

		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />

		<xsl:apply-templates select="xsl:call-template|xsl:apply-templates|xsl:text|xsl:value-of|xsl:if|xsl:choose|xsl:for-each|xsl:variable">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>

</xsl:template>

<!-- ************************************************************************************************** -->
<xsl:template match="xsl:text">

	<xsl:param name="indent" />

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>#505050</xsl:text></xsl:attribute>

		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>
		<xsl:text>'</xsl:text>
		<xsl:value-of select="." />
		<xsl:text>'</xsl:text>
		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />
	</xsl:element>

</xsl:template>

<!-- ************************************************************************************************** -->
<xsl:template match="xsl:value-of">

	<xsl:param name="indent" />

	<xsl:apply-templates select="comment()">
		<xsl:with-param name="indent" select="$indent" />
	</xsl:apply-templates>

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>#808080</xsl:text></xsl:attribute>
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>
		<xsl:text>&lt;</xsl:text>
		
		<xsl:value-of select="attribute::select" />
	
		<xsl:text>&gt;</xsl:text>
		<xsl:element name="BR" />
		<xsl:if test="@select = '$cr'">
			<xsl:element name="BR" />
		</xsl:if>
		<xsl:value-of select="$cr" />
	</xsl:element>

</xsl:template>


<!-- ************************************************************************************************** -->
<xsl:template match="xsl:variable">

	<xsl:param name="indent" />

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>#05D060</xsl:text></xsl:attribute>
	
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>
		<xsl:value-of select="attribute::name" />
		<xsl:text> : </xsl:text>
		<xsl:value-of select="attribute::select" />

		<xsl:apply-templates select="comment()" mode="paramVariable"/>

		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />

		<xsl:apply-templates select="xsl:call-template|xsl:apply-templates|xsl:text|xsl:value-of|xsl:if|xsl:choose|xsl:for-each">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>

</xsl:template>


<!-- ************************************************************************************************** -->
<xsl:template match="xsl:if">

	<xsl:param name="indent" />

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>#A09000</xsl:text></xsl:attribute>
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>
	
		<xsl:text>if (</xsl:text>
		<xsl:value-of select="attribute::test" />
		<xsl:text>)</xsl:text>
		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />

		<xsl:apply-templates select="xsl:call-template|xsl:apply-templates|xsl:text|xsl:value-of|xsl:if|xsl:choose|xsl:for-each|xsl:variable">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>

</xsl:template>


<!-- ************************************************************************************************** -->
<xsl:template match="xsl:for-each">

	<xsl:param name="indent" />

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>#FF7010</xsl:text></xsl:attribute>
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>
	
		<xsl:text>for-each (</xsl:text>
		<xsl:value-of select="attribute::select" />
		<xsl:text>)</xsl:text>
		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />

		<xsl:apply-templates select="xsl:call-template|xsl:apply-templates|xsl:text|xsl:value-of|xsl:if|xsl:choose|xsl:for-each|xsl:variable">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>

</xsl:template>


<!-- ************************************************************************************************* -->
<xsl:template match="xsl:choose">

	<xsl:param name="indent" />

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>#800060</xsl:text></xsl:attribute>
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>

		<xsl:text>switch :</xsl:text>
		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />
		<xsl:apply-templates select="xsl:when|xsl:otherwise">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>

</xsl:template>


<!-- ************************************************************************************************* -->
<xsl:template match="xsl:when">

	<xsl:param name="indent" />

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>#D00505</xsl:text></xsl:attribute>
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>

		<xsl:text>case (</xsl:text>
		<xsl:value-of select="attribute::test" />
		<xsl:text>) :</xsl:text>
		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />

		<xsl:apply-templates select="xsl:call-template|xsl:apply-templates|xsl:text|xsl:value-of|xsl:if|xsl:for-each|xsl:variable|xsl:choose">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>

</xsl:template>


<!-- ************************************************************************************************* -->
<xsl:template match="xsl:otherwise">

	<xsl:param name="indent" />
	
	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>#E03060</xsl:text></xsl:attribute>
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
		</xsl:call-template>

		<xsl:text>otherwise : </xsl:text>
		<xsl:element name="BR" />
		<xsl:value-of select="$cr" />

		<xsl:apply-templates select="xsl:call-template|xsl:apply-templates|xsl:text|xsl:value-of|xsl:if|xsl:for-each|xsl:variable|xsl:choose">
			<xsl:with-param name="indent" select="$indent + $incIndent" />
		</xsl:apply-templates>
	</xsl:element>


</xsl:template>


<!-- ************************************************************************************************ -->
<xsl:template match="comment()">

	<xsl:param name="indent" />

	<xsl:element name="BR" />
	<xsl:call-template name="indent">
		<xsl:with-param name="indent" select="$indent" />
	</xsl:call-template>

	<xsl:apply-templates select="." mode="paramVariable" />

	<xsl:element name="BR" />
	<xsl:value-of select="$cr" />


</xsl:template>


<!-- ************************************************************************************************** -->
<xsl:template match="comment()" mode="paramVariable">

	<xsl:element name="FONT">
		<xsl:attribute name="COLOR"><xsl:text>black</xsl:text></xsl:attribute>
		<xsl:element name="I">
			<xsl:value-of select="string(.)" />
		</xsl:element>
	</xsl:element>

</xsl:template>


<!-- ************************************************************************************************** -->
<xsl:template match="*">

	<xsl:value-of select="concat('autre template : ',name(),$cr)" />

</xsl:template>


<!-- ************************************************************************************************* -->
<xsl:template name="hrefTemplates">

	<xsl:param name="match" />
	<xsl:param name="mode" />
	<xsl:param name="couleur" />

	<xsl:variable name="newMatch">
		<xsl:choose>
			<xsl:when test="contains($match, '|')">
				<xsl:value-of select="substring-before($match,'|')"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$match" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:element name="A">
		<xsl:attribute name="HREF">
			<xsl:text>#</xsl:text>
			<xsl:call-template name="nameHREF">
				<xsl:with-param name="match" select="$newMatch" />
			</xsl:call-template>
			<xsl:value-of select="$mode" />
		</xsl:attribute>
		<xsl:element name="FONT">
			<xsl:attribute name="COLOR"><xsl:value-of select="$couleur" /></xsl:attribute>
			<xsl:value-of select="$newMatch" />
		</xsl:element>
	</xsl:element>


	<xsl:if test="contains($match, '|')">
		<xsl:text>, </xsl:text>
		<xsl:call-template name="hrefTemplates">
			<xsl:with-param name="match" select="substring-after($match,'|')" />
			<xsl:with-param name="mode" select="$mode" />
		</xsl:call-template>
	</xsl:if>
	

</xsl:template>


<!-- ************************************************************************************************* -->
<xsl:template name="hrefTemplates2">

	<xsl:param name="match" />
	<xsl:param name="mode" />
	<xsl:param name="name" />

	<xsl:variable name="element">
		<xsl:choose>
			<xsl:when test="contains($match,'|')">
				<xsl:value-of select="substring-before($match,'|')" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$match" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:element name="A">
		<xsl:attribute name="NAME">
			<xsl:value-of select="concat($element,$name,$mode)" />
		</xsl:attribute>
	</xsl:element>

	<xsl:if test="$match != $element">
		<xsl:call-template name="hrefTemplates2">
			<xsl:with-param name="match" select="substring-after($match,'|')" />
			<xsl:with-param name="mode" select="$mode" />
			<xsl:with-param name="name" select="$name" />
		</xsl:call-template>
	</xsl:if>


</xsl:template>


<!-- ************************************************************************************************* -->
<xsl:template name="nameHREF">

	<xsl:param name="match" />

	<xsl:choose>
		<xsl:when test="contains($match, '/')">
			<xsl:call-template name="nameHREF">
				<xsl:with-param name="match" select="substring-after($match,'/')" />
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:choose>
				<xsl:when test="contains($match, '[')">
					<xsl:value-of select="substring-before($match, '[')" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$match" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:otherwise>
	</xsl:choose>

</xsl:template>


<!-- ************************************************************************************************* -->
<xsl:template name="indent">

	<xsl:param name="indent" select="0" />
	<xsl:param name="index" select="0" />
	
	<xsl:if test="$indent &gt; $index">
		<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
		<xsl:call-template name="indent">
			<xsl:with-param name="indent" select="$indent" />
			<xsl:with-param name="index" select="$index+1" />
		</xsl:call-template>
	</xsl:if>

</xsl:template>


</xsl:stylesheet>








