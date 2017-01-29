package app.controller;

import app.ZipService;
import app.dto.MessageResponse;
import app.UserInfo;
import app.UserInfoProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MainController {

    private UserInfoProvider userInfoProvider;
    private ZipService       zipService;

    @Autowired
    public MainController(UserInfoProvider provider, ZipService service) {
        userInfoProvider = provider;
        zipService = service;
    }

    @RequestMapping(value = "api/user/{username}", method = GET)
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

}
