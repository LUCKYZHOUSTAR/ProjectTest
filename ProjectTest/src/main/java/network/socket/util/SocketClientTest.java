/**     
 * @FileName: SocketClientTest.java   
 * @Package:network.socket.util   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月14日 上午9:01:26   
 * @version V1.0     
 */
package network.socket.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import javax.net.SocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @ClassName: SocketClientTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月14日 上午9:01:26     
 */
public class SocketClientTest {

    private final static String CHARSET_NAME = "GB18030";
    private final static int    TIMEOUT      = 10;
    private static Logger       logger       = LoggerFactory.getLogger(SocketClientTest.class);

    public String sendXML(String ip, int port, String xml) {

        //创建连接操作
        Socket client = null;
        try {
            //异常统一都在这里捕获
            client = createConnection(ip, port);
            byte[] data = xml.getBytes("utf-8");
            //开始写入操作，单独封装成一个方法
            writeXMLWithPreLen(client.getOutputStream(), data);
        } catch (IOException e) {
            //打印日志的时候，需要把类的名称也给打印出来，方便以后的定位于排查
            logger.error("SocketClientTest writeMXL client IOEXCEPTON", e);
            return null;
        }

        //开始读取操作
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            //closeconnection可以提取出来
            if (read(client.getInputStream(), bos, 8) <= 0) {
                logger.error("socketcliennttest read ioexctption");
                //closeConnection(client);
                return null;
            }

            return new String(bos.toByteArray());
        } catch (Exception e) {
            logger.error("socketclienttest sendXML ioexception");
            //closeConnection(client);
            return null;
        } finally {
            closeConnection(client);
        }
    }

    /**
     * @throws IOException    
     * @Title: writeXMLWithPreLen   
     * @Description: 不需哟啊有返回值进行判断的操作，直接写入即可
     * @param @param out
     * @param @param preLen
     * @param @return  
     * @return int  
     * @throws   
     */
    private void writeXMLWithPreLen(OutputStream out, byte[] data) throws IOException {

        //需要输入8个字节代表长度
        byte[] tempData = new byte[8 + data.length];
        //temdata有任何改变都会反应到buffer中来
        ByteBuffer buffer = ByteBuffer.wrap(tempData);
        //此处是关键的地方
        String len = String.format("%8d", new Object[] { data.length });
        tempData = len.getBytes();
        buffer.put(tempData);
        buffer.put(data);
        buffer.compact();
        out.write(buffer.array());
        out.flush();

    }

    /**   
     * @Title: createConnection   
     * @Description: 创建连接，异常统一都在上层方法来捕获
     * @param @param ip
     * @param @param port
     * @param @return
     * @param @throws IOException  
     * @return Socket  
     * @throws   
     */
    private Socket createConnection(String ip, int port) throws IOException {
        Socket client = SocketFactory.getDefault().createSocket();
        SocketAddress socketAddress = new InetSocketAddress(ip, port);
        client.connect(socketAddress);
        client.setSoTimeout(TIMEOUT * 1000);
        client.setTcpNoDelay(true);
        return client;
    }

    private void closeConnection(Socket client) {
        if (client == null) {
            return;
        }
        try {
            client.close();
        } catch (IOException e) {
            logger.error("close Connection IOException");
        }
    }

    //需要有整数返回值进行判断操作
    private int read(InputStream in, OutputStream out, int preLen) throws IOException {
        //首先需要读取长度，消息的长度字段
        int len = readPreLen(in, preLen);
        if (len <= 0) {
            return -1;
        }
        //开始读取
        int count = 0;
        byte[] tempData = new byte[len];
        while (count < len) {
            //每次读取剩余长度和1024字节之间最小的值
            int readLen = in.read(tempData, 0, Math.min(len - count, 1024));
            //这句话是为了防止服务端关闭连接
            if (readLen == -1) {
                break;
            }
            count += readLen;
            out.write(tempData, 0, readLen);
        }

        //判断消息是否读取完毕
        if (count != len) {
            throw new IOException("data is not receive complete:{" + len + "," + count + ")");
        }
        return len;
    }

    private int readPreLen(InputStream in, int preLen) throws IOException {
        if (preLen <= 0) {
            return -1;
        }
        //前8个字节代表消息的长度
        int count = 0;
        byte[] temData = new byte[preLen];
        while (count <= preLen) {
            temData[count] = (byte) in.read();
            count++;
        }

        if (count != preLen) {
            return -1;
        }

        return Integer.parseInt(new String(temData));

    }
}
