package org.concordia.kingdoms.board;

import java.util.Iterator;

public interface IEntriesAware<T extends ICoordinate> {

	void setEntries(Iterator<Entry<T>> entries);
}
