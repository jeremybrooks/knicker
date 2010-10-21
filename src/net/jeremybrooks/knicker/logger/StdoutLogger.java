/*
 * Knicker is Copyright 2010 by Jeremy Brooks
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

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Log to <code>System.out</code>.
 *
 * By default, Knicker will not log anything. If you want to see Knicker log output
 * on stdout, call <code>KnickerLogger.setLogger(new StdoutLogger());</code>.
 * 
 * @author jeremyb
 */
public class StdoutLogger implements LogInterface {


    /**
     * Log messages to stdout.
     *
     * @param message the message to log.
     */
    @Override
    public void log(String message) {
	System.out.println(message);
    }


    /**
     * Log message to stdout, along with exception information.
     *
     * @param message the message to log.
     * @param t the Throwable to log.
     */
    @Override
    public void log(String message, Throwable t) {
	System.out.println(message);
	System.out.println(getStackTrace(t));
    }


    /**
     * Get stack trace as a String.
     *
     * @param t the Throwable.
     * @return stack trace as a String, or an empty String if the Throwable
     *         was null.
     */
    private String getStackTrace(Throwable t) {
	if (t == null) {
	    return "";
	}
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}
