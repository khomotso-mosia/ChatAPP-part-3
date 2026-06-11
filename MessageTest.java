/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.chatapppart1;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 
/**
 * Unit tests for the Message class — Part 2 POE requirement.
 * Place this file in: src/test/java/com/mycompany/chatapppart1/
 */
public class MessageTest {
 
    private Message message;
 
    @BeforeEach
    public void setUp() {
        // Reset the static message counter before each test so tests are independent
        Message.resetTotalMessagesSent();
        message = new Message("+27831234567", "Hi there friend");
    }
 
    // -----------------------------------------------------------------------
    // Tests for checkMessageID()
    // -----------------------------------------------------------------------
 
    @Test
    public void testMessageInvalid() {
        assertNotNull(message.getMessageID(),
                "Message ID should not be null after construction.");
    }
 
    @Test
    public void testMessageIDLengthIsValid() {
        assertTrue(message.checkMessageID(),
                "Auto-generated message ID should be 10 characters or less.");
    }
 
    @Test
    public void testMessageIDExactlyTenCharacters() {
        // The auto-generated ID is always exactly 10 digits
        assertEquals(10, message.getMessageID().length(),
                "Auto-generated message ID should be exactly 10 characters.");
    }
 
    // -----------------------------------------------------------------------
    // Tests for checkRecipientCell()
    // -----------------------------------------------------------------------
 
    @Test
    public void testValidRecipientCell() {
        assertEquals("Cell phone number successfully captured.",
                message.checkRecipientCell(),
                "A valid +27 number should return a success message.");
    }
 
    @Test
    public void testInvalidRecipientCellMissingPlus() {
        Message badMsg = new Message("27831234567", "Test message");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.",
                badMsg.checkRecipientCell(),
                "A number without '+' prefix should be invalid.");
    }
 
    @Test
    public void testInvalidRecipientCellTooLong() {
        Message badMsg = new Message("+2783123456789999", "Test message");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.",
                badMsg.checkRecipientCell(),
                "A number longer than 13 characters should be invalid.");
    }
 
    // -----------------------------------------------------------------------
    // Tests for createMessageHash()
    // -----------------------------------------------------------------------
 
    @Test
    public void testMessageHashNotEmpty() {
        assertFalse(message.getMessageHash().isEmpty(),
                "Message hash should not be empty after construction.");
    }
 
    @Test
    public void testMessageHashFormat() {
        // Hash format: FIRST2OFID:COUNT:FIRSTWORDLASTWORD  — all uppercase
        String hash = message.getMessageHash();
        assertTrue(hash.contains(":"),
                "Message hash should contain ':' separators.");
        assertEquals(hash, hash.toUpperCase(),
                "Message hash should be all uppercase.");
    }
 
    @Test
    public void testMessageHashFirstWordLastWord() {
        // message content is "Hi there friend" → first=HI, last=FRIEND
        String hash = message.getMessageHash();
        assertTrue(hash.endsWith("HIFRIEND"),
                "Hash should end with the first word + last word of the message in uppercase.");
    }
 
    // -----------------------------------------------------------------------
    // Tests for SentMessage()
    // -----------------------------------------------------------------------
 
    @Test
    public void testSentMessageSend() {
        assertEquals("Message successfully sent.",
                message.SentMessage("Send"),
                "Valid message with 'Send' action should return a success message.");
    }
 
    @Test
    public void testSentMessageStore() {
        String result = message.SentMessage("Store");
        assertTrue(result.contains("Message successfully stored."),
                "Store action should return a stored confirmation message.");
        assertTrue(result.contains("messageID"),
                "Stored message output should contain 'messageID' JSON key.");
    }
 
    @Test
    public void testSentMessageDiscard() {
        assertEquals("Message discarded.",
                message.SentMessage("Discard"),
                "'Discard' action should return a discard confirmation.");
    }
 
    @Test
    public void testSentMessageInvalidAction() {
        assertEquals("Invalid action. Please type Send, Store, or Discard.",
                message.SentMessage("fly"),
                "An unrecognised action should return an error message.");
    }
 
    @Test
    public void testMessageTooLong() {
        // Create a message that is exactly 251 characters
        String longText = "A".repeat(251);
        Message longMsg = new Message("+27831234567", longText);
        String result = longMsg.SentMessage("Send");
        assertTrue(result.contains("Failed"),
                "Sending a message over 250 characters should fail.");
    }
}
// =======================================================================
    // PART 3 TESTS — six new test methods
   // =======================================================================

    
    @Test
    public void testSentMessagesArray_correctlyPopulated() {
        // Create and send message 1 from the POE
        Message msg1 = new Message("+27834557896", "Did you get the cake?");
        msg1.SentMessage("send");

        // Create and send message 4 from the POE (valid number used so send works)
        Message msg4 = new Message("+27834484567", "It is dinner time!");
        msg4.SentMessage("send");

        // Both texts must now be inside the sentMessages array
        assertTrue(Message.getSentMessages().contains("Did you get the cake?"),
                "sentMessages should contain 'Did you get the cake?'");
        assertTrue(Message.getSentMessages().contains("It is dinner time!"),
                "sentMessages should contain 'It is dinner time!'");
    }

    
    @Test
    public void testDisplayLongestMessage_returnsCorrectMessage() {
        // Add all five POE messages directly into the storedMessages array
        Message.getStoredMessages().add("Did you get the cake?");
        Message.getStoredMessages().add("Where are you? You are late! I have asked you to be on time.");
        Message.getStoredMessages().add("Ok, I am leaving without you.");
        Message.getStoredMessages().add("It is dinner time!");
        Message.getStoredMessages().add("Hi Mike, can you join us for dinner tonight");

        // The message object used here is just a vehicle to call the method
        Message msg = new Message("+27834557896", "temp");
        String result = msg.displayLongestMessage();

        // The longest message must appear in the result
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."),
                "displayLongestMessage should return the longest stored message.");
    }

   
    @Test
    public void testSearchByMessageID_returnsCorrectMessage() {
        // Send exactly one message so we know index 0 belongs to this message
        Message msg4 = new Message("+27834484567", "It is dinner time!");
        msg4.SentMessage("send");

        // Index 0 in messageIDs belongs to msg4 because it is the only sent message
        String storedID = Message.getMessageIDs().get(0);

        // Search for that ID
        Message searcher = new Message("+27834557896", "temp");
        String result = searcher.searchByMessageID(storedID);

        // The result must contain the message text
        assertTrue(result.contains("It is dinner time!"),
                "searchByMessageID should return 'It is dinner time!' for that ID.");
    }

    
    @Test
    public void testSearchByRecipient_returnsAllMatchingMessages() {
        // Two messages to the same recipient
        Message msg2 = new Message("+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        msg2.SentMessage("send");

        Message msg5 = new Message("+27838884567", "Ok, I am leaving without you.");
        msg5.SentMessage("send");

        // One message to a DIFFERENT recipient — must NOT appear in results
        Message msg3 = new Message("+27834484567", "Did you get the cake?");
        msg3.SentMessage("send");

        // Search for the shared recipient
        Message searcher = new Message("+27834557896", "temp");
        String result = searcher.searchByRecipient("+27838884567");

        // Both messages for +27838884567 must be in the result
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."),
                "Result should contain the first message for +27838884567.");
        assertTrue(result.contains("Ok, I am leaving without you."),
                "Result should contain the second message for +27838884567.");
    }

    
    @Test
    public void testDeleteByHash_removesCorrectMessage() {
        // Send exactly one message
        Message msg2 = new Message("+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        msg2.SentMessage("send");

        // Index 0 belongs to msg2 — it is the only sent message in this test
        String storedHash = Message.getMessageHashes().get(0);

        // Delete by that hash
        Message deleter = new Message("+27834557896", "temp");
        String result = deleter.deleteByHash(storedHash);

        // Confirm the deleted message text appears in the result
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."),
                "Delete result should contain the deleted message text.");

        // Confirm the success phrase appears
        assertTrue(result.contains("successfully deleted"),
                "Delete result should confirm the message was successfully deleted.");
    }

    
    @Test
    public void testDisplayReport_containsRequiredFields() {
        // Send two messages with different recipients
        Message msg1 = new Message("+27834557896", "Did you get the cake?");
        msg1.SentMessage("send");

        Message msg2 = new Message("+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        msg2.SentMessage("send");

        // Generate the full report
        String report = Message.printMessages();

        // Report must have the header
        assertTrue(report.contains("Message Report"),
                "Report should contain the 'Message Report' header.");

        // Report must contain both recipients
        assertTrue(report.contains("+27834557896"),
                "Report should contain recipient +27834557896.");
        assertTrue(report.contains("+27838884567"),
                "Report should contain recipient +27838884567.");

        // Report must contain both message texts
        assertTrue(report.contains("Did you get the cake?"),
                "Report should contain 'Did you get the cake?'");
        assertTrue(report.contains("Where are you? You are late!"),
                "Report should contain the second message text.");

        // Report must contain hashes — hashes always include ':' separators
        assertTrue(report.contains(":"),
                "Report should contain message hashes with ':' separators.");
    }

} 
