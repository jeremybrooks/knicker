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
package net.jeremybrooks.knicker;


/**
 * Knicker encapsulates all errors in an instance of <code>KnickerException</code>.
 *
 * @author Jeremy Brooks
 */
public class KnickerException extends java.lang.Exception {


    /**
     * Creates a new instance of <code>KnickerException</code> without detail message.
     */
    public KnickerException() {
    }


    /**
     * Constructs an instance of <code>KnickerException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public KnickerException(String msg) {
	super(msg);
    }


    /**
     * Constructs an instance of <code>KnickerException</code> with the specified detail message
     * and cause of the error.
     *
     * @param msg the detail message.
     * @param cause the cause of the error.
     */
    public KnickerException(String msg, Throwable cause) {
	super(msg, cause);
    }

}
