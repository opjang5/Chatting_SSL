public class Test {
	 
	public static void main(String[] args) throws Exception {
		String filepath="./";	//指定路径
		RSAEncrypt.genKeyPair(filepath);	//生成密钥对文件
		String prikey = RSAEncrypt.loadPrivateKeyByFile(filepath);	//获取私钥字符串
		String pubkey = RSAEncrypt.loadPublicKeyByFile(filepath);	//获取公钥字符串
		System.out.println(prikey);	//打印私钥字符串
		System.out.println(pubkey);	//打印公钥字符串
		System.out.println();
		
		//注意加解密都是要传参数：密钥对象与字节数组，密文是用了Base64编码的，所以记得加密之后要编码，解密时在传入参数中要解码
		System.out.println("--------------公钥加密私钥解密过程-------------------");
		String plainText="公钥加密，私钥解密";
		//公钥加密过程
		byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),plainText.getBytes());	//涉及到比较多的函数，要看清楚！
		String cipher=Base64.encode(cipherData);	//千万别忘了，得到的字符数组必须要用Base64编码成字符串
		//私钥解密过程
		byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));	//涉及到比较多的函数，要看清楚！
		String restr=new String(res);
		
		System.out.println("原文："+plainText);
		System.out.println("加密："+cipher);
		System.out.println("解密："+restr);
		System.out.println();
		
		System.out.println("--------------私钥加密公钥解密过程-------------------");
		plainText="私钥加密，公钥解密";
		//私钥加密过程
		cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)),plainText.getBytes());	//涉及到比较多的函数，要看清楚！
		cipher=Base64.encode(cipherData);	//千万别忘了，得到的字符数组必须要用Base64编码成字符串
		//公钥解密过程
		res=RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)), Base64.decode(cipher));	//涉及到比较多的函数，要看清楚！
		restr=new String(res);
		System.out.println("原文："+plainText);
		System.out.println("加密："+cipher);
		System.out.println("解密："+restr);
		System.out.println();
		
		//注意了，签名的时候，传入的参数是内容字符串，私钥字符串，得到的是签名结果的字符串
		System.out.println("---------------私钥签名过程------------------");
		String content="这是用于签名的原始数据";
		String signstr=RSASignature.sign(content,RSAEncrypt.loadPrivateKeyByFile(filepath));	//签名算法：字符串内容+字符串私钥->签名结果的字符串
		System.out.println("签名原串："+content);
		System.out.println("签名串："+signstr);
		System.out.println();
		
		System.out.println("---------------公钥校验签名------------------");
		System.out.println("签名原串："+content);
		System.out.println("签名串："+signstr);
		
		System.out.println("验签结果："+RSASignature.doCheck(content, signstr, RSAEncrypt.loadPublicKeyByFile(filepath)));	//验签算法：字符串内容+字符串签名后的结果+字符串私钥->签名结果的字符串
		System.out.println();
		}
	}
