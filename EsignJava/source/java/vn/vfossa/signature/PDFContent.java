/****************************************************************************** * 
* Copyright (C) 2013   Nguyen Khanh Thinh, Nguyen Dinh Nien Contributors
* This program is free software; you can redistribute it and/or 
* modify it under the terms of the GNU General Public License 
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version. 
* This program is distributed in the hope that it will be useful, 
* but WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
* GNU General Public License for more details.  
* You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
* Any further request, feel freely to contactmhst1304@googlegroups.com or 
* visit https://www.hostedredmine.com/projects/mhst1304 or
* participate online daily meeting via IRC channel #mhst1304 server freenode.net at 9PM - GMT+7.

*************************************************************************************************/
package vn.vfossa.signature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.dsig.XMLSignature;

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
			ca.setTime(new Date());
			sap.setSignDate(ca);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		sap.setCrypto(key, cer, null, PdfSignatureAppearance.WINCER_SIGNED);
		sap.setVisibleSignature(new com.lowagie.text.Rectangle(m, 0, m + 200,
				30), 1, null);

		stp.close();
		//Files.delete(Paths.get(path));
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
