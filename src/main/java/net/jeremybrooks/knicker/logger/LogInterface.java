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
 * Defines the interface that Knicker uses to log messages.
 *
 * By default, Knicker will not log anything. If you wish to see Knicker log output,
 * you can implement this class and then tell Knicker what class to use for logging
 * by calling <code>KnickerLogger.setLogger(your class instance)</code>.
 * 
 * @author Jeremy Brooks
 */
public interface LogInterface {

    /**
     * Log a message.
     *
     * @param message the message.
     */
    public void log(String message);


    /**
     * Log a message along with an Exception.
     *
     * @param message the message.
     * @param t the cause of the error.
     */
    public void log(String message, Throwable t);
    
}
