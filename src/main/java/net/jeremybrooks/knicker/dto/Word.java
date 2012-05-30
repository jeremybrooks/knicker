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
import java.util.ArrayList;
import java.util.List;


/**
 * Represents word data returned by a call to the Wordnik lookup API.
 *
 * @author Jeremy Brooks
 */
public class Word implements Serializable {

	private static final long serialVersionUID = -368699002993567147L;
	private String word;

    private String canonicalForm;

    private List<String> suggestions;

    private String originalWord;

    public Word() {
        this.suggestions = new ArrayList<String>();
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
        sb.append(": [ word=").append(this.word).append(" | ");
        sb.append("originalWord").append(this.originalWord).append(" | ");
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


    /**
     * @return the originalWord
     */
    public String getOriginalWord() {
        return originalWord;
    }


    /**
     * @param originalWord the originalWord to set
     */
    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

}
