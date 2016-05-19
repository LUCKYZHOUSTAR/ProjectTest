/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.retvalue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
* @ClassName: PartitionElement 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:29:18 
*  
*/
public class PartitionElement {
    private List<Set<String>> db            = new ArrayList();

    private List<Set<String>> tab           = new ArrayList();

    private List<Set<String>> uniqueColumns = new ArrayList();

    public PartitionElement() {
        this.db.add(new HashSet());
        this.tab.add(new HashSet());
        this.uniqueColumns.add(new HashSet());
    }

    @Deprecated
    public void addDBFirstElement(String str) {
        PartitionElementUtils.add(this.db, str);
    }

    @Deprecated
    public void addAllDBFirstElement(Set<String> set) {
        PartitionElementUtils.addAll(this.db, set);
    }

    @Deprecated
    public void addPKFirstElement(String str) {
        PartitionElementUtils.add(this.uniqueColumns, str);
    }

    @Deprecated
    public void addAllPKFirstElement(Set<String> set) {
        PartitionElementUtils.addAll(this.uniqueColumns, set);
    }

    @Deprecated
    public void addTabFirstElement(String str) {
        PartitionElementUtils.add(this.tab, str);
    }

    @Deprecated
    public void addAllTabFirstElement(Set<String> set) {
        PartitionElementUtils.addAll(this.tab, set);
    }

    @Deprecated
    public Set<String> getDbFirstElement() {
        if (this.db.size() == 1) {
            return (Set) this.db.get(0);
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public Set<String> getTabFirstElement() {
        if (this.tab.size() == 1) {
            return (Set) this.tab.get(0);
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public Set<String> getPkFirstElement() {
        if (this.uniqueColumns.size() == 1) {
            return (Set) this.uniqueColumns.get(0);
        }
        throw new IllegalArgumentException();
    }

    public List<Set<String>> getUniqueColumns() {
        return this.uniqueColumns;
    }

    public List<Set<String>> getDb() {
        return this.db;
    }

    public List<Set<String>> getTab() {
        return this.tab;
    }

    public void setDb(List<Set<String>> db) {
        if (db != null)
            this.db = db;
    }

    public void setTab(List<Set<String>> tab) {
        if (tab != null)
            this.tab = tab;
    }

    public void setUniqueColumns(List<Set<String>> uniqueColumns) {
        if (uniqueColumns != null)
            this.uniqueColumns = uniqueColumns;
    }
}
