package inpacker.web.controller;

import inpacker.core.Pack;
import inpacker.core.PackService;

import inpacker.px.PxItemRepository;
import inpacker.px.PxPackConfig;
import inpacker.px.PxPackItem;
import inpacker.px.PxUser;

import inpacker.web.dto.CreatePxPackRequest;
import inpacker.web.dto.PackStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static inpacker.web.dto.MessageResponse.invalidCreatePackRequestBody;
import static inpacker.web.dto.MessageResponse.packNotFound;
import static inpacker.web.dto.MessageResponse.userNotFound;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Controller
public class PxPackController {

    private final PackService<PxPackConfig, PxPackItem> packService;
    private final PxItemRepository                      pxRepo;

    @Autowired
    public PxPackController(@Qualifier("pxPackService") PackService<PxPackConfig, PxPackItem> packService,
                            @Qualifier("pxRepository") PxItemRepository pxRepo) {
        this.packService = packService;
        this.pxRepo = pxRepo;
    }

    @PostMapping("api/packs/px")
    public ResponseEntity<?> createPack(@RequestBody CreatePxPackRequest req) {
        if (!isValidRequest(req))
            return status(UNPROCESSABLE_ENTITY).body(invalidCreatePackRequestBody());
        final PxUser user = pxRepo.get500pxUser(req.username);
        if (user == null)
            return status(NOT_FOUND).body(userNotFound(req.username));
        final PxPackConfig config = req.config(user);
        final Pack pack = packService.createPack(config);
        return ok(new PackStatusResponse(pack));
    }

    @GetMapping("api/packs/px/{packId:.+}/status")
    public ResponseEntity<?> getPackStatus(@PathVariable("packId") String packId) {
        final Pack pack = packService.getPack(packId);
        if (pack == null)
            return status(NOT_FOUND).body(packNotFound(packId));
        else
            return ok(new PackStatusResponse(pack));
    }

    public static boolean isValidRequest(CreatePxPackRequest req) {
        return req.username != null && !req.username.trim().isEmpty();
    }
}
