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

// JAVA I/O

import net.jeremybrooks.knicker.dto.AudioFileMetadata;
import net.jeremybrooks.knicker.dto.Definition;
import net.jeremybrooks.knicker.dto.Example;
import net.jeremybrooks.knicker.dto.FrequencySummary;
import net.jeremybrooks.knicker.dto.Phrase;
import net.jeremybrooks.knicker.dto.Pronunciation;
import net.jeremybrooks.knicker.dto.Related;
import net.jeremybrooks.knicker.dto.SearchResults;
import net.jeremybrooks.knicker.dto.Syllable;
import net.jeremybrooks.knicker.dto.Word;
import net.jeremybrooks.knicker.logger.KnickerLogger;
import org.w3c.dom.Document;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// JAVA UTILITY
// JAVA XML
// KNICKER
// XML


/**
 * This is the class that should be used by programs that want to access the
 * Wordnik Word API.
 * <p/>
 * To use this, you must first register for a Wordnik API key. You can do that
 * here: http://api.wordnik.com/signup/. Once you have an API key, set it as a
 * system property, like this:
 * <code>System.setProperty("WORDNIK_API_KEY", "<your API key>");</code>
 * Once you have set the WORDNIK_API_KEY, just call the methods in this class.
 * The methods are all public static. Most of the methods will return instances
 * of classes in the <code>net.jeremybrooks.knicker.dto</code> package.
 * <p/>
 * If there are errors, a <code>KnickerException</code> will be thrown.
 *
 * @author Jeremy Brooks
 */
public class WordApi extends Knicker {

	/**
	 * Fetch the word you requested, along with its canonical Wordnik ID,
	 * assuming it is found in the corpus.
	 * <p/>
	 * This method is equivalent to calling <code>lookup(word, false, false);</code>.
	 *
	 * @param word the word to look up.
	 * @return results of looking up the word.
	 * @throws KnickerException if the word is null, or if there are any errors.
	 */
	public static Word lookup(String word) throws KnickerException {
		return lookup(word, false, false);
	}


	/**
	 * Fetch the word you requested, along with its canonical Wordnik ID,
	 * assuming it is found in the corpus.
	 * <p/>
	 * You can pass additional parameters to retrieve spelling suggestions.
	 *
	 * @param word               the word to look up.
	 * @param useCanonical       If true will try to return the correct word root
	 *                           ('cats' -> 'cat'). If false returns exactly what was requested.
	 * @param includeSuggestions Return suggestions (for correct spelling, case variants, etc.)
	 * @return results of looking up the word.
	 * @throws KnickerException if the word is null, or if there are any errors.
	 */
	public static Word lookup(String word, boolean useCanonical, boolean includeSuggestions) throws KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up an empty word.");
		}

		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim()).append('?');

		if (useCanonical) {
			uri.append("useCanonical=").append(Boolean.toString(useCanonical)).append("&");
		}
		if (includeSuggestions) {
			uri.append("useSuggestions=").append(Boolean.toString(includeSuggestions));
		}


		return DTOBuilder.buildWord(Util.doGet(uri.toString()));
	}


	/**
	 * Retrieve five example sentences for a word in Wordnik’s corpus.
	 * <p/>
	 * <p>This is equivalent to calling <examples>examples(word, false, null, false, 0, 0);</code></p>
	 *
	 * @param word the word to fetch examples for.
	 * @return search results with the examples list populated.
	 * @throws KnickerException if word is null, or if there are any errors.
	 */
	public static SearchResults examples(String word) throws KnickerException {
		return examples(word, false, null, false, 0, 0);
	}


	/**
	 * Retrieve example sentences for a word in Wordnik's corpus.
	 *
	 * @param word              the word to fetch examples for.
	 * @param includeDuplicates show duplicate examples from different sources.
	 * @param contentProvider   return results from a specific content provider.
	 *                          If this parameter is null, it is ignored.
	 * @param useCanonical      if true will try to return the correct word root
	 *                          ('cats' -> 'cat'). If false returns exactly what was requested.
	 * @param skip              results to skip. If this parameter is less than 1, it is ignored.
	 * @param limit             maximum number of results to return. If this parameter is
	 *                          less than 1, it is ignored.
	 * @return search results with the list of examples populated.
	 * @throws KnickerException if word is null, or if there are any errors.
	 */
	public static SearchResults examples(String word, boolean includeDuplicates,
										 String contentProvider, boolean useCanonical, int skip, int limit) throws
			KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up an empty word.");
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("includeDuplicates", Boolean.toString(includeDuplicates));
		if (contentProvider != null) {
			params.put("contentProvider", contentProvider);
		}
		params.put("useCanonical", Boolean.toString(useCanonical));
		if (skip > 0) {
			params.put("skip", Integer.toString(skip));
		}
		if (limit > 0) {
			params.put("limit", Integer.toString(limit));
		}

		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim());
		uri.append("/examples");
		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		return DTOBuilder.buildExamples(Util.doGet(uri.toString()));
	}


	/**
	 * Look up definitions for a word.
	 * <p/>
	 * This is equivalent to calling <code>definitions(word, 0, null, false, null, false, false);</code>
	 *
	 * @param word the word to return definitions for.
	 * @return list of definitions for the word.
	 * @throws KnickerException if the word is null or empty, or if there are
	 *                          any errors.
	 */
	public static List<Definition> definitions(String word) throws
			KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up definitions for an empty word.");
		}

		return definitions(word, 0, null, false, null, false, false);
	}


	/**
	 * Look up definitions for a word.
	 * <p/>
	 * Dictionaries will be tried in the order returned by the sourceDictionaries
	 * iterator. If you want a specific order, use a Set implementation that
	 * guarantees a specific order.
	 *
	 * NOTE: If you get errors when trying to look up definitions in more than one dictionary at the same time,
	 *       either use the dictionary <code>SourceDictionary.all</code> or make multiple calls to single dictionaries
	 *       and aggregate the results.
	 *
	 * @param word               word to return definitions for.
	 * @param sourceDictionaries dictionaries to retrieve definitions from.
	 * @return list of definitions for the word.
	 * @throws KnickerException if the word is null or empty, or if there are any errors.
	 */
	public static List<Definition> definitions(String word,
											   Set<SourceDictionary> sourceDictionaries) throws
			KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up definitions for an empty word.");
		}

		Map<String, String> params = new HashMap<String, String>();
		if (sourceDictionaries != null && sourceDictionaries.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (SourceDictionary sd : sourceDictionaries) {
				sb.append(sd.toString().trim()).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("sourceDictionaries", sb.toString());
		}

		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim());
		uri.append("/definitions");
		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		return DTOBuilder.buildDefinitions(Util.doGet(uri.toString()));
	}


	/**
	 * Look up definitions for a word.
	 * <p/>
	 * Dictionaries will be tried in the order returned by the sourceDictionaries
	 * iterator. If you want a specific order, use a Set implementation that
	 * guarantees a specific order.
	 *
	 * @param word               word to return definitions for.
	 * @param limit              maximum number of results to return.
	 * @param partOfSpeech       list of part-of-speech types to retrieve.
	 * @param includeRelated     return related words with definitions.
	 * @param sourceDictionaries dictionaries to retrieve definitions from.
	 * @param useCanonical       if true will try to return the correct word root
	 *                           ('cats' -> 'cat'). If false returns exactly what was requested.
	 * @param includeTags        return a closed set of XML tags in response.
	 * @return list of definitions for the word.
	 * @throws KnickerException if the word is null or empty, or if there was an error.
	 */
	public static List<Definition> definitions(String word, int limit,
											   Set<PartOfSpeech> partOfSpeech, boolean includeRelated,
											   Set<SourceDictionary> sourceDictionaries,
											   boolean useCanonical, boolean includeTags) throws KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up definitions for an empty word.");
		}

		Map<String, String> params = new HashMap<String, String>();
		if (limit > 0) {
			params.put("limit", Integer.toString(limit));
		}
		if (useCanonical) {
			params.put("useCanonical", "true");
		}

		if (partOfSpeech != null && partOfSpeech.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (PartOfSpeech pos : partOfSpeech) {
				sb.append(pos.toString().trim().replaceAll("_", "-")).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("partOfSpeech", sb.toString());
		}

		if (sourceDictionaries != null && sourceDictionaries.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (SourceDictionary sd : sourceDictionaries) {
				sb.append(sd.toString().trim()).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("sourceDictionaries", sb.toString());
		}

		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim());
		uri.append("/definitions");
		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}


		return DTOBuilder.buildDefinitions(Util.doGet(uri.toString()));
	}


	/**
	 * See how often particular words occur in Wordnik corpus, ordered by year.
	 * <p/>
	 * <p>This is the same as calling <code>frequency(word, false, 0, 0)</code>.</p>
	 *
	 * @param word the word to fetch frequency data for.
	 * @return frequency summary.
	 * @throws KnickerException if the word is null, or if any errors occur.
	 */
	public static FrequencySummary frequency(String word) throws
			KnickerException {
		return frequency(word, false, 0, 0);
	}


	/**
	 * See how often particular words occur in Wordnik corpus, ordered by year.
	 * <p/>
	 * <p>This is the same as calling <code>frequency(word, useCanonical, 0, 0)</code>.</p>
	 *
	 * @param word         word the word to fetch frequency data for.
	 * @param useCanonical if true, allow the API to select the canonical form of the word.
	 * @return frequency summary.
	 * @throws KnickerException if the word is null, or if any errors occur.
	 */
	public static FrequencySummary frequency(String word, boolean useCanonical)
			throws
			KnickerException {
		return frequency(word, useCanonical, 0, 0);
	}


	/**
	 * See how often particular words occur in Wordnik corpus, ordered by year.
	 *
	 * @param word         the word to fetch frequency data for.
	 * @param useCanonical if true, allow the API to select the canonical form of the word.
	 * @param startYear    starting year. If < 1, the parameter is ignored.
	 * @param endYear      ending year. If < 1, the parameter is ignored.
	 * @return frequency summary.
	 * @throws KnickerException if the word is null, or if any errors occur.
	 * @see <a href="http://docs.wordnik.com/api/methods#freq">Wordnik documentation</a>
	 */
	public static FrequencySummary frequency(String word, boolean useCanonical,
											 int startYear, int endYear) throws KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up an empty word.");
		}

		Map<String, String> params = new HashMap<String, String>();

		if (useCanonical) {
			params.put("useCanonical", "true");
		}

		if (startYear > 0) {
			params.put("startYear", Integer.toString(startYear));
		}
		if (endYear > 0) {
			params.put("endYear", Integer.toString(endYear));
		}

		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim());
		uri.append("/frequency");
		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		return DTOBuilder.buildFrequencySummary(Util.doGet(uri.toString()));
	}


	/**
	 * Return the top example for a word.
	 * <p/>
	 * <p>This is equivalent to calling <code>topExample(word, null, false)</code></p>.
	 *
	 * @param word the word to return the top example for.
	 * @return top example for the word.
	 * @throws KnickerException if word is null or if there are any errors.
	 */
	public static Example topExample(String word) throws KnickerException {
		return topExample(word, null, false);
	}


	/**
	 * Return the top example for a word.
	 *
	 * @param word            the word to return the top example for.
	 * @param contentProvider return results from a specific content provider.
	 *                        If this parameter is null, it is ignored.
	 * @param useCanonical    if true will try to return the correct word root
	 *                        ('cats' -> 'cat'). If false, returns exactly what was requested.
	 * @return top example for the word.
	 * @throws KnickerException if word is null or if there are any errors.
	 */
	public static Example topExample(String word, String contentProvider, boolean useCanonical) throws
			KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up an empty word.");
		}

		Map<String, String> params = new HashMap<String, String>();

		params.put("useCanonical", Boolean.toString(useCanonical));
		if (contentProvider != null) {
			params.put("contentProvider", contentProvider);
		}

		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim());
		uri.append("/topExample");
		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		return DTOBuilder.buildTopExample(Util.doGet(uri.toString()));
	}


	/**
	 * Retrieve related words for a particular word.
	 * <p/>
	 * <p>This is equivalent to calling <code>
	 * related(word, false, null, 0);
	 * </code></p>
	 *
	 * @param word the word to fetch related words for.
	 * @return list of related words.
	 * @throws KnickerException if the word is null, or if there are any errors.
	 */
	public static List<Related> related(String word) throws KnickerException {
		return related(word, false, null, 0);
	}


	/**
	 * Retrieve related words for a particular word.
	 *
	 * @param word             the word to fetch related words for.
	 * @param useCanonical     if true, allow the API to select the canonical form of the word.
	 * @param relationshipType specify which relationship types to return.
	 * @param limitPerRelationshipType Limits the total results per type of relationship type
	 * @return list of related words.
	 * @throws KnickerException if the word is null, or if there are any errors.
	 */
	public static List<Related> related(String word, boolean useCanonical,
										Set<RelationshipType> relationshipType,
										int limitPerRelationshipType) throws KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up an empty word.");
		}

		Map<String, String> params = new HashMap<String, String>();
		if (limitPerRelationshipType > 0) {
			params.put("limitPerRelationshipType", Integer.toString(limitPerRelationshipType));
		}
		if (useCanonical) {
			params.put("useCanonical", "true");
		}
		if (relationshipType != null && relationshipType.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (RelationshipType rt : relationshipType) {
				sb.append(rt.toString().trim().replaceAll("_", "-")).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("relationshipTypes", sb.toString());
		}


		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim());
		uri.append("/relatedWords");
		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		return DTOBuilder.buildRelated(Util.doGet(uri.toString()));
	}


	/**
	 * Fetch interesting bi-gram phrases containing your word.
	 * <p/>
	 * <p>This is equivalent to calling <code>phrases(word, 0, null, false)</code></p>
	 *
	 * @param word the word to look up.
	 * @return list of bi-gram phrases containing your word.
	 * @throws KnickerException if the word is null, or if there are any errors.
	 */
	public static List<Phrase> phrases(String word) throws KnickerException {
		return phrases(word, 0, null, false);
	}


	/**
	 * Fetch interesting bi-gram phrases containing your word.
	 *
	 * @param word         the word to look up.
	 * @param limit        Limit the number of results returned. If this parameter is
	 *                     < 1, it will be ignored.
	 * @param wlmi         minimum WLMI for the phrase. If this parameter is null, it
	 *                     is ignored.
	 * @param useCanonical If true will try to return the correct word root
	 *                     ('cats' -> 'cat'). If false returns exactly what was requested.
	 * @return list of bi-gram phrases containing your word.
	 * @throws KnickerException if the word is null, or if there are any errors.
	 * @see <a ref="http://docs.wordnik.com/api/methods#phrases">Wordnik documentation</a>
	 */
	public static List<Phrase> phrases(String word, int limit, String wlmi, boolean useCanonical) throws KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up an empty word.");
		}

		Map<String, String> params = new HashMap<String, String>();
		if (limit > 0) {
			params.put("limit", Integer.toString(limit));
		}
		if (wlmi != null && !wlmi.trim().isEmpty()) {
			params.put("wlmi", wlmi.trim());
		}
		if (useCanonical) {
			params.put("useCanonical", "true");
		}

		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim());
		uri.append("/phrases");

		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		Document doc = Util.doGet(uri.toString());

		return DTOBuilder.buildPhrase(doc);
	}


	/**
	 * Returns syllable information for a word.
	 * <p/>
	 * <p>This is equivalent to calling <code>hyphenation(word, false, null, 0)</code></p>
	 *
	 * @param word word to get syllables for.
	 * @return list of syllables for the word.
	 * @throws KnickerException if word is null, or if there are any errors.
	 */
	public static List<Syllable> hyphenation(String word) throws
			KnickerException {
		return hyphenation(word, false, null, 0);
	}


	/**
	 * Returns syllable information for a word.
	 *
	 * @param word             word to get syllables for.
	 * @param useCanonical     if true will try to return a correct word root
	 *                         ('cats' -> 'cat'). If false returns exactly what was requested.
	 * @param sourceDictionary source dictionary to get data from. If this parameter
	 *                         is null, it will be ignored.
	 * @param limit            maximum number of results to return. If this parameter is less
	 *                         than 1, it will be ignored.
	 * @return list of syllables for the word.
	 * @throws KnickerException if word is null, or if there are any errors.
	 */
	public static List<Syllable> hyphenation(String word, boolean useCanonical,
											 SourceDictionary sourceDictionary, int limit) throws
			KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up an empty word.");
		}

		Map<String, String> params = new HashMap<String, String>();
		if (limit > 0) {
			params.put("limit", Integer.toString(limit));
		}
		if (useCanonical) {
			params.put("useCanonical", "true");
		}
		if (sourceDictionary != null) {
			params.put("sourceDictionary", sourceDictionary.toString().trim().replaceAll("_", "-"));
		}

		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim());
		uri.append("/hyphenation");

		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		Document doc = Util.doGet(uri.toString());

		return DTOBuilder.buildHyphenation(doc);
	}


	/**
	 * Returns text pronunciations for a given word.
	 * <p/>
	 * <p>This is equivalent to calling pronunciations(word, false, null, null, 0)</code></p>
	 *
	 * @param word word to fetch pronunciation information for.
	 * @return list of pronunciation objects.
	 * @throws KnickerException if word is null or if there are any errors.
	 */
	public static List<Pronunciation> pronunciations(String word) throws
			KnickerException {
		return pronunciations(word, false, null, null, 0);
	}


	/**
	 * Returns text pronunciations for a given word.
	 *
	 * @param word             word to fetch pronunciation information for.
	 * @param useCanonical     if true will try to return a correct word root
	 *                         ('cats' -> 'cat'). If false returns exactly what was requested.
	 * @param sourceDictionary source dictionary to use for pronunciation data.
	 * @param typeFormat       text pronunciation type.
	 * @param limit            maximum number of results to return.
	 * @return list of pronunciation objects.
	 * @throws KnickerException if word is null or if there are any errors.
	 */
	public static List<Pronunciation> pronunciations(String word, boolean useCanonical,
													 SourceDictionary sourceDictionary, TypeFormat typeFormat, int limit) throws
			KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up an empty word.");
		}

		Map<String, String> params = new HashMap<String, String>();
		if (limit > 0) {
			params.put("limit", Integer.toString(limit));
		}
		if (useCanonical) {
			params.put("useCanonical", "true");
		}
		if (sourceDictionary != null) {
			params.put("sourceDictionary", sourceDictionary.toString().trim().replaceAll("_", "-"));
		}
		if (typeFormat != null) {
			params.put("typeFormat", typeFormat.toString().trim().replaceAll("_", "-"));
		}

		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim());
		uri.append("/pronunciations");

		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		Document doc = Util.doGet(uri.toString());

		return DTOBuilder.buildPronunciation(doc);
	}


	/**
	 * Fetches audio metadata for a word.
	 * <p/>
	 * <p>This is equivalent to calling <code><p>audio(word, false, 0)</code></p>
	 *
	 * @param word the word to fetch audio metadata for.
	 * @return list of AudioFileMetadata objects for the word.
	 * @throws KnickerException if word is null or if there are any errors.
	 */
	public static List<AudioFileMetadata> audio(String word) throws KnickerException {
		return audio(word, false, 0);
	}


	/**
	 * Fetches audio metadata for a word.
	 *
	 * @param word         the word to fetch audio metadata for.
	 * @param useCanonical if true will try to return a correct word root
	 *                     ('cats' -> 'cat'). If false returns exactly what was requested.
	 * @param limit        maximum number of results to return.
	 * @return list of AudioFileMetadata objects for the word.
	 * @throws KnickerException if word is null or if there are any errors.
	 */
	public static List<AudioFileMetadata> audio(String word, boolean useCanonical, int limit) throws
			KnickerException {
		if (word == null || word.isEmpty()) {
			throw new KnickerException("Cannot look up an empty word.");
		}

		Map<String, String> params = new HashMap<String, String>();
		if (limit > 0) {
			params.put("limit", Integer.toString(limit));
		}
		if (useCanonical) {
			params.put("useCanonical", "true");
		}

		StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
		uri.append('/').append(word.trim());
		uri.append("/audio");

		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		Document doc = Util.doGet(uri.toString());

		return DTOBuilder.buildAudio(doc);
	}


	/**
	 * Get the audio data from Wordnik.
	 * <p/>
	 * <p>The fileUrl parameter in the audioFileMetadata object will expire.
	 * If you want the audio data, you should call this method shorty getting
	 * the audioFileMetadata object.</p>
	 * <p/>
	 * <p>If the Wordnik servers return anything other than an HTTP 200 in the
	 * header, a KnickerException will be thrown indicating what the HTTP
	 * response was, and showing all the headers.</p>
	 *
	 * @param audioFileMetadata audio file metadata object describing the audio
	 *                          pronunciation you want to retrieve.
	 * @return audio data.
	 * @throws KnickerException if the audioFileMetadata is null, or if there
	 *                          are any errors.
	 */
	public static byte[] getAudioData(AudioFileMetadata audioFileMetadata) throws KnickerException {
		if (audioFileMetadata == null) {
			throw new KnickerException("Parameter audioFileMetadata cannot be null.");
		}

		byte[] data = null;
		int contentLength = 0;
		BufferedInputStream in = null;
		URL page = null;
		URLConnection conn = null;

		try {
			page = new URL(audioFileMetadata.getFileUrl());
			conn = page.openConnection();
			contentLength = conn.getContentLength();
			in = new BufferedInputStream(conn.getInputStream());
			data = new byte[contentLength];
			int bytesRead = 0;
			int offset = 0;
			while (offset < contentLength) {
				bytesRead = in.read(data, offset, data.length - offset);
				if (bytesRead == -1) {
					break;
				}
				offset += bytesRead;
			}

		} catch (Exception e) {
			throw new KnickerException("There was an error while getting audio data from URL "
					+ audioFileMetadata.getFileUrl(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				// not critical, just log warning
				KnickerLogger.getLogger().log("WARN: Error closing input stream.", e);
			}
		}

		return data;
	}

}
