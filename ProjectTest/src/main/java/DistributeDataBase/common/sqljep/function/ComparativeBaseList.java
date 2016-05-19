/**
 * 
 */
package DistributeDataBase.common.sqljep.function;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: ComparativeBaseList 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:53:26 
*  
*/
public abstract class ComparativeBaseList extends Comparative {
    protected List<Comparative> list = new ArrayList(2);

    public ComparativeBaseList(int function, Comparable<?> value) {
        super(function, value);
        this.list.add(new Comparative(function, value));
    }

    protected ComparativeBaseList() {
    }

    public ComparativeBaseList(int capacity) {
        this.list = new ArrayList(capacity);
    }

    public ComparativeBaseList(Comparative item) {
        super(item.getComparison(), item.getValue());
        this.list.add(item);
    }

    public List<Comparative> getList() {
        return this.list;
    }

    public void addComparative(Comparative item) {
        this.list.add(item);
    }

    public Object clone() {
        try {
            Constructor con = getClass().getConstructor((Class[]) null);

            ComparativeBaseList compList = (ComparativeBaseList) con.newInstance((Object[]) null);
            for (Comparative com : this.list) {
                compList.addComparative((Comparative) com.clone());
            }
            compList.setComparison(getComparison());
            compList.setValue(getValue());
            return compList;
        } catch (Exception e) {
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean firstElement = true;
        for (Comparative comp : this.list) {
            if (!firstElement) {
                sb.append(getRelation());
            }
            sb.append(comp.toString());
            firstElement = false;
        }
        return sb.toString();
    }

    protected abstract String getRelation();
}
