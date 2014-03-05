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
import org.apache.xmlrpc.XmlRpcServer;  // pour appeler LAZY en RPC
import javax.xml.transform.*;
import javax.xml.transform.stream.*;




/***************************************************
 *
 *    ns.java
 *
 * HISTORY
 *   Eearly versions of the Lazy node server have been written in
 *    - Applescript (ran with the Webstar server) (J. Guyot)
 *    - PL/SQL (ran in the Oracle Webserver) (JG, M. Bonjour)
 *
 * v2.5 - first version written in Java (a servlet with a JDBC connection) (G. Falquet)
 * v2.6 - (?)introduced 'expand-in-place' (GF)
 * v3.0 - active hrefs (database update) (GF)
 * v4.0 - node instance caching, security and encryption, node types, session variables,
 *		    correct concurrency handling, multiple connections, code restructuring (JG)
 * v4.1 - new login policy, simplified security scheme, project description without special attributes (A_XSL), etc.
 *      - uses the Java 1.4 encryption scheme
 *
 * p4.2 - professional version (j.guyot)
 *
 ***************************************************/

// 25.02.2002 encryption DES 64 bits included :
//		NE PAS OUBLIER DE DEFINIR L'ALGO DES AVEC runGegister.bat dans dossier LIB
//		--------------------------------------------------------------------------


// new dynamic node
// new clearnode(project,name) -> compiler ...

// 10.2.2003 correction expand(call external service)
// return expandIncludes(callExternalService(className,methodName,params), request,session); // call and expand

// 31.3.2003 correction d'un bug pour les !A_..  étaient dépendants de la langue de l'utilisateur
// modif dans replaceSystemParameters(..)

// 21.5.2003 ajout des fonctions de clearcache
// 6.5.2003 externaliser les masques de connextion

//  6.1.2004 insertion des includes dans les href, include et active href
//  6.1.2004 ajout du desc dans le order by

//  27.1.2004 ajout du paramètre MAX_CURSOR pour limiter le nombre de curseurs ouverts sur tous les serveurs

//  20.1.2004 correction d'un bug dans les caches [[? doit tenir compte de la langue

//  17.3.2004 ajout de la fonction SECURE.langid_modify["FRE"] pour modifier dynamiquement la langue
//  peut être inclus dans un noeud sans cache...

//  30.3.2004 ajout de la fonction RPC
//  - modifier xsl pour ajouter archive
//      <xsl:attribute name="archive"><xsl:value-of select="@archive"/></xsl:attribute>
//  - modifier le web.xml pour ajouter
//    <servlet-mapping>
//        <servlet-name>
//            ns
//        </servlet-name>
//        <url-pattern>
//            /rpc
//        </url-pattern>
//    </servlet-mapping>

// 2.11.2004 ajout de la possibité de hoster le xslt
// dans l'url on ajoute &tn=xslthosted pour le premier appel (après cela devient un défault)
// dans le projet, on ajoute le litéral !A_XSLT avec le nom du fichier qui contient le xsl
// le fichier est directement dans apache .../bin/xsl
// 12.01.2005 by Nes Dans le cas ou typenode=xslthosted ne pas appliquer la transformation  replaceHrefs
// avant l'appel de XSLT

// 20.04.2005 filtrages des noeuds dans ResteCache.ResetNodesDependencies() (ligne 38)
// par un ajout de "where status = 'VALID'" dans la requête SQL pour ne prendre que les noeuds
// correctements compilés pour la création de la liste des noeuds dépendants. (RZ)

// 22.04.2005 CheckAcess simplifié avec acceptation de tous les noeuds d'un projet: PROJECT.*
// dans la méthode SecureLogin.checkAccess (RZ)

// 07.09.2005 SU.replaceHrefs(prevContent) ajouté pour xslthosted pour être cohérant avec la version 1.5 java.
// pour version 1.4 java supprimer l'apper SU.replaceHrefs à prevContent pou se mettre dans l'état du
// 12.01.2005 (modif NS ci-dessus) (ligne 618 et 641 de méthode doGet)

// 15.03.2006 UPLOAD de fichier sur le serveur où est installé l'instance Tomcat:
// - 2 classes ajoutées: Upload et MultipartRequestParser
// - verboseUpload ajouté (nécessite adaptation de lazyProperties sur chaque instance)
// - "upload.path" ajouté dans lazyProperties pour définir un dossier par défaut des upload
// - Upload est appelé à travers un noeuds public: UTIL.upload[<FolderDestination>].
//   <FolderDestination> s'ajoute au chemin défini par "upload.path".
//   dans un noeud lazy quelconque on invoque l'upload ainsi: include UTIL.upload[<FolderDestination>]
//   et une fenêtre popup propose une interface pour uploader un fichier.  (RZ)


/* to do list
 
 
 
- réintroduire le zoommax, ....
 
 
 
 */

/**
 *
 * The servlet that processes node requests
 *
 * @author Jacques Guyot
 * @author Gilles Falquet
 * @version 4.2 Summer 2003
 *
 */


public class NodeServer extends HttpServlet {
    
    public static boolean verboseExpanding = false;
    public static boolean verboseCharCoding = false;
    public static boolean verboseCache = false;
    public static boolean verboseClearCache = false;
    public static boolean verboseModify = false;
    public static boolean verboseReplace = false;
    public static boolean verboseTiming = false;
    public static boolean verboseConnect = false;
    public static boolean verboseDB = false;
    public static boolean verboseCrypto = false;
    public static boolean verboseExternal = false;
    public static boolean verboseRPC = false;
    public static boolean verboseFILE = true;
    public static boolean verboseUpload = false;
    
    public static boolean cacheDefinition =true;
    public static boolean cacheNodeInstance = true;
    public static boolean DependentNodeClear = false;
    
    public static boolean computeSaveTime = false;
    
    String nsVersion = "4.5.11";
    static String fileSeparator = System.getProperty("file.separator");
    
    // URL for a connection to an ORACLE database
    String url = "jdbc:oracle:thin:@cuisund:1521:cui";
    String user = "lazy";
    String pwd = "lazy";
    String driver = "oracle.jdbc.driver.OracleDriver";
    static String fileURL = "http://127.0.0.0:8080"; // xsl, css file server
    static String serverURL = "C:/Tomcat 5.5/webapps/ROOT"; // tomcat Root file path
    static String lazylogFilename  = "lazylog.xsl";  //lazylog file name for this instance
    static String folderUpload = "c:"+fileSeparator+"LazyUpload";
    String hostAdress = "127.0.0.0"; // host Adress
    
    // constant Serveur LAZY
    public static int LENGTH_HISTORY = 20;
    public static int NODE_HISTORY = 1000;
    public static int NODE_REQ_HISTORY = 200;
    public static String SERVER_DEF_TYPE = "xml";
    public static boolean encryptOFF=false;
    public static String encryptKEY = "1212121212121212";
    public static  String UnicodeCollationSQL92 = ""; // other
    //static  String UnicodeCollationSQL92 = "N"; // unicode
    public static int maxInactiveInterval = 1800;  // in second (-1 = no limit)
    public static String defaultLang = "FRA";
    public static int MAX_CURSOR = 100;  // max cursor
    
    
    static final String includePrefix = "<<??include a=";
    static final String includeSuffix = "//??>>";
    static final String SystParamPrefix = "[[";                   // used in node definition
    static final String SystParamSuffix = "]]";
    static final String eipPrefix = "<a href=\"ns?eip=ZYX";
    static final String expandedBegin = "<!--#EXPANDED-:";
    static final String endComment = "-->";
    static final String expandedEnd = "<!--#END EXPANDED-:";
    static final String cipPrefix = "<a href=\"ns?cip=ZYX";
    static final String cipTextHTML ="<SPAN CLASS=\"closeit\">&gt;close&lt;</SPAN>";
    static final String cipTextXML = "<CloseIt/>";
    static final String focusTextHTML = "";
    static final String focusTextXML = "<focus>expanded</focus>";
    static final String msgNotInHistoryCache = "<h3>The context of this link was to old to be reloaded!</h3>";
    
    int reqID = 0;
    public static XmlRpcServer xmlrpc = new XmlRpcServer();
    
    
    
    
    /************************************
     *
     *  INIT
     */
    public void init(ServletConfig config) throws ServletException {
        
        
        // Obtain parameters  from   a ressource bundle  (file Lazy.propertier)
        
        
        
        ResourceBundle rb = ResourceBundle.getBundle("Lazy");
        url = rb.getString("database.url");
        user = rb.getString("database.user");
        pwd = rb.getString("database.password");
        driver = rb.getString("database.driver");
        fileURL = rb.getString("fileserver.url");
        serverURL = rb.getString("root.server");
        lazylogFilename = rb.getString("lazylog.filename");
        folderUpload= rb.getString("upload.path").replace('/',fileSeparator.charAt(0)).replace('\\',fileSeparator.charAt(0));
        hostAdress = rb.getString("host.adress");
        encryptKEY = rb.getString("encrypt.key");
        
        if (rb.getString("verbose.Expanding").equals("on")) verboseExpanding = true;
        if (rb.getString("verbose.CharCoding").equals("on")) verboseCharCoding = true;
        if (rb.getString("verbose.Cache").equals("on")) verboseCache = true;
        if (rb.getString("verbose.ClearCache").equals("on")) verboseClearCache = true;
        if (rb.getString("verbose.Modify").equals("on")) verboseModify = true;
        if (rb.getString("verbose.Replace").equals("on")) verboseReplace = true;
        if (rb.getString("verbose.Timing").equals("on")) verboseTiming = true;
        if (rb.getString("verbose.Connect").equals("on")) verboseConnect = true;
        if (rb.getString("verbose.DB").equals("on")) verboseDB = true;
        if (rb.getString("verbose.Crypto").equals("on")) verboseCrypto = true;
        if (rb.getString("verbose.External").equals("on")) verboseExternal = true;
        if (rb.getString("verbose.File").equals("on")) verboseFILE = true;
        if (rb.getString("verbose.Upload").equals("on")) verboseUpload = true;
        
        
        if (rb.getString("dependentnode.on").equals("on")) DependentNodeClear = true;
        if (rb.getString("encrypt.off").equals("off")) encryptOFF = true;
        LENGTH_HISTORY = Integer.parseInt(rb.getString("cache.length_history"));
        NODE_HISTORY = Integer.parseInt(rb.getString("cache.node_history"));
        NODE_REQ_HISTORY = Integer.parseInt(rb.getString("cache.node_req_history"));
        SERVER_DEF_TYPE = rb.getString("host.server_def_type");
        UnicodeCollationSQL92 = rb.getString("host.unicodecollation");
        defaultLang = rb.getString("host.defaultLang");
        maxInactiveInterval=Integer.parseInt(rb.getString("host.maxInactiveInterval"));
        MAX_CURSOR=Integer.parseInt(rb.getString("host.maxcursor"));
        if (rb.getString("host.computeSaveTime").equals("on")) computeSaveTime = true;
        
        
        System.out.println(
                "Initializing with :\n url="+ url
                + "\n user="+ user
                + "\n driver="+ driver
                + "\n fileserver="+ fileURL
                + "\n rootserver="+ serverURL
                + "\n lazylogFilename="+ lazylogFilename
                + "\n fileUpload="+ folderUpload
                + "\n hostAdress="+ hostAdress
                + "\n maxInactiveInterval="+ maxInactiveInterval
                + "\n computeSaveTime="+ computeSaveTime
                + "\n DEPENDENT NODE CLEAR CACHE="+ DependentNodeClear
                + "\n LENGTH_HISTORY="+ LENGTH_HISTORY
                + "\n NODE_HISTORY="+ NODE_HISTORY
                + "\n LENGTH_HISTORY="+ NODE_REQ_HISTORY
                + "\n SERVER_DEF_TYPE="+ SERVER_DEF_TYPE
                + "\n MAX_CURSOR="+ MAX_CURSOR
                + "\n verbose.Expanding="+ verboseExpanding
                + "\n verbose.CharCoding="+ verboseCharCoding
                + "\n verbose.Cache="+ verboseCache
                + "\n verbose.ClearCache="+ verboseClearCache
                + "\n verbose.Modify="+ verboseModify
                + "\n verbose.Replace="+ verboseReplace
                + "\n verbose.Timing="+ verboseTiming
                + "\n verbose.Connect="+ verboseConnect
                + "\n verbose.DB="+ verboseDB
                + "\n verbose.Crypto="+ verboseCrypto
                + "\n verbose.External="+ verboseExternal
                + "\n verbose.File="+ verboseFILE
                + "\n verbose.Upload="+ verboseUpload
                );
        
        
        // init secure engine
        Secure.init(encryptKEY,encryptOFF); // only half key is in the properties
        
        // make a connection with the data base
        DBServices.init(url, user, Secure.decryptpwd(pwd), driver);
        
        // clean dynamic node from schema
        QueryResult res=DBServices.execSQL("delete from nodes where substr(name,1,1)='_'",false);
        System.out.println("Dynamic nodes cleaned: "+res.nbUpdated);
        
        // recompute nodedependencies
        if (DependentNodeClear) System.out.println(ResetCache.ResetNodesDependencies());
        
        
        // init RPC server
        xmlrpc = new XmlRpcServer();
        xmlrpc.addHandler("rpc", new RPC());
        
        
    }
    
    /************************************
     *
     *  DESTROY close the data base connection
     */
    public void destroy() {
        DBServices.finish();
        
    }
    
   /*
    * the request parameters are as follows:
    *  a : node name
    *  u : node parameter value (multiple)
    *  eip : XYZnnnXYZppp : expand in place request for page ppp at location nnn
    *  act : new | upd | del : action to perform before node generation
    *  tbl : table to update
    *  an : attribute name (multiple), for insert (new), update, and delete
    *  av : attribute value (multiple)
    *  kn : key attribute name (multiple), for update and delete
    *  kv : key attribute value (multiple)
    *  tn : type node http/xml..
    *  login : user name
    *  password : password
    */
    
    /************************************
     *
     *  DO GET  parse the query string
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        
        
        TreeMapCache contentsHistory;
        String typeNode,
                prevContent,
                identified = "",
                userID,
                grpID,
                firstURL,
                clientAdress,
                firstconnection = "NO";
        long starttime = System.currentTimeMillis();
        
        reqID++; // request unique ID
        
        boolean isEIP = false; // is this request an "expand in place"
        boolean isCIP = false; // is this request a "close expanded in place"
        String eipNum = "";
        String anchorNum = "0";
        
        //
        // Create session history if new session
        //
        
        HttpSession session = request.getSession();
        System.out.println("-------------------------:");
        System.out.println("getRequestURL:"+request.getRequestURL());
        
        if (verboseConnect)
            System.out.println(
                    "\n"
                    + new java.util.Date(starttime)
                    + ": GET request for session "
                    + session.getId()
                    + "\n client :"
                    + request.getRemoteAddr()
                    + "\n query :"
                    + request.getQueryString());
        
        if (session.getAttribute("contentsHistory") == null) { // first connection!
            session.setMaxInactiveInterval(maxInactiveInterval);
            firstconnection = "YES";
            contentsHistory = new TreeMapCache(LENGTH_HISTORY, "REQUEST" + session.getId());
            session.setAttribute("contentsHistory", contentsHistory);
            typeNode = SERVER_DEF_TYPE; // default
            if (request.getParameterValues("tn") != null) {
                typeNode = request.getParameterValues("tn")[0];
            }
            session.setAttribute("typeNode", typeNode);
            grpID = "PUBLIC"; // default group of data
            session.setAttribute("GRP", grpID);
            clientAdress = request.getRemoteAddr();
            session.setAttribute("clientAdress", clientAdress);
            if (clientAdress.equals(hostAdress)) {
                identified = "YES";
                firstconnection = "NO";
                session.setAttribute("identified", identified);
                userID = "LOCAL_RUN_ON_HOST";
                session.setAttribute("USER", userID);
                System.out.println("identified..");
                if (NodeServer.verboseConnect) System.out.println(userID);
            } else {
                identified = "NO";
                session.setAttribute("identified", identified);
                userID = "PUBLIC";
                session.setAttribute("USER", userID);
                System.out.println("not identified..");
            }
            
            System.out.println(
                    "Create contentsHistory for this session (client):" + clientAdress);
            String[] login = request.getParameterValues("login");
            if (!userID.equals("LOCAL_RUN_ON_HOST")&&(login == null)) {
                // must be identified
                if (verboseConnect) System.out.println("must identified");
                SecureLogin.askForPWD(response, "firstlog");
                firstURL =
                        fileURL
                        + request.getContextPath().substring(1)
                        + request.getServletPath()
                        + "?"
                        + request.getQueryString();
                session.setAttribute("firstURL", firstURL);
            } else { // connect
                if (!userID.equals("LOCAL_RUN_ON_HOST")){
                    SecureLogin.checkUser( url,session, request, response, "", false);
                    String direct = (String) (session.getAttribute("DIRECT"));
                    if(verboseConnect)System.out.println("connection type direct:"+direct);
                    if (direct.equals("YES")) firstconnection="NO";
                }
            }
            
        } // end for first connection
        
        session.setAttribute("REQUESTID", ""+reqID); // set a new id for this session
        userID = (String) (session.getAttribute("USER"));
        grpID = (String) (session.getAttribute("GRP"));
        if (firstconnection.equals("YES") ) {
            /* do nothing*/
            System.out.println("first connect");
        } else { // not a first connection
            if (verboseConnect) System.out.println("not a first connection");
            identified = (String) (session.getAttribute("identified"));
            if (identified.equals("NO")) {
                String[] login = request.getParameterValues("login");
                if (login != null) {
                    firstURL = (String) (session.getAttribute("firstURL"));
                    //SecureLogin.checkUserIdInDB( url,session, request, response, firstURL);
                    SecureLogin.checkUser( url,session, request, response, firstURL,true);
                }
            } else { // already identified
                System.out.println("already identified -- getRequestURL:"+request.getRequestURL());
                if ((request.getRequestURL().toString()).endsWith("rpc")){ // command RPC
                    if (verboseRPC) System.out.println("XML-RPC RequestURL:"+request.getRequestURL());
                    RPC.execute(this,request,response,xmlrpc);
                } else { // url file
                    if ((request.getRequestURL().toString()).endsWith("file")){ // command get file
                        if (verboseFILE) System.out.println("FILE RequestURL:"+request.getRequestURL());
                        FILE.execute(this,request,response);
                    }else {// XML Publisher reporting
//                        if ((request.getRequestURL().toString()).endsWith("xmlReport")){ // command get XMLPublisher reporting
//                            if (verboseFILE) System.out.println("XMLPublisher report RequestURL:"+request.getRequestURL());
//                            XMLGetReport xmp = new XMLGetReport(); xmp.genreate(this,request,response,session);
//                        } else { // url lazy
                            if (request.getParameterValues("tn") != null) {  // recharge éventuellement le type de noeud
                                String urlval = request.getParameterValues("tn")[0];
                                session.setAttribute("typeNode", urlval);
                            }
                            
                            typeNode = (String) (session.getAttribute("typeNode"));
                            
                            if (typeNode.equals("html"))
                                response.setContentType("text/html; charset=utf-8");
                            if (typeNode.equals("purehtml"))
                                response.setContentType("text/html");
                            if (typeNode.equals("xml"))
                                response.setContentType("text/xml; charset=utf-8");
                            if (typeNode.equals("xslthosted"))
                                response.setContentType("text/html; charset=utf-8");
                            if (typeNode.equals("purexml"))
                                response.setContentType("text/xml; charset=utf-8");
                            if (typeNode.equals("svg"))
                                response.setContentType("image/svg");
                            
                            
                            PrintWriter out = response.getWriter();
                            
                            try{  // restart on last node if error !!!!!!!
                                contentsHistory = (TreeMapCache) (session.getAttribute("contentsHistory"));
                                
                                //
                                //  look for new default value tn="text/html", "image/svg" or "text/xml"
                                //
                                if (request.getParameterValues("tn") != null) {
                                    String urlval = request.getParameterValues("tn")[0];
                                    session.setAttribute("typeNode", urlval);
                                } else {
                                    // session.setAttribute("typeNode", SERVER_DEF_TYPE);  reset to default !
                                }
                                
                                
                                
                                //
                                //  Perform database update action
                                //
                                
                                String actionMsg = "";
                                
                                String[] act = request.getParameterValues("act");
                                if (act != null) {
                                    actionMsg = dbAction(request,session)+"<hr/>";
                                    if(!DependentNodeClear) Node.clearAllNodes();
                                    // clear cache if update - must be optimize !!!!!!!!!!!!!!!!
                                }
                                
                                
                                String a = request.getParameterValues("a")[0];
                                
                                
                                String[] u = request.getParameterValues("u");
                                
                                
                                
                                
                                //
                                // Read eip or cip parameter
                                //
                                
                                if (request.getParameterValues("eip") != null) {
                                    String eipPar = request.getParameterValues("eip")[0];
                                    isEIP = true;
                                    eipNum = eipPar.substring(3, eipPar.indexOf("XYZ"));
                                    anchorNum = eipPar.substring(eipPar.indexOf("XYZ") + 3, eipPar.length());
                                    
                                    if (NodeServer.verboseExpanding)
                                        System.out.println(
                                                "Expand in place anchorNum=" + anchorNum + " anchorNumber=" + eipNum);
                                    
                                }
                                if (request.getParameterValues("cip") != null) {
                                    String eipPar = request.getParameterValues("cip")[0];
                                    if (NodeServer.verboseExpanding)
                                        System.out.println("Close eipPar=" + eipPar);
                                    isCIP = true;
                                    eipNum = eipPar.substring(3, eipPar.indexOf("XYZ"));
                                    anchorNum = eipPar.substring(eipPar.indexOf("XYZ") + 3, eipPar.length());
                                    
                                    if (NodeServer.verboseExpanding)
                                        System.out.println(
                                                "Close expand in place anchorNum=" + anchorNum + " anchorNumber=" + eipNum);
                                    
                                }
                                
                                // build a string representation of the node parameters
                                
                                String ul = "";
                                if (u != null) {
                                    for (int i = 0; i < u.length; i++) {
                                        if (i > 0)
                                            ul += ",";
                                        ul += u[i];
                                    }
                                }
                                
                                
                                // System.out.println("receive from url:"+ul);
                                
                                String preContent=outPre(a, ul, typeNode);
                                
                                String newContent = "";
                                
                                
                                
                                
                                if (isCIP) {
                                    // close expanded node
                                    Object oContent = contentsHistory.get(anchorNum);
                                    if (oContent == null)
                                        newContent = msgNotInHistoryCache;
                                    else
                                        newContent = // "Not yet implemented";
                                                closeInPlace((String) oContent, eipNum);
                                } else {
                                    
                                    
                                    // generate the node content
                                    if (verboseTiming)System.out.println("before query time: " + (System.currentTimeMillis() - starttime) + " msec");
                                    newContent += query(a, u, 0, request, session, response);
                                    // += Purpose: add action error message in front of page content.
                                    if (verboseTiming)System.out.println(" after query time : " + (System.currentTimeMillis() - starttime) + " msec");
                                }
                                
                                // if expand-in-place : place it into the appropriate page
                                
                                if (isEIP) {
                                    Object oContent = contentsHistory.get(anchorNum);
                                    if (oContent == null)
                                        prevContent = msgNotInHistoryCache + newContent;
                                    else
                                        prevContent = expandInPlace((String) oContent, eipNum, newContent, typeNode);
                                } else
                                    prevContent = newContent;
                                
                                boolean isContainsEIPorCIP = false;
                                // is this result contains "close or expanded in place"
                                
                                if (prevContent.indexOf(eipPrefix, 0) != -1) { // contains eip
                                    prevContent = replaceEIP(prevContent);
                                    isContainsEIPorCIP = true;
                                }
                                
                                if (prevContent.indexOf(cipPrefix, 0) != -1) { // contains eip
                                    prevContent = replaceCIP(prevContent);
                                    isContainsEIPorCIP = true;
                                }
                                
                                if (isContainsEIPorCIP) { // archive in cache
                                    contentsHistory.put("" + reqID, prevContent,0);
                                }
                                
                                
                                if (verboseTiming)System.out.println(" compute time: " + (System.currentTimeMillis() - starttime) + " msec");
                                prevContent = actionMsg + prevContent;
                                String postContent=outPost(a, ul, typeNode);
                                if (typeNode.equals("xslthosted")){
                                    XSLT(preContent+SU.replaceHrefs(prevContent)+postContent, a, out);
                                    
                                } else{
                                    out.println(preContent);
                                    out.println(SU.replaceHrefs(prevContent));  //6.1.2004 encode url parameter
                                    if (verboseTiming)System.out.println("after SU time: " + (System.currentTimeMillis() - starttime) + " msec");
                                    out.println(postContent);
                                    
                                }
                                
                                
                                /* save last node for error restart*/
                                session.setAttribute("PREV_NODE", a);
                                session.setAttribute("PREV_UL", ul);
                                session.setAttribute("PREV_TYPE_NODE", typeNode);
                                session.setAttribute("PREV_NODE_CONTENT", prevContent);
                                
                                if (verboseTiming)System.out.println("total (compute+transmit) time: "+ (System.currentTimeMillis() - starttime)+ " msec\n");
                            }catch (NullPointerException e) {
                                e.printStackTrace();
                                // display the previous node if no node has been secified
                                String preContent=outPre((String) (session.getAttribute("PREV_NODE")),(String) (session.getAttribute("PREV_UL")),(String) (session.getAttribute("PREV_TYPE_NODE")));
                                prevContent=((String) (session.getAttribute("PREV_NODE_CONTENT")));
                                String postContent=outPost((String) (session.getAttribute("PREV_NODE")),(String) (session.getAttribute("PREV_UL")),(String) (session.getAttribute("PREV_TYPE_NODE")));
                                if (typeNode.equals("xslthosted")){
                                    XSLT(preContent+SU.replaceHrefs(prevContent)+postContent,  (String) (session.getAttribute("PREV_NODE")), out);
                                } else{
                                    out.println(preContent);
                                    out.println(SU.replaceHrefs(prevContent));  //6.1.2004 encode url parameter
                                    out.println(postContent);
                                }
                                
                            }
                        } // url lazy
                  //  }// XML Publisher reporting
                } // url file
            } // else already identified
        } // else not a first connection
        System.out.println(
                userID+"-"+request.getRemoteAddr()
                + ", "+(System.currentTimeMillis() - starttime)+ "ms, "
                + new java.util.Date(starttime));
        if (verboseTiming) System.out.println(TreeMapCache.getGlobalStatistic());
    }
    
    void XSLT(String xml, String a, PrintWriter htmlFile){
        
        // doit être optimisé pour mettre en cache le transformateur (un par utilisateur)
        try{
            StringReader xmlFile = new StringReader(xml);
            File xsltFile = new File(serverURL+Node.getXSLTFileName(a));
            
            System.out.println(xsltFile.getAbsolutePath()+"\n"+xsltFile.getName());
            Source xmlSource =new StreamSource(xmlFile);
            Source xsltSource =new StreamSource(xsltFile);
            Result result =new StreamResult(htmlFile);
            
            
            
            // create an instance of TransformerFactory
            TransformerFactory transFact =TransformerFactory.newInstance(  );
            Timer t=new Timer("xslt factory");
            Transformer trans =  transFact.newTransformer(xsltSource);
            
            t.stop();
            
            t=new Timer("xslt transform");
            trans.transform(xmlSource,  result);
            t.stop();
            
            
            
        }catch (Exception e) {
            e.printStackTrace();}
    }
    
    
    
    /************************************
     *
     *  OUT PRE
     */
    String outPre(String a, String ul,  String typeNode) {
        StringBuffer out=new StringBuffer("");
        if (typeNode.equals("html")) {
            out.append("<html>");
            out.append("<head>");
            out.append("<LINK rel=stylesheet href=\"lazy-basic.css\" type=\"text/css\">");
            out.append("<title>Node: " + a + "[" + ul + "]" + "</title>");
            out.append("</head>");
            out.append("<body>");
        }
        
        if (typeNode.equals("xml")) {
            // out.println("<?xml version='1.0'  encoding='ISO-8859-1' ?>");
            // out.println("<?xml version='1.0'  encoding='UTF-8' ?>");
            out.append("<?xml version='1.0' ?>"); // utf-16  is the default for explorer
            
            out.append(
                    "<?xml-stylesheet type='text/xsl' href='" + fileURL +"xsl/"+ Node.getXSLFileName(a)+"'?>");
            out.append("<lazy>");
            out.append("<lazycss>" + fileURL +"css/"+ Node.getCSSFileName(a)+ "</lazycss>");
            out.append("<lazybody>" + fileURL +"bckgnd/"+ Node.getBCKGNDFileName(a)+ "</lazybody>");
            //out.println("<title>" + a + "[" + ul + "]" + "</title>");
            out.append("<title>" + a +  "</title>");
        }
        if (typeNode.equals("xslthosted")) {
            // out.println("<?xml version='1.0'  encoding='ISO-8859-1' ?>");
            // out.println("<?xml version='1.0'  encoding='UTF-8' ?>");
            out.append("<?xml version='1.0' ?>"); // utf-16  is the default for explorer
            
            //out.append("<?xml-stylesheet type='text/xsl' href='" + fileURL +"xsl/"+ Node.getXSLFileName(a)+"'?>");
            out.append("<lazy>");
            out.append("<lazycss>" + fileURL +"css/"+ Node.getCSSFileName(a)+ "</lazycss>");
            out.append("<lazybody>" + fileURL +"bckgnd/"+ Node.getBCKGNDFileName(a)+ "</lazybody>");
            //out.println("<title>" + a + "[" + ul + "]" + "</title>");
            out.append("<title>" + a +  "</title>");
        }
        
        if (typeNode.equals("purexml")) {
            // out.println("<?xml version='1.0'  encoding='ISO-8859-1' ?>");
            out.append("<?xml version='1.0'  encoding='UTF-8' ?>");
        }
        if (typeNode.equals("purehtml")) {
        }
        if (typeNode.equals("svg")) {
        }
        return out.toString();
    }
    
    /************************************
     *
     *  OUT POST
     */
    String outPost(String a, String ul,  String typeNode) {
        StringBuffer out=new StringBuffer("");
        if (typeNode.equals("html")) {
            out.append(
                    "<p>&nbsp;</p><font size=-1><i>node "
                    + a
                    + "["
                    + ul
                    + "]"
                    + "  -- Lazy node HTML server v"
                    + nsVersion
                    + " ("
                    + user
                    + ")</i></font>");
            out.append("</body>");
            out.append("</html>");
        }
        
        if (typeNode.equals("xml")) {
            out.append("</lazy>");
        }
        if (typeNode.equals("xslthosted")) {
            out.append("</lazy>");
        }
        
        if (typeNode.equals("purexml")) {
        }
        if (typeNode.equals("purehtml")) {
        }
        if (typeNode.equals("svg")) {
        }
        return out.toString();
        
    }
    
    /************************************
     *
     *  CONVERT PARAMS
     */
    public static String[] convertParams(String[] p) { // is used to convert params from IE5
        if (p == null)
            return null;
        if (verboseCharCoding) System.out.println("nb param:"+p.length);
        String[] okp = new String[p.length];
        byte[] bw;
        for (int i = 0; i < p.length; i++) {
            if (verboseCharCoding) System.out.print("convertParams / param:"+p[i]+"->");
            okp[i]=URLUTF8Encoder.unescape(p[i]);
            if (verboseCharCoding) System.out.println(okp[i]);
        }
        return okp;
        
    }
    
    /************************************
     *
     *  BYTE PRINT
     */
    public static String byteprint(String w) { // is used to see bytes of a string
        String s = "->";
        try {
            byte[] bw = w.getBytes();
            for (int i = 0; i < java.lang.reflect.Array.getLength(bw); i++) {
                s += String.valueOf((int) (bw[i])) + ",";
            }
        } catch (Exception e) {
            System.err.println("Conversion error - byteprint");
        }
        return s;
    }
    
    /************************************
     *
     *  QUERY
     */
    public String query(
            String node,
            String[] params,
            int level,
            HttpServletRequest request,
            HttpSession session,
            HttpServletResponse response) {
        params = convertParams(params);
        SU.replaceQueryParameters(session,params);
        // System.out.println("**query: "+node);
        
        String userId=(String)session.getAttribute("USER");
        String GrpId=(String)session.getAttribute("GRP");
        if (!SecureLogin.checkAccess(node, GrpId, userId, "LAZY",  session)) {
            // no privileges on DB
            System.out.println("*** access ERROR ***:"+node+"/"+GrpId+"/"+userId);
            return "<H1>NO PRIVILEGES ON NODE (" + node+ ")</H1>";
        }
        
        
        if (node.startsWith("__")) {  // invoke external methods
            String className=Node.getProjectName(node.substring(2));
            String methodName=Node.getNodeName(node.substring(2));
            return expandIncludes(callExternalService(className,methodName,params), request,session, response); // call and expand
        }
        if (node.equals("NODE.getDynamicInfo")) {return Node.getDynamicInfo(params);} // get dynamic info on node
        if (node.equals("SECURE.grpid_modify")) {modifyGRPID(request,session);} // just modify the GRPID
        if (node.equals("SECURE.langid_modify")) {modifyLANGID(request,session); return "";} // just modify the LANGID (must be include)
        if (node.equals("SECURE.lazy_admin_clearAllNodes")) {Node.clearAllNodes();} // clear node cache
        if (node.equals("SECURE.ResetNodesDependencies")) {ResetCache.ResetNodesDependencies();return "ok reset dep";}  // reset node ...
        if (node.equals("SECURE.lazy_admin_clearDependentNodes")) {Node.clearDependentNodes(params[0]);return "";} // clear dependent node on a table
        if (node.equals("SECURE.dynamicNode")) {return dynamicNode(params,request,session, response);} // appel dynamique
        if (node.equals("SECURE.buildNode")) {return buildNode(params,request,session, response);} // appel dynamique
        if (node.equals("SECURE.encryptpwd")) {return Secure.encryptpwd(params[0]);} // encrypte un mot de passe
        if (node.equals("SECURE.testDB")) {return DBServices.testConnection(params[0]);} // test si la connection est ok
        if (node.equals("SECURE.restartDB")) {DBServices.init(url,user,Secure.decryptpwd(pwd),driver);
        Node.clearAllNodes(); return "<h3>restart DB OK</h3>";} // réinitialise les BD
        if (node.equals("NODE.lazy_compileProject")) {compileProject(request,session,params);} // clear node cache
        if (node.equals("UTIL.execUpload")) { Upload.askForFile(request,response,params);  }
        return queryDB(node, params, level, request, session, response);
        
    }
    
    public String buildNode(
            String[] params,
            HttpServletRequest request,
            HttpSession session,
            HttpServletResponse response) {
        int level=0;
        String nodebuilder=params[0]+"."+params[1];  // the name of the node builder
        String projectid=params[2]      ;  // the newnode project
        String nodename=params[3]           ;  // the newnode name
        String[] newparams=new String[params.length-2];
        for (int i=0;i<newparams.length;i++){  // shift parameters
            newparams[i]=params[i+2];
        }
        String nodetxt = queryDB(nodebuilder, newparams, level, request, session, response);  // build node
        String deleteRequest = "delete from nodes where "+
                "projectid='"+projectid+"'"+
                "and name='"+nodename+"'";
        QueryResult Q = DBServices.execSQL(deleteRequest, false);  // on DICTLAZY DB
        
        String insertRequest = "insert into nodes (projectid,name,plaintxt,status) values ("+
                "'"+projectid+"'"+
                ",'"+nodename+"'"+
                ",'"+SU.doubleQuotes(nodetxt)+"'"+
                ",'INVALID')";
        Q = DBServices.execSQL(insertRequest, false);  // on DICTLAZY DB insert into nodes
        
        if (Q.valid) { // insert ok
            String[] compileparams=new String[2];
            compileparams[0]=projectid;
            compileparams[1]=nodename;
            compileProject(request,session,compileparams);
            try {
                String sqlRequest="select status, error from nodes where "+
                        "projectid='"+projectid+"'"+
                        "and name='"+nodename+"'";
                Q = DBServices.execSQL(sqlRequest, true);
                if (Q.valid) {
                    Q.result.next();  // must be one row
                    String status=Q.result.getString(1);
                    if (status.equals("VALID")) {return "";} else { // error during compilation
                        return "<hr/><h3>Compiler error</h3>"
                                + "<p>node :<b>"+nodetxt+"</b> execution</p>"
                                + "<p>error :<b>"+Q.result.getString(2)+"</b> execution</p>";
                    }
                } else  return "Build:  error in select STATUS";
            } // try
            catch (SQLException e) { return "clearDependentNodes:  SQLError: " + e.getMessage();}
            
        } else { // sql error
            return "<hr/><h3>Database access error</h3>"
                    + "<p>during:<b>insert</b> execution</p>"
                    + "<p>SQL text <b>"
                    + Q.sql
                    + "</b></p>"
                    + "<p>SQLError: "
                    + Q.msg
                    + "</p>";
        }
        
    }
    
    public String dynamicNode(
            String[] params,
            HttpServletRequest request,
            HttpSession session,
            HttpServletResponse response) {
        int level=0;
        String node=params[0]+"."+params[1];  // build the name
        String[] newparams=new String[params.length-2];
        for (int i=0;i<newparams.length;i++){  // shift parameters
            newparams[i]=params[i+2];
        }
        return queryDB(node, newparams, level, request, session, response);
    }
    
    
    
    public static String callExternalService(String className, String method,Object params) {
        if(verboseExternal)System.out.println("callExternalService: "+className+"."+method);
        String result = "error in callExternalService: "+className+"."+method;
        try {
            if ("RPC".equals(className)) className="isi.jg.ns."+className;
            Class c = Class.forName(className);
            Class[] parameterTypes = new Class[] {Object.class};
            Method callMethod;
            Object obj = c.newInstance();
            Object[] arguments = new Object[] {params};
            callMethod = c.getMethod(method, parameterTypes);
            result = (String) callMethod.invoke(obj, arguments);
        } catch (NoSuchMethodException e) {
            System.out.println(e);e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println(e);e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println(e);e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e);e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println(e);e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);e.printStackTrace();
        }
        
        return result;
    }
    
    
    void modifyGRPID(HttpServletRequest request,HttpSession session){
        String[] u = request.getParameterValues("u");
        session.setAttribute("GRP", u[0]);
    }
    void modifyLANGID(HttpServletRequest request,HttpSession session){
        String[] u = request.getParameterValues("u");
        //System.out.println("modify language:"+u[0]);
        session.setAttribute("LANG", u[0]);
    }
    
    void compileProject(HttpServletRequest request,HttpSession session,String u[]){
        String projectid=u[0];
        String name=u[1];
        String sqlRequest="select plaintxt from nodes where "+
                "projectid='"+projectid+"' and name like '"+name+"'" ;
        QueryResult Q = DBServices.execSQL(sqlRequest, true);
        if (Q.valid) {
            try {
                int nbRes=0;
                Compiler.ns_init(driver,url,user,Secure.decryptpwd(pwd));
                while (Q.result.next()) {
                    nbRes++;
                    String k="define project "+projectid+" "+Q.result.getString(1)+" end";
                    PrintWriter xout = new PrintWriter(new FileOutputStream("out.sql"));
                    Compiler a = new Compiler(new StringReader(k), xout);
                    a.startrule();
                }
                System.out.print("compile node from BD: "+nbRes);
                Node.clearNode(projectid,name);
                Q.result.close();
                Compiler.closeDB();
            } // try
            catch (Exception e) {
                System.out.println("ns: during lazy_compileProject() SQLError: " + e.getMessage());
            }
        }else System.out.println("ns: during lazy_compileProject() SQLError: " + Q.msg);
        
    }
    
    /************************************
     *
     *  QUERY DB
     */
    String queryDB(
            String node,
            String[] params,
            int level,
            HttpServletRequest request,
            HttpSession session,
            HttpServletResponse response) {
        ResultSet resDef;
        StringBuffer resb = new StringBuffer();
        
        if (level > 10)
            return ""; // Just to avoid infinite inclusions
        
        Node N = Node.getNodeDefinition(node);
        
        if (!N.valid) {
            return N.msg;
        } // error in loading definition
        
        StringBuffer content = N.Actualize(params,session);
        // get a instance of this node with this parameters
        
        /* Expand in place the included nodes */
        
        if (N.NOinclude)
            return (new String(content)); // THIS NODE cannot generate include ...
        else
            return expandIncludes(new String(content), request, session, response);
    }
    
    /**
     * Find all the inclusion marks and replace them with the node content to be included<p>
     * Inclusion marks are : &lt;&lt;??include a=<b>NODE_NAME</b>&amp;amp;u=<b>NODE_PARAM_1</b>&amp;amp;u=<b>NODE_PARAM_2</b>&amp;amp;u=...??&gt;&gt;
     * <p>
     *
     * 2003-04-19: Change the replacement order to support includes within include parameters
     */
    String expandIncludes(String s, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        boolean trace = false;
        int ix = 0;
        int iscan = 0;
        StringBuilder resb = new StringBuilder();
        if (trace) System.out.println("starting expand of :"+s);
        
        while ((ix = s.indexOf(includePrefix, iscan)) > -1) {
            
            /* Find the end of the inclusion, which may contain includes in parameters */
            
            int expandEnd = s.indexOf(includeSuffix, ix);
            int iy = s.indexOf(includePrefix, ix+1);
            while (iy > -1 && iy < expandEnd) {// found one more inclusion
                if (trace) System.out.println("INC/INC --- include in PARAMS");
                expandEnd = s.indexOf(includeSuffix, expandEnd+1); // must find
                iy = s.indexOf(includePrefix, iy+1);
            }
            
            String ss = s.substring(ix, expandEnd+includeSuffix.length());
            if (trace) System.out.println("* Expand --- ss= "+ss);
            
                        /* Now do all the expansions in ss, starting with the innermost ones
                         */
            int xend = expandEnd;
            while ((xend = ss.indexOf(includeSuffix)) > -1) {
                
                // Find an include without includes in its parameters
                int xstart = ss.lastIndexOf(includePrefix, xend);
                
                int nodeNameStart = xstart + includePrefix.length(); // !! length of include markup
                
                int nodeNameEnd = ss.indexOf("&amp;u=", xstart); // xml jg
                
                if (nodeNameEnd == -1 || nodeNameEnd > xend) nodeNameEnd = xend; // gf 0 parameter  //xend for expandEnd
                String nodeName = ss.substring(nodeNameStart, nodeNameEnd);
                
                String[] params = {};
                
                if (nodeNameEnd < xend) {
                    String upar = ss.substring(nodeNameEnd, xend); // xml jg
                    upar = SU.getParameters(upar);
                    StringBuilder convert=new StringBuilder();
                    int ixx = 0, ixxlast=0;
                    // Replace every '&amp' by '&'
                    while ((ixx = upar.indexOf("&amp;", ixx)) > -1) {
                        ixx = ixx + 1; // keep &
                        convert.append(upar.substring(ixxlast, ixx));
                        ixxlast=ixx+4;
                    }
                    convert.append(upar.substring(ixxlast, upar.length()));
                    upar=convert.toString();
                    
                    if (trace) System.out.print("DBG-INC upar='"+upar+"'");
                    if (upar.equals("u=")) {
                        params = new String[] {""};
                        // upar += "0";
                    } else {
                        java.util.Hashtable hparams = HttpUtils.parseQueryString(upar);
                        params = (String[]) (hparams.get("u"));
                    }
                    if (trace) for (int i=0; i<params.length; i++) System.out.print("["+params[i]+"]");
                }
                if (NodeServer.verboseExpanding)
                    System.out.println(
                            "expanding " + nodeName + " " + ss.substring(nodeNameEnd, xend));
                
                String expanded = "";
                // try {
                expanded = query(nodeName, params, 1, request, session, response);
                if (trace) if (expanded.length() < 30) System.out.println("DBG-INC expanded='"+expanded+"'");
                // }
                // catch (LazySecurityException le) { /* do nothing -- unauthorized nodes are simply not included */ }
                
                ss = ss.substring(0, xstart) + expanded + ss.substring(xend+includeSuffix.length());
            } // while
            
            resb.append(s.substring(iscan, ix));
            // resb.append(expanded);
            resb.append(ss);
            iscan = expandEnd + includeSuffix.length();
            
        } // while
        resb.append(s.substring(iscan, s.length()));
        return new String(resb);
    }
    
    
    
    
    /************************************
     *
     *  EXPAND IN PLACE
     */
    String expandInPlace(String s, String eipNum, String exp, String typeNode) {
        int ix = 0;
        
        int ifocus = s.indexOf("<focus>expanded</focus>");
        if (ifocus != -1) { // exist so delete old anchor
            s = s.substring(0, ifocus) + s.substring(ifocus + 23, s.length());
        }
        
        String cipText = ""; // xml jg
        String focusText = ""; // xml jg
        if (typeNode.equals("html")) {
            cipText = cipTextHTML; // xml jg
            focusText = focusTextHTML;
        }
        if (typeNode.equals("xml")||typeNode.equals("xslthosted")) {
            cipText = cipTextXML; // xml jg
            focusText = focusTextXML;
        }
        
        if ((ix = s.indexOf(eipPrefix + eipNum, 0)) > -1) {
            int beginAnchor = s.indexOf(">", ix) + 1;
            int endAnchor = s.indexOf("</a>", beginAnchor);
            
            return s.substring(0, ix)
            + focusText
                    + cipPrefix
                    + s.substring(ix + (eipPrefix + eipNum).length(), beginAnchor)
                    + expandedBegin
                    + reqID
                    + "-->"
                    + cipText
                    + "<!--"
                    + s.substring(beginAnchor, endAnchor)
                    + "-->"
                    + "</a>"
                    + exp
                    + expandedEnd
                    + reqID
                    + "-->"
                    + s.substring(endAnchor + "</a>".length());
        } else
            return s;
    }
    
    /************************************
     *
     *  CLOSE IN PLACE
     */
    String closeInPlace(String s, String eipNum) {
        int a = s.indexOf(cipPrefix + eipNum, 0);
        String res = "--ERROR--NOTHING TO CLOSE--";
        if (a > -1) {
            res = s.substring(0, a) + eipPrefix;
            int beginExpBegin = s.indexOf(expandedBegin, a); // <!--#EXPANDED-:
            res += s.substring(a + cipPrefix.length(), beginExpBegin);
            int beginReqNum = beginExpBegin + expandedBegin.length();
            int endReqNum = s.indexOf("-->", beginReqNum);
            String reqNum = s.substring(beginReqNum, endReqNum);
            int beginAnchor = s.indexOf("<!--", endReqNum) + "<!--".length();
            int endAnchor = s.indexOf("--></a>", beginAnchor);
            res += s.substring(beginAnchor, endAnchor) + "</a>";
            int beginEndExpand = s.indexOf(expandedEnd + reqNum, endAnchor);
            int endEndExpand = s.indexOf("-->", beginEndExpand) + "-->".length();
            res += s.substring(endEndExpand);
        }
        return res;
    }
    
    /************************************
     *
     *  REPLACE EIP  // optimise version JG
     */
    String replaceEIP(String s) {
        int ix = 0, ixf = 0, ixx;
        int eipnum = 0;
        StringBuffer bs = new StringBuffer("");
        
        if (s.indexOf(eipPrefix, ix) != -1) { // subsitute
            while ((ix = s.indexOf(eipPrefix, ix)) > -1) {
                bs.append(s.substring(ixf, ix) + eipPrefix + eipnum + "XYZ" + reqID);
                ixf = s.indexOf("&", ix);
                //s = s.substring(0, ix) + eipPrefix + eipnum + "XYZ" + reqID + s.substring(ixf);  // fron not optimise version
                eipnum++;
                ix = ix + 10;
            } // while
            bs.append(s.substring(ixf));
            return bs.toString();
        } else
            return s;
    }
    
    /************************************
     *
     *  REPLACE CIP
     */
    String replaceCIP(String s) {
        int ix = 0, ixf = 0, ixx;
        int eipnum = 0;
        StringBuffer bs = new StringBuffer("");
        
        if (s.indexOf(cipPrefix, ix) != -1) { // subsitute
            while ((ix = s.indexOf(cipPrefix, ix)) > -1) {
                bs.append(s.substring(ixf, ix) + cipPrefix + eipnum + "XYZ" + reqID);
                ixf = s.indexOf("&", ix);
                //s = s.substring(0, ix) + cipPrefix + eipnum + "XYZ" + reqID + s.substring(ixf);  // fron not optimise version
                eipnum++;
                ix = ix + 10;
            } // while
            bs.append(s.substring(ixf));
            return bs.toString();
        } else
            return s;
    }
    
   /*
    * Database actions
    *
    */
    String dbAction(HttpServletRequest request, HttpSession session) {
        String[] act = request.getParameterValues("act");act=Secure.decryptParam(act);
        String res = "";
        
        if (act != null) {
            String[] tbl = request.getParameterValues("tbl");tbl=Secure.decryptParam(tbl);
            String[] an = request.getParameterValues("an");an=Secure.decryptParam(an);
            String[] av = convertParams(request.getParameterValues("av"));
            String[] hn = request.getParameterValues("hn");hn=Secure.decryptParam(hn);
            String[] hv = convertParams(request.getParameterValues("hv"));hv=Secure.decryptParam(hv);
            String[] kn = request.getParameterValues("kn");kn=Secure.decryptParam(kn);
            String[] kv = convertParams(request.getParameterValues("kv")); kv=Secure.decryptParam(kv);
            String[] conid = request.getParameterValues("con"); conid=Secure.decryptParam(conid);// db to update
            String[] u_enc = request.getParameterValues("u_enc");// CDATA
            
            String encodeType="XML";
            if (u_enc!=null)  encodeType=u_enc[0];   // XML is the standard but in CDATA field no encoding is necessary
            
            String userId=(String)session.getAttribute("USER");
            String GrpId=(String)session.getAttribute("GRP");
            
            if (!SecureLogin.checkAccess(tbl[0].toUpperCase(), GrpId, userId,"TABLE",  session)) {
                // no privileges on DB
                System.out.println("*** access ERROR ***:"+tbl[0]+"/"+GrpId+"/"+userId);
                return "<H1>NO PRIVILEGES ON TABLE (" + tbl[0]+ ")</H1>";
            }
            
            if (an!=null){applyFunction(an,av);} // apply function on parameter value
            
            if (verboseModify) System.out.println("hn");
            encodeAndStore(session, encodeType, hn,hv);
            if (verboseModify) System.out.println("kn=");
            encodeAndStore(session, encodeType, kn,kv);
            if (verboseModify) System.out.println("an=");
            encodeAndStore(session, encodeType, an,av);
            
            
            if (act[0].equals("new"))
                res = insertIntoDB( encodeType, tbl, an, av, hn, hv, conid[0]);
            if (act[0].equals("upd"))
                res = updateDB(encodeType, tbl, an, av, hn, hv, kn, kv, conid[0]);
            if (act[0].equals("del"))
                res = deletefromDB(encodeType, tbl, kn, kv, conid[0]);
            
            if (DependentNodeClear) Node.clearDependentNodes(tbl[0]);
        }
        return res;
        
    }
    
    static void applyFunction(String[] an, String[] av){ // an is not null
        int ix=0;
        for (int i = 0; i < an.length; i++){
            ix = 0;
            if ((ix = an[i].indexOf("|", ix)) > 0){ // there is a function
                String fName=an[i].substring(0, ix);
                String fAttr=an[i].substring(ix+1, an[i].length());
                an[i]=fAttr;
                if (fName.equals("encoded")) {av[i]=LazyFunction.encoded(av[i]);} else an[i]+=" ERROR on function name: "+fName;
            }
        }
    }
    
    static void encodeAndStore(HttpSession session, String encodeType, String[] xn, String[] xv ){
        if (xn==null) return;
        if (verboseModify) System.out.println("xn="+xn.length+"xv="+xv.length);
        if (xn.length != xv.length){System.out.println("ERROR - wrong parameters"); return;}
        for (int i = 0; i < xn.length; i++){
            xv[i]=SU.cleanValue(encodeType, xv[i]);
            session.setAttribute((String)("!"+xn[i]),xv[i]);  // could be referenced in noded [[PARAM_name]]
            if (verboseCharCoding) System.out.println("add : PARAM !"+xn[i]+"->"+xv[i]);
        }
    }
    /************************************
     *
     *  INSERT INTO DB
     */
    String insertIntoDB(
            String encodeType,
            String[] intoTable,
            String[] attrNames,
            String[] attrValues,
            String[] attrNamesHidden,
            String[] attrValuesHidden,
            String conid) {
        
        String insertRequest = "";
        
        if (intoTable.length == 0
                || attrNames.length == 0
                || attrValues.length != attrNames.length)
            return "<p>ERROR - wrong parameters in insertIntoDB</p>";
        
        if (verboseModify) System.out.println("Preparing INSERT...");
        
        insertRequest = "insert into " + intoTable[0] + " (" + attrNames[0];
        for (int i = 1; i < attrNames.length; i++)
            insertRequest += "," + attrNames[i];
        if (attrNamesHidden!=null) {
            for (int i = 0; i < attrNamesHidden.length; i++)
                insertRequest += "," + attrNamesHidden[i];
        }
        insertRequest += ") VALUES ("+UnicodeCollationSQL92+"'" + attrValues[0] + "'";
        for (int i = 1; i < attrValues.length; i++)
            insertRequest += ","+UnicodeCollationSQL92+"'" + attrValues[i] + "'";
        if (attrValuesHidden!=null) {
            for (int i = 0; i < attrValuesHidden.length; i++)
                insertRequest += ","+UnicodeCollationSQL92+"'" + attrValuesHidden[i] + "'";
        }
        insertRequest += ")";
        
        if (verboseModify) System.out.println("on "+conid+": "+insertRequest);
        
        QueryResult Q = DBServices.execSQLonDB(insertRequest,conid, false);
        
        if (verboseModify) System.out.println("after INSERT...");
        
        
        if (Q.valid) {
            return "<b>" + Q.nbUpdated + " rows inserted</b>";
        } else { // sql error
            return "<hr/><h3>Database access error</h3>"
                    + "<p>during:<b>insert</b> execution</p>"
                    + "<p>SQL text <b>"
                    + Q.sql
                    + "</b></p>"
                    + "<p>SQLError: "
                    + Q.msg
                    + "</p>";
        }
        
    }
    
    /************************************
     *
     *  UPDATE DB
     */
    String updateDB(
            String encodeType,
            String[] updateTable,
            String[] attrNames,
            String[] attrValues,
            String[] attrNamesHidden,
            String[] attrValuesHidden,
            String[] keyNames,
            String[] keyValues,
            String conid) {
        
        String updateRequest = "";
        
      /*  accept attrName==null !!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (updateTable.length == 0
         || attrNames.length == 0
         || attrValues.length != attrNames.length
         || keyNames.length == 0
         || keyValues.length != keyNames.length)
         return "<p>ERROR - wrong parameters in updateDB</p>";
       */
        
        if (verboseModify) System.out.println("Preparing UPDATE...");
        
        updateRequest =
                "update  "
                + updateTable[0]
                + " set ";
        if (attrNames!=null) { // exist visible attribute
            updateRequest+=attrNames[0]
                    + "="+UnicodeCollationSQL92+"'"
                    + attrValues[0]
                    + "'";
            if (verboseModify)
                System.out.println("UPDATE:" + updateRequest);
            
            for (int i = 1; i < attrNames.length; i++){
                if (verboseModify)
                    System.out.println("UPDATE:" + updateRequest);
                updateRequest += ","
                        + attrNames[i]
                        + "="+UnicodeCollationSQL92+"'"
                        + attrValues[i]
                        + "'";}
        }
        if (attrNamesHidden!=null) {
            for (int i = 0; i < attrNamesHidden.length; i++){
                if ((attrNames!=null)||i!=0) updateRequest += ",";
                updateRequest +=   attrNamesHidden[i]
                        + "="+UnicodeCollationSQL92+"'"
                        + attrValuesHidden[i]
                        + "'";
            }
        }
        
        
        updateRequest += " where "
                + keyNames[0]
                + "="+UnicodeCollationSQL92+"'"
                + keyValues[0]
                + "'";
        
        for (int i = 1; i < keyNames.length; i++)
            updateRequest += " and "
                    + keyNames[i]
                    + "="+UnicodeCollationSQL92+"'"
                    + keyValues[i]
                    + "'";
        
        if (verboseModify) System.out.println(updateRequest);
        
        QueryResult Q = DBServices.execSQLonDB(updateRequest,conid, false);
        
        if (Q.valid) {
            return "<b>" + Q.nbUpdated + " rows updated</b>";
        } else { // sql error
            return "<hr/><h3>Database access error</h3>"
                    + "<p>during:<b>update</b> execution</p>"
                    + "<p>SQL text <b>"
                    + Q.sql
                    + "</b></p>"
                    + "<p>SQLError: "
                    + Q.msg
                    + "</p>";
        }
        
    }
    
    /************************************
     *
     *  DELETE FROM DB
     */
    String deletefromDB(String encodeType, String[] delTable, String[] keyNames, String[] keyValues,
            String conid) {
        
        String deleteRequest = "";
        
        if (delTable.length == 0
                || keyNames.length == 0
                || keyValues.length != keyNames.length)
            return "<p>ERROR - wrong parameters in deleteFromDB</p>";
        
        if (verboseModify) System.out.println("Preparing DELETE...");
        
        deleteRequest =
                "delete from  "
                + delTable[0]
                + " where "
                + keyNames[0]
                + "="+UnicodeCollationSQL92+"'"
                + keyValues[0]
                + "'";
        
        for (int i = 1; i < keyNames.length; i++)
            deleteRequest += " and "
                    + keyNames[i]
                    + "="+UnicodeCollationSQL92+"'"
                    + keyValues[i]
                    + "'";
        
        if (verboseModify) System.out.println(deleteRequest);
        
        QueryResult Q = DBServices.execSQLonDB(deleteRequest,conid, false);
        
        if (Q.valid) {
            return "<b>" + Q.nbUpdated + " rows deleted</b>";
        } else { // sql error
            return "<hr/><h3>Database access error</h3>"
                    + "<p>during:<b>delete</b> execution</p>"
                    + "<p>SQL text <b>"
                    + Q.sql
                    + "</b></p>"
                    + "<p>SQLError: "
                    + Q.msg
                    + "</p>";
        }
        
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        //redirect upload request (type is multipart/form-data)
        String contentType = request.getContentType();
        if(contentType != null && contentType.startsWith("multipart/form-data")) {
            Upload.doit(request,response);
        } else {
            doGet(request, response);  // command LAZY
        }
    }
    
    
    
}

