/**
 * 
 */
package DistributeDataBase.common.jdbc.sorter;

import java.io.Serializable;
import java.sql.SQLException;

/** 
* @ClassName: NullExceptionSorter 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:45:38 
*  
*/
public class NullExceptionSorter implements ExceptionSorter, Serializable {

    private static final long serialVersionUID = 202928214888283717L;

    public boolean isExceptionFatal(SQLException e) {
        return false;
    }

}
