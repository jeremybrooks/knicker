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
 * Represents data returned by a call to the Wordnik related API.
 *
 * @see http://docs.wordnik.com/api/methods#relateds
 * @author jeremyb
 */
public class Related {

    private String relType;

    private List<String> wordstrings;


    public Related() {
	this.wordstrings = new ArrayList<String>();
    }


    /**
     * @return the relType
     */
    public String getRelType() {
	return relType;
    }


    /**
     * @param relType the relType to set
     */
    public void setRelType(String relType) {
	this.relType = relType;
    }


    /**
     * @return the wordstrings
     */
    public List<String> getWordstrings() {
	return wordstrings;
    }


    public void addWordstring(String wordstring) {
	this.wordstrings.add(wordstring);
    }


    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(this.getClass().getName());
	sb.append(": [ ").append("relType=").append(this.relType).append(" | ");

	sb.append("wordstrings=");
	if (this.wordstrings != null) {
	    for (String s : this.wordstrings) {
		sb.append('<').append(s).append('>');
	    }
	}

	sb.append(" ]");

	return sb.toString();
    }

}
