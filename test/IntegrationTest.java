import org.junit.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class IntegrationTest {

    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333");
            assertTrue(browser.pageSource().contains("Welcome to PlayChat"));
        });
    }

}
