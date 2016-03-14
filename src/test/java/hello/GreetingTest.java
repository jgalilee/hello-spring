package hello;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class GreetingTest extends TestCase {

    private Greeting greeting;
    private int defaultId = 1;
    private String defaultContent = "Hello";

    @Before
    public void setUp() {
        greeting = new Greeting(defaultId, defaultContent);
    }

    @Test
    public void testGetId() {
        assertEquals(1, greeting.getId());
    }

    @Test
    public void testGetContent() {
        assertEquals(defaultContent, greeting.getContent());
    }

}