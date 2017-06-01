package com.sm.open.care.core.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 锁，防止重复调用
 * @ClassName: LockUtil
 * @Package: com.sm.open.care.core.utils
 */
public class LockUtil {
	
	private Lock lock = new ReentrantLock();						// 可重入锁
	private Condition condition = lock.newCondition();				// 锁状态
	private static Set<String> lockSet = new HashSet<String>();		// 加锁key
	
	/**
	 * @Description: 将需要加锁的key值进行加锁
	 */
	public void lock(String key) {
		lock.lock();
		try {
			if(lockSet.contains(key)) {
				try {
					condition.await();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}else{
				lockSet.add(key);
			}
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * @Description: 根据key值释放持有的锁
	 * @param key
	 * @return
	 */
	public void unlock(String key) {
		lock.lock();
		try {
			lockSet.remove(key);
			condition.signal();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * @Description: 创建单利锁对象
	 * @return
	 */
	public static LockUtil createLock() {
		return SingletonHolder.instance;
	}
	
	/**
	 * @Description: 静态内部类实现单利锁对象
	 */
	private static class SingletonHolder {
		private static LockUtil instance = new LockUtil();
	}
	
}
