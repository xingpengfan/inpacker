package app.controller;

import app.dto.NotFoundResponse;
import lib.UserInfo;
import lib.UserInfoProvider;
import lib.UserInfoProviderImpl;
import lib.UserNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MainController {

    private UserInfoProvider userInfoProvider = new UserInfoProviderImpl();

    @RequestMapping(value = "api/user/{username}", method = GET)
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        try {
            final UserInfo userInfo = userInfoProvider.getUserInfo(username);
            return ResponseEntity.ok(userInfo);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(new NotFoundResponse());
        }
    }

}
