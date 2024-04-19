package example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong(1);

    @GetMapping("/greeting")
    public ResponseEntity<Greeting> greeting(@RequestParam(name="name", defaultValue="world!") String name) {
        return ResponseEntity.ok(new Greeting(counter.getAndIncrement(), name));
    }
}
