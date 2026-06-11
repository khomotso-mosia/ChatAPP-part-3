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