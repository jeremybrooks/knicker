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
import java.util.List;

/**
 * @author Jeremy Brooks
 */
public class DefinitionSearchResults implements Serializable {
	private static final long serialVersionUID = -8102740880719365267L;
	private int totalResults;
	private List<DefinitionSearchResult> results;


	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public List<DefinitionSearchResult> getResults() {
		return results;
	}

	public void setResults(List<DefinitionSearchResult> results) {
		this.results = results;
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("net.jeremybrooks.knicker.dto.DefinitionSearchResults");
		sb.append("{totalResults=").append(totalResults);
		sb.append(" | results=").append(results);
		sb.append('}');
		return sb.toString();
	}
}
