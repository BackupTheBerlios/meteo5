<?xml version="1.0" standalone="yes"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
		xmlns:lxslt="http://xml.apache.org/xslt"
		xmlns:redirect="org.apache.xalan.lib.Redirect"
		xmlns:stringutils="xalan://org.apache.tools.ant.util.StringUtils"
		xmlns:date="http://exslt.org/dates-and-times"
		extension-element-prefixes="redirect date">
	<xsl:output method="html" indent="yes" encoding="US-ASCII"/>
	<xsl:decimal-format decimal-separator="." grouping-separator=","/>
<!--
   Copyright 2001-2004 The Apache Software Foundation

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 -->

<!--
 Sample stylesheet to be used with An JUnitReport output.

 It creates a set of HTML files a la javadoc where you can browse easily
 through all packages and classes.

 @author Stephane Bailliez <a href="mailto:sbailliez@apache.org"/>
 @author Erik Hatcher <a href="mailto:ehatcher@apache.org"/>
 @author Martijn Kruithof <a href="mailto:martijn@kruithof.xs4all.nl"/>
-->

<!--
 This stylesheet has been modified by JTest project (under project of STclass)
 team.

 It creates a set of HTML files like javdoc where you con browse easily through
 all packages, classes and test cases.
 
 @author Julien Haillot
 @author David Poirier
 @author Nicolas Castel
-->
<!-- relative paths are not supported using the transform method from Xalan Transformer class -->
<!-- therefore, a full pathname property is given in the testExecs Node-->
<!-- <xsl:param name="output.dir" select="'./'"/> -->
	<xsl:param name="output.dir" select="//properties/property[@name = 'outHTML']/@value"/>
	
	<xsl:template match="test_execs">
    <!-- create the index.html -->
    		
		<redirect:write file="{$output.dir}/profile.html">
			<xsl:call-template name="profile.html"/>
		</redirect:write>
		
		<redirect:write file="{$output.dir}/index.html">
			<xsl:call-template name="index.html"/>
		</redirect:write>

    <!-- create the stylesheet.css -->
		<redirect:write file="{$output.dir}/stylesheet.css">
			<xsl:call-template name="stylesheet.css"/>
		</redirect:write>

    <!-- create the overview-packages.html at the root -->
		<redirect:write file="{$output.dir}/overview-summary.html">
			<xsl:apply-templates select="." mode="overview.packages"/>
		</redirect:write>

    <!-- create the all-packages.html at the root -->
		<redirect:write file="{$output.dir}/overview-frame.html">
			<xsl:apply-templates select="." mode="all.packages"/>
		</redirect:write>

    <!-- create the all-classes.html at the root -->
		<redirect:write file="{$output.dir}/allclasses-frame.html">
			<xsl:apply-templates select="." mode="all.classes"/>
		</redirect:write>

    <!-- process all packages -->
		<xsl:for-each select="./test_exec[not(./@package = preceding-sibling::test_exec/@package)]">
			<xsl:call-template name="package">
				<xsl:with-param name="name" select="@package"/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>
	
	
	<xsl:template name="package">
		<xsl:param name="name"/>
		<xsl:variable name="package.dir">
			<xsl:if test="not($name = '')">
				<xsl:value-of select="translate($name,'.','/')"/>
			</xsl:if>
			<xsl:if test="$name = ''">.</xsl:if>
		</xsl:variable>
    <!--Processing package <xsl:value-of select="@name"/> in <xsl:value-of select="$output.dir"/> -->
    <!-- create a classes-list.html in the package directory -->
		<redirect:write file="{$output.dir}/{$package.dir}/package-frame.html">
			<xsl:call-template name="classes.list">
				<xsl:with-param name="name" select="$name"/>
			</xsl:call-template>
		</redirect:write>

    <!-- create a package-summary.html in the package directory -->
		<redirect:write file="{$output.dir}/{$package.dir}/package-summary.html">
			<xsl:call-template name="package.summary">
				<xsl:with-param name="name" select="$name"/>
			</xsl:call-template>
		</redirect:write>

    <!-- for each class, creates a @classname.html -->
    <!-- @bug there will be a problem with inner classes having the same name, it will be overwritten -->
		<xsl:for-each select="/test_execs/test_exec[@package = $name]">
			<redirect:write file="{$output.dir}/{$package.dir}/{@classname}.html">
				<xsl:apply-templates select="." mode="class.details"/>
			</redirect:write>
			<!--<xsl:for-each select="/test_execs/test_exec[@package = $name]/testcase">-->
			<xsl:for-each select="testcase">
				
				<redirect:write file="{$output.dir}/{$package.dir}/{../@classname}-{@name}.html"> <!-- modified : .html added -->
					<xsl:apply-templates select="." mode="testcase.html"/>
				</redirect:write>
			</xsl:for-each>
			
			<xsl:if test="string-length(./system-out)!=0">
				<redirect:write file="{$output.dir}/{$package.dir}/{@classname}-out.txt">
					<xsl:value-of select="./system-out" />
				</redirect:write>
			</xsl:if>
			<xsl:if test="string-length(./system-err)!=0">
				<redirect:write file="{$output.dir}/{$package.dir}/{@classname}-err.txt">
					<xsl:value-of select="./system-err" />
				</redirect:write>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="index.html">
		<html>
			<head>
				<title>STclass Test Results on <xsl:value-of select="date:date-time()" />
				</title>
			</head>
			<frameset cols="20%,80%">
				<frameset rows="30%,70%">
					<frame src="overview-frame.html" name="packageListFrame"/>
					<frame src="allclasses-frame.html" name="classListFrame"/>
				</frameset>
				<frame src="overview-summary.html" name="classFrame"/>
				<noframes>
					<h2>Frame Alert</h2>
					<p>
                This document is designed to be viewed using the frames feature. If you see this message, you are using a non-frame-capable web client.
            </p>
				</noframes>
			</frameset>
		</html>
	</xsl:template>

<!-- this is the stylesheet css to use for nearly everything -->
	<xsl:template name="stylesheet.css">
body {
    font:normal 68% verdana,arial,helvetica;
    color:black;
    background-color:#f1dfc4;
    font-style: normal;
    font-family: sans-serif;
    }


a:link {color: #784800;    background: transparent;}
a:visited {color: #784800;    background: transparent;}
a:active {color: red;    background: transparent;}

table tr td, table tr th {
    font-size: 68%;
}
table.details tr th{
    font-weight: bold;
    text-align:left;
    background:#987033;
}
table.details tr td{
    background:#eeeee0;
}

p {
    line-height:1.5em;
    margin-top:0.5em; margin-bottom:1.0em;
}
h1 {
    font-weight: bold; 
    font-size:  180%;
    color:#987033;
    background: transparent;
    margin: 0ex auto 0ex auto;
    padding: 0.2em 2em 0.2em 2em;
    border: 2px solid #987033;
    text-align: center;
    font-family: verdana,arial,helvetica;
}
h2 {
    margin-top: 1em; margin-bottom: 0.5em; font: bold 125% verdana,arial,helvetica
}
h3 {
    margin-bottom: 0.5em; font: bold 115% verdana,arial,helvetica
}
h4 {
    margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
}
h5 {
    margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
}
h6 {
    margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
}
.Error {
    font-weight:bold; color:red;
}
.Failure {
    font-weight:bold; color:orange;
}
.Properties {
  text-align:right;
}
a.treeLine {
  background:url(./img/go-next.png) no-repeat;
  /*width:16px;
  height:16px;*/
  padding-left:16px;
  padding-right:3px;
}

</xsl:template>

<!-- ======================================================================
This page is created for every test_exec class.
It prints a summary of the test_exec and detailed information about


testcase methods.

====================================================================== -->

<!-- ======================================================================
    This page is created for every test_exec class.
    It prints a summary of the test_exec and detailed information about
    testcase methods.
     ====================================================================== -->
	<xsl:template match="test_exec" mode="class.details">
		<xsl:variable name="package.name" select="@package"/>
		
		
		<xsl:variable name="class.fullname">
			<xsl:if test="not($package.name = '')">
				<xsl:value-of select="$package.name"/>.</xsl:if>
			<xsl:value-of select="@classname"/>
		</xsl:variable>
		
		<xsl:variable name="class.name" select="@classname"/>
		
		<html>
			<head>
				<title>Test Suite Results: <xsl:value-of select="$class.fullname"/>
				</title>
				<xsl:call-template name="create.stylesheet.link">
					<xsl:with-param name="package.name" select="$package.name"/>
				</xsl:call-template>
				<script type="text/javascript" language="JavaScript">
        var TestCases = new Array();
        var cur;
        <xsl:apply-templates select="properties"/>
				</script>
				<script type="text/javascript" language="JavaScript"><![CDATA[
        function displayProperties (name) {
          var win = window.open('','System Properties','scrollbars=1,resizable=1');
          var doc = win.document.open();
          doc.write("<html><head><title>Properties of " + name + "</title>");
          doc.write("<style type=\"text/css\">");
          doc.write("body {font:normal 68% verdana,arial,helvetica; color:#000000; }");
          doc.write("table tr td, table tr th { font-size: 68%; }");
          doc.write("table.properties { border-collapse:collapse; border-left:solid 1 #cccccc; border-top:solid 1 #cccccc; padding:5px; }");
          doc.write("table.properties th { text-align:left; border-right:solid 1 #cccccc; border-bottom:solid 1 #cccccc; background-color:#eeeeee; }");
          doc.write("table.properties td { font:normal; text-align:left; border-right:solid 1 #cccccc; border-bottom:solid 1 #cccccc; background-color:#fffffff; }");
          doc.write("h3 { margin-bottom: 0.5em; font: bold 115% verdana,arial,helvetica }");
          doc.write("</style>");
          doc.write("</head><body>");
          doc.write("<h3>Properties of " + name + "</h3>");
          doc.write("<div align=\"right\"><a href=\"javascript:window.close();\">Close</a></div>");
          doc.write("<table class='properties'>");
          doc.write("<tr><th>Name</th><th>Value</th></tr>");
          for (prop in TestCases[name]) {
            doc.write("<tr><th>" + prop + "</th><td>" + TestCases[name][prop] + "</td></tr>");
          }
          doc.write("</table>");
          doc.write("</body></html>");
          doc.close();
          win.focus();
        }
      ]]>
      </script>
			</head>
			<body>
				<h1>Class <xsl:value-of select="$class.fullname"/>
				</h1>
            <!--table class="details" border="0" cellpadding="5" cellspacing="2" width="100%">
                <xsl:call-template name="test_exec.test.header"/>
                <xsl:apply-templates select="." mode="print.test"/>
            </table-->
					
				
				<xsl:apply-templates mode="print.testsuite" select="//test_exec[./@classname = $class.name]" />
					<!--<xsl:apply-templates select="//test_exec[./@classname = $class.name]/testcase" mode="print.test"/>-->
			
			</body>
		</html>
	</xsl:template>


<!-- ======================================================================
    This page is created for every testcase class.
    It prints a summary of the testcase and detailed information about
    testcase.
     ====================================================================== -->
	<xsl:template match="testcase" mode="testcase.html">
		<xsl:variable name="package.name" select="../@package"/>
		<xsl:variable name="class.name">
			<xsl:if test="not($package.name = '')">
				<xsl:value-of select="$package.name"/>.</xsl:if>
			<xsl:value-of select="../@classname"/>
		</xsl:variable>
		<html>
			<head>
				<title>Test Case Results: <xsl:value-of select="$class.name"/>
				</title>
				<xsl:call-template name="create.stylesheet.link">
					<xsl:with-param name="package.name" select="$package.name"/>
				</xsl:call-template>
			</head>
			<body>
				<h1>Test Case <xsl:value-of select="@name"/> of <xsl:value-of select="$class.name"/>
				</h1>
				<table class="details" border="0" cellpadding="5" cellspacing="2" width="100%">
					<xsl:call-template name="testcase.test.header"/>
					<xsl:apply-templates select="." mode="print.test"/>
				</table>
				
				<h2>Test Units</h2>
				<table class="details" border="0" cellpadding="5" cellspacing="2" width="100%">
					<xsl:call-template name="testunit.test.header"/>
                <!--
                test can even not be started at all (failure to load the class)
                so report the error directly
                -->
					<xsl:if test="./error">
						<tr class="Error">
							<td colspan="4">
								<xsl:apply-templates select="./error"/>
							</td>
						</tr>
					</xsl:if>
					<xsl:apply-templates select="./testunit" mode="print.test"/>
				</table>
				<xsl:if test="string-length(./system-err)!=0">
					<div class="Properties">
						<a>
							<xsl:attribute name="href">./<xsl:value-of select="@classname"/>-err.txt</xsl:attribute>
                        System.err &#187;
                    </a>
					</div>
				</xsl:if>
			</body>
		</html>
	</xsl:template>




  <!--
   Write properties into a JavaScript data structure.
   This is based on the original idea by Erik Hatcher (ehatcher@apache.org)
   -->
	<xsl:template match="properties">
    cur = TestCases['<xsl:value-of select="../@package"/>.<xsl:value-of select="../@classname"/>'] = new Array();
    <xsl:for-each select="property">
    <xsl:sort select="@name"/>
        cur['<xsl:value-of select="@name"/>'] = '<xsl:call-template name="JS-escape"><xsl:with-param name="string" select="@value"/>
			</xsl:call-template>';
	cur['<xsl:value-of select="@name"/>'] = '<xsl:value-of select="@value" />';
    </xsl:for-each>
	</xsl:template>


<!-- ======================================================================
    This page is created for every package.
    It prints the name of all classes that belongs to this package.
    @param name the package name to print classes.
     ====================================================================== -->
<!-- list of classes in a package -->
	<xsl:template name="classes.list">
		<xsl:param name="name"/>
		<html>
			<head>
				<title>Tests Result of Classes: <xsl:value-of select="$name"/>
				</title>
				<xsl:call-template name="create.stylesheet.link">
					<xsl:with-param name="package.name" select="$name"/>
				</xsl:call-template>
			</head>
			<body>
				<table width="100%">
					<tr>
						<td nowrap="nowrap">
							<h2>
								<a href="package-summary.html" target="classFrame">
									<xsl:value-of select="$name"/>
									<xsl:if test="$name = ''">&lt;none&gt;</xsl:if>
								</a>
							</h2>
						</td>
					</tr>
				</table>
				
				<h2>Classes</h2>
				<table width="100%">
					<xsl:for-each select="/test_execs/test_exec[(./@package = $name) and (not(./@classname = preceding-sibling::test_exec/@classname))]">
						<xsl:sort select="@classname"/>
						<tr>
							<td nowrap="nowrap">
								<a href="{@classname}.html" target="classFrame">
									<xsl:value-of select="@classname"/>
								</a>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>


<!--
    Creates an all-classes.html file that contains a link to all package-summary.html
    on each class.
-->
	<xsl:template match="test_execs" mode="all.classes">
		<html>
			<head>
				<title>All Unit Test Classes</title>
				<xsl:call-template name="create.stylesheet.link">
					<xsl:with-param name="package.name"/>
				</xsl:call-template>
			</head>
			<body>
				<h2>Classes</h2>
				<table width="100%">
					<xsl:apply-templates select="test_exec[not(./@classname = preceding-sibling::test_exec/@classname)]" mode="all.classes">
						<xsl:sort select="@classname"/>
					</xsl:apply-templates>
				</table>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="test_exec" mode="all.classes">
		<xsl:variable name="package.name" select="@package"/>
		<tr>
			<td nowrap="nowrap">
				<a target="classFrame">
					<xsl:attribute name="href">
						<xsl:if test="not($package.name='')">
							<xsl:value-of select="translate($package.name,'.','/')"/>
							<xsl:text>/</xsl:text>
						</xsl:if>
						<xsl:value-of select="@classname"/>
						<xsl:text>.html</xsl:text>
					</xsl:attribute>
					<xsl:value-of select="@classname"/>
				</a>
			</td>
		</tr>
	</xsl:template>


<!--
    Creates an html file that contains a link to all package-summary.html files on
    each package existing on test_execs.
    @bug there will be a problem here, I don't know yet how to handle unnamed package :(
-->
	<xsl:template match="test_execs" mode="all.packages">
		<html>
			<head>
				<title>All Unit Test Packages</title>
				<xsl:call-template name="create.stylesheet.link">
					<xsl:with-param name="package.name"/>
				</xsl:call-template>
			</head>
			<body>
            Test Report of <xsl:value-of select="date:date-time()" />
				<h2>
					<a href="overview-summary.html" target="classFrame">Home</a>
				</h2>
				<h2>Packages</h2>
				<table width="100%">
					<xsl:apply-templates select="test_exec[not(./@package = preceding-sibling::test_exec/@package)]" mode="all.packages">
						<xsl:sort select="@package"/>
					</xsl:apply-templates>
				</table>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="test_exec" mode="all.packages">
		<tr>
			<td nowrap="nowrap">
				<a href="./{translate(@package,'.','/')}/package-summary.html" target="classFrame">
					<xsl:value-of select="@package"/>
					<xsl:if test="@package = ''">&lt;none&gt;</xsl:if>
				</a>
			</td>
		</tr>
	</xsl:template>


<!--
SUMMARY
-->
	
	<xsl:template match="test_execs" mode="overview.packages">
		<html>
			<head>
				<title>Test Results: Summary</title>
				<xsl:call-template name="create.stylesheet.link">
					<xsl:with-param name="package.name"/>
				</xsl:call-template>
			</head>
			<body>
				<xsl:attribute name="onload">open('allclasses-frame.html','classListFrame')</xsl:attribute>
				<h1>Summary</h1>
				
				<h2>Packages</h2>
				<table class="details" border="0" cellpadding="5" cellspacing="2" width="100%">
					<xsl:call-template name="package.stat.header"/>
					<xsl:for-each select="test_exec[not(./@package = preceding-sibling::test_exec/@package)]">
						<xsl:sort select="@package" order="ascending"/>
						
						<xsl:variable name="insamepackage" select="/test_execs/test_exec[./@package = current()/@package]"/>
						<tr valign="top">
							
							<xsl:variable name="testCount" select="count($insamepackage/testcase/testunit)"/>
							<xsl:variable name="errorCount" select="count($insamepackage/testcase/testunit/error)"/>
							<xsl:variable name="failureCount" select="count($insamepackage/testcase/testunit/failure)"/>
							<xsl:variable name="successRate" select="($testCount - $failureCount - $errorCount) div $testCount"/>
							
							<xsl:attribute name="class">
								<xsl:choose>
									<xsl:when test="$successRate = 1">Pass</xsl:when>
									<xsl:otherwise>Error</xsl:otherwise>
								</xsl:choose>
							</xsl:attribute>
							
							
							<td>
								<a href="./{translate(@package,'.','/')}/package-summary.html">
									<xsl:value-of select="@package"/>
									<xsl:if test="@package = ''">&lt;none&gt;</xsl:if>
								</a>
							</td>
							<td>
								<xsl:value-of select="count($insamepackage/testcase/testunit)"/>
							</td>
							<td>
								<xsl:variable name="testCount" select="count($insamepackage/testcase/testunit)"/>
								<xsl:variable name="errorCount" select="count($insamepackage/testcase/testunit/error)"/>
								<xsl:variable name="failureCount" select="count($insamepackage/testcase/testunit/failure)"/>
								<xsl:variable name="successRate" select="($testCount - $failureCount - $errorCount) div $testCount"/>
								
								<xsl:call-template name="display-percent">
									<xsl:with-param name="value" select="$successRate"/>
								</xsl:call-template>
							
							</td>
							<td>
								<xsl:call-template name="display-time">
									<xsl:with-param name="value" select="sum($insamepackage/testcase/testunit/@time)"/>
								</xsl:call-template>
							
							</td>
						</tr>
					</xsl:for-each>
				</table>
				<br/>
				<h2>
					<a href="./profile.html" target="_blank">&gt;&gt; Statistics and profiling data</a>
				</h2>
			</body>
		</html>
	</xsl:template>
	
	
	<xsl:template name="package.summary">
		<xsl:param name="name"/>
		<html>
			<head>
				<xsl:call-template name="create.stylesheet.link">
					<xsl:with-param name="package.name" select="$name"/>
				</xsl:call-template>
			</head>
			<body>
				<xsl:attribute name="onload">open('package-frame.html','classListFrame')</xsl:attribute>
				<h1>Package <xsl:value-of select="$name"/>
				</h1>
				
				<xsl:variable name="insamepackage" select="/test_execs/test_exec[(./@package = $name) and (not(./@classname = preceding-sibling::test_exec/@classname))]"/>
				<xsl:if test="count($insamepackage) &gt; 0">
					<h2>Classes</h2>
					<p>
						<table class="details" border="0" cellpadding="5" cellspacing="2" width="100%">
							<xsl:call-template name="test_exec.test.header"/>
							<xsl:apply-templates select="$insamepackage" mode="print.class">
								<xsl:sort select="@classname"/>
							</xsl:apply-templates>
						</table>
					</p>
				</xsl:if>
			</body>
		</html>
	</xsl:template>


<!--
    transform string like a.b.c to ../../../
    @param path the path to transform into a descending directory path
-->
	<xsl:template name="path">
		<xsl:param name="path"/>
		<xsl:if test="contains($path,'.')">
			<xsl:text>../</xsl:text>
			<xsl:call-template name="path">
				<xsl:with-param name="path">
					<xsl:value-of select="substring-after($path,'.')"/>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="not(contains($path,'.')) and not($path = '')">
			<xsl:text>../</xsl:text>
		</xsl:if>
	</xsl:template>


<!-- create the link to the stylesheet based on the package name -->
	<xsl:template name="create.stylesheet.link">
		<xsl:param name="package.name"/>
		<link rel="stylesheet" type="text/css" title="Style">
			<xsl:attribute name="href">
				<xsl:if test="not($package.name = 'unnamed package')">
					<xsl:call-template name="path">
						<xsl:with-param name="path" select="$package.name"/>
					</xsl:call-template>
				</xsl:if>stylesheet.css</xsl:attribute>
		</link>
	</xsl:template>

<!-- package stat header -->
	<xsl:template name="package.stat.header">
		
		<tr valign="top">
			<th width="70%">Package Name</th>
			<th>TestUnits</th>
			<th>Success rate</th>
			<th nowrap="nowrap">Total time</th>
		</tr>
	</xsl:template>
                                                    
<!-- class header -->
	<xsl:template name="test_exec.test.header">
		<tr valign="top">
			<th width="80%">Class Name</th>
			<th>TestUnits</th>
			<th>Errors</th>
			<th>Failures</th>
			<th nowrap="nowrap">Time(s)</th>
		</tr>
	</xsl:template>

<!-- test case header -->
	<xsl:template name="testcase.test.header">
		<tr valign="top">
			<th width="80%">TestCase Name</th>
			<th>TestUnits</th>
			
			<th>Errors</th>
			<th>Failures</th>
			
			<th nowrap="nowrap">Time(s)</th>
		</tr>
	</xsl:template>
                                                    
<!-- method header -->
	<xsl:template name="testunit.test.header">
		<tr valign="top">
			<th>TestUnit Name</th>
			<th>Status</th>
			<th width="80%">Trace</th>
			<th nowrap="nowrap">Time</th>
		</tr>
	</xsl:template>
	
	<xsl:template match="test_exec" mode="print.testsuite">
		
		<h2>Test suite : <xsl:value-of select="@suitename" />
		</h2>
		<table class="details" border="0" cellpadding="5" cellspacing="2" width="100%">
			<xsl:call-template name="testcase.test.header"/>
              <!--
              test can even not be started at all (failure to load the class)
              so report the error directly
              -->
			<xsl:if test="./error">
				<tr class="Error">
					<td colspan="4">
						<xsl:apply-templates select="./error"/>
					</td>
				</tr>
			</xsl:if>
			<xsl:apply-templates select="testcase" mode="print.test"/>
		</table>
		
		<div class="Properties">
			<a>
				<xsl:attribute name="href">javascript:displayProperties('<xsl:value-of select="@package"/>.<xsl:value-of select="@classname"/>');</xsl:attribute>
                    Properties &#187;
                </a>
		</div>
		<xsl:if test="string-length(./system-out)!=0">
			<div class="Properties">
				<a>
					<xsl:attribute name="href">./<xsl:value-of select="@classname"/>-out.txt</xsl:attribute>
                        System.out &#187;
                    </a>
			</div>
		</xsl:if>
		<xsl:if test="string-length(./system-err)!=0">
			<div class="Properties">
				<a>
					<xsl:attribute name="href">./<xsl:value-of select="@classname"/>-err.txt</xsl:attribute>
                        System.err &#187;
                    </a>
			</div>
		</xsl:if>
	
	</xsl:template>
	
	
	<xsl:template match="test_exec" mode="print.class">
		<xsl:variable name="classname" select="./@classname" />
		<xsl:variable name="errors" select="count(//test_exec[./@classname = $classname]/testcase/testunit/error)" />
		<xsl:variable name="failures" select="count(//test_exec[./@classname = $classname]/testcase/testunit/failure)" />
		<xsl:variable name="tunits" select="count(//test_exec[./@classname = $classname]/testcase/testunit)" />
		<xsl:variable name="time" select="sum(//test_exec[./@classname = $classname]/testcase/testunit/@time)" />
		
		<tr valign="top">
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="$errors &gt; 0">Error</xsl:when>
					<xsl:when test="$failures &gt; 0">Failure</xsl:when>
					<xsl:otherwise>Pass</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			<td>
				<a href="{@classname}.html">
					<xsl:value-of select="@classname"/>
				</a>
			</td>
			<td>
				<xsl:value-of select="$tunits"/>
			</td>
			<td>
				<xsl:value-of select="$errors"/>
			</td>
			<td>
				<xsl:value-of select="$failures"/>
			</td>
			<td>
				<xsl:call-template name="display-time">
					<xsl:with-param name="value" select="$time"/>
				</xsl:call-template>
			</td>
		</tr>
	</xsl:template>
	
	<xsl:template match="testcase" mode="print.test">
		<tr valign="top">
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="count(./testunit/error) &gt; 0">Error</xsl:when>
					<xsl:when test="count(./testunit/failure) &gt; 0">Failure</xsl:when>
					<xsl:otherwise>Pass</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			
			<td>
				<a href="{../@classname}-{@name}.html">
					<xsl:value-of select="@name"/>
				</a>
			</td>
			<td>
				<xsl:value-of select="count(./testunit)"/>
			</td>
			
			<td>
				<xsl:value-of select="count(./testunit/error)"/>
			</td>
			<td>
				<xsl:value-of select="count(./testunit/failure)"/>
			</td>
			
			<td>
				<xsl:call-template name="display-time">
					<xsl:with-param name="value" select="sum(testunit/@time)"/>
				</xsl:call-template>
			</td>
		</tr>
	</xsl:template>
	
	
	<xsl:template match="testunit" mode="print.test">
		<tr valign="top">
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="error">Error</xsl:when>
					<xsl:when test="failure">Failure</xsl:when>
					<xsl:otherwise>TableRowColor</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			<td>
				<xsl:value-of select="@name"/>
			</td>
			<xsl:choose>
				<xsl:when test="failure">
					<td>Failure</td>
					<td>
						<xsl:apply-templates select="failure"/>
					</td>
				</xsl:when>
				<xsl:when test="error">
					<td>Error</td>
					<td>
						<xsl:apply-templates select="error"/>
					</td>
				</xsl:when>
				<xsl:otherwise>
					<td>Success</td>
					<td></td>
				</xsl:otherwise>
			</xsl:choose>
			<td>
				<xsl:call-template name="display-time">
					<xsl:with-param name="value" select="@time"/>
				</xsl:call-template>
			</td>
		</tr>
	</xsl:template>

<!-- Note : the below template error and failure are the same style
            so just call the same style store in the toolkit template -->
	<xsl:template match="failure">
		<xsl:call-template name="display-failures"/>
	</xsl:template>
	
	<xsl:template match="error">
		<xsl:call-template name="display-failures"/>
	</xsl:template>

<!-- Style for the error and failure in the testcase template -->
	<xsl:template name="display-failures">
		<xsl:choose>
			<xsl:when test="not(@type)">N/A</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="@type"/>
			</xsl:otherwise>
		</xsl:choose>

    <!-- display the stacktrace -->
		
		<br/>
		<br/>
		<code>
			<!--<xsl:call-template name="br-replace">
				<xsl:with-param name="word" select="."/>
			</xsl:call-template>
			-->
			<xsl:value-of select="." />
		</code>
		
    <!-- the latter is better but might be problematic for non-21" monitors... -->
    <!--pre><xsl:value-of select="."/></pre-->
	</xsl:template>
	
	<xsl:template name="JS-escape">
		<xsl:param name="string"/>
    <!-- <xsl:param name="tmp1" select="stringutils:replace(string($string), '\', '\\')"/> -->
		<xsl:param name="tmp1" select="$string" />
    <!-- <xsl:param name="tmp2" select="stringutils:replace(string($tmp1), &quot;&apos;&quot;, &quot;\&apos;&quot;)"/> -->
		<xsl:param name="tmp2" select="$string" />
		<xsl:value-of select="$tmp2"/>
	</xsl:template>


<!--
    template that will convert a carriage return into a br tag
    @param word the text from which to convert CR to BR tag
-->
	<xsl:template name="br-replace">
		<xsl:param name="word"/>
		<xsl:param name="br">
			<br/>
		</xsl:param>
		<xsl:value-of select='stringutils:replace(string($word),"&#xA;",$br)'/>
	</xsl:template>
	
	<xsl:template name="display-time">
		<xsl:param name="value"/>
		<xsl:value-of select="format-number($value,'0.000')"/>
	</xsl:template>
	
	<xsl:template name="display-percent">
		<xsl:param name="value"/>
		<xsl:value-of select="format-number($value,'0.00%')"/>
	</xsl:template>

<!-- template to create a empty statistics and profiling page that may be replaced later -->
	<xsl:template name="profile.html">
		<html xmlns:stringutils="xalan://org.apache.tools.ant.util.StringUtils" xmlns:lxslt="http://xml.apache.org/xslt">
			<head>
				<META http-equiv="Content-Type" content="text/html; charset=US-ASCII" />
				<link href="stylesheet.css" rel="stylesheet" type="text/css" title="Style" />
				<title>SSTree Tree Table</title>
			</head>
			<body>
				<h1>Profiling and statistics of last test execution</h1>
				<br />
there are no statistics or profiling data for the last test execution ! Next time launch runtest with option -stat
</body>
		</html>
	</xsl:template>
	
	<!--
	
	<xsl:template name="getClassName">
	-->
	<!-- ie. returns 'name' from the string 'something.else.name'  -->
	<!--
	<xsl:param name="item" />

	<xsl:choose>
		<xsl:when test="contains($item, '.')">
			<xsl:call-template name="getClassName">
				<xsl:with-param name="item" select="substring-after($item, '.')" />
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$item"/>
		</xsl:otherwise>
	</xsl:choose>
	
	</xsl:template>
	
	<xsl:template name="getPackageName">
	-->
	<!-- ie. returns 'name' from the string 'something.else.name'  -->
	<!--
	<xsl:param name="item" />

		<xsl:when test="contains($item, '.')">
			<xsl:value-of select="substring-before($item, '.')"/>
			<xsl:if test="contains(substring-after($item, '.'))">
				<xsl:text>.</xsl:text>
			</xsl:if>
			<xsl:call-template name="getPackageName">
				<xsl:with-param name="item" select="substring-after($item, '.')" />
			</xsl:call-template>
		</xsl:when>
	
	</xsl:template>
	-->

</xsl:stylesheet>
