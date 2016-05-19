/**
 * 
 */
package DistributeDataBase.rule.ruleengine.enumerator;

import DistributeDataBase.common.sqljep.function.Comparative;

/** 
* @ClassName: EnumerationInterruptException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:10:38 
*  
*/
public class EnumerationInterruptException extends RuntimeException {
    private static final long     serialVersionUID = 1L;
    private transient Comparative comparative;

    public EnumerationInterruptException(Comparative comparative) {
        this.comparative = comparative;
    }

    public EnumerationInterruptException() {
    }

    public Comparative getComparative() {
        return this.comparative;
    }

    public void setComparative(Comparative comparative) {
        this.comparative = comparative;
    }
}
