/**
 * 
 */
package DistributeDataBase.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: SimpleNamedMessageFormat 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:32:31 
*  
*/
public class SimpleNamedMessageFormat {

    private static final String DEFAULT_PLACEHOLDER_PREFIX = "{";
    private static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";
    private final String        pattern;
    private final String        placeholderPrefix;
    private final String        placeholderSuffix;
    private volatile boolean    parsed;
    private volatile List<Frag> frags;

    public SimpleNamedMessageFormat(String pattern) {
        this.pattern = pattern;
        this.placeholderPrefix = "{";
        this.placeholderSuffix = "}";
    }

    public SimpleNamedMessageFormat(String pattern, String placeholderPrefix,
                                    String placeholderSuffix) {
        this.pattern = pattern;
        this.placeholderPrefix = placeholderPrefix;
        this.placeholderSuffix = placeholderSuffix;
    }

    public String format(Map<String, ? extends Object> args) {
        if ((this.parsed) && (this.frags != null)) {
            return buildByParsedFrags(args);
        }
        return format0(args);
    }

    private String buildByParsedFrags(Map<String, ? extends Object> args) {
        StringBuilder sb = new StringBuilder();
        for (Frag frag : this.frags) {
            if (!frag.isPlaceHolderName) {
                sb.append(frag.value);
            } else {
                Object arg = args.get(frag.value);
                if (arg != null)
                    sb.append(arg);
                else
                    sb.append(this.placeholderPrefix).append(frag.value)
                        .append(this.placeholderSuffix);
            }
        }
        return sb.toString();
    }

    private String format0(Map<String, ? extends Object> args) {
        List initfrags = new ArrayList();
        int cursor = 0;
        int index0 = this.pattern.indexOf(this.placeholderPrefix);
        int index1 = this.pattern.indexOf(this.placeholderSuffix);
        while ((index0 != -1) && (index1 != -1)) {
            initfrags.add(new Frag(this.pattern.substring(cursor, index0), false));
            initfrags.add(new Frag(this.pattern.substring(index0 + this.placeholderPrefix.length(),
                index1), true));

            cursor = index1 + this.placeholderSuffix.length();
            index0 = this.pattern.indexOf(this.placeholderPrefix, cursor);
            index1 = this.pattern.indexOf(this.placeholderSuffix,
                index0 + this.placeholderPrefix.length());
        }
        initfrags.add(new Frag(this.pattern.substring(cursor), false));
        this.frags = initfrags;
        this.parsed = true;
        return buildByParsedFrags(args);
    }

    private static class Frag {
        public final String  value;
        public final boolean isPlaceHolderName;

        public Frag(String piece, boolean isPlaceHolderName) {
            this.value = piece;
            this.isPlaceHolderName = isPlaceHolderName;
        }
    }
}
