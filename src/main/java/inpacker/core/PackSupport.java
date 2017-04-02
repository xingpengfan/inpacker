package inpacker.core;

import java.io.*;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PackSupport {

    public static <T extends PackItem> void createZipPack(ZipOutputStream zos,
                                                          BlockingDeque<T> deq,
                                                          Consumer<T> newItemSuccess,
                                                          Consumer<T> newItemFail,
                                                          Runnable done) {
        T item;
        try {
            item = deq.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            done.run();
            return;
        }
        while (!item.getFileName().equals("end")) {
            final boolean added = addItemToZip(item, zos);
            if (added) newItemSuccess.accept(item);
            else newItemFail.accept(item);
            try {
                item = deq.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                done.run();
                return;
            }
        }
        try {
            zos.close();
        } catch (IOException e) {
            throw new RuntimeException(e); // FIXME
        }
        done.run();
    }

    public static <T extends PackItem> void createZipPack(ZipOutputStream zos, Iterable<T> items,
                                                          Consumer<T> newItemSuccess,
                                                          Consumer<T> newItemFail,
                                                          Runnable done) {
        for (T item : items) {
            final boolean added = addItemToZip(item, zos);
            if (added) newItemSuccess.accept(item);
            else newItemFail.accept(item);
        }
        try {
            zos.close();
        } catch (IOException e) {
            throw new RuntimeException(e); // FIXME
        }
        done.run();
    }

    private static boolean addItemToZip(PackItem item, ZipOutputStream zos) {
        try {
            zos.putNextEntry(new ZipEntry(item.getFileName()));
            saveFromUrl(zos, item.getUrl());
            zos.closeEntry();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static <T extends PackItem> void createDirPack(File dir, Iterable<T> items,
                                                          Consumer<T> newItemSuccess,
                                                          Consumer<T> newItemFail,
                                                          Runnable done) {
        for (T item : items) {
            final File itemFile = new File(dir.getAbsolutePath() + "/" + item.getFileName());

            try (final FileOutputStream fos = new FileOutputStream(itemFile)) {
                saveFromUrl(fos, item.getUrl());
                newItemSuccess.accept(item);
            } catch (IOException e) {
                newItemFail.accept(item);
            }
        }
        done.run();
    }

    public static void saveFromUrl(OutputStream output, String url) throws IOException {
        try (final InputStream input = new URL(url).openStream()) {
            final byte[] bytes = new byte[1024];
            int c;
            while ((c = input.read(bytes)) > 0) {
                output.write(bytes, 0, c);
            }
        }
    }

    private PackSupport() {}
}
