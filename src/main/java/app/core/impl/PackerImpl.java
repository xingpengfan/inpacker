package app.core.impl;

import app.core.Item;
import app.core.Packer;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class PackerImpl implements Packer {

    @Override
    public void pack(BlockingDeque<Item> itemsDeque, File packPath, Function<Item, String> getFileName) {
        final ZipOutputStream zos = createZipOutputStream(packPath);
        Item item = item(itemsDeque);
        while (!item.id.equals("end")) {
            final String name = getFileName.apply(item);
            newItem(item, name, zos);
            item = item(itemsDeque);
        }
    }

    private void newItem(Item item, String filename, ZipOutputStream zos) {
        try {
            zos.putNextEntry(new ZipEntry(item.username + "/" + filename));
            InputStream in = new URL(item.url).openStream();
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

    private Item item(BlockingDeque<Item> items) {
        try {
            return items.takeFirst();
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
}
