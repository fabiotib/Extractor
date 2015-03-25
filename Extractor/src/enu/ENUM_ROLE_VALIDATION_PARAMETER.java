package enu;

public enum ENUM_ROLE_VALIDATION_PARAMETER {

	FIRST_NULL_OR_URL("parameter.equals(\"\") || (!parameter.equals(\"\") && parameter.matches(\"^(https?|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\"))", "O primeiro parametro ou é vazio ou deve ser igual a uma URL."),
	FIRST_MANDATORY("!parameter.equals(\"\")", "O primeiro parametro e obrigatorio."),
	TWO_NULL("parameter.equals(\"\") && parameter2.equals(\"\")", "Os dois parametros sao nulos."),
	TWO_MANDATORY("!parameter.equals(\"\") && !parameter2.equals(\"\")", "Os dois parametros sao obrigatorios."),
	FIRST_NUMBER_SECOND_DOMAIN(String.format("parameter.matches(\"[1-9]+\") && (parameter2.equals(\"%s\") || parameter2.equals(\"%s\"))", ENUM_TYPES_OF_ORIGIN.ORIGINAL.getXmlCommand(), ENUM_TYPES_OF_ORIGIN.LAST.getXmlCommand()), String.format("O primeiro parametro e deve informar qual o id do passo que deseja recupar o resultado. O segundo parametro e igual ou a %s (caso seja uma lista de elementos sera a lista) ou a %s (sera um elemento da lista).", ENUM_TYPES_OF_ORIGIN.ORIGINAL.getXmlCommand(), ENUM_TYPES_OF_ORIGIN.LAST.getXmlCommand())),
	//FIRST_NUMBER_OR_ARRAY_NUMBER("parameter.matches(\"[^1-9,]+,\")", "O primeiro parametro deve ser o id do step que deseja retornar ou uma lista (separado por virgula) dos ids que deseja retornar."),
	;

	String _expression = null;
	String _error = null;
	
	public String getExpression(){
		return _expression;
	}

	public String getError(){
		return _error;
	}

	ENUM_ROLE_VALIDATION_PARAMETER(String expression, String error){
		this._expression = expression;
		this._error = error;
	}
}
