package enu;

import interfaces.IEnumCommandObject;

import java.lang.reflect.Method;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public enum ENUM_COMMAND_DOCUMENT implements IEnumCommandObject{

	CONNECT_WEB_SITE(ENUM_COMMAND.CONNECT_WEB_SITE, Jsoup.class, "connect", org.jsoup.Connection.class, String.class),
	GET_WEB_SITE(ENUM_COMMAND.GET_WEB_SITE, Connection.class, "get", org.jsoup.nodes.Document.class),
	SEARCH_BY_ID(ENUM_COMMAND.SEARCH_BY_ID, Document.class, "getElementById", org.jsoup.nodes.Element.class, String.class),
	SEARCH_BY_TAG(ENUM_COMMAND.SEARCH_BY_TAG, Document.class, "getElementsByTag", org.jsoup.select.Elements.class, String.class),
	SEARCH_BY_CLASS(ENUM_COMMAND.SEARCH_BY_CLASS, Document.class, "getElementsByClass", org.jsoup.select.Elements.class, String.class),
	SEARCH_BY_SELECT(ENUM_COMMAND.SEARCH_BY_SELECT, Document.class, "select", org.jsoup.select.Elements.class, String.class),
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

	ENUM_COMMAND_DOCUMENT(ENUM_COMMAND enuCommand, Class<?> object, String methodName, Class<?> returnType, Class<?>...parameterTypes){
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
