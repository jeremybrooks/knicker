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

// JAVA I/O
import java.io.StringWriter;

// JAVA UTILITY
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// JAVA XML
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
import net.jeremybrooks.knicker.logger.KnickerLogger;
import org.w3c.dom.Document;


/**
 * This is the class that should be used by programs that want to access the
 * Wordnik API.
 *
 * To use this, you must first register for a Wordnik API key. You can do that
 * here: http://docs.wordnik.com/api. Once you have an API key, set it as a
 * system property, like this:
 * <code>System.setProperty("WORDNIK_API_KEY", "<your API key>");</code>
 * Once you have set the WORDNIK_API_KEY, just call the methods in this class.
 * The methods are all public static. Most of the methods will return instances
 * of classes in the <code>net.jeremybrooks.knicker.dto</code> package.
 *
 * If there are errors, a <code>KnickerException</code> will be thrown.
 *
 * @author jeremyb
 */
public class Knicker {


    
    // API endpoints
    private static final String WORD_ENDPOINT = "http://api.wordnik.com/api/word.xml";
    private static final String WORDS_ENDPOINT = "http://api.wordnik.com/api/words.xml";
    private static final String SUGGEST_ENDPOINT = "http://api.wordnik.com/api/suggest.xml";
    private static final String WOTD_ENDPOINT = "http://api.wordnik.com/api/wordoftheday.xml";
    private static final String ACCOUNT_ENDPOINT = "https://api.wordnik.com/api/account.xml";
    private static final String WORDLIST_ENDPOINT = "https://api.wordnik.com/api/wordList.xml";
    private static final String WORDLISTS_ENDPOINT = "https://api.wordnik.com/api/wordLists.xml";

    
    /**
     * Source dictionaries supported by the Wordnik API.
     */
    public static enum SourceDictionary {
	ahd,
	century,
	wiktionary,
	webster,
	wordnet
    }


    /**
     * Parts of speech supported by the Wordnik API.
     *
     * Note: The underscores will be replaced with dashes when using these
     *       values as parameters to the Wordnik API. For example,
     *       "noun_and_verb" becomes "noun-and-verb".
     */
    public static enum PartOfSpeech {
	noun,
	verb,
	adjective,
	adverb,
	idiom,
	article,
	abbreviation,
	preposition,
	prefix,
	interjection,
	suffix,
	conjunction,
	adjective_and_adverb,
	noun_and_adjective,
	noun_and_verb_transitive,
	noun_and_verb,
	past_participle,
	imperative,
	noun_plural,
	proper_noun_plural,
	verb_intransitive,
	proper_noun,
	adjective_and_noun,
	imperative_and_past_participle,
	pronoun,
	verb_transitive,
	noun_and_verb_intransitive,
	adverb_and_preposition,
	proper_noun_posessive,
	noun_posessive
    }

    /**
     * Relationship types supported by the Wordnik API.
     *
     * <ul>
     * <li>synonym: words with very similar meanings (beautiful: pretty)</li>
     * <li>antonym: words with opposite meanings (beautiful: ugly)</li>
     * <li>form: words with the same stem (dog: dogs, dogged, doggish)</li>
     * <li>hyponym: a word that is more specific (‘chair’ is a hyponym of ‘furniture’)</li>
     * <li>variant: a different spelling for the same word: color and colour are variants</li>
     * <li>verb-stem: Pass in a verb like “running” and get the stem, which is “run.”</li>
     * <li>verb-form: Pass in “run” and find different verb forms, like “running”, “ran”, and “runs.”</li>
     * <li>cross-reference: a related word; (bobcat: lynx)</li>
     * <li>same-context: Shows relationships between words which are often used in the same manner. For instance “cheeseburger” and “pizza” are often used the same way. Both also taste great.</li>
     * </ul>
     *
     * Note: The underscores will be replaced with dashes when using these
     *       values as parameters to the Wordnik API. For example,
     *       "verb_stem" becomes "verb-stem".
     */
    public static enum RelationshipType {
	synonym,
	antonym,
	form,
	hyponym,
	variant,
	verb_stem,
	verb_form,
	cross_reference,
	same_context
    }

    
    /**
     * List types supported by Wordnik.
     */
    public static enum ListType {
	PUBLIC,
	PRIVATE
    }
 

    /**
     * Fetch the word you requested, along with its canonical Wordnik ID,
     * assuming it is found in the corpus.
     *
     * This method is equivalent to calling <code>lookup(word, false, false);</code>.
     *
     * @see http://docs.wordnik.com/api/methods#words
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
     *
     * You can pass additional parameters to retrieve spelling suggestions.
     *
     * @see http://docs.wordnik.com/api/methods#words
     * @param word the word to look up.
     * @param useSuggest Enable the suggester, which will return a list of suggestions, if available.
     * @param literal If the suggester is enabled, simply return the best match.
     * @return results of looking up the word.
     * @throws KnickerException if the word is null, or if there are any errors.
     */
    public static Word lookup(String word, boolean useSuggest, boolean literal) throws KnickerException {
	if (word == null || word.isEmpty()) {
	    throw new KnickerException("Cannot look up an empty word.");
	}

	StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
	uri.append('/').append(word.trim());

	if (useSuggest) {
	    uri.append("?").append("useSuggest=true");
	    if (literal) {
		uri.append("&").append("literal=true");
	    }
	}

	Document doc = Util.doGet(uri.toString());
	try {
	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	    //initialize StreamResult with File object to save to file
	    StreamResult result = new StreamResult(new StringWriter());
	    DOMSource source = new DOMSource(doc);
	    transformer.transform(source, result);

	    String xmlString = result.getWriter().toString();
	    KnickerLogger.getLogger().log(xmlString);
	} catch (Exception e) {
	    throw new KnickerException("", e);
	}

	return DTOBuilder.buildWord(doc);
    }


    /**
     * Fetch interesting bi-gram phrases containing your word.
     * This is equivalent to calling <code>phrase(word, 0);</code>.
     *
     * @see http://docs.wordnik.com/api/methods#phrases
     * @param word the word to look up.
     * @return list of bi-gram phrases containing your word.
     * @throws KnickerException if the word is null, or if there are any errors.
     */
    public static List<Phrase> phrase(String word) throws KnickerException {
	return phrase(word, 0);
    }


    /**
     * Fetch interesting bi-gram phrases containing your word.
     *
     * @see http://docs.wordnik.com/api/methods#phrases
     * @param word the word to look up.
     * @param limit Limit the number of results returned.
     * @return list of bi-gram phrases containing your word.
     * @throws KnickerException if the word is null, or if there are any errors.
     */
    public static List<Phrase> phrase(String word, int limit) throws KnickerException {
	if (word == null || word.isEmpty()) {
	    throw new KnickerException("Cannot look up an empty word.");
	}

	StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
	uri.append('/').append(word.trim());
	uri.append("/phrases");

	if (limit > 0) {
	    uri.append("?limit=").append(limit);
	}

	Document doc = Util.doGet(uri.toString());

	return DTOBuilder.buildPhrase(doc);
    }

    
    /**
     * Fetch a word's definitions from the Century Dictionary,
     * Webster GCIDE, and Wordnet dictionaries.
     * 
     * This is equivalent to <code>definitions(word, 0, false, null, null)</code>.
     *
     * @see http://docs.wordnik.com/api/methods#defs
     * @param word the word to fetch definitions for.
     * @return a list of definitions for the word.
     * @throws KnickerException if the word is null or if there are any errors.
     */
    public static List<Definition> definitions(String word) throws KnickerException {
	return definitions(word, 0, false, null, null);
    }


    /**
     * Fetch a word's definitions from a specific source dictionary.
     *
     * This is equivalent to <code>definitions(word, 0, false, sourceDictionary, null)</code>.
     *
     * @see http://docs.wordnik.com/api/methods#defs
     * @param word the word to fetch definitions for.
     * @param sourceDictionary the source dictionary to use.
     * @return a list of definitions for the word.
     * @throws KnickerException if the word is null or if there are any errors.
     */
    public static List<Definition> definitions(String word, SourceDictionary sourceDictionary) throws
	    KnickerException {
	    return definitions(word, 0, false, sourceDictionary, null);
    }


    /**
     * Fetch a word's definitions, with optional parameters.
     *
     * @see http://docs.wordnik.com/api/methods#defs
     * @param word the word to fetch definitions for.
     * @param limit specify the number of results returned.
     * @param useCanonical if true, let the API select the canonical form of the word.
     * @param sourceDictionary scope the request to a specific source dictionary.
     * @param partOfSpeech specify one or many part of speech types to return
     *        definitions for.
     * @return a list of definitions for the word.
     * @throws KnickerException if the word is null or if there are any errors.
     */
    public static List<Definition> definitions(String word, int limit, boolean  useCanonical,
	    SourceDictionary sourceDictionary, EnumSet<PartOfSpeech> partOfSpeech) throws KnickerException {
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
	    params.put("sourceDictionary", sourceDictionary.toString());
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

	StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
	uri.append('/').append(word.trim());
	uri.append("/definitions");
	if (params.size() > 0) {
	    uri.append('?').append(Util.buildParamList(params));
	}

	return DTOBuilder.buildDefinitions(Util.doGet(uri.toString()));
    }



    /**
     * Retrieve five example sentences for a word in Wordnik’s corpus.
     * Each example contains the source document and a source URL, if it exists.
     * 
     * Convenience method equivalent to <code>examples(word, false)</code>.
     * 
     * @see http://docs.wordnik.com/api/methods#examples
     * @param word the word to fetch examples for.
     * @return list of examples for the word.
     * @throws KnickerException if word is null, or if there are any errors.
     */
    public static List<Example> examples(String word) throws KnickerException {
	return examples(word, false);
    }

    /**
     * Retrieve five example sentences for a word in Wordnik’s corpus.
     * Each example contains the source document and a source URL, if it exists.
     *
     * @see http://docs.wordnik.com/api/methods#examples
     * @param word the word to fetch examples for.
     * @param useCanonical if true, allow the API to select the canonical form of the word.
     * @return list of examples for the word.
     * @throws KnickerException if word is null, or if there are any errors.
     */
    public static List<Example> examples(String word, boolean useCanonical) throws KnickerException {
	if (word == null || word.isEmpty()) {
	    throw new KnickerException("Cannot look up an empty word.");
	}

	StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
	uri.append('/').append(word.trim());
	uri.append("/examples");

	if (useCanonical) {
	    uri.append("?useCanonical=true");
	}

	return DTOBuilder.buildExamples(Util.doGet(uri.toString()));
    }


    /**
     * Retrieve related words for a particular word.
     *
     * Convenience method equivalent to <code>related(word, 0, false, null, null, null)</code>.
     *
     * @see http://docs.wordnik.com/api/methods#relateds
     * @param word the word to fetch related words for.
     * @return list of related words.
     * @throws KnickerException if the word is null, or if there are any errors.
     */
    public static List<Related> related(String word) throws KnickerException {
	return related(word, 0, false, null, null, null);
    }

    
    /**
     * Retrieve related words for a particular word.
     *
     * Convenience method equivalent to <code>related(word, 0, false, null, null, sourceDictionary)</code>.
     *
     * @see http://docs.wordnik.com/api/methods#relateds
     * @param word the word to fetch related words for.
     * @param sourceDictionary  scope results to this source dictionary.
     * @return list of related words.
     * @throws KnickerException if the word is null, or if there are any errors.
     */
    public static List<Related> related(String word, SourceDictionary sourceDictionary) throws
	    KnickerException {
	return related(word, 0, false, null, null, sourceDictionary);
    }


    /**
     * Retrieve related words for a particular word.
     *
     * @see http://docs.wordnik.com/api/methods#relateds
     * @param word the word to fetch related words for.
     * @param limit the number of results to return.
     * @param useCanonical if true, allow the API to select the canonical form of the word.
     * @param partOfSpeech specify the part of speech to fetch related items for.
     * @param relationshipType specify which relationship types to return.
     * @param sourceDictionary scope the request to a specific dictionary.
     * @return list of related words.
     * @throws KnickerException if the word is null, or if there are any errors.
     */
    public static List<Related> related(String word, int limit, boolean useCanonical,
	    EnumSet<PartOfSpeech> partOfSpeech, List<RelationshipType> relationshipType,
	    SourceDictionary sourceDictionary) throws KnickerException {
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
	    params.put("sourceDictionary", sourceDictionary.toString());
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
	if (relationshipType != null && relationshipType.size() > 0) {
	    StringBuilder sb = new StringBuilder();
	    for (RelationshipType rt : relationshipType) {
		sb.append(rt.toString().trim().replaceAll("_", "-")).append(',');
	    }
	    if (sb.length() > 0) {
		sb.deleteCharAt(sb.length() - 1);
	    }

	    params.put("relationshipType", sb.toString());
	}


	StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
	uri.append('/').append(word.trim());
	uri.append("/related");
	uri.append('?').append(Util.buildParamList(params));

	return DTOBuilder.buildRelated(Util.doGet(uri.toString()));
    }
    
    
    /**
     * See how often particular words occur in Wordnik corpus, ordered by year.
     * 
     * Convenience method equivalent to <code>frequency(word, false)</code>.
     * 
     * @see http://docs.wordnik.com/api/methods#freq
     * @param word the word to fetch frequency data for.
     * @return frequency summary.
     * @throws KnickerException if the word is null, or if any errors occur.
     */
    public static FrequencySummary frequency(String word) throws KnickerException {
	return frequency(word, false);
    }


    /**
     * See how often particular words occur in Wordnik corpus, ordered by year.
     *
     * @see http://docs.wordnik.com/api/methods#freq
     * @param word the word to fetch frequency data for.
     * @param useCanonical if true, allow the API to select the canonical form of the word.
     * @return frequency summary.
     * @throws KnickerException if the word is null, or if any errors occur.
     */
    public static FrequencySummary frequency(String word, boolean useCanonical) throws KnickerException {
	if (word == null || word.isEmpty()) {
	    throw new KnickerException("Cannot look up an empty word.");
	}
	StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
	uri.append('/').append(word.trim());
	uri.append("/frequency");

	if (useCanonical) {
	    uri.append("?useCanonical=true");
	}

	return DTOBuilder.buildFrequencySummary(Util.doGet(uri.toString()));
    }


    /**
     * See how common particular words occur with punctuation (period,
     * question mark, exclamation point).
     *
     * Convenience method equivalent to <code>punctuationFactor(word, false)</code>.
     *
     * @see http://docs.wordnik.com/api/methods#punc
     * @param word the word to get the punctuation factor for.
     * @param useCanonical if true, allow the API to select the canonical form of the word.
     * @return punctuation factor information.
     * @throws KnickerException if the word is null or if there are any errors.
     */
    public static PunctuationFactor punctuationFactor(String word) throws KnickerException {
	return punctuationFactor(word, false);
    }

    /**
     * See how common particular words occur with punctuation (period,
     * question mark, exclamation point).
     *
     * @see http://docs.wordnik.com/api/methods#punc
     * @param word the word to get the punctuation factor for.
     * @param useCanonical if true, allow the API to select the canonical form of the word.
     * @return punctuation factor information.
     * @throws KnickerException if the word is null or if there are any errors.
     */
    public static PunctuationFactor punctuationFactor(String word, boolean useCanonical) throws KnickerException {
	if (word == null || word.isEmpty()) {
	    throw new KnickerException("Cannot look up an empty word.");
	}
	StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
	uri.append('/').append(word.trim());
	uri.append("/punctuationFactor");

	if (useCanonical) {
	    uri.append("?useCanonical=true");
	}

	return DTOBuilder.buildPunctuationFactor(Util.doGet(uri.toString()));
    }



    /**
     * Fetch a word’s text pronunciation from the Wornik corpus, in arpabet
     * and/or gcide-diacritical format.
     *
     * Convenience method equivalent to <code>pronunciations(word, false)</code>.
     *
     * @see http://docs.wordnik.com/api/methods#textpron
     * @param word the word to fetch pronunciation information for.
     * @return list of pronunciation information.
     * @throws KnickerException if word is null, or if there are any errors.
     */
    public static List<Pronunciation> pronunciations(String word) throws KnickerException {
	return pronunciations(word, false);
    }
    

    /**
     * Fetch a word’s text pronunciation from the Wornik corpus, in arpabet
     * and/or gcide-diacritical format.
     *
     * @see http://docs.wordnik.com/api/methods#textpron
     * @param word the word to fetch pronunciation information for.
     * @param useCanonical if true, allow the API to select the canonical form of the word.
     * @return list of pronunciation information.
     * @throws KnickerException if word is null, or if there are any errors.
     */
    public static List<Pronunciation> pronunciations(String word, boolean useCanonical) throws KnickerException {
	if (word == null || word.isEmpty()) {
	    throw new KnickerException("Cannot look up an empty word.");
	}
	StringBuilder uri = new StringBuilder(WORD_ENDPOINT);
	uri.append('/').append(word.trim());
	uri.append("/pronunciations");

	if (useCanonical) {
	    uri.append("?useCanonical=true");
	}

	return DTOBuilder.buildPronunciation(Util.doGet(uri.toString()));
    }


    /**
     * The autocomplete service takes a word fragment — that is, a few letters — 
     * and returns words which start with that fragment. The results are based 
     * on corpus frequency, not static word lists, so you have access to more 
     * dynamic words in the language.
     * 
     * You can pass a “*” as a single-character wild-card in the request, 
     * so “t*e” would match “the”.  The single-character wildcard will not
     * substitute a null character in its place, so “the*” will first match 
     * “they” not “the”.  You can pass multiple wildcards in a request.
     * 
     * This convenience method is equivalent to <code>autocomplete(wordFragment, 0, 0, false, false);</code>.
     *
     * @see http://docs.wordnik.com/api/methods#auto
     * @param wordFragment fragment to find matches for.
     * @throws KnickerException if wordFragment is null or if there are any errors.
     */
    public static SearchResult autocomplete(String wordFragment) throws KnickerException {
	return autocomplete(wordFragment, 0, 0, false, false);
    }


    /**
     * The autocomplete service takes a word fragment — that is, a few letters —
     * and returns words which start with that fragment. The results are based
     * on corpus frequency, not static word lists, so you have access to more
     * dynamic words in the language.
     *
     * You can pass a “*” as a single-character wild-card in the request,
     * so “t*e” would match “the”.  The single-character wildcard will not
     * substitute a null character in its place, so “the*” will first match
     * “they” not “the”.  You can pass multiple wildcards in a request.
     *
     * @see http://docs.wordnik.com/api/methods#auto
     * @param wordFragment fragment to find matches for.
     * @param limit limit the number of results. Zero will return all matches.
     * @param skip specify the starting index for the results returned. This
     * allows you to paginate through the matching values.
     * @param sortAlpha if true, data will be sorted by wordstring ascending. If
     * false, data will be sorted by frequency descending.
     * @param hasDef if true, only words with dictionary definitions will be returned.
     * @return search result object with results of the autocomplete.
     * @throws KnickerException if wordFragment is null or if there are any errors.
     */
    public static SearchResult autocomplete(String wordFragment, int limit, int skip, boolean sortAlpha, boolean hasDef)
	    throws KnickerException {
	if (wordFragment == null || wordFragment.isEmpty()) {
	    throw new KnickerException("Cannot look up an empty word fragment.");
	}

	Map<String, String> params = new HashMap<String, String>();
	if (limit > 0) {
	    params.put("limit", Integer.toString(limit));
	}
	if (skip > 0) {
	    params.put("skip", Integer.toString(skip));
	}
	if (sortAlpha) {
	    params.put("sortBy", "alpha");
	}
	if (hasDef) {
	    params.put("hasDictionaryDef", "true");
	}

	StringBuilder uri = new StringBuilder(SUGGEST_ENDPOINT);
	uri.append('/').append(wordFragment.trim());
	if (params.size() > 0) {
	    uri.append('?').append(Util.buildParamList(params));
	}

	return DTOBuilder.buildSearchResult(Util.doGet(uri.toString()));
    }


    /**
     * Fetch Wordnik’s Word-of-the-Day, including definitions and example sentences.
     *
     * @see http://docs.wordnik.com/api/methods#wotd
     * @return word of the day, with definitions and example sentences.
     * @throws KnickerException if there are any errors.
     */
    public static WordOfTheDay wordOfTheDay() throws KnickerException {
	return DTOBuilder.buildWordOfTheDay(Util.doGet(WOTD_ENDPOINT));
    }


    /**
     * Fetch a random word from the Wordnik corpus.
     *
     * @see http://docs.wordnik.com/api/methods#random
     * @param hasDictionaryDef only return words where there is a definition available.
     * @return random word.
     * @throws KnickerException if there are any errors.
     */
    public static Word randomWord(boolean hasDictionaryDef) throws KnickerException {
	StringBuilder uri = new StringBuilder(WORDS_ENDPOINT);
	uri.append("/randomWord");
	if (hasDictionaryDef) {
	    uri.append("?hasDictionaryDef=true");
	}

	return DTOBuilder.buildWord(Util.doGet(uri.toString()));
    }


    /**
     * Fetch multiple random words from the Wordnik corpus.
     *
     * This convenience method is equivalent to 
     * <code>randomWords(null, null, 0, 0, 0, 0, 0, 0, 0, 0);</code>.
     *
     * @see http://docs.wordnik.com/api/methods#random
     * @return list of random words.
     * @throws KnickerException if there are any errors.
     */
    public static List<Word> randomWords() throws KnickerException {
	return randomWords(null, null, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    
    /**
     * Get random words conforming to the parameters provided.
     *
     * @see http://docs.wordnik.com/api/methods#random
     * @param includePartOfSpeech include only words with specific parts of speech.
     * @param excludePartOfSpeech exclude words with specific parts of speech.
     * @param minCorpusCount match a specific corpus frequency in Wordnik's corpus.
     * @param maxCorpusCount match a specific corpus frequency in Wordnik's corpus.
     * @param minDictionaryCount match words .
     * @param maxDictionaryCount match words with a maximum number of definitions.
     * @param minLength match words with a minimum length.
     * @param maxLength match words with a maximum length.
     * @param skip skip a number of records.
     * @param limit limit the number of results. If this parameter is less than
     *        one, the default of 5 will be returned.
     * @return list of random words matching the parameters specified.
     * @throws KnickerException if there are any errors.
     */
    public static List<Word> randomWords(EnumSet<PartOfSpeech> includePartOfSpeech,
	    EnumSet<PartOfSpeech> excludePartOfSpeech, int minCorpusCount,
	    int maxCorpusCount, int minDictionaryCount, int maxDictionaryCount,
	    int minLength, int maxLength, int skip, int limit) throws
	    KnickerException {
	
	Map<String, String> params = new HashMap<String, String>();
	if (includePartOfSpeech != null && !includePartOfSpeech.isEmpty()) {
	    StringBuilder sb = new StringBuilder();
	    for (PartOfSpeech pos : includePartOfSpeech) {
		sb.append(pos).append(',');
	    }
	    sb.deleteCharAt(sb.lastIndexOf(","));
	    params.put("includePartOfSpeech", sb.toString());
	}
	if (excludePartOfSpeech != null && !excludePartOfSpeech.isEmpty()) {
	    StringBuilder sb = new StringBuilder();
	    for (PartOfSpeech pos : excludePartOfSpeech) {
		sb.append(pos).append(',');
	    }
	    sb.deleteCharAt(sb.lastIndexOf(","));
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
	uri.append("/randomWords");
	if (params.size() > 0) {
	    uri.append('?').append(Util.buildParamList(params));
	}

	return DTOBuilder.buildWords(Util.doGet(uri.toString()));

    }

    
    /**
     * Log in to Wordnik.
     *
     * Certain methods — currently, user accounts and list-related CRUD
     * operations — are only available to you if you pass a valid
     * authentication token.
     *
     * This method logs you in to Wordnik via the API and returns an instance of
     * <code>AuthenticationToken</code> which you can then use to make other
     * requests.
     *
     * @see http://docs.wordnik.com/api/methods#auth
     * @param username Wordnik username.
     * @param password Wordnik password for the user.
     * @return authentication token for the user.
     * @throws KnickerException if the username or password is null, or if there
     * are any errors.
     */
    public static AuthenticationToken authenticate(String username, String password) throws KnickerException {
	if (username == null || username.isEmpty()) {
	    throw new KnickerException("You must specify a username.");
	}
	if (password == null || password.isEmpty()) {
	    throw new KnickerException("You must specify a password.");
	}

	AuthenticationToken auth = null;

	StringBuilder uri = new StringBuilder(ACCOUNT_ENDPOINT);
	uri.append("/authenticate/").append(username);
	uri.append("?password=").append(password);

	try {
	    auth = DTOBuilder.buildAuthenticationToken(Util.doGet(uri.toString()));
	} catch (Exception e) {
	    throw new KnickerException("Unable to authenticate. Check your username and password.", e);
	}

	return auth;
    }


    /**
     * Check your API key usage by calling the apiTokenStatus
     * resource. This call does not count against your API usage.
     *
     * @see http://docs.wordnik.com/api/methods#usage
     * @return your current API usage information.
     * @throws KnickerException if there are any errors.
     */
    public static TokenStatus status() throws KnickerException {
	StringBuilder uri = new StringBuilder(ACCOUNT_ENDPOINT);
	uri.append("/apiTokenStatus");

	return DTOBuilder.buildTokenStatus(Util.doGet(uri.toString()));
    }
    

    /**
     * Fetch all of the authenticated user’s word lists.
     *
     * This method requires a valid authentication token, which can be obtained
     * by calling the authenticate method.
     *
     * @see http://docs.wordnik.com/api/methods#lists
     * @param token authentication token.
     * @return all of the user's word lists.
     * @throws KnickerException if the token is null, or if there are any errors.
     */
    public static List<WordList> getLists(AuthenticationToken token) throws KnickerException {
	if (token == null) {
	    throw new KnickerException("Authentication token required.");
	}

	StringBuilder uri = new StringBuilder(WORDLISTS_ENDPOINT);
	
	return DTOBuilder.buildWordLists(Util.doGet(uri.toString(), token));
    }

    
    /**
     * Create a new list on behalf of the authenticated user.
     *
     * This method requires a valid authentication token, which can be obtained
     * by calling the authenticate method.
     *
     * @see http://docs.wordnik.com/api/methods#lists
     * @param token authentication token.
     * @param listName the name of the list to be created.
     * @param description a description of the list to be created.
     * @param type the type of list to be created.
     * @return the newly created word list.
     * @throws KnickerException if any parameters are null, or if there are any errors.
     */
    public static WordList createList(AuthenticationToken token, String listName, String description, ListType type) throws KnickerException {
	if (token == null) {
	    throw new KnickerException("Authentication token required.");
	}
	if (listName == null || listName.isEmpty()) {
	    throw new KnickerException("List name required.");
	}
	if (description == null || description.isEmpty()) {
	    throw new KnickerException("Description required.");
	}
	if (type == null) {
	    throw new KnickerException("List type required.");
	}

	/* The POST data should look like this:
	 *
	 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	    <wordList>
	    <description>test list</description>
	    <name>test</name>
	    <type>PUBLIC</type>
	 </wordList>
	 * 
	 */
	StringBuilder data = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
	data.append("<wordList>\n");
	data.append("<description>").append(description).append("</description>\n");
	data.append("<name>").append(listName).append("</name>\n");
	data.append("<type>").append(type.toString()).append("</type>\n");
	data.append("</wordList>");

	return DTOBuilder.buildWordList(Util.doPost(WORDLISTS_ENDPOINT, data.toString(), token));
    }

    
    /**
     * Add a word to the given list, on behalf of the authenticated user.
     *
     * @see http://docs.wordnik.com/api/methods#lists
     * @param token authentication token.
     * @param permalinkId the permalink id of the list to add the word to.
     * @param word the word to add to the given list.
     * @throws KnickerException if any parameters are null, or if there are any errors.
     */
    public static void addWordToList(AuthenticationToken token, String permalinkId, String word) throws KnickerException {
	if (token == null) {
	    throw new KnickerException("Authentication token required.");
	}
	if (permalinkId == null || permalinkId.isEmpty()) {
	    throw new KnickerException("Parameter permalinkId required.");
	}
	if (word == null || word.isEmpty()) {
	    throw new KnickerException("Parameter word required.");
	}

	/*
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<stringValues>
	    <stringValue>
		<wordstring>hello</wordstring>
	    </stringValue>
	</stringValues>
	 */
	StringBuilder data = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
	data.append("<stringValues>\n");
	data.append("<stringValue>\n");
	data.append("<wordstring>").append(word).append("</wordstring>\n");
	data.append("</stringValue>\n");
	data.append("</stringValues>\n");

	StringBuilder uri = new StringBuilder(WORDLIST_ENDPOINT);
	uri.append("/").append(permalinkId).append("/words");

	Util.doPost(uri.toString(), data.toString(), token);
    }

    
    /**
     * Return all the words from the given list.
     *
     * This method requires a valid authentication token, which can be obtained
     * by calling the authenticate method.
     *
     * @see http://docs.wordnik.com/api/methods#lists
     * @param token authentication token.
     * @param permalinkId the permalink of the word list to get words from.
     * @return list of word list words.
     * @throws KnickerException if the token or permalinkId are null, or if there are any errors.
     */
    public static List<WordListWord> getWordsFromList(AuthenticationToken token, String permalinkId) throws KnickerException {
	if (token == null) {
	    throw new KnickerException("Authentication token required.");
	}
	if (permalinkId == null || permalinkId.isEmpty()) {
	    throw new KnickerException("Parameter permalinkId required.");
	}

	StringBuilder uri = new StringBuilder(WORDLIST_ENDPOINT);
	uri.append("/").append(permalinkId).append("/words");

	return DTOBuilder.buildWordListWords(Util.doGet(uri.toString(), token));
    }

    
    /**
     * Delete the given word from the given list.
     *
     * @see http://docs.wordnik.com/api/methods#lists
     * @param token authentication token.
     * @param permalinkId the permalink of the word list to delete the word from.
     * @param word the word to delete from the list.
     * @throws KnickerException if any parameter is invalid, or if there are any errors.
     */
    public static void deleteWordFromList(AuthenticationToken token, String permalinkId, String word) throws KnickerException {
	if (token == null) {
	    throw new KnickerException("Authentication token required.");
	}
	if (permalinkId == null || permalinkId.isEmpty()) {
	    throw new KnickerException("Parameter permalinkId required.");
	}
	if (word == null || word.isEmpty()) {
	    throw new KnickerException("Parameter word required.");
	}

	/*
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<stringValues>
	    <stringValue>
		<wordstring>hello</wordstring>
	    </stringValue>
	</stringValues>
	 */
	StringBuilder data = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
	data.append("<stringValues>\n");
	data.append("<stringValue>\n");
	data.append("<wordstring>").append(word).append("</wordstring>\n");
	data.append("</stringValue>\n");
	data.append("</stringValues>\n");

	StringBuilder uri = new StringBuilder(WORDLIST_ENDPOINT);
	uri.append("/").append(permalinkId).append("/deleteWords");

	// NOTE: The Wordnik API says to use a DELETE method here, but
	//       it did not work. A POST works.
	Util.doPost(uri.toString(), data.toString(), token);
    }


    
    /**
     * Delete the given word list.
     *
     * This method requires a valid authentication token, which can be obtained
     * by calling the authenticate method.
     *
     * @see http://docs.wordnik.com/api/methods#lists
     * @param token authentication token.
     * @param permalinkId the permalink Id of the word list to doDelete.
     * @throws KnickerException if the token or permalinkId are null, or if there are any errors.
     */
    public static void deleteList(AuthenticationToken token, String permalinkId) throws KnickerException {
	if (token == null) {
	    throw new KnickerException("Authentication token required.");
	}
	if (permalinkId == null || permalinkId.isEmpty()) {
	    throw new KnickerException("Parameter permalinkId required.");
	}

	StringBuilder uri = new StringBuilder(WORDLIST_ENDPOINT);
	uri.append("/").append(permalinkId);

	Util.doDelete(uri.toString(), null, token);
    }


    /**
     * Search for words that match the query.
     *
     * <p>The query can contain the following wildcards:</p>
     * 
     * <ul>
     * <li>@ match one vowel</li>
     * <li># match one consonant</li>
     * <li>? match one character</li>
     * <li>* match zero-to-many characters</li>
     * </ul>
     *
     * <p>So to find words starting in “c” and having two consecutive vowels,
     * ending in “p”, the query would look like this: <code>c@@*p</code>.</p>
     *
     * <p>This convenience method is equivalent to
     * <code>wordSearch(query, null, null, 0, 0, 0, 0, 0, 0, 0, 0);</code>.</p>
     *
     * @see http://docs.wordnik.com/api/methods#wordsearch
     * @param query query specifying how to match words.
     * @return list of words as WordFrequency objects.
     * @throws KnickerException if the query is null or if there are any errors.
     */
    public static List<WordFrequency> wordSearch(String query) throws KnickerException {
	return wordSearch(query, null, null, 0, 0, 0, 0, 0, 0, 0, 0);
    }
    

    /**
     * Search for words that match the query.
     *
     * <p>The query can contain the following wildcards:</p>
     *
     * <ul>
     * <li>@ match one vowel</li>
     * <li># match one consonant</li>
     * <li>? match one character</li>
     * <li>* match zero-to-many characters</li>
     * </ul>
     *
     * <p>So to find words starting in “c” and having two consecutive vowels,
     * ending in “p”, the query would look like this: <code>c@@*p</code>.</p>
     *
     * @see http://docs.wordnik.com/api/methods#wordsearch
     * @param query query specifying words to search for.
     * @param includePartOfSpeech parts of speech to include.
     * @param excludePartOfSpeech parts of speech to exclude.
     * @param minCorpusCount minimum count in the Wordnik corpus.
     * @param maxCorpusCount maximum count in the Wordnik corpus.
     * @param minDictionaryCount minimum number of times the word is defined.
     * @param maxDictionaryCount maximum number of times the word is defined.
     * @param minLength minimum length for returned words.
     * @param maxLength maximum length for returned words.
     * @param skip number of records to skip.
     * @param limit maximum number of records to return.
     * @return list of words as WordFrequency objects.
     * @throws KnickerException if the query is null or if there are any errors.
     */
    public static List<WordFrequency> wordSearch(String query,
	    EnumSet<PartOfSpeech> includePartOfSpeech,
	    EnumSet<PartOfSpeech> excludePartOfSpeech,
	    int minCorpusCount, int maxCorpusCount,
	    int minDictionaryCount, int maxDictionaryCount,
	    int minLength, int maxLength,
	    int skip, int limit) throws KnickerException {

	if (query == null || query.isEmpty()) {
	    throw new KnickerException("You must provide a search query.");
	}
	
	Map<String, String> params = new HashMap<String, String>();

	params.put("query", query);

	if (includePartOfSpeech != null && !includePartOfSpeech.isEmpty()) {
	    StringBuilder sb = new StringBuilder();
	    for (PartOfSpeech pos : includePartOfSpeech) {
		sb.append(pos).append(',');
	    }
	    sb.deleteCharAt(sb.lastIndexOf(","));
	    params.put("includePartOfSpeech", sb.toString());
	}
	if (excludePartOfSpeech != null && !excludePartOfSpeech.isEmpty()) {
	    StringBuilder sb = new StringBuilder();
	    for (PartOfSpeech pos : excludePartOfSpeech) {
		sb.append(pos).append(',');
	    }
	    sb.deleteCharAt(sb.lastIndexOf(","));
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
	uri.append("/search");
	if (params.size() > 0) {
	    uri.append('?').append(Util.buildParamList(params));
	}

	return DTOBuilder.buildWordFrequency(Util.doGet(uri.toString()));
    }
}
