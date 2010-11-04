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
 *
 * @author jeremyb
 */
public class WordFrequency {

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
	sb.append(": [ ").append("count=").append(this.count).append(" | ");

	sb.append("wordstring=").append(this.wordstring);

	sb.append(" ]");

	return sb.toString();
    }
}
