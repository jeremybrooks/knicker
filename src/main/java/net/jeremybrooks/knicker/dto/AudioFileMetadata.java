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
 *
 * @author Jeremy Brooks (jeremyb@whirljack.net)
 */
public class AudioFileMetadata implements Serializable {

	private static final long serialVersionUID = -1828373273102446538L;
	private int commentCount;
    private String createdAt;
    private String createdBy;
    private String fileUrl;
    private String id;
    private String word;


    /**
     * @return the commentCount
     */
    public int getCommentCount() {
	return commentCount;
    }


    /**
     * @param commentCount the commentCount to set
     */
    public void setCommentCount(int commentCount) {
	this.commentCount = commentCount;
    }


    /**
     * @return the createdAt
     */
    public String getCreatedAt() {
	return createdAt;
    }


    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(String createdAt) {
	this.createdAt = createdAt;
    }


    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
	return createdBy;
    }


    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }


    /**
     * @return the fileUrl
     */
    public String getFileUrl() {
	return fileUrl;
    }


    /**
     * @param fileUrl the fileUrl to set
     */
    public void setFileUrl(String fileUrl) {
	this.fileUrl = fileUrl;
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

    
    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(this.getClass().getName());

	sb.append(": [ ").append("commentCount=").append(this.commentCount).append(" | ");
	sb.append("createdAt=").append(this.createdAt).append(" | ");
	sb.append("createdBy=").append(this.createdBy).append(" | ");
	sb.append("fileUrl=").append(this.fileUrl).append(" | ");
	sb.append("id=").append(this.id).append(" | ");
	sb.append("word=").append(this.word).append(" ]");

	return sb.toString();
    }
}
