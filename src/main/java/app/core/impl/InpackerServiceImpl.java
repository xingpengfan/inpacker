package app.core.impl;

import app.core.InpackerService;
import app.core.Item;
import app.core.Packer;
import app.core.UserMediaProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class InpackerServiceImpl implements InpackerService {

    private final UserMediaProvider mediaProvider;
    private final Packer            packer;

    @Autowired
    public InpackerServiceImpl(UserMediaProvider userMediaProvider, Packer packer) {
        this.mediaProvider = userMediaProvider;
        this.packer = packer;
    }

    @Override
    public void createPack(String username, boolean includeImages, boolean includeVideos) {
        BlockingDeque<Item> itemsDeque = new LinkedBlockingDeque<>();

        new Thread(() -> mediaProvider.getUserMedia(username, itemsDeque)).start();

        packer.pack(itemsDeque, (item) -> item.id);
    }
}
