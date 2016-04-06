/**     
 * @FileName: RpcImporter.java   
 * @Package:RPC.test   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月6日 下午1:35:28   
 * @version V1.0     
 */
package RPC.test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**  
 * @ClassName: RpcImporter   
 * @Description: 本地服务代理
 * 将本地接口转换成JDK的动态代理，在动态代理中时间接口的远程调用
 * 创建socket客户端操作，根据指定地址连接远程服务提供者
 * 将远程服务调用所需要的接口类，方法名，参数列表等编码后发送给服务 提供者，
 * 同步阻塞等待服务端返回应答，获取应答之后返回
 * @author: LUCKY  
 * @date:2016年4月6日 下午1:35:28     
 */
public class RpcImporter<S> {
    @SuppressWarnings("unchecked")
    public S importer(final Class<?> serviceClass, final InetSocketAddress addr) {
        return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(),
            new Class<?>[] { serviceClass.getInterfaces()[0] }, new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] arg2) throws Throwable {
                    // TODO Auto-generated method stub
                    Socket socket = null;
                    ObjectOutputStream output = null;
                    ObjectInputStream input = null;
                    try {
                        socket = new Socket();
                        socket.connect(addr);
                        output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeUTF(serviceClass.getName());
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(arg2);
                        input = new ObjectInputStream(socket.getInputStream());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return input.readObject();
                }
            });

    }

}
