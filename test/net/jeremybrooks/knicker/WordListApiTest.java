/*
 * Knicker is Copyright 2010-2011 by Jeremy Brooks
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

import java.util.List;
import javax.swing.SortOrder;
import net.jeremybrooks.knicker.Knicker.SortBy;
import net.jeremybrooks.knicker.logger.StdoutLogger;
import net.jeremybrooks.knicker.logger.KnickerLogger;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import net.jeremybrooks.knicker.Knicker.ListType;
import net.jeremybrooks.knicker.dto.AuthenticationToken;
import net.jeremybrooks.knicker.dto.WordList;
import net.jeremybrooks.knicker.dto.WordListWord;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jeremyb
 */
public class WordListApiTest {

    public WordListApiTest() {
    }

    static String username;
    static String password;
    static AuthenticationToken token;

    private String testListName = "TEST_LIST";
    private String testListDescription = "This is a test list created by the Knicker automated tests.";

    private static WordList testList;
    private static String testWord1 = "computer";
    private static String testWord2 = "zebra";
    private static List<String> testWordList;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
	Properties p = new Properties();
	InputStream in = WordApiTest.class.getResourceAsStream("/net/jeremybrooks/knicker/secret.properties");
	p.load(in);

	System.setProperty("WORDNIK_API_KEY", p.getProperty("WORDNIK_API_KEY"));
	username = p.getProperty("WORDNIK_USERNAME");
	password = p.getProperty("WORDNIK_PASSWORD");

	token = AccountApi.authenticate(username, password);

	testWordList = new ArrayList<String>();
	testWordList.add(testWord1);
	testWordList.add(testWord2);
	
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
     * Test of createList method, of class WordListApi.
     */
    @Test
    public void testCreateList() throws Exception {
	System.out.println("createList");
	ListType type = ListType.PUBLIC;
	WordList result = WordListApi.createList(token, testListName, testListDescription, type);

	assertNotNull(result);
	assertNotNull(result.getId());
	assertEquals(username, result.getUsername());
	assertEquals(token.getUserId(), result.getUserId());
	assertEquals(testListName, result.getName());
	assertEquals(testListDescription, result.getDescription());

	testList = result;
    }


    /**
     * Test of getWordList method, of class WordListApi.
     */
    @Test
    public void testGetWordList() throws Exception {
	System.out.println("getWordList");
	String permalink = testList.getPermalink();
	WordList expResult = null;
	WordList result = WordListApi.getWordList(token, permalink);

	assertNotNull(result);
	assertEquals(username, result.getUsername());
	assertEquals(token.getUserId(), result.getUserId());
	assertEquals(testListName, result.getName());
	assertEquals(testListDescription, result.getDescription());
    }


    /**
     * Test adding one word.
     * This should be followed by updating one word and deleting one word.
     * @throws Exception
     */
    @Test
    public void testAddWordToList() throws Exception {
	System.out.println("addWordToList");
	String permalink = testList.getPermalink();
	WordListApi.addWordToList(token, permalink, "test");
    }


    /**
     * Test of deleteWordFromList method, of class WordListApi.
     */
    @Test
    public void testDeleteWordFromList() throws Exception {
	System.out.println("deleteWordFromList");

	String permalink = testList.getPermalink();
	String word = "test";
	WordListApi.deleteWordFromList(token, permalink, word);

	// check that the list is now empty
	List<WordListWord> result = WordListApi.getWordsFromList(token, permalink);
	assertEquals(result.size(), 0);
    }


    /**
     * Test of addWordToList method, of class WordListApi.
     */
    @Test
    public void testAddWordsToList() throws Exception {
	System.out.println("addWordsToList");
	String permalink = testList.getPermalink();

	WordListApi.addWordsToList(token, permalink, testWordList);	
    }

    
    


    /**
     * Test of getWordsFromList method, of class WordListApi.
     */
    @Test
    public void testGetWordsFromList_AuthenticationToken_String() throws Exception {
	System.out.println("getWordsFromList");
	String permalink = testList.getPermalink();
	List<WordListWord> result = WordListApi.getWordsFromList(token, permalink);

	assertNotNull(result);
	for (WordListWord word : result) {
	    assertEquals(word.getUsername(), username);
	}
    }

    /**
     * Test of getWordsFromList method, of class WordListApi.
     */
    @Test
    public void testGetWordsFromList_6args() throws Exception {
	System.out.println("testGetWordsFromList_6args");
	String permalink = testList.getPermalink();
	SortBy sortBy = null;
	SortOrder sortOrder = null;
	int skip = 0;
	int limit = 0;
	List<WordListWord> result = WordListApi.getWordsFromList(token, permalink, sortBy, sortOrder, skip, limit);

	assertNotNull(result);
	for (WordListWord word : result) {
	    assertEquals(word.getUsername(), username);
	}

	sortOrder = SortOrder.ASCENDING;
	sortBy = SortBy.alpha;
	result = WordListApi.getWordsFromList(token, permalink, sortBy, sortOrder, skip, limit);
	assertNotNull(result);
	assertEquals(result.get(0).getWord(), testWord1);
	assertEquals(result.get(1).getWord(), testWord2);

	sortOrder = SortOrder.DESCENDING;
	result = WordListApi.getWordsFromList(token, permalink, sortBy, sortOrder, skip, limit);
	assertNotNull(result);
	assertEquals(result.get(0).getWord(), testWord2);
	assertEquals(result.get(1).getWord(), testWord1);

	limit = 1;
	result = WordListApi.getWordsFromList(token, permalink, sortBy, sortOrder, skip, limit);
	assertNotNull(result);
	assertEquals(result.get(0).getWord(), testWord2);
    }
    

    /**
     * Test of deleteWordsFromList method, of class WordListApi.
     */
    @Test
    public void testDeleteWordsFromList() throws Exception {
	System.out.println("deleteWordsFromList");

	String permalink = testList.getPermalink();
	WordListApi.deleteWordsFromList(token, permalink, testWordList);

	// check that the list is now empty
	List<WordListWord> result = WordListApi.getWordsFromList(token, permalink);
	assertEquals(result.size(), 0);
    }
    

    /**
     * Test of updateWordList method, of class WordListApi.
     */
    @Test
    public void testUpdateWordList() throws Exception {
	System.out.println("updateWordList");

	String newDescription = "Changed the description.";
	String newName = "TEST LIST NEW NAME";
	
	testList.setDescription(newDescription);
	testList.setName(newName);
	testList.setType(ListType.PRIVATE);
	WordListApi.updateWordList(token, testList);

	// check that the list has 2 items
	WordList result = WordListApi.getWordList(token, testList.getPermalink());
	assertEquals(result.getDescription(), newDescription);
	assertEquals(result.getName(), newName);
	assertEquals(result.getType(), ListType.PRIVATE);
	assertEquals(result.getId(), testList.getId());
	assertEquals(result.getNumberWordsInList(), testList.getNumberWordsInList());
	assertEquals(result.getPermalink(), testList.getPermalink());
	assertEquals(result.getUserId(), testList.getUserId());
	assertEquals(result.getUsername(), testList.getUsername());
	assertFalse(result.getUpdatedAt().equals(testList.getUpdatedAt()));
    }


    


    

    /**
     * Test of deleteList method, of class WordListApi.
     */
    @Test
    public void testDeleteList() throws Exception {
	System.out.println("deleteList");
	String permalink = testList.getPermalink();

	WordListApi.deleteList(token, permalink);
    }


    


}