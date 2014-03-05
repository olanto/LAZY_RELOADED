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
import org.apache.xmlrpc.XmlRpcServer;
import org.apache.xmlrpc.XmlRpcRequestParam;
import org.apache.xmlrpc.XmlRpcRequest;

public class RPC {
    
    static void  execute(NodeServer ns,HttpServletRequest request, HttpServletResponse response, XmlRpcServer xmlrpc)
    throws IOException, ServletException{
        
        InputStream is=request.getInputStream();
        XmlRpcRequestParam rp=new XmlRpcRequestParam();
        XmlRpcRequest rq=rp.processRequest(is);
        String p1=(String)rq.getParameter(0);
        String p2=(String)rq.getParameter(1);
        String p3=(String)rq.getParameter(2);
        String p4=(String)rq.getParameter(3);
        String p5=(String)rq.getParameter(4);
        String p6=(String)rq.getParameter(5);
        String p7=(String)rq.getParameter(6);
        String p8=(String)rq.getParameter(7);
        String p9=(String)rq.getParameter(8);
        String nodeid=rq.getMethodName();
//        System.out.println("getMethod:"+nodeid);
//        System.out.println("getParam:"+p1);
//        System.out.println("getParam:"+p2);
//        System.out.println("getParam:"+p3);
//        System.out.println("getParam:"+p4);
//        System.out.println("getParam:"+p5);
//        System.out.println("getParam:"+p6);
//        System.out.println("getParam:"+p7);
//        System.out.println("getParam:"+p8);
//        System.out.println("getParam:"+p9);
        
        String[] params=new String[11];
        params[0]=Node.getProjectName(nodeid);
        params[1]=Node.getNodeName(nodeid);
        params[2]=p1;
        params[3]=p2;
        params[4]=p3;
        params[5]=p4;
        params[6]=p5;
        params[7]=p6;
        params[8]=p7;
        params[9]=p8;
        params[10]=p9;
        String result="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><methodResponse><params><param>"+
        ns.dynamicNode(params,request,request.getSession(),response)+
        // "<value>hello</value>"+ // only for test
        "</param></params></methodResponse>";
        response.setContentType("text/xml");
        response.setContentLength(result.length());
        OutputStream output = response.getOutputStream();
        output.write(result.getBytes());
        output.flush();
//        System.out.println(new String(result));
    }
    
    
    
    
}