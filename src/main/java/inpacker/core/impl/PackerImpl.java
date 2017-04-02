package inpacker.core.impl;

import inpacker.core.model.InstagramPost;
import inpacker.core.Packer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.function.BiFunction;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PackerImpl implements Packer {

    @Override
    public void pack(BlockingDeque<InstagramPost> itemsDeque,
                     File packPath,
                     BiFunction<InstagramPost, Integer, String> fileNameCreator,
                     Runnable newItemCallback) {
        final ZipOutputStream zos = createZipOutputStream(packPath);
        InstagramPost post = takePost(itemsDeque);
        int index = 1;
        while (!post.id.equals("end")) {
            final String name = fileNameCreator.apply(post, index);
            newItem(post, name, zos);
            post = takePost(itemsDeque);
            index++;
            newItemCallback.run();
        }
        completePack(zos);
    }

    private void newItem(InstagramPost post, String filename, ZipOutputStream zos) {
        try {
            zos.putNextEntry(new ZipEntry(post.username + "/" + filename));
            InputStream in = new URL(post.url).openStream();
            byte[] b = new byte[1024];
            int count;
            while ((count = in.read(b)) > 0) {
                zos.write(b, 0, count);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private InstagramPost takePost(BlockingDeque<InstagramPost> posts) {
        try {
            return posts.takeFirst();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private ZipOutputStream createZipOutputStream(File path) {
        try {
            return new ZipOutputStream(new FileOutputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void completePack(ZipOutputStream zos) {
        try {
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
