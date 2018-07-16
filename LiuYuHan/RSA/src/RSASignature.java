import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSASignature {
	//签名算法：字符串内容+字符串私钥->签名结果的字符串
	public static String sign(String content, String privateKey)
	{
        try 
        {
			//转换私钥材料
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	//实例化密钥工厂
			KeyFactory keyf = KeyFactory.getInstance("RSA");
        	//取私钥对象
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            //实例化Signature
			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
            //初始化Signature
			signature.initSign(priKey);
            //更新
			signature.update( content.getBytes());
            //签名
			byte[] signed = signature.sign();
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        return null;
    }
	//验签算法：字符串内容+字符串签名后的结果+字符串私钥->签名结果的字符串
	public static boolean doCheck(String content, String sign, String publicKey)
	{
		try 
		{
			//实例化密钥工厂
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        //转换为公钥对象
			byte[] encodedKey = Base64.decode(publicKey);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
			//验签
			signature.initVerify(pubKey);
			signature.update( content.getBytes() );	
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
}
