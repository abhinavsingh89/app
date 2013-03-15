package org.concordia.kingdoms.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author Team K
 * @since 1.1
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tile implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private TileType type;

	@XmlElement
	private String name;

	@XmlElement
	private Integer value;

	/**
	 * default constructor
	 */
	public Tile() {
		this.type = null;
		this.name = null;
		this.value = null;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
