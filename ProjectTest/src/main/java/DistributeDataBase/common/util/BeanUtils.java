/**
 * 
 */
package DistributeDataBase.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/** 
* @ClassName: BeanUtils 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:17:12 
*  
*/
public class BeanUtils {
    public static <T> T instantiateClass(Class<T> clazz) throws Throwable {
        if (clazz == null) {
            throw new IllegalArgumentException("ERROR ## the clazz is null");
        }
        if (clazz.isInterface()) {
            throw new IllegalArgumentException(clazz + " Specified class is an interface");
        }
        return instantiateClass(clazz.getDeclaredConstructor(new Class[0]), new Object[0]);
    }

    public static <T> T instantiateClass(Constructor<T> ctor, Object[] args) throws Throwable {
        if (ctor == null)
            throw new IllegalArgumentException("ERROR ## the ctor is null");
        try {
            ReflectionUtils.makeAccessible(ctor);
            return ctor.newInstance(args);
        } catch (InstantiationException ex) {
            throw new Throwable(ctor.getDeclaringClass() + " Is it an abstract class?", ex);
        } catch (IllegalAccessException ex) {
            throw new Throwable(ctor.getDeclaringClass() + " Is the constructor accessible?", ex);
        } catch (IllegalArgumentException ex) {
            throw new Throwable(ctor.getDeclaringClass() + " Illegal arguments for constructor", ex);
        } catch (InvocationTargetException ex) {
            throw new Throwable(ctor.getDeclaringClass() + "Constructor threw exception",
                ex.getTargetException());
        }
    }
}
