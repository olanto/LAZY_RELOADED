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
import java.net.*;
import java.util.*;
import java.sql.*;

public class OracleConnection {
   
   public OracleConnection(){}
   
   public Object[] CreateConnection(String url, String user, String pwd, String driver) {
      
      Object[] result = new Object[2];  
      Connection con;
      Statement stmt;
                      
      // make a connection with the data base
      try {
         System.out.println("connecting...");
         Class.forName(driver);
         con = DriverManager.getConnection(url, user, pwd);
         stmt = con.createStatement();  
         result[0]= con;
         result[1]= stmt;                     
      }
      catch (Exception e) {
         System.out.println("*** Unable to connect OracleConnection " + url);
         System.out.println(e.toString());
      }
      
      return result;
   }
      
   public  void closeConnection(Connection con) {
      try {
         if (con != null){
            con.close();
            System.out.println("connection closed!");
         }
      }
      catch (java.sql.SQLException e) {
         System.out.println("*** DB connection destroy failed\n");
         System.out.println(e.toString());
      }
   }
   
}

