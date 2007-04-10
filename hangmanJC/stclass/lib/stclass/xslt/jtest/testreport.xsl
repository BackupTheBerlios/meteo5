<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" >
    <xsl:output method="html" indent="yes" encoding="US-ASCII" />
    <xsl:template  match="xmlreport" >
        <html>
            <head>
                <title>STclass test report</title>
            </head>
  
            <body>
                
                <h1>Report for : 
                    <xsl:value-of select="./testsuite/@classid" /> 
                    Testsuite : 
                    <xsl:value-of select="./testsuite/@testsuiteid" />
                </h1>
                
        
                <xsl:for-each select="./testsuite/testcase" >
                    <h1>TestCase report :
                        <xsl:value-of select="@name" />
                    </h1>
          
                    <table class="details" >
                        <tr>
                            <td>
                                <h2>TestUnit Name</h2>
                            </td>
                            <td>
                                <h2>Class Name</h2>
                            </td>
                            <td>
                                <h2>Type</h2>
                            </td>
                            <td>
                                <h2>Time</h2>
                            </td>
                        </tr>
                        <xsl:for-each select="./testunit" >
                            <tr>
                                <td>
                                    <xsl:value-of select="@name" />
                                </td>
                                <td>
                                    <xsl:value-of select="@classname" />
                                </td>
                                <td>
                                    <xsl:value-of select="@type" />
                                </td>
                                <td>
                                    <xsl:value-of select="@time" />
                                </td>
                            </tr>
                        </xsl:for-each>
                    </table>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
