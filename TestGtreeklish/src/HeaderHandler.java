import java.util.*;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.ws.handler.*;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

@Override
public boolean handleMessage(SOAPMessageContext smc) {

    Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

    if (outboundProperty.booleanValue()) {

        try {

            SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
            SOAPHeader header = envelope.addHeader();

            SOAPFactory soapFactory = SOAPFactory.newInstance();
            Name headerName = soapFactory.createName("ServiceAuthHeader", "", "http://www.interhome.com/webservice");
            SOAPHeaderElement headerElement = header.addHeaderElement(headerName);

            Name username = soapFactory.createName("Username");
            SOAPElement usernameElement = headerElement.addChildElement(username);
            usernameElement.addTextNode("GB1009688");

            Name password = soapFactory.createName("Password");
            SOAPElement passwordElement = headerElement.addChildElement(password);
            passwordElement.addTextNode("verbier");


        } catch (Exception e) {
        }

    }
    return outboundProperty;

}

@Override
public Set getHeaders() {
    return null;
}

@Override
public boolean handleFault(SOAPMessageContext context) {
    return true;
}

@Override
public void close(MessageContext context) {
}
}

