package com.test.spring;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.young.tools.common.util.mysql.id.datasource.IDDataSourcePool;

public class SpringUtils {

	private static ApplicationContext ac;

	private synchronized static void init() {
		if (ac == null) {
			ac = ContextLoader.getCurrentWebApplicationContext();
			if (ac == null) {
				ac = new ClassPathXmlApplicationContext(
						new String[] { "spring.xml" });
			}
		}
	}

	public static Object getBean(String beanName) {
		init();
		return ac.getBean(beanName);
	}

	public static <V> V getBean(Class<V> beanClass) {
		init();
		return ac.getBean(beanClass);
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(SpringUtils.getBean(IDDataSourcePool.class).getRDBMSDataSource());
		System.out.println(SpringUtils.getBean(IDDataSourcePool.class).getRDBMSDataSource());
		System.out.println(SpringUtils.getBean(IDDataSourcePool.class).getRDBMSDataSource());
		System.out.println(SpringUtils.getBean(IDDataSourcePool.class).getRDBMSDataSource());
		System.out.println(SpringUtils.getBean(IDDataSourcePool.class).getRDBMSDataSource());
	}
}
