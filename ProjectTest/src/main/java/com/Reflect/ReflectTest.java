package com.Reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class ReflectTest {

    @org.junit.Test
    public void newInstanceTest() throws Exception {
        User obj = (User) Class.forName("com.Reflect.Test").newInstance();
        obj.eat("吃事务");
        System.out.println(obj.fly("haha"));
    }

    @Test
    public void FieldTest() throws Exception {
        //只能获得共有的属性字段操作
        Field[] fields = User.class.getFields();
        //获取所有的方法操作
        Field[] fields2 = User.class.getDeclaredFields();

        Field field0 = User.class.getField("number");
        User user = new User();
        user.setNumber("111");
        field0.set(user, "cc");
        System.out.println(user.getNumber());
        for (Field field : fields) {
            //成员变量名称
            System.out.println(field.getName());
            //成员变量的类型操作
            System.out.println(field.getType());
            //成员变量的值操作
            System.out.println(field.get(user));
            System.out.println(field.getModifiers());
        }

        //获得私有的访问修饰符操作
        Field nameField = User.class.getDeclaredField("name");
        System.out.println(nameField.getName());
        System.out.println(nameField.getType());
        nameField.setAccessible(true);
        nameField.set(user, "超强");
        System.out.println(user.getName());

    }

    @Test
    public void MethodTest() throws Exception {
        //只获取共有的方法
        Method[] methods = User.class.getMethods();
        //获取所有的方法，包括私有的方法
        Method[] methods2 = User.class.getDeclaredMethods();

        User user = new User();

        for (Method method : methods2) {
            System.out.println(method.getName());
            System.out.println("是不是私有的方法呢" + Modifier.isPrivate(method.getModifiers()));
            System.out.println("是不是共有的方法呢" + Modifier.isPublic(method.getModifiers()));
        }

        Method method = User.class.getDeclaredMethod("run");
        method.setAccessible(true);
        method.invoke(user);
        Class[] cArg = new Class[2];
        cArg[0] = String.class;
        cArg[1] = String.class;
        //参数的类型信息
        Method method2 = User.class.getDeclaredMethod("fly", cArg);
        method2.setAccessible(true);
        //设置所有的私有访问的方法都可以访问
        //        AccessibleObject.setAccessible(methods2,true);  
        //设置要调用的参数的信息
        System.out.println(method2.invoke(user, "我很好", "你好吗"));
    }

    //通过PropertyDescriptor修改属性方式
    @Test
    public void PropertyDescriptorTest() throws Exception {
        System.out.println(User.class.getName());
        PropertyDescriptor propDesc = new PropertyDescriptor("name", User.class);
        User user = new User();
        //获取属性字段
        Method writeMethod = propDesc.getWriteMethod();
        System.out.println(writeMethod.getName());
        writeMethod.invoke(user, new Object[] { "哈哈" });
        System.out.println(user.getName());
    }

}
