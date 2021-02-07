package workx.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encoder {
	//Funzione che data una stringa restituisce la sua codifica in MD5
	public String encode(String word) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(word.getBytes());
		byte[] digest = md.digest();
		BigInteger no = new BigInteger(1, digest); 
        
		// Convert message digest into hex value 
        String hashtext = no.toString(16); 
        while (hashtext.length() < 32) { 
        	hashtext = "0" + hashtext; 
        }
        return hashtext;
	}
}
