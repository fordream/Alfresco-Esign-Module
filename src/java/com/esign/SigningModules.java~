package java.com.esign;

/*
 * Project: Alfresco Esign Module , part of the Creative Summer
 * License   : GNU General Public License, version 2 (http://www.gnu.org/licenses/gpl-2.0.html)
 */

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ecoit.ca.applet.exception.TokenException;
import com.ecoit.ca.token.TokenModule;
import com.ecoit.ca.token.TokenModules;
import com.ecoit.ca.utilities.Base64Utils;

public class SigningModules {
	
	public static FormSigner createFormSigner() throws TokenException {
//		TokenModule token = TokenModules.newDefaultTokenModule();
		FormSigner fsigner = new BasicFormSigner();
		return fsigner;
	}

	private static FileSigner createPDFSigner(final TokenModule token,
			final String filePath) {

		FileSigner fsigner = new FileSigner() {

			@Override
			public boolean verifyFile() {
				// TODO Auto-generated method stub
				try {
					PdfContent content = new PdfContent(filePath);
					PdfSigner signer = new PdfSigner(
							(X509Certificate) token.getCertificate(), null);
					return signer.verify(content)[0];
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return false;
			}

			@Override
			public String signFile() {
				// TODO Auto-generated method stub
				try {
					PdfContent content = new PdfContent(filePath);
					PdfSigner signer = new PdfSigner(
							(X509Certificate) token.getCertificate(),
							token.getPrivateKey());
					signer.sign(content);
					File file = new File(content.getPathSigned());
					file.renameTo(new File(filePath));
					JOptionPane.showMessageDialog(null, "K\u00FD xong");
					return filePath;
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "K\u00FD l\u1ED7i");
					return null;
				}
			}
		};
		return fsigner;
	}

	private static FileSigner createDocxSigner(final TokenModule token,
			final String filePath) {
		FileSigner fsigner = new FileSigner() {

			@Override
			public boolean verifyFile() {
				// TODO Auto-generated method stub
				try {
					MicrosoftOOXMLContent content = new MicrosoftOOXMLContent(
							filePath);
					MicrosoftOOXMLSigner signer = new MicrosoftOOXMLSigner(
							(X509Certificate) token.getCertificate(), null);
					boolean bool = signer.verify(content)[0];
					content.closeContent();
					return bool;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return false;
			}

			@Override
			public String signFile() {
				// TODO Auto-generated method stub
				try {
					MicrosoftOOXMLContent content = new MicrosoftOOXMLContent(
							filePath);
					MicrosoftOOXMLSigner signer = new MicrosoftOOXMLSigner(
							(X509Certificate) token.getCertificate(),
							token.getPrivateKey());
					MicrosoftOOXMLContent newContent[] = signer.sign(content);
					newContent[0].getContentObject().save(
							new File(newContent[0].getPathSigned()));
					newContent[0].closeContent();
					String re = newContent[0].getPathSigned();
					Path p = Paths.get(filePath);
					Files.delete(p);
					File file = new File(re);
					file.renameTo(new File(filePath));

					JOptionPane.showMessageDialog(null, "K\u00FD xong");
					return filePath;

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "K\u00FD l\u1ED7i");
					return null;
				}
			}
		};
		return fsigner;
	}

	public static FileSigner createFileSigner(String filePath) {
		String extension = "";
		for (int index = filePath.length() - 1; index >= 0; index--)
			if (filePath.charAt(index) == '.') {
				extension = filePath.substring(index + 1);
				break;
			}

		try {
			TokenModule token = TokenModules.newDefaultTokenModule();
			if (extension.equals("pdf"))
				return SigningModules.createPDFSigner(token, filePath);
			else if (extension.equals("docx"))
				return SigningModules.createDocxSigner(token, filePath);
			else
				JOptionPane.showMessageDialog(null,
						"Kh\u00F4ng h\u1ED7 tr\u1EE3 lo\u1EA1i file n\u00E0y !");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
