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

// JAVA UTILITY
import java.util.ArrayList;
import java.util.List;


/**
 * Represents data returned by a call to the Wordnik autocomplete API.
 *
 * @see http://docs.wordnik.com/api/methods#auto
 * @author jeremyb
 */
public class SearchResult {

    private int matches;

    private int more;

    private List<Match> matchList;

    private int searchTermCount;

    private String searchTermWordstring;


    public SearchResult() {
	this.matchList = new ArrayList<Match>();
    }


    /**
     * @return the matches
     */
    public int getMatches() {
	return matches;
    }


    /**
     * @param matches the matches to set
     */
    public void setMatches(int matches) {
	this.matches = matches;
    }


    /**
     * @return the more
     */
    public int getMore() {
	return more;
    }


    /**
     * @param more the more to set
     */
    public void setMore(int more) {
	this.more = more;
    }


    /**
     * @return the matchList
     */
    public List<Match> getMatchList() {
	return matchList;
    }


    public void addMatch(int count, String wordstring) {
	Match match = new Match();
	match.setCount(count);
	match.setWordstring(wordstring);
	this.matchList.add(match);
    }


    /**
     * @return the searchTermCount
     */
    public int getSearchTermCount() {
	return searchTermCount;
    }


    /**
     * @param searchTermCount the searchTermCount to set
     */
    public void setSearchTermCount(int searchTermCount) {
	this.searchTermCount = searchTermCount;
    }


    /**
     * @return the searchTermWordstring
     */
    public String getSearchTermWordstring() {
	return searchTermWordstring;
    }


    /**
     * @param searchTermWordstring the searchTermWordstring to set
     */
    public void setSearchTermWordstring(String searchTermWordstring) {
	this.searchTermWordstring = searchTermWordstring;
    }


    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(this.getClass().getName());
	sb.append(": [ ");
	sb.append("matches=").append(this.matches).append(" | ");
	sb.append("more=").append(this.more).append(" | ");
	sb.append("searchTermCount=").append(this.searchTermCount).append(" | ");
	sb.append("searchTermWordstring=").append(this.searchTermWordstring).append(" | ");

	sb.append("matchList=");
	if (this.matchList != null) {
	    for (Match m : this.matchList) {
		sb.append('<').append(m.toString()).append('>');
	    }
	}

	sb.append(" ]");

	return sb.toString();
    }


    /**
     * Represents a match element.
     */
    public class Match {

	private int count;

	private String wordstring;


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
	    sb.append(": [ count=").append(this.count).append(" | ");
	    sb.append("wordstring=").append(this.wordstring).append(" ]");

	    return sb.toString();
	}

    }
}
