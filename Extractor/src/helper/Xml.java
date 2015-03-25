package helper;

import java.io.File;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Xml {

    @SuppressWarnings("null")
	public String Transform(Document document, String stylesheet) {

    	Result retval = null;
        try {
            //File stylesheet = new File(argv[0]);
            //File datafile = new File(argv[1]);

            //DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //DocumentBuilder builder = factory.newDocumentBuilder();
            //Document document = builder.parse(datafile);

            
            StreamSource stylesource = new StreamSource(stylesheet);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(stylesource);
            
             transformer.transform((Source) document, retval);
            
        } catch (Throwable e) {
            System.err.println(e);
          }
        
        return retval.toString();
    }
    
    public static Document parser(File file) throws SAXException, IOException, ParserConfigurationException{
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder builder = factory.newDocumentBuilder();
    	return builder.parse(file);
    }
    
    @SuppressWarnings("unchecked")
	public static <T> T select(Document document, String expression, QName returnType) throws XPathExpressionException {
    	XPathFactory xPathfactory = XPathFactory.newInstance();
    	XPath xpath = xPathfactory.newXPath();
    	return (T)xpath.compile(expression).evaluate(document, returnType); //XPathConstants.NODESET
    }

    @SuppressWarnings("unchecked")
	public static <T> T select(Node node, String expression, QName returnType) throws XPathExpressionException {
    	XPathFactory xPathfactory = XPathFactory.newInstance();
    	XPath xpath = xPathfactory.newXPath();
    	return (T)xpath.compile(expression).evaluate(node, returnType); //XPathConstants.NODESET
    }

    
    
    
}