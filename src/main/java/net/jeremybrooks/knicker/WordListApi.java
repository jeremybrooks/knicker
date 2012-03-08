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
import net.jeremybrooks.knicker.dto.WordList;
import net.jeremybrooks.knicker.dto.WordListWord;

import javax.swing.SortOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is the class that should be used by programs that want to access the
 * Wordnik WordList API.
 * <p/>
 * NOTE: This class also includes access to the wordLists API.
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
public class WordListApi extends Knicker {


    /**
     * Create a new list on behalf of the authenticated user.
     * <p/>
     * This method requires a valid authentication token, which can be obtained
     * by calling the authenticate method.
     *
     * @param token       authentication token.
     * @param listName    the name of the list to be created.
     * @param description a description of the list to be created.
     * @param type        the type of list to be created.
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

        return DTOBuilder.buildWordList(Util.doPost(WORD_LISTS_ENDPOINT, data.toString(), token));
    }


    /**
     * Get a word list by permalink id.
     *
     * @param token     the authentication token.
     * @param permalink identifies the word list to retrieve.
     * @return word list for the id.
     * @throws KnickerException if any parameters are null, or if there are any errors.
     */
    public static WordList getWordList(AuthenticationToken token, String permalink) throws KnickerException {
        if (token == null) {
            throw new KnickerException("Authentication token required.");
        }
        if (permalink == null || permalink.isEmpty()) {
            throw new KnickerException("List permalink required.");
        }

        StringBuilder uri = new StringBuilder(WORD_LIST_ENDPOINT);
        uri.append('/').append(permalink.trim());

        return DTOBuilder.buildWordList(Util.doGet(uri.toString(), token));
    }


    /**
     * Add a word to a list.
     *
     * @param token     authentication token.
     * @param permalink permalink id of the list to add the word to.
     * @param word      the word to add.
     * @throws KnickerException if any parameters are null, or if there are any errors.
     */
    public static void addWordToList(AuthenticationToken token, String permalink, String word)
            throws KnickerException {
        List<String> list = new ArrayList<String>();
        list.add(word);
        addWordsToList(token, permalink, list);
    }


    /**
     * Add words to the given list, on behalf of the authenticated user.
     *
     * @param token     authentication token.
     * @param permalink the permalink id of the list to add the word to.
     * @param words     the words to add to the given list.
     * @throws KnickerException if any parameters are null, or if there are any errors.
     */
    public static void addWordsToList(AuthenticationToken token, String permalink, List<String> words) throws KnickerException {
        if (token == null) {
            throw new KnickerException("Authentication token required.");
        }
        if (permalink == null || permalink.isEmpty()) {
            throw new KnickerException("Parameter permalinkId required.");
        }
        if (words == null || words.isEmpty()) {
            throw new KnickerException("Parameter words required.");
        }

        /*
      <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <stringValues>
          <stringValue>
          <word>hello</word>
          </stringValue>
         <stringValue>
          <word>hi</word>
          </stringValue>
      </stringValues>
       */
        StringBuilder data = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        data.append("<stringValues>\n");
        for (String word : words) {
            data.append("<stringValue>\n");
            data.append("<word>").append(word).append("</word>\n");
            data.append("</stringValue>\n");
        }
        data.append("</stringValues>\n");

        StringBuilder uri = new StringBuilder(WORD_LIST_ENDPOINT);
        uri.append("/").append(permalink).append("/words");

        Util.doPost(uri.toString(), data.toString(), token);
    }


    /**
     * Delete a word from a list.
     *
     * @param token     authentication token.
     * @param permalink permalink id of the list to delete the word from.
     * @param word      the word to delete.
     * @throws KnickerException if any parameters are null, or if there are any errors.
     */
    public static void deleteWordFromList(AuthenticationToken token, String permalink, String word)
            throws KnickerException {
        List<String> list = new ArrayList<String>();
        list.add(word);
        deleteWordsFromList(token, permalink, list);
    }


    /**
     * Add words to the given list, on behalf of the authenticated user.
     *
     * @param token     authentication token.
     * @param permalink the permalink id of the list to delete the words from.
     * @param words     the words to delete from the given list.
     * @throws KnickerException if any parameters are null, or if there are any errors.
     */
    public static void deleteWordsFromList(AuthenticationToken token, String permalink, List<String> words) throws KnickerException {
        if (token == null) {
            throw new KnickerException("Authentication token required.");
        }
        if (permalink == null || permalink.isEmpty()) {
            throw new KnickerException("Parameter permalinkId required.");
        }
        if (words == null || words.isEmpty()) {
            throw new KnickerException("Parameter words required.");
        }

        /*
      <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <stringValues>
          <stringValue>
          <word>hello</word>
          </stringValue>
         <stringValue>
          <word>hi</word>
          </stringValue>
      </stringValues>
       */
        StringBuilder data = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        data.append("<stringValues>\n");
        for (String word : words) {
            data.append("<stringValue>\n");
            data.append("<word>").append(word).append("</word>\n");
            data.append("</stringValue>\n");
        }
        data.append("</stringValues>\n");

        StringBuilder uri = new StringBuilder(WORD_LIST_ENDPOINT);
        uri.append("/").append(permalink).append("/deleteWords");

        Util.doPost(uri.toString(), data.toString(), token);
    }

    /**
     * Update the word list metadata.
     *
     * @param token    authentication token.
     * @param wordList object representing the wordList to be updated.
     * @throws KnickerException if any parameters are null, or if there are any errors.
     */
    public static void updateWordList(AuthenticationToken token, WordList wordList) throws KnickerException {
        if (token == null) {
            throw new KnickerException("Authentication token required.");
        }
        if (wordList == null) {
            throw new KnickerException("Parameter wordList required.");
        }

        /*
       * The PUT command includes this XML document:
      <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <wordList>
          <createdAt>2011-03-23T20:51:03.753Z</createdAt>
          <description>This is a test list created by the Knicker automated tests.</description>
          <id>29774</id>
          <name>TEST_LIST</name>
          <numberWordsInList>0</numberWordsInList>
          <permalink>test-list--2</permalink>
          <type>PUBLIC</type>
          <updatedAt>2011-03-23T20:51:03.753Z</updatedAt>
          <userId>1055256</userId>
          <username>jeremybrooks</username>
      </wordList>
       */
        StringBuilder data = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        data.append("<wordList>");
        data.append("<createdAt>").append(wordList.getCreatedAt()).append("</createdAt>");
        data.append("<description>").append(wordList.getDescription()).append("</description>");
        data.append("<id>").append(wordList.getId()).append("</id>");
        data.append("<name>").append(wordList.getName()).append("</name>");
        data.append("<numberWordsInList>").append(wordList.getNumberWordsInList()).append("</numberWordsInList>");
        data.append("<permalink>").append(wordList.getPermalink()).append("</permalink>");
        data.append("<type>").append(wordList.getType().toString()).append("</type>");
        data.append("<updatedAt>").append(wordList.getUpdatedAt()).append("</updatedAt>");
        data.append("<userId>").append(wordList.getUserId()).append("</userId>");
        data.append("<username>").append(wordList.getUsername()).append("</username>");
        data.append("</wordList>");

        StringBuilder uri = new StringBuilder(WORD_LIST_ENDPOINT);
        uri.append("/").append(wordList.getPermalink());

        Util.doPut(uri.toString(), data.toString(), token);
    }


    /**
     * Return all the words from the given list.
     * <p/>
     * This method requires a valid authentication token, which can be obtained
     * by calling the authenticate method.
     * <p/>
     * This is the same as calling <code>getWordsFromList(token, permalink, null, null, 0, 0)</code>.
     *
     * @param token     authentication token.
     * @param permalink the permalink of the word list to get words from.
     * @return list of word list words.
     * @throws KnickerException if the token or permalinkId are null, or if there are any errors.
     */
    public static List<WordListWord> getWordsFromList(AuthenticationToken token, String permalink) throws KnickerException {
        return getWordsFromList(token, permalink, null, null, 0, 0);
    }


    /**
     * Return all the words from the given list.
     * <p/>
     * This method requires a valid authentication token, which can be obtained
     * by calling the authenticate method.
     *
     * @param token     authentication token.
     * @param permalink the permalink of the word list to get words from.
     * @param sortBy    field to sort by.
     * @param sortOrder direction to sort.
     * @param skip      results to skip.
     * @param limit     maximum number of results to return.
     * @return list of word list words.
     * @throws KnickerException if the token or permalinkId are null, or if there are any errors.
     */
    public static List<WordListWord> getWordsFromList(AuthenticationToken token,
                                                      String permalink, SortBy sortBy, SortOrder sortOrder, int skip, int limit) throws KnickerException {
        if (token == null) {
            throw new KnickerException("Authentication token required.");
        }
        if (permalink == null || permalink.isEmpty()) {
            throw new KnickerException("Parameter permalink required.");
        }

        Map<String, String> params = new HashMap<String, String>();
        if (sortBy != null) {
            params.put("sortBy", sortBy.toString());
        }
        if (sortOrder != null) {
            params.put("sortOrder", sortOrder.toString());
        }
        if (skip > 0) {
            params.put("skip", Integer.toString(skip));
        }
        if (limit > 0) {
            params.put("limit", Integer.toString(limit));
        }

        StringBuilder uri = new StringBuilder(WORD_LIST_ENDPOINT);
        uri.append('/').append(permalink.trim());
        uri.append("/words");
        if (params.size() > 0) {
            uri.append('?').append(Util.buildParamList(params));
        }

        return DTOBuilder.buildWordListWords(Util.doGet(uri.toString(), token));
    }


    /**
     * Delete the given word list.
     * <p/>
     * This method requires a valid authentication token, which can be obtained
     * by calling the authenticate method.
     *
     * @param token authentication token.
     * @param permalink    the permalinkId of the word list to delete.
     * @throws KnickerException if the token or id are null, or if there are any errors.
     */
    public static void deleteList(AuthenticationToken token, String permalink) throws KnickerException {
        if (token == null) {
            throw new KnickerException("Authentication token required.");
        }
        if (permalink == null || permalink.isEmpty()) {
            throw new KnickerException("Parameter id required.");
        }

        StringBuilder uri = new StringBuilder(WORD_LIST_ENDPOINT);
        uri.append("/").append(permalink);

        Util.doDelete(uri.toString(), token);
    }
}
