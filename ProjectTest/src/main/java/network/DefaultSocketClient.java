/*
package network;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.net.SocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSocketClient
{

    public DefaultSocketClient()
    {
    }

    public static String sendReceive(String ip, int port, String xml)
    {
        Socket client;
        ByteArrayOutputStream bos;
        client = null;
        try
        {
            client = createConnection(ip, port);
            byte tmpDat[] = xml.getBytes("GB18030");
            writeWithPreLen(client.getOutputStream(), tmpDat);
        }
        catch(Exception e)
        {
            logger.error("DefaultSocketClient sendReceive Exception, \u53D1\u9001\u5931\u8D25", e);
            closeConnection(client);
            return null;
        }
        bos = new ByteArrayOutputStream();
        String s;
        if(read(client.getInputStream(), bos, 8) > 0)
            break MISSING_BLOCK_LABEL_91;
        logger.error("DefaultSocketClient recv error, \u63A5\u6536\u5F02\u5E38");
        s = null;
        closeConnection(client);
        return s;
        s = new String(bos.toByteArray(), "GB18030");
        closeConnection(client);
        return s;
        IOException e;
        String s1;
        logger.error("DefaultSocketClient sendReceive Exception, \u72B6\u6001\u4E0D\u660E", e);
        s1 = null;
        closeConnection(client);
        return s1;
        Exception exception;
        closeConnection(client);
        throw exception;
    }

    private static Socket createConnection(String host, int port)
        throws IOException
    {
       
        Socket socket = SocketFactory.getDefault().createSocket();
        socket.connect(new InetSocketAddress(host, port), timeout * 1000);
        socket.setSoTimeout(timeout * 1000);
        socket.setTcpNoDelay(true);
        return socket;
    }

    private static void closeConnection(Socket socket)
    {
        if(socket == null)
            return;
        try
        {
            socket.close();
        }
        catch(IOException e)
        {
            logger.error("DefaultSocketClient closeConnection IOException", e);
        }
    }

    private static void writeWithPreLen(OutputStream out, byte data[])
        throws IOException
    {
        byte dataToSend[] = new byte[8 + data.length];
        String preLen = String.format("%08d", new Object[] {
            Integer.valueOf(data.length)
        });
        ByteBuffer bb = ByteBuffer.wrap(dataToSend);
        bb.put(preLen.getBytes());
        bb.put(data);
        bb.compact();
        out.write(bb.array());
        out.flush();
    }

    private static int read(InputStream in, OutputStream out, int preLen)
        throws IOException
    {
        int len = readLen(in, preLen);
        if(len <= 0)
            return 0;
        int count = 0;
        byte buf[] = new byte[1024];
        do
        {
            if(count >= len)
                break;
            int readlen = in.read(buf, 0, Math.min(len - count, 1024));
            if(readlen == -1)
                break;
            out.write(buf, 0, readlen);
            count += readlen;
        } while(true);
        if(count != len)
            throw new IOException((new StringBuilder()).append("data is not receive completed:(").append(len).append(",").append(count).append(")").toString());
        else
            return len;
    }

    private static int readLen(InputStream in, int prelen)
        throws IOException
    {
        if(prelen <= 0)
            return 0;
        byte lendata[] = new byte[prelen];
        int count = 0;
        int off = 0;
        do
        {
            int c;
            if((c = in.read()) == -1)
                break;
            lendata[off++] = (byte)c;
        } while(++count != prelen);
        if(count != prelen)
        {
            return 0;
        } else
        {
            int len = Integer.parseInt(new String(lendata));
            return len;
        }
    }

    private static Logger logger = LoggerFactory.getLogger("");
    static final String defaultCharset = "GB18030";
    private static int timeout = 10;

}


*/