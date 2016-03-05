import models.HomeModel;
import org.junit.*;
import play.twirl.api.Content;

import static org.junit.Assert.*;


public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render(new HomeModel());
        assertEquals("text/html", html.contentType());
        assertTrue(html.body().contains("Welcome to PlayChat"));
    }


}
