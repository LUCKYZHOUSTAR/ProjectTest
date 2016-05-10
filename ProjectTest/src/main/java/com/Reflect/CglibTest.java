/**
 * 
 */
package com.Reflect;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.NoOp;

/**
 * @author LUCKY
 *
 */
public class CglibTest {

    public static void main(String[] args) {
        TableDAO tableDao = TableDAOFactory.getInstance();
        doMethod(tableDao);
        TableDAO tDao = TableDAOFactory.getAuthInstance(new AuthProxy("张三"), new CallBackTest(
            new TableDAO()));
        doMethod(tDao);
    }

    public static void doMethod(TableDAO dao) {
        dao.create();
        dao.query();
        dao.update();
        dao.delete();
    }
}

class AuthProxy implements MethodInterceptor {

    private String name;

    public AuthProxy(String name) {
        this.name = name;
    }

    @Override
    public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3)
                                                                                      throws Throwable {
        //用户判断
        if (!"张三".equals(name)) {
            System.out.println("没有权限");
            return null;
        }

        return arg3.invokeSuper(arg0, arg2);
    }

}

//工厂类
class TableDAOFactory {
    private static TableDAO tDao = new TableDAO();

    public static TableDAO getInstance() {
        return tDao;
    }

    public static TableDAO getAuthInstance(AuthProxy authProxy, CallBackTest invocationHandlerTest) {
        //用来生成代理对象
        Enhancer en = new Enhancer();
        //需要进行代理的类
        en.setSuperclass(TableDAO.class);
        //设置需要回调的方法
        //        en.setCallback(invocationHandlerTest);
        //        en.setCallbacks(new Callback[]{});
        en.setCallbacks(new Callback[] { authProxy, NoOp.INSTANCE });
        //设置方法过滤
        en.setCallbackFilter(new AuthProxyFilter());
        //生成代理实例  
        return (TableDAO) en.create();
    }
}

class TableDAO {
    public void create() {
        System.out.println("create() is running !");
    }

    public void query() {
        System.out.println("query() is running !");
    }

    public void update() {
        System.out.println("update() is running !");
    }

    public void delete() {
        System.out.println("delete() is running !");
    }
}

//CallbackFilte可以明确表明，被代理的类中不同的方法， 
class AuthProxyFilter implements CallbackFilter {
    public int accept(Method arg0) {
        if (!"query".equalsIgnoreCase(arg0.getName()))
            return 0;
        return 1;
    }

}

class CallBackTest implements InvocationHandler {

    private Object dynamicProxy;

    public CallBackTest(Object obj) {
        this.dynamicProxy = obj;
    }

    /* (non-Javadoc)
     * @see org.springframework.cglib.proxy.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public Object invoke(Object obj, Method method, Object[] aobj) throws Throwable {
        //在调用真实对象之前添加一些自己的东西
        System.out.println("调用之前一些处理工作");
        //Method:public abstract void com.Reflect.UserTestInterface.run()
        System.out.println("Method:" + method);
        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        method.invoke(dynamicProxy, aobj);
        //　　在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("在之后也可以做一些处理性的工作");
        return null;
    }

}