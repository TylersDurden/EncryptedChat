
import java.util.*;
import java.io.*;
import java.security.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;


/** <?ET_TU_BRUTE?>*/
public class Brutus implements Runnable{ 
    
    private static Linguist lang;
    private static Vector<String> packetdata;
    private static Vector<String> results;
    private static Map<String,Vector<String>> trsltr = new HashMap<>(); 
    private static Map<Vector<String>,Vector<String>> hashed = new HashMap<>();
    
    public Brutus(Vector<String>encryptedData){
        //Initialize Brutus
        initializeBrutus();
        //
        Brutus.packetdata = encryptedData;
        //
        Brutus.lang =  new Linguist();
        // Now run it
        System.out.println(" --  Decrypting Message  -- ");
        run();
        System.out.print(Brutus.results.toString());
    }
    
    /** TODO: Create a map of Hashes/Word to translate between
    known words found in <Linguist.dictionary>! */
    void initializeBrutus(){
        for(Map.Entry<String,Vector<String>> entry : 
                        Brutus.lang.dictionary.entrySet()){
            Vector<String> encwlist = new Vector<String>();
            for(String word : entry.getValue()){
                encwlist.add(flashHash(word));
            }
            Brutus.trsltr.put(entry.getKey(),encwlist);
            Brutus.hashed.put(encwlist,entry.getValue());
        }
        
        
    }
    
    String flashHash(String word){
        String hash = null;
        
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte [] b = word.getBytes(StandardCharsets.UTF_8);
            StringBuffer hstr = new StringBuffer();
            byte [] bs = md.digest(b);
            for(byte byt : bs){
                String hex = Integer.toHexString(0xff & byt);
                if(hstr.length()==1)
                hstr.append('0');
                hstr.append(hex);
                }
                hash = hstr.toString();
            }
        catch(NoSuchAlgorithmException e){System.out.println("Encryption Failure");}
        
        
        return hash;
    }



    /** TODO: Turn received byte arr packet data into their encrypted
    strings, and then lookup this hashval from <etubruTable>
    to translate into a word. Package the words together in series
    as Vector to preserve correct order of words in message!*/    
    public void run(){
        Vector<String>result = new Vector<>();
        /** TODO: Go from Packets on outer loop instead */
        Brutus.results = result;    
    }
    
    public static void main(String[]args){}
    
}
