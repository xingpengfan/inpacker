package app.controller;

import app.core.InpackerService;
import app.core.model.PackSettings;
import app.core.model.User;
import app.dto.PackStatusResponse;
import app.dto.MessageResponse;
import app.dto.PackSettingsDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class InpackerController {

    private final InpackerService service;

    @Autowired
    public InpackerController(InpackerService inpackerService) {
        service = inpackerService;
    }

    @RequestMapping(value = "api/user/{username:.+}", method = GET)
    public ResponseEntity<?> getUser(@PathVariable String username) {
        final User user = service.getUser(username);
        if (user == null)
            return status(404).body(new MessageResponse("Not Found"));
        else
            return ok(user);
    }

    @RequestMapping(value = "api/pack/{username:.+}", method = POST)
    public ResponseEntity<PackStatusResponse> createPack(@PathVariable String username,
                                                         @RequestBody PackSettingsDto packSettingsDto) {
        final PackSettings packSettings = packSettingsDto.getPackSettings();
        final String packName = service.getPackName(username, packSettings);
        final Boolean packStatus = service.getPackStatus(packName);
        if (packStatus == null) {
            new Thread(() -> service.createPack(username, packSettings)).start();
            return ok(new PackStatusResponse(packName, false));
        } else
            return ok(new PackStatusResponse(packName, packStatus));
    }

    @RequestMapping(value = "api/pack/{packName:.+}/status", method = GET)
    public ResponseEntity<?> getPackStatus(@PathVariable String packName) {
        final boolean packStatus = service.getPackStatus(packName);
        return ok(new PackStatusResponse(packName, packStatus));
    }

    @RequestMapping(value = "packs/{packName:.+}.zip", method = GET, produces = "application/zip")
    @ResponseBody
    public ResponseEntity<FileSystemResource> downloadPack(@PathVariable String packName) {
        final File packFile = service.getPackFile(packName);
        if (packFile == null)
            return status(404).body(null);
        else
            return ok(new FileSystemResource(packFile));
    }
}
