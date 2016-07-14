package test.alrotic;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		System.out.println("-----");
//		int i=1/0;
//		double d=1.0/0.0;
		
		List<String> strList=new ArrayList<>();
		for (String str : strList) {
			strList.remove(str);
		}
		
		
	}
}
