package app.controller;

import app.dto.NotFoundResponse;
import app.UserInfo;
import app.UserInfoProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MainController {

    private UserInfoProvider userInfoProvider;

    @Autowired
    public MainController(UserInfoProvider provider) {
        userInfoProvider = provider;
    }

    @RequestMapping(value = "api/user/{username}", method = GET)
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        final UserInfo userInfo = userInfoProvider.getUserInfo(username);
        if (userInfo == null) {
            return ResponseEntity.status(404).body(new NotFoundResponse());
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }

}
