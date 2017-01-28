package app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Service {

    private UserPicturesProvider picturesProvider;

    private static final String ZIP_DIR = "/public/zip/";

    public Service() {
        picturesProvider = new UserPicturesProviderImpl();
    }

    public void createZip(String username) throws IOException, InterruptedException {
        BlockingDeque<String> urlsDeque = new LinkedBlockingDeque<>();

        new Thread(() -> picturesProvider.getUserPicturesUrls(username, urlsDeque)).start();

        final ZipOutputStream zipOutputStream = createZipOutputStream(ZIP_DIR, username);

        String url = "";
        int picName = 1;
        while (!url.equals("end")) {
            url = urlsDeque.takeFirst();
            if (url.equals("end")) continue;
            addPicToZip(url, username + "/" + picName + ".jpg", zipOutputStream);
            picName++;
        }

        zipOutputStream.close();
    }

    private ZipOutputStream createZipOutputStream(String path, String zipName) throws FileNotFoundException {
        zipName += ".zip";
        final URL resource = getClass().getResource(path);
        return new ZipOutputStream(new FileOutputStream(resource.getFile() + zipName));
    }

    private void addPicToZip(String url, String picName, ZipOutputStream zipOutputStream) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(picName));
        InputStream in = new URL(url).openStream();
        byte[] b = new byte[1024];
        int count;

        while ((count = in.read(b)) > 0) {
            zipOutputStream.write(b, 0, count);
        }
        in.close();
    }

}
