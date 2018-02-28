#cd /projects/Data/src
# Compile java program 
javac Messenger.java
# create dumplogs for hashing 
#touch user1m1s1.txt 
#touch m0digest.txt
# Run program, sending message from example.txt 
#out = "/projects/Data/EncryptedChat-v0.1/src./example.txt"
#echo 'Enter message and press [ENTER]:'
#read message > example.txt
java Messenger -S /projects/Data/EncryptedChat-v0.1/src example.txt 
