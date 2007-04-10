<?xml version="1.0" encoding='ISO-8859-1' standalone='yes' ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" encoding="ISO-8859-1" omit-xml-declaration="yes"/>



<!-- ****************************************************************** -->
<!-- * Class Declaration elements ************************************* -->
<!-- ****************************************************************** -->


<!-- ****************************************************************** -->
    <xsl:template match="Class">

	<!-- Class element, first we check the import, then write the declaration of the class
		with inherited classes and implemented interfaces, then we process the invariant, the
	    features of the class, the Tests, and the end of the class (}) -->

		<!-- Imported package and classes  -->
		
	<xsl:param name="option" select="'normal'" />
	
        <xsl:apply-templates select="ClsLinks/Suppliers" />
	
        <xsl:text>import org.stclass.runtime.SelfTest;</xsl:text>
        <xsl:value-of select="$cr" />
        <xsl:text>import org.stclass.runtime.STstub;</xsl:text>
        <xsl:value-of select="$cr" />
        <xsl:text>import org.stclass.runtime.ContractViolation;</xsl:text>
        <xsl:value-of select="$cr" />
        <xsl:value-of select="$cr" />
		
		<!-- Imported package for Exception catching, see 'beginMainMethod' in o2cm2java-Test.xsl  -->
		<xsl:text>import java.io.FileNotFoundException;</xsl:text>
        <xsl:value-of select="$cr" />
        <xsl:text>import javax.xml.parsers.ParserConfigurationException;</xsl:text>
        <xsl:value-of select="$cr" />
        <xsl:text>import javax.xml.transform.TransformerException;</xsl:text>
        <xsl:value-of select="$cr" />
        <xsl:value-of select="$cr" />
		
		<!-- java standard imports used in instrumented source -->
		<xsl:text>import java.util.*;</xsl:text>
        <xsl:value-of select="$cr" />
        <xsl:value-of select="$cr" />

		<!-- Declaration of the class  : public class ... extends... implements... -->
	
        <xsl:apply-templates select="ClsSign" />
        <xsl:apply-templates select="ClsLinks" />

	
        <xsl:call-template name="beginSourceCopiedLine">
            <xsl:with-param name="indent" select="0" />
            <xsl:with-param name="forceNumber" select="@start" />
        </xsl:call-template>
        <xsl:value-of select="concat('{',$cr)" />

		<!-- Apply the invariant of the class -->
	
		<!-- modified : 'if' added => WARNING: may not work with Invariant inheritance -->
		<!-- <xsl:if test="count(//Invar/*) > 0 and count(//Contracts) > 0"> -->
			<xsl:apply-templates select="ClsSign/Invariant" mode="Declaration">
				<xsl:with-param name="indent" select="1" />
			</xsl:apply-templates>
		<!-- </xsl:if> -->
		
		<!-- Write the method used to evaluate preconditions -->
		<xsl:call-template name="precondEval" >
			<xsl:with-param name="indent" select="1" />
		</xsl:call-template>

		<!-- Features : variables, methods, initializers, inner classes -->
	
        <xsl:apply-templates select="Features">
            <xsl:with-param name="indent" select="1" />
        </xsl:apply-templates>

		<!-- Tests -->
	
	<xsl:if test="$option = 'normal'" >	
        	<xsl:apply-templates select="Test">
			<xsl:with-param name="indent" select="1" />
		</xsl:apply-templates>
	</xsl:if>


		<!-- End of the class -->
	
        <xsl:call-template name="beginSourceCopiedLine">
            <xsl:with-param name="indent" select="0" />
            <xsl:with-param name="forceNumber" select="@end" />
        </xsl:call-template>
        <xsl:text>} // ------------------------------------- class </xsl:text>
        <xsl:call-template name="getClassLongName">
            <xsl:with-param name="sep" select="'.'" />
        </xsl:call-template>
        <xsl:value-of select="$cr" />


    </xsl:template>


<!-- ****************************************************************** -->
<!-- modified : this template does not seem to be used anymore -->
    <xsl:template match="Class" mode="intern">
        <xsl:param name="indent" />
	
	<!-- a local class that doesn't need contracts and test neither documentation -->
	
        <xsl:apply-templates select="ClsSign">
            <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:apply-templates select="ClsLinks" />

	
        <xsl:call-template name="beginSourceCopiedLine">
            <xsl:with-param name="indent" select="$indent" />
            <xsl:with-param name="forceNumber" select="@start" />
        </xsl:call-template>
        <xsl:value-of select="concat('{',$cr)" />

	<!-- Features : variables and methods -->
	
        <xsl:apply-templates select="Features" mode="intern">
            <xsl:with-param name="indent" select="$indent+1" />
        </xsl:apply-templates>
	
        <xsl:call-template name="beginSourceCopiedLine">
            <xsl:with-param name="indent" select="$indent" />
            <xsl:with-param name="forceNumber" select="@end" />
        </xsl:call-template>
        <xsl:value-of select="concat('}',$cr)" />

    </xsl:template>


<!-- ****************************************************************** -->
    <xsl:template match="Class" mode="interface">
        <xsl:param name="indent" />
	
	<!-- a local class that doesn't need contracts and test neither documentation -->
	
        <xsl:apply-templates select="ClsSign">
            <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:apply-templates select="ClsLinks" />

	
        <xsl:call-template name="beginSourceCopiedLine">
            <xsl:with-param name="indent" select="$indent" />
            <xsl:with-param name="forceNumber" select="@start" />
        </xsl:call-template>
        <xsl:value-of select="concat('{',$cr)" />

	<!-- Features : variables and methods -->
	
        <xsl:apply-templates select="Features">
            <xsl:with-param name="indent" select="$indent+1" />
        </xsl:apply-templates>
	
        <xsl:call-template name="beginSourceCopiedLine">
            <xsl:with-param name="indent" select="$indent" />
            <xsl:with-param name="forceNumber" select="@end" />
        </xsl:call-template>
        <xsl:value-of select="concat('}',$cr)" />

    </xsl:template>





<!-- ****************************************************************** -->
<!-- Imports, Class Signature, Implement, Inherits ******************** -->
<!-- ****************************************************************** -->



<!-- ****************************************************************** -->
    <xsl:template match="Suppliers">

	<!-- List of Imported class or packages -->
	
        <xsl:apply-templates select="Supplier" />
        <xsl:value-of select="$cr" />

    </xsl:template>


<!-- ****************************************************************** -->
    <xsl:template match="Supplier">

	<!-- one line of import, keyword 'import ' followed by the package or class name -->
	
        <xsl:text>import </xsl:text>
        <xsl:value-of select="text()" />
        <xsl:text>;</xsl:text>
        <xsl:value-of select="$cr" />

    </xsl:template>


<!-- ****************************************************************** -->
    <xsl:template match="ClsSign">
        <xsl:param name="indent" select="0" />

	<!-- declaration line of the class, without extends and implements -->
	
        <xsl:call-template name="beginSourceCopiedLine">
            <xsl:with-param name="indent" select="$indent" />
        </xsl:call-template>
	
        <xsl:apply-templates select="Visibility" />
        <xsl:apply-templates select="Qualifiers" />
		<xsl:if test="not(Qualifiers)">
			<xsl:text>public </xsl:text>
		</xsl:if>
	
        <xsl:choose>
            <xsl:when test="../@interface = 'yes'">
                <xsl:text>interface </xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>class </xsl:text>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:value-of select="../Ident/@name" />

    </xsl:template>


<!-- ****************************************************************** -->
    <xsl:template match="ClsLinks">
	
	<!-- links : inherited class, and implemented interfaces -->
	
        <xsl:apply-templates select="Inherit" />
        <xsl:apply-templates select="Implements" />
        <xsl:value-of select="$cr" />

    </xsl:template>


<!-- ****************************************************************** -->
    <xsl:template match="Inherit"> 

	<!-- inherited class, keyword extends followed by the name of the class inherited -->
	
        <xsl:text> extends </xsl:text>
        <xsl:apply-templates select="Parent" />

    </xsl:template>


<!-- ****************************************************************** -->
    <xsl:template match="Implements">
	
	<!-- implemented interfaces, keyword implement followed by the list of implements -->
	
        <xsl:text> implements </xsl:text>
        <xsl:apply-templates select="Parent" />

    </xsl:template>


<!-- ****************************************************************** -->
    <xsl:template match="Parent">

	<!-- a class inherited or implemented -->
	
        <xsl:value-of select="." />

	<!-- if there is a list of interfaces, we write a ', ' between each of them -->
        <xsl:if test="position() &lt; last()">
            <xsl:text>, </xsl:text>
        </xsl:if>

    </xsl:template>




</xsl:stylesheet>