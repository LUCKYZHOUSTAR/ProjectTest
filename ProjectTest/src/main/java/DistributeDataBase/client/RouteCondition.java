/**
 * 
 */
package DistributeDataBase.client;

/** 
* @ClassName: RouteCondition 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午9:31:45 
*  
*/
public class RouteCondition {

    public static void main(String[] args) {
        int suffixLen = Integer.valueOf(3).toString().length();
        String[] dbIndexes = new String[3];
        for (int i = 0; i < 3; i++) {
            String suffix = String.valueOf(i);
            while (suffix.length() < suffixLen) {
                suffix = new StringBuilder().append("0").append(suffix).toString();
            }
            dbIndexes[i] = new StringBuilder().append("master").append(suffix).toString();

            System.out.println(dbIndexes[i]);
        }

    }
}
