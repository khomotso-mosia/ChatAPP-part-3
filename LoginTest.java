package com.mycompany.chatapppart1;

import com.mycompany.chatapppart1.login;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    login login = new login();

    // -------- USERNAME TESTS --------
    @Test
    public void testValidUsername() {
        assertTrue(login.checkUserName("Kyl_1"));
    }

    @Test
    public void testInvalidUsername_NoUnderscore() {
        assertFalse(login.checkUserName("Kyle1"));
    }

    @Test
    public void testInvalidUsername_TooLong() {
        assertFalse(login.checkUserName("Kyl_1234"));
    }

    // -------- PASSWORD TESTS --------
    @Test
    public void testValidPassword() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99"));
    }

    @Test
    public void testInvalidPassword_NoCapital() {
        assertFalse(login.checkPasswordComplexity("ch&&sec@ke99"));
    }

    @Test
    public void testInvalidPassword_NoNumber() {
        assertFalse(login.checkPasswordComplexity("Ch&&sec@ke"));
    }

    @Test
    public void testInvalidPassword_NoSpecialChar() {
        assertFalse(login.checkPasswordComplexity("Chessecake99"));
    }

    @Test
    public void testInvalidPassword_TooShort() {
        assertFalse(login.checkPasswordComplexity("Ch&99"));
    }

    // -------- PHONE NUMBER TESTS --------
    @Test
    public void testValidPhoneNumber() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testInvalidPhoneNumber_NoCountryCode() {
        assertFalse(login.checkCellPhoneNumber("0838968976"));
    }

    @Test
    public void testInvalidPhoneNumber_TooLong() {
        assertFalse(login.checkCellPhoneNumber("+27838968976789"));
    }

    // -------- REGISTER USER TESTS --------
    @Test
    public void testRegisterUser_Success() {
        String result = login.registerUser("Kyl_1", "Ch&&sec@ke99", "+27838968976");
        assertEquals("User registered successfully", result);
    }

    @Test
    public void testRegisterUser_InvalidUsername() {
        String result = login.registerUser("Kyle1", "Ch&&sec@ke99", "+27838968976");
        assertFalse(result.contains("Username incorrectly formatted"));
    }

    @Test
    public void testRegisterUser_InvalidPassword() {
        String result = login.registerUser("Kyl_1", "password", "+27838968976");
        assertFalse(result.contains("Password does not meet complexity requirements"));
    }

    @Test
    public void testRegisterUser_InvalidPhone() {
        String result = login.registerUser("Kyl_1", "Ch&&sec@ke99", "0838968976");
        assertFalse(result.contains("Cell phone number incorrectly formatted"));
    }

    // -------- LOGIN TESTS --------
    @Test
    public void testLoginSuccess() {
        login.registerUser("Kyl_1", "Ch&&sec@ke99", "+27838968976");
        assertTrue(login.loginUser("Kyl_1", "Ch&&sec@ke99"));
    }

    @Test
    public void testLoginFail_WrongUsername() {
        login.registerUser("Kyl_1", "Ch&&sec@ke99", "+27838968976");
        assertFalse(login.loginUser("kylee_1", "Ch&&sec@ke99"));
    }

    @Test
    public void testLoginFail_WrongPassword() {
        login.registerUser("Kyl_1", "Ch&&sec@ke99", "+27838968976");
        assertFalse(login.loginUser("Kyl_1", "WrongPass1!"));
    }

    // -------- LOGIN STATUS TEST --------
    @Test
    public void testReturnLoginStatus_Success() {
        String result = login.returnLoginStatus(true);
        assertEquals("Welcome< username> ,  it is great to see you ", result);
    }

    @Test
    public void testReturnLoginStatus_Failure() {
        String result = login.returnLoginStatus(false);
        assertEquals("Login failed: incorrect username or password", result);
    }
}
    
   
    

