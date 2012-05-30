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
 * Represents data returned by a call to the Wordnik phrases API.
 *
 * @author Jeremy Brooks
 */
public class Phrase implements Serializable {

	private static final long serialVersionUID = -7945579472747777719L;
	private int count;
    private String mi;
    private String wlmi;
    private String gram1;
    private String gram2;


    /**
     * @return the count
     */
    public int getCount() {
	return count;
    }


    /**
     * @param count the count to set
     */
    public void setCount(int count) {
	this.count = count;
    }


    /**
     * @return the mi
     */
    public String getMi() {
	return mi;
    }


    /**
     * @param mi the mi to set
     */
    public void setMi(String mi) {
	this.mi = mi;
    }


    /**
     * @return the wlmi
     */
    public String getWlmi() {
	return wlmi;
    }


    /**
     * @param wlmi the wlmi to set
     */
    public void setWlmi(String wlmi) {
	this.wlmi = wlmi;
    }


    /**
     * @return the gram1
     */
    public String getGram1() {
	return gram1;
    }


    /**
     * @param gram1 the gram1 to set
     */
    public void setGram1(String gram1) {
	this.gram1 = gram1;
    }


    /**
     * @return the gram2
     */
    public String getGram2() {
	return gram2;
    }


    /**
     * @param gram2 the gram2 to set
     */
    public void setGram2(String gram2) {
	this.gram2 = gram2;
    }


    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(Word.class.getName());
	sb.append(": [ count=").append(this.count).append(" | ");
	sb.append("mi=").append(this.mi).append(" | ");
	sb.append("wlmi=").append(this.wlmi).append(" | ");
	sb.append("gram1=").append(this.gram1).append(" | ");
	sb.append("gram2=").append(this.gram2).append(" ]");

	return sb.toString();
    }


}
