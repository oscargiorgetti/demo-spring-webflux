package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {
    private static List<Person> personList = new ArrayList<>();
    static {
        personList.add(new Person(1, "John"));
        personList.add(new Person(2, "Jane"));
        personList.add(new Person(3, "Max"));
        personList.add(new Person(4, "Alex"));
        personList.add(new Person(5, "Aloy"));
        personList.add(new Person(6, "Sarah"));
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id, @RequestParam(defaultValue = "2") int delay)
            throws InterruptedException {
        //Thread.sleep(delay * 1000);
        Optional<Person> p = personList.stream()
                .filter((person) -> person.getId() == id)
                .findFirst();
        if (p.isPresent())
            return new ResponseEntity<>(p.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
