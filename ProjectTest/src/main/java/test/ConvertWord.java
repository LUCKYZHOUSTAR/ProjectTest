package test;

public class ConvertWord {

	public static final String words="i love you";
	
	public static void main(String[] args) {
		reverseString2();
		printWord();
	}
	public static void printWord(){
		char[] charArray=words.toCharArray();
		String resultString="";
		for(int i=charArray.length-1;i>=0;i--){
			resultString+=charArray[i];
		}
		System.out.println(resultString);
	}
	
	//笛貴實現
	public void reverString(String str){
		String subString1=words.substring(0,words.length()-1);
		String subString2=words.substring(words.length()-1);
		reverString(subString1);
	}
	
	
	public static void reverseString2(){
		StringBuffer stringBuffer=new StringBuffer(words);
		System.out.println(stringBuffer.reverse());
	}
}
