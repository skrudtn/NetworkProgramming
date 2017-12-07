package Control;

import Model.Crypto.AES256Util;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by skrud on 2017-11-27.
 */
public class CryptoController {
    private String aesKey;
    private AES256Util aes256;
    CryptoController () throws UnsupportedEncodingException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        aesKey= "my-first-aes256-key!'!"; 		// key는 16자 이상
        aes256 = new AES256Util(aesKey);
    }

    public String getAesKey() {
        return aesKey;
    }
    public String getAesEncodedText(String str) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        System.out.println("평문 : "+str);
        return aes256.aesEncode(str);
    }
    public String getAesDecodedText(String str) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
//        System.out.println("암호문 : "+str);
        return aes256.aesDecode(str);
    }

//    public Key getRSAPublicKey(){
//        return publicKey;
//    }
//    public Key getRSAPrivateKey(){
//        return privateKey;
//    }
//    public String getRsaEncodedText(String str) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
//        return rsa.getEncodedText(str);
//    }
//    public String getRsaDecodedText(String str) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
//        return rsa.getDecodeText(str);
//    }
}
