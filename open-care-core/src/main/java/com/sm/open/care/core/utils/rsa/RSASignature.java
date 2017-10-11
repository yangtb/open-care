package com.sm.open.care.core.utils.rsa;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.MessageFormat;


/**
 * @ClassName: RSASignature
 * @Description: 签名及校验类
 */
public class RSASignature {
	
	private final static Logger logger = LoggerFactory.getLogger(RSASignature.class);
	
	/**
	 * 签名算法
	 */
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/**
	 * RSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param encode
	 *            字符集编码
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey, String encode) {
		logger.debug("【RSA签名】数据签名 content：{0} privateKey：{1} encode：{2}", content, privateKey, encode);
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(encode));
			byte[] signed = signature.sign();
			return Base64.encodeBase64String(signed);
		} catch (Exception e) {
			logger.error(MessageFormat.format("【RSA签名】数据签名失败 content：{0} privateKey：{1} encode：{2}", content, privateKey, encode), e);
		}

		return null;
	}

	/**
	 * 签名（无编码入参）
	 * @param content
	 * @param privateKey
	 * @return
	 */
	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes());
			byte[] signed = signature.sign();
			return Base64.encodeBase64String(signed);
		} catch (Exception e) {
			logger.error(MessageFormat.format("【RSA签名(没有编码入参)】数据签名失败 content：{0} privateKey：{1}", content, privateKey), e);
		}
		return null;
	}

	/**
	 * RSA验签名检查
	 *
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            分配给开发商公钥
	 * @param encode
	 *            字符集编码
	 * @return 布尔值
	 */
	public static boolean doCheck(String content, String sign, String publicKey, String encode) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decodeBase64(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes(encode));
			boolean bverify = signature.verify(Base64.decodeBase64(sign));
			return bverify;
		} catch (Exception e) {
			logger.error(MessageFormat.format("【RSA验签名检查】数据签名失败 content：{0} sign：{1} publicKey：{2} encode:{3}", content, sign, publicKey, encode), e);
		}
		return false;
	}

	/**
	 * RSA验签名检查(无编码入参)
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 */
	public static boolean doCheck(String content, String sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decodeBase64(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes());
			boolean bverify = signature.verify(Base64.decodeBase64(sign));
			return bverify;
		} catch (Exception e) {
			logger.error(MessageFormat.format("【RSA验签名检查(无编码参数)】数据签名失败 content：{0} sign：{1} publicKey：{2}", content, sign, publicKey), e);
		}
		return false;
	}

}
