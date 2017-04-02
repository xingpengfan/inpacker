package inpacker.core;

import inpacker.instagram.InstagramPost;
import inpacker.instagram.IgPackConfig;

import java.util.concurrent.BlockingDeque;

public interface Repository {

    void getInstagramPosts(IgPackConfig conf, BlockingDeque<InstagramPost> deque);

}
