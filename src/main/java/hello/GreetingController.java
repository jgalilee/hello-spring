package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    public static final String DefaultGreeting = "World";

    private final AtomicLong counter = new AtomicLong();

    private static final String template = "Hello, %s!";

    @RequestMapping(method= RequestMethod.GET, value="/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue=DefaultGreeting) String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}