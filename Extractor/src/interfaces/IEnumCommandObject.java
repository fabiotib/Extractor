package interfaces;

import java.lang.reflect.Method;
import enu.ENUM_COMMAND;

public interface IEnumCommandObject {

	public ENUM_COMMAND getEnuCommand();

	public Class<?> getObject();

	public Method getMethod();

	public Class<?> getReturnType();
	
	public Class<?>[] getParameterTypes();

}
