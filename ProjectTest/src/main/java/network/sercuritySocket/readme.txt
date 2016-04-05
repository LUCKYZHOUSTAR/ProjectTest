一、创建服务端密钥
	keytool.exe -genkeypair -v -alias sslsocket -keyalg RSA -keystore e:\sslsocket.keystore 
	
	出现如下结果:
		输入keystore密码：
		再次输入新密码:
		您的名字与姓氏是什么？
		  [Unknown]：  lwx
		您的组织单位名称是什么？
		  [Unknown]：  newland
		您的组织名称是什么？
		  [Unknown]：  bomc
		您所在的城市或区域名称是什么？
		  [Unknown]：  fz
		您所在的州或省份名称是什么？
		  [Unknown]：  fj
		该单位的两字母国家代码是什么
		  [Unknown]：  zh
		CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh 正确吗？
		  [否]：  y
		
		正在为以下对象生成 1,024 位 RSA 密钥对和自签名证书 (SHA1withRSA)（有效期为 90 天
		）:
		         CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh
		输入<sslsocket>的主密码
		        （如果和 keystore 密码相同，按回车）：
		[正在存储 e:\sslsocket.keystore]
		
查看生成的密钥信息
	keytool -list  -v -keystore e:\sslsocket.keystore -storepass 123456		
	
	出现如下结果:
		Keystore 类型： JKS
		Keystore 提供者： SUN
		
		您的 keystore 包含 1 输入
		
		别名名称： sslsocket
		创建日期： 2013-5-8
		项类型: PrivateKeyEntry
		认证链长度： 1
		认证 [1]:
		所有者:CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh
		签发人:CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh
		序列号:5189a30d
		有效期: Wed May 08 08:57:49 CST 2013 至Tue Aug 06 08:57:49 CST 2013
		证书指纹:
		         MD5:51:5E:1A:57:1B:B9:18:3A:9B:05:F7:13:E5:06:AB:F0
		         SHA1:11:0E:C8:8B:46:1F:27:FA:12:95:95:4E:1E:29:E7:27:50:2E:E9:48
		         签名算法名称:SHA1withRSA
		         版本: 3
		
		
		*******************************************
		*******************************************
		
二、生成服务端证书
	keytool.exe -exportcert -v -alias sslsocket -file e:\sslsocket.cer -keystore e:\sslsocket.keystore

查看证书信息
	keytool.exe -printcert -v -file e:\sslsocket.cer
	
	打印结果
		所有者:CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh
		签发人:CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh
		序列号:5189a30d
		有效期: Wed May 08 08:57:49 CST 2013 至Tue Aug 06 08:57:49 CST 2013
		证书指纹:
		         MD5:51:5E:1A:57:1B:B9:18:3A:9B:05:F7:13:E5:06:AB:F0
		         SHA1:11:0E:C8:8B:46:1F:27:FA:12:95:95:4E:1E:29:E7:27:50:2E:E9:48
		         签名算法名称:SHA1withRSA
		         版本: 3
		         
三、生成客户端密钥
	keytool.exe -importcert -v -alias sslsocketcer -file e:\sslsocket.cer -keystore e:\sslclient.keystore

四、进行程序开发

关于keytool的更多本资料
	http://blog.chinaunix.net/uid-17102734-id-2830223.html
				         
		         
		
		
