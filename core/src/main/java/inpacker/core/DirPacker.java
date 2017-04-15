package inpacker.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.function.Consumer;

public class DirPacker<I extends PackItem> implements Packer<I> {

    @Override
    public void pack(BlockingDeque<I> itemsDeque,
                     File packsDir,
                     String packId,
                     Consumer<I> newItemSuccess,
                     Consumer<I> newItemFail,
                     Consumer<File> done,
                     Runnable failed) {
        if (!packsDir.exists())
            throw new IllegalArgumentException("packsDir does not exist");
        final File packDir = new File(packsDir, packId);
        if (!packDir.mkdir())
            throw new RuntimeException("unable to create pack directory");
        I item = takeItem(itemsDeque);
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

    private I takeItem(BlockingDeque<I> deque) {
        try {
            return deque.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
}
