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
 * Represents data returned by a call to the Wordnik examples API.
 *
 * @see http://docs.wordnik.com/api/methods#examples
 * @author jeremyb
 */
public class Example {

    private String display;

    private String documentId;

    private String exampleId;

    private String scoreId;

    private String rating;

    private String title;

    private String url;

    private String wordstring;

    private String year;

    private List<Provider> providers;


    public Example() {
	this.providers = new ArrayList<Provider>();
    }


    /**
     * @return the display
     */
    public String getDisplay() {
	return display;
    }


    /**
     * @param display the display to set
     */
    public void setDisplay(String display) {
	this.display = display;
    }


    /**
     * @return the documentId
     */
    public String getDocumentId() {
	return documentId;
    }


    /**
     * @param documentId the documentId to set
     */
    public void setDocumentId(String documentId) {
	this.documentId = documentId;
    }


    /**
     * @return the exampleId
     */
    public String getExampleId() {
	return exampleId;
    }


    /**
     * @param exampleId the exampleId to set
     */
    public void setExampleId(String exampleId) {
	this.exampleId = exampleId;
    }


    /**
     * @return the scoreId
     */
    public String getScoreId() {
	return scoreId;
    }


    /**
     * @param scoreId the scoreId to set
     */
    public void setScoreId(String scoreId) {
	this.scoreId = scoreId;
    }


    /**
     * @return the rating
     */
    public String getRating() {
	return rating;
    }


    /**
     * @param rating the rating to set
     */
    public void setRating(String rating) {
	this.rating = rating;
    }


    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }


    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }


    /**
     * @return the url
     */
    public String getUrl() {
	return url;
    }


    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
	this.url = url;
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


    /**
     * @return the year
     */
    public String getYear() {
	return year;
    }


    /**
     * @param year the year to set
     */
    public void setYear(String year) {
	this.year = year;
    }


    /**
     * @return the providers
     */
    public List<Provider> getProviders() {
	return providers;
    }


    public void addProvider(String id, String name) {
	Provider provider = new Provider();
	provider.setId(id);
	provider.setName(name);
	this.providers.add(provider);
    }


    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(this.getClass().getName());

	sb.append(": [ ").append("display=").append(this.display).append(" | ");
	sb.append("documentId=").append(this.documentId).append(" | ");
	sb.append("exampleId=").append(this.exampleId).append(" | ");
	sb.append("scoreId=").append(this.scoreId).append(" | ");
	sb.append("rating=").append(this.rating).append(" | ");
	sb.append("title=").append(this.title).append(" | ");
	sb.append("url=").append(this.url).append(" | ");
	sb.append("wordstring=").append(this.wordstring).append(" | ");
	sb.append("year=").append(this.year).append(" | ");

	sb.append("providers=");
	if (this.providers != null) {
	    for (Provider p : this.providers) {
		sb.append('<').append(p.toString()).append('>');
	    }
	}

	return sb.toString();
    }


    /**
     * Represents a provider element.
     */
    public class Provider {

	private String id;

	private String name;


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
	 * @return the name
	 */
	public String getName() {
	    return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
	    this.name = name;
	}


	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder(this.getClass().getName());
	    sb.append(": [ id=").append(this.id).append(" | ");
	    sb.append("name=").append(this.name).append(" ]");

	    return sb.toString();
	}

    }
}
