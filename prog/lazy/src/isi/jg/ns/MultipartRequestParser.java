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
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * MultipartRequestParser reads a multipart/form-data HTTP
 * request, writes any files to disk, and stores all web
 * variables in a hashmap.
 *
 *
 */
public class MultipartRequestParser {
    private static final int BUFFER_SIZE = 8 * 1024; //8K buffer
    
    private ServletRequest request;
    private ServletInputStream in;
    private MultipartStreamReader msr;
    private String saveDirectory = "";
    private String boundary;
    private String fileSeparator;
    private HashMap webVars;
    
    
    /**
     * Constructor that initializes all instance variables.
     *
     * @param request ServletRequest
     * @param saveDirectory Directory to which to save files
     * @exception IOException
     */
    public MultipartRequestParser(ServletRequest request,
            String saveDirectory) throws IOException {
        this.request = request;
        this.saveDirectory = saveDirectory;
        in = request.getInputStream();
        msr = new MultipartStreamReader();
        fileSeparator = System.getProperty("file.separator");
        
        //instantiate hashmap to store web variables
        webVars = new HashMap();
    }
    
    
    /**
     * Returns the web variables hashmap.
     *
     * @return HashMap containing all of the web variables.
     */
    public HashMap getWebVars() {
        return webVars;
    }
    
    
    /**
     * Gets the boundary marker, moves past the first boundary,
     * and calls processParts() to process each part of the
     * request.
     *
     * @exception Exception
     */
    public void parseRequest() throws Exception {
        String contentType = request.getContentType();
        
        //make sure the request is multipart/form-data
        if (!contentType.startsWith("multipart/form-data")) {
            throw new Exception("<IMG SRC=\"/icon/error.gif\" >&nbsp;&nbsp;&nbsp;<b>Request not multipart/form-data.</b>");
        }
        
        //get the boundary marker
        boundary = getBoundary(contentType);
        
        findNextBoundary(); //move past first boundary
        
        processParts(); //process each part of the multipart request
    }
    
    
    /**
     * Processes each part of the multipart/form-data request.
     *
     * @exception Exception
     */
    private void processParts() throws Exception {
        if (NodeServer.verboseUpload)  System.out.println("processParts()");
        
        //get the web variable name and filename from the request
        ContentDisposition disposition = getDisposition();
        
        //loop through all of the parts of the request
        while (!disposition.getName().equals("")) {
            if (NodeServer.verboseUpload) System.out.println("disposition.getName()="+disposition.getName());
            if (NodeServer.verboseUpload) System.out.println("disposition.getFilename()="+disposition.getFilename());
            if (disposition.getFilename().equals("")) {
                //if filename is empty, this part must be a web variable
                storeVariable(disposition.getName());
                if (disposition.getName().equals("Filename")) {throw new Exception("<IMG SRC=\"/icon/warning.gif\" >&nbsp;&nbsp;&nbsp;<b>Sélectionnez préalablement un fichier !</b>");}        
            } else {
                //filename exists so this part is a file being uploaded
                storeFile(disposition);
            }
            
            //get name of next web variable and filename
            disposition = getDisposition();
        }
    }
    
    
    /**
     * Reads the name and value of the web variable and places it
     * in the web variable hashmap.
     *
     * @param name String containing the name of the variable to
     *  store
     * @exception IOException
     */
    private void storeVariable(String name) throws IOException {
        findEmptyLine(); //move past empty line
        
        webVars.put(name, msr.readLine()); //add variable to hashmap
        
        findNextBoundary(); //move past next boundary
    }
    
    
    /**
     * Reads a file from the multipart/form-data request and writes
     * it to the file system.
     *
     * @param disposition ContentDisposition object containing the
     *  name of the web variable and filename
     */
    private void storeFile(ContentDisposition disposition) throws
            IOException, SecurityException {
        FileOutputStream fileOut = null;
               
            //create file object and output stream for writing file
            String filename = disposition.getFilename();
            saveDirectory = (String)this.webVars.get("destDir");
            File file = new File(saveDirectory );
            
            if (!file.exists()) {               
               Boolean ok = file.mkdirs();
                if (NodeServer.verboseUpload) System.out.println("directory "+saveDirectory +" created="+ok);
               if (!ok) {throw new IOException("<IMG SRC=\"/icon/error.gif\" >&nbsp;&nbsp;&nbsp;<b>création de fichier impossible !!</b>");}
            }
               
            fileOut = new FileOutputStream(file+fileSeparator+filename);           
            
            findEmptyLine(); //move past empty line
            
            int bytesRead = 0;
            
            boolean rnAddFlag = false;
            
            byte[] bytes = new byte[BUFFER_SIZE];
            
            //read entire upload file from request in 8K chunks
            while ((bytesRead = in.readLine(bytes, 0, BUFFER_SIZE))
            != -1) {
                //pre-check for boundary marker
                if (bytes[0] == '-' && bytes[1] == '-' && bytes[2] == '-'
                        && bytesRead < 500) {
                    //this may be boundary marker, get string to make sure
                    String line = new String(bytes, 0, bytesRead,
                            "ISO8859_1");
                    
                    if (line.startsWith(boundary)) {
                        //we're at the boundary marker so we're done reading
                        break;
                    }
                }
                
                //we read a \r\n from the previous iteration that needs
                //to be written to the file
                if (rnAddFlag) {
                    fileOut.write('\r');
                    fileOut.write('\n');
                    rnAddFlag = false;
                }
                
                //since ServletInputStream adds its own \r\n to the last
                //line read, don't write it to the file until we're sure
                //that this is not the last line
                if (bytesRead > 2 && bytes[bytesRead-2] == '\r' &&
                        bytes[bytesRead-1] == '\n') {
                    bytesRead = bytesRead - 2;
                    rnAddFlag = true;
                }
                
                fileOut.write(bytes, 0, bytesRead); //write data to file
            }      
            fileOut.close();
       
    }
    
    
    /**
     * Returns the boundary marker from the Content-Type header.
     *
     * @param contentType The value passed in the Content-Type
     *  header
     * @return String containing the boundary marker
     */
    private String getBoundary(String contentType) {
        String boundaryMarker = "boundary=";
        
        int boundaryIndex = contentType.indexOf(boundaryMarker) +
                boundaryMarker.length();
        
        //the boundary in the Content-Type lacks a leading "--"
        boundaryMarker = "--" + contentType.substring(boundaryIndex);
        
        return boundaryMarker;
    }
    
    
    /**
     * Moves past the next boundary marker.
     *
     * @exception IOException
     */
    private void findNextBoundary() throws IOException {
        String line = msr.readLine();
        
        //keep reading until the end of  stream or boundary marker
        while (line != null && !line.startsWith(boundary)) {
            line = msr.readLine();
        }
    }
    
    
    /**
     * Reads the name of web variable and filename from the
     * Content-Disposition header and stores it in a new
     * ContentDisposition object.
     *
     * @return ContentDisposition object with name of web variable
     *  and filename
     * @exception Exception
     */
    private ContentDisposition getDisposition() throws Exception {
        //read first line of this part of the multipart request
        String line = msr.readLine();
        
        //find the Content-Disposition header
        while (line != null && !line.equals("") &&
                !line.toLowerCase().startsWith("content-disposition:")) {
            line = msr.readLine();
        }
        
        ContentDisposition disposition = new ContentDisposition();
        
        if (line == null || !line.toLowerCase().startsWith(
                "content-disposition:")) {
            //we're at the end of the stream or the part doesn't
            //contain a Content-Disposition header, return empty object
            return disposition;
        }
        
        //get name of web variable and filename
        String name = getDispositionName(line);
        
        disposition.setName(name);
        
        //get filename from Content-Disposition if it exists
        String filename = getDispositionFilename(line);
        
        disposition.setFilename(filename);
        
        return disposition;
    }
    
    
    /**
     * Returns the web variable name from the Content-Disposition
     * header.
     *
     * @param line String containing the Content-Disposition header
     * @return String containing the name of the web variable
     */
    private String getDispositionName(String line) {
        String search = "name=\"";
        
        int nameIndex = line.toLowerCase().indexOf(search);
        
        if (nameIndex == -1) {
            return "";
        }
        
        nameIndex += search.length();
        
        String dispositionName = line.substring(nameIndex,
                line.indexOf("\"", nameIndex+1));
        
        return dispositionName;
    }
    
    
    /**
     * Returns the filename from the Content-Disposition header.
     *
     * @param line String containing the Content-Disposition header
     * @return String containing the filename if it exists
     */
    private String getDispositionFilename(String line) {
        String search = "filename=\"";
        
        int filenameIndex = line.toLowerCase().indexOf(search);
        
        if (filenameIndex == -1) {
            return "";
        }
        
        filenameIndex += search.length();
        
        String filename = line.substring(filenameIndex,
                line.indexOf("\"", filenameIndex));
        
        filename = filename.substring(
                filename.lastIndexOf(fileSeparator)+1);
        
        return filename;
    }
    
    
    /**
     * Moves past the next empty line in the input stream.
     *
     * @exception IOException
     */
    private void findEmptyLine() throws IOException {
        String line = msr.readLine();
        
        //loop until end of stream or empty line
        while (line != null && !line.equals("")) {
            line = msr.readLine();
        }
    }
    
    
    /**
     * MultipartStreamReader is an inner class that contains a
     * single readLine() method. This method returns a single line
     * from the request input stream.
     */
    class MultipartStreamReader {
        /**
         * Reads a single line (up to 1K in length) from the input
         * stream. The line is limited to 1K in length since this
         * method is for reading HTTP headers and web variable
         * values, not files.
         *
         * @return String containing a single line from the input
         *  stream
         * @exception IOException
         */
        public String readLine() throws IOException {
            String line = "";
            
            byte[] bytes = new byte[1024]; //1K buffer
            
            int bytesRead = in.readLine(bytes, 0, BUFFER_SIZE);
            
            if (bytesRead == -1) {
                return null; //no line to read so return null
            } else {
                //convert byte array to a string
                line = new String(bytes, 0, bytesRead, "ISO8859_1");
            }
            
            if (line.endsWith("\r\n")) {
                //remove the trailing \r\n
                line = line.substring(0, line.length()-2);
            }
            
            return line;
        }
    }
    
    
    /**
     * ContentDisposition is an inner class that encapsulates the
     * properties of the Content-Disposition HTTP header.
     * Specifically, this class stores the name of the web variable
     * and the filename.
     */
    class ContentDisposition {
        private String name = "";
        private String filename = "";
        
        
        /**
         * Returns the name of the web variable extracted from the
         * Content-Disposition header.
         *
         * @return Name of web variable
         */
        String getName() {
            return name;
        }
        
        
        /**
         * Sets the name of the web variable.
         *
         * @param name String containing the name of the web
         *  variable
         */
        void setName(String name) {
            this.name = name;
        }
        
        
        /**
         * Returns the filename extracted from the Content-
         * Disposition header.
         *
         * @return String containing the filename
         */
        String getFilename() {
            return filename;
        }
        
        
        /**
         * Sets the filename value.
         *
         * @param filename String containing the name of the file
         */
        void setFilename(String filename) {
            this.filename = filename;
        }
    }
}
