/**     
 * @FileName: SocketClient.java   
 * @Package:network.socket.util   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月13日 下午8:02:53   
 * @version V1.0     
 */
package network.socket.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import javax.net.SocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @ClassName: SocketClient   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月13日 下午8:02:53     
 */
public class SocketClient {
    private static Logger logger  = LoggerFactory.getLogger(SocketClient.class);
    private int           timeout = 10;

    public String sendXML(String ip, int port, String xml) {
        Socket client = null;
        try {
            client = createConnection(ip, port);
            byte[] tmpData = xml.getBytes("GB18030");
            writeWithPreLen(client.getOutputStream(), tmpData);
        } catch (IOException e) {
            logger.error("socket client sendXML ioexception", e);
            closeConnection(client);
            return null;
        }

        //开始进行读取的操作
        //由于最后需要转换成 字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {

            if (read(client.getInputStream(), bos, 8) <= 0) {
                logger.error("socketclient received data error");
                return null;
            }

            return new String(bos.toByteArray(), "GB18030");
        } catch (Exception e) {
            logger.error("socketclient send data ioexception,状态不明");
            return null;

        } finally {
            closeConnection(client);
        }

    }

    private Socket createConnection(String host, int port) {
        Socket socket = null;
        try {
            socket = SocketFactory.getDefault().createSocket(host, port);
            socket.setSoTimeout(timeout * 1000);
            socket.setTcpNoDelay(true);
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket;
    }

    private void closeConnection(Socket client) {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                logger.error("Socket closeConnection IOException", e);
            }
        }
    }

    private void writeWithPreLen(OutputStream output, byte[] data) throws IOException {
        byte[] sendData = new byte[8 + data.length];
        String preLen = String.format("%08d", new Object[]{data.length});
        ByteBuffer buffer = ByteBuffer.wrap(sendData);
        buffer.put(preLen.getBytes());
        buffer.put(data);
        buffer.compact();
        output.write(buffer.array());
        output.flush();
    }

    /**   
     * @Title: read   
     * @Description: 用8个字节来表示消息体的长度
     * @param @param in
     * @param @param out
     * @param @param preLen
     * @param @return
     * @param @throws IOException  
     * @return int  
     * @throws   
     */
    private int read(InputStream in, OutputStream out, int preLen) throws IOException {
        //需要先把头给读取了，接着读取消息体的内容
        //len代表消息体的长度
        int len = readLen(in, preLen);
        if (len <= 0) {
            return -1;
        }

        byte[] buffer = new byte[1024];
        int count = 0;
        //消息体为8M,也就是8388608个字节
        //第一次从0读到
        //第二次从
        while (count < len) {
            int readLen = in.read(buffer, 0, Math.min(len - count, 1024));
            out.write(buffer, 0, readLen);
            count += readLen;
        }

        if (count != len) {
            throw new IOException("data is not receive completed:(" + len + "," + count + ")");
        }

        return len;
    }

    /**   
     * @Title: readLen   
     * @Description: 前八个字节代表消息的长度，用来获取消息的长度
     * @param @param in
     * @param @param preLen
     * @param @return
     * @param @throws IOException  
     * @return int  
     * @throws   
     */
    private int readLen(InputStream in, int preLen) throws IOException {
        //写代码的时候，也需要对值进行验证
        if (preLen <= 0) {
            return -2;
        }
        byte[] buffer = new byte[preLen];
        int count = 0;
        while (count <= preLen) {
            buffer[count] = (byte) in.read();
            count++;
        }

        if (count != preLen) {
            return -1;
        }
        int len = Integer.parseInt(new String(buffer));
        return len;
    }
}
