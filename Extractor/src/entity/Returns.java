package entity;

import java.util.ArrayList;

import helper.Xml;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Returns extends ArrayList<Return>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Returns parser(Document document, Steps steps) throws Exception {
		Returns returns = new Returns();
		NodeList nodeList = null;
		StringBuilder error  = null;
		try {
			nodeList = (NodeList)Xml.select(document, "/root/returns/return", XPathConstants.NODESET);
		} catch (Exception e) {
			throw new Exception("Problemas ao recuperar os nodes '/root/returns/return'. Por favor, verifique o arquivo xml. Mensagem: " + e.toString() + " Class: Returns - Method: parser.");
		} 
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Integer xmlId = null;
			error = new StringBuilder();
			try {
				
				Object xmlIdAux = Xml.select(nodeList.item(i), "@id", XPathConstants.STRING);
				
				if (xmlIdAux == null || xmlIdAux.toString().trim().equals(""))
					error.append("O atributo id e obrigatorio.");
				else if (!xmlIdAux.toString().matches("^[1-9]{1}[0-9]*"))
					error.append("O atributo id deve ser numerico e iniciar em 1.");
				else {
					xmlId = Integer.valueOf(xmlIdAux.toString());
				}
				
				Step step = steps.getStep(xmlId);
				if (step != null && step.getError() != null && !step.getError().trim().equals(""))
					error.append(step.getError());
				
				returns.add(new Return(xmlId, step != null ? step.getResult() : null, error.toString()));
				
			} catch (Exception e) {
				returns.add(new Return(xmlId, null, e.toString()));
			} 
		}
		return returns;
	}
	
	public Return getReturn(Integer id) {
		for (Return retval : this) {
			if (retval.getId() == id){
				return retval;
			}
		}
		return null;
	}
}
