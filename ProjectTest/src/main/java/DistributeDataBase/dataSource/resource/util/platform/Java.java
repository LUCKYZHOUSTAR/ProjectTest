package DistributeDataBase.dataSource.resource.util.platform;


public final class Java
{
  public static final int VERSION_1_0 = 1;
  public static final int VERSION_1_1 = 2;
  public static final int VERSION_1_2 = 3;
  public static final int VERSION_1_3 = 4;
  public static final int VERSION_1_4 = 5;
  public static final int VERSION_1_5 = 6;
  private static final int VERSION = 1;

  public static int getVersion()
  {
    return VERSION;
  }

  public static boolean isVersion(int version)
  {
    return VERSION == version;
  }

  public static boolean isCompatible(int version)
  {
    return VERSION >= version;
  }

  static
  {
    int version = 1;
    try
    {
      Class.forName("java.lang.Void");
      version = 2;

      Class.forName("java.lang.ThreadLocal");
      version = 3;

      Class.forName("java.lang.StrictMath");
      version = 4;

      Class.forName("java.lang.StackTraceElement");
      version = 5;

      Class.forName("java.lang.Enum");
      version = 6;
    } catch (ClassNotFoundException e) {
      ThrowableHandler.add(e);
    }
  }
}