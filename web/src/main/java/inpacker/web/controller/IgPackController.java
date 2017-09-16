package inpacker.web.controller;

import inpacker.core.Pack;
import inpacker.core.PackService;
import inpacker.instagram.IgItemRepository;
import inpacker.instagram.IgPackConfig;
import inpacker.instagram.IgPackItem;
import inpacker.instagram.IgUser;
import inpacker.web.dto.CreateIgPackRequest;
import inpacker.web.dto.PackStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static inpacker.web.dto.MessageResponse.invalidCreatePackRequestBody;
import static inpacker.web.dto.MessageResponse.packNotFound;
import static inpacker.web.dto.MessageResponse.userNotFound;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Controller
public class IgPackController {

    private final PackService<IgPackConfig, IgPackItem> packService;
    private final IgItemRepository igRepo;

    @Autowired
    public IgPackController(@Qualifier("igPackService") PackService<IgPackConfig, IgPackItem> packService,
                            @Qualifier("igRepository") IgItemRepository igRepo) {
        this.packService = packService;
        this.igRepo = igRepo;
    }

    @PostMapping("api/packs/ig")
    public ResponseEntity<?> createPack(@RequestBody CreateIgPackRequest req) {
        if (!isValidRequest(req))
            return status(UNPROCESSABLE_ENTITY).body(invalidCreatePackRequestBody());
        final IgUser user = igRepo.getInstagramUser(req.username);
        if (user == null)
            return status(NOT_FOUND).body(userNotFound(req.username));
        final IgPackConfig config = req.toIgPackConfig(user);
        final Pack pack = packService.createPack(config);
        return ok(new PackStatusResponse(pack));
    }

    @GetMapping("api/packs/ig/{packId:.+}/status")
    public ResponseEntity<?> getPackStatus(@PathVariable("packId") String packId) {
        final Pack pack = packService.getPack(packId);
        if (pack == null)
            return status(NOT_FOUND).body(packNotFound(packId));
        else
            return ok(new PackStatusResponse(pack));
    }

    @GetMapping("api/packs")
    public ResponseEntity<List<PackStatusResponse>> getPacks() {
        return ok(packService.getPacks().stream().map(PackStatusResponse::new).collect(Collectors.toList()));
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

    public static boolean isValidRequest(CreateIgPackRequest req) {
        return req.username != null && !req.username.trim().isEmpty() && (req.includeImages || req.includeVideos);
    }
}
