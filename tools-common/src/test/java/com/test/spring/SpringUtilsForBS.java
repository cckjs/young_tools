package com.test.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtilsForBS extends ApplicationObjectSupport{

	private static ApplicationContext ac;

	private synchronized void init() {
		if (ac == null) {
			ac = getApplicationContext();
			if (ac == null) {
				ac = new ClassPathXmlApplicationContext(
						new String[] { "datasource.xml" });
			}
		}
	}

	public  Object getBean(String beanName) {
		init();
		return ac.getBean(beanName);
	}

	public  <V> V getBean(Class<V> beanClass) {
		init();
		return ac.getBean(beanClass);
	}

}
