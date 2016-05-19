/**
 * 
 */
package DistributeDataBase.common;

/** 
* @ClassName: RuntimeConfigHolder 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:08:38 
*  
*/
public class RuntimeConfigHolder<T> {

    private volatile T runtime;

    public T get() {
        return this.runtime;
    }

    public void set(T runtime) {
        this.runtime = runtime;
    }
}
