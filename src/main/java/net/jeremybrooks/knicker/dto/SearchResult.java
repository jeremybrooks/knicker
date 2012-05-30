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
package net.jeremybrooks.knicker.dto;

import java.io.Serializable;


/**
 * Represents data returned by a call to the Wordnik search API.
 *
 * @author Jeremy Brooks
 */
public class SearchResult implements Serializable {

	private static final long serialVersionUID = -6718508932509586642L;
	private int count;
    private String lexicality;
    private String word;

//    private int matches;
//
//    private int more;
//
//    private List<Match> matchList;
//
//    private int searchTermCount;
//
//    private String searchTermWordstring;


    public SearchResult() {
//	this.matchList = new ArrayList<Match>();
    }


//    /**
//     * @return the matches
//     */
//    public int getMatches() {
//	return matches;
//    }
//
//
//    /**
//     * @param matches the matches to set
//     */
//    public void setMatches(int matches) {
//	this.matches = matches;
//    }
//
//
//    /**
//     * @return the more
//     */
//    public int getMore() {
//	return more;
//    }
//
//
//    /**
//     * @param more the more to set
//     */
//    public void setMore(int more) {
//	this.more = more;
//    }
//
//
//    /**
//     * @return the matchList
//     */
//    public List<Match> getMatchList() {
//	return matchList;
//    }
//
//
//    public void addMatch(int count, String wordstring) {
//	Match match = new Match();
//	match.setCount(count);
//	match.setWordstring(wordstring);
//	this.matchList.add(match);
//    }
//
//
//    /**
//     * @return the searchTermCount
//     */
//    public int getSearchTermCount() {
//	return searchTermCount;
//    }
//
//
//    /**
//     * @param searchTermCount the searchTermCount to set
//     */
//    public void setSearchTermCount(int searchTermCount) {
//	this.searchTermCount = searchTermCount;
//    }
//
//
//    /**
//     * @return the searchTermWordstring
//     */
//    public String getSearchTermWordstring() {
//	return searchTermWordstring;
//    }
//
//
//    /**
//     * @param searchTermWordstring the searchTermWordstring to set
//     */
//    public void setSearchTermWordstring(String searchTermWordstring) {
//	this.searchTermWordstring = searchTermWordstring;
//    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());
        sb.append(": [ ");
        sb.append("count=").append(this.count).append(" | ");
        sb.append("lexicality=").append(this.lexicality).append(" | ");
        sb.append("word=").append(this.word);

        sb.append(" ]");

        return sb.toString();
    }


    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }


    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }


    /**
     * @return the lexicality
     */
    public String getLexicality() {
        return lexicality;
    }


    /**
     * @param lexicality the lexicality to set
     */
    public void setLexicality(String lexicality) {
        this.lexicality = lexicality;
    }


    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }


    /**
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }

}
