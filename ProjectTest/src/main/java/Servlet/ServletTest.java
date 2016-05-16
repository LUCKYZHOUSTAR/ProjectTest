/**
 * 
 */
package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LUCKY
 *
 */
public class ServletTest extends HttpServlet {

    private String message;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                                                                          IOException {
        // TODO Auto-generated method stub
        System.out.println(message);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        System.out.println("开始销毁程序");
    }

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        message = "我已经初始化了";
    }

}
