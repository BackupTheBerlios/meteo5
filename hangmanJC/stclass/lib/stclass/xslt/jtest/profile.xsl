<?xml version="1.0" standalone="yes"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
		xmlns:lxslt="http://xml.apache.org/xslt"
		xmlns:redirect="org.apache.xalan.lib.Redirect"
		xmlns:stringutils="xalan://org.apache.tools.ant.util.StringUtils"
		xmlns:date="http://exslt.org/dates-and-times"
		extension-element-prefixes="redirect date">
	<xsl:output method="html" indent="yes" encoding="US-ASCII"/>
	<xsl:decimal-format decimal-separator="." grouping-separator=","/>
	
	<xsl:key name="cframes" match="frame" use="@cn"/>
	
	<xsl:key name="mframes" match="frame" use="@mn"/>
	
	
	<xsl:template match="/profile">
		
		<html>
			
			<xsl:call-template name="head" />
			
			<body onload="collapseAllRows();">
				
				<h1>Profiling and statistics of last test execution</h1>
				
				<p>The data displayed in this page concerns a single test execution including contracts evaluation. It is not relevant to a normal execution of the program and should not be used to evaluate its efficiency.</p>
				
				<xsl:call-template name="tree" />
				
				<xsl:call-template name="stats" />
			
			</body>
		</html>
	
	</xsl:template>
	
	<xsl:template name="tree">
		
		<h2>Contextual execution tree</h2>
		
		<table width="97%" align="center" class="details">
			
			<tr>
				<th width="60%">Call</th>
				<th>Count</th>
				<th>Time (ms)</th>
			</tr>
			
			<xsl:apply-templates select="/profile/thread/interaction/frame">
				<xsl:with-param name="father"></xsl:with-param>
				<xsl:with-param name="indent"></xsl:with-param>
			</xsl:apply-templates>
		
		</table>
	</xsl:template>
	
	<xsl:template name="stats">
		
		<xsl:for-each select="//frame[generate-id() = generate-id(key('cframes',@cn)[1])]">
			<xsl:call-template name="classStats">
				<xsl:with-param name="cname" select="@cn" />
			</xsl:call-template>
		</xsl:for-each>
	
	</xsl:template>
	
	<xsl:template name="classStats">
		<xsl:param name="cname" />
		
		<h2>Statistics of class "<xsl:value-of select="$cname"/>"</h2>
		
		<table width="97%" align="center" class="details">
			
			<tr>
				<th width="60%">Call</th>
				<th>Count</th>
				<th>Time (ms)</th>
				<th>Average time (ms)</th>
				<th>Percentage</th>
			</tr>
			
			<xsl:for-each select="//frame[(@cn = $cname) and not(contains(@mn,'__ststub')) and not(contains(@mn,'__inc_icl_at_entry__')) and not(contains(@mn,'OUTC_')) and not(contains(@mn,':TS_')) and not(contains(@mn,'__inv_check_at_exit__')) and not(contains(@mn,'__inv_check_at_entry__')) and not(contains(@mn,'__eval_pre_cond')) and (generate-id() = generate-id(key('mframes',@mn)[1]))]">
				
				<xsl:call-template name="methodStats">
					<xsl:with-param name="mname" select="@mn" />
				</xsl:call-template>
			</xsl:for-each>
		
		</table>
	
	</xsl:template>
	
	<xsl:template name="methodStats">
		<xsl:param name="mname" />
		
		<xsl:variable name="count" select="sum(//frame[@mn = $mname]/@c)" />
		<xsl:variable name="time" select="sum(//frame[@mn=$mname]/@t) div 1000000" />
		<xsl:variable name="avtime" select="$time div $count" />
		<xsl:variable name="pct" select="number(@t) div number(//frame[contains(@mn,':main')]/@t)" />
		<xsl:variable name="timeError" select="$pct &gt; 1" />
		
		<tr>
			<td>
				<xsl:call-template name="methodName">
					<xsl:with-param name="mn" select="$mname" />
				</xsl:call-template>
			</td>
			<td>
				<b><xsl:value-of select="$count"/></b>
			</td>
			<td>
				<xsl:choose>
					<xsl:when test="$timeError">
						<xsl:text>wrong value</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="display-time">
							<xsl:with-param name="value" select="$time" />
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</td>
			<td>
				<xsl:choose>
					<xsl:when test="$timeError">
						<xsl:text>wrong value</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="display-time">
							<xsl:with-param name="value" select="$avtime" />
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</td>
			<td>
				<xsl:choose>
					<xsl:when test="$timeError">
						<xsl:text>wrong value</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="display-pct">
							<xsl:with-param name="value" select="$pct" />
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</td>
		</tr>
	
	</xsl:template>
	
	<xsl:template match="frame">
	<!--
		<xsl:variable name="father" select="$father" />
		<xsl:variable name="indent" select="$indent" />
	-->
		<xsl:param name="father" />
		<xsl:param name="indent" />
		
		<xsl:variable name="time" select="number(@t) div 1000000" />
		
		<xsl:variable name="children" select="frame[not(contains(@mn,'__ststub')) and not(contains(@mn,'__inc_icl_at_entry__')) and not(contains(@mn,'OUTC_')) and not(contains(@mn,':TS_')) and not(contains(@mn,':__eval_pre_cond'))]" />
		
		<xsl:variable name="newId">
			<xsl:value-of select="$father" />
			<xsl:value-of select="position()" />
		</xsl:variable>
		
		<tr>
			<xsl:attribute name="id">
				<xsl:value-of select="$newId" />
			</xsl:attribute>
			<td>
				<div>
					<xsl:value-of select="$indent" />|
					<xsl:choose>
					<xsl:when test="count($children) != 0">
						<a class="treeLine" href="#" onclick="toggleRows(this)"></a>
						</xsl:when>
						<xsl:otherwise>
							<a href="#"></a>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:call-template name="methodName">
						<xsl:with-param name="mn" select="@mn" />
					</xsl:call-template>
				</div>
			</td>
			<td>
				<b><xsl:value-of select="@c" /></b>
			</td>
			<td>
				<xsl:choose>
					<xsl:when test="number(@t) &gt; number(//frame[contains(@mn,':main')]/@t)">
						<xsl:text>wrong value</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="display-time">
							<xsl:with-param name="value" select="$time" />
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</td>
		</tr>
		<xsl:apply-templates select="$children">
			<xsl:with-param name="father">
				<xsl:value-of select="$newId" />-</xsl:with-param>
			<xsl:with-param name="indent">
				<xsl:value-of select="concat($indent,'&#160;&#160;&#160;&#160;')" />
			</xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template name="display-time">
		<xsl:param name="value"/>
		<xsl:value-of select="format-number($value,'0.000')"/>
	</xsl:template>
	
	<xsl:template name="display-pct">
		<xsl:param name="value"/>
		<xsl:value-of select="format-number($value,'0.00 %')"/>
	</xsl:template>
	
	<xsl:template name="methodName">
		<xsl:param name="mn" />
		<xsl:choose>
			<xsl:when test="contains($mn,':TC_')">
				<xsl:value-of select="substring-before($mn,':TC_')"/>
				<xsl:text>: (test case) </xsl:text>
				<xsl:value-of select="substring-after($mn,':TC_')"/>
			</xsl:when>
			<xsl:when test="contains($mn,':TS_')">
				<xsl:value-of select="substring-before($mn,':TS_')"/>
				<xsl:text>: (test suite) </xsl:text>
				<xsl:value-of select="substring-after($mn,':TS_')"/>
			</xsl:when>
			<xsl:when test="contains($mn,'__inv_check_at_exit__')">
				<xsl:value-of select="substring-before($mn,'__inv_check_at_exit__')"/>
				<xsl:text> (invariants at exit ?) </xsl:text>
			</xsl:when>
			<xsl:when test="contains($mn,'__inv_check_at_entry__')">
				<xsl:value-of select="substring-before($mn,'__inv_check_at_entry__')"/>
				<xsl:text> (invariants at entry ?) </xsl:text>
			</xsl:when>
			<xsl:when test="contains($mn,'__check_invariant__')">
				<xsl:value-of select="substring-before($mn,'__check_invariant__')"/>
				<xsl:text> (check invariants) </xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$mn"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="head">
		<head>
			<link title="Style" type="text/css" rel="stylesheet" href="stylesheet.css" />
			<script language="javascript1.2">
				
				<xsl:text>
// You probably should factor this out to a .js file
function toggleRows(elm) {
 var rows = document.getElementsByTagName("TR");
 elm.style.backgroundImage = "url(./img/go-next.png)";
 var newDisplay = "none";
 var thisID = elm.parentNode.parentNode.parentNode.id + "-";
 // Are we expanding or contracting? If the first child is hidden, we expand
  for (var i = 0; i &lt; rows.length; i++) {
   var r = rows[i];
   if (matchStart(r.id, thisID, true)) {
    if (r.style.display == "none") {
     if (document.all) newDisplay = "block"; //IE4+ specific code
     else newDisplay = "table-row"; //Netscape and Mozilla
     elm.style.backgroundImage = "url(./img/go-down.png)";
    }
    break;
   }
 }
 
 // When expanding, only expand one level.  Collapse all desendants.
 var matchDirectChildrenOnly = (newDisplay != "none");

 for (var j = 0; j &lt; rows.length; j++) {
   var s = rows[j];
   if (matchStart(s.id, thisID, matchDirectChildrenOnly)) {
     s.style.display = newDisplay;
     var cell = s.getElementsByTagName("TD")[0];
     var tier = cell.getElementsByTagName("DIV")[0];
     var folder = tier.getElementsByTagName("A")[0];
     if (folder.getAttribute("onclick") != null) {
      folder.style.backgroundImage = "url(./img/go-next.png)";
     }
   }
 }
}

function matchStart(target, pattern, matchDirectChildrenOnly) {
 var pos = target.indexOf(pattern);
 if (pos != 0) return false;
 if (!matchDirectChildrenOnly) return true;
 if (target.slice(pos + pattern.length, target.length).indexOf("-") >= 0) return false;
 return true;
}

function collapseAllRows() {
 var rows = document.getElementsByTagName("TR");
 for (var j = 0; j &lt; rows.length; j++) {
   var r = rows[j];
   if (r.id.indexOf("-") >= 0) {
     r.style.display = "none";    
   }
 }
}
</xsl:text>
			
			</script>
			
			<title>Profiling and statistics page</title>
		</head>
	
	</xsl:template>


</xsl:stylesheet>