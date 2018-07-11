package HloveyRC4;

public class HloveyRC4 {
	/**
	 * RC4的加密和解密都是调用RC4()函数 
	 * aInput是需要加密/解密的内容
	 * aKey是密钥
	 * @param aInput
	 * @param aKey
	 * @return
	 */
	public static String RC4(String aInput,String aKey)   
    {   
        int[] iS = new int[256];   
        byte[] iK = new byte[256];   
          
        //初始化S盒
        for (int i=0;i<256;i++)   
            iS[i]=i;   
              
        int j = 1;   
          
        for (short i= 0;i<256;i++)   
        {   //charAt返回处于index位置上的字符
        	//用密钥aKey初始化K盒
            iK[i]=(byte)aKey.charAt((i % aKey.length()));   
        }   
          
        j=0;   
          
        //用K盒产生S盒的初始置换
        for (int i=0;i<255;i++)   
        {   
            j=(j+iS[i]+iK[i]) % 256;   
            int temp = iS[i];   
            iS[i]=iS[j];   
            iS[j]=temp;   
        }   
      
      
        int i=0;   
        j=0;   
        //输入长度=输出长度
        char[] iInputChar = aInput.toCharArray();   
        char[] iOutputChar = new char[iInputChar.length];   
        for(short x = 0;x<iInputChar.length;x++)   
        {   
            i = (i+1) % 256;   
            j = (j+iS[i]) % 256;   //计算J
            //交换Si和Sj
            int temp = iS[i];   
            iS[i]=iS[j];   
            iS[j]=temp;   
            int t = (iS[i]+iS[j]) % 256;//得到t   
            int iY = iS[t];//得到最终输出的结果
            char iCY = (char)iY;   
            iOutputChar[x] =(char)( iInputChar[x] ^ iCY) ;//将得到的流密码和明文异或      
        }   
          
        return new String(iOutputChar);   
                  
    }  
	
}
