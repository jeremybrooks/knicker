# Knicker
## A Java interface to the Wordnik API.

You can use Knicker to add dictionary lookup capabilities to your own Java
programs. Knicker has been designed to be simple and straightforward to use. It
does not require any third party libraries, just Java 1.6 or higher.

Version 2.x of Knicker supports version 4 of the Wordnik API.

Version 1.x of Knicker supports version 3 of the Wordnik API.

# Using Knicker
To use Knicker, you will need to obtain a Wordnik API key. More information
about the Wordnik API can be found on their developer site at
http://developer.wordnik.com

Knicker looks for the system property WORDNIK_API_KEY, so you need to set it
before you try to use Knicker:
System.setProperty("WORDNIK_API_KEY", <your API key>);

Then just call the API methods using the Knicker class:

WordApi.lookup("zebra");

The Wordnik API is divided into different sections based on the API endpoint.
Knicker, for the most part, follows this logical arrangement. If you want
to call methods on the account endpoint, use the AccountApi class, for methods
on the word endpoint, use the WordApi class, and so on.

Here's a quick sample showing how to check the API key status and look up the
definition of a word:


import java.util.List;
import net.jeremybrooks.knicker.AccountApi;
import net.jeremybrooks.knicker.WordApi;
import net.jeremybrooks.knicker.dto.Definition;
import net.jeremybrooks.knicker.dto.TokenStatus;

public class TestKnicker {


    public static void main(String[] args) throws Exception {
        // use your API key here
        System.setProperty("WORDNIK_API_KEY", "your api key");


	// check the status of the API key
	TokenStatus status = AccountApi.apiTokenStatus();
	if (status.isValid()) {
	    System.out.println("API key is valid.");
	} else {
	    System.out.println("API key is invalid!");
	    System.exit(1);
	}


	// get a list of definitions for a word
	List<Definition> def = WordApi.definitions("siren");
	System.out.println("Found " + def.size() + " definitions.");

	int i = 1;
	for (Definition d : def) {
	    System.out.println((i++) + ") " + d.getPartOfSpeech() + ": " + d.getText());
	}

    }
}

# Maven
Knicker is available from Maven Central. Just add this dependency to your pom file:
    <dependency>
        <groupId>net.jeremybrooks</groupId>
        <artifactId>knicker</artifactId>
        <version>2.4.1</version>
    </dependency>


# Problems?
Issues can be reported on the Knicker Issues page: https://github.com/jeremybrooks/knicker/issues

## KNOWN ISSUES
Version 2.3.2 (July 25, 2012)
* The limit parameter on search results may not be respected. This seems to be an issue on the Wordnik side.
* Using multiple dictionaries when looking up definitions may cause an error. Use the dictionary SourceDictionary.all or make multiple calls to single dictionaries. This seems to be an issue on the Wordnik side.

# Changes
Version 2.4.1 (October 22, 2013)
* Updated the PartOfSpeech enumeration to reflect the current list of Wordnik parts of speech
* Fix the string built for certain API queries to ensure reliable results
* Commented out two tests that are known to break currently