package test.alrotic;

public class AddTest {

	public static void main(String[] args) {
		AddTest test=new AddTest();
		System.out.println(test.add(8, 18));
	}
	//：写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则 运算符号。 
	public int add(int num1,int num2){
		int sum,carray;
		do{
			sum=num1^num2;
			carray=(num1&num2)<<1;
			num1=sum;
			num2=carray;
		}while(num2!=0);
		
		return num1;
	}
}
