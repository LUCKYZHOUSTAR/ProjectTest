/**     
 * @FileName: AtomicIntegerWithLock.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月26日 下午4:45:07   
 * @version V1.0     
 */
package com.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: AtomicIntegerWithLock
 * @Description: Locks的应用
 * @author: LUCKY
 * @date:2016年1月26日 下午4:45:07
 */
public class AtomicIntegerWithLock {

	private int value;
	private Lock lock = new ReentrantLock();

	public AtomicIntegerWithLock() {
		super();
	}

	public AtomicIntegerWithLock(int value) {
		this.value = value;
	}
	
	public final int get(){
		lock.lock();
		try {
			return value;
		} catch (Exception e) {
			lock.unlock();
		}
		
		return value;
	}

	public final void set(int newValue) {
        lock.lock();
        try {
            value = newValue;
        } finally {
            lock.unlock();
        }

    }
	public final int getAndSet(int newValue) {
        lock.lock();
        try {
            int ret = value;
            value = newValue;
            return ret;
        } finally {
            lock.unlock();
        }
    }

    public final boolean compareAndSet(int expect, int update) {
        lock.lock();
        try {
            if (value == expect) {
                value = update;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public final int getAndIncrement() {
        lock.lock();
        try {
            return value++;
        } finally {
            lock.unlock();
        }
    }

    public final int getAndDecrement() {
        lock.lock();
        try {
            return value--;
        } finally {
            lock.unlock();
        }
    }

    public final int incrementAndGet() {
        lock.lock();
        try {
            return ++value;
        } finally {
            lock.unlock();
        }
    }

    public final int decrementAndGet() {
        lock.lock();
        try {
            return --value;
        } finally {
            lock.unlock();
        }
    }

    public String toString() {
        return Integer.toString(get());
    }
	
}
