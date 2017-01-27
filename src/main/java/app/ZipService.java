package app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipService {

    public void createZip(String[] urls, String zipName) throws IOException {
        zipName += ".zip";
        final URL resource = getClass().getResource("/public/zip/");

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(resource.getFile() + zipName));

        for (int i = 0; i < urls.length; i++) {
            addPicToZip(urls[i], "pic_" + i, out);
        }

        out.close();
    }

    private void addPicToZip(String url, String picName, ZipOutputStream zipOutputStream) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry("pics/" + picName + ".jpg"));
        InputStream in = new URL(url).openStream();
        byte[] b = new byte[1024];
        int count;

        while ((count = in.read(b)) > 0) {
            zipOutputStream.write(b, 0, count);
        }
        in.close();
    }
}
