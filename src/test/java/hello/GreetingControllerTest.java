package hello;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest(randomPort=true)
public class GreetingControllerTest extends TestCase {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testHelloOnlyGET() throws Exception {
        for(HttpMethod method : HttpMethod.values()) {
            if(method.equals(HttpMethod.OPTIONS) || method.equals(HttpMethod.TRACE)) {
                continue;
            }
            ResultActions resultActions = this.mockMvc.perform(request(method, "/greeting"));
            if(method.equals(HttpMethod.GET)) {
                resultActions.andExpect(status().isOk());
            } else {
                resultActions.andExpect(status().isMethodNotAllowed());
            }
        }
    }

    @Test
    public void testHelloContentType() throws Exception {
        this.mockMvc.perform(get("/greeting")).
                andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testHelloWithoutName() throws Exception {
        this.mockMvc.perform(get("/greeting")).
                andExpect(jsonPath("$.id", notNullValue())).
                andExpect(jsonPath("$.content", is(String.format("Hello, %s!", GreetingController.DefaultGreeting))));
    }

    @Test
    public void testHelloWithName() throws Exception {
        String name = "John";
        this.mockMvc.perform(get(String.format("/greeting/?name=%s", name))).
                andExpect(jsonPath("$.id", notNullValue())).
                andExpect(jsonPath("$.content", is(String.format("Hello, %s!", name))));
    }

}