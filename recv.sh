#!/bin/bash
#Compile Programs for receiving a message
javac Messenger.java
javac Linguist.java 
# Receieve messages to output.txt
touch output.txt
java Messenger -R /projects/Data/EncryptedChat-v0.1/src > output.txt
# Validate file 
touch verify.txt
cksum output.txt > verify.txt
