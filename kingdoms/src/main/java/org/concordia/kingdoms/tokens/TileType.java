package org.concordia.kingdoms.tokens;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Declared enum for all types of tiles The value of these tiles is within the
 * range - 1 to 6
 * 
 * @author Team K
 * @since 1.0
 * @see Tile
 * 
 */
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
