package app.controller;

import app.core.InpackerService;
import app.dto.MessageResponse;
import app.core.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MainController {

    private final InpackerService service;

    @Autowired
    public MainController(InpackerService inpackerService) {
        this.service = inpackerService;
    }

    @RequestMapping(value = "api/user/{username:.+}", method = GET)
    public ResponseEntity<?> getUserUser(@PathVariable String username) {
        final User user = service.getUser(username);
        if (user == null) {
            return ResponseEntity.status(404).body(new MessageResponse("Not Found"));
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @RequestMapping(value = "api/zip/{username:.+}", method = POST)
    public ResponseEntity<?> createZip(@PathVariable String username) {
        try {
            service.createPack(username, true, true);
            return ResponseEntity.ok(new MessageResponse("created"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}
