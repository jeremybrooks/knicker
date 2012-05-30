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
 * Represents data returned by a call to the Wordnik examples API.
 *
 * @author Jeremy Brooks
 */
public class Example implements Serializable {

	private static final long serialVersionUID = -6063353481846670987L;
	private String text;
    private String exampleId;
    private String documentId;
    private Provider provider;
    private String rating;
    private String title;
    private String url;
    private String word;
    private String year;

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
     * @return the provider
     */
    public Provider getProvider() {
        return provider;
    }


    /**
     * @param provider the provider to set
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }


    /**
     * Return a human readable representation of this class.
     *
     * @return representation of this class.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());

        sb.append(": [ documentId=").append(this.documentId).append(" | ");
        sb.append("exampleId=").append(this.getExampleId()).append(" | ");
        sb.append("word=").append(this.getWord()).append(" | ");
        sb.append("rating=").append(this.rating).append(" | ");
        sb.append("title=").append(this.title).append(" | ");
        sb.append("url=").append(this.url).append(" | ");
        sb.append("text=").append(this.text).append(" | ");
        sb.append("year=").append(this.year).append(" | ");
        sb.append("provider=");
        if (getProvider() != null) {
            sb.append(this.getProvider().toString());
        } else {
            sb.append("null");
        }

        sb.append(" ]");
        return sb.toString();
    }
}
