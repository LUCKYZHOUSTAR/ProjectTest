package test.log;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogTest {

	public static Logger log=null;
	public static void main(String[] args) {
		//采用配置默认来加载指定的log4j.xml文件
		PropertyConfigurator.configure ("");
		//获得根rooter
		log=LogManager.getRootLogger();
		//获取指定LogggerName的log信息
		log=Logger.getLogger("appender-web");
		log.setLevel(Level.toLevel("LogLevel"));
		
	}
}
