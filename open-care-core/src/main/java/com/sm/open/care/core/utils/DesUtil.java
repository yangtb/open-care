package com.sm.open.care.core.utils;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.Security;

public class DesUtil {

	private final Cipher encryptCipher;

	private final Cipher decryptCipher;

	public static String utilEncryptData(String encryptdata, String encryptkey)
			throws Exception {
		String rs = "";
		DesUtil desPlus = new DesUtil(encryptkey);
		rs = desPlus.encrypt(encryptdata);
		return rs;
	}

	public static String utilDecryptData(String decryptdata, String decryptkey)
			throws Exception {
		return decryptData(decryptdata, decryptkey, true);
	}

	public static String decryptData(String decryptdata, String decryptkey,
			boolean retry) throws Exception {
		DesUtil desPlus;
		String res = "";
		try {
			desPlus = new DesUtil(decryptkey);
			res = desPlus.decrypt(decryptdata);
		} catch (Exception e) {
			if (retry) {
				desPlus = new DesUtil(decryptkey);
				res = desPlus.decrypt(decryptdata
						+ utilEncryptData("", decryptkey));

			}
		}
		return res;
	}

	public String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	@SuppressWarnings("restriction")
	public DesUtil(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());
		encryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	private Key getKey(byte[] arrBTmp) throws Exception {
		byte[] arrB = new byte[8];

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

}
