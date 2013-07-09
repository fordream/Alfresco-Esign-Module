package java.com.esign;

/*
 * Project: Alfresco Esign Module , part of the Creative Summer
 * License   : GNU General Public License, version 2 (http://www.gnu.org/licenses/gpl-2.0.html)
 */

import java.net.URI;
import java.security.PublicKey;

public interface SigningModule {

	
	public String signForm(String originalData);

	public boolean verifyForm(String signature,String originalData,PublicKey pub);
	
	public URI signFile(URI originalURI);
	
	public boolean verifyFile(URI signedURI);

}
