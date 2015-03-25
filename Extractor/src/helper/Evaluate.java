package helper;

import java.util.Map;
import java.util.Map.Entry;
import bsh.Interpreter;

public class Evaluate {

	public static Boolean eval(String expression, Map<String, Object> parameters)
			throws Exception {
		try {
			Interpreter i = new Interpreter();
			for (Entry<String, Object> item : parameters.entrySet()) {
				i.set(item.getKey(), item.getValue());
			}
			return (Boolean) i.eval(expression);
		} catch (Throwable e) {
			throw new Exception("Falha ao avaliar a expressão. Class: Evaluate, Method: eval.");
		}
	}
}