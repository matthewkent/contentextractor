package com.matthewekent;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class TestUtil {

	public static org.w3c.dom.Document getW3CDocument() {
		try {
			return HtmlUtil.getDocumentFromHtml("<html><head><title>title</title></head><body>sup</body></html>");
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static org.w3c.dom.Document getW3CDocument(String html) {
		try {
			return HtmlUtil.getDocumentFromHtml(html);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static byte[] getNormalPdfBytes() {
		byte[] pdf = {
				37, 
				80, 68, 70, 45, 49, 46, 52, 10, 37, -61, -92, -61, -68, -61, -74, -61, -97, 10, 50, 32, 
				48, 32, 111, 98, 106, 10, 60, 60, 47, 76, 101, 110, 103, 116, 104, 32, 51, 32, 48, 32, 
				82, 47, 70, 105, 108, 116, 101, 114, 47, 70, 108, 97, 116, 101, 68, 101, 99, 111, 100, 101, 
				62, 62, 10, 115, 116, 114, 101, 97, 109, 10, 120, -100, 29, -118, -79, 10, 2, 65, 12, 68, 
				-5, 124, 69, 106, 97, -49, 108, 110, -109, 85, 8, 41, -60, -69, -62, -18, 96, -63, 66, -20, 
				-44, -21, 4, -81, -15, -9, -51, -54, -64, 99, 30, 51, 52, 100, -4, -62, 7, 9, 19, 69, 
				-43, -52, -63, 122, -20, -36, -98, 120, -35, -31, -5, 63, -10, 108, 43, -100, 26, -120, 14, 7, 
				-84, 92, -30, -48, 30, -72, -97, 51, 102, -58, -10, -70, -103, -112, 39, -74, 82, 92, -84, 104, 
				-81, 76, 33, 105, 52, -99, 117, 116, -74, 42, 122, -10, 48, -15, -92, -90, 83, 45, 126, 111, 
				23, -104, 26, 44, -80, -32, 15, -109, 68, 28, -6, 10, 101, 110, 100, 115, 116, 114, 101, 97, 
				109, 10, 101, 110, 100, 111, 98, 106, 10, 10, 51, 32, 48, 32, 111, 98, 106, 10, 49, 50, 
				49, 10, 101, 110, 100, 111, 98, 106, 10, 10, 53, 32, 48, 32, 111, 98, 106, 10, 60, 60, 
				47, 84, 121, 112, 101, 47, 70, 111, 110, 116, 47, 83, 117, 98, 116, 121, 112, 101, 47, 84, 
				121, 112, 101, 49, 47, 66, 97, 115, 101, 70, 111, 110, 116, 47, 84, 105, 109, 101, 115, 45, 
				82, 111, 109, 97, 110, 10, 47, 69, 110, 99, 111, 100, 105, 110, 103, 47, 87, 105, 110, 65, 
				110, 115, 105, 69, 110, 99, 111, 100, 105, 110, 103, 10, 62, 62, 10, 101, 110, 100, 111, 98, 
				106, 10, 10, 54, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 70, 49, 32, 53, 32, 48, 
				32, 82, 10, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 10, 55, 32, 48, 32, 111, 98, 
				106, 10, 60, 60, 47, 70, 111, 110, 116, 32, 54, 32, 48, 32, 82, 10, 47, 80, 114, 111, 
				99, 83, 101, 116, 91, 47, 80, 68, 70, 47, 84, 101, 120, 116, 93, 10, 62, 62, 10, 101, 
				110, 100, 111, 98, 106, 10, 10, 49, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 84, 121, 
				112, 101, 47, 80, 97, 103, 101, 47, 80, 97, 114, 101, 110, 116, 32, 52, 32, 48, 32, 82, 
				47, 82, 101, 115, 111, 117, 114, 99, 101, 115, 32, 55, 32, 48, 32, 82, 47, 77, 101, 100, 
				105, 97, 66, 111, 120, 91, 48, 32, 48, 32, 54, 49, 50, 32, 55, 57, 50, 93, 47, 71, 
				114, 111, 117, 112, 60, 60, 47, 83, 47, 84, 114, 97, 110, 115, 112, 97, 114, 101, 110, 99, 
				121, 47, 67, 83, 47, 68, 101, 118, 105, 99, 101, 82, 71, 66, 47, 73, 32, 116, 114, 117, 
				101, 62, 62, 47, 67, 111, 110, 116, 101, 110, 116, 115, 32, 50, 32, 48, 32, 82, 62, 62, 
				10, 101, 110, 100, 111, 98, 106, 10, 10, 52, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 
				84, 121, 112, 101, 47, 80, 97, 103, 101, 115, 10, 47, 82, 101, 115, 111, 117, 114, 99, 101, 
				115, 32, 55, 32, 48, 32, 82, 10, 47, 77, 101, 100, 105, 97, 66, 111, 120, 91, 32, 48, 
				32, 48, 32, 53, 57, 53, 32, 56, 52, 50, 32, 93, 10, 47, 75, 105, 100, 115, 91, 32, 
				49, 32, 48, 32, 82, 32, 93, 10, 47, 67, 111, 117, 110, 116, 32, 49, 62, 62, 10, 101, 
				110, 100, 111, 98, 106, 10, 10, 56, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 84, 121, 
				112, 101, 47, 67, 97, 116, 97, 108, 111, 103, 47, 80, 97, 103, 101, 115, 32, 52, 32, 48, 
				32, 82, 10, 47, 79, 112, 101, 110, 65, 99, 116, 105, 111, 110, 91, 49, 32, 48, 32, 82, 
				32, 47, 88, 89, 90, 32, 110, 117, 108, 108, 32, 110, 117, 108, 108, 32, 48, 93, 10, 62, 
				62, 10, 101, 110, 100, 111, 98, 106, 10, 10, 57, 32, 48, 32, 111, 98, 106, 10, 60, 60, 
				47, 65, 117, 116, 104, 111, 114, 60, 70, 69, 70, 70, 48, 48, 53, 52, 48, 48, 54, 53, 
				48, 48, 54, 52, 48, 48, 50, 48, 48, 48, 52, 52, 48, 48, 55, 65, 48, 48, 54, 57, 
				48, 48, 55, 53, 48, 48, 54, 50, 48, 48, 54, 49, 62, 10, 47, 67, 114, 101, 97, 116, 
				111, 114, 60, 70, 69, 70, 70, 48, 48, 53, 55, 48, 48, 55, 50, 48, 48, 54, 57, 48, 
				48, 55, 52, 48, 48, 54, 53, 48, 48, 55, 50, 62, 10, 47, 80, 114, 111, 100, 117, 99, 
				101, 114, 60, 70, 69, 70, 70, 48, 48, 52, 70, 48, 48, 55, 48, 48, 48, 54, 53, 48, 
				48, 54, 69, 48, 48, 52, 70, 48, 48, 54, 54, 48, 48, 54, 54, 48, 48, 54, 57, 48, 
				48, 54, 51, 48, 48, 54, 53, 48, 48, 50, 69, 48, 48, 54, 70, 48, 48, 55, 50, 48, 
				48, 54, 55, 48, 48, 50, 48, 48, 48, 51, 50, 48, 48, 50, 69, 48, 48, 51, 51, 62, 
				10, 47, 67, 114, 101, 97, 116, 105, 111, 110, 68, 97, 116, 101, 40, 68, 58, 50, 48, 48, 
				56, 48, 52, 50, 50, 49, 54, 48, 50, 50, 49, 45, 48, 55, 39, 48, 48, 39, 41, 62, 
				62, 10, 101, 110, 100, 111, 98, 106, 10, 10, 120, 114, 101, 102, 10, 48, 32, 49, 48, 10, 
				48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 32, 54, 53, 53, 51, 53, 32, 102, 32, 10, 
				48, 48, 48, 48, 48, 48, 48, 52, 48, 56, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 
				48, 48, 48, 48, 48, 48, 48, 48, 49, 57, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 
				48, 48, 48, 48, 48, 48, 48, 50, 49, 49, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 
				48, 48, 48, 48, 48, 48, 48, 53, 53, 48, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 
				48, 48, 48, 48, 48, 48, 48, 50, 51, 49, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 
				48, 48, 48, 48, 48, 48, 48, 51, 50, 52, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 
				48, 48, 48, 48, 48, 48, 48, 51, 53, 53, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 
				48, 48, 48, 48, 48, 48, 48, 54, 52, 56, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 
				48, 48, 48, 48, 48, 48, 48, 55, 51, 49, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 
				116, 114, 97, 105, 108, 101, 114, 10, 60, 60, 47, 83, 105, 122, 101, 32, 49, 48, 47, 82, 
				111, 111, 116, 32, 56, 32, 48, 32, 82, 10, 47, 73, 110, 102, 111, 32, 57, 32, 48, 32, 
				82, 10, 47, 73, 68, 32, 91, 32, 60, 54, 50, 67, 50, 53, 65, 52, 56, 68, 51, 69, 
				51, 56, 68, 56, 51, 57, 48, 68, 69, 67, 49, 68, 49, 48, 49, 66, 54, 69, 56, 67, 
				69, 62, 10, 60, 54, 50, 67, 50, 53, 65, 52, 56, 68, 51, 69, 51, 56, 68, 56, 51, 
				57, 48, 68, 69, 67, 49, 68, 49, 48, 49, 66, 54, 69, 56, 67, 69, 62, 32, 93, 10, 
				47, 68, 111, 99, 67, 104, 101, 99, 107, 115, 117, 109, 32, 47, 65, 49, 66, 48, 52, 57, 
				49, 53, 70, 49, 54, 52, 65, 69, 49, 52, 68, 53, 54, 56, 56, 67, 53, 65, 56, 65, 
				69, 68, 57, 56, 69, 49, 10, 62, 62, 10, 115, 116, 97, 114, 116, 120, 114, 101, 102, 10, 
				57, 55, 49, 10, 37, 37, 69, 79, 70, 10
		};
		
		return pdf;
	}
	
	public static byte[] getEncryptedPdfBytes() {
		byte[] pdf = {
				37, 
				80, 68, 70, 45, 49, 46, 52, 10, 37, -61, -92, -61, -68, -61, -74, -61, -97, 10, 50, 32, 
				48, 32, 111, 98, 106, 10, 60, 60, 47, 76, 101, 110, 103, 116, 104, 32, 51, 32, 48, 32, 
				82, 47, 70, 105, 108, 116, 101, 114, 47, 70, 108, 97, 116, 101, 68, 101, 99, 111, 100, 101, 
				62, 62, 10, 115, 116, 114, 101, 97, 109, 10, 12, 3, -102, -29, -15, 35, 62, -63, 72, 126, 
				-113, -83, -39, 70, 90, -25, -27, -27, 94, 75, 126, -1, 44, -72, -128, -85, -48, -71, -39, 65, 
				-124, 127, 94, -122, -28, 120, -127, -83, 32, -43, -117, -17, -128, -66, -65, -9, 74, -123, -47, 30, 
				-36, 60, -14, -24, 60, -5, -29, 39, 8, -8, 64, -82, 11, -112, 43, -111, 58, 70, 6, 45, 
				53, 114, -60, -81, 39, -64, -31, -116, 100, -49, -14, 1, 98, -111, -72, 63, 61, -26, 90, -21, 
				-5, 109, -60, -52, 112, -110, 110, 98, 89, 41, 101, -70, 18, -110, 48, 49, -44, -105, 26, 125, 
				-124, -45, 80, -58, -33, 122, 78, 19, -36, -98, -48, -1, -46, -30, 100, -6, -84, 90, -73, 28, 
				102, -60, 84, 66, -12, -10, -84, 71, 49, -127, 10, 101, 110, 100, 115, 116, 114, 101, 97, 109, 
				10, 101, 110, 100, 111, 98, 106, 10, 10, 51, 32, 48, 32, 111, 98, 106, 10, 49, 52, 48, 
				10, 101, 110, 100, 111, 98, 106, 10, 10, 53, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 
				84, 121, 112, 101, 47, 70, 111, 110, 116, 47, 83, 117, 98, 116, 121, 112, 101, 47, 84, 121, 
				112, 101, 49, 47, 66, 97, 115, 101, 70, 111, 110, 116, 47, 84, 105, 109, 101, 115, 45, 82, 
				111, 109, 97, 110, 10, 47, 69, 110, 99, 111, 100, 105, 110, 103, 47, 87, 105, 110, 65, 110, 
				115, 105, 69, 110, 99, 111, 100, 105, 110, 103, 10, 62, 62, 10, 101, 110, 100, 111, 98, 106, 
				10, 10, 54, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 70, 49, 32, 53, 32, 48, 32, 
				82, 10, 62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 10, 55, 32, 48, 32, 111, 98, 106, 
				10, 60, 60, 47, 70, 111, 110, 116, 32, 54, 32, 48, 32, 82, 10, 47, 80, 114, 111, 99, 
				83, 101, 116, 91, 47, 80, 68, 70, 47, 84, 101, 120, 116, 93, 10, 62, 62, 10, 101, 110, 
				100, 111, 98, 106, 10, 10, 49, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 84, 121, 112, 
				101, 47, 80, 97, 103, 101, 47, 80, 97, 114, 101, 110, 116, 32, 52, 32, 48, 32, 82, 47, 
				82, 101, 115, 111, 117, 114, 99, 101, 115, 32, 55, 32, 48, 32, 82, 47, 77, 101, 100, 105, 
				97, 66, 111, 120, 91, 48, 32, 48, 32, 54, 49, 50, 32, 55, 57, 50, 93, 47, 71, 114, 
				111, 117, 112, 60, 60, 47, 83, 47, 84, 114, 97, 110, 115, 112, 97, 114, 101, 110, 99, 121, 
				47, 67, 83, 47, 68, 101, 118, 105, 99, 101, 82, 71, 66, 47, 73, 32, 116, 114, 117, 101, 
				62, 62, 47, 67, 111, 110, 116, 101, 110, 116, 115, 32, 50, 32, 48, 32, 82, 62, 62, 10, 
				101, 110, 100, 111, 98, 106, 10, 10, 52, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 84, 
				121, 112, 101, 47, 80, 97, 103, 101, 115, 10, 47, 82, 101, 115, 111, 117, 114, 99, 101, 115, 
				32, 55, 32, 48, 32, 82, 10, 47, 77, 101, 100, 105, 97, 66, 111, 120, 91, 32, 48, 32, 
				48, 32, 53, 57, 53, 32, 56, 52, 50, 32, 93, 10, 47, 75, 105, 100, 115, 91, 32, 49, 
				32, 48, 32, 82, 32, 93, 10, 47, 67, 111, 117, 110, 116, 32, 49, 62, 62, 10, 101, 110, 
				100, 111, 98, 106, 10, 10, 56, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 84, 121, 112, 
				101, 47, 67, 97, 116, 97, 108, 111, 103, 47, 80, 97, 103, 101, 115, 32, 52, 32, 48, 32, 
				82, 10, 47, 79, 112, 101, 110, 65, 99, 116, 105, 111, 110, 91, 49, 32, 48, 32, 82, 32, 
				47, 88, 89, 90, 32, 110, 117, 108, 108, 32, 110, 117, 108, 108, 32, 48, 93, 10, 62, 62, 
				10, 101, 110, 100, 111, 98, 106, 10, 10, 57, 32, 48, 32, 111, 98, 106, 10, 60, 60, 47, 
				65, 117, 116, 104, 111, 114, 60, 55, 69, 50, 70, 51, 66, 66, 55, 51, 69, 54, 67, 52, 
				50, 53, 69, 57, 68, 65, 68, 51, 57, 65, 53, 50, 53, 55, 53, 48, 70, 54, 57, 56, 
				54, 49, 65, 48, 66, 69, 51, 57, 48, 53, 54, 62, 10, 47, 67, 114, 101, 97, 116, 111, 
				114, 60, 55, 69, 50, 70, 51, 66, 66, 52, 51, 69, 55, 66, 52, 50, 53, 51, 57, 68, 
				70, 57, 51, 57, 56, 52, 50, 53, 55, 68, 62, 10, 47, 80, 114, 111, 100, 117, 99, 101, 
				114, 60, 55, 69, 50, 70, 51, 66, 65, 67, 51, 69, 55, 57, 52, 50, 53, 70, 57, 68, 
				69, 51, 51, 57, 65, 69, 50, 53, 54, 57, 48, 70, 54, 54, 56, 54, 48, 54, 48, 66, 
				69, 50, 57, 48, 53, 50, 50, 55, 57, 55, 65, 49, 51, 56, 66, 68, 57, 52, 70, 67, 
				56, 52, 56, 54, 70, 53, 52, 53, 57, 68, 69, 70, 48, 49, 67, 65, 51, 48, 62, 10, 
				47, 67, 114, 101, 97, 116, 105, 111, 110, 68, 97, 116, 101, 40, -60, -22, 92, 116, -45, 14, 
				49, 114, 14, -81, -65, 92, 98, -41, 21, 61, 59, 56, -85, 95, 60, -90, -96, 7, 0, 41, 
				62, 62, 10, 101, 110, 100, 111, 98, 106, 10, 10, 49, 48, 32, 48, 32, 111, 98, 106, 10, 
				60, 60, 47, 70, 105, 108, 116, 101, 114, 47, 83, 116, 97, 110, 100, 97, 114, 100, 47, 86, 
				32, 50, 47, 76, 101, 110, 103, 116, 104, 32, 49, 50, 56, 47, 82, 32, 51, 47, 79, 40, 
				-65, -70, -17, -7, -34, -122, -21, 25, 86, -121, -70, -50, 45, 63, -76, -11, 46, 6, 49, -119, 
				116, 111, -36, -54, -67, 50, -115, -47, -12, -16, -62, -4, 41, 47, 85, 40, -59, -10, -57, -26, 
				-106, -2, 97, -39, -63, -66, -67, -92, 126, -94, -95, 53, 0, 0, 0, 0, 0, 0, 0, 0, 
				0, 0, 0, 0, 0, 0, 0, 0, 41, 47, 80, 32, 45, 49, 48, 50, 56, 62, 62, 10, 
				101, 110, 100, 111, 98, 106, 10, 10, 120, 114, 101, 102, 10, 48, 32, 49, 49, 10, 48, 48, 
				48, 48, 48, 48, 48, 48, 48, 48, 32, 54, 53, 53, 51, 53, 32, 102, 32, 10, 48, 48, 
				48, 48, 48, 48, 48, 52, 50, 55, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 
				48, 48, 48, 48, 48, 48, 49, 57, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 
				48, 48, 48, 48, 48, 50, 51, 48, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 
				48, 48, 48, 48, 48, 53, 54, 57, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 
				48, 48, 48, 48, 48, 50, 53, 48, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 
				48, 48, 48, 48, 48, 51, 52, 51, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 
				48, 48, 48, 48, 48, 51, 55, 52, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 
				48, 48, 48, 48, 48, 54, 54, 55, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 
				48, 48, 48, 48, 48, 55, 53, 48, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 48, 48, 
				48, 48, 48, 48, 48, 57, 57, 50, 32, 48, 48, 48, 48, 48, 32, 110, 32, 10, 116, 114, 
				97, 105, 108, 101, 114, 10, 60, 60, 47, 83, 105, 122, 101, 32, 49, 49, 47, 82, 111, 111, 
				116, 32, 56, 32, 48, 32, 82, 10, 47, 69, 110, 99, 114, 121, 112, 116, 32, 49, 48, 32, 
				48, 32, 82, 10, 47, 73, 110, 102, 111, 32, 57, 32, 48, 32, 82, 10, 47, 73, 68, 32, 
				91, 32, 60, 55, 50, 52, 55, 53, 51, 69, 65, 49, 48, 69, 49, 66, 51, 50, 49, 50, 
				54, 57, 68, 53, 66, 53, 67, 68, 56, 53, 50, 48, 70, 68, 65, 62, 10, 60, 55, 50, 
				52, 55, 53, 51, 69, 65, 49, 48, 69, 49, 66, 51, 50, 49, 50, 54, 57, 68, 53, 66, 
				53, 67, 68, 56, 53, 50, 48, 70, 68, 65, 62, 32, 93, 10, 47, 68, 111, 99, 67, 104, 
				101, 99, 107, 115, 117, 109, 32, 47, 70, 52, 66, 57, 48, 56, 70, 55, 65, 55, 67, 65, 
				56, 66, 54, 55, 66, 49, 50, 66, 49, 70, 52, 55, 70, 55, 66, 69, 53, 57, 54, 49, 
				10, 62, 62, 10, 115, 116, 97, 114, 116, 120, 114, 101, 102, 10, 49, 49, 50, 57, 10, 37, 
				37, 69, 79, 70, 10
		};
		
		return pdf;
	}
}