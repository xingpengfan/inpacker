package app.controller;

import app.core.Inpacker;
import app.core.model.Pack;
import app.core.model.PackSettings;
import app.core.model.User;
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
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class InpackerController {

    private final Inpacker inpacker;

    @Autowired
    public InpackerController(Inpacker inpacker) {
        this.inpacker = inpacker;
    }

    @RequestMapping(value = "api/user/{username:.+}", method = GET)
    public ResponseEntity<?> getUser(@PathVariable String username) {
        final User user = inpacker.getUser(username);
        if (user == null)
            return status(404).body(new MessageResponse("Not Found"));
        else
            return ok(user);
    }

    @RequestMapping(value = "api/pack/{username:.+}", method = POST)
    public ResponseEntity<Pack> createPack(@PathVariable String username,
                                                         @RequestBody PackSettingsDto packSettingsDto) {
        final PackSettings packSettings = packSettingsDto.getPackSettings();
        final String packName = inpacker.getPackName(username, packSettings);
        final Pack pack = inpacker.getPack(packName);
        if (pack == null) {
            new Thread(() -> inpacker.createPack(username, packSettings)).start();
            return ok(new Pack(packName));
        } else
            return ok(pack);
    }

    @RequestMapping(value = "api/pack/{packName:.+}/status", method = GET)
    public ResponseEntity<Pack> getPackStatus(@PathVariable String packName) {
        return ok(inpacker.getPack(packName));
    }

    @RequestMapping(value = "api/packs", method = GET)
    public ResponseEntity<List<Pack>> getPacksList() {
        return ok(inpacker.getPacks());
    }

    @RequestMapping(value = "packs/{packName:.+}.zip", method = GET, produces = "application/zip")
    @ResponseBody
    public ResponseEntity<FileSystemResource> downloadPack(@PathVariable String packName) {
        final File packFile = inpacker.getPackFile(packName);
        if (packFile == null)
            return status(404).body(null);
        else
            return ok(new FileSystemResource(packFile));
    }
}
