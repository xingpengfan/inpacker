package app.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;

@Controller
public class AppErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
