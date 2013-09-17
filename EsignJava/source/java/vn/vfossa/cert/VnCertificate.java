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
