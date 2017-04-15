package inpacker.core;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PackSupport {

    public static boolean saveToZip(PackItem item, ZipOutputStream zos) {
        try {
            zos.putNextEntry(new ZipEntry(item.getFileName()));
            save(zos, item.getUrl());
            zos.closeEntry();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void save(OutputStream output, String url) throws IOException {
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
