/**
 * 
 */
package DistributeDataBase.rule.ruleengine.exception;

import java.sql.SQLException;

/** 
* @ClassName: RuleRuntimeExceptionWrapper 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:07:04 
*  
*/
public class RuleRuntimeExceptionWrapper extends RuntimeException {
    private static final long serialVersionUID = 5542737179921749267L;
    final SQLException        sqlException;

    public RuleRuntimeExceptionWrapper(SQLException throwable) {
        super(throwable);
        this.sqlException = throwable;
    }

    public SQLException getSqlException() {
        return this.sqlException;
    }
}
