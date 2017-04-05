package inpacker.web.controller;

import inpacker.core.*;
import inpacker.instagram.*;
import inpacker.web.dto.CreatePackRequest;
import inpacker.web.dto.MessageResponse;

import inpacker.web.dto.PackStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class InstagramController {

    private final DefaultPackService<IgPackConfig, IgPackItem> defaultPackService;
    private final IgRepository instagramRepository;

    @Autowired
    public InstagramController(@Qualifier("ig") DefaultPackService<IgPackConfig, IgPackItem> defaultPackService,
                               IgRepository instagramRepository) {
        this.defaultPackService = defaultPackService;
        this.instagramRepository = instagramRepository;
    }

    @RequestMapping(value = "api/user/{username:.+}", method = GET)
    public ResponseEntity<?> getUser(@PathVariable String username) {
        final IgUser user = instagramRepository.getInstagramUser(username);
        if (user == null)
            return status(NOT_FOUND).body(MessageResponse.userNotFound(username));
        else
            return ok(user);
    }

    @RequestMapping(value = "api/packs", method = POST)
    public ResponseEntity<?> createPack(@RequestBody CreatePackRequest req) {
        final IgUser user = instagramRepository.getInstagramUser(req.username);
        if (user == null)
            return status(NOT_FOUND).body(MessageResponse.userNotFound(req.username));
        final IgPackConfig config = req.getIgPackConfig(user);
        final Pack pack = defaultPackService.createPack(config);
        return ok(new PackStatusResponse(pack));
    }

    @RequestMapping(value = "api/pack/{packName:.+}/status", method = GET)
    public ResponseEntity<?> getPackStatus(@PathVariable String packName) {
        final Pack pack = defaultPackService.getPack(packName);
        if (pack == null)
            return status(NOT_FOUND).body(MessageResponse.packNotFound(packName));
        else
            return ok(new PackStatusResponse(pack));
    }

    @RequestMapping(value = "packs/{packName:.+}.zip", method = GET, produces = "application/zip")
    @ResponseBody
    public ResponseEntity<FileSystemResource> downloadPack(@PathVariable String packName) {
        final File packFile = defaultPackService.getPackFile(packName);
        if (packFile == null)
            return status(404).body(null);
        else
            return ok(new FileSystemResource(packFile));
    }
}
