/* Lazy hypertext view system
Copyright (C) 2002-2003,  ISI Research Group, CUI, University of Geneva
 
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
You can also find the GNU GPL at http://www.gnu.org/copyleft/gpl.html
 
You can contact the ISI research group at http://cui.unige.ch/isi
 */
package isi.jg.ns;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;
import java.lang.reflect.*;




class SU { // strings utilities
    
    static final String hrefPrefix = "<a href=\"ns?";
    
    static String replaceHrefs(String s) {
        int ix = 0, ixf=0, ixflast=0;
        StringBuilder sb=new StringBuilder();
        while ((ix = s.indexOf(hrefPrefix, ix)) > -1) {
            ixf = s.indexOf("\">", ix);
            String uParameterPart = s.substring(ix, ixf);
            //System.out.println("first ix:"+ix+" ixf:"+ixf+" s:"+s);
            sb.append(s.substring(ixflast, ix));
            sb.append(getParameters(uParameterPart));
            ix = ixf;
            ixflast=ixf;
        }
        if (ixflast!=s.length()) sb.append(s.substring(ixflast, s.length()));  // copie la fin
        return sb.toString();
    }
    
    static String getParameters(String s2) {
        
        int ix2 = 0, ixf2 = 0, ixflast=0;
        StringBuilder sb=new StringBuilder();
        while ((ix2 = s2.indexOf("&amp;u=", ix2)) > -1) {
            ix2 = ix2 + 7;
            if ((ixf2 = s2.indexOf("&amp;u=", ix2)) < 0){
                ixf2=s2.length();}
            String uParameterPart = s2.substring(ix2, ixf2);
            sb.append(s2.substring(ixflast, ix2));
            sb.append(URLUTF8Encoder.encode(uParameterPart));
            
            ix2 = ixf2;
            ixflast=ixf2;
        }
        
        
        return sb.toString();
    }
    
    /************************************
     *
     *  DOUBLE QUOTE
     */
    static String doubleQuotes(String s) {
        int ix = 0, ixlast=0;
        StringBuilder sb=new StringBuilder();
        if (NodeServer.verboseCharCoding) System.out.println("Avant doubleQuote:"+s);
        while ((ix = s.indexOf("'", ix)) > -1) {
            sb.append(s.substring(ixlast, ix));
            sb.append("''");
            // s = s.substring(0, ix) + "''" + s.substring(ix + 1, s.length());
            ix += 1;
            ixlast=ix;
        }
        sb.append(s.substring(ixlast, s.length()));
        if (NodeServer.verboseCharCoding) System.out.println("Apres doubleQuote:"+sb);
        return sb.toString();
        
    }
    
    /************************************
     *
     *  For update ....
     */
    static String cleanValue(String encodeType, String s) {  // peut être optimisé
        int ix = 0;
        if (encodeType.equals("XML")){
            while ((ix = s.indexOf("&", ix)) > -1) {
                s = s.substring(0, ix) + "&amp;" + s.substring(ix + 1, s.length());
                ix += 4;
            }
            ix = 0;
            while ((ix = s.indexOf("<", ix)) > -1) {
                s = s.substring(0, ix) + "&lt;" + s.substring(ix + 1, s.length());
                ix += 3;
            }
        }
        ix = 0;
        while ((ix = s.indexOf("'", ix)) > -1) {
            s = s.substring(0, ix) + "''" + s.substring(ix + 1, s.length());
            ix += 3;
        }
        
        return s;
        
    }
    
    
    
    
    /************************************
     *
     *  REPLACE PARAMETERS
     */
    static String replaceParameters(String s2, String[] params) {
        String paramVal = "";
        int nbparam = 0, ix = 0, ixf, ixx, ixfLast=0;
        StringBuilder sb=new StringBuilder();
        
        if (params != null)
            nbparam = params.length;
        while ((ix = s2.indexOf("<<??param-", ix)) > -1) {
            int ipara = 0;
            ixf = s2.indexOf("//??>>", ix);
      //      System.out.println("S2 replacing "+s2.substring(ix, ixf)+" at "+ix+"--"+ixf);
            ixx = ix + 10; // length of "<<??param-"
            while (ixx < ixf) {
                ipara = ipara * 10 + (s2.charAt(ixx) - '0');
                ixx++;
            }
            if (ipara < nbparam)
                paramVal = doubleQuotes(params[ipara]);
            else
                paramVal = " ";
            sb.append(s2.substring(ixfLast, ix));
            sb.append("'" + paramVal + "'");
            ixfLast=ixf+6;
            ix=ixf;
        }
        sb.append(s2.substring(ixfLast, s2.length()));
        return sb.toString();
    }
    
    /************************************
     *
     *  REPLACE SYSTEM PARAMETERS
     */
    static String replaceSystemParameters(String s2,HttpSession session, Hashtable text, String projectid) {
        
        int ix = 0, ixf, ixx, ixfLast=0;
        String replacevalue="";
        StringBuilder sb=new StringBuilder();

        while ((ix = s2.indexOf(NodeServer.SystParamPrefix, ix)) > -1) {
            int ipara = 0;
            ixf = s2.indexOf(NodeServer.SystParamSuffix, ix);
            ixx = ix + 2; // length of "[["
            String var=s2.substring(ixx,ixf);
            if (NodeServer.verboseReplace) System.out.println("replacing s2"+s2.substring(ix, ixf)+" at "+ix+"--"+ixf+" var:"+var);
            if (!var.substring(0,1).equals("?")){  // not a project variable
                if (!var.substring(0,1).equals("!")){  // not a request variable
                    replacevalue=(String) (session.getAttribute(s2.substring(ixx,ixf)));
                } else {replacevalue =NodeServer.SystParamPrefix+var+NodeServer.SystParamSuffix; // a request variable dont modify now
                }
            } else {
                String userlang=((String)(session.getAttribute("LANG")));
                if (var.substring(1,2).equals("!")){  // project system variable jg 31.3.2003
                    userlang=NodeServer.defaultLang;
                }
                
                String keytxt=projectid+","+userlang+","+var.substring(1, var.length());
                if (NodeServer.verboseReplace) System.out.println("s2 look for "+keytxt);
                String value=(String)text.get(keytxt);
                if (value==null) {// try SHARE
                    keytxt="SHARE"+","+((String)(session.getAttribute("STYLE")))+","+var.substring(1, var.length());
                    if (NodeServer.verboseReplace) System.out.println("s2 look for "+keytxt);
                    value=(String)text.get(keytxt);
                    if (value==null) {
                        value="no definition for: "+var;}
                }
                replacevalue=value;
            }          
            sb.append(s2.substring(ixfLast, ix)   + replacevalue  ); 
            ix=ixx;
            ixfLast=ixf+2;
        }
        sb.append(s2.substring(ixfLast, s2.length()));        
        return sb.toString();
    }
    
    static void replaceQueryParameters(HttpSession session, String[] u) {
        
        if (u==null) return;
        for (int i=0 ;i<u.length ;i++){
            int ix = 0, ixf, ixx;
            String replacevalue="";
            String s=u[i];
            if (NodeServer.verboseCharCoding) System.out.println("AVANT U:"+s);
            while ((ix = s.indexOf(NodeServer.SystParamPrefix, ix)) > -1) {
                int ipara = 0;
                ixf = s.indexOf(NodeServer.SystParamSuffix, ix);
                ixx = ix + 2; // length of "[[!"
                String var=s.substring(ixx,ixf);
                replacevalue=(String) (session.getAttribute(s.substring(ixx,ixf)));
                if (NodeServer.verboseReplace) System.out.println("QueryParameters replacing "+s.substring(ix, ixf)+" at "+ix+"--"+ixf+" var:"+var+" ->:"+replacevalue);
                s = s.substring(0, ix)
                + replacevalue
                        + s.substring(ixf + 2, s.length()); // 2 length ]]
                ix=ixx;
            }
            if (NodeServer.verboseCharCoding) System.out.println("APRES U:"+s);
            u[i]= s;
        }
    }
    
    
    static String concatParameters(String[] params) {
        int nbparam = 0;
        String s = "";
        
        int ix = 0, ixf, ixx;
        
        if (params != null) {
            nbparam = params.length;
            for (int i = 0; i < nbparam; i++)
                s += "|" + params[i];
        }
        
        return s;
    }
    
}
