/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.retvalue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** 
* @ClassName: TargetDB 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:30:49 
*  
*/
public class TargetDB implements DatabasesAndTables {
    private String               dbIndex;
    private String[]             writePool;
    private String[]             readPool;
    private Set<String>          tableNames;
    private Map<Integer, Object> changedParams = Collections.emptyMap();

    public String[] getWritePool() {
        return this.writePool;
    }

    public void setWritePool(String[] writePool) {
        this.writePool = writePool;
    }

    public String[] getReadPool() {
        return this.readPool;
    }

    public void setReadPool(String[] readPool) {
        this.readPool = readPool;
    }

    public Set<String> getTableNames() {
        return this.tableNames;
    }

    public void setTableNames(Set<String> tableNames) {
        this.tableNames = tableNames;
    }

    public void addOneTable(String table) {
        if (this.tableNames == null) {
            this.tableNames = new HashSet();
        }
        this.tableNames.add(table);
    }

    public String getDbIndex() {
        return this.dbIndex;
    }

    public void setDbIndex(String dbIndex) {
        this.dbIndex = dbIndex;
    }

    public String toString() {
        return "TargetDB [dbIndex=" + this.dbIndex + ", readPool=" + Arrays.toString(this.readPool)
               + ", tableNames=" + this.tableNames + ", writePool="
               + Arrays.toString(this.writePool) + "]";
    }

    public void setChangedParams(Map<Integer, Object> changedParams) {
        this.changedParams = changedParams;
    }

    public Map<Integer, Object> getChangedParams() {
        return this.changedParams;
    }
}
