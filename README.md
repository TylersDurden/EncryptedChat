# EncryptedChat
I want to work on some of my network programming skills, so I came up with this 
little project. 

Essentially this will be a point-to-point encrypted communication channel, with no
persistence of memory (conversations are erased, client locations go unused). 
If two people have the programs installed it should be as easy as one client 
entering the project directory and launching the **curious.sh** script. Then waiting
for the other client to do the same. While the connection is alive the conversation
history is visible, but when one end breaks the connection everything is erased on 
both ends. 

# Encryption Mechanism 
So I'm choosing a very strange but unique mechanism for encryption, because it is both
simple but I believe also very effective as essentially rendering all communications 
highly secure and very easy to decipher with correct software. 

Essentially what I've done is to take the EOWL (**Large English World List**) and when
the messenger recieves words for input, it confirms that the word exists in dictionary
and assigns a packet constructed of the SHA-256 hash value of that single word. Each
encrypted word is then sent as a packet.

Because the recipient also has the Messenger software, they can very easily associate
hash values with dictionary words, translating the message (hopefully in near-real time). 
The unencrypted text is only printed to terminal, and the true values are never printed 
or saved anywhere. 

# Fleeting Connections 


