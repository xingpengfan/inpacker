package inpacker.core;

import inpacker.core.instagram.InstagramPost;
import inpacker.core.instagram.IgPackConfig;

import java.util.concurrent.BlockingDeque;

public interface Repository {

    void getInstagramPosts(IgPackConfig conf, BlockingDeque<InstagramPost> deque);

}
