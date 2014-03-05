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
import java.io.*;
import isi.jg.ns.*;
import java.util.*;


/*************************************
 *
 * usage in Lazy :
 * __ExecuteBat.doIt["filename.bat","-W -P myParameter -O myPath/myOutput.txt"]
 * __ExecuteBat.doIt["filename.bat"],"-O output.txt"]
 * __ExecuteBat.doIt["filename.bat"],"-P <parameters>"]
 * __ExecuteBat.doIt["filename.bat"]
 *
 * **********************************/


/*   A T T T E N T I O N  
 *  ====================
 *
 *  Avec jdk 1.5 (ou supérieur) le fin d'un "Process", qui a priori se termine
 *  correctement [myProcess.waitFor()] retourne le code 2 alors que sous JDK 1.3
 *  on obtient la valeur correcte 0.
 *  
 **************************************/


public class ExecuteBat {
   
   public ExecuteBat(){}
   
   private Runtime myRuntime;
   private boolean WAIT=true;
   private int returnvalue = -100;
   
   /*******
    *
    * DO IT
    *
    */
   public  String doIt(Object params) {
      String returnString="exécution terminée", batParamters="", outputfile="c:/temp/defaultLog.txt", returnLink="NULL", errorProcess="Terminé avec erreur(s)", successMsg="";
      String[] u=(String [])params;
      String batCommand = u[0];
      
      if (u.length == 2){
         String param;
         int ixx=0,ix=u[1].indexOf('-');
         while (ix > -1){
            ixx = u[1].indexOf("-",ix+1);
            if (ixx>0)
               param = u[1].substring(ix+1,ixx);    //for all parameters and remove "-" char
            else
               param = u[1].substring(ix+1);    //for last parmaeter and remove "-" char
            if (param.startsWith("P"))
               batCommand += " "+param.substring(2);   // -P for command line's parameters
            if (param.startsWith("W"))
               WAIT = true;                            // -W for waititing till the process end
            if (param.startsWith("O")){
               outputfile = param.substring(2);        //  -O for standard output file
            }
            if (param.startsWith("R")){
               returnLink = param.substring(2);        //  -R for return link
            }
            if (param.startsWith("E")){
               errorProcess = param.substring(2);        //  -E for error return message
            }
            if (param.startsWith("S")){
               successMsg = param.substring(2);        //  -S for success  return message
            }            
            ix = ixx;
         }
      }
      if(NodeServer.verboseExternal) System.out.println("batCommand: "+batCommand+
      "\noutputfile: "+outputfile+
      "\nwait: "+WAIT+
      "\nreturnLink: "+returnLink);
      
      try{
         Runtime myRuntime = Runtime.getRuntime();
         if(NodeServer.verboseExternal) {
         	if (myRuntime == null) System.out.println("myRuntime == null");
         	else System.out.println("myRuntime =="+myRuntime.availableProcessors());}
         Process myProcess;
         myProcess = myRuntime.exec(batCommand);     
         // Get input and  output stream
         InputStream in = myProcess.getInputStream();
         FileOutputStream fo = new FileOutputStream(new File(outputfile));
         byte[] buffer = new byte[4096];
         int c;
         while ((c = in.read(buffer)) != -1) {
            fo.write(buffer,0,c);
        }        
        in.close();
        fo.flush();
        fo.close();
 
         
         if (WAIT){
            returnvalue = myProcess.waitFor();
            int exitVal = myProcess.exitValue();
            if(NodeServer.verboseExternal) {System.out.println("returnvalue: "+returnvalue);}
            if(NodeServer.verboseExternal) {System.out.println("exitVal: "+exitVal);}
            if (returnvalue < 3)  // modif jdk 1.5
               returnString = "<ICON>VALID</ICON> "+successMsg;
            else
               returnString = "<ICON>INVALID</ICON> "+errorProcess;
            if (!returnLink.equals("NULL"))
               returnString += " <a href=\""+returnLink+"\">voir le résultat</a>";
         }
         else {
            if (!returnLink.equals("NULL"))
               returnString += " <a href=\""+returnLink+"\">voir le résultat</a>";
         }
      }
      catch (IOException e) {
         System.out.println("error in ExecuteBat class IOException (doIt method) :\n"+e);
         if (!returnLink.equals("NULL"))
            return "<ICON>INVALID</ICON> "+errorProcess+" <a href=\""+returnLink+"\">voir le résultat</a>";
         else
            return "<ICON>INVALID</ICON> "+errorProcess;
         
      }
      catch (InterruptedException e1) {
         System.out.println("error in ExecuteBat class InterruptedException (doIt method) :\n"+e1);
         return "<ICON>INVALID</ICON> "+errorProcess;
      }
      catch (SecurityException  e2) {
         System.out.println("error in ExecuteBat class SecurityException (doIt method) :\n"+e2);
         return "<ICON>INVALID</ICON> "+errorProcess;
      }
      return returnString;
   }
}
