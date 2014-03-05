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

public class FILE {
    
    static void  execute(NodeServer ns,HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{
        String cmd="";
        String project="";
        String path="";
        String file="";
        String ext="";
        
        HttpSession session = request.getSession();
        String userId=(String)session.getAttribute("USER");
        String grpId=(String)session.getAttribute("GRP");
        
        if (request.getParameterValues("project") != null) {
            project = request.getParameterValues("project")[0];
        }
        if (request.getParameterValues("path") != null) {
            path = request.getParameterValues("path")[0];
        }
      if (SecureLogin.checkAccessPath(project+"."+path,grpId,userId,"REALM", session)) {  // autorisé sur ce royaume
            
            if (request.getParameterValues("cmd") != null) {
                cmd = request.getParameterValues("cmd")[0];
            }
            if (request.getParameterValues("project") != null) {
                project = request.getParameterValues("project")[0];
            }
            if (request.getParameterValues("file") != null) {
                file = request.getParameterValues("file")[0]; 
            }
            if (request.getParameterValues("ext") != null) {
                ext = request.getParameterValues("ext")[0];
            }
            String fileName=Node.getROOTFileName(project)+path+file+"."+ext;
            
            byte result[];
            result=getRowByte(fileName);
            //result=getRowByte("U:/Utilisateurs/Richard Zbinden/PUBLIC/Descriptif.pdf");
            if (ext.equalsIgnoreCase("html")) response.setContentType("text/html"); //; charset=utf-8");
            if (ext.equalsIgnoreCase("pdf")) response.setContentType("application/pdf"); //; charset=utf-8");
            if (ext.equalsIgnoreCase("xls")) response.setContentType("application/vnd.ms-excel"); //; charset=utf-8");
            if (ext.equalsIgnoreCase("doc")) response.setContentType("application/msword");            
            if (ext.equalsIgnoreCase("ppt") || ext.equalsIgnoreCase("pps")) response.setContentType("application/vnd.ms-powerpoint");            
            if (ext.equalsIgnoreCase("zip")) response.setContentType("application/zip");            
                                  
            response.setContentLength(result.length);
            OutputStream output = response.getOutputStream();
            output.write(result);
            output.flush();
        } else {
            String resultfalse="<p> Not authorised to see this<hr>"+
            "<p> cmd: "+cmd+
            "<p> project: "+project+
            "<p> path: "+path+
            "<p> file: "+file+
            "<p> ext: "+ext+"<hr>";
            response.setContentType("text/html; charset=utf-8");
            response.setContentLength(resultfalse.length());
            OutputStream output = response.getOutputStream();
            output.write(resultfalse.getBytes());
            output.flush();
        }
    }
    
    
    public static byte[] getRowByte(String fname) throws IOException {
        File file= new File(fname);
        System.out.println("file "+fname);
        InputStream is = new FileInputStream(file);
        
        long length = file.length();
        
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
        
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
        && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        
        // Close the input stream and return bytes
        is.close();
        return bytes;
                
    }
}