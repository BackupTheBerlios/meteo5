<?xml version="1.0" encoding='ISO-8859-1' standalone='yes' ?>


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="text" encoding="ISO-8859-1" omit-xml-declaration="yes"/>



<!-- ****************************************************************** -->
<!-- * BLOCK OF CODE and others MAIN METHODS ************************** -->
<!-- ****************************************************************** -->

<!-- ****************************************************************** -->
  <xsl:template match="BlocCode">

    <xsl:param name="indent" />

    <xsl:apply-templates select="Slist" mode="blocCode">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Slist" mode="blocCode">

    <xsl:param name="indent" />

    <xsl:apply-templates select="Stat[position()=1]" mode="checkSuper">
      <xsl:with-param name="indent" select="$indent+1" />
      <xsl:with-param name="context" select="'copyCode'" />
    </xsl:apply-templates>

    <xsl:apply-templates select="Stat[position() > 1]">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>

  </xsl:template>





<!-- ****************************************************************** -->
<!-- * STATEMENTS ***************************************************** -->
<!-- ****************************************************************** -->



<!-- ****************************************************************** -->
  <xsl:template match="Slist">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat('{',$cr)" />

    <xsl:apply-templates select="Stat">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat('}',$cr)" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Slist" mode="dependant">
    <xsl:param name="indent" />
    <xsl:apply-templates select="*">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Slist" mode="preambdecl">
    <xsl:param name="indent" />
    <xsl:apply-templates select="*[boolean(descendant::VariableDef)]">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Slist" mode="preamb">
    <xsl:param name="indent" />
    <xsl:apply-templates select="*[not(boolean(descendant::VariableDef))]">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Stat">
    <xsl:param name="indent" />
    <xsl:variable name="newIndent">
      <xsl:choose>
        <xsl:when test="boolean(child::Slist)">
          <xsl:value-of select="$indent" />
        </xsl:when>
        <xsl:when test="name(parent::*) = 'Slist'">
          <xsl:value-of select="$indent" />
        </xsl:when>
        <xsl:otherwise>
          <!-- only 1 stat in a For, If, While, etc -->
          <xsl:value-of select="$indent+1"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <xsl:apply-templates select="*">
      <xsl:with-param name="indent" select="$newIndent" />
    </xsl:apply-templates>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Stat" mode="checkSuper">

    <xsl:param name="indent" />
    <xsl:param name="context" select="'normal'" />

    <xsl:variable name="firstExprIsSuper" 
                  select="boolean(descendant::MethodCall/Expression/PrimaryExpression/BuiltInExpr[@type='super'])" />

    <xsl:choose>
      <xsl:when test="($context = 'copyCode') and ($firstExprIsSuper = true())">
        <!-- nothing -->
      </xsl:when>
      <xsl:when test="($context = 'copyCode') and ($firstExprIsSuper = false())">
        <xsl:apply-templates select="*">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:when test="($context = 'beginMethod') and ($firstExprIsSuper = true())">
        <xsl:apply-templates select="*">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:when test="($context = 'beginMethod') and ($firstExprIsSuper = false())">
        <!-- nothing -->
      </xsl:when>
    </xsl:choose>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Stat/Expression">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:apply-templates select="*">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

    <xsl:value-of select="concat(';',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="EmptyStat">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>;</xsl:text>
    <xsl:value-of select="$cr"/>

  </xsl:template>



<!-- ****************************************************************** -->
  <xsl:template match="VariableDef">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:value-of select="concat(@type,' ',@name)" />

    <xsl:apply-templates select="VarInit">
      <xsl:with-param name="indent" select="$indent"/>
    </xsl:apply-templates>

    <xsl:value-of select="concat(';',$cr)" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="VarInit">
    <xsl:param name="indent" />

    <xsl:text> = </xsl:text>

    <xsl:apply-templates select="*">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="ArrayInit">
    <xsl:param name="indent" />
    <xsl:text>{</xsl:text>
    <xsl:for-each select="*">
            <!-- MODIFIED -->
      <xsl:apply-templates select="*">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:apply-templates>
      <xsl:if test="position() &lt; last()">
        <xsl:text>, </xsl:text>
      </xsl:if>
    </xsl:for-each>
    <xsl:text>}</xsl:text>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Label">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:value-of select="concat(@name,' : ',$cr)" />

    <xsl:apply-templates select="Stat">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="If">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:text>if (</xsl:text>
    <xsl:apply-templates select="Expression" />
    <xsl:value-of select="concat(')',$cr)" />

    <xsl:apply-templates select="Stat[position() = 1]">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

    <xsl:if test="boolean(Stat[position() = 2])">
      <xsl:call-template name="beginSourceCopiedLine">
        <xsl:with-param name="indent" select="$indent"/>
      </xsl:call-template>
      <xsl:text>else</xsl:text>
      <xsl:value-of select="$cr" />

      <xsl:apply-templates select="Stat[position() = 2]">
        <xsl:with-param name="indent" select="$indent"/>
      </xsl:apply-templates>
    </xsl:if>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="For">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:text>for (</xsl:text>
    <xsl:apply-templates select="ForInit">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>; </xsl:text>
    <xsl:apply-templates select="ForCond">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>; </xsl:text>
    <xsl:apply-templates select="ForIter">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>)</xsl:text>
    <xsl:value-of select="$cr" />

    <xsl:apply-templates select="Stat">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="ForInit">
    <xsl:param name="indent" />

    <xsl:if test="boolean(child::VariableDef)">
      <xsl:value-of select="concat(VariableDef/@type,' ',VariableDef/@name)" />
      <xsl:apply-templates select="VariableDef/VarInit">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:apply-templates>
    </xsl:if>

    <xsl:if test="boolean(child::ExpressionList)">
      <xsl:apply-templates select="ExpressionList">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:apply-templates>
    </xsl:if>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="ForCond">
    <xsl:param name="indent" />

    <xsl:apply-templates select="*">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="ForIter">
    <xsl:param name="indent" />

    <xsl:apply-templates select="*">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="While">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>while (</xsl:text>
    <xsl:apply-templates select="Expression"/>
    <xsl:text>)</xsl:text>
    <xsl:value-of select="$cr" />

    <xsl:apply-templates select="Stat">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Do">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>Do</xsl:text>
    <xsl:value-of select="$cr" />

    <xsl:apply-templates select="Stat">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>while (</xsl:text>
    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>)</xsl:text>
    <xsl:value-of select="$cr" />


  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Break">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat('break ',@label,';',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Continue">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat('continue ',@label,';',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Return">
    <xsl:param name="indent" />

    <xsl:variable name="isInInternalClass">
      <xsl:value-of select="boolean(ancestor::AnonymousClass| ancestor::TypeDef)" />
    </xsl:variable>

    <xsl:choose>
      <xsl:when test="$isInInternalClass = 'true'">
        <xsl:apply-templates select="." mode="withoutContract">
          <xsl:with-param name="indent" select="$indent"/>
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="." mode="withContract">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Return" mode="withContract">
    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:choose>
      <xsl:when test="boolean(Expression)">
        <xsl:text>__return_value_holder_ = (</xsl:text>
        <xsl:apply-templates select="Expression">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:text>);</xsl:text>
        <xsl:value-of select="$cr" />
        <xsl:call-template name="beginSourceCopiedLine">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:call-template>
                                <!-- no breakBlock anymore -->
                <!-- <xsl:text>break breakBlock;</xsl:text> -->
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>return;</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Return" mode="withoutContract">
    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:text>return</xsl:text>
    <xsl:if test="boolean(Expression)">
      <xsl:text> </xsl:text>
      <xsl:apply-templates select="Expression">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:apply-templates>
    </xsl:if>
    <xsl:text>;</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Switch">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:text>switch (</xsl:text>
    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>) {</xsl:text>
    <xsl:value-of select="$cr" />

    <xsl:apply-templates select="CaseGroup">
      <xsl:with-param name="indent" select="$indent+1"/>
    </xsl:apply-templates>

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent"/>
    </xsl:call-template>
    <xsl:value-of select="concat('}',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="CaseGroup">

    <xsl:param name="indent" />

    <xsl:apply-templates select="Case|DefaultCase">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

    <xsl:apply-templates select="Slist" mode="dependant">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Case">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:text>case </xsl:text>
    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text> :</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="DefaultCase">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:text>default :</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Throw">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:text>throw (</xsl:text>
    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>);</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Synchronized">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>

    <xsl:text>synchronized (</xsl:text>
    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>)</xsl:text>
    <xsl:value-of select="$cr" />

    <xsl:apply-templates select="Stat">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TryBlock">

    <xsl:param name="indent" />

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat('try {',$cr)" />

    <xsl:apply-templates select="Slist[position() = 1]" mode="dependant">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>

    <xsl:apply-templates select="Handler">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

    <xsl:if test="boolean(Slist[position() = 2])">
      <xsl:value-of select="concat(' finally {',$cr)" />
      <xsl:apply-templates select="Slist[position() = 2]" mode="dependant">
        <xsl:with-param name="indent" select="$indent+1" />
      </xsl:apply-templates>
      <xsl:call-template name="beginSourceCopiedLine">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:call-template>
      <xsl:text>}</xsl:text>
    </xsl:if>

    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Handler">

    <xsl:param name="indent" />

    <xsl:text> catch(</xsl:text>
    <xsl:value-of select="concat(VariableDef/@type,' ',VariableDef/@name)" />
    <xsl:text>) {</xsl:text>
    <xsl:value-of select="$cr" />

    <xsl:apply-templates select="Slist" mode="dependant">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>

    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>


  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TypeDef">
    <xsl:param name="indent" />

    <xsl:apply-templates select="Class" mode="intern">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>





<!-- ****************************************************************** -->
<!-- * EXPRESSIONS **************************************************** -->
<!-- ****************************************************************** -->



<!-- ****************************************************************** -->
  <xsl:template match="Expression">
    <xsl:param name="indent" />

    <xsl:apply-templates select="*">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TernaryExpression">
    <xsl:param name="indent" />

    <xsl:choose>
      <xsl:when test="@type = 'question'">
        <xsl:text>(</xsl:text>
        <xsl:apply-templates select="Expression[position() = 1]">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:text>) ? (</xsl:text>
        <xsl:apply-templates select="Expression[position() = 2]">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:text>) : (</xsl:text>
        <xsl:apply-templates select="Expression[position() = 3]">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:text>)</xsl:text>
      </xsl:when>
    </xsl:choose>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="BinaryExpression[@type != 'implies']">
    <xsl:param name="indent" />

    <xsl:variable name="inter">
      <xsl:choose>
        <xsl:when test="@type = 'assign'">
          <xsl:value-of select="' = '" />
        </xsl:when>
        <xsl:when test="@type = 'plusAssign'">
          <xsl:value-of select="' += '" />
        </xsl:when>
        <xsl:when test="@type = 'minusAssign'">
          <xsl:value-of select="' -= '" />
        </xsl:when>
        <xsl:when test="@type = 'starAssign'">
          <xsl:value-of select="' *= '" />
        </xsl:when>
        <xsl:when test="@type = 'divAssign'">
          <xsl:value-of select="' /= '" />
        </xsl:when>
        <xsl:when test="@type = 'modAssign'">
          <xsl:value-of select="' %= '" />
        </xsl:when>
        <xsl:when test="@type = 'srAssign'">
          <xsl:value-of select="' &gt;&gt;= '" />
        </xsl:when>
        <xsl:when test="@type = 'bsrAssign'">
          <xsl:value-of select="' &gt;&gt;&gt;= '" />
        </xsl:when>
        <xsl:when test="@type = 'slAssign'">
          <xsl:value-of select="' &lt;&lt;= '" />
        </xsl:when>
        <xsl:when test="@type = 'bandAssign'">
          <xsl:value-of select="' &amp;= '" />
        </xsl:when>
        <xsl:when test="@type = 'bxorAssign'">
          <xsl:value-of select="' ^= '" />
        </xsl:when>
        <xsl:when test="@type = 'borAssign'">
          <xsl:value-of select="' |= '" />
        </xsl:when>
        <xsl:when test="@type = 'lor'">
          <xsl:value-of select="' || '" />
        </xsl:when>
        <xsl:when test="@type = 'land'">
          <xsl:value-of select="' &amp;&amp; '" />
        </xsl:when>
        <xsl:when test="@type = 'bor'">
          <xsl:value-of select="' | '" />
        </xsl:when>
        <xsl:when test="@type = 'bxor'">
          <xsl:value-of select="'^'" />
        </xsl:when>
        <xsl:when test="@type = 'band'">
          <xsl:value-of select="' &amp; '" />
        </xsl:when>
        <xsl:when test="@type = 'equal'">
          <xsl:value-of select="' == '" />
        </xsl:when>
        <xsl:when test="@type = 'notEqual'">
          <xsl:value-of select="' != '" />
        </xsl:when>
        <xsl:when test="@type = 'lt'">
          <xsl:value-of select="' &lt; '" />
        </xsl:when>
        <xsl:when test="@type = 'gt'">
          <xsl:value-of select="' &gt; '" />
        </xsl:when>
        <xsl:when test="@type = 'le'">
          <xsl:value-of select="' &lt;= '" />
        </xsl:when>
        <xsl:when test="@type = 'ge'">
          <xsl:value-of select="' &gt;= '" />
        </xsl:when>
        <xsl:when test="@type = 'sl'">
          <xsl:value-of select="' &lt;&lt; '" />
        </xsl:when>
        <xsl:when test="@type = 'sr'">
          <xsl:value-of select="' &gt;&gt; '" />
        </xsl:when>
        <xsl:when test="@type = 'bsr'">
          <xsl:value-of select="' &gt;&gt;&gt; '" />
        </xsl:when>
        <xsl:when test="@type = 'plus'">
          <xsl:value-of select="' + '" />
        </xsl:when>
        <xsl:when test="@type = 'minus'">
          <xsl:value-of select="' - '" />
        </xsl:when>
        <xsl:when test="@type = 'div'">
          <xsl:value-of select="' / '" />
        </xsl:when>
        <xsl:when test="@type = 'mod'">
          <xsl:value-of select="'%'" />
        </xsl:when>
        <xsl:when test="@type = 'star'">
          <xsl:value-of select="' * '" />
        </xsl:when>
        <xsl:when test="@type = 'instanceof'">
          <xsl:value-of select="' instanceof '" />
        </xsl:when>
        <xsl:when test="@type = 'dot'">
          <xsl:value-of select="'.'"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="@type" />
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <xsl:variable name="parentExpr1">
      <xsl:choose>
        <xsl:when test="@type = 'dot'">
          <xsl:value-of select="boolean(Expression[position() = 1]/TernaryExpression | Expression[position() = 1]/BinaryExpression[@type != 'dot'] | Expression[position() = 1]/TypeCast | Expression[position() = 1]/MethodCall)" /><!-- modified -->
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="boolean(Expression[position() = 1]/TernaryExpression|Expression[position() = 1]/BinaryExpression[@type != 'dot'])" /><!-- modified -->
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="parentExpr2">
      <xsl:choose>
        <xsl:when test="@type = 'dot'">
          <xsl:value-of select="boolean(Expression[position() = 2]/TernaryExpression | Expression[position() = 2]/BinaryExpression[@type != 'dot'])" /><!-- modified -->
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="boolean(Expression[position() = 2]/TernaryExpression|Expression[position() = 2]/BinaryExpression[@type != 'dot'])" /><!-- modified -->
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="$parentExpr1 = 'true'">
      <xsl:text>(</xsl:text>
    </xsl:if>
    <xsl:apply-templates select="Expression[position() = 1]">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:if test="$parentExpr1 = 'true'">
      <xsl:text>)</xsl:text>
    </xsl:if>

    <xsl:value-of disable-output-escaping="yes" select="$inter" />

    <xsl:if test="$parentExpr2 = 'true'">
      <xsl:text>(</xsl:text>
    </xsl:if>
    <xsl:apply-templates select="Expression[position() = 2]">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:if test="$parentExpr2 = 'true'">
      <xsl:text>)</xsl:text>
    </xsl:if>


  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="UnaryExpression[@type != 'pre']">
    <xsl:param name="indent" />

    <xsl:variable name="before">
      <xsl:choose>
        <xsl:when test="@type = 'inc'">
          <xsl:value-of select="'++'" />
        </xsl:when>
        <xsl:when test="@type = 'dec'">
          <xsl:value-of select="'--'" />
        </xsl:when>
        <xsl:when test="@type = 'bnot'">
          <xsl:value-of select="'~'" />
        </xsl:when>
        <xsl:when test="@type = 'lnot'">
          <xsl:value-of select="'!'" />
        </xsl:when>
        <xsl:when test="@type = 'unaryMinus'">
          <xsl:value-of select="'-'" />
        </xsl:when>
        <xsl:when test="@type = 'unaryPlus'">
          <xsl:value-of select="'+'" />
        </xsl:when>
      </xsl:choose>
    </xsl:variable>

    <xsl:variable name="after">
      <xsl:choose>
        <xsl:when test="@type = 'postInc'">
          <xsl:value-of select="'++'" />
        </xsl:when>
        <xsl:when test="@type = 'postDec'">
          <xsl:value-of select="'--'" />
        </xsl:when>
      </xsl:choose>
    </xsl:variable>

    <xsl:variable name="parentExpr" select="boolean(Expression//TernaryExpression|Expression//BinaryExpression[@type != 'dot'])" />

    <xsl:value-of select="$before" />

    <xsl:if test="$parentExpr">
      <xsl:text>(</xsl:text>
    </xsl:if>
    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:if test="$parentExpr">
      <xsl:text>)</xsl:text>
    </xsl:if>

    <xsl:value-of select="$after" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="PrimaryExpression">
    <xsl:param name="indent" />

    <xsl:apply-templates select="*">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Identifier">

    <xsl:value-of select="text()" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="MethodCall">
    <xsl:param name="indent" />

    <xsl:variable name="parentExpr" select="boolean(Expression//TernaryExpression|Expression//BinaryExpression)" />

    <xsl:if test="parentExpr">
      <xsl:text>(</xsl:text>
    </xsl:if>
    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:if test="parentExpr">
      <xsl:text>)</xsl:text>
    </xsl:if>

    <xsl:text>(</xsl:text>
    <xsl:apply-templates select="ExpressionList">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>)</xsl:text>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="BuiltInExpr">

    <xsl:value-of select="@type" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TypeCast">
    <xsl:param name="indent" />
        
                <!-- a TypeCast must surrounded with ( ), in order to call 
                     an eventually following method on the right type -->
    <xsl:text>(</xsl:text> <!-- modified -->

    <xsl:text>(</xsl:text>
    <xsl:value-of select="@type" />
    <xsl:text>) </xsl:text>

    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
                
    <xsl:text>) </xsl:text> <!-- modified -->

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="ArrayIndex">
    <xsl:param name="indent" />

    <xsl:apply-templates select="Expression[position() = 1]">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>[</xsl:text>
    <xsl:apply-templates select="Expression[position() = 2]">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>]</xsl:text>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="NewExpr">
    <xsl:param name="indent" />

    <xsl:text>new </xsl:text>
    <xsl:value-of select="@type" />
    <xsl:apply-templates select="*">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="NewArrayDecl">
    <xsl:param name="indent" />
    <!--NEW ARRAY-->
    <xsl:apply-templates select="ArrayDecl">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:if test="count(child::*) = 2">
      <!--xsl:text> NEW ARRAY INIT</xsl:text-->
      <xsl:apply-templates select="ArrayInit">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:apply-templates>
    </xsl:if>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="ArrayDecl">
    <xsl:param name="indent" />

    <xsl:text>[</xsl:text>
    <xsl:apply-templates select="Expression">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>]</xsl:text>
    <xsl:apply-templates select="ArrayDecl">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="NewObjectDecl">
    <xsl:param name="indent" />

    <xsl:text>(</xsl:text>
    <xsl:apply-templates select="ExpressionList">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
    <xsl:text>)</xsl:text>
    <xsl:apply-templates select="AnonymousClass">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="AnonymousClass">
    <xsl:param name="indent" />

    <xsl:text> {</xsl:text>
    <xsl:value-of select="$cr" />
    <xsl:apply-templates select="Variable|Method|InstInit|ClsInit|Class" mode="intern">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:apply-templates>
    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:value-of select="concat('}',$cr)" />
    <xsl:call-template name="beginSourceCopiedLine">
      <xsl:with-param name="indent" select="$indent"/>
    </xsl:call-template>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Constant">
    <xsl:param name="indent" />

    <xsl:value-of select="text()" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="ExpressionList">
    <xsl:param name="indent" />

    <xsl:for-each select="Expression">
      <xsl:apply-templates select=".">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:apply-templates>
      <xsl:if test="position() &lt; last()">
        <xsl:text>, </xsl:text>
      </xsl:if>
    </xsl:for-each>

  </xsl:template>


</xsl:stylesheet>