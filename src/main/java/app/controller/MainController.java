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

    private static final UserInfo real = new UserInfo();

    @Autowired
    public MainController(UserInfoProvider provider) {
        userInfoProvider = provider;
        real.biography = "some user biography";
        real.count = 56;
        real.fullName = "Jack White";
        real.isPrivate = false;
        real.profilePic = "https://avatars3.githubusercontent.com/u/7673240?v=3&s=460";
        real.username = "real";
    }

    @RequestMapping(value = "api/user/{username}", method = GET)
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        if ("real".equals(username)) {
            return ResponseEntity.ok(real);
        } else {
            return ResponseEntity.status(404).body(new NotFoundResponse());
        }
//        final UserInfo userInfo = userInfoProvider.getUserInfo(username);
//        if (userInfo == null) {
//            return ResponseEntity.status(404).body(new NotFoundResponse());
//        } else {
//            return ResponseEntity.ok(userInfo);
//        }
    }

}
