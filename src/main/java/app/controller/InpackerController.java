package app.controller;

import app.core.InpackerService;
import app.dto.MessageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class InpackerController {

    private final InpackerService service;

    @Autowired
    public InpackerController(InpackerService inpackerService) {
        service = inpackerService;
    }

    @RequestMapping(value = "api/pack/{username:.+}", method = POST)
    public ResponseEntity<MessageResponse> createPack(@PathVariable String username,
                                                      @RequestParam(required = false) boolean includeImages,
                                                      @RequestParam(required = false) boolean includeVideos) {
        service.createPack(username, includeImages, includeVideos);
        return ResponseEntity.ok(new MessageResponse(String.format("Started creating %s's pack", username)));
    }
}
