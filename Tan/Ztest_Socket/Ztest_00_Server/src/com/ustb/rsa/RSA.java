package com.ustb.rsa;

public class RSA {
	//生成密钥对
	public static void genKeyPair(String filepath) {	
		RSAEncrypt.genKeyPair(filepath);
	}
	public static String encrypt(String x,String key){
		return x;
	}
	public static String decrypt(String x,String key){
		return x;
	}
	//获取公钥的字符串形式
	public static String getPubKey(String filepath) throws Exception {
		return RSAEncrypt.loadPublicKeyByFile(filepath);
	}
	//获取私钥的字符串形式
	public static String getPriKey(String filepath) throws Exception {
		return RSAEncrypt.loadPrivateKeyByFile(filepath);
	}
	//公钥加密
	public static String pubEncrypt(String publickey, String plainText) throws Exception {
		byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(publickey),plainText.getBytes());	
		String cipher=Base64.encode(cipherData);	
		return cipher;
	}
	//私钥解密
	public static String priDecrypt(String privatekey, String cipher) throws Exception {
		byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(privatekey), Base64.decode(cipher));
		String restr=new String(res);
		return restr;
	}
	//私钥加密
	public static String priEncrypt(String privatekey, String plainText) throws Exception {
		byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(privatekey),plainText.getBytes());	
		String cipher=Base64.encode(cipherData);	
		return cipher;
	}
	//公钥解密
	public static String pubDecrypt(String publickey, String cipher) throws Exception {
		byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(publickey), Base64.decode(cipher));	
		String restr=new String(res);	
		return restr;
	}
	//签名
	public static String sign(String content, String privateKey) {
		return RSASignature.sign(content, privateKey);
	}
	//验签
	public static boolean doCheck(String content, String sign, String publicKey) {
		return RSASignature.doCheck(content, sign, publicKey);
	}
}
