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
import java.sql.*;
import java.util.*;

/*****************************************************
 *
 *             CLASS COPY TABLE
 *
 *****************************************************
 *
 *  Cette classe permet de copier un tableau à 2 entrées défini dans le projet
 *  "grid" en créant pour les tables grid_name, columns, lines et cross des
 *  "rows" identiques à celles qui définissent le tableau à copier mais avec
 *  de nouveaux id
 *
 */
public class CopyTable {
	
	public CopyTable(){}
   
   public  synchronized String doIt(Object params) {
      
      //connection variables
      Connection con;
      Statement stmt;
      ResultSet results;
      String url = "jdbc:odbc:sgbdUNO1",
      user = "grid",
      pwd = "grille",
      driver = "sun.jdbc.odbc.JdbcOdbcDriver";
      
      //local variables
      Vector oldIdCols = new Vector();
      Vector wordingCols = new Vector();
      Vector oldIdLine = new Vector();
      Vector wordingLine  = new Vector();
      int newIdGrid, newIdLine, newIdCol, tmp1, tmp2;
      String gridID="",socID="",newGridName="";
      
      // connect to the database
      OracleConnection oc = new OracleConnection();
      Object[] conection = oc.CreateConnection(url, user, pwd, driver);
      con = (Connection)conection[0];
      stmt = (Statement)conection[1];
      
      // get the value contained in parameters of this method
      String[] temp = (String[])params;
      socID=temp[0];
      gridID=temp[1];
      newGridName=temp[2];
      
      try{
         //get next id_grid_name, next id_column and next id_line
         results = stmt.executeQuery("select seq_grid_name.nextval from dual");
         results.next();
         newIdGrid = new Integer(results.getString(1)).intValue();
         results = stmt.executeQuery("select seq_lines.nextval from dual");
         results.next();
         newIdLine = (new Integer(results.getString(1)).intValue())+1;
         results = stmt.executeQuery("select seq_columns.nextval from dual");
         results.next();
         newIdCol = (new Integer(results.getString(1)).intValue())+1;
         
         // get id and header's wording from column and line from the original grid (id_grid_name=gridID)
         results = stmt.executeQuery("select id_line, wording  from lines where id_soc = '"+socID+"' and id_grid_name = '"+gridID+"'");
         while (results.next()) {
            oldIdLine.add( results.getString(1));
            wordingLine.add(results.getString(2));
         }
         results = stmt.executeQuery("select id_column, wording  from columns where id_soc = '"+socID+"' and id_grid_name = '"+gridID+"'");
         while (results.next()) {
            oldIdCols.add(results.getString(1));
            wordingCols.add(results.getString(2));
         }
         
         //create the new grid_name
         stmt.executeUpdate("insert into grid_name (id_soc, id_grid_name, wording)  select id_soc, '"+newIdGrid+
         "','"+newGridName+"' wording from grid_name where id_soc = '"+socID+"' and id_grid_name = '"+gridID+"'");
         
         //createt the line  header
         for (int i=0;i<oldIdLine.size();i++){            
            stmt.executeUpdate("insert into lines (id_soc, id_line, id_grid_name, wording) values ('"+
            socID+"', seq_lines.nextval ,'"+newIdGrid+"','"+(String)wordingLine.get(i)+"')");
         }
         //ceate the column header
         for (int i=0;i<oldIdCols.size();i++){            
            stmt.executeUpdate("insert into columns (id_soc, id_column, id_grid_name, wording) values ('"+
            socID+"', seq_columns.nextval ,'"+newIdGrid+"','"+(String)wordingCols.get(i)+"')");
         }
         //fill all cells with values by an uptade
         for (int li=0;li<oldIdLine.size();li++)
            for (int c=0;c<oldIdCols.size();c++){
               tmp1 = newIdCol+c;
               tmp2 = newIdLine+li;
               stmt.executeUpdate("update cross set txt = (select txt from cross where id_soc = '"+socID+
               "' and id_line = '"+(String)oldIdLine.get(li)+"' and id_column = '"+(String)oldIdCols.get(c)+"' )where id_soc = '"+socID+"' and id_line = '"+tmp2+"' and id_column = '"+tmp1+"'");
            }
      }
      catch (SQLException ex) {
         System.out.println("error in CopyTable class (doIt method) :\n"+ex);
      }
      return "";
      
      
      
   }
   
}
