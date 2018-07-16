import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.*;
import java.io.*;

public class RSAEncrypt {
	//随机生成密钥对：需要文件路径
	public static void genKeyPair(String filePath) {
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 初始化密钥对生成器
		keyPairGen.initialize(1024,new SecureRandom());
		// 生成密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();	
		// 获取私钥对象
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 获取公钥对象
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		try {
			// 编码获得公钥字符串
			String publicKeyString = Base64.encode(publicKey.getEncoded());
			// 编码获得私钥字符串
			String privateKeyString = Base64.encode(privateKey.getEncoded());
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
	
	//从文件中加载公钥：需要文件路径->公钥字符串
	public static String loadPublicKeyByFile(String path) throws Exception {
		try {
			//公钥数据流读入
			BufferedReader br = new BufferedReader(new FileReader(path
					+ "/publicKey.keystore"));	
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}
	
	//从字符串中加载公钥：公钥字符串->公钥对象
	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			//将公钥字符串转成PublicKey对象
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}
	
	//从文件中加载私钥：需要文件路径->私钥字符串
	public static String loadPrivateKeyByFile(String path) throws Exception {
		try {
			//私钥数据流读入
			BufferedReader br = new BufferedReader(new FileReader(path
					+ "/privateKey.keystore"));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}
	//从字符串中加载私钥：私钥字符串->私钥对象
	public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.decode(privateKeyStr);
			//将私钥字符串转成PrivateKey对象
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}
	//公钥加密：公钥对象+明文字节数组->密文字节数组
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用RSA实例化cipher对象
			cipher = Cipher.getInstance("RSA");
			// 初始化cipher，传入的是加密模式和公钥
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			// 定义输入明文的长度
			int inputLen = plainTextData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offset = 0;
			byte[] cache;
			int i = 0;
			//分段加密
			while(inputLen - offset > 0){
				if(inputLen - offset > 117){
					//加密的结果被字节数组cache接收
					cache = cipher.doFinal(plainTextData, offset,117);
				}else{
					cache = cipher.doFinal(plainTextData, offset, inputLen - offset);
				}
				out.write(cache, 0 , cache.length);
				i++;
				offset = i * 117;
			}
			
			byte[] output = out.toByteArray();
			out.close();
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}
	
	//公钥解密：公钥对象+密文字节数组->明文字节数组
	public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("解密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用RSA实例化cipher对象
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			int inputLen = cipherData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offset = 0;
			byte[] cache;
			int i = 0;
			//分段解密
			while(inputLen - offset > 0){
				if(inputLen - offset > 128){
					cache = cipher.doFinal(cipherData, offset,128);
				}else{
					cache = cipher.doFinal(cipherData, offset, inputLen - offset);
				}
				out.write(cache, 0 , cache.length);
				i++;
				offset = i * 128;
			}
			
			byte[] output = out.toByteArray();
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}
	
	
	//私钥加密：私钥对象+明文字节数组->密文字节数组
	public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用RSA实例化cipher对象
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			int inputLen = plainTextData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offset = 0;
			byte[] cache;
			int i = 0;
			//分段加密
			while(inputLen - offset > 0){
				if(inputLen - offset > 117){
					cache = cipher.doFinal(plainTextData, offset,117);
				}else{
					cache = cipher.doFinal(plainTextData, offset, inputLen - offset);
				}
				out.write(cache, 0 , cache.length);
				i++;
				offset = i * 117;
			}
			
			byte[] output = out.toByteArray();
			out.close();
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}
	
	//私钥解密：私钥对象+密文字节数组->明文字节数组
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用RSA实例化cipher对象
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			int inputLen = cipherData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offset = 0;
			byte[] cache;
			int i = 0;
			//分段解密
			while(inputLen - offset > 0){
				if(inputLen - offset > 128){
					cache = cipher.doFinal(cipherData, offset,128);
				}else{
					cache = cipher.doFinal(cipherData, offset, inputLen - offset);
				}
				out.write(cache, 0 , cache.length);
				i++;
				offset = i * 128;
			}
			
			byte[] output = out.toByteArray();
			out.close();
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}
}
