package network.sercuritySocket.com.lwx.socket;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.security.KeyStore;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import test.jiami.User;

public class ClientTest {
    private final static char[] password    = "123456".toCharArray();
    private static SSLContext   context;
    static InputStream          inputStream = ClientTest.class
                                                .getResourceAsStream("sslclient.keystore");

    public static void main(String[] args) throws Exception {
        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(inputStream, password);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ts);
        TrustManager[] tm = tmf.getTrustManagers();
        context = SSLContext.getInstance("SSL");
        context.init(null, tm, null);

        //SocketFactory factory=	SSLSocketFactory.getDefault();
        //Socket socket =factory.createSocket("localhost", 10000);
        SocketFactory factory = context.getSocketFactory();
        SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 10000);

        //ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        User user = new User("sdf", "sdf");
        out.writeObject(user);
        out.flush();

        socket.close();

    }
}
