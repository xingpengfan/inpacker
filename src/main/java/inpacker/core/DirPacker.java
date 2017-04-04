package inpacker.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.function.Consumer;

public class DirPacker<I extends PackItem> implements Packer<I> {

    @Override
    public void pack(BlockingDeque<I> itemsDeque,
                     File packDir,
                     String packName,
                     Consumer<I> newItemSuccess,
                     Consumer<I> newItemFail,
                     Runnable done,
                     Runnable failed) {
        final File dir = new File(packDir, packName);
        dir.mkdirs();
        I item = takeItem(itemsDeque);
        if (item == null) {
            failed.run();
            return;
        }
        while (!item.getFileName().equals("end")) {
            try {
                final File itemFile = new File(dir, item.getFileName());
                PackSupport.saveFromUrl(new FileOutputStream(itemFile), item.getUrl());
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
        done.run();
    }

    private I takeItem(BlockingDeque<I> deque) {
        try {
            return deque.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
}
