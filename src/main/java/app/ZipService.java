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

    private UserMediaProvider picturesProvider;

    @Value("${zip.dir.path}")
    private String zipDirPath;

    @Autowired
    public ZipService(UserMediaProvider provider) {
        picturesProvider = provider;
    }

    public void createZip(String username) throws IOException, InterruptedException {
        BlockingDeque<Item> itemsDeque = new LinkedBlockingDeque<>();

        new Thread(() -> picturesProvider.getUserPicturesUrls(username, itemsDeque)).start();

        final ZipOutputStream zipOutputStream = createZipOutputStream(zipDirPath, username);

        Item item = new Item();
        item.createdTime = 0;
        int picName = 1;
        while (item.createdTime != -1) {
            item = itemsDeque.takeFirst();
            if (item.createdTime == -1) continue;
            addPicToZip(item.url, username + "/" + picName + ".jpg", zipOutputStream);
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
