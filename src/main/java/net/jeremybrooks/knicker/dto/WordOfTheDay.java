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
 * Represents data returned by a call to the Wordnik word-of-the-day API.
 *
 * @author Jeremy Brooks
 */
public class WordOfTheDay implements Serializable {

	private static final long serialVersionUID = 7162018037337508463L;
	private ContentProvider contentProvider;
    private List<Definition> definitions;
    private List<Example> examples;
    private String id;
    private String note;
    private String publishDate;
    private String word;


    public WordOfTheDay() {
        definitions = new ArrayList<Definition>();
        examples = new ArrayList<Example>();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());

        sb.append(": [ ");
        sb.append("id=").append(this.getId()).append(" | ");
        sb.append("publishDate=").append(this.publishDate).append(" | ");
        sb.append("word=").append(this.word).append(" | ");
        sb.append("note=").append(this.note).append(" | ");
        sb.append("contentProvider=").append(this.contentProvider).append(" | ");


        sb.append("definitions=");
        if (this.getDefinitions() != null) {
            for (Definition d : this.getDefinitions()) {
                sb.append('<').append(d).append('>');
            }
        }

        sb.append("examples=");
        if (this.getExamples() != null) {
            for (Example e : this.getExamples()) {
                sb.append('<').append(e).append('>');
            }
        }


        sb.append(" ]");

        return sb.toString();
    }


    /**
     * @return the publishDate
     */
    public String getPublishDate() {
        return publishDate;
    }


    /**
     * @param publishDate the publishDate to set
     */
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
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
     * @return the contentProvider
     */
    public ContentProvider getContentProvider() {
        return contentProvider;
    }


    /**
     * @param contentProvider the contentProvider to set
     */
    public void setContentProvider(ContentProvider contentProvider) {
        this.contentProvider = contentProvider;
    }


    /**
     * @return the definitions
     */
    public List<Definition> getDefinitions() {
        return definitions;
    }


    /**
     * @param definition the definition to add
     */
    public void addDefinition(Definition definition) {
        this.definitions.add(definition);
    }


    /**
     * @return the examples
     */
    public List<Example> getExamples() {
        return examples;
    }


    /**
     * @param example the example to add
     */
    public void addExample(Example example) {
        this.examples.add(example);
    }


    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }


    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }
}
