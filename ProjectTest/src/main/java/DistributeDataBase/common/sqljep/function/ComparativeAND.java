/**
 * 
 */
package DistributeDataBase.common.sqljep.function;

/** 
* @ClassName: ComparativeAND 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:54:14 
*  
*/
public class ComparativeAND extends ComparativeBaseList {
    public ComparativeAND(int function, Comparable<?> value) {
        super(function, value);
    }

    public ComparativeAND() {
    }

    public ComparativeAND(Comparative item) {
        super(item);
    }

    protected String getRelation() {
        return " and ";
    }
}