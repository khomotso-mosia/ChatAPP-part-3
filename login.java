package com.mycompany.chatapppart1;

public class login {
    // Step 4 : The login class.
    // Variables are stored and declared.
    String username;
    String password;
    String phoneNumber;
    String firstName;
    String lastName;
    
    public login(String username, String password, String phoneNumber, String firstName, String lastName) {
      this.username = username;
      this.password = password;
      this.phoneNumber = phoneNumber;
      this.firstName = firstName;
      this.lastName = lastName;
    }

    login() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    //Step 5 : Username validation.
    // USERNAME VALIDATION
    //-Ensure that the entered username has an underscore .
    //-Ensure that it contains no more than 5 characters long .
    
    public boolean checkUserName(String username){
return username.contains("_") && username.length() <= 5;
    }
    //Step 6: this is where the password is being validated
    // PASSWORD VALIDATION
    //- Ensure that the password entered does not go above 8 characters long.
    //-Ensure that the password contains atleast 1 capital letter.
    //-Ensure that the password contains atleast 1 number .
    //- Ensure that the password has atleast 1 special character(#$%^&*).
    
    public boolean checkPasswordComplexity(String password){
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        if(password.length()>=8){
            
     
        for (int i = 0; i < password.length(); i++){
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        if (hasCapital && hasNumber && hasSpecial){
            return true;
        }
    }
     return false;
    }
    //Step 7: This is where the cellphone number is being validated
    // PHONE NUMBER VALIDATION
    //-Ensure that the cellphone number contains the South African code (+27)...
    //-Ensure that the number does not exceed 12 characters long and is also not less than 12 characters .
    
    public boolean checkCellPhoneNumber(String phone) {
        return phone.startsWith("+27") && phone.length() == 12;
    }

    // Step 8: This is where the register method is created
    // This method will check the previously validated data and stored the data once its correct and than produce return messages.
    // ---REGISTER USER---
    public String registerUser(String firstName, String lastName, String username){
        
        
        if (!checkUserName(username)){
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }

        if (!checkPasswordComplexity(password)){
            return "Password is not correctly formatted; please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";
        }    

        if (!checkCellPhoneNumber(phoneNumber)){
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        
       
        this.username = username;
        this.password = password;

        return "Registration successful!";
               
    }

    // Step 9:LOGIN FEATURE
    //This is where the user is now permitted to logging in with the same log in details they registered with.
    //--- LOGIN USER----
    public boolean loginUser(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }

    // LOGIN STATUS MESSAGE
    // The message that is returned once the login has been successful.
    public String returnLoginStatus(boolean loggedIn) {
        if (loggedIn) {
            return (" Welcome " + firstName + " " + lastName +", it is great to see you again.");
        } else {
            return ("Username or password incorrect please try again.");
        }
    }

    String returnLoginStatus(String loginSuccess) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}