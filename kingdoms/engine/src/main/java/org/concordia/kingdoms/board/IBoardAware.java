package org.concordia.kingdoms.board;

public interface IBoardAware<T extends ICoordinate> {

	void setBoard(Board<T> board);

}
