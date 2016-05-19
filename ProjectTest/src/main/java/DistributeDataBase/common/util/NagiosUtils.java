/**
 * 
 */
package DistributeDataBase.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: NagiosUtils 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:40:23 
*  
*/
public class NagiosUtils {
    private static final Logger nagiosLog                  = LoggerFactory.getLogger("Nagios");
    public static final String  KEY_DB_NOT_AVAILABLE       = "DB_NOT_AVAILABLE";
    public static final String  KEY_SQL_PARSE_FAIL         = "SQL_PARSE_FAIL";
    public static final String  KEY_REPLICATION_FAIL_RATE  = "REPLICATION_FAIL_RATE";
    public static final String  KEY_REPLICATION_TIME_AVG   = "REPLICATION_TIME_AVG";
    public static final String  KEY_INSERT_LOGDB_FAIL_RATE = "INSERT_LOGDB_FAIL_RATE";
    public static final String  KEY_INSERT_LOGDB_TIME_AVG  = "INSERT_LOGDB_TIME_AVG";

    public static void addNagiosLog(String key, String value) {
        key = key.replaceAll(":", "_");
        key = key.replaceAll(",", "|");
        value = value.replaceAll(":", "_");
        value = value.replaceAll(",", "|");
        innerAddNagiosLog(key, value);
    }

    public static void addNagiosLog(String key, int value) {
        key = key.replaceAll(":", "_");
        key = key.replaceAll(",", "|");
        innerAddNagiosLog(key, Integer.toString(value));
    }

    public static void addNagiosLog(String key, long value) {
        key = key.replaceAll(":", "_");
        key = key.replaceAll(",", "|");
        innerAddNagiosLog(key, Long.toString(value));
    }

    public static void addNagiosLog(String host, String key, String value) {
        host = host.replaceAll(":", "_");
        host = host.replaceAll(",", "|");
        key = key.replaceAll(":", "_");
        key = key.replaceAll(",", "|");
        value = value.replaceAll(":", "_");
        value = value.replaceAll(",", "|");
        innerAddNagiosLog(host, key, value);
    }

    public static void addNagiosLog(String host, String key, int value) {
        host = host.replaceAll(":", "_");
        host = host.replaceAll(",", "|");
        key = key.replaceAll(":", "_");
        key = key.replaceAll(",", "|");
        innerAddNagiosLog(host, key, Integer.toString(value));
    }

    public static void addNagiosLog(String host, String key, long value) {
        host = host.replaceAll(":", "_");
        host = host.replaceAll(",", "|");
        key = key.replaceAll(":", "_");
        key = key.replaceAll(",", "|");
        innerAddNagiosLog(host, key, Long.toString(value));
    }

    private static void innerAddNagiosLog(String key, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append(":");
        sb.append(value);
        if (nagiosLog.isDebugEnabled())
            nagiosLog.debug(sb.toString());
    }

    private static void innerAddNagiosLog(String host, String key, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append(host);
        sb.append("_");
        sb.append(key);
        sb.append(":");
        sb.append(value);
        if (nagiosLog.isDebugEnabled())
            nagiosLog.debug(sb.toString());
    }
}