package org.concordia.kingdoms.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * It displays the output on the console
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public class Console<T extends TDCoordinate> implements Presentable {

	private static final Logger log = LoggerFactory.getLogger(Console.class);

	private List<List<Entry<T>>> entries;

	/**
	 * Constructor for console
	 * 
	 * @param entries
	 *            - 2-D array
	 */
	public Console(final Iterator<Entry<T>> entries) {
		resolveEntries(entries);
	}

	private List<List<Entry<T>>> resolveEntries(final Iterator<Entry<T>> entries) {
		this.entries = Lists.newArrayList();
		while (entries.hasNext()) {
			List<Entry<T>> columns = Lists.newArrayList();
			int i = 0;
			while (i < Board.MAX_COLUMNS) {
				columns.add(entries.next());
				i++;
			}
			this.entries.add(columns);
		}
		return null;
	}

	// It displays output on console
	public void present() {
		for (int i = 0; i < Board.MAX_ROWS; i++) {
			System.out
					.println("------------------------------------------------------------");
			for (int j = 0; j < Board.MAX_COLUMNS; j++) {

				System.out.print(this.entries.get(i).get(j));
			}
			System.out.println();
		}
	}

	public static int readInt(BufferedReader br, String message)
			throws IOException {

		System.out.println(message);

		boolean notNumber = true;
		int retInt = 0;

		while (notNumber) {
			String input = br.readLine();
			try {
				retInt = Integer.parseInt(input);
				notNumber = false;
			} catch (NumberFormatException ex) {
				log.error("Invalid input " + input + " to be a  number");
			}
		}// while

		return retInt;
	}

	public static String readString(BufferedReader br, String message)
			throws IOException {

		System.out.println(message);
		String input = br.readLine();
		return input;
	}

}
