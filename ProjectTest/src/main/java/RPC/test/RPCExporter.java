/**     
 * @FileName: RPCExporter.java   
 * @Package:RPC.test   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月6日 下午1:22:55   
 * @version V1.0     
 */
package RPC.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**  
 * @ClassName: RPCExporter   
 * @Description: RPC服务端发布者代码实现
 * 作为服务端，监听客户端的tcp连接操作，接受到新的客户端连接后，将其封装成task对象，由线程池操作
 * 将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
 * 将执行结果通过对象反序列化，通过socket发送给客户端
 * 远程服务调用完成之后，释放socket等连接操作
 * @author: LUCKY  
 * @date:2016年4月6日 下午1:22:55     
 */
public class RPCExporter {

    static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime()
                                 .availableProcessors());

    public static void exporter(String hostname, int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(hostname, port));
        try {
            while (true) {
                executor.execute(new ExporterTask(serverSocket.accept()));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private static class ExporterTask implements Runnable {

        Socket client = null;

        public ExporterTask(Socket client) {
            this.client = client;
        }

        /* (non-Javadoc)   
         *    
         * @see java.lang.Runnable#run()   
         */
        @Override
        public void run() {
            // TODO Auto-generated method stub
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {
                input = new ObjectInputStream(client.getInputStream());
                String interfaceName = input.readUTF();
                Class<?> service = Class.forName(interfaceName);
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Method method = service.getMethod(methodName, parameterTypes);
                Object result = method.invoke(service.newInstance(), arguments);
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
