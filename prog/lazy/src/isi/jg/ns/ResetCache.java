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

import java.sql.*;


class ResetCache{
    
    public static String ResetNodesDependencies(){ 
        String log="";
        try {
            String sqlModify="delete from secure_nodedep";
            QueryResult Q1 = DBServices.execSQL(sqlModify, false);
            
            if (Q1.valid) {log+= "<b>ResetNodesDependencies:" + Q1.nbUpdated + " rows deleted</b><p/>\n";}
            else {log+="<b>ResetNodesDependencies:  error in delete </b><p/>\n"; return log;}
            
                        
            String sqlRequest="select projectid, name, upper(collection) from nodes where status = 'VALID'";
            QueryResult Q = DBServices.execSQL(sqlRequest, true);
            
            if (Q.valid) {
                int nbRes=0;
                while (Q.result.next()) {
                    nbRes++;
                    String project=Q.result.getString(1);
                    String name=Q.result.getString(2);
                    String collection=Q.result.getString(3);
                    if (collection.indexOf(",")!=-1){
                        while (collection.indexOf(",")!=-1){
                            int ix=collection.indexOf(",");
                            String table=collection.substring(0,ix);
                            collection=collection.substring(ix+1);
                            int ispace=table.indexOf(" "); // pour les alias
                            if (ispace!=-1) table=table.substring(0,ispace);
                            addDependency(project,name,table,log);
                        }
                    }
                   int ispace=collection.indexOf(" ");  // pour les alias
                   if (ispace!=-1) collection=collection.substring(0,ispace);
                   addDependency(project,name,collection,log); // for the last (or the only one!)
                }
                log+="<b>ResetNodesDependencies:  OK END </b><p/>\n";
                Q.result.close();
            }
            else{log+="<b>ResetNodesDependencies:  error in selec </b><p/>\n"; return log;}
        } // try
        catch (SQLException e) {
            log+="<b>ResetNodesDependencies:  SQLError: " + e.getMessage()+"</b><p/>\n"; return log;}
        return log;
    }
    
    static String addDependency(String project, String name, String table, String log){
        if (NodeServer.verboseClearCache) System.out.println(project+","+name+","+table);
        String sqlModify="insert into secure_nodedep values("+
        "'"+project+"',"+
        "'"+name+"',"+
        "'"+table+"')";
        QueryResult Q2 = DBServices.execSQL(sqlModify, false);
        if (Q2.valid) {}
        else {log+="<b>ResetNodesDependencies:  error in insert </b>"
        +"<hr/><h3>Database access error</h3>"
        + "<p>during:<b>insert</b> execution</p>"
        + "<p>SQL text <b>"+ Q2.sql+ "</b></p>"
        + "<p>SQLError: "+ Q2.msg+ "</p>";}
        return log;
    }
    
    
}

