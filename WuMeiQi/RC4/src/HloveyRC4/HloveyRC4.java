package HloveyRC4;

public class HloveyRC4 {
	/**
	 * RC4�ļ��ܺͽ��ܶ��ǵ���RC4()���� 
	 * aInput����Ҫ����/���ܵ�����
	 * aKey����Կ
	 * @param aInput
	 * @param aKey
	 * @return
	 */
	public static String RC4(String aInput,String aKey)   
    {   
        int[] iS = new int[256];   
        byte[] iK = new byte[256];   
          
        //��ʼ��S��
        for (int i=0;i<256;i++)   
            iS[i]=i;   
              
        int j = 1;   
          
        for (short i= 0;i<256;i++)   
        {   //charAt���ش���indexλ���ϵ��ַ�
        	//����ԿaKey��ʼ��K��
            iK[i]=(byte)aKey.charAt((i % aKey.length()));   
        }   
          
        j=0;   
          
        //��K�в���S�еĳ�ʼ�û�
        for (int i=0;i<255;i++)   
        {   
            j=(j+iS[i]+iK[i]) % 256;   
            int temp = iS[i];   
            iS[i]=iS[j];   
            iS[j]=temp;   
        }   
      
      
        int i=0;   
        j=0;   
        //���볤��=�������
        char[] iInputChar = aInput.toCharArray();   
        char[] iOutputChar = new char[iInputChar.length];   
        for(short x = 0;x<iInputChar.length;x++)   
        {   
            i = (i+1) % 256;   
            j = (j+iS[i]) % 256;   //����J
            //����Si��Sj
            int temp = iS[i];   
            iS[i]=iS[j];   
            iS[j]=temp;   
            int t = (iS[i]+iS[j]) % 256;//�õ�t   
            int iY = iS[t];//�õ���������Ľ��
            char iCY = (char)iY;   
            iOutputChar[x] =(char)( iInputChar[x] ^ iCY) ;//���õ�����������������      
        }   
          
        return new String(iOutputChar);   
                  
    }  
	
}
