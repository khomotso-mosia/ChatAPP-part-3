/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;

/**
 *
 * @author mosia
 */
import java.util.Scanner;

public class mainapp {

    private static boolean loginSuccess;
    public static void main(String[] args){
         // Step 10: This step is where the program interacts with the user
        // A scanner is created or inputed to be able to allow the user to insert values/personal details information.
        Scanner input = new Scanner(System.in);

        

        // Variables that are being stored / declared.
        String username = null;
        String password = null;
        String phone = null;
        String firstName = null;
        String lastName = null;

        // -------- USER REGISTRATION --------
        boolean loggedRegistration = false;

        while (!loggedRegistration) {
            System.out.println("=== USER REGISTRATION ===");
            
             System.out.print("Enter firstName : ");
             firstName = input.nextLine();
             
             System.out.print("Enter lastName : ");
             lastName = input.nextLine();
             
            // Username
            System.out.print("Enter a username: ");
            username = input.nextLine();
            
            login templogin = new login(username, "", "", firstName,lastName);
                    
            if (!templogin.checkUserName(username)) {
                System.out.println("Username is not correctly formatted;please ensure that your contains an underscore and is no more than five characters in length.");
                continue; // go back to start of loop
            }
            System.out.println("Username successfully captured");

            // Password
            System.out.print("Enter a password: ");
            password = input.nextLine();

            if (!templogin.checkPasswordComplexity(password)) {
                System.out.println("Password is not correctly formatted;please ensure that the password contains at least eight characters ,a capital letter,a number, and special character.");
                continue;
            }
            System.out.println("Password successfully captured.");

            System.out.print("Enter cell phone number (+27...): ");
            phone = input.nextLine();

            if (!templogin.checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
                continue;
            }
            System.out.println("Cell phone number successfully added.");
        
            login login = new login(username, password, phone, firstName, lastName);
            // Register user
           
            String result = login.registerUser("firstName","lastName",username);
            System.out.println(result);
           //here the loop is now being exitted,the loop stop /Exit
            loggedRegistration = true; 
        }
        
        login login = new login(username, password, phone, firstName, lastName);

        // -------- USER LOGIN --------
        boolean signIn = false;

        while (!signIn) {
            System.out.println("\n=== USER LOGIN ===");

            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();
            
            
            boolean success = login.loginUser(loginUsername, loginPassword);
            System.out.println(login.returnLoginStatus(success));

            String message = login.returnLoginStatus(success);
            

            if (success) {
                // The loop is going to stop only when the login is correct.
                signIn = true;
            }
        }
   //Part 2 addition 
   // To confirm that the user has successfully logged in this is then set as true.
   //This acts as a "gate"-only the logged in users have access to the messaging menu.
        boolean loginSuccessPart2 = true;
        
        // This is where it is validated if the user is logged in before the messaging menu is shown.
            if (loginSuccessPart2) {

            Message.loadStoredMessages();
                // Now that the user has been validated and passed the login check greet the user.
            System.out.println("\nWelcome to Quickchat.");
            //This variable keeps track of how many messages have been sent in the sessions.
            int messageCount = 0;
            
            //This variable then manages and controls the loop in the main menu
            //For as long it is true the,the menu will repeatedly keep showing . 
            boolean keepRunning = true;
 
            while (keepRunning ) {
                // This is where the main menu is displayed to the user.
                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.println("4) Stored Messages");  
                System.out.print("Select an option: ");
      
                //The number of the user types read is stored in 'choice'
                int choice = input.nextInt();
                input.nextLine();
                
                
                //The switch checks which option the user selected and runs the matching block.
                switch (choice) {
 
                    case 1:
                        //When the user chooses the option to send messages.
                        //Asks the user how many messages they would like to send.
                        System.out.print("How many messages would you like to send? ");
                        int numMessages = input.nextInt();
                        input.nextLine();
 
                        //Loop runs only once for each message the user wants to send.
                        for (int i = 0; i < numMessages; i++) {
 
                            
                            System.out.println("\n=== Message " + (messageCount + 1) + " ===");
 
                            // Ensure the recipient's number is validated prior to instantiating the Message object.
                            String cell;
                            //Loop continues to ask for a number until the correctly formatted and valid one is entered by the user.
                            while (true) {
                                System.out.print("Enter Recipient Cellphone Number (+27...): ");
                                cell = input.nextLine();
                                // validates if the number starts with "+" and is 13 characters or less.
                                //loop is exitted if valid 
                                //If not valid an error is shown and ask again.
                                if (cell.startsWith("+") && cell.length() <= 13) {
                                    break;
                                }
                                System.out.println("Invalid number. Must start with '+' "
                                        + "and be no more than 13 characters.");
                            }
                            //The user is aske to enter the message they want.
                            //Show that message must now exceed 250 characters long.
                            System.out.print("Enter Message (Max 250 characters): ");
                            String text = input.nextLine();
 
                            // Initialize the Message object, allowing the class to auto-generate the ID.
                            Message msg = new Message(cell, text);
                            
                            //The message details is displayed to the user for them to be able to review if it is what the wanted to do or send .
                            System.out.println("\nMessage ID     : " + msg.getMessageID());
                            System.out.println("Recipient      : " + msg.getRecipientNumber());
                            System.out.println("Message        : " + msg.getMessageContent());
                            System.out.println("Generated Hash : " + msg.createMessageHash());
 
                            //The user is asked what they want to do with the message they have typed in.
                            //They can choose whether they want to Send,Store or discard.
                            System.out.print("\nType 'Send', 'Store', or 'Discard': ");
                            String action = input.nextLine();
                            //A result message that is printed is retrieved from the message class that handle the logic.
                            System.out.println(msg.SentMessage(action));
                            messageCount++;
                        }
                        break;
                        //Switch is exitted and return back to the top of the main menu loop.
 
                    case 2:
                        //When user chooses to use the recently sent messages.
                        //"Coming soon" is displayed because this feature is no available as yet.
                        System.out.println("Coming Soon.");
                        break;
 
                    case 3:
                        //User chooses to quit
                        //Setting keepRuning to false stops the while loop from running again.
                        keepRunning = false;
                        System.out.println("Goodbye!");
                        break;
 
                    default:
                        //Runs if the user types anything other than 1,2 or 3.
                        System.out.println("Invalid selection. Please enter 1, 2, or 3.");
                }
            }
            //Loop ends ,the user is showen how many messages they proccessed this session.
            System.out.println("\nSession ended. Total messages processed: " + messageCount);
 
        } else {
            //If the user login is false resulting in the user not logged in access is blocked.
            System.out.println("Access Denied. Application exiting.");
        }
        //Scanner close now that reading inputs is done.
        input.close();
    }
}
// -----------------------------------------------------------------------
    // PART 3 — STORED MESSAGES SUB-MENU
    // This method runs when the user selects option 4 from the main menu.
    // It shows its own loop with 7 options (a–f plus quit back to main).
    // -----------------------------------------------------------------------
    private static void storedMessagesMenu() {
 
        boolean inSubMenu = true;
 
        while (inSubMenu) {
 
            //  This is where the sub-menu is displayed.
            System.out.println("\n--- STORED MESSAGES MENU ---");
            System.out.println("a) Display all stored messages");
            System.out.println("b) Display longest message");
            System.out.println("c) Search by message ID");
            System.out.println("d) Search by recipient");
            System.out.println("e) Delete message by hash");
            System.out.println("f) Display full message report");
            System.out.println("g) Return to main menu");
            System.out.print("Select an option: ");
 
            String subChoice = input.nextLine().trim().toLowerCase();
 
            switch (subChoice) {
 
                case "a":
                    // This is where all of the stored messages are displayed
                    // Shows every message that was loaded from the JSON file
                    System.out.println("\n--- All Stored Messages ---");
                    if (Message.getStoredMessages().isEmpty()) {
                        System.out.println("No stored messages available.");
                    } else {
                        for (int i = 0; i < Message.getStoredMessages().size(); i++) {
                            System.out.println((i + 1) + ". " + Message.getStoredMessages().get(i));
                        }
                    }
                    break;
 
                case "b":
                    // Here the longest message is being displayed.
                    // A temporary Message object is created just to call the method
                    Message tempMsg = new Message("", "temp");
                    System.out.println("\n" + tempMsg.displayLongestMessage());
                    break;
 
                case "c":
                    // Search by message ID
                    System.out.print("Enter Message ID to search: ");
                    String searchID = input.nextLine().trim();
                    Message msgForSearch = new Message("", "temp");
                    System.out.println(msgForSearch.searchByMessageID(searchID));
                    break;
 
                case "d":
                    // Search by recipient.
                    System.out.print("Enter recipient number to search: ");
                    String recipientSearch = input.nextLine().trim();
                    Message msgForRecipient = new Message("", "temp");
                    System.out.println(msgForRecipient.searchByRecipient(recipientSearch));
                    break;
 
                case "e":
                    // Delete by hash
                    System.out.print("Enter message hash to delete: ");
                    String hashToDelete = input.nextLine().trim();
                    Message msgForDelete = new Message("", "temp");
                    System.out.println(msgForDelete.deleteByHash(hashToDelete));
                    break;
 
                case "f":
                    // Display full report.
                    System.out.println(Message.printMessages());
                    break;
 
                case "g":
                    // Return to the main menu.
                    inSubMenu = false;
                    System.out.println("Returning to main menu...");
                    break;
 
                default:
                    System.out.println("Invalid option. Please enter a, b, c, d, e, f, or g.");
            }
        }
    }
}
