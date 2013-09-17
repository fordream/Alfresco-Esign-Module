package vn.vfossa.cert;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class VnCertificate {
	private X509Certificate cert;
	private PrivateKey privatekey;
	
	public Certificate(File certFile, String certpass, String prkeypass) throw NonCertFileException{
		KeyStore ks = KeyStore.getInstance("pkcs12");
		ks.load(new FileInputStream(certFile), certpass.toCharArray());
		String alias = ks.aliases().nextElement();
		this.cert = (X509Certificate)ks.getCertificate(alias);
		this.priavatekey = (PrivateKey)ks.getKey(alias, prkeypass.toCharArray())
	}
	
	public X509Certificate getCert(){
		return this.cert;
	}
	public void setCert(X509Certificate cert){
		this.cert = cert;
	}
	public PrivateKey getPrivateKey(){
		return this.privatekey;
	}
	public void setPrivateKey(PrivateKey privatekey){
		this.privatekey = privatekey;
	}
}
