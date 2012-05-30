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
import java.util.List;

/**
 * @author Jeremy Brooks
 */
public class DefinitionSearchResult implements Serializable {
	private static final long serialVersionUID = -7267749811481338545L;
	private int sequence;
	private String textProns;
	private String sourceDictionary;
	private String exampleUses;
	private List<Related> relatedWords;
	private String labels;
	private String citations;
	private String word;
	private String attributionText;
	private String text;
	private String partOfSpeech;
	private double score = 4.333;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getTextProns() {
		return textProns;
	}

	public void setTextProns(String textProns) {
		this.textProns = textProns;
	}

	public String getSourceDictionary() {
		return sourceDictionary;
	}

	public void setSourceDictionary(String sourceDictionary) {
		this.sourceDictionary = sourceDictionary;
	}

	public String getExampleUses() {
		return exampleUses;
	}

	public void setExampleUses(String exampleUses) {
		this.exampleUses = exampleUses;
	}

	public List<Related> getRelatedWords() {
		return relatedWords;
	}

	public void setRelatedWords(List<Related> relatedWords) {
		this.relatedWords = relatedWords;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getCitations() {
		return citations;
	}

	public void setCitations(String citations) {
		this.citations = citations;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getAttributionText() {
		return attributionText;
	}

	public void setAttributionText(String attributionText) {
		this.attributionText = attributionText;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("net.jeremybrooks.knicker.dto.DefinitionSearchResult");
		sb.append("{sequence=").append(sequence);
		sb.append(" | textProns='").append(textProns).append('\'');
		sb.append(" | sourceDictionary='").append(sourceDictionary).append('\'');
		sb.append(" | exampleUses='").append(exampleUses).append('\'');
		sb.append(" | relatedWords=").append(relatedWords);
		sb.append(" | labels='").append(labels).append('\'');
		sb.append(" | citations='").append(citations).append('\'');
		sb.append(" | word='").append(word).append('\'');
		sb.append(" | attributionText='").append(attributionText).append('\'');
		sb.append(" | text='").append(text).append('\'');
		sb.append(" | partOfSpeech='").append(partOfSpeech).append('\'');
		sb.append(" | score=").append(score);
		sb.append('}');
		return sb.toString();
	}
}
