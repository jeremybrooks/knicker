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

import java.util.ArrayList;
import java.util.List;


/**
 * Represents word data returned by a call to the Wordnik lookup API.
 *
 * @see http://docs.wordnik.com/api/methods#words
 * @author jeremyb
 */
public class Word {

    private String id;

    private String wordstring;

    private String canonicalForm;

    private List<String> suggestions;


    public Word() {
	this.suggestions = new ArrayList<String>();
    }


    /**
     * @return the id
     */
    public String getId() {
	return id;
    }


    /**
     * @param id the id to set
     */
    public void setId(String id) {
	this.id = id;
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


    /**
     * @return the suggestions
     */
    public List<String> getSuggestions() {
	return suggestions;
    }


    public void addSuggestion(String suggestion) {
	this.suggestions.add(suggestion);
    }


    /**
     * @return the canonicalForm
     */
    public String getCanonicalForm() {
	return canonicalForm;
    }


    /**
     * @param canonicalForm the canonicalForm to set
     */
    public void setCanonicalForm(String canonicalForm) {
	this.canonicalForm = canonicalForm;
    }


    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(Word.class.getName());
	sb.append(": [ id=").append(this.id).append(" | ");
	sb.append("wordstring=").append(this.wordstring).append(" | ");
	sb.append("canonicalForm=").append(this.canonicalForm).append(" | ");
	sb.append("suggestions=");
	if (this.suggestions != null && this.suggestions.size() > 0) {
	    for (String s : this.suggestions) {
		sb.append(s).append(",");
	    }
	    sb.deleteCharAt(sb.length() - 1);
	}

	sb.append(" ]");

	return sb.toString();
    }

}
