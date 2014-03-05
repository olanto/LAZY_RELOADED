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
import java.net.*;
import java.io.*;


/*****************************************************
 *
 *             CLASS CHECK IP ADDRESS
 *
 *****************************************************
 *
 *  Cette classe permet de tester si une adresse répond IP est valide
 *  (exemple 10.212.2.101) et si c'est le cas, elle renvoie son nom logique
 *  .
 *
 *  ATTENTION: les paramètres de la méthode doIt sont : soc ID, gridID,
 *  idColumn(ip addr) et idColumn(etat) et ces paramètres sont écrits
 *  en DUR dans le noeud GRID.checkIPaddess.lzy
 *
 */
public class CheckIPAddress implements Runnable{
   
   boolean ping_termine = false;
   Thread myThread;
   String validity= "INVALID", hostName = "NON COMUNIQUE", inetAddr = "NON COMUNIQUE";
   String wording, ipAddr, id_line;
   
   // default constructor
   public CheckIPAddress(){}
   
   
   /**********************************
    *
    *  Thread constructor
    */
   public CheckIPAddress(String aWording, String anIpAddr, String anId_line){
      wording = aWording;
      ipAddr = anIpAddr;
      id_line = anId_line;
      
      myThread = new Thread(this);
      myThread.start();
   }
   
   /*********************************
    *
    *  Run
    */
   public void run() {
      
      //variables  locales
      String line;
      int tmp;
      Runtime runtime = Runtime.getRuntime();
      Process myProcess;
      BufferedReader bf;
      
      try{
         for (int k=0;k<2;k++) {
            // Ping ip address by Inet address
            myProcess = runtime.exec("C:/WINNT/system32/ping -n 1 "+ipAddr);
            bf = new BufferedReader( new InputStreamReader(myProcess.getInputStream()));
            while ((line = bf.readLine()) != null) {
               if (line.indexOf("ponse de") != -1){
                  validity = "VALID";
                  break;
               }
            }
            // NS lookup
            if (validity.equals("VALID")){
               myProcess = runtime.exec("C:/WINNT/system32/nslookup "+ipAddr);
               bf = new BufferedReader( new InputStreamReader(myProcess.getInputStream()));
               while ((line = bf.readLine()) != null) {
                  tmp = line.indexOf("Nom");
                  if (tmp != -1){
                     hostName = line.trim().substring(tmp+9,line.indexOf(".",tmp+4)).toUpperCase();
                     break;
                  }
               }
            }
            // Ping ip address by hostname (wording)
            myProcess = runtime.exec("C:/WINNT/system32/ping -n 1 "+wording);
            bf = new BufferedReader( new InputStreamReader(myProcess.getInputStream()));
            while ((line = bf.readLine()) != null) {
               tmp = line.indexOf("ponse de");
               if (tmp != -1){
                  inetAddr = line.trim().substring(tmp+9,line.indexOf(":",tmp+4)-1).toUpperCase();
                  break;
               }
            }
         }
         ping_termine = true;
      }
      catch (IOException e) {
         System.out.println("error in CheckIPAdress class (doIt method) :\n"+e);
      }
      
   }
   
   /************************************************
    *
    *  DO IT
    *
    ***/
   public  synchronized String doIt(Object params) {
      //variables de connexion
      Statement stmt, stmt2;
      ResultSet resultLine, results;
      String url = "jdbc:odbc:sgbdUNO1",
      user = "grid",
      pwd = "grille",
      driver = "sun.jdbc.odbc.JdbcOdbcDriver";
      
      //variables locales
      String wording, id_line="",  gridID="",socID="", idColumnIPAddr="", idColumnServeur="";
      int checkedAddress = 0, actualRow=0, nbAddr=0;
      Connection con;
      
      
      try{
         //connexion à la base de données
         OracleConnection oc = new OracleConnection();
         Object[] conection = oc.CreateConnection(url, user, pwd, driver);
         con = (Connection)conection[0];
         stmt = (Statement)conection[1];
         stmt2 = con.createStatement();
         
         // récupère les valeurs passéees en paramètres de cette méthode
         String[] temp = (String[])params;
         socID=temp[0];
         gridID=temp[1];
         idColumnServeur=temp[2];
         idColumnIPAddr=temp[3];
         
         // compte tous les éléments à pinger et crée un tableau
         resultLine = stmt.executeQuery("select count(wording) from lines  where id_soc = '"+socID+"' and id_grid_name = '"+gridID+"'");
         resultLine.next();
         nbAddr = new Integer(resultLine.getString(1)).intValue();
         CheckIPAddress[] ping = new CheckIPAddress[nbAddr];
         
         // pour toutes les lignes, récupère le libellé et sa position dans la table Oracle d'une address IP
         resultLine = stmt.executeQuery("select wording, id_line from lines  where id_soc = '"+socID+"' and id_grid_name = '"+gridID+"'");
         
         while (resultLine.next()) {
            wording = resultLine.getString(1);
            id_line = resultLine.getString(2);
            
            // récupèration de l'adresse IP
            results = stmt2.executeQuery("select txt from cross where id_soc = '"+socID+"' and  id_line = '"+id_line+"' and  id_column = '"+idColumnIPAddr+"'");
            results.next();
            ipAddr = results.getString(1);
            
            // start threads
            ping[actualRow] = new CheckIPAddress(wording, ipAddr, id_line);
            actualRow++;
         }
         
         
         
         // attendre que toutes les machines ayent répondu
         while (checkedAddress<nbAddr) {
            checkedAddress=0;
            // attendre
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            // compter le nombre de machines ayant répondu
            for (int i=0; i<nbAddr; i++) {
               if (ping[i].ping_termine)
                  checkedAddress++;
            }//end for
            System.out.print("\r"+(checkedAddress*100)/nbAddr+"% d'adresses testées...");
         }
         System.out.println();
         
         // update database
         checkedAddress = 0;
         for (int i=0; i<nbAddr; i++) {
            if (ping[i].ipAddr.startsWith("***"))
               stmt.executeUpdate("update cross set txt = '******' where id_soc = '"+socID+"' and id_line = '"+ping[i].id_line+"' and id_column = '"+idColumnServeur+"'");
            else {
               System.out.println("update cross set txt = 'Ping = &#32;<"+ping[i].validity+"/>Nom = <b>"+ping[i].hostName+"</b><BR/>IP addr. = <b>"+ping[i].inetAddr+"</b>' where id_soc = '"+socID+"' and id_line = '"+ping[i].id_line+"' and id_column = '"+idColumnServeur+"'");
               stmt.executeUpdate("update cross set txt = 'Ping = &#32;<"+ping[i].validity+"/>Nom = <b>"+ping[i].hostName+"</b><BR/>IP addr. = <b>"+ping[i].inetAddr+"</b>' where id_soc = '"+socID+"' and id_line = '"+ping[i].id_line+"' and id_column = '"+idColumnServeur+"'");
               checkedAddress++;
            }
         }
         oc.closeConnection(con);
      }
      catch (SQLException ex) {
         System.out.println("error in CheckIPAdress class (doIt method) :\n"+ex);
      }
      System.out.print("checkedAddress"+checkedAddress);
      return checkedAddress+" adresses vérifiées";
   }
}