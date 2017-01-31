package app.core.impl;

import app.core.Item;
import app.core.ItemNameHelper;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.Instant.ofEpochSecond;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;

@Service
public class ItemNameHelperImpl implements ItemNameHelper {

    @Override
    public String getName(Item item) {
        Objects.requireNonNull(item, "item is null");
        final LocalDateTime date = ofInstant(ofEpochSecond(item.createdTime), systemDefault());
        final String extension = getFilenameExtension(item.type);
        return String.format("%s_%s.%s", date.toLocalDate().toString(), item.id, extension);
    }

    private String getFilenameExtension(String type) {
        switch (type) {
            case "image": return "jpg";
            case "video": return "mp4";
            default: throw new IllegalArgumentException("unknown type user media type: " + type);
        }
    }


}
