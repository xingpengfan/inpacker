package inpacker.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.function.Consumer;

public class DirPacker implements Packer {

    @Override
    public void pack(BlockingDeque<PackItem> itemsDeque,
                     File packsDir,
                     String packId,
                     Consumer<PackItem> newItemSuccess,
                     Consumer<PackItem> newItemFail,
                     Consumer<File> done,
                     Runnable failed) {
        if (!packsDir.exists())
            throw new IllegalArgumentException("packsDir does not exist");
        final File packDir = new File(packsDir, packId);
        if (!packDir.mkdir())
            throw new RuntimeException("unable to create pack directory");
        PackItem item = takeItem(itemsDeque);
        if (item == null) {
            failed.run();
            return;
        }
        while (!item.getFileName().equals("end")) {
            try {
                final File itemFile = new File(packDir, item.getFileName());
                PackSupport.save(new FileOutputStream(itemFile), item.getUrl());
                newItemSuccess.accept(item);
                item = takeItem(itemsDeque);
                if (item == null) {
                    failed.run();
                    return;
                }
            } catch (IOException e) {
                newItemFail.accept(item);
            }
        }
        done.accept(packDir);
    }

    private <I extends PackItem> I takeItem(BlockingDeque<I> deque) {
        try {
            return deque.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
}
