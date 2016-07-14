package test.alrotic;

public class Sigleton {

	
	private static class SingletonHolder{
		private static final Sigleton INSTANCE=new Sigleton();
	}
	
	private Sigleton(){}
	public static final Sigleton getInstance(){
		return SingletonHolder.INSTANCE;
	}
	
}
