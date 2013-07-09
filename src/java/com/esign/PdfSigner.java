package java.com.esign;

/*
 * Project: Alfresco Esign Module , part of the Creative Summer
 * License   : GNU General Public License, version 2 (http://www.gnu.org/licenses/gpl-2.0.html)
 */

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class PdfSigner implements Signer<PdfContent> {

	private X509Certificate certificate;
	private PrivateKey privateKey;
	public PdfSigner(X509Certificate certificate,PrivateKey privateKey){
		this.certificate = certificate;
		this.privateKey = privateKey;
	}
	@Override
	public PdfContent[] sign(PdfContent... data) throws Exception {
		// TODO Auto-generated method stub
		for(PdfContent content: data){
			content.addSignature(certificate, privateKey);
		}
		return data;
	}
	@Override
	public boolean[] verify(PdfContent... data) {
		// TODO Auto-generated method stub
		boolean results[] = new boolean[data.length];
		for(int i=0;i<data.length;i++){
			results[i] = data[i].validateSignatures(); 
		}
		return results;
	}
	
}
