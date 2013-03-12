package org.concordia.kingdoms.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContainer extends ClassPathXmlApplicationContext {

	public static SpringContainer INSTANCE = new SpringContainer();

	public SpringContainer() {
		super("spring-beans.xml");
	}

}
