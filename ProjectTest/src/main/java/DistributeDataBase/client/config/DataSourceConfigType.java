/**
 * 
 */
package DistributeDataBase.client.config;


/** 
* @ClassName: DataSourceConfigType 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午4:10:20 
*  
*/
public enum DataSourceConfigType {

    GROUP, SHARD, SHARD_GROUP, SHARD_FAILOVER;

    public static DataSourceConfigType typeOf(String type) {
        if ((null == type) || (type.isEmpty())) {
            throw new IllegalArgumentException("the DataSourceConfigType can not be empty");
        }

        for (DataSourceConfigType t : values()) {
            if (t.name().equalsIgnoreCase(type)) {

                return t;
            }
        }

        throw new IllegalArgumentException(
            "the DataSourceConfigType "
                    + type
                    + "has not been supported yet,must to be [group,shard,shard_group,shard_failover]");

    }

    private boolean isGroup() {
        return equals(GROUP);
    }

    public boolean isShard() {
        return equals(SHARD);
    }

    public boolean isShardGroup() {
        return equals(SHARD_GROUP);
    }

    public boolean isShardFailover() {
        return equals(SHARD_FAILOVER);
    }
}
