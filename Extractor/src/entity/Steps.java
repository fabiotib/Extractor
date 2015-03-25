package entity;

import helper.Evaluate;
import helper.Xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import enu.ENUM_COMMAND;
import enu.ENUM_TYPES_OF_ORIGIN;

public class Steps extends ArrayList<Step> {

	private int _currencyIndex = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Steps parser(Document document, String...parameters) throws Exception {
		Steps steps = new Steps();
		Integer indexParameter = 0;
		NodeList nodeList = null;
		StringBuilder error  = null;
		try {
			nodeList = (NodeList)Xml.select(document, "/root/steps/step", XPathConstants.NODESET);//document.getElementsByTagName("step");
		} catch (Exception e) {
			throw new Exception("Problemas ao recuperar os nodes '/root/steps/step'. Por favor, verifique o arquivo xml. Mensagem: " + e.toString() + " Class: Steps - Method: parser.");
		} 
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Integer xmlId = null;
			String xmlCommand = null;
			String xmlParameter = null;
			String xmlParameter2 = null;
			error = new StringBuilder();
			try {
				
				Object xmlIdAux = Xml.select(nodeList.item(i), "@id", XPathConstants.STRING);
				
				if (xmlIdAux == null || xmlIdAux.toString().trim().equals(""))
					error.append("O atributo id e obrigatorio.");
				else if (!xmlIdAux.toString().matches("^[1-9]{1}[0-9]*"))
					error.append("O atributo id deve ser numerico e iniciar em 1.");
				else {
					xmlId = Integer.valueOf(xmlIdAux.toString());
					
					if (xmlId != i + 1)
						error.append("O atributo id nao esta na sequencia correta.");
				}
				
				xmlCommand = Xml.select(nodeList.item(i), "@command", XPathConstants.STRING);
				xmlParameter = Xml.select(nodeList.item(i), "@parameter", XPathConstants.STRING);
				xmlParameter2 = Xml.select(nodeList.item(i), "@parameter2", XPathConstants.STRING);
				
				if (xmlParameter != null && xmlParameter.contains("$parameter$") && parameters.length > indexParameter) {
					xmlParameter = xmlParameter.replace("$parameter$", parameters[indexParameter]);
					indexParameter++;
				}
	
				if (xmlParameter2 != null && xmlParameter2.contains("$parameter$") && parameters.length > indexParameter) {
					xmlParameter2 = xmlParameter2.replace("$parameter$", parameters[indexParameter]);
					indexParameter++;
				}
	
				ENUM_COMMAND enuCommand = ENUM_COMMAND.getEnuCommand(xmlCommand);
				
				Map<String, Object> validationParameters = new HashMap<String, Object>();
				validationParameters.put("parameter", xmlParameter);
				validationParameters.put("parameter2", xmlParameter2);
				
				if (validationParameters.size() > 0) {
					if (!Evaluate.eval(enuCommand.getEnuRole().getExpression(), validationParameters))
						error.append(enuCommand.getEnuRole().getError());
				}
				
				steps.add(new Step(xmlId, ENUM_COMMAND.getEnuCommand(xmlCommand), xmlParameter, xmlParameter2, error.toString()));
			} catch (Exception e) {
				steps.add(new Step(xmlId, ENUM_COMMAND.getEnuCommand(xmlCommand), xmlParameter, xmlParameter2, e.toString()));
			} 
		}
		
		return steps;
	}

	public Object getResult(Integer id, String origin) {
		Step step = getStep(id);
		if (step != null){
			if (ENUM_TYPES_OF_ORIGIN.getEnuCommand(origin) == ENUM_TYPES_OF_ORIGIN.ORIGINAL)
				return step.getOriginalResult();
			else
				return step.getResult();
		}
		return null;
	}
	
	public int getCurrencyIndex(){
		return _currencyIndex;
	}

	public void setCurrencyIndex(int currencyIndex){
		this._currencyIndex = currencyIndex;
	}

	public Step getStep(Integer id) {
		for (Step step : this) {
			if (step.getId() == id){
				return step;
			}
		}
		return null;
	}

	public Boolean hasError() {
		for (Step step : this) {
			if (step.getError() != null && !step.getError().trim().equals("")){
				return true;
			}
		}
		return false;
	}

	public Step getCurrencyStep() {
		return this.get(getCurrencyIndex());
	}

	public Step getPreviousStep() {
		if (getCurrencyIndex() > 0)
			return this.get(getCurrencyIndex() - 1);
		
		return null;
	}

	public Step getNextStep() {
		if (getCurrencyIndex() < this.size())
			return this.get(getCurrencyIndex() + 1);
		
		return null;
	}

}
