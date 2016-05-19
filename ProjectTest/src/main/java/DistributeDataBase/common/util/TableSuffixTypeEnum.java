/**
 * 
 */
package DistributeDataBase.common.util;

/** 
* @ClassName: TableSuffixTypeEnum 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:31:27 
*  
*/
public enum TableSuffixTypeEnum {
    twoColumnForEachDB("twoColumnForEachDB"), dbIndexForEachDB("dbIndexForEachDB"), groovyTableList(
                                                                                                    "groovyTableList"), groovyAdjustTableList(
                                                                                                                                              "groovyAdjustTableList"), groovyThroughAllDBTableList(
                                                                                                                                                                                                    "groovyThroughAllDBTableList"), throughAllDB(
                                                                                                                                                                                                                                                 "throughAllDB"), resetForEachDB(
                                                                                                                                                                                                                                                                                 "resetForEachDB"), none(
                                                                                                                                                                                                                                                                                                         "none");

    private String value;

    private TableSuffixTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
