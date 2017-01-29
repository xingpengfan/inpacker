package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipService {

    private UserPicturesProvider picturesProvider;

    @Value("${zip.dir.path}")
    private String zipDirPath;

    @Autowired
    public ZipService(UserPicturesProvider provider) {
        picturesProvider = provider;
    }

    public void createZip(String username) throws IOException, InterruptedException {
        // create zip directory if it does not exist
        File zipDir = new File(zipDirPath);
        if (!zipDir.exists()) {
            zipDir.mkdirs();
        }

        BlockingDeque<String> urlsDeque = new LinkedBlockingDeque<>();

        new Thread(() -> picturesProvider.getUserPicturesUrls(username, urlsDeque)).start();

        final ZipOutputStream zipOutputStream = createZipOutputStream(zipDirPath, username);

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

    private ZipOutputStream createZipOutputStream(String path, String zipName) throws IOException {
        zipName += ".zip";
        final File file = new File(path + zipName);
        final FileOutputStream fos = new FileOutputStream(file);
        return new ZipOutputStream(fos);
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
