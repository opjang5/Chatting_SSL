﻿1. UserA用DH的Generateg(x)方法生成x位的大素数a, p, g;
2. UserA计算 A = DH.Momi(g, a, p) 
2. UserA向B发送大素数p, g, A
3. UserB用 DH.Generateg(x) 方法生成大素数 b
4. UserB计算 B = DH,Momi(g, b, p)
4. UserB向A发送B
5. UserA方得到协商好的密钥：DH.Momi(B,a,p)
6. UserB方得到协商好的密钥：DH.Momi(A,b,p)

sc.sendMsg("ask@@"+userName+"@@"+name+"@@"+p+"@@"+g+"@@"+A);
