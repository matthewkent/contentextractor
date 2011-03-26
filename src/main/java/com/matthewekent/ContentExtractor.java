package com.matthewekent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * A text content extractor for HTML web pages. The input is an org.w3c.dom.Document object, so HTML
 * parsing must be done separately. The output is a deep copy of the original Document object with
 * text-containing nodes remaining.
 * 
 * The implementation is based on the approach described in
 * "Automating Content Extraction of HTML Documents", published 2005 by Gupta et al.
 * 
 * @author matt
 *
 */
public class ContentExtractor {
	
	private static final String[] IGNORED_ELEMENTS = {"applet", "area", "base", "br", "button", "dl", "embed", "form", "head", "h1", "h2", "h3",
		"h4", "h5", "h6", "hr", "img", "input", "label", "link", "map", "meta", "noframes", "noscript", "object", "ol", "option", "script",
		"select", "style", "textarea", "title", "ul"};
	private static final String[] LINK_CONTAINER_ELEMENTS = {"div", "td", "ul", "ol", "dl", "p", "span", "blockquote", "body"};
	private static final String[] FLATTENABLE_ELEMENTS = {"abbr", "acronym", "address", "b", "big", "cite", "code",
		"del", "dfn", "em", "font", "i", "ins", "kbd", "q", "samp", "small", "span", "strong", "sub", "sup", "tt", "var"};
	private static final double LINK_TEXT_REMOVAL_RATIO = 0.25;
	private static final int AVG_CHARS_PER_WORD = 5;
	private static final int MIN_TEXT_SUBSTANCE_CHARS = 4;
	private static final int MIN_TEXT_SUBSTANCE_WORDS = 4;

	private Set<String> ignoredElements;
	private Set<String> linkContainerElements;
	private Set<String> flattenableElements;
	
	public ContentExtractor() throws IOException {
		// initialize sets from lists
		ignoredElements = new HashSet<String>(Arrays.asList(IGNORED_ELEMENTS));
		linkContainerElements = new HashSet<String>(Arrays.asList(LINK_CONTAINER_ELEMENTS));
		flattenableElements = new HashSet<String>(Arrays.asList(FLATTENABLE_ELEMENTS));
	}
	
	/**
	 * Uses a variety of techniques to remove non-textual content from
	 * the given document. First a copy of the document is created, so the
	 * original given document object is not modified. Then the following
	 * rules are applied to the document:
	 * 
	 * 1. Flatten out all formatting nodes.
	 * 2. Remove all nodes on the ignoredTags list.
	 * 3. Compute the text/link ratio for any nodes on the linkContainers list,
	 * and remove the text and links if linkTextRemovalRatio is exceeded.
	 * 4. Remove all text nodes with less than minTextSubstanceChars characters of text.
	 * 5. Run the sentence scrubber over all remaining text nodes.
	 * 
	 * The result is a copy of the original document with only contentful nodes remaining.
	 * 
	 * @param document the document to extract content from
	 * @return a copy of the given document with non-textual content removed
	 */
	public Document extractContent(Document document) {
		Document docCopy = (Document) document.cloneNode(true);
		// flatten formatting and container nodes
		flattenNode(docCopy.getDocumentElement());
		// apply filters to the document
		applyFilters(docCopy.getDocumentElement());
		return docCopy;
	}
	
	/**
	 * Apply the following filtering techniques to the given node:
	 * 
	 * 1. If the node is not an element, ignore and return.
	 * 2. If the node is on the ignoredTags list, remove it.
	 * 3. If the node is on the linkContainers list, apply the
	 * removeLinkText algorithm to its children.
	 * 3a. Process all children of the node.
	 * 4. Remove any marked children of the node.
	 * 5. Flatten the text out of any remaining link children.
	 * 6. Collapse any fragmented text children.
	 * 7. Trim any remaining text children.
	 * 
	 * NOTE - This method proceeds breadth-first instead of depth-first
	 * so that elements on the ignoredTags list will be removed as early
	 * as possible and their children will not be processed unnecessarily.
	 * 
	 * @return true if the node should be removed, false otherwise
	 */
	protected boolean applyFilters(Node node) {
		if(!node.hasChildNodes()) {
			// return true to remove if this node is an empty non-text leaf
			return node.getNodeType() != Node.TEXT_NODE;
		}
		
		// if on the ignored list, mark for removal
		if(ignoredElements.contains(node.getNodeName())) {
			return true;
		}
		
		// mark any elements with an inline style of display:none for removal
		if(node.hasAttributes()) {
			Node style = node.getAttributes().getNamedItem("style");
			if(style != null && style.getNodeValue() != null) {
				if(Pattern.compile("display\\s*:\\s*none").matcher(style.getNodeValue()).find()) {
					return true;
				}
			}
		}
		
		// check the text/link ratio and remove text and/or
		//  link children appropriately
		if(linkContainerElements.contains(node.getNodeName())) {
			removeTextAndLinks(node);
		}
		
		// now filter all the children
		List<Node> childrenToRemove = new ArrayList<Node>();
		for(int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node child = node.getChildNodes().item(i);
			if(applyFilters(child)) {
				childrenToRemove.add(child);
			}
		}
		
		// removed all the marked children
		for(Node child: childrenToRemove) {
			node.removeChild(child);
		}
		
		// flatten any leftover links
		flattenLinks(node);
		
		// collapse any fragmented text nodes
		collapseText(node);
		
		// trim any remaining text nodes
		//  (except for links, wait for them to get collapsed by their parent)
		if(!isLink(node)) {
			trimText(node);
		}
		
		// mark for removal if no children are left
		return !node.hasChildNodes();
	}
	
	/**
	 * Trims the text children of the given node based on the following rules.
	 * 
	 * 1. Apply the sentence scrubber to each text child.
	 * 2. For each remaining text node child of this node, sum the total number of non-whitespace characters
	 * and total number of whitespace-separated words. If either of the totals do not meet the minimums,
	 * remove the text child.
	 * 
	 * NOTE - This method expects collapseText() to be called before it. Each text child is considered independently
	 * because it assumes that all neighboring text children have already been collapsed, and thus the remaining
	 * text children are separated by some significant node.
	 */
	protected void trimText(Node node) {
		if(!node.hasChildNodes()) {
			return;
		}
		
		// gather all the text children
		List<Node> textChildren = new ArrayList<Node>();
		for(int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node child = node.getChildNodes().item(i);
			if(child.getNodeType() == Node.TEXT_NODE) {
				textChildren.add(child);
			}
		}
		
		// remove the remaining text children that do not meet the min chars or words thresholds
		for(Node child: textChildren) {
			if(child.getTextContent().trim().split("\\s+").length < MIN_TEXT_SUBSTANCE_WORDS ||
					child.getTextContent().trim().replaceAll("\\s+", "").length() < MIN_TEXT_SUBSTANCE_CHARS) {
				node.removeChild(child);
			}
		}
	}
	
	/**
	 * Removes text and link children from the given node according to the following rules:
	 * 
	 * 1. Sum the total number of characters across all text children of this node.
	 * 2. Count the number of link children of this node.
	 * 3. For each list child of this node, repeat steps 1 and 2 and add the results
	 * to the overall totals.
	 * 4. If the the number of link children divided by the approximate number of words
	 * in the text is greater than or equal to linkTextRemovalRatio, remove all text and
	 * link children of this node.
	 */
	protected void removeTextAndLinks(Node node) {
		if(!node.hasChildNodes()) {
			return;
		}
		int numLinks = 0;
		int numTextChars = 0;
		List<Node> textOrLinkChildren = new ArrayList<Node>();
		for(int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node child = node.getChildNodes().item(i);
			if(child.getNodeType() == Node.TEXT_NODE) {
				textOrLinkChildren.add(child);
				numTextChars += child.getTextContent().trim().replaceAll("\\s+", "").length();
			} else if(isLink(child)) {
				textOrLinkChildren.add(child);
				numLinks++;
			}
		}
		// compute the link/text ratio
		double linkTextRatio = (double)numLinks/((double)numTextChars / (double)AVG_CHARS_PER_WORD);
		// check the threshold
		if(linkTextRatio >= LINK_TEXT_REMOVAL_RATIO) {
			for(Node child: textOrLinkChildren) {
				// ask for the parent directly because some of these nodes
				//  may be grandchildren
				child.getParentNode().removeChild(child);
			}
		}
	}
	
	protected boolean isLink(Node node) {
		return (node.getNodeName() != null && node.getNodeName().equals("a") &&
				node.getAttributes() != null && node.getAttributes().getNamedItem("href") != null);
	}
	
	/**
	 * This method performs a depth-first search starting at the given node.
	 * 
	 * Once returned from the search, if the given node is on the
	 * flattenableNodes list, clone its children and add them to its
	 * parent, then remove the node.
	 */
	protected void flattenNode(Node node) {
		if (!node.hasChildNodes()) {
			return;
		}
		// recursively flatten the children (depth-first)
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			flattenNode(node.getChildNodes().item(i));
		}
		Node parent = node.getParentNode();
		// if this node is flattenable and it has a parent, proceed
		if(flattenableElements.contains(node.getNodeName()) && parent != null) {
			// copy each child node and attach it to its grandparent
			for (int j = 0; j < node.getChildNodes().getLength(); j++) {
				Node child = node.getChildNodes().item(j);
				Node childClone = child.cloneNode(true);
				parent.insertBefore(childClone, node);
			}
			// remove the flattened node and its children
			parent.removeChild(node);
		}
	}
	
	/**
	 * Flatten any link children of the given node. This method is not
	 * recursive; only one level of flattening is performed.
	 */
	protected void flattenLinks(Node node) {
		if (!node.hasChildNodes()) {
			return;
		}
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node child = node.getChildNodes().item(i);
			if(isLink(child)) {
				// clone each grandchild and add them to the node
				for (int j = 0; j < child.getChildNodes().getLength(); j++) {
					Node grandchild = child.getChildNodes().item(j);
					Node grandchildClone = grandchild.cloneNode(true);
					node.insertBefore(grandchildClone, child);
				}
				// remove the link and its old children
				node.removeChild(child);
			}
		}
	}
	
	/**
	 * Collapses any neighboring text children of the given node into single text nodes.
	 */
	protected void collapseText(Node node) {
		if(!node.hasChildNodes()) {
			return;
		}
		Node prevText = null;
		List<Node> childrenToRemove = new ArrayList<Node>();
		for(int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node child = node.getChildNodes().item(i);
			// mark the first text node we encounter and collapse the text
			//  of any following text nodes into it
			if(child.getNodeType() == Node.TEXT_NODE) {
				if(prevText == null) {
					prevText = child;
				} else {
					prevText.setTextContent(prevText.getTextContent() + child.getTextContent());
					// mark the collapsed text child for removal, but don't remove it yet so
					//  we don't affect the for-loop over the children
					childrenToRemove.add(child);
				}
			}
			// if we encounter a non-text node, re-set the reference to
			//  the first text node and wait for the next one
			else {
				prevText = null;
			}
		}
		for(Node child: childrenToRemove) {
			node.removeChild(child);
		}
	}
}
