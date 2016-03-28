/**     
 * @FileName: RelativeURLTest.java   
 * @Package:network.URL   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月28日 下午5:00:22   
 * @version V1.0     
 */
package network.URL;

import java.applet.Applet;
import java.awt.GridLayout;
import java.awt.Label;
import java.net.MalformedURLException;
import java.net.URL;

/**  
 * @ClassName: RelativeURLTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月28日 下午5:00:22     
 */
public class RelativeURLTest extends Applet{
    public void init() {

        try {
            URL base = this.getDocumentBase();
            URL relative = new URL(base, "mailinglists.html");
            this.setLayout(new GridLayout(2, 1));
            this.add(new Label(base.toString()));
            this.add(new Label(relative.toString()));
        } catch (MalformedURLException ex) {
            this.add(new Label("This shouldn't happen!"));
        }

    }
}
