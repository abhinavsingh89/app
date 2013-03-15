package org.concordia.kingdoms.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * This class is used to initialize spring beans.
 * @author Team K
 * @since 1.1
 */
public class SpringContainer extends ClassPathXmlApplicationContext {

	public static SpringContainer INSTANCE = new SpringContainer();

	public SpringContainer() {
		super("spring-beans.xml");
	}

}
