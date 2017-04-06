package inpacker.web;

import inpacker.core.DefaultPackService;
import inpacker.core.Pack;
import inpacker.instagram.IgPackConfig;
import inpacker.instagram.IgPackItem;
import inpacker.instagram.IgRepository;
import inpacker.instagram.IgUser;
import inpacker.web.controller.InstagramController;
import inpacker.web.dto.CreatePackRequest;
import inpacker.web.dto.MessageResponse;
import inpacker.web.dto.PackStatusResponse;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class InstagramControllerTest {

    private static final IgUser SOME_USER = new IgUser("123_321", "dude", false, "Some User", "bio", "profile_pic_url", 555, false);

    @Mock private DefaultPackService<IgPackConfig, IgPackItem> defaultPackService;
    @Mock private IgRepository repository;
    private InstagramController instagramController;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(repository.getInstagramUser(SOME_USER.username)).thenReturn(SOME_USER);
        instagramController = new InstagramController(defaultPackService, repository);
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
        final String username = SOME_USER.username;

        final ResponseEntity<?> resp = instagramController.getUser(username);

        assertEquals(resp.getStatusCodeValue(), 200);
        assertEquals(resp.getBody(), SOME_USER);
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
        final CreatePackRequest req = new CreatePackRequest(SOME_USER.username, true, true, "id");
        final Pack pack = new Pack("some_pack");

        when(repository.getInstagramUser(SOME_USER.username)).thenReturn(SOME_USER);
        when(defaultPackService.createPack(any())).thenReturn(pack);
        final ResponseEntity<?> resp = instagramController.createPack(req);

        assertEquals(resp.getStatusCodeValue(), 200);
        assertEquals(resp.getBody(), new PackStatusResponse(pack));
        verify(repository, times(1)).getInstagramUser(SOME_USER.username);
        verify(defaultPackService, times(1)).createPack(any());
    }

}
