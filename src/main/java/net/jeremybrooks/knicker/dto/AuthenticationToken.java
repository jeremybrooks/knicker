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
 * Represents data returned by a call to the Wordnik authenticate API.
 *
 * @author Jeremy Brooks
 */
public class AuthenticationToken implements Serializable {

	private static final long serialVersionUID = -1394179631959846065L;
	private String token;
    private String userId;


    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }


    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());

        sb.append(": [ ").append("token=").append(this.token).append(" | ");
        sb.append("userId=").append(this.userId).append(" ]");

        return sb.toString();
    }
}
