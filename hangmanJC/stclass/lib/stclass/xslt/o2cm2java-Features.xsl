<?xml version="1.0" encoding='ISO-8859-1' standalone='yes' ?>


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="text" encoding="ISO-8859-1" omit-xml-declaration="yes"/>





<!-- ****************************************************************** -->
<!-- * Features : methods, variables, initializers and inner classes ** -->
<!-- ****************************************************************** -->


<!-- ****************************************************************** -->
  <xsl:template match="Features">
    <xsl:param name="indent" />

        <!-- Features of the class sorted by category -->

    <xsl:value-of select="$cr" />

    <xsl:text>// Features : </xsl:text>
    <xsl:value-of select="attribute::comment" />
    <xsl:text>----------------------------------</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:value-of select="$cr" />

    <xsl:apply-templates select="Variable">
      <xsl:sort data-type="number" select="./VarSign/@line"/>
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>
                <!-- 
        <xsl:apply-templates select="Method">
            <xsl:with-param name="indent" select="$indent+1" />
        </xsl:apply-templates>
                 -->
                <!-- Sort Methods in ascending order (use line numbering) -->
    <xsl:apply-templates select="Method">
      <xsl:sort data-type="number" select="@start"/>
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>
                <!--  -->
    <xsl:apply-templates select="InstInit">
      <xsl:with-param name="indent" select="$indent+1"/>
    </xsl:apply-templates>
    <xsl:apply-templates select="ClsInit">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>
    <xsl:apply-templates select="Class" mode="intern">
      <xsl:with-param name="indent" select="$indent+1"/>
    </xsl:apply-templates>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Features" mode="intern">
    <xsl:param name="indent" />

        <!-- Features of the class sorted by category -->

    <xsl:apply-templates select="Variable" mode="intern">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>
                <!--
        <xsl:apply-templates select="Method" mode="intern">
            <xsl:with-param name="indent" select="$indent+1" />
        </xsl:apply-templates>
                 -->
                <!-- Sort Methods in ascending order (use line numbering) -->
    <xsl:apply-templates select="Method" mode="intern">
      <xsl:sort data-type="number" select="@start"/>
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>
                <!--  -->
    <xsl:apply-templates select="InstInit" mode="intern">
      <xsl:with-param name="indent" select="$indent+1"/>
    </xsl:apply-templates>
    <xsl:apply-templates select="ClsInit" mode="intern">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>
    <xsl:apply-templates select="Class" mode="intern">
      <xsl:with-param name="indent" select="$indent+1"/>
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Ident" mode="feature">

        <!-- Identity of a feature element, write a single line of javadoc : ' * .......'
             without the beginning '/*(*)' and the end '*/' -->

    <xsl:call-template name="docLine">
      <xsl:with-param name="prefix" select="' '" />
      <xsl:with-param name="content" select="attribute::role" />
    </xsl:call-template>

  </xsl:template>





<!-- ****************************************************************** -->
<!-- * Methods (and constructors) ************************************* -->
<!-- ****************************************************************** -->



<!-- ****************************************************************** -->
  <xsl:template match="Method">
    <xsl:param name="indent" />

        <!-- One method of the class, first process the documentation, then the declaration,
             several details as return value holder and @pre holder, the checking of invariants at entry,
             the preconditions, the real code of the method, then the postconditions, the invariant at exit,
             and the return statement -->

    <xsl:variable name="abstract">  <!--<xsl:variable name="abstract2">-->
      <xsl:value-of select="boolean(MethSign/Qualifiers/Qualifier[@value = 'abstract']) or (../../@interface = 'yes')" />
    </xsl:variable>
        <!--<xsl:variable name="abstract" select="concat('',$abstract2)" />-->
        
        <!-- Documentation, contains just the Identity (Ident) of the method -->

    <xsl:call-template name="beginJavadocComment">
      <xsl:with-param name="prefix" select="' '" />
    </xsl:call-template>

    <xsl:apply-templates select="Ident" mode="feature" />

    <xsl:call-template name="endComment">
      <xsl:with-param name="prefix" select="' '" />
    </xsl:call-template>


                <!-- Method Signature -->

    <xsl:apply-templates select="MethSign">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="abstract" select="$abstract" />
    </xsl:apply-templates>

    <xsl:if test="$abstract = 'false'">
            <!--xsl:apply-templates select="." mode="code">
                <xsl:with-param name="indent" select="$indent+1" />
            </xsl:apply-templates-->
      <xsl:call-template name="methodCode">
        <xsl:with-param name="indent" select="$indent+1" />
      </xsl:call-template>
    </xsl:if>


  </xsl:template>

        
<!-- ****************************************************************** -->
  <!--xsl:template match="Method" mode="code"-->
  <xsl:template name="methodCode">
    <xsl:param name="indent" />
                

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="forceNumber" select="@start" />
    </xsl:call-template>
    <xsl:value-of select="concat('{',$cr)" />


    <!-- Code of the Method -->
        
    <!-- Manage the keyword super() for redefined constructor, if super(..) 
         exists it must be the first line of code in the Method -->
        
    <!--xsl:if test="boolean(MethSign/Type)"  DD 20061103 -->
    <xsl:if test="../@category = 'constructor'">
      <xsl:apply-templates select="BlocCode/Slist/Stat[position()=1]" 
                           mode="checkSuper">
        <xsl:with-param name="indent" select="$indent+1" />
        <xsl:with-param name="context" select="'beginMethod'" />
      </xsl:apply-templates>
      <!--xsl:text>// super detected</xsl:text><xsl:value-of select="$cr" /-->
    </xsl:if>


                <!-- If the method has no-void return type, we create a variable '__return_value_holder' to
                     store the result of returns statement  -->
    <xsl:call-template name="createReturnHolder">
      <xsl:with-param name="indent" select="$indent+1" />
      <xsl:with-param name="context" select="'beginMethod'" />
    </xsl:call-template>
                
                <!-- Invariant and preconditions -->

    <xsl:apply-templates select="." mode="beginContractsManagement">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>

                <!-- __ststub().profile() -->
                <!-- there's no more __ststub().profile(), uses JIP profiler lib instead -->
                <!-- 
                <xsl:if test="../@category != 'constructor'">
                        <xsl:call-template name="beginTestManagmentLine">
                                <xsl:with-param name="indent" select="$indent+2" />
                        </xsl:call-template>
                        <xsl:text>__ststub().profile();</xsl:text>
                        <xsl:value-of select="$cr" />
                </xsl:if>
                 -->
                
                <!-- modified : no breakBlock anymore (useless)
        <xsl:if test="MethSign/Type/@value != 'void'">
            <xsl:call-template name="beginSourceCopiedLine">
                <xsl:with-param name="indent" select="$indent+2" />
            </xsl:call-template>
            <xsl:text>breakBlock:{</xsl:text>
            <xsl:value-of select="$cr" />
        </xsl:if>
                 -->
                <!-- The real code, copied from the original source -->

    <xsl:apply-templates select="BlocCode">
      <xsl:with-param name="indent" select="$indent+3" />
    </xsl:apply-templates>
        
                
        <!-- <xsl:if test="MethSign/Type/@value != 'void'"> -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>} catch(Exception _ex) {</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:text>throw new RuntimeException(_ex.getMessage());</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />
        <!-- </xsl:if> -->

                <!-- postconditions and invariant -->

    <xsl:apply-templates select="." mode="endContractsManagement">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>

        
                <!-- The last codeline is the 'return __return_value_holder;' if the method need it -->

    <xsl:call-template name="createReturnHolder">
      <xsl:with-param name="indent" select="$indent+1" />
      <xsl:with-param name="context" select="'endMethod'" />
    </xsl:call-template>

                
                <!-- End of the method : '} // ....' -->

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="forceNumber" select="@end" />
    </xsl:call-template>
    <xsl:value-of select="concat('}',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Method" mode="intern">
    <xsl:param name="indent" />

                <!-- Method Signature -->

    <xsl:apply-templates select="MethSign">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

                <!-- Code of the Method -->

    <xsl:apply-templates select="BlocCode/Slist">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>



<!-- ****************************************************************** -->
  <xsl:template match="MethSign">

        <!-- Signature of a method, Visibility, Qualifiers, return type, name, and arguments -->

    <xsl:param name="indent" />
    <xsl:param name="abstract" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:apply-templates select="Visibility" />
    <xsl:apply-templates select="Qualifiers" />
    <xsl:apply-templates select="Type" />

    <xsl:value-of select="../Ident/@name" />

    <xsl:text>(</xsl:text>
    <xsl:apply-templates select="Arguments" />
    <xsl:text>)</xsl:text>

    <xsl:if test="$abstract = 'true'">
      <xsl:text>;</xsl:text>
    </xsl:if>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Arguments">

        <!-- arguments of a method -->

        <!--<xsl:value-of select="$cr" />
        <xsl:value-of select="$tab" />-->
    <xsl:apply-templates select="Arg" />
        <!--<xsl:value-of select="$tab" />-->

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Arg">

        <!-- one argument of a method -->

    <xsl:if test="@dir = 'const'">
      <xsl:text>final </xsl:text>
    </xsl:if>

    <xsl:apply-templates select="Type" />
    <xsl:value-of select="Ident/@name" />

    <xsl:if test="position() &lt; last()">
      <xsl:text>, </xsl:text>
    </xsl:if>

        <!--<xsl:value-of select="$tab" />
        <xsl:text>// </xsl:text>
        <xsl:value-of select="Ident/@role" />
        <xsl:value-of select="$cr" />-->

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="createReturnHolder">

    <xsl:param name="indent" />
    <xsl:param name="context" />

    <xsl:variable name="type" select="MethSign/Type/@value" />

    <xsl:variable name="defaultInit">
      <xsl:call-template name="getDefaultInitValue">
        <xsl:with-param name="type" select="$type" />
      </xsl:call-template>
    </xsl:variable>

    <xsl:if test="$type != 'void'">
      <xsl:choose>
        <xsl:when test="$context = 'beginMethod'">
          <xsl:call-template name="beginSourceCopiedLine">
            <xsl:with-param name="indent" select="$indent" />
            <xsl:with-param name="forcedNumber" select="0" />
          </xsl:call-template>
          <xsl:value-of select="concat($type,' __return_value_holder_ = ',$defaultInit,';',$cr)" />
                                        <!-- modified : return value holder must be initialized -->
        </xsl:when>
        <xsl:when test="$context = 'endMethod'">
          <xsl:call-template name="beginSourceCopiedLine">
            <xsl:with-param name="indent" select="$indent" />
            <xsl:with-param name="forcedNumber" select="0" />
          </xsl:call-template>
          <xsl:value-of select="concat('return __return_value_holder_;',$cr)" />
        </xsl:when>
      </xsl:choose>
    </xsl:if>

  </xsl:template>





<!-- ****************************************************************** -->
<!-- * Variables ****************************************************** -->
<!-- ****************************************************************** -->



<!-- ****************************************************************** -->
  <xsl:template match="Variable">
    <xsl:param name="indent" />

        <!-- Variable of the class -->

                <!-- documentation -->
    <xsl:call-template name="beginJavadocComment">
      <xsl:with-param name="prefix" select="' '" />
    </xsl:call-template>

    <xsl:apply-templates select="Ident" mode="feature" />

    <xsl:call-template name="endComment">
      <xsl:with-param name="prefix" select="' '" />
    </xsl:call-template>

                <!-- declaration -->
    <xsl:apply-templates select="VarSign">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

    <xsl:value-of select="$cr" />
    <xsl:value-of select="$cr" />


  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Variable" mode="intern">
    <xsl:param name="indent" />

        <!-- Variable of the class -->

    <xsl:apply-templates select="VarSign">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="VarSign">

        <!-- Signature of a variable -->

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:apply-templates select="Visibility" />
    <xsl:apply-templates select="Qualifiers" />
    <xsl:apply-templates select="Type" />

    <xsl:value-of select="../Ident/@name" />
    <xsl:apply-templates select="Value">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>;</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Value">
    <xsl:param name="indent" />

        <!-- Default value of a variable -->
    <xsl:text> = </xsl:text>
    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:apply-templates select="ArrayInit">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>


  </xsl:template>





<!-- ****************************************************************** -->
<!-- * Initializers *************************************************** -->
<!-- ****************************************************************** -->


<!-- ****************************************************************** -->
  <xsl:template match="ClsInit">
    <xsl:param name="indent" />
        
        <!-- static initializer of a class -->

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent"/>
      <xsl:with-param name="forceNumber" select="@start"/>
    </xsl:call-template>
    <xsl:value-of select="concat('static {', $cr)"/>

    <xsl:apply-templates select="BlocCode/Slist" mode="dependant">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent"/>
      <xsl:with-param name="forceNumber" select="@end"/>
    </xsl:call-template>
    <xsl:value-of select="concat('}', $cr)"/>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="InstInit">
    <xsl:param name="indent" />
        
        <!-- static initializer of a class -->

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent"/>
      <xsl:with-param name="forceNumber" select="@start"/>
    </xsl:call-template>
    <xsl:value-of select="concat('{', $cr)"/>

    <xsl:apply-templates select="BlocCode/Slist" mode="dependant">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent"/>
      <xsl:with-param name="forceNumber" select="@end"/>
    </xsl:call-template>
    <xsl:value-of select="concat('}', $cr)"/>

  </xsl:template>


</xsl:stylesheet>
