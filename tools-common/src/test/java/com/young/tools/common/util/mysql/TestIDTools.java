package com.young.tools.common.util.mysql;

import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestIDTools {

	public static void main(String[] args) throws SQLException, InterruptedException {
		Thread thread = null;
		ThreadPool pool = new ThreadPool(50, 6000, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
		
		for(int i=0;i<100;i++){
		   thread = new Thread(new IDCreateThread());
		   pool.executeThread(thread);
		}
		pool.shutDown(false);
		pool.monitorThreadPool();
	}
}
