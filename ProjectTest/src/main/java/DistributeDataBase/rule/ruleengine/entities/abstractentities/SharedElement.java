/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.abstractentities;

/** 
* @ClassName: SharedElement 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:14:57 
*  
*/
public abstract class SharedElement implements Cloneable, OneToMany {
    private String id;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getId() {
        return this.id;
    }

    public void init() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void put(OneToManyEntry oneToManyEntry) {
        throw new IllegalArgumentException("should not be here");
    }
}