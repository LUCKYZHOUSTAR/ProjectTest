/**
 * 
 */
package DistributeDataBase.rule.bean;

import java.util.List;

import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDB;

/** 
* @ClassName: CalculationResult 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:34:09 
*  
*/
public abstract interface CalculationResult {
    public abstract List<TargetDB> getTargetDBList();
}
