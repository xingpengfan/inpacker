package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class MainService {

    @Value("${zip.dir.path}")
    private String zipDirPath;

    public List<String> getZipDirContent() {
        File zipDir = new File(zipDirPath);
        final String[] files = zipDir.list();
        return Arrays.asList(files);
    }
}
