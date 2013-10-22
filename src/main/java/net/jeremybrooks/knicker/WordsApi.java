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


import net.jeremybrooks.knicker.dto.DefinitionSearchResults;
import net.jeremybrooks.knicker.dto.SearchResults;
import net.jeremybrooks.knicker.dto.Word;
import net.jeremybrooks.knicker.dto.WordOfTheDay;

import javax.swing.SortOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This is the class that should be used by programs that want to access the
 * Wordnik Words API.
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
public class WordsApi extends Knicker {


	/**
	 * Fetch a random word from the Wordnik corpus.
	 * <p/>
	 * <p>This is equivalent to calling <code>WordsApi.randomWord(true, null, null, 0, 0, 0, 0, 0, 0)</code></p>
	 *
	 * @return random word.
	 * @throws KnickerException if there are any errors.
	 */

	public static Word randomWord() throws
			KnickerException {
		return randomWord(true, null, null, 0, 0, 0, 0, 0, 0);
	}


	/**
	 * Fetch a random word from the Wordnik corpus.
	 *
	 * @param hasDictionaryDef    if true, only return words with dictionary definitions.
	 * @param includePartOfSpeech part of speech values to include. If this parameter
	 *                            is null, it will be ignored.
	 * @param excludePartOfSpeech part of speech values to exclude. If this parameter
	 *                            is null, it will be ignored.
	 * @param minCorpusCount      minimum corpus frequency for terms. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param maxCorpusCount      maximum corpus frequence for terms. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param minDictionaryCount  minimum dictionary count. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param maxDictionaryCount  maximum dictionary count. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param minLength           minimum word length. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param maxLength           maximum word length. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @return a random word.
	 * @throws KnickerException if there are any errors, or if a word cannot be
	 *                          found that matches the parameters.
	 */
	public static Word randomWord(boolean hasDictionaryDef, Set<PartOfSpeech> includePartOfSpeech,
								  Set<PartOfSpeech> excludePartOfSpeech, int minCorpusCount,
								  int maxCorpusCount, int minDictionaryCount, int maxDictionaryCount,
								  int minLength, int maxLength) throws KnickerException {


		Map<String, String> params = new HashMap<String, String>();

		if (hasDictionaryDef) {
			params.put("hasDictionaryDef", "true");
		} else {
			params.put("hasDictionaryDef", "false");
		}


		if (includePartOfSpeech != null && includePartOfSpeech.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (PartOfSpeech sd : includePartOfSpeech) {
				sb.append(sd.toString().trim().replaceAll("_", "-")).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("includePartOfSpeech", sb.toString());
		}

		if (excludePartOfSpeech != null && excludePartOfSpeech.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (PartOfSpeech sd : excludePartOfSpeech) {
				sb.append(sd.toString().trim().replaceAll("_", "-")).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("excludePartOfSpeech", sb.toString());
		}

		if (minCorpusCount >= 0) {
			params.put("minCorpusCount", Integer.toString(minCorpusCount));
		}
		if (maxCorpusCount > 0) {
			params.put("maxCorpusCount", Integer.toString(maxCorpusCount));
		} else {
			params.put("maxCorpusCount", "-1");
		}

		if (minDictionaryCount > 0) {
			params.put("minDictionaryCount", Integer.toString(minDictionaryCount));
		}
		if (maxDictionaryCount > 0) {
			params.put("maxDictionaryCount", Integer.toString(maxDictionaryCount));
		} else {
			params.put("maxDictionaryCount", "-1");
		}

		if (minLength > 0) {
			params.put("minLength", Integer.toString(minLength));
		}
		if (maxLength > 0) {
			params.put("maxLength", Integer.toString(maxLength));
		} else {
			params.put("maxLength", "-1");
		}


		StringBuilder uri = new StringBuilder(WORDS_ENDPOINT);
		uri.append("/randomWord");
		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		return DTOBuilder.buildWord(Util.doGet(uri.toString()));
	}


	/**
	 * Return a list of 10 random words from Wordnik.
	 * <p/>
	 * <p>This is equivalent to calling <code>
	 * WordsApi.randomWords(true, null, null, 0, 0, 0, 0, 0, 0, null, null, 10)
	 * </code</p>
	 *
	 * @return list of random words.
	 * @throws KnickerException if there are any errors.
	 */
	public static List<Word> randomWords() throws KnickerException {
		return WordsApi.randomWords(true, null, null, 0, 0, 0, 0, 0, 0, null, null, 10);
	}


	/**
	 * Return a list of random words from Wordnik.
	 *
	 * @param hasDictionaryDef    if true, only return words with dictionary definitions.
	 * @param includePartOfSpeech part of speech values to include. If this parameter
	 *                            is null, it will be ignored.
	 * @param excludePartOfSpeech part of speech values to exclude. If this parameter
	 *                            is null, it will be ignored.
	 * @param minCorpusCount      minimum corpus frequency for terms. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param maxCorpusCount      maximum corpus frequence for terms. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param minDictionaryCount  minimum dictionary count. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param maxDictionaryCount  maximum dictionary count. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param minLength           minimum word length. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param maxLength           maximum word length. If this parameter
	 *                            is less than 1, it will be ignored.
	 * @param sortBy              specify the sorting of the returned list.
	 * @param sortDirection       specify the sort order of the returned list.
	 * @param limit               maximum number of words to return.
	 * @return a list of random words.
	 * @throws KnickerException if there are any errors, or if a list cannot be
	 *                          found that matches the parameters.
	 */
	public static List<Word> randomWords(boolean hasDictionaryDef, Set<PartOfSpeech> includePartOfSpeech,
										 Set<PartOfSpeech> excludePartOfSpeech, int minCorpusCount,
										 int maxCorpusCount, int minDictionaryCount, int maxDictionaryCount,
										 int minLength, int maxLength, SortBy sortBy,
										 SortDirection sortDirection, int limit) throws KnickerException {

		Map<String, String> params = new HashMap<String, String>();

		if (hasDictionaryDef) {
			params.put("hasDictionaryDef", "true");
		} else {
			params.put("hasDictionaryDef", "false");
		}

		if (includePartOfSpeech != null && includePartOfSpeech.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (PartOfSpeech sd : includePartOfSpeech) {
				sb.append(sd.toString().trim().replaceAll("_", "-")).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("includePartOfSpeech", sb.toString());
		}

		if (excludePartOfSpeech != null && excludePartOfSpeech.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (PartOfSpeech sd : excludePartOfSpeech) {
				sb.append(sd.toString().trim().replaceAll("_", "-")).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("excludePartOfSpeech", sb.toString());
		}

		if (minCorpusCount > 0) {
			params.put("minCorpusCount", Integer.toString(minCorpusCount));
		}
		if (maxCorpusCount > 0) {
			params.put("maxCorpusCount", Integer.toString(maxCorpusCount));
		}

		if (minDictionaryCount > 0) {
			params.put("minDictionaryCount", Integer.toString(minDictionaryCount));
		}
		if (maxDictionaryCount > 0) {
			params.put("maxDictionaryCount", Integer.toString(maxDictionaryCount));
		}

		if (minLength > 0) {
			params.put("minLength", Integer.toString(minLength));
		}
		if (maxLength > 0) {
			params.put("maxLength", Integer.toString(maxLength));
		}

		if (sortBy != null) {
			params.put("sortBy", sortBy.toString());
		}

		if (sortDirection != null) {
			params.put("sortDirection", sortDirection.toString());
		}

		if (limit > 0) {
			params.put("limit", Integer.toString(limit));
		}


		StringBuilder uri = new StringBuilder(WORDS_ENDPOINT);
		uri.append("/randomWords");
		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		return DTOBuilder.buildWords(Util.doGet(uri.toString()));
	}


	/**
	 * Fetch Wordnik’s Word-of-the-Day, including definitions and example sentences.
	 *
	 * @return word of the day, with definitions and example sentences.
	 * @throws KnickerException if there are any errors.
	 */
	public static WordOfTheDay wordOfTheDay() throws KnickerException {
		return DTOBuilder.buildWordOfTheDay(Util.doGet(WORDS_ENDPOINT + "/wordOfTheDay"));
	}


	/**
	 * Search for a word.
	 * <p/>
	 * <p>This is equivalent to calling
	 * <code>search(query, true, null, null, 0, 0, 0, 0, 0, 0, 0, 0)</code>.
	 *
	 * @param query the word to search for.
	 * @return object representing the results of the search query.
	 * @throws KnickerException if there are any errors.
	 */
	public static SearchResults search(String query) throws KnickerException {
		return search(query, true, null, null, 0, 0, 0, 0, 0, 0, 0, 0);
	}


	/**
	 * Search for a word.
	 *
	 * @param query               the word to search for.
	 * @param caseSensitive       search case sensitive.
	 * @param includePartOfSpeech only include these parts of speech.
	 * @param excludePartOfSpeech exclude these parts of speech.
	 * @param minCorpusCount      minimum corpus frequency count for terms.
	 * @param maxCorpusCount      maximum corpus frequency count for terms.
	 * @param minDictionaryCount  minimum number of dictionary entries.
	 * @param maxDictionaryCount  maximum number of dictionary entries.
	 * @param minLength           minimum word length.
	 * @param maxLength           maximum word length.
	 * @param skip                results to skip.
	 * @param limit               maximum number of results to return.
	 * @return object representing the results of the search query.
	 * @throws KnickerException if there are any errors.
	 */
	public static SearchResults search(String query, boolean caseSensitive,
									   Set<PartOfSpeech> includePartOfSpeech,
									   Set<PartOfSpeech> excludePartOfSpeech,
									   int minCorpusCount, int maxCorpusCount,
									   int minDictionaryCount, int maxDictionaryCount,
									   int minLength, int maxLength,
									   int skip, int limit) throws KnickerException {

		Map<String, String> params = new HashMap<String, String>();

		if (caseSensitive) {
			params.put("caseSensitive", "true");
		} else {
			params.put("caseSensitive", "false");
		}

		if (includePartOfSpeech != null && includePartOfSpeech.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (PartOfSpeech sd : includePartOfSpeech) {
				sb.append(sd.toString().trim().replaceAll("_", "-")).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("includePartOfSpeech", sb.toString());
		}

		if (excludePartOfSpeech != null && excludePartOfSpeech.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (PartOfSpeech sd : excludePartOfSpeech) {
				sb.append(sd.toString().trim().replaceAll("_", "-")).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("excludePartOfSpeech", sb.toString());
		}

		if (minCorpusCount > 0) {
			params.put("minCorpusCount", Integer.toString(minCorpusCount));
		}
		if (maxCorpusCount > 0) {
			params.put("maxCorpusCount", Integer.toString(maxCorpusCount));
		}

		if (minDictionaryCount > 0) {
			params.put("minDictionaryCount", Integer.toString(minDictionaryCount));
		}
		if (maxDictionaryCount > 0) {
			params.put("maxDictionaryCount", Integer.toString(maxDictionaryCount));
		}

		if (minLength > 0) {
			params.put("minLength", Integer.toString(minLength));
		}
		if (maxLength > 0) {
			params.put("maxLength", Integer.toString(maxLength));
		}
		if (skip > 0) {
			params.put("skip", Integer.toString(skip));
		}
		if (limit > 0) {
			params.put("limit", Integer.toString(limit));
		}


		StringBuilder uri = new StringBuilder(WORDS_ENDPOINT);
		uri.append("/search/").append(query);
		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		return DTOBuilder.buildSearchResults(Util.doGet(uri.toString()));
	}


	/**
	 * Reverse dictionary search.
	 * <p/>
	 * Performs the reverse search using default values.
	 *
	 * @param query search term.
	 * @return object representing the reverse dictionary search results.
	 * @throws KnickerException if the query is null or empty, or if there are any errors.
	 */
	public static DefinitionSearchResults reverseDictionary(String query) throws KnickerException {
		if (query == null || query.isEmpty()) {
			throw new KnickerException("Query cannot be null or empty.");
		}
		return reverseDictionary(query, null, null, null, null, null, 0, 0, 0, 0, null, false, null, null, 0, 10);
	}

	/**
	 * Reverse dictionary search.
	 *
	 * @param query                     search term.
	 * @param findSenseForWord          restricts words and finds closest sense. Not sent in request if null.
	 * @param includeSourceDictionaries only include these source dictionaries. Not sent in request if null.
	 * @param excludeSourceDictionaries excludes these source dictionaries. Not sent in request if null.
	 * @param includePartOfSpeech       only include these parts of speech. Not sent in request if null.
	 * @param excludePartOfSpeech       excludes these parts of speech. Not sent in request if null.
	 * @param minCorpusCount            minimum corpus frequency for terms. Not sent in request if zero.
	 * @param maxCorpusCount            maximum corpus frequency for terms. Not sent in request if zero.
	 * @param minLength                 minimum word length. Not sent in request if zero.
	 * @param maxLength                 maximum word length. Not sent in request if zero.
	 * @param expandTerms               expand terms. Not sent in request if null.
	 * @param includeTags               return a closed set of XML tags in response.
	 * @param sortBy                    attribute to sort by. Not sent in request if null.
	 * @param sortOrder                 sort direction. Not sent in request if null.
	 * @param skip                      results to skip. Zero is used if less than zero.
	 * @param limit                     maximum number of results to return. Default of 10 used if less than one.
	 * @return object representing the reverse dictionary search results.
	 * @throws KnickerException if query is null or empty, or if there are any other errors.
	 */
	public static DefinitionSearchResults reverseDictionary(String query, String findSenseForWord, Set<SourceDictionary> includeSourceDictionaries,
															Set<SourceDictionary> excludeSourceDictionaries, Set<PartOfSpeech> includePartOfSpeech, Set<PartOfSpeech> excludePartOfSpeech,
															int minCorpusCount, int maxCorpusCount, int minLength, int maxLength, ExpandTerms expandTerms, boolean includeTags,
															SortBy sortBy, SortOrder sortOrder, int skip, int limit) throws KnickerException {

		if (query == null || query.isEmpty()) {
			throw new KnickerException("Query cannot be null or empty.");
		}

		Map<String, String> params = new HashMap<String, String>();

		params.put("query", query);

		if (findSenseForWord != null) {
			params.put("findSenseForWord", findSenseForWord);
		}

		if (includeSourceDictionaries != null && includeSourceDictionaries.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (SourceDictionary sourceDictionary : includeSourceDictionaries) {
				sb.append(sourceDictionary).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			params.put("includeSourceDictionaries", sb.toString());
		}

		if (excludeSourceDictionaries != null && excludeSourceDictionaries.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (SourceDictionary sourceDictionary : excludeSourceDictionaries) {
				sb.append(sourceDictionary).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			params.put("excludeSourceDictionaries", sb.toString());
		}


		if (includePartOfSpeech != null && includePartOfSpeech.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (PartOfSpeech sd : includePartOfSpeech) {
				sb.append(sd.toString().trim().replaceAll("_", "-")).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("includePartOfSpeech", sb.toString());
		}

		if (excludePartOfSpeech != null && excludePartOfSpeech.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (PartOfSpeech sd : excludePartOfSpeech) {
				sb.append(sd.toString().trim().replaceAll("_", "-")).append(',');
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			params.put("excludePartOfSpeech", sb.toString());
		}

		if (minCorpusCount > 0) {
			params.put("minCorpusCount", Integer.toString(minCorpusCount));
		}

		if (maxCorpusCount > 0) {
			params.put("maxCorpusCount", Integer.toString(maxCorpusCount));
		}

		if (minLength > 0) {
			params.put("minLength", Integer.toString(minLength));
		}

		if (maxLength > 0) {
			params.put("maxLength", Integer.toString(maxLength));
		}

		if (expandTerms != null) {
			params.put("expandTerms", expandTerms.toString());
		}

		params.put("includeTags", Boolean.toString(includeTags));

		if (sortBy != null) {
			params.put("sortBy", sortBy.toString());
		}

		if (sortOrder != null) {
			params.put("sortOrder", sortOrder.toString());
		}

		if (skip < 0) {
			params.put("skip", "0");
		} else {
			params.put("skip", Integer.toString(skip));
		}

		if (limit < 1) {
			params.put("limit", "10");
		} else {
			params.put("limit", Integer.toString(limit));
		}

		StringBuilder uri = new StringBuilder(WORDS_ENDPOINT);
		uri.append("/reverseDictionary");
		if (params.size() > 0) {
			uri.append('?').append(Util.buildParamList(params));
		}

		return DTOBuilder.buildDefinitionSearchResults(Util.doGet(uri.toString()));
	}
}
