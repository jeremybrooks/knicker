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
 * Represents data returned by a call to the Wordnik user API.
 *
 * @author Jeremy Brooks
 * @see <a ref="http://docs.wordnik.com/docs">Wordnik documentation</a>
 */
public class User implements Serializable {

	private static final long serialVersionUID = -6102214221572219229L;
	private String email;
    private String id;
    private String status;
    private String userName;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());

        sb.append(": [ ").append("email=").append(this.email).append(" | ");
        sb.append("id=").append(this.id).append(" | ");
        sb.append("status=").append(this.status).append(" | ");
        sb.append("userName=").append(this.userName).append(" ]");

        return sb.toString();
    }


    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }


    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }


    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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
}
