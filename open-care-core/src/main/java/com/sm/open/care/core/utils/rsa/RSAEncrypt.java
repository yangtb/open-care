package com.sm.open.care.core.utils.rsa;

import com.sm.open.care.core.exception.BizRuntimeException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @ClassName: RSAEncrypt
 * @Description: RSA加解密
 * @Author yangtongbin
 * @Date 2017/10/11 11:19
 */
public class RSAEncrypt {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM_ECB1 = "RSA/ECB/PKCS1Padding";
    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 生成公私钥对
     */
    public static RsaKeyPair genKeyPair() {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 得到公钥字符串
        String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
        return new RsaKeyPair(privateKeyString, publicKeyString);
    }

    /**
     * 随机生成密钥对
     */
    public static void genKeyPair(String filePath) {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        try {
            // 得到公钥字符串
            String publicKeyString = Base64.encodeBase64String(publicKey
                    .getEncoded());
            // 得到私钥字符串
            String privateKeyString = Base64.encodeBase64String(privateKey
                    .getEncoded());
            // 将密钥对写入到文件
            FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
            FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
            BufferedWriter pubbw = new BufferedWriter(pubfw);
            BufferedWriter pribw = new BufferedWriter(prifw);
            pubbw.write(publicKeyString);
            pribw.write(privateKeyString);
            pubbw.flush();
            pubbw.close();
            pubfw.close();
            pribw.flush();
            pribw.close();
            prifw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param path 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static String loadPublicKeyByFile(String path) throws BizRuntimeException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "/publicKey.keystore"));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new BizRuntimeException("公钥数据流读取错误", e);
        } catch (NullPointerException e) {
            throw new BizRuntimeException("公钥输入流为空", e);
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
            throws BizRuntimeException {
        try {
            byte[] buffer = Base64.decodeBase64(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new BizRuntimeException("无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new BizRuntimeException("公钥非法", e);
        } catch (NullPointerException e) {
            throw new BizRuntimeException("公钥数据为空", e);
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param path 私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    public static String loadPrivateKeyByFile(String path) throws BizRuntimeException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "/privateKey.keystore"));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new BizRuntimeException("私钥数据读取错误", e);
        } catch (NullPointerException e) {
            throw new BizRuntimeException("私钥输入流为空", e);
        }
    }

    /**
     * 根据字符串获取私钥
     * @param privateKeyStr
     * @return
     * @throws BizRuntimeException
     */
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
            throws BizRuntimeException {
        try {
            byte[] buffer = Base64.decodeBase64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new BizRuntimeException("无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new BizRuntimeException("私钥非法", e);
        } catch (NullPointerException e) {
            throw new BizRuntimeException("私钥数据为空", e);
        }
    }

    /**
     * 根据公钥字符串加密
     * @param publicKeyStr
     * @param plainTextData
     * @return
     * @throws BizRuntimeException
     */
    public static String encryptByPublicKeyStr(String publicKeyStr, String plainTextData)
            throws BizRuntimeException {
        return encrypt(loadPublicKeyByStr(publicKeyStr), plainTextData.getBytes(), false);
    }

    /**
     * 是否使用urlsafe编码
     * @param publicKeyStr
     * @param plainTextData
     * @param isUrlSafe
     * @return
     * @throws BizRuntimeException
     */
    public static String encryptByPublicKeyStr(String publicKeyStr, String plainTextData, boolean isUrlSafe)
            throws BizRuntimeException {
        return encrypt(loadPublicKeyByStr(publicKeyStr), plainTextData.getBytes(), isUrlSafe);
    }

    /**
     * 公钥加密过程
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(RSAPublicKey publicKey, byte[] plainTextData, boolean isUrlSafe)
            throws BizRuntimeException {
        if (publicKey == null) {
            throw new BizRuntimeException("加密公钥为空, 请设置");

        }

        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB1);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            if (isUrlSafe) {
                return Base64.encodeBase64URLSafeString(output);
            }
            return Base64.encodeBase64String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new BizRuntimeException("无此加密算法", e);
        } catch (NoSuchPaddingException e) {
            throw new BizRuntimeException("加密异常", e);
        } catch (InvalidKeyException e) {
            throw new BizRuntimeException("加密公钥非法,请检查", e);
        } catch (IllegalBlockSizeException e) {
            throw new BizRuntimeException("明文长度非法", e);
        } catch (BadPaddingException e) {
            throw new BizRuntimeException("明文数据已损坏", e);
        }
    }

    /**
     * 根据私钥字符串加密
     * @param privateKeyStr
     * @param plainTextData
     * @return
     * @throws BizRuntimeException
     */
    public static String encryptByPrivateKeyStr(String privateKeyStr, String plainTextData)
            throws BizRuntimeException {
        return encrypt(loadPrivateKeyByStr(privateKeyStr), plainTextData.getBytes(), false);
    }

    /**
     * 根据私钥字符串url安全加密
     * @param privateKeyStr
     * @param plainTextData
     * @param isUrlSafe
     * @return
     * @throws BizRuntimeException
     */
    public static String encryptByPrivateKeyStr(String privateKeyStr, String plainTextData, boolean isUrlSafe)
            throws BizRuntimeException {
        return encrypt(loadPrivateKeyByStr(privateKeyStr), plainTextData.getBytes(), isUrlSafe);
    }

    /**
     * 私钥加密过程
     *
     * @param privateKey    私钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(RSAPrivateKey privateKey, byte[] plainTextData, boolean isUrlSafe)
            throws BizRuntimeException {
        if (privateKey == null) {
            throw new BizRuntimeException("加密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB1);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(plainTextData);
            if (isUrlSafe) {
                return Base64.encodeBase64URLSafeString(output);
            }
            return Base64.encodeBase64String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new BizRuntimeException("无此加密算法", e);
        } catch (NoSuchPaddingException e) {
            throw new BizRuntimeException("加密异常", e);
        } catch (InvalidKeyException e) {
            throw new BizRuntimeException("加密私钥非法,请检查", e);
        } catch (IllegalBlockSizeException e) {
            throw new BizRuntimeException("明文长度非法", e);
        } catch (BadPaddingException e) {
            throw new BizRuntimeException("明文数据已损坏", e);
        }
    }

    /**
     * 根据私钥字符串解密
     *
     * @param privateKeyStr
     * @param cipherData
     * @return
     * @throws BizRuntimeException
     */
    public static String decryptByPrivateKeyStr(String privateKeyStr, String cipherData)
            throws BizRuntimeException {
        return decrypt(loadPrivateKeyByStr(privateKeyStr), Base64.decodeBase64(cipherData));
    }

    /**
     * 私钥解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(RSAPrivateKey privateKey, byte[] cipherData)
            throws BizRuntimeException {
        if (privateKey == null) {
            throw new BizRuntimeException("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB1);
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output).trim();
        } catch (NoSuchAlgorithmException e) {
            throw new BizRuntimeException("无此解密算法", e);
        } catch (NoSuchPaddingException e) {
            throw new BizRuntimeException("解密异常", e);
        } catch (InvalidKeyException e) {
            throw new BizRuntimeException("解密私钥非法,请检查", e);
        } catch (IllegalBlockSizeException e) {
            throw new BizRuntimeException("密文长度非法", e);
        } catch (BadPaddingException e) {
            throw new BizRuntimeException("密文数据已损坏", e);
        }
    }


    /**
     * 根据公钥字符串解密
     *
     * @param publicKeyStr
     * @param cipherData
     * @return
     * @throws BizRuntimeException
     */
    public static String decryptByPublicKeyStr(String publicKeyStr, String cipherData)
            throws BizRuntimeException {
        return decrypt(loadPublicKeyByStr(publicKeyStr), Base64.decodeBase64(cipherData));
    }

    /**
     * 公钥解密过程
     *
     * @param publicKey  公钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(RSAPublicKey publicKey, byte[] cipherData)
            throws BizRuntimeException {
        if (publicKey == null) {
            throw new BizRuntimeException("解密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB1);
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output).trim();
        } catch (NoSuchAlgorithmException e) {
            throw new BizRuntimeException("无此解密算法", e);
        } catch (NoSuchPaddingException e) {
            throw new BizRuntimeException("解密异常", e);
        } catch (InvalidKeyException e) {
            throw new BizRuntimeException("解密公钥非法,请检查", e);
        } catch (IllegalBlockSizeException e) {
            throw new BizRuntimeException("密文长度非法", e);
        } catch (BadPaddingException e) {
            throw new BizRuntimeException("密文数据已损坏", e);
        }
    }

    /**
     * 字节数据转十六进制字符串
     *
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteArrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            // 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            // 取出字节的低四位 作为索引得到相应的十六进制标识符
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i < data.length - 1) {
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }

}
