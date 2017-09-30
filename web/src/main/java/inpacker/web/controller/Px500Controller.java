package inpacker.web.controller;

import inpacker.px.PxItemRepository;
import inpacker.px.PxUser;
import inpacker.web.dto.PxUserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static inpacker.web.dto.MessageResponse.userNotFound;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class Px500Controller {

    private final PxItemRepository pxRepo;

    @Autowired
    public Px500Controller(PxItemRepository repo) {
        this.pxRepo = repo;
    }

    @GetMapping("api/px/user/{username:.+}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        final PxUser user = pxRepo.get500pxUser(username);
        return user == null ? status(NOT_FOUND).body(userNotFound(username)) : ok(new PxUserDto(user));
    }
}
