package org.concordia.kingdoms.board;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Sets;

public interface IDisaster<T extends ICoordinate> extends IDisasterTiming {

	void strike(Board<T> board) throws GameRuleException;

	List<TileType> getImmuneTileTypes();

	class Effect {

		private static Random random = new Random();

		public static Set<TDCoordinate> getEffectedCoordinates() {

			int randomEntry = random.nextInt(30);

			int row = 0;
			int column = 0;

			if (randomEntry < Board.MAX_COLUMNS) {
				column = randomEntry;
			} else {
				column = randomEntry % Board.MAX_COLUMNS;
				row = randomEntry / Board.MAX_COLUMNS;
			}

			TDCoordinate randomCoordinate = TDCoordinate.newInstance(row,
					column);

			Set<TDCoordinate> coordinates = Sets.newHashSet();

			int towardsHorizontal = random.nextInt(3);
			int maxCoordinates = random.nextInt(4);
			if (towardsHorizontal == 0) {
				// only left
				getLeftCoordinates(randomCoordinate, maxCoordinates,
						coordinates);
			}

			if (towardsHorizontal == 1) {
				getRightCoordinates(randomCoordinate, maxCoordinates,
						coordinates);
			}

			if (towardsHorizontal == 2) {
				getLeftCoordinates(randomCoordinate, maxCoordinates / 2,
						coordinates);
				getRightCoordinates(randomCoordinate, maxCoordinates
						- maxCoordinates / 2, coordinates);
			}

			int towardsVertical = random.nextInt(3);

			if (towardsVertical == 0) {
				// only top
				getTopCoordinates(randomCoordinate, maxCoordinates, coordinates);
			}

			if (towardsVertical == 1) {
				getBottomCoordinates(randomCoordinate, maxCoordinates,
						coordinates);
			}

			if (towardsVertical == 2) {
				getTopCoordinates(randomCoordinate, maxCoordinates / 2,
						coordinates);
				getBottomCoordinates(randomCoordinate, maxCoordinates
						- maxCoordinates / 2, coordinates);
			}

			return coordinates;
		}

		private static void getLeftCoordinates(TDCoordinate coordinate,
				int maxCoordinates, Set<TDCoordinate> coordinates) {

			int row = coordinate.getRow();
			int column = coordinate.getColumn();
			while (column > 0 && maxCoordinates > 0) {
				TDCoordinate c = TDCoordinate.newInstance(row, column);
				coordinates.add(c);
				column--;
				maxCoordinates--;
			}
		}

		private static void getRightCoordinates(TDCoordinate coordinate,
				int maxCoordinates, Set<TDCoordinate> coordinates) {
			int row = coordinate.getRow();
			int column = coordinate.getColumn();
			while (column < Board.MAX_COLUMNS && maxCoordinates > 0) {
				TDCoordinate c = TDCoordinate.newInstance(row, column);
				coordinates.add(c);
				column++;
				maxCoordinates--;
			}
		}

		private static void getTopCoordinates(TDCoordinate coordinate,
				int maxCoordinates, Set<TDCoordinate> coordinates) {
			int row = coordinate.getRow();
			int column = coordinate.getColumn();
			while (row > 0 && maxCoordinates > 0) {
				TDCoordinate c = TDCoordinate.newInstance(row, column);
				coordinates.add(c);
				row--;
				maxCoordinates--;
			}
		}

		private static void getBottomCoordinates(TDCoordinate coordinate,
				int maxCoordinates, Set<TDCoordinate> coordinates) {
			int row = coordinate.getRow();
			int column = coordinate.getColumn();
			while (row < Board.MAX_ROWS && maxCoordinates > 0) {
				TDCoordinate c = TDCoordinate.newInstance(row, column);
				coordinates.add(c);
				row++;
				maxCoordinates--;
			}
		}

	}
}
