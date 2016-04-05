package network.sercuritySocket.jiami;
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
                                                .getResourceAsStream("tclient.keystore");

    public static void main(String[] args) {

        try {
            KeyStore ts = KeyStore.getInstance(KeyStore.getDefaultType());
            ts.load(inputStream, password);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);
            TrustManager[] tm = tmf.getTrustManagers();
            context = SSLContext.getInstance("SSL");
            context.init(null, tm, null);

            SocketFactory factory = context.getSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 10000);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            User user = new User("sdfas", "sdfas");
            out.writeObject(user);
            out.flush();

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}