/**
 * 
 */
package DistributeDataBase.common.sqljep.function;

/** 
* @ClassName: ComparativeOR 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:54:43 
*  
*/
public class ComparativeOR extends ComparativeBaseList {
    public ComparativeOR(int function, Comparable<?> value) {
        super(function, value);
    }

    public ComparativeOR() {
    }

    public ComparativeOR(Comparative item) {
        super(item);
    }

    public ComparativeOR(int capacity) {
        super(capacity);
    }

    protected String getRelation() {
        return " or ";
    }
}
