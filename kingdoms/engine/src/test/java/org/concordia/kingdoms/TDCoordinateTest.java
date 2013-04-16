/**
 * Class for coordinates test
 * @author Team K
 * @since 2.0
 */
package org.concordia.kingdoms;

import org.concordia.kingdoms.board.TDCoordinate;

import junit.framework.TestCase;

public class TDCoordinateTest extends TestCase {

	public void testEquals() {
		TDCoordinate coordinate1 = TDCoordinate.newInstance(0, 0);
		TDCoordinate coordinate2 = TDCoordinate.newInstance(0, 0);
		assertEquals(coordinate1, coordinate2);
	}
}
