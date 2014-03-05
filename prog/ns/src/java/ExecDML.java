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
import java.util.*;
import java.text.*;
import isi.jg.ns.*;


public class ExecDML{

static long lastnotify=0,nextmax=0; 

public ExecDML(){}


    public static String doIt(Object o){
	/*
	__ExecDML.doIt[project,dmlstat,verbose] return string
	__ExecDML.doIt[PFR,"select sysdate from dual","YES"] return string

	*/
	String[] u=(String [])o;
	String conid=Node.getConId(u[0]+".");  // look for a connection projectname.
	if(NodeServer.verboseExternal) System.out.println("SQL TEXT"+u[1]);
	QueryResult Q = DBServices.execSQLonDB(u[1],conid, false);
        String res="";
        if (Q.valid) {
            res= "<b>" + Q.nbUpdated + " rows updated</b>";
        }
        else { // sql error
            res= "<hr/><h3>Database access error</h3>"
            + "<p>during:<b>update</b> execution</p>"
            + "<p>SQL text <b>"
            + Q.sql
            + "</b></p>"
            + "<p>SQLError: "
            + Q.msg
            + "</p>";
        }

	
        if (u[2].equals("YES")) return "connection:"+conid+" for project:"+u[0]+" / "+res;
        else return "";
        
     }
     
  public static String selectAndReturn(Object o){
	/*
	Return the result of an SQL select whose query returns only ONE ROW !

	__ExecDML.selectAndReturn[project,dmlstat] return string

	*/
	String[] u=(String [])o;
	String conid=Node.getConId(u[0]+".");  // look for a connection projectname.
	if(NodeServer.verboseExternal) System.out.println("SQL TEXT"+u[1]);
	QueryResult Q = DBServices.execSQLonDB(u[1],conid, true);
	String res="";
	if (Q.valid) {
		try {
			Q.result.next();
			res= Q.result.getString(1);
		}catch (Exception ex){// sql error
			if (Q.msg.trim().length() == 0) {  //no row to return but no errors
				res=""; 
			}
			else {
				res= "<hr/><h3>Database access error</h3>"
				+ "<p>during:<b>update or select</b> execution</p>"
				+ "<p>SQL text <b>"
				+ Q.sql
				+ "</b></p>"
				+ "<p>SQLError: "
				+ Q.msg
				+ "</p>";
			}
		}
	}
	else { // sql error		
		res= "<hr/><h3>Database access error</h3>"
		+ "<p>during:<b>update</b> execution</p>"
		+ "<p>SQL text <b>"
		+ Q.sql
		+ "</b></p>"
		+ "<p>SQLError: "
		+ Q.msg
		+ "</p>";		
	}
	return res;

}     

   public static String selectAndReturnWithParams(Object o){
	/*
	Return the result of an SQL whith parameter(s) select whose query returns only ONE ROW !

	__ExecDML.selectAndReturn[project,nbparams,dmlstat] return string

	*/
	String[] u=(String [])o;  
	String sqlText = u[2];
	for (int i=0;i<Integer.parseInt(u[1])*2;i++)
	sqlText += u[3+i];
	String conid=Node.getConId(u[0]+".");  // look for a connection projectname.
	if(NodeServer.verboseExternal) System.out.println("SQL TEXT"+sqlText);
	QueryResult Q = DBServices.execSQLonDB(sqlText,conid, true);
	String res="";
	if (Q.valid) {
		try {
			Q.result.next();
			res= Q.result.getString(1);
		}catch (Exception ex){// sql error
			if (Q.msg.trim().length() == 0) {  //no row to return but no errors
				res=""; 
			}
			else {
				res= "<hr/><h3>Database access error</h3>"
				+ "<p>during:<b>update or select</b> execution</p>"
				+ "<p>SQL text <b>"
				+ Q.sql
				+ "</b></p>"
				+ "<p>SQLError: "
				+ Q.msg
				+ "</p>";
			}
		}
	}
	else { // sql error		
		res= "<hr/><h3>Database access error</h3>"
		+ "<p>during:<b>update</b> execution</p>"
		+ "<p>SQL text <b>"
		+ Q.sql
		+ "</b></p>"
		+ "<p>SQLError: "
		+ Q.msg
		+ "</p>";		
	}
	return res;

}     
   public static String doItWithParams(Object o){
	/*
	__ExecDML.doIt[project,nbparams,dmlstat,verbose] return string
	__ExecDML.doIt[PFR,"select name from emp where empno = ',selectNo,'","NO"] return string

	*/
	String[] u=(String [])o;	
	String sqlText = u[2];
	for (int i=0;i<Integer.parseInt(u[1])*2;i++)
	sqlText += u[3+i];
	if(NodeServer.verboseExternal) System.out.println("SQL TEXT : "+sqlText);
   String conid=Node.getConId(u[0]+".");  // look for a connection projectname.
	QueryResult Q = DBServices.execSQLonDB(sqlText,conid, false);
        String res="";
        if (Q.valid) {
            res= "<b>" + Q.nbUpdated + " rows updated</b>";
        }
        else { // sql error
            res= "<hr/><h3>Database access error</h3>"
            + "<p>during:<b>update</b> execution</p>"
            + "<p>SQL text <b>"
            + Q.sql
            + "</b></p>"
            + "<p>SQLError: "
            + Q.msg
            + "</p>";
        }
		  
        if (u[u.length-1].equals("YES")) return "connection:"+conid+" for project:"+u[0]+" / "+res;
        else return "";
        
     }

    public static String checknotify(Object o){
	/*
	__ExecDML.checknotify[verbose] return string

	*/
	String res="";
	String[] u=(String [])o;
	
	String conid="DICTLAZY";  // look in dictionnary
	
      {  String sqlRequest="select max(n) from secure_notify";
        QueryResult Q = DBServices.execSQL(sqlRequest, true);
        if (Q.valid) {            
            try {   Q.result.next();
                    nextmax =Q.result.getLong(1);
                Q.result.close();
            } // try
            catch (Exception e) {
                System.out.println("ExecDML.checknotify: SQLError: " + e.getMessage());
            }
         }
        else System.out.println("ExecDML.checknotify: SQLError: " + Q.msg);
       }
       {String sqlRequest="select tab from secure_notify where n>"+lastnotify;
        QueryResult Q = DBServices.execSQL(sqlRequest, true);
        
        if (Q.valid) {
            
            try {
                int nbRes=0;
                while (Q.result.next()) {
                    String tab=Q.result.getString(1);
                    Node.clearDependentNodes(tab);
                    res+=" "+tab;
                    
                }
                if (NodeServer.verboseCache) System.out.print("load Text in hashtable nb: "+nbRes);
                Q.result.close();
            } // try
            catch (Exception e) {
                System.out.println("Nodes: during initText() SQLError: " + e.getMessage());
            }
        }
        else System.out.println("Nodes: during initText() SQLError: " + Q.msg);
    }
    long temp=lastnotify;
    lastnotify=nextmax;
    return "last "+temp+" next "+nextmax+res; 
    }


 
}