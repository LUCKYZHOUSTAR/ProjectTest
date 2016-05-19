/**
 * 
 */
package DistributeDataBase.common.util;

import java.util.Iterator;
import java.util.SortedSet;

/** 
* @ClassName: NavigableSet 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:36:39 
*  
*/
public interface NavigableSet<E> extends SortedSet<E> {
    public abstract E lower(E paramE);

    public abstract E floor(E paramE);

    public abstract E ceiling(E paramE);

    public abstract E higher(E paramE);

    public abstract E pollFirst();

    public abstract E pollLast();

    public abstract Iterator<E> iterator();

    public abstract NavigableSet<E> descendingSet();

    public abstract Iterator<E> descendingIterator();

    public abstract NavigableSet<E> subSet(E paramE1, boolean paramBoolean1, E paramE2,
                                           boolean paramBoolean2);

    public abstract NavigableSet<E> headSet(E paramE, boolean paramBoolean);

    public abstract NavigableSet<E> tailSet(E paramE, boolean paramBoolean);

    public abstract SortedSet<E> subSet(E paramE1, E paramE2);

    public abstract SortedSet<E> headSet(E paramE);

    public abstract SortedSet<E> tailSet(E paramE);
}
