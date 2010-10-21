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
 * Represents data returned by a call to the Wordnik word-of-the-day API.
 *
 * @see http://docs.wordnik.com/api/methods#wotd
 * @author jeremyb
 */
public class WordOfTheDay {

    private String publishDate;
    private String id;
    private List<String> definitions;
    private List<String> examples;
    private List<String> notes;
    private String wordstring;

    public WordOfTheDay() {
	this.definitions = new ArrayList<String>();
	this.examples = new ArrayList<String>();
	this.notes = new ArrayList<String>();
    }


    public void addDefinition(String definition) {
	this.definitions.add(definition);
    }

    public void addExample(String example) {
	this.examples.add(example);
    }

    public void addNote(String note) {
	this.notes.add(note);
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
     * @return the definitions
     */
    public List<String> getDefinitions() {
	return definitions;
    }


    /**
     * @param definitions the definitions to set
     */
    public void setDefinitions(List<String> definitions) {
	this.definitions = definitions;
    }


    /**
     * @return the examples
     */
    public List<String> getExamples() {
	return examples;
    }


    /**
     * @param examples the examples to set
     */
    public void setExamples(List<String> examples) {
	this.examples = examples;
    }


    /**
     * @return the notes
     */
    public List<String> getNotes() {
	return notes;
    }


    /**
     * @param notes the notes to set
     */
    public void setNotes(List<String> notes) {
	this.notes = notes;
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

	sb.append(": [ ");
	sb.append("id=").append(this.id).append(" | ");
	sb.append("wordstring=").append(this.wordstring).append(" | ");

	sb.append("definitions=");
	if (this.definitions != null) {
	    for (String s : this.definitions) {
		sb.append('<').append(s).append('>');
	    }
	}

	sb.append("examples=");
	if (this.examples != null) {
	    for (String s : this.examples) {
		sb.append('<').append(s).append('>');
	    }
	}

	sb.append("notes=");
	if (this.notes != null) {
	    for (String s : this.notes) {
		sb.append('<').append(s).append('>');
	    }
	}

	sb.append(" ]");
	
	return sb.toString();
    }
}
