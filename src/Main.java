
import java.util.ArrayList;
import java.util.List;
import net.jeremybrooks.knicker.Knicker;
import net.jeremybrooks.knicker.dto.Phrase;
import net.jeremybrooks.knicker.dto.Definition;
import net.jeremybrooks.knicker.Knicker.SourceDictionary;
import net.jeremybrooks.knicker.dto.AuthenticationToken;
import net.jeremybrooks.knicker.dto.FrequencySummary;
import net.jeremybrooks.knicker.dto.Pronunciation;
import net.jeremybrooks.knicker.dto.Related;
import net.jeremybrooks.knicker.dto.Word;
import net.jeremybrooks.knicker.dto.WordList;
import net.jeremybrooks.knicker.dto.WordListWord;
import net.jeremybrooks.knicker.logger.KnickerLogger;
import net.jeremybrooks.knicker.logger.StdoutLogger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jeremyb
 */
public class Main {

    public static void main(String[] args) throws Exception {
	System.setProperty("WORDNIK_API_KEY", "5458ce49330219f23e0020790810f29b3818096f5fbbf8560");

	KnickerLogger.setLogger(new StdoutLogger());

//	System.out.println(Knicker.lookup("lookup").toString());
//	System.out.println(Knicker.lookup("test", true, false).toString());
//
//	List<Phrase> list = Knicker.biGram("Christmas", 0);
//	for (Phrase b : list) {
//	    System.out.println(b.toString());
//	}

//	List<Definition> d = Knicker.definitions("cat", 0, false, null, null);
//	System.out.println("Got " + d.size() + " defintions");
//	for (Definition def : d) {
//	    System.out.println(def.toString());
//	}

	List<Knicker.PartOfSpeech> pos = new ArrayList<Knicker.PartOfSpeech>();
	List<Knicker.SourceDictionary> sd = new ArrayList<Knicker.SourceDictionary>();
	List<Knicker.RelationshipType> rt = new ArrayList<Knicker.RelationshipType>();

	
//	pos.add(Knicker.PartOfSpeech.verb);
//	pos.add(Knicker.PartOfSpeech.adverb_and_preposition);
//
//	sd.add(SourceDictionary.century);
//
//	rt.add(Knicker.RelationshipType.antonym);
//	rt.add(Knicker.RelationshipType.cross_reference);
	
//	List<Related> list = Knicker.related("abate", 0, false, pos, rt, sd);
//	System.out.println("got " + list.size());
//	for (Related r : list) {
//	    System.out.println(r.toString());
//	}

//	FrequencySummary fs = Knicker.frequency("cat", false);
//	System.out.println(fs.toString());


//	System.out.println(Knicker.autocomplete("xix"));

//	List<Word> words = Knicker.randomWords(true, 1000);
//	System.out.println("got " + words.size());
//	for (Word w : words) {
//	    System.out.println(w);
//	}

	AuthenticationToken token = Knicker.authenticate("jeremybrooks", "vP)WYKigC2FM[yVA8nfe");

//	Knicker.status();
	
//	List<WordList> list = Knicker.getLists(token);
//	for (WordList l : list) {
//	    System.out.println(l.toString());
//	}

//	WordList wordList = Knicker.createList(token, "test3", "test list3", Knicker.ListType.PUBLIC);
//	System.out.println(wordList);

//	Knicker.addWordToList(token, "test--21", "hello");

	Knicker.deleteWordFromList(token, "test--21", "hello");

//	List<WordListWord> list = Knicker.getWordsFromList(token, "test--21");
//	for (WordListWord w : list) {
//	    System.out.println(w);
//	}

//	Knicker.deleteList(token, "test3");
    }
}
