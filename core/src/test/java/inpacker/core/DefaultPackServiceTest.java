package inpacker.core;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.testng.Assert.*;

public class DefaultPackServiceTest {

    private DefaultPackService<PackConfig<PackItem>, PackItem> service;
    @Mock private Packer<PackItem> packer;
    @Mock private ItemRepository<PackConfig<PackItem>, PackItem> repository;
    @Mock private File packsDir;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(packsDir.exists()).thenReturn(false);
        when(packsDir.mkdirs()).thenReturn(true);
        service = new DefaultPackService<>(packsDir, repository, packer);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createPackShouldThrowNpeIfConfigIsNull() {
        service.createPack(null);
    }

    @Test
    public void createPackShouldNotCreateNewPackIfOneWithTheSpecifiedConfigAlreadyExists() {
        final PackConfig<PackItem> config = new FakePackConfig<>();
        final Pack pack1 = service.createPack(config);

        final Pack pack2 = service.createPack(config);

        assertEquals(pack1, pack2);
        verify(repository, times(1)).getPackItems(eq(config), any());
        verify(packer, times(1)).pack(any(), any(), eq(pack1));
    }

}

class FakePackConfig<I extends PackItem> implements PackConfig<I> {

    @Override
    public boolean test(I item) {
        return true;
    }

    @Override
    public String getUniqueId() {
        return "123";
    }

    @Override
    public int numberOfItems() {
        return -1;
    }
}

class FakePackItem implements PackItem {

    private final String u;
    private final String n;

    FakePackItem(String url, String name) {
        u = url;
        n = name;
    }

    @Override
    public String getUrl() {
        return u;
    }

    @Override
    public String getFileName() {
        return n;
    }
}
