public class PasswordValidatorTest {

    static String validatePassword(String password, String scenarioName) {
        if (password.length() < 8 || password.length() > 20) {
            return "TEST FAILED: " + scenarioName + " - Password length invalid";
        }
        if (!Character.isLetterOrDigit(password.charAt(0)) ) {
            return "TEST FAILED: " + scenarioName + " - Password cannot start with special character";
        }
        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int numberCount = 0;
        int specialCharCount = 0;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) uppercaseCount++;
            else if (Character.isLowerCase(c)) lowercaseCount++;
            else if (Character.isDigit(c)) numberCount++;
            else specialCharCount++;
        }

        if (uppercaseCount == 0) return "TEST FAILED: " + scenarioName + " - No uppercase letter";
        if (lowercaseCount == 0) return "TEST FAILED: " + scenarioName + " - No lowercase letter";
        if (numberCount == 0) return "TEST FAILED: " + scenarioName + " - No number";
        if (specialCharCount == 0) return "TEST FAILED: " + scenarioName + " - No special character";

        return "TEST PASSED: " + scenarioName;
    }
    public static void main(String[] args) {
        System.out.println(validatePassword("Test@1234", "Valid password"));
        System.out.println(validatePassword("short1@A", "Password at minimum length"));
        System.out.println(validatePassword("test@1234", "No uppercase letter"));
        System.out.println(validatePassword("TEST@1234", "No lowercase letter"));
        System.out.println(validatePassword("Test@abcd", "No number"));
        System.out.println(validatePassword("Testabcd1", "No special character"));
        System.out.println(validatePassword("Te@1", "Password too short"));
        System.out.println(validatePassword("@Test1234", "Password starts with special character"));
        System.out.println(validatePassword("Test@1234567890123456", "Password too long"));
    }
}