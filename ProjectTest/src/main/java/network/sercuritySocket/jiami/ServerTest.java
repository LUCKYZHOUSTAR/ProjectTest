/**     
 * @FileName: ServerTest.java   
 * @Package:network.sercuritySocket.jiami   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月31日 上午9:36:17   
 * @version V1.0     
 */
package network.sercuritySocket.jiami;

import java.awt.Stroke;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import test.jiami.User;

/*开发步骤
5  * 1.生成服务端密钥
6  * 2.导出服务端证书
7  * 3.生成客户端密钥
8  * 4.程序开发测试
9  * 关于证书的生成请参考readme.txt
10  * 参考资料:http://chrui.iteye.com/blog/1018778
11  * @version 1.0
12  * @date 2013-5-7 23:22:45    
13  * @update 2013-5-8 10:22:45    
14  * @blgos http://www.cnblogs.com/draem0507
    */
public class ServerTest {

    private ServerSocket        serverSocket;
    private final static char[] password = "123456".toCharArray();
    private SSLContext          context;
    private InputStream         inputStream;

    public ServerTest() {
        inputStream = this.getClass().getResourceAsStream("kserver.keystore");
        initContext();
        try {
            ServerSocketFactory factory = context.getServerSocketFactory();
            serverSocket = factory.createServerSocket(10000);
            while (true) {
                Socket socket = serverSocket.accept();
                new ReceiveSocket(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ssl上下文对象初始化
    private void initContext() {
        try {
//            KeyStore store = KeyStore.getInstance("JKS");
            KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());

            store.load(inputStream, password);
            KeyManagerFactory factory = KeyManagerFactory.getInstance("SunX509");
            factory.init(store, password);
            KeyManager[] keyManagers = factory.getKeyManagers();
            context = SSLContext.getInstance("SSL");
//            TrustManagerFactory trustManagerFactory=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(store);
//            TrustManager[] trustManagers=trustManagerFactory.getTrustManagers();
            context.init(keyManagers, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ReceiveSocket extends Thread {
        private Socket socket;

        public ReceiveSocket(Socket socket) {
            this.socket = socket;
        }

        private ObjectInputStream  reader;
        private ObjectOutputStream writer;

        /* (non-Javadoc)   
         *    
         * @see java.lang.Thread#run()   
         */
        @Override
        public void run() {
            try {
                reader = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                Object obj = reader.readObject();
                if (obj != null) {
                    User user = (User) obj;
                    System.out.println(user.getPassword());
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                if (null != reader) {
                    try {
                        reader.close();
                    } catch (Exception e2) {
                        // TODO: handle exception
                    }
                }

                if (writer != null) {
                    try {
                        writer.close();
                    } catch (Exception e2) {
                        // TODO: handle exception
                    }
                }

                try {
                    socket.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                }
            }
        }

    }

    
    public static void main(String[] args) {
        new ServerTest();
    }
}
