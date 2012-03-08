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

import net.jeremybrooks.knicker.Knicker.PartOfSpeech;
import net.jeremybrooks.knicker.Knicker.SortBy;
import net.jeremybrooks.knicker.Knicker.SortDirection;
import net.jeremybrooks.knicker.dto.AuthenticationToken;
import net.jeremybrooks.knicker.dto.SearchResults;
import net.jeremybrooks.knicker.dto.Word;
import net.jeremybrooks.knicker.dto.WordOfTheDay;
import net.jeremybrooks.knicker.logger.KnickerLogger;
import net.jeremybrooks.knicker.logger.StdoutLogger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Jeremy Brooks
 */
public class WordsApiTest {

    public WordsApiTest() {
    }


    static String username;
    static String password;
    static AuthenticationToken token;

    @BeforeClass
    public static void setUpClass() throws Exception {
	Properties p = new Properties();
	InputStream in = WordsApiTest.class.getResourceAsStream("/secret.properties");
	p.load(in);

	System.setProperty("WORDNIK_API_KEY", p.getProperty("WORDNIK_API_KEY"));
	username = p.getProperty("WORDNIK_USERNAME");
	password = p.getProperty("WORDNIK_PASSWORD");

	KnickerLogger.setLogger(new StdoutLogger());
    }


    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    /**
     * Test of randomWord method, of class WordsApi.
     */
    @Test
    public void testRandomWord_0args() throws Exception {
	System.out.println("randomWord");
	Word result = WordsApi.randomWord();

	assertNotNull(result);
	assertNotNull(result.getWord());
	assertTrue(result.getWord().length() > 0);

    }


    /**
     * Test of randomWord method, of class WordsApi.
     */
    @Test
    public void testRandomWord_9args() throws Exception {
	System.out.println("randomWord_9args");
	boolean hasDictionaryDef = false;
	EnumSet<PartOfSpeech> includePartOfSpeech = null;
	EnumSet<PartOfSpeech> excludePartOfSpeech = null;
	int minCorpusCount = 0;
	int maxCorpusCount = 0;
	int minDictionaryCount = 0;
	int maxDictionaryCount = 0;
	int minLength = 0;
	int maxLength = 0;

	Word result = WordsApi.randomWord(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength);

	assertNotNull(result);
	assertNotNull(result.getWord());
	assertTrue(result.getWord().length() > 0);

	minLength = 10;
	result = WordsApi.randomWord(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength);
	assertNotNull(result);
	assertNotNull(result.getWord());
	assertTrue(result.getWord().length() >= minLength);

	minLength = 5;
	includePartOfSpeech = EnumSet.of(Knicker.PartOfSpeech.noun_and_adjective);
	result = WordsApi.randomWord(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength);
	assertNotNull(result);
	assertNotNull(result.getWord());
	assertTrue(result.getWord().length() >= minLength);


	hasDictionaryDef = false;
	result = WordsApi.randomWord(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength);
	assertNotNull(result);
	assertNotNull(result.getWord());
	assertTrue(result.getWord().length() >= minLength);

	includePartOfSpeech = null;
	maxLength = 5;
	result = WordsApi.randomWord(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength);
	assertNotNull(result);
	assertNotNull(result.getWord());
	assertEquals(minLength, result.getWord().length());
    }


    /**
     * Test of randomWords method, of class WordsApi.
     */
    @Test
    public void testRandomWords_0args() throws Exception {
	System.out.println("randomWords");
	List<Word> result = WordsApi.randomWords();

	assertNotNull(result);
	assertEquals(10, result.size());
    }


    /**
     * Test of randomWords method, of class WordsApi.
     */
    @Test
    public void testRandomWords_12args() throws Exception {
	System.out.println("randomWords");
	boolean hasDictionaryDef = false;
	EnumSet<PartOfSpeech> includePartOfSpeech = null;
	EnumSet<PartOfSpeech> excludePartOfSpeech = null;
	int minCorpusCount = 0;
	int maxCorpusCount = 0;
	int minDictionaryCount = 0;
	int maxDictionaryCount = 0;
	int minLength = 0;
	int maxLength = 0;
	SortBy sortBy = null;
	SortDirection sortDirection = null;
	int limit = 5;


	List<Word> result = WordsApi.randomWords(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength, sortBy, sortDirection, limit);

	assertNotNull(result);
	assertEquals(5, result.size());


	minLength = 10;
	maxLength = 10;
	result = WordsApi.randomWords(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength, sortBy, sortDirection, limit);

	assertNotNull(result);
	assertEquals(5, result.size());
	for (Word w : result) {
	    assertEquals(minLength, w.getWord().length());
	}
    }


    /**
     * Test of wordOfTheDay method, of class WordsApi.
     */
    @Test
    public void testWordOfTheDay() throws Exception {
	System.out.println("wordOfTheDay");
	WordOfTheDay result = WordsApi.wordOfTheDay();
	assertNotNull(result);
	assertTrue(result.getDefinitions().size() > 0);
	assertTrue(result.getExamples().size() > 0);
    }


    /**
     * Test of search method, of class WordsApi.
     */
    @Test
    public void testSearch_String() throws Exception {
	System.out.println("search");
	String query = "computer";
	SearchResults result = WordsApi.search(query);
	assertNotNull(result);
	assertTrue(result.getSearchResults().size() > 0);
	assertTrue(result.getTotal() > 0);
    }


    /**
     * Test of search method, of class WordsApi.
     */
    @Test
    public void testSearch_12args() throws Exception {
	System.out.println("search");
	String query = "computer";
	boolean caseSensitive = true;
	EnumSet<PartOfSpeech> includePartOfSpeech = null;
	EnumSet<PartOfSpeech> excludePartOfSpeech = null;
	int minCorpusCount = 0;
	int maxCorpusCount = 0;
	int minDictionaryCount = 0;
	int maxDictionaryCount = 0;
	int minLength = 0;
	int maxLength = 0;
	int skip = 0;
	int limit = 0;
	SearchResults result = WordsApi.search(query, caseSensitive, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength, skip, limit);
	assertNotNull(result);
	assertTrue(result.getSearchResults().size() > 0);
	assertTrue(result.getTotal() > 0);

	caseSensitive = false;
	limit = 3;
	result = WordsApi.search(query, caseSensitive, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength, skip, limit);
	assertNotNull(result);
	assertEquals(3, result.getSearchResults().size());
    }

}