package org.concordia.kingdoms.jaxb;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used for marshelling and un-marshelling
 * 
 * @author Team K
 * @since 1.1
 */
public class JaxbUtil {

	private static final Logger log = LoggerFactory.getLogger(JaxbUtil.class);

	private static JAXBContext context = null;

	public static final JaxbUtil INSTANCE = new JaxbUtil();

	public void save(GameState gameState) throws JAXBException {
		if (context == null) {
			this.context = JAXBContext.newInstance(GameState.class);
		}

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// Write to System.out
		m.marshal(gameState, System.out);
		// Write to File
		String fileName = gameState.getFileName();
		fileName = fileName + ".xml";
		m.marshal(gameState, new File(fileName));
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
