package app.controller;

import app.Service;
import app.dto.MessageResponse;
import app.UserInfo;
import app.UserInfoProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MainController {

    private UserInfoProvider userInfoProvider;
    private Service service;

    private static final UserInfo real = new UserInfo();

    @Autowired
    public MainController(UserInfoProvider provider, Service s) {
        userInfoProvider = provider;
        service = s;
        real.biography = "some user biography";
        real.count = 56;
        real.fullName = "Jack White";
        real.isPrivate = false;
        real.profilePic = "https://avatars3.githubusercontent.com/u/7673240?v=3&s=460";
        real.username = "real";
    }

    @RequestMapping(value = "api/user/{username}", method = GET)
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
//        if ("real".equals(username)) {
//            return ResponseEntity.ok(real);
//        } else {
//            return ResponseEntity.status(404).body(new MessageResponse("Not Found"));
//        }
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
            service.createZip(username);
            return ResponseEntity.ok(new MessageResponse("created"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("io-exception"));
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("interrupted-exception"));
        }
    }

}
