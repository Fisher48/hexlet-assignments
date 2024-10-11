package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// BEGIN
@RestController
public class WelcomeController {

    @Autowired
    private Daytime daytime;

    @GetMapping(path = "/welcome")
    public void show() {
        if (daytime.getName().equals("day")) {
            System.out.println("It is night now! Welcome to Spring!");
        } else {
            System.out.println("It is day now! Welcome to Spring!");
        }
    }
}
// END
