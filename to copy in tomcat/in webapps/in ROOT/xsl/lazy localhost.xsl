<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/" >
            <HTML>
              <HEAD>
               <META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
                <TITLE><xsl:apply-templates select="/lazy/title"/></TITLE>
              </HEAD>
	           <script>
	              function NavigateToArticle(f){ 
                 window.navigate(f);
	              } 
	           </script>
                <xsl:apply-templates/>
            </HTML>
    </xsl:template>
    
    
    <xsl:template match="*">
           <xsl:element name = "{name(.)}">
                <xsl:apply-templates/>
           </xsl:element>	
    </xsl:template>

    <xsl:template match="text()">
    
	    <xsl:value-of select="."/>
    </xsl:template>
    <xsl:template match="lazy">             
             <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="title">
    </xsl:template>
    <xsl:template match="post">
           <hr/>
           <b><xsl:apply-templates/></b>
    </xsl:template>
     <xsl:template match="basdepage">
           <hr/>
           <b><xsl:apply-templates/></b>
    </xsl:template>
     <xsl:template match="CloseIt">
	  <img><xsl:attribute name="src">
                 http://62.147.229.43:1880/icon/close.gif
               </xsl:attribute>
               <xsl:attribute name="border">0</xsl:attribute>
         </img>
     </xsl:template>
     <xsl:template match="lazycss">
	  <link><xsl:attribute name="rel">stylesheet</xsl:attribute>
               <xsl:attribute name="type">text/css</xsl:attribute>
               <xsl:attribute name="href"><xsl:value-of select="."/></xsl:attribute>
         </link>
     </xsl:template>
     <xsl:template match="lazybody">
	  <body><xsl:attribute name="onload">NavigateToArticle('#expanded')</xsl:attribute>
               <xsl:attribute name="background"><xsl:value-of select="."/></xsl:attribute>
         </body>
     </xsl:template>
    <xsl:template match="form">
	  <form>
           <xsl:attribute name="action"><xsl:value-of select="@action"/></xsl:attribute>
           <xsl:attribute name="method"><xsl:value-of select="@method"/></xsl:attribute>
           <xsl:apply-templates/>
	  <input>
           <xsl:attribute name="type">hidden</xsl:attribute>
           <xsl:attribute name="name">dummypatch</xsl:attribute>
           <xsl:attribute name="value">testnextversion</xsl:attribute>
          </input>
        </form>
     </xsl:template>
    <xsl:template match="input">
	  <input>
           <xsl:attribute name="type"><xsl:value-of select="@type"/></xsl:attribute>
           <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
           <xsl:attribute name="size"><xsl:value-of select="@size"/></xsl:attribute>
           <xsl:attribute name="value"><xsl:value-of select="@value"/></xsl:attribute>
           <xsl:attribute name="checked"></xsl:attribute>
          </input>
    </xsl:template>
    <xsl:template match="textarea">
	  <textarea>
           <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
           <xsl:attribute name="rows"><xsl:value-of select="@rows"/></xsl:attribute>
           <xsl:attribute name="cols"><xsl:value-of select="@cols"/></xsl:attribute>
           <xsl:value-of select="."/></textarea>
     </xsl:template>
    <xsl:template match="select">
	  <select>
           <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
           <xsl:apply-templates/>
          </select>
     </xsl:template>
    <xsl:template match="option">
	  <option>
           <xsl:apply-templates/>
          </option>
     </xsl:template>
    <xsl:template match="selected_option">
          <xsl:attribute name="selected"></xsl:attribute>
     </xsl:template>
    <xsl:template match="value">
          <xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute>
     </xsl:template>
     
    <xsl:template match="ICON">
	 	<img>
	  		<xsl:choose>
	  			<xsl:when test = ".//COMMENT">
	  		  		<xsl:attribute name="alt"><xsl:value-of select="COMMENT"/></xsl:attribute>	
	        		<xsl:attribute name="src">
               	http://localhost:1880/icon/<xsl:value-of select="IMG"/>.gif
           		</xsl:attribute>
         	</xsl:when>
         	
         	<xsl:otherwise>
         		<xsl:attribute name="src">
               	http://localhost:1880/icon/<xsl:value-of select="."/>.gif
           		</xsl:attribute>
         	</xsl:otherwise>	
         </xsl:choose>
         <xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>		
       </img>
     </xsl:template>  		
    
 
 
    <xsl:template match="embeded">
    	<center>
	  		<EMBED>
              <xsl:attribute name="SRC">                 
                 http://localhost:1880/graph?url=http://lzy1:1880/lazy/ns?a=GRAPH.graphics&amp;tn=xml&amp;<xsl:value-of select="."/>&amp;login=r_zbinden&amp;password=kjoonc6&amp;direct=YES
               </xsl:attribute>
               <xsl:attribute name="width">100%</xsl:attribute>
               <xsl:attribute name="height">85%</xsl:attribute>
     		</EMBED>
     	</center>
    </xsl:template>

    <xsl:template match="TABLE|TABLE1|TABLE2|TABLE3|TABLE4|TABLE5|TABLE_BG1|TABLE_BG2|TABLE_BG3|TABLE_MIN|TABLE_BG4">
	  <table><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
              <xsl:apply-templates/>
          </table>
    </xsl:template>
    
    <xsl:template match="CAPTION">
	  <caption><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
              <xsl:apply-templates/>
          </caption>
    </xsl:template>
    <xsl:template match="CELL_TITLE|CELL_TITLE_R">
	  <th><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
              <xsl:apply-templates/></th>
    </xsl:template>
    <xsl:template match="ROW|ROWT|ROW1|ROW2|ROW3|ROW4|ROW5">
	  <tr><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
              <xsl:apply-templates/></tr>
    </xsl:template>
    <xsl:template match="ROW_ALT">
	  <tr>
	  <xsl:attribute name="class">            
                <xsl:if test="position() mod 2 = 1">ROWODD</xsl:if>
                <xsl:if test="position() mod 2 = 0">ROWEVEN</xsl:if>            
         </xsl:attribute>
         <xsl:apply-templates/>
	  </tr>
    </xsl:template>
    <xsl:template match="CELL|CELLT|CELL_BG1|CELL_BG1_R|CELL_BG2|CELL_R|CELL_BG2_UP|CELL_WARNING|CELL_FIX|CELL_LIB|CELL_CODE|CELL_VAL">
  	  <td><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
              <xsl:apply-templates/>
          </td>          
    </xsl:template>
    <xsl:template match="COLSPAN">
          <xsl:attribute name="colspan"><xsl:value-of select="."/></xsl:attribute>
     </xsl:template>
    <xsl:template match="ROWSPAN">
          <xsl:attribute name="colspan"><xsl:value-of select="."/></xsl:attribute>
     </xsl:template>
    
    <xsl:template match="CELL_print">
  	  <td><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
              <xsl:apply-templates/>
          </td>          
    </xsl:template>
    
    
    
    
    <xsl:template match="LIST">
  	  <ul><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
              <xsl:apply-templates/>
          </ul>          
    </xsl:template>
    <xsl:template match="LIST_NUM">
  	  <ol><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
              <xsl:apply-templates/>
          </ol>          
    </xsl:template>
    <xsl:template match="ITEM">
  	  <li><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
              <xsl:apply-templates/>
          </li>          
    </xsl:template>
    
    <xsl:template match="TITLE_PAGE|TITLE2|TITLE3">
        <p><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
           <xsl:apply-templates/>
        </p>
    </xsl:template>

    <xsl:template match="FRAMESET">
	  <frameset>xx
	        <xsl:attribute name="COLS"><xsl:value-of select="@COLS"/></xsl:attribute>
	        <xsl:attribute name="ROWS"><xsl:value-of select="@ROWS"/></xsl:attribute>
             <xsl:apply-templates/>
          </frameset>
    </xsl:template>

    <xsl:template match="FRAME">
	  <frame>yy
	        <xsl:attribute name="NAME"><xsl:value-of select="@NAME"/></xsl:attribute>
	        <xsl:attribute name="SRC"><xsl:value-of select="@SRC"/></xsl:attribute>
             <xsl:apply-templates/>
          </frame>
    </xsl:template>


    <xsl:template match="a">
	  <a>   <xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute>
	        <xsl:attribute name="target"><xsl:value-of select="@target"/></xsl:attribute>
             <xsl:apply-templates/></a>
    </xsl:template>
    
    <xsl:template match="focus">
	  <a>     <xsl:attribute name="name">
	              <xsl:value-of select="."/>
             </xsl:attribute>
    </a>
    </xsl:template>
    
     <xsl:template match="POPUP">
             <xsl:attribute name="title">
                 <xsl:value-of select="."/>
             </xsl:attribute>            
    </xsl:template>
	
	<xsl:template match="IMAGE">
	 	<img>
	 		<xsl:attribute name="border">0</xsl:attribute>	
	 		<xsl:attribute name="src">
	 	 		<xsl:value-of select="."/>
        	</xsl:attribute>
     	</img>
	</xsl:template>
	 
	<xsl:template match="IMAGE_B">
	 	<img>
	 		<xsl:attribute name="height">500</xsl:attribute>	
	 		<xsl:attribute name="border">0</xsl:attribute>	
	 		<xsl:attribute name="src">
	 	 		<xsl:value-of select="."/>
        	</xsl:attribute>
     	</img>
	</xsl:template>
	
	<xsl:template match="IMAGE_S">
	 	<img>
	 		<xsl:attribute name="height">100</xsl:attribute>	
	 		<xsl:attribute name="border">0</xsl:attribute>	
	 		<xsl:attribute name="src">
	 	 		<xsl:value-of select="."/>
        	</xsl:attribute>
     	</img>
	</xsl:template>
    

    <xsl:template match="P|P_small">
         <p><xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
             <xsl:apply-templates/>    
         </p>
    </xsl:template>
    
     <xsl:template match="APPLET">
	  <applet>
	   <xsl:attribute name="codebase"><xsl:value-of select="@codebase"/></xsl:attribute>
           <xsl:attribute name="code"><xsl:value-of select="@code"/></xsl:attribute>
           <xsl:attribute name="archive"><xsl:value-of select="@archive"/></xsl:attribute>
           <xsl:attribute name="width"><xsl:value-of select="@width"/></xsl:attribute>
           <xsl:attribute name="height"><xsl:value-of select="@height"/></xsl:attribute>
           <xsl:apply-templates/>
         </applet>
    </xsl:template>
    <xsl:template match="PARAM">
        <param>
           <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute> 
           <xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute> 
        </param>
    </xsl:template>
	
	 <xsl:template match="NEW_WINDOW">	           	
       	<xsl:variable name="a_node" select= "@a_node"/>  	      	
       	<xsl:variable name="a_title" select= "@a_title"/>  	      	
       	<xsl:variable name="a_param" select= "@a_param"/>  
       	<xsl:variable name="a_value" select= "@a_value"/>  	      	
		<input type="button" value="{$a_value}"						
		onClick="window.open('ns?a={$a_node}','{$a_title}','{$a_param}');">		
		</input>				
	 </xsl:template>

    
    <xsl:template match="CLOSE_WINDOW">	           	
		<input type="button" value="Fermer La Fenêtre"						
		onClick="window.close();">		
		</input>				
	 </xsl:template>
    


</xsl:stylesheet>