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
 * Represents data returned by a call to the Wordnik punctuationFactor API.
 *
 * @see http://docs.wordnik.com/api/methods#punc
 * @author jeremyb
 */
public class PunctuationFactor {

    private int	exclamationPointCount;
    private int periodCount;
    private int questionMarkCount;
    private int	totalCount;
    private String wordId;


    /**
     * @return the exclamationPointCount
     */
    public int getExclamationPointCount() {
	return exclamationPointCount;
    }


    /**
     * @param exclamationPointCount the exclamationPointCount to set
     */
    public void setExclamationPointCount(int exclamationPointCount) {
	this.exclamationPointCount = exclamationPointCount;
    }


    /**
     * @return the periodCount
     */
    public int getPeriodCount() {
	return periodCount;
    }


    /**
     * @param periodCount the periodCount to set
     */
    public void setPeriodCount(int periodCount) {
	this.periodCount = periodCount;
    }


    /**
     * @return the questionMarkCount
     */
    public int getQuestionMarkCount() {
	return questionMarkCount;
    }


    /**
     * @param questionMarkCount the questionMarkCount to set
     */
    public void setQuestionMarkCount(int questionMarkCount) {
	this.questionMarkCount = questionMarkCount;
    }


    /**
     * @return the totalCount
     */
    public int getTotalCount() {
	return totalCount;
    }


    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
    }


    /**
     * @return the wordId
     */
    public String getWordId() {
	return wordId;
    }


    /**
     * @param wordId the wordId to set
     */
    public void setWordId(String wordId) {
	this.wordId = wordId;
    }


    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(this.getClass().getName());

	sb.append(": [ ").append("exclamationPointCount=").append(this.exclamationPointCount).append(" | ");
	sb.append("periodCount=").append(this.periodCount).append(" | ");
	sb.append("questionMarkCount=").append(this.questionMarkCount).append(" | ");
	sb.append("totalCount=").append(this.totalCount).append(" | ");
	sb.append("wordId=").append(this.wordId).append(" ]");

	return sb.toString();
    }
}
