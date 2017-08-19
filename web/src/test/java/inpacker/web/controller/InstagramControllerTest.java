package inpacker.web.controller;

import inpacker.core.Pack;
import inpacker.core.PackService;
import inpacker.instagram.IgItemRepository;
import inpacker.instagram.IgPackConfig;
import inpacker.instagram.IgPackItem;
import inpacker.instagram.IgUser;
import inpacker.web.dto.IgUserDto;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import inpacker.web.dto.CreatePackRequest;
import inpacker.web.dto.MessageResponse;
import inpacker.web.dto.PackStatusResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class InstagramControllerTest {

    private static final IgUser someUser = new IgUser("123_321", "dude",  "Some User", "bio", "profile_pic_url", false, false, 555);

    @Mock private PackService<IgPackConfig, IgPackItem> packService;
    @Mock private IgItemRepository repository;
    private InstagramController instagramController;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(repository.getInstagramUser(someUser.getUsername())).thenReturn(someUser);
        instagramController = new InstagramController(packService, repository);
    }

    @Test
    public void getUserShouldRespond404IfUserWithTheSpecifiedUsernameNotFound() {
        final String username = "not_existing_user";

        when(repository.getInstagramUser(username)).thenReturn(null);
        final ResponseEntity<?> resp = instagramController.getUser(username);

        assertEquals(resp.getStatusCodeValue(), 404);
        assertEquals(resp.getBody(), MessageResponse.userNotFound(username));
        verify(repository, times(1)).getInstagramUser(username);
    }

    @Test
    public void getUserShouldRespond200WithRequestedUser() {
        final String username = someUser.getUsername();

        final ResponseEntity<?> resp = instagramController.getUser(username);

        assertEquals(resp.getStatusCodeValue(), 200);
        assertEquals(resp.getBody(), new IgUserDto(someUser));
        verify(repository, times(1)).getInstagramUser(username);
    }

    @Test
    public void createPackShouldRespond422IfCreatePackRequestBodyIsNotValid() {
        final CreatePackRequest invalidReq = new CreatePackRequest("", false, false, "asd");

        final ResponseEntity<?> resp = instagramController.createPack(invalidReq);

        assertEquals(resp.getStatusCodeValue(), 422);
        assertEquals(resp.getBody(), MessageResponse.invalidCreatePackRequestBody());
    }

    @Test
    public void createPackShouldRespond404IfUserWithTheSpecifiedUsernameNotFound() {
        final String username = "not_existing_user";
        final CreatePackRequest req = new CreatePackRequest(username, true, true, "id");

        when(repository.getInstagramUser(username)).thenReturn(null);
        final ResponseEntity<?> resp = instagramController.createPack(req);

        assertEquals(resp.getStatusCodeValue(), 404);
        assertEquals(resp.getBody(), MessageResponse.userNotFound(username));
        verify(repository, times(1)).getInstagramUser(username);
    }

    @Test
    public void createPackShouldRespond200WithPackStatus() {
        final CreatePackRequest req = new CreatePackRequest(someUser.getUsername(), true, true, "id");
        final Pack pack = new Pack("some_pack");

        when(packService.createPack(any())).thenReturn(pack);
        final ResponseEntity<?> resp = instagramController.createPack(req);

        assertEquals(resp.getStatusCodeValue(), 200);
        assertEquals(resp.getBody(), new PackStatusResponse(pack));
        verify(repository, times(1)).getInstagramUser(someUser.getUsername());
        verify(packService, times(1)).createPack(any());
    }

}
