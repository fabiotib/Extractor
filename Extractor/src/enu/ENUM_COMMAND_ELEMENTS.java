package enu;

import interfaces.IEnumCommandObject;

import java.lang.reflect.Method;
import java.util.EnumSet;

import extractor.Elements;

public enum ENUM_COMMAND_ELEMENTS implements IEnumCommandObject{

	//SEARCH_BY_ID(ENUM_COMMAND.SEARCH_BY_ID, Elements.class, "getElementById", Elements.class, String.class),
	//SEARCH_BY_TAG(ENUM_COMMAND.SEARCH_BY_TAG, Elements.class, "getElementsByTag", Elements.class, String.class),
	//SEARCH_BY_CLASS(ENUM_COMMAND.SEARCH_BY_CLASS, Elements.class, "getElementsByClass", Elements.class, String.class),
	SEARCH_BY_SELECT(ENUM_COMMAND.SEARCH_BY_SELECT, Elements.class, "select", Elements.class, String.class),
	GET_FIRST(ENUM_COMMAND.GET_FIRST, Elements.class, "first", org.jsoup.nodes.Element.class),
	
	;
	
	private ENUM_COMMAND _enuCommand;
	private Class<?> _object;
	private Method _method;
	private Class<?> _returnType;
	private Class<?>[] _parameterTypes;
	
	public final ENUM_COMMAND getEnuCommand() {
		return _enuCommand;
	}

	public final Class<?> getObject() {
		return _object;
	}

	public final Method getMethod() {
		return _method;
	}

	public final Class<?> getReturnType() {
		return _returnType;
	}

	public final Class<?>[] getParameterTypes() {
		return _parameterTypes;
	}

	public static Boolean hasEnum(ENUM_COMMAND enuCommand){
		for (ENUM_COMMAND_ELEMENTS item : EnumSet.allOf(ENUM_COMMAND_ELEMENTS.class)) {
			if (item.getEnuCommand().equals(enuCommand))
				return true;
		}
		return false;
	}
	
	public static IEnumCommandObject getEnuCommand(ENUM_COMMAND enuCommand){
		for (ENUM_COMMAND_ELEMENTS item : EnumSet.allOf(ENUM_COMMAND_ELEMENTS.class)) {
			if (item.getEnuCommand().equals(enuCommand))
				return item;
		}
		return null;
	}
	
	ENUM_COMMAND_ELEMENTS(ENUM_COMMAND enuCommand, Class<?> object, String methodName, Class<?> returnType, Class<?>...parameterTypes){
		this._enuCommand = enuCommand;
		this._object = object;
		try {
			this._method = object.getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			try{
				this._method = object.getDeclaredMethod(methodName, parameterTypes);
			} catch (Throwable e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this._returnType = returnType;
		this._parameterTypes = parameterTypes;//Arrays.asList(parameterTypes);
	}
}
