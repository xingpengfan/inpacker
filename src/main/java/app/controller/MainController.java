package app.controller;

import app.core.MainService;
import app.core.ZipService;
import app.dto.MessageResponse;
import app.core.User;
import app.core.UserProvider;
import app.dto.PackDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MainController {

    private final UserProvider userProvider;
    private final ZipService   zipService;
    private final MainService  mainService;

    @Autowired
    public MainController(UserProvider userProvider, ZipService zipService, MainService mainService) {
        this.userProvider = userProvider;
        this.zipService = zipService;
        this.mainService = mainService;
    }

    @RequestMapping(value = "api/user/{username:.+}", method = GET)
    public ResponseEntity<?> getUserUser(@PathVariable String username) {
        final User user = userProvider.getUser(username);
        if (user == null) {
            return ResponseEntity.status(404).body(new MessageResponse("Not Found"));
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @RequestMapping(value = "api/zip/{username:.+}", method = POST)
    public ResponseEntity<?> createZip(@PathVariable String username) {
        try {
            zipService.createZip(username);
            return ResponseEntity.ok(new MessageResponse("created"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping(value = "api/packs", method = GET)
    public ResponseEntity<List<PackDto>> getZipDirContent() {
        final List<String> files = mainService.getZipDirContent();
        return ResponseEntity.ok(files.stream()
                                      .map((name) -> new PackDto(name, "done"))
                                      .collect(Collectors.toList()));
    }

}
