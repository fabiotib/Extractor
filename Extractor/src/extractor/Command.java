package extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entity.Step;
import entity.Steps;
import enu.ENUM_ACTION;
import enu.ENUM_COMMAND;
import enu.ENUM_COMMAND_CONNECTION;
import enu.ENUM_COMMAND_DOCUMENT;
import enu.ENUM_COMMAND_ELEMENT;
import enu.ENUM_COMMAND_ELEMENTS;
import enu.ENUM_COMMAND_JSOUP;
import interfaces.IEnumCommandObject;

public abstract class Command {

	public abstract void extract(Steps steps);
	
	private IEnumCommandObject getCommand(Steps steps){

		Step currencyStep = steps.getCurrencyStep();
		Step previousStep = steps.getPreviousStep();
		Class<?> classResult = null;
		if (previousStep != null && previousStep.getResult() != null)
			classResult = previousStep.getResult().getClass();
		
		switch (currencyStep.getEnuCommand()) {
			case CONNECT_WEB_SITE:
				return ENUM_COMMAND_JSOUP.CONNECT_WEB_SITE;
			case GET_WEB_SITE:
				return ENUM_COMMAND_CONNECTION.GET_WEB_SITE;
			case SEARCH_BY_ID:
				if (classResult != null && classResult.equals(Document.class))
					return ENUM_COMMAND_DOCUMENT.SEARCH_BY_ID;
				else if (classResult != null && classResult.equals(Element.class))
					return ENUM_COMMAND_ELEMENT.SEARCH_BY_ID;
				else
					currencyStep.setError(String.format("Comando desconhecido. Commando: %s - Tipo do objeto: %s", currencyStep.getEnuCommand().getXmlCommand(), classResult != null ? classResult.getName() : ""));
				break;
			case SEARCH_BY_TAG:
				if (classResult != null && classResult.equals(Document.class))
					return ENUM_COMMAND_DOCUMENT.SEARCH_BY_TAG;
				else if (classResult != null && classResult.equals(Element.class))
					return ENUM_COMMAND_ELEMENT.SEARCH_BY_TAG;
				else
					currencyStep.setError(String.format("Comando desconhecido. Commando: %s - Tipo do objeto: %s", currencyStep.getEnuCommand().getXmlCommand(), classResult != null ? classResult.getName() : ""));
				break;
			case SEARCH_BY_CLASS:
				if (classResult != null && classResult.equals(Document.class))
					return ENUM_COMMAND_DOCUMENT.SEARCH_BY_CLASS;
				else if (classResult != null && classResult.equals(Element.class))
					return ENUM_COMMAND_ELEMENT.SEARCH_BY_CLASS;
				else
					currencyStep.setError(String.format("Comando desconhecido. Commando: %s - Tipo do objeto: %s", currencyStep.getEnuCommand().getXmlCommand(), classResult != null ? classResult.getName() : ""));
				break;
			case SEARCH_BY_SELECT:
				if (classResult != null && classResult.equals(Document.class))
					return ENUM_COMMAND_DOCUMENT.SEARCH_BY_SELECT;
				else if (classResult != null && classResult.equals(Element.class))
					return ENUM_COMMAND_ELEMENT.SEARCH_BY_SELECT;
				else if (classResult != null && classResult.equals(Elements.class))
					return ENUM_COMMAND_ELEMENTS.SEARCH_BY_SELECT;
				else
					currencyStep.setError(String.format("Comando desconhecido. Commando: %s - Tipo do objeto: %s", currencyStep.getEnuCommand().getXmlCommand(), classResult != null ? classResult.getName() : ""));
				break;
			case GET_FIRST:
					return ENUM_COMMAND_ELEMENTS.GET_FIRST;
			case GET_TEXT:
				if (classResult != null && classResult.equals(Document.class))
					return ENUM_COMMAND_DOCUMENT.GET_TEXT;
				else if (classResult != null && classResult.equals(Element.class))
					return ENUM_COMMAND_ELEMENT.GET_TEXT;
				else
					currencyStep.setError(String.format("Comando desconhecido. Commando: %s - Tipo do objeto: %s", currencyStep.getEnuCommand().getXmlCommand(), classResult != null ? classResult.getName() : ""));
				break;
			case GET_HTML:
				if (classResult != null && classResult.equals(Document.class))
					return ENUM_COMMAND_DOCUMENT.GET_HTML;
				else if (classResult != null && classResult.equals(Element.class))
					return ENUM_COMMAND_ELEMENT.GET_HTML;
				else
					currencyStep.setError(String.format("Comando desconhecido. Commando: %s - Tipo do objeto: %s", currencyStep.getEnuCommand().getXmlCommand(), classResult != null ? classResult.getName() : ""));
				break;
			case GET_VALUE_ATTRIBUTE:
				if (classResult != null && classResult.equals(Element.class))
					return ENUM_COMMAND_ELEMENT.GET_VALUE_ATTRIBUTE;
				else
					currencyStep.setError(String.format("Comando desconhecido. Commando: %s - Tipo do objeto: %s", currencyStep.getEnuCommand().getXmlCommand(), classResult != null ? classResult.getName() : ""));
				break;
			default:
				currencyStep.setError(String.format("Comando desconhecido. Commando: %s - Tipo do objeto: %s", currencyStep.getEnuCommand().getXmlCommand(), classResult != null ? classResult.getName() : ""));
		}
		
		return null;
	}
	
	private void loop(Steps steps){
		try {

			Elements elements = (Elements)steps.getPreviousStep().getResult();
			for (int i = 0; i < elements.size(); i++) {
				steps.getPreviousStep().setResult(elements.get(i));
				executeSteps(steps);
				if (steps.getCurrencyStep().getNextAction() == ENUM_ACTION.ERROR)
					return;
				else if (i + 1 >= elements.size())
					steps.getCurrencyStep().setNextAction(ENUM_ACTION.RETURN);
				else if (steps.getCurrencyStep().getNextAction() == ENUM_ACTION.RETURN)
					return;
			}
		} catch (Exception e) {
			steps.getCurrencyStep().setError(e.toString());
		}
	}

	public void executeSteps(Steps steps){

		try {
			steps.getCurrencyStep().setNextAction(null);
			int originalIndex = steps.getCurrencyIndex();
			for (int i = originalIndex; i < steps.size(); i++) {
				steps.setCurrencyIndex(i);
				resolveStep(steps);
				if (steps.getCurrencyStep().getNextAction() == ENUM_ACTION.ERROR)
					return;
				else if (steps.getCurrencyStep().getNextAction() == ENUM_ACTION.BREAK) {
					steps.getCurrencyStep().setNextAction(null);
					steps.setCurrencyIndex(originalIndex);
					break;
				} else if (steps.getCurrencyStep().getNextAction() == ENUM_ACTION.RETURN)
					return;
				else if (i + 1 >= steps.size())
					steps.getCurrencyStep().setNextAction(ENUM_ACTION.RETURN);
				else steps.getCurrencyStep().setNextAction(null);
				
			}
			
		} catch (Exception e) {
			steps.getCurrencyStep().setError(e.toString());
		}
	}

	private void resolveStep(Steps steps){
		Step currencyStep = steps.getCurrencyStep();
		Step previousStep = steps.getPreviousStep();

		try {
	
			if (previousStep != null &&
				previousStep.getResult() != null &&
				previousStep.getResult().getClass() == Elements.class && 
				!ENUM_COMMAND_ELEMENTS.hasEnum(steps.getCurrencyStep().getEnuCommand())) {
				loop(steps);
			} else if (currencyStep.getEnuCommand() == ENUM_COMMAND.EQUALS){
				if (!equals(previousStep.getResult(), currencyStep.getParameter()))
					currencyStep.setNextAction(ENUM_ACTION.BREAK);//DA O BREAK, POIS NÃO ENTROU NO IF
			} else if (currencyStep.getEnuCommand() == ENUM_COMMAND.EQUALS_IGNORE_CASE){
				if (!equalsIgnoreCase(previousStep.getResult(), currencyStep.getParameter()))
					currencyStep.setNextAction(ENUM_ACTION.BREAK);//DA O BREAK, POIS NÃO ENTROU NO IF
			} else if (currencyStep.getEnuCommand() == ENUM_COMMAND.EQUALS_IGNORE_SPECIAL_CARACTER){
				if (!equalsIgnoreSpecialCaracter(previousStep.getResult(), currencyStep.getParameter()))
					currencyStep.setNextAction(ENUM_ACTION.BREAK);//DA O BREAK, POIS NÃO ENTROU NO IF
			} else if (currencyStep.getEnuCommand() == ENUM_COMMAND.EQUALS_REGULAR_EXPRESSION){
				if (!equalsRegularExpression(previousStep.getResult(), currencyStep.getParameter()))
					currencyStep.setNextAction(ENUM_ACTION.BREAK);//DA O BREAK, POIS NÃO ENTROU NO IF
			} else if (currencyStep.getEnuCommand() == ENUM_COMMAND.CONTAINS){
				if (!contains(previousStep.getResult(), currencyStep.getParameter()))
					currencyStep.setNextAction(ENUM_ACTION.BREAK);//DA O BREAK, POIS NÃO ENTROU NO IF
			} else if (currencyStep.getEnuCommand() == ENUM_COMMAND.RETURN){
				currencyStep.setNextAction(ENUM_ACTION.RETURN);//DA O BREAK, POIS NÃO ENTROU NO IF
			} else if (currencyStep.getEnuCommand() == ENUM_COMMAND.REPLACE_ALL){
				currencyStep.setResult(replaceAll(previousStep.getResult(), currencyStep.getParameter(), currencyStep.getparameter2()));
			} else if (currencyStep.getEnuCommand() == ENUM_COMMAND.GET_RESULT_STEP){
				currencyStep.setResult(steps.getResult(Integer.valueOf(currencyStep.getParameter()), currencyStep.getparameter2()));
			} else {
				if (currencyStep.getEnuCommand() == ENUM_COMMAND.CONNECT_WEB_SITE){
					currencyStep.setParameter(previousStep.getResult().toString());
					currencyStep.setResult(null);
				}
				executeCommand(steps);
			}
		} catch (Exception e) {
			currencyStep.setError(e.toString());
		}
	}

	public void executeCommand(Steps steps){

		Step currencyStep = steps.getCurrencyStep();
		Step previousStep = steps.getPreviousStep();
		try {

			Object retval = null;

			IEnumCommandObject command = getCommand(steps);
			
			if (currencyStep.getNextAction() == ENUM_ACTION.ERROR)
				return;
			
			if (previousStep != null) {
				if (currencyStep.getParameter() != null)
					retval = command.getMethod().invoke(previousStep.getResult(), currencyStep.getParameter());
				else
					retval = command.getMethod().invoke(previousStep.getResult());	
			} else { 
				if (currencyStep.getParameter() != null)
					retval = command.getMethod().invoke(null, currencyStep.getParameter());	
				else 
					retval = command.getMethod().invoke(null);
			}

			steps.getCurrencyStep().setResult(retval);
		} catch (Exception e) {
			currencyStep.setError(e.toString());
		}
	}

	private Boolean equalsIgnoreCase(Object object, String text){ 
		String text1 = resolveText(object);
		if (text1 != null)
			return text1.trim().equalsIgnoreCase(text.trim());

		return false;
	}

	private Boolean equals(Object object, String text){ 
		String text1 = resolveText(object);
		if (text1 != null)
			text1.trim().equals(text.trim());

		return false;
	}

	private Boolean equalsIgnoreSpecialCaracter(Object object, String text){
		String text1 = resolveText(object);
		if (text1 != null)
			return text1.replaceAll("[^a-zA-Z0-9 ]", "").trim().equalsIgnoreCase(text.replaceAll("[^a-zA-Z0-9 ]", "").trim());
		
		return false;
	}

	private Boolean equalsRegularExpression(Object object, String text){
		String text1 = resolveText(object);
		if (text1 != null)
			return text1.trim().matches(text.trim());
		
		return false;
	}

	private Boolean contains(Object object, String text){
		String text1 = resolveText(object);
		if (text1 != null)
			return text1.trim().contains(text.trim());
		
		return false;
	}

	private String replaceAll(Object object, String regex, String replacement){
		String text1 = resolveText(object);
		if (text1 != null)
			return text1.replaceAll(regex, replacement == null ? "" : replacement);
		
		return null;
	}

	private String resolveText(Object object){
		String text1 = null;
		if (object.getClass() == Element.class)
			text1 = ((Element)object).text().trim();
		else if (object.getClass() == String.class)
			text1 = ((String)object).trim();

		return text1;
	}

}
