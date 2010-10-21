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
package net.jeremybrooks.knicker.dto;

/**
 * Represents words inside a word list.
 *
 * @see http://docs.wordnik.com/api/methods#lists
 * @author jeremyb
 */
public class WordListWord {


    private String createdAt;
    private int numberCommentsOnWord;
    private int numberLists;
    private String userId;
    private String userName;
    private String wordstring;


    /**
     * @return the createdAt
     */
    public String getCreatedAt() {
	return createdAt;
    }


    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(String createdAt) {
	this.createdAt = createdAt;
    }


    /**
     * @return the numberCommentsOnWord
     */
    public int getNumberCommentsOnWord() {
	return numberCommentsOnWord;
    }


    /**
     * @param numberCommentsOnWord the numberCommentsOnWord to set
     */
    public void setNumberCommentsOnWord(int numberCommentsOnWord) {
	this.numberCommentsOnWord = numberCommentsOnWord;
    }


    /**
     * @return the numberLists
     */
    public int getNumberLists() {
	return numberLists;
    }


    /**
     * @param numberLists the numberLists to set
     */
    public void setNumberLists(int numberLists) {
	this.numberLists = numberLists;
    }


    /**
     * @return the userId
     */
    public String getUserId() {
	return userId;
    }


    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
	this.userId = userId;
    }


    /**
     * @return the userName
     */
    public String getUserName() {
	return userName;
    }


    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
	this.userName = userName;
    }


    /**
     * @return the wordstring
     */
    public String getWordstring() {
	return wordstring;
    }


    /**
     * @param wordstring the wordstring to set
     */
    public void setWordstring(String wordstring) {
	this.wordstring = wordstring;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(this.getClass().getName());
	sb.append(": [ ");
	sb.append("createdAt=").append(this.createdAt).append("  | ");
	sb.append("numberCommentsOnWord=").append(this.numberCommentsOnWord).append("  | ");
	sb.append("numberLists=").append(this.numberLists).append("  | ");
	sb.append("userId=").append(this.userId).append("  | ");
	sb.append("userName=").append(this.userName).append("  | ");
	sb.append("wordstring=").append(this.wordstring);
	sb.append(" ]");

	return sb.toString();
    }
}
