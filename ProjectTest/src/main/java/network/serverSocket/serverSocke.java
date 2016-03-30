/**     
 * @FileName: serverSocke.java   
 * @Package:network.serverSocket   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月30日 上午10:06:20   
 * @version V1.0     
 */
package network.serverSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ServerSocketFactory;
import javax.print.attribute.standard.MediaSize.Other;

import EffectiveJava.Item8.publicTest;

/**  
 * @ClassName: serverSocke   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月30日 上午10:06:20     
 */
public class serverSocke {

    public static void main(String[] args) {

    }

    /**   
     * @Title: Test   
     * @Description: 
     * @param   服务端在接受到请求后，处理完后直接关闭客户端的Socket操作
     * @return void  
     * @throws   
     */
    public void Test() {

        ServerSocket serverSocket;
        Socket connection = null;
        try {
            serverSocket = new ServerSocket(80);
            connection = serverSocket.accept();
            OutputStream out = connection.getOutputStream();
            Writer writer = new OutputStreamWriter(out);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    /**   
     * @Title: Test2   
     * @Description: 
     * @param   循环轮训的请求操作
     * @return void  
     * @throws   
     */
    public void Test2() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8900);
            while (true) {
                Socket connection = null;
                try {
                    connection = server.accept();
                    Writer out = new OutputStreamWriter(connection.getOutputStream());
                    Date now = new Date();
                    out.write(now.toString());
                    out.flush();
                    connection.close();
                } catch (IOException e) {
                    //仅仅这个请求，忽略
                } finally {
                    if (connection != null) {
                        connection.close();
                    }
                }
            }
        } catch (IOException e) {
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**   
     * @Title: Test3   
     * @Description: 
     * @param   外部的try会捕获在daytime端口上构造的产生的任何的IOExcetption异常。accept方法在一个无线循环中调用来监视新的连接操作。与很多
     * 服务一样，这个服务不会终止，而是会继续监听，直到抛出一个异常或者手动让它停止为止
     * @return void  
     * @throws   
     */
    public void Test3() {
        try (ServerSocket server = new ServerSocket(2345)) {
            while (true) {
                try (Socket connection = server.accept()) {
                    Writer out = new OutputStreamWriter(connection.getOutputStream());
                    out.write(new Date().toString());
                    out.flush();
                    //处理完后，才关闭连接操作
                    connection.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    /**   
     * @Title: Test4   
     * @Description: 
     * @param   由于每次服务器端处理的时候都会堵住操作，因此可以采取多线程的方式来
     * 处理每个连接的操作。客户端每次请求的时候，服务器端都会把客户端请求放置到队列中
     * 每次accept的时候都是从队列中来获取一个请求操作。默认的队列长度为50。如果队列值
     * 超过的话，会拒绝连接的请求操作，直到队列腾出新的空间
     * @return void  
     * @throws   
     */
    public void Test4() {

        try (ServerSocket server = new ServerSocket(2323)) {
            while (true) {
                try {
                    Socket connection = server.accept();
                    DayTimeThead dayTimeThead = new DayTimeThead(connection);
                    dayTimeThead.start();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private class DayTimeThead extends Thread {
        private Socket connecion;

        DayTimeThead(Socket connection) {
            this.connecion = connection;
        }

        /* (non-Javadoc)   
         *    
         * @see java.lang.Thread#run()   
         */
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //每个线程处理单独的连接请求处理
            try {
                Writer out = new OutputStreamWriter(connecion.getOutputStream());
                out.write(new Date().toGMTString());
                out.flush();
                connecion.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (connecion != null) {
                    try {
                        //处理完后，关闭连接
                        connecion.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    /**   
     * @Title: Test5   
     * @Description: 
     * @param   下面采用线程池框架来做
     * @return void  
     * @throws   
     */
    public void Test5() {

        ExecutorService executor = Executors.newFixedThreadPool(50);
        try (ServerSocket server = ServerSocketFactory.getDefault().createServerSocket(3830)) {
            while (true) {
                try {
                    Socket socket = server.accept();
                    executor.submit(new daytimeTask(socket));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public class daytimeTask implements Callable<Void> {

        private Socket socket;

        public daytimeTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public Void call() throws Exception {
            try {
                Writer out = new OutputStreamWriter(socket.getOutputStream());
                out.write(new Date().toString());
                out.flush();
            } catch (IOException e) {
            } finally {
                if (socket != null) {
                    socket.close();
                }
            }
            return null;
        }

    }

    public void Test6(ServerSocket ss) {
        //判断是否关闭操作
        boolean b = ss.isBound() && !ss.isClosed();
        //将ConectionTimme设置为5，latency设置为3，bandwidth设置为3，表示超时时间是最重要的特性。最小延迟最不重要
        ss.setPerformancePreferences(5, 3, 2);
    }
}
