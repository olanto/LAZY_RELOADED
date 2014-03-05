package isi.jg.ns;

//import javax.servlet.ServletOutputStream;
//import oracle.apps.xdo.XDOException;
//import oracle.apps.xdo.common.pdf.util.PDFDocMerger;
//import oracle.apps.xdo.dataengine.DataProcessor;
//import oracle.apps.xdo.template.*;
//import javax.servlet.http.*;
//import java.io.*;
//import java.net.*;

/*************************************
 *
 * usage in Lazy :
 * "<a href=\"xmlReport?rtf=",completeRtfFileFame,"/&amp;node=",PROJECT.nodeName,"/&amp;u=",nodeParam1,"/&amp;u=",nodeParam12,...,"/&amp;ei=.pdf"
 *usage in URL:
 * http://lzy1:1880/lazy/xmlReport?rtf=c:/temp/xx.rtf&node=PROJECT.nodeName&u=nodeParam1&u="nodeParam12&ei=.pdf
 *
 * **********************************/

public class XMLGetReport {
    
//    HttpServletResponse response;
//    String user="", grpId="", node="", rtf="";
//    
//    void XMLGetReport(){}
//    
//    
//    public  void genreate(NodeServer ns, HttpServletRequest request,HttpServletResponse response, HttpSession session) {
//        this.response = response;       
//        grpId = (String)session.getAttribute("GRP");
//        user = (String)session.getAttribute("USER");
//        String[] u=null;        
//        byte result[]= null;
//        try{
//            if (request.getParameterValues("rtf") != null) {
//                rtf = request.getParameterValues("rtf")[0];}
//            if (request.getParameterValues("node") != null) {
//                node = request.getParameterValues("node")[0];}
//            if (request.getParameterValues("u") != null) {
//                u = request.getParameterValues("u");}
//            if (SecureLogin.checkAccess(node,grpId,user,"LAZY", session)){  // check premier noeud du rapport                
//                String xmlNode = ns.query(node,u,0,request,session,response);                
//                result = rtfProcessorStream(xmlNode,session,rtf);
//                if (result == null)System.out.println("result = NULL");
//                response.setContentType("application/pdf");
//                response.setContentLength(result.length);
//                OutputStream output = response.getOutputStream();
//                output.write(result);
//                output.flush();
//            } else{ returnErrMsg("<H1>NO PRIVILEGES ON REPORT NODE (" + node+ ")</H1><hr>");}
//            
//        } catch (Exception e) {
//            String tmp="";            
//            if (u!=null) for (int i=0;i<u.length;i++)tmp+="["+i+"]"+u[i]+" ";                            
//            returnErrMsg("<P>Génération du rapport impossible dans le contexte suivant:<br>- fichier :\"" +rtf+"\""+
//                        "<br>- Utilisateur : "+user+
//                        "<br>- Groupe : "+grpId+
//                        "<br>- Noeud du rapport : "+node+" "+tmp+"<hr></P>");                        
//            
//        }
//    }
//    
//    void returnErrMsg(String msg){
//        try{
//        response.setContentType("text/html");
//                response.setContentLength(msg.length());
//                OutputStream output = response.getOutputStream();
//                output.write(msg.getBytes());
//                output.flush();
//        } catch (Exception e) {}
//    }
//    
//    byte[] rtfProcessorStream(String xmlNode, HttpSession session, String rtf) {
//        PipedOutputStream pOut = null;
//        PipedInputStream pIn = null;
//        ByteArrayOutputStream pdfInBbytes = new ByteArrayOutputStream();
//        String res="";
//        try {
//            pOut = new PipedOutputStream();
//            pIn = new PipedInputStream(pOut);
//            new RTFtoXSLThread(pOut,rtf).start();
//            FOProcessor foprocessor = new FOProcessor();
//            foprocessor.setData(new StringReader(xmlNode));
//            foprocessor.setTemplate(pIn);
//            foprocessor.setOutput(pdfInBbytes);
//            foprocessor.setOutputFormat(FOProcessor.FORMAT_PDF);
//            foprocessor.generate();
//            
//        } catch (XDOException e) {            
//            System.out.println("XDOException in XMLGetReport.rtfProcessorStream :" + e.getMessage());
//            return null;
//        } catch (IOException e) {
//            System.out.println("IOException in XMLGetReport.rtfProcessorStream :" + e.getMessage());
//            return null;
//        }
//        return pdfInBbytes.toByteArray();
//    }
//    
//    
//    class RTFtoXSLThread extends Thread{
//        PipedOutputStream pOut;
//        RTFProcessor processor ;
//        String rtf;
//        
//        public RTFtoXSLThread(PipedOutputStream pOut, String rtf){
//            this.pOut= pOut;
//            this.rtf=rtf;
//        }
//        
//        public void run(){
//            try{                               
//                processor = new RTFProcessor(rtf);                          
//                processor.setOutput(pOut);
//                processor.process();
//            } catch (XDOException e) {
//                System.out.println("XDOException in RTFtoXSLThread.run :" + e.getMessage());
//                e.printStackTrace();
//                returnErrMsg(e.getMessage());
//            } catch (IOException e) {
//                System.out.println("IOException in RTFtoXSLThread.run :" + e.getMessage());
//                e.printStackTrace();                
//                returnErrMsg(e.getMessage()+e.getLocalizedMessage());
//            }
//        }
//    }
    
}
