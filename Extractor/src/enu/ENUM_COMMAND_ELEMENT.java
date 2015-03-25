package enu;

import interfaces.IEnumCommandObject;

import java.lang.reflect.Method;
import org.jsoup.nodes.Element;

public enum ENUM_COMMAND_ELEMENT implements IEnumCommandObject{

	SEARCH_BY_ID(ENUM_COMMAND.SEARCH_BY_ID, Element.class, "getElementById", org.jsoup.nodes.Element.class, String.class),
	SEARCH_BY_TAG(ENUM_COMMAND.SEARCH_BY_TAG, Element.class, "getElementsByTag", org.jsoup.select.Elements.class, String.class),
	SEARCH_BY_CLASS(ENUM_COMMAND.SEARCH_BY_CLASS, Element.class, "getElementsByClass", org.jsoup.select.Elements.class, String.class),
	SEARCH_BY_SELECT(ENUM_COMMAND.SEARCH_BY_SELECT, Element.class, "select", org.jsoup.select.Elements.class, String.class),
	GET_VALUE_ATTRIBUTE(ENUM_COMMAND.GET_VALUE_ATTRIBUTE, Element.class, "attr", String.class, String.class),
	GET_TEXT(ENUM_COMMAND.GET_TEXT, Element.class, "text", String.class),
	GET_HTML(ENUM_COMMAND.GET_HTML, Element.class, "html", String.class),
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

	ENUM_COMMAND_ELEMENT(ENUM_COMMAND enuCommand, Class<?> object, String methodName, Class<?> returnType, Class<?>...parameterTypes){
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
