package com.sm.open.care.core.utils.rsa;

import java.io.Serializable;
import java.util.UUID;

/**
 * @ClassName: RsaKeyPair
 * @Description: Rsa公私钥对
 */
public class RsaKeyPair implements Serializable {
	private static final long serialVersionUID = -741471372149714869L;
	
	/** 标识rsa公私钥对的唯一标识 */
	private String uniqueId;
	
	/** rsa私钥 */
	private String privateKey;
	
	/** rsa公钥 */
	private String publicKey;
	
	public RsaKeyPair(){
		this.uniqueId = UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public RsaKeyPair(String privateKey, String publicKey) {
		this();
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public String toString() {
		return "RsaKeyPair [uniqueId=" + uniqueId + ", privateKey=" + privateKey + ", publicKey=" + publicKey + "]";
	}
	
}
