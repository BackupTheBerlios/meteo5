<?xml version="1.0" encoding='ISO-8859-1' standalone='yes' ?>


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="text" encoding="ISO-8859-1" omit-xml-declaration="yes"/>




<!-- ****************************************************************** -->
<!-- * Test managment ************************************************* -->
<!-- ****************************************************************** -->



<!-- ****************************************************************** -->
  <xsl:template match="Test">

	<!-- The Tests of the class -->
	
	<!-- generate a default constructor only if the class is not an abstract class -->
    <xsl:if test="not(//ClsSign/Qualifiers/Qualifier[@value='abstract'])">
      <xsl:apply-templates select="TestCreate" />
    </xsl:if>
    <xsl:apply-templates select="TestCase" />
    <xsl:apply-templates select="TestSuite" />
	<!-- generate a Main only if the class is not an abstract class -->
    <xsl:if test="not(//ClsSign/Qualifiers/Qualifier[@value='abstract'])">
      <xsl:apply-templates select="." mode="mainMethod" />
    </xsl:if>
	<!-- copy the STStub delegation at end (always needed, even if abstract class) -->
    <xsl:apply-templates select="." mode="ststubDelegation" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="TestCreate">

	<!-- Tcreate element, by default or not -->
	
    <xsl:param name="indent" select="1" />
    <xsl:param name="context">
		<!-- Name of the test case where this TCreate appears -->
      <xsl:value-of select="'TC_new_default'" />
    </xsl:param>

		<!-- comments -->
	
    <xsl:variable name="space" select="' '" />
	
    <xsl:call-template name="beginJavadocComment">
      <xsl:with-param name="prefix" select="$space" />
    </xsl:call-template>
	
    <xsl:call-template name="docLine">
      <xsl:with-param name="prefix" select="$space" />
      <xsl:with-param name="content">
        <xsl:text>OU</xsl:text>
        <xsl:value-of select="$context" />
        <xsl:text>	: object under test creator</xsl:text>
      </xsl:with-param>
    </xsl:call-template>
	
    <xsl:call-template name="endComment">
      <xsl:with-param name="prefix" select="$space" />
    </xsl:call-template>

		<!-- declaration -->
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>public </xsl:text>
    <xsl:call-template name="getClassName" />
    <xsl:text> OU</xsl:text>
    <xsl:value-of select="$context" />
    <xsl:text>()</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>{</xsl:text>
    <xsl:value-of select="$cr" />

		<!-- code -->
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>return (</xsl:text>
    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent+2"/>
    </xsl:apply-templates>
    <xsl:text>);</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TestCase">

	<!-- a Test Case, contains Test Units, and eventually Tcreate, Preambule, and Postambule -->
	
    <xsl:param name="indent" select="'1'" />
	
    <xsl:variable name="name" select="concat('TC_',Ident/@name)" />
    <xsl:variable name="space" select="' '" />

		<!-- Test Create for this test Case -->
	
    <xsl:apply-templates select="TestCreate">
      <xsl:with-param name="context" select="$name" />
    </xsl:apply-templates>

		<!-- Documentation -->
	
    <xsl:call-template name="beginJavadocComment">
      <xsl:with-param name="prefix" select="$space" />
    </xsl:call-template>
	
    <xsl:call-template name="docLine">
      <xsl:with-param name="prefix" select="$space" />
      <xsl:with-param name="content">
        <xsl:value-of select="$name" />
        <xsl:text>	: </xsl:text>
        <xsl:value-of select="Ident/@role" />
      </xsl:with-param>
    </xsl:call-template>
	
    <xsl:call-template name="endComment">
      <xsl:with-param name="prefix" select="$space" />
    </xsl:call-template>

		<!-- Declaration of the Test Case method -->
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>public String </xsl:text>
    <xsl:value-of select="$name" />
    <xsl:text>(String tunitName)</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>{</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>String ret = "";</xsl:text>
    <xsl:value-of select="$cr" />

		<!-- Possibly a Preambule -->
	
    <xsl:apply-templates select="Preambule">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>

		<!-- Common Test Unit Code -->
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>if ( !ret.equals("") ) { return ret; }</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>if ( tunitName.equals("_help_") ) {</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:text>ret = "</xsl:text>
    <xsl:value-of select="Ident/@role" />
	<!--xsl:text>" +</xsl:text>
	<xsl:value-of select="$cr" />

	<xsl:call-template name="beginTestUnitLine">
		<xsl:with-param name="indent" select="$indent+8" />
	</xsl:call-template>
	<xsl:text>"; </xsl:text>
	<xsl:value-of select="Description/Para" /-->
    <xsl:text>";</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>} else if ( tunitName.equals("_list_") ) {</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:text>ret = "</xsl:text>
	<!-- <xsl:apply-templates select="List" mode="string" /> -->
    <xsl:apply-templates select="List" mode="TCase"> <!-- modified -->
      <xsl:with-param name="name" select="$name" />
    </xsl:apply-templates>
    <xsl:text>";</xsl:text>
    <xsl:value-of select="$cr" />

		<!-- Process each Test Unit -->
	
    <xsl:apply-templates select="List" mode="TestCase">
      <xsl:with-param name="indent" select="$indent+1"/>
    </xsl:apply-templates>

		<!-- End of Test Unit -->
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>} else {</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:text>ret = "badCall";</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />

		<!-- possibly a Postambule -->
	
    <xsl:apply-templates select="Postambule">
      <xsl:with-param name="indent" select="$indent + 1" />
    </xsl:apply-templates>

	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>return ret;</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$cr" />


  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Preambule">

	<!-- Preambule to a Test Case -->
	
    <xsl:param name="indent" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>// preamble</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:apply-templates select="BlocCode" mode="preambdecl">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>try {</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:apply-templates select="BlocCode" mode="preamb">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>} catch (ContractViolation e) {</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>if ( !tunitName.equals("_help_") </xsl:text>
    <xsl:text>&amp;&amp; !tunitName.equals("_list_") ) {</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:text>__ststub().contractViolationError("In preamble",e);</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:text>ret = "CVerror";</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>} // nothing else</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>} catch (Exception e) {</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>if ( !tunitName.equals("_help_") </xsl:text>
    <xsl:text>&amp;&amp; !tunitName.equals("_list_") ) {</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:text>__ststub().exceptionError("In preamble",e);</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:text>ret = "EXerror";</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>} // nothing else</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>// end preamble</xsl:text>
    <xsl:value-of select="$cr" />
  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Postambule">

	<!-- Postamble to a Test Case -->
	
    <xsl:param name="indent" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>// postamble</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>if ( !tunitName.equals("_help_") &amp;&amp; !tunitName.equals("_list_") ) {</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:apply-templates select="BlocCode" mode="test">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>// end postamble</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="List" mode="TestCase">
    <xsl:param name="indent" />
	
    <xsl:for-each select="TUnitRef">
      <xsl:variable name="id" select="concat('test.',@item)" />
      <xsl:apply-templates select="../../../TestUnit[@id = $id]">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:apply-templates>
    </xsl:for-each>

  </xsl:template>


<!-- ****************************************************************** -->
<!-- modified -->
  <xsl:template match="List" mode="string">
	
    <xsl:for-each select="TUnitRef|TCaseRef|TSuiteRef">
      <xsl:apply-templates select="." mode="name" />
      <xsl:if test="position() != last()">
        <xsl:text>,</xsl:text> <!-- modified -->
      </xsl:if>
    </xsl:for-each>

  </xsl:template>

<!-- ****************************************************************** -->
<!-- newly added -->
  <xsl:template match="List" mode="TCase">
    <xsl:param name="name" />
	
    <xsl:for-each select="TUnitRef">
      <xsl:value-of select="$name"/>
      <xsl:text>.</xsl:text>
      <xsl:call-template name="getName">
        <xsl:with-param name="item" select="@item"/>
      </xsl:call-template>
      <xsl:if test="position() != last()">
        <xsl:text>,</xsl:text>
      </xsl:if>
    </xsl:for-each>

  </xsl:template>

<!-- ****************************************************************** -->
<!-- newly added -->
  <xsl:template match="List" mode="TestSuite">
	
    <xsl:for-each select="TCaseRef">
      <xsl:text>TC_</xsl:text>
      <xsl:call-template name="getName">
        <xsl:with-param name="item" select="@item" />
      </xsl:call-template>
      <xsl:if test="position() != last()">
        <xsl:text>,</xsl:text>
      </xsl:if>
    </xsl:for-each>

  </xsl:template>

<!-- ****************************************************************** -->
<!-- newly added -->
  <xsl:template name="getName">
	<!-- ie. returns 'name' from the string 'something.else.name'  -->
    <xsl:param name="item" />
	
    <xsl:choose>
      <xsl:when test="contains($item, '.')">
        <xsl:call-template name="getName">
          <xsl:with-param name="item" select="substring-after($item, '.')" />
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$item"/>
      </xsl:otherwise>
    </xsl:choose>


  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="TCaseRef|TSuiteRef|TUnitRef" mode="name">
	
    <xsl:value-of select="@item" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TestUnit">
	
	<!-- a Test unit -->
	
    <xsl:param name="indent" select="'2'" />
	
    <xsl:variable name="name" select="Ident/@name" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>} else if ( tunitName.equals("</xsl:text>
    <xsl:value-of select="$name" />
    <xsl:text>") ) {</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>__ststub().testTitle("</xsl:text>
    <xsl:value-of select="Ident/@role" />
    <xsl:text>",</xsl:text>
    <xsl:value-of select="$cr" />

		<!-- Beginning of a Test Unit, depends on if it's an inverted Test Unit or not -->
	
    <xsl:choose>
      <xsl:when test="@forecast = 'fail'">
        <xsl:apply-templates select="." mode="startInvertedUnit">
          <xsl:with-param name="indent" select="$indent+1" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="." mode="startNormalUnit">
          <xsl:with-param name="indent" select="$indent+1" />
        </xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>

		<!-- code of the Test Unit -->
	
    <xsl:apply-templates select="BlocCode" mode="test">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:apply-templates>

		<!-- End of a Test Unit -->
	
    <xsl:choose>
      <xsl:when test="@forecast = 'fail'">
        <xsl:apply-templates select="." mode="endInvertedUnit"> <!-- modified -->
          <xsl:with-param name="indent" select="$indent+1" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="." mode="endNormalUnit">
          <xsl:with-param name="indent" select="$indent+1" />
        </xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>


  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TestUnit" mode="startNormalUnit">

	<!-- Start of a classic Test Unit -->
	
    <xsl:param name="indent" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>"") ;</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>try {</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TestUnit" mode="startInvertedUnit">

	<!-- Start of an inverted Test Unit -->
	
    <xsl:param name="indent" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>"") ;</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>// inverted test (contract violation should occur)</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>boolean isThrowed = false;</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>ContractViolation violation = null;</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>try {</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>


  <!-- ****************************************************************** -->
  <xsl:template match="BlocCode" mode="test">
    <!-- Bloc of Code in Tests -->
    <xsl:param name="indent" />
    <xsl:apply-templates select="Slist" mode="dependant">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
  </xsl:template>

  <!-- ****************************************************************** -->
  <xsl:template match="BlocCode" mode="preambdecl">
    <!-- Bloc of Code in Tests case preamble -->
    <xsl:param name="indent" />
    <xsl:apply-templates select="Slist" mode="preambdecl">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
  </xsl:template>

  <!-- ****************************************************************** -->
  <xsl:template match="BlocCode" mode="preamb">
    <!-- Bloc of Code in Tests case preamble -->
    <xsl:param name="indent" />
    <xsl:apply-templates select="Slist" mode="preamb">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
  </xsl:template>




<!-- ****************************************************************** -->
  <xsl:template match="TestUnit" mode="endNormalUnit">

	<!-- End of a Classic Test Unit -->
	
    <xsl:param name="indent" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>ret = "Ok";</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>} catch (ContractViolation e) {</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>__ststub().contractViolationError("</xsl:text>
    <xsl:value-of select="../Ident/@name" />
    <xsl:text>",e);</xsl:text> <!-- modified : e.getStackTrace()-->
    <xsl:value-of select="$cr" />	

	<!-- 
	<xsl:call-template name="beginTestUnitLine">
		<xsl:with-param name="indent" select="$indent+1" />
	</xsl:call-template>
	<xsl:text>e.getStackTrace());</xsl:text>
	<xsl:value-of select="$cr" />	
	-->
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>ret = "CVerror";</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>} catch (Exception e) {</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>__ststub().exceptionError("</xsl:text>
    <xsl:value-of select="../Ident/@name" />
    <xsl:text>",e);</xsl:text> <!-- modified: e.getStackTrace() -->
    <xsl:value-of select="$cr" />	

	<!-- 
	<xsl:call-template name="beginTestUnitLine">
		<xsl:with-param name="indent" select="$indent+1" />
	</xsl:call-template>
	<xsl:text>e.getStackTrace());</xsl:text> 
	<xsl:value-of select="$cr" />
	-->
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>ret = "EXerror";</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestUnitLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TestUnit" mode="endInvertedUnit">

	<!-- End of an inverted Test Unit -->
	
    <xsl:param name="indent" />
	
    <xsl:variable name="bol">
		<!-- Beginning of each line -->
      <xsl:call-template name="beginTestUnitLine">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:call-template>
    </xsl:variable>

	
    <xsl:value-of select="$bol" />
    <xsl:text> ret = "Ok";</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text>} catch (ContractViolation e) {</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text> isThrowed = true;</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text> violation = e;</xsl:text>
    <xsl:value-of select="$cr" />

    <xsl:value-of select="$bol" /><!-- added DD 20061103 -->
    <xsl:text>__ststub().contractViolationError("</xsl:text>
    <xsl:value-of select="../Ident/@name" />
    <xsl:text>",e);</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text> ret = "Ok";</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text>} catch (Exception ex) {</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text> __ststub().exceptionError("</xsl:text>
    <xsl:value-of select="../Ident/@name" />
    <xsl:text>",ex);</xsl:text> <!-- modified: ex.getStackTrace() -->
    <xsl:value-of select="$cr" />

	<!-- 
	<xsl:value-of select="$bol" />
	<xsl:text> ex.getStackTrace());</xsl:text>
	<xsl:value-of select="$cr" />
	-->
	
    <xsl:value-of select="$bol" />
    <xsl:text> ret = "EXerror";</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text>if ( !isThrowed ) {</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text> __ststub().invertedContractViolationError("</xsl:text>
    <xsl:value-of select="Ident/@name"/>
    <xsl:text>"/*,*/</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text> /*violation.getStackTrace()*/);</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text> ret = "ICVerror";</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:value-of select="$bol" />
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr"/>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TestLaunch">

	<!-- Launch of the Test ??? -->
	
    <xsl:call-template name="beginComment" />
		
    <xsl:apply-templates select="Description" mode="test" />

	
    <xsl:call-template name="endComment" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Test" mode="mainMethod">

	<!-- Main Method generated -->
	
    <xsl:variable name="bol">
		<!-- Beginning of line -->
      <xsl:call-template name="beginTestManagmentLine">
        <xsl:with-param name="indent" select="0" />
      </xsl:call-template>
    </xsl:variable>
	
    <xsl:variable name="className">
		<!-- name of the class -->
      <xsl:call-template name="getClassName" />
    </xsl:variable>
	
    <xsl:variable name="classLongName">
		<!-- long name of the class : package.class -->
      <xsl:call-template name="getClassLongName">
        <xsl:with-param name="sep" select="'.'" />
      </xsl:call-template>
    </xsl:variable>

		<!-- Copy with replace the variable $beginMainMethod -->
	
    <xsl:call-template name="replace">
      <xsl:with-param name="toReplace" select="'****bol****'" />
      <xsl:with-param name="replaceBy" select="$bol" />
      <xsl:with-param name="stringToProcess">
        <xsl:call-template name="replace">
          <xsl:with-param name="toReplace" select="'****className****'" />
          <xsl:with-param name="replaceBy" select="$className" />
          <xsl:with-param name="stringToProcess">
            <xsl:call-template name="replace">
              <xsl:with-param name="toReplace" select="'****classLongName****'" />
              <xsl:with-param name="replaceBy" select="$classLongName" />
              <xsl:with-param name="stringToProcess" select="$beginMainMethod" />
            </xsl:call-template>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>

		<!-- Apply each TestSuite defined in Tests -->

	<!-- <xsl:apply-templates select="TestSuite" /> -->
	
		<!-- Create an objet to be tested, with the default constructor -->
	
    <xsl:call-template name="beginTestManagmentLine">
      <xsl:with-param name="indent" select="1" />
    </xsl:call-template>
    <xsl:text>ess = </xsl:text>
    <xsl:choose>
      <xsl:when test="*[TestCreate]">
        <xsl:apply-templates select="TestCreate/Expression" />
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>new </xsl:text>
        <xsl:value-of select="//Class/Ident/@name"/>
        <xsl:text>()</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:text>;</xsl:text>
    <xsl:value-of select="$cr" />

		<!-- Copy with replace the variable $endMainMethod -->
    <xsl:call-template name="replace">
      <xsl:with-param name="toReplace" select="'****bol****'" />
      <xsl:with-param name="replaceBy" select="$bol" />
      <xsl:with-param name="stringToProcess">
        <xsl:call-template name="replace">
          <xsl:with-param name="toReplace" select="'****classLongName****'"/>
          <xsl:with-param name="replaceBy" select="$classLongName" />
          <xsl:with-param name="stringToProcess" select="$endMainMethod" />
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>
		<!-- see 'ststubDelegation' below -->

  </xsl:template>

<!-- ****************************************************************** -->
<!-- modified : newly added -->
  <xsl:template match="Test" mode="ststubDelegation">
	
    <xsl:variable name="bol">
		<!-- Beginning of line -->
      <xsl:call-template name="beginTestManagmentLine">
        <xsl:with-param name="indent" select="0" />
      </xsl:call-template>
    </xsl:variable>
	
    <xsl:variable name="classLongName">
		<!-- long name of the class : package.class -->
      <xsl:call-template name="getClassLongName">
        <xsl:with-param name="sep" select="'.'" />
      </xsl:call-template>
    </xsl:variable>

	<!-- Copy with replace the variable $delegationSupport -->
	
    <xsl:call-template name="replace">
      <xsl:with-param name="toReplace" select="'****bol****'" />
      <xsl:with-param name="replaceBy" select="$bol" />
      <xsl:with-param name="stringToProcess">
        <xsl:call-template name="replace">
          <xsl:with-param name="toReplace" select="'****classLongName****'"/>
          <xsl:with-param name="replaceBy" select="$classLongName" />
          <xsl:with-param name="stringToProcess" select="$delegationSupport" />
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TestSuite">

	<!-- A Test Suite element -->
	<!-- 
	<xsl:call-template name="beginTestManagmentLine">
		<xsl:with-param name="indent" select="3" />
	</xsl:call-template>
	<xsl:text>ST.initSeq("</xsl:text>
	<xsl:value-of select="attribute::name" />
	<xsl:text>",</xsl:text>
	<xsl:value-of select="$cr" />
	
	<xsl:call-template name="beginTestManagmentLine">
		<xsl:with-param name="indent" select="3" />
	</xsl:call-template>
	<xsl:text>new String[] {</xsl:text>
	<xsl:apply-templates select="TCaseRef" />
	<xsl:text>});</xsl:text>
	<xsl:value-of select="$cr" />
	 -->
	
    <xsl:param name="indent" select="'1'" />
    <xsl:variable name="name">
      <xsl:call-template name="getName">
        <xsl:with-param name="item" select="Ident/@item" />
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="space" select="' '" />

		<!-- Documentation -->
	
    <xsl:call-template name="beginJavadocComment">
      <xsl:with-param name="prefix" select="$space" />
    </xsl:call-template>
	
    <xsl:call-template name="docLine">
      <xsl:with-param name="prefix" select="$space" />
      <xsl:with-param name="content">
        <xsl:text>TS_</xsl:text>
        <xsl:value-of select="Ident/@name"/>
        <xsl:text>	: </xsl:text>
        <xsl:value-of select="Ident/@role" />
      </xsl:with-param>
    </xsl:call-template>
	
    <xsl:call-template name="endComment">
      <xsl:with-param name="prefix" select="$space" />
    </xsl:call-template>
	
    <xsl:call-template name="beginTestManagmentLine">
      <xsl:with-param name="indent" select="1" />
    </xsl:call-template>
    <xsl:text>public String TS_</xsl:text>
    <xsl:value-of select="Ident/@name"/>
    <xsl:text>(){</xsl:text>
    <xsl:value-of select="$cr" />
	
    <xsl:call-template name="beginTestManagmentLine">
      <xsl:with-param name="indent" select="2" />
    </xsl:call-template>
    <xsl:text>return "</xsl:text>
    <xsl:apply-templates select="List" mode="TestSuite" />
    <xsl:text>";</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginTestManagmentLine">
      <xsl:with-param name="indent" select="1" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:value-of select="$cr" />


  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Description" mode="test">
    <!-- Description of a Test Unit -->	
    <xsl:call-template name="docLine" />
	
    <xsl:for-each select="Para|Example">
      <xsl:call-template name="Para_Example" />
    </xsl:for-each>
	
    <xsl:call-template name="docLine" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="ValidSeq">
	
    <xsl:value-of select="$tab" />
    <xsl:text>private static String[] VAL_SEQ =</xsl:text>
    <xsl:value-of select="concat($cr,$tab)" />
    <xsl:text>{</xsl:text>
	
    <xsl:apply-templates select="TUnitRef" />
	
    <xsl:text>};</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="VerifSeq">
	
    <xsl:value-of select="$tab" />
    <xsl:text>private static String[] VER_SEQ =</xsl:text>
    <xsl:value-of select="concat($cr,$tab)" />
    <xsl:text>{</xsl:text>
	
    <xsl:apply-templates select="TUnitRef" />
	
    <xsl:text>};</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:variable name="beginMainMethod">
	<!-- The beginning of the main method, used to launch tests -->
    <xsl:text>    /*
     *  Test environment definition
     *
     */
****bol****public static void main(String[] args) 
****bol****	throws FileNotFoundException, TransformerException, ParserConfigurationException {
****bol**** ****className****	ess;
****bol**** boolean	ret;
****bol**** ST = SelfTest.getHandle(****classLongName****.class);
</xsl:text>
  </xsl:variable>
<!-- ****************************************************************** -->
  <xsl:variable name="endMainMethod"> <!-- modified -->
	<!-- End of the main method, and declaration of the variable ST, and the method __ststub() -->
    <xsl:text>****bol**** ST.testOptions(ess,args); <!-- modified -->
****bol**** ret = ST.test(); <!-- modified -->
****bol**** if ( ret ) {System.exit(0);} else {System.exit(1);}
****bol****}
</xsl:text>
  </xsl:variable>
<!-- ****************************************************************** -->
  <xsl:variable name="delegationSupport"> <!-- modified -->
	<!-- declaration of the variable ST, and the method __ststub() -->
    <xsl:text>****bol****// --- Delegation support -------------------------------------
****bol****private static STstub	ST =
****bol****STstub.getHandle(****classLongName****.class);
****bol****protected STstub __ststub() { return ST; }
****bol****
</xsl:text>
  </xsl:variable>



</xsl:stylesheet>