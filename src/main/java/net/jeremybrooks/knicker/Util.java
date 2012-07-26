/*
 * Knicker is Copyright 2010-2012 by Jeremy Brooks
 *
 * This file is part of Knicker.
 *
 * Knicker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Knicker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Knicker.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.jeremybrooks.knicker;


import net.jeremybrooks.knicker.dto.AuthenticationToken;
import net.jeremybrooks.knicker.logger.KnickerLogger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;


/**
 * This class contains utility methods used by other Knicker classes.
 * <p/>
 * There are methods to do HTTP GET, POST, and DELETE requests, and methods to
 * work with xml document objects.
 *
 * @author Jeremy Brooks
 * @author Mark Colley (use CharArrayWriter to preserve special characters)
 */
public class Util {

	/*
		 * Create instances of the factory objects used by this class.
		 */
	static {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		xPathFactory = XPathFactory.newInstance();
	}

	/* Instance of the document builder factory. */
	private static DocumentBuilderFactory documentBuilderFactory;

	/* Instance of the x path factory. */
	private static XPathFactory xPathFactory;


	/**
	 * Call the URI using an HTTP GET request, returning the response as an xml document
	 * object.
	 *
	 * @param uri the URI to call.
	 * @return server response as a Document instance, or null if the server
	 *         did not return anything.
	 * @throws KnickerException if the uri is invalid, or if there are any errors.
	 */
	static Document doGet(String uri) throws KnickerException {
		return doGet(uri, null);
	}


	/**
	 * Call the URI using an HTTP GET request, returning the response as an xml
	 * Document instance.
	 * <p/>
	 * This method accepts an AuthenticationToken, and will set the request
	 * header parameter 'auth_token' with the token. If the token is null,
	 * the header parameter will not be set.
	 *
	 * @param uri   the URI to call.
	 * @param token the authentication token.
	 * @return server response as a Document instance, or null if the server
	 *         did not return anything.
	 * @throws KnickerException if the uri is invalid, or if there are any errors.
	 */
	static Document doGet(String uri, AuthenticationToken token) throws KnickerException {
		if (uri == null || uri.trim().isEmpty()) {
			throw new KnickerException("Parameter uri cannot be null or empty.");
		}
		if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
			throw new KnickerException("Parameter uri must start with http:// or https://");
		}

		KnickerLogger.getLogger().log("GET URL: '" + uri + "'");

		CharArrayWriter writer;

		// Send a GET request to the server
		try {
			URL url = parseUrl(uri);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(getConnTimeout());
			conn.setReadTimeout(getReadTimeout());

			// api key
			conn.addRequestProperty("api_key", System.getProperty("WORDNIK_API_KEY"));

			// auth header
			if (token != null) {
				conn.addRequestProperty("auth_token", token.getToken());
			}

			// Get the response
			StreamSource streamSource = new StreamSource(conn.getInputStream());

			writer = new CharArrayWriter();
			StreamResult streamResult = new StreamResult(writer);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(streamSource, streamResult);
			KnickerLogger.getLogger().log("----------RESPONSE START----------");
			KnickerLogger.getLogger().log(writer.toString());
			KnickerLogger.getLogger().log("----------RESPONSE END----------");

		} catch (Exception e) {
			throw new KnickerException("Error getting a response from the server.", e);
		}

		return getDocument(writer.toString());
	}


	/**
	 * Call the URI using an HTTP POST request, returning the response as an xml
	 * Document instance.
	 * <p/>
	 * This method accepts an AuthenticationToken, and will set the request
	 * header parameter 'auth_token' with the token. If the token is null,
	 * the header parameter will not be set.
	 *
	 * @param uri   the URI to call.
	 * @param data  the data for the POST operation.
	 * @param token authentication token instance to use for the call.
	 * @return server response as a Document instance, or null if the server
	 *         did not return any data.
	 * @throws KnickerException if the uri is invalid, or if there are any errors.
	 */
	static Document doPost(String uri, String data, AuthenticationToken token) throws KnickerException {
		if (uri == null || uri.trim().isEmpty()) {
			throw new KnickerException("Parameter uri cannot be null or empty.");
		}
		if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
			throw new KnickerException("Parameter uri must start with http:// or https://");
		}

		DataOutputStream out = null;
		BufferedReader in = null;
		StringBuilder sb = new StringBuilder();

		KnickerLogger.getLogger().log("POST URL: '" + uri + "'");

		try {
			// Send data
			URL url = parseUrl(uri);
			URLConnection conn = url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(getConnTimeout());
			conn.setReadTimeout(getReadTimeout());
			((HttpURLConnection) conn).setRequestMethod("POST");

			// api key
			conn.addRequestProperty("api_key", System.getProperty("WORDNIK_API_KEY"));
			KnickerLogger.getLogger().log("added header 'api_key', " + System.getProperty("WORDNIK_API_KEY"));
			// auth header
			if (token != null) {
				conn.addRequestProperty("auth_token", token.getToken());
				KnickerLogger.getLogger().log("added header 'auth_token', " + token.getToken());
			}
			conn.addRequestProperty("Content-Type", "text/xml");


			out = new DataOutputStream(conn.getOutputStream());
			KnickerLogger.getLogger().log("----------POST DATA START----------");
			KnickerLogger.getLogger().log(data);
			KnickerLogger.getLogger().log("----------POST DATA END----------");
			out.writeBytes(data);
			out.flush();

			// Get the response
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			KnickerLogger.getLogger().log("----------RESPONSE START----------");
			while ((line = in.readLine()) != null) {
				sb.append(line);
				KnickerLogger.getLogger().log(line);
			}
			KnickerLogger.getLogger().log("----------RESPONSE END----------");

		} catch (Exception e) {
			throw new KnickerException("Error while performing HTTP POST operation.", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				// ignore
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Exception e) {
					// ignore
				}
			}
		}

		return getDocument(sb.toString());
	}


	/**
	 * Perform an HTTP DELETE request.
	 * <p/>
	 * Note: The Wordnik API methods that require a DELETE operation do not
	 * return data.
	 *
	 * @param uri   the URI to call.
	 * @param token authentication token.
	 * @throws KnickerException if the uri is invalid, or if there are any errors.
	 */
	static void doDelete(String uri, AuthenticationToken token) throws KnickerException {
		if (uri == null || uri.trim().isEmpty()) {
			throw new KnickerException("Parameter uri cannot be null or empty.");
		}
		if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
			throw new KnickerException("Parameter uri must start with http:// or https://");
		}

		BufferedReader in = null;

		KnickerLogger.getLogger().log("DELETE URL: '" + uri + "'");


		try {
			// Send data
			URL url = parseUrl(uri);
			URLConnection conn = url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(getConnTimeout());
			conn.setReadTimeout(getReadTimeout());
			((HttpURLConnection) conn).setRequestMethod("DELETE");

			// api key
			conn.addRequestProperty("api_key", System.getProperty("WORDNIK_API_KEY"));
			KnickerLogger.getLogger().log("added header 'api_key', " + System.getProperty("WORDNIK_API_KEY"));
			// auth header
			if (token != null) {
				conn.addRequestProperty("auth_token", token.getToken());
				KnickerLogger.getLogger().log("added header 'auth_token', " + token.getToken());
			}
			conn.addRequestProperty("Content-Type", "text/xml");

			// Get the response
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		} catch (Exception e) {
			throw new KnickerException("Error while performing HTTP DELETE operation.", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				// ignore
			}
		}
	}


	static void doPut(String uri, String data, AuthenticationToken token) throws KnickerException {
		if (uri == null || uri.trim().isEmpty()) {
			throw new KnickerException("Parameter uri cannot be null or empty.");
		}
		if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
			throw new KnickerException("Parameter uri must start with http:// or https://");
		}

		DataOutputStream out = null;
		BufferedReader in = null;

		KnickerLogger.getLogger().log("PUT URL: '" + uri + "'");


		try {
			// Send data
			URL url = parseUrl(uri);
			URLConnection conn = url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(getConnTimeout());
			conn.setReadTimeout(getReadTimeout());
			((HttpURLConnection) conn).setRequestMethod("PUT");

			// api key
			conn.addRequestProperty("api_key", System.getProperty("WORDNIK_API_KEY"));
			KnickerLogger.getLogger().log("added header 'api_key', " + System.getProperty("WORDNIK_API_KEY"));
			// auth header
			if (token != null) {
				conn.addRequestProperty("auth_token", token.getToken());
				KnickerLogger.getLogger().log("added header 'auth_token', " + token.getToken());
			}
			conn.addRequestProperty("Content-Type", "text/xml");

			out = new DataOutputStream(conn.getOutputStream());
			KnickerLogger.getLogger().log("----------PUT DATA START----------");
			if (data != null) {
				KnickerLogger.getLogger().log(data);
				out.writeBytes(data);
				out.flush();
			}
			KnickerLogger.getLogger().log("----------PUT DATA END----------");

			// Get the response
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		} catch (Exception e) {
			throw new KnickerException("Error while performing HTTP PUT operation.", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				// ignore
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}


	/**
	 * Parse a string containing XML into a Document instance.
	 * <p/>
	 * If the xml passed in is null or empty, return null.
	 *
	 * @param xml the xml to parse as a Document instance.
	 * @return the document, or null.
	 * @throws KnickerException if there are any errors.
	 */
	private static Document getDocument(String xml) throws KnickerException {
		Document retDoc = null;
		InputStream in = null;

		if (xml != null && xml.trim().length() > 0) {
			try {
				in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
				retDoc = documentBuilderFactory.newDocumentBuilder().parse(in);
			} catch (Exception e) {
				throw new KnickerException("Unable to create Document.", e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
						// ignore
					}
				}
			}
		}

		return retDoc;
	}


	/**
	 * Get a value by xpath.
	 * <p/>
	 * This method will return an empty string if there are any errors, such
	 * as an invalid xpath or an invalid document object.
	 *
	 * @param document xml document to apply xpath to.
	 * @param xpath    the xpath to get the value from.
	 * @return value from the xpath, or an empty string.
	 */
	static String getValueByXPath(Document document, String xpath) {
		String value = "";
		try {
			value = xPathFactory.newXPath().evaluate(xpath, document).trim();
		} catch (Exception e) {
			// ignore; will return empty string
		}

		return value;
	}


	/**
	 * Get a value by xpath and return as an int.
	 *
	 * @param document xml document to apply xpath to.
	 * @param xpath    the xpath to get the value from.
	 * @return value contained at the xpath, or 0 if parsing fails.
	 */
	static int getValueByXPathAsInt(Document document, String xpath) {
		int x = 0;

		try {
			x = Integer.parseInt(getValueByXPath(document, xpath));
		} catch (Exception e) {
			// ignore, will return 0
		}
		return x;
	}


	/**
	 * Get a value by xpath and return as an long.
	 *
	 * @param document xml document to apply xpath to.
	 * @param xpath    the xpath to get the value from.
	 * @return value contained at the xpath, or 0 if parsing fails.
	 */
	static long getValueByXPathAsLong(Document document, String xpath) {
		long x = 0;

		try {
			x = Long.parseLong(getValueByXPath(document, xpath));
		} catch (Exception e) {
			// ignore, will return 0
		}
		return x;
	}


	/**
	 * Get a value by xpath and return as a boolean.
	 *
	 * @param document xml document to apply xpath to.
	 * @param xpath    the xpath to get the value from.
	 * @return true if the value is "1", false otherwise.
	 */
	static boolean getValueByXPathAsBoolean(Document document, String xpath) {
		boolean b = false;
		try {
			b = getValueByXPath(document, xpath).equals("true");
		} catch (Exception e) {
			// ignore; will return false
		}

		return b;
	}


	/**
	 * Get the text of the named child node, and convert to an int.
	 * <p/>
	 * NOTE: This method will return zero if the value of the node is '0', or if
	 * there is an error. Callers that care about the difference should
	 * call getNamedChildTextContent and parse the value.
	 *
	 * @param node the node to look in.
	 * @param name the name of the child node to get the value from.
	 * @return value of the named child node, or zero there is an error.
	 */
	static int getNamedChildTextContentAsInt(Node node, String name) {
		int value = 0;

		try {
			value = Integer.parseInt(getNamedChildTextContent(node, name));
		} catch (Exception e) {
			// ignore; will return zero
		}

		return value;
	}


	/**
	 * Get the text of the named child node, and convert to a double.
	 * <p/>
	 * NOTE: This method will return zero if the value of the node is '0', or if
	 * there is an error. Callers that care about the difference should
	 * call getNamedChildTextContent and parse the value.
	 *
	 * @param node the node to look in.
	 * @param name the name of the child node to get the value from.
	 * @return value of the named child node, or zero if there is an error.
	 */
	static double getNamedChildTextContentAsDouble(Node node, String name) {
		double value = 0.0;

		try {
			value = Double.parseDouble(getNamedChildTextContent(node, name));
		} catch (Exception e) {
			// ignore; will return zero
		}

		return value;
	}


	/**
	 * Get the text of the named child node.
	 *
	 * @param node the node to look in.
	 * @param name the child node to get the value of.
	 * @return text content of the child node, or an empty string.
	 */
	static String getNamedChildTextContent(Node node, String name) {
		String content = "";

		if (node != null) {
			try {
				NodeList nodes = node.getChildNodes();
				if (nodes != null) {
					for (int i = 0; i < nodes.getLength(); i++) {
						Node child = nodes.item(i);
						if (child.getNodeName().equals(name)) {
							content = child.getTextContent();
							break;
						}
					}
				}
			} catch (Exception e) {
				// ignore, will return empty string
			}
		}

		return content.trim();
	}


	/**
	 * Get a named child node from the specified node.
	 *
	 * @param node the node to search for the named child node.
	 * @param name the name of the child node to find.
	 * @return named child node, or null if it is not found.
	 */
	static Node getNamedChildNode(Node node, String name) {
		Node retNode = null;

		if (node == null || name == null || name.trim().isEmpty()) {
			retNode = null;
		} else {

			NodeList nodes = node.getChildNodes();

			if (nodes != null) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node n = nodes.item(i);
					if (n.getNodeName().equals(name)) {
						retNode = n;
						break;
					}
				}
			}
		}

		return retNode;
	}


	/**
	 * Build a parameter list from a map.
	 * <p/>
	 * The parameter list is in standard uri notation, and URL encoded.
	 *
	 * @param params the parameter map.
	 * @return a string representing the parameters.
	 * @throws KnickerException if there are any errors.
	 */
	static String buildParamList(Map<String, String> params) throws KnickerException {
		StringBuilder sb = new StringBuilder();
		for (String key : params.keySet()) {
			try {
				KnickerLogger.getLogger().log("Adding parameter key/value pair " + key + "=" + params.get(key));
				sb.append(URLEncoder.encode(key, "UTF-8")).append('=').append(URLEncoder.encode(params.get(key), "UTF-8")).append('&');
			} catch (Exception e) {
				throw new KnickerException("Error encoding.", e);
			}
		}

		int index = sb.lastIndexOf("&");
		if (index > 0) {
			sb.deleteCharAt(sb.lastIndexOf("&"));
		}


		return sb.toString();
	}


	/**
	 * Get a named value from the NamedNodeMap.
	 * <p/>
	 * If the value does not exist, or if there is an error getting data from
	 * the map, an empty string will be returned.
	 *
	 * @param map  the NamedNodeMap to get a value from.
	 * @param name the name of the attribute to find.
	 * @return value of the named attribute from the map, or an empty String.
	 */
	static String getAttribute(NamedNodeMap map, String name) {
		String value = "";

		try {
			Node node = map.getNamedItem(name);
			if (node != null) {
				value = node.getNodeValue().trim();
			}
		} catch (Exception e) {
			// ignore; will return empty string
		}

		return value;
	}


	/**
	 * Get a named value from the NamedNodeMap as an int.
	 * <p/>
	 * If the value does not exist, or if there is an error getting data from
	 * the map, 0 will be returned.
	 *
	 * @param map  the NamedNodeMap to get a value from.
	 * @param name the name of the attribute to find.
	 * @return value of the named attribute from the map as an int.
	 */
	static int getAttributeAsInt(NamedNodeMap map, String name) {
		int value = 0;

		try {
			value = Integer.parseInt(getAttribute(map, name));
		} catch (Exception e) {
			// will return 0
		}

		return value;
	}


	/**
	 * Get a named value from the NamedNodeMap as a boolean.
	 * <p/>
	 * Returns true only if the attribute is "1".
	 * <p/>
	 * If the value does not exist, or if there is an error getting data from
	 * the map, false will be returned.
	 *
	 * @param map  the NamedNodeMap to get a value from.
	 * @param name the name of the attribute to find.
	 * @return value of the named attribute from the map as a boolean.
	 */
	static boolean getAttributeAsBoolean(NamedNodeMap map, String name) {
		boolean value = false;

		try {
			value = (getAttribute(map, name)).equals("1");
		} catch (Exception e) {
			// will return false
		}

		return value;
	}


	protected static int getConnTimeout() {
		int timeout = 10000;

		try {
			timeout = Integer.parseInt("KNICKER_CONN_TIMEOUT");
		} catch (Exception e) {
			// ignore; will return 10000
		}

		return timeout;
	}


	protected static int getReadTimeout() {
		int timeout = 30000;

		try {
			timeout = Integer.parseInt("KNICKER_READ_TIMEOUT");
		} catch (Exception e) {
			// ignore; will return 30000
		}

		return timeout;
	}


	/**
	 * Parse a string representing an absolute URL to a URL with correctly encoded special characters.
	 *
	 * @param s the string representing the absolute URL.
	 * @return properly encoded URL.
	 * @throws Exception if there are errors parsing the string.
	 */
	public static URL parseUrl(String s) throws Exception {
	     URL u = new URL(s);
	     return new URI(
	            u.getProtocol(),
	            u.getAuthority(),
	            u.getPath(),
	            u.getQuery(),
	            u.getRef()).
	            toURL();
	}

}
