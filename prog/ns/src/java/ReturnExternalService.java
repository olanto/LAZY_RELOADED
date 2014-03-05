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


public class ReturnExternalService{

	public ReturnExternalService(){}
   
   private static final String includePrefix = "<<??include a=";
   private static final String includeSuffix = "//??>>";
   
   public static String forgeInclude(String nodeName, String[] params){
      String res = includePrefix+nodeName, t="";          
      for (int i=0;i<params.length;i++){
         res += "&amp;u="+params[i];
         t +=", "+params[i];
      }      
      return res+includeSuffix;
   }


}