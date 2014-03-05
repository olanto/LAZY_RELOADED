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


public class DBServices {
    
    static private Hashtable connections;
    static private long nbreq = 0;
    static long nbclose = 0;  // pour gérer les curseurs ouverts
    
    // URL for a connection to an ORACLE database
    
    public static synchronized void init(String wurl, String wuser, String wpwd, String wdriver){
        connections=new Hashtable();
        ConnectionDB dictlazy=new ConnectionDB(wdriver,wurl,wuser,wpwd,"DICTLAZY: Bootstrap DB");
        connections.put("DICTLAZY",dictlazy);
        QueryResult Q=execSQL("select connectid,driver,url,userdb,pwddb from secure_connects", true);
        if (Q.valid) {
            
            try {
                int nbRes=0;
                while (Q.result.next()) {
                    nbRes++;
                    String k=Q.result.getString(1);
                    String d=Q.result.getString(2);
                    String u=Q.result.getString(3);
                    String n=Q.result.getString(4);
                    String p=Secure.decryptpwd(Q.result.getString(5));
                    ConnectionDB con=new ConnectionDB(d,u,n,p,k);
                    connections.put(k,con);
                }
                Q.result.close();
            } // try
            catch (SQLException e) {
                System.out.println("DBServices: (Get) during initializition of DB connections SQLError: " + e.getMessage());
            }
        } else System.out.println("DBServices: (Query) during initializition of DB connections SQLError: " + Q.msg);
    }
    
    public static void finish() {
        connections=null; // force finalize of connectionDB objects
    }
    
    public static String testConnection(String db){
        QueryResult res=execSQLonDB("select count(*) from dual",db,true);
        if(res.valid){
            return "<ICON>VALID</ICON>";
        } else{
            return "<ICON>INVALID</ICON>";
        }
        
    }
    
    
    public  static QueryResult execSQL(String sql, boolean select) {
        return execSQLonDB(sql,"DICTLAZY",select);
    }
    
    public  static QueryResult execSQLonDB(String sql,String db, boolean select) {
        if (NodeServer.verboseDB)System.out.println("DBServices:execSQLonDB. "+db);
        ConnectionDB CDB=(ConnectionDB) connections.get(db);
        ++nbreq;
        // increase opencursor in ORACLE !!!
        if (nbreq-nbclose>NodeServer.MAX_CURSOR){
            System.out.println("#cursor before gc():"+(nbreq-nbclose));
            System.gc();
            System.out.println("#cursor after gc():"+(nbreq-nbclose));
            
        }
        QueryResult Q = new QueryResult(sql);
        try {
            if ((nbreq % 100) == 0){
                System.out.println("DBServices: nbreq" + nbreq);
                System.out.println("#open cursor):"+(nbreq-nbclose));
            }
            if (CDB.con == null) {
                System.out.println("DBServices: error in connection after" + nbreq);
                CDB.con = DriverManager.getConnection(CDB.url, CDB.user, CDB.pwd);
                Q.stmt = CDB.con.createStatement();
                //Q.stmt.setEscapeProcessing(false);	//ligne suportée que par 'oci8' jdbc driver
            } else {
                if (NodeServer.verboseDB)System.out.println("DBServices: execSQLonDB.createstatement");
                Q.stmt = CDB.con.createStatement();
                //Q.stmt.setEscapeProcessing(false);	//ligne suportée que par 'oci8' jdbc driver
            }
            if (NodeServer.verboseDB)System.out.println("DBServices: Executing sql ..." + Q.sql);
            if (select) {
                if (NodeServer.verboseDB){
                    Timer t1 = new Timer("sql");
                    Q.result = Q.stmt.executeQuery(Q.sql);
                    t1.stop();
                }else { Q.result = Q.stmt.executeQuery(Q.sql);}
            } else {
                if (NodeServer.verboseDB)System.out.println("DBServices: execSQLonDB.execDML");
                Q.nbUpdated = Q.stmt.executeUpdate(Q.sql);
            }
        } catch (SQLException e) {
            System.out.println("DBServices: SQLError: " + e.getMessage());
            System.out.println("DBServices: SQL: " + Q.sql);
            Q.valid = false;
            Q.msg = e.getMessage();
         /*  only if bugs !!
         try {
            con.close();
            System.out.println("DBServices: close DB OK");
         }
         catch (java.sql.SQLException e2) {
            System.out.println("DBServices: failed to close DB");
            System.out.println(e2.toString());
         }
         con = null;
          */
        }
        return Q;
    }
}

