/**     
 * @FileName: BinarySaver.java   
 * @Package:network.HttpConnection   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 上午10:03:53   
 * @version V1.0     
 */
package network.HttpConnection;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**  
 * @ClassName: BinarySaver   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 上午10:03:53     
 */
public class BinarySaver {
    public static void main(String args[]) {

        try {
            URL root = new URL("http://www.cafeaulait.org/books/jnp3/examples/15/EncodingAwareSourceViewer.java");
            saveBinaryFile(root);
        } catch (MalformedURLException ex) {
            System.err.println("http://www.cafeaulait.org/books/jnp3/examples/15/EncodingAwareSourceViewer.java" + " is not URL I understand.");
        } catch (IOException ex) {
            System.err.println(ex);
        }

    } // end main

    public static void saveBinaryFile(URL u) throws IOException {

        URLConnection uc = u.openConnection();
        String contentType = uc.getContentType();
        int contentLength = uc.getContentLength();
        if (contentType.startsWith("text/") || contentLength == -1) {
            throw new IOException("This is not a binary file.");
        }

        InputStream raw = uc.getInputStream();
        InputStream in = new BufferedInputStream(raw);
        byte[] data = new byte[contentLength];
        int bytesRead = 0;
        int offset = 0;
        while (offset < contentLength) {
            bytesRead = in.read(data, offset, data.length - offset);
            if (bytesRead == -1)
                break;
            offset += bytesRead;
        }
        in.close();

        if (offset != contentLength) {
            throw new IOException("Only read " + offset + " bytes; Expected " + contentLength
                                  + " bytes");
        }

        
        String filename = u.getFile();
        filename = filename.substring(filename.lastIndexOf('/') + 1);
        FileOutputStream fout = new FileOutputStream(filename);
        fout.write(data);
        fout.flush();
        fout.close();

    }
}
