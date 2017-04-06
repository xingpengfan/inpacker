package inpacker.web.controller;

import inpacker.core.*;
import inpacker.instagram.*;
import inpacker.web.dto.CreatePackRequest;

import inpacker.web.dto.MessageResponse;
import inpacker.web.dto.PackStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;

import static inpacker.web.dto.MessageResponse.invalidCreatePackRequestBody;
import static inpacker.web.dto.MessageResponse.packNotFound;
import static inpacker.web.dto.MessageResponse.userNotFound;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Controller
public class InstagramController {

    private final DefaultPackService<IgPackConfig, IgPackItem> packService;
    private final IgRepository igRepo;

    @Autowired
    public InstagramController(@Qualifier("ig") DefaultPackService<IgPackConfig, IgPackItem> packService,
                               IgRepository igRepo) {
        this.packService = packService;
        this.igRepo = igRepo;
    }

    @GetMapping(value = "api/user/{username:.+}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        final IgUser user = igRepo.getInstagramUser(username);
        return user == null ? status(NOT_FOUND).body(userNotFound(username)) : ok(user);
    }

    @PostMapping(value = "api/packs")
    public ResponseEntity<?> createPack(@RequestBody CreatePackRequest req) {
        if (!isValidRequest(req))
            return status(UNPROCESSABLE_ENTITY).body(invalidCreatePackRequestBody());
        final IgUser user = igRepo.getInstagramUser(req.username);
        if (user == null)
            return status(NOT_FOUND).body(userNotFound(req.username));
        final IgPackConfig config = req.toIgPackConfig(user);
        final Pack pack = packService.createPack(config);
        return ok(new PackStatusResponse(pack));
    }

    @GetMapping(value = "api/pack/{packName:.+}/status")
    public ResponseEntity<?> getPackStatus(@PathVariable String packName) {
        final Pack pack = packService.getPack(packName);
        if (pack == null)
            return status(NOT_FOUND).body(packNotFound(packName));
        else
            return ok(new PackStatusResponse(pack));
    }

    @GetMapping(value = "packs/{packName:.+}.zip", produces = "application/zip")
    @ResponseBody
    public ResponseEntity<FileSystemResource> downloadPack(@PathVariable String packName) {
        final File packFile = packService.getPackFile(packName);
        if (packFile == null)
            return status(NOT_FOUND).body(null);
        else
            return ok(new FileSystemResource(packFile));
    }

    public static boolean isValidRequest(CreatePackRequest req) {
        return req.username != null && !req.username.isEmpty() && (req.includeImages || req.includeVideos);
    }
}
