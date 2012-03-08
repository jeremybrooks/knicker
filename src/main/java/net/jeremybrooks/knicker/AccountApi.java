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


import net.jeremybrooks.knicker.dto.AuthenticationToken;
import net.jeremybrooks.knicker.dto.TokenStatus;
import net.jeremybrooks.knicker.dto.User;
import net.jeremybrooks.knicker.dto.WordList;

import java.util.List;

/**
 * This is the class that should be used by programs that want to access the
 * Wordnik Account API.
 * <p/>
 * To use this, you must first register for a Wordnik API key. You can do that
 * here: http://api.wordnik.com/signup/. Once you have an API key, set it as a
 * system property, like this:
 * <code>System.setProperty("WORDNIK_API_KEY", "<your API key>");</code>
 * Once you have set the WORDNIK_API_KEY, just call the methods in this class.
 * The methods are all public static. Most of the methods will return instances
 * of classes in the <code>net.jeremybrooks.knicker.dto</code> package.
 * <p/>
 * If there are errors, a <code>KnickerException</code> will be thrown.
 *
 * @author Jeremy Brooks
 */
public class AccountApi extends Knicker {


    /**
     * Log in to Wordnik.
     * <p/>
     * Certain methods — currently, user accounts and list-related CRUD
     * operations — are only available to you if you pass a valid
     * authentication token.
     * <p/>
     * This method logs you in to Wordnik via the API and returns an instance of
     * <code>AuthenticationToken</code> which you can then use to make other
     * requests.
     *
     * @param username Wordnik username.
     * @param password Wordnik password for the user.
     * @return authentication token for the user.
     * @throws KnickerException if the username or password is null, or if there
     *                          are any errors.
     */
    public static AuthenticationToken authenticate(String username, String password) throws KnickerException {
        if (username == null || username.isEmpty()) {
            throw new KnickerException("You must specify a username.");
        }
        if (password == null || password.isEmpty()) {
            throw new KnickerException("You must specify a password.");
        }

        AuthenticationToken auth = null;

        StringBuilder uri = new StringBuilder(ACCOUNT_ENDPOINT);
        uri.append("/authenticate/").append(username);
        uri.append("?password=").append(password);

        auth = DTOBuilder.buildAuthenticationToken(Util.doGet(uri.toString()));

        return auth;
    }


    /**
     * Check your API key usage by calling the apiTokenStatus
     * resource. This call does not count against your API usage.
     *
     * @return your current API usage information.
     * @throws KnickerException if there are any errors.
     */
    public static TokenStatus apiTokenStatus() throws KnickerException {
        StringBuilder uri = new StringBuilder(ACCOUNT_ENDPOINT);
        uri.append("/apiTokenStatus");

        return DTOBuilder.buildTokenStatus(Util.doGet(uri.toString()));
    }


    /**
     * Get information about the currently logged in user.
     * <p/>
     * A valid auth token is required. The auth token can be obtained by calling
     * authenticate.
     *
     * @param token the authentication token for the logged in user.
     * @return a user object.
     * @throws KnickerException if the token is null or if there are any errors.
     */
    public static User user(AuthenticationToken token) throws KnickerException {
        if (token == null) {
            throw new KnickerException("Authentication token required.");
        }

        StringBuilder uri = new StringBuilder(ACCOUNT_ENDPOINT);
        uri.append("/user?auth_token=").append(token.getToken());

        return DTOBuilder.buildUser(Util.doGet(uri.toString()));
    }


    /**
     * Fetch all of the authenticated user’s word lists.
     * <p/>
     * This method requires a valid authentication token, which can be obtained
     * by calling the authenticate method.
     *
     * @param token authentication token.
     * @return all of the user's word lists.
     * @throws KnickerException if the token is null, or if there are any errors.
     */
    public static List<WordList> wordLists(AuthenticationToken token) throws KnickerException {
        if (token == null) {
            throw new KnickerException("Authentication token required.");
        }

        StringBuilder uri = new StringBuilder(ACCOUNT_ENDPOINT);
        uri.append("/wordLists?auth_token=").append(token.getToken());

        return DTOBuilder.buildWordLists(Util.doGet(uri.toString(), token));
    }


    /**
     * Fetch the authenticated user’s word lists.
     * <p/>
     * This method requires a valid authentication token, which can be obtained
     * by calling the authenticate method.
     *
     * @param token authentication token.
     * @param skip  number of lists to skip.
     * @param limit maximum number of results to return.
     * @return list of the user's word lists.
     * @throws KnickerException if the token is null, or if there are any errors.
     */
    public static List<WordList> wordLists(AuthenticationToken token, int skip, int limit) throws KnickerException {
        if (token == null) {
            throw new KnickerException("Authentication token required.");
        }

        StringBuilder uri = new StringBuilder(ACCOUNT_ENDPOINT);
        uri.append("/wordLists?auth_token=").append(token.getToken());
        uri.append("&limit=").append(limit);
        uri.append("&skip=").append(skip);

        return DTOBuilder.buildWordLists(Util.doGet(uri.toString()));
    }

}
