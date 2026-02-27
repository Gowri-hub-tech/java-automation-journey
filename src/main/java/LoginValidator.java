public class LoginValidator {

    static String correctUsername = "testuser";
    static String correctPassword = "Test@1234";
    static void validateLogin(String username, String password, String scenarioName) {
        if (username.equals(correctUsername) && password.equals(correctPassword)) {
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
    }
}