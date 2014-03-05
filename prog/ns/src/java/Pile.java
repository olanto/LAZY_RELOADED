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

public class Pile{
   
   static Hashtable sessionPile=new Hashtable();
   static  Vector ciruclarBuffer;
   static final int MAX_HISTORY = 20;
   public static boolean verboseShow = false;
   
   
   public Pile(){
      /*************************************
       *
       * usage in Lazy :      
       * __Pile.put["[[SESSION]]","PROJECT.NODENAME", param1, param2, param3,...]       
       * __Pile.get["[[SESSION]]","PROJECT.DEFAULT_NODENAME", param1, param2, param3,...] 
       * __Pile.removeAll["[[SESSION]]"]       
       *
       * **********************************/      
   }
   
   /***********
    * PUT
    **********/
   public static String put(Object o){
            
      String[] u=(String [])o;      
      String[] params = new String[u.length-1];
      String result ="";
      
      System.arraycopy(u,1,params,0,params.length);
      String session = u[0];      
      if (!sessionPile.containsKey(session)) {
         ciruclarBuffer = new Vector(MAX_HISTORY,0);
         addNode(session,ciruclarBuffer,params);
      }
      else  {
         addNode(session,(Vector)sessionPile.get(session),params);
      }      
      if (verboseShow) {  result = "put : session:"+session+" , "; for (int z=0;z<params.length;z++) result += params[z]+", "; }
      return result;
   }
   
   
   /************
    * ADD NODE
    ************/
   public static void addNode(String session, Vector ciruclarBuffer, String[] u){
      if (ciruclarBuffer.size()==MAX_HISTORY)
         ciruclarBuffer.remove(MAX_HISTORY-1);
      ciruclarBuffer.insertElementAt(u,0);
      sessionPile.remove(session);
      sessionPile.put(session,ciruclarBuffer);      
   }
   
   
   /**********
    * GET
    *********/
   public static String get(Object o){
      String[] u=(String [])o;        
      String[] params = new String[u.length-2];   //u[0]=session and u[1]defaultNode       
      System.arraycopy(u,2,params,0,params.length); //set a node parameter array for the default node 
       
            
     // String selectId[] = {u[2]};
            
      String res = "Error in Pile.get !!";            
      if (!sessionPile.containsKey(u[0])){         
         res = "<h3>Pas de pages historisées pour la session "+u[0]+" !</h3>"+ReturnExternalService.forgeInclude(u[1],params);         
      }
      if (((Vector)sessionPile.get(u[0])).size()==1){                                                  	
         res = "<h3>Plus de pages historisées !</h3>"+ReturnExternalService.forgeInclude(u[1],params);         
      }
      else {
      	((Vector)sessionPile.get(u[0])).remove(0);    // remove first initial page
      	String[] stackParams = (String[])(((Vector)sessionPile.get(u[0])).get(0));  //get stored node parameters
      	String[] nodeparams = new String[stackParams.length-1];   // define a node parameters array;
         System.arraycopy(stackParams,1,nodeparams,0,nodeparams.length);  // fill the node parameters array whitout the return node (stackParams[0])
       	res = ReturnExternalService.forgeInclude(stackParams[0],nodeparams);    
      }            
      return res;
   }
   
   /**********
    * REMOVE ALL
    *********/
   public static String removeAll(Object o){
      String[] u=(String [])o;      
      String result = "";
      if (sessionPile.containsKey(u[0])){       
         ((Vector)sessionPile.get(u[0])).removeAllElements();
      }
      return result;
   }
}