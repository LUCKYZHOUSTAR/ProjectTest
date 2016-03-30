/**     
 * @FileName: Text.java   
 * @Package:EffectiveJava.Item8   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 下午7:05:07   
 * @version V1.0     
 */
package EffectiveJava.Item8;

import java.util.EnumSet;
import java.util.Set;

import java.util.EnumSet;
import java.util.Set;

/**  
 * @ClassName: Text   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 下午7:05:07     
 */
public class Text {
    public enum Style {
        BOLD, ITALIC, UNDERLINE, STRIKETHROUGH
    }

    // Any Set could be passed in, but EnumSet is clearly best
    public void applyStyles(Set<Style> styles) {
        // Body goes here
    }

    // Sample use
    public static void main(String[] args) {
        Text text = new Text();
        text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
    }
}
