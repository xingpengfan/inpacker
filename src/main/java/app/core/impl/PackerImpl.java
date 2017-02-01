package app.core.impl;

import app.core.Item;
import app.core.Packer;

import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.function.Function;

@Service
public class PackerImpl implements Packer {

    @Override
    public void pack(BlockingDeque<Item> itemsDeque, Function<Item, String> getFileName) {

    }
}
