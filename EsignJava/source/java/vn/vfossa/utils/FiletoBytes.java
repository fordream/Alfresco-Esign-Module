/****************************************************************************** * .
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
/*

 * This code was developped by a group of 3 students from UET-VNU .
 * License   : GNU General Public License, version 2 (http://www.gnu.org/licenses/gpl-2.0.html)
 * 
 */		
	
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FiletoBytes {
		/**
		 * returns a byte array representation of a given file
		 * 
		 */

		public static byte [] fileToBytes(File file) throws NullPointerException,IOException{
			if(file == null) throw new NullPointerException("Not Found");
			
			// byte array 
			byte[] bytes=new byte[(int)file.length()];
						
			
			InputStream input=new FileInputStream(file);
			
			//read the bytes
			int iter = 0 , block = 0;
			while ( iter < bytes.length) {
				block = input.read(bytes, iter , bytes.length-iter);
				if ( block >= 0 ) {
					iter += block;
				} else {
					break;					
				}
			}
			
			input.close();
			
			return bytes;
		}
}
