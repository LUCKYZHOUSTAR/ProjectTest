/**
 * 
 */
package DistributeDataBase.dataSource.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: PoolConditionWriter 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:51:04 
*  
*/
public class PoolConditionWriter implements Runnable {
    private static final Logger logger      = LoggerFactory.getLogger("zdal-datasource-pool");

    private ZDataSource         zdatasource = null;

    public PoolConditionWriter(ZDataSource zdatasource) {
        this.zdatasource = zdatasource;
    }

    public void run() {
        if (this.zdatasource == null)
            return;
        PoolCondition poolCondition = this.zdatasource.getPoolCondition();
        if (poolCondition != null)
            logger.warn(poolCondition);
    }
}
