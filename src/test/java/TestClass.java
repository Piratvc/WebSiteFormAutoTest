import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

public class TestClass extends Common {
    public void registerNewUser(String email, String name, String password) {
        webDriver.get(baseUrl + "/signup");
        webDriver.findElement(By.name("email")).sendKeys(email);
        webDriver.findElement(By.name("name")).sendKeys(name);
        webDriver.findElement(By.name("password")).sendKeys(password);
        webDriver.findElement(By.className("button")).click();
    }

    public void loginUser(String email, String password) {
        webDriver.get(baseUrl + "/login");
        webDriver.findElement(By.name("email")).sendKeys(email);
        webDriver.findElement(By.name("password")).sendKeys(password);
        webDriver.findElement(By.className("button")).click();
    }

    @Test
    public void test_i1() {
        webDriver.get(baseUrl);
        assertEquals(webDriver.findElement(By.className("title")).getText(), "Test home page");
        webDriver.get(baseUrl + "/login");
        assertEquals(webDriver.findElement(By.className("title")).getText(), "Login");
        webDriver.get(baseUrl + "/signup");
        assertEquals(webDriver.findElement(By.className("title")).getText(), "Sign Up");
    }

    @Test
    public void test_i2() {
        registerNewUser("ivanivanov@mail.ru", "ivan", "1111");
        assertEquals(webDriver.findElement(By.className("title")).getText(), "Login");
    }

    @Test
    public void test_i3() {
        String emailUser = "Ivan@mail.ru";
        String nameUser = "Ivan Kapkan";
        String passwordUser = "1111";
        registerNewUser(emailUser, nameUser, passwordUser);
        loginUser(emailUser, passwordUser);
        assertEquals(webDriver.findElement(By.className("title")).getText(), "Welcome, " + nameUser + "!");
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:5000/profile");
    }

    @Test
    public void test_i4() {
        String emailUser = "Ivan@mail.ru";
        String nameUser = "Ivan Kapkan";
        String passwordUser = "1111";
        registerNewUser(emailUser, nameUser, passwordUser);
        loginUser(emailUser, passwordUser);
        assertEquals(webDriver.findElement(By.className("title")).getText(), "Welcome, " + nameUser + "!");
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:5000/profile");
        webDriver.get(baseUrl + "/logout");
        assertEquals(webDriver.findElement(By.className("title")).getText(), "Test home page");
        webDriver.get(baseUrl + "/profile");
        assertEquals(webDriver.findElement(By.className("notification")).getText(), "Please log in to access this page.");
    }

    @Test
    public void test_i5() {
        String emailUser = "";
        String nameUser = "Ivan Kapkan";
        String passwordUser = "1111";
        registerNewUser(emailUser, nameUser, passwordUser);
        assertFalse(webDriver.findElement(By.className("title")).getText().equals("Login"));
    }

    @Test
    public void test_i6() {
        String emailUser = "ivannnn@mail.ru";
        String nameUser = "Ivan Kapkan";
        String passwordUser = "";
        registerNewUser(emailUser, nameUser, passwordUser);
        assertFalse(webDriver.findElement(By.className("title")).getText().equals("Login"));
    }

    @Test
    public void test_i7() {
        String emailUser = "alexey@mail.ru";
        String nameUser = "";
        String passwordUser = "1234";
        registerNewUser(emailUser, nameUser, passwordUser);
        assertTrue(webDriver.findElement(By.className("title")).getText().equals("Login"));
        registerNewUser(emailUser, nameUser, passwordUser);
        assertFalse(webDriver.findElement(By.className("title")).getText().equals("Login"));
        assertEquals(webDriver.findElement(By.className("notification")).getText(),
                "Email address already exists. Go to login page.");
    }

    @Test
    public void test_i8() {
        String emailUser = "alexey@mail";
        String nameUser = "";
        String passwordUser = "1234";
        registerNewUser(emailUser, nameUser, passwordUser);
        assertFalse(webDriver.findElement(By.className("title")).getText().equals("Login"));
    }

    @Test
    public void test_i9() {
        String emailUser = "ivanivan222@rambler.com";
        String passwordUser = "123456";
        loginUser(emailUser, passwordUser);
        assertEquals(webDriver.findElement(By.className("notification")).getText(),
                "Please check your login details and try again.");
        webDriver.get(baseUrl + "/profile");
        assertEquals(webDriver.findElement(By.className("notification")).getText(), "Please log in to access this page.");

    }

    @Test
    public void test_i10() {
        String emailUser = "ivanivan222>@rambler.com";
        String nameUser = "";
        String passwordUser = "123456";
        loginUser(emailUser, passwordUser);
        assertFalse(webDriver.findElement(By.className("title")).getText().equals("Welcome, " + nameUser + "!"));
        webDriver.get(baseUrl + "/profile");
        assertEquals(webDriver.findElement(By.className("notification")).getText(), "Please log in to access this page.");
        emailUser = "'@'.ru";
        loginUser(emailUser, passwordUser);
        assertFalse(webDriver.findElement(By.className("title")).getText().equals("Welcome, " + nameUser + "!"));
        webDriver.get(baseUrl + "/profile");
        assertEquals(webDriver.findElement(By.className("notification")).getText(), "Please log in to access this page.");
        emailUser = "'";
        loginUser(emailUser, passwordUser);
        assertFalse(webDriver.findElement(By.className("title")).getText().equals("Welcome, " + nameUser + "!"));
        webDriver.get(baseUrl + "/profile");
        assertEquals(webDriver.findElement(By.className("notification")).getText(), "Please log in to access this page.");
    }

    @Test
    public void test_i11() {
        String emailUser = "ivanivan222>@rambler.com";
        String nameUser = "";
        String passwordUser = "123456";
        registerNewUser(emailUser, nameUser, passwordUser);
        assertFalse(webDriver.findElement(By.className("title")).getText().equals("Login"));
        emailUser = "ivanivan22211@rambler.com";
        nameUser = "'Иван<";
        registerNewUser(emailUser, nameUser, passwordUser);
        assertFalse(webDriver.findElement(By.className("title")).getText().equals("Login"));
    }
}
