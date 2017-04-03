package inpacker.instagram;

import inpacker.core.PackSupport;
import inpacker.core.Packer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipPacker implements Packer<IgPackItem> {

    @Override
    public void pack(BlockingDeque<IgPackItem> postsDeque,
                     File packPath,
                     Consumer<IgPackItem> newItemSuccess,
                     Consumer<IgPackItem> newItemFail,
                     Runnable done) {
        final ZipOutputStream zos = createZipOutputStream(packPath);
        PackSupport.createZipPack(zos, postsDeque, newItemSuccess, newItemFail, done);
    }

    private void newItem(IgPost post, String filename, ZipOutputStream zos) {
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

    private IgPost takePost(BlockingDeque<IgPost> posts) {
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
