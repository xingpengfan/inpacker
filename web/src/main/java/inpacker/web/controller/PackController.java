package inpacker.web.controller;

import inpacker.core.Pack;
import inpacker.core.PackService;
import inpacker.instagram.IgItemRepository;
import inpacker.instagram.IgPackConfig;
import inpacker.instagram.IgPackItem;
import inpacker.instagram.IgUser;
import inpacker.px.PxItemRepository;
import inpacker.px.PxPackConfig;
import inpacker.px.PxPackItem;
import inpacker.px.PxUser;
import inpacker.web.dto.CreateIgPackRequest;
import inpacker.web.dto.CreatePxPackRequest;
import inpacker.web.dto.PackStatusResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class PackController {

    private final PackService<IgPackConfig> igPackService;
    private final IgItemRepository          igRepo;
    private final PxItemRepository          pxRepo;
    private final PackService<PxPackConfig> pxPackService;

    @Autowired
    public PackController(@Qualifier("igPackService") PackService<IgPackConfig> igPackService,
                          @Qualifier("pxPackService") PackService<PxPackConfig> pxPackService,
                          @Qualifier("igRepository") IgItemRepository igRepo,
                          @Qualifier("pxRepository") PxItemRepository pxRepo) {
        this.igPackService = igPackService;
        this.pxPackService = pxPackService;
        this.igRepo = igRepo;
        this.pxRepo = pxRepo;
    }

    @PostMapping("api/packs/ig")
    public ResponseEntity<?> createIgPack(@RequestBody CreateIgPackRequest req) {
        if (!isValidRequest(req))
            return status(UNPROCESSABLE_ENTITY).body(invalidCreatePackRequestBody());
        final IgUser user = igRepo.getInstagramUser(req.username);
        if (user == null)
            return status(NOT_FOUND).body(userNotFound(req.username));
        final IgPackConfig config = req.toIgPackConfig(user);
        final Pack pack = igPackService.createPack(config);
        return ok(new PackStatusResponse(pack));
    }

    @PostMapping("api/packs/px")
    public ResponseEntity<?> createPxPack(@RequestBody CreatePxPackRequest req) {
        if (!isValidRequest(req))
            return status(UNPROCESSABLE_ENTITY).body(invalidCreatePackRequestBody());
        final PxUser user = pxRepo.get500pxUser(req.username);
        if (user == null)
            return status(NOT_FOUND).body(userNotFound(req.username));
        final PxPackConfig config = req.config(user);
        final Pack pack = pxPackService.createPack(config);
        return ok(new PackStatusResponse(pack));
    }

    @GetMapping("api/packs/{packId:.+}/status")
    public ResponseEntity<?> getPackStatus(@PathVariable("packId") String packId) {
        final Pack pack = getPack(packId);
        if (pack == null)
            return status(NOT_FOUND).body(packNotFound(packId));
        else
            return ok(new PackStatusResponse(pack));
    }

    @GetMapping(value = "packs/{packId:.+}.zip", produces = "application/zip")
    @ResponseBody
    public ResponseEntity<FileSystemResource> downloadPack(@PathVariable("packId") String packId) {
        final Pack pack = getPack(packId);
        if (pack == null || pack.getFile() == null)
            return status(NOT_FOUND).body(null);
        else
            return ok(new FileSystemResource(pack.getFile()));
    }

    @GetMapping("api/packs")
    public ResponseEntity<List<PackStatusResponse>> getAllPacks() {
        final List<Pack> packs = igPackService.getPacks();
        packs.addAll(pxPackService.getPacks());
        return ok(packs.stream().map(PackStatusResponse::new).collect(Collectors.toList()));
    }

    private Pack getPack(String id) {
        Pack pack = igPackService.getPack(id);
        if (pack == null)
            pack = pxPackService.getPack(id);
        return pack;
    }

    private boolean isValidRequest(CreateIgPackRequest req) {
        return req.username != null && !req.username.trim().isEmpty() && (req.includeImages || req.includeVideos);
    }

    private boolean isValidRequest(CreatePxPackRequest req) {
        return req.username != null && !req.username.trim().isEmpty();
    }
}
