package com.test.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtilsForExtends implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public <V> V getBean(Class<V> clazz){
		return applicationContext.getBean(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public <V> V getBean(String beanName){
		return (V) applicationContext.getBean(beanName);
	}

}
