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

import net.jeremybrooks.knicker.Knicker;

import java.io.Serializable;

/**
 * Represents data returned by a call to the Wordnik getLists API.
 *
 * @author Jeremy Brooks
 */
public class WordList implements Serializable {

	private static final long serialVersionUID = -2426868885366575325L;
	private String createdAt;
    private String description;
    private String id;
    private String name;
    private int numberWordsInList;
    private String permalink;
    private Knicker.ListType type;
    private String updatedAt;
    private String userId;
    private String username;


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
     * @return the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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


    /**
     * @return the numberWordsInList
     */
    public int getNumberWordsInList() {
        return numberWordsInList;
    }


    /**
     * @param numberWordsInList the numberWordsInList to set
     */
    public void setNumberWordsInList(int numberWordsInList) {
        this.numberWordsInList = numberWordsInList;
    }


    /**
     * @return the permalinkId
     */
    public String getPermalink() {
        return permalink;
    }


    /**
     * @param permalink the permalinkId to set
     */
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }


    /**
     * @return the type
     */
    public Knicker.ListType getType() {
        return type;
    }


    /**
     * @param type the type to set
     */
    public void setType(Knicker.ListType type) {
        this.type = type;
    }


    /**
     * @return the updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }


    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }


    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }


    /**
     * @return the userName
     */
    public String getUsername() {
        return username;
    }


    /**
     * @param username the userName to set
     */
    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());

        sb.append(": [ ");
        sb.append("createdAt=").append(this.createdAt).append(" | ");
        sb.append("description=").append(this.description).append(" | ");
        sb.append("id=").append(this.id).append(" | ");
        sb.append("name=").append(this.name).append(" | ");
        sb.append("numberWordsInList=").append(this.numberWordsInList).append(" | ");
        sb.append("permalink=").append(this.permalink).append(" | ");
        sb.append("type=").append(this.type).append(" | ");
        sb.append("updatedAt=").append(this.updatedAt).append(" | ");
        sb.append("userId=").append(this.userId).append(" | ");
        sb.append("username=").append(this.username);
        sb.append(" ]");

        return sb.toString();
    }
}
