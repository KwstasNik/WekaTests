package com.innoetics.services;

public class InnoG2GWSClassSoapProxy implements com.innoetics.services.InnoG2GWSClassSoap {
  private String _endpoint = null;
  private com.innoetics.services.InnoG2GWSClassSoap innoG2GWSClassSoap = null;
  
  public InnoG2GWSClassSoapProxy() {
    _initInnoG2GWSClassSoapProxy();
  }
  
  public InnoG2GWSClassSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initInnoG2GWSClassSoapProxy();
  }
  
  private void _initInnoG2GWSClassSoapProxy() {
    try {
      innoG2GWSClassSoap = (new com.innoetics.services.InnoG2GWSClassLocator()).getInnoG2GWSClassSoap();
      if (innoG2GWSClassSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)innoG2GWSClassSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)innoG2GWSClassSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (innoG2GWSClassSoap != null)
      ((javax.xml.rpc.Stub)innoG2GWSClassSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.innoetics.services.InnoG2GWSClassSoap getInnoG2GWSClassSoap() {
    if (innoG2GWSClassSoap == null)
      _initInnoG2GWSClassSoapProxy();
    return innoG2GWSClassSoap;
  }
  
  public java.lang.String greeklishToGreek(java.lang.String text) throws java.rmi.RemoteException{
    if (innoG2GWSClassSoap == null)
      _initInnoG2GWSClassSoapProxy();
    return innoG2GWSClassSoap.greeklishToGreek(text);
  }
  
  public java.lang.String greekToGreeklish(java.lang.String text) throws java.rmi.RemoteException{
    if (innoG2GWSClassSoap == null)
      _initInnoG2GWSClassSoapProxy();
    return innoG2GWSClassSoap.greekToGreeklish(text);
  }
  
  public java.lang.String greekToGreeklishELOT(java.lang.String text) throws java.rmi.RemoteException{
    if (innoG2GWSClassSoap == null)
      _initInnoG2GWSClassSoapProxy();
    return innoG2GWSClassSoap.greekToGreeklishELOT(text);
  }
  
  public com.innoetics.services.AnalyseGreeklishToGreekResponseAnalyseGreeklishToGreekResult analyseGreeklishToGreek(java.lang.String text) throws java.rmi.RemoteException{
    if (innoG2GWSClassSoap == null)
      _initInnoG2GWSClassSoapProxy();
    return innoG2GWSClassSoap.analyseGreeklishToGreek(text);
  }
  
  
}