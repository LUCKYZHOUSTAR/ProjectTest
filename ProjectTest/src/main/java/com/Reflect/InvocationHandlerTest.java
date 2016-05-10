/**
 * 
 */
package com.Reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author LUCKY
 *
 */
public class InvocationHandlerTest implements InvocationHandler {

    private Object dynamicProxy;

    public InvocationHandlerTest(Object obj) {
        this.dynamicProxy = obj;
    }

    /*
     * proxy:　　指代我们所代理的那个真实对象
    method:　　指代的是我们所要调用真实对象的某个方法的Method对象
    args:　　指代的是调用真实对象某个方法时接受的参数
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

    public static void main(String[] args) {
        //动态代理只能代理接口
        UserTestInterface userTest = new UserTest();
        // //我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        InvocationHandlerTest invok = new InvocationHandlerTest(userTest);
        /* 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
        * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
        * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
        * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
        */
        UserTestInterface userTest2 = (UserTestInterface) Proxy.newProxyInstance(
            InvocationHandlerTest.class.getClassLoader(), userTest.getClass().getInterfaces(),
            invok);
        userTest2.run();
        //        userTest2.fly("飞起来了");
    }

}

class UserTest implements UserTestInterface {
    public void run() {
        System.out.println("飞起来了");
    }

    public int fly(String name) {
        System.out.println(name + "飞起来了");
        return 4;
    }
}

interface UserTestInterface {
    public void run();

    public int fly(String name);
}