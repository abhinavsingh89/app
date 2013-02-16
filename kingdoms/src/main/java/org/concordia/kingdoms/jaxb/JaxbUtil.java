package org.concordia.kingdoms.jaxb;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.concordia.kingdoms.board.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class JaxbUtil {

	private static final Logger log = LoggerFactory.getLogger(JaxbUtil.class);

	private static JAXBContext context = null;

	public static final JaxbUtil INSTANCE = new JaxbUtil();

	private static final String GAME_STATE_XML = "kingdoms-jaxb.xml";

	public void save(GameState gameState) throws JAXBException {
		if (context == null) {
			this.context = JAXBContext.newInstance(GameState.class);
		}

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// Write to System.out
		m.marshal(gameState, System.out);
		// Write to File
		m.marshal(gameState, new File(GAME_STATE_XML));
	}

	public void testData(GameState gameState) {
		gameState.setComponentsOnBoard(10);

		List<Entry> entries = Lists.newArrayList();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				final Entry entry = new Entry();
				entry.setRow(i);
				entry.setColumn(j);
				entries.add(entry);
			}
		}
		gameState.setEntries(entries);

		gameState.setMAX_COLUMNS(Board.MAX_COLUMNS);
		gameState.setMAX_ROWS(Board.MAX_ROWS);

		Player player1 = new Player();
		player1.setChosenColor(Color.RED);
		player1.setName("test1");
		player1.setScore(123);

		Player player2 = new Player();
		player2.setChosenColor(Color.RED);
		player2.setName("test1");
		player2.setScore(123);

		gameState.setPlayers(Lists.newArrayList(player1, player2));
	}

	public GameState getGameState() {
		GameState gameState = new GameState();
		return gameState;
	}

	public GameState load(File file) throws JAXBException, IOException {
		if (this.context == null) {
			this.context = JAXBContext.newInstance(GameState.class);
		}
		Unmarshaller um = context.createUnmarshaller();
		final GameState gameState = (GameState) um.unmarshal(new FileReader(
				file));
		return gameState;
	}

}
