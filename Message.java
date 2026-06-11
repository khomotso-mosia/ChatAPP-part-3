/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;

/**
 *
 * @author mosia
 */
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

//This class represents a single chat message in the ChatApp.
// It handles everything related to a message — creating it, validating it,
// hashing it, and deciding whether to send, store, or discard it.
public class Message{
    //Variables.
    private String messageID;
    private int numMessagesSent;
    private String recipientNumber;
    private String messageContent;
    private String messageHash;
 
    // Static counter shared across all Message instances to track total messages sent.
    private static int totalMessagesSent = 0;
   //--------------------------------------------------------------------------
   //CONSTRUCTOR
   //When a message object is created ,a constructor runs automatically.
   //Only the recipient's number amd the meassage text anything else is automatically generated.
   //---------------------------------------------------------------------------
    public Message(String recipient, String content) {
        this.recipientNumber = recipient;
        this.messageContent = content;
        this.messageID = generateMessageID();
        this.numMessagesSent = totalMessagesSent;
        this.messageHash = createMessageHash();
    }
 
    // -----------------------------------------------------------------------
    // 1. ID GENERATION
    // Generates a unique 10-character message ID using a random number.
    //  Every message needs a unique ID so we can tell messages apart.
    // -----------------------------------------------------------------------
    private String generateMessageID() {
        // Produces a random number between 0000000000 and 9999999999 (10 digits)
        long randomNum = (long) (Math.random() * 10_000_000_000L);
        return String.format("%010d", randomNum);
    }
 
    // -----------------------------------------------------------------------
    // 2. VALIDATION — Check if Message ID valid (must be 10 characters or less)
    //Returns true is valid and returns false if not.
    // -----------------------------------------------------------------------
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }
 
    // -----------------------------------------------------------------------
    // 3. VALIDATION — Recipient cell number is valid
    //    Must start with '+' and be no longer than 13 characters
    //    Must contain international code .
    // -----------------------------------------------------------------------
    public String checkRecipientCell() {
        if (this.recipientNumber != null
                && this.recipientNumber.startsWith("+")
                && this.recipientNumber.length() <= 13) {
            return "Cell phone number successfully captured.";
        }
        // if the is an error this message is displayed
        return "Cell phone number incorrectly formatted or does not contain international code.";
    }
 
    // -----------------------------------------------------------------------
    // 4. MESSAGE HASH
    //    Format: first2charsOfID : messageCounter : firstWordLastWord
    //    Everything is converted to uppercase.
    //    Example ID="00123", count=1, message="Hi there friend"
    //    → "00:1:HIFRIEND"
    // -----------------------------------------------------------------------
    public String createMessageHash() {
        if (this.messageID == null || this.messageContent == null
                || this.messageContent.trim().isEmpty()) {
            return "";
        } 
        //Take only the first 2 characters of the ID
        String firstTwo = this.messageID.substring(0, 2);
        
        //Split the message into individual words using spaces.
        String[] words = this.messageContent.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        return (firstTwo + ":" + this.numMessagesSent + ":" + firstWord + lastWord).toUpperCase();
    }
 
    // -----------------------------------------------------------------------
    // 5. SEND,STORE AND DISCARD THE MESSAGE 
    // Validates the message before sending ,and returns a result message.
    // When the user enters any option of the 3 options and this method handles each case.
    // -----------------------------------------------------------------------
    public String SentMessage(String action) {
        if (action == null) {
            return "No action provided.";
        }
 
        switch (action.trim().toLowerCase()) {
 
            case "send":
                // Validate ID length and if it is valid
                if (!checkMessageID()) {
                    return "Failed: Message ID exceeds 10 characters.";
                }
                // Validate recipient number if valid
                if (!checkRecipientCell().startsWith("Cell phone number successfully")) {
                    return "Failed: " + checkRecipientCell();
                }
                // Validate message length if the message is no more than 250 characters max.
                if (this.messageContent.length() > 250) {
                    return "Failed: Message exceeds 250 characters — please reduce your message length.";
                }
                // All checks passed — increase counter by 1
                totalMessagesSent++;
                this.numMessagesSent = totalMessagesSent;
                this.messageHash = createMessageHash();
                // PART 3: Add to sentMessages, messageHashes, messageIDs, and recipientList arrays
                sentMessages.add(this.messageContent);
                messageHashes.add(this.messageHash);
                messageIDs.add(this.messageID);
                recipientList.add(this.recipientNumber);
 
                return "Message successfully sent.";
 
            case "store":
                //Mssage saved as JSON and confirm to the user if stored.
                 // PART 3: Add to sentMessages, messageHashes, messageIDs, and recipientList arrays
                sentMessages.add(this.messageContent);
                messageHashes.add(this.messageHash);
                messageIDs.add(this.messageID);
                recipientList.add(this.recipientNumber);
 
                return "Message successfully stored.\n" + storeMessage();
 
            case "discard":
                // PART 3: Add to disregardedMessages array
                disregardedMessages.add(this.messageContent);
                return "Message discarded.";
 
            default:
                return "Invalid action. Please type Send, Store, or Discard.";
        }
    }
 
    // -----------------------------------------------------------------------
    // 6. STORE SAVED MESSAGE AS JSON 
    // JSON is a standard format for storing data.
    // -----------------------------------------------------------------------
    public String storeMessage() {
        //JSON object is created.
        JSONObject json = new JSONObject();
        json.put("messageID", this.messageID);
        json.put("numMessagesSent", this.numMessagesSent);
        json.put("recipientNumber", this.recipientNumber);
        json.put("messageContent", this.messageContent);
        json.put("messageHash", this.messageHash);
        
        // The JSONObject is converted to a string,indented with 2 spaces for readability
        return json.toString(2);
    }
 
    // -----------------------------------------------------------------------
    // 7. PRINT all message details
    //Returns a neatly formatted String showing all message information.
    // -----------------------------------------------------------------------
    public String printMessages() {
        return "Message ID: " + this.messageID + "\n" +
               "Message Hash: " + this.messageHash + "\n" +
               "Recipient: " + this.recipientNumber + "\n" +
               "Message: " + this.messageContent;
    }
    // -----------------------------------------------------------------------
    // PART 3 —  DISPLAY THE LONGEST MESSAGE
    // Loops through storedMessages and returns the one with the most characters.
    // -----------------------------------------------------------------------
    public String displayLongestMessage() {
        // Start with an empty string as the "longest so far"
        String longest = "";
 
        // Go through every message in the storedMessages array
        for (String message : storedMessages) {
            // If this message is longer than what we have so far, update longest
            if (message.length() > longest.length()) {
                longest = message;
            }
        }
 
        // If nothing was found, tell the user
        if (longest.isEmpty()) {
            return "No stored messages found.";
        }
 
        return "Longest message: " + longest;
    }
 
    // -----------------------------------------------------------------------
    // PART 3 —SEARCH BY MESSAGE ID
    // The user types an ID. We look for it in messageIDs and return
    // the matching message from sentMessages at the same position (index).
    // This is called "parallel array searching".
    // -----------------------------------------------------------------------
    public String searchByMessageID(String id) {
        // Loop through every ID we have stored
        for (int i = 0; i < messageIDs.size(); i++) {
            // Check if this ID matches what the user typed
            if (messageIDs.get(i).equals(id)) {
                // The message is at the SAME index in sentMessages
                if (i < sentMessages.size()) {
                    return "Message found: " + sentMessages.get(i);
                }
            }
        }
        // Nothing matched
        return "Message not found.";
    }
 
    // -----------------------------------------------------------------------
    // PART 3 — SEARCH BY RECIPIENT
    // The user types a cell number. We find ALL messages sent to that number.
    // There may be more than one result so we collect all matches.
    // -----------------------------------------------------------------------
    public String searchByRecipient(String recipient) {
        StringBuilder results = new StringBuilder();
 
        // Loop through recipientList (same size as sentMessages)
        for (int i = 0; i < recipientList.size(); i++) {
            if (recipientList.get(i).equals(recipient)) {
                // Found a match — add the message to our results
                results.append("- ").append(sentMessages.get(i)).append("\n");
            }
        }
 
        // Return results or a "not found" message
        if (results.length() == 0) {
            return "No messages found for recipient: " + recipient;
        }
        return "Messages for " + recipient + ":\n" + results.toString();
    }
 
    // -----------------------------------------------------------------------
    // PART 3 — DELETE BY MESSAGE HASH
    // The user types a hash. We find it in messageHashes, then remove
    // the matching entry from ALL the parallel arrays at that same index.
    // -----------------------------------------------------------------------
    public String deleteByHash(String hash) {
        // Loop through messageHashes to find the matching one
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
 
                // Save the message text before deleting so we can confirm to the user
                String deletedMessage = "";
                if (i < sentMessages.size()) {
                    deletedMessage = sentMessages.get(i);
                    sentMessages.remove(i);      // Remove from sentMessages
                    recipientList.remove(i);      // Remove matching recipient too
                }
 
                // Remove from the hash and ID arrays
                messageHashes.remove(i);
                messageIDs.remove(i);
 
                // Return the success message in the format the POE requires
                return "Message: " + deletedMessage + " successfully deleted.";
            }
        }
        // Hash was not found in any array
        return "Hash not found.";
    }
 
    // -----------------------------------------------------------------------
    // PART 3 — DISPLAY MESSAGE REPORT (updated printMessages)
    // Shows a full formatted report of all SENT messages including
    // their hash, recipient, and message text.
    // -----------------------------------------------------------------------
    public static String printMessages() {
        // Use StringBuilder to build up the full report text
        StringBuilder report = new StringBuilder();
        report.append("=== Message Report ===\n");
 
        // If no messages have been sent yet
        if (sentMessages.isEmpty()) {
            report.append("No sent messages to display.\n");
            return report.toString();
        }
 
        // Loop through each sent message using its index
        for (int i = 0; i < sentMessages.size(); i++) {
            report.append("------------------------------\n");
            report.append("Hash      : ").append(messageHashes.get(i)).append("\n");
            report.append("Recipient : ").append(recipientList.get(i)).append("\n");
            report.append("Message   : ").append(sentMessages.get(i)).append("\n");
        }
 
        report.append("==============================\n");
        return report.toString();
    }
 
 
    // -----------------------------------------------------------------------
    // Getters — used by test classes
    // Allows other classes to read private fields
    // -----------------------------------------------------------------------
    public String getMessageID()       { return messageID; }
    public int    getNumMessagesSent() { return numMessagesSent; }
    public String getRecipientNumber() { return recipientNumber; }
    public String getMessageContent()  { return messageContent; }
    public String getMessageHash()     { return messageHash; }
 
    public static int getTotalMessagesSent() { return totalMessagesSent; }
 
    // Resets the counter back to zero that is used in unit tests 
    public static void resetTotalMessagesSent() { totalMessagesSent = 0; }
}
