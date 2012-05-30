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


import net.jeremybrooks.knicker.dto.AudioFileMetadata;
import net.jeremybrooks.knicker.dto.AuthenticationToken;
import net.jeremybrooks.knicker.dto.ContentProvider;
import net.jeremybrooks.knicker.dto.Definition;
import net.jeremybrooks.knicker.dto.DefinitionSearchResult;
import net.jeremybrooks.knicker.dto.DefinitionSearchResults;
import net.jeremybrooks.knicker.dto.Example;
import net.jeremybrooks.knicker.dto.FrequencySummary;
import net.jeremybrooks.knicker.dto.Phrase;
import net.jeremybrooks.knicker.dto.Pronunciation;
import net.jeremybrooks.knicker.dto.Provider;
import net.jeremybrooks.knicker.dto.Related;
import net.jeremybrooks.knicker.dto.SearchResult;
import net.jeremybrooks.knicker.dto.SearchResults;
import net.jeremybrooks.knicker.dto.Syllable;
import net.jeremybrooks.knicker.dto.TokenStatus;
import net.jeremybrooks.knicker.dto.User;
import net.jeremybrooks.knicker.dto.Word;
import net.jeremybrooks.knicker.dto.WordList;
import net.jeremybrooks.knicker.dto.WordListWord;
import net.jeremybrooks.knicker.dto.WordOfTheDay;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jeremy Brooks (jeremyb@whirljack.net)
 * @author Boris Goldowski (patch for attribution text support)
 */
class DTOBuilder {

	/**
	 * Parse an XML document into an AuthenticationToken.
	 * <p/>
	 * XML looks like this:
	 * <authenticationToken>
	 * <token>23213de303e61272b52e05bb2dea578294acc1ed6953aa5</token>
	 * <userId>1055256</userId>
	 * </authenticationToken>
	 *
	 * @param doc XML document to parse.
	 * @return AuthenticationToken object.
	 * @throws KnickerException if there are any errors.
	 */
	static AuthenticationToken buildAuthenticationToken(Document doc) throws KnickerException {
		AuthenticationToken auth = new AuthenticationToken();

		try {
			auth.setToken(Util.getValueByXPath(doc, "/authenticationToken/token"));
			auth.setUserId(Util.getValueByXPath(doc, "/authenticationToken/userId"));
		} catch (Exception e) {
			throw buildKnickerException("buildAuthenticationToken", doc, e);
		}

		return auth;
	}


	/**
	 * Parse an XML document into a TokenStatus object.
	 * <p/>
	 * XML looks like this:
	 * <apiTokenStatus>
	 * <expiresInMillis>9223372036854775807</expiresInMillis>
	 * <remainingCalls>4998</remainingCalls>
	 * <resetsInMillis>3512457</resetsInMillis>
	 * <token>5458ce4933e219f23e002079e810f29b3818096f5fbbf8560</token>
	 * <totalRequests>2</totalRequests>
	 * <valid>true</valid>
	 * </apiTokenStatus>
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
			throw buildKnickerException("buildTokenStatus", doc, e);
		}

		return status;
	}


	/**
	 * Parse the user information from the XML document and return it as
	 * an instance of the User class.
	 * <p/>
	 * The returned XML looks like this:
	 * <user>
	 * <email>jeremyb@whirljack.net</email>
	 * <id>1055256</id>
	 * <status>0</status>
	 * <userName>jeremybrooks</userName>
	 * </user>
	 *
	 * @param doc the XML document to parse.
	 * @return user object based on the XML document.
	 * @throws KnickerException if there are any errors.
	 */
	static User buildUser(Document doc) throws KnickerException {
		User user = new User();

		try {
			user.setEmail(Util.getValueByXPath(doc, "/user/email"));
			user.setId(Util.getValueByXPath(doc, "/user/id"));
			user.setStatus(Util.getValueByXPath(doc, "/user/status"));
			user.setUserName(Util.getValueByXPath(doc, "/user/userName"));
		} catch (Exception e) {
			throw buildKnickerException("buildUser", doc, e);
		}

		return user;
	}


	/**
	 * Parse an XML document into a list of WordList objects.
	 * <p/>
	 * XML looks like this:
	 * <wordLists>
	 * <wordList>
	 * <createdAt>2010-10-14T20:46:04Z</createdAt>
	 * <description>test list2</description>
	 * <id>27354</id>
	 * <name>test2</name>
	 * <numberWordsInList>1</numberWordsInList>
	 * <permalink>test2--1</permalink>
	 * <type>PUBLIC</type>
	 * <updatedAt>2010-10-14T20:46:04Z</updatedAt>
	 * <userId>1055256</userId>
	 * <userName>jeremybrooks</userName>
	 * </wordList>
	 * <wordList>
	 * <createdAt>2010-10-14T20:44:43Z</createdAt>
	 * <description>test list</description>
	 * <id>27353</id>
	 * <name>test</name>
	 * <numberWordsInList>1</numberWordsInList>
	 * <permalink>test--21</permalink>
	 * <type>PUBLIC</type>
	 * <updatedAt>2010-10-14T20:44:43Z</updatedAt>
	 * <userId>1055256</userId>
	 * <userName>jeremybrooks</userName>
	 * </wordList>
	 * </wordLists>
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
					wl.setPermalink(Util.getNamedChildTextContent(listNode, "permalink"));
					wl.setUpdatedAt(Util.getNamedChildTextContent(listNode, "updatedAt"));
					wl.setUserId(Util.getNamedChildTextContent(listNode, "userId"));
					wl.setUsername(Util.getNamedChildTextContent(listNode, "username"));
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
			throw buildKnickerException("buildWordLists", doc, e);
		}

		return list;
	}


	/**
	 * Parse XML document into a Word object.
	 * <p/>
	 * XML looks like this:
	 * <wordObject>
	 * <word>cat</word>
	 * <canonicalForm>cat</canonicalForm>
	 * <originalWord>cat</originalWord>
	 * <suggestions>
	 * <suggestion>zebra</suggestion>
	 * </suggestions>
	 * </wordObject>
	 * <p/>
	 * This method expects API v4 data.
	 *
	 * @param doc XML document to parse.
	 * @return Word object.
	 * @throws KnickerException if there are any errors.
	 */
	static Word buildWord(Document doc) throws KnickerException {
		Word w = new Word();

		try {
			w.setWord(Util.getValueByXPath(doc, "/wordObject/word"));
			w.setCanonicalForm(Util.getValueByXPath(doc, "/wordObject/canonicalForm"));
			w.setOriginalWord(Util.getValueByXPath(doc, "/wordObject/originalWord"));
			NodeList sugs = doc.getElementsByTagName("suggestions");
			for (int i = 0; i < sugs.getLength(); i++) {
				Node sug = sugs.item(i);
				w.addSuggestion(Util.getNamedChildTextContent(sug, "suggestion"));
			}

		} catch (Exception e) {
			throw buildKnickerException("buildWord", doc, e);
		}

		return w;
	}


	/**
	 * Parse XML document into a list of Definition objects.
	 * <p/>
	 * XML looks like this:
	 * <definitions>
	 * <definition sequence="0">
	 * <text>A procedure for critical evaluation; a means of determining the presence, quality, or truth of something; a trial:  a test of one's eyesight; subjecting a hypothesis to a test; a test of an athlete's endurance. </text>
	 * <partOfSpeech>noun</partOfSpeech>
	 * <score>0.0</score>
	 * <sourceDictionary>ahd-legacy</sourceDictionary>
	 * <word>test</word>
	 * </definition>
	 * <definition sequence="1">
	 * <text>A series of questions, problems, or physical responses designed to determine knowledge, intelligence, or ability.</text>
	 * <partOfSpeech>noun</partOfSpeech>
	 * <score>0.0</score>
	 * <sourceDictionary>ahd-legacy</sourceDictionary>
	 * <word>test</word>
	 * </definition>
	 * </definitions>
	 *
	 * @param doc the XML document to parse.
	 * @return list of Definition objects.
	 * @throws KnickerException if there are any errors.
	 */
	static List<Definition> buildDefinitions(Document doc) throws KnickerException {
		List<Definition> definitions = new ArrayList<Definition>();

		try {
			NodeList defNodes = doc.getElementsByTagName("definition");
			for (int i = 0; i < defNodes.getLength(); i++) {
				Definition definition = new Definition();

				Node defNode = defNodes.item(i);
				NamedNodeMap nnm = defNode.getAttributes();
				definition.setSequence(Util.getAttributeAsInt(nnm, "sequence"));
				definition.setText(Util.getNamedChildTextContent(defNode, "text"));
				definition.setPartOfSpeech(Util.getNamedChildTextContent(defNode, "partOfSpeech"));
				definition.setScore(Util.getNamedChildTextContent(defNode, "score"));
				definition.setSourceDictionary(Util.getNamedChildTextContent(defNode, "sourceDictionary"));
				definition.setWord(Util.getNamedChildTextContent(defNode, "word"));
				definition.setAttributionText(Util.getNamedChildTextContent(defNode, "attributionText"));

				definitions.add(definition);
			}
		} catch (Exception e) {
			throw buildKnickerException("buildDefinitions", doc, e);
		}


		return definitions;
	}


	/**
	 * Parse an XML document into a FrequencySummary object.
	 * <p/>
	 * XML looks like this:
	 * <p/>
	 * <frequencySummary>
	 * <frequency>
	 * <count>391</count>
	 * <year>1987</year>
	 * </frequency>
	 * <frequency>
	 * <count>258</count>
	 * <year>2003</year>
	 * </frequency>
	 * <frequency>
	 * <count>288</count>
	 * <year>2004</year>
	 * </frequency>
	 * <totalCount>2721</totalCount>
	 * <unknownYearCount>0</unknownYearCount>
	 * <word>cat</word>
	 * </frequencySummary>
	 *
	 * @param doc XML document to parse.
	 * @return FrequencySummary object.
	 * @throws KnickerException if there are any errors.
	 */
	static FrequencySummary buildFrequencySummary(Document doc) throws KnickerException {
		FrequencySummary fs = new FrequencySummary();

		try {
			NodeList fNodes = doc.getElementsByTagName("frequency");
			if (fNodes != null) {
				for (int i = 0; i < fNodes.getLength(); i++) {
					Node node = fNodes.item(i);
					fs.addFrequency(
							Util.getNamedChildTextContentAsInt(node, "count"),
							Util.getNamedChildTextContent(node, "year"));

				}
			}

			fs.setTotalCount(Util.getValueByXPathAsInt(doc, "/frequencySummary/totalCount"));
			fs.setUnknownYearCount(Util.getValueByXPathAsInt(doc, "/frequencySummary/unknownYearCount"));
			fs.setWord(Util.getValueByXPath(doc, "/frequencySummary/word"));
		} catch (Exception e) {
			throw buildKnickerException("buildFrequencySummary", doc, e);
		}

		return fs;
	}


	/**
	 * Parse an XML document into a list of Related objects.
	 * <p/>
	 * XML looks like this:
	 * <relateds>
	 * <related relationshipType="hyponym">
	 * <words>
	 * <word>jaguar</word>
	 * <word>wild-cat</word>
	 * <word>panthera leo</word>
	 * <word>tiger</word>
	 * <word>panthera tigris</word>
	 * <word>acinonyx jubatus</word>
	 * <word>panthera onca</word>
	 * <word>felis domesticus</word>
	 * <word>tigon</word>
	 * <word>king of beasts</word>
	 * </words>
	 * </related>
	 * </relateds>
	 *
	 * @param doc XML document to parse.
	 * @return list of Related objects.
	 * @throws KnickerException if there are any errors.
	 */
	static List<Related> buildRelated(Document doc) throws KnickerException {
		List<Related> relateds = new ArrayList<Related>();

		try {
			NodeList relatedNodes = doc.getElementsByTagName("related");
			if (relatedNodes != null) {
				for (int i = 0; i < relatedNodes.getLength(); i++) {
					Node node = relatedNodes.item(i);
					Related r = new Related();
					r.setRelationshipType(Util.getAttribute(node.getAttributes(), "relationshipType"));

					NodeList wsNodes = Util.getNamedChildNode(node, "words").getChildNodes();
					for (int j = 0; j < wsNodes.getLength(); j++) {
						Node wsNode = wsNodes.item(j);
						if (wsNode.getNodeName().equals("word")) {
							r.addWord(wsNode.getTextContent());
						}
					}
					relateds.add(r);
				}
			}
		} catch (Exception e) {
			throw buildKnickerException("buildRelated", doc, e);
		}

		return relateds;
	}


	/**
	 * Parse XML document into a list of Phrase objects.
	 * <p/>
	 * XML looks like this:
	 * <bigrams>
	 * <bigram>
	 * <count>353</count>
	 * <mi>12.414364902158674</mi>
	 * <wlmi> 20.877889275429855</wlmi>
	 * <gram1>Christmas</gram1>
	 * <gram2>Eve</gram2>
	 * </bigram>
	 * <bigram>
	 * <count>268</count>
	 * <mi>10.707678907779517</mi>
	 * <wlmi>18.77376809823729</wlmi>
	 * <gram1>Christmas</gram1>
	 * <gram2>tree</gram2>
	 * </bigram>
	 * <bigrams>
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
			throw buildKnickerException("buildPhrase", doc, e);
		}

		return phrases;
	}


	/**
	 * Create a list of Syllable objects from an XML document. The XML looks
	 * like this:
	 * <p/>
	 * <syllables>
	 * <syllable type="stress" seq="0">
	 * <text>hy</text>
	 * </syllable>
	 * <syllable seq="1">
	 * <text>phen</text>
	 * </syllable>
	 * </syllables>
	 *
	 * @param doc the XML document to parse.
	 * @return list of syllable objects.
	 * @throws KnickerException if there are any errors.
	 */
	static List<Syllable> buildHyphenation(Document doc) throws
			KnickerException {
		List<Syllable> syllables = new ArrayList<Syllable>();

		try {
			NodeList nodes = doc.getElementsByTagName("syllable");
			if (nodes != null) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Syllable s = new Syllable();
					Node node = nodes.item(i);
					s.setType(Util.getAttribute(node.getAttributes(), "type"));
					s.setSeq(Util.getAttributeAsInt(node.getAttributes(), "seq"));
					s.setText(Util.getNamedChildTextContent(node, "text"));

					syllables.add(s);
				}
			}
		} catch (Exception e) {
			throw buildKnickerException("buildHyphenation", doc, e);
		}

		return syllables;
	}


	/**
	 * Parse an XML document into a list of Pronunciation objects.
	 * <p/>
	 * XML looks like this:
	 * <textProns>
	 * <textPron seq="0">
	 * <id>0</id>
	 * <raw>(kŏm*pūt"ẽr)</raw>
	 * <rawType>gcide-diacritical</rawType>
	 * </textPron>
	 * <textPron seq="0">
	 * <id>0</id>
	 * <raw>K AH0 M P Y UW1 T ER0</raw>
	 * <rawType>arpabet</rawType>
	 * </textPron>
	 * </textProns>
	 *
	 * @param doc XML document to parse.
	 * @return list of Pronunciation objects.
	 * @throws KnickerException if there are any errors.
	 */
	static List<Pronunciation> buildPronunciation(Document doc) throws KnickerException {
		List<Pronunciation> list = new ArrayList<Pronunciation>();

		try {
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
		} catch (Exception e) {
			throw buildKnickerException("buildPronunciation", doc, e);
		}
	}


	/**
	 * Build a list of audio file metadata objects from an XML document.
	 * <p/>
	 * The XML looks like this:
	 * <p/>
	 * <audioFileMetadatas>
	 * <audioFile>
	 * <commentCount>0</commentCount>
	 * <createdAt>2009-03-15T15:31:45Z</createdAt>
	 * <createdBy>ahd</createdBy>
	 * <fileUrl>http://api.wordnik.com/v4/audioFile.mp3/75846cf897b18e9b7c44cbfe6b6b8ff2e04de9f23413ea27be3323860b01a41f</fileUrl>
	 * <id>1</id>
	 * <word>a</word>
	 * </audioFile>
	 * <audioFile>
	 * <commentCount>0</commentCount>
	 * <createdAt>2009-03-15T15:31:46Z</createdAt>
	 * <createdBy>ahd</createdBy>
	 * <fileUrl>http://api.wordnik.com/v4/audioFile.mp3/2bffa1199cc1f3c29a8141c77af6644b0974a2b16040539102cdd02a7a9d3d9e</fileUrl>
	 * <id>2</id>
	 * <word>a</word>
	 * </audioFile>
	 * </audioFileMetadatas>
	 *
	 * @param doc the document to parse.
	 * @return list of audio file metadata objects.
	 * @throws KnickerException if there are any errors.
	 */
	static List<AudioFileMetadata> buildAudio(Document doc) throws
			KnickerException {
		List<AudioFileMetadata> list = new ArrayList<AudioFileMetadata>();

		try {
			NodeList nodes = doc.getElementsByTagName("audioFile");
			if (nodes != null) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					AudioFileMetadata meta = new AudioFileMetadata();
					meta.setCommentCount(Util.getNamedChildTextContentAsInt(node, "commentCount"));
					meta.setCreatedAt(Util.getNamedChildTextContent(node, "createdAt"));
					meta.setCreatedBy(Util.getNamedChildTextContent(node, "createdBy"));
					meta.setFileUrl(Util.getNamedChildTextContent(node, "fileUrl"));
					meta.setId(Util.getNamedChildTextContent(node, "id"));
					meta.setWord(Util.getNamedChildTextContent(node, "word"));

					list.add(meta);
				}
			}
		} catch (Exception e) {
			throw buildKnickerException("buildAudio", doc, e);
		}

		return list;
	}


	/**
	 * Parse an XML document into a list of Word objects.
	 * <p/>
	 * XML looks like this:
	 * <words>
	 * <wordObject>
	 * <word>resisting</word>
	 * </wordObject>
	 * <wordObject>
	 * <word>boyz</word>
	 * </wordObject>
	 * </words>
	 *
	 * @param doc XML document to parse.
	 * @return list of Word objects.
	 * @throws KnickerException if there are any errors.
	 */
	static List<Word> buildWords(Document doc) throws KnickerException {
		List<Word> words = new ArrayList<Word>();

		try {
			NodeList nodes = doc.getElementsByTagName("wordObject");
			if (nodes != null) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					Word word = new Word();
					word.setWord(Util.getNamedChildTextContent(node, "word"));
					words.add(word);
				}
			}
		} catch (Exception e) {
			throw buildKnickerException("buildWords", doc, e);
		}

		return words;
	}


	/**
	 * Parse an XML document into a WordOfTheDay object.
	 * <p/>
	 * XML looks like this:
	 * <WordOfTheDay>
	 * <contentProvider>
	 * <id>711</id>
	 * <name>wordnik</name>
	 * </contentProvider>
	 * <definitions>
	 * <definition>
	 * <source>wiktionary</source>
	 * <text>(noun) A despicable person.</text>
	 * </definition>
	 * </definitions>
	 * <examples>
	 * <example>
	 * <text>To paraphrase Oscar Wilde: I don't know if there is anything particularly stupefying in the air in this particular part of Samoa, but it seems to me that the number of raving ratbag idiots there considerably exceeds the usual number that statistics have laid down for our guidance.</text>
	 * <id>457001316</id>
	 * <title>Tallulah Morehead:   Survivor: Heroes vs Villains : Banana Wars.</title>
	 * <url>http://www.huffingtonpost.com/tallulah-morehead/isurvivor-heroes-vs-villa_b_549251.html</url>
	 * </example>
	 * <example>
	 * <text>The ratbag made a last-ditch attempt to salvage the situation telling the Herald Sun: 'I don't want to lose my livelihood over a stupid prank.'</text>
	 * <id>756920552</id>
	 * <title>Lechia Gdansk harness spirit of Solidarity to fuel Poland's boom</title>
	 * <url>http://www.guardian.co.uk/sport/2010/nov/07/lechia-gdansk-solidarity-poland</url>
	 * </example>
	 * <example>
	 * <text>Like so many young people these days, she has no trouble conversing with a horrible old ratbag such as me, and if she feels at all awkward, she conceals it.</text>
	 * <id>565010086</id>
	 * <title>New Statesman</title>
	 * <url>http://www.newstatesman.com/lifestyle/2010/08/hovel-razors-friend-nice</url>
	 * </example>
	 * </examples>
	 * <id>58320</id>
	 * <note>'Ratbag' may be Australian in origin.</note>
	 * <publishDate>2011-03-08T03:00:00Z</publishDate>
	 * <word>ratbag</word>
	 * </WordOfTheDay>
	 *
	 * @param doc XML document to parse.
	 * @return WordOfTheDay object.
	 * @throws KnickerException if there are any errors.
	 */
	static WordOfTheDay buildWordOfTheDay(Document doc) throws KnickerException {
		WordOfTheDay wotd = new WordOfTheDay();

		try {
			wotd.setId(Util.getValueByXPath(doc, "/WordOfTheDay/id"));
			wotd.setPublishDate(Util.getValueByXPath(doc, "/WordOfTheDay/publishDate"));
			wotd.setWord(Util.getValueByXPath(doc, "/WordOfTheDay/word"));
			wotd.setNote(Util.getValueByXPath(doc, "/WordOfTheDay/note"));

			NodeList nodes = doc.getElementsByTagName("contentProvider");
			if (nodes != null) {
				Node node = nodes.item(0);
				ContentProvider cp = new ContentProvider();
				cp.setId(Util.getNamedChildTextContent(node, "id"));
				cp.setName(Util.getNamedChildTextContent(node, "name"));

				wotd.setContentProvider(cp);
			}


			nodes = doc.getElementsByTagName("definitions");
			if (nodes != null && nodes.getLength() > 0) {
				NodeList defNodes = nodes.item(0).getChildNodes();
				if (defNodes != null) {
					for (int i = 0; i < defNodes.getLength(); i++) {
						Node node = defNodes.item(i);
						if (node.getNodeName().equals("definition")) {

							Definition d = new Definition();
							d.setSourceDictionary(Util.getNamedChildTextContent(node, "source"));
							d.setText(Util.getNamedChildTextContent(node, "text"));
							wotd.addDefinition(d);
						}
					}
				}
			}

			nodes = doc.getElementsByTagName("examples");
			if (nodes != null && nodes.getLength() > 0) {
				NodeList exNodes = nodes.item(0).getChildNodes();
				if (exNodes != null) {
					for (int i = 0; i < exNodes.getLength(); i++) {
						Node node = exNodes.item(i);
						if (node.getNodeName().equals("example")) {
							Example e = new Example();
							e.setExampleId(Util.getNamedChildTextContent(node, "id"));
							e.setText(Util.getNamedChildTextContent(node, "text"));
							e.setTitle(Util.getNamedChildTextContent(node, "title"));
							e.setUrl(Util.getNamedChildTextContent(node, "url"));
							wotd.addExample(e);
						}
					}
				}
			}
		} catch (Exception e) {
			throw buildKnickerException("buildWordOfTheDay", doc, e);
		}


		return wotd;
	}


	/**
	 * Build a list of SearchResult objects from an XML document.
	 * <p/>
	 * The XML looks like this:
	 * <p/>
	 * <wordSearchResults>
	 * <searchResults>
	 * <searchResult>
	 * <count>747905</count>
	 * <lexicality>0.0</lexicality>
	 * <word>computer</word>
	 * </searchResult>
	 * <searchResult>
	 * <count>232635</count>
	 * <lexicality>0.0</lexicality>
	 * <word>computers</word>
	 * </searchResult>
	 * </searchResults>
	 * <totalResults>2</totalResults>
	 * </wordSearchResults>
	 *
	 * @param doc the xml document to parse.
	 * @return search results object.
	 * @throws KnickerException if there are any errors.
	 */
	static SearchResults buildSearchResults(Document doc) throws KnickerException {
		SearchResults sr = new SearchResults();

		try {
			sr.setTotal(Util.getValueByXPathAsInt(doc, "/wordSearchResults/totalResults"));

			NodeList nl = doc.getElementsByTagName("searchResults");
			if (nl != null && nl.getLength() > 0) {
				NodeList resultNodes = nl.item(0).getChildNodes();
				for (int i = 0; i < resultNodes.getLength(); i++) {
					Node n = resultNodes.item(i);
					if (n.getNodeName().equals("searchResult")) {
						SearchResult s = new SearchResult();
						s.setCount(Util.getNamedChildTextContentAsInt(n, "count"));
						s.setLexicality(Util.getNamedChildTextContent(n, "lexicality"));
						s.setWord(Util.getNamedChildTextContent(n, "word"));
						sr.addSearchResult(s);
					}
				}
			}
		} catch (Exception e) {
			throw buildKnickerException("buildSearchResults", doc, e);
		}

		return sr;
	}


	/**
	 * Parse an XML document into a WordList object.
	 * <p/>
	 * XML looks like this:
	 * <wordList>
	 * <createdAt>2011-03-23T20:51:03.753Z</createdAt>
	 * <description>This is a test list created by the Knicker automated tests.</description>
	 * <id>29774</id>
	 * <name>TEST_LIST</name>
	 * <numberWordsInList>0</numberWordsInList>
	 * <permalink>test-list--2</permalink>
	 * <type>PUBLIC</type>
	 * <updatedAt>2011-03-23T20:51:03.753Z</updatedAt>
	 * <userId>1055256</userId>
	 * <username>jeremybrooks</username>
	 * </wordList>
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
			wordList.setPermalink(Util.getValueByXPath(doc, "/wordList/permalink"));
			wordList.setUpdatedAt(Util.getValueByXPath(doc, "/wordList/updatedAt"));
			wordList.setUserId(Util.getValueByXPath(doc, "/wordList/userId"));
			wordList.setUsername(Util.getValueByXPath(doc, "/wordList/username"));

			String type = Util.getValueByXPath(doc, "/wordList/type");
			if (type.equalsIgnoreCase("PUBLIC")) {
				wordList.setType(Knicker.ListType.PUBLIC);
			} else if (type.equalsIgnoreCase("PRIVATE")) {
				wordList.setType(Knicker.ListType.PRIVATE);
			} else {
				wordList.setType(null);
			}
		} catch (Exception e) {
			throw buildKnickerException("buildWordList", doc, e);
		}

		return wordList;
	}


	/**
	 * Parse an XML document into a list of WordListWord objects.
	 * <p/>
	 * XML looks like this:
	 * <wordListWords>
	 * <wordListWord>
	 * <createdAt>2011-03-23T22:09:27Z</createdAt>
	 * <numberCommentsOnWord>1</numberCommentsOnWord>
	 * <numberLists>12</numberLists>
	 * <userId>1055256</userId>
	 * <username>jeremybrooks</username>
	 * <word>zebra</word>
	 * </wordListWord>
	 * <wordListWord>
	 * <createdAt>2011-03-23T22:09:27Z</createdAt>
	 * <numberCommentsOnWord>6</numberCommentsOnWord>
	 * <numberLists>33</numberLists>
	 * <userId>1055256</userId>
	 * <username>jeremybrooks</username>
	 * <word>computer</word>
	 * </wordListWord>
	 * </wordListWords>
	 *
	 * @param doc XML document to parse.
	 * @return list of WordListWord objects.
	 * @throws KnickerException if there are any errors.
	 */
	static List<WordListWord> buildWordListWords(Document doc) throws KnickerException {
		List<WordListWord> list = new ArrayList<WordListWord>();

		try {
			Node wordNode = Util.getNamedChildNode(doc, "listedWords");
			if (wordNode != null) {
				NodeList wordNodes = wordNode.getChildNodes();
				if (wordNodes != null) {
					for (int i = 0; i < wordNodes.getLength(); i++) {
						Node node = wordNodes.item(i);
						if (node.getNodeName().equals("wordListWord")) {
							WordListWord word = new WordListWord();
							word.setCreatedAt(Util.getNamedChildTextContent(node, "createdAt"));
							word.setNumberCommentsOnWord(Util.getNamedChildTextContentAsInt(node, "numberCommentsOnWord"));
							word.setNumberLists(Util.getNamedChildTextContentAsInt(node, "numberLists"));
							word.setUserId(Util.getNamedChildTextContent(node, "userId"));
							word.setUsername(Util.getNamedChildTextContent(node, "username"));
							word.setWord(Util.getNamedChildTextContent(node, "word"));

							list.add(word);
						}
					}
				}
			}
		} catch (Exception e) {
			throw buildKnickerException("buildWordListWords", doc, e);
		}

		return list;
	}


	/**
	 * Parse an XML document into a list of Example objects.
	 * <p/>
	 * XML looks like this:
	 * <exampleSearchResults>
	 * <examples>
	 * <example>
	 * <text>I&#65533;&#65533;&#65533;m arguing that a test whose answer is used to justify an action which causes the failure of the test, is invalid *as a test*.</text>
	 * <documentId>32278280</documentId>
	 * <exampleId>967836135</exampleId>
	 * <provider>
	 * <id>711</id>
	 * <name>wordnik</name>
	 * </provider>
	 * <rating>4488.0</rating>
	 * <title>The argument that changed me from pro-life to pro-choice</title>
	 * <url>http://www.amptoons.com/blog/archives/2004/07/22/the-argument-that-changed-me-from-pro-life-to-pro-choice/</url>
	 * <word>test</word>
	 * <year>2004</year>
	 * </example>
	 * <example>
	 * <text>Try not to use the word test when describing what will happen.</text>
	 * <documentId>32437012</documentId>
	 * <exampleId>974762101</exampleId>
	 * <provider>
	 * <id>722</id>
	 * <name>simonschuster</name>
	 * </provider>
	 * <rating>762.0</rating>
	 * <title>Testing for Kindergarten</title>
	 * <url>http://books.simonandschuster.com/9781416596769</url>
	 * <word>test</word>
	 * <year>2010</year>
	 * </example>
	 * </examples>
	 * </exampleSearchResults>
	 *
	 * @param doc XML document to parse.
	 * @return search results with the examples list populated.
	 * @throws KnickerException if there are any errors.
	 */
	static SearchResults buildExamples(Document doc) throws KnickerException {
		SearchResults results = new SearchResults();

		try {
			NodeList nl = doc.getElementsByTagName("examples");
			if (nl != null && nl.getLength() > 0) {
				NodeList resultNodes = nl.item(0).getChildNodes();
				for (int i = 0; i < resultNodes.getLength(); i++) {
					Node n = resultNodes.item(i);
					if (n.getNodeName().equals("example")) {
						Example e = new Example();
						e.setText(Util.getNamedChildTextContent(n, "text"));
						e.setDocumentId(Util.getNamedChildTextContent(n, "documentId"));
						e.setExampleId(Util.getNamedChildTextContent(n, "exampleId"));
						e.setRating(Util.getNamedChildTextContent(n, "rating"));
						e.setTitle(Util.getNamedChildTextContent(n, "title"));
						e.setUrl(Util.getNamedChildTextContent(n, "url"));
						e.setWord(Util.getNamedChildTextContent(n, "word"));
						e.setYear(Util.getNamedChildTextContent(n, "year"));

						Node pNode = Util.getNamedChildNode(n, "provider");
						Provider p = new Provider();
						p.setId(Util.getNamedChildTextContent(pNode, "id"));
						p.setName(Util.getNamedChildTextContent(pNode, "name"));
						e.setProvider(p);

						results.addExample(e);
					}
				}
			}
		} catch (Exception e) {
			throw buildKnickerException("buildExamples", doc, e);
		}

		return results;
	}


	/**
	 * Build an Example object based on an XML document.
	 * <p/>
	 * The XML will look like this:
	 * <example>
	 * <text>The full cast includes the singing voices of Ms. Harrow (as the title cat), Grady Tate (as the Artist), Anton Krukowski and Daryl Sherman, with Kameron Steele as the narrator.</text>
	 * <documentId>31779559</documentId>
	 * <exampleId>869020710</exampleId>
	 * <provider>
	 * <id>711</id>
	 * <name>wordnik</name>
	 * </provider>
	 * <rating>760.8301</rating>
	 * <title>The Cat Who Went To Heaven At HSA &amp;laquo;</title>
	 * <url>http://harlemworldblog.wordpress.com/2009/04/28/the-cat-who-went-to-heaven-at-hsa/</url>
	 * <word>cat</word>
	 * <year>2009</year>
	 * </example>
	 *
	 * @param doc xml document to parse.
	 * @return example object populated with values from the xml.
	 * @throws KnickerException if there are any errors.
	 */
	static Example buildTopExample(Document doc) throws KnickerException {
		Example example = new Example();
		try {
			example.setText(Util.getValueByXPath(doc, "/example/text"));
			example.setDocumentId(Util.getValueByXPath(doc, "/example/documentId"));
			example.setExampleId(Util.getValueByXPath(doc, "/example/exampleId"));
			example.setRating(Util.getValueByXPath(doc, "/example/rating"));
			example.setTitle(Util.getValueByXPath(doc, "/example/title"));
			example.setUrl(Util.getValueByXPath(doc, "/example/url"));
			example.setWord(Util.getValueByXPath(doc, "/example/word"));
			example.setYear(Util.getValueByXPath(doc, "/example/year"));

			Node pNode = Util.getNamedChildNode(doc, "provider");
			Provider p = new Provider();
			p.setId(Util.getNamedChildTextContent(pNode, "id"));
			p.setName(Util.getNamedChildTextContent(pNode, "name"));
			example.setProvider(p);
		} catch (Exception e) {
			throw buildKnickerException("buildTopExample", doc, e);
		}

		return example;
	}


	/**
	 * Build a DefinitionSearchResults object from the XML document.
	 * This is the return type for the "reverse search" call. The XML will look like this:
	 * <p/>
	 * <definitionSearchResults>
	 * <totalResults>1260</totalResults>
	 * <results>
	 * <result sequence="0">
	 * <textProns/>
	 * <sourceDictionary>wordnet</sourceDictionary>
	 * <exampleUses/>
	 * <relatedWords>
	 * <relWord relationshipType="hypernym">
	 * <words>
	 * <word>mental testing</word>
	 * <word>test</word>
	 * <word>psychometric test</word>
	 * <word>mental test</word>
	 * </words>
	 * </relWord>
	 * <relWord relationshipType="hyponym">
	 * <words>
	 * <word>binet-simon scale</word>
	 * <word>Stanford-Binet test</word>
	 * </words>
	 * </relWord>
	 * </relatedWords>
	 * <labels/>
	 * <citations/>
	 * <word>iq test</word>
	 * <attributionText>from WordNet 3.0 Copyright 2006 by Princeton University. All rights reserved.
	 * </attributionText>
	 * <text>a psychometric test of intelligence</text>
	 * <partOfSpeech>noun</partOfSpeech>
	 * <score>5.4835267</score>
	 * </result>
	 * </results>
	 * </definitionSearchResults>
	 *
	 * @param doc xml response
	 * @return object representing the results of the reverse word search
	 * @throws KnickerException if there are any errors parsing the document
	 */
	static DefinitionSearchResults buildDefinitionSearchResults(Document doc) throws KnickerException {
		DefinitionSearchResults results = new DefinitionSearchResults();
		List<DefinitionSearchResult> resultList = new ArrayList<DefinitionSearchResult>();
		try {
			results.setTotalResults(Util.getValueByXPathAsInt(doc, "/definitionSearchResults/totalResults"));
			NodeList nl = doc.getElementsByTagName("results");
			if (nl != null && nl.getLength() > 0) {
				NodeList resultNodes = nl.item(0).getChildNodes();
				for (int i = 0; i < resultNodes.getLength(); i++) {
					Node n = resultNodes.item(i);
					if (n.getNodeName().equals("result")) {
						DefinitionSearchResult result = new DefinitionSearchResult();
						result.setSequence(Util.getAttributeAsInt(n.getAttributes(), "sequence"));
						result.setTextProns(Util.getNamedChildTextContent(n, "textProns"));
						result.setSourceDictionary(Util.getNamedChildTextContent(n, "sourceDictionary"));
						result.setExampleUses(Util.getNamedChildTextContent(n, "exampleUses"));

						// get related words; yes, this is ugly
						List<Related> relatedList = new ArrayList<Related>();
						Node node = Util.getNamedChildNode(n, "relatedWords");
						NodeList nodeList = node.getChildNodes();
						if (nodeList != null && nodeList.getLength() > 0) {
							for (int j = 0; j < nodeList.getLength(); j++) {
								Node node1 = nodeList.item(j);
								if (node1.getNodeName().equals("relWord")) {
									Related related = new Related();
									related.setRelationshipType(Util.getAttribute(node1.getAttributes(), "relationshipType"));
									Node wordsNode = Util.getNamedChildNode(node1, "words");
									NodeList wordNodeList = wordsNode.getChildNodes();
									if (wordNodeList != null && wordNodeList.getLength() > 0) {
										for (int k = 0; k < wordNodeList.getLength(); k++) {
											Node wordNode = wordNodeList.item(k);
											if (wordNode.getNodeName().equals("word")) {
												related.addWord(wordNode.getTextContent());
											}
										}
									}
									relatedList.add(related);
								}
							}
						}
						result.setRelatedWords(relatedList);

						result.setLabels(Util.getNamedChildTextContent(n, "labels"));
						result.setCitations(Util.getNamedChildTextContent(n, "citations"));
						result.setWord(Util.getNamedChildTextContent(n, "word"));
						result.setAttributionText(Util.getNamedChildTextContent(n, "attributionText"));
						result.setText(Util.getNamedChildTextContent(n, "text"));
						result.setPartOfSpeech(Util.getNamedChildTextContent(n, "partOfSpeech"));
						result.setScore(Util.getNamedChildTextContentAsDouble(n, "score"));

						resultList.add(result);
					}
				}
			}

			results.setResults(resultList);
		} catch (Exception e) {
			throw buildKnickerException("buildDefinitionSearchResults", doc, e);
		}

		return results;
	}


	/*
		 * Build a consistent error message for all methods in this class.
		 *
		 * @param method the method name that is reporting the error.
		 * @param doc the XML document that was being parsed.
		 * @param e the exception that is being reported.
		 * @return KnickerException describing the error.
		 */
	private static KnickerException buildKnickerException(String method, Document doc, Exception e) {
		StringBuilder sb = new StringBuilder("Error in DTOBuilder method '");
		sb.append(method).append("'. ");
		if (doc == null) {
			sb.append("XML Document was null.");
		} else {
			sb.append("XML Document was not null.");
		}
		return new KnickerException(sb.toString(), e);
	}

}
