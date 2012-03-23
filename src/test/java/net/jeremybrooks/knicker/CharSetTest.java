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
import net.jeremybrooks.knicker.dto.Definition;
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
 * Test for special characters.
 *
 * @author Jeremy Brooks
 */
public class CharSetTest {


    public CharSetTest() {
    }

    static String username;
    static String password;
    static AuthenticationToken token;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Properties p = new Properties();
        InputStream in = AccountApiTest.class.getResourceAsStream("/secret.properties");
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
     * This tests that special characters come back as expected. The attribution text from the
     * American Heritage Dictionary is used, since it contains the ® character.
     */
    @Test
    public void testCharset() throws Exception {
        System.out.println("definitions");
        String word = "wonderful";
        EnumSet<Knicker.SourceDictionary> sourceDictionaries = EnumSet.of(WordApi.SourceDictionary.ahd);
        List expResult = null;
        List<Definition> result = WordApi.definitions(word, sourceDictionaries);
        assertNotNull(result);
        assertTrue(result.size() > 0);
        for (Definition d : result) {
            assertNotNull(d.getAttributionText());
            assertEquals("from The American Heritage® Dictionary of the English Language, 4th Edition", d.getAttributionText());
            KnickerLogger.getLogger().log(d.toString());
        }

    }

}
