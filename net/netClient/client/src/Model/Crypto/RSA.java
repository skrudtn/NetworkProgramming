package Model.Crypto;

/**
 * Created by skrud on 2017-11-27.
 */
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class RSA {
    private Key publicKey;
    private Key privateKey;
    private Cipher cipher;
    public RSA(){
        try {
            //공개키 및 개인키 생성
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.genKeyPair();
            publicKey = keyPair.getPublic(); // 공개키
            privateKey = keyPair.getPrivate(); // 개인키

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
//            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            cipher = Cipher.getInstance("RSA");
            // 복호화(Decryption) 예제

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Key getPublicKey() {
        return publicKey;
    }

    public Key getPrivateKey() {
        return privateKey;
    }
    public String getEncodedText(String str) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] arrCipherData = cipher.doFinal(str.getBytes()); // 암호화된 데이터(byte 배열)
        String strCipher = new String(arrCipherData);

        System.out.println(strCipher); // 암호화 결과물 출력(눈으로 보이기에는 깨질 수 있음)
        return strCipher;
    }
    public String getDecodeText(String str) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] arrData = cipher.doFinal(str.getBytes());
        String strResult = new String(arrData);

        System.out.println(strResult);
        return strResult;
    }
}
