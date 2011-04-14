/*
 * Knicker is Copyright 2010-2011 by Jeremy Brooks
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
 * @author jeremyb
 */
public class Definition implements Serializable {

    private int sequence;

//    private String id;

  //  private List<Citation> citations;

    private String text;

    private String word;

    //private List<Label> labels;

    //private List<Note> notes;

    private String partOfSpeech;

    private String source;
    //private String seqString;

    //private List<String> exampleUses;


    public Definition() {
//	this.labels = new ArrayList<Label>();
//	this.citations = new ArrayList<Citation>();
//	this.notes = new ArrayList<Note>();
//	this.exampleUses = new ArrayList<String>();
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


//    /**
//     * @return the id
//     */
//    public String getId() {
//	return id;
//    }
//
//
//    /**
//     * @param id the id to set
//     */
//    public void setId(String id) {
//	this.id = id;
//    }
//
//
//    /**
//     * @return the citations
//     */
//    public List<Citation> getCitations() {
//	return citations;
//    }


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
     * @param headword the headword to set
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


    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(this.getClass().getName());
	sb.append(": [ sequence=").append(this.sequence).append(" | ");
	sb.append("text=").append(this.text).append(" | ");
	sb.append("source=").append(this.source).append(" | ");
	sb.append("word=").append(this.word).append(" | ");
	sb.append("partOfSpeech=").append(this.partOfSpeech);


	sb.append(" ]");

	return sb.toString();
    }


    /**
     * @return the source
     */
    public String getSource() {
	return source;
    }


    /**
     * @param source the source to set
     */
    public void setSource(String source) {
	this.source = source;
    }


//    public void addCitation(String cite, String source) {
//	Citation citation = new Citation();
//	citation.setCite(cite);
//	citation.setSource(source);
//	this.citations.add(citation);
//    }
//
//
//    public void addLabel(String type, String text) {
//	Label label = new Label();
//	label.setText(text);
//	label.setType(type);
//	this.labels.add(label);
//    }
//
//
//    public void addNote(int pos, String value) {
//	Note note = new Note();
//	note.setPos(pos);
//	note.setValue(value);
//	this.notes.add(note);
//    }
//
//
//    public void addExampleUse(String exampleUse) {
//	this.exampleUses.add(exampleUse);
//    }
//
//
//    /**
//     * @return the exampleUses
//     */
//    public List<String> getExampleUses() {
//	return exampleUses;
//    }


//    /**
//     * Represents a citation element.
//     */
//    public class Citation {
//
//	private String cite;
//
//	private String source;
//
//
//	/**
//	 * @return the cite
//	 */
//	public String getCite() {
//	    return cite;
//	}
//
//
//	/**
//	 * @param cite the cite to set
//	 */
//	public void setCite(String cite) {
//	    this.cite = cite;
//	}
//
//
//	/**
//	 * @return the source
//	 */
//	public String getSource() {
//	    return source;
//	}
//
//
//	/**
//	 * @param source the source to set
//	 */
//	public void setSource(String source) {
//	    this.source = source;
//	}
//
//
//	@Override
//	public String toString() {
//	    StringBuilder sb = new StringBuilder(this.getClass().getName());
//	    sb.append(": [ cite=").append(this.cite).append(" | ");
//	    sb.append("source=").append(this.source).append(" ]");
//
//	    return sb.toString();
//	}
//
//    }
//
//
//    /**
//     * Represents a label element.
//     */
//    public class Label {
//
//	private String type;
//
//	private String text;
//
//
//	/**
//	 * @return the type
//	 */
//	public String getType() {
//	    return type;
//	}
//
//
//	/**
//	 * @param type the type to set
//	 */
//	public void setType(String type) {
//	    this.type = type;
//	}
//
//
//	/**
//	 * @return the text
//	 */
//	public String getText() {
//	    return text;
//	}
//
//
//	/**
//	 * @param text the text to set
//	 */
//	public void setText(String text) {
//	    this.text = text;
//	}
//
//
//	@Override
//	public String toString() {
//	    StringBuilder sb = new StringBuilder(this.getClass().getName());
//	    sb.append(": [ type=").append(this.type).append(" | ");
//	    sb.append("text=").append(this.text).append(" ]");
//
//	    return sb.toString();
//	}
//
//    }
//
//
//    /**
//     * Represents a note element.
//     */
//    public class Note {
//
//	private int pos;
//
//	private String value;
//
//
//	/**
//	 * @return the pos
//	 */
//	public int getPos() {
//	    return pos;
//	}
//
//
//	/**
//	 * @param pos the pos to set
//	 */
//	public void setPos(int pos) {
//	    this.pos = pos;
//	}
//
//
//	/**
//	 * @return the value
//	 */
//	public String getValue() {
//	    return value;
//	}
//
//
//	/**
//	 * @param value the value to set
//	 */
//	public void setValue(String value) {
//	    this.value = value;
//	}
//
//
//	@Override
//	public String toString() {
//	    StringBuilder sb = new StringBuilder(this.getClass().getName());
//	    sb.append(": [ pos=").append(this.pos).append(" | ");
//	    sb.append("value=").append(this.value).append(" ]");
//
//	    return sb.toString();
//	}
//
//    }
}
