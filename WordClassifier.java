import java.util.*;
import java.util.regex.*;

public class WordClassifier {

    Map<String, Vector<String>> wordbank = new HashMap<>();
    public static Vector<String> types   = new Vector<>();

    public WordClassifier(Vector<String> wordObjs, boolean areknown) {
        // Define each possible word TYPE 
        types.add("NOUN");
        types.add("ADJECTIVE");
        types.add("VERB");
        types.add("PRONOUN");
        types.add("ARTICLE");
        types.add("PREPOSITION");
        /**TODO: I get zeo for the size of the KNOWN word objects.
        Fix that first, and then handle classification of known word objects.
        After that is working (because there are typically more known words 
        than unknown thankfully, figure out the process of starting to try
        and classify/identify unknown words. */
        System.out.println("Classifying "+wordObjs.size()+" word objects");
        //Fill fields for each word based on whether known/unknown 
        if (areknown) {for (String knownWord : wordObjs) {assignWeightsToKnownWords(knownWord);}}
        else{for(String possibleWord : wordObjs){new Word(possibleWord, false, new Vector<Double>());}}
    }

    //wordbank = Linguist.dictionary 

    public void run() {
    }

    void assignWeightsToKnownWords(String word) {
        System.out.println("Possible TYPE values for word: "+types);
        System.out.println("Enter Type: ");

    }


    public static class Word {

        int                   size;
        // Unknown words
        Vector<String>        closest    = new Vector<String>();
        //known word properties
        public Vector<Double> weights    = new Vector<Double>();
        double                complexity = 0.0;
        boolean               isProperNoun;
        boolean               isAlpha;
        boolean               isAlphanumeric;

        public Word(String text, boolean hasWts, Vector<Double> weights) {
            size = text.length();
            closest = Linguist.dictionary.get(text.split("")[0].toUpperCase());
            if (hasWts) {
                weights = createWeights(weights);
            } else {
                try {
                    System.out.println(closest.size() + " possible matches for: " + text);
                } catch (NullPointerException e) {
                    complexity += 0.25;
                }
            }


        }

        Vector<Double> createWeights(Vector<Double> weights) {
            Vector<Double> correctedWeights = new Vector<Double>();

            return correctedWeights;
        }

        /** <REGEX_OPERATIONS>
         *  .   Any character (may or may not match line terminators)
            \d	A digit: [0-9]
            \D	A non-digit: [^0-9]
            \s	A whitespace character: [ \t\n\x0B\f\r]
            \S	A non-whitespace character: [^\s]
            \w	A word character: [a-zA-Z_0-9]
            \W	A non-word character: [^\w]
         */
        static Integer extract(String str) {
            Integer dig = 0;
            Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(str);
            while (m.find()) {
                return Integer.parseInt(m.group(1));
            }
            return dig;
        }
    }


    public static void main(String[] args) {
        
        //Now create the Linguist
        new Linguist();
    }
}
