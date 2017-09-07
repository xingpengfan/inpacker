package inpacker.web.controller;

import inpacker.instagram.IgItemRepository;
import inpacker.instagram.IgUser;
import inpacker.web.dto.IgUserDto;
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
public class InstagramController {

    private final IgItemRepository igRepo;

    @Autowired
    public InstagramController(IgItemRepository igRepo) {
        this.igRepo = igRepo;
    }

    @GetMapping("api/user/{username:.+}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        final IgUser user = igRepo.getInstagramUser(username);
        return user == null ? status(NOT_FOUND).body(userNotFound(username)) : ok(new IgUserDto(user));
    }
}
