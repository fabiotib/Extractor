package enu;

import interfaces.IEnumCommandObject;

import java.lang.reflect.Method;
import org.jsoup.Connection;

public enum ENUM_COMMAND_CONNECTION implements IEnumCommandObject{

	GET_WEB_SITE(ENUM_COMMAND.GET_WEB_SITE, Connection.class, "get", org.jsoup.nodes.Document.class),
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

	ENUM_COMMAND_CONNECTION(ENUM_COMMAND enuCommand, Class<?> object, String methodName, Class<?> returnType, Class<?>...parameterTypes){
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
