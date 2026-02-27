import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginValidatorTest {

    private static final String CORRECT_USERNAME = "testuser";
    private static final String CORRECT_PASSWORD = "Test@1234";

    private String validateLogin(String username, String password) {
        if (username.length() < 5 || username.length() > 20) {
            return "Username length invalid";
        }
        if (!Character.isLetterOrDigit(username.charAt(0))) {
            return "Username cannot start with a special character";
        }
        if (!username.matches("[a-zA-Z0-9_]+")) {
            return "Username contains an invalid character";
        }
        if (password.length() < 8) {
            return "Password too short";
        }
        if (username.equals(CORRECT_USERNAME) && password.equals(CORRECT_PASSWORD)) {
            return "LOGIN_SUCCESS";
        }
        return "Invalid username or password";
    }
    @Test(groups = "happy_path")
    public void validUsernameAndPassword() {
        String result = validateLogin("testuser", "Test@1234");
        Assert.assertEquals(result, "LOGIN_SUCCESS", "Valid credentials should pass");
    }

    // ===== WRONG CREDENTIALS =====
    @Test(groups = "wrong_credentials")
    public void correctUsernameWrongPassword() {
        String result = validateLogin("testuser", "wrongpassword");
        Assert.assertNotEquals(result, "LOGIN_SUCCESS", "Wrong password should fail");
    }

    @Test(groups = "wrong_credentials")
    public void wrongUsernameCorrectPassword() {
        String result = validateLogin("wronguser", "Test@1234");
        Assert.assertNotEquals(result, "LOGIN_SUCCESS", "Wrong username should fail");
    }

    @Test(groups = "wrong_credentials")
    public void wrongUsernameAndPassword() {
        String result = validateLogin("wronguser", "wrongpassword");
        Assert.assertNotEquals(result, "LOGIN_SUCCESS", "Wrong credentials should fail");
    }
    // ===== EMPTY FIELDS =====
    @Test(groups = "empty_fields")
    public void emptyUsername() {
        String result = validateLogin("", "Test@1234");
        Assert.assertNotEquals(result, "LOGIN_SUCCESS", "Empty username should fail");
    }

    @Test(groups = "empty_fields")
    public void emptyPassword() {
        String result = validateLogin("testuser", "");
        Assert.assertNotEquals(result, "LOGIN_SUCCESS", "Empty password should fail");
    }

    @Test(groups = "empty_fields")
    public void bothEmpty() {
        String result = validateLogin("", "");
        Assert.assertNotEquals(result, "LOGIN_SUCCESS", "Both empty should fail");
    }

    // ===== LENGTH VALIDATION =====
    @Test(groups = "length_validation")
    public void usernameTooShort() {
        String result = validateLogin("ab", "Test@1234");
        Assert.assertEquals(result, "Username length invalid", "Short username should return length error");
    }

    @Test(groups = "length_validation")
    public void usernameTooLong() {
        String result = validateLogin("thisusernameiswaytoolongforvalidation", "Test@1234");
        Assert.assertEquals(result, "Username length invalid", "Long username should return length error");
    }

    @Test(groups = "length_validation")
    public void passwordTooShort() {
        String result = validateLogin("testuser", "short");
        Assert.assertEquals(result, "Password too short", "Short password should return length error");
    }

    // ===== SPECIAL CHARACTERS =====
    @Test(groups = "special_characters")
    public void usernameStartsWithSpecialCharacter() {
        String result = validateLogin("@testuser", "Test@1234");
        Assert.assertEquals(result, "Username cannot start with a special character", "Special char at start should fail");
    }

    @Test(groups = "special_characters")
    public void usernameContainsInvalidCharacter() {
        String result = validateLogin("test@user", "Test@1234");
        Assert.assertEquals(result, "Username contains an invalid character", "Invalid char should fail");
    }

    @Test(groups = "special_characters")
    public void usernameContainsSpace() {
        String result = validateLogin("test user", "Test@1234");
        Assert.assertEquals(result, "Username contains an invalid character", "Space in username should fail");
    }
     // ===== CASE SENSITIVITY =====
    @Test(groups = "case_sensitivity")
    public void usernameInAllCaps() {
        String result = validateLogin("TESTUSER", "Test@1234");
        Assert.assertNotEquals(result, "LOGIN_SUCCESS", "Uppercase username should fail");
    }

    @Test(groups = "case_sensitivity")
    public void usernameInMixedCase() {
        String result = validateLogin("TestUser", "Test@1234");
        Assert.assertNotEquals(result, "LOGIN_SUCCESS", "Mixed case username should fail");
    }

    // ===== SECURITY =====
    @Test(groups = "security")
    public void sqlInjectionAttempt() {
        String result = validateLogin("' OR '1'='1", "Test@1234");
        Assert.assertNotEquals(result, "LOGIN_SUCCESS", "SQL injection should be blocked");
    }
}