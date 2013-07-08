package java.com.esign;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collections;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;

public class ObjectFactory {

	public static void generateXMLSignature(PublicKey publicKey,
			PrivateKey privateKey, InputStream data) throws Exception {
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		// Create a Reference to an external URI that will be digested
		// using the SHA1 digest algorithm
		Reference ref = fac.newReference("http://www.w3.org/TR/xml-stylesheet",
				fac.newDigestMethod(DigestMethod.SHA1, null));

		// Create the SignedInfo
		SignedInfo si = fac
				.newSignedInfo(fac.newCanonicalizationMethod(
						CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,
						(C14NMethodParameterSpec) null), fac
						.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
						Collections.singletonList(ref));

		// Create a KeyValue containing the DSA PublicKey that was generated
		KeyInfoFactory kif = fac.getKeyInfoFactory();
		KeyValue kv = kif.newKeyValue(publicKey);

		// Create a KeyInfo and add the KeyValue to it
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));

		// Create the XMLSignature (but don't sign it yet)
		XMLSignature signature = fac.newXMLSignature(si, ki);

		// Create the Document that will hold the resulting XMLSignature
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true); // must be set
		Document doc = dbf.newDocumentBuilder().parse(data);

		// Create a DOMSignContext and set the signing Key to the DSA
		// PrivateKey and specify where the XMLSignature should be inserted
		// in the target document (in this case, the document root)
		DOMSignContext signContext = new DOMSignContext(privateKey, doc);

		// Marshal, generate (and sign) the detached XMLSignature. The DOM
		// Document will contain the XML Signature if this method returns
		// successfully.
		signature.sign(signContext);

		// output the resulting document
		//OutputStream os;

	//	os = System.out;

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
	//	trans.transform(new DOMSource(doc), new StreamResult(os));
	}
}
