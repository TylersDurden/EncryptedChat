import java.util.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class Linguist implements Runnable {

    public static final float                 start        = System.currentTimeMillis();

    public static final String[]              specialChars = {"!", "#", "$", "%", "&", "'", "(", ")", 
                                                              "*", "+", ",", "-", ".", "/", ":", ";", 
                                                              "<", "=", ">", "?", "@", "~", "`", "{", "}","["};

    public static final String[]              letters      = {"a", "b", "c", "d", "e", "f", "g", "h",
                                                              "i", "j", "k", "l", "m", "n", "o", "p", 
                                                              "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
                                                               
    public static final int []                numbers     = {0,1,2,3,4,5,6,7,8,9}; 

    public static Vector<String>              uppers       = new Vector<>();
    public static Vector<String>              lowers       = new Vector<>();
    /** DICTIONARY */
    public static Vector<String>              awords       = new Vector<>();
    public static Vector<String>              bwords       = new Vector<>();
    public static Vector<String>              cwords       = new Vector<>();
    public static Vector<String>              dwords       = new Vector<>();
    public static Vector<String>              ewords       = new Vector<>();
    public static Vector<String>              fwords       = new Vector<>();
    public static Vector<String>              gwords       = new Vector<>();
    public static Vector<String>              hwords       = new Vector<>();
    public static Vector<String>              iwords       = new Vector<>();
    public static Vector<String>              jwords       = new Vector<>();
    public static Vector<String>              kwords       = new Vector<>();
    public static Vector<String>              lwords       = new Vector<>();
    public static Vector<String>              mwords       = new Vector<>();
    public static Vector<String>              nwords       = new Vector<>();
    public static Vector<String>              owords       = new Vector<>();
    public static Vector<String>              pwords       = new Vector<>();
    public static Vector<String>              qwords       = new Vector<>();
    public static Vector<String>              rwords       = new Vector<>();
    public static Vector<String>              swords       = new Vector<>();
    public static Vector<String>              twords       = new Vector<>();
    public static Vector<String>              uwords       = new Vector<>();
    public static Vector<String>              vwords       = new Vector<>();
    public static Vector<String>              wwords       = new Vector<>();
    public static Vector<String>              xwords       = new Vector<>();
    public static Vector<String>              ywords       = new Vector<>();
    public static Vector<String>              zwords       = new Vector<>();
    public static Map<String, Vector<String>> dictionary   = new HashMap<>();
    public static Vector<String>              query        = new Vector<String>();
    public static Vector<String>              knownWords   = new Vector<>();
    
    public static Map<String,Vector<String>> hashdict = new HashMap<>();
    
    /** <Be_Cunning> */
    public Linguist() {
        System.out.println("\n___________________________________");
        System.out.println("|/ / / /{LINGUIST_CREATED} \\ \\ \\ \\|");
        System.out.println("___________________________________");
        run();
        //System.out.println("Finished in " + stopTimer() + " seconds");
    }


    public static void main(String[] args) {

        //Now create the Linguist
        new Linguist();
        new Interpreter(new Vector<>());

    }

    public float stopTimer() {
        return (System.currentTimeMillis() - start);
    }

    public void run() {
        /**
         * <[1]>  Fill word bank
         */
        Linguist.dictionary.put("A", digestWordList("A Words.csv"));
        Linguist.dictionary.put("B", digestWordList("B Words.csv"));
        Linguist.dictionary.put("C", digestWordList("C Words.csv"));
        Linguist.dictionary.put("D", digestWordList("D Words.csv"));
        Linguist.dictionary.put("E", digestWordList("E Words.csv"));
        Linguist.dictionary.put("F", digestWordList("F Words.csv"));
        Linguist.dictionary.put("G", digestWordList("G Words.csv"));
        Linguist.dictionary.put("H", digestWordList("H Words.csv"));
        Linguist.dictionary.put("I", digestWordList("I Words.csv"));
        Linguist.dictionary.put("J", digestWordList("J Words.csv"));
        Linguist.dictionary.put("K", digestWordList("K Words.csv"));
        Linguist.dictionary.put("L", digestWordList("L Words.csv"));
        Linguist.dictionary.put("M", digestWordList("M Words.csv"));
        Linguist.dictionary.put("N", digestWordList("N Words.csv"));
        Linguist.dictionary.put("O", digestWordList("O Words.csv"));
        Linguist.dictionary.put("P", digestWordList("P Words.csv"));
        Linguist.dictionary.put("Q", digestWordList("Q Words.csv"));
        Linguist.dictionary.put("R", digestWordList("R Words.csv"));
        Linguist.dictionary.put("S", digestWordList("S Words.csv"));
        Linguist.dictionary.put("T", digestWordList("T Words.csv"));
        Linguist.dictionary.put("U", digestWordList("U Words.csv"));
        Linguist.dictionary.put("V", digestWordList("V Words.csv"));
        Linguist.dictionary.put("W", digestWordList("W Words.csv"));
        Linguist.dictionary.put("X", digestWordList("X Words.csv"));
        Linguist.dictionary.put("Y", digestWordList("Y Words.csv"));
        Linguist.dictionary.put("Z", digestWordList("Z Words.csv"));
        System.out.println("Dictionaries Initialized");

        for (String let : letters) {
            uppers.add(let.toUpperCase());
        }
        
        encryptDictionary();

    }
    
    private static String bytesToHex(byte[] hash) {
            StringBuffer hString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hString.length() == 1)
                    hString.append('0');
                hString.append(hex);
            }
            return hString.toString();
        }
        
        void encryptDictionary(){
            for(String letter : letters){
            Vector<String> cryptdic = new Vector<>();
            for(String word : Linguist.dictionary.get(letter.toUpperCase())){
                try{
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    Vector <byte[]> bs = new Vector<>();
                    cryptdic.add(bytesToHex(md.digest(word.getBytes(StandardCharsets.UTF_8))));
                }catch(NoSuchAlgorithmException e){e.printStackTrace();}
                
            }
            Linguist.hashdict.put(letter.toUpperCase(),cryptdic);
        }
        }

    Vector<String> digestWordList(String wordlist) {
        File wordrepo = Paths.get("/projects/Data/EncryptedChat-v0.1/src/Vocabulary/Words/EOWL-v1.1.2/CSV Format", wordlist).toFile();
        Vector<String> digest = new Vector<>();
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(wordrepo));
            while ((line = br.readLine()) != null) {
                digest.add(line);
            }
            br.close();
            //Fill the Linguist WORDBANK 
            if (wordlist.split(" ")[0].compareTo("A") == 0) {Linguist.awords = digest;}
            if (wordlist.split(" ")[0].compareTo("B") == 0) {Linguist.bwords = digest;}
            if (wordlist.split(" ")[0].compareTo("C") == 0) {Linguist.cwords = digest;}
            if (wordlist.split(" ")[0].compareTo("D") == 0) {Linguist.dwords = digest;}
            if (wordlist.split(" ")[0].compareTo("E") == 0) {Linguist.ewords = digest;}
            if (wordlist.split(" ")[0].compareTo("F") == 0) {Linguist.fwords = digest;}
            if (wordlist.split(" ")[0].compareTo("G") == 0) {Linguist.gwords = digest;}
            if (wordlist.split(" ")[0].compareTo("H") == 0) {Linguist.hwords = digest;}
            if (wordlist.split(" ")[0].compareTo("I") == 0) {
                Linguist.iwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("J") == 0) {
                Linguist.jwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("K") == 0) {
                Linguist.kwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("L") == 0) {
                Linguist.lwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("M") == 0) {
                Linguist.mwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("N") == 0) {
                Linguist.nwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("O") == 0) {
                Linguist.owords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("P") == 0) {
                Linguist.pwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("Q") == 0) {
                Linguist.qwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("R") == 0) {
                Linguist.rwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("S") == 0) {
                Linguist.swords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("T") == 0) {
                Linguist.twords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("U") == 0) {
                Linguist.uwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("V") == 0) {
                Linguist.vwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("W") == 0) {
                Linguist.wwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("X") == 0) {
                Linguist.xwords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("Y") == 0) {
                Linguist.ywords = digest;
            }
            if (wordlist.split(" ")[0].compareTo("Z") == 0) {
                Linguist.zwords = digest;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return digest;
    }


    private static class Interpreter implements Runnable {

        private static Vector<String> words = new Vector<>();
        private static Double         difficulty;
        private static int            size;

        Interpreter(Vector<String> statement) {
            Interpreter.words = statement;
            Interpreter.size = statement.size();
            if (Interpreter.size > 0) {
                processQuery();
                run();
            } else {
                System.out.println("{DEBUG MODE}");
                //Grab word objects from a text file 
                analyzeTextFile("/projects/Data/src", "example.txt");
                Vector<String> w3rds = inspectTextFile(Linguist.query);
                // Isolate Words of Interest
                Interpreter.words = w3rds;
                // Classify Known words (if any)
                WordClassifier intel = new WordClassifier(Linguist.knownWords,true);
                // Unknown word objects get handled seperately. 
                Vector <String> unidentifiedWordObjects = processQuery();
                //Classify words (if possible), false because they aren't known words
                WordClassifier wcUWO = new WordClassifier(unidentifiedWordObjects,false);
                //
                System.out.println(unidentifiedWordObjects.toString());
                //
                Linguist.deepRead();
                
                
            }

        }
        
        
        
        /** <HANDLE_RAW_TEXT>*/
        Vector<String> processQuery() {
            System.out.println("Trying to analyze " + Interpreter.words.size() + " words.");
            Vector<String> wordsfound = new Vector<>();
            Vector<String> newWords = new Vector<>();
            for (String word : Interpreter.words) {
                if (word.compareTo(" ") == 0) {
                    continue;
                }
                if (checkWordBank(word)) {
                    wordsfound.add(word);
                } else {
                   newWords.add(word);
                }

            }
            
            Linguist.knownWords = wordsfound;
            if (wordsfound.size() != words.size()) {
                for (String unknown : newWords) {
                    String key = unknown.split("")[0].toUpperCase();
                    try {
                        Linguist.dictionary.get(key).add(unknown);
                    } catch (NullPointerException e) {
                    }
                }
                System.out.println("Identified "+knownWords.size()+" known words in text.");
                System.out.println("Found " + newWords.size() + " new words to Analyze.");
                
            }
            return newWords;
        }

        boolean checkWordBank(String word) {
            int index = 0;
            // return -1 if word isn't there. Also add it to wordbank 
            String let = word.split("")[0];
            //set initial boolean to false
            boolean isWord = false;
            if (Linguist.uppers.contains(let.toUpperCase())) {
                for (String opt : Linguist.dictionary.get(let.toUpperCase())) {
                    if (word.toLowerCase().compareTo(opt) == 0) {
                        isWord = true;
                    }
                }
            }

            return isWord;
        }

        public void run() {
            boolean running = true;
            System.out.println("Please Enter 1: and then a piece of text for Analysis.");
            System.out.println("Or Enter 2:/path/to/your./file.txt for Analysis.");
            while (running) {
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();
                System.out.print(input.split(":")[0]);
                try {
                    switch (Integer.parseInt(input.split(":")[0])) {
                        case 1:
                            inspectTextFile(analyzeLineOfText(input.split(":")[1]));
                            break;
                        case 2:
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Improperly formatted input.");
                }
                sc.close();
                running = false;
            }

        }

        Vector<String> analyzeLineOfText(String input) {
            System.out.println("Analyzing:\n" + input);
            Vector<String> line = new Vector<>();
            for (String s : input.split(" ")) {
                line.add(s);
            }
            new Interpreter(line);
            return line;
        }

        Vector<String> analyzeTextFile(String path, String fname) {
            BufferedReader br = null;
            Vector<String> content = new Vector<>();
            try {
                File f = Paths.get(path, fname).toFile();
                br = new BufferedReader(new FileReader(f));
                String line;
                while ((line = br.readLine()) != null) {
                    content.add(line);
                }
                br.close();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("File location invalid. You Entered:");
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Now have the content of the text file as a vector
            Linguist.query = content;
            //Inspect it
            return content;
        }
    }

    /** */
    public static Vector<String> inspectTextFile(Vector<String> input) {
        Vector<String> result = new Vector<>();
        int nsent = 0;
        for (String word : Linguist.query.toString().split(" ")) {
            if (Arrays.binarySearch(word.split(""), ".") != -1) {
                nsent += 1;
            } else {
                result.add(word);
            }
        }
        System.out.println("Identified " + nsent + " sentences, made up of " + result.size() + " words");
    
        return result;
    }

    static void deepRead(){
        //Begin with word size
        //Do letter frequency analysis 
        //Begin analyzing word choice
    }
    
    /** Use .txt file inputs and use this method to allow for automated user
    entry to go through words and assign various weights. Slowly work through
    dictionary this way.  */
    static void useTextSampleToAssignWeights(){
        
    }
    
}
