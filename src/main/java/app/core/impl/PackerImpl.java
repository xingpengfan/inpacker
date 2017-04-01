package app.core.impl;

import app.core.model.Item;
import app.core.Packer;

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
    public void pack(BlockingDeque<Item> itemsDeque,
                     File packPath,
                     BiFunction<Item, Integer, String> fileNameCreator,
                     Runnable newItemCallback) {
        final ZipOutputStream zos = createZipOutputStream(packPath);
        Item item = item(itemsDeque);
        int index = 1;
        while (!item.id.equals("end")) {
            final String name = fileNameCreator.apply(item, index);
            newItem(item, name, zos);
            item = item(itemsDeque);
            index++;
            newItemCallback.run();
        }
        completePack(zos);
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

    private void completePack(ZipOutputStream zos) {
        try {
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
