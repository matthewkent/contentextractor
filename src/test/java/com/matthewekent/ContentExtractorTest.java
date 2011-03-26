package com.matthewekent;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class ContentExtractorTest {

	private ContentExtractor extractor;
	
	static String nodeToString(Node node) throws Exception {
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

		DOMImplementationLS impl = 
		    (DOMImplementationLS)registry.getDOMImplementation("LS");

		LSSerializer writer = impl.createLSSerializer();
		String str = writer.writeToString(node);
		// hack off the leading xml declaration
		return str.substring(str.indexOf("\n") + 1).trim();
	}
	
	@Before
	public void setUp() throws Exception {
		extractor = new ContentExtractor();
	}
	
	@After
	public void tearDown() {
		
	}

	@Test
	public void testFlattenNode() throws Exception {
		org.w3c.dom.Document doc = null;
		Node node = null;
		
		// <p><b>hay guise</b></p> -> <p>hay guise</p>
		doc = HtmlUtil.getDocumentFromHtml("<p><b>hay guise</b></p>");
		node = doc.getElementsByTagName("p").item(0);
		extractor.flattenNode(node);
		assertEquals("<p>hay guise</p>", nodeToString(node));
		
		doc = HtmlUtil.getDocumentFromHtml("<p>oh hay <b>guise</b> whats going on</p>");
		node = doc.getElementsByTagName("p").item(0);
		extractor.flattenNode(node);
		assertEquals("<p>oh hay guise whats going on</p>", nodeToString(node));
		
		// <div><p>hay guise</p></div>
		doc = HtmlUtil.getDocumentFromHtml("<div><p>hay guise</p></div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.flattenNode(node);
		assertEquals("<div><p>hay guise</p></div>", nodeToString(node));
		
		// <div><span><a href="http://www.internet.com"><i>a link</i></a><b>click it nao</b></span></div>
		//  -> <div><a href="http://www.internet.com">a link</a>click it nao</div>
		doc = HtmlUtil.getDocumentFromHtml("<div><span><a href=\"http://www.internet.com\"><i>a link</i></a><b>click it nao</b></span></div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.flattenNode(node);
		assertEquals("<div><a href=\"http://www.internet.com\">a link</a>click it nao</div>", nodeToString(node));
		
		doc = HtmlUtil.getDocumentFromHtml("<div><span><a href=\"http://www.internet.com\">link1</a> <i>some stuff</i> <b>click it nao</b></span><p>hay guise <b>whats</b> going on</p></div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.flattenNode(node);
		assertEquals("<div><a href=\"http://www.internet.com\">link1</a> some stuff click it nao<p>hay guise whats going on</p></div>", nodeToString(node));
		
		doc = HtmlUtil.getDocumentFromHtml("<div><span></span><p>hay guise <b>whats</b> going on</p></div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.flattenNode(node);
		assertEquals("<div><span/><p>hay guise whats going on</p></div>", nodeToString(node));
	}
	
	@Test
	public void testRemoveTextAndLinks() throws Exception {
		org.w3c.dom.Document doc = null;
		Node node = null;
		
		doc = HtmlUtil.getDocumentFromHtml("<div/>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.removeTextAndLinks(node);
		assertEquals("<div/>", nodeToString(node));
		
		doc = HtmlUtil.getDocumentFromHtml("<div><a href=\"http://www.internet.com\">link1</a><a href=\"http://www.internet.com\">link2</a>hay guise</div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.removeTextAndLinks(node);
		assertEquals("<div/>", nodeToString(node));
		
		doc = HtmlUtil.getDocumentFromHtml("<div><a href=\"http://www.internet.com\">link1</a>hay guise whats going on. oh not much just hot swapping.</div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.removeTextAndLinks(node);
		assertEquals("<div><a href=\"http://www.internet.com\">link1</a>hay guise whats going on. oh not much just hot swapping.</div>", nodeToString(node));
		
		doc = HtmlUtil.getDocumentFromHtml("<div>a         b  <a href=\"http://www.internet.com\">link1</a>   c  <a href=\"http://www.internet.com\">link2</a></div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.removeTextAndLinks(node);
		assertEquals("<div/>", nodeToString(node));
	}
	
	@Test
	public void testFlattenLinks() throws Exception {
		org.w3c.dom.Document doc = null;
		Node node = null;
		
		doc = HtmlUtil.getDocumentFromHtml("<div>hay guise <a href=\"http://www.internet.com\">whats</a> going on</div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.flattenLinks(node);
		assertEquals("<div>hay guise whats going on</div>", nodeToString(node));
		
		doc = HtmlUtil.getDocumentFromHtml("<div>hay guise <a href=\"http://www.internet.com\">whats</a> <a href=\"http://www.internet.com\">going</a> on</div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.flattenLinks(node);
		assertEquals("<div>hay guise whats going on</div>", nodeToString(node));
	}
	
	@Test
	public void testCollapseText() throws Exception {
		org.w3c.dom.Document doc = null;
		Node node = null;
		
		// in order to produce fragmented text nodes, use the flattenLinks method
		doc = HtmlUtil.getDocumentFromHtml("<div>hay guise <a href=\"http://www.internet.com\">whats</a> <a href=\"http://www.internet.com\">going</a> on</div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.flattenLinks(node);
		assertEquals("<div>hay guise whats going on</div>", nodeToString(node));
		assertEquals(5, node.getChildNodes().getLength());
		extractor.collapseText(node);
		assertEquals("<div>hay guise whats going on</div>", nodeToString(node));
		assertEquals(1, node.getChildNodes().getLength());
		
		doc = HtmlUtil.getDocumentFromHtml("<div>hay guise <a href=\"http://www.internet.com\">whats up</a><p></p>oh well <a href=\"http://www.internet.com\">not much</a></div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.flattenLinks(node);
		assertEquals("<div>hay guise whats up<p/>oh well not much</div>", nodeToString(node));
		assertEquals(5, node.getChildNodes().getLength());
		extractor.collapseText(node);
		assertEquals("<div>hay guise whats up<p/>oh well not much</div>", nodeToString(node));
		assertEquals(3, node.getChildNodes().getLength());
	}
	
	@Test
	public void testApplyFilters() throws Exception {
		org.w3c.dom.Document doc = null;
		Node node = null;
		
		doc = HtmlUtil.getDocumentFromHtml(
			"<div>" +
			"<ul>" +
			"<li><a href=\"http://www.internet.com\">link1</a></li>" +
			"<li><a href=\"http://www.internet.com\">link2</a></li>" +
			"<li><a href=\"http://www.internet.com\">link3</a></li>" +
			"</ul>" +
			"<h2>a cool article</h2>" +
			"<div>" +
			"<p>hay guise whats going on. just <a href=\"http://www.internet.com\">click me</a>. oh not much just chillin.</p>" +
			"<p>well actually you see its quite complicated.</p>" +
			"</div>" +
			"<h2>another cool section</h2>" +
			"<div>" +
			"<p><a href=\"http://www.internet.com\">click me</a> please <a href=\"http://www.internet.com\">click me</a> hey <a href=\"http://www.internet.com\">click me</a></p>" +
			"<p style=\"display: none;\">you cant see me</p>" +
			"</div>" +
			"<div>" +
			"<ul>" +
			"<li><a href=\"http://www.internet.com\">link4</a></li>" +
			"<li><a href=\"http://www.internet.com\">link5</a></li>" +
			"</ul>" +
			"</div>" +
			"</div>"
			);
		node = doc.getElementsByTagName("div").item(0);
		extractor.applyFilters(node);
		assertEquals(
			"<div>" +
			"<div>" +
			"<p>hay guise whats going on. just click me. oh not much just chillin.</p>" +
			"<p>well actually you see its quite complicated.</p>" +
			"</div>" +
			"</div>"
			, nodeToString(node));
	}
	
	@Test
	public void testExtractContent() throws Exception {
		org.w3c.dom.Document doc = null;
		
		doc = HtmlUtil.getDocumentFromHtml(
			"<div>" +
			"<h1>the best article evar</h1>" +
			"<ul>" +
			"<li><a href=\"http://www.internet.com\">link1</a></li>" +
			"<li><a href=\"http://www.internet.com\">link2</a></li>" +
			"</ul>" +
			"<h2>the first section</h2>" +
			"<p>hay guise whats going on. well <b>you see</b> its actually quite complicated.</p>" +
			"<p>some <a href=\"http://www.internet.com\">junk</a> in <a href=\"http://www.internet.com\">the</a> middle <a href=\"http://www.internet.com\">here</a></p>" +
			"<h2>the next section</h2>" +
			"<p>alright break it down camacho. okay <span class=\"stuff\">this</span> is how its gonna be. " +
				"go <a href=\"http://www.internet.com\">here</a> and click on a bunch of stuff.</p>" +
			"<ul>" +
			"<li><a href=\"http://www.internet.com\">link4</a></li>" +
			"<li><a href=\"http://www.internet.com\">link5</a></li>" +
			"</ul>" +
			"</div>"
			);
		org.w3c.dom.Document extracted = extractor.extractContent(doc);
		// add the extra nodes inserted by HtmlCleaner
		assertEquals(
			"<html>" +
			"<body>" +
			"<div>" +
			"<p>hay guise whats going on. well you see its actually quite complicated.</p>" +
			"<p>alright break it down camacho. okay this is how its gonna be. go here and click on a bunch of stuff.</p>" +
			"</div>" +
			"</body>" +
			"</html>"
			, nodeToString(extracted));
	}
	
	@Test
	public void testSpecialCases() throws Exception {
		org.w3c.dom.Document doc = null;
		Node node = null;
		
		doc = HtmlUtil.getDocumentFromHtml(
			"<div id=\"subNavigation\"><ul id=\"subnavUS\">" +
			"<li><a href=\"http://www.nytimes.com/pages/politics/index.html\">Politics</a></li>" +
			"<li><a href=\"http://www.nytimes.com/pages/washington/index.html\">Washington</a></li>" +
			"<li><a href=\"http://www.nytimes.com/pages/education/index.html\">Education</a></li>" +
			"</ul></div>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.applyFilters(node);
		assertEquals("<div id=\"subNavigation\"/>", nodeToString(node));
		
		doc = HtmlUtil.getDocumentFromHtml("<table><tr><td><font>Feb. 26, 2008</font><br/><b>CBSSports.com wire reports</b></td></tr></table>");
		node = doc.getElementsByTagName("td").item(0);
		extractor.flattenNode(node);
		assertEquals("<td>Feb. 26, 2008<br/>CBSSports.com wire reports</td>", nodeToString(node));
		
		doc = HtmlUtil.getDocumentFromHtml(
			"<div id=\"navigation\" >" +
			"<ul class=\"tabs\">" +
			"<li id=\"navWorld\" ><a href=\"http://www.nytimes.com/pages/world/index.html\">World</a>" +
			"</li>" +
			"<li id=\"navUS\" class=\"selected\"><a href=\"http://www.nytimes.com/pages/national/index.html\">U.S.</a>" +
			"<ul id=\"subnavUS\">" +
			"<li><a href=\"http://www.nytimes.com/pages/politics/index.html\">Politics</a>" +
			"</li>" +
			"<li><a href=\"http://www.nytimes.com/pages/washington/index.html\">Washington</a>" +
			"</li>" +
			"<li><a href=\"http://www.nytimes.com/pages/education/index.html\">Education</a>" +
			"</li>" +
			"</ul>" +
			"</li>" +
			"<li id=\"navNYRegion\" ><a href=\"http://www.nytimes.com/pages/nyregion/index.html\">N.Y. / Region</a>" +
			"</li>" +
			"<li id=\"navBusiness\" ><a href=\"http://www.nytimes.com/pages/business/index.html\">Business</a>" +
			"</li>" +
			"<li id=\"navTechnology\" ><a href=\"http://www.nytimes.com/pages/technology/index.html\">Technology</a>" +
			"</li>" +
			"<li id=\"navScience\" ><a href=\"http://www.nytimes.com/pages/science/index.html\">Science</a>" +
			"</li>");
		node = doc.getElementsByTagName("div").item(0);
		extractor.applyFilters(node);
		assertEquals("<div id=\"navigation\"/>", nodeToString(node));
	}
}
