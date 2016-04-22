/**     
 * @FileName: StackTraceElementTest.java   
 * @Package:BasicJava   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午6:58:13   
 * @version V1.0     
 */
package MQResource;

/**  
 * @ClassName: StackTraceElementTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午6:58:13     
 */
public class StackTraceElementTest {
    Trace trace = new Trace();

    public static void main(String[] args) {
//        new StackTraceElementTest().test();
       String s= new StackTraceElementTest().Test2(new Throwable("你好吗"));
       System.out.println(s);
    }

    private void test() {
        trace.methodStart();
        System.out.println("111\ndddd\n");
        trace.trace("this the trace");
        trace.methodEnd();
    }

    private String Test2(final Throwable e) {
        StringBuffer sb=new StringBuffer();
        if(e!=null){
            sb.append(e.toString());
            StackTraceElement[] stackTrace=e.getStackTrace();
            if(stackTrace!=null&&stackTrace.length>0){
                StackTraceElement trace=stackTrace[0];
                
                sb.append(",")
                .append(trace.toString());
            }
        }
        
        return sb.toString();
    }
}

class Trace {
    public void methodStart() {

        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[0];
        System.out.println("[" + thisMethodStack.toString() + "]-----MethodStart");
    }

    public void methodEnd() {
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[0];
        System.out.println("[" + thisMethodStack.toString() + "]-----MethodEnd");
    }

    public void trace(String string) {
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[0];
        System.out.println("[" + thisMethodStack.toString() + "]" + string);
    }
    
    
    
}