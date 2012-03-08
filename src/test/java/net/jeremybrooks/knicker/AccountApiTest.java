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

import net.jeremybrooks.knicker.dto.WordList;
import java.io.InputStream;
import java.util.Properties;
import java.util.List;
import net.jeremybrooks.knicker.dto.AuthenticationToken;
import net.jeremybrooks.knicker.dto.TokenStatus;
import net.jeremybrooks.knicker.dto.User;
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
public class AccountApiTest {

    public AccountApiTest() {
    }

    static String username;
    static String password;
    static AuthenticationToken token;

    @BeforeClass
    public static void setUpClass() throws Exception {
	Properties p = new Properties();
	InputStream in = AccountApiTest.class.getResourceAsStream("/net/jeremybrooks/knicker/secret.properties");
	p.load(in);

	System.setProperty("WORDNIK_API_KEY", p.getProperty("WORDNIK_API_KEY"));
	username = p.getProperty("WORDNIK_USERNAME");
	password = p.getProperty("WORDNIK_PASSWORD");

	//KnickerLogger.setLogger(new StdoutLogger());
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
     * Test of authenticate method, of class Knicker.
     */
    @Test
    public void testAuthenticate() throws Exception {
	System.out.println("authenticate");
	AuthenticationToken result = AccountApi.authenticate(username, password);
	assertNotNull(result);

	token = result;
    }


    /**
     * Test of apiTokenStatus method, of class Knicker.
     */
    @Test
    public void testApiTokenStatus() throws Exception {
	System.out.println("apiTokenStatus");
	TokenStatus result = AccountApi.apiTokenStatus();
	assertNotNull(result);
	assertEquals(result.isValid(), true);
    }


    /**
     * Test of user method, of class Knicker.
     */
    @Test
    public void testUser() throws Exception {
	System.out.println("user");
	User result = AccountApi.user(token);
	assertNotNull(result);
	assertEquals(result.getUserName(), username);
    }


    /**
     * Test of wordLists method, of class Knicker.
     */
    @Test
    public void testWordLists_AuthenticationToken() throws Exception {
	System.out.println("wordLists");
	List<WordList> result = AccountApi.wordLists(token);
	assertNotNull(result);
	for (WordList list : result) {
	    assertEquals(username, list.getUsername());
	}
    }


    /**
     * Test of wordLists method, of class Knicker.
     */
    @Test
    public void testWordLists_3args() throws Exception {
	System.out.println("wordLists");
	int skip = 0;
	int limit = 1;
	List<WordList> result = AccountApi.wordLists(token, skip, limit);
	assertNotNull(result);
	assertEquals(result.size(), limit);
    }

}