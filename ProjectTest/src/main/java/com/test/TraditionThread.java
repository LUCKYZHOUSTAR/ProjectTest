package com.test;

public class TraditionThread {
	public static void main(String[] args) {
		final Outputer outputer = new Outputer();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputer.output("哈哈哈");
				}
				
			}
		}).start();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputer.output2("大大大大阿达");
				}
				
			}
		}).start();
		
	}

	static class  Outputer {

		
		
		public void test(){
			synchronized (this) {
				
			}
		}
		
		public void output(String name) {
			
			//如果有很多需要锁住的方法的话，则也必须保证只有一把钥匙，这个意思就是锁住的方法的钥匙都是一样的
			//锁一定要是同一个对象才可以
			//这样的话就保证了只有一把钥匙
			String xxxString="";
			int len = name.length();
			//钥匙不一样，所以都可以打开这扇门，如果要保持线程唯一的话，只能有一把钥匙
			synchronized (xxxString) {
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}

		//而同步方法则是this对象
		public synchronized void output2(String name) {
			
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	
		 //加锁的房间 ，由于是静态方法，因此锁为当前方法的字节码，
		//也就是Outputer.class
	    public static synchronized void output3(String name){  
	        int len = name.length();  
	        for(int i=0;i<len;i++){  
	                System.out.print(name.charAt(i));  
	        }  
	        System.out.println();  
	    }     
		
	}
	

}