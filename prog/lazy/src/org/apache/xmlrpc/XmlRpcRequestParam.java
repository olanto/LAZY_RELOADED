package org.apache.xmlrpc;


import java.io.InputStream;
import java.util.Vector;

/**
 * Process an InputStream and produce and XmlRpcRequest.  This class is only to publish method
 *
 * @author Jacques.Guyot
 */
public class XmlRpcRequestParam extends XmlRpcRequestProcessor
{
    public XmlRpcRequest processRequest(InputStream is)
    { return super.processRequest(is);
    }


}
