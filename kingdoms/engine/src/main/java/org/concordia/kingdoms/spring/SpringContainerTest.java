package org.concordia.kingdoms.spring;

import org.concordia.kingdoms.GameBox;

public class SpringContainerTest {

	public SpringContainerTest() {

		final GameBox gameBox = SpringContainer.INSTANCE
				.getBean("gameBox", GameBox.class);
		System.out.println("test");
	}

	public static void main(String[] args) {
		SpringContainerTest test = new SpringContainerTest();
	}
}
