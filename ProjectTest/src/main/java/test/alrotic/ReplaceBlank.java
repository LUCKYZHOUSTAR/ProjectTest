package test.alrotic;


//实现一个函数，把字符串中的每个空格替换成%20
public class ReplaceBlank {

	
	
	public String replaceBlank(String input){
		StringBuffer outBuffer=new StringBuffer();
		for(int i=0;i<input.length();i++){
			if(input.charAt(i)==' '){
				outBuffer.append("%")
				.append("2")
				.append("0");
			}else {
				outBuffer.append(String.valueOf(input.charAt(i)));
			}
		}
		
		return new String(outBuffer);
	}
	
}
