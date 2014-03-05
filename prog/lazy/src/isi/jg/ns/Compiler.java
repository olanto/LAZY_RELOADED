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
/*  TODO List
 
     Add an attribute type
 
     Connect to the database and check the collection and attribute names
 
 
    node style : node nodename[parameterlist] [nodeStyle, nodeStyleWhenIncluded]
    item style : items [itemsStyle, itemsStyleWhenIncluded]
 
    add node compiler version to node definition tuple in 'nodes'
 */



/* new 1.10.2001
 
 
 *   input_field =   "select"    "(" simple_expr "," simple_expr [ "," simplexpr ]
 *                                | include ... ")"
 new  2.11.2001
 
 
 * set_attribute = "set" "parameter" "=" (input_field | simple_expr)   // do display action jg
 * on_action = "on" String-expr "do" "display" "node"
 new  5.11.2001 jg
 
        selected  by an order by clause could be optional
new  5.11.2001 jg
 
        test "define"
        autorize :- in identifier
new 5.02.2002 jg
 
        node xxx ....
           project "id ..."
 
-delete all node from project ...
-assign VERS30 to old project
-node are unique in all project ... (node is a key !!)
 
new 24.06.2003 jg
 
- correction des group by -> simple_expression() au lieu de identifier()
 
 
 new 6.1.2004
 *
 -insertion des includes dans les href, include et active href
 -ajout du desc dans le order by
 *
 
 
 */


import java.io.*;
import java.util.*;
import java.sql.*;


public class Compiler {
    
    boolean debug = false;
    
    
    static final String compilerVersion = "4.0b";
    
    static String url  = "jdbc:oracle:thin:@cuisund:1521:cui";
    static String user = "lazy";
    static String pwd  = "lazy";
    static String driver = "oracle.jdbc.driver.OracleDriver";
    static Statement stmt;
    static Connection con;
    static boolean directToDB;
    
    static String concatPre = "''''";
    static String concatSep = ", "; // ||
    static String concatPost = "";
    
    static String concatPreAmp = "''''";
    static String concatSepAmp = "&";
    static String concatPostAmp = "";
    
    PrintWriter out;
    
    String servlet = "ns";
    
    boolean fromFile;
    
    int errInNodeDef = 0;
    
    StreamTokenizer sym;
    BufferedReader txt;   // to find node text
    int currenttxtline=0;
    
    String laststring;
    
    int TT_WORD = StreamTokenizer.TT_WORD;
    int TT_NUMBER = StreamTokenizer.TT_NUMBER;
    
    boolean immediate, copyon;
    
    boolean todb = false;
    
    String  nodeid, imm_node, projectid;
    
    StringBuffer viewtext, copybuf;
    
    String displaybuf, prebuf, postbuf, selectbuf, orderbuf, frombuf, plaintxtbuf, statusbuf, errorbuf, groupbuf;
    
    /* to handle fields or parameters composed of a single attribute
       if the same attr. appears twice it must be aliased
     */
    boolean attrOnly;
    int nbAttrOnly = 0;
    Set attrOnlySet;
    String theAttr;
    int cachesize=-1;
    
    String itemsType;
    boolean itemsFound;
    
    String[] paranames = new String[100];
    int nbparams = 0;
    
    int attrCount = 0;
    int preAttrCount, dispAttrCount, postAttrCount; // number of database attributes found in pre, items, and post parts
    
    
   /*
     look for s in the parameter list, return param index if found and nbparams if not
    */
    int paraIndex(String s) {
        int x = 0;
        while (x < nbparams && ! s.equals(paranames[x])) x++;
        return x;
    }
    
    public    Compiler(Reader rdr, Reader plaintxt,PrintWriter p) {
        
        txt=new BufferedReader(plaintxt);
        fromFile=true;
        init(rdr,p);
    }
    
    public    Compiler(Reader rdr,PrintWriter p) {
        
        fromFile=false;
        init(rdr,p);
    }
    
    void init(Reader rdr,PrintWriter p) {
        
        
        out = p;
        
        sym = new StreamTokenizer(rdr);
        
        sym.wordChars('_','_');
        sym.wordChars('0','9');
        sym.ordinaryChars('/','/');
        sym.ordinaryChars('-','-');
        sym.slashSlashComments(true);
        sym.slashStarComments(true);
        
        
        next();
        
    }
    
    public static void global_init(){
        
        ResourceBundle rb = ResourceBundle.getBundle("LazyCompiler");
        String stodb = rb.getString("todatabase");
        directToDB = stodb != null ? stodb.equals("on") : false;
        
        if (directToDB) {
            
            url = rb.getString("database.url");
            user = rb.getString("database.user");
            pwd = rb.getString("database.password");
            driver = rb.getString("database.driver");
            
            // adapt to different concatenation syntaxes
            String dbc = rb.getString("database.concatenation");
            if (dbc.equals("amp")) {
                concatPre = concatPreAmp;
                concatSep = concatSepAmp = "&";
                concatPost = concatPostAmp = "";
            }
            
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pwd);
                stmt = con.createStatement();
                System.out.println("Lazy node compiler "+compilerVersion+"  (-->"+url+"/"+user+")");
            }
            catch (Exception e) {
                System.out.println("*** Unable to connect to database "+url);
                System.out.println(e.toString());
                return;
            }
        }
        
    }
    
    static void ns_init(String wdriver, String wurl,String wuser,String wpwd ){
        
        directToDB = true;
        
        if (directToDB) {
            
            url = wurl;
            user = wuser;
            pwd = wpwd;
            driver = wdriver;
            
            
            
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pwd);
                stmt = con.createStatement();
                System.out.println("Lazy node compiler "+compilerVersion+"  (-->"+url+"/"+user+")");
            }
            catch (Exception e) {
                System.out.println("*** Unable to connect to database "+url);
                System.out.println(e.toString());
                return;
            }
        }
        
    }
    
    
    String getTextNode(int first, int last){ // return text from line first to line last
        // actual line is currenttxtline
        try {
            int skip=first-currenttxtline-1;
            for (int i=0;i<skip;i++) txt.readLine();// skip line
            int get=last-first+1;
            String s="";
            for (int i=0;i<get;i++) s+=txt.readLine()+"\n";
            currenttxtline=last;
            return s;
        }
        catch (Exception e) {
            System.out.println("*** Unable to read lines from plaintext ");
            System.out.println(e.toString());
            return "error in file! (two nodes on the same line .... ?" ;}
        
    }
    
    static void closeDB() {
        try {
            if (con != null) con.close();
            // System.out.println("DB connection closed");
        }
        catch (java.sql.SQLException e) {
            System.out.println("*** DB connection close failed");
            System.out.println(e.toString());
        }
        
    }
    
    
    
    void next() {
        try {sym.nextToken(); }
        catch (IOException e) {System.err.println("IO error");}
        
    }
    
    boolean term(String s) {
        return sym.ttype == TT_WORD && sym.sval.equals(s);
    }
    
    boolean isIdentifier() {
        if (sym.ttype == TT_WORD)
            if (sym.sval.equals("define")
            || sym.sval.equals("end")   // from GF
            || sym.sval.equals("node")
            || sym.sval.equals("pre")
            || sym.sval.equals("items")
            || sym.sval.equals("post")
            || sym.sval.equals("from")
            || sym.sval.equals("group")
            || sym.sval.equals("selected")
            || sym.sval.equals("by")
            || sym.sval.equals("desc")
            || sym.sval.equals("project")
            || sym.sval.equals("in")
            || sym.sval.equals("distinct")
            || sym.sval.equals("cachesize")
            || sym.sval.equals("limit")
            || sym.sval.equals("order")) return false;
            else return true;
        else return false;
    }
    
    
    ////////////////////////////// Parser ///////////////////////////////
    
        /*
           startrule = "define" { node_def } "end".
         */
    public void startrule() {
        if (term("define")) {
            next();
            if (term("project")) {
                next();
                projectid=sym.sval; // %save_projectid
                if (fromFile) {
                    System.out.println("clear all nodes for this project ");
                    String delRequest = "delete from nodes where projectid='"+projectid+"'";
                    // System.out.println(delRequest);
                    try {
                        int n = stmt.executeUpdate(delRequest);
                    }
                    catch (SQLException se) {
                        System.out.println("*** SQL ERR, projectid: "+projectid);
                    }
                }
                next();
            }
            else {projectid="vers30";
            System.out.println("add a project id after define !!! ");
            }
            System.out.println("project "+projectid);
            
            while (term("node")) {
                errInNodeDef = 0;
                errorbuf="";
                node_def();
            }
            if (term("end")) {next(); }
            else {
                err("'end' missing");
                invalideLastNode();
            }
            out.println("commit;");
            
        }
        else err("'define' missing");
        if (fromFile) {closeDB();};
    }
    
    /*
       node_def = "node" node_identifier parameters
                      [cachesize=number]
                      new_content
                      from_part();
     
     
     */
    
    void node_def() {
        // "node"
        int firstline=sym.lineno();
        errorbuf="";
        
        next();
        if (sym.ttype == TT_WORD) {
            nodeid = sym.sval; // %save_nodeid
            System.out.println("node "+sym.sval);
            
            next();
            parameters();
            // defaultvalue();
            
            cachesize=-1;
            if (term("cachesize")) {
                next();
                if (sym.ttype == '=') {
                    next();
                    if (sym.ttype == TT_NUMBER) {
                        cachesize=(int)sym.nval;
                        next();
                    }
                    else err("missing number after =");;
                }
                else err("missing '=' after cachesize");
            }
            
            
            attrCount = 0;
            attrOnlySet = new TreeSet();
            
            
            new_content();
            
            from_part();
            //System.out.println("after from:"+sym.sval);
            
            // System.out.print("parameters=");
            // for (int i=0; i<nbparams; i++) System.out.print(paranames[i]+" ");
            
            int lastline=sym.lineno()-1;
            if (errInNodeDef==0) statusbuf="VALID"; else statusbuf="INVALID";
            if (fromFile) plaintxtbuf=doubleQuotes(getTextNode(firstline,lastline));
            storeCompiledNode();
            
        }
        else err("<identifier> missing after 'node'");
    }
    
 /*
  
  parameters = [ "[" parameter { "," parameter  } "]" ]
  */
    void parameters() {
        nbparams = 0;
        if (sym.ttype == '[') {
            next();
            parameter();
            while (sym.ttype == ',') {
                next();
                parameter();
            }
            if (sym.ttype == ']') { next(); }
            else err("missing ']' after param list");
        }
    }
    /*
           parameter = identifier
     */
    void parameter() {
        if (sym.ttype == TT_WORD) {
            paranames[nbparams] = sym.sval;
            nbparams++;
            next();
        }
    }
    
    
    
    
/*
    new_content = field { "," field  }
 */
    void new_content() {
        itemsFound = false;
        attrCount = 0;
        startcopy();
        echo(concatPre);
        field();
        while (sym.ttype == ',') {
            next();
            field();
        }
        echo(concatPost);
        stopcopy();
        if (itemsFound) {
            postbuf = copybuf.toString(); postAttrCount = attrCount;
        } else {
            prebuf = copybuf.toString(); preAttrCount = attrCount;
            displaybuf = "'' ''";
            postbuf = "'' ''";
        }
    }
    
    
/*
   field =  ["expand"] "href" link '(' field_list ')'
           | "include" link
           | content
 ***       | "active" "href" active_link '(' field_list ')'
 ***       | set_attribute
 ***       | on_action
 */
    
    void field() {
        
        if (term("href") || term("expand")) {
            link();
            if (sym.ttype == '(') {
                next();
                field();
                while (sym.ttype == ',') {
                    next();
                    field();
                }
                if (sym.ttype == ')') next();
                else err("missing ')' at end of anchor fields");
            }
            else err("missing '('  after href ");
            echo(concatSep+"''</a>''");}
        else if (term("include")) link();
        else if (term("active")) {
            echo(concatSep+"''<form action=\""+servlet+"\" method=\"post\">''");
            active_link();
            if (sym.ttype == '(') {
                next();
                field();
                while (sym.ttype == ',') {
                    next();
                    field();
                }
                if (sym.ttype == ')') next();
                else err("missing ')' at end of anchor fields");
            }
            else err("missing '('  after href ");
            echo(concatSep+"''</form>''");
        }
        else if (term("set")) set_attribute();
        else if (term("on")) on_action();
        
        else content();
    }
    
/*
 * link = (  "include" | "expand" | "href" [ "in" frameident]) nodeident [ "[" simple_expression_OR_INCLUDE_list "]" ] [target] *
 *  an "href" link of the form "href nodeName[expr_1, expr_2, ...]"
 *  will produce the following HTML code in a node instance:
 *
 *    <a href="servletName?a=nodeName&u=$expr_$&u=$expr_2$&u=...">
 *
 *  an "expand href" will produce
 *
 *    <a href="servletName?eip=ZYX0000XYZ&a=nodeName&u=u=$expr_$&u=$expr_2$&u=...">
 *
 *  an "include" will produce
 *
 *    <<??include a=nodeName&u=u=$expr_$&u=$expr_2$&u=...//??>>
 *
 *  where $param_i$ is the result of compiling the expression param_i
 *
 */
    
    void link() {
        boolean isInclude = false;
        if (term("href") || term("include") || term("expand")) {
            if (term("include")) {
                isInclude = true;
                echo(concatSep+"''<<??include a=");
                next();
            }
            else {
                if (term("expand")) {
                    echo(concatSep+"''<a href=\""+ servlet + "?eip=ZYX0000XYZ&amp;a=");  // xml jg
                    next(); next();
                } // skip href !!
                else {// href
                    next();
                    if (term("in")) {
                        next();
                        if (sym.ttype == TT_WORD) {
                            { echo(concatSep+"''<a target=\""+sym.sval+"\" href=\"" + servlet + "?a=");
                              next();}
                        }
                        else err("illegal frame identifier");
                    }                   
                    else {echo(concatSep+"''<a href=\"" + servlet + "?a=");}
                }
            }
            if (sym.ttype == TT_WORD) {
                { String nodeident=sym.sval;
                  next();
                  if (nodeident.indexOf(".")==-1) { // implicit call to a node of this project
                      nodeident=projectid+"."+nodeident;
                  }
                  echo(nodeident+"&amp;u=''"); }  // xml jg
            }
            else err("illegal node identifier");
            
            // with inclusion of include
            if (sym.ttype == '[') {
                next();
                //echo(concatSep+"''&amp;u=''");
                
                if (term("include")) link();
                else { echo(concatSep); first_level_simple_expression(); }
                while (sym.ttype == ',') {
                    next();
                    echo(concatSep+"''&amp;u=''");
                    //echo(concatSep+"''&amp;u=''");
                    if (term("include")) link();
                    else { echo(concatSep); first_level_simple_expression(); }// xml jg
                }
                if (sym.ttype != ']') err("missing ']' after node parameter list or missing ',' between parameters");
                //else
                next();
            }
            
                /* without inclusion of include
                if (sym.ttype == '[') {
                    next();
                    echo(concatSep);
                    first_level_simple_expression();
                    while (sym.ttype == ',') {
                        next();
                        echo(concatSep+"''&amp;u=''"+concatSep);   // xml jg
                        first_level_simple_expression();
                    }
                    if (sym.ttype != ']') err("missing ']' after node param list");
                    else next();
                }
                 */
            if(isInclude) echo(concatSep+"''//??>>''");
            else echo(concatSep+"''\">''");
        }
    }
    
    
/*
 *  active_link ::= "active" "href" nodeident [ "[" simple_expression_OR_INCLUDE_list "]" ]
 *
 *  compiled to
 *
 *  '<input type="hidden" name="a" value=" [[ nodeident ]] ">'
 *
 *  '<input type="hidden" name="u" value="', [[ simple_expression ]], '">'
 *  ...
 *
 *
 */
    
    void active_link() {
        // current symbol is "active"
        next();
        if (term("href")) next();
        else err("missing href after active");
        
        if (sym.ttype == TT_WORD) {
            String nodeident=sym.sval;
            next();
            if (nodeident.indexOf(".")==-1) { // implicit call to a node of this project
                nodeident=projectid+"."+nodeident;
            }
            echo(concatSep+"''<input type=\"hidden\" name=\"a\" value=\""+nodeident+"\"/>''");       //xml jg
        }
        else err("illegal node identifier");
        if (sym.ttype == '[') {
            next();
            //jg echo(concatSep+"''<input type=\"hidden\" name=\"u\" value=\"''"+concatSep);
            //jg first_level_simple_expression();
            echo(concatSep+"''<input type=\"hidden\" name=\"u\" value=\"''");
            if (term("include")) link();
            else {echo(concatSep); first_level_simple_expression();}
            
            echo(concatSep+"''\"/>''");  //xml jg
            
            while (sym.ttype == ',') {
                next();
                //jg echo(concatSep+"''<input type=\"hidden\" name=\"u\" value=\"''"+concatSep);
                //jg first_level_simple_expression();
                echo(concatSep+"''<input type=\"hidden\" name=\"u\" value=\"''");
                if (term("include")) link();
                else {echo(concatSep); first_level_simple_expression();}
                echo(concatSep+"''\"/>''");  //xml jg
            }
            if (sym.ttype != ']') err("missing ']' after node param list");
            else next();
        }
        
    }
    
        /*
         * set_attribute = "set" attrident "=" (input_field | simple_expr)
         * set_attribute = "set" "parameter" "=" (input_field | simple_expr)   // do display action jg
         * set_attribute = "set" "parameter_encoding" "=" (input_field | simple_expr)   // to modify encoding jg
         *
         */
    void set_attribute() {
        next();
        if (sym.ttype == TT_WORD) {
            String parameterName="";                                                                // jg
            String fieldName="av";
            parameterName=sym.sval;                                                                 // jg
            if (parameterName.equals("parameter")){ fieldName="u";}           //  for a parameter U=... jg
            if (parameterName.equals("parameter_encoding")){ fieldName="u_enc";
            parameterName="parameter";}  //  for a parameter_encoding u_enc=... jg
            next();
            
            if (sym.ttype == '(') {next();  // it's a function like encoded(pwd)
            if (sym.ttype == TT_WORD) {
                parameterName+="|"+sym.sval ;
                next();
                if (sym.ttype == ')') next();
                else err("missing ')' ");
            } else err("illegal attribute identifier");
            }
            
            if (sym.ttype == '=') next();
            else err("missing '=' ");
            
            if (term("textfield") || term("textarea")|| term("select")|| term("free")) {
                if (!(parameterName.equals("parameter")))
                {echo(concatSep+"''<input type=\"hidden\" name=\"an\" value=\"<<$$"+parameterName+"$$>>\"/>''");} //xml jg not a display
                input_field(fieldName);}  //jg select
            else {
                if (!parameterName.equals("parameter")){ // a update hidden
                    echo(concatSep+"''<input type=\"hidden\" name=\"hn\" value=\"<<$$"+parameterName+"$$>>\"/>''");
                    echo(concatSep+"''<input type=\"hidden\" name=\"hv\" value=\"<<$$''"+concatSep); // jg
                    first_level_simple_expression();
                    echo(concatSep+"''$$>>\"/>''");   //xml jg
                }
                else { // a display or parameter_encoding
                    echo(concatSep+"''<input type=\"hidden\" name=\""+fieldName+"\" value=\"''"+concatSep); // jg
                    first_level_simple_expression();
                    echo(concatSep+"''\"/>''");   //xml jg
                }
            }
        }
        else err("illegal attribute identifier");
    }
    
        /*
         *   input_field = "textfield" "(" simple_expr [ "," simplexpr ] ")"
         *               | "textarea"  "(" simple_expr "," simple_expr [ "," simplexpr ] ")"
         *               | "free    "  "(" <element> "("  generalized exp   ")" ")"
         *               | "select"    "(" simple_expr "," simple_expr [ "," simplexpr ]
         *                                | include ... ")"
         */
    void input_field(String fieldName) {  // jg
        if (term("textfield")) {
            next();
            if (sym.ttype == '(') next();
            else err("missing '('");
            echo(concatSep+"''<input type=\"text\" name=\""+fieldName+"\" size=\"''"+concatSep);   // jg
            first_level_simple_expression();
            if (sym.ttype == ',') { // initial value of the field
                echo(concatSep+"''\" value=\"''"+concatSep);
                next();
                first_level_simple_expression();
            }
            echo(concatSep+"''\"/>''");       //xml jg
            if (sym.ttype == ')') next();
            else err("missing ')'");
        } else if(term("textarea")) {
            next();
            if (sym.ttype == '(') next();
            else err("missing '(' after 'textarea'");
            echo(concatSep+"''<textarea name=\""+fieldName+"\" rows=\"''"+concatSep);   // jg
            // nb. of columns
            first_level_simple_expression();
            echo(concatSep+"''\" cols=\"''"+concatSep);
            // skip ','
            next();
            // nb of rows
            first_level_simple_expression();
            echo(concatSep+"''\">''");
            // text (not required)
            if (sym.ttype == ',') {
                next();
                echo(concatSep);
                first_level_simple_expression();
            }
            echo(concatSep+"''</textarea>''");
            if (sym.ttype == ')') next();
            else err("missing ')'");
        } else if(term("free")) {
            next();
            if (sym.ttype == '(') next();
            else err("missing '(' after 'free'");
            field();
            if (sym.ttype == ')') next();
            else err("missing ')'");
        } if (term("select")) {                     // select field JG
            next();
            if (sym.ttype == '(') next();
            else err("missing '('");
            echo(concatSep+"''<select name=\""+fieldName+"\" >''");   // jg
            if (term("include")) {link(); // include the option list
            } else {echo(concatSep+"''<option>''"+concatSep);
            first_level_simple_expression();
            echo(concatSep+"''</option>''");
            while (sym.ttype == ',') {
                next();
                echo(concatSep+"''<option>''"+concatSep);
                first_level_simple_expression();
                echo(concatSep+"''</option>''");
            }
            }
            echo(concatSep+"''</select>''");       //xml jg
            if (sym.ttype == ')') next();
            else err("missing ')'");
        }
    }
    
   /*
    *
    * on_action = "on" String-expr "do" db_action (tableident) [ "[" keyvalue {"," keyvalue} "]" ]
    * on_action = "on" String-expr "do" "display" "node"                                                         // jg
    *
    */
    void on_action() {
        next(); // skip "on"
        echo(concatSep+"''<input type=\"submit\" name=\"bidon\" value=\"''"+concatSep);
        first_level_simple_expression();
        echo(concatSep+"''\"/>''");         //xml jg
        
        if (term("do")) next();
        else err("missing 'do'");
        String dbaction = "";
        
        if (term("insert")) dbaction = "new";
        else if (term("delete")) dbaction= "del";
        else if (term("update")) dbaction= "upd";
        else if (term("display")) dbaction= "dis";
        
        if (!dbaction.equals("dis")){                                                                 // not "do display" jg
            echo(concatSep+"''<input type=\"hidden\" name=\"act\" value=\"<<$$"+dbaction+"$$>>\"/>"
            +"<input  type=\"hidden\" name=\"con\" value=\"<<$$[[?!A_DB]]$$>>\"/>''");}  //xml jg
        
        next();
        
        if (sym.ttype == TT_WORD) {
            if (!dbaction.equals("dis")){                                                                 // not "do display" jg
                echo(concatSep+"''<input type=\"hidden\" name=\"tbl\" value=\"<<$$"+sym.sval+"$$>>\"/>''");}  //xml jg
            next();
        }
        
        else
            err("missing table identifier in db operation");
        
        if (sym.ttype == '[') {
            do {
                next();
                if (sym.ttype == TT_WORD) {
                    keyvalue();
                } else {
                    err("key attribute identifier expected");
                }
                
            }
            while (sym.ttype == ',');
            
            if (sym.ttype == ']') next();
            else err("']' expected after key attribute list");
        }
    }
        /*
         * keyvalue = keyattrident [ "=" first_level_simple_expression ]
         *
         */
    void keyvalue() {
        String attrName = sym.sval;
        echo(concatSep+"''<input type=\"hidden\" name=\"kn\" value=\"<<$$"+attrName+"$$>>\"/>''");//xml jg
        next();
        if (sym.ttype == '=') {
            echo(concatSep+"''<input type=\"hidden\" name=\"kv\" value=\"<<$$''"+concatSep);
            first_level_simple_expression();
            echo(concatSep+"''$$>>\"/>''");//xml jg
        } else {
            echo(concatSep+"''<input type=\"hidden\" name=\"kv\" value=\"<<$$''"
            +concatSep+attrName);
            //
            // Still this tricky thing to avoid ambiguities in the 'order by' clause
            // when the same column is selected twice
            //
            if(!attrOnlySet.add(attrName)) {
                nbAttrOnly++;
                echo(" as ZZZZ_00"+nbAttrOnly);
            }
            echo(concatSep+"''$$>>\"/>''");//xml jg
        }
    }
    
    
/*
        content =     element_type [ "(" [  field { , field } ] ")" ]
                                 | first_level_simple_expression
                                 | "{" items_content "}".
 */
    
    void content() {
        String elem_name ="";
        
        if (sym.ttype == '<') {
            elem_name = element_type();
            if (sym.ttype == '(') {
                next();
                if (sym.ttype != ')') {
                    echo(concatSep+"''>''"); // ends tag
                    field();
                    while (sym.ttype == ',') { next(); field(); }
                    if (sym.ttype == ')') { next();}
                    else err("missing ')' at end of subfield list");
                    echo(concatSep+"''</"+elem_name+">''");
                }
                else { // empty list
                    /* HTML specific ! */
                    if (elem_name.equals("hr") || elem_name.equals("HR")
                    || elem_name.equals("br") || elem_name.equals("BR"))
                        echo(concatSep+"''>''");
                    else
                        echo(concatSep+"''/>''");
                    next();
                }
            }
            else // tag alone
                echo(concatSep+"''>''");
        } else if (sym.ttype == '{')
            items_content();
        else {
            echo(concatSep); first_level_simple_expression();
        }
    }
    
/*
   items_content = field {"," field }
 */
    void items_content() {
        if (!itemsFound) {
            itemsFound = true;
            
            // end pre-part
            echo(concatPost);
            stopcopy();
            prebuf = copybuf.toString(); preAttrCount = attrCount;
            
            // start item-part
            attrCount = 0;
            attrOnlySet = new TreeSet();
            startcopy();
            echo(concatPre);
            next();
            
            field();
            while (sym.ttype == ',') {
                next();
                field();
            }
            
            if (sym.ttype == '}') next();
            else err("'}' expected at the end of item fields");
            
            echo(concatPost);
            displaybuf = copybuf.toString(); dispAttrCount = attrCount;
            
            // start post-part
            attrCount = 0;
            attrOnlySet = new TreeSet();
            startcopy();
            echo(concatPre);
        }
        else err("'items' found twice in the same node definition");
    }
    
    
/*
   element_type = "<" elem_type_ident { elem_type_ident "=" simple_expression } ">"
 
 */
    String element_type() {
        String eIdent="";
        next();
        if (sym.ttype == TT_WORD) {
            eIdent = elem_type_ident();
            echo(concatSep+"''<"+eIdent+"''");
            
            while (sym.ttype == TT_WORD) {
                String attrIdent = elem_type_ident();
                echo(concatSep+"'' "+attrIdent+"=\"''"+concatSep);
                
                if (sym.ttype == '=') next();
                else err("missing '=' after element attribute name");
                first_level_simple_expression();
                while (sym.ttype == ',') {
                    next();
                    echo(concatSep);
                    first_level_simple_expression();
                    // System.out.print("--" + sym.ttype + "--");
                    
                }
                echo(concatSep+"''\"''");
            }
            // echo("concatSept+"''>''");
            // we don't know yet if the tag ends with > or /> (empty tag)
            if (sym.ttype == '>')
                next();
            else
                err("missing '>' after style function name");
        }
        else
            err("missing  element type identifier after '<'");
        return eIdent;
    }
    
/*
   elem_type_ident = word { (':' | '-') word }
 */
    String elem_type_ident() {
        // PREcondition: sym.ttype == TT_WORD
        //
        // BUG: white spaces within the identifier are accepted before and
        // after : and - .
        //
        String id = sym.sval;
        next();
        while (sym.ttype == ':' || sym.ttype == '-') {
            id+=(char)sym.ttype;
            next();
            if (sym.ttype == TT_WORD) {
                id += sym.sval;
                next();
            }
        }
        return id;
    }
    
    
    
/*
  This is simply to detect simple expressions that contain only an attribute name.
  In this case the attribute must be aliased if it appears more than once.
  This is to avoid ambiguities in the ORDRER BY part of the SQL statement.
 
  For instance,
 
      the SQL statement   select A, A from T order by A
 
      is ambiguous, it must be written as
 
      select A, A as A2 from T order by A
 */
    void first_level_simple_expression() {
        attrOnly = true;
        simple_expression();
        if (attrOnly) {
            if(!attrOnlySet.add(theAttr)) {
                nbAttrOnly++;
                echo(" as ZZZZ_00"+nbAttrOnly);
            }
        }
    }
    
/*
   simple_expression  = term { +|- term }
 
 */
    void simple_expression() {
        term();
        while (sym.ttype == '+' || sym.ttype == '-') {
            attrOnly = false;
            echo(""+(char)sym.ttype);
            next();
            term();
        }
    }
    
/*
   term = factor {*|/ factor }
 */
    
    void term() {
        factor();
        while (sym.ttype == '*' || sym.ttype == '/') {
            attrOnly = false;
            echo(""+(char)sym.ttype);
            next();
            factor();
        }
    }
    
/*
 
factor =   stringconstant
         |numberconstant
         |function_identifier "(" simple_exression { ","  simple_exression } ")"
         | [collection_ident '.'] attribute_ident
         | parameter_ident
         |'(' expression ')'
 */
    void factor() {
        if (sym.ttype == '"') { quoted_string(); attrOnly = false;}
        else if (sym.ttype == TT_NUMBER) {number_literal();  attrOnly = false;}
        else if (sym.ttype == '-') {  // JG 2002.10.1 negative constant
            echo("-");
            next();
            if (sym.ttype == TT_NUMBER) {number_literal(); sym.nval*=-1; attrOnly = false;}
            else err("number missing after '-'");
        }
        else if (sym.ttype == '(') {
            attrOnly = false;
            echo("(");
            next();
            expression();
            if (sym.ttype == ')') {echo(")"); next();}
            else err("')' missing");
        }
        else if (sym.ttype == TT_WORD) {
            String ident1 = sym.sval;
            next();
            if (sym.ttype == '(') { // function call
                attrOnly = false;
                echo(ident1); echo("("); next(); simple_expression_list();
                if (sym.ttype == ')') {echo(")"); next();}
                else err("')' missing at end of param. list");
            }
            else if (sym.ttype == '.') { // collection.attribute
                echo(ident1); echo("."); next();
                if (sym.ttype == TT_WORD) {
                    echo(sym.sval); next(); attrCount++;
                } else err("attribute name missing after '.'");
            }
            else { // attribute or parameter
                int pi = paraIndex(ident1);
                if (pi < nbparams) {
                    echo("<<??param-"+pi+"//??>>");  attrOnly = false;}
                else {
                    echo(ident1); attrCount++; theAttr = ident1;
                }
                
            }
        }
        else err("string or number or function or variable expected");
    }
    
 /*
   expression = condition
  */
    void expression() {
        condition();
    }
    
    
    void quoted_string() {
        if (sym.ttype == '"') {
            { laststring = sym.sval; }
            { echo("''" + quadrupleQuotes(sym.sval) + "''"); }
            next();
        }
        else err("illegal string");
    }
    
    void number_literal() {
        if (sym.ttype == TT_NUMBER) {
            if ((int)sym.nval == sym.nval) echo(""+(int)sym.nval);
            else echo(""+sym.nval);
            next();
        }
        else err("illegal number literal");
    }
    
    void identifier() {
        if (sym.ttype == TT_WORD) {
            echo(sym.sval);
            next();
        }
        else err("illegal identifier");
    }
    
    void simple_expression_list() {
        expression();//simple_expression();  // modifier pour accepter les casewhen
        while (sym.ttype == ',') {
            echo(","); next();
            expression();//simple_expression(); // modifier pour accepter les casewhen
        }
    }
    
    
    
/* from_part = "from" collection_id [alias_id] { ',' collection_id [alias_id] }
                      [select_part();]
                      [order_part]
                      [limit_part]
 
 */
    
    void  from_part() {
        if (term("from")) {
            startcopy();
            next();
            identifier(); // collection name
            if (sym.ttype == TT_WORD && !term("distinct")&& !term("group") &&  !term("selected")&& !term("order")&& !term("node")&& !term("end")) {// alias    jg
                echo(" ");
                identifier();
            }
            while (sym.ttype == ',') {
                echo(","); next();
                identifier(); // collection name
                if (sym.ttype == TT_WORD && !term("distinct")&& !term("group")&& !term("selected")&& !term("order")&& !term("node")&& !term("end")) { // alias
                    echo(" ");
                    identifier();
                }
            }
            frombuf = copybuf.toString();
            if (term("distinct")) {distinct();}
            select_part();                                      // JG
            //System.out.println("after select:"+sym.sval);
            group_part();                                       // jg
            order_part();                                       // jg
            limit_part();                                       // jg
            //System.out.println("after order:"+sym.sval);
        }
        else {frombuf="dual";selectbuf = "NODEF";orderbuf="NODEF";groupbuf="NODEF";}  // FROM PART IS OPTIONAL JG
        
    }
    
    /* distinct_part = "distinct" //after from_part  */
    
    void distinct(){
        next();
        displaybuf="distinct "+displaybuf;
    }
    
    
    void  group_part() {
        if (term("group")) {
            next();
            if (term("by")) {
                startcopy();
                next();
                simple_expression(); // collection name or expression
                while (sym.ttype == ',') {
                    echo(","); next();
                    simple_expression(); // collection name or expression
                }
                groupbuf = copybuf.toString();
            }else err("'by' missing after 'group'");
        }
        else {groupbuf="NODEF";}  // GROUP PART IS OPTIONAL JG
        
    }
    
    
    
/* select_part = "selected" "by" condition.
 */
    
    void select_part() {
        if (term("selected")) {
            next();
            if (term("by")) {
                startcopy();
                next();
                condition();
                selectbuf = copybuf.toString();
            }
            else err("'by' missing after 'selected'");
        }
        else selectbuf = "NODEF";  //  selected by is optional jg
    }
    
/* condition = ["not"] logical_term { "or" logical_term  }
 
 */
    void condition() {
        if (term("not")) {echo(" not "); next();}
        logical_term();
        while(term("or")) {echo(" or "); next(); logical_term();}
    }
    
/* logical_term = logical_factor { "and" logical_factor }
 */
    void logical_term() {
        logical_factor();
        while(term("and")) {echo(" and "); next(); logical_factor();}
    }
    
/*  logical_factor =  simple_expression (  comparision_op simple_expression
                                                                                  | "is" ["not"] "null" ).
 
 */
    void logical_factor() {
          /* DELETE
             if (sym.ttype =='(') {
                 echo("(");
         next();
         condition();
                 if (sym.ttype == ')') {echo(")"); next();}
         else err("')' missing at end of logical factor");
      }
      else {
           */
        simple_expression();
        if (term("is")) {
            echo(" is "); next();
            if (term("not")) { echo(" not "); next(); }
            if (term("null")) { echo(" null "); next();}
            else err("'null' expected");
        }
        else {
            /* ADDED */
            if (term("like") || sym.ttype == '<' || sym.ttype == '>' || sym.ttype == '!' ||
            sym.ttype == '=') {
                comparison_op();
                simple_expression();
            }
            
        }
        
    }
    
/*
    comparison_op = "like" | "<" | "<=" | ">" | ">=" | "=" | "<>"
 */
    void comparison_op() {
        if (term("like")) {echo(" like ");
        next();
        }
        else if (sym.ttype == '<') {
            echo("<"); next();
            if (sym.ttype == '=') { echo("="); next(); }
            if (sym.ttype == '>') { echo(">"); next(); }
        }
        else if (sym.ttype == '>') {
            echo(">"); next();
            if (sym.ttype == '=') { echo("="); next(); }
        }
        else if (sym.ttype == '!') {
            next();
            if (sym.ttype == '=') { echo("<>"); next(); }
        }
        else if (sym.ttype == '=') { echo("="); next(); }
    }
    
    
    // order_part =  "order" "by" simple_expression ["desc"] {',' simple_expression ["desc"]}.
    
    void order_part() {
        if (term("order")) {
            startcopy();
            next();
            if (term("by")) {
                // echo("order by ");
                next();
                simple_expression();
                if (term("desc")) {
                    echo(" desc ");
                    next();
                }                
                while (sym.ttype == ',') {
                    echo(","); next();
                    simple_expression();
                    if (term("desc")) {
                        echo(" desc ");
                        next();
                    }
                    
                }
                
                orderbuf = copybuf.toString();
            }
            else err("'by' missing after 'order'");
        }
        else orderbuf ="NODEF";  // order by is optional jg
    }

       // limit_part =  "limit"  simple_expression }.
    
    void limit_part() {
        if (term("limit")) {
            startcopy();
            echo(" limit ");
            next();
            simple_expression();
            System.out.println(orderbuf+" | "+copybuf.toString());
            orderbuf+=copybuf.toString();
          }
    }

    
    ///////////////////////// Actions //////////////////////////
    
    void err(String e) {
        System.err.println("line : " + sym.lineno() + " " + e);
        errorbuf+="line : " + sym.lineno() + " " + e+"\n";
        errInNodeDef++;
        
    }
    
    void echo(String s) {
        // System.out.print(s);
        // if (copyon)
        copybuf.append(s);
        if (debug) System.out.print(s);
        
    }
    
    
    void startcopy() {
        copyon = true;
        copybuf = new StringBuffer();
    }
    
    void stopcopy() {
        copyon = false;
    }
    
    void orderforview() {
        orderbuf = copybuf.toString();
    }
    
    void selectforview() {
        selectbuf = copybuf.toString();
    }
    
    void fromforview() {
        frombuf = copybuf.toString();
    }
    
    void displayforview() {
        displaybuf = copybuf.toString();
    }
    
    
    void storeCompiledNode() {
        if (errInNodeDef>0) {
            //System.out.println("*** node "+nodeid+" : "+errInNodeDef+" error"+(errInNodeDef>1?"s":""));
            // return; // JG
        }
        if (directToDB) {
            try {
                if (fromFile){
                    String insertRequest =
                    "insert into nodes(projectid,name, nbparam, pre, items, post, collection, selector, groupby, ordering, plaintxt,status,error,cachesize)"
                    + " values("
                    + "'"+projectid+"',"
                    + "'"+nodeid+"',"
                    +" "+nbparams+","
                    +"'"+prebuf+"',"
                    +"'"+displaybuf+"',"
                    +"'"+postbuf+"',"
                    +"'"+frombuf+"',"
                    +"'"+selectbuf+"',"
                    +"'"+groupbuf+"',"
                    +"'"+orderbuf+"',"
                    +"'"+plaintxtbuf+"',"
                    +"'"+statusbuf+"',"
                    +"'"+doubleQuotes(errorbuf)+"',"
                    +cachesize
                    +")";
                    //System.out.println(insertRequest);
                    int m = stmt.executeUpdate(insertRequest);
                    //System.out.println("node: "+nodeid /* +" "+m+" row inserted in table nodes" */);
                }
                else{
                    String updateRequest =
                    "update  nodes"
                    + " set "
                    +" nbparam="+nbparams+","
                    +"pre='"+prebuf+"',"
                    +"items='"+displaybuf+"',"
                    +"post='"+postbuf+"',"
                    +"collection='"+frombuf+"',"
                    +"selector='"+selectbuf+"',"
                    +"groupby='"+groupbuf+"',"
                    +"ordering='"+orderbuf+"',"
                    +"status='"+statusbuf+"',"
                    +"error='"+doubleQuotes(errorbuf)+"',"
                    +"cachesize="+cachesize
                    +" where "
                    + "projectid='"+projectid+"'"
                    + " and name='"+nodeid+"'";
                    //System.out.println(updateRequest);
                    int m = stmt.executeUpdate(updateRequest);
                    //System.out.println("node: "+nodeid /* +" "+m+" row updated in table nodes" */);
                }
            }
            catch (SQLException se) {
                System.out.println("*** SQL ERR, node: "+nodeid+" "+se.getMessage());
            }
        }
        if (fromFile){
            out.println();
            out.println("delete from nodes where name='"+nodeid+"';");
            out.println();
            out.println(
            "insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)");
            out.println("values(");
            out.println("'"+nodeid+"',");
            out.println(" "+nbparams+",");
            out.println("'"+prebuf+"',");
            out.println("'"+displaybuf+"',");
            out.println("'"+postbuf+"',");
            out.println("'"+frombuf+"',");
            out.println("'"+selectbuf+"',");
            out.println("'"+orderbuf+"'");
            out.println(");");
        }
    }
    
    void invalideLastNode() {
        if (directToDB) {
            try {
                String updateRequest =
                "update  nodes"
                + " set "
                +"status='INVALID',"
                +"error='"+doubleQuotes(errorbuf)+"'"
                +" where "
                + "projectid='"+projectid+"'"
                + " and name='"+nodeid+"'";
                //System.out.println(updateRequest);
                int m = stmt.executeUpdate(updateRequest);
                //System.out.println("node: "+nodeid /* +" "+m+" row updated in table nodes" */);
            }
            catch (SQLException se) {
                System.out.println("*** SQL ERR, node: "+nodeid+" "+se.getMessage());
            }
        }
    }
    
    
    
    String quadrupleQuotes(String s) {
        int ix = 0, ixf, ixx;
        while ((ix = s.indexOf("'", ix)) > -1) {
            s = s.substring(0, ix) + "''''" + s.substring(ix+1, s.length());
            ix += 4;
        }
        return s;
        
    }
    
    String doubleQuotes(String s) {
        int ix = 0, ixf, ixx;
        while ((ix = s.indexOf("'", ix)) > -1) {
            s = s.substring(0, ix) + "''" + s.substring(ix+1, s.length());
            ix += 2;
        }
        return s;
        
    }
    
}

