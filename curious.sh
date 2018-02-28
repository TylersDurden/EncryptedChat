#!/bin/bash
# Compile java program 
javac Messenger.java
# send example.txt file through encrypted chat to whoever is at other end
java Messenger -S /projects/Data/EncryptedChat-v0.1/src example.txt 
