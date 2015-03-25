package enu;

import interfaces.IEnumCommandObject;

import java.lang.reflect.Method;

public enum ENUM_COMMAND_STRING implements IEnumCommandObject{

	EQUALS(ENUM_COMMAND.EQUALS, String.class, "equals", Boolean.class, Object.class),
	CONTAINS(ENUM_COMMAND.CONTAINS, String.class, "contains", Boolean.class, CharSequence.class),
	EQUALS_IGNORE_CASE(ENUM_COMMAND.CONTAINS, String.class, "equals", Boolean.class, CharSequence.class),
	EQUALS_IGNORE_SPECIAL_CARACTER(ENUM_COMMAND.CONTAINS, String.class, "equals", Boolean.class, CharSequence.class),
/*	REPLACE_ALL(ENUM_COMMAND.REPLACE_ALL, String.class, "replaceAll", String.class, String.class, String.class),
	UPPER_CASE(ENUM_COMMAND.UPPER_CASE, String.class, "toUpperCase", String.class),
	LOWER_CASE(ENUM_COMMAND.LOWER_CASE, String.class, "toLowerCase", String.class),
*/
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

	ENUM_COMMAND_STRING(ENUM_COMMAND enuCommand, Class<?> object, String methodName, Class<?> returnType, Class<?>...parameterTypes){
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
