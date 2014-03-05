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



public class Node {
    
    //static private Hashtable nodeDefCache=new Hashtable();
    static private TreeMapCache nodeDefCache = new TreeMapCache(NodeServer.NODE_HISTORY, "NODE_DEF");
    static private Hashtable Text=initText() ;
    
    boolean valid = true;
    boolean NOinclude = true;
    int nbparam;
    String projectid, nodeName, pre, post, collection, items, selector, order, groupby;
    String preSQL, itemsSQL, postSQL;
    String msg = "";
    private TreeMapCache Cache;
    private int cachesize=-1;
    
    Node() {
    }
    Node(String wnodeName) {
        nodeName = wnodeName;
    }
    
    public static String getXSLFileName(String nodename){
        String keytxt=getProjectName(nodename)+","+NodeServer.defaultLang+",!A_XSL";
        return (String)Text.get(keytxt);
    }
    public static String getXSLTFileName(String nodename){
        String keytxt=getProjectName(nodename)+","+NodeServer.defaultLang+",!A_XSLT";
        return (String)Text.get(keytxt);
    }
    public static String getCSSFileName(String nodename){
        String keytxt=getProjectName(nodename)+","+NodeServer.defaultLang+",!A_CSS";
        return (String)Text.get(keytxt);
    }
    public static String getConId(String nodename){
        String keytxt=getProjectName(nodename)+","+NodeServer.defaultLang+",!A_DB";
        return (String)Text.get(keytxt);
    }
    public static String getBCKGNDFileName(String nodename){
        String keytxt=getProjectName(nodename)+","+NodeServer.defaultLang+",!A_BCKGND";
        return (String)Text.get(keytxt);
        
    }
     public static String getROOTFileName(String projectname){
        String keytxt=projectname+","+NodeServer.defaultLang+",!A_ROOT";
        return (String)Text.get(keytxt);
    }
   
    public static Hashtable initText(){
        Hashtable ht= new Hashtable();
        String sqlRequest="select projectid,lang,txtid,lib from secure_alltxt";
        QueryResult Q = DBServices.execSQL(sqlRequest, true);
        
        if (Q.valid) {
            
            try {
                int nbRes=0;
                while (Q.result.next()) {
                    nbRes++;
                    String k=Q.result.getString(1)+","+Q.result.getString(2)+","+Q.result.getString(3);// prj, lang, key
                    String v=Q.result.getString(4);
                    if (NodeServer.verboseCache) System.out.println("load Text :"+k+"=>"+v);
                    ht.put(k,v);//
                }
                if (NodeServer.verboseCache) System.out.print("load Text in hashtable nb: "+nbRes);
                Q.result.close();
            } // try
            catch (SQLException e) {
                System.out.println("Nodes: during initText() SQLError: " + e.getMessage());
            }
        }
        else System.out.println("Nodes: during initText() SQLError: " + Q.msg);
        return ht;
    }
    
    public static void clearAllNodes() { // remove all definition from cache
        if (NodeServer.verboseClearCache) System.out.println("Nodes: clear all nodes definitions");
        nodeDefCache.clear();
        Text=initText(); // must be optimize
    }
    
    public static void clearDependentNodes(String modifyobject) { // remove cache dependent nodes
        modifyobject=modifyobject.toUpperCase();
        if (NodeServer.verboseClearCache) System.out.println("clearDependentNodes: clear dependents nodes for: "+modifyobject);
        try {
            String sqlRequest="select distinct projectid, name from secure_nodecachereset where objismodify='"+
            modifyobject+"'";
            QueryResult Q = DBServices.execSQL(sqlRequest, true);
            if (Q.valid) {
                int nbRes=0;
                while (Q.result.next()) {
                    nbRes++;
                    String project=Q.result.getString(1);
                    String name=Q.result.getString(2);
                    clearNode(project, name);
                }
                Q.result.close();
            }
            else System.out.println("clearDependentNodes:  error in select\n");
        } // try
        catch (SQLException e) {
            System.out.println("clearDependentNodes:  SQLError: " + e.getMessage()+"\n");}
        Text=initText(); // must be optimize
    }
    
    public static void clearNode(String project, String name) { // remove all definition from cache for this node
                    if (NodeServer.verboseClearCache) System.out.println("clearNode: clear CACHE for node: " + project+"."+name);
                    Node N = (Node) nodeDefCache.get(project+"."+name); // try to load from cache
                    if (N != null) { // already in the cache
                            if (NodeServer.verboseClearCache) System.out.println("clearNode: in CACHE so clear it!: " + project+"."+name);
                            nodeDefCache.remove(project+"."+name);
                    }
    }
    
    
    public static String getProjectName(String s){
        int ix = s.indexOf(".");
        if (ix==-1) {System.out.println("Nodes: error in project name P.N :" + s); return "***ERROR***";}
        return s.substring(0,ix);
    }
    
    public static String getNodeName(String s){
        int ix = s.indexOf(".");
        if (ix==-1) {System.out.println("Nodes: error in node name P.N :" + s); return "***ERROR***";}
        return s.substring(ix+1,s.length());
    }
    
    public static String  getDynamicInfo(String[] params){
        if (params[2].equals("DEF")){
            return nodeDefCache.getXMLStatistic();
        }
        if (params[2].equals("GLOBAL")){
            return TreeMapCache.getXMLGlobalStatistic();
        }
        if (params[2].equals("PROJECT")){
            Node N = (Node) nodeDefCache.get(params[0]+"."+params[1]); // try to load from cache
            if (N != null) { // already in the cache
                return N.Cache.getXMLStatistic();
            }
            else return "<CELL>no statistic</CELL>"+
            "<CELL></CELL>"+
            "<CELL></CELL>"+
            "<CELL></CELL>"+
            "<CELL></CELL>"+
            "<CELL></CELL>"+
            "<CELL></CELL>";
        }
        return "<CELL>error in parameter</CELL>"+
        "<CELL></CELL>"+
        "<CELL></CELL>"+
        "<CELL></CELL>"+
        "<CELL></CELL>"+
        "<CELL></CELL>"+
        "<CELL></CELL>";
    }
    
    public static Node getNodeDefinition(String nodeName) {
        
        Node N = (Node) nodeDefCache.get(nodeName); // try to load from cache
        
        if (N != null) { // already in the cache
            if (NodeServer.verboseCache)
                System.out.println(
                "Nodes: loading definition from CACHE for node: " + N.nodeName);
            return N;
        }
        
        // not in cache must be loaded from DB
        long starttime=System.currentTimeMillis();
        N = new Node(nodeName);
        
        if (NodeServer.verboseCache)
            System.out.println("Nodes: loading definition from BD for node: " + N.nodeName);
        String projectpart=getProjectName(N.nodeName);
        String nodepart=getNodeName(N.nodeName);
        if (NodeServer.verboseCache) System.out.println("Project: "+projectpart +" Node: " + nodepart);
        
        try {
            QueryResult Q =
            DBServices.execSQL(
            "select projectid,nbparam, pre, items, post, collection, selector, groupby, ordering, cachesize"
            + " from nodes where projectid = '"+ projectpart+"' and name = '"+ nodepart+ "'",
            true);
            if (!Q.result.next()) {
                N.valid = false;
                N.msg =
                "<hr/><h3>Database access error</h3>"
                + "<p>(the requested node:<b>"
                + N.nodeName
                + "</b> may not exist)</p>";
                return N;
            }
            N.projectid = Q.result.getString("projectid");
            N.nbparam = Q.result.getInt("nbparam");
            N.pre = Q.result.getString("pre");
            N.items = Q.result.getString("items");
            N.post = Q.result.getString("post");
            N.collection = Q.result.getString("collection");
            N.selector = Q.result.getString("selector");
            if (!(N.selector.equals("NODEF")))
                N.selector = " where " + N.selector; // selected by could be optional jg
            else
                N.selector = "";
            N.groupby = Q.result.getString("groupby");
            if (!(N.groupby.equals("NODEF")))
                N.groupby = " group by " + N.groupby; // group by could be optional jg
            else
                N.groupby = "";
            N.order = Q.result.getString("ordering");
            if (!(N.order.equals("NODEF")))
                N.order = " order by " + N.order; // order by could be optional jg
            else
                N.order = "";
            N.cachesize = Q.result.getInt("cachesize");
            if (N.cachesize==-1) N.cachesize=NodeServer.NODE_REQ_HISTORY;
            
            Q.result.close();
            // System.out.println("Nodes: Definition of node " + N.nodeName + " loaded");
            // build sql queries
            if (N.pre.equals("''"))
                N.preSQL = "";
            else
                N.preSQL = "select 1," + N.pre + " FROM " + N.collection + N.selector;
            if (N.post.equals("''"))
                N.postSQL = "";
            else
                N.postSQL = "select 1," + N.post + " FROM " + N.collection + N.selector;
            N.itemsSQL =
            "select " + N.items + " FROM " + N.collection + N.selector +N.groupby + N.order;
            
            N.NOinclude =
            (N.pre.indexOf(NodeServer.includePrefix) == -1)
            & (N.items.indexOf(NodeServer.includePrefix) == -1)
            & (N.post.indexOf(NodeServer.includePrefix) == -1);
            if (NodeServer.verboseCache)
                System.out.println("Nodes: no include : " + N.NOinclude);
            N.Cache = new TreeMapCache(N.cachesize, "NODE_REQ_" + nodeName);
            
            
        }
        catch (SQLException e) {
            System.out.println("Nodes: SQLError: " + e.getMessage());
            N.valid = false;
            N.msg = e.getMessage();
        }
        
        if (NodeServer.cacheDefinition)
            nodeDefCache.put(nodeName, N,(System.currentTimeMillis() - starttime)); // add to the cache
        return N;
    }
    
    public StringBuffer Actualize(String[] params,HttpSession session) { // get a instance of this node with this parameters
        
        String keyuser ="";
        String sqlall=preSQL+itemsSQL+postSQL; // examine all resquest
        if (sqlall.indexOf("[[USER]]")!=-1) keyuser+=session.getAttribute("USER"); else keyuser+="|";
        if ((sqlall.indexOf("[[LANG]]")!=-1)||(sqlall.indexOf("[[?")!=-1)) keyuser+=session.getAttribute("LANG"); else keyuser+="|";
        if (sqlall.indexOf("[[GRP]]")!=-1) keyuser+=session.getAttribute("GRP"); else keyuser+="|";
        if (sqlall.indexOf("[[STYLE]]")!=-1) keyuser+=session.getAttribute("STYLE"); else keyuser+="|";
                      
        String keyparam = keyuser+SU.concatParameters(params);
        StringBuffer content = (StringBuffer) Cache.get(keyparam);
        // try to load from cache
        
        if (content != null) { // already in the cache
            if (NodeServer.verboseCache)
                System.out.println(
                "Nodes: loading data from CACHE for node: "
                + nodeName
                + " with param: "
                + keyparam);
            return Secure.transformPrivatePart(content);
        }
        
        long starttime=System.currentTimeMillis();
        
        content = new StringBuffer();
        
        
        /// could be optimize --> on node
        //String keytxt=projectid+","+NodeServer.defaultLang+",!A_DB";
        //String conID=(String)Text.get(keytxt);
        String conID=getConId(nodeName);
        if (NodeServer.verboseConnect) System.out.println("connect to look for: "+nodeName+" found this connection: "+conID);
        if (conID==null) {conID="DICTLAZY";}
        
          if (NodeServer.verboseCharCoding) {
            for (int j=0;j<params.length;j++)
            System.out.println("AVANT doQuery params["+j+"]:"+params[j]);            
        }        
        doQuery(conID,nodeName, "PRE", preSQL, params, 1, 2, content, session, projectid);
        doQuery(conID,nodeName, "ITEMS", itemsSQL, params, 100000, 1, content, session, projectid);
        doQuery(conID,nodeName, "POST", postSQL, params, 1, 2, content, session, projectid);
        
        // System.out.println("+++"+content.toString());
        
        if (NodeServer.cacheNodeInstance)
            Cache.put(keyparam, content,(System.currentTimeMillis() - starttime)); // add to the cache
        
        
        return Secure.transformPrivatePart(content);
    }
    
    static  void doQuery(
    String conID,
    String nodeName,
    String nodeExecute,
    String sqlRequest,
    String[] params,
    int maxRes,
    int firstCol,
    StringBuffer content,
    HttpSession session,
    String projectid
    ) {
        
        if (!sqlRequest.equals("")) {
            
            StringBuilder resb = new StringBuilder();
            
            sqlRequest = SU.replaceParameters(sqlRequest, params);
            sqlRequest = SU.replaceSystemParameters(sqlRequest,session,Text,projectid);
            
            QueryResult Q = DBServices.execSQLonDB(sqlRequest, conID, true);
            //System.out.print("> executed "); ////
            
            if (Q.valid) {
                
                try {
                    ResultSetMetaData resultSchema = Q.result.getMetaData();
                    int nbCol = resultSchema.getColumnCount();
                    
                    //System.out.println("> meta ");   ////
                    int nbRes = 0;
                    while (nbRes < maxRes && Q.result.next()) {
                        
                        //System.out.print("> row ");  ////
                        nbRes++;
                        resb.setLength(0); // clear resb
                        for (int i = firstCol; i <= nbCol; i++) {
                            String cs = Q.result.getString(i);
                            //System.out.print("> col ");
                            if (!Q.result.wasNull())
                                resb.append(cs);
                        }
                        //System.out.println("resb.toString():"+resb.toString());////
                        //System.out.println("----replace    :"+replaceHrefs(resb.toString()));
                        //6.1.2004 jg content.append(SU.replaceHrefs(resb.toString()));
                        content.append(resb); //6.1.2004 jg retarde l'encodage des url
                        // definitely not !!! content.append("\n");   // could perturb XML ... ? but it's more readable !!! definitely not !!!
                        //System.out.println(".");
                    }
                    if (NodeServer.verboseDB) System.out.println("nb res du query (doQuery):"+nbRes);
                    Q.result.close();
                } // try
                catch (SQLException e) {
                    System.out.println("Nodes: SQLError: " + e.getMessage());
                    content = new StringBuffer();
                    content.append("<hr/><h3>Database access error</h3>");
                    content.append("<p>Error in node conversion:<b>" + nodeName + "</b></p>");
                    content.append("<p>during:<b>" + nodeExecute + "</b> part</p>");
                    content.append("<p>SQL text<b><![CDATA[" + sqlRequest + "]]></b></p>");
                    content.append("<p>SQLError: " + e.getMessage() + "</p>");
                }
                
            }
            else { // sql error
                content.append("<hr/><h3>Database access error</h3>");
                content.append("<p>Error in node execution:<b>" + nodeName + "</b></p>");
                content.append("<p>during:<b>" + nodeExecute + "</b> part</p>");
                content.append("<p>SQL text <b><![CDATA[" + sqlRequest + "]]></b></p>");
                content.append("<p>SQLError: " + Q.msg + "</p>");
            }
        } // if not empty
    }
}

