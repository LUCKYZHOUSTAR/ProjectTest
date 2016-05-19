/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.retvalue;

import java.util.List;
import java.util.Set;

/** 
* @ClassName: PartitionElementUtils 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:29:42 
*  
*/
public class PartitionElementUtils {
    public static <E> void add(List<Set<E>> target, E value) {
        int dbsize = target.size();
        switch (dbsize) {
            case 1:
                ((Set) target.get(0)).add(value);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static <E> void addAll(List<Set<E>> target, Set<E> value) {
        int dbsize = target.size();
        switch (dbsize) {
            case 1:
                ((Set) target.get(0)).addAll(value);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
