/**
 * InnoG2GWSClassSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.innoetics.services;

public interface InnoG2GWSClassSoap extends java.rmi.Remote {
    public java.lang.String greeklishToGreek(java.lang.String text) throws java.rmi.RemoteException;
    public java.lang.String greekToGreeklish(java.lang.String text) throws java.rmi.RemoteException;
    public java.lang.String greekToGreeklishELOT(java.lang.String text) throws java.rmi.RemoteException;
    public com.innoetics.services.AnalyseGreeklishToGreekResponseAnalyseGreeklishToGreekResult analyseGreeklishToGreek(java.lang.String text) throws java.rmi.RemoteException;
}
