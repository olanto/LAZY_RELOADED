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
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 * Upload servlet works in conjunction with a browser's form-
 * based file upload capability to allow the client to transfer
 * a file (binary or ASCII) to the server which is then stored
 * in the server's file system. This servlet uses the
 * MultipartRequestParser class to parse the upload request and
 * store the file.
 *
 *
 */
public class Upload //extends HttpServlet
{
    
    //default maximum allowable file size is 15MB
    private static final int MAX_SIZE = 15 * 1024 * 1024;
    private static String fileSeparator;
    private static String saveDirectory;
    
   
    /**
     * Handles all HTTP GET requests. The upload servlet returns a
     * generic upload HTML form in response to all GET requests.
     * Upon success, this form indicates that the servlet should
     * redirect to a file called "/success.html" located at the web
     * server's root. Though this form may be used, in most cases a
     * custom upload form should be created that posts to this
     * servlet.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @exception ServletException
     * @exception IOException
     */
    public static void askForFile(HttpServletRequest request,
            HttpServletResponse response,  String[] params)  {
        
        fileSeparator = System.getProperty("file.separator");
        saveDirectory = NodeServer.folderUpload;
        if (params[0] != null) saveDirectory += fileSeparator+params[0];
        if (NodeServer.verboseUpload) System.out.println("saveDirectory="+saveDirectory);
        String ul = "&u=";
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (i > 0)
                    ul += "&u=";
                ul += params[i];
            }
        }
        if (NodeServer.verboseUpload) System.out.println("node=UTIL.execUpload   params="+ul);
        response.setContentType("text/html");
        PrintWriter out;
        try {
            out = new PrintWriter(response.getWriter());
            getCommonHtml(out, "") ;
            
            
        } catch (IOException ex) { ex.printStackTrace(); }
        
    }
    
    
    /**
     * Process all HTTP POST requests. This method makes sure that
     * the POSTed request is of type multpart/form-data and that
     * the uploaded file is not too large. It then instantiates the
     * MultipartRequestParser to parse the request and write the
     * file to the file system. It then either displays a success
     * message or redirects the user to the success HTML page
     * submitted by the upload HTML form.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @exception ServletException
     * @exception IOException
     */
    public static void  doit(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,    IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String statusMsg="<IMG SRC=\"/icon/error.gif\" >&nbsp;&nbsp;&nbsp;";
        try {
            int contentLength = request.getContentLength();
            //make sure request is not larger than max upload size
            if (contentLength > MAX_SIZE) {
                statusMsg = "<IMG SRC=\"/icon/error.gif\" >&nbsp;&nbsp;&nbsp;<b>Taille du fichier trop élevé (max = 15Mo)!</b>";
            }else {
                //instantiate class to parse multipart/form-data request
                MultipartRequestParser mrp = new MultipartRequestParser(request, "userDirectory");
                mrp.parseRequest(); //parse the request
                if (NodeServer.verboseUpload) System.out.println("parseRequest() -- OK");
                statusMsg="<IMG SRC=\"/icon/check.gif\" >&nbsp;&nbsp;&nbsp;<b>Fichier chargé avec succès !</b>";
            }
            getCommonHtml(out, statusMsg);                
        } catch (Exception e) {
            System.out.println("UPLOAD ERROR:\nsaveDirectory="+saveDirectory+"\nMessage="+e.getMessage());
            getCommonHtml(out, e.getMessage());
        }
    }
    
    
    /**
     * Returns information about this servlet to the server.
     *
     * @return Brief description of this servlet
     */
    public String getServletInfo() {
        return "UploadServlet allows the client to upload files.";
    }
    
    static void getCommonHtml(PrintWriter out, String statusMsg){
        out.println("<HTML>");
        out.println("<HEAD><TITLE>File Upload (v 1.0)</TITLE></HEAD>");
        out.println("<BODY>");
        out.println("<TABLE BORDER=0 width=100%><TR>");
        out.println("<TD ALIGN=LEFT>"+statusMsg+"</TD>");
        out.println("<TD ALIGN=RIGHT><a href=\"javascript:window.close()\"><IMG SRC=\"/icon/fermer.gif\" ALT=\"fermer\" ALIGN=RIGHT BORDER=0></a></TD>");
        out.println("</TR></TABLE>");
        out.println("<H2>Chargement de fichier... (upload)</H2>");
        
        //the encoding type is multipart/form-data for file uploads
        out.println("<FORM ENCTYPE=\"multipart/form-data\" ");
        out.println("METHOD=\"POST\">");
        //these lines indicate where to upload the file
        out.println("<INPUT TYPE=\"HIDDEN\" NAME=\"destDir\" ");
        out.println("VALUE=\""+saveDirectory+"\">");
        //these lines add the file box and browse button to the form
        out.println("<B>Fichier:</B> <INPUT TYPE=\"FILE\" ");
        out.println("NAME=\"Filename\" SIZE=\"40\" ");
        out.println("MAXLENGTH=\"255\"><P>");
        out.println("<INPUT TYPE=\"SUBMIT\" VALUE=\"Charger\">");
        out.println("</FORM></BODY></HTML>");
        out.close();
    }
}
