package com.mojang.minecraftpe.store;

/**
 * Created by cheesy on 1/8/16.
 */
public class NativeStoreListener implements StoreListener {
    private long mCallback;

    public NativeStoreListener(long l) {
        mCallback = l;
    }
}
