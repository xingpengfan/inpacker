package inpacker.web;

import inpacker.core.DefaultPackService;
import inpacker.instagram.IgPackConfig;
import inpacker.instagram.IgPackItem;
import inpacker.instagram.IgRepository;
import inpacker.web.controller.InstagramController;
import inpacker.web.dto.CreatePackRequest;
import inpacker.web.dto.MessageResponse;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class InstagramControllerTest {

    @Mock private DefaultPackService<IgPackConfig, IgPackItem> defaultPackService;
    @Mock private IgRepository repository;
    private InstagramController instagramController;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instagramController = new InstagramController(defaultPackService, repository);
    }

    @Test
    public void createPackShouldReturn404IfUserWithTheSpecifiedUsernameNotFound() {
        final String username = "not_existing_user";
        final CreatePackRequest req = new CreatePackRequest();
        req.username = username;

        when(repository.getInstagramUser(username)).thenReturn(null);
        final ResponseEntity<?> resp = instagramController.createPack(req);

        assertEquals(resp.getStatusCodeValue(), 404);
        assertEquals(resp.getBody(), MessageResponse.userNotFound(username));
        verify(repository, times(1)).getInstagramUser(username);
    }

}
