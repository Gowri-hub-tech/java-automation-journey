public class LoginValidator {

    static String correctUsername = "testuser";
    static String correctPassword = "Test@1234";
    static void validateLogin(String username, String password, String scenarioName) {
        if (username.length() < 5 || username.length() > 20) {
            System.out.println("TEST FAILED: " + scenarioName + " - Username length invalid");
        } else if (password.length() < 8) {
            System.out.println("TEST FAILED: " + scenarioName + " - Password too short");
        } else if (username.equals(correctUsername) && password.equals(correctPassword)) {
            System.out.println("TEST PASSED: " + scenarioName);
        } else {
            System.out.println("TEST FAILED: " + scenarioName);
        }
 
    }
     public static void main(String[] args) {
        validateLogin("testuser", "Test@1234", "Correct username and password");
        validateLogin("testuser", "wrongpassword", "Correct username, wrong password");
        validateLogin("wronguser", "Test@1234", "Wrong username, correct password");
        validateLogin("wronguser", "wrongpassword", "Wrong username and password");
        validateLogin("", "Test@1234", "Empty username");
        validateLogin("testuser", "", "Empty password");
        validateLogin("", "", "Both empty");
        validateLogin("ab", "Test@1234", "Username too short");
        validateLogin("thisusernameiswaytoolongforvalidation", "Test@1234", "Username too long");
        validateLogin("testuser", "short", "Password too short");
    }
}