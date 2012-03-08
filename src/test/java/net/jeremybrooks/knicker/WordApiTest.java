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
import net.jeremybrooks.knicker.Knicker.RelationshipType;
import net.jeremybrooks.knicker.Knicker.SourceDictionary;
import net.jeremybrooks.knicker.Knicker.TypeFormat;
import net.jeremybrooks.knicker.dto.AudioFileMetadata;
import net.jeremybrooks.knicker.dto.AuthenticationToken;
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
public class WordApiTest {

    public WordApiTest() {
    }

    static String username;
    static String password;
    static AuthenticationToken token;

    @BeforeClass
    public static void setUpClass() throws Exception {
	Properties p = new Properties();
	InputStream in = WordApiTest.class.getResourceAsStream("/secret.properties");
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
     * Test of lookup method, of class WordApi.
     */
    @Test
    public void testLookup_String() throws Exception {
	System.out.println("lookup");
	String word = "cats";
	Word expResult = null;
	Word result = WordApi.lookup(word);
	assertNotNull(result);
	assertEquals(result.getWord(), word);
    }


    /**
     * Test of lookup method, of class WordApi.
     */
    @Test
    public void testLookup_3args() throws Exception {
	System.out.println("lookup");
	String word = "cats";
	boolean useCanonical = true;
	boolean includeSuggestions = true;
	Word expResult = null;
	Word result = WordApi.lookup(word, useCanonical, includeSuggestions);
	assertNotNull(result);
	assertEquals(result.getWord(), "cat");

	useCanonical = false;
	result = WordApi.lookup(word, useCanonical, includeSuggestions);
	assertNotNull(result);
	assertEquals(result.getWord(), word);
	assertTrue(result.getSuggestions().size() > 0);
    }


    /**
     * Test of examples method, of class WordApi.
     */
    @Test
    public void testExamples_String() throws Exception {
	System.out.println("examples");
	String word = "computer";
	
	SearchResults results = WordApi.examples(word);
	assertNotNull(results);
	assertEquals(results.getSearchResults().size(), 0);
	for (Example e : results.getExamples()) {
	    assertEquals(e.getWord(), word);
	}
    }


    /**
     * Test of examples method, of class WordApi.
     */
    @Test
    public void testExamples_6args() throws Exception {
	System.out.println("examples");
	String word = "computer";
	boolean includeDuplicates = false;
	String contentProvider = null;
	boolean useCanonical = true;
	int skip = 0;
	int limit = 0;

	SearchResults results = WordApi.examples(word, includeDuplicates, contentProvider, useCanonical, skip, limit);
	assertNotNull(results);
	assertEquals(results.getSearchResults().size(), 0);
	for (Example e : results.getExamples()) {
	    assertEquals(e.getWord(), word);
	}

	limit = 1;
	results = WordApi.examples(word, includeDuplicates, contentProvider, useCanonical, skip, limit);
	assertNotNull(results);
	assertEquals(results.getSearchResults().size(), 0);
	for (Example e : results.getExamples()) {
	    assertEquals(e.getWord(), word);
	}
	assertEquals(results.getExamples().size(), limit);
    }


    /**
     * Test of definitions method, of class WordApi.
     */
    @Test
    public void testDefinitions() throws Exception {
	System.out.println("definitions");
	String word = "cat";
	List<Definition> result = WordApi.definitions(word);
	assertNotNull(result);
	assertTrue(result.size() > 0);
    }


    /**
     * Test of definitions method, of class WordApi.
     */
    @Test
    public void testDefinitions_String_EnumSet() throws Exception {
	System.out.println("definitions");
	String word = "cat";
	EnumSet<SourceDictionary> sourceDictionaries = EnumSet.of(WordApi.SourceDictionary.ahd, WordApi.SourceDictionary.century);
	List expResult = null;
	List<Definition> result = WordApi.definitions(word, sourceDictionaries);
	assertNotNull(result);
	assertTrue(result.size() > 0);
	for (Definition d : result) {
        assertNotNull(d.getAttributionText());
	    System.out.println(d.toString());
	}
    }


    /**
     * Test of definitions method, of class WordApi.
     */
    @Test
    public void testDefinitions_7args() throws Exception {
	System.out.println("definitions");
	String word = "cat";
	int limit = 5;
	EnumSet<PartOfSpeech> partOfSpeech = null;
	boolean includeRelated = false;
	EnumSet<SourceDictionary> sourceDictionaries = null;
	boolean useCanonical = false;
	boolean includeTags = false;
	List<Definition> result = WordApi.definitions(word, limit, partOfSpeech, includeRelated, sourceDictionaries, useCanonical, includeTags);
	assertNotNull(result);
	assertTrue(result.size() == 5);
    }


    /**
     * Test of frequency method, of class WordApi.
     */
    @Test
    public void testFrequency_String() throws Exception {
	System.out.println("frequency");
	String word = "cat";

	FrequencySummary result = WordApi.frequency(word);

	assertNotNull(result);
	assertEquals(word, result.getWord());
	assertNotNull(result.getFrequencies());
	assertTrue(result.getFrequencies().size() > 0);
    }


    /**
     * Test of frequency method, of class WordApi.
     */
    @Test
    public void testFrequency_String_boolean() throws Exception {
	System.out.println("frequency");
	String word = "cat";
	boolean useCanonical = true;

	FrequencySummary result = WordApi.frequency(word, useCanonical);
	assertNotNull(result);
	assertEquals(word, result.getWord());
	assertNotNull(result.getFrequencies());
	assertTrue(result.getFrequencies().size() > 0);
    }


    /**
     * Test of frequency method, of class WordApi.
     */
    @Test
    public void testFrequency_4args_startOnly() throws Exception {
	System.out.println("frequency");
	String word = "cat";
	boolean useCanonical = false;
	int startYear = 1990;
	int endYear = 0;

	FrequencySummary result = WordApi.frequency(word, useCanonical, startYear, endYear);
	assertNotNull(result);
	assertEquals(word, result.getWord());
	assertNotNull(result.getFrequencies());
	assertTrue(result.getFrequencies().size() > 0);
    }

    /**
     * Test of frequency method, of class WordApi.
     */
    @Test
    public void testFrequency_4args_endOnly() throws Exception {
	System.out.println("frequency");
	String word = "cat";
	boolean useCanonical = false;
	int startYear = 0;
	int endYear = 2000;

	FrequencySummary result = WordApi.frequency(word, useCanonical, startYear, endYear);
	assertNotNull(result);
	assertEquals(word, result.getWord());
	assertNotNull(result.getFrequencies());
	assertTrue(result.getFrequencies().size() > 0);
    }


    /**
     * Test of frequency method, of class WordApi.
     */
    @Test
    public void testFrequency_4args() throws Exception {
	System.out.println("frequency");
	String word = "cat";
	boolean useCanonical = false;
	int startYear = 1990;
	int endYear = 2000;

	FrequencySummary result = WordApi.frequency(word, useCanonical, startYear, endYear);
	assertNotNull(result);
	assertEquals(word, result.getWord());
	assertNotNull(result.getFrequencies());
	assertTrue(result.getFrequencies().size() > 0);
    }



    /**
     * Test of related method, of class WordApi.
     */
    @Test
    public void testRelated_String() throws Exception {
	System.out.println("related");
	String word = "cat";
	List<Related> result = WordApi.related(word);

	assertNotNull(result);
	assertTrue(result.get(0).getWords().size() > 0);
    }


    /**
     * Test of related method, of class WordApi.
     */
    @Test
    public void testRelated_6args() throws Exception {
	System.out.println("related");
	String word = "cat";
	int limit = 2;
	boolean useCanonical = false;
	EnumSet<PartOfSpeech> partOfSpeech = null;
	EnumSet<RelationshipType> relationshipType = null;
	SourceDictionary sourceDictionary = null;

	List<Related> result = WordApi.related(word, limit, useCanonical, partOfSpeech, relationshipType, sourceDictionary);

	assertNotNull(result);
	assertEquals(result.get(0).getWords().size(), 2);
    }


    /**
     * Test of phrases method, of class WordApi.
     */
    @Test
    public void testPhrases_String() throws Exception {
	System.out.println("phrases");
	String word = "cat";

	List<Phrase> result = WordApi.phrases(word);

	assertNotNull(result);
	assertTrue(result.size() > 0);
    }


    /**
     * Test of phrases method, of class WordApi.
     */
    @Test
    public void testPhrases_4args() throws Exception {
	System.out.println("phrases");
	String word = "cat";
	int limit = 5;
	String wlmi = "";
	boolean useCanonical = false;

	List<Phrase> result = WordApi.phrases(word);

	assertNotNull(result);
	assertTrue(result.size() > 0);
	assertEquals(result.size(), 5);
    }


    /**
     * Test of hyphenation method, of class WordApi.
     */
    @Test
    public void testHyphenation_String() throws Exception {
	System.out.println("hyphenation");
	String word = "computer";
	List<Syllable> result = WordApi.hyphenation(word);

	assertNotNull(result);
	assertEquals(result.size(), 3);
    }


    /**
     * Test of hyphenation method, of class WordApi.
     */
    @Test
    public void testHyphenation_4args() throws Exception {
	System.out.println("hyphenation");
	String word = "computer";
	boolean useCanonical = false;
	SourceDictionary sourceDictionary = null;
	int limit = 0;

	List<Syllable> result = WordApi.hyphenation(word, useCanonical, sourceDictionary, limit);

	assertNotNull(result);
	assertEquals(result.size(), 3);
    }


    /**
     * Test of pronunciations method, of class WordApi.
     */
    @Test
    public void testPronunciations_String() throws Exception {
	System.out.println("pronunciations");
	String word = "a";

	List<Pronunciation> result = WordApi.pronunciations(word);

	assertNotNull(result);
    }


    /**
     * Test of pronunciations method, of class WordApi.
     */
    @Test
    public void testPronunciations_5args() throws Exception {
	System.out.println("pronunciations");
	String word = "a";
	boolean useCanonical = false;

	// NOTE: This dictionary seems to return several results,
	//       where others may return nothing. This may change
	//       in the future.
	SourceDictionary sourceDictionary = SourceDictionary.cmu;
	TypeFormat typeFormat = null;
	int limit = 1;

	List<Pronunciation> result = WordApi.pronunciations(word, useCanonical, sourceDictionary, typeFormat, limit);

	assertNotNull(result);
	System.out.println(result.toString());
	assertEquals(1, result.size());

	sourceDictionary = null;
	typeFormat = TypeFormat.gcide_diacritical;
	result = WordApi.pronunciations(word, useCanonical, sourceDictionary, typeFormat, limit);

	assertNotNull(result);
	System.out.println(result.toString());
	assertEquals(1, result.size());
    }


    /**
     * Test of audio method, of class WordApi.
     */
    @Test
    public void testAudio_String() throws Exception {
	System.out.println("audio");
	String word = "a";

	List<AudioFileMetadata> result = WordApi.audio(word);

	assertNotNull(result);
    }


    /**
     * Test of audio method, of class WordApi.
     */
    @Test
    public void testAudio_3args() throws Exception {
	System.out.println("audio");
	String word = "a";
	boolean useCanonical = false;
	int limit = 1;

	List<AudioFileMetadata> result = WordApi.audio(word, useCanonical, limit);

	assertNotNull(result);
	assertEquals(result.size(), 1);
    }


    /**
     * Test of getAudioData method, of class WordApi.
     */
    @Test
    public void testGetAudioData() throws Exception {
	System.out.println("getAudioData");

	List<AudioFileMetadata> audio = WordApi.audio("a");
	assertNotNull(audio);
	assertTrue(audio.size() >= 1);

	byte[] result = WordApi.getAudioData(audio.get(0));
	
	assertNotNull(result);
	assertTrue(result.length > 0);
	System.out.println("Got " + result.length + " bytes of data.");

	audio.get(0).setFileUrl("http://api.wordnik.com/v4/audioFile.mp3/d7e9fd4ec39829cb61169850a4e8a9e3a8b15d7b730afbeac7c77ee558f73bfc");
	try {
	    result = WordApi.getAudioData(audio.get(0));
	} catch (Exception e) {
	    assertNotNull(e);
	    System.out.println("This is testing a failure. You should see the headers in the excemption.");
	    System.out.println(e);
	}
    }


    /**
     * Test of topExample method, of class WordApi.
     */
    @Test
    public void testTopExample_String() throws Exception {
	System.out.println("topExample");
	String word = "cat";
	Example result = WordApi.topExample(word);
	assertNotNull(result);
	assertEquals(result.getWord(), word);
	assertNotNull(result.getProvider());
    }


    /**
     * Test of topExample method, of class WordApi.
     */
    @Test
    public void testTopExample_3args() throws Exception {
	System.out.println("topExample");
	String word = "cat";
	String contentProvider = null;
	boolean useCanonical = false;
	Example result = WordApi.topExample(word, contentProvider, useCanonical);
	assertNotNull(result);
	assertEquals(result.getWord(), word);
	assertNotNull(result.getProvider());

	word = "cats";
	useCanonical = true;
	result = WordApi.topExample(word, contentProvider, useCanonical);
	assertNotNull(result);
	assertEquals(result.getWord(), "cat");
	assertNotNull(result.getProvider());
    }

}