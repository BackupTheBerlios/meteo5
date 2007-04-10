<?xml version="1.0" encoding='ISO-8859-1' standalone='yes' ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="text" encoding="ISO-8859-1" omit-xml-declaration="yes"/>

  <xsl:include href="o2cm2java-Class.xsl" />
  <xsl:include href="o2cm2java-Features.xsl" />
  <xsl:include href="o2cm2java-Test.xsl" />
  <xsl:include href="o2cm2java-Contract.xsl" />
  <xsl:include href="o2cm2java-Code.xsl" />





<!-- ****************************************************************** -->
<!-- * Variables Declarations ***************************************** -->
<!-- ****************************************************************** -->



<!-- ****************************************************************** -->
  <xsl:variable name="cr">
    <xsl:text>
</xsl:text>
  </xsl:variable>

<!-- ****************************************************************** -->
  <xsl:variable name="tab">
    <xsl:text>  </xsl:text>
  </xsl:variable>

<!-- ****************************************************************** -->
  <xsl:variable name="fileHeader">
	<!-- First comment in the generated source -->
    <xsl:text>
 *
 * **************************************************
 *  THIS FILE SHOULD NOT BE EDITED DIRECTLY
 *  Edit the source and remake the preprocessing
 * **************************************************
 *    All code lines are left commented to show their original position
 *    in source code; the comment contains two fields, a code of two letters
 *    and a number that displays the line number of related code in the true
 *    source file ('0000' if the actual code has no related code in source)
 *		'SC'  :  code copyed from source
 *		'cc'  :  code related to contracts management
 *              'as'  :  code related to traces and assertions
 *		'tt'  :  code implementing test management
 *		'tu'  :  code of testing units
 *
</xsl:text>
  </xsl:variable>





<!-- ****************************************************************** -->
<!-- * Root elements of o2cm ****************************************** -->
<!-- ****************************************************************** -->



<!-- ****************************************************************** --> 
  <xsl:template match="/">
	<!-- Root element -->

    <xsl:apply-templates select="o2cm" />

  </xsl:template>
  

<!-- ****************************************************************** -->
  <xsl:template match="o2cm">

    <!-- Main element, contains MetaData, Package of the class, and the class itself -->
    
    <xsl:param name="option" select="'normal'" />

    <xsl:apply-templates select="MetaData" />
    <xsl:apply-templates select="ClsPackage" />

    <xsl:choose>
      <xsl:when test="Class/@interface = 'yes'">
        <xsl:apply-templates select="Class" mode="interface" />
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="Class" >
		<xsl:with-param name="option" select="$option" />
	</xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="MetaData">

	<!-- javadoc, present the class ... -->

    <xsl:text>/*   instr/</xsl:text>
    <xsl:call-template name="getClassLongName">
      <xsl:with-param name="sep" select="'/'" />
    </xsl:call-template>
    <xsl:text>.java</xsl:text>

    <xsl:value-of select="$fileHeader" />

    <xsl:call-template name="endComment" />
    <xsl:value-of select="$cr" />
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="ClsPackage">

	<!-- Package of the class -->

    <xsl:if test="@name != ''">
      <xsl:text>package </xsl:text>
      <xsl:value-of select="attribute::name" />
      <xsl:text>;</xsl:text>
      <xsl:value-of select="$cr" />
      <xsl:value-of select="$cr" />
    </xsl:if>

  </xsl:template>





<!-- ****************************************************************** -->
<!-- * Common elements ************************************************ -->
<!-- ****************************************************************** -->


		
<!-- ****************************************************************** -->
  <xsl:template match="Visibility">

	<!-- Visibility (public, private...) -->

	<!--
	<xsl:value-of select="attribute::visibility" />
	<xsl:text> </xsl:text>
	-->

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Qualifiers">

	<!-- List of Qualifiers -->

    <xsl:apply-templates select="Qualifier" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Qualifier">

	<!-- A Qualifier (static, final, abstract, ....)-->

    <xsl:value-of select="attribute::value" />
    <xsl:text> </xsl:text>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Type">
	
	<!-- A Type, for return value or variable and arguments -->

    <xsl:value-of select="attribute::value" />
    <xsl:text> </xsl:text>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="Para_Example">

	<!-- Para and Example are both documentation parts -->

    <xsl:param name="prefix" select="''" />
    <xsl:param name="doc" select="' * '" />

    <xsl:call-template name="docLine">
      <xsl:with-param name="prefix" select="$prefix" />
      <xsl:with-param name="doc" select="$doc" />
      <xsl:with-param name="content" select="." />
    </xsl:call-template>

  </xsl:template>





<!-- ****************************************************************** -->
<!-- * Functions ****************************************************** -->
<!-- ****************************************************************** -->



<!-- ****************************************************************** -->
  <xsl:template name="beginComment">

	<!-- Begin a Multilines comment : /*\n -->

    <xsl:param name="prefix">
		<!-- what is juste before the '/*' -->
      <xsl:value-of select="''" />
    </xsl:param>

    <xsl:value-of select="concat($prefix,'/*',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="beginJavadocComment">

	<!-- Begin Javadoc Comment : /**\n -->

    <xsl:param name="prefix">
		<!-- What is just before the '/**' -->
      <xsl:value-of select="''" />
    </xsl:param>

    <xsl:value-of select="concat($prefix,'/**',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="endComment">
	
	<!-- End Comment : */\n -->

    <xsl:param name="prefix">
		<!-- What is just before the '*/' -->
      <xsl:value-of select="''" />
    </xsl:param>

    <xsl:value-of select="concat($prefix,' */',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="docLine">

	<!-- Build a comment line : * ...\n -->

    <xsl:param name="prefix">
		<!-- What is juste before the '*' -->
      <xsl:value-of select="''"/>
    </xsl:param>
    <xsl:param name="doc">
		<!-- the part with the * -->
      <xsl:value-of select="' * '" />
    </xsl:param>
    <xsl:param name="content">
		<!-- the text after the *. If there is a \n in this parameters, it's replaced by a space -->
      <xsl:value-of select="''" />
    </xsl:param>

    <xsl:value-of select="concat($prefix,$doc,translate($content,'&#10;',' '),$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="replace">

	<!-- Replace a string by an other string in a third string -->

    <xsl:param name="stringToProcess">
		<!-- The string where process the replacement --></xsl:param>
    <xsl:param name="toReplace">
		<!-- The string to replace --></xsl:param>
    <xsl:param name="replaceBy">
		<!-- replace with this string --></xsl:param>

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



<!-- ****************************************************************** -->
  <xsl:template name="beginTestManagmentLine">

	<!-- Begin a line of Test Managment -->

    <xsl:param name="indent" />
    <xsl:param name="number" select="'0'" />

    <xsl:call-template name="commentedLine">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="type" select="'tt'" />
      <xsl:with-param name="number" select="$number" />
    </xsl:call-template>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="beginTestUnitLine">

	<!-- Begin a line of Test Unit -->

    <xsl:param name="indent" />

    <xsl:call-template name="commentedLine">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="type" select="'tu'" />
      <xsl:with-param name="number" select="@line" />
    </xsl:call-template>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="beginContractLine">

	<!-- Begin a Contract Line -->

    <xsl:param name="indent" />

    <xsl:variable name="number">
		<!-- line number -->
      <xsl:choose>
	  	<!-- Require, Ensure and Invar don't have line attribute
        <xsl:when test="boolean(ancestor-or-self::Require/@line)">
          <xsl:value-of select="ancestor-or-self::Require/@line" />
        </xsl:when>
        <xsl:when test="boolean(ancestor-or-self::Ensure/@line)">
          <xsl:value-of select="ancestor-or-self::Ensure/@line" />
        </xsl:when>
        <xsl:when test="boolean(ancestor-or-self::Invar/@line)">
          <xsl:value-of select="ancestor-or-self::Invar/@line" />
        </xsl:when>
		 -->
		<xsl:when test="@line"> <!-- modified : get the line of the current node -->
			<xsl:value-of select="@line"/>
		</xsl:when>
        <xsl:otherwise>
          <xsl:text>0</xsl:text>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
	
    <xsl:call-template name="commentedLine">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="type" select="'cc'" />
      <xsl:with-param name="number" select="$number" />
    </xsl:call-template>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="beginSourceCopiedLine">

	<!-- Begin a Line of Copied Source -->

    <xsl:param name="indent" />
    <xsl:param name="forceNumber" select="-1" />
	
    <xsl:variable name="number">
		<!-- line number -->
      <xsl:choose>
        <xsl:when test="boolean(@line)">
          <xsl:value-of select="attribute::line" />
        </xsl:when>
        <xsl:when test="$forceNumber != -1">
          <xsl:value-of select="$forceNumber" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:text>0</xsl:text>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
	
    <xsl:call-template name="commentedLine">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="type" select="'SC'" />
      <xsl:with-param name="number" select="$number" />
    </xsl:call-template>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="commentedLine">

	<!-- write a comment /*...*/ with '/*[type][number]*/ [indent]'  -->

    <xsl:param name="type">
		<!-- type of the comment --></xsl:param>
    <xsl:param name="number">
		<!-- line number --></xsl:param>
    <xsl:param name="indent">
		<!-- indentation, it's a NUMBER of space --></xsl:param>

    <xsl:text>/*</xsl:text>
    <xsl:value-of select="$type" />
    <xsl:value-of select="format-number($number,'0000')" />
    <xsl:text>*/ </xsl:text>
    <xsl:call-template name="indent">
      <xsl:with-param name="space" select="$indent" />
    </xsl:call-template>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="indent">

	<!-- write an indentation -->

    <xsl:param name="space" select="'0'"/>
    <xsl:param name="increment" select="'0'" />

    <xsl:if test="$space &gt; $increment">
      <xsl:value-of select="' '" />
      <xsl:call-template name="indent">
        <xsl:with-param name="space" select="$space" />
        <xsl:with-param name="increment" select="$increment + 1" />
      </xsl:call-template>
    </xsl:if>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="getClassLongName">

	<!-- Returns the long name of a class (ie package.class) -->

    <xsl:param name="sep" select="'_'" />


	<!-- BUG : this version does not work correctly anymore 
	<xsl:value-of select="translate(//ClsPackage/@name,'.',$sep)" />
	<xsl:if test="//ClsPackage">
		<xsl:value-of select="$sep" />
	</xsl:if>-->
	<!-- 
    <xsl:value-of select="translate(ancestor-or-self::o2cm/ClsPackage/@name,'.',$sep)" />
    <xsl:if test="ancestor-or-self::o2cm/ClsPackage">
      <xsl:value-of select="$sep" />
    </xsl:if>
    <xsl:call-template name="getClassName" />
	 -->
	
	<!-- modified, if no package, dont add package name before className -->
	<xsl:if test="//ClsPackage/@name and //ClsPackage/@name != ''">
		<xsl:value-of select="translate(//ClsPackage/@name,'.',$sep)"/>
		<xsl:value-of select="$sep" />
	</xsl:if>
	<xsl:call-template name="getClassName" />
	
	
  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template name="getClassName">

	<!-- Returns the name of the class -->

	<!-- BUG : this version does not work correctly anymore
	<xsl:value-of select="/o2cm/Class/Ident/@name" />-->
    <xsl:value-of select="ancestor-or-self::o2cm/Class/Ident/@name" />

  </xsl:template>
  
<!-- ****************************************************************** -->
  <xsl:template name="getDefaultInitValue">
  	<!-- Returns an initilaizing value for a specific type -->
	<xsl:param name="type" />
	
	<xsl:choose>
		<xsl:when test="$type = 'boolean'">
			<xsl:text>false</xsl:text>
		</xsl:when>
		<xsl:when test="$type = 'char'">
			<xsl:text>''</xsl:text>
		</xsl:when>
		<xsl:when test="($type='byte') or ($type='short') or ($type='int') or ($type='long')">
			<xsl:text>0</xsl:text>
		</xsl:when>
		<xsl:when test="($type='float') or ($type='double')">
			<xsl:text>0</xsl:text>
		</xsl:when>
		<xsl:otherwise> <!-- if it's an Object type (not a primitive one), returns null -->
			<xsl:text>null</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
	
  </xsl:template>

</xsl:stylesheet>