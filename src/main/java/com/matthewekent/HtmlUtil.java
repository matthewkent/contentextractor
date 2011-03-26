package com.matthewekent;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

/**
 * Miscellaneous utilities for parsing and extracting data from HTML.
 * 
 * @author matt
 *
 */
public class HtmlUtil {

	private static final String xmlTagStrippingRegex = "\\A\\s*<\\?xml.*?\\?>";
	private static final Pattern xmlTagStrippingPattern = Pattern.compile(xmlTagStrippingRegex, Pattern.DOTALL);

	/**
	 * Construct a DOM document from an HTML string.
	 * 
	 * @param html the HTML to parse
	 * @return a complete document object
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public static org.w3c.dom.Document getDocumentFromHtml(String html) throws ParserConfigurationException, IOException {
		Matcher mat = xmlTagStrippingPattern.matcher(html);
		html = mat.replaceFirst("");
		
		CleanerProperties props = new CleanerProperties();
		props.setOmitComments(true);
		props.setTranslateSpecialEntities(false);
		props.setRecognizeUnicodeChars(false);
		props.setAdvancedXmlEscape(true);
		HtmlCleaner cleaner = new HtmlCleaner(props);
		TagNode node = cleaner.clean(html);
		Document doc = new DomSerializer(props, true).createDOM(node);

		return doc;
	}
}
