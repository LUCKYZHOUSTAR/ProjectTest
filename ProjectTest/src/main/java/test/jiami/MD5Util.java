package test.jiami;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.map.StaticBucketMap;

//MD5加密的工具类
public class MD5Util {

	/*
	 * 1）对强行攻击的安全性：最显著和最重要的区别是SHA-1摘要比MD5摘要长32
	 * 位。使用强行技术，产生任何一个报文使其摘要等于给定报摘要的难度对MD5是2
	 * ^128数量级的操作，而对SHA-1则是2^160数量级的操作。这样，SHA-1对强行攻击有更大的强度。
	 * 
	 * 2）对密码分析的安全性：由于MD5的设计，易受密码分析的攻击，SHA-1显得不易受这样的攻击。
	 * 
	 * 3）速度：在相同的硬件上，SHA-1的运行速度比MD5慢。
	 * 4）两者都是不可逆的，一般用作信息摘要的算法处理，判断文件在传输过程中是否有更改过的痕迹
	 */
	private static final String MD5 = "MD5";
	private static final String SHA = "SHA";
	//上下游约定好的MD5KEY值
	private static final String MD5KEY="123456ABCDEF";

	public static String md5Encode(String inStr, String type) {
		MessageDigest md5 = null;
		StringBuffer sb = new StringBuffer();

		try {
			//返回实现指定摘要算法的 MessageDigest 对象。
			md5 = MessageDigest.getInstance(type);
			byte[] byteArray = inStr.getBytes("UTF-8");
			//  通过执行诸如填充之类的最终操作完成哈希计算。
			byte[] md5Bytes = md5.digest(byteArray);
		    for (int i = 0; i < md5Bytes.length; i++) {
		      sb.append(Integer.toHexString(md5Bytes[i] & 0xFF | 0x100).substring(1, 3));
		    }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	
	    return sb.toString().toLowerCase();

	}

	
	/*
	 * 
	 * obj代表实体
	 * */
	public static String fromObjToMap(User user){
		Map<String, String> treeMap=new TreeMap<String, String>();
		treeMap.put("password", user.getPassword());
		treeMap.put("userName", user.getUserName());
		return converMapToString(treeMap);
	}
	
	/*
	 *		需要验签的串，最后的格式如下	charset=GBK&merchantId=800010000010005

	 * */
	
	
	public static String converMapToString(Map<String, String> map){
		StringBuffer sb=new StringBuffer();
		for(String key:map.keySet()){
			sb.append(key).append("=").append(map.get(key)).append("&");
		}
		
		return sb.substring(0,sb.length()-1).toString();
	}
	
	/*
	 * data:需要验签的串
	 * oldSignData：上游系统传递过来的MD5sign
	 */
	public static boolean signData(User user,String oldSignData){
		String signData=fromObjToMap(user);
		//把验签的串与上下游约定好的MD5KEY进行拼接后，进行验签操作
		return md5Encode(signData+MD5KEY, MD5).equals(oldSignData);
	}
	public static void main(String[] args) throws Exception {
		User user=new User("张三","123456");
		String str=fromObjToMap(user);
		
		
		//---------------------------------------MD5加签操作---------------------
		System.out.println("MD5后：" + md5Encode(str+MD5KEY,MD5));
		
		//--------------------------------------  MD5验签操作----------------------
		System.out.println(signData(user, "624f8a162dad38b625d334d7960893a7"));
	
	}
}
