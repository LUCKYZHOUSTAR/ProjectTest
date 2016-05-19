/**
 * 
 */
package DistributeDataBase.client.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: ExceptionUtils 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午7:42:40 
*  
*/
public class ExceptionUtils {
    public static final String SQL_EXECUTION_ERROR_CONTEXT_LOG     = "SQL_EXECUTION_ERROR_CONTEXT_LOG";
    public static final String SQL_EXECUTION_ERROR_CONTEXT_MESSAGE = "SQLException ,context is ";
    public static final Logger log                                 = LoggerFactory
                                                                       .getLogger(SQL_EXECUTION_ERROR_CONTEXT_LOG);

    public static void throwSQLException(List<SQLException> exceptions, String sql,
                                         List<Object> args) throws SQLException {

        if ((exceptions != null) && (!exceptions.isEmpty())) {
            SQLException first = (SQLException) exceptions.get(0);
            if (sql != null) {
                log.warn(
                    new StringBuilder().append("ZDAL SQL EXECUTE ERROR REPORTER:")
                        .append(getErrorContext(sql, args, "SQLException ,context is ")).toString(),
                    first);
            }

            int i = 1;
            for (int n = exceptions.size(); i < n; i++) {
                if (sql != null) {
                    log.warn(
                        new StringBuilder().append("layer:").append(n)
                            .append("ZDAL SQL EXECUTE ERROR REPORTER :")
                            .append(getErrorContext(sql, args, "SQLException ,context is "))
                            .toString(), (Throwable) exceptions.get(i));
                }
            }

            throw mergeException(exceptions);
        }
    }

    public static SQLException mergeException(List<SQLException> exceptions) {

        SQLException first = exceptions.get(0);
        List stes = new ArrayList(30 * exceptions.size());

        for (StackTraceElement ste : first.getStackTrace()) {
            stes.add(ste);
        }

        Set exceptionsSet = new HashSet(exceptions.size());
        exceptionsSet.add(first);
        int i = 1;
        for (int n = exceptions.size(); i < n; i++) {
            if (!exceptionsSet.contains(exceptions.get(i))) {
                exceptionsSet.add(exceptions.get(i));
                for (StackTraceElement ste : ((SQLException) exceptions.get(i)).getStackTrace()) {
                    stes.add(ste);
                }
            }
        }
        first.setStackTrace((StackTraceElement[]) stes.toArray(new StackTraceElement[stes.size()]));
        return first;
    }

    public static void throwSQLException(SQLException exception, String sql, List<Object> args)
                                                                                               throws SQLException {
        if (sql != null) {
            log.warn(
                new StringBuilder().append("ZDAL SQL EXECUTE ERROR REPORTER:")
                    .append(getErrorContext(sql, args, "SQLException ,context is "))
                    .append("nest Exceptions is ").append(exception.getMessage()).toString(),
                exception);
        }
        throw exception;
    }

    public static String getErrorContext(String sql, List<Object> arguments, String message) {

        StringBuilder sb = new StringBuilder();
        sb.append(message).append(sql).append("|||arguments:");
        printArgument(arguments, sb);
        return sb.toString();
    }

    private static void printArgument(List<Object> parameters, StringBuilder sb) {

        int i = 0;
        if (parameters != null) {
            for (Object param : parameters) {
                sb.append("[index:").append(i).append("|parameter:").append(param)
                    .append("|typeclass:")
                    .append(param == null ? null : param.getClass().getName()).append("]");
                i++;
            }
        } else {
            sb.append("[empty]");
        }
    }
}
