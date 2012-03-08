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
package net.jeremybrooks.knicker.logger;

/**
 * This is the default logger used by Knicker. It does nothing.
 *
 * @author Jeremy Brooks
 */
public class DefaultLogger implements LogInterface {


    @Override
    public void log(String message) {
	// Does nothing
    }


    @Override
    public void log(String message, Throwable exception) {
	// Does nothing
    }

}
