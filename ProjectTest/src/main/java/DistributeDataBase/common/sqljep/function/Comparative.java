/**
 * 
 */
package DistributeDataBase.common.sqljep.function;

/** 
* @ClassName: Comparative 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:48:50 
*  
*/
public class Comparative implements Comparable, Cloneable {

    public static final int GreaterThan        = 1;
    public static final int GreaterThanOrEqual = 2;
    public static final int Equivaent          = 3;
    public static final int Like               = 4;
    public static final int NotLike            = 5;
    public static final int NotEquivalent      = 6;
    public static final int LessThan           = 7;
    public static final int LessThanOrEqual    = 8;
    public static final int NotSupport         = -1;

    private Comparable      value;
    private int             comparison;

    public static int reverseComparison(int function) {
        return 9 - function;
    }

    public static int exchangeComparison(int function) {
        if (function == 1)
            return 7;
        if (function == 2)
            return 8;
        if (function == 7) {
            return 1;
        }
        if (function == 8) {
            return 2;
        }
        return function;
    }

    protected Comparative() {
    }

    public Comparative(int function, Comparable value) {
        this.comparison = function;
        this.value = value;
    }

    public Comparable getValue() {
        return this.value;
    }

    public void setComparison(int function) {
        this.comparison = function;
    }

    public static String getComparisonName(int function) {
        if (function == 3)
            return "=";
        if (function == 1)
            return ">";
        if (function == 2)
            return ">=";
        if (function == 8)
            return "<=";
        if (function == 7)
            return "<";
        if (function == 6)
            return "<>";
        if (function == 4)
            return "LIKE";
        if (function == 5) {
            return "NOT LIKE";
        }
        return null;
    }

    public static int getComparisonByIdent(String ident) {
        if ("=".equals(ident))
            return 3;
        if (">".equals(ident))
            return 1;
        if (">=".equals(ident))
            return 2;
        if ("<=".equals(ident))
            return 8;
        if ("<".equals(ident))
            return 7;
        if ("!=".equals(ident))
            return 6;
        if ("<>".equals(ident))
            return 6;
        if ("like".equalsIgnoreCase(ident)) {
            return 4;
        }
        return -1;
    }

    public int getComparison() {
        return this.comparison;
    }

    public void setValue(Comparable value) {
        this.value = value;
    }

    public int compareTo(Object o) {
        if ((o instanceof Comparative)) {
            Comparative other = (Comparative) o;
            return getValue().compareTo(other.getValue());
        }
        if ((o instanceof Cloneable)) {
            return getValue().compareTo(o);
        }
        return -1;
    }

    public String toString() {
        if (this.value != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(this.comparison).append("]");
            sb.append(this.value.toString());
            return sb.toString();
        }
        return null;
    }

    public Object clone() {
        return new Comparative(this.comparison, this.value);
    }
}
