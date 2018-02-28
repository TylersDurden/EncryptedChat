import java.util.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;


/**<MESSENGER>
 * Crude Messenger in the following way: 
 * every string in a message is a hash of a .txt file containing that single word
 */
public class Messenger {
    
    private String path;


    public static void main(String[] args) {

        /**<ARGUMENT_SETUP:> TODO: <ADD> a Location Paramater
         *user@dock:/$java <Messenger> <-S> <-/path/to/File/for/Sending>
         *user@dock:/$java <Messenger> <-R> <-/path/to/File/for/Receiving> 
         */

        switch (args[0]) {
            case "-S":
                System.out.println("Sending a Message");
                Writer wr = new Writer(args[1], args[2]);
                break;
            case "-R":
                System.out.println("Receiving a Message");
                new Reader("user",args[1], args[2]);
                break;

        }
      
    }


    private static class Writer implements Runnable {

        private static String         argument;
        private static String         name;
        private static int            lines;
        private static Vector<String> message = new Vector<>();
        private static Vector<String> encryptedMessage = new Vector<>();
       
       public Writer(String path, String fname) {
            Writer.argument = path;
            Writer.name = fname;
            run();
            if (message.size() > 0) {
                System.out.println("Read contents from file. Writing outgoing message.");
                //Now Send the Message
                new ServeMessage(createEncryptedMessage());
            }
        }

        public void run() {
            BufferedReader br = null;
            Vector<String> content = new Vector<>();
            /** Grab message content from example.txt */
            try {
                br = new BufferedReader(new FileReader(Paths.get(Writer.argument, Writer.name).toFile()));
                String s;
                while ((s = br.readLine()) != null) {
                    content.add(s);
                }
                Writer.lines = content.size();
                Vector<String> words = new Vector<>();
                for (String ln : content.toString().split("\n")) {
                    for (String word : ln.split(" ")) {
                        words.add(word);
                    }
                }
                System.out.println(lines + " lines in message.");
                System.out.println(words.size() + " words found in file.");
                Writer.message = words;
            } catch (FileNotFoundException e) {
                System.out.println("Couldn't Find file");
            } catch (IOException e) {
                System.out.print("Exception: " + e.getMessage());
            }
        }


        Vector<String> createEncryptedMessage() {
            /**<|//\\//\\HASH//\\//\\|//HASH\\|//\\//\\HASH//\\//|:~_Get.Hash.Data_~:>*/
           
                Vector<String> hashes = new Vector<String>();
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                Vector<byte[]> words = new Vector<>();
                if (message.size() > 0) {
                    for (String s : message) {
                        words.add(s.getBytes(StandardCharsets.UTF_8));
                        
                    }
                } else {System.out.println("No Words found");}
                int index = 0;
                for (byte[] b : words) {
                    String hash = bytesToHex(digest.digest(b));
                    hashes.add(hash);
                    index++;
                }
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Bad Crypto Settings.");
            }
            System.out.println(hashes.size()+" words hashed");
            return hashes;
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


    }
    
    private static class ServeMessage{
        
        private Vector<String> content = new Vector<>();
        private Vector<byte[]> packets = new Vector<>();
        private Socket sock;
        
        private ServeMessage(Vector<String>messageContents){
            content = messageContents;

            createPacketData();
            try{sendMessage();}
            catch(Exception e){e.printStackTrace();}
            
            boolean noresponse = true;
            Vector<String> responses = new Vector<>();
            // After Message has been sent, wait for a reply 
            while(noresponse){
                String ans = waitForReply();
                if(ans.compareTo("")!=0){responses.add(ans);}
            }
            
            
        }
        
        /** <Create_[PACKETS]> */
        void createPacketData(){
            for(String chunk : content){
                packets.add((chunk+" ").getBytes());
            }
        }
        
        /** Send the packets */
        void sendMessage()throws Exception {
            sock = new Socket("localhost",6789);
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
            for(byte[] packet : packets){
                dos.write(packet);
            }
            sock.close();
            //Debug Print out
            System.out.print(packets.size()+" encrypted packets sent");
        }
        
        
        /** get any replies to message */
        String waitForReply(){
            String response = "";
            
            return response;
        }
        
    }

    private static class Reader implements Runnable {
        
        private Vector<String> encryptedAnswers = new Vector<>();
        private Vector<String> decryptedAnswers = new Vector<>();
        private boolean reading;
        private String userName;
        private String senderName;
        private ServerSocket srvsocket;
        
        public Reader(String uname, String path, String outputlog) {
            
            try{srvsocket = new ServerSocket(6789);}
            catch(IOException e){}
            
            userName = uname;
            reading = true;
            run();

        }
        
        public void run() {
            
            new Linguist();
            
            while(reading){
                try{
                    Socket tmpsock = srvsocket.accept();
                    senderName = tmpsock.getInetAddress().toString();
                    BufferedReader in = new BufferedReader(new InputStreamReader(tmpsock.getInputStream()));
                    DataOutputStream dos = new DataOutputStream(tmpsock.getOutputStream());
                    String response = in.readLine();
                    for(String word : response.split(" ")){encryptedAnswers.add(word);}
                    System.out.println(encryptedAnswers.size()+" encrypted Answers Recieved from "+senderName);
                    int SEQ = 0;
                    for(String encw : encryptedAnswers){SEQ++;System.out.println("["+SEQ+"]"+encw);}
                    
                    /** Retreive words from <Linguist.hashdict>*/
                    for(String hash : encryptedAnswers){
                        
                        for(Map.Entry<String,Vector<String>>entry:Linguist.hashdict.entrySet()){
                            int indice = 0;
                            String wordlist = entry.getKey();
                            for(String keys : entry.getValue()){
                                if(hash.compareTo(keys)==0){
                                    decryptedAnswers.add(Linguist.dictionary.get(wordlist).get(indice));
                                    }
                                indice += 1; 
                            }
                        }
                    }
                    
                    if(decryptedAnswers.size()>0){
                        int words = 0;
                        for(String msgrecvd: decryptedAnswers){
                            System.out.print(msgrecvd+" ");
                            words ++;
                            if(words%10==0){System.out.println("\n");}
                        }
                    }
                    
                    tmpsock.close();
                    }
                catch(IOException e){}
                
                
            }
            
            
        }

    }

}
