package Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JettyServer {
    private Server server;
    private WebAppContext webApp;

    private static final String DEFAULT_TEST_WEB_XML = "test-web.xml";

    public static void main(String[] args) throws Exception {
        JettyServer jettyServer = new JettyServer(9090);
        jettyServer.start();
    }

    public JettyServer(int port) throws Exception {
        this.server = new Server(port);
        // 根据我们项目规范, 获取项目路径和项目名称
        String path = JettyServer.class.getClassLoader().getResource(".").getPath();
        int target = path.lastIndexOf("target");
        String testModulePath = path.substring(0, target - 1);
        int lastIndexOfSlash = testModulePath.lastIndexOf("/");
        // 项目根目录
        String root = testModulePath.substring(0, lastIndexOfSlash);
        // 项目名称, 根据我们项目规范才能正确得到
        String projectName = testModulePath.substring(lastIndexOfSlash + 1, testModulePath.length())
                .split("-")[0];
        // 初始化webApp和classPath
        initWebApp(root, projectName);
    }

    public void start() throws Exception {
        this.server.start();
        this.server.join();
    }

    public void initWebApp(String root, String projectName) throws Exception {
        if (!root.endsWith("/")) {
            root = root + "/";
        }
        // Webapp目录
        String webAppPath = root + projectName + "-web" + "/" + "src" + "/" + "main" + "/" + "webapp";
        // 运行后的contextPath
        String contextPath = "/" + projectName;
        // 初始化WebAppContext
        File webFile = new File(webAppPath);
        if (!webFile.exists()) {
            throw new Exception("项目路径不存在：" + webAppPath);
        }
        this.webApp = new WebAppContext();
        this.webApp.setContextPath(contextPath);
        this.webApp.setWar(webAppPath);
        // 初始化WebAppClassLoader和classpath
        String[] classesPaths = getListedClassesPath(root, projectName);
        // 项目依赖的其他jar包路径, 目前没有
        //        String[] jarsPaths = new String[]{baseDir + projectName + "-web" + "/" + "src" + "/" + "main" + "/" + "webapp" + "/" + "WEB-INF" + "/" + "lib",};
        // 过滤项目本身生成的jar包
        //        String[] jarFilters = new String[]{projectName};
        // 加载classpath
        String classesPath = getClassesPath(classesPaths);
        //        String jarsPath = getJarsPath(jarsPaths, jarFilters);// jar包路径处理
        //        classesPath = classesPath.append(jarsPath);
        WebAppClassLoader classLoader = new WebAppClassLoader(webApp);
        classLoader.addClassPath(classesPath);
        this.webApp.setClassLoader(classLoader);
        // 替换web工程下的web.xml, 使用自定义test-web.xml, 可以隔离placeholder
        URL webResource = classLoader.getResource(DEFAULT_TEST_WEB_XML);
        this.webApp.setDescriptor(webResource.getPath());
        this.server.setHandler(this.webApp);
    }

    /**
     * 写死工程下的子工程
     *
     * @param baseDir
     * @param projectName
     * @return
     */
    @Deprecated
    @SuppressWarnings("unused")
    private String[] getConstantClassesPath(String baseDir, String projectName) {
        return new String[]{
                baseDir + "/" + projectName + "-web" + "/" + "target" + "/" + "classes",
                baseDir + "/" + projectName + "-biz" + "/" + "target" + "/" + "classes",
                baseDir + "/" + projectName + "-common" + "/" + "target" + "/" + "classes",
                baseDir + "/" + projectName + "-dao" + "/" + "target" + "/" + "classes",
                baseDir + "/" + projectName + "-service-api" + "/" + "target" + "/" + "classes",
                baseDir + "/" + projectName + "-service-provider" + "/" + "target" + "/" + "classes",
                baseDir + "/" + projectName + "-service-consumer" + "/" + "target" + "/" + "classes",
                baseDir + "/" + projectName + "-util" + "/" + "target" + "/" + "classes",
                baseDir + "/" + projectName + "-web" + "/" + "target" + "/" + "classes",
        };
    }

    /**
     * 自动获取工程目录下的子工程
     *
     * @param root
     * @param projectName
     * @return
     * @throws Exception
     */
    private String[] getListedClassesPath(String root, String projectName) throws Exception {
        File dir = new File(root);
        File[] files = dir.listFiles();
        if (files == null) {
            throw new Exception("项目路径不存在：" + root);
        }
        List<String> paths = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory() && file.getName().startsWith(projectName)) {
                paths.add(file.getAbsolutePath() + "/" + "target" + "/" + "classes");
            }
        }
        return paths.toArray(new String[0]);
    }

    /**
     * @return String 返回类型
     * @Title: getClassesPath
     * @Description: classPath及jar 路径数组转成String串 逗号间隔
     */
    private String getClassesPath(String[] classesPaths) {
        StringBuilder projectClassesPaths = new StringBuilder();
        if (null != classesPaths) {// class路径处理
            for (String classesPath : classesPaths) {
                File file = new File(classesPath);
                if (!file.exists() || !file.isDirectory()) {
                    continue;
                }
                projectClassesPaths.append(classesPath).append(",");
            }
        }
        return projectClassesPaths.toString();
    }

    /**
     * @return 设定文件 String 返回类型
     * @Title: getJarsPath jar相关文件处理
     * @Description: 过滤掉不要的jar 将jar文件路径数组转成String串
     */
    @SuppressWarnings("unused")
    private String getJarsPath(String[] jarsPaths, String[] jarFilters) {
        if (null == jarsPaths) {
            return null;
        }
        StringBuilder jarPaths = new StringBuilder();
        for (String jarPath : jarsPaths) {
            File file = new File(jarPath);
            if (!file.exists() || !file.isDirectory()) {
                continue;
            }
            File[] listFiles = file.listFiles();
            loop:
            for (File _file : listFiles) {
                String _fileName = _file.getName();
                if (null != jarFilters) {
                    for (String jarFilter : jarFilters) {
                        if (_fileName.startsWith(jarFilter)) {
                            continue loop;
                        }
                    }
                }
                jarPaths.append(_file.getAbsolutePath()).append(",");
            }
        }
        return jarPaths.toString();
    }
}
