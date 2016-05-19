/**
 * 
 */
package DistributeDataBase.client.config.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

/** 
* @ClassName: ZdalAppBean 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午4:33:40 
*  
*/
public class ZdalAppBean implements InitializingBean {

    private String                  appName;
    private String                  dbmode;
    private List<AppDataSourceBean> appDataSourceList = new ArrayList<>();

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDbmode() {
        return this.dbmode;
    }

    public void setDbmode(String dbmode) {
        this.dbmode = dbmode;
    }

    public List<AppDataSourceBean> getAppDataSourceList() {
        return this.appDataSourceList;
    }

    public void setAppDataSourceList(List<AppDataSourceBean> appDataSourceList) {
        this.appDataSourceList = appDataSourceList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (StringUtils.isBlank(this.appName)) {
            throw new IllegalArgumentException("ERROR ## the appName is null");
        }
        if (StringUtils.isBlank(this.dbmode)) {
            throw new IllegalArgumentException("ERROR ## the dbmode is null of " + this.appName);
        }
        if ((this.appDataSourceList == null) || (this.appDataSourceList.isEmpty())) {
            throw new IllegalArgumentException("ERROR ## the appDataSource is empty of "
                                               + this.appName);
        }

        Map<String, AppDataSourceBean> tmps = new HashMap<String, AppDataSourceBean>();
        for (AppDataSourceBean bean : this.appDataSourceList) {
            tmps.put(bean.getAppDataSourceName(), bean);
        }

        if (tmps.size() != this.appDataSourceList.size())
            throw new IllegalArgumentException(
                "ERROR ## the appDataSourceList has same appDataSourceName of " + this.appName);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appDataSourceList == null) ? 0 : appDataSourceList.hashCode());
        result = prime * result + ((appName == null) ? 0 : appName.hashCode());
        result = prime * result + ((dbmode == null) ? 0 : dbmode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ZdalAppBean other = (ZdalAppBean) obj;
        if (appDataSourceList == null) {
            if (other.appDataSourceList != null)
                return false;
        } else if (!appDataSourceList.equals(other.appDataSourceList))
            return false;
        if (appName == null) {
            if (other.appName != null)
                return false;
        } else if (!appName.equals(other.appName))
            return false;
        if (dbmode == null) {
            if (other.dbmode != null)
                return false;
        } else if (!dbmode.equals(other.dbmode))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ZdalAppBean [appName=" + appName + ", dbmode=" + dbmode + ", appDataSourceList="
               + appDataSourceList + "]";
    }

}
