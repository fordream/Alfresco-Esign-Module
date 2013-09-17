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
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.dsig.XMLSignature;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;

import com.ecoit.ca.signature.openxml4j.PackageDigitalSignature;
import com.ecoit.ca.signature.openxml4j.PackageDigitalSignatureManager;
import com.ecoit.ca.signature.openxml4j.VerifyResult;

public class MicrosoftOOXMLContent implements SignContent<OPCPackage> {

	private PackageDigitalSignatureManager pdsm;
	private String path, signedPath;
	private OPCPackage content;

	// TODO: create more meaningful exception
	public MicrosoftOOXMLContent(String path) {
		this.path = path;
		try {

			content = OPCPackage.open(new File(path));
			pdsm = new PackageDigitalSignatureManager(content);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public MicrosoftOOXMLContent(String path, byte[] byteArrOfFile) {
		this.path = path;
		try {
			content = OPCPackage.open(path);
			pdsm = new PackageDigitalSignatureManager(content);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	@Override
	public String getPathSigned() {
		for (int i = path.length() - 1; i >= 0; i--) {
			if (path.charAt(i) == '.') {
				signedPath = path.substring(0, i) + "_signed"
						+ path.substring(i);
				break;
			}
		}

		return signedPath;
	}
	
	@Override
	public OPCPackage getContentObject() {
		return pdsm.getContainer();
	}

	public void closeContent() throws Exception {
		content.close();
//		pdsm.getContainer().close();
		
	}

	@Override
	public XMLSignature[] getSignatures() {
		List<XMLSignature> sigs = new ArrayList<XMLSignature>();
		try {
			for (PackageDigitalSignature sig : pdsm.getSignatures()) {
				sigs.add(sig.getSignature());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sigs.toArray(new XMLSignature[0]);
	}

	@Override
	public boolean isSignedBy(X509Certificate certificate) {
		try {
			if (pdsm.getSignatures() == null)
				return false;

			for (PackageDigitalSignature sig : pdsm.getSignatures()) {
				if (sig.getSigner().equals(certificate))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void addSignature(X509Certificate certificate, PrivateKey key) {
		try {
			pdsm.SignDocument(key, certificate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validateSignatures() {
		try {
			return pdsm.VerifySignatures().equals(VerifyResult.Success);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
