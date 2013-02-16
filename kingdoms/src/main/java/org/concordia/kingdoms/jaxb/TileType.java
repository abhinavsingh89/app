package org.concordia.kingdoms.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum(String.class)
public enum TileType {

	@XmlEnumValue("resource")
	RESOURCE, @XmlEnumValue("hazard")
	HAZARD, @XmlEnumValue("dragon")
	DRAGON, @XmlEnumValue("mountain")
	MOUNTAIN, @XmlEnumValue("goldmine")
	GOLDMINE, @XmlEnumValue("wizard")
	WIZARD;

}
