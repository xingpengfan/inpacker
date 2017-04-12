package inpacker.web.controller;

import inpacker.core.*;

import inpacker.instagram.IgPackConfig;
import inpacker.instagram.IgPackItem;
import inpacker.instagram.IgRepository;
import inpacker.instagram.IgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import inpacker.web.dto.CreatePackRequest;
import inpacker.web.dto.PackStatusResponse;

import java.io.File;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static inpacker.web.dto.MessageResponse.invalidCreatePackRequestBody;
import static inpacker.web.dto.MessageResponse.packNotFound;
import static inpacker.web.dto.MessageResponse.userNotFound;

@Controller
public class InstagramController {

    private final PackService<IgPackConfig, IgPackItem> packService;
    private final IgRepository igRepo;

    @Autowired
    public InstagramController(@Qualifier("igPackService") PackService<IgPackConfig, IgPackItem> packService,
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

    @GetMapping(value = "api/packs/{packId:.+}/status")
    public ResponseEntity<?> getPackStatus(@PathVariable("packId") String packId) {
        final Pack pack = packService.getPack(packId);
        if (pack == null)
            return status(NOT_FOUND).body(packNotFound(packId));
        else
            return ok(new PackStatusResponse(pack));
    }

    @GetMapping(value = "packs/{packId:.+}.zip", produces = "application/zip")
    @ResponseBody
    public ResponseEntity<FileSystemResource> downloadPack(@PathVariable("packId") String packId) {
        final File packFile = packService.getPackFile(packId);
        if (packFile == null)
            return status(NOT_FOUND).body(null);
        else
            return ok(new FileSystemResource(packFile));
    }

    public static boolean isValidRequest(CreatePackRequest req) {
        return req.username != null && !req.username.isEmpty() && (req.includeImages || req.includeVideos);
    }
}
