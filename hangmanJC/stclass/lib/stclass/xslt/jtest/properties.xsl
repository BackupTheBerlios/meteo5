<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" >
  <xsl:output xmlns:xsl="http://www.w3.org/1999/XSL/Transform" method="html" indent="yes" encoding="US-ASCII" />
  <xsl:template xmlns:xsl="http://www.w3.org/1999/XSL/Transform" match="testsuite">
    <html>
      <head>
        <title>STclass test report</title>
        <style type="text/css" >
      body {
        font:normal 68% verdana,arial,helvetica;
        color:#000000;
      }
      table tr td, tr th {
          font-size: 68%;
          
      }
      table.details tr th{
        font-weight: bold;
        text-align:left;
        background:#a6caf0;
      }
      table.details tr td{
        background:#eeeee0;
      }
      
      p {
        line-height:1.5em;
        margin-top:0.5em; margin-bottom:1.0em;
        margin-left:2em;
        margin-right:2em;
      }
      h1 {
        margin: 0px 0px 5px; font: 165% verdana,arial,helvetica
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
        font-weight:bold; color:purple;
      }
      .Properties {
        text-align:right;
      }
  </style>
      </head>
      <body>
          <h1>Report for : 
              <xsl:value-of xmlns:xsl="http://www.w3.org/1999/XSL/Transform" select="./@classid" /> 
              Testsuite : 
              <xsl:value-of xmlns:xsl="http://www.w3.org/1999/XSL/Transform" select="./@testsuiteid" />
        </h1>
        <table class="details" >
          <tr>
            <td>
              <h2>Property name</h2>
            </td>
            <td>
              <h2>Property value</h2>
            </td>
          </tr>
          <xsl:for-each xmlns:xsl="http://www.w3.org/1999/XSL/Transform" select="./properties/property" >
              <!--<sort xmlns="http://www.w3.org/1999/XSL/Transform" select="@name" />-->
            <tr>
              <td>
                <xsl:value-of xmlns:xsl="http://www.w3.org/1999/XSL/Transform" select="@name" />
              </td>
              <td>
                <xsl:value-of xmlns:xsl="http://www.w3.org/1999/XSL/Transform" select="@value" />
              </td>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
