package cn.blackbulb;

import com.auth0.jwt.interfaces.RSAKeyProvider;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author wangcan
 */
public class JWTUtil {

    //    final JwkStore jwkStore = new JwkStore("{JWKS_FILE_HOST}");
    //    final RSAPrivateKey privateKey = null;
    final String privateKeyId = "JINGDATA-INVESTMENT";//Create an Id for the above key

    RSAKeyProvider refreshKeyProvider = new RSAKeyProvider() {
        @Override
        public RSAPublicKey getPublicKeyById(String kid) {
            try {
                byte[] keyBytes;
                keyBytes = (new BASE64Decoder()).decodeBuffer(privateKeyId);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey privateKey = keyFactory.generatePublic(keySpec);
                return (RSAPublicKey) privateKey;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        public RSAPrivateKey getPrivateKey() {
            try {
                byte[] keyBytes;
                keyBytes = (new BASE64Decoder()).decodeBuffer(privateKeyId);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
                return (RSAPrivateKey) privateKey;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            return null;
            //            return privateKey;
        }

        @Override
        public String getPrivateKeyId() {
            return privateKeyId;
        }
    };
}
