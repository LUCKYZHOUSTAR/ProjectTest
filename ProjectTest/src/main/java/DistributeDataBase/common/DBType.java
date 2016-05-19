/**
 * 
 */
package DistributeDataBase.common;

/** 
* @ClassName: DBType 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午3:33:34 
*  
*/
public enum DBType {

    ORACLE, MYSQL, DB2;

    public boolean isOracle() {
        return equals(ORACLE);
    }

    public boolean isMySql() {
        return equals(MYSQL);
    }

    public boolean isDB2() {
        return equals(DB2);
    }

    public static DBType convert(String strType) {
        for (DBType t : values()) {
            if (t.toString().equalsIgnoreCase(strType)) {
                return t;
            }
        }

        throw new IllegalArgumentException("Invalid DBType:" + strType
                                           + "must to be(oracle,mysql,db2)");
    }
    
    public static void main(String[] args) {
        
       System.out.println(valueOf("MYSQL").getClass());
    }
}
