package extractor;

import org.jsoup.nodes.Element;

public class Elements extends org.jsoup.select.Elements {

	public Elements getElementById(String id){
		Elements retval = new Elements();
		
		for (Element item : this) {
			Element element = item.getElementById(id);
			if (element != null)
				retval.add(element);
		}
		
		return retval;
	}

	public Elements getElementByClass(String className){
		Elements retval = new Elements();
		
		for (Element item : this) {
			Elements elements = (Elements) item.getElementsByClass(className);
			if (elements != null && elements.size() > 0)
				retval.addAll(elements);
		}
		
		return retval;
	}
	
	public Elements getElementByTag(String tagName){
		Elements retval = new Elements();
		
		for (Element item : this) {
			Elements elements = (Elements) item.getElementsByTag(tagName);
			if (elements != null && elements.size() > 0)
				retval.addAll(elements);
		}
		
		return retval;
	}

	public String attr(String tagName){
		return this.attr(tagName);
	}
}
