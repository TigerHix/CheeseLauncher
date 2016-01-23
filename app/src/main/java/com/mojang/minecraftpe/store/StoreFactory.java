package com.mojang.minecraftpe.store;

public class StoreFactory {
    public StoreFactory() {}

    public static Store createSamsungAppStore(StoreListener storeListener) {
        return new Store(storeListener);
    }

    public static Store createAmazonAppStore(StoreListener storeListener) {
        return new Store(storeListener);
    }

    public static Store createGooglePlayStore(String something,
                                              StoreListener storeListener) {
        return new Store(storeListener);
    }
}
