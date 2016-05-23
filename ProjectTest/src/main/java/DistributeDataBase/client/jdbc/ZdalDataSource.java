package com.alipay.zdal.client.jdbc;

import com.alipay.zdal.client.exceptions.ZdalClientException;
import com.alipay.zdal.client.exceptions.ZdalFeatureNotSupportException;
import com.alipay.zdal.common.Closable;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;

public class ZdalDataSource extends AbstractZdalDataSource
  implements DataSource, Closable
{
  public void init()
  {
    if (this.inited.get() == true)
      throw new ZdalClientException("ERROR ## init twice");
    try
    {
      super.initZdalDataSource();
    } catch (Exception e) {
      CONFIG_LOGGER.error("zdal init fail,config:" + toString(), e);
      throw new ZdalClientException(e);
    }
  }

  public java.util.logging.Logger getParentLogger()
    throws SQLFeatureNotSupportedException
  {
    throw new ZdalFeatureNotSupportException("Don't support ParentLogger");
  }
}