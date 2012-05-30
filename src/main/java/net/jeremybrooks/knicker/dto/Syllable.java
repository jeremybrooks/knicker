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
 * Represents a syllable element returned from the Wordnik API.
 *
 * @author Jeremy Brooks
 */
public class Syllable implements Serializable {


	private static final long serialVersionUID = 3323101537377996783L;
	private String type;
    private int seq;
    private String text;


    /**
     * @return the type
     */
    public String getType() {
        return type;
    }


    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * @return the seq
     */
    public int getSeq() {
        return seq;
    }


    /**
     * @param seq the seq to set
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }


    /**
     * @return the text
     */
    public String getText() {
        return text;
    }


    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());

        sb.append(": [ ").append("type=").append(this.type).append(" | ");
        sb.append(": [ ").append("seq=").append(this.seq).append(" | ");
        sb.append("text=").append(this.text).append(" ]");

        return sb.toString();
    }
}
