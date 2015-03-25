package enu;

import java.util.EnumSet;

public enum ENUM_TYPES_OF_ORIGIN {

	ORIGINAL("original"),
	LAST("last");
	;

	private String _xmlCommand;

	public final String getXmlCommand() {
		return _xmlCommand;
	}
	
	public static ENUM_TYPES_OF_ORIGIN getEnuCommand(String xmlCommand){
		for (ENUM_TYPES_OF_ORIGIN item : EnumSet.allOf(ENUM_TYPES_OF_ORIGIN.class)) {
			if (item.getXmlCommand().equals(xmlCommand))
				return item;
		}
		return null;
	}

	ENUM_TYPES_OF_ORIGIN(String xmlCommand){
		this._xmlCommand = xmlCommand;
	}
}
