package java.com.esign;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.crypto.dsig.XMLSignature;


import com.ecoit.ca.utilities.DateFromServer;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfPKCS7;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;

public class PdfContent implements SignContent<PdfReader> {
	private PdfReader content;
	private String path, signedPath;

	public PdfContent(String path) throws Exception {
		this.path = path;
		content = new PdfReader(path);
	}

	public PdfContent(String path, byte[] byteArrOfFile) throws IOException
	{
		this.path = path;
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(byteArrOfFile);
		fos.close();
		
		content = new PdfReader(path);
	}
	
	@Override
	public PdfReader getContentObject() {
		// TODO Auto-generated method stub
		return content;
	}

	@Override
	public XMLSignature[] getSignatures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSignedBy(X509Certificate certificate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addSignature(X509Certificate certificate, PrivateKey key)
			throws Exception {
		// TODO Auto-generated method stub
		Certificate cer[] = { certificate };
		for (int index = path.length() - 1; index >= 0; index--)
			if (path.charAt(index) == '.') {
				signedPath = path.substring(0, index) + "_signed"
						+ path.substring(index);
				break;
			}
		File file = new File(signedPath);
		FileOutputStream out = new FileOutputStream(file);

		int numOfPages = content.getNumberOfPages();
		int numOfSignatures = content.getAcroFields().getSignatureNames()
				.size() + 1;
		PdfStamper stp = PdfStamper.createSignature(content, out, '\0', null,
				true);
		PdfSignatureAppearance sap = stp.getSignatureAppearance();
		int m = (numOfPages - 1) * 5 + (numOfSignatures - 1) * 200;

		try {
			Calendar ca = Calendar.getInstance();
			ca.setTime(DateFromServer.getDate());
			sap.setSignDate(ca);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		sap.setCrypto(key, cer, null, PdfSignatureAppearance.WINCER_SIGNED);
		sap.setVisibleSignature(new com.lowagie.text.Rectangle(m, 0, m + 200,
				30), 1, null);

		stp.close();
		Files.delete(Paths.get(path));
		content = new PdfReader(signedPath);
	}

	@Override
	public String getPathSigned() {
		return signedPath;
	}
	
	@Override
	public boolean validateSignatures() {
		// TODO Auto-generated method stub
		AcroFields af = content.getAcroFields();
		List<String> names = af.getSignatureNames();
		String name = names.get(0);
		PdfPKCS7 pk = af.verifySignature(name);
		X509Certificate pkc[] = (X509Certificate[]) pk.getCertificates();
		Calendar calendar = pk.getTimeStampDate();
		String fails = PdfPKCS7.verifyCertificate(pkc[0], null, calendar);
		if (fails == null)
			return true;

		return false;
	}

}
