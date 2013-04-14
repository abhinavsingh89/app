package org.concordia.kingdoms.board;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameRuleException;

import print.color.Ansi.Attribute;
import print.color.Ansi.BColor;
import print.color.Ansi.FColor;
import print.color.ColoredPrinter;

import com.google.common.collect.Lists;

public class MudSlide implements IDisaster<TDCoordinate> {

	private Random random = new Random();

	private int strike = 0;

	private ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
			.foreground(FColor.WHITE).background(BColor.BLUE) // setting
			// format
			.build();

	public boolean strikeNow() {
		int rand = random.nextInt(100);
		return rand > 95 && strike <= 18;
	}

	public void strike(Board<TDCoordinate> board) throws GameRuleException {
		if (strikeNow()) {
			cp.print("\t\t\t\tMUD", Attribute.BOLD, FColor.YELLOW, BColor.GREEN);
			cp.print("SLIDE\t\t\t\t\n", Attribute.BOLD, FColor.YELLOW,
					BColor.RED);
			cp.clear();
			Set<TDCoordinate> effectedCoordinates = IDisaster.Effect
					.getEffectedCoordinates();
			for (TDCoordinate coord : effectedCoordinates) {
				System.out.print("(" + coord.getRow() + "," + coord.getColumn()
						+ ")");
			}
			board.shuffle(effectedCoordinates);
			strike++;
		}
	}

	public List<TileType> getImmuneTileTypes() {
		return Lists.newArrayList();
	}

}
