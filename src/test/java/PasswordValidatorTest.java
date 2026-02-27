import org.testng.Assert;
import org.testng.annotations.Test;

public class PasswordValidatorTest {

    private String validatePassword(String password) {
        if (password.length() < 8 || password.length() > 20) {
            return "Password length invalid";
        }
        if (!Character.isLetterOrDigit(password.charAt(0))) {
            return "Password cannot start with special character";
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

        if (uppercaseCount == 0) return "No uppercase letter";
        if (lowercaseCount == 0) return "No lowercase letter";
        if (numberCount == 0) return "No number";
        if (specialCharCount == 0) return "No special character";

        return "PASSWORD_VALID";
    }
    @Test(groups = "happy_path")
    public void validPassword() {
        String result = validatePassword("Test@1234");
        Assert.assertEquals(result, "PASSWORD_VALID", "Valid password should pass");
    }

    // ===== LENGTH VALIDATION =====
    @Test(groups = "length_validation")
    public void passwordAtMinimumLength() {
        String result = validatePassword("Test@12!");
        Assert.assertEquals(result, "PASSWORD_VALID", "Password at minimum length should pass");
    }

    @Test(groups = "length_validation")
    public void passwordTooShort() {
        String result = validatePassword("Te@1");
        Assert.assertEquals(result, "Password length invalid", "Short password should return length error");
    }

    @Test(groups = "length_validation")
    public void passwordTooLong() {
        String result = validatePassword("Test@1234567890123456");
        Assert.assertEquals(result, "Password length invalid", "Long password should return length error");
    }

    // ===== COMPLEXITY VALIDATION =====
    @Test(groups = "complexity_validation")
    public void noUppercaseLetter() {
        String result = validatePassword("test@1234");
        Assert.assertEquals(result, "No uppercase letter", "Password without uppercase should fail");
    }

    @Test(groups = "complexity_validation")
    public void noLowercaseLetter() {
        String result = validatePassword("TEST@1234");
        Assert.assertEquals(result, "No lowercase letter", "Password without lowercase should fail");
    }

    @Test(groups = "complexity_validation")
    public void noNumber() {
        String result = validatePassword("Test@abcd");
        Assert.assertEquals(result, "No number", "Password without number should fail");
    }

    @Test(groups = "complexity_validation")
    public void noSpecialCharacter() {
        String result = validatePassword("Testabcd1");
        Assert.assertEquals(result, "No special character", "Password without special char should fail");
    }

    // ===== SPECIAL CHARACTERS =====
    @Test(groups = "special_characters")
    public void passwordStartsWithSpecialCharacter() {
        String result = validatePassword("@Test1234");
        Assert.assertEquals(result, "Password cannot start with special character", "Password starting with special char should fail");
    }
}