package com.Concurrent.Exchanger;

import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * This class implements the consumer of the example
 *
 */
public class Consumer implements Runnable {

	/**
	 * Buffer to save the events produced
	 */
	private List<String> buffer;
	
	/**
	 * Exchager to synchronize with the consumer
	 */
	private final Exchanger<List<String>> exchanger;

	/**
	 * Constructor of the class. Initializes its attributes
	 * @param buffer Buffer to save the events produced
	 * @param exchanger Exchanger to syncrhonize with the consumer
	 */
	public Consumer(List<String> buffer, Exchanger<List<String>> exchanger){
		this.buffer=buffer;
		this.exchanger=exchanger;
	}
	
	/**
	 * Main method of the producer. It consumes all the events produced by the Producer. After
	 * processes ten events, it uses the exchanger object to synchronize with 
	 * the producer. It sends to the producer an empty buffer and receives a buffer with ten events
	 */
	@Override
	public void run() {
		int cycle=1;
		
		for (int i=0; i<10; i++){
			System.out.printf("Consumer: Cycle %d\n",cycle);

			try {
				// Wait for the produced data and send the empty buffer to the producer
				//从官方的javadoc可以知道，当一个线程到达exchange调用点时，如果它的伙伴线程此前已经调用了此方法，那么它的伙伴会被调度唤醒并与之进行对象交换，然后各自返回。如果它的伙伴还没到达交换点，那么当前线程将会被挂起，直至伙伴线程到达——完成交换正常返回；
				//或者当前线程被中断——抛出中断异常；又或者是等候超时——抛出超时异常。
				buffer=exchanger.exchange(buffer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.printf("Consumer: %d\n",buffer.size());
			
			for (int j=0; j<10; j++){
				String message=buffer.get(0);
				System.out.printf("Consumer: %s\n",message);
				buffer.remove(0);
			}
			
			cycle++;
		}
		
	}

}
