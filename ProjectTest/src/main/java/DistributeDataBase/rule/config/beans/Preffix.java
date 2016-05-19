/**
 * 
 */
package DistributeDataBase.rule.config.beans;

/** 
* @ClassName: Preffix 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:51:32 
*  
*/
public class Preffix {
    private String tbPreffix = "_";
    private String tbType;

    public Preffix() {
    }

    public Preffix(String pre) {
        this.tbPreffix = pre;
    }

    public String getTbPreffix() {
        return this.tbPreffix;
    }

    public void setTbPreffix(String tbPreffix) {
        this.tbPreffix = tbPreffix;
    }

    public String getTbType() {
        return this.tbType;
    }

    public void setTbType(String tbType) {
        this.tbType = tbType;
    }
}