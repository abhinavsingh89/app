package org.concordia.kingdoms.board;

import java.util.Iterator;

import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.exceptions.GameRuleException;
/**
 * @author Team K
 * @since 1.1
 */
public interface IMatrix<T extends ICoordinate> extends ScoreService<T>{

	void putComponent(Component component, T coordinate)
			throws GameRuleException;

	boolean isValidPosition(T coordinate);

	boolean isEmpty();

	boolean isEmpty(T coordinate);

	int getTotalComponents();

	Iterator<Entry<T>> getEntries();

}