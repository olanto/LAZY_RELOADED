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
import javax.xml.transform.*;
import javax.xml.transform.stream.*;


class SecureLogin{
    
    public static void askForPWD(HttpServletResponse response, String commenttag)
    throws ServletException, IOException {
        StringBuffer out=new StringBuffer("");
        
        System.out.println("fileURL"+ NodeServer.fileURL);
        
        out.append("<?xml version='1.0' ?>"); // utf-16  is the default for explorer
        
        //out.append("<?xml-stylesheet type='text/xsl' href='" + NodeServer.fileURL +"xsl/lazylog.xsl'?>");
        out.append("<lazy>");
        out.append("<lazycss>" + NodeServer.fileURL +"css/lazylog.css</lazycss>");
        out.append("<lazybody>" + NodeServer.fileURL +"bckgnd/lazylog.jpg</lazybody>");
        out.append("<title>lazy log</title>");
        out.append("<"+commenttag+"/>");
        out.append("<lazylog/>");
        out.append("</lazy>");
        
        response.setContentType("text/html; charset=utf-8");
        PrintWriter res = response.getWriter();
        XSLT(out.toString(), res);
        res.close();
        
    }
    
    static void XSLT(String xml, PrintWriter htmlFile){
        
        // doit être optimisé pour mettre en cache le transformateur (un par utilisateur)
        try{
            StringReader xmlFile = new StringReader(xml);
            //File xsltFile = new File(NodeServer.serverURL+"lazylog.xsl");
            File xsltFile = new File(NodeServer.serverURL+NodeServer.lazylogFilename);
            
            
            Source xmlSource =new StreamSource(xmlFile);
            Source xsltSource =new StreamSource(xsltFile);
            Result result =new StreamResult(htmlFile);
            
            // create an instance of TransformerFactory
            TransformerFactory transFact =TransformerFactory.newInstance(  );
            Timer t=new Timer("xslt factory");
            Transformer trans =  transFact.newTransformer(xsltSource);
            t.stop();
            //System.out.println(xml);
            t=new Timer("xslt transform");
            trans.transform(xmlSource,  result);
            t.stop();
            
        }catch (Exception e) {
            e.printStackTrace();}
    }
    
    
    
    public static void checkUser(
            String dbURL,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response,
            String firstURL,
            boolean automaticURL)
            throws ServletException, IOException {
        
        String user = (request.getParameter("login")).toUpperCase();
        String pwd = request.getParameter("password");        
        String direct = request.getParameter("direct");
        if (direct==null) direct="NO";
        session.setAttribute("DIRECT", direct);
        if (NodeServer.verboseConnect) System.out.println("user:"+user+"\tpassword:"+pwd);
        /**/
        try {
            
            // load user parameters  inversion login et pwd pour éviter sql injection dans login (R_ZBINDEN'--)
            QueryResult Q =
                    DBServices.execSQL(
                    "select defaultGrpId,lang,style,admin, infouser"
                    + " from secure_users where  pwd='"+Secure.encryptpwd(pwd)+"'  and  userid = '"+user+ "' ",
                    true);
            if (!Q.valid) {
                askForPWD(response, "nobd");
                return;
            }
            
            Q.result.next();
            session.setAttribute("GRP",Q.result.getString("defaultGrpId"));
            session.setAttribute("LANG",Q.result.getString("lang"));
            session.setAttribute("STYLE",Q.result.getString("style"));
            session.setAttribute("ADMIN",Q.result.getString("admin"));
            session.setAttribute("INFOUSER",Q.result.getString("infouser"));
            session.setAttribute("SESSION",session.getId());
            
            // load user grants
            String k=session.getAttribute("GRP")+"|PUBLIC_FILE."+session.getAttribute("INFOUSER")+"/PUBLIC/|REALM";    // user grants for user File        
            session.setAttribute(k,"ok"); // user grants for user File
            k=session.getAttribute("GRP")+"|SECURE._n1_"+session.getAttribute("INFOUSER");    // user grants for SECURE._n1_[[user]] for change grp                    
            session.setAttribute(k,"ok"); //user grants for SECURE._n1_[[user]] for change grp
            Q = DBServices.execSQL(
                    "select grpid,nodeid,typeid from secure_grantnodes where userid = '"+user+ "'",
                    true);
            if (Q.valid) {
                int nbRes=0;
                while (Q.result.next()) {
                    nbRes++;                    
                    k=Q.result.getString(1)+"|"+Q.result.getString(2)+"|"+Q.result.getString(3);
                    if (NodeServer.verboseConnect) System.out.println(k+" granted");
                    session.setAttribute(k,"ok");
                }
                if (NodeServer.verboseConnect) System.out.println("load grants  nb: "+nbRes);
                Q.result.close();
            }
            System.out.println("DB connection OK for:" + user + "\n");
            
            String identified = "YES";
            
            session.setAttribute("identified", identified);
            String userID = user;
            session.setAttribute("USER", userID);
            
            if(direct.equals("NO")){
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                
                if (automaticURL) out.println("<META HTTP-EQUIV=refresh CONTENT=\"1; URL=" + firstURL + "\">");
                out.println("<html>");
                out.println("<HEAD>");
                out.println("<TITLE>Authentification</TITLE>");
                out.println("</HEAD>");
                out.println("<BODY>");
                out.println("<H3>Connection OK (" + user + ")</H3>");
                out.println("</BODY>");
                out.println("</html>");
                
                
                out.close();
            }
        } catch (Exception e) {
            System.out.println("*** Error in connection for user: " + user);
            askForPWD(response, "noconnect");
        }
    }
    
    
    public static boolean checkAccess(String nodeID, String grpID,String userID,String typeID, HttpSession session) {
        
        String grants="",ok="";     
        if (NodeServer.verboseConnect) System.out.println("ChecAccess Node"+grpID+"|"+nodeID+"|"+typeID);
        if (!typeID.equals("TABLE")){  // for nodes.*
         //   grants=grpID+"|"+nodeID.substring(0,nodeID.indexOf("."))+".*|"+typeID;            
            String nodeStar = nodeID.substring(0,nodeID.indexOf("."))+".*";
            grants=grpID+"|"+nodeStar+"|LAZY";            
            ok = (String)session.getAttribute(grants);// try any node in the grpid for this user (PROJECT.*)
            if (ok != null) {return true;}
            grants="PUBLIC|"+nodeStar+"|LAZY";
            ok = (String)session.getAttribute(grants);// try Public and node for this user
            if (ok != null) {return true;}
        }
        grants=grpID+"|"+nodeID+"|"+typeID;
        ok = (String)session.getAttribute(grants);// try grpi and node for this user
        if (ok != null) {return true;}
        grants="PUBLIC|"+nodeID+"|"+typeID;
        ok = (String)session.getAttribute(grants);// try Public and node for this user
        if (ok != null) {return true;}
        if (userID.equals("LOCAL_RUN_ON_HOST")) {return true;}  // try if connect on host
        if (((String)session.getAttribute("ADMIN")).equals("ADMIN")) {return true;}  // try if admin
        return false;
    }
    
    public static boolean checkAccessPath(String path, String grpID,String userID,String typeID, HttpSession session) {
        if (NodeServer.verboseFILE) System.out.println("path:"+path+" grpID:"+grpID+" userID:"+userID);       
    //    String ok = (String)session.getAttribute(path);   //accessPublic_File only                    
    //    if (ok != null) {return true;}                    //accessPublic_File Only   
        int nextslash=0;
        nextslash=path.indexOf("/",nextslash);
        while (nextslash!=-1){
            String subpath=path.substring(0,nextslash+1);
            if (NodeServer.verboseFILE) System.out.println("subpath:"+subpath+" from 0 to "+nextslash);
            String grants=grpID+"|"+subpath+"|"+typeID;
            if (NodeServer.verboseFILE)System.out.println(grants);
            String ok = (String)session.getAttribute(grants);// try grpi and node for this user
            if (NodeServer.verboseFILE)System.out.println("ok"+ok);
            if (ok != null) {return true;}
            nextslash=path.indexOf("/",nextslash+1);
        }
        //if (((String)session.getAttribute("ADMIN")).equals("ADMIN")) {return true;}  // try if admin
        return false;
    }
}

