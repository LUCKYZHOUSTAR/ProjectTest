/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.retvalue;

import java.util.List;

/** 
* @ClassName: TargetDBMetaData 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:31:11 
*  
*/
public class TargetDBMetaData {
    private boolean              allowReverseOutput;
    private final List<TargetDB> target;
    private final String         virtualTableName;

    public TargetDBMetaData(String virtualTableName, List<TargetDB> targetdbs,
                            boolean allowReverseOutput) {
        this.virtualTableName = virtualTableName;
        this.target = targetdbs;

        this.allowReverseOutput = allowReverseOutput;
    }

    public List<TargetDB> getTarget() {
        return this.target;
    }

    public String getVirtualTableName() {
        return this.virtualTableName;
    }

    public boolean allowReverseOutput() {
        return this.allowReverseOutput;
    }

    public void needAllowReverseOutput(boolean reverse) {
        this.allowReverseOutput = reverse;
    }
}