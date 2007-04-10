<?xml version="1.0" encoding='ISO-8859-1' standalone='yes' ?>


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="text" encoding="ISO-8859-1" omit-xml-declaration="yes"/>

<!-- This stylesheet contains all the templates used to generate and manage
 contracts in the class. It is dependant on the stylesheet o2cm2java-Features
 which import it.-->



<!-- ****************************************************************** -->
<!-- * GLOBAL VARIABLES USED WITH CONTRACTS *************************** -->
<!-- ****************************************************************** -->

<!-- ****************************************************************** -->
  <xsl:variable name="testInContractEval">
    <!-- The beginning of a bloc which check the pre, post, and invariants -->
    <!-- bol : the beginning of a line -->
    <!-- shortType : pre, post, inv -->
    <xsl:text>****bol****if (! __in_contract_eval) {
****bol**** __in_contract_eval = true;
</xsl:text>
  </xsl:variable>

<!-- ****************************************************************** -->
  <xsl:variable name="beginOfPrePostInv">
    <!-- The beginning of a bloc which check the pre, post, and invariants -->
    <!-- bol : the beginning of a line -->
    <!-- shortType : pre, post, inv -->
    <xsl:text>****bol**** try {
****bol****  String __****shortType****_msg = null;
</xsl:text>
  </xsl:variable>

<!-- ****************************************************************** -->
  <xsl:variable name="endOfPrePostInv">
    <!-- The end of the bloc which check the pre, post ans invariants. -->
    <!-- bol : beginning of line -->
    <!-- lineNumber : line number" -->
    <!-- type : precondition, postcondition, invariant -->
    <!-- typeMAJ : PRECONDITION, POSTCONDITION, INVARIANT -->
    <!-- shortType : pre, post or inv -->
    <!-- methodName : name of method for pre and post, 
                      name of the class for invariant -->
    <xsl:text>****bol****  /*__in_contract_eval = false;*/
****bol****  if (__****shortType****_msg != null) {
****bol****   throw new org.stclass.runtime.ContractViolation (
****bol****          "\nERROR: ****className****.java[line ****lineNumber****]:"
****bol****          + " ****typeMAJ**** VIOLATED\n"
****bol****          + "(****methodName****" + __****shortType****_msg);
****bol****  }
****bol**** } catch ( RuntimeException __ex ) {
****bol****  __in_contract_eval = false;
****bol****  String txt = "";  
****bol****  if (__ex.getClass()==org.stclass.runtime.ContractViolation.class) {
****bol****   txt = __ex.toString(); 
****bol****  } else {
****bol****   txt = "\nERROR: ****className****.java[line ****lineNumber****]:"
****bol****         + " exception &lt;&lt;" + __ex 
****bol****         + "&gt;&gt;\n occured while evaluating ****type**** in "
****bol****         + "****methodName****";
****bol****  }
****bol****  throw new org.stclass.runtime.ContractViolation(txt);
****bol**** }
</xsl:text>
  </xsl:variable>

<!-- ****************************************************************** -->
  <xsl:variable name="beginInvMethod">
    <!-- The beginning of the method ckeck_invariant -->
    <!-- bol : beginning of line -->
    <!-- ClassLongName : name of the class with package name -->
    <xsl:text>****bol****// Tests the invariants of the class and its superclasses.
****bol****// This method is public to give subclasses acccess to the inv
****bol****public synchronized void __check_invariant__****ClassLongName**** ( 
****bol****      String location) throws org.stclass.runtime.ContractViolation {
</xsl:text>
  </xsl:variable>

  <xsl:variable name="invariantMethods">
    <!-- Methods and variables used to managed the invariant and contracts -->
    <xsl:text>****bol**** 
****bol****// Mark contract evaluation sequences to avoid contract 
****bol****// evaluation for methods that are called in contracts
****bol****private static boolean __in_contract_eval = false ;
****bol****
****bol****// The next methods keep track of calling chain to avoid recursive 
****bol****// invariant checks.
****bol****// Stores bookkeeping information - key: thread, value: call level
****bol****protected transient java.util.Hashtable __icl_ =
****bol****                                          new java.util.Hashtable(1);
****bol****
****bol****// Update bookkeeping of method nesting and check the invariant if 
****bol****// appropriate
****bol****private synchronized void __inv_check_at_entry__****ClassLongName**** (
****bol****                      Thread thread, String loc) 
****bol****                      throws org.stclass.runtime.ContractViolation {
****bol**** // perform lazy init after de-serialization (transient __icl_)
****bol**** if (__icl_ == null) __icl_ = new java.util.Hashtable(1);
****bol**** if ( !__icl_.containsKey(thread) ) {
****bol****  __icl_.put(thread, new Integer(1));
****bol****  __check_invariant__****ClassLongName****(loc);
****bol**** } else {
****bol****  __icl_.put(thread, 
****bol****  new Integer(((Integer)__icl_.get(thread)).intValue()+1));
****bol**** }
****bol****}
****bol****
****bol****// Update bookkeeping of method nesting and check the invariant 
****bol****// if appropriate
****bol****private synchronized void __inv_check_at_exit__****ClassLongName****(
****bol****                      Thread thread, String loc) 
****bol****                      throws org.stclass.runtime.ContractViolation {
****bol**** // perform lazy init after de-serialization (transient __icl_)
****bol**** if (__icl_ == null) __icl_ = new java.util.Hashtable(1);
****bol**** if (((Integer)__icl_.get(thread)).intValue() == 1 ) {
****bol****  try {
****bol****   __check_invariant__****ClassLongName****(loc);
****bol****  } finally {
****bol****   __icl_.remove(thread); // remove from bookkeeping,
****bol****   // before checking (resoliant wrt exceptions)
****bol****  }
****bol**** } else {
****bol****  __icl_.put(thread, 
****bol****  new Integer(((Integer)__icl_.get(thread)).intValue()-1));
****bol**** }
****bol****}
****bol****
****bol****// Update bookkeeping of method nesting DO NOT check the 
****bol****// invariant (i.e. for non-default constr.)
****bol****private synchronized void __inc_icl_at_entry__****ClassLongName**** (
****bol****                          Thread thread)  {
****bol**** // perform lazy init after de-serialization (transient __icl_)
****bol**** if (__icl_ == null) __icl_ = new java.util.Hashtable(1);
****bol**** if ( !__icl_.containsKey(thread) ) {
****bol****  __icl_.put(thread, new Integer(1));
****bol**** } else {
****bol****  __icl_.put(thread, 
****bol****  new Integer(((Integer)__icl_.get(thread)).intValue()+1));
****bol**** }
****bol****}
****bol****
</xsl:text>
  </xsl:variable>

  <xsl:variable name="precondEval">
    <!-- Method used to evaluate preconditions -->
    <xsl:text>****bol****
****bol****//evaluate preconditions
****bol****private static String __eval_pre_cond(Vector messages, int ind) {
****bol**** String ret;
****bol**** if( messages.elementAt(ind) == null ) {
****bol****  if ( ind+1 &lt; messages.size() ) {
****bol****   ret = __eval_pre_cond(messages, ind+1);
****bol****  } else {
****bol****   ret = null;
****bol****  }
****bol**** } else if ( messages.elementAt(ind).equals("void") ) {
****bol****  if ( ind+1 &lt; messages.size() ) {
****bol****   ret = __eval_pre_cond(messages, ind+1);
****bol****  } else {
****bol****   ret = null;
****bol****  }
****bol**** } else {
****bol****  if ( ind+1 &lt; messages.size() ) {
****bol****   if( __eval_pre_cond(messages, ind+1) == null ) {
****bol****    ret = messages.elementAt(ind) 
****bol****          + "\nContract consistence warning. "
****bol****          + "A precondition violation exception was raised by a "
****bol****          + "sub-class but not by a super-class or interface.";
****bol****   } else {
****bol****    ret = (String)messages.elementAt(ind);
****bol****   }
****bol****  } else {
****bol****   ret = (String)messages.elementAt(ind);
****bol****  }
****bol**** }
****bol**** return ret;
****bol****}
****bol****
</xsl:text>
  </xsl:variable>


<!-- ****************************************************************** -->     
<!-- * DEPTH TEMPLATES, USED TO CREATE BOOLEAN VARIABLES __eval ******* -->
<!-- ****************************************************************** -->     

<!-- ****************************************************************** -->
  <xsl:template match="Contracts|Invariant" mode="generateBooleanVariables">
    <xsl:param name="indent" />
        
    <xsl:variable name="maxDepth">
      <xsl:apply-templates select="." mode="depth" />
    </xsl:variable>
        
    <!-- Generate the boolean variables -->
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>boolean </xsl:text>
    <xsl:call-template name="genBooleanVar">
      <xsl:with-param name="depth" select="$maxDepth" />
    </xsl:call-template>
    <xsl:text>;</xsl:text>
    <xsl:value-of select="$cr" />
  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Contracts" mode="depth">
        
    <xsl:variable name="depthRequire">
      <xsl:apply-templates select="Require" mode="depth"/>
    </xsl:variable>
        
    <xsl:variable name="depthEnsure">
      <xsl:apply-templates select="Ensure" mode="depth"/>
    </xsl:variable>
        
    <xsl:choose>
      <xsl:when test="$depthEnsure &gt; $depthRequire">
        <xsl:value-of select="$depthEnsure" />
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$depthRequire"/>
      </xsl:otherwise>
    </xsl:choose>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Invariant" mode="depth">
        
    <xsl:apply-templates select="Invar" mode="depth" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Require|Ensure|Invar|InheritedRequire|InheritedEnsure|InheritedInvar" mode="depth">

    <!-- obtains the list of depths of all Element in Contracts or Invariant -->
        
    <xsl:variable name="listofmaxdepths">
      <!-- <xsl:for-each select="Assertion|InheritedRequire|InheritedEnsure|InheritedInvar"> -->
      <xsl:for-each select=".//Assertion"> <!-- modified : just count according to the number of Assertion nodes -->
        <xsl:apply-templates select="." mode="depth" />
      </xsl:for-each>
      <!-- 1 is the minimum possible -->
      <xsl:text>1-</xsl:text>
    </xsl:variable>

    <!-- obtains the higher depth -->
    <xsl:call-template name="maxdepth">
      <xsl:with-param name="depths" select="$listofmaxdepths" />
    </xsl:call-template>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Assertion" mode="depth">
        
    <xsl:variable name="firstExprIsEval">
      <!-- is the first expression an eval ? -->
      <xsl:apply-templates select="CodeContract/Expression/*" mode="isEval" />
    </xsl:variable>
        
    <xsl:variable name="initDepth">
      <!-- the minimal depth of this contract -->
      <xsl:choose>
        <xsl:when test="$firstExprIsEval = 'true'">
          <xsl:value-of select="1" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="2" />
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
        
    <xsl:apply-templates select="CodeContract/Expression" mode="depth">
      <xsl:with-param name="depth" select="$initDepth" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="BinaryExpression[@type='implies']|BinaryExpression[@type = 'land']|BinaryExpression[@type = 'lor']" mode="depth">
    <xsl:param name="depth"/>

    <!-- this Expression is an eval so it need a boolean variable, and it 
         increase depth -->
    <xsl:value-of select="concat($depth+1,'-')" />
        
    <xsl:variable name="child1IsEval">
      <!-- is the first child an eval ? -->
      <xsl:apply-templates select="Expression[position()=1]/*" mode="isEval" />
    </xsl:variable>
    <xsl:variable name="newDepth1">
      <!-- the new base of depth of the first child -->
      <xsl:choose>
        <xsl:when test="$child1IsEval = 'true'">
          <xsl:value-of select="$depth+1" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$depth+2" />
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <!-- search the depth in the first child -->
    <xsl:apply-templates select="Expression[position()=1]" mode="depth">
      <xsl:with-param name="depth" select="$newDepth1" />
    </xsl:apply-templates>
        
    <xsl:variable name="child2IsEval">
      <!-- is the second child an eval ? -->
      <xsl:apply-templates select="Expression[position()=2]/*" mode="isEval" />
    </xsl:variable>
    <xsl:variable name="nbEvalInChild1">
      <!-- number of eval in the first child -->
      <xsl:apply-templates select="Expression[position()=1]/*" mode="nbEval" />
    </xsl:variable>
    <xsl:variable name="newDepth2">
      <!-- the new base of depth of the second child -->
      <xsl:choose>
        <xsl:when test="$child2IsEval = 'true'">
          <xsl:value-of select="$depth+1" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$depth+2+$nbEvalInChild1" />
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <!-- search the depth in the second child -->
    <xsl:apply-templates select="Expression[position()=2]" mode="depth">
      <xsl:with-param name="depth" select="$newDepth2" />
    </xsl:apply-templates>

  </xsl:template>

<!-- *********************************************************************** -->
  <xsl:template match="Forall|Exists" mode="depth">
    <xsl:param name="depth"/>

    <!-- this Expression is an eval so it need a boolean variable, 
         it increase depth -->
    <xsl:value-of select="concat($depth+1,'-')" />
        
    <xsl:variable name="childIsEval">
      <!-- is the child an eval ? -->
      <xsl:apply-templates select="Expression/*" mode="isEval" />
    </xsl:variable>
        
    <!-- search the depth in the child -->
    <xsl:choose>
      <xsl:when test="$childIsEval = 'true'">
        <xsl:apply-templates select="Expression" mode="depth">
          <xsl:with-param name="depth" select="$depth + 1" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="Expression" mode="depth">
          <xsl:with-param name="depth" select="$depth + 2" />
        </xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="*" mode="depth">
    <xsl:param name="depth" />
        
    <xsl:for-each select="child::*">
      <xsl:variable name="nbInPrev">
        <!-- the number of evals in a possible child -->
        <xsl:apply-templates select="." mode="nbEvalInPreviousElement" />
      </xsl:variable>
      <!-- search depth in the current child -->
      <xsl:apply-templates select="." mode="depth">
        <xsl:with-param name="depth" select="$depth + $nbInPrev" />
      </xsl:apply-templates>
    </xsl:for-each>


  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="genBooleanVar">

    <!-- generate the boolean variables __evalXX.. for contract evaluation
         this template generate one couple of variable __evalX0, __evalX1 
         and so is recursive to generate all variables -->
        
    <xsl:param name="depth">
      <!-- depth defines the number of variables couple to create --></xsl:param>
    <xsl:param name="currentDepth" select="'1'" />
      <!-- the range of the current variables generated -->
        
    <xsl:if test="$currentDepth &lt;= $depth">
      <!-- write the couple if the currentDepth is lower or equal to the depth -->
      <xsl:text>__eval</xsl:text>
      <xsl:value-of select="concat($currentDepth,'0, ')" />
      <xsl:text>__eval</xsl:text>
      <xsl:value-of select="concat($currentDepth,'1')" />
    </xsl:if>
    <xsl:if test="$currentDepth &lt; $depth">
      <!-- if the currentDepth is lower than the depth needed, recursively 
           call this template -->
      <xsl:text>, </xsl:text>
      <xsl:call-template name="genBooleanVar">
        <xsl:with-param name="depth" select="$depth" />
        <xsl:with-param name="currentDepth" select="$currentDepth + 1" />
      </xsl:call-template>
    </xsl:if>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template name="maxdepth">

    <!-- search the maximum depth in the list of depth obtains by the template 
         Contracts in mode "depth"
         this list is build with depths followed by a '-', '5-6-2-1-' is a 
         possible list
         this template is recursive, at each step, it take the first depth of 
         the list and compare it with the maximum depth already found -->
        
    <xsl:param name="depths">
      <!-- the list of depths --></xsl:param>
    <xsl:param name="max" select="'0'" />
      <!-- the current maximum depth found -->
        
    <xsl:variable name="first">
      <!-- the first depth of the list -->
      <xsl:value-of select="substring-before($depths,'-')" />
    </xsl:variable>
        
    <xsl:variable name="rest">
      <!-- the rest of the list -->
      <xsl:value-of select="substring-after($depths,'-')" />
    </xsl:variable>
        
    <xsl:if test="$rest != ''">
      <!-- if the list is not completely analysed we call recursively 
           this template -->
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
      <!-- if the list is completely analysed, search a last time the 
           maximum depth -->
      <xsl:if test="$first &gt; $max">
        <xsl:value-of select="$first" />
      </xsl:if>
      <xsl:if test="$first &lt;= $max">
        <xsl:value-of select="$max" />
      </xsl:if>
    </xsl:if>
  </xsl:template>


<!-- ****************************************************************** -->     
<!-- * MAIN METHODS USED TO GENERATE CONTRACTS ************************ -->
<!-- ****************************************************************** -->     


<!-- ****************************************************************** -->
  <xsl:template match="Method" mode="beginContractsManagement">
    <xsl:param name="indent" />
        
    <!-- this template write all necessary code to manage the contracts before 
         writing the real code of the method
         It is dependant of the template 'Method' mode 'endContractsManagement 
         and call one of this template without the other will create a bad code
         not compilable -->
    
    <!-- If it exists a '@pre' in any postcondition, we must create a value 
         holder to store the value at entry in the method -->
    <xsl:apply-templates select="Contracts//PreValue">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
        
    <!-- Create the code to check invariant at entry in the method. The code 
         depends on the type of the method : constructor or other invariant 
         check has moved at the end of the precond test -->
    
    <!-- Generate boolean variables __evalXX used by the contracts -->
    <xsl:apply-templates select="Contracts" mode="generateBooleanVariables">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
        
    <!-- if (!__in_contrect_eval) ... -->
    <!-- beginning of contract management -->
    <xsl:call-template name="replacementForContractDeclaration">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="type" select="'invariant'" />
      <xsl:with-param name="content" select="$testInContractEval" />
    </xsl:call-template>
        
    <!-- modified : don' add pre block if the method has no pre cond -->
    <!-- Generate the Code for preconditions -->
    <xsl:if test="boolean(Contracts/Require//Assertion[*])"> <!-- modified -->
      <xsl:apply-templates select="Contracts" mode="RequireDeclaration">
        <!-- sort contracts in the right order -->
        <xsl:sort data-type="text" select="descendant-or-self::*[@line]/@line"/>
        <xsl:with-param name="indent" select="$indent" />
      </xsl:apply-templates>
    </xsl:if>
        
    <!-- call invariant check here -->
    <xsl:if test="count(./MethSign/Qualifiers/Qualifier[@value='static'])=0">
      <!-- Create the code to check invariant at entry in the method. The code 
           depends on the type of the method : constructor or other -->
      <xsl:call-template name="beginContractLine">
        <xsl:with-param name="indent" select="$indent+1" />
      </xsl:call-template>
      <xsl:if test="../@category = 'constructor'">
        <xsl:text>__inc_icl</xsl:text>
      </xsl:if>
      <xsl:if test="../@category != 'constructor'">
        <xsl:text>__inv_check</xsl:text>
      </xsl:if>
      <xsl:text>_at_entry__</xsl:text>
      <xsl:call-template name="getClassLongName" />
      <xsl:text>(</xsl:text>
      <xsl:value-of select="$cr" />
          
      <xsl:call-template name="beginContractLine">
        <xsl:with-param name="indent" select="$indent+2" />
      </xsl:call-template>
      <xsl:text>Thread.currentThread()</xsl:text>
          
      <xsl:if test="../@category = 'constructor'">
        <xsl:text>);</xsl:text>
        <xsl:value-of select="$cr" />
      </xsl:if>
          
      <xsl:if test="../@category != 'constructor'">
        <xsl:text>,</xsl:text>
        <xsl:value-of select="$cr" />
                  
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent+2" />
        </xsl:call-template>
        <xsl:text>"</xsl:text>
        <xsl:call-template name="getClassName" />
        <xsl:text>.java:</xsl:text>
        <xsl:value-of select="@start" />
        <xsl:text>: "+</xsl:text>
        <xsl:value-of select="$cr" />
                  
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent+2" />
        </xsl:call-template>
        <xsl:text>"just before enter </xsl:text>
        <xsl:call-template name="getClassLongName">
          <xsl:with-param name="sep" select="'.'" />
        </xsl:call-template>
        <xsl:text>::</xsl:text>
        <xsl:value-of select="Ident/@name" />
        <xsl:text>()");</xsl:text>
        <xsl:value-of select="$cr" />
      </xsl:if>
    </xsl:if>
    
        <!-- set contract state to false (not in contract anymore) -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:value-of select="concat('__in_contract_eval = false;',$cr)" />
        
    <!-- close if (!__in_contrect_eval) -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat('}',$cr)" />
        
    <!-- Try block for the code and the postconditions -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>try {</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="PreValue">
    <xsl:param name="indent" />
        
    <!-- Manage the expressions with the stclass operator @pre, that need to be 
         saved at the beginning of the method -->
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat(@type,' ',@name,' = ')" />
    <xsl:variable name="name" select="@name" />
    <xsl:apply-templates select="../..//UnaryExpression[@id=$name]/Expression"/>
    <xsl:value-of select="concat(';',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Method" mode="endContractsManagement">
    <xsl:param name="indent" />

    <!-- This template write all necessary code to manage the contracts after 
         the real code of the method. It is dependant of the template 'Method' 
         mode 'beginContractsManagement' and call one of this template without
         the other will create a bad code not compilable -->

    <!-- beginning of contract management -->
    <xsl:call-template name="replacementForContractDeclaration">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="type" select="'postcondition'" />
      <xsl:with-param name="content" select="$testInContractEval" />
    </xsl:call-template>        
                
    <!-- modified : don' add post block if the method has no post cond -->
    <!-- Generate the code for postconditions -->
    <xsl:if test="boolean(Contracts/Ensure//Assertion[*])">
      <xsl:apply-templates select="Contracts" mode="EnsureDeclaration">
                        <!-- sort contracts in the right order -->
        <xsl:sort data-type="text" select="descendant-or-self::*[@line]/@line"/>
        <xsl:with-param name="indent" select="$indent+1" />
      </xsl:apply-templates>
    </xsl:if>
        
    <!-- Create the code used to check invariant at exit of the Method -->
    <xsl:if test="count(./MethSign/Qualifiers/Qualifier[@value='static'])=0">
      <!-- modified : no finally, add inv check at exit directly at the end
      <xsl:call-template name="beginContractLine">
              <xsl:with-param name="indent" select="$indent" />
      </xsl:call-template>
      <xsl:text>} finally {</xsl:text>
      <xsl:value-of select="$cr" />
       -->
      
      <xsl:call-template name="beginContractLine">
        <xsl:with-param name="indent" select="$indent+2" />
      </xsl:call-template>
      <xsl:text>__inv_check_at_exit__</xsl:text>
      <xsl:call-template name="getClassLongName" />
      <xsl:text>(</xsl:text>
      <xsl:value-of select="$cr" />
          
      <xsl:call-template name="beginContractLine">
        <xsl:with-param name="indent" select="$indent+2" />
      </xsl:call-template>
      <xsl:text>Thread.currentThread(),</xsl:text>
      <xsl:value-of select="$cr" />
          
      <xsl:call-template name="beginContractLine">
        <xsl:with-param name="indent" select="$indent+2" />
      </xsl:call-template>
      <xsl:text>"</xsl:text>
      <xsl:call-template name="getClassName" />
      <xsl:text>.java:</xsl:text>
      <xsl:value-of select="@end" />
      <xsl:text>: "+</xsl:text>
      <xsl:value-of select="$cr" />
          
      <xsl:call-template name="beginContractLine">
        <xsl:with-param name="indent" select="$indent+2" />
      </xsl:call-template>
      <xsl:text>"just before exit </xsl:text>
      <xsl:call-template name="getClassLongName">
        <xsl:with-param name="sep" select="'.'" />
      </xsl:call-template>
      <xsl:text>::</xsl:text>
      <xsl:value-of select="Ident/@name" />
      <xsl:text>()");</xsl:text>
      <xsl:value-of select="$cr" />
    </xsl:if>
    
    <!-- set contract state to false (not in contract anymore) -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:value-of select="concat('__in_contract_eval = false;',$cr)" />
                
    <!-- close if (!__in_contrect_eval) -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat('}',$cr)" />
        
    <!-- modified : match end of finally
    <xsl:call-template name="beginContractLine">
            <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />
    -->

  </xsl:template>

  <xsl:template name="precondEval">
    <xsl:param name="indent" />
        
    <xsl:variable name="bol">
      <!-- the beginning of the lines of the method to create -->
      <xsl:call-template name="beginContractLine">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:call-template>
    </xsl:variable>
        
    <xsl:call-template name="replace">
      <xsl:with-param name="toReplace" select="'****bol****'" />
      <xsl:with-param name="replaceBy" select="$bol" />
      <xsl:with-param name="stringToProcess">
        <xsl:value-of select="$precondEval"/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>
                
<!-- ****************************************************************** -->
  <xsl:template match="Invariant" mode="Declaration">
    <xsl:param name="indent" />
        
    <xsl:variable name="bol">
      <!-- the beginning of the lines of the method to create -->
      <xsl:call-template name="beginContractLine">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:call-template>
    </xsl:variable>
        
    <!-- Write the methods used to call the invariant check -->
    <xsl:call-template name="replace">
      <xsl:with-param name="toReplace" select="'****bol****'" />
      <xsl:with-param name="replaceBy" select="$bol" />
      <xsl:with-param name="stringToProcess">
        <xsl:call-template name="replace">
          <xsl:with-param name="stringToProcess" select="$invariantMethods" />
          <xsl:with-param name="toReplace" select="'****ClassLongName****'" />
          <xsl:with-param name="replaceBy">
            <xsl:call-template name="getClassLongName" />
          </xsl:with-param>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>

    <!-- Write the method that check the invariant of the class -->

    <!-- beginning of the method -->
    <xsl:call-template name="replace">
      <xsl:with-param name="toReplace" select="'****bol****'" />
      <xsl:with-param name="replaceBy" select="$bol" />
      <xsl:with-param name="stringToProcess">
        <xsl:call-template name="replace">
          <xsl:with-param name="stringToProcess" select="$beginInvMethod" />
          <xsl:with-param name="toReplace" select="'****ClassLongName****'" />
          <xsl:with-param name="replaceBy">
            <xsl:call-template name="getClassLongName" />
          </xsl:with-param>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>

    <!-- beginning of contract management -->
    <xsl:call-template name="replacementForContractDeclaration">
      <xsl:with-param name="indent" select="$indent+1" />
      <xsl:with-param name="type" select="'invariant'" />
      <xsl:with-param name="content" select="$testInContractEval" />
    </xsl:call-template>
    <xsl:call-template name="replacementForContractDeclaration">
      <xsl:with-param name="indent" select="$indent+1" />
      <xsl:with-param name="type" select="'invariant'" />
      <xsl:with-param name="content" select="$beginOfPrePostInv" />
    </xsl:call-template>

    <!-- create __evalXX boolean variables -->
    <xsl:apply-templates select="." mode="generateBooleanVariables">
      <xsl:with-param name="indent" select="$indent+3" />
    </xsl:apply-templates>

    <!-- create the code for each Invariant condition (Invar) -->
    <xsl:apply-templates select="Invar">
      <xsl:with-param name="indent" select="$indent+3" />
    </xsl:apply-templates>      
        
    <!-- end of contract management -->
    <xsl:call-template name="replacementForContractDeclaration">
      <xsl:with-param name="indent" select="$indent+1" />
      <xsl:with-param name="lineNumber" select="../@line"/>
      <xsl:with-param name="type" select="'invariant'" />
      <xsl:with-param name="content" select="$endOfPrePostInv" />
    </xsl:call-template>
        
    <!-- set contract state to false (not in contract anymore) -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:value-of select="concat('__in_contract_eval = false;',$cr)" />
        
    <!-- close if (!__in_contrect_eval) -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:value-of select="concat('}',$cr)" />
        
    <!-- end of the method -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />


  </xsl:template>



<!-- ****************************************************************** -->
  <xsl:template match="Contracts" mode="RequireDeclaration">
    <xsl:param name="indent" />

        <!-- Write the code for the preconditions -->


        <!-- beginning of contract management -->
    <xsl:call-template name="replacementForContractDeclaration">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="type" select="'precondition'" />
      <xsl:with-param name="content" select="$beginOfPrePostInv" />
    </xsl:call-template>

        
    <xsl:apply-templates select="Require">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:apply-templates>
        

<!--    <xsl:apply-templates select="InheritedContracts" mode="RequireDeclaration">
                <xsl:with-param name="indent" select="$indent+2" />
        </xsl:apply-templates>
-->

        <!-- end of contract management -->
    <xsl:call-template name="replacementForContractDeclaration">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="lineNumber" select="../@start"/>
      <xsl:with-param name="type" select="'precondition'" />
      <xsl:with-param name="content" select="$endOfPrePostInv" />
    </xsl:call-template>

  </xsl:template>



<!-- ******************************************************************** -->
  <xsl:template match="Contracts" mode="EnsureDeclaration">
    <xsl:param name="indent" />

        <!-- Write the code for the postconditions -->
        
    <xsl:call-template name="replacementForContractDeclaration">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="type" select="'postcondition'" />
      <xsl:with-param name="content" select="$beginOfPrePostInv" />
    </xsl:call-template>
        
        <!-- create code for local postconditions -->
    <xsl:apply-templates select="Ensure">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:apply-templates>
        
        <!-- create code for inherited postconditions -->
<!--    <xsl:apply-templates select="InheritedContracts" mode="EnsureDeclaration">
                <xsl:with-param name="indent" select="$indent+2" />
        </xsl:apply-templates>
-->

        <!-- end of contract management -->
    <xsl:call-template name="replacementForContractDeclaration">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="lineNumber" select="../@start"/>
      <xsl:with-param name="type" select="'postcondition'" />
      <xsl:with-param name="content" select="$endOfPrePostInv" />
    </xsl:call-template>

  </xsl:template>


<!-- *************************************************************************************** -->
  <xsl:template name="replacementForContractDeclaration">

        <!-- template used to do replacement in the variables containing the
                contracts declarations -->
        
    <xsl:param name="indent" />
    <xsl:param name="lineNumber" />
    <xsl:param name="type">
                <!-- precondition, postcondition, or invariant --></xsl:param>
    <xsl:param name="content">
                <!-- String where process the replacement --></xsl:param>

        
    <xsl:variable name="shortType">
      <xsl:if test="$type = 'precondition'">
        <xsl:text>pre</xsl:text>
      </xsl:if>
      <xsl:if test="$type = 'postcondition'">
        <xsl:text>post</xsl:text>
      </xsl:if>
      <xsl:if test="$type = 'invariant'">
        <xsl:text>inv</xsl:text>
      </xsl:if>
    </xsl:variable>
        
    <xsl:variable name="bol">
                <!-- beginning of a line -->
      <xsl:call-template name="beginContractLine">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:call-template>
    </xsl:variable>
        
    <xsl:variable name="ClassName">
                <!-- the name of the class -->
      <xsl:call-template name="getClassName" />
    </xsl:variable>
        
    <xsl:variable name="MethodName">
                <!-- the name of the current method, -->
      <xsl:call-template name="getClassLongName">
        <xsl:with-param name="sep" select="'.'" />
      </xsl:call-template>
      <xsl:if test="boolean(../Ident/@name)">
                <!-- if the current contract isn't an invariant -->
        <xsl:value-of select="concat('::',../Ident/@name,'()')" />
      </xsl:if>
    </xsl:variable>
        
        
    <xsl:call-template name="replace">
      <xsl:with-param name="toReplace" select="'****type****'"/>
      <xsl:with-param name="replaceBy" select="$type"/>
      <xsl:with-param name="stringToProcess">
        <xsl:call-template name="replace">
          <xsl:with-param name="toReplace" select="'****typeMAJ****'"/>
          <xsl:with-param name="replaceBy" select="translate($type,'preconditsva','PRECONDITSVA')"/>
          <xsl:with-param name="stringToProcess">
            <xsl:call-template name="replace">
              <xsl:with-param name="toReplace" select="'****shortType****'"/>
              <xsl:with-param name="replaceBy" select="$shortType"/>
              <xsl:with-param name="stringToProcess">
                <xsl:call-template name="replace">
                  <xsl:with-param name="toReplace" select="'****className****'"/>
                  <xsl:with-param name="replaceBy" select="$ClassName"/>
                  <xsl:with-param name="stringToProcess">
                    <xsl:call-template name="replace">
                      <xsl:with-param name="toReplace" select="'****methodName****'"/>
                      <xsl:with-param name="replaceBy" select="$MethodName"/>
                      <xsl:with-param name="stringToProcess">
                        <xsl:call-template name="replace">
                          <xsl:with-param name="toReplace" select="'****lineNumber****'"/>
                          <xsl:with-param name="replaceBy" select="$lineNumber"/>
                          <xsl:with-param name="stringToProcess">
                            <xsl:call-template name="replace">
                              <xsl:with-param name="toReplace" select="'****bol****'"/>
                              <xsl:with-param name="replaceBy" select="$bol"/>
                              <xsl:with-param name="stringToProcess" select="$content"/>
                            </xsl:call-template>
                          </xsl:with-param>
                        </xsl:call-template>
                      </xsl:with-param>
                    </xsl:call-template>
                  </xsl:with-param>
                </xsl:call-template>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>

  </xsl:template>

<!-- ***************************************************************** -->

  <xsl:template match="Require">
    <xsl:param name="indent" />

        <!-- modified : need to call this template on sub nodes -->
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>Vector __messages = new Vector();</xsl:text>
    <xsl:value-of select="$cr" />
        
    <xsl:apply-templates select="Assertion" mode="declaration">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>__messages.addElement(__pre_msg);</xsl:text>
    <xsl:value-of select="$cr" />
        
    <xsl:apply-templates select="InheritedRequire">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>__pre_msg = __eval_pre_cond(__messages,0);</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>

<!-- ***************************************************************** -->
  <xsl:template match="Ensure|Invar">
    <xsl:param name="indent" />
        
    <xsl:apply-templates select="Assertion" mode="declaration">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>
        <!-- modified : need to call this template on sub nodes -->
    <xsl:apply-templates select=".//InheritedEnsure | .//InheritedInvar">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>

<!-- ***************************************************************** -->
  <xsl:template match="InheritedRequire">
    <xsl:param name="indent" />
        
    <xsl:choose>
      <xsl:when test="count(Assertion) &gt; 0">
                        
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:call-template>
        <xsl:text>__pre_msg = null;</xsl:text>
        <xsl:value-of select="$cr" />
      </xsl:when>
      <xsl:otherwise>
                        
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:call-template>
        <xsl:text>__messages.addElement("void");</xsl:text>
        <xsl:value-of select="$cr" />
      </xsl:otherwise>
    </xsl:choose>
        
    <xsl:apply-templates select="Assertion" mode="declaration">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="inherited" select="'true'" />
    </xsl:apply-templates>
        
    <xsl:if test="count(Assertion) &gt; 0">
      <xsl:call-template name="beginContractLine">
        <xsl:with-param name="indent" select="$indent" />
      </xsl:call-template>
      <xsl:text>__messages.addElement(__pre_msg);</xsl:text>
      <xsl:value-of select="$cr" />
    </xsl:if>
        
    <xsl:apply-templates select="InheritedRequire">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>

<!-- ***************************************************************** -->
  <xsl:template match="InheritedEnsure|InheritedInvar">
    <xsl:param name="indent" />
        
    <xsl:apply-templates select="Assertion" mode="declaration">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="inherited" select="'true'" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ***************************************************************** -->
  <xsl:template match="Assertion" mode="declaration">
        <!-- Generate code for Pre, Postconditions, and invariants -->
        
    <xsl:param name="indent" />
    <xsl:param name="inherited" select="false()" />
        
    <xsl:variable name="type_msg">
                <!-- name of the String containing an error message in case of an Exception occurs during
                     contract evaluation. Depends on if it's pre post or invariant -->
      <xsl:text>__</xsl:text>
      <xsl:if test="contains(name(..),'Require')">
        <xsl:text>pre</xsl:text>
      </xsl:if>
      <xsl:if test="contains(name(..),'Ensure')">
        <xsl:text>post</xsl:text>
      </xsl:if>
      <xsl:if test="contains(name(..),'Invar')">
        <xsl:text>inv</xsl:text>
      </xsl:if>
      <xsl:text>_msg</xsl:text>
    </xsl:variable>

        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>if (</xsl:text>
    <xsl:value-of select="$type_msg" />
    <xsl:text> == null){</xsl:text>
    <xsl:value-of select="$cr" />

        <!-- Generation of the code which will check the contract -->
    <xsl:apply-templates select=".">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:apply-templates>


        <!-- Generation of the code which throws the exception if the contract is violated -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>if (! __eval10) {</xsl:text>
    <xsl:value-of select="$cr" />
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:value-of select="$type_msg" />
    <xsl:text> = new String("</xsl:text>
        
    <xsl:choose>
      <xsl:when test="not($inherited)">
        <xsl:text>)[line </xsl:text>
        <xsl:value-of select="@line" />
        <xsl:text>]\n"+</xsl:text>
        <xsl:value-of select="$cr" />
      </xsl:when>
      <xsl:otherwise>
        <xsl:text> declared in" +</xsl:text>
        <xsl:value-of select="$cr" />
                        
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent+2" />
        </xsl:call-template>
        <xsl:text>" </xsl:text>
        <xsl:value-of select="../@class" />
        <xsl:text>::</xsl:text>
        <xsl:value-of select="(ancestor::Method)/Ident/@name" />
        <xsl:text>()[Line </xsl:text>
        <xsl:value-of select="@line" />
        <xsl:text>])\n" +</xsl:text>
        <xsl:value-of select="$cr" />
      </xsl:otherwise>
    </xsl:choose>
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+2" />
    </xsl:call-template>
    <xsl:text>" -- </xsl:text>
    <xsl:value-of disable-output-escaping="yes" select="attribute::assid" />
    <xsl:text>\n");</xsl:text> <!-- modified: :\n" -->
    <xsl:value-of select="$cr" />

        <!-- print the assertion of the contract
        <xsl:call-template name="beginContractLine">
                <xsl:with-param name="indent" select="$indent+2" />
        </xsl:call-template>
        <xsl:text>"\t</xsl:text>
        <xsl:apply-templates select="Eval" mode="text" />
        <xsl:text>\n");</xsl:text>
        <xsl:value-of select="$cr" />
        -->
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>}</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>



<!-- ****************************************************************** -->
  <xsl:template match="Assertion">
    <xsl:param name="indent" />

        <!-- Create expression evaluation for a contract -->
        
    <xsl:apply-templates select="CodeContract/Expression" mode="firstInContract">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Expression" mode="firstInContract">
    <xsl:param name="indent" />
        
    <xsl:variable name="isEval">
                <!-- is the first expression an eval ? -->
      <xsl:apply-templates select="*" mode="isEval" />
    </xsl:variable>
        
    <xsl:choose>
      <xsl:when test="$isEval = 'true'">
        <xsl:apply-templates select="*" mode="createEvals">
          <xsl:with-param name="rang" select="1" />
          <xsl:with-param name="varRes" select="10" />
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="*" mode="createEvals">
          <xsl:with-param name="rang" select="2" />
          <xsl:with-param name="varRes" select="20" />
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:call-template>
        <xsl:text>__eval10 = </xsl:text>
        <xsl:apply-templates select="*" mode="contract">
          <xsl:with-param name="rang" select="2"/>
        </xsl:apply-templates>
        <xsl:value-of select="concat(';',$cr)" />
      </xsl:otherwise>
    </xsl:choose>

  </xsl:template>





<!-- ****************************************************************** -->     
<!-- * createEvals PROCESS, GENERATE CODE FOR STCLASS OPERATORS ******* -->
<!-- ****************************************************************** -->     



<!-- ****************************************************************** -->
  <xsl:template match="*" mode="createEvals">
    <xsl:param name="indent" />
    <xsl:param name="rang" />
    <xsl:param name="varRes" />
        
    <xsl:for-each select="*">
      <xsl:variable name="nbEvalInPreviousElement">
        <xsl:apply-templates select="." mode="nbEvalInPreviousElement" />
      </xsl:variable>
                
      <xsl:apply-templates select="." mode="createEvals">
        <xsl:with-param name="rang" select="$rang+$nbEvalInPreviousElement" />
        <xsl:with-param name="varRes" select="$varRes+(10*$nbEvalInPreviousElement)" />
        <xsl:with-param name="indent" select="$indent" />
      </xsl:apply-templates>
        
    </xsl:for-each>
  </xsl:template>



<!-- ****************************************************************** -->
  <xsl:template match="BinaryExpression[@type='implies']" mode="createEvals">
    <xsl:param name="indent" />
    <xsl:param name="rang" />
    <xsl:param name="varRes" />
        
    <xsl:variable name="child1isEval">
      <xsl:apply-templates select="Expression[position()=1]/*" mode="isEval"/>
    </xsl:variable>
    <xsl:variable name="child2isEval">
      <xsl:apply-templates select="Expression[position()=2]/*" mode="isEval"/>
    </xsl:variable>
        
    <xsl:choose>
      <xsl:when test="$child1isEval = 'true'">
        <xsl:apply-templates select="*[position()=1]" mode="createEvals">
          <xsl:with-param name="rang" select="$rang+1" />
          <xsl:with-param name="varRes" select="concat($rang+1,'0')" />
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="*[position()=1]" mode="createEvals">
          <xsl:with-param name="rang" select="$rang+2" />
          <xsl:with-param name="varRes" select="concat($rang+2,'0')" />
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:call-template>
        <xsl:value-of select="concat('__eval',$rang+1,'0 = ')" />
        <xsl:apply-templates select="*[position()=1]" mode="contract">
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
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="*[position()=2]" mode="createEvals">
          <xsl:with-param name="rang" select="$rang+2" />
          <xsl:with-param name="varRes" select="concat($rang+2,'0')" />
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:call-template>
        <xsl:value-of select="concat('__eval',$rang+1,'1 = ')" />
        <xsl:apply-templates select="*[position()=2]" mode="contract">
          <xsl:with-param name="rang" select="$rang+2"/>
        </xsl:apply-templates>
        <xsl:value-of select="concat(';',$cr)" />
      </xsl:otherwise>
    </xsl:choose>
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat('__eval', $varRes, ' = ')" />
    <xsl:value-of select="concat('(__eval',$rang+1,'0) ? (__eval',$rang+1,'1) : true;',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="BinaryExpression[@type='land']|BinaryExpression[@type='lor']" mode="createEvals">
    <xsl:param name="indent" />
    <xsl:param name="rang" />
    <xsl:param name="varRes" />
        
    <xsl:variable name="child1isEval">
      <xsl:apply-templates select="Expression[position()=1]/*" mode="isEval"/>
    </xsl:variable>
    <xsl:variable name="child2isEval">
      <xsl:apply-templates select="Expression[position()=2]/*" mode="isEval"/>
    </xsl:variable>
        
    <xsl:choose>
      <xsl:when test="$child1isEval = 'true'">
        <xsl:apply-templates select="*[position()=1]" mode="createEvals">
          <xsl:with-param name="rang" select="$rang+1" />
          <xsl:with-param name="varRes" select="concat($rang+1,'0')" />
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="*[position()=1]" mode="createEvals">
          <xsl:with-param name="rang" select="$rang+2" />
          <xsl:with-param name="varRes" select="concat($rang+2,'0')" />
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:call-template>
        <xsl:value-of select="concat('__eval',$rang+1,'0 = ')" />
        <xsl:apply-templates select="*[position()=1]" mode="contract">
          <xsl:with-param name="rang" select="$rang+2"/>
        </xsl:apply-templates>
        <xsl:value-of select="concat(';',$cr)" />
      </xsl:otherwise>
    </xsl:choose>
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>if (</xsl:text>
    <xsl:if test="@type = 'lor'">
      <xsl:text>!</xsl:text>
    </xsl:if>
    <xsl:text>__eval</xsl:text>
    <xsl:value-of select="$rang+1" />
    <xsl:text>0) {</xsl:text>
    <xsl:value-of select="$cr" />
        
    <xsl:choose>
      <xsl:when test="$child2isEval = 'true'">
        <xsl:apply-templates select="*[position()=2]" mode="createEvals">
          <xsl:with-param name="rang" select="$rang+1" />
          <xsl:with-param name="varRes" select="concat($rang+1,'1')" />
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="*[position()=2]" mode="createEvals">
          <xsl:with-param name="rang" select="$rang+2" />
          <xsl:with-param name="varRes" select="concat($rang+2,'1')" />
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent" />
        </xsl:call-template>
        <xsl:value-of select="concat('__eval',$rang+1,'1 = ')" />
        <xsl:apply-templates select="*[position()=2]" mode="contract">
          <xsl:with-param name="rang" select="$rang+2"/>
        </xsl:apply-templates>
        <xsl:value-of select="concat(';',$cr)" />
      </xsl:otherwise>
    </xsl:choose>
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>} else {__eval</xsl:text>
    <xsl:value-of select="$rang+1" />
    <xsl:text>1 = true;} // __eval need to be initialized</xsl:text>
    <xsl:value-of select="$cr" />
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:text>__eval</xsl:text>
    <xsl:value-of select="$varRes" />
    <xsl:text> = __eval</xsl:text>
    <xsl:value-of select="$rang + 1" />
    <xsl:text>0 </xsl:text>
    <xsl:if test="@type = 'land'">
      <xsl:text disable-output-escaping="yes">&amp;&amp;</xsl:text>
    </xsl:if>
    <xsl:if test="@type = 'lor'">
      <xsl:text>||</xsl:text>
    </xsl:if>
    <xsl:text> __eval</xsl:text>
    <xsl:value-of select="$rang + 1" />
    <xsl:text>1;</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Forall|Exists" mode="createEvals">
    <xsl:param name="indent" />
    <xsl:param name="rang" />
    <xsl:param name="varRes" />
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat('__eval',$varRes,' = ')" />
    <xsl:if test="name() = 'Forall'">
      <xsl:text>true</xsl:text>
    </xsl:if>
    <xsl:if test="name() = 'Exists'">
      <xsl:text>false</xsl:text>
    </xsl:if>
    <xsl:value-of select="concat(';',$cr)" />
        
    <xsl:apply-templates select="Enumeration" mode="createEvals">
      <xsl:with-param name="indent" select="$indent" />
      <xsl:with-param name="varRes" select="$varRes" />
    </xsl:apply-templates>
        
        
    <xsl:variable name="exprChildIsEval">
      <xsl:apply-templates select="Expression/*[position()=1]" mode="isEval"/>
    </xsl:variable>
    <xsl:choose>
      <xsl:when test="$exprChildIsEval = 'true'">
        <xsl:apply-templates select="Expression" mode="createEvals">
          <xsl:with-param name="rang" select="$rang+1" />
          <xsl:with-param name="varRes" select="concat($rang+1,'0')" />
          <xsl:with-param name="indent" select="$indent" />
        </xsl:apply-templates>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="Expression" mode="createEvals">
          <xsl:with-param name="rang" select="$rang+2" />
          <xsl:with-param name="varRes" select="concat($rang+2,'0')" />
          <xsl:with-param name="indent" select="$indent+1" />
        </xsl:apply-templates>
        <xsl:call-template name="beginContractLine">
          <xsl:with-param name="indent" select="$indent+1" />
        </xsl:call-template>
        <xsl:value-of select="concat('__eval',$rang+1,'0 = ')" />
        <xsl:apply-templates select="Expression" mode="contract">
          <xsl:with-param name="rang" select="$rang+2"/>
        </xsl:apply-templates>
        <xsl:value-of select="concat(';',$cr)" />
      </xsl:otherwise>
    </xsl:choose>
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:value-of select="concat('__eval',$varRes,' = __eval',$rang+1,'0;',$cr)" />
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
    <xsl:value-of select="concat('}',$cr)" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="Enumeration" mode="createEvals">
    <xsl:param name="indent" />
    <xsl:param name="varRes" />
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent" />
    </xsl:call-template>
        <!--<xsl:text>for (java.util.Iterator __e_enum = </xsl:text>-->
    <xsl:text>for (java.util.Enumeration __e_enum = </xsl:text>
    <xsl:apply-templates select="Expression" mode="contract"/>
    <xsl:value-of select="concat(';',$cr)" />
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
        <!--<xsl:text disable-output-escaping="yes">__e_enum.hasNext() &amp;&amp; </xsl:text>-->
    <xsl:text disable-output-escaping="yes">__e_enum.hasMoreElements() &amp;&amp; </xsl:text>
    <xsl:if test="name(..) = 'Exists'">
      <xsl:text>!</xsl:text>
    </xsl:if>
    <xsl:text>__eval</xsl:text>
    <xsl:value-of select="concat($varRes,';) {',$cr)" />
        
    <xsl:call-template name="beginContractLine">
      <xsl:with-param name="indent" select="$indent+1" />
    </xsl:call-template>
    <xsl:value-of select="@type"/>
    <xsl:text> </xsl:text>
    <xsl:value-of select="@name"/>
    <xsl:text> = (</xsl:text>
    <xsl:value-of select="@type"/>
        <!--<xsl:text>) (__e_enum.next());</xsl:text>-->
    <xsl:text>) (__e_enum.nextElement());</xsl:text>
    <xsl:value-of select="$cr" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="*" mode="isEval">
        
        <!-- is the current element an eval ? The current element must be a child of
                an Expression element -->
        
    <xsl:choose>
      <xsl:when test="name() = 'BinaryExpression'">
        <xsl:choose>
          <xsl:when test="@type = 'implies'">
            <xsl:value-of select="true()" />
          </xsl:when>
          <xsl:when test="@type = 'land'">
            <xsl:value-of select="true()" />
          </xsl:when>
          <xsl:when test="@type = 'lor'">
            <xsl:value-of select="true()" />
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="false()" />
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:when test="name() = 'PrimaryExpression'">
        <xsl:choose>
          <xsl:when test="boolean(child::Forall)">
            <xsl:value-of select="true()" />
          </xsl:when>
          <xsl:when test="boolean(child::Exists)">
            <xsl:value-of select="true()" />
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="false()" />
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="false()" />
      </xsl:otherwise>
    </xsl:choose>

  </xsl:template>





<!-- ****************************************************************** -->
<!-- * contract PROCESS, USED TO GENERATE CODE FOR CLASSIC OPERATORS ** -->
<!-- ****************************************************************** -->     


<!-- ****************************************************************** -->
  <xsl:template match="Expression" mode="contract">
    <xsl:param name="rang"/>
        
    <xsl:apply-templates select="*" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>
  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="BinaryExpression[@type='implies']|Forall|Exists| BinaryExpression[@type='land']|BinaryExpression[@type='lor']" mode="contract">

        <!-- these elements are all 'eval' tha can be found in the expression of a
                contract. In this mode we just replace theyr by the name of the variable
                to which they correspond -->
        
    <xsl:param name="rang"/>
        
    <xsl:value-of select="concat('__eval',$rang,'0')" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="UnaryExpression[@type = 'pre']" mode="contract">
        
    <xsl:value-of select="@id"/>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="ReturnExpr" mode="contract">
        
    <xsl:value-of select="'__return_value_holder_'" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="TernaryExpression" mode="contract"> <!-- FAIT -->
    <xsl:param name="rang"/>
        
    <xsl:choose>
      <xsl:when test="@type = 'question'">
        <xsl:text>(</xsl:text>
        <xsl:apply-templates select="Expression[position() = 1]" mode="contract">
          <xsl:with-param name="rang" select="$rang" />
        </xsl:apply-templates>
        <xsl:text>) ? (</xsl:text>
                        
        <xsl:variable name="nbEvalInFirst">
          <xsl:apply-templates select="*[position()=1]" mode="nbEval" />
        </xsl:variable>
                        
        <xsl:apply-templates select="Expression[position() = 2]" mode="contract">
          <xsl:with-param name="rang" select="$rang+$nbEvalInFirst" />
        </xsl:apply-templates>
        <xsl:text>) : (</xsl:text>
                        
        <xsl:variable name="nbEvalInSecond">
          <xsl:apply-templates select="*[position()=2]" mode="nbEval" />
        </xsl:variable>
                        
        <xsl:apply-templates select="Expression[position() = 3]" mode="contract">
          <xsl:with-param name="rang" select="$rang+$nbEvalInFirst+$nbEvalInSecond" />
        </xsl:apply-templates>
        <xsl:text>)</xsl:text>
      </xsl:when>
    </xsl:choose>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="BinaryExpression[@type != 'implies']" mode="contract"> <!-- FAIT -->
    <xsl:param name="rang"/>
        
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
          <xsl:value-of select="boolean(Expression[position() = 1]//TernaryExpression | Expression[position() = 1]//BinaryExpression[@type != 'dot'] | Expression[position() = 1]//TypeCast | Expression[position() = 1]//MethodCall)" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="boolean(Expression[position() = 1]//TernaryExpression|Expression[position() = 1]//BinaryExpression[@type != 'dot'])" />
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="parentExpr2">
      <xsl:choose>
        <xsl:when test="@type = 'dot'">
          <xsl:value-of select="boolean(Expression[position() = 2]//TernaryExpression | Expression[position() = 2]//BinaryExpression[@type != 'dot'])" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="boolean(Expression[position() = 2]//TernaryExpression|Expression[position() = 2]//BinaryExpression[@type != 'dot'])" />
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
        
    <xsl:if test="$parentExpr1 = 'true'">
      <xsl:text>(</xsl:text>
    </xsl:if>
    <xsl:apply-templates select="Expression[position() = 1]" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>
    <xsl:if test="$parentExpr1 = 'true'">
      <xsl:text>)</xsl:text>
    </xsl:if>
        
    <xsl:value-of disable-output-escaping="yes" select="$inter" />
        
    <xsl:variable name="nbEvalInFirst">
      <xsl:apply-templates select="*[position()=1]" mode="nbEval" />
    </xsl:variable>
        
    <xsl:if test="$parentExpr2 = 'true'">
      <xsl:text>(</xsl:text>
    </xsl:if>
    <xsl:apply-templates select="Expression[position() = 2]" mode="contract">
      <xsl:with-param name="rang" select="$rang+$nbEvalInFirst" />
    </xsl:apply-templates>
    <xsl:if test="$parentExpr2 = 'true'">
      <xsl:text>)</xsl:text>
    </xsl:if>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="UnaryExpression[@type != 'pre']" mode="contract">
    <xsl:param name="rang"/>
        
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
        
    <xsl:variable name="parentExpr" select="boolean(Expression//TernaryExpression|Expression//BinaryExpression)" />
        
    <xsl:value-of select="$before" />
        
    <xsl:if test="$parentExpr">
      <xsl:text>(</xsl:text>
    </xsl:if>
    <xsl:apply-templates select="Expression" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>
    <xsl:if test="$parentExpr">
      <xsl:text>)</xsl:text>
    </xsl:if>
        
    <xsl:value-of select="$after" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="PrimaryExpression" mode="contract">
    <xsl:param name="rang"/>
        
    <xsl:apply-templates select="*" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Identifier" mode="contract">
        
    <xsl:value-of select="text()" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="MethodCall" mode="contract"> <!-- FAIT -->
    <xsl:param name="rang" />
        
    <xsl:variable name="parentExpr" select="boolean(Expression//TernaryExpression|Expression//BinaryExpression)" />
        
    <xsl:if test="parentExpr">
      <xsl:text>(</xsl:text>
    </xsl:if>
    <xsl:apply-templates select="Expression" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>
    <xsl:if test="parentExpr">
      <xsl:text>)</xsl:text>
    </xsl:if>
        
    <xsl:variable name="nbEvalInFirst">
      <xsl:apply-templates select="*[position()=1]" mode="nbEval" />
    </xsl:variable>
        
    <xsl:text>(</xsl:text>
    <xsl:apply-templates select="ExpressionList" mode="contract">
      <xsl:with-param name="rang" select="$rang+$nbEvalInFirst" />
    </xsl:apply-templates>
    <xsl:text>)</xsl:text>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="BuiltInExpr" mode="contract">
        
    <xsl:value-of select="@type" />

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="TypeCast" mode="contract">
    <xsl:param name="rang"/>
        
    <xsl:text>(</xsl:text>
    <xsl:value-of select="@type" />
    <xsl:text>) </xsl:text>
        
    <xsl:apply-templates select="Expression" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="ArrayIndex" mode="contract">
    <xsl:param name="rang"/>
        
    <xsl:apply-templates select="Expression[position() = 1]" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>
        
    <xsl:variable name="nbEvalInFirst">
      <xsl:apply-templates select="Expression[position() = 1]" />
    </xsl:variable>
        
    <xsl:text>[</xsl:text>
    <xsl:apply-templates select="Expression[position() = 2]" mode="contract">
      <xsl:with-param name="rang" select="$rang+$nbEvalInFirst" />
    </xsl:apply-templates>
    <xsl:text>]</xsl:text>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="NewExpr" mode="contract">
    <xsl:param name="rang"/>
        
    <xsl:text>new </xsl:text>
    <xsl:value-of select="@type" />
    <xsl:apply-templates select="*" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="NewArrayDecl" mode="contract">
    <xsl:param name="rang"/>
        
    <xsl:apply-templates select="ArrayDecl" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>
    <xsl:if test="count(child::*) = 2">
      <xsl:text> </xsl:text>
      <xsl:variable name="nbEvalInArrayDecl">
        <xsl:apply-templates select="ArrayDecl" mode="nbEval" />
      </xsl:variable>
      <xsl:apply-templates select="ArrayInit" mode="contract">
        <xsl:with-param name="rang" select="$rang+$nbEvalInArrayDecl" />
      </xsl:apply-templates>
    </xsl:if>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="ArrayDecl" mode="contract">
    <xsl:param name="rang"/>
        
    <xsl:text>[</xsl:text>
    <xsl:apply-templates select="Expression" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>
    <xsl:text>]</xsl:text>
        
    <xsl:variable name="nbEvalInExpression">
      <xsl:apply-templates select="Expression" mode="isEval" />
    </xsl:variable>
        
    <xsl:apply-templates select="ArrayDecl" mode="contract">
      <xsl:with-param name="rang" select="$rang+$nbEvalInExpression" />
    </xsl:apply-templates>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="NewObjectDecl" mode="contract">
    <xsl:param name="indent" />
    <xsl:param name="rang"/>
        
    <xsl:text>(</xsl:text>
    <xsl:apply-templates select="ExpressionList" mode="contract">
      <xsl:with-param name="rang" select="$rang" />
    </xsl:apply-templates>
    <xsl:text>)</xsl:text>
    <xsl:if test="count(child::*) = 2">
                
      <xsl:variable name="nbEvalInExpressionList">
        <xsl:apply-templates select="ExpressionList" mode="nbEval" />
      </xsl:variable>
                
      <xsl:text> {</xsl:text>
      <xsl:value-of select="$cr" />
      <xsl:apply-templates select="features">
        <xsl:with-param name="rang" select="$rang+$nbEvalInExpressionList" />
      </xsl:apply-templates>
      <xsl:call-template name="beginSourceCopiedLine">
        <xsl:with-param name="indent" select="$indent+1" />
      </xsl:call-template>
    </xsl:if>

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="Constant">
    <xsl:param name="rang"/>
        
    <xsl:value-of select="text()" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="ExpressionList" mode="contract">
    <xsl:param name="rang"/>
        
    <xsl:for-each select="Expression">
      <xsl:variable name="nbEvalInPrevious">
        <xsl:apply-templates select="." mode="nbEvalInPreviousElement"/>
      </xsl:variable>
                
      <xsl:apply-templates select="." mode="contract">
        <xsl:with-param name="rang" select="$rang+$nbEvalInPrevious" />
      </xsl:apply-templates>
      <xsl:if test="position() &lt; last()">
        <xsl:text>, </xsl:text>
      </xsl:if>
    </xsl:for-each>

  </xsl:template>


<!-- ****************************************************************** -->
  <xsl:template match="*" mode="nbEvalInPreviousElement">       
        
        <!-- return the number of eval in the previous element(s) of this -->
        
    <xsl:variable name="previous" select="preceding-sibling::*" />
    <xsl:variable name="last" select="$previous[position()=last()]" />
    <xsl:variable name="rest" select="$previous[position() != last()]" />
        
    <xsl:variable name="nbEvalInLast">
      <xsl:choose>
        <xsl:when test="boolean($last)">
          <xsl:apply-templates select="$last" mode="nbEval" />
        </xsl:when>
        <xsl:otherwise>0</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="nbEvalInRest">
      <xsl:choose>
        <xsl:when test="boolean($rest)">
          <xsl:apply-templates select="$last" mode="nbEvalInPreviousElement" />
        </xsl:when>
        <xsl:otherwise>0</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
        
    <xsl:value-of select="$nbEvalInLast + $nbEvalInRest" />

  </xsl:template>

<!-- ****************************************************************** -->
  <xsl:template match="*" mode="nbEval">
        
        <!-- return the number of eval in the current element -->
        
    <xsl:value-of select="count(.//BinaryExpression[@type='implies'] | .//Forall | .//Exists | .//BinaryExpression[@type='land'] | .//BinaryExpression[@type='lor'])" />

  </xsl:template>



</xsl:stylesheet>