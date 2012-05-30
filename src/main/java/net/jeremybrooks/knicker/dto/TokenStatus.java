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
 * Represents data returned by a call to the Wordnik token status API.
 *
 * @author Jeremy Brooks
 */
public class TokenStatus implements Serializable {

	private static final long serialVersionUID = -663296789006258800L;
	private long expiresInMillis;
    private int remainingCalls;
    private long resetsInMillis;
    private String token;
    private int totalRequests;
    private boolean valid;


    /**
     * @return the expiresInMillis
     */
    public long getExpiresInMillis() {
        return expiresInMillis;
    }


    /**
     * @param expiresInMillis the expiresInMillis to set
     */
    public void setExpiresInMillis(long expiresInMillis) {
        this.expiresInMillis = expiresInMillis;
    }


    /**
     * @return the remainingCalls
     */
    public int getRemainingCalls() {
        return remainingCalls;
    }


    /**
     * @param remainingCalls the remainingCalls to set
     */
    public void setRemainingCalls(int remainingCalls) {
        this.remainingCalls = remainingCalls;
    }


    /**
     * @return the resetsInMillis
     */
    public long getResetsInMillis() {
        return resetsInMillis;
    }


    /**
     * @param resetsInMillis the resetsInMillis to set
     */
    public void setResetsInMillis(long resetsInMillis) {
        this.resetsInMillis = resetsInMillis;
    }


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
     * @return the totalRequests
     */
    public int getTotalRequests() {
        return totalRequests;
    }


    /**
     * @param totalRequests the totalRequests to set
     */
    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }


    /**
     * @return the valid
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * @param valid the valid to set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());

        sb.append(": [ ");
        sb.append("expiresInMillis=").append(this.expiresInMillis).append(" | ");
        sb.append("remainingCalls=").append(this.remainingCalls).append(" | ");
        sb.append("resetsInMillis=").append(this.resetsInMillis).append(" | ");
        sb.append("token=").append(this.token).append(" | ");
        sb.append("totalRequests=").append(this.totalRequests).append(" | ");
        sb.append("valid=").append(this.valid);
        sb.append(" ]");

        return sb.toString();
    }
}
