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
 * Represents data returned by a call to the Wordnik definitions API.
 *
 * @author Jeremy Brooks (jeremyb@whirljack.net)
 * @author Boris Goldowski (patch for attribution text support)
 */
public class Definition implements Serializable {

	private static final long serialVersionUID = -2518164900481852360L;
	private int sequence;

    private String text;

    private String word;

    private String partOfSpeech;

    private String sourceDictionary;

    private String score;

    private String attributionText;

    public Definition() {
    }


    /**
     * @return the sequence
     */
    public int getSequence() {
        return sequence;
    }


    /**
     * @param sequence the sequence to set
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
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


//    /**
//     * @return the labels
//     */
//    public List<Label> getLabels() {
//	return labels;
//    }
//
//
//    /**
//     * @return the notes
//     */
//    public List<Note> getNotes() {
//	return notes;
//    }


    /**
     * @return the partOfSpeech
     */
    public String getPartOfSpeech() {
        return partOfSpeech;
    }


    /**
     * @param partOfSpeech the partOfSpeech to set
     */
    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }


//    /**
//     * @return the seqString
//     */
//    public String getSeqString() {
//	return seqString;
//    }
//
//
//    /**
//     * @param seqString the seqString to set
//     */
//    public void setSeqString(String seqString) {
//	this.seqString = seqString;
//    }


    /**
     * Get the attribution text for this definition.
     *
     * @return attribution text.
     */
    public String getAttributionText() {
        return this.attributionText;
    }


    /**
     * Set the attribution text for this definition.
     *
     * @param attributionText the attribution text for this definition.
     */
    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());
        sb.append(": [ sequence=").append(this.sequence).append(" | ");
        sb.append("text=").append(this.text).append(" | ");
        sb.append("sourceDictionary=").append(this.sourceDictionary).append(" | ");
        sb.append("score=").append(this.getScore()).append(" | ");
        sb.append("word=").append(this.word).append(" | ");
        sb.append("attributionText=").append(this.attributionText).append(" | ");
        sb.append("partOfSpeech=").append(this.partOfSpeech);


        sb.append(" ]");

        return sb.toString();
    }


    /**
     * @return the sourceDictionary
     */
    public String getSourceDictionary() {
        return sourceDictionary;
    }


    /**
     * @param sourceDictionary the sourceDictionary to set
     */
    public void setSourceDictionary(String sourceDictionary) {
        this.sourceDictionary = sourceDictionary;
    }


    /**
     * @return the score
     */
    public String getScore() {
        return score;
    }


    /**
     * @param score the score to set
     */
    public void setScore(String score) {
        this.score = score;
    }

}
