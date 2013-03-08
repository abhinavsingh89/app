package org.concordia.kingdoms.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "castle")
@XmlAccessorType(XmlAccessType.FIELD)
public class Castle implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private String name;

	@XmlElement
	private int rank;

	@XmlAttribute
	private Color color;

	public Castle() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
