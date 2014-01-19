/**
 * InnoG2GWSClassLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.innoetics.services;

public class InnoG2GWSClassLocator extends org.apache.axis.client.Service implements com.innoetics.services.InnoG2GWSClass {

/**
 * <b>Tnnoetics Greeklish to Greek web service.</b>
 */

    public InnoG2GWSClassLocator() {
    }


    public InnoG2GWSClassLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public InnoG2GWSClassLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for InnoG2GWSClassSoap
    private java.lang.String InnoG2GWSClassSoap_address = "http://services2.innoetics.com/InnoG2GWS/InnoG2GWS.asmx";

    public java.lang.String getInnoG2GWSClassSoapAddress() {
        return InnoG2GWSClassSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String InnoG2GWSClassSoapWSDDServiceName = "InnoG2GWSClassSoap";

    public java.lang.String getInnoG2GWSClassSoapWSDDServiceName() {
        return InnoG2GWSClassSoapWSDDServiceName;
    }

    public void setInnoG2GWSClassSoapWSDDServiceName(java.lang.String name) {
        InnoG2GWSClassSoapWSDDServiceName = name;
    }

    public com.innoetics.services.InnoG2GWSClassSoap getInnoG2GWSClassSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(InnoG2GWSClassSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getInnoG2GWSClassSoap(endpoint);
    }

    public com.innoetics.services.InnoG2GWSClassSoap getInnoG2GWSClassSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.innoetics.services.InnoG2GWSClassSoapStub _stub = new com.innoetics.services.InnoG2GWSClassSoapStub(portAddress, this);
            _stub.setPortName(getInnoG2GWSClassSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setInnoG2GWSClassSoapEndpointAddress(java.lang.String address) {
        InnoG2GWSClassSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.innoetics.services.InnoG2GWSClassSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.innoetics.services.InnoG2GWSClassSoapStub _stub = new com.innoetics.services.InnoG2GWSClassSoapStub(new java.net.URL(InnoG2GWSClassSoap_address), this);
                _stub.setPortName(getInnoG2GWSClassSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("InnoG2GWSClassSoap".equals(inputPortName)) {
            return getInnoG2GWSClassSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services.innoetics.com", "InnoG2GWSClass");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://services.innoetics.com", "InnoG2GWSClassSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("InnoG2GWSClassSoap".equals(portName)) {
            setInnoG2GWSClassSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
