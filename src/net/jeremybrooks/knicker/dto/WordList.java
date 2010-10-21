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

import net.jeremybrooks.knicker.Knicker;

/**
 * Represents data returned by a call to the Wordnik getLists API.
 *
 * @see http://docs.wordnik.com/api/methods#lists
 * @author jeremyb
 */
public class WordList {
    
    private String createdAt;
    private String description;
    private String id;
    private String name;
    private int numberWordsInList;
    private String permalinkId;
    private Knicker.ListType type;
    private String updatedAt;
    private String userId;
    private String userName;


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
    public String getPermalinkId() {
	return permalinkId;
    }


    /**
     * @param permalinkId the permalinkId to set
     */
    public void setPermalinkId(String permalinkId) {
	this.permalinkId = permalinkId;
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
    public String getUserName() {
	return userName;
    }


    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
	this.userName = userName;
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
	sb.append("permalinkId=").append(this.permalinkId).append(" | ");
	sb.append("type=").append(this.type).append(" | ");
	sb.append("updatedAt=").append(this.updatedAt).append(" | ");
	sb.append("userId=").append(this.userId).append(" | ");
	sb.append("userName=").append(this.userName);
	sb.append(" ]");

	return sb.toString();
    }
}
