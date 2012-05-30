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
 * Represents data returned by a call to the Wordnik pronunciations API.
 *
 * @author Jeremy Brooks
 */
public class Pronunciation implements Serializable {

	private static final long serialVersionUID = -6987796220378505558L;
	private String id;
    private String raw;
    private String rawType;


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
     * @return the raw
     */
    public String getRaw() {
        return raw;
    }


    /**
     * @param raw the raw to set
     */
    public void setRaw(String raw) {
        this.raw = raw;
    }


    /**
     * @return the rawType
     */
    public String getRawType() {
        return rawType;
    }


    /**
     * @param rawType the rawType to set
     */
    public void setRawType(String rawType) {
        this.rawType = rawType;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());

        sb.append(": [ ").append("id=").append(this.id).append(" | ");
        sb.append("raw=").append(this.raw).append(" | ");
        sb.append("rawType=").append(this.rawType).append(" ]");

        return sb.toString();
    }
}
