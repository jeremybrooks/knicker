/*
 * Knicker is Copyright 2010 by Jeremy Brooks
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

// JAVA UTILITY
import java.util.ArrayList;
import java.util.List;

// KNICKER
import net.jeremybrooks.knicker.dto.AuthenticationToken;
import net.jeremybrooks.knicker.dto.Phrase;
import net.jeremybrooks.knicker.dto.Definition;
import net.jeremybrooks.knicker.dto.Example;
import net.jeremybrooks.knicker.dto.FrequencySummary;
import net.jeremybrooks.knicker.dto.Pronunciation;
import net.jeremybrooks.knicker.dto.PunctuationFactor;
import net.jeremybrooks.knicker.dto.Related;
import net.jeremybrooks.knicker.dto.SearchResult;
import net.jeremybrooks.knicker.dto.TokenStatus;
import net.jeremybrooks.knicker.dto.Word;
import net.jeremybrooks.knicker.dto.WordFrequency;
import net.jeremybrooks.knicker.dto.WordList;
import net.jeremybrooks.knicker.dto.WordListWord;
import net.jeremybrooks.knicker.dto.WordOfTheDay;

// XML
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *
 * @author jeremyb
 */
class DTOBuilder {

    /**
     * Parse XML document into a Word object.
     *
     * XML looks like this:
	<word>
	    <id>5008170</id>
	    <wordstring>Zeebra</wordstring>
	    <suggestions>
		<suggestion>zebra</suggestion>
	    </suggestions>
	</word>
     * 
     * @param doc XML document to parse.
     * @return Word object.
     * @throws KnickerException if there are any errors.
     */
    static Word buildWord(Document doc) throws KnickerException {
	Word w = new Word();

	try {
	    w.setId(Util.getValueByXPath(doc, "/word/id"));
	    w.setWordstring(Util.getValueByXPath(doc, "/word/wordstring"));
	    w.setCanonicalForm(Util.getValueByXPath(doc, "/word/canonicalForm"));

	    NodeList sugs = doc.getElementsByTagName("suggestions");
	    for (int i = 0; i < sugs.getLength(); i++) {
		Node sug = sugs.item(i);
		w.addSuggestion(Util.getNamedChildTextContent(sug, "suggestion"));
	    }

	} catch (Exception e) {
	    throw new KnickerException("Error while building Word.", e);
	}

	return w;
    }


    /**
     * Parse XML document into a list of Phrase objects.
     *
     * XML looks like this:
	<bigrams>
	    <bigram>
		<count>353</count>
		<mi>12.414364902158674</mi>
		<wlmi> 20.877889275429855</wlmi>
		<gram1>Christmas</gram1>
		<gram2>Eve</gram2>
	    </bigram>
	    <bigram>
		<count>268</count>
		<mi>10.707678907779517</mi>
		<wlmi>18.77376809823729</wlmi>
		<gram1>Christmas</gram1>
		<gram2>tree</gram2>
	    </bigram>
	<bigrams>
     * 
     * @param doc XML document to parse.
     * @return list of Phrase objects.
     * @throws KnickerException if there are any errors.
     */
    static List<Phrase> buildPhrase(Document doc) throws KnickerException {
	List<Phrase> phrases = new ArrayList<Phrase>();

	try {
	    NodeList nodes = doc.getElementsByTagName("bigram");
	    for (int i = 0; i < nodes.getLength(); i++) {
		Phrase p = new Phrase();
		Node node = nodes.item(i);
		p.setCount(Util.getNamedChildTextContentAsInt(node, "count"));
		p.setMi(Util.getNamedChildTextContent(node, "mi"));
		p.setWlmi(Util.getNamedChildTextContent(node, "wlmi"));
		p.setGram1(Util.getNamedChildTextContent(node, "gram1"));
		p.setGram2(Util.getNamedChildTextContent(node, "gram2"));

		phrases.add(p);
	    }
	} catch (Exception e) {
	    throw new KnickerException("Error while building BiGram list.", e);
	}

	return phrases;
    }


    /**
     * Parse XML document into a list of Definition objects.
     *
     * XML looks like this:
	<definitions>
	    <definition sequence="0" id="3117123">
		<citations>
		    <citation>
			<cite>Laying aside their often rancorous debate...</cite>
			<source>Mark Derr (N. Y. Times, Nov. 2, 1999...</source>
		    </citation>
		</citations>
		<text>Any animal belonging to the natural family...</text>
		<exampleUses>
		    <exampleUsage>
			<text>The patient regurgitated the food we gave him last night</text>
		    </exampleUsage>
		    <exampleUsage>
		        <text>He purged continuously</text>
		    </exampleUsage>
		</exampleUses>
		<headword>cat</headword>
		<labels>
		    <label type="fld">
			<text>(Zoöl.)</text>
		    </label>
		</labels>
		<notes>
		    <note pos="0">
			<value>☞ The domestic cat includes many...</value>
		    </note>
		</notes>
		<partOfSpeech>noun</partOfSpeech>
		<seqString>1.</seqString>
	    </definition>
		<definition sequence="1" id="3117124">
		<headword>cat</headword>
		<labels>
		    <label type="fld">
			<text>(Naut.)</text>
		    </label>
		</labels>
		<partOfSpeech>noun</partOfSpeech>
		<seqString>2.</seqString>
	    </definition>
	</definitions>
     *
     * @param doc the XML document to parse.
     * @return list of Definition objects.
     * @throws KnickerException if there are any errors.
     */
    static List<Definition> buildDefinitions(Document doc) throws KnickerException {
	List<Definition> definitions = new ArrayList<Definition>();

	NodeList defNodes = doc.getElementsByTagName("definition");
	for (int i = 0; i < defNodes.getLength(); i++) {
	    Definition definition = new Definition();

	    Node defNode = defNodes.item(i);
	    NamedNodeMap nnm = defNode.getAttributes();
	    definition.setSequence(Util.getAttributeAsInt(nnm, "sequence"));
	    definition.setId(Util.getAttribute(nnm, "id"));
	    definition.setText(Util.getNamedChildTextContent(defNode, "text"));
	    definition.setHeadword(Util.getNamedChildTextContent(defNode, "headword"));
	    definition.setPartOfSpeech(Util.getNamedChildTextContent(defNode, "partOfSpeech"));
	    definition.setSeqString(Util.getNamedChildTextContent(defNode, "seqString"));

	    Node citeNode = Util.getNamedChildNode(defNode, "citations");
	    if (citeNode != null) {
		NodeList citeNodes = citeNode.getChildNodes();
		for (int j = 0; j < citeNodes.getLength(); j++) {
		    Node cite = citeNodes.item(j);
		    if (cite.getNodeName().equals("citation")) {
			definition.addCitation(
				Util.getNamedChildTextContent(cite, "cite"),
				Util.getNamedChildTextContent(cite, "source"));
		    }
		}
	    }

	    Node labelsNode = Util.getNamedChildNode(defNode, "labels");
	    if (labelsNode != null) {
		NodeList labelNodes = labelsNode.getChildNodes();
		for (int j = 0; j < labelNodes.getLength(); j++) {
		    Node node = labelNodes.item(j);
		    if (node.getNodeName().equals("label")) {
			definition.addLabel(
				Util.getAttribute(node.getAttributes(), "type"),
				Util.getNamedChildTextContent(node, "text"));
		    }
		}
	    }

	    Node notesNode = Util.getNamedChildNode(defNode, "notes");
	    if (notesNode != null) {
		NodeList noteNodes = notesNode.getChildNodes();
		for (int j = 0; j < noteNodes.getLength(); j++) {
		    Node node = noteNodes.item(j);
		    if (node.getNodeName().equals("note")) {
			definition.addNote(
				Util.getAttributeAsInt(node.getAttributes(), "pos"),
				Util.getNamedChildTextContent(node, "value"));
		    }
		}
	    }


	    Node examplesNode = Util.getNamedChildNode(defNode, "exampleUses");
	    if (examplesNode != null) {
		NodeList exampleNodes = examplesNode.getChildNodes();
		for (int j = 0; j < exampleNodes.getLength(); j++) {
		    Node node = exampleNodes.item(j);
		    if (node.getNodeName().equals("exampleUsage")) {
			definition.addExampleUse(Util.getNamedChildTextContent(node, "text"));
		    }
		}
	    }

	    definitions.add(definition);
	}


	return definitions;
    }


    /**
     * Parse an XML document into a list of Example objects.
     *
     * XML looks like this:
	<examples>
	    <example>
		<display>That mountain cat is a very confused young hunter, and he might not attack you the next time, and you might be able to dive out of the way again.</display>
		<documentId>22330027</documentId>
		<exampleId>281222695</exampleId>
		<scoreId>3305513809</scoreId>
		<provider>
		    <id>711</id>
		    <name>wordnik</name>
		</provider>
		<rating>755.66016</rating>
		<title>Timegod's World</title>
		<wordstring>cat</wordstring>
		<year>1992</year>
	    </example>
	    <example>
		<display>When a person who has worked hard and scored brilliantly in cat is rejected because of some scores in your 12th (any board in India anyways is based on rote learning) it is a pity.</display>
		<documentId>14411375</documentId>
		<exampleId>96214469</exampleId>
		<scoreId>3379638015</scoreId>
		<provider>
		    <id>712</id>
		    <name>spinner</name>
		</provider>
		<rating>749.0</rating>
		<url>http://www.pagalguy.com/forum/showthread.php?p=1507312#post1507312</url>
		<wordstring>cat</wordstring>
		<year>2009</year>
	    </example>
	    <example>
		<display>Hopefully, this cat is about to illustrate how not only human, but all mammals, feel about mimes.</display>
		<documentId>18957123</documentId>
		<exampleId>209733335</exampleId>
		<scoreId>2216887758</scoreId>
		<provider>
		    <id>712</id>
		    <name>spinner</name>
		</provider>
		<rating>604.0</rating>
		<title>Dlisted - Be Very Afraid</title>
		<url>http://dlisted.com/node/33002</url>
		<wordstring>cat</wordstring>
		<year>2009</year>
	    </example>
	    <example>
		<display>I've just read that this cat is a cross between a serval cat and a domestic cat.</display>
		<documentId>20451030</documentId>
		<exampleId>233273752</exampleId>
		<scoreId>2660509130</scoreId>
		<provider>
		    <id>712</id>
		    <name>spinner</name>
		</provider>
		<rating>604.0</rating>
		<title>Dlisted - Be Very Afraid</title>
		<url>http://www.dlisted.com/node/34590</url>
		<wordstring>cat</wordstring>
		<year>2009</year>
	    </example>
	    <example>
		<display>After all, if you're a rat, a cat is a dangerous predator.</display>
		<documentId>24257280</documentId>
		<exampleId>357392708</exampleId>
		<scoreId>4212152206</scoreId>
		<provider>
		    <id>707</id>
		    <name>wsj</name>
		</provider>
		<rating>604.0</rating>
		<title>NPR Smells a Rat</title>
		<url>http://online.wsj.com/article/SB10001424052970203706604574378750993506072.html</url>
		<wordstring>cat</wordstring>
		<year>2009</year>
	    </example>
	</examples>
     *
     * @param doc XML document to parse.
     * @return list of Example objects.
     * @throws KnickerException if there are any errors.
     */
    public static List<Example> buildExamples(Document doc) throws KnickerException {
	List<Example> examples = new ArrayList<Example>();

	NodeList exampleNodes = doc.getElementsByTagName("example");

	for (int i = 0; i < exampleNodes.getLength(); i++) {
	    Example example = new Example();
	    Node exampleNode = exampleNodes.item(i);

	    example.setDisplay(Util.getNamedChildTextContent(exampleNode, "display"));
	    example.setDocumentId(Util.getNamedChildTextContent(exampleNode, "documentId"));
	    example.setExampleId(Util.getNamedChildTextContent(exampleNode, "exampleId"));
	    example.setScoreId(Util.getNamedChildTextContent(exampleNode, "scoreId"));
	    example.setRating(Util.getNamedChildTextContent(exampleNode, "rating"));
	    example.setTitle(Util.getNamedChildTextContent(exampleNode, "title"));
	    example.setUrl(Util.getNamedChildTextContent(exampleNode, "url"));
	    example.setWordstring(Util.getNamedChildTextContent(exampleNode, "wordstring"));
	    example.setYear(Util.getNamedChildTextContent(exampleNode, "year"));

	    NodeList providerNodes = exampleNode.getChildNodes();
	    for (int j = 0; j < providerNodes.getLength(); j++) {
		Node node = providerNodes.item(j);
		if (node.getNodeName().equals("provider")) {
		    example.addProvider(
			    Util.getNamedChildTextContent(node, "id"),
			    Util.getNamedChildTextContent(node, "name"));
		}
	    }

	    examples.add(example);
	}


	return examples;
    }


    /**
     * Parse an XML document into a list of Related objects.
     *
     * XML looks like this:
	<related relType="form">
	    <wordstrings>
		<wordstring>abater</wordstring>
		<wordstring>abatement</wordstring>
		<wordstring>abate of</wordstring>
		<wordstring>unabated</wordstring>
		<wordstring>abated</wordstring>
		<wordstring>abatable</wordstring>
		<wordstring>abating</wordstring>
	    </wordstrings>
	</related>
     *
     * @param doc XML document to parse.
     * @return list of Related objects.
     * @throws KnickerException if there are any errors.
     */
    static List<Related> buildRelated(Document doc) throws KnickerException {
	List<Related> relateds = new ArrayList<Related>();

	NodeList relatedNodes = doc.getElementsByTagName("related");
	if (relatedNodes != null) {
	    for (int i = 0; i < relatedNodes.getLength(); i++) {
		Node node = relatedNodes.item(i);
		Related r = new Related();
		r.setRelType(Util.getAttribute(node.getAttributes(), "relType"));

		NodeList wsNodes = Util.getNamedChildNode(node, "wordstrings").getChildNodes();
		for (int j = 0; j < wsNodes.getLength(); j++) {
		    Node wsNode = wsNodes.item(j);
		    if (wsNode.getNodeName().equals("wordstring")) {
			r.addWordstring(wsNode.getTextContent());
		    }
		}
		relateds.add(r);
	    }
	}

	return relateds;
    }


    /**
     * Parse an XML document into a FrequencySummary object.
     *
     * XML looks like this:

	<frequencySummary>
	    <frequency>
		<count>21523</count>
		<year>2008</year>
	    </frequency>
	    <frequency>
		<count>41828</count>
		<year>2009</year>
	    </frequency>  ...
	    <frequencyString>-1:11966,1344:9,1485:14,1503:4, ...</frequencyString>
	    <totalCount>112461</totalCount>
	    <unknownYearCount>11966</unknownYearCount>
	    <wordId>27568</wordId>
	</frequencySummary>
     *
     * @param doc XML document to parse.
     * @return FrequencySummary object.
     * @throws KnickerException if there are any errors.
     */
    static FrequencySummary buildFrequencySummary(Document doc) throws KnickerException {
	FrequencySummary fs = new FrequencySummary();

	fs.setFrequencyString(Util.getValueByXPath(doc, "/frequencySummary/frequencyString"));
	fs.setTotalCount(Util.getValueByXPathAsInt(doc, "/frequencySummary/totalCount"));
	fs.setUnknownYearCount(Util.getValueByXPathAsInt(doc, "/frequencySummary/unknownYearCount"));
	fs.setWordId(Util.getValueByXPath(doc, "/frequencySummary/wordId"));

	NodeList fNodes = doc.getElementsByTagName("frequency");
	if (fNodes != null) {
	    for (int i = 0; i < fNodes.getLength(); i++) {
		Node node = fNodes.item(i);
		fs.addFrequency(
			Util.getNamedChildTextContentAsInt(node, "count"),
			Util.getNamedChildTextContent(node, "year"));

	    }
	}

	return fs;
    }


    /**
     * Parse XML document into a PunctuationFactor object.
     *
     * XML looks like this:
	<punctuationFactor>
	    <exclamationPointCount>1787</exclamationPointCount>
	    <periodCount>27245</periodCount>
	    <questionMarkCount>1365</questionMarkCount>
	    <totalCount>112461</totalCount>
	    <wordId>27568</wordId>
	</punctuationFactor>
     *
     * @param doc XML document to parse.
     * @return PunctuaionFactor object.
     * @throws KnickerException if there are any errors.
     */
    static PunctuationFactor buildPunctuationFactor(Document doc) throws KnickerException {
	PunctuationFactor pf = new PunctuationFactor();

	pf.setExclamationPointCount(Util.getValueByXPathAsInt(doc, "/punctuationFactor/exclamationPointCount"));
	pf.setPeriodCount(Util.getValueByXPathAsInt(doc, "/punctuationFactor/periodCount"));
	pf.setQuestionMarkCount(Util.getValueByXPathAsInt(doc, "/punctuationFactor/questionMarkCount"));
	pf.setTotalCount(Util.getValueByXPathAsInt(doc, "/punctuationFactor/totalCount"));
	pf.setWordId(Util.getValueByXPath(doc, "/punctuationFactor/wordId"));

	return pf;
    }


    /**
     * Parse an XML document into a list of Pronunciation objects.
     *
     * XML looks like this:
	<textProns>
	    <textPron seq="0">
		<id>0</id>
		<raw>(kŏm*pūt"ẽr)</raw>
		<rawType>gcide-diacritical</rawType>
	    </textPron>
	    <textPron seq="0">
		<id>0</id>
		<raw>K AH0 M P Y UW1 T ER0</raw>
		<rawType>arpabet</rawType>
	    </textPron>
	</textProns>
     *
     * @param doc XML document to parse.
     * @return list of Pronunciation objects.
     * @throws KnickerException if there are any errors.
     */
    static List<Pronunciation> buildPronunciation(Document doc) throws KnickerException {
	List<Pronunciation> list = new ArrayList<Pronunciation>();

	NodeList nodes = doc.getElementsByTagName("textPron");
	if (nodes != null) {
	    for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);
		Pronunciation pron = new Pronunciation();
		pron.setId(Util.getNamedChildTextContent(node, "id"));
		pron.setRaw(Util.getNamedChildTextContent(node, "raw"));
		pron.setRawType(Util.getNamedChildTextContent(node, "rawType"));

		list.add(pron);
	    }
	}

	return list;
    }


    /**
     * Parse an XML document into a SearchResult object.
     *
     * XML looks like this:
	<searchResult>
	    <matches>8449</matches>
	    <more>8434</more>
	    <searchTerm>
		<count>0</count>
		<wordstring>ca</wordstring>
	    </searchTerm>
	    <match>
		<count>0</count>
		<wordstring>ca</wordstring>
	    </match>  <match>
		<count>207455</count>
		<wordstring>caused</wordstring>
	    </match>
	    <match>
		<count>90414</count>
		<wordstring>causing</wordstring>
	    </match>
	</searchResult>
     *
     * @param doc XML document to parse.
     * @return SearchResult object.
     * @throws KnickerException if there are any errors.
     */
    static SearchResult buildSearchResult(Document doc) throws KnickerException {
	SearchResult result = new SearchResult();

	result.setMatches(Util.getValueByXPathAsInt(doc, "/searchResult/matches"));
	result.setMore(Util.getValueByXPathAsInt(doc, "/searchResult/more"));
	result.setSearchTermCount(Util.getValueByXPathAsInt(doc, "/searchResult/searchTerm/count"));
	result.setSearchTermWordstring(Util.getValueByXPath(doc, "/searchResult/searchTerm/wordstring"));

	NodeList matchNodes = doc.getElementsByTagName("match");
	if (matchNodes != null) {
	    for (int i = 0; i < matchNodes.getLength(); i++) {
		Node matchNode = matchNodes.item(i);
		result.addMatch(
			Util.getNamedChildTextContentAsInt(matchNode, "count"),
			Util.getNamedChildTextContent(matchNode, "wordstring"));

	    }
	}


	return result;
    }


    /**
     * Parse an XML document into a WordOfTheDay object.
     *
     * XML looks like this:
	<wotd publishDate="2010-10-12T02:00:00Z" id="318">
	    <definition>
		<text>(noun) A seller of low-priced trashy goods.</text>
	    </definition>
	    <definition>
		<text>(noun) A man who travels in search of employment: so called because he carries his swag, or bundle of clothes, blanket, etc.</text>
	    </definition>
	    <example>
		<text>You can't go about the world being nothing, but if you are a traveller you have to carry a bag, while if you are a swagman you have to carry a swag, and the question is: Which is the heavier?</text>
	    </example>
	    <example>
		<text>Banjo Paterson, too, was moved by the shearers' strike and many people see his song 'Waltzing Matilda' as taking the side of the unionist workers, the swagman, in their battle against the squatter station owners and the police troupers and soldiers drafted in to help break the strike.</text>
	    </example>
	    <example>
		<text>Every mile or so the swagman seems to stop, build a fire, and brew his draught of tea, which he makes strong enough to take the place of the firiest swig of whiskey.</text>
	    </example>
	    <note>'Swagman' is Australian in origin, and is also known as 'swaggie' and 'sundowner.'</note>
	    <wordstring>swagman</wordstring>
	</wotd>
     *
     * @param doc XML document to parse.
     * @return WordOfTheDay object.
     * @throws KnickerException if there are any errors.
     */
    static WordOfTheDay buildWordOfTheDay(Document doc) throws KnickerException {
	WordOfTheDay wotd = new WordOfTheDay();

	wotd.setId(Util.getValueByXPath(doc, "/wotd/@id"));
	wotd.setPublishDate(Util.getValueByXPath(doc, "/wotd/@publishDate"));
	wotd.setWordstring(Util.getValueByXPath(doc, "/wotd/wordstring"));

	NodeList nodes = Util.getNamedChildNode(doc, "wotd").getChildNodes();

	for (int i = 0; i < nodes.getLength(); i++) {
	    Node node = nodes.item(i);
	    if (node.getNodeName().equals("definition")) {
		wotd.addDefinition(Util.getNamedChildTextContent(node, "text"));
	    } else if (node.getNodeName().equals("example")) {
		wotd.addExample(Util.getNamedChildTextContent(node, "text"));
	    } else if (node.getNodeName().equals("note")) {
		wotd.addNote(node.getTextContent());
	    }
	}


	return wotd;
    }


    /**
     * Parse an XML document into a list of Word objects.
     *
     * XML looks like this:
	<words>
	    <word>
		<id>17968399</id>
		<wordstring>dragonarmies</wordstring>
	    </word>
	    <word>
		<id>575630</id>
		<wordstring>microcontroller</wordstring>
	    </word>
	</words>
     *
     * @param doc XML document to parse.
     * @return list of Word objects.
     * @throws KnickerException if there are any errors.
     */
    static List<Word> buildWords(Document doc) throws KnickerException {
	List<Word> words = new ArrayList<Word>();

	try {
	    NodeList nodes = Util.getNamedChildNode(doc, "words").getChildNodes();
	    for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);
		Word word = new Word();
		word.setId(Util.getNamedChildTextContent(node, "id"));
		word.setWordstring(Util.getNamedChildTextContent(node, "wordstring"));

		words.add(word);
	    }
	} catch (Exception e) {
	    throw new KnickerException("Error parsing XML.", e);
	}

	return words;
    }


    /**
     * Parse an XML document into an AuthenticationToken.
     *
     * XML looks like this:
	<authenticationToken>
     	    <token>23213de303e61272b52e05bb2dea578294acc1ed6953aa5</token>
     	    <userId>1055256</userId>
     	</authenticationToken>
     *
     * @param doc XML document to parse.
     * @return AuthenticationToken object.
     * @throws KnickerException if there are any errors.
     */
    static AuthenticationToken buildAuthenticationToken(Document doc) throws KnickerException {
	AuthenticationToken auth = new AuthenticationToken();

	auth.setToken(Util.getValueByXPath(doc, "/authenticationToken/token"));
	auth.setUserId(Util.getValueByXPath(doc, "/authenticationToken/userId"));

	return auth;
    }


    /**
     * Parse an XML document into a TokenStatus object.
     *
     * XML looks like this:
	<apiTokenStatus>
     	    <expiresInMillis>9223372036854775807</expiresInMillis>
     	    <remainingCalls>4998</remainingCalls>
     	    <resetsInMillis>3512457</resetsInMillis>
     	    <token>5458ce4933e219f23e002079e810f29b3818096f5fbbf8560</token>
     	    <totalRequests>2</totalRequests>
     	    <valid>true</valid>
     	</apiTokenStatus>
     * 
     * @param doc XML document to parse.
     * @return TokenStatus object.
     * @throws KnickerException if there are any errors.
     */
    static TokenStatus buildTokenStatus(Document doc) throws KnickerException {
	TokenStatus status = new TokenStatus();
	try {
	    status.setExpiresInMillis(Util.getValueByXPathAsLong(doc, "/apiTokenStatus/expiresInMillis"));
	    status.setRemainingCalls(Util.getValueByXPathAsInt(doc, "/apiTokenStatus/remainingCalls"));
	    status.setResetsInMillis(Util.getValueByXPathAsLong(doc, "/apiTokenStatus/resetsInMillis"));
	    status.setToken(Util.getValueByXPath(doc, "/apiTokenStatus/token"));
	    status.setTotalRequests(Util.getValueByXPathAsInt(doc, "/apiTokenStatus/totalRequests"));
	    status.setValid(Util.getValueByXPathAsBoolean(doc, "/apiTokenStatus/valid"));
	} catch (Exception e) {
	    throw new KnickerException("Error parsing token status.", e);
	}

	return status;
    }


    /**
     * Parse an XML document into a WordList object.
     *
     * XML looks like this:
	<wordList>
	    <createdAt>2010-10-14T20:46:04.264Z</createdAt>
	    <description>test list2</description>
	    <id>27354</id>
	    <name>test2</name>
	    <numberWordsInList>0</numberWordsInList>
	    <permalinkId>test2--1</permalinkId>
	    <type>PUBLIC</type>
	    <updatedAt>2010-10-14T20:46:04.264Z</updatedAt>
	    <userId>1055256</userId>
	    <userName>jeremybrooks</userName>
	</wordList>
     *
     * @param doc XML document to parse.
     * @return WordList object.
     * @throws KnickerException if there are any errors.
     */
    static WordList buildWordList(Document doc) throws KnickerException {
	WordList wordList = new WordList();

	try {
	    wordList.setCreatedAt(Util.getValueByXPath(doc, "/wordList/createdAt"));
	    wordList.setDescription(Util.getValueByXPath(doc, "/wordList/description"));
	    wordList.setId(Util.getValueByXPath(doc, "/wordList/id"));
	    wordList.setName(Util.getValueByXPath(doc, "/wordList/name"));
	    wordList.setNumberWordsInList(Util.getValueByXPathAsInt(doc, "/wordList/numberWordsInList"));
	    wordList.setPermalinkId(Util.getValueByXPath(doc, "/wordList/permalinkId"));
	    wordList.setUpdatedAt(Util.getValueByXPath(doc, "/wordList/updatedAt"));
	    wordList.setUserId(Util.getValueByXPath(doc, "/wordList/userId"));
	    wordList.setUserName(Util.getValueByXPath(doc, "/wordList/userName"));

	    String type = Util.getValueByXPath(doc, "/wordList/type");
	    if (type.equalsIgnoreCase("PUBLIC")) {
		wordList.setType(Knicker.ListType.PUBLIC);
	    } else if (type.equalsIgnoreCase("PRIVATE")) {
		wordList.setType(Knicker.ListType.PRIVATE);
	    } else {
		wordList.setType(null);
	    }
	} catch (Exception e) {
	    throw new KnickerException("Error parsing word list XML.", e);
	}

	return wordList;
    }


    /**
     * Parse an XML document into a list of WordList objects.
     *
     * XML looks like this:
	<wordLists>
     	    <wordList>
     		<createdAt>2010-10-14T20:46:04Z</createdAt>
     		<description>test list2</description>
     		<id>27354</id>
     		<name>test2</name>
     		<numberWordsInList>1</numberWordsInList>
     		<permalinkId>test2--1</permalinkId>
     		<type>PUBLIC</type>
     		<updatedAt>2010-10-14T20:46:04Z</updatedAt>
     		<userId>1055256</userId>
     		<userName>jeremybrooks</userName>
     	    </wordList>
     	    <wordList>
     		<createdAt>2010-10-14T20:44:43Z</createdAt>
     		<description>test list</description>
     		<id>27353</id>
     		<name>test</name>
     		<numberWordsInList>1</numberWordsInList>
     		<permalinkId>test--21</permalinkId>
     		<type>PUBLIC</type>
     		<updatedAt>2010-10-14T20:44:43Z</updatedAt>
     		<userId>1055256</userId>
     		<userName>jeremybrooks</userName>
     	    </wordList>
     	</wordLists>
     *
     * @param doc XML document to parse.
     * @return list of WordList objects.
     * @throws KnickerException if there are any errors.
     */
    static List<WordList> buildWordLists(Document doc) throws KnickerException {
	List<WordList> list = new ArrayList<WordList>();

	try {
	    NodeList wordLists = Util.getNamedChildNode(doc, "wordLists").getChildNodes();
	    for (int i = 0; i < wordLists.getLength(); i++) {
		Node listNode = wordLists.item(i);
		if (listNode.getNodeName().equals("wordList")) {
		    WordList wl = new WordList();
		    wl.setCreatedAt(Util.getNamedChildTextContent(listNode, "createdAt"));
		    wl.setDescription(Util.getNamedChildTextContent(listNode, "description"));
		    wl.setId(Util.getNamedChildTextContent(listNode, "id"));
		    wl.setName(Util.getNamedChildTextContent(listNode, "name"));
		    wl.setNumberWordsInList(Util.getNamedChildTextContentAsInt(listNode, "numberWordsInList"));
		    wl.setPermalinkId(Util.getNamedChildTextContent(listNode, "permalinkId"));
		    wl.setUpdatedAt(Util.getNamedChildTextContent(listNode, "updatedAt"));
		    wl.setUserId(Util.getNamedChildTextContent(listNode, "userId"));
		    wl.setUserName(Util.getNamedChildTextContent(listNode, "userName"));
		    String type = Util.getNamedChildTextContent(listNode, "type");

		    if (type.equalsIgnoreCase("PUBLIC")) {
			wl.setType(Knicker.ListType.PUBLIC);
		    } else if (type.equalsIgnoreCase("PRIVATE")) {
			wl.setType(Knicker.ListType.PRIVATE);
		    } else {
			wl.setType(null);
		    }

		    list.add(wl);
		}
	    }
	} catch (Exception e) {
	    throw new KnickerException("Error parsing word list XML.", e);
	}

	return list;
    }


    /**
     * Parse an XML document into a list of WordListWord objects.
     *
     * XML looks like this:
	<wordListWords>
	    <wordListWord>
		<createdAt>2010-10-18T22:44:56Z</createdAt>
		<numberCommentsOnWord>0</numberCommentsOnWord>
		<numberLists>2</numberLists>
		<userId>1055256</userId>
		<userName>jeremybrooks</userName>
		<wordstring>valetudinarianism</wordstring>
	    </wordListWord>
	</wordListWords>
     *
     * @param doc XML document to parse.
     * @return list of WordListWord objects.
     * @throws KnickerException if there are any errors.
     */
    static List<WordListWord> buildWordListWords(Document doc) throws KnickerException {
	List<WordListWord> list = new ArrayList<WordListWord>();

	NodeList wordNodes = Util.getNamedChildNode(doc, "wordListWords").getChildNodes();
	if (wordNodes != null) {
	    for (int i = 0; i < wordNodes.getLength(); i++) {
		Node node = wordNodes.item(i);
		if (node.getNodeName().equals("wordListWord")) {
		    WordListWord word = new WordListWord();
		    word.setCreatedAt(Util.getNamedChildTextContent(node, "createdAt"));
		    word.setNumberCommentsOnWord(Util.getNamedChildTextContentAsInt(node, "numberCommentsOnWord"));
		    word.setNumberLists(Util.getNamedChildTextContentAsInt(node, "numberLists"));
		    word.setUserId(Util.getNamedChildTextContent(node, "userId"));
		    word.setUserName(Util.getNamedChildTextContent(node, "userName"));
		    word.setWordstring(Util.getNamedChildTextContent(node, "wordstring"));

		    list.add(word);
		}
	    }
	}

	return list;
    }

    
    /**
     * Parse an XML document into a list of WordFrequency objects.
     *
     * <p>This document is returned by the word search API.</p>
     *
     * <p>The XML looks like this:</p>
     *
     <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     <wordFrequencies>
	<wordFrequency>
	    <count>85240</count>
	    <wordstring>coup</wordstring>
	</wordFrequency>
	<wordFrequency>
	    <count>10570</count>
	    <wordstring>courtship</wordstring>
	</wordFrequency>
	<wordFrequency>
	    <count>6802</count>
	    <wordstring>coop</wordstring>
	</wordFrequency>
     </wordFrequencies>
     *
     * @param doc the XML document to parse.
     * @return list of WordFrequency objects.
     * @throws KnickerException if there are any errors.
     */
    static List<WordFrequency> buildWordFrequency(Document doc) throws KnickerException {
	List<WordFrequency> list = new ArrayList<WordFrequency>();

	NodeList wordNodes = Util.getNamedChildNode(doc, "wordFrequencies").getChildNodes();
	if (wordNodes != null) {
	    for (int i = 0; i < wordNodes.getLength(); i++) {
		Node node = wordNodes.item(i);
		if (node.getNodeName().equals("wordFrequency")) {
		    WordFrequency wf = new WordFrequency();
		    wf.setCount(Util.getNamedChildTextContentAsInt(node, "count"));
		    wf.setWordstring(Util.getNamedChildTextContent(node, "wordstring"));

		    list.add(wf);
		}
	    }
	}

	return list;
    }
}
