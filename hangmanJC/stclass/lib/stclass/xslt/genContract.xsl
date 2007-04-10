<?xml version="1.0" encoding='ISO-8859-1' standalone='yes' ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text" encoding="ISO-8859-1" omit-xml-declaration="yes"/>

<!-- GENERATE CODE -->

<!-- *********************************************************************** -->
<xsl:variable name="cr">
  <xsl:text>
</xsl:text>
</xsl:variable>

<xsl:variable name="tab">
  <xsl:text>    </xsl:text>
</xsl:variable>

<!-- EXPRESSIONS + autres -->

<!-- *********************************************************************** -->
<xsl:template match="CONTRATS">
	
	<xsl:for-each select="REQUIRE">
		<xsl:text>boolean </xsl:text>
		<!--<xsl:call-template name="genBooleanVar">
			<xsl:with-param name="depth">
				<xsl:call-template name="maxdepth">
					<xsl:with-param name="depths">
						<xsl:apply-templates select="." mode="depth">
							<xsl:with-param name="depth" select="'1'" />
						</xsl:apply-templates>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:with-param>
		</xsl:call-template>-->
		<xsl:call-template name="genBooleanVar">
			<xsl:with-param name="depth">
				<xsl:apply-templates select="." mode="depth" />
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:value-of select="concat(';',$cr)" />
		<xsl:apply-templates select="." />
	</xsl:for-each>
	
</xsl:template>


<!-- *********************************************************************** -->
<xsl:template match="REQUIRE">

	<xsl:text>*****************************************************</xsl:text>
	<xsl:value-of select="$cr" />
	<xsl:value-of select="comment()" />
	<xsl:value-of select="$cr" />
	<xsl:apply-templates select="EXPR" mode="firstInContract" />
	
</xsl:template>

<!-- *********************************************************************** -->
<xsl:template match="EXPR" mode="firstInContract">
	
	<xsl:choose>
		<xsl:when test="boolean(IMPLIES|FORALL|EXISTS|LAND|LOR)">
			<xsl:apply-templates select="*" mode="createEvals">
				<xsl:with-param name="rang" select="1" />
				<xsl:with-param name="varRes" select="10" />
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="*" mode="createEvals">
				<xsl:with-param name="rang" select="2" />
				<xsl:with-param name="varRes" select="20" />
			</xsl:apply-templates>
			<xsl:text>__eval10 = </xsl:text>
			<xsl:apply-templates select="*">
				<xsl:with-param name="rang" select="2"/>
			</xsl:apply-templates>
			<xsl:value-of select="concat(';',$cr)" />
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>


<!-- *********************************************************************** -->
<xsl:template match="EXPR">
	<xsl:param name="rang" />
	
	<xsl:apply-templates select="*">
		<xsl:with-param name="rang" select="$rang" />
	</xsl:apply-templates>
	
</xsl:template>


<!-- *********************************************************************** -->
<xsl:template match="IDENT">
	<xsl:param name="rang" />
	
	<xsl:value-of select="." />
</xsl:template>



<!-- *********************************************************************** -->
<xsl:template match="DOT">
	<xsl:param name="rang" />
	
	<xsl:apply-templates select="*[position()=1]">
		<xsl:with-param name="rang" select="$rang"/>
	</xsl:apply-templates>
	
	<xsl:text>.</xsl:text>
	
	<xsl:variable name="nbEvalInFirst">
		<xsl:apply-templates select="*[position()=2]" mode="nbEvalInPreviousElement">
			<xsl:with-param name="position" select="1" />
		</xsl:apply-templates>
	</xsl:variable>
	
	<xsl:apply-templates select="*[position()=2]">
		<xsl:with-param name="rang" select="$rang+nbEvalInFirst"/>
	</xsl:apply-templates>

</xsl:template>

<!-- *********************************************************************** -->
<xsl:template match="GE|NOT_EQUAL">
	<xsl:param name="rang" />
	
	<xsl:apply-templates select="*[position()=1]">
		<xsl:with-param name="rang" select="$rang"/>
	</xsl:apply-templates>
	
	<xsl:value-of disable-output-escaping="yes" select="text()" />
	
	<xsl:variable name="nbEvalInFirst">
		<xsl:apply-templates select="*[position()=2]" mode="nbEvalInPreviousElement">
			<xsl:with-param name="position" select="1" />
		</xsl:apply-templates>
	</xsl:variable>
	<xsl:apply-templates select="*[position()=2]">
		<xsl:with-param name="rang" select="$rang+$nbEvalInFirst"/>
	</xsl:apply-templates>

</xsl:template>


<!-- *********************************************************************** -->
<xsl:template match="METHOD_CALL">
	<xsl:param name="rang" />
	
	<xsl:apply-templates select="*[position()=1]">
	  <xsl:with-param name="rang" select="$rang"/>	
	</xsl:apply-templates>
	
	<xsl:variable name="nbEvalInFirst">
		<xsl:apply-templates select="*[position()=2]" mode="nbEvalInPreviousElement">
			<xsl:with-param name="position" select="1" />
		</xsl:apply-templates>
	</xsl:variable>
	
	<xsl:apply-templates select="*[position()=2]">
	  <xsl:with-param name="rang" select="$rang+$nbEvalInFirst"/>	
	</xsl:apply-templates>
</xsl:template>


<!-- *********************************************************************** -->
<xsl:template match="ELIST">
	<xsl:param name="rang" />
	
	<xsl:text>(</xsl:text>
	<xsl:for-each select="*">
		<xsl:variable name="nbEvalInPrevious">
			<xsl:apply-templates select="." mode="nbEvalInPreviousElement">
				<xsl:with-param name="position" select="position()-1" />
			</xsl:apply-templates>
		</xsl:variable>
		
		<xsl:apply-templates select=".">
			<xsl:with-param name="rang" select="$rang+$nbEvalInPrevious" />
		</xsl:apply-templates>
		
		<xsl:if test="position() != last()">
			<xsl:text>, </xsl:text>
		</xsl:if>
	</xsl:for-each>
	<xsl:text>)</xsl:text>

</xsl:template>


<!-- *********************************************************************** -->
<xsl:template match="IMPLIES|FORALL|EXISTS|LAND|LOR">

	<xsl:param name="rang" />

	<xsl:value-of select="concat('__eval',$rang,'0')" />
	
</xsl:template>

<!-- *********************************************************************** -->
<xsl:template match="IMPLIES" mode="createEvals">

	<xsl:param name="rang" />
	<xsl:param name="varRes" />

	<xsl:variable name="child1isEval">
		<xsl:apply-templates select="*[position()=1]" mode="isEval"/>
	</xsl:variable>
	<xsl:variable name="child2isEval">
		<xsl:apply-templates select="*[position()=2]" mode="isEval"/>
	</xsl:variable>

	<xsl:choose>
		<xsl:when test="$child1isEval = 'true'">
			<xsl:apply-templates select="*[position()=1]" mode="createEvals">
				<xsl:with-param name="rang" select="$rang+1" />
				<xsl:with-param name="varRes" select="concat($rang+1,'0')" />
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="*[position()=1]" mode="createEvals">
				<xsl:with-param name="rang" select="$rang+2" />
				<xsl:with-param name="varRes" select="concat($rang+2,'0')" />
			</xsl:apply-templates>
			<xsl:value-of select="concat('__eval',$rang+1,'0 = ')" />
			<xsl:apply-templates select="*[position()=1]">
				<xsl:with-param name="rang" select="$rang+2"/>
			</xsl:apply-templates>
			<xsl:value-of select="concat(';',$cr)" />
		</xsl:otherwise>
	</xsl:choose>

	<xsl:choose>
		<xsl:when test="$child2isEval = 'true'">
			<xsl:apply-templates select="*[position()=2]" mode="createEvals">
				<xsl:with-param name="rang" select="$rang+1" />
				<xsl:with-param name="varRes" select="concat($rang+1,'1')" />
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="*[position()=2]" mode="createEvals">
				<xsl:with-param name="rang" select="$rang+2" />
				<xsl:with-param name="varRes" select="concat($rang+2,'1')" />
			</xsl:apply-templates>
			<xsl:value-of select="concat('__eval',$rang+1,'1 = ')" />
			<xsl:apply-templates select="*[position()=2]">
				<xsl:with-param name="rang" select="$rang+2"/>
			</xsl:apply-templates>
			<xsl:value-of select="concat(';',$cr)" />
		</xsl:otherwise>
	</xsl:choose>

	

	<xsl:value-of select="concat('__eval', $varRes, ' = ')" />
	<xsl:value-of select="concat('(__eval',$rang+1,'0) ? (__eval',$rang+1,'1) : true;',$cr)" />

</xsl:template>

<!-- *********************************************************************** -->
<xsl:template match="ENUMERATION" mode="createEvals">

	<xsl:param name="varRes" />

	<xsl:text>for (java.util.Iterator __e_enum = </xsl:text>
	<xsl:apply-templates select="*[position()=3]" />
	<xsl:value-of select="concat(';',$cr)" />
	
	<xsl:text disable-output-escaping="yes">__e_enum.hasNext() &amp;&amp; </xsl:text>
	<xsl:if test="name(..) = 'EXISTS'">
		<xsl:text>!</xsl:text>
	</xsl:if>
	<xsl:text>__eval</xsl:text>
	<xsl:value-of select="concat($varRes,';) {',$cr)" />
	
	<xsl:apply-templates select="*[position()=1]"/>
	<xsl:text> </xsl:text>
	<xsl:apply-templates select="*[position()=2]"/>
	<xsl:text> = (</xsl:text>
	<xsl:apply-templates select="*[position()=1]"/>
	<xsl:text>) (__e_enum.next());</xsl:text>
	<xsl:value-of select="$cr" />
    
</xsl:template>

<!-- *********************************************************************** -->
<xsl:template match="FORALL|EXISTS" mode="createEvals">

	<xsl:param name="rang" />
	<xsl:param name="varRes" />
	
	<xsl:value-of select="concat('__eval',$varRes,' = ')" />
	<xsl:if test="name() = 'FORALL'">	
		<xsl:text>true</xsl:text>
	</xsl:if>
	<xsl:if test="name() = 'EXISTS'">
		<xsl:text>false</xsl:text>
	</xsl:if>
	<xsl:value-of select="concat(';',$cr)" />
	
	<xsl:apply-templates select="ENUMERATION" mode="createEvals">
		<xsl:with-param name="varRes" select="$varRes" />
	</xsl:apply-templates>
	
	
	<xsl:variable name="exprChildIsEval">
		<xsl:apply-templates select="EXPR/*[position()=1]" mode="isEval"/>
	</xsl:variable>
	<xsl:choose>
		<xsl:when test="$exprChildIsEval = 'true'">
			<xsl:apply-templates select="EXPR" mode="createEvals">
				<xsl:with-param name="rang" select="$rang+1" />
				<xsl:with-param name="varRes" select="concat($rang+1,'0')" />
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="EXPR" mode="createEvals">
				<xsl:with-param name="rang" select="$rang+2" />
				<xsl:with-param name="varRes" select="concat($rang+2,'0')" />
			</xsl:apply-templates>
			<xsl:value-of select="concat('__eval',$rang+1,'0 = ')" />
			<xsl:apply-templates select="EXPR">
				<xsl:with-param name="rang" select="$rang+2"/>
			</xsl:apply-templates>
			<xsl:value-of select="concat(';',$cr)" />
		</xsl:otherwise>
	</xsl:choose>
	
	<xsl:value-of select="concat('__eval',$varRes,' = __eval',$rang+1,'0;',$cr)" />
	<xsl:value-of select="concat('}',$cr)" />

</xsl:template>



<!-- *********************************************************************** -->
<xsl:template match="LAND|LOR" mode="createEvals">

	<xsl:param name="rang" />
	<xsl:param name="varRes" />

	<xsl:variable name="child1isEval">
		<xsl:apply-templates select="*[position()=1]" mode="isEval"/>
	</xsl:variable>
	<xsl:variable name="child2isEval">
		<xsl:apply-templates select="*[position()=2]" mode="isEval"/>
	</xsl:variable>

	<xsl:choose>
		<xsl:when test="$child1isEval = 'true'">
			<xsl:apply-templates select="*[position()=1]" mode="createEvals">
				<xsl:with-param name="rang" select="$rang+1" />
				<xsl:with-param name="varRes" select="concat($rang+1,'0')" />
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="*[position()=1]" mode="createEvals">
				<xsl:with-param name="rang" select="$rang+2" />
				<xsl:with-param name="varRes" select="concat($rang+2,'0')" />
			</xsl:apply-templates>
			<xsl:value-of select="concat('__eval',$rang+1,'0 = ')" />
			<xsl:apply-templates select="*[position()=1]">
				<xsl:with-param name="rang" select="$rang+2"/>
			</xsl:apply-templates>
			<xsl:value-of select="concat(';',$cr)" />
		</xsl:otherwise>
	</xsl:choose>

	<xsl:value-of select="'if ('" />
	<xsl:if test="name() = 'LOR'">
		<xsl:text>!</xsl:text>
	</xsl:if>
	<xsl:value-of select="concat('__eval',$rang+1,'0) {',$cr)" />

	<xsl:choose>
		<xsl:when test="$child2isEval = 'true'">
			<xsl:apply-templates select="*[position()=2]" mode="createEvals">
				<xsl:with-param name="rang" select="$rang+1" />
				<xsl:with-param name="varRes" select="concat($rang+1,'1')" />
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="*[position()=2]" mode="createEvals">
				<xsl:with-param name="rang" select="$rang+2" />
				<xsl:with-param name="varRes" select="concat($rang+2,'1')" />
			</xsl:apply-templates>
			<xsl:value-of select="concat('__eval',$rang+1,'1 = ')" />
			<xsl:apply-templates select="*[position()=2]">
				<xsl:with-param name="rang" select="$rang+2"/>
			</xsl:apply-templates>
			<xsl:value-of select="concat(';',$cr)" />
		</xsl:otherwise>
	</xsl:choose>

	<xsl:value-of select="concat('} else {',$cr)" />
	<xsl:value-of select="concat('__eval',$rang+1,'1 = false;',$cr,'}',$cr)" />

	<xsl:value-of select="concat('__eval', $varRes, ' = ')" />
	<xsl:value-of select="concat('__eval',$rang+1,'0 ')"/>
	<xsl:choose>
		<xsl:when test="name() = 'LAND'">
			<xsl:text disable-output-escaping="yes">&amp;&amp;</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>||</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
	<xsl:value-of select="concat(' __eval',$rang+1,'1;',$cr)" />

</xsl:template>


<!-- *********************************************************************** -->
<xsl:template match="*" mode="createEvals">
	
	<xsl:param name="rang" />
	<xsl:param name="varRes" />
	
<!--	<xsl:value-of select="concat(name(),' ',$rang,' ',$varRes,$cr)" />-->
	
	<xsl:variable name="parentElement" select="name(.)" />
	
	<xsl:for-each select="child::*">
		
		<xsl:variable name="position" select="position()" />
		<xsl:variable name="nbEvalInPreviousElement">
			<xsl:apply-templates select="." mode="nbEvalInPreviousElement">
				<xsl:with-param name="position" select="$position -1" />
			</xsl:apply-templates>
		</xsl:variable>

		<xsl:apply-templates select="." mode="createEvals">
			<xsl:with-param name="rang" select="$rang+$nbEvalInPreviousElement" />
			<xsl:with-param name="varRes" select="$varRes+(10*$nbEvalInPreviousElement)" />
		</xsl:apply-templates>
		
	</xsl:for-each>
</xsl:template>

<!-- *********************************************************************** -->
<xsl:template match="*" mode="nbEvalInPreviousElement">	
	<xsl:param name="position" />

	<xsl:value-of select="count(
		../*[position() = $position]//IMPLIES |
		../*[position() = $position]//FORALL |
		../*[position() = $position]//EXISTS |
		../*[position() = $position]//LAND |
		../*[position() = $position]//LOR
	)"/>
		
</xsl:template>

<!-- *********************************************************************** -->
<xsl:template match="*" mode="isEval">
	
	<xsl:choose>
		<xsl:when test="name() = 'IMPLIES'">
			<xsl:value-of select="true()" />
		</xsl:when>
		<xsl:when test="name() = 'FORALL'">
			<xsl:value-of select="true()" />
		</xsl:when>
		<xsl:when test="name() = 'EXISTS'">
			<xsl:value-of select="true()" />
		</xsl:when>
		<xsl:when test="name() = 'LAND'">
			<xsl:value-of select="true()" />
		</xsl:when>
		<xsl:when test="name() = 'LOR'">
			<xsl:value-of select="true()" />
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="false()" />
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!-- *********************************************************************** -->
<xsl:template match="REQUIRE" mode="depth">
	<xsl:param name="depth"/>
	
	<xsl:variable name="firstExprIsEval">
		<xsl:apply-templates select="EXPR/*" mode="isEval" />
	</xsl:variable>
	
	<xsl:variable name="initDepth">
		<xsl:choose>
			<xsl:when test="$firstExprIsEval = 'true'">
				<xsl:value-of select="1" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="2" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<xsl:call-template name="maxdepth">
		<xsl:with-param name="depths">
			<xsl:apply-templates select="EXPR" mode="depth">
				<xsl:with-param name="depth" select="$initDepth"/>
			</xsl:apply-templates>
		</xsl:with-param>
	</xsl:call-template>
	
</xsl:template>


<!-- *********************************************************************** -->
<xsl:template match="IMPLIES|LAND|LOR" mode="depth">
	<xsl:param name="depth"/>

	<xsl:value-of select="concat($depth+1,'-')" />
	
	<xsl:for-each select="*">
		<xsl:variable name="position" select="position()" />
		<xsl:variable name="nbInPrev">
			<xsl:apply-templates select="." mode="nbEvalInPreviousElement">
				<xsl:with-param name="position" select="$position -1" />
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:variable name="childIsEval">
			<xsl:apply-templates select="." mode="isEval" />
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$childIsEval = 'true'">
				<xsl:apply-templates select="." mode="depth">
					<xsl:with-param name="depth" select="$depth + 1" />
				</xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="." mode="depth">
					<xsl:with-param name="depth" select="$depth + 2 + $nbInPrev" />
				</xsl:apply-templates>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:for-each>
	
	
</xsl:template>

<!-- *********************************************************************** -->
<xsl:template match="FORALL|EXISTS" mode="depth">
	<xsl:param name="depth"/>

	<xsl:value-of select="concat($depth+1,'-')" />
	
	<xsl:variable name="childIsEval">
		<xsl:apply-templates select="EXPR/*" mode="isEval" />
	</xsl:variable>
	<xsl:choose>
		<xsl:when test="$childIsEval = 'true'">
			<xsl:apply-templates select="EXPR" mode="depth">
				<xsl:with-param name="depth" select="$depth + 1" />
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="EXPR" mode="depth">
				<xsl:with-param name="depth" select="$depth + 2" />
			</xsl:apply-templates>
		</xsl:otherwise>
	</xsl:choose>

</xsl:template>


<!-- *********************************************************************** -->
<xsl:template match="*" mode="depth">
	<xsl:param name="depth" />
	
	<xsl:for-each select="child::*">
		<xsl:variable name="position" select="position()" />
		<xsl:variable name="nbInPrev">
			<xsl:apply-templates select="." mode="nbEvalInPreviousElement">
				<xsl:with-param name="position" select="$position -1" />
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:apply-templates select="." mode="depth">
			<xsl:with-param name="depth" select="$depth + $nbInPrev" />
		</xsl:apply-templates>
	</xsl:for-each>

</xsl:template>


<!-- ********************************************************** -->
<xsl:template name="genBooleanVar">

	<!-- generate the boolean variables __evalXX.. for contract evaluation -->

	<xsl:param name="depth" />
	<xsl:param name="currentDepth" select="'1'" />

	<xsl:if test="$currentDepth &lt;= $depth">
		<xsl:text>__eval</xsl:text>
		<xsl:value-of select="concat($currentDepth,'0, ')" />
		<xsl:text>__eval</xsl:text>
		<xsl:value-of select="concat($currentDepth,'1')" />
	</xsl:if>
	<xsl:if test="$currentDepth &lt; $depth">
		<xsl:text>, </xsl:text>
		<xsl:call-template name="genBooleanVar">
			<xsl:with-param name="depth" select="$depth" />
			<xsl:with-param name="currentDepth" select="$currentDepth + 1" />
		</xsl:call-template>
	</xsl:if>

</xsl:template>
<!-- *********************************************************** -->
<xsl:template name="maxdepth">

	<!-- search the maximum depth in the list of depth obtains by the template Contracts in mode "depth" -->

	<xsl:param name="depths" />
	<xsl:param name="max" select="'0'" />

	<xsl:variable name="first">
		<xsl:value-of select="substring-before($depths,'-')" />
	</xsl:variable>

	<xsl:variable name="rest">
		<xsl:value-of select="substring-after($depths,'-')" />
	</xsl:variable>

	<xsl:if test="$rest != ''">
		<xsl:if test="$first &lt;= $max">
			<xsl:call-template name="maxdepth">
				<xsl:with-param name="depths" select="$rest"/>
				<xsl:with-param name="max" select="$max" />
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="$first &gt; $max">
			<xsl:call-template name="maxdepth">
				<xsl:with-param name="depths" select="$rest"/>
				<xsl:with-param name="max" select="$first" />
			</xsl:call-template>
		</xsl:if>
	</xsl:if>
	<xsl:if test="$rest = ''">
		<xsl:if test="$first &gt; $max">
			<xsl:value-of select="$first" />
		</xsl:if>
		<xsl:if test="$first &lt;= $max">
			<xsl:value-of select="$max" />
		</xsl:if>
	</xsl:if>

</xsl:template>



<!-- ********************************************************** -->
<xsl:template match="Require" mode="depth">

	<!-- check the depth of Require, Ensure and Invar element -->

	<xsl:variable name="res">
		<xsl:apply-templates select="Expr" mode="depth">
			<xsl:with-param name="depth" select="'0'" />
		</xsl:apply-templates>
	</xsl:variable>

	<xsl:call-template name="maxdepth">
		<xsl:with-param name="depths" select="$res" />
	</xsl:call-template>
	<xsl:text>-</xsl:text>

</xsl:template>


			
<!-- *********************************************************************** -->
<xsl:template name="replace">

  <!-- Replace a string by an other string in a third string -->

  <xsl:param name="stringToProcess">
    <!-- The string where process the replacement -->
  </xsl:param>
  <xsl:param name="toReplace">
    <!-- The string to replace -->
  </xsl:param>
  <xsl:param name="replaceBy">
    <!-- replace with this string -->
  </xsl:param>
	
  <xsl:if test="contains($stringToProcess,$toReplace)">
    <xsl:value-of disable-output-escaping="yes" select="concat(substring-before($stringToProcess,$toReplace),$replaceBy)"/> 
    <xsl:call-template name="replace">
      <xsl:with-param name="stringToProcess" select="substring-after($stringToProcess,$toReplace)" />
      <xsl:with-param name="toReplace" select="$toReplace" />
      <xsl:with-param name="replaceBy" select="$replaceBy" />
    </xsl:call-template>
  </xsl:if>

  <xsl:if test="not(contains($stringToProcess,$toReplace))">
    <xsl:value-of disable-output-escaping="yes" select="$stringToProcess" />
  </xsl:if>

</xsl:template>


</xsl:stylesheet>

