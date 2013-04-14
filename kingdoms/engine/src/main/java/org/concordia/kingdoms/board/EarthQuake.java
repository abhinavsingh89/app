package org.concordia.kingdoms.board;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.concordia.kingdoms.domain.TileType;

import print.color.Ansi.Attribute;
import print.color.Ansi.BColor;
import print.color.Ansi.FColor;
import print.color.ColoredPrinter;

import com.google.common.collect.Lists;

public class EarthQuake implements IDisaster<TDCoordinate> {

	Random random = new Random();

	int strike = 0;

	ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
			.foreground(FColor.WHITE).background(BColor.BLUE) // setting
			// format
			.build();

	public void strike(Board<TDCoordinate> board) {
		if (strikeNow()) {
			cp.print("\t\t\t\tEARTH", Attribute.BOLD, FColor.YELLOW,
					BColor.GREEN);
			cp.print("QUAKE\t\t\t\t\n", Attribute.BOLD, FColor.YELLOW,
					BColor.RED);
			cp.clear();
			Set<TDCoordinate> effectedCoordinates = IDisaster.Effect
					.getEffectedCoordinates();
			for (TDCoordinate coord : effectedCoordinates) {
				System.out.print("(" + coord.getRow() + "," + coord.getColumn()
						+ ")");
			}
			board.returnComponent(effectedCoordinates, getImmuneTileTypes());
			strike++;
		}

	}

	public List<TileType> getImmuneTileTypes() {
		return Lists.newArrayList(TileType.DRAGON, TileType.GOLDMINE,
				TileType.WIZARD);
	}

	public boolean strikeNow() {
		int rand = random.nextInt(100);
		return rand > 95 && strike <= 18;
	}

}
