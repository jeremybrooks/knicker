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
 * Represents data returned by a call to the Wordnik related API.
 *
 * @author Jeremy Brooks
 */
public class Related implements Serializable {

	private static final long serialVersionUID = -6573884162214865856L;
	private String relationshipType;

    private List<String> words;


    public Related() {
        this.words = new ArrayList<String>();
    }


    /**
     * @return the relationshipType
     */
    public String getRelType() {
        return relationshipType;
    }


    /**
     * @param relationshipType the relationshipType to set
     */
    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }


    /**
     * @return the list of words
     */
    public List<String> getWords() {
        return words;
    }


    public void addWord(String word) {
        this.words.add(word);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());
        sb.append(": [ ").append("relationshipType=").append(this.relationshipType).append(" | ");

        sb.append("words=");
        if (this.words != null) {
            for (String s : this.words) {
                sb.append('<').append(s).append('>');
            }
        }

        sb.append(" ]");

        return sb.toString();
    }

}
