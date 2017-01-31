package app.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    private ItemNameHelper itemNameHelper;

    @Value("${zip.dir.path}")
    private String zipDirPath;

    @Autowired
    public ZipService(UserMediaProvider provider, ItemNameHelper nameHelper) {
        picturesProvider = provider;
        itemNameHelper = nameHelper;
    }

    @PostConstruct
    private void createZipDir() {
        File zipDir = new File(zipDirPath);
        if (!zipDir.exists()) {
            zipDir.mkdirs();
        }
    }

    public void createZip(String username) throws IOException, InterruptedException {
        BlockingDeque<Item> itemsDeque = new LinkedBlockingDeque<>();

        new Thread(() -> picturesProvider.getUserMedia(username, itemsDeque)).start();

        final ZipOutputStream zipOutputStream = createZipOutputStream(zipDirPath, username);

        Item item = new Item();
        item.createdTime = 0;
        while (item.createdTime != -1) {
            item = itemsDeque.takeFirst();
            if (item.createdTime == -1) continue;
            final String name = itemNameHelper.getName(item);
            addItemToZip(item.url, username + "/" + name, zipOutputStream);
        }

        zipOutputStream.close();
    }

    private ZipOutputStream createZipOutputStream(String path, String zipName) throws IOException {
        zipName += ".zip";
        final File file = new File(path + zipName);
        final FileOutputStream fos = new FileOutputStream(file);
        return new ZipOutputStream(fos);
    }

    private void addItemToZip(String url, String picName, ZipOutputStream zipOutputStream) throws IOException {
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
