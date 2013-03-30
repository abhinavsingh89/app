package org.concordia.kingdoms.ui;

public enum Strategy {

	MAXIMIZE("org.concordia.kingdoms.strategies.MaximizeStrategy"), MINIMIZE(
			"org.concordia.kingdoms.strategies.MinimizeStrategy"), RANDOM(
			"org.concordia.kingdoms.strategies.RandomStrategy"), DUMB(
			"org.concordia.kingdoms.strategies.DumbStrategy"), HUMAN(
			"org.concordia.kingdoms.strategies.UserInputStrategy");

	private String clsName;

	private Strategy(String clsName) {
		this.clsName = clsName;
	}

	public String getClassName() {
		return this.clsName;
	}

}
