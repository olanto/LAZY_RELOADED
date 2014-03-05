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
package isi.jg.ns;


import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

 
class Secure {
    
    private static SecretKey mykey;
    static boolean nocoding;
    private static SecretKey internalkey ;

    private static final char[] hexDigits = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };
	
	    
    
    
    public static void init(String rawhexkey,boolean ident){
        try {
            nocoding=ident;
           /* la clé du ns */
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES","SunJCE");
            DESKeySpec desKeySpec = new DESKeySpec(fromString(rawhexkey));
            mykey = keyFactory.generateSecret(desKeySpec);
           /* la clé interne au serveur pour les updates */
            KeyGenerator KG = KeyGenerator.getInstance("DES","SunJCE");
            internalkey = KG.generateKey(); // generate a internal key for encoded url
        }
        catch (Exception e) {
            System.out.println("*** ERROR in SECURE.init exception: " + e);
        }
    }
    
    
    public static String encryptpwd(String msg){
        if (nocoding) return msg;
        try {
            Cipher ENC = Cipher.getInstance("DES/ECB/PKCS5Padding","SunJCE");
            
            ENC.init( Cipher.ENCRYPT_MODE, mykey);
            byte[] ciphertext = ENC.doFinal(msg.getBytes());
            if (NodeServer.verboseCrypto) System.out.println(msg+" -> encrypt pwd = "+toString(ciphertext));
            return toString(ciphertext);
        }
        catch (Exception e) {
            System.out.println("*** ERROR in SECURE.encryptpwd exception: " + e);
        }
        return "";
    }
    
    public static String decryptpwd(String msg){
        if (nocoding) return msg;
        try {
            byte[] tempo=fromString(msg);
            Cipher DEC1 = Cipher.getInstance("DES/ECB/PKCS5Padding","SunJCE");
            DEC1.init( Cipher.DECRYPT_MODE, mykey);
            byte[] ciphertext = DEC1.doFinal(tempo);
            String s=new String(ciphertext);
            if (NodeServer.verboseCrypto) System.out.println(msg+" -> decrypt pwd = "+s);
            return s;
        }
        catch (Exception e) {
            System.out.println("*** ERROR in SECURE.decryptpwd exception: " + e);
        }
        return "";
    }
    
    public static String encrypt(String msg){
        if (nocoding) return msg;
        try {
            Cipher ENC = Cipher.getInstance("DES/ECB/PKCS5Padding","SunJCE");
            
            ENC.init( Cipher.ENCRYPT_MODE, internalkey);
            byte[] ciphertext = ENC.doFinal(msg.getBytes());
            if (NodeServer.verboseCrypto) System.out.println(msg+" -> encrypt = "+toString(ciphertext));
            return toString(ciphertext);
        }
        catch (Exception e) {
            System.out.println("*** ERROR in SECURE.encrypt exception: " + e);
        }
        return "";
    }
    
    public static String decrypt(String msg){
        if (nocoding) return msg;
        try {
            byte[] tempo=fromString(msg);
            Cipher DEC1 = Cipher.getInstance("DES/ECB/PKCS5Padding","SunJCE");


            DEC1.init( Cipher.DECRYPT_MODE, internalkey);
            byte[] ciphertext = DEC1.doFinal(tempo);
            String s=new String(ciphertext);
            if (NodeServer.verboseCrypto) System.out.println(msg+" -> decrypt = "+s);
            return s;
        }
        catch (Exception e) {
            System.out.println("*** ERROR in SECURE.decrypt exception: " + e);
        }
        return "";
    }
    
    public static String[] decryptParam(String[] params){
        if (params == null) return null;
        for (int i = 0; i < params.length; i++)
            params[i]= decrypt(params[i]);
        return params;
    }
    
    public static StringBuffer transformPrivatePart(StringBuffer content){
        
        String s = content.toString();
        if (s.indexOf("<<$$")==-1) return content; // no action parameter
        
        int ix = 0, ixf = 0;
        
        while ((ix = s.indexOf("<<$$", ix)) > -1) {
            if ((ixf = s.indexOf("$$>>", ix)) < 0) {System.out.println("ERROR in ActionPrivatePart ix:"+ix+" ixf:"+ixf+" s:"+s);break;} ;
            String paramName = s.substring(ix+4, ixf);
            if (NodeServer.verboseCrypto) System.out.println("encrypt param Name:"+paramName);
            s = s.substring(0, ix) +encrypt(paramName)+ s.substring(ixf+4, s.length());
        }
        StringBuffer newContent= new StringBuffer(s);
        return newContent;
    }

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

