/**
 * 
 */
package DistributeDataBase.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: PreFillConnection 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午9:45:04 
*  
*/
public class PreFillConnection {

    private static final Logger logger=LoggerFactory.getLogger("zdal-client-config");
    private static final String MYSQL_PREFILL_SQL="select 1";
    private static final String ORACLE_PREFILL_SQL="select sysdate from dual";
    
}
