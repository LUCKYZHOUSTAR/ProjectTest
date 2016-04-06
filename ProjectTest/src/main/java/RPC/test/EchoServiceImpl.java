/**     
 * @FileName: EchoServiceImpl.java   
 * @Package:RPC.test   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月6日 下午1:20:56   
 * @version V1.0     
 */
package RPC.test;


/**  
 * @ClassName: EchoServiceImpl   
 * @Description: RPC接口实现类操作
 * @author: LUCKY  
 * @date:2016年4月6日 下午1:20:56     
 */
public class EchoServiceImpl implements EchoService{

    /* (non-Javadoc)   
     * @param ping
     * @return   
     * @see RPC.test.EchoService#echo(java.lang.String)   
     */  
    @Override
    public String echo(String ping) {
        // TODO Auto-generated method stub
        return ping!=null? ping+"----> i am ok":"i am ok.";
    }

}
