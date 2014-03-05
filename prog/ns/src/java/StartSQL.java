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
import java.lang.reflect.*;

public class StartSQL {
   
   /** Creates a new instance of StartSQL */
   public StartSQL() {
   }
   
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {     
        //callExternalService("ExecuteBat","doIt",params);
      String[] params = {"\"D:/Tomcat 5.0/webapps/ROOT/batFiles/PFR/do_analysis_XXXX.bat\""," -W -O D:/Tomcat 5.0/webapps/ROOT/batFiles/YYYY/log/PROMPT_ANALYSIS.txt -R http://lzypat1:1880/batFiles/YYYY/log"};
      callExternalService("ExecuteBatThread","doIt",params);
      
   }
   
   
   public static String callExternalService(String className, String method,Object params) {
      String[] temp = (String[])params;
      System.out.println("callExternalService: "+className+"."+method+"["+temp[0]+"]");
      String result = null;
      try {
         Class c = Class.forName(className);
         Class[] parameterTypes = new Class[] {Object.class};
         Method callMethod; 
         Object obj = c.newInstance();
         Object[] arguments = new Object[] {params};         
         callMethod = c.getMethod(method, parameterTypes);         
         result = (String) callMethod.invoke(obj /*null*/, arguments);     
      } catch (NoSuchMethodException e) {
         System.out.println(e);
      } catch (IllegalAccessException e) {
         System.out.println(e);
      } catch (InvocationTargetException e) {
         System.out.println(e);
      } catch (ClassNotFoundException e) {
         System.out.println(e);      
      } catch (InstantiationException e) {
         System.out.println(e);
      }
      return result;
   }
}
