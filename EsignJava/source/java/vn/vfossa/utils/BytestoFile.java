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
package vn.vfossa.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * Project: Alfresco Encryption Extension Module , part of the Creative Summer
 * This code was developped by a group of 3 students from UET-VNU .
 * License   : GNU General Public License, version 2 (http://www.gnu.org/licenses/gpl-2.0.html)
 * 
 */		

public class BytestoFile {
	/**
	 * returns a file from a byte array
	 * 
	 */

	public static File bytesToFile (byte[] bytes,String path) throws NullPointerException, IOException{
		if(bytes == null) throw new NullPointerException();
		if(path == null) throw new NullPointerException();
		
		File file=new File(path);
		FileOutputStream fout;
		fout = new FileOutputStream(file);

		for (int i = 0; i < bytes.length; i++)
			fout.write(bytes[i]);

		fout.close();
		
		return file;
	}

}
