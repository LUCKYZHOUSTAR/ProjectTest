package network.sercuritySocket.com.lwx.socket;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import test.jiami.User;



/**
 * @author  draem0507@gmail.com
 * @TODO	java�߳̿���֮�� SSL����
 * ��������
 * 1.��ɷ������Կ
 * 2.���������֤��
 * 3.��ɿͻ�����Կ
 * 4.���򿪷�����
 * ����֤��������ο�readme.txt
 * �ο�����:http://chrui.iteye.com/blog/1018778
 * @version 1.0
 * @date 2013-5-7 23:22:45	
 * @update 2013-5-8 10:22:45	
 * @blgos http://www.cnblogs.com/draem0507
 */

public class ServerTest {
	private ServerSocket serverSocket;
	private final static char[] password="123456".toCharArray();
	private SSLContext context;
	URL url = ServerTest.class.getResource("sslsocket.keystore");
    String path = url.toString();
    private InputStream inputStream;
	

	public ServerTest() {
		inputStream=this.getClass().getResourceAsStream("/sslsocket.keystore");
		initContext();
		try {
			//ֱ�����лᱨ javax.net.ssl.SSLException:
			//ServerSocketFactory factory= 	SSLServerSocketFactory.getDefault();
			ServerSocketFactory factory= 	context.getServerSocketFactory();
//			serverSocket = new ServerSocket(10000);
			serverSocket=factory.createServerSocket(10000);
			while (true) {
				Socket socket = serverSocket.accept();
				new ReceiveSocket(socket).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//ssl �����Ķ���ĳ�ʼ��
	private void initContext() {
		try {
			KeyStore store=KeyStore.getInstance("JKS");
			store.load(inputStream, password);
			KeyManagerFactory factory=KeyManagerFactory.getInstance("SunX509");
			factory.init(store,password);
			KeyManager []keyManagers=factory.getKeyManagers();
			context=SSLContext.getInstance("SSL");
			context.init(keyManagers, null	, null);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		new ServerTest();

	}

	private class ReceiveSocket extends Thread {
		private Socket socket;

		public ReceiveSocket(Socket socket) {
			this.socket = socket;
		}

		private ObjectInputStream reader;
		private ObjectOutputStream writer;

		@Override
		public void run() {

			try {
				reader=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				//writer=new ObjectOutputStream(socket.getOutputStream());
				// ��������ѭ�� �����Ϣ
				
					//java.io.EOFException
				Object obj=	reader.readObject();
					if(obj!=null)
					{
						User user =(User)obj;
						System.out.println(user.getPassword());
					}
				//	while (true) {}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (null != reader) {
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (null != writer) {
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	
}
