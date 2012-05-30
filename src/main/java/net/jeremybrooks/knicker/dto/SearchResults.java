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
 * Represents data returned by a call to the Wordnik search API.
 *
 * @author Jeremy Brooks
 */
public class SearchResults implements Serializable {

	private static final long serialVersionUID = 7715395616547902974L;
	private int total;
    private List<SearchResult> searchResults;
    private List<Example> examples;

    public SearchResults() {
        this.searchResults = new ArrayList<SearchResult>();
        this.examples = new ArrayList<Example>();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());
        sb.append(": [ ");
        sb.append("total=").append(this.getTotal()).append(" | ");
        sb.append("searchResults=");
        for (SearchResult sr : this.getSearchResults()) {
            sb.append('<').append(sr.toString()).append('>');
        }
        sb.append("examples=");
        for (Example e : this.getExamples()) {
            sb.append('<').append(e.toString()).append('>');
        }

        sb.append(" ]");

        return sb.toString();
    }


    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }


    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }


    /**
     * @return the results
     */
    public List<SearchResult> getSearchResults() {
        return searchResults;
    }


    /**
     * @param searchResult the results to set
     */
    public void addSearchResult(SearchResult searchResult) {
        this.searchResults.add(searchResult);
    }

    /**
     * @return the examples
     */
    public List<Example> getExamples() {
        return examples;
    }


    /**
     * @param example the example to add to the list of examples.
     */
    public void addExample(Example example) {
        this.examples.add(example);
    }
}
