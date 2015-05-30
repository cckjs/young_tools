package com.young.tools.common.util.mysql;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestRoute {

	public static void main(String[] args) throws Exception {
		ThreadPool pool = new ThreadPool(40, 100, 1, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>());
		for (int i = 0; i < 10; i++) {
			pool.executeThread(new Thread(new RouteThread(i * 3000, 3000)));
		}
		pool.shutDown(false);
		pool.monitorThreadPool();
	}
}
