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


class ConnectionDB {
    public Connection con;
    
    public String url = "";
    public String user = "";
    public String pwd = "";
    public String driver = "";
    public String msg = "";
    
    ConnectionDB(String wdriver, String wurl, String wuser, String wpwd, String wmsg) {
        driver=wdriver;url=wurl;user=wuser;pwd=wpwd;msg=wmsg;
        System.out.println(
        "ConnectionDB:"+msg+" Initializing DB connection with :\n url="
        + url
        + "\n user="
        + user
        + "\n driver="
        + driver);
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pwd);
            
            System.out.println("ConnectionDB: connection "+msg+" OK\n");
        }
        catch (Exception e) {
            System.out.println("ConnectionDB: "+msg+" *** Unable to connect to database " + url);
            System.out.println(e.toString());
            return;
        }
    }
    public void finalize() {
        try {
            con.close();
        }
        catch (SQLException e) {
            System.out.println("ConnectionDB: "+msg+" SQLError: " + e.getMessage());
        }
    }
}

