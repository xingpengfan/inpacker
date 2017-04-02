package inpacker.core;

import inpacker.core.model.InstagramPost;
import inpacker.core.model.IgPackConfig;

import java.util.concurrent.BlockingDeque;

public interface Repository {

    void getInstagramPosts(IgPackConfig conf, BlockingDeque<InstagramPost> deque);

}
