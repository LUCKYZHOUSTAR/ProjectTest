/**
 * 
 */
package DistributeDataBase.common;

/** 
* @ClassName: OperationDBType 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:16:04 
*  
*/
public enum OperationDBType {

    readFromDb(0), writeIntoDb(1);

    private int i;

    private OperationDBType(int i) {
        this.i = i;
    }

    public int value() {
        return this.i;
    }

    public static OperationDBType valueOf(int i) {
        for (OperationDBType t : values()) {
            if (t.value() == i) {
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid operationDBType:" + i);
    }
}
