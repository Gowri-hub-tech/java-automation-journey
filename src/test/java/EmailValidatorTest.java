import org.testng.Assert;
import org.testng.annotations.Test;

public class EmailValidatorTest {
    private String validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "Email cannot be empty";
        }
        if (email.contains(" ")) {
            return "Email cannot contain spaces";
        }
        long atCount = email.chars().filter(c -> c == '@').count();
        if (atCount != 1) {
            return "Email must contain exactly one @ symbol";
        }
        if (email.contains("..")) {
            return "Email cannot contain consecutive dots";
        }
        if (email.matches("[a-zA-Z][a-zA-Z0-9._-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            return "EMAIL_VALID";
        }
        return "Invalid email format";
    }
    @Test(groups = "happy_path")
    public void validEmail() {
        String result = validateEmail("testuser@example.com");
        Assert.assertEquals(result, "EMAIL_VALID", "Valid email should pass");
    }

    @Test(groups = "happy_path")
    public void singleCharacterLocalPart() {
        String result = validateEmail("t@example.com");
        Assert.assertEquals(result, "EMAIL_VALID", "Single character local part should pass");
    }

    @Test(groups = "happy_path")
    public void validOrgEmail() {
        String result = validateEmail("test.user@example.org");
        Assert.assertEquals(result, "EMAIL_VALID", "Valid .org email should pass");
    }

    @Test(groups = "happy_path")
    public void validCoUkEmail() {
        String result = validateEmail("test@example.co.uk");
        Assert.assertEquals(result, "EMAIL_VALID", "Valid .co.uk email should pass");
    }

    @Test(groups = "happy_path")
    public void validEmailWithSpecialChars() {
        String result = validateEmail("test.user_name-123@example.com");
        Assert.assertEquals(result, "EMAIL_VALID", "Valid email with dots underscores hyphens should pass");
    }

    // ===== MISSING PARTS =====
    @Test(groups = "missing_parts")
    public void missingLocalPart() {
        String result = validateEmail("@example.com");
        Assert.assertEquals(result, "Invalid email format", "Missing local part should fail");
    }

    @Test(groups = "missing_parts")
    public void missingDomain() {
        String result = validateEmail("testuser@.com");
        Assert.assertEquals(result, "Invalid email format", "Missing domain should fail");
    }

    @Test(groups = "missing_parts")
    public void missingExtension() {
        String result = validateEmail("testuser@example.");
        Assert.assertEquals(result, "Invalid email format", "Missing extension should fail");
    }

    @Test(groups = "missing_parts")
    public void noDotBeforeExtension() {
        String result = validateEmail("testuser@example");
        Assert.assertEquals(result, "Invalid email format", "No dot before extension should fail");
    }

    @Test(groups = "missing_parts")
    public void emptyEmail() {
        String result = validateEmail("");
        Assert.assertEquals(result, "Email cannot be empty", "Empty email should fail");
    }

    // ===== INVALID START =====
    @Test(groups = "invalid_start")
    public void localPartStartsWithNumber() {
        String result = validateEmail("1testuser@example.com");
        Assert.assertEquals(result, "Invalid email format", "Local part starting with number should fail");
    }

    @Test(groups = "invalid_start")
    public void localPartStartsWithSpecialCharacter() {
        String result = validateEmail("@testuser@example.com");
        Assert.assertEquals(result, "Email must contain exactly one @ symbol", "Local part starting with special char should fail");
    }

    // ===== INVALID FORMAT =====
    @Test(groups = "invalid_format")
    public void twoAtSymbols() {
        String result = validateEmail("testuser@@example.com");
        Assert.assertEquals(result, "Email must contain exactly one @ symbol", "Two @ symbols should fail");
    }

    @Test(groups = "invalid_format")
    public void spaceInLocalPart() {
        String result = validateEmail("test user@example.com");
        Assert.assertEquals(result, "Email cannot contain spaces", "Space in local part should fail");
    }

    @Test(groups = "invalid_format")
    public void spaceInDomain() {
        String result = validateEmail("testuser@exam ple.com");
        Assert.assertEquals(result, "Email cannot contain spaces", "Space in domain should fail");
    }

    @Test(groups = "invalid_format")
    public void consecutiveDots() {
        String result = validateEmail("test..user@example.com");
        Assert.assertEquals(result, "Email cannot contain consecutive dots", "Consecutive dots should fail");
    }

    // ===== INVALID EXTENSION =====
    @Test(groups = "invalid_extension")
    public void extensionTooLong() {
        String result = validateEmail("testuser@example.education");
        Assert.assertEquals(result, "Invalid email format", "Extension too long should fail");
    }
}