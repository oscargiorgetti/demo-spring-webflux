package com.example.demo;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController
public class GreetReactiveController {

    private Greeting createGreeting(){
        return new Greeting("Hello @" + Instant.now().toString());
    }

    @GetMapping("/greetings")
    public Publisher<Greeting> greetingPublisher() {
        Flux<Greeting> greetingFlux = Flux.<Greeting>generate(sink -> sink.next(createGreeting())).take(50);
        return greetingFlux;
    }

    //@GetMapping(value = "/greetings/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @GetMapping(value = "/greetings/events", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    Flux<Greeting> events() {
        Flux<Greeting> greetingFlux = Flux.fromStream(Stream.generate(() -> createGreeting()));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(greetingFlux, durationFlux).map(Tuple2::getT1);
    }
}
