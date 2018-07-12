
public class Test {
	public static void main(String[] args) throws Exception {
		String filepath = "./";	//指定路径
		RSA.genKeyPair(filepath);	//生成密钥对文件
		//获取公钥的字符串形式
		String pubkey =  RSA.getPubKey(filepath);
		System.out.println(pubkey);
		//获取私钥的字符串形式
		String prikey =  RSA.getPriKey(filepath);
		System.out.println(prikey);
		//公钥加密
		String cipher =  RSA.pubEncrypt(pubkey,"This is plainText");
		System.out.println(cipher);
		//私钥解密
		String res =  RSA.priDecrypt(prikey, cipher);
		System.out.println(res);
		//私钥加密
		cipher =  RSA.priEncrypt(prikey,"This is plainText");
		System.out.println(cipher);
		//公钥解密
		res =  RSA.pubDecrypt(pubkey, cipher);
		System.out.println(res);
		//签名
		String sign = RSA.sign("Hello,it's me", prikey);
		System.out.println(sign);
		//验签
		boolean check = RSA.doCheck("Hello,it's me", sign, pubkey);
		System.out.println(check);
	}
}
