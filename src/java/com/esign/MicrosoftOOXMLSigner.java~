package java.com.esign;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class MicrosoftOOXMLSigner implements Signer<MicrosoftOOXMLContent> {

	private X509Certificate certificate;
	private PrivateKey privateKey;

	public MicrosoftOOXMLSigner(X509Certificate certificate,
			PrivateKey privateKey) {
		this.certificate = certificate;
		this.privateKey = privateKey;
	}

	@Override
	public MicrosoftOOXMLContent[] sign(MicrosoftOOXMLContent... data) {
		for (MicrosoftOOXMLContent content : data) {
			if (!content.isSignedBy(certificate))
				content.addSignature(certificate, privateKey);
		}
		return data;
	}

	@Override
	public boolean[] verify(MicrosoftOOXMLContent... data) {
		boolean[] results = new boolean[data.length];
		for (int i = 0; i < data.length; i++)
			results[i] = data[i].validateSignatures();

		return results;
	}
}
