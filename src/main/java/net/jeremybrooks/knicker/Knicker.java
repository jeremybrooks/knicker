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
 * Base class for the Wordnik API classes.
 * <p/>
 * <p>This class defines the enum constants that are needed by the API classes.
 * Use the <code>AccountApi</code>, <code>WordApi</code>, <code>WordListApi</code>,
 * and <code>WordsApi</code> classes to access Wordnik.</p>
 *
 * @author Jeremy Brooks
 */
public abstract class Knicker {

	// API endpoints
	protected static final String ACCOUNT_ENDPOINT = "https://api.wordnik.com/v4/account.xml";
	protected static final String WORD_ENDPOINT = "http://api.wordnik.com/v4/word.xml";
	protected static final String WORD_LIST_ENDPOINT = "http://api.wordnik.com/v4/wordList.xml";
	protected static final String WORD_LISTS_ENDPOINT = "http://api.wordnik.com/v4/wordLists.xml";
	protected static final String WORDS_ENDPOINT = "http://api.wordnik.com/v4/words.xml";


	/**
	 * Source dictionaries supported by the Wordnik API.
	 * <p/>
	 * <ul>
	 * <li>ahd: The American Heritage Dictionary. Probably the closest thing to a good traditional dictionary. Has most modern words.</li>
	 * <li>century: An older work (last updated in 1910), and with a much more scholarly bent.</li>
	 * <li>cmu: Can be used for audio pronunciations; no text definitions are available.</li>
	 * <li>macmillan: Can be used for audio pronunciations; no text definitions are available.</li>
	 * <li>wiktionary: A user contributed work, so it has a lot of neologisms. It's sometimes spotty, but great for current stuff and overall pretty good.</li>
	 * <li>webster: The GNU collaborative dictionary, aka the GCIDE. It's based on a public-domain 1911 webster's dictionary, but has been significantly updated. More information here: http://www.ibiblio.org/webster/</li>
	 * <li>wordnet: A 'lexical database' developed at Princeton. Contains "glosses" (aka, short definitions) for most words. More information here: http://wordnet.princeton.edu/</li>
	 * </ul>
	 */
	public static enum SourceDictionary {
		all,
		ahd,
		century,
		cmu,
		macmillan,
		wiktionary,
		webster,
		wordnet
	}


	/**
	 * Parts of speech supported by the Wordnik API.
	 * <p/>
	 * Note: The underscores will be replaced with dashes when using these
	 * values as parameters to the Wordnik API. For example,
	 * "noun_and_verb" becomes "noun-and-verb".
	 */
	public static enum PartOfSpeech {
		noun,
		verb,
		adjective,
		adverb,
		idiom,
		article,
		abbreviation,
		preposition,
		prefix,
		interjection,
		suffix,
		conjunction,
		past_participle,
		imperative,
		noun_plural,
		proper_noun_plural,
		verb_intransitive,
		proper_noun,
		pronoun,
		verb_transitive,
		proper_noun_posessive,
		noun_posessive,
		affix,
		auxiliary_verb,
		definite_article,
		family_name,
		given_name,
		phrasal_prefix
	}

	/**
	 * Relationship types supported by the Wordnik API.
	 * <p/>
	 * <ul>
	 * <li>synonym: words with very similar meanings (beautiful: pretty)</li>
	 * <li>antonym: words with opposite meanings (beautiful: ugly)</li>
	 * <li>form: words with the same stem (dog: dogs, dogged, doggish)</li>
	 * <li>hyponym: a word that is more specific (‘chair’ is a hyponym of ‘furniture’)</li>
	 * <li>variant: a different spelling for the same word: color and colour are variants</li>
	 * <li>verb-stem: Pass in a verb like “running” and get the stem, which is “run.”</li>
	 * <li>verb-form: Pass in “run” and find different verb forms, like “running”, “ran”, and “runs.”</li>
	 * <li>cross-reference: a related word; (bobcat: lynx)</li>
	 * <li>same-context: Shows relationships between words which are often used in the same manner. For instance “cheeseburger” and “pizza” are often used the same way. Both also taste great.</li>
	 * </ul>
	 * <p/>
	 * Note: The underscores will be replaced with dashes when using these
	 * values as parameters to the Wordnik API. For example,
	 * "verb_stem" becomes "verb-stem".
	 */
	public static enum RelationshipType {
		synonym,
		antonym,
		equivalent,
		related_word,
		rhyme,
		etymologically_related_term,
		form,
		hypernym,
		inflected_form,
		primary,
		hyponym,
		variant,
		verb_stem,
		verb_form,
		cross_reference,
		same_context
	}


	/**
	 * List types supported by Wordnik.
	 */
	public static enum ListType {
		PUBLIC,
		PRIVATE
	}

	public static enum TypeFormat {
		ahd,
		arpabet,
		gcide_diacritical,
		IPA
	}

	public static enum SortBy {
		alpha,
		count
	}

	public static enum SortDirection {
		asc,
		desc
	}

	public static enum ExpandTerms {
		synonym,
		hypernym
	}
}
