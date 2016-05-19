package DistributeDataBase.common.util;

import java.beans.Introspector;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


//TODO 有错误  2016年5月17日 18:43:12
public class ClassUtils
{
  public static final String ARRAY_SUFFIX = "[]";
  private static final String INTERNAL_ARRAY_PREFIX = "[";
  private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";
  private static final char PACKAGE_SEPARATOR = '.';
  private static final char INNER_CLASS_SEPARATOR = '$';
  public static final String CGLIB_CLASS_SEPARATOR = "$$";
  public static final String CLASS_FILE_SUFFIX = ".class";
  private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap(8);

  private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new HashMap(8);

  private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap(32);

  private static final Map<String, Class<?>> commonClassCache = new HashMap(32);

  private static void registerCommonClasses(Class<?>[] commonClasses)
  {
    for (Class clazz : commonClasses)
      commonClassCache.put(clazz.getName(), clazz);
  }

  public static ClassLoader getDefaultClassLoader()
  {
    ClassLoader cl = null;
    try {
      cl = Thread.currentThread().getContextClassLoader();
    }
    catch (Throwable ex) {
    }
    if (cl == null)
    {
      cl = ClassUtils.class.getClassLoader();
    }
    return cl;
  }

  public static ClassLoader overrideThreadContextClassLoader(ClassLoader classLoaderToUse)
  {
    Thread currentThread = Thread.currentThread();
    ClassLoader threadContextClassLoader = currentThread.getContextClassLoader();
    if ((classLoaderToUse != null) && (!classLoaderToUse.equals(threadContextClassLoader))) {
      currentThread.setContextClassLoader(classLoaderToUse);
      return threadContextClassLoader;
    }
    return null;
  }

  @Deprecated
  public static Class<?> forName(String name)
    throws ClassNotFoundException, LinkageError
  {
    return forName(name, getDefaultClassLoader());
  }

  public static Class<?> forName(String name, ClassLoader classLoader)
    throws ClassNotFoundException, LinkageError
  {
    if (name == null) {
      throw new IllegalArgumentException("Name must not be null");
    }
    Class clazz = resolvePrimitiveClassName(name);
    if (clazz == null) {
      clazz = (Class)commonClassCache.get(name);
    }
    if (clazz != null) {
      return clazz;
    }

    if (name.endsWith("[]")) {
      String elementClassName = name.substring(0, name.length() - "[]".length());
      Class elementClass = forName(elementClassName, classLoader);
      return Array.newInstance(elementClass, 0).getClass();
    }

    if ((name.startsWith("[L")) && (name.endsWith(";"))) {
      String elementName = name.substring("[L".length(), name.length() - 1);

      Class elementClass = forName(elementName, classLoader);
      return Array.newInstance(elementClass, 0).getClass();
    }

    if (name.startsWith("[")) {
      String elementName = name.substring("[".length());
      Class elementClass = forName(elementName, classLoader);
      return Array.newInstance(elementClass, 0).getClass();
    }

    ClassLoader classLoaderToUse = classLoader;
    if (classLoaderToUse == null)
      classLoaderToUse = getDefaultClassLoader();
    try
    {
      return classLoaderToUse.loadClass(name);
    } catch (ClassNotFoundException ex) {
      int lastDotIndex = name.lastIndexOf(46);
      if (lastDotIndex != -1) {
        String innerClassName = new StringBuilder().append(name.substring(0, lastDotIndex)).append('$').append(name.substring(lastDotIndex + 1)).toString();
        try
        {
          return classLoaderToUse.loadClass(innerClassName);
        }
        catch (ClassNotFoundException ex2) {
        }
      }
      throw ex;
    }
  }

  public static Class<?> resolveClassName(String className, ClassLoader classLoader)
    throws IllegalArgumentException
  {
    try
    {
      return forName(className, classLoader);
    } catch (ClassNotFoundException ex) {
      throw new IllegalArgumentException(new StringBuilder().append("Cannot find class [").append(className).append("]").toString(), ex);
    } catch (LinkageError ex) {
      throw new IllegalArgumentException(new StringBuilder().append("Error loading class [").append(className).append("]: problem with class file or dependent class.").toString(), ex);
    }
  }

  public static Class<?> resolvePrimitiveClassName(String name)
  {
    Class result = null;

    if ((name != null) && (name.length() <= 8))
    {
      result = (Class)primitiveTypeNameMap.get(name);
    }
    return result;
  }

  @Deprecated
  public static boolean isPresent(String className)
  {
    return isPresent(className, getDefaultClassLoader());
  }

  public static boolean isPresent(String className, ClassLoader classLoader)
  {
    try
    {
      forName(className, classLoader);
      return true;
    } catch (Throwable ex) {
    }
    return false;
  }

  public static Class<?> getUserClass(Object instance)
  {
    if (instance == null) {
      throw new IllegalArgumentException("Instance must not be null");
    }
    return getUserClass(instance.getClass());
  }

  public static Class<?> getUserClass(Class<?> clazz)
  {
    if ((clazz != null) && (clazz.getName().contains("$$"))) {
      Class superClass = clazz.getSuperclass();
      if ((superClass != null) && (!Object.class.equals(superClass))) {
        return superClass;
      }
    }
    return clazz;
  }

  public static boolean isCacheSafe(Class<?> clazz, ClassLoader classLoader)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    ClassLoader target = clazz.getClassLoader();
    if (target == null) {
      return false;
    }
    ClassLoader cur = classLoader;
    if (cur == target) {
      return true;
    }
    while (cur != null) {
      cur = cur.getParent();
      if (cur == target) {
        return true;
      }
    }
    return false;
  }

  public static String getShortName(String className)
  {
    if (className == null) {
      throw new IllegalArgumentException("Class name must not be empty");
    }
    int lastDotIndex = className.lastIndexOf(46);
    int nameEndIndex = className.indexOf("$$");
    if (nameEndIndex == -1) {
      nameEndIndex = className.length();
    }
    String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
    shortName = shortName.replace('$', '.');
    return shortName;
  }

  public static String getShortName(Class<?> clazz)
  {
    return getShortName(getQualifiedName(clazz));
  }

  public static String getShortNameAsProperty(Class<?> clazz)
  {
    String shortName = getShortName(clazz);
    int dotIndex = shortName.lastIndexOf(46);
    shortName = dotIndex != -1 ? shortName.substring(dotIndex + 1) : shortName;
    return Introspector.decapitalize(shortName);
  }

  public static String getClassFileName(Class<?> clazz)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    String className = clazz.getName();
    int lastDotIndex = className.lastIndexOf(46);
    return new StringBuilder().append(className.substring(lastDotIndex + 1)).append(".class").toString();
  }

  public static String getPackageName(Class<?> clazz)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    String className = clazz.getName();
    int lastDotIndex = className.lastIndexOf(46);
    return lastDotIndex != -1 ? className.substring(0, lastDotIndex) : "";
  }

  public static String getQualifiedName(Class<?> clazz)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    if (clazz.isArray()) {
      return getQualifiedNameForArray(clazz);
    }
    return clazz.getName();
  }

  private static String getQualifiedNameForArray(Class<?> clazz)
  {
    StringBuilder result = new StringBuilder();
    while (clazz.isArray()) {
      clazz = clazz.getComponentType();
      result.append("[]");
    }
    result.insert(0, clazz.getName());
    return result.toString();
  }

  public static String getQualifiedMethodName(Method method)
  {
    if (method == null) {
      throw new IllegalArgumentException("Method must not be null");
    }
    return new StringBuilder().append(method.getDeclaringClass().getName()).append(".").append(method.getName()).toString();
  }

  public static String getDescriptiveType(Object value)
  {
    if (value == null) {
      return null;
    }
    Class clazz = value.getClass();
    if (Proxy.isProxyClass(clazz)) {
      StringBuilder result = new StringBuilder(clazz.getName());
      result.append(" implementing ");
      Class[] ifcs = clazz.getInterfaces();
      for (int i = 0; i < ifcs.length; i++) {
        result.append(ifcs[i].getName());
        if (i < ifcs.length - 1) {
          result.append(',');
        }
      }
      return result.toString();
    }if (clazz.isArray()) {
      return getQualifiedNameForArray(clazz);
    }
    return clazz.getName();
  }

  public static boolean matchesTypeName(Class<?> clazz, String typeName)
  {
    return (typeName != null) && ((typeName.equals(clazz.getName())) || (typeName.equals(clazz.getSimpleName())) || ((clazz.isArray()) && (typeName.equals(getQualifiedNameForArray(clazz)))));
  }

  public static boolean hasConstructor(Class<?> clazz, Class<?>[] paramTypes)
  {
    return getConstructorIfAvailable(clazz, paramTypes) != null;
  }

  public static <T> Constructor<T> getConstructorIfAvailable(Class<T> clazz, Class<?>[] paramTypes)
  {
    if (clazz == null)
      throw new IllegalArgumentException("Class must not be null");
    try
    {
      return clazz.getConstructor(paramTypes); } catch (NoSuchMethodException ex) {
    }
    return null;
  }

  public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes)
  {
    return getMethodIfAvailable(clazz, methodName, paramTypes) != null;
  }

  public static Method getMethodIfAvailable(Class<?> clazz, String methodName, Class<?>[] paramTypes)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    if (methodName == null)
      throw new IllegalArgumentException("Method name must not be null");
    try
    {
      return clazz.getMethod(methodName, paramTypes); } catch (NoSuchMethodException ex) {
    }
    return null;
  }

  public static int getMethodCountForName(Class<?> clazz, String methodName)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    if (methodName == null) {
      throw new IllegalArgumentException("Method name must not be null");
    }
    int count = 0;
    Method[] declaredMethods = clazz.getDeclaredMethods();
    for (Method method : declaredMethods) {
      if (methodName.equals(method.getName())) {
        count++;
      }
    }
    Class[] ifcs = clazz.getInterfaces();
    for (Class ifc : ifcs) {
      count += getMethodCountForName(ifc, methodName);
    }
    if (clazz.getSuperclass() != null) {
      count += getMethodCountForName(clazz.getSuperclass(), methodName);
    }
    return count;
  }

  public static boolean hasAtLeastOneMethodWithName(Class<?> clazz, String methodName)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    if (methodName == null) {
      throw new IllegalArgumentException("Method name must not be null");
    }
    Method[] declaredMethods = clazz.getDeclaredMethods();
    for (Method method : declaredMethods) {
      if (method.getName().equals(methodName)) {
        return true;
      }
    }
    Class[] ifcs = clazz.getInterfaces();
    for (Class ifc : ifcs) {
      if (hasAtLeastOneMethodWithName(ifc, methodName)) {
        return true;
      }
    }
    return (clazz.getSuperclass() != null) && (hasAtLeastOneMethodWithName(clazz.getSuperclass(), methodName));
  }

  public static Method getMostSpecificMethod(Method method, Class<?> targetClass)
  {
    Method specificMethod = null;
    if ((method != null) && (isOverridable(method, targetClass)) && (targetClass != null) && (!targetClass.equals(method.getDeclaringClass())))
    {
      specificMethod = ReflectionUtils.findMethod(targetClass, method.getName(), method.getParameterTypes());
    }

    return specificMethod != null ? specificMethod : method;
  }

  private static boolean isOverridable(Method method, Class targetClass)
  {
    if (Modifier.isPrivate(method.getModifiers())) {
      return false;
    }
    if ((Modifier.isPublic(method.getModifiers())) || (Modifier.isProtected(method.getModifiers()))) {
      return true;
    }
    return getPackageName(method.getDeclaringClass()).equals(getPackageName(targetClass));
  }

  public static Method getStaticMethod(Class<?> clazz, String methodName, Class<?>[] args)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    if (methodName == null)
      throw new IllegalArgumentException("Method name must not be null");
    try
    {
      Method method = clazz.getMethod(methodName, args);
      return Modifier.isStatic(method.getModifiers()) ? method : null; } catch (NoSuchMethodException ex) {
    }
    return null;
  }

  public static boolean isPrimitiveWrapper(Class<?> clazz)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    return primitiveWrapperTypeMap.containsKey(clazz);
  }

  public static boolean isPrimitiveOrWrapper(Class<?> clazz)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    return (clazz.isPrimitive()) || (isPrimitiveWrapper(clazz));
  }

  public static boolean isPrimitiveArray(Class<?> clazz)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    return (clazz.isArray()) && (clazz.getComponentType().isPrimitive());
  }

  public static boolean isPrimitiveWrapperArray(Class<?> clazz)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    return (clazz.isArray()) && (isPrimitiveWrapper(clazz.getComponentType()));
  }

  public static Class<?> resolvePrimitiveIfNecessary(Class<?> clazz)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    return (clazz.isPrimitive()) && (clazz != Void.TYPE) ? (Class)primitiveTypeToWrapperMap.get(clazz) : clazz;
  }

  public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType)
  {
    if (lhsType == null) {
      throw new IllegalArgumentException("Left-hand side type must not be null");
    }
    if (rhsType == null) {
      throw new IllegalArgumentException("Right-hand side type must not be null");
    }
    if (lhsType.isAssignableFrom(rhsType)) {
      return true;
    }
    if (lhsType.isPrimitive()) {
      Class resolvedPrimitive = (Class)primitiveWrapperTypeMap.get(rhsType);
      if ((resolvedPrimitive != null) && (lhsType.equals(resolvedPrimitive)))
        return true;
    }
    else {
      Class resolvedWrapper = (Class)primitiveTypeToWrapperMap.get(rhsType);
      if ((resolvedWrapper != null) && (lhsType.isAssignableFrom(resolvedWrapper))) {
        return true;
      }
    }
    return false;
  }

  public static boolean isAssignableValue(Class<?> type, Object value)
  {
    if (type == null) {
      throw new IllegalArgumentException("Type must not be null");
    }
    return !type.isPrimitive() ? true : value != null ? isAssignable(type, value.getClass()) : false;
  }

  public static String convertResourcePathToClassName(String resourcePath)
  {
    if (resourcePath == null) {
      throw new IllegalArgumentException("Resource path must not be null");
    }
    return resourcePath.replace('/', '.');
  }

  public static String convertClassNameToResourcePath(String className)
  {
    if (className == null) {
      throw new IllegalArgumentException("Class name must not be null");
    }
    return className.replace('.', '/');
  }

  public static String addResourcePathToPackagePath(Class<?> clazz, String resourceName)
  {
    if (resourceName == null) {
      throw new IllegalArgumentException("Resource name must not be null");
    }
    if (!resourceName.startsWith("/")) {
      return new StringBuilder().append(classPackageAsResourcePath(clazz)).append("/").append(resourceName).toString();
    }
    return new StringBuilder().append(classPackageAsResourcePath(clazz)).append(resourceName).toString();
  }

  public static String classPackageAsResourcePath(Class<?> clazz)
  {
    if (clazz == null) {
      return "";
    }
    String className = clazz.getName();
    int packageEndIndex = className.lastIndexOf(46);
    if (packageEndIndex == -1) {
      return "";
    }
    String packageName = className.substring(0, packageEndIndex);
    return packageName.replace('.', '/');
  }

  public static String classNamesToString(Class[] classes)
  {
    return classNamesToString(Arrays.asList(classes));
  }

  public static String classNamesToString(Collection<Class> classes)
  {
    if ((classes == null) || (classes.isEmpty())) {
      return "[]";
    }
    StringBuilder sb = new StringBuilder("[");
    for (Iterator it = classes.iterator(); it.hasNext(); ) {
      Class clazz = (Class)it.next();
      sb.append(clazz.getName());
      if (it.hasNext()) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }

  public static Class[] getAllInterfaces(Object instance)
  {
    if (instance == null) {
      throw new IllegalArgumentException("Instance must not be null");
    }
    return getAllInterfacesForClass(instance.getClass());
  }

  public static Class<?>[] getAllInterfacesForClass(Class<?> clazz)
  {
    return getAllInterfacesForClass(clazz, null);
  }

  public static Class<?>[] getAllInterfacesForClass(Class<?> clazz, ClassLoader classLoader)
  {
    Set ifcs = getAllInterfacesForClassAsSet(clazz, classLoader);
    return (Class[])ifcs.toArray(new Class[ifcs.size()]);
  }

  public static Set<Class> getAllInterfacesAsSet(Object instance)
  {
    if (instance == null) {
      throw new IllegalArgumentException("Instance must not be null");
    }
    return getAllInterfacesForClassAsSet(instance.getClass());
  }

  public static Set<Class> getAllInterfacesForClassAsSet(Class clazz)
  {
    return getAllInterfacesForClassAsSet(clazz, null);
  }

  public static Set<Class> getAllInterfacesForClassAsSet(Class clazz, ClassLoader classLoader)
  {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    if ((clazz.isInterface()) && (isVisible(clazz, classLoader))) {
      return Collections.singleton(clazz);
    }
    Set interfaces = new LinkedHashSet();
    while (clazz != null) {
      Class[] ifcs = clazz.getInterfaces();
      for (Class ifc : ifcs) {
        interfaces.addAll(getAllInterfacesForClassAsSet(ifc, classLoader));
      }
      clazz = clazz.getSuperclass();
    }
    return interfaces;
  }

  public static Class<?> createCompositeInterface(Class<?>[] interfaces, ClassLoader classLoader)
  {
    if (interfaces == null) {
      throw new IllegalArgumentException("Interfaces must not be empty");
    }
    if (classLoader == null) {
      throw new IllegalArgumentException("ClassLoader must not be null");
    }
    return Proxy.getProxyClass(classLoader, interfaces);
  }

  public static boolean isVisible(Class<?> clazz, ClassLoader classLoader)
  {
    if (classLoader == null)
      return true;
    try
    {
      Class actualClass = classLoader.loadClass(clazz.getName());
      return clazz == actualClass;
    }
    catch (ClassNotFoundException ex) {
    }
    return false;
  }

  static
  {
    primitiveWrapperTypeMap.put(Boolean.class, Boolean.TYPE);
    primitiveWrapperTypeMap.put(Byte.class, Byte.TYPE);
    primitiveWrapperTypeMap.put(Character.class, Character.TYPE);
    primitiveWrapperTypeMap.put(Double.class, Double.TYPE);
    primitiveWrapperTypeMap.put(Float.class, Float.TYPE);
    primitiveWrapperTypeMap.put(Integer.class, Integer.TYPE);
    primitiveWrapperTypeMap.put(Long.class, Long.TYPE);
    primitiveWrapperTypeMap.put(Short.class, Short.TYPE);

    for (Map.Entry entry : primitiveWrapperTypeMap.entrySet()) {
      primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());
      registerCommonClasses(new Class[] { (Class)entry.getKey() });
    }

    Set primitiveTypes = new HashSet(32);
    primitiveTypes.addAll(primitiveWrapperTypeMap.values());
    primitiveTypes.addAll(Arrays.asList(new Class[] { [Z.class, [B.class, [C.class, [D.class, [F.class, [I.class, [J.class, [S.class }));

    primitiveTypes.add(Void.TYPE);
    for (Class primitiveType : primitiveTypes) {
      primitiveTypeNameMap.put(primitiveType.getName(), primitiveType);
    }

    registerCommonClasses(new Class[] { [Ljava.lang.Boolean.class, [Ljava.lang.Byte.class, [Ljava.lang.Character.class, [Ljava.lang.Double.class, [Ljava.lang.Float.class, [Ljava.lang.Integer.class, [Ljava.lang.Long.class, [Ljava.lang.Short.class });

    registerCommonClasses(new Class[] { Number.class, [Ljava.lang.Number.class, String.class, [Ljava.lang.String.class, Object.class, [Ljava.lang.Object.class, Class.class, [Ljava.lang.Class.class });

    registerCommonClasses(new Class[] { Throwable.class, Exception.class, RuntimeException.class, Error.class, StackTraceElement.class, [Ljava.lang.StackTraceElement.class });
  }
}