package app.controller;

import app.MainService;
import app.ZipService;
import app.dto.MessageResponse;
import app.UserInfo;
import app.UserInfoProvider;
import app.dto.PackDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MainController {

    private final UserInfoProvider userInfoProvider;
    private final ZipService       zipService;
    private final MainService      mainService;

    @Autowired
    public MainController(UserInfoProvider userInfoProvider, ZipService zipService, MainService mainService) {
        this.userInfoProvider = userInfoProvider;
        this.zipService = zipService;
        this.mainService = mainService;
    }

    @RequestMapping(value = "api/user/{username:.+}", method = GET)
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        final UserInfo userInfo = userInfoProvider.getUserInfo(username);
        if (userInfo == null) {
            return ResponseEntity.status(404).body(new MessageResponse("Not Found"));
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }

    @RequestMapping(value = "api/zip/{username}", method = POST)
    public ResponseEntity<?> createZip(@PathVariable String username) {
        try {
            zipService.createZip(username);
            return ResponseEntity.ok(new MessageResponse("created"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("io-exception"));
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("interrupted-exception"));
        }
    }

    @RequestMapping(value = "api/zip", method = GET)
    public ResponseEntity<List<PackDto>> getZipDirContent() {
        final List<String> files = mainService.getZipDirContent();
        return ResponseEntity.ok(files.stream()
                                      .map((name) -> new PackDto(name, "done"))
                                      .collect(Collectors.toList()));
    }

}
