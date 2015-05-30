package com.young.tools.common.util.mysql;

import com.test.spring.SpringUtils;
import com.young.tools.common.util.mysql.replication.ExtendRelication;
import com.young.tools.common.util.mysql.replication.bean.ShardRoute;

public class RouteThread implements Runnable{

	private long start;
	
	private long interval;
	
	public RouteThread(long start,long interval){
		this.start = start;
		this.interval = interval;
	}
	
	public void run() {
		try{
			ExtendRelication dao = SpringUtils.getBean(ExtendRelication.class);
		ShardRoute route = null;
		for (int i = 0; i < interval; i++) {
			route = dao.findReadRoute("user_business", i+start);
			System.out.println(route.getFragment().getTableIndex()+","+route.getDbConfig().getRole());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
