package DistributeDataBase.common.jdbc.sorter;

import java.io.Serializable;
import java.sql.SQLException;

public class MySQLExceptionSorter implements ExceptionSorter, Serializable {
    private static final long serialVersionUID = 2375890129763721017L;

    public boolean isExceptionFatal(SQLException e) {
        String sqlState = e.getSQLState();
        if ((sqlState != null) && (sqlState.startsWith("08"))) {
            return true;
        }
        switch (e.getErrorCode()) {
            case 1004:
            case 1005:
            case 1015:
            case 1021:
            case 1037:
            case 1038:
            case 1040:
            case 1041:
            case 1042:
            case 1043:
            case 1045:
            case 1047:
            case 1081:
            case 1129:
            case 1130:
            case 999999:
                return true;
        }

        String error_text = e.getMessage().toUpperCase();
        if ((error_text.indexOf("COMMUNICATIONS LINK FAILURE") > -1)
            || (error_text.indexOf("COULD NOT CREATE CONNECTION") > -1)
            || (error_text.indexOf("ACCESS DENIED FOR USER") > -1)
            || (error_text.indexOf("NO DATASOURCE") > -1)
            || (error_text.indexOf("NO ALIVE DATASOURCE") > -1)
            || (error_text.indexOf("THE LAST PACKET SUCCESSFULLY RECEIVED") > -1)) {
            return true;
        }

        return false;
    }
}