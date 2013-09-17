package vn.vfossa.signature;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.xml.crypto.dsig.XMLSignature;


public interface SignContent<T> {

	public T getContentObject();
	
	public XMLSignature[] getSignatures();
	
	public boolean isSignedBy(X509Certificate certificate);
	
	public void addSignature(X509Certificate certificate, PrivateKey key) throws Exception;
	
	public boolean validateSignatures();
	
	public String getPathSigned();
}