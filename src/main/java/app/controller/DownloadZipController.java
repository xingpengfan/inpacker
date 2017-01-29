package app.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class DownloadZipController {

    @RequestMapping(value = "zip/{username}.zip", method = GET, produces = "application/zip")
    @ResponseBody
    public FileSystemResource getZip(@PathVariable String username) {
        return new FileSystemResource(new File("/home/igor/" + username + ".zip"));
    }
}
