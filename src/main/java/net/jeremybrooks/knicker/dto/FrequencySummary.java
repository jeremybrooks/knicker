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
import java.util.ArrayList;
import java.util.List;


/**
 * Represents data returned by a call to the Wordnik frequency API.
 *
 * @author Jeremy Brooks
 */
public class FrequencySummary implements Serializable {

	private static final long serialVersionUID = 2263001296867261312L;
	private List<Frequency> frequencies;

    private String word;

    private int totalCount;

    private int unknownYearCount;


    public FrequencySummary() {
        this.frequencies = new ArrayList<Frequency>();
    }


    /**
     * @return the frequencies
     */
    public List<Frequency> getFrequencies() {
        return frequencies;
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
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }


    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


    /**
     * @return the unknownYearCount
     */
    public int getUnknownYearCount() {
        return unknownYearCount;
    }


    /**
     * @param unknownYearCount the unknownYearCount to set
     */
    public void setUnknownYearCount(int unknownYearCount) {
        this.unknownYearCount = unknownYearCount;
    }


    public void addFrequency(int count, String year) {
        Frequency frequency = new Frequency();
        frequency.setCount(count);
        frequency.setYear(year);
        this.frequencies.add(frequency);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());

        sb.append(": [ word=").append(this.word).append(" | ");
        sb.append("totalCount=").append(this.totalCount).append(" | ");
        sb.append("unknownYearCount=").append(this.unknownYearCount).append(" | ");
        sb.append("frequencies=");
        if (this.frequencies != null) {
            for (Frequency f : this.frequencies) {
                sb.append('<').append(f).append('>');
            }
        }

        sb.append(" ]");

        return sb.toString();
    }


    /**
     * Represents a frequency element.
     */
    public class Frequency {

        private int count;

        private String year;


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


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(this.getClass().getName());
            sb.append(": [ count=").append(this.count).append(" | ");
            sb.append("year=").append(this.year).append(" ]");

            return sb.toString();
        }

    }
}
