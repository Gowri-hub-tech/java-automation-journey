public class EmailValidatorTest {

    static String validateEmail(String email, String scenarioName) {
        if (email == null || email.isEmpty()) {
            return "TEST FAILED: " + scenarioName + " - Email cannot be empty";
        }
        if (email.contains(" ")) {
            return "TEST FAILED: " + scenarioName + " - Email cannot contain spaces";
        }
        long atCount = email.chars().filter(c -> c == '@').count();
        if (atCount != 1) {
            return "TEST FAILED: " + scenarioName + " - Email must contain exactly one @ symbol";
        }
        if (email.contains("..")) {
            return "TEST FAILED: " + scenarioName + " - Email cannot contain consecutive dots";
        }
        if (email.matches("[a-zA-Z][a-zA-Z0-9._-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            return "TEST PASSED: " + scenarioName;
        }
        return "TEST FAILED: " + scenarioName + " - Invalid email format";
    }
    public static void main(String[] args) {
        System.out.println(validateEmail("testuser@example.com", "Valid email"));
        System.out.println(validateEmail("t@example.com", "Single character local part"));
        System.out.println(validateEmail("test.user@example.org", "Valid .org email"));
        System.out.println(validateEmail("test@example.co.uk", "Valid .co.uk email"));
        System.out.println(validateEmail("@example.com", "Missing local part"));
        System.out.println(validateEmail("testuser@.com", "Missing domain"));
        System.out.println(validateEmail("testuser@example.", "Missing extension"));
        System.out.println(validateEmail("testuser@example", "No dot before extension"));
        System.out.println(validateEmail("1testuser@example.com", "Local part starts with number"));
        System.out.println(validateEmail("@testuser@example.com", "Local part starts with special character"));
        System.out.println(validateEmail("testuser@@example.com", "Two @ symbols"));
        System.out.println(validateEmail("test user@example.com", "Space in local part"));
        System.out.println(validateEmail("testuser@exam ple.com", "Space in domain"));
        System.out.println(validateEmail("test..user@example.com", "Consecutive dots"));
        System.out.println(validateEmail("", "Empty email"));
        System.out.println(validateEmail("testuser@example.education", "Extension too long"));
        System.out.println(validateEmail("test.user_name-123@example.com", "Valid email with dots underscores hyphens"));
    }
}