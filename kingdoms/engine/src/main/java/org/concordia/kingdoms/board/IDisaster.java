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

			System.out.println("Disaster Coordinate:("
					+ randomCoordinate.getRow() + ","
					+ randomCoordinate.getColumn() + ")");

			Set<TDCoordinate> coordinates = Sets.newHashSet();

			int towardsHorizontal = random.nextInt(3);
			int towardsVertical = random.nextInt(3);
			int maxVerticalCoordinates = random.nextInt(4) + 1;

			if (towardsHorizontal == 0) {
				// only left
				fillLeftVerticalThickness(row, column, coordinates,
						towardsVertical, maxVerticalCoordinates);
			}

			if (towardsHorizontal == 1) {

				fillRightVerticalThickness(row, column, coordinates,
						towardsVertical, maxVerticalCoordinates);
			}

			if (towardsHorizontal == 2) {

				fillLeftVerticalThickness(row, column, coordinates,
						towardsVertical, maxVerticalCoordinates / 2);

				fillRightVerticalThickness(row, column, coordinates,
						towardsVertical, maxVerticalCoordinates
								- maxVerticalCoordinates / 2);
			}

			return coordinates;
		}

		private static void fillRightVerticalThickness(int row, int column,
				Set<TDCoordinate> coordinates, int towardsVertical,
				int maxVerticalCoordinates) {
			int verticalThickness = maxVerticalCoordinates;
			while (verticalThickness > 0) {
				fillVerticalCoordinates(TDCoordinate.newInstance(row, column),
						coordinates,
						random.nextInt(maxVerticalCoordinates) + 1,
						towardsVertical);
				verticalThickness--;
				column++;
			}
		}

		private static void fillLeftVerticalThickness(int row, int column,
				Set<TDCoordinate> coordinates, int towardsVertical,
				int maxVerticalCoordinates) {
			int verticalThickness = maxVerticalCoordinates;
			while (verticalThickness > 0) {
				fillVerticalCoordinates(TDCoordinate.newInstance(row, column),
						coordinates,
						random.nextInt(maxVerticalCoordinates) + 1,
						towardsVertical);
				verticalThickness--;
				column--;
			}
		}

		private static void fillVerticalCoordinates(
				TDCoordinate randomCoordinate, Set<TDCoordinate> coordinates,
				int maxCoordinates, int towardsVertical) {

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
		}

		private static void getLeftCoordinates(TDCoordinate coordinate,
				int maxCoordinates, Set<TDCoordinate> coordinates) {

			int row = coordinate.getRow();
			int column = coordinate.getColumn();
			while (column >= 0 && maxCoordinates > 0) {
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
			while (row >= 0 && maxCoordinates > 0) {
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
