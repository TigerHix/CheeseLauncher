package com.mojang.minecraftpe.store;

public class Store {
    private StoreListener mListener;

    public Store(StoreListener storeListener) {
        mListener = storeListener;
    }

    public String getStoreId() {
        return "PLACEHOLDER.";
    }

    public void purchase(String s) {}
    public void queryProducts(String as[]) {}
    public void queryPurchases() {}
    public void destructor() {}
}
