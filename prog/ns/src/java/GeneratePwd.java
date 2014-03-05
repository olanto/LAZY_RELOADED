/* Lazy hypertext view system
Copyright (C) 2002-2003,  ISI Research Group, CUI, University of Geneva
 
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
You can also find the GNU GPL at http://www.gnu.org/copyleft/gpl.html
 
You can contact the ISI research group at http://cui.unige.ch/isi
 */
/*
 * GeneratePwd.java
 *
 * Created on 9. septembre 2002, 14:55
 *
 * @author  ZBINDEN
 */

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class GeneratePwd {
   
   String password;
   String result = "DEFAULT PLAIN TEXT PASSWORD";
   static final String KEY = "8765432187654321";
   
   public GeneratePwd(){}
   /************************************
    *
    *  DO IT
    */
   public synchronized  String doIt(Object params) {
      try {
         
         // get the value contained in parameters of this method
         String[] paramValues = (String[])params;
         password=paramValues[0];
         
         // generate Cipher objects for encoding and decoding
         Cipher itsocipher1 = Cipher.getInstance("DES/ECB/PKCS5Padding","SunJCE");
                  
         System.out.println("Initializing key... ");
         KeyGenerator KG = KeyGenerator.getInstance("DES","SunJCE");
         SecretKey internalkey = KG.generateKey(); // generate a internal key for encoded url
         
         // generate a KeyGenerator object
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES","SunJCE");         
         DESKeySpec desKeySpec = new DESKeySpec(Hexa.fromString(KEY));
         SecretKey mykey = keyFactory.generateSecret(desKeySpec);
         
         // initialize the Cipher objects         
         System.out.println("Initializing ciphers...1");
         itsocipher1.init( Cipher.ENCRYPT_MODE, mykey);
                 
         
         byte[] ciphertext = itsocipher1.doFinal(password.getBytes());
         // print out length and representation of ciphertext         
         result = Hexa.toString(ciphertext);
         System.out.println("Representation = "+Hexa.toString(ciphertext));
         
        
         
      }
      catch (Exception e) {
         System.out.println("Caught exception: " + e ); e.printStackTrace();
      }
       return result;
   }
}

class Hexa {
   
   private static final char[] hexDigits = {
      '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
   };
   
   public static byte[] fromString(String hex) {
      int len = hex.length();
      byte[] buf = new byte[((len + 1) / 2)];
      
      int i = 0, j = 0;
      if ((len % 2) == 1)
         buf[j++] = (byte) fromDigit(hex.charAt(i++));
      
      while (i < len) {
         buf[j++] = (byte) ((fromDigit(hex.charAt(i++)) << 4) |
         fromDigit(hex.charAt(i++)));
      }
      return buf;
   }
   
   public static int fromDigit(char ch) {
      if (ch >= '0' && ch <= '9')
         return ch - '0';
      if (ch >= 'A' && ch <= 'F')
         return ch - 'A' + 10;
      if (ch >= 'a' && ch <= 'f')
         return ch - 'a' + 10;
      
      throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
   }
   public static String toString(byte[] ba, int offset, int length) {
      char[] buf = new char[length * 2];
      int j = 0;
      int k;
      
      for (int i = offset; i < offset + length; i++) {
         k = ba[i];
         buf[j++] = hexDigits[(k >>> 4) & 0x0F];
         buf[j++] = hexDigits[ k        & 0x0F];
      }
      return new String(buf);
   }
   
   public static String toString(byte[] ba) {
      return toString(ba, 0, ba.length);
   }
   
   
   
   
   
}