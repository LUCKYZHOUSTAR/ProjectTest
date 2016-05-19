package DistributeDataBase.dataSource.resource.util.id;

import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class SerialVersion
{
  private static final Logger logger = Logger.getLogger(SerialVersion.class);
  public static final int LEGACY = 0;
  public static final int JBOSS_402 = 1;
  public static int version = 1;

  static
  {
    AccessController.doPrivileged(new PrivilegedAction() {
      public Object run() {
        try {
          if (System.getProperty("org.jboss.j2ee.LegacySerialization") != null)
            SerialVersion.version = 0;
        } catch (Throwable ignored) {
          SerialVersion.logger.error(ignored);
        }
        return null;
      }
    });
  }
}