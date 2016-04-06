/**     
 * @FileName: HttpClient.java   
 * @Package:com.jiedaibao.coscore.biz.pay.common   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月5日 上午10:22:27   
 * @version V1.0     
 */
package network.socket.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**  
 * @ClassName: HttpClient   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月5日 上午10:22:27     
 */
public class HttpClient {
    private URL    url;
    private int    connectionTimeout;
    private int    readTimeOut;
    private String result;

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HttpClient(String url, int connectionTimeout, int readTimeOut) {
        try {
            this.url = new URL(url);
            this.connectionTimeout = connectionTimeout;
            this.readTimeOut = readTimeOut;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String send(String reqData, String encoding) throws Exception {
        try {
            HttpURLConnection httpURLConnection = createConnection(encoding);
            if (httpURLConnection == null) {
                throw new Exception("创建联接失败");
            }
            requestServer(httpURLConnection, reqData, encoding);
            this.result = response(httpURLConnection, encoding);

            return this.result;
        } catch (Exception e) {
            throw e;
        }
    }

    private void requestServer(URLConnection connection, String message, String encoder)
                                                                                        throws Exception {
        PrintStream out = null;
        try {
            connection.connect();
            out = new PrintStream(connection.getOutputStream(), false, encoder);
            out.print(message);
            out.flush();

            if (out != null)
                out.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null)
                out.close();
        }
    }

    private String response(HttpURLConnection connection, String encoding)
                                                                          throws URISyntaxException,
                                                                          IOException, Exception {
        InputStream in = null;
        StringBuilder sb = new StringBuilder(1024);
        BufferedReader br = null;
        try {
            if (200 == connection.getResponseCode()) {
                in = connection.getInputStream();
                sb.append(new String(read(in), encoding));
            } else {
                in = connection.getErrorStream();
                sb.append(new String(read(in), encoding));
            }

            return sb.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
            if (in != null) {
                in.close();
            }
            if (connection != null)
                connection.disconnect();
        }
    }

    public static byte[] read(InputStream in) throws IOException {
        byte[] buf = new byte[1024];
        int length = 0;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            bout.write(buf, 0, length);
        }
        bout.flush();
        return bout.toByteArray();
    }

    private HttpURLConnection createConnection(String encoding) throws ProtocolException {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) this.url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        httpURLConnection.setConnectTimeout(this.connectionTimeout);
        httpURLConnection.setReadTimeout(this.readTimeOut);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Content-type",
            "application/x-www-form-urlencoded;charset=" + encoding);

        httpURLConnection.setRequestMethod("POST");
        if ("https".equalsIgnoreCase(this.url.getProtocol())) {
            HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
            husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
            husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());
            return husn;
        }
        return httpURLConnection;
    }
}
