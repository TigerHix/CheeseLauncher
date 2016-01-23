package com.mojang.android.net;

public class HTTPResponse {
    private final String mBody;
    private int mResponseCode;
    private int mStatus;

    public HTTPResponse(int i, int j, String s) {
        mStatus = i;
        mResponseCode = j;
        mBody = s;
    }

    public String getBody() { return mBody; }
    public int getResponseCode() { return mResponseCode; }
    public int getStatus() { return mStatus; }

    public static final int STATUS_FAIL = 0;
    public static final int STATUS_SUCCESS = 1;
}
