һ�������������Կ
	keytool.exe -genkeypair -v -alias sslsocket -keyalg RSA -keystore e:\sslsocket.keystore 
	
	�������½��:
		����keystore���룺
		�ٴ�����������:
		����������������ʲô��
		  [Unknown]��  lwx
		������֯��λ������ʲô��
		  [Unknown]��  newland
		������֯������ʲô��
		  [Unknown]��  bomc
		�����ڵĳ��л�����������ʲô��
		  [Unknown]��  fz
		�����ڵ��ݻ�ʡ��������ʲô��
		  [Unknown]��  fj
		�õ�λ������ĸ���Ҵ�����ʲô
		  [Unknown]��  zh
		CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh ��ȷ��
		  [��]��  y
		
		����Ϊ���¶������� 1,024 λ RSA ��Կ�Ժ���ǩ��֤�� (SHA1withRSA)����Ч��Ϊ 90 ��
		��:
		         CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh
		����<sslsocket>��������
		        ������� keystore ������ͬ�����س�����
		[���ڴ洢 e:\sslsocket.keystore]
		
�鿴���ɵ���Կ��Ϣ
	keytool -list  -v -keystore e:\sslsocket.keystore -storepass 123456		
	
	�������½��:
		Keystore ���ͣ� JKS
		Keystore �ṩ�ߣ� SUN
		
		���� keystore ���� 1 ����
		
		�������ƣ� sslsocket
		�������ڣ� 2013-5-8
		������: PrivateKeyEntry
		��֤�����ȣ� 1
		��֤ [1]:
		������:CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh
		ǩ����:CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh
		���к�:5189a30d
		��Ч��: Wed May 08 08:57:49 CST 2013 ��Tue Aug 06 08:57:49 CST 2013
		֤��ָ��:
		         MD5:51:5E:1A:57:1B:B9:18:3A:9B:05:F7:13:E5:06:AB:F0
		         SHA1:11:0E:C8:8B:46:1F:27:FA:12:95:95:4E:1E:29:E7:27:50:2E:E9:48
		         ǩ���㷨����:SHA1withRSA
		         �汾: 3
		
		
		*******************************************
		*******************************************
		
�������ɷ����֤��
	keytool.exe -exportcert -v -alias sslsocket -file e:\sslsocket.cer -keystore e:\sslsocket.keystore

�鿴֤����Ϣ
	keytool.exe -printcert -v -file e:\sslsocket.cer
	
	��ӡ���
		������:CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh
		ǩ����:CN=lwx, OU=newland, O=bomc, L=fz, ST=fj, C=zh
		���к�:5189a30d
		��Ч��: Wed May 08 08:57:49 CST 2013 ��Tue Aug 06 08:57:49 CST 2013
		֤��ָ��:
		         MD5:51:5E:1A:57:1B:B9:18:3A:9B:05:F7:13:E5:06:AB:F0
		         SHA1:11:0E:C8:8B:46:1F:27:FA:12:95:95:4E:1E:29:E7:27:50:2E:E9:48
		         ǩ���㷨����:SHA1withRSA
		         �汾: 3
		         
�������ɿͻ�����Կ
	keytool.exe -importcert -v -alias sslsocketcer -file e:\sslsocket.cer -keystore e:\sslclient.keystore

�ġ����г��򿪷�

����keytool�ĸ��౾����
	http://blog.chinaunix.net/uid-17102734-id-2830223.html
				         
		         
		
		
