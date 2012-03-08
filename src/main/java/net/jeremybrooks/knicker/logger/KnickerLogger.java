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
 * Set and get the logger that Knicker will use.
 * 
 * @author Jeremy Brooks
 */
public class KnickerLogger {

    private static LogInterface logger = null;


    /**
     * @return the logger
     */
    public static LogInterface getLogger() {
	if (logger == null) {
	    logger = new DefaultLogger();
	}
	
	return logger;
    }


    /**
     * @param logger the logger to set
     */
    public static void setLogger(LogInterface logger) {
	if (logger == null) {
	    KnickerLogger.logger = new DefaultLogger();
	} else {
	    KnickerLogger.logger = logger;
	}
    }



}
