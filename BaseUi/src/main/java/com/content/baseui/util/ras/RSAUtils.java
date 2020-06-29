package com.content.baseui.util.ras;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * Created by wangwei on 2019-01-04
 */
public class RSAUtils {

    private static final String CHARSET = "UTF-8";
    private static final String RSA_ALGORITHM = "RSA";
    private static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    private static final int BASE64TYPE = Base64.DEFAULT;

    public static Map<String, String> createKeys(int keySize) {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeToString(publicKey.getEncoded(), BASE64TYPE);
//        String publicKeyStr = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeToString(privateKey.getEncoded(), BASE64TYPE);
//        String privateKeyStr = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param publicKeyStr 密钥字符串（经过base64编码）
     *                     Created by wangwei on 2019-01-04
     */
    public static RSAPublicKey getPublicKey(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, NoSuchProviderException {
//        //通过X509编码的Key指令获得公钥对象
//        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM, "BC");
//        byte[] publicKeyArrys = Base64.decode(publicKey.getBytes(CHARSET), BASE64TYPE);
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyArrys);
//
////        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(org.apache.commons.codec.binary.Base64.decodeBase64(publicKey));
//        return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);

        PublicKey publicKey = null;
        byte[] decodeKey = Base64.decode(publicKeyStr, BASE64TYPE);
//        byte[] testKey = new byte[]{48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0, 48, -127, -119, 2, -127, -127, 0, -93, 50, -100, 13, 85, -109, -125, -12, 80, -37, -75, 103, -114, 108, -20, -18, -55, 16, -34, 22, -106, -44, -1, -102, 78, -1, -99, 60, 119, 100, 34, 37, -88, 48, 88, -101, 2, 115, -94, 121, 14, -23, 100, 26, 16, -112, 52, 16, -23, 45, 84, -11, 115, -60, 117, -87, 46, -107, -112, -25, -26, -123, -56, -92, 105, 29, -21, -50, 106, -55, -110, -86, -100, 50, 99, -16, -84, -127, -12, 12, 68, 19, -110, -56, -95, -52, -69, -118, -105, -53, -46, -120, -104, 88, -111, 10, 26, -39, -44, -21, -87, 8, 0, 37, 34, 110, 33, -8, 65, 11, 99, -53, -38, -58, -111, -94, 95, 94, 58, -51, 37, -25, -127, 104, 51, 46, -11, 85, 2, 3, 1, 0, 1};
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodeKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(x509);
        return (RSAPublicKey) publicKey;
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     *                   Created by wangwei on 2019-01-04
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        byte[] privateKeyArrys = Base64.decode(privateKey, BASE64TYPE);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyArrys);
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 公钥加密
     * Created by wangwei on 2019-01-04
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()), BASE64TYPE);
//            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     * Created by wangwei on 2019-01-04
     */
    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decode(data, BASE64TYPE), publicKey.getModulus().bitLength()), CHARSET);
//            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     * Created by wangwei on 2019-01-04
     */
    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decode(data, BASE64TYPE), privateKey.getModulus().bitLength()), CHARSET);
//            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     * Created by wangwei on 2019-01-04
     */
    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()), BASE64TYPE);
//            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opMode, byte[] data, int keySize) {
        int maxBlock;
        if (opMode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        byte[] resultData = null;
        int i = 0;
        try {
            while (data.length > offSet) {
                if (data.length - offSet > maxBlock) {
                    buff = cipher.doFinal(data, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(data, offSet, data.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
            resultData = out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultData;
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> keyMap = RSAUtils.createKeys(1024);
        String publicKey = keyMap.get("publicKey");
        String privateKey = keyMap.get("privateKey");
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);

        System.out.println("私钥加密——公钥解密");
        String str = "842333504788617991";
        System.out.println("\r明文：\r\n" + str);
        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
        String encodedData = RSAUtils.privateEncrypt(str, RSAUtils.getPrivateKey(privateKey));
        System.out.println("密文：\r\n" + encodedData);
        String decodedData = RSAUtils.publicDecrypt(encodedData, RSAUtils.getPublicKey(publicKey));
        System.out.println("解密后文字: \r\n" + decodedData);


    }
}
