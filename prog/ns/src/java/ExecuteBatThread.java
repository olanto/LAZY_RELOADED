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
 * __ExecuteBatThread.doIt["filename.bat","XXXX","YYY","BATCHID","-P myParameter -O myPath/myOutput.txt -R myPath/myRetrunLink.txt"]
 * __ExecuteBatThread.doIt["filename.bat","XXXX","YYY","BATCHID","-O output.txt"]
 * __ExecuteBatThread.doIt["filename.bat","XXXX","YYY","BATCHID","-P <parameters>"]
 * __ExecuteBatThread.doIt["filename.bat","XXXX","YYY","BATCHID"]
 *
 * **********************************/
public class ExecuteBatThread implements Runnable{
   
   boolean bat_termine = false;
   Thread myThread; 
   String batCommand, outputfile, returnLink,projectName, datagrp, batchid;  
   
   
   // default constructor
   public ExecuteBatThread(){}
   
   
   /**********************************
    *
    *  Thread constructor
    */
   public ExecuteBatThread(String _batCommand, String _outputfile, String _projectName, String _returnLink, String _datagrp, String _batchid){
      
      batCommand = _batCommand;
      outputfile = _outputfile;
      projectName = _projectName;
      returnLink = _returnLink;
      datagrp = _datagrp;
      batchid = _batchid;
      
      
      myThread = new Thread(this);
      myThread.start();
      
   }
   
   /*********************************
    *
    *  Run
    */
	public void run() {
   	//variables  locales   	  
   	Process myProcess = null; 
   	int returnvalue = -100;
   	String returnString="exécution terminée", result="";
   	  
   	try{   	  
         Runtime myRuntime = Runtime.getRuntime();         
         myProcess = myRuntime.exec(batCommand);              
         // Get input and  output stream
         InputStream in = myProcess.getInputStream();
			FileOutputStream fo = new FileOutputStream(new File(outputfile));
         byte[] buffer = new byte[4096];
         int c;
         while ((c = in.read(buffer)) != -1) {
            fo.write(buffer,0,c);
            fo.flush();
        }
        in.close();        
        fo.close();         
         
      	QueryResult Q = DBServices.execSQLonDB("update paramrisk set str = 'FINISHED' where datagrp = '"+datagrp+"' and code = '"+batchid+"'",Node.getConId(projectName+"."), true);         
   	}
      catch (IOException e) {
         System.out.println("error in ExecuteBat class (doIt method) :\n"+e);
         if (myProcess != null)myProcess.destroy();
         if (!returnLink.equals("NULL"))
            result = "<ICON>INVALID</ICON>("+returnvalue+") <a href=\""+returnLink+"\">voir le résultat</a>";
         else
            result = "<ICON>INVALID</ICON>("+returnvalue+")";
         
      }      
      if (myProcess != null)myProcess.destroy();
      result = returnString;
   }
   
   
   /*******
    *
    * DO IT
    *
    */    
   public  String doIt(Object params) {
      String  batParamters="", outputfile="c:/temp/defaultLog.txt", returnLink="NULL", projectName="NO_PROJECT_NAME", datagrp="NO_DATAGRP", batchid="BATCH-1";
      String[] u=(String [])params;
      String batCommand = u[0];       
      datagrp = u[1];       
      projectName = u[2];       
      batchid = u[3];       
      
      
      if (u.length == 5){
         String param;
         int ixx=0,ix=u[4].indexOf('-');
         while (ix > -1){
            ixx = u[4].indexOf("-",ix+1);
            if (ixx>0)
               param = u[4].substring(ix+1,ixx);    //for all parameters and remove "-" char
            else
               param = u[4].substring(ix+1);    //for last parmaeter and remove "-" char
            if (param.startsWith("P"))
               batCommand += " "+param.substring(2);   // -P for command line's parameters           
            if (param.startsWith("O")){
               outputfile = param.substring(2);        //  -O for standard output file
            }
            if (param.startsWith("R"))
               returnLink = param.substring(2);        //  -R for return link
            ix = ixx;
         }
      }
      if(NodeServer.verboseExternal) System.out.println("batCommand: "+batCommand+
      "\noutputfile: "+outputfile+
      "\nprojectName: "+projectName+
      "\ndatagrp: "+datagrp+
      "\nbatchid: "+batchid+
      "\nreturnLink: "+returnLink+
      "\nparams: "+u[4]);
            
      ExecuteBatThread execBat = new ExecuteBatThread(batCommand, outputfile, projectName, returnLink, datagrp, batchid);
      DBServices.execSQLonDB("update paramrisk set str = 'PROCESSING', dte = sysdate where datagrp = '"+datagrp+"' and code = '"+batchid+"'",Node.getConId(projectName+"."), true);         
      return "<ICON>check</ICON>&#160;&#160;<B>Calcul du "+batchid+" Lancé!!</B>";
   }
}