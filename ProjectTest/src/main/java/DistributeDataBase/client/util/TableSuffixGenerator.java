/**
 * 
 */
package DistributeDataBase.client.util;

import org.apache.commons.lang.StringUtils;

/** 
* @ClassName: TableSuffixGenerator 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午9:40:26 
*  
*/
public class TableSuffixGenerator {

    public static String getTableSuffix(int x, int masterDBSize) {

        int length = (int) Math.ceil(Math.log10(masterDBSize));
        //TODO 这块给修改过--2016年5月17日 09:44:49
        return String.valueOf(length);
    }

    public static int trade50ConvertGroupNum(int groupNum) {
        if (groupNum < 0) {
            throw new IllegalArgumentException("ERROR ## the groupNum = " + groupNum + " is  < 0 ");
        }
        if (groupNum > 109) {
            throw new IllegalArgumentException("ERROR ## the groupNum = " + groupNum + " is >109 ");
        }
        if (groupNum < 10) {
            return groupNum + groupNum * 10;
        }
        return groupNum - (10 - groupNum / 10);
    }

}
