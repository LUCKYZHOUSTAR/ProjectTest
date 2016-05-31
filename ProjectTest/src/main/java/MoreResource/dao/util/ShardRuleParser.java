/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package MoreResource.dao.util;

import org.apache.commons.lang.StringUtils;

/** 
* @ClassName: ShardRuleParser 
* @Description: 
* @author LUCKY
* @date 2016年5月19日 下午4:05:17 
*  
*/
public class ShardRuleParser {

    private static final int OUTUSRID_LENGTH = 32;

    private static final int TABLE_PER_DB    = 2;

    /**
     * 根据uid解析分库位.
     * @param outusrid
     * @return
     */
    public static int parserDbIndex(String outusrid) {
        int id = getId(outusrid) / TABLE_PER_DB;
        return id;
    }

    /**
     * 根据uid解析分表位.
     * @param outusrid
     * @return
     */
    public static String parserTbIndex(String outusrid) {
        int id = getId(outusrid);
        String tbindex = String.valueOf(((id / 32) * 32) + (id % 32));
        return tbindex;
    }

    private static int getId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("ERROR ## the userId is null");
        }
        int hashid = HashUtil.quickHash(userId.getBytes());
        return hashid;
    }

    //================================================================================

    public static int parserDbIndexLocal(String id,String userName) {
        return Integer.valueOf(userName) % 2;
    }

    public static String parserTbIndexLocal(String id,String userName) {

        return String.valueOf(Integer.valueOf(userName) % 2);
    }

    public static int parserDbIndexBeta(String outusrid) {
        int id = quickHash(outusrid.getBytes(), 639) / 10;
        return id;
    }

    public static String parserTbIndexBeta(String outusrid) {
        int id = quickHash(outusrid.getBytes(), 639);
        String tbindex = String.valueOf(((id / 32) * 32) + (id % 32));
        return tbindex;
    }

    public static int parserDbIndexDev(String outusrid) {
        int id = quickHash(outusrid.getBytes(), 1) / 10;
        return id;
    }

    public static String parserTbIndexDev(String outusrid) {
        int id = quickHash(outusrid.getBytes(), 1);
        String tbindex = String.valueOf(((id / 11) * 11) + (id % 11));
        return tbindex;
    }

    public static int quickHash(byte[] bytes, int bound) {
        if (bytes.length == 0) {
            return 0;
        }

        int h = 0;
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            h = (31 * h) ^ bytes[i];
        }

        return h & bound;
    }

    public static void main(String[] args) {
//        System.out.println(5 % 2);
//        String outusrid = "345345";
//        System.out.println(parserDbIndexLocal(outusrid));
//        System.out.println(parserTbIndexLocal(outusrid));
    }
}
