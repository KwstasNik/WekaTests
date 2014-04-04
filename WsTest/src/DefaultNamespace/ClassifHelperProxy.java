package DefaultNamespace;

public class ClassifHelperProxy implements DefaultNamespace.ClassifHelper {
  private String _endpoint = null;
  private DefaultNamespace.ClassifHelper classifHelper = null;
  
  public ClassifHelperProxy() {
    _initClassifHelperProxy();
  }
  
  public ClassifHelperProxy(String endpoint) {
    _endpoint = endpoint;
    _initClassifHelperProxy();
  }
  
  private void _initClassifHelperProxy() {
    try {
      classifHelper = (new DefaultNamespace.ClassifHelperServiceLocator()).getClassifHelper();
      if (classifHelper != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)classifHelper)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)classifHelper)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (classifHelper != null)
      ((javax.xml.rpc.Stub)classifHelper)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public DefaultNamespace.ClassifHelper getClassifHelper() {
    if (classifHelper == null)
      _initClassifHelperProxy();
    return classifHelper;
  }
  
  public int classificationresults(java.lang.String inputarff) throws java.rmi.RemoteException{
    if (classifHelper == null)
      _initClassifHelperProxy();
    return classifHelper.classificationresults(inputarff);
  }
  
  
}